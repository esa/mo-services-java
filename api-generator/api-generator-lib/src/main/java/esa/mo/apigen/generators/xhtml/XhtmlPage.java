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
package esa.mo.apigen.generators.xhtml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The file a page is written to: what wraps the content, and the writing of it.
 */
public final class XhtmlPage {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * The style sheet, carried as the file it is so it can be read and edited as CSS. It
     * is written into the page rather than beside it, so that a page is one file that can
     * be moved, mailed or opened from anywhere.
     */
    private static final String STYLESHEET = "/xhtml/page.css";

    private XhtmlPage() {
    }

    /**
     * Writes a page.
     *
     * @param file Where to write it.
     * @param title What the page is about, which is what a browser shows in its tab. The
     * generator this replaces wrote every page with an empty title, so a reader with
     * several open could not tell one from another.
     * @param body The content of the page.
     * @throws IOException if writing fails.
     */
    public static void write(Path file, String title, XhtmlBody body) throws IOException {
        StringBuilder out = new StringBuilder();
        out.append(XhtmlText.line(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
        out.append(XhtmlText.line(0, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\""
                + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"));
        out.append(XhtmlText.line(0, "<html xmlns=\"http://www.w3.org/1999/xhtml\""
                + " xmlns:svg=\"http://www.w3.org/2000/svg\""
                + " xmlns:xlink=\"http://www.w3.org/1999/xlink\">"));
        out.append(XhtmlText.line(1, "<head>"));
        out.append(XhtmlText.line(2, "<title>" + XhtmlBody.escape(title) + "</title>"));
        out.append(XhtmlText.line(2, "<style type=\"text/css\">"));
        out.append(stylesheet());
        out.append(XhtmlText.line(2, "</style>"));
        out.append(XhtmlText.line(1, "</head>"));
        out.append(XhtmlText.line(1, "<body>"));
        out.append(body.toString());
        out.append(XhtmlText.line(1, "</body>"));
        out.append(XhtmlText.line(0, "</html>"));

        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        Files.write(file, out.toString().getBytes(UTF8));
    }

    private static String stylesheet() throws IOException {
        InputStream in = XhtmlPage.class.getResourceAsStream(STYLESHEET);
        if (in == null) {
            throw new IOException("The stylesheet '" + STYLESHEET + "' is not on the classpath");
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
