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
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.docx.DocxNumberingWriter;
import esa.mo.tools.stubgen.docx.DocxBaseWriter;
import esa.mo.tools.stubgen.docx.GeneratorUtils;
import esa.mo.tools.stubgen.docx.DocxWriter;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.specification.TypeRef;
import esa.mo.xsd.*;
import esa.mo.xsd.EnumerationType.Item;
import esa.mo.xsd.util.XmlSpecification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.maven.plugin.logging.Log;

/**
 * Generates a MS Word compliant docx file of the service specification.
 */
public class GeneratorDocx extends GeneratorDocument {

    private static final String STD_COLOUR = null;
    private static final String HEADER_COLOUR = "00CCFF";
    private static final String FIXED_COLOUR = "E0E0E0";
    private static final int[] SERVICE_OVERVIEW_TABLE_WIDTHS = new int[]{
        2250, 2801, 1382, 1185, 1382
    };
    private static final int[] SERVICE_COM_TYPES_TABLE_WIDTHS = new int[]{
        2250, 1685, 2801, 1185, 1185
    };
    private static final int[] OPERATION_OVERVIEW_TABLE_WIDTHS = new int[]{
        2000, 1700, 800, 4500
    };
    private static final int[] OPERATION_ERROR_TABLE_WIDTHS = new int[]{
        1500, 1500, 2500, 3500
    };
    private static final int[] ERROR_TABLE_WIDTHS = new int[]{
        2302, 1430, 5268
    };
    private static final int[] ENUM_TABLE_WIDTHS = new int[]{
        2302, 2430, 4268
    };
    private static final int[] LIST_TABLE_WIDTHS = new int[]{
        2302, 6698
    };
    private static final int[] COMPOSITE_TABLE_WIDTHS = new int[]{
        2302, 1830, 1100, 3768
    };

    private boolean includeMessageFieldNames = true;
    private boolean includeDiagrams = true;
    private boolean oldStyle = false;
    private final Log logger;

    /**
     * Constructor.
     *
     * @param logger The logger to use.
     */
    public GeneratorDocx(org.apache.maven.plugin.logging.Log logger) {
        super(new GeneratorConfiguration("", "", "", "",
                "", "", "", "", "", "", "", ""));
        this.logger = logger;
    }

    @Override
    public String getShortName() {
        return "DOCX";
    }

    @Override
    public String getDescription() {
        return "Generates a document of the service specification.";
    }

    @Override
    public void init(String destinationFolderName, boolean generateStructures,
            boolean generateCOM, Map<String, String> packageBindings,
            Map<String, String> extraProperties) throws IOException {
        super.init(destinationFolderName, generateStructures, generateCOM, packageBindings, extraProperties);

        if (extraProperties.containsKey("docx.includeMessageFieldNames")) {
            includeMessageFieldNames = Boolean.parseBoolean(extraProperties.get("docx.includeMessageFieldNames"));
        }

        if (extraProperties.containsKey("docx.includeDiagrams")) {
            includeDiagrams = Boolean.parseBoolean(extraProperties.get("docx.includeDiagrams"));
        }

        if (extraProperties.containsKey("docx.oldStyle")) {
            oldStyle = Boolean.parseBoolean(extraProperties.get("docx.oldStyle"));
        }
    }

