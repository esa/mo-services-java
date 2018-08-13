package esa.mo.mal.encoder.http;

import java.io.OutputStream;
import java.util.logging.Level;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

/**
 * 
 * @author rvangijlswijk
 *
 */
public class HTTPXMLElementOutputStream implements MALElementOutputStream {

  protected final OutputStream dos;
  protected MALListEncoder enc;

  public HTTPXMLElementOutputStream(OutputStream os) {
    this.dos = os;
    if (enc == null) {
      enc = new HTTPXMLStreamWriter(this.dos);
    }
  }

  @Override
  public void writeHeader(MALMessageHeader header) throws IllegalArgumentException, MALException {
    // Do nothing
  }

  @Override
  public void writeElement(final Element element, final OperationField field) throws MALException {

    if (enc == null) {
      enc = new HTTPXMLStreamWriter(this.dos);
    }

    if (element != null) {
      encodeBody(element);
    } else if (element == null) {
      ((HTTPXMLStreamWriter) this.enc).encodeNullableElement(null);
    }
  }

  private void encodeBody(final Object element) throws MALException {
    RLOGGER.finest("HTTPXMLElementOutputStream.encodeBody");

    try {
      String elementName = element.getClass().getSimpleName();
      if (element instanceof Element) {
        if (!elementName.isEmpty()) {
          ((HTTPXMLStreamWriter) this.enc).encodeElement((Element) element, elementName);
        } else {
          ((Element) element).encode(this.enc);
        }
      } else {
        ((HTTPXMLStreamWriter) this.enc).encode(element, element.getClass().getSimpleName());
      }

    } catch (Exception ex) {
      RLOGGER.log(Level.WARNING, "exception in HTTPXMLElementOutputStream.encodeBody: " + ex.getMessage(), ex);
      throw new MALException(ex.getMessage());
    }
  }

  @Override
  public void flush() throws MALException {

  }

  @Override
  public void close() throws MALException {

    try {
      this.enc.close();
    } catch (Exception ex) {
      RLOGGER.warning("exception in HTTPXMLElementOutputStream.close: " + ex.getMessage());
      throw new MALException(ex.getMessage());
    }

  }
}
