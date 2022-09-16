/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
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
package esa.mo.tools.stubgen.writers;

/**
 * Basic implementation of the base writer that adds a few extra support
 * methods.
 */
public abstract class AbstractWriter implements TargetWriter {

    private final String lineSeparator;

    /**
     * Constructor.
     *
     * @param lineSeparator The line separator to use.
     */
    public AbstractWriter(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * Constructor.
     */
    public AbstractWriter() {
        this.lineSeparator = "\n";
    }

    @Override
    public String getLineSeparator() {
        return lineSeparator;
    }

    @Override
    public void addTypeDependency(String typeName) {
    }

    /**
     * Creates a String indented correctly with a semicolon at the end if
     * required.
     *
     * @param tabCount Indentation level.
     * @param statement The file statement.
     * @param addSemi True if a trailing semicolon is required.
     * @return the created string.
     */
    public String addFileStatement(int tabCount, String statement, boolean addSemi) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < tabCount; i++) {
            buf.append("  ");
        }

        buf.append(statement);
        if (addSemi) {
            buf.append(";");
        }

        buf.append(lineSeparator);

        return buf.toString();
    }
}