    @Override
    public void compile(String destFolderName, XmlSpecification xml,
            JAXBElement rootNode) throws IOException, JAXBException {
        SpecificationType spec = xml.getSpecType();

        for (AreaType area : spec.getArea()) {
            String destinationFolderName = destFolderName + "/" + area.getName();
            String folder = destinationFolderName + "/word";
            String ext = "xml";
            logger.info("Creating file " + folder + " numbering." + ext);
            DocxNumberingWriter docxNumberingFile = new DocxNumberingWriter(folder, "numbering", ext);

            logger.info("Creating file " + folder + " document." + ext);
            DocxWriter docxServiceFile = new DocxWriter(folder, "document", ext, docxNumberingFile);
            DocxBaseWriter docxDataFile = new DocxBaseWriter();

            // create DOCX target area
            StubUtils.createResource(destinationFolderName, "[Content_Types]", ext, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\"><Default Extension=\"png\" ContentType=\"image/png\"/><Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/><Default Extension=\"xml\" ContentType=\"application/xml\"/><Override PartName=\"/word/document.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml\"/><Override PartName=\"/word/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml\"/><Override PartName=\"/word/numbering.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml\"/></Types>");
            StubUtils.createResource(destinationFolderName + "/_rels", "", "rels", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\"><Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"word/document.xml\"/></Relationships>");
            StubUtils.createResource(folder, "styles", ext, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><w:styles xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"><w:docDefaults><w:rPrDefault><w:rPr><w:rFonts w:ascii=\"Calibri\" w:eastAsia=\"Times New Roman\" w:hAnsi=\"Calibri\" w:cs=\"Times New Roman\"/><w:sz w:val=\"22\"/><w:szCs w:val=\"22\"/><w:lang w:val=\"en-GB\" w:eastAsia=\"en-GB\" w:bidi=\"ar-SA\"/></w:rPr></w:rPrDefault><w:pPrDefault/></w:docDefaults><w:latentStyles w:defLockedState=\"0\" w:defUIPriority=\"99\" w:defSemiHidden=\"1\" w:defUnhideWhenUsed=\"1\" w:defQFormat=\"0\" w:count=\"267\"><w:lsdException w:name=\"Normal\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 1\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 2\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 3\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 4\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 5\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 6\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 7\" w:locked=\"1\" w:uiPriority=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 8\" w:locked=\"1\" w:uiPriority=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"heading 9\" w:locked=\"1\" w:uiPriority=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"toc 1\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 2\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 3\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 4\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 5\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 6\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 7\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 8\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"toc 9\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"caption\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Title\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Default Paragraph Font\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Subtitle\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Strong\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Emphasis\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Table Grid\" w:locked=\"1\" w:semiHidden=\"0\" w:uiPriority=\"0\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Placeholder Text\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"No Spacing\" w:semiHidden=\"0\" w:uiPriority=\"1\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Light Shading\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Revision\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"List Paragraph\" w:semiHidden=\"0\" w:uiPriority=\"34\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Quote\" w:semiHidden=\"0\" w:uiPriority=\"29\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Intense Quote\" w:semiHidden=\"0\" w:uiPriority=\"30\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Medium List 2 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 1\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 2\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 3\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 4\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 5\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Shading Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"60\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light List Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"61\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Light Grid Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"62\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 1 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"63\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Shading 2 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"64\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 1 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"65\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium List 2 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"66\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 1 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"67\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 2 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"68\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Medium Grid 3 Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"69\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Dark List Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"70\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Shading Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"71\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful List Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"72\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Colorful Grid Accent 6\" w:semiHidden=\"0\" w:uiPriority=\"73\" w:unhideWhenUsed=\"0\"/><w:lsdException w:name=\"Subtle Emphasis\" w:semiHidden=\"0\" w:uiPriority=\"19\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Intense Emphasis\" w:semiHidden=\"0\" w:uiPriority=\"21\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Subtle Reference\" w:semiHidden=\"0\" w:uiPriority=\"31\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Intense Reference\" w:semiHidden=\"0\" w:uiPriority=\"32\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Book Title\" w:semiHidden=\"0\" w:uiPriority=\"33\" w:unhideWhenUsed=\"0\" w:qFormat=\"1\"/><w:lsdException w:name=\"Bibliography\" w:uiPriority=\"37\"/><w:lsdException w:name=\"TOC Heading\" w:uiPriority=\"39\" w:qFormat=\"1\"/></w:latentStyles><w:style w:type=\"paragraph\" w:default=\"1\" w:styleId=\"Normal\"><w:name w:val=\"Normal\"/><w:qFormat/><w:rsid w:val=\"008E3E32\"/></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading1\"><w:name w:val=\"heading 1\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading1Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:pPr><w:keepNext/><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"0\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\" w:cs=\"Arial\"/><w:b/><w:bCs/><w:kern w:val=\"32\"/><w:sz w:val=\"32\"/><w:szCs w:val=\"32\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading2\"><w:name w:val=\"heading 2\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading2Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:pPr><w:keepNext/><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"1\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\" w:cs=\"Arial\"/><w:b/><w:bCs/><w:i/><w:iCs/><w:sz w:val=\"28\"/><w:szCs w:val=\"28\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading3\"><w:name w:val=\"heading 3\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading3Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:pPr><w:keepNext/><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"2\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\" w:cs=\"Arial\"/><w:b/><w:bCs/><w:sz w:val=\"26\"/><w:szCs w:val=\"26\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading4\"><w:name w:val=\"heading 4\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading4Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:pPr><w:keepNext/><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"3\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/><w:b/><w:bCs/><w:sz w:val=\"28\"/><w:szCs w:val=\"28\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading5\"><w:name w:val=\"heading 5\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading5Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:pPr><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"4\"/></w:pPr><w:rPr><w:b/><w:bCs/><w:i/><w:iCs/><w:sz w:val=\"26\"/><w:szCs w:val=\"26\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Heading6\"><w:name w:val=\"heading 6\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:link w:val=\"Heading6Char\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:pPr><w:spacing w:before=\"240\" w:after=\"60\"/><w:outlineLvl w:val=\"5\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/><w:b/><w:bCs/></w:rPr></w:style><w:style w:type=\"character\" w:default=\"1\" w:styleId=\"DefaultParagraphFont\"><w:name w:val=\"Default Paragraph Font\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/></w:style><w:style w:type=\"table\" w:default=\"1\" w:styleId=\"TableNormal\"><w:name w:val=\"Normal Table\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:unhideWhenUsed/><w:qFormat/><w:tblPr><w:tblInd w:w=\"0\" w:type=\"dxa\"/><w:tblCellMar><w:top w:w=\"0\" w:type=\"dxa\"/><w:left w:w=\"108\" w:type=\"dxa\"/><w:bottom w:w=\"0\" w:type=\"dxa\"/><w:right w:w=\"108\" w:type=\"dxa\"/></w:tblCellMar></w:tblPr></w:style><w:style w:type=\"numbering\" w:default=\"1\" w:styleId=\"NoList\"><w:name w:val=\"No List\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:unhideWhenUsed/></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading1Char\"><w:name w:val=\"Heading 1 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading1\"/><w:uiPriority w:val=\"99\"/><w:locked/><w:rPr><w:rFonts w:ascii=\"Cambria\" w:hAnsi=\"Cambria\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/><w:kern w:val=\"32\"/><w:sz w:val=\"32\"/><w:szCs w:val=\"32\"/></w:rPr></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading2Char\"><w:name w:val=\"Heading 2 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading2\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:locked/><w:rPr><w:rFonts w:ascii=\"Cambria\" w:hAnsi=\"Cambria\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/><w:i/><w:iCs/><w:sz w:val=\"28\"/><w:szCs w:val=\"28\"/></w:rPr></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading3Char\"><w:name w:val=\"Heading 3 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading3\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:locked/><w:rPr><w:rFonts w:ascii=\"Cambria\" w:hAnsi=\"Cambria\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/><w:sz w:val=\"26\"/><w:szCs w:val=\"26\"/></w:rPr></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading4Char\"><w:name w:val=\"Heading 4 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading4\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:rPr><w:rFonts w:ascii=\"Calibri\" w:hAnsi=\"Calibri\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/><w:sz w:val=\"28\"/><w:szCs w:val=\"28\"/></w:rPr></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading5Char\"><w:name w:val=\"Heading 5 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading5\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:rPr><w:rFonts w:ascii=\"Calibri\" w:hAnsi=\"Calibri\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/><w:i/><w:iCs/><w:sz w:val=\"26\"/><w:szCs w:val=\"26\"/></w:rPr></w:style><w:style w:type=\"character\" w:customStyle=\"1\" w:styleId=\"Heading6Char\"><w:name w:val=\"Heading 6 Char\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:link w:val=\"Heading6\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:locked/><w:rsid w:val=\"008E3E32\"/><w:rPr><w:rFonts w:ascii=\"Calibri\" w:hAnsi=\"Calibri\" w:cs=\"Times New Roman\"/><w:b/><w:bCs/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"Caption\"><w:name w:val=\"caption\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:uiPriority w:val=\"99\"/><w:qFormat/><w:locked/><w:rPr><w:b/><w:bCs/><w:sz w:val=\"20\"/><w:szCs w:val=\"20\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:customStyle=\"1\" w:styleId=\"TableTitle\"><w:name w:val=\"_Table_Title\"/><w:basedOn w:val=\"Normal\"/><w:next w:val=\"Normal\"/><w:uiPriority w:val=\"99\"/><w:rsid w:val=\"006673F3\"/><w:pPr><w:keepNext/><w:keepLines/><w:suppressAutoHyphens/><w:spacing w:before=\"480\" w:after=\"240\"/><w:jc w:val=\"center\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Times New Roman\" w:hAnsi=\"Times New Roman\"/><w:b/><w:sz w:val=\"24\"/><w:szCs w:val=\"24\"/><w:lang w:val=\"en-US\" w:eastAsia=\"en-US\"/></w:rPr></w:style><w:style w:type=\"paragraph\" w:styleId=\"ListParagraph\"><w:name w:val=\"List Paragraph\"/><w:basedOn w:val=\"Normal\"/><w:uiPriority w:val=\"34\"/><w:qFormat/><w:rsid w:val=\"0052240D\"/><w:pPr><w:ind w:left=\"720\"/><w:contextualSpacing/></w:pPr></w:style><w:style w:type=\"character\" w:styleId=\"Hyperlink\"><w:name w:val=\"Hyperlink\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:uiPriority w:val=\"99\"/><w:unhideWhenUsed/><w:rsid w:val=\"000712F8\"/><w:rPr><w:color w:val=\"0000FF\" w:themeColor=\"hyperlink\"/><w:u w:val=\"single\"/></w:rPr></w:style><w:style w:type=\"character\" w:styleId=\"FollowedHyperlink\"><w:name w:val=\"FollowedHyperlink\"/><w:basedOn w:val=\"DefaultParagraphFont\"/><w:uiPriority w:val=\"99\"/><w:semiHidden/><w:unhideWhenUsed/><w:rsid w:val=\"000712F8\"/><w:rPr><w:color w:val=\"800080\" w:themeColor=\"followedHyperlink\"/><w:u w:val=\"single\"/></w:rPr></w:style></w:styles>");

            if (!area.getName().equalsIgnoreCase(StdStrings.COM) || generateCOM()) {
                logger.info("Processing area: " + area.getName());
                docxServiceFile.addTitle(1, "Specification: " + area.getName());

                docxServiceFile.addTitle(2, "General");
                docxServiceFile.addComment(area.getComment());
                for (DocumentationType documentation : area.getDocumentation()) {
                    docxServiceFile.addTitle(2, documentation.getName());
                    docxServiceFile.addNumberedComment(GeneratorUtils.addSplitStrings(null, documentation.getContent()));
                }

                // create services
                for (ServiceType service : area.getService()) {
                    docxServiceFile.addTitle(2, "Service: ", service.getName(), "SERVICE", true);
                    docxServiceFile.addTitle(3, "Overview");
                    docxServiceFile.addComment(service.getComment());
                    drawServiceTable(docxServiceFile, area, service);

                    for (DocumentationType documentation : service.getDocumentation()) {
                        docxServiceFile.addTitle(3, documentation.getName());
                        docxServiceFile.addNumberedComment(GeneratorUtils.addSplitStrings(null, documentation.getContent()));
                    }

                    if (!StdStrings.COM.equalsIgnoreCase(service.getName())) {
                        if (service instanceof ExtendedServiceType) {
                            drawCOMUsageTables(docxServiceFile, area, ((ExtendedServiceType) service));
                        }
                    } else {
                        List<String> comments = new ArrayList<>();

                        for (CapabilitySetType cSet : service.getCapabilitySet()) {
                            String str = cSet.getComment();

                            if (null != str) {
                                comments.addAll(GeneratorUtils.addSplitStrings(null, str));
                            }
                        }

                        docxServiceFile.addNumberedComment(comments);
                    }

                    for (CapabilitySetType cSet : service.getCapabilitySet()) {
                        for (OperationType op : cSet.getSendIPOrSubmitIPOrRequestIP()) {
                            docxServiceFile.addTitle(3, "OPERATION: ", op.getName(), "OPERATION_" + service.getName(), true);
                            docxServiceFile.addTitle(4, "Overview");
                            docxServiceFile.addComment(op.getComment());
                            drawOperationTable(docxServiceFile, area, service, op);
                            addOperationStructureDetails(docxServiceFile, op);
                            addOperationErrorDetails(docxServiceFile, area, service, op);
                        }
                    }
                }

                // process data types
                docxDataFile.addTitle(1, "Data types");
                boolean hasDataTypes = false;
                AreaDataTypeList areaTypes = area.getDataTypes();

                // if area level types exist
                if (areaTypes != null && !areaTypes.getFundamentalOrAttributeOrComposite().isEmpty()) {
                    hasDataTypes = true;
                    docxDataFile.addTitle(2, "Area data types: " + area.getName());
                    // create area level data types
                    for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite()) {
                        if (oType instanceof FundamentalType) {
                            createFundamentalClass(docxDataFile, (FundamentalType) oType);
                        } else if (oType instanceof AttributeType) {
                            createAttributeClass(docxDataFile, (AttributeType) oType);
                        } else if (oType instanceof CompositeType) {
                            createCompositeClass(docxDataFile, area, null, (CompositeType) oType);
                        } else if (oType instanceof EnumerationType) {
                            createEnumerationClass(docxDataFile, (EnumerationType) oType);
                        } else {
                            throw new IllegalArgumentException("Unexpected area ("
                                    + area.getName() + ") level datatype of "
                                    + oType.getClass().getName());
                        }
                    }
                }

                // Process service level types
                for (ServiceType service : area.getService()) {
                    DataTypeList dataTypes = service.getDataTypes();
                    // if service level types exist
                    if (dataTypes != null && !dataTypes.getCompositeOrEnumeration().isEmpty()) {
                        hasDataTypes = true;
                        docxDataFile.addTitle(2, "Service data types: " + service.getName());
                        for (Object oType : dataTypes.getCompositeOrEnumeration()) {
                            if (oType instanceof EnumerationType) {
                                createEnumerationClass(docxDataFile, (EnumerationType) oType);
                            } else if (oType instanceof CompositeType) {
                                createCompositeClass(docxDataFile, area, service, (CompositeType) oType);
                            } else {
                                throw new IllegalArgumentException("Unexpected service ("
                                        + area.getName() + ":" + service.getName()
                                        + ") level datatype of " + oType.getClass().getName());
                            }
                        }
                    }
                }

                if (!hasDataTypes) {
                    docxDataFile.addComment("No data types are defined in this specification.");
                }

                // process errors
                docxDataFile.addTitle(1, "Error codes");

                List<ErrorDefinitionType> errors = new LinkedList<>();

                // if area level types exist
                if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty()) {
                    errors.addAll(area.getErrors().getError());
                }

                // process service level types
                for (ServiceType service : area.getService()) {
                    // if service level types exist
                    if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty()) {
                        errors.addAll(service.getErrors().getError());

                        // check for operation level error definitions
                        for (CapabilitySetType cap : service.getCapabilitySet()) {
                            for (OperationType op : cap.getSendIPOrSubmitIPOrRequestIP()) {
                                if (op instanceof SubmitOperationType) {
                                    SubmitOperationType lop = (SubmitOperationType) op;
                                    addErrorDefinitions(errors, lop.getErrors());
                                } else if (op instanceof RequestOperationType) {
                                    RequestOperationType lop = (RequestOperationType) op;
                                    addErrorDefinitions(errors, lop.getErrors());
                                } else if (op instanceof InvokeOperationType) {
                                    InvokeOperationType lop = (InvokeOperationType) op;
                                    addErrorDefinitions(errors, lop.getErrors());
                                } else if (op instanceof ProgressOperationType) {
                                    ProgressOperationType lop = (ProgressOperationType) op;
                                    addErrorDefinitions(errors, lop.getErrors());
                                } else if (op instanceof PubSubOperationType) {
                                    PubSubOperationType lop = (PubSubOperationType) op;
                                    addErrorDefinitions(errors, lop.getErrors());
                                }
                            }
                        }
                    }
                }

