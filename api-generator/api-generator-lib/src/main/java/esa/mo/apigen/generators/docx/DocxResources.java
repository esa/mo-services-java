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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Reads the fixed parts of a Word document out of the module's resources.
 * <p>
 * A .docx carries a good deal of XML that says nothing about the specification being
 * documented - the style sheet, the content types, the package relationships, the numbering
 * definitions. It was 270 000 characters of escaped string literal in the generator this
 * replaces; here it is the files it actually is, so that a style can be read, diffed and
 * edited as XML.
 */
public final class DocxResources {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * The style sheet in use: Times New Roman at 12 point, with the MO table style. Four
     * others are kept beside it - Calibri, plain Times, Times with the MO table style, and
     * the CCSDS sheet - none of them selected by anything. They are carried because they
     * were carried before, not because anything reaches them.
     */
    public static final String STYLES = "styles.xml";

    public static final String CONTENT_TYPES = "content-types.xml";

    public static final String PACKAGE_RELS = "package.rels";

    public static final String NUMBERING_PREAMBLE = "numbering-preamble.xml";

    public static final String NUMBERING_INSTANCE = "numbering-instance.xml";

    private DocxResources() {
    }

    /**
     * Reads one of the fixed parts.
     *
     * @param name The resource name, one of the constants here.
     * @return the contents.
     * @throws IOException if the resource is missing from the build.
     */
    public static String read(String name) throws IOException {
        InputStream in = DocxResources.class.getResourceAsStream("/docx/" + name);
        if (in == null) {
            throw new IOException("The docx resource '" + name + "' is not on the classpath");
        }
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] chunk = new byte[8192];
            int read;
            while ((read = in.read(chunk)) != -1) {
                buf.write(chunk, 0, read);
            }
            return new String(buf.toByteArray(), UTF8);
        } finally {
            in.close();
        }
    }
}
