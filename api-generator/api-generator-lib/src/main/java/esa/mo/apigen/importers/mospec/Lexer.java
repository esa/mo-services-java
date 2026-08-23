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

import esa.mo.apigen.importers.ImportException;
import java.util.ArrayList;
import java.util.List;

/**
 * Turns MOSpec text into tokens.
 * <p>
 * The language has one documentation syntax, in two spellings: {@code ///} for a line and
 * {@code /** … **}{@code /} for a block. Both produce a single DOC token carrying the text
 * with its markers stripped, which is what makes the readable syntax the parsed syntax -
 * there is nothing to rewrite before parsing.
 */
public final class Lexer {

    private final String source;
    private final String where;

    private int position = 0;
    private int line = 1;
    private int column = 1;

    /**
     * @param source The text to read.
     * @param where What to call it in an error message.
     */
    public Lexer(String source, String where) {
        this.source = source;
        this.where = where;
    }

    /**
     * @return every token of the source, ending with one of kind END.
     * @throws ImportException if the text cannot be read.
     */
    public List<Token> tokens() throws ImportException {
        List<Token> tokens = new ArrayList<Token>();
        while (true) {
            Token token = next();
            tokens.add(token);
            if (token.getKind() == Token.Kind.END) {
                return tokens;
            }
        }
    }

    private Token next() throws ImportException {
        skipBlanksAndOrdinaryComments();
        if (position >= source.length()) {
            return token(Token.Kind.END, "");
        }

        int startLine = line;
        int startColumn = column;
        char c = source.charAt(position);

        if (startsDoc()) {
            return new Token(Token.Kind.DOC, readDoc(), startLine, startColumn);
        }
        if (c == '"') {
            return new Token(startsText() ? Token.Kind.TEXT : Token.Kind.STRING,
                    startsText() ? readText() : readString(), startLine, startColumn);
        }
        if (Character.isDigit(c) || (c == '-' && isDigitAt(position + 1))) {
            return new Token(Token.Kind.NUMBER, readNumber(), startLine, startColumn);
        }
        if (Character.isLetter(c) || c == '_') {
            return new Token(Token.Kind.WORD, readWord(), startLine, startColumn);
        }
        // Two-character punctuation first, so that -> is not read as - then >.
        if (c == '-' && isAt(position + 1, '>')) {
            advance();
            advance();
            return new Token(Token.Kind.PUNCTUATION, "->", startLine, startColumn);
        }
        if (c == ':' && isAt(position + 1, ':')) {
            advance();
            advance();
            return new Token(Token.Kind.PUNCTUATION, "::", startLine, startColumn);
        }
        advance();
        return new Token(Token.Kind.PUNCTUATION, String.valueOf(c), startLine, startColumn);
    }

    /**
     * Steps over anything that carries no meaning: whitespace, and comments that are not
     * documentation.
     */
    private void skipBlanksAndOrdinaryComments() throws ImportException {
        while (position < source.length()) {
            char c = source.charAt(position);
            if (Character.isWhitespace(c)) {
                advance();
            } else if (isAt(position, '/') && isAt(position + 1, '/') && !startsDoc()) {
                while (position < source.length() && source.charAt(position) != '\n') {
                    advance();
                }
            } else if (isAt(position, '/') && isAt(position + 1, '*') && !startsDoc()) {
                advance();
                advance();
                while (position < source.length()
                        && !(isAt(position, '*') && isAt(position + 1, '/'))) {
                    advance();
                }
                expect('*');
                expect('/');
            } else {
                return;
            }
        }
    }

    /**
     * @return true if what follows is documentation rather than an ordinary comment.
     */
    private boolean startsDoc() {
        return (isAt(position, '/') && isAt(position + 1, '/') && isAt(position + 2, '/'))
                || (isAt(position, '/') && isAt(position + 1, '*') && isAt(position + 2, '*'));
    }

    /**
     * Reads documentation, in either spelling, into the text it carries. Consecutive line
     * comments are one piece of documentation, which is how a paragraph is written without
     * a block.
     */
    private String readDoc() throws ImportException {
        if (isAt(position + 1, '*')) {
            return readDocBlock();
        }
        StringBuilder buf = new StringBuilder();
        boolean first = true;
        while (isAt(position, '/') && isAt(position + 1, '/') && isAt(position + 2, '/')) {
            advance();
            advance();
            advance();
            if (isAt(position, ' ')) {
                advance();
            }
            StringBuilder text = new StringBuilder();
            while (position < source.length() && source.charAt(position) != '\n') {
                text.append(source.charAt(position));
                advance();
            }
            buf.append(first ? "" : "\n").append(text);
            first = false;
            skipBlanksBeforeNextDocLine();
        }
        return buf.toString();
    }

    /**
     * Steps over the whitespace between two documentation lines, but no further: a blank
     * line ends the documentation rather than joining what follows to it.
     */
    private void skipBlanksBeforeNextDocLine() {
        int mark = position;
        int markLine = line;
        int markColumn = column;
        int newlines = 0;
        while (position < source.length() && Character.isWhitespace(source.charAt(position))) {
            if (source.charAt(position) == '\n') {
                newlines++;
            }
            advance();
        }
        boolean continues = newlines <= 1 && isAt(position, '/')
                && isAt(position + 1, '/') && isAt(position + 2, '/');
        if (!continues) {
            position = mark;
            line = markLine;
            column = markColumn;
        }
    }