                if (!errors.isEmpty()) {
                    docxDataFile.addComment("The following table lists the errors defined in this specification:");

                    docxDataFile.startTable(ERROR_TABLE_WIDTHS, area.getName() + " Error Codes");
                    docxDataFile.startRow();
                    docxDataFile.addCell(0, ERROR_TABLE_WIDTHS, "Error", HEADER_COLOUR);
                    docxDataFile.addCell(1, ERROR_TABLE_WIDTHS, "Error #", HEADER_COLOUR);
                    docxDataFile.addCell(2, ERROR_TABLE_WIDTHS, "Comment", HEADER_COLOUR);
                    docxDataFile.endRow();

                    for (ErrorDefinitionType err : errors) {
                        docxDataFile.startRow();
                        docxDataFile.addCell(0, ERROR_TABLE_WIDTHS, err.getName());
                        docxDataFile.addCell(1, ERROR_TABLE_WIDTHS, String.valueOf(err.getNumber()));
                        docxDataFile.addCell(2, ERROR_TABLE_WIDTHS, err.getComment(), false);
                        docxDataFile.endRow();
                    }

                    docxDataFile.endTable();
                } else {
                    docxDataFile.addComment("No errors are defined in this specification.");
                }
            }

            docxServiceFile.appendBuffer(docxDataFile.getBuffer());
            docxServiceFile.flush();
            docxNumberingFile.flush();

            ArrayList<String> filenames = new ArrayList<>();
            filenames.add("[Content_Types].xml");
            filenames.add("_rels/.rels");
            filenames.add("word/_rels/document.xml.rels");
            filenames.add("word/document.xml");
            filenames.add("word/styles.xml");
            filenames.add("word/numbering.xml");

            for (int j = 1; j < docxServiceFile.IMAGE_INDEX; j++) {
                filenames.add("word/media/image" + j + ".png");
            }

            StubUtils.createZipfile(destinationFolderName, filenames.toArray(new String[0]), "/ServiceSpec" + area.getName() + ".docx");
        }
    }

    private void drawServiceTable(DocxBaseWriter docxFile, AreaType area, ServiceType service) throws IOException {
        docxFile.startTable(SERVICE_OVERVIEW_TABLE_WIDTHS, service.getName() + " Service Operations");

        docxFile.startRow();
        docxFile.addCell(0, SERVICE_OVERVIEW_TABLE_WIDTHS, "Area Identifier", HEADER_COLOUR);
        docxFile.addCell(1, SERVICE_OVERVIEW_TABLE_WIDTHS, "Service Identifier", HEADER_COLOUR);
        docxFile.addCell(2, SERVICE_OVERVIEW_TABLE_WIDTHS, "Area Number", HEADER_COLOUR);
        docxFile.addCell(3, SERVICE_OVERVIEW_TABLE_WIDTHS, "Service Number", HEADER_COLOUR);
        docxFile.addCell(4, SERVICE_OVERVIEW_TABLE_WIDTHS, "Area Version", HEADER_COLOUR);
        docxFile.endRow();
        docxFile.startRow();
        docxFile.addCell(0, SERVICE_OVERVIEW_TABLE_WIDTHS, area.getName());
        docxFile.addCell(1, SERVICE_OVERVIEW_TABLE_WIDTHS, service.getName());
        docxFile.addCell(2, SERVICE_OVERVIEW_TABLE_WIDTHS, String.valueOf(area.getNumber()));
        docxFile.addCell(3, SERVICE_OVERVIEW_TABLE_WIDTHS, String.valueOf(service.getNumber()));
        docxFile.addCell(4, SERVICE_OVERVIEW_TABLE_WIDTHS, String.valueOf(area.getVersion()));
        docxFile.endRow();

        docxFile.startRow();
        docxFile.addCell(0, SERVICE_OVERVIEW_TABLE_WIDTHS, "Interaction Pattern", HEADER_COLOUR);
        docxFile.addCell(1, SERVICE_OVERVIEW_TABLE_WIDTHS, "Operation Identifier", HEADER_COLOUR, 2);
        docxFile.addCell(3, SERVICE_OVERVIEW_TABLE_WIDTHS, "Operation Number", HEADER_COLOUR);
        docxFile.addCell(4, SERVICE_OVERVIEW_TABLE_WIDTHS, "Capability Set", HEADER_COLOUR);
        docxFile.endRow();

        for (CapabilitySetType cSet : service.getCapabilitySet()) {
            drawServiceCapabilitySet(docxFile, service, cSet, STD_COLOUR);
        }

        docxFile.endTable();
    }

    private void drawServiceCapabilitySet(DocxBaseWriter docxFile, ServiceType service, CapabilitySetType cSet, String colour) throws IOException {
        if (null != cSet) {
            boolean firstRow = true;
            for (OperationType op : cSet.getSendIPOrSubmitIPOrRequestIP()) {
                docxFile.startRow();
                docxFile.addCell(0, SERVICE_OVERVIEW_TABLE_WIDTHS, operationType(op), colour);
                docxFile.addCell(1, SERVICE_OVERVIEW_TABLE_WIDTHS, op.getName(), colour, "OPERATION_" + service.getName() + "_" + op.getName(), 2);
                docxFile.addCell(3, SERVICE_OVERVIEW_TABLE_WIDTHS, String.valueOf(op.getNumber()), colour);
                docxFile.addCell(4, SERVICE_OVERVIEW_TABLE_WIDTHS, String.valueOf(cSet.getNumber()), colour, true, firstRow);
                docxFile.endRow();

                firstRow = false;
            }
        }
    }

    private void drawCOMUsageTables(DocxWriter docxFile, AreaType area, ExtendedServiceType service) throws IOException {
        SupportedFeatures features = service.getFeatures();

        if (null != features) {
            boolean hasCOMobjects = false;
            boolean hasCOMevents = false;

            if (null != features.getObjects()) {
                docxFile.addTitle(3, "COM usage");
                docxFile.addNumberedComment(GeneratorUtils.addSplitStrings(null, features.getObjects().getComment()));

                if (!features.getObjects().getObject().isEmpty()) {
                    hasCOMobjects = true;

                    docxFile.startTable(SERVICE_COM_TYPES_TABLE_WIDTHS, service.getName() + " Service Object Types");

                    docxFile.startRow();
                    docxFile.addCell(0, SERVICE_COM_TYPES_TABLE_WIDTHS, "Object Name", HEADER_COLOUR);
                    docxFile.addCell(1, SERVICE_COM_TYPES_TABLE_WIDTHS, "Object Number", HEADER_COLOUR);
                    docxFile.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS, "Object Body Type", HEADER_COLOUR);
                    docxFile.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Related points to", HEADER_COLOUR);
                    docxFile.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Source points to", HEADER_COLOUR);
                    docxFile.endRow();

                    List<String> cmts = new LinkedList<>();

                    for (ModelObjectType obj : features.getObjects().getObject()) {
                        docxFile.startRow();
                        docxFile.addCell(0, SERVICE_COM_TYPES_TABLE_WIDTHS, obj.getName());
                        docxFile.addCell(1, SERVICE_COM_TYPES_TABLE_WIDTHS, String.valueOf(obj.getNumber()));

                        if (null != obj.getObjectType() && (null != obj.getObjectType().getAny())) {
                            docxFile.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS,
                                    includeMessageFieldNames, oldStyle, area, service,
                                    TypeUtils.getTypeListViaXSDAny(obj.getObjectType().getAny()), null);
                        } else {
                            docxFile.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS, "No body");
                        }

                        if (null != obj.getRelatedObject()) {
                            if (null != obj.getRelatedObject().getObjectType()) {
                                String text = GeneratorUtils.createFQTypeName(area, service, obj.getRelatedObject().getObjectType());
                                docxFile.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, text, null);
                            } else {
                                if (null != obj.getRelatedObject().getComment()) {
                                    docxFile.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, obj.getRelatedObject().getComment(), null);
                                } else {
                                    docxFile.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Not specified");
                                }
                            }
                        } else {
                            docxFile.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Set to NULL", STD_COLOUR);
                        }

                        if (null != obj.getSourceObject()) {
                            if (null != obj.getSourceObject().getObjectType()) {
                                String text = GeneratorUtils.createFQTypeName(area, service, obj.getSourceObject().getObjectType());
                                docxFile.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, text, null);
                            } else {
                                if (null != obj.getSourceObject().getComment()) {
                                    docxFile.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, obj.getSourceObject().getComment(), null);
                                } else {
                                    docxFile.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Not specified");
                                }
                            }
                        } else {
                            docxFile.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Set to NULL");
                        }

                        docxFile.endRow();
                    }

                    docxFile.endTable();

                    docxFile.addNumberedComment(cmts);
                }
            }

            if (null != features.getEvents()) {
                hasCOMevents = true;

                DocxBaseWriter evntTable = new DocxBaseWriter(docxFile.getNumberWriter());
                evntTable.addTitle(3, "COM Event Service usage");
                evntTable.addNumberedComment(GeneratorUtils.addSplitStrings(null, features.getEvents().getComment()));

                evntTable.startTable(SERVICE_COM_TYPES_TABLE_WIDTHS, service.getName() + " Service Events");

                evntTable.startRow();
                evntTable.addCell(0, SERVICE_COM_TYPES_TABLE_WIDTHS, "Event Name", HEADER_COLOUR);
                evntTable.addCell(1, SERVICE_COM_TYPES_TABLE_WIDTHS, "Object Number", HEADER_COLOUR);
                evntTable.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS, "Object Body Type", HEADER_COLOUR);
                evntTable.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Related points to", HEADER_COLOUR);
                evntTable.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Source points to", HEADER_COLOUR);
                evntTable.endRow();

                for (ModelObjectType evnt : features.getEvents().getEvent()) {
                    evntTable.startRow();
                    evntTable.addCell(0, SERVICE_COM_TYPES_TABLE_WIDTHS, evnt.getName(), STD_COLOUR);
                    evntTable.addCell(1, SERVICE_COM_TYPES_TABLE_WIDTHS, String.valueOf(evnt.getNumber()), STD_COLOUR);

                    if (null != evnt.getObjectType()) {
                        evntTable.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS,
                                includeMessageFieldNames, oldStyle, area, service,
                                TypeUtils.getTypeListViaXSDAny(evnt.getObjectType().getAny()), null);
                    } else {
                        evntTable.addCell(2, SERVICE_COM_TYPES_TABLE_WIDTHS, "No body", STD_COLOUR);
                    }

                    if (null != evnt.getRelatedObject()) {
                        if (null != evnt.getRelatedObject().getObjectType()) {
                            evntTable.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS,
                                    GeneratorUtils.createFQTypeName(area, service, evnt.getRelatedObject().getObjectType()), null);
                        } else {
                            if (null != evnt.getRelatedObject().getComment()) {
                                evntTable.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, evnt.getRelatedObject().getComment(), null);
                            } else {
                                evntTable.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Not specified");
                            }
                        }
                    } else {
                        evntTable.addCell(3, SERVICE_COM_TYPES_TABLE_WIDTHS, "Set to NULL", STD_COLOUR);
                    }

                    if (null != evnt.getSourceObject()) {
                        if (null != evnt.getSourceObject().getObjectType()) {
                            evntTable.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS,
                                    GeneratorUtils.createFQTypeName(area, service, evnt.getSourceObject().getObjectType()), null);
                        } else {
                            if (null != evnt.getSourceObject().getComment()) {
                                evntTable.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, evnt.getSourceObject().getComment(), null);
                            } else {
                                evntTable.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Not specified");
                            }
                        }
                    } else {
                        evntTable.addCell(4, SERVICE_COM_TYPES_TABLE_WIDTHS, "Set to NULL");
                    }

                    evntTable.endRow();
                }

                evntTable.endTable();

                docxFile.appendBuffer(evntTable.getBuffer());
            }

            if ((hasCOMobjects) || (hasCOMevents)) {
                StringBuilder str = new StringBuilder(StdStrings.COM);
                if (hasCOMobjects) {
                    str.append(" object");
                }
                if (hasCOMevents) {
                    if (hasCOMobjects) {
                        str.append(" and");

                    }
                    str.append(" event");
                }
                str.append(" relationships");

                docxFile.addTitle(3, "COM Object Relationships");
                docxFile.addComment("The Figure below shows the " + str + " for this service:");

                if (includeDiagrams) {
                    for (AnyTypeReferenceWithId any : service.getFeatures().getDiagram()) {
                        for (Object o : any.getAny()) {
                            try {
                                docxFile.addDiagram(o);
                            } catch (TranscoderException ex) {
                                logger.error("|Execption thrown rasterizing image", ex);
                            }
                        }
                    }
                } else {
                    docxFile.addComment("INSERT DIAGRAM HERE");
                }

                docxFile.addFigureCaption(service.getName() + " Service " + str);
            }

            if (features.getArchiveUsage() != null) {
                DocxBaseWriter archiveUsage = new DocxBaseWriter(docxFile.getNumberWriter());
                archiveUsage.addTitle(3, "COM Archive Service usage");
                archiveUsage.addNumberedComment(GeneratorUtils.addSplitStrings(null, features.getArchiveUsage().getComment()));

                docxFile.appendBuffer(archiveUsage.getBuffer());
            }

            if (features.getActivityUsage() != null) {
                DocxBaseWriter activityUsage = new DocxBaseWriter(docxFile.getNumberWriter());
                activityUsage.addTitle(3, "COM Activity Service usage");
                activityUsage.addNumberedComment(GeneratorUtils.addSplitStrings(null, features.getActivityUsage().getComment()));

                docxFile.appendBuffer(activityUsage.getBuffer());
            }
        }
    }

    private void drawOperationTable(DocxBaseWriter docxFile, AreaType area, ServiceType service, OperationType op) throws IOException {
        docxFile.startTable(OPERATION_OVERVIEW_TABLE_WIDTHS);

        docxFile.startRow();
        docxFile.addCell(0, OPERATION_OVERVIEW_TABLE_WIDTHS, "Operation Identifier", HEADER_COLOUR);
        docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, op.getName(), STD_COLOUR, 3);
        docxFile.endRow();

        if (op instanceof SendOperationType) {
            SendOperationType lop = (SendOperationType) op;
            drawOperationPattern(docxFile, "SEND");
            drawOperationMessageHeader(docxFile);
            drawOperationMessageDetails(docxFile, area, service, true, "SEND",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSend().getAny()));
        } else if (op instanceof SubmitOperationType) {
            SubmitOperationType lop = (SubmitOperationType) op;
            drawOperationPattern(docxFile, "SUBMIT");
            drawOperationMessageHeader(docxFile);
            drawOperationMessageDetails(docxFile, area, service, true, "SUBMIT",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSubmit().getAny()));
        } else if (op instanceof RequestOperationType) {
            RequestOperationType lop = (RequestOperationType) op;
            drawOperationPattern(docxFile, "REQUEST");
            drawOperationMessageHeader(docxFile);
            drawOperationMessageDetails(docxFile, area, service, true, "REQUEST",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getRequest().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "RESPONSE",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny()));
        } else if (op instanceof InvokeOperationType) {
            InvokeOperationType lop = (InvokeOperationType) op;
            drawOperationPattern(docxFile, "INVOKE");
            drawOperationMessageHeader(docxFile);
            drawOperationMessageDetails(docxFile, area, service, true, "INVOKE",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getInvoke().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "ACK",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "RESPONSE",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny()));
        } else if (op instanceof ProgressOperationType) {
            ProgressOperationType lop = (ProgressOperationType) op;
            drawOperationPattern(docxFile, "PROGRESS");
            drawOperationMessageHeader(docxFile);
            drawOperationMessageDetails(docxFile, area, service, true, "PROGRESS",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getProgress().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "ACK",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "UPDATE",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getUpdate().getAny()));
            drawOperationMessageDetails(docxFile, area, service, false, "RESPONSE",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny()));
        } else if (op instanceof PubSubOperationType) {
            PubSubOperationType lop = (PubSubOperationType) op;
            drawOperationPattern(docxFile, "PUBLISH-SUBSCRIBE");
            AnyTypeReference subKeys = lop.getMessages().getSubscriptionKeys();
            if (subKeys != null) {
                drawOperationPubSubKeys(docxFile, area, service,
                        TypeUtils.getTypeListViaXSDAny(subKeys.getAny()));
            }
            drawOperationMessageHeader(docxFile);
            // Probably looks cooler like this:
            if (subKeys != null) {
                //drawOperationMessageDetails(docxFile, area, service, true, "SUBSCRIPTION KEYS", TypeUtils.getTypeListViaXSDAny(subKeys.getAny()));
            }
            drawOperationMessageDetails(docxFile, area, service, false, "PUBLISH/NOTIFY",
                    TypeUtils.getTypeListViaXSDAny(lop.getMessages().getPublishNotify().getAny()));
        }

        docxFile.endTable();
    }

    private void drawOperationPattern(DocxBaseWriter docxFile, String patternType) throws IOException {
        docxFile.startRow();
        docxFile.addCell(0, OPERATION_OVERVIEW_TABLE_WIDTHS, "Interaction Pattern", HEADER_COLOUR);
        docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, patternType, FIXED_COLOUR, 3);
        docxFile.endRow();
    }

    private void drawOperationPubSubKeys(DocxBaseWriter docxFile, AreaType area, ServiceType service, List<TypeRef> types) throws IOException {
        docxFile.startRow();
        docxFile.addCell(0, OPERATION_OVERVIEW_TABLE_WIDTHS, "Subscription Keys", HEADER_COLOUR);

        if (types == null || types.isEmpty()) {
            docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, "Empty", FIXED_COLOUR, 3);
        } else {
            docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, includeMessageFieldNames, oldStyle, area, service, types, null, 3);
        }

        docxFile.endRow();
    }

    private void drawOperationMessageHeader(DocxBaseWriter docxFile) throws IOException {
        docxFile.startRow();
        docxFile.addCell(0, OPERATION_OVERVIEW_TABLE_WIDTHS, "Pattern Sequence", HEADER_COLOUR);
        docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, "Message", HEADER_COLOUR);
        docxFile.addCell(2, OPERATION_OVERVIEW_TABLE_WIDTHS, "Nullable", HEADER_COLOUR);
        docxFile.addCell(3, OPERATION_OVERVIEW_TABLE_WIDTHS, "Type Signature", HEADER_COLOUR);
        docxFile.endRow();
    }

    private void drawOperationMessageDetails(DocxBaseWriter docxFile, AreaType area,
            ServiceType service, boolean isIn, String message, List<TypeRef> types) throws IOException {
        docxFile.startRow();
        String inOut = (isIn) ? "IN" : "OUT";
        docxFile.addCell(0, OPERATION_OVERVIEW_TABLE_WIDTHS, inOut, FIXED_COLOUR);
        docxFile.addCell(1, OPERATION_OVERVIEW_TABLE_WIDTHS, message, FIXED_COLOUR);

        if (types == null || types.isEmpty()) {
            docxFile.addCell(2, OPERATION_OVERVIEW_TABLE_WIDTHS, "-", FIXED_COLOUR);
            docxFile.addCell(3, OPERATION_OVERVIEW_TABLE_WIDTHS, "-", FIXED_COLOUR);
        } else {
            docxFile.addCellNullability(2, OPERATION_OVERVIEW_TABLE_WIDTHS, types, null, 0);
            docxFile.addCell(3, OPERATION_OVERVIEW_TABLE_WIDTHS, includeMessageFieldNames, oldStyle, area, service, types, null);
        }
        docxFile.endRow();
    }

    private void addOperationStructureDetails(DocxBaseWriter docxFile, OperationType op) throws IOException {
        List<AnyTypeReference> msgs = new LinkedList<>();

        if (op instanceof SendOperationType) {
            SendOperationType lop = (SendOperationType) op;
            msgs.add(lop.getMessages().getSend());
        } else if (op instanceof SubmitOperationType) {
            SubmitOperationType lop = (SubmitOperationType) op;
            msgs.add(lop.getMessages().getSubmit());
        } else if (op instanceof RequestOperationType) {
            RequestOperationType lop = (RequestOperationType) op;
            msgs.add(lop.getMessages().getRequest());
            msgs.add(lop.getMessages().getResponse());
        } else if (op instanceof InvokeOperationType) {
            InvokeOperationType lop = (InvokeOperationType) op;
            msgs.add(lop.getMessages().getInvoke());
            msgs.add(lop.getMessages().getAcknowledgement());
            msgs.add(lop.getMessages().getResponse());
        } else if (op instanceof ProgressOperationType) {
            ProgressOperationType lop = (ProgressOperationType) op;
            msgs.add(lop.getMessages().getProgress());
            msgs.add(lop.getMessages().getAcknowledgement());
            msgs.add(lop.getMessages().getUpdate());
            msgs.add(lop.getMessages().getResponse());
        } else if (op instanceof PubSubOperationType) {
            PubSubOperationType lop = (PubSubOperationType) op;
            msgs.add(lop.getMessages().getPublishNotify());
        }

        docxFile.addTitle(4, "Type Signature Details");

        if (!msgs.isEmpty()) {
            addTypeSignatureDetails(docxFile, msgs);
        }

        docxFile.addTitle(4, "Requirements");

        if (!msgs.isEmpty()) {
            addRequirementsDetails(docxFile, msgs);
        }
    }

    private void addTypeSignatureDetails(DocxBaseWriter docxFile, List<AnyTypeReference> msgs) throws IOException {
        List<String> signatureDetails = null;
        for (AnyTypeReference msg : msgs) {
            List<TypeRef> refs = TypeUtils.getTypeListViaXSDAny(msg.getAny());
            for (TypeRef typeRef : refs) {
                if (typeRef.isField()) {
                    signatureDetails = GeneratorUtils.addSplitStrings(signatureDetails, typeRef.getFieldRef().getComment());
                }
            }
        }

        docxFile.addNumberedComment(signatureDetails);
    }

    private void addRequirementsDetails(DocxBaseWriter docxFile, List<AnyTypeReference> msgs) throws IOException {
        List<String> requirements = null;
        for (AnyTypeReference msg : msgs) {
            requirements = GeneratorUtils.addSplitStrings(requirements, msg.getComment());
        }

        docxFile.addNumberedComment(requirements);
    }

    private void addOperationErrorDetails(DocxBaseWriter docxFile, AreaType area, ServiceType service, OperationType op) throws IOException {
        docxFile.addTitle(4, "Errors");

        if (op instanceof SendOperationType) {
            docxFile.addComment("The operation cannot return any errors.");
        } else if (op instanceof SubmitOperationType) {
            SubmitOperationType lop = (SubmitOperationType) op;
            addErrorStructureDetails(docxFile, area, service, lop.getErrors());
        } else if (op instanceof RequestOperationType) {
            RequestOperationType lop = (RequestOperationType) op;
            addErrorStructureDetails(docxFile, area, service, lop.getErrors());
        } else if (op instanceof InvokeOperationType) {
            InvokeOperationType lop = (InvokeOperationType) op;
            addErrorStructureDetails(docxFile, area, service, lop.getErrors());
        } else if (op instanceof ProgressOperationType) {
            ProgressOperationType lop = (ProgressOperationType) op;
            addErrorStructureDetails(docxFile, area, service, lop.getErrors());
        } else if (op instanceof PubSubOperationType) {
            PubSubOperationType lop = (PubSubOperationType) op;
            addErrorStructureDetails(docxFile, area, service, lop.getErrors());
        }
    }

    private void addErrorStructureDetails(DocxBaseWriter docxFile, AreaType area, ServiceType service, OperationErrorList errors) throws IOException {
        if (errors == null || errors.getErrorOrErrorRef() == null || errors.getErrorOrErrorRef().isEmpty()) {
            docxFile.addComment("The operation does not return any errors.");
            return;
        }

        if (errors.getErrorOrErrorRef().size() == 1) {
            docxFile.addComment("The operation may return the following error:");
        } else {
            docxFile.addComment("The operation may return one of the following errors:");
        }

        TreeMap<String, List<Object[]>> m = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    Long value1 = Long.valueOf(o1);
                    Long value2 = Long.valueOf(o2);
                    return value1.compareTo(value2);
                } catch (java.lang.NumberFormatException e) {
                    return o1.compareTo(o2);
                }
            }
        });

        for (Object object : errors.getErrorOrErrorRef()) {
            if (object instanceof ErrorDefinitionType) {
                ErrorDefinitionType err = (ErrorDefinitionType) object;
                List<String> pcmts = GeneratorUtils.addSplitStrings(null, err.getComment());
                String ev = "Not Used";
                String errorTypeDescription = "-";

                if (err.getExtraInformation() != null) {
                    ev = GeneratorUtils.createFQTypeName(area, service, err.getExtraInformation().getType());

                    if (err.getExtraInformation().getComment() != null) {
                        errorTypeDescription = err.getExtraInformation().getComment();
                    }
                }

                List<Object[]> v;
                if (m.containsKey(String.valueOf(err.getNumber()))) {
                    v = m.get(String.valueOf(err.getNumber()));
                } else {
                    v = new ArrayList<>();
                    m.put(String.valueOf(err.getNumber()), v);
                }

                v.add(new Object[]{err.getName(), pcmts, err.getNumber(), ev, errorTypeDescription});
            } else if (object instanceof ErrorReferenceType) {
                ErrorReferenceType err = (ErrorReferenceType) object;
                List<String> pcmts = GeneratorUtils.addSplitStrings(null, err.getComment());
                String errorNumber = "UNKNOWN ERROR NUMBER!";
                String es;
                if ((err.getType().getArea() == null) || (err.getType().getArea().equals(area.getName()))) {
                    ErrorDefinitionType edt = getErrorDefinition(err.getType().getName());
                    if (edt != null) {
                        errorNumber = String.valueOf(edt.getNumber());
                    }
                    es = errorNumber;
                } else {
                    errorNumber = "Defined in " + err.getType().getArea();
                    es = "0";
                }

                String errorType = "Not Used";
                String errorTypeDescription = "-";

                if (err.getExtraInformation() != null) {
                    errorType = GeneratorUtils.createFQTypeName(area, service, err.getExtraInformation().getType());

                    if (err.getExtraInformation().getComment() != null) {
                        errorTypeDescription = err.getExtraInformation().getComment();
                    }
                }

                List<Object[]> value;
                if (m.containsKey(es)) {
                    value = m.get(es);
                } else {
                    value = new ArrayList<>();
                    m.put(es, value);
                }

                value.add(new Object[]{err.getType().getName(), pcmts, errorNumber, errorType, errorTypeDescription});
            }
        }

        for (String key : m.navigableKeySet()) {
            for (Object[] err : m.get(key)) {
                docxFile.addTitle(5, "ERROR: " + (String) err[0]);

                docxFile.addNumberedComment((List<String>) err[1]);

                docxFile.startTable(OPERATION_ERROR_TABLE_WIDTHS);
                docxFile.startRow();
                docxFile.addCell(0, OPERATION_ERROR_TABLE_WIDTHS, "Error", HEADER_COLOUR);
                docxFile.addCell(1, OPERATION_ERROR_TABLE_WIDTHS, "Error #", HEADER_COLOUR);
                docxFile.addCell(2, OPERATION_ERROR_TABLE_WIDTHS, "ExtraInfo Type", HEADER_COLOUR);
                docxFile.addCell(3, OPERATION_ERROR_TABLE_WIDTHS, "ExtraInfo description", HEADER_COLOUR);
                docxFile.endRow();

                docxFile.startRow();
                docxFile.addCell(0, OPERATION_ERROR_TABLE_WIDTHS, (String) err[0]);
                docxFile.addCell(1, OPERATION_ERROR_TABLE_WIDTHS, String.valueOf(err[2]));
                docxFile.addCell(2, OPERATION_ERROR_TABLE_WIDTHS, (String) err[3]);
                docxFile.addCell(3, OPERATION_ERROR_TABLE_WIDTHS, (String) err[4]);
                docxFile.endRow();
                docxFile.endTable();
            }
        }
    }

    private void addErrorDefinitions(List<ErrorDefinitionType> errors, OperationErrorList errs) throws IOException {
        if (errs != null && errs.getErrorOrErrorRef() != null && (!errs.getErrorOrErrorRef().isEmpty())) {
            for (Object object : errs.getErrorOrErrorRef()) {
                if (object instanceof ErrorDefinitionType) {
                    errors.add((ErrorDefinitionType) object);
                }
            }
        }
    }

    private void createFundamentalClass(DocxBaseWriter docxFile, FundamentalType fundamental) throws IOException {
        String fundName = fundamental.getName();

        logger.info("Creating fundamental class " + fundName);

        docxFile.addTitle(3, "Fundamental: ", fundName, "DATATYPE", true);

        if ((null != fundamental.getComment()) && (0 < fundamental.getComment().length())) {
            docxFile.addComment(fundamental.getComment());
        }
    }

    private void createAttributeClass(DocxBaseWriter docxFile, AttributeType attribute) throws IOException {
        String attrName = attribute.getName();

        logger.info("Creating attribute class " + attrName);

        docxFile.addTitle(3, "Attribute: ", attrName, "DATATYPE", true);

        if ((null != attribute.getComment()) && (0 < attribute.getComment().length())) {
            docxFile.addComment(attribute.getComment());
        }

        docxFile.startTable(LIST_TABLE_WIDTHS);

        docxFile.startRow();
        docxFile.addCell(0, LIST_TABLE_WIDTHS, "Name", HEADER_COLOUR);
        docxFile.addCell(1, LIST_TABLE_WIDTHS, attrName, STD_COLOUR);
        docxFile.endRow();

        docxFile.startRow();
        docxFile.addCell(0, LIST_TABLE_WIDTHS, "Extends", HEADER_COLOUR);
        docxFile.addCell(1, LIST_TABLE_WIDTHS, StdStrings.ATTRIBUTE, STD_COLOUR);
        docxFile.endRow();

        docxFile.startRow();
        docxFile.addCell(0, LIST_TABLE_WIDTHS, "Short Form Part", HEADER_COLOUR);
        docxFile.addCell(1, LIST_TABLE_WIDTHS, String.valueOf(attribute.getShortFormPart()), STD_COLOUR);
        docxFile.endRow();

        docxFile.endTable();
    }

    private void createEnumerationClass(DocxBaseWriter docxFile, EnumerationType enumeration) throws IOException {
        String enumName = enumeration.getName();

        logger.info("Creating enumeration class " + enumName);

        docxFile.addTitle(3, "ENUMERATION: ", enumName, "DATATYPE", true);

        if ((null != enumeration.getComment()) && (0 < enumeration.getComment().length())) {
            docxFile.addComment(enumeration.getComment());
        }

        docxFile.startTable(ENUM_TABLE_WIDTHS);

        docxFile.startRow();
        docxFile.addCell(0, ENUM_TABLE_WIDTHS, "Name", HEADER_COLOUR);
        docxFile.addCell(1, ENUM_TABLE_WIDTHS, enumeration.getName(), STD_COLOUR, 2);
        docxFile.endRow();

        docxFile.startRow();
        docxFile.addCell(0, ENUM_TABLE_WIDTHS, "Short Form Part", HEADER_COLOUR);
        docxFile.addCell(1, ENUM_TABLE_WIDTHS, String.valueOf(enumeration.getShortFormPart()), STD_COLOUR, 2);
        docxFile.endRow();

        // create attributes
        docxFile.startRow();
        docxFile.addCell(0, ENUM_TABLE_WIDTHS, "Enumeration Value", HEADER_COLOUR);
        docxFile.addCell(1, ENUM_TABLE_WIDTHS, "Numerical Value", HEADER_COLOUR);
        docxFile.addCell(2, ENUM_TABLE_WIDTHS, "Comment", HEADER_COLOUR);
        docxFile.endRow();

        for (Item item : enumeration.getItem()) {
            docxFile.startRow();
            docxFile.addCell(0, ENUM_TABLE_WIDTHS, item.getValue());
            docxFile.addCell(1, ENUM_TABLE_WIDTHS, String.valueOf(item.getNvalue()));
            docxFile.addCell(2, ENUM_TABLE_WIDTHS, item.getComment(), false);
            docxFile.endRow();
        }

        docxFile.endTable();
    }

    private void createCompositeClass(DocxBaseWriter docxFile, AreaType area, ServiceType service, CompositeType composite) throws IOException {
        String compName = composite.getName();

        logger.info("Creating composite class " + compName);

        docxFile.addTitle(3, "Composite: ", compName, "DATATYPE", true);

        if ((null != composite.getComment()) && (0 < composite.getComment().length())) {
            docxFile.addComment(composite.getComment());
        }

        docxFile.startTable(COMPOSITE_TABLE_WIDTHS);

        docxFile.startRow();
        docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, "Name", HEADER_COLOUR);
        docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, composite.getName(), STD_COLOUR, 3);
        docxFile.endRow();

        TypeReference extendsClass = null;
        if ((null != composite.getExtends()) && (null != composite.getExtends().getType())) {
            extendsClass = composite.getExtends().getType();
        } else {
            extendsClass = new TypeReference();
            extendsClass.setArea(StdStrings.MAL);
            extendsClass.setName(StdStrings.COMPOSITE);
        }
        docxFile.startRow();
        docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, "Extends", HEADER_COLOUR);
        docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, false, oldStyle, area, service, new TypeRef(extendsClass), STD_COLOUR, 3);
        docxFile.endRow();

        if (composite.getShortFormPart() == null) {
            docxFile.startRow();
            docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, "Abstract", HEADER_COLOUR, 4);
            docxFile.endRow();
        } else {
            docxFile.startRow();
            docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, "Short Form Part", HEADER_COLOUR);
            docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, String.valueOf(composite.getShortFormPart()), STD_COLOUR, 3);
            docxFile.endRow();
        }

        // create attributes
        List<CompositeField> superCompElements = createCompositeSuperElementsList(docxFile, extendsClass);
        List<CompositeField> compElements = createCompositeElementsList(docxFile, composite);

        if (!compElements.isEmpty()) {
            docxFile.startRow();
            docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, "Field", HEADER_COLOUR);
            docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, "Type", HEADER_COLOUR);
            docxFile.addCell(2, COMPOSITE_TABLE_WIDTHS, "Nullable", HEADER_COLOUR);
            docxFile.addCell(3, COMPOSITE_TABLE_WIDTHS, "Comment", HEADER_COLOUR);
            docxFile.endRow();

            if (!superCompElements.isEmpty()) {
                for (CompositeField element : superCompElements) {
                    docxFile.startRow();
                    docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, element.getFieldName(), FIXED_COLOUR);
                    docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, false, oldStyle, area, service, new TypeRef(element.getTypeReference()), FIXED_COLOUR, 0);
                    docxFile.addCell(2, COMPOSITE_TABLE_WIDTHS, element.isCanBeNull() ? "Yes" : "No", FIXED_COLOUR);
                    docxFile.addCell(3, COMPOSITE_TABLE_WIDTHS, element.getComment(), FIXED_COLOUR, false);
                    docxFile.endRow();
                }
            }

            for (CompositeField element : compElements) {
                docxFile.startRow();
                docxFile.addCell(0, COMPOSITE_TABLE_WIDTHS, element.getFieldName());
                docxFile.addCell(1, COMPOSITE_TABLE_WIDTHS, false, oldStyle, area, service, new TypeRef(element.getTypeReference()), null, 0);
                docxFile.addCell(2, COMPOSITE_TABLE_WIDTHS, element.isCanBeNull() ? "Yes" : "No");
                docxFile.addCell(3, COMPOSITE_TABLE_WIDTHS, element.getComment(), false);
                docxFile.endRow();
            }
        }

        docxFile.endTable();
    }

    private static String operationType(OperationType op) throws IOException {
        if (op instanceof SendOperationType) {
            return "SEND";
        } else if (op instanceof SubmitOperationType) {
            return "SUBMIT";
        } else if (op instanceof RequestOperationType) {
            return "REQUEST";
        } else if (op instanceof InvokeOperationType) {
            return "INVOKE";
        } else if (op instanceof ProgressOperationType) {
            return "PROGRESS";
        } else if (op instanceof PubSubOperationType) {
            return "PUBLISH-SUBSCRIBE";
        }

        return "Unknown";
    }
}
