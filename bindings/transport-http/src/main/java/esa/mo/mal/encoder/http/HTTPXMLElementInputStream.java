package esa.mo.mal.encoder.http;

import java.io.InputStream;

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALListDecoder;
import org.ccsds.moims.mo.mal.OperationField;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * 
 * @author rvangijlswijk
 *
 */
public class HTTPXMLElementInputStream implements MALElementInputStream {

  private MALListDecoder dec;

  public HTTPXMLElementInputStream(InputStream is) {

    this.dec = new HTTPXMLStreamReader(is);

    MALContextFactory.getElementsRegistry().registerElementsForArea(MALHelper.MAL_AREA);
  }

  @Override
  public MALMessageHeader readHeader(MALMessageHeader header) throws MALException {
    return header.decode(dec);
  }

  @Override
  public Element readElement(Element element, OperationField field)
      throws IllegalArgumentException, MALException {

    return this.dec.decodeElement((Element) element);
  }

  @Override
  public void close() throws MALException {
  }

  public MALListDecoder getDecoder() {
    return this.dec;
  }

}
