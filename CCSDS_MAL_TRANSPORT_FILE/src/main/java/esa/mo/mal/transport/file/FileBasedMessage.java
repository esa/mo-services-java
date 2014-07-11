package esa.mo.mal.transport.file;

import esa.mo.mal.transport.gen.GENMessage;
import esa.mo.mal.transport.gen.GENMessageHeader;
import java.io.IOException;
import java.io.InputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;

public class FileBasedMessage extends GENMessage
{
  private final InputStream is;

  public FileBasedMessage(InputStream ios, MALElementStreamFactory encFactory) throws MALException 
  {
    super(false, true, new GENMessageHeader(), ios, encFactory);

    is = ios;
  }

  @Override
  public void free() throws MALException
  {
    System.out.println("File based message freed");
    super.free();

    try
    {
      is.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }
  }
}
