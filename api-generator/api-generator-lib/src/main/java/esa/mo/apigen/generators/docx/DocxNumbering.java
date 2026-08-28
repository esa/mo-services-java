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
package esa.mo.apigen.generators.docx;

import java.io.IOException;

/**
 * The numbering definitions of one document.
 * <p>
 * A Word document keeps its list numbering apart from its body: the body says which
 * numbering an item belongs to, and the numbering part says what that looks like. Two are
 * defined up front - a dash bullet and a symbol bullet - and a further one is defined each
 * time a numbered list is started, so that lists number from the top rather than continuing
 * one another. That is why this is not a fixed resource like the style sheet, and why every
 * document ends up with a numbering part of its own.
 */
public final class DocxNumbering {

    private final StringBuilder definitions = new StringBuilder();

    private final StringBuilder instances = new StringBuilder();

    /**
     * Counts the numberings handed out. The preamble defines 0 and 1, so the first one
     * asked for is 2.
     */
    private int lastInstance = 1;

    /**
     * @throws IOException if the fixed part of the numbering is missing from the build.
     */
    public DocxNumbering() throws IOException {
        definitions.append(DocxResources.read(DocxResources.NUMBERING_PREAMBLE))
                .append(DocxText.SEPARATOR);
        instances.append(DocxText.line(2,
                "<w:num w:numId=\"1\"><w:abstractNumId w:val=\"1\"/></w:num>", DocxText.SEPARATOR));
    }

    /**
     * Defines a numbering for a list that is about to start.
     *
     * @return the identifier the body refers to it by.
     * @throws IOException if the template is missing from the build.
     */
    public int nextInstance() throws IOException {
        int instance = ++lastInstance;
        String id = String.valueOf(instance);
        definitions.append(DocxResources.read(DocxResources.NUMBERING_INSTANCE)
                .replace("${id}", id)).append(DocxText.SEPARATOR);
        instances.append(DocxText.line(2, "<w:num w:numId=\"" + id
                + "\"><w:abstractNumId w:val=\"" + id + "\"/></w:num>", DocxText.SEPARATOR));
        return instance;
    }

    /**
     * @return the numbering part of the document: every definition, then every instance
     * that refers to one.
     */
    public String toXml() {
        return definitions.toString() + instances + "</w:numbering>" + DocxText.SEPARATOR;
    }
}
