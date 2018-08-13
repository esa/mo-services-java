package esa.mo.mal.encoder.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

/**
 * 
 * @author rvangijlswijk
 *
 */
public class HTTPXMLStreamFactory extends MALElementStreamFactory {

  @Override
  protected void init(String protocol, Map properties)
      throws IllegalArgumentException, MALException {
    // TODO Auto-generated method stub

  }

  @Override
  public MALElementInputStream createInputStream(InputStream is)
      throws IllegalArgumentException, MALException {
    return new HTTPXMLElementInputStream(is);
  }

  @Override
  public MALElementInputStream createInputStream(byte[] bytes, int offset)
      throws IllegalArgumentException, MALException {
    InputStream is = new ByteArrayInputStream(bytes, offset, bytes.length);
    return new HTTPXMLElementInputStream(is);
  }

  @Override
  public MALElementOutputStream createOutputStream(OutputStream os)
      throws IllegalArgumentException, MALException {
    return new HTTPXMLElementOutputStream(os);
  }

}
