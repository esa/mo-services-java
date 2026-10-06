/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO API Generator
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ----------------------------------------------------------------------------
 */
package esa.mo.apigen.generators.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Replaces the fully qualified type names of a finished Java source file with simple
 * names, and declares the imports they need.
 * <p>
 * The writers name every type in full, which is always correct. This pass shortens a
 * name only where the simple name cannot mean anything else in the file:
 * <ul>
 * <li>a simple name that the code already uses unqualified - a field, a parameter, a
 * nested class, a type of the same package written short - is left qualified, since in
 * an expression a variable obscures a type of the same name;</li>
 * <li>of two types sharing a simple name, only one is shortened: the one in the file's own
 * package if there is one, otherwise the one named most often;</li>
 * <li>a type is never imported under the name of the class the file declares;</li>
 * <li>{@code java.lang} names stay qualified: a hand-written class in the same package
 * could take the simple name, and that cannot be seen from here.</li>
 * </ul>
 * Only names in code decide what is imported. A name in a comment is shortened if the
 * code imports it anyway, and is left qualified otherwise, so that no import exists for
 * the documentation alone.
 */
public final class JavaImports {

    private static final Pattern QUALIFIED = Pattern.compile(
            "(?<![\\w.])((?:org|java|javax|esa)(?:\\.[a-z_][a-z0-9_]*)+)\\.([A-Z]\\w*)");
    private static final Pattern IDENTIFIER = Pattern.compile("(?<![\\w.])[A-Za-z_]\\w*");
    private static final Pattern PACKAGE = Pattern.compile("^package\\s+([\\w.]+)\\s*;\\n\\n");
    private static final Pattern DECLARED = Pattern.compile(
            "\\b(?:class|interface|enum)\\s+([A-Z]\\w*)");

    private static final Set<String> KEYWORDS = new HashSet<String>(Arrays.asList(
            "abstract", "case", "class", "enum", "extends", "final", "implements",
            "instanceof", "interface", "new", "private", "protected", "public", "return",
            "static", "throw", "throws"));

    private JavaImports() {
    }

    /**
     * @param source A complete compilation unit, starting with its package statement and
     * a blank line, and holding no imports.
     * @return the same unit with qualified names shortened and the imports declared.
     */
    public static String organise(String source) {
        Matcher pkg = PACKAGE.matcher(source);
        if (!pkg.find()) {
            return source;
        }
        String packageName = pkg.group(1);
        List<Segment> segments = Segment.split(source, pkg.end());
        String declared = declaredName(segments);

        Map<String, Map<String, Integer>> candidates = new TreeMap<String, Map<String, Integer>>();
        Set<String> unqualified = new HashSet<String>();
        for (Segment segment : segments) {
            if (segment.kind == Kind.CODE) {
                collect(segment.text, candidates, unqualified, declared);
            }
        }

        Map<String, String> chosen = choose(candidates, unqualified, packageName, declared);

        StringBuilder out = new StringBuilder(source.substring(0, pkg.end()));
        Set<String> imports = new TreeSet<String>();
        for (Map.Entry<String, String> entry : chosen.entrySet()) {
            if (!entry.getValue().equals(packageName)) {
                imports.add(entry.getValue() + "." + entry.getKey());
            }
        }
        for (String name : imports) {
            out.append("import ").append(name).append(";\n");
        }
        if (!imports.isEmpty()) {
            out.append('\n');
        }
        for (Segment segment : segments) {
            out.append(segment.kind == Kind.LITERAL
                    ? segment.text : shorten(segment.text, chosen));
        }
        return out.toString();
    }

