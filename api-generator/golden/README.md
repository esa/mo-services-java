# Golden-tree harness (Phase 0)

The regression net for replacing the generators with `api-generator-lib`. It captures what the
current generators produce and reports every difference on a later build.

See `../api-generator-lib/DESIGN.md` §10.1.

```
./golden.sh capture        build apis/* and mo-navigator, store the result as the baseline
./golden.sh compare        build again and report every difference from the baseline
./golden.sh snapshot-only  capture from existing target/ output, without rebuilding
./golden.sh compare-only   compare without rebuilding
```

The baseline lives in `testbeds/testbed-api-generator/baseline/` (override with
`GOLDEN_BASELINE=/some/path`) and **is committed**, beside the tests that read it: ~20 MB on disk
but 1.5 MB packed, and it changes only when the old generators do. Keeping it in the repository is
what lets CI run the golden tests, and it makes a change in generated output show up as a diff
rather than only as a failing assertion. Full diffs are written to `last-diff.txt`; the console
shows a per-file summary.

**What else reads the baseline.** `testbeds/testbed-api-generator` holds the JUnit side — the
golden-tree tests that generate with `api-generator-lib` and compare against `baseline/`, so
capturing one here is what makes them run rather than skip:

```
cd testbeds/testbed-api-generator && mvn test
```

Without a baseline those tests skip — which now only happens to someone who has cleared it to
re-capture, since CI has the committed one (DESIGN.md §10.8).

## What it captures

| | |
| --- | --- |
| Java | `apis/*/target/generated-sources/stub` — **950 files, ~116 000 lines** across 7 modules |
| docx | every specification rendered by `AppGenerateDocx`, **unzipped** so `word/document.xml` diffs as text — 18 documents |

Unzipping matters: a `.docx` is a zip, and zips differ byte-wise on timestamps alone. Comparing the
XML inside is both stable and readable.

## The baseline is a change detector, not a definition of correctness

The current output has its own quirks. Every reported difference is one of two things:

- **a defect in the new generator** — fix it;
- **a deliberate improvement** — record it in `intended-differences.txt` with a reason and a date,
  then re-capture so the diff returns to zero.

A permanently fuzzy diff is no safety net, because the next real regression hides in the noise.

A textual diff is also not sufficient on its own once differences are intentional. The second tier
is to compile the regenerated tree and run the existing suites — `mal-impl`, `services-impl`, the
transports and the testbeds all compile against `apis/*`.

## Findings from the first capture

Two specifications produce **no** docx at all. Both are pre-existing, unrelated to this migration,
and worth fixing independently:

- **`area051-v001-Mission-Data-Product.xml`** — generation throws
  `IllegalStateException: Unknown composite super type: String`, and the exception is caught
  per-file, so the run reports success while silently producing nothing for that spec.
- **`area002-v002-Basics.xml`** — declares `name="COM" number="2" version="1"`, identical to
  `area002-v001-COM.xml`. The docx filename is built from area number, version and name
  (`GeneratorDocx:365`), so both specs map to the same file and one silently overwrites the other.
  This is the area-identity collision of design §4.1, causing real data loss today.

Also worth knowing: **`area052-v001-Mission-Data-Product-Distribution.xml` is written as
`Service_Specification_area009-v001-distribution.docx`** — the name comes from the *declared* area
number (9), not the filename (052), so it sits one character away from colliding with MPD, which is
also area 9.

Generation is otherwise deterministic: three consecutive runs produced byte-identical output.
