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
import esa.mo.tools.stubgen.writers.AbstractWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Cesar.Coelho
 */
public class DocxNumberingWriter extends AbstractWriter {

    private int numberingInstance = 1;
    private final Writer file;
    private final StringBuffer buffer = new StringBuffer();

    public DocxNumberingWriter(String folder, String className, String ext) throws IOException {
        file = StubUtils.createLowLevelWriter(folder, className, ext);
        file.append(makeLine(0, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><w:numbering xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"><w:abstractNum w:abstractNumId=\"0\"><w:nsid w:val=\"0010107A\"/><w:multiLevelType w:val=\"hybridMultilevel\"/><w:tmpl w:val=\"2FAEA97C\"/><w:lvl w:ilvl=\"0\"><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"-\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"1080\"/></w:tabs><w:ind w:left=\"1080\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"1\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"o\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"1800\"/></w:tabs><w:ind w:left=\"1800\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"2\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"?\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"2520\"/></w:tabs><w:ind w:left=\"2520\" w:hanging=\"360\"/></w:pPr></w:lvl></w:abstractNum><w:abstractNum w:abstractNumId=\"1\"><w:nsid w:val=\"6805624A\"/><w:multiLevelType w:val=\"hybridMultilevel\"/><w:tmpl w:val=\"E1A8AF06\"/><w:lvl w:ilvl=\"0\" w:tplc=\"08090001\"><w:start w:val=\"1\"/><w:numFmt w:val=\"bullet\"/><w:lvlText w:val=\"\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"720\"/></w:tabs><w:ind w:left=\"720\" w:hanging=\"360\"/></w:pPr><w:rPr><w:rFonts w:ascii=\"Symbol\" w:hAnsi=\"Symbol\" w:hint=\"default\"/></w:rPr></w:lvl><w:lvl w:ilvl=\"1\" w:tplc=\"607A8D92\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%2.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"1440\"/></w:tabs><w:ind w:left=\"1440\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"2\" w:tplc=\"87184C80\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%3.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"2160\"/></w:tabs><w:ind w:left=\"2160\" w:hanging=\"180\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"3\" w:tplc=\"277E9776\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"decimal\"/><w:lvlText w:val=\"%4.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"2880\"/></w:tabs><w:ind w:left=\"2880\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"4\" w:tplc=\"3934DEFA\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%5.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"3600\"/></w:tabs><w:ind w:left=\"3600\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"5\" w:tplc=\"B838E67C\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%6.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"4320\"/></w:tabs><w:ind w:left=\"4320\" w:hanging=\"180\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"6\" w:tplc=\"AF3C1126\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"decimal\"/><w:lvlText w:val=\"%7.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"5040\"/></w:tabs><w:ind w:left=\"5040\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"7\" w:tplc=\"2DE61552\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%8.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"5760\"/></w:tabs><w:ind w:left=\"5760\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"8\" w:tplc=\"43546844\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%9.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"6480\"/></w:tabs><w:ind w:left=\"6480\" w:hanging=\"180\"/></w:pPr></w:lvl></w:abstractNum>"));
        buffer.append(makeLine(2, "<w:num w:numId=\"1\"><w:abstractNumId w:val=\"1\"/></w:num>"));
    }

    protected int getNextNumberingInstance() throws IOException {
        int instance = ++numberingInstance;
        file.append(makeLine(0, "<w:abstractNum w:abstractNumId=\"" + instance + "\"><w:nsid w:val=\"" + instance + "\"/><w:multiLevelType w:val=\"hybridMultilevel\"/><w:tmpl w:val=\"FFFFFFFF\"/><w:lvl w:ilvl=\"0\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%1)\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"720\"/></w:tabs><w:ind w:left=\"720\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"1\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%2.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"1440\"/></w:tabs><w:ind w:left=\"1440\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"2\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%3.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"2160\"/></w:tabs><w:ind w:left=\"2160\" w:hanging=\"180\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"3\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"decimal\"/><w:lvlText w:val=\"%4.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"2880\"/></w:tabs><w:ind w:left=\"2880\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"4\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%5.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"3600\"/></w:tabs><w:ind w:left=\"3600\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"5\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%6.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"4320\"/></w:tabs><w:ind w:left=\"4320\" w:hanging=\"180\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"6\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"decimal\"/><w:lvlText w:val=\"%7.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"5040\"/></w:tabs><w:ind w:left=\"5040\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"7\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerLetter\"/><w:lvlText w:val=\"%8.\"/><w:lvlJc w:val=\"left\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"5760\"/></w:tabs><w:ind w:left=\"5760\" w:hanging=\"360\"/></w:pPr></w:lvl><w:lvl w:ilvl=\"8\" w:tentative=\"1\"><w:start w:val=\"1\"/><w:numFmt w:val=\"lowerRoman\"/><w:lvlText w:val=\"%9.\"/><w:lvlJc w:val=\"right\"/><w:pPr><w:tabs><w:tab w:val=\"num\" w:pos=\"6480\"/></w:tabs><w:ind w:left=\"6480\" w:hanging=\"180\"/></w:pPr></w:lvl></w:abstractNum>"));
        buffer.append(makeLine(2, "<w:num w:numId=\"" + instance + "\"><w:abstractNumId w:val=\"" + instance + "\"/></w:num>"));
        return instance;
    }

    @Override
    public void flush() throws IOException {
        file.append(buffer.toString());
        file.append(makeLine(0, "</w:numbering>"));
        file.flush();
    }

}