    /**
     * Reads a block of documentation, dropping the leading star of each line.
     */
    private String readDocBlock() throws ImportException {
        advance();
        advance();
        advance();
        StringBuilder buf = new StringBuilder();
        while (position < source.length()
                && !(isAt(position, '*') && isAt(position + 1, '*') && isAt(position + 2, '/'))) {
            buf.append(source.charAt(position));
            advance();
        }
        expect('*');
        expect('*');
        expect('/');

        List<String> lines = new ArrayList<String>();
        for (String written : buf.toString().split("\n", -1)) {
            // Only what is in front of the text is removed - the indentation, the star, and
            // the one space after it. Whatever follows is the text, trailing spaces and all:
            // some comments in the specifications end in one, and the round trip has to
            // give them back unchanged.
            int at = 0;
            while (at < written.length() && (written.charAt(at) == ' '
                    || written.charAt(at) == '\t')) {
                at++;
            }
            if (at < written.length() && written.charAt(at) == '*') {
                at++;
                if (at < written.length() && written.charAt(at) == ' ') {
                    at++;
                }
                lines.add(written.substring(at));
            } else {
                lines.add(written.substring(at));
            }
        }
        // The first and last lines are the ones the markers sat on, and are empty.
        while (!lines.isEmpty() && lines.get(0).trim().isEmpty()) {
            lines.remove(0);
        }
        while (!lines.isEmpty() && lines.get(lines.size() - 1).trim().isEmpty()) {
            lines.remove(lines.size() - 1);
        }
        return join(lines);
    }

    /**
     * @return true if what follows is a triple-quoted block rather than a plain string.
     */
    private boolean startsText() {
        return isAt(position, '"') && isAt(position + 1, '"') && isAt(position + 2, '"');
    }

    /**
     * Reads a triple-quoted block, which keeps its own line breaks and gives up the
     * indentation that was only there to line it up with the code around it.
     */
    private String readText() throws ImportException {
        advance();
        advance();
        advance();
        StringBuilder buf = new StringBuilder();
        while (position < source.length() && !startsText()) {
            buf.append(source.charAt(position));
            advance();
        }
        expect('"');
        expect('"');
        expect('"');
        return undent(buf.toString());
    }

    private String readString() throws ImportException {
        advance();
        StringBuilder buf = new StringBuilder();
        while (position < source.length() && source.charAt(position) != '"') {
            if (source.charAt(position) == '\\' && position + 1 < source.length()) {
                advance();
            }
            buf.append(source.charAt(position));
            advance();
        }
        expect('"');
        return buf.toString();
    }

    private String readNumber() {
        StringBuilder buf = new StringBuilder();
        if (isAt(position, '-')) {
            buf.append('-');
            advance();
        }
        while (position < source.length() && Character.isDigit(source.charAt(position))) {
            buf.append(source.charAt(position));
            advance();
        }
        return buf.toString();
    }

    private String readWord() {
        StringBuilder buf = new StringBuilder();
        while (position < source.length()) {
            char c = source.charAt(position);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                break;
            }
            buf.append(c);
            advance();
        }
        return buf.toString();
    }

    /**
     * Removes the indentation a block was written with, keeping whatever indentation is
     * part of the text itself.
     */
    private static String undent(String text) {
        List<String> lines = new ArrayList<String>();
        for (String written : text.split("\n", -1)) {
            lines.add(written);
        }
        // Exactly one line is dropped at each end, and only if it is blank: those are the
        // lines the delimiters sit on. Dropping every blank line would swallow a blank line
        // the text itself ends with, and some requirements do end with one.
        if (!lines.isEmpty() && lines.get(0).trim().isEmpty()) {
            lines.remove(0);
        }
        if (!lines.isEmpty() && lines.get(lines.size() - 1).trim().isEmpty()) {
            lines.remove(lines.size() - 1);
        }

        int common = Integer.MAX_VALUE;
        for (String written : lines) {
            if (written.trim().isEmpty()) {
                continue;
            }
            int leading = 0;
            while (leading < written.length() && written.charAt(leading) == '\t') {
                leading++;
            }
            common = Math.min(common, leading);
        }
        if (common == Integer.MAX_VALUE || common == 0) {
            return join(lines);
        }
        List<String> trimmed = new ArrayList<String>();
        for (String written : lines) {
            trimmed.add(written.length() >= common ? written.substring(common) : written);
        }
        return join(trimmed);
    }

    private static String join(List<String> lines) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            buf.append(i == 0 ? "" : "\n").append(lines.get(i));
        }
        return buf.toString();
    }

    private void expect(char c) throws ImportException {
        if (position >= source.length() || source.charAt(position) != c) {
            throw new ImportException(where + ":" + line + ":" + column
                    + ": expected '" + c + "'");
        }
        advance();
    }

    private boolean isAt(int at, char c) {
        return at < source.length() && source.charAt(at) == c;
    }

    private boolean isDigitAt(int at) {
        return at < source.length() && Character.isDigit(source.charAt(at));
    }

    private void advance() {
        if (source.charAt(position) == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        position++;
    }

    private Token token(Token.Kind kind, String text) {
        return new Token(kind, text, line, column);
    }
}
