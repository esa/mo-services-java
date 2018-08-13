package esa.mo.mal.encoder.http;

import java.util.List;
import java.util.logging.Level;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.ccsds.moims.mo.mal.structures.Element;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

public class HTTPXMLStreamListWriter extends HTTPXMLStreamWriter {

  private List list;

  public HTTPXMLStreamListWriter(XMLStreamWriter wr, List list) {

    this(wr, list, list.getClass().getSimpleName());
  }

  public HTTPXMLStreamListWriter(XMLStreamWriter wr, List list, String typeName) {

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

  @Override
  public void close() {

    try {

      writer.writeDTD(LINE_END);
      writer.writeEndElement();

    } catch (XMLStreamException e) {
      RLOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

}
