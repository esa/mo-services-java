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
package esa.mo.apigen.model;

/**
 * A position within a source, recorded by importers so that validation issues can point
 * at the text that caused them.
 */
public final class SourceLocation {

    private final SourceRef source;
    private final int line;
    private final int column;

    public SourceLocation(SourceRef source, int line, int column) {
        this.source = source;
        this.line = line;
        this.column = column;
    }

    public SourceRef getSource() {
        return source;
    }

    /**
     * @return the 1-based line number, or 0 if unknown.
     */
    public int getLine() {
        return line;
    }

    /**
     * @return the 1-based column number, or 0 if unknown.
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(source == null ? "<unknown>" : source.getName());
        if (line > 0) {
            buf.append(':').append(line);
            if (column > 0) {
                buf.append(':').append(column);
            }
        }
        return buf.toString();
    }
}
