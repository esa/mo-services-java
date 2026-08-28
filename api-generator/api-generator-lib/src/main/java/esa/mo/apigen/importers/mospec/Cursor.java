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
import esa.mo.apigen.model.SourceLocation;
import esa.mo.apigen.model.SourceRef;
import java.util.List;

/**
 * A position in a stream of tokens, with the small vocabulary a recursive-descent parser
 * needs: look at what is next, take it if it is what was expected, complain if it is not.
 */
public final class Cursor {

    private final List<Token> tokens;
    private final SourceRef source;

    private int at = 0;

    public Cursor(List<Token> tokens, SourceRef source) {
        this.tokens = tokens;
        this.source = source;
    }

    /**
     * @return the token about to be read, without reading it.
     */
    public Token peek() {
        return tokens.get(at);
    }

    /**
     * @param ahead How far to look past the next token.
     * @return the token that far ahead, or the last one if that is past the end.
     */
    public Token peek(int ahead) {
        int index = at + ahead;
        return tokens.get(index < tokens.size() ? index : tokens.size() - 1);
    }

    /**
     * @return the next token, and moves past it.
     */
    public Token take() {
        return tokens.get(at++);
    }

    /**
     * Takes the next token if it is the expected word or punctuation.
     *
     * @param expected What to look for.
     * @return true if it was there and was taken.
     */
    public boolean accept(String expected) {
        if (peek().is(expected)) {
            at++;
            return true;
        }
        return false;
    }

    /**
     * Takes the next token, which must be the expected word or punctuation.
     *
     * @param expected What must be there.
     * @return the token taken.
     * @throws ImportException if something else is there.
     */
    public Token expect(String expected) throws ImportException {
        if (!peek().is(expected)) {
            throw error("expected '" + expected + "'");
        }
        return take();
    }

    /**
     * Takes the next token, which must be of the expected kind.
     *
     * @param kind What kind must be there.
     * @param what What to call it in an error message.
     * @return the text of the token taken.
     * @throws ImportException if something else is there.
     */
    public String expect(Token.Kind kind, String what) throws ImportException {
        if (peek().getKind() != kind) {
            throw error("expected " + what);
        }
        return take().getText();
    }

    /**
     * @return true if there is nothing left to read.
     */
    public boolean atEnd() {
        return peek().getKind() == Token.Kind.END;
    }

    /**
     * @return where the next token was written.
     */
    public SourceLocation location() {
        Token token = peek();
        return new SourceLocation(source, token.getLine(), token.getColumn());
    }

    /**
     * @param message What was expected.
     * @return an exception naming the place and what was found instead.
     */
    public ImportException error(String message) {
        Token token = peek();
        return new ImportException(source.getName() + ":" + token.getLine() + ":"
                + token.getColumn() + ": " + message + ", found '" + token.getText() + "'");
    }
}
