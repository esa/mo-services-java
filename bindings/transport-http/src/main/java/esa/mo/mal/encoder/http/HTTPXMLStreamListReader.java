package esa.mo.mal.encoder.http;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import org.ccsds.moims.mo.mal.MALListDecoder;

public class HTTPXMLStreamListReader extends HTTPXMLStreamReader implements MALListDecoder {

  private List list;
  private String listName;
  private int size = 0;

  public HTTPXMLStreamListReader(InputStream is) {
    super(is);
  }

  public HTTPXMLStreamListReader(List list, XMLEventReader eventReader) {
    this(list, eventReader, list.getClass().getSimpleName());
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

      XMLEvent event = eventReader.peek();

      if (event.isCharacters()) {
        eventReader.nextEvent();
      }
      boolean isStart = event.isStartElement();

      if (isStart) {
        this.size++;
      }
      return isStart;

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
