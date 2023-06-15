/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
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
package esa.mo.tools.stubgen.docx;

import esa.mo.tools.stubgen.StubUtils;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Cesar.Coelho
 */
public class DocxWriter extends DocxBaseWriter {

    public int IMAGE_INDEX = 1;
    private final String destinationFolder;
    private final Writer file;
    private final StringBuffer docxRelBuf;

    public DocxWriter(String folder, String className, String ext,
            DocxNumberingWriter numberWriter) throws IOException {
        super(numberWriter);

        destinationFolder = folder;
        file = StubUtils.createLowLevelWriter(folder, className, ext);
        file.append(makeLine(0, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", false));
        file.append(makeLine(0, "<w:document xmlns:wpc=\"http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:wp14=\"http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing\" xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" xmlns:w10=\"urn:schemas-microsoft-com:office:word\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:w14=\"http://schemas.microsoft.com/office/word/2010/wordml\" xmlns:wpg=\"http://schemas.microsoft.com/office/word/2010/wordprocessingGroup\" xmlns:wpi=\"http://schemas.microsoft.com/office/word/2010/wordprocessingInk\" xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\" xmlns:wps=\"http://schemas.microsoft.com/office/word/2010/wordprocessingShape\" mc:Ignorable=\"w14 wp14\">", false));
        file.append(makeLine(1, "<w:body>", false));
        docxRelBuf = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering\" Target=\"numbering.xml\"/>");
    }

    public void addDiagram(Object o) throws IOException, TranscoderException {
        if (o instanceof Element) {
            int i = IMAGE_INDEX++;

            Element e = (Element) o;
            int[] dims = rasterizeDiagram(e, destinationFolder + "/media", "image" + i);

            docxRelBuf.append("<Relationship Id=\"image");
            docxRelBuf.append(i);
            docxRelBuf.append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" Target=\"media/");
            docxRelBuf.append("image");
            docxRelBuf.append(i);
            docxRelBuf.append(".png\"/>");

            int mx = 5722620 / dims[0];
            int my = dims[1] * mx;
            StringBuffer b = new StringBuffer("<w:p><w:r><w:drawing><wp:inline distT=\"0\" distB=\"0\" distL=\"0\" distR=\"0\"><wp:extent cx=\"5722620\" cy=\"");
            b.append(my);
            b.append("\"/><wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>");
            b.append("<wp:docPr id=\"");
            b.append(i);
            b.append("\" name=\"Picture ");
            b.append(i);
            b.append("\"/>");
            b.append("<wp:cNvGraphicFramePr><a:graphicFrameLocks xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" noChangeAspect=\"1\"/></wp:cNvGraphicFramePr><a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"><a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\"><pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\"><pic:nvPicPr>");
            b.append("<pic:cNvPr id=\"");
            b.append(i);
            b.append("\" name=\"Picture ");
            b.append(i);
            b.append("\"/>");
            b.append("<pic:cNvPicPr><a:picLocks noChangeAspect=\"1\" noChangeArrowheads=\"1\"/></pic:cNvPicPr></pic:nvPicPr><pic:blipFill>");
            b.append("<a:blip r:embed=\"image");
            b.append(i);
            b.append("\">");
            b.append("</a:blip><a:srcRect/><a:stretch><a:fillRect/></a:stretch></pic:blipFill><pic:spPr bwMode=\"auto\"><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"5722620\" cy=\"");
            b.append(my);
            b.append("\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom><a:noFill/><a:ln><a:noFill/></a:ln></pic:spPr></pic:pic></a:graphicData></a:graphic></wp:inline></w:drawing></w:r></w:p>");

            appendBuffer(b);
        }
    }

    @Override
    public void flush() throws IOException {
        file.append(getBuffer());
        file.append(makeLine(1, "</w:body>", false));
        file.append(makeLine(0, "</w:document>", false));
        file.flush();

        docxRelBuf.append("</Relationships>");
        StubUtils.createResource(destinationFolder + "/_rels", "document.xml", "rels", docxRelBuf.toString());
    }

    /**
     * Rasterizes an SVG DOM tree.
     *
     * @param svg SVG DOM node.
     * @param folder Folder to create PNG file in.
     * @param name filename without PNG extension.
     * @return width and height of rasterized image or null if failed.
     * @exception IOException if error.
     */
    protected int[] rasterizeDiagram(Element svg, String folder, String name) throws IOException, TranscoderException {
        int[] rv = new int[2];
        // Create a Transcoder
        PNGTranscoder t = new PNGTranscoder();
        // Create a new document.
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        Document document = impl.createDocument(svgNS, "svg", null);
        Element root = document.getDocumentElement();
        rv[0] = Integer.valueOf(svg.getAttribute("width"));
        rv[1] = Integer.valueOf(svg.getAttribute("height"));
        root.setAttributeNS(null, "width", svg.getAttribute("width"));
        root.setAttributeNS(null, "height", svg.getAttribute("height"));
        // Create a duplicate node and transfer ownership of the
        // new node into the destination document
        Node newNode = document.importNode(svg, true);
        // Make the new node an actual item in the target document
        root.appendChild(newNode);
        // Set the transcoder input and output.
        TranscoderInput input = new TranscoderInput(document);
        OutputStream ostream = new FileOutputStream(StubUtils.createLowLevelFile(folder, name, "png"));
        TranscoderOutput output = new TranscoderOutput(ostream);

        // Perform the transcoding.
        t.transcode(input, output);

        ostream.flush();
        ostream.close();

        return rv;
    }

}