    private static String declaredName(List<Segment> segments) {
        for (Segment segment : segments) {
            if (segment.kind == Kind.CODE) {
                Matcher m = DECLARED.matcher(segment.text);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return null;
    }

    /**
     * Records the qualified names in a stretch of code, and every simple name it uses
     * unqualified. An unqualified use of the file's own class name already means that
     * class, so it counts only where it declares a variable of that name.
     */
    private static void collect(String code, Map<String, Map<String, Integer>> candidates,
            Set<String> unqualified, String declared) {
        Matcher q = QUALIFIED.matcher(code);
        while (q.find()) {
            if ("java.lang".equals(q.group(1))) {
                continue;
            }
            Map<String, Integer> packages = candidates.get(q.group(2));
            if (packages == null) {
                packages = new TreeMap<String, Integer>();
                candidates.put(q.group(2), packages);
            }
            Integer count = packages.get(q.group(1));
            packages.put(q.group(1), count == null ? 1 : count + 1);
        }
        Matcher id = IDENTIFIER.matcher(code);
        while (id.find()) {
            String name = id.group();
            if (name.equals(declared) && !declaresVariable(code, id.start())) {
                continue;
            }
            unqualified.add(name);
        }
    }

    /**
     * Returns true if the name at this position is declared as a variable, field,
     * parameter or method: it follows a type rather than a keyword or punctuation.
     */
    private static boolean declaresVariable(String code, int start) {
        int end = start;
        while (end > 0 && Character.isWhitespace(code.charAt(end - 1))) {
            end--;
        }
        if (end == 0) {
            return false;
        }
        char previous = code.charAt(end - 1);
        if (previous == '>' || previous == ']') {
            return true;
        }
        int begin = end;
        while (begin > 0 && Character.isJavaIdentifierPart(code.charAt(begin - 1))) {
            begin--;
        }
        return begin < end && !KEYWORDS.contains(code.substring(begin, end));
    }

    /**
     * @return the package each shortened simple name stands for.
     */
    private static Map<String, String> choose(Map<String, Map<String, Integer>> candidates,
            Set<String> unqualified, String packageName, String declared) {
        Map<String, String> chosen = new HashMap<String, String>();
        for (Map.Entry<String, Map<String, Integer>> entry : candidates.entrySet()) {
            String simple = entry.getKey();
            if (unqualified.contains(simple)) {
                continue;
            }
            Map<String, Integer> packages = entry.getValue();
            if (packages.containsKey(packageName)) {
                chosen.put(simple, packageName);
                continue;
            }
            if (simple.equals(declared)) {
                continue;
            }
            String best = null;
            for (Map.Entry<String, Integer> p : packages.entrySet()) {
                // Ties go to the first package in order, which the TreeMap already gives.
                if (best == null || p.getValue() > packages.get(best)) {
                    best = p.getKey();
                }
            }
            chosen.put(simple, best);
        }
        return chosen;
    }

    private static String shorten(String text, Map<String, String> chosen) {
        Matcher q = QUALIFIED.matcher(text);
        StringBuffer out = new StringBuffer();
        while (q.find()) {
            String replacement = q.group(1).equals(chosen.get(q.group(2)))
                    ? q.group(2) : q.group();
            q.appendReplacement(out, Matcher.quoteReplacement(replacement));
        }
        q.appendTail(out);
        return out.toString();
    }

    private enum Kind {
        CODE, COMMENT, LITERAL
    }

    /**
     * A stretch of source that is all code, all one comment, or all one string or
     * character literal.
     */
    private static final class Segment {

        private final Kind kind;
        private final String text;

        private Segment(Kind kind, String text) {
            this.kind = kind;
            this.text = text;
        }

        private static List<Segment> split(String source, int from) {
            List<Segment> segments = new ArrayList<Segment>();
            int start = from;
            int i = from;
            while (i < source.length()) {
                char c = source.charAt(i);
                int end;
                Kind kind;
                if (source.startsWith("//", i)) {
                    end = source.indexOf('\n', i);
                    end = end < 0 ? source.length() : end;
                    kind = Kind.COMMENT;
                } else if (source.startsWith("/*", i)) {
                    end = source.indexOf("*/", i + 2);
                    end = end < 0 ? source.length() : end + 2;
                    kind = Kind.COMMENT;
                } else if (c == '"' || c == '\'') {
                    end = i + 1;
                    while (end < source.length() && source.charAt(end) != c) {
                        end += source.charAt(end) == '\\' ? 2 : 1;
                    }
                    end = Math.min(end + 1, source.length());
                    kind = Kind.LITERAL;
                } else {
                    i++;
                    continue;
                }
                if (start < i) {
                    segments.add(new Segment(Kind.CODE, source.substring(start, i)));
                }
                segments.add(new Segment(kind, source.substring(i, end)));
                start = end;
                i = end;
            }
            if (start < source.length()) {
                segments.add(new Segment(Kind.CODE, source.substring(start)));
            }
            return segments;
        }
    }
}
