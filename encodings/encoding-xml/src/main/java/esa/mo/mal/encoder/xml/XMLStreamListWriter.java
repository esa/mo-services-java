/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Encoder - XML
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
package esa.mo.mal.encoder.xml;

import static esa.mo.mal.encoder.xml.XMLStreamFactory.RLOGGER;
import java.util.List;
import java.util.logging.Level;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.ccsds.moims.mo.mal.structures.Element;

public class XMLStreamListWriter extends esa.mo.mal.encoder.xml.XMLStreamWriter {

    private List list;

    public XMLStreamListWriter(XMLStreamWriter wr, List list) {
        this(wr, list, list.getClass().getSimpleName());
    }

    public XMLStreamListWriter(XMLStreamWriter wr, List list, String typeName) {
        this.writer = wr;
        this.list = list;

        try {
            writer.writeDTD(LINE_END);
            writer.writeStartElement(typeName);
            if (!typeName.equals("AttributeList")) {
                writer.writeAttribute("malxml:type", ((Element) list).getShortForm().toString());
            }
            writer.writeDTD(LINE_END);
        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public int size() {
        return list.size();
    }

    public void close() {
        try {
            writer.writeDTD(LINE_END);
            writer.writeEndElement();
        } catch (XMLStreamException e) {
            RLOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
