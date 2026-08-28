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
package esa.mo.apigen.importers.mospec;

/**
 * One token of a MOSpec file, with where it was found.
 */
public final class Token {

    /**
     * What kind of thing a token is. The language is keyword-led and brace-delimited, so
     * the kinds are few: a word, a number, a piece of text, a piece of punctuation, or the
     * documentation attached to whatever comes next.
     */
    public enum Kind {
        WORD, NUMBER, STRING, TEXT, PUNCTUATION, DOC, END
    }

    private final Kind kind;
    private final String text;
    private final int line;
    private final int column;

    public Token(Kind kind, String text, int line, int column) {
        this.kind = kind;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    public Kind getKind() {
        return kind;
    }

    public String getText() {
        return text;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    /**
     * @param expected The word or punctuation to test for.
     * @return true if this token is exactly that.
     */
    public boolean is(String expected) {
        return expected.equals(text);
    }

    @Override
    public String toString() {
        return kind + " '" + text + "' at " + line + ":" + column;
    }
}
