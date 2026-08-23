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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * One Word document: its body, its numbering, and the parts that go around them.
 * <p>
 * A .docx is a zip of XML parts. Everything fixed comes out of the module's resources; what
 * is left is the body, the numbering, and the relationships that name the images. This
 * writes the parts into a directory, which is what the comparison against the reference
 * output reads and what a zip is made from.
 */
public final class DocxDocument {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * How wide a diagram is drawn, in English Metric Units: the width of the text on the
     * page. The height follows from the proportions of the diagram.
     */
    private static final int WIDTH_EMU = 5722620;

    private static final String HEADER
            = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + DocxText.BODY_SEPARATOR
            + "<w:document xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\""
            + " xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
            + " xmlns:o=\"urn:schemas-microsoft-com:office:office\""
            + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
            + " xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\""
            + " xmlns:v=\"urn:schemas-microsoft-com:vml\""
            + " xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\""
            + " xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\""
            + " xmlns:w10=\"urn:schemas-microsoft-com:office:word\""
            + " xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
            + " xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\""
            + " xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\""
            + " xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\""
            + " xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\""
            + " xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\""
            + " mc:Ignorable=\"w14 wp14\">" + DocxText.BODY_SEPARATOR;

    private static final String RELATIONSHIPS
            = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
            + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/"
            + "2006/relationships/styles\" Target=\"styles.xml\"/>"
            + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/"
            + "2006/relationships/numbering\" Target=\"numbering.xml\"/>";

    private final File folder;

    private final DocxNumbering numbering;

    private final DocxBody body;

    private final List<String> images = new ArrayList<String>();

    /**
     * @param folder The directory the parts are written into.
     * @throws IOException if the fixed parts are missing from the build.
     */
    public DocxDocument(File folder) throws IOException {
        this.folder = folder;
        this.numbering = new DocxNumbering();
        this.body = new DocxBody(numbering);
    }

    /**
     * @return the body of the document, to write into.
     */
    public DocxBody getBody() {
        return body;
    }

    /**
     * Adds a diagram, rasterised from the SVG the specification declares.
     * <p>
     * A .docx carries pictures, not drawings, so the SVG is rendered to a PNG beside the
     * document and the body points at it. The drawing is set to the width of the text and
     * as tall as its own proportions make it.
     *
     * @param svg The SVG of the diagram, as the specification wrote it.
     * @throws IOException if the diagram cannot be read or rendered.
     */
    public void addDiagram(String svg) throws IOException {
        int index = images.size() + 1;
        String name = "image" + index;
        int[] size = rasterise(svg, new File(folder, "word/media"), name);
        images.add(name);

        int height = size[1] * (WIDTH_EMU / size[0]);
        body.appendDrawing(index, height, WIDTH_EMU);
    }

    /**
     * Writes every part of the document.
     *
     * @throws IOException if a part cannot be written.
     */
    public void write() throws IOException {
        write(new File(folder, "[Content_Types].xml"),
                DocxResources.read(DocxResources.CONTENT_TYPES));
        write(new File(folder, "_rels/.rels"),
                DocxResources.read(DocxResources.PACKAGE_RELS));
        write(new File(folder, "word/styles.xml"), DocxResources.read(DocxResources.STYLES));
        write(new File(folder, "word/numbering.xml"), numbering.toXml());
        write(new File(folder, "word/document.xml"), HEADER
                + DocxText.line(1, "<w:body>", DocxText.BODY_SEPARATOR)
                + body.toXml()
                + DocxText.line(1, "</w:body>", DocxText.BODY_SEPARATOR)
                + DocxText.line(0, "</w:document>", DocxText.BODY_SEPARATOR));

        StringBuilder rels = new StringBuilder(RELATIONSHIPS);
        for (String image : images) {
            rels.append("<Relationship Id=\"").append(image)
                    .append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/")
                    .append("relationships/image\" Target=\"media/").append(image).append(".png\"/>");
        }
        write(new File(folder, "word/_rels/document.xml.rels"),
                rels.append("</Relationships>").toString());
    }

    /**
     * Renders an SVG diagram to a PNG.
     *
     * @param svg The SVG the specification declares.
     * @param into The directory to write the image into.
     * @param name The name of the image, without its extension.
     * @return the width and height the SVG says it is.
     * @throws IOException if the diagram cannot be read or rendered.
     */
    private static int[] rasterise(String svg, File into, String name) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Element declared = builder.parse(
                    new ByteArrayInputStream(svg.getBytes(UTF8))).getDocumentElement();

            // Rebuilt on Batik's own document rather than rendered where it stands: the
            // transcoder reads the size off the root, and the declared SVG is a fragment of
            // a specification rather than a document in its own right.
            DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
            Document document = impl.createDocument(
                    SVGDOMImplementation.SVG_NAMESPACE_URI, "svg", null);
            Element root = document.getDocumentElement();
            int[] size = new int[]{
                Integer.parseInt(declared.getAttribute("width")),
                Integer.parseInt(declared.getAttribute("height"))};
            root.setAttributeNS(null, "width", declared.getAttribute("width"));
            root.setAttributeNS(null, "height", declared.getAttribute("height"));
            Node imported = document.importNode(declared, true);
            root.appendChild(imported);

            into.mkdirs();
            OutputStream out = new FileOutputStream(new File(into, name + ".png"));
            try {
                new PNGTranscoder().transcode(
                        new TranscoderInput(document), new TranscoderOutput(out));
                out.flush();
            } finally {
                out.close();
            }
            return size;
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IOException("Could not render the diagram '" + name + "'", ex);
        }
    }

    private static void write(File file, String content) throws IOException {
        file.getParentFile().mkdirs();
        Writer out = new OutputStreamWriter(new FileOutputStream(file), UTF8);
        try {
            out.write(content);
        } finally {
            out.close();
        }
    }
}
