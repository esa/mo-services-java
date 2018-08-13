package esa.mo.mal.encoder.http;

import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import org.ccsds.moims.mo.mal.MALListDecoder;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

public class HTTPXMLStreamListReader extends HTTPXMLStreamReader implements MALListDecoder {

  private List list;
  private String listName;
  private int size = 0;

  public HTTPXMLStreamListReader(InputStream is) {
    super(is);
  }

  public HTTPXMLStreamListReader(List list, XMLEventReader eventReader) {

    this.list = list;
    this.listName = this.list.getClass().getSimpleName();

    this.eventReader = eventReader;
  }

  public HTTPXMLStreamListReader(List list, XMLEventReader eventReader, String elementName) {

    this.list = list;
    this.listName = elementName;

    this.eventReader = eventReader;
  }

  @Override
  public boolean hasNext() {

    try {
      if (!eventReader.hasNext())
        return false;

      if (eventReader.peek().isCharacters()) {
        eventReader.nextEvent();
      }
      boolean result = eventReader.peek().isStartElement();
      if (result) {
        this.size++;
      }
      return result;

    } catch (XMLStreamException e) {
      RLOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    return false;
  }

  @Override
  public int size() {

    return this.size;
  }

}
