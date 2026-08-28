#!/usr/bin/env bash
#
# Phase 0 regression harness for the api-generator-lib migration.
#
#   ./golden.sh capture    build with the current generators, store the result as the baseline
#   ./golden.sh compare    build again and report every difference from the baseline
#
# The baseline detects change; it does not define correctness. A reported difference is
# either a defect in the new generator, or a deliberate improvement — in which case record
# it in intended-differences.txt, then re-capture so the diff returns to zero.
#
# See ../api-generator-lib/DESIGN.md §10.1.

set -u -o pipefail

HERE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO="$(cd "$HERE/../.." && pwd)"
BASELINE="${GOLDEN_BASELINE:-$REPO/testbeds/testbed-api-generator/baseline}"
IGNORE_FILE="$HERE/intended-differences.txt"
MVN="${MVN:-mvn}"
MVN_FLAGS="${MVN_FLAGS:---batch-mode -DskipTests}"

API_MODULES=$(cd "$REPO" && ls -d apis/api-*/ 2>/dev/null | sed 's#/$##' | sort)

log()  { printf '\033[1m==>\033[0m %s\n' "$*"; }
warn() { printf '\033[33m !!\033[0m %s\n' "$*"; }
die()  { printf '\033[31mERROR\033[0m %s\n' "$*" >&2; exit 1; }

# ---------------------------------------------------------------- build

build_java() {
    log "Building apis/* with the current generators"
    local list; list=$(echo "$API_MODULES" | paste -sd,)
    (cd "$REPO" && $MVN $MVN_FLAGS install -pl "$list" --also-make) \
        || die "Maven build failed"
}

build_navigator() {
    log "Building mo-navigator (for docx generation)"
    (cd "$REPO" && $MVN $MVN_FLAGS install -pl tooling/mo-navigator --also-make) \
        || die "mo-navigator build failed"
}

# ------------------------------------------------------------- snapshot

# snapshot_java <destdir> — copy every generated Java tree, preserving module layout
snapshot_java() {
    local dest="$1"; mkdir -p "$dest"
    local n=0
    for m in $API_MODULES; do
        local src="$REPO/$m/target/generated-sources/stub"
        [ -d "$src" ] || { warn "no generated sources in $m — was it built?"; continue; }
        mkdir -p "$dest/$(basename "$m")"
        cp -r "$src/." "$dest/$(basename "$m")/"
        n=$((n + $(find "$src" -name '*.java' | wc -l)))
    done
    echo "$n"
}

# snapshot_docx <destdir> — generate docx for every spec, unzipped so it diffs as text
snapshot_docx() {
    local dest="$1"; mkdir -p "$dest"
    local jar="$REPO/tooling/mo-navigator/target/mo-navigator-jar-with-dependencies.jar"
    [ -f "$jar" ] || { warn "mo-navigator jar not found — skipping docx"; echo 0; return; }

    local tmp; tmp=$(mktemp -d)
    local n=0
    for set_name in prototypes standards; do
        local xmldir="$REPO/xml-service-specifications/xml-ccsds-mo-$set_name/src/main/resources/xml"
        [ -d "$xmldir" ] || continue
        rm -rf "$tmp/$set_name"; mkdir -p "$tmp/$set_name"
        java -cp "$jar" esa.mo.navigator.AppGenerateDocx "$xmldir" "$tmp/$set_name" >/dev/null 2>&1 \
            || warn "docx generation reported an error for $set_name"
        while IFS= read -r -d '' f; do
            local name; name=$(basename "$f" .docx)
            local out="$dest/$set_name/$name"
            mkdir -p "$out"
            (cd "$out" && unzip -oq "$f")     # unzip: word/document.xml et al diff as text
            # The rasterised diagrams are not kept. The new generator no longer draws them
            # (DESIGN.md 8.3), they are the only part of a capture that neither compresses
            # nor deltas, and a PNG shows nothing in a diff. What the old generator still
            # emits around them is compared, with the drawing markup normalised away.
            rm -rf "$out/word/media"
            n=$((n+1))
        done < <(find "$tmp/$set_name" -name '*.docx' -print0 2>/dev/null)
    done
    rm -rf "$tmp"
    echo "$n"
}

snapshot_all() {
    local dest="$1"
    rm -rf "$dest"; mkdir -p "$dest"
    local nj nd
    nj=$(snapshot_java "$dest/java")
    nd=$(snapshot_docx "$dest/docx")
    {
        echo "captured:  $(date -u +%Y-%m-%dT%H:%M:%SZ)"
        echo "commit:    $(cd "$REPO" && git rev-parse --short HEAD 2>/dev/null || echo unknown)"
        echo "branch:    $(cd "$REPO" && git rev-parse --abbrev-ref HEAD 2>/dev/null || echo unknown)"
        echo "java files: $nj"
        echo "docx documents: $nd"
    } > "$dest/MANIFEST"
    log "Snapshot: $nj Java files, $nd docx documents"
}

# -------------------------------------------------------------- compare

compare_to_baseline() {
    local fresh="$1"
    [ -d "$BASELINE" ] || die "No baseline at $BASELINE — run '$0 capture' first"

    local excludes=()
    if [ -f "$IGNORE_FILE" ]; then
        while IFS= read -r line; do
            line="${line%%#*}"; line="$(echo "$line" | xargs)"
            [ -n "$line" ] && excludes+=(--exclude="$line")
        done < "$IGNORE_FILE"
    fi

    log "Comparing against $BASELINE"
    local report="$HERE/last-diff.txt"
    diff -r -u "${excludes[@]}" \
         --exclude=MANIFEST \
         "$BASELINE" "$fresh" > "$report" 2>&1
    local rc=$?

    if [ $rc -eq 0 ]; then
        log "No differences."
        rm -f "$report"
        return 0
    fi

    # per-file summary, which is the useful view
    echo
    warn "Differences found — full diff in $report"
    echo
    grep -E '^(diff -r|Only in)' "$report" \
        | sed -e 's#^diff -r -u [^ ]* #  changed: #' \
              -e "s#$BASELINE/##g" -e "s#$fresh/##g" \
              -e 's#^Only in #  only in: #' \
        | sort | uniq -c | sed 's/^ *[0-9]* //' | head -60
    echo
    local changed only
    changed=$(grep -c '^diff -r' "$report" || true)
    only=$(grep -c '^Only in' "$report" || true)
    printf '  %s file(s) differ, %s present on one side only\n\n' "$changed" "$only"
    return 1
}

# ----------------------------------------------------------------- main

case "${1:-help}" in
  capture)
      build_java; build_navigator
      snapshot_all "$BASELINE"
      log "Baseline written to $BASELINE"
      ;;
  compare)
      build_java; build_navigator
      fresh=$(mktemp -d)
      trap 'rm -rf "$fresh"' EXIT
      snapshot_all "$fresh"
      compare_to_baseline "$fresh"
      ;;
  snapshot-only)                       # capture without rebuilding — uses existing target/
      snapshot_all "$BASELINE"
      log "Baseline written to $BASELINE (no rebuild)"
      ;;
  compare-only)                        # compare without rebuilding
      fresh=$(mktemp -d)
      trap 'rm -rf "$fresh"' EXIT
      snapshot_all "$fresh"
      compare_to_baseline "$fresh"
      ;;
  *)
      sed -n '2,14p' "$0" | sed 's/^# \{0,1\}//'
      ;;
esac
