/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.body;

import esa.mo.mal.transport.gen.GENTransport;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;

/**
 * Implementation of the MALMessageBody interface.
 */
public class GENMessageBody implements MALMessageBody, java.io.Serializable
{
  /**
   * Factory used to create encoders/decoders.
   */
  protected MALElementStreamFactory encFactory;
  /**
   * Input stream that holds the encoded message body parts.
   */
  protected MALElementInputStream encBodyElements;
  /**
   * True if we have already decoded the body.
   */
  protected boolean decodedBody = false;
  /**
   * Number of body parts.
   */
  protected int bodyPartCount;
  /**
   * Short forms of the body parts.
   */
  protected Object[] shortForms;
  /**
   * The decoded body parts.
   */
  protected Object[] messageParts;
  private final boolean wrappedBodyParts;
  private static final long serialVersionUID = 222222222222223L;

  /**
   * Constructor.
   *
   * @param shortForms The short forms of the body parts.
   * @param messageParts The message body parts.
   */
  public GENMessageBody(final Object[] shortForms, final Object[] messageParts)
  {
    wrappedBodyParts = false;
    if (null != messageParts)
    {
      this.bodyPartCount = messageParts.length;
    }
    else
    {
      this.bodyPartCount = 0;
    }

    this.shortForms = shortForms;
    this.messageParts = messageParts;
    decodedBody = true;
  }

  /**
   * Constructor.
   *
   * @param messageParts The message body parts.
   */
  public GENMessageBody(final Object[] messageParts)
  {
    wrappedBodyParts = false;
    if (null != messageParts)
    {
      this.bodyPartCount = messageParts.length;
    }
    else
    {
      this.bodyPartCount = 0;
    }

    this.shortForms = new Object[this.bodyPartCount];
    this.messageParts = messageParts;
    decodedBody = true;
  }

  /**
   * Constructor.
   *
   * @param wrappedBodyParts True if the encoded body parts are wrapped in BLOBs.
   * @param count The number of message parts.
   * @param encFactory The encoder stream factory to use.
   * @param encBodyElements The input stream that holds the encoded body parts.
   */
  public GENMessageBody(final boolean wrappedBodyParts,
          final int count,
          final MALElementStreamFactory encFactory,
          final MALElementInputStream encBodyElements)
  {
    this.wrappedBodyParts = wrappedBodyParts;
    this.bodyPartCount = count;
    this.encFactory = encFactory;
    this.shortForms = new Object[count];
    this.encBodyElements = encBodyElements;
  }

  @Override
  public int getElementCount()
  {
    return bodyPartCount;
  }

  @Override
  public MALEncodedBody getBodyElement() throws MALException
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Object getBodyElement(final int index, final Object element) throws IllegalArgumentException, MALException
  {
    if (null != element)
    {
      shortForms[index] = element.getClass().getPackage().getName();
    }

    decodeMessageBody();

    return messageParts[index];
  }

  @Override
  public MALEncodedElement getEncodedBodyElement(final int index) throws MALException
  {
    if (-1 == index)
    {
      // want the complete message body
      return new MALEncodedElement((Blob) encBodyElements.readElement(new Blob(), null));
    }
    else
    {
      return null;
    }
  }

  /**
   * Returns the short form of a request body part.
   *
   * @param index The index of the body part to return the short form of.
   * @return the request short form.
   */
  public Object getBodyShortForm(final int index)
  {
    return shortForms[index];
  }

  /**
   * Returns the decoded message parts.
   *
   * @return The decoded message parts.
   */
  public Object[] getMessageParts()
  {
    decodeMessageBody();
    return messageParts;
  }

  /**
   * Sets the message parts.
   *
   * @param messageParts The message parts to set
   */
  public void setMessageParts(final Object[] messageParts)
  {
    this.messageParts = messageParts;
    decodedBody = true;
  }

  /**
   * Decodes the message body.
   */
  protected void decodeMessageBody()
  {
    if (!decodedBody)
    {
      decodedBody = true;

      try
      {
        GENTransport.LOGGER.log(Level.FINE, "GEN Message decoding body ... pc ({0})", bodyPartCount);
        messageParts = new Object[bodyPartCount];

        if (bodyPartCount == 1)
        {
          messageParts[0] = decodeBodyPart(encBodyElements);
        }
        else if (bodyPartCount > 1)
        {
          MALElementInputStream benc = encBodyElements;
          if (wrappedBodyParts)
          {
            GENTransport.LOGGER.fine("GEN Message decoding body wrapper");
            final Blob body = (Blob) encBodyElements.readElement(new Blob(), null);
            final ByteArrayInputStream bais = new ByteArrayInputStream(body.getValue());
            benc = encFactory.createInputStream(bais);
          }

          for (int i = 0; i < bodyPartCount; i++)
          {
            GENTransport.LOGGER.log(Level.FINE, "GEN Message decoding body part : {0}", i);
            messageParts[i] = decodeBodyPart(benc);
          }
        }

        GENTransport.LOGGER.info("GEN Message decoded body");
      }
      catch (MALException ex)
      {
        GENTransport.LOGGER.log(Level.WARNING, "GEN Message body ERROR on decode : {0}", ex);
        ex.printStackTrace();
      }
    }
  }

  /**
   * Decodes a single part of the message body.
   *
   * @param decoder The decoder to use.
   * @return The decoded chunk.
   * @throws MALException if any error detected.
   */
  protected Object decodeBodyPart(final MALElementInputStream decoder) throws MALException
  {
    Object rv = null;

    MALElementInputStream lenc = decoder;
    if (wrappedBodyParts)
    {
      final Blob ele = (Blob) decoder.readElement(new Blob(), null);
      final ByteArrayInputStream lbais = new ByteArrayInputStream(ele.getValue());
      lenc = encFactory.createInputStream(lbais);
    }

    // boolean to tell us whether it is a MAL element or JAXB element we have received
    final Union ut = (Union) lenc.readElement(new Union(Boolean.TRUE), null);

    if (ut.getBooleanValue())
    {
      final Union u = (Union) lenc.readElement(new Union(0L), null);
      if (null != u)
      {
        final Long shortForm = u.getLongValue();
        GENTransport.LOGGER.log(Level.FINER, "GEN Message decoding body part : Type = {0}", shortForm);
        final MALElementFactory ef =
                MALContextFactory.getElementFactoryRegistry().lookupElementFactory(shortForm);

        if (null != ef)
        {
          rv = lenc.readElement(ef.createElement(), null);
        }
        else
        {
          throw new MALException("GEN transport unable to find element factory for short type: " + shortForm);
        }
      }
    }
    else
    {
      final Union u = (Union) lenc.readElement(new Union(""), null);
      if (null != u)
      {
        final String shortForm = u.getStringValue();
        GENTransport.LOGGER.log(Level.FINER, "GEN Message decoding XML body part : Type = {0}", shortForm);

        try
        {
          final String schemaURN = shortForm.substring(0, shortForm.lastIndexOf(':'));
          final String packageName = (String) shortForms[0];

          final JAXBContext jc = JAXBContext.newInstance(packageName);
          final Unmarshaller unmarshaller = jc.createUnmarshaller();

          final String srcString = ((Union) lenc.readElement(new Union(""), null)).getStringValue();
          final StringReader ir = new StringReader(srcString);
          final JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(ir);
          rv = rootElement.getValue();
        }
        catch (JAXBException ex)
        {
          throw new MALException("XML Decoding error", ex);
        }
      }
    }

    return rv;
  }
}
