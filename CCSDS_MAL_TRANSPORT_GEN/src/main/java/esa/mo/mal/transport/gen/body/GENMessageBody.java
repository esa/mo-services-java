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

import esa.mo.mal.encoder.gen.GENElementInputStream;
import esa.mo.mal.transport.gen.GENTransport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UShort;
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
   * @param encFactory The encoder stream factory to use.
   * @param encBodyElements The input stream that holds the encoded body parts.
   */
  public GENMessageBody(final boolean wrappedBodyParts,
          final MALElementStreamFactory encFactory,
          final MALElementInputStream encBodyElements)
  {
    this.wrappedBodyParts = wrappedBodyParts;
    this.encFactory = encFactory;
    this.encBodyElements = encBodyElements;
  }

  @Override
  public int getElementCount()
  {
    decodeMessageBody();
    return bodyPartCount;
  }

  @Override
  public MALEncodedBody getEncodedBody() throws MALException
  {
    if (!decodedBody && (encBodyElements instanceof GENElementInputStream))
    {
      return new MALEncodedBody(new Blob(((GENElementInputStream) encBodyElements).getRemainingEncodedData()));
    }
    else
    {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }

  @Override
  public Object getBodyElement(final int index, final Object element) throws IllegalArgumentException, MALException
  {
    decodeMessageBody();
    if ((null != element) && (null == shortForms[index]))
    {
      shortForms[index] = element.getClass().getPackage().getName();
    }

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
    decodeMessageBody();
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
   * Encodes the contents of the message body into the provided stream
   *
   * @param streamFactory The stream factory to use for encoder creation.
   * @param enc The output stream to use for encoding.
   * @param lowLevelOutputStream Low level output stream to use when have an already encoded body.
   * @throws MALException On encoding error.
   */
  public void encodeMessageBody(final MALElementStreamFactory streamFactory,
          final MALElementOutputStream enc,
          final OutputStream lowLevelOutputStream) throws MALException
  {
    // first check to see if we have an already encoded body
    if ((null != messageParts) && (1 == messageParts.length) && (messageParts[0] instanceof MALEncodedBody))
    {
      enc.flush();

      try
      {
        lowLevelOutputStream.write(((MALEncodedBody) messageParts[0]).getEncodedBody().getValue());
        lowLevelOutputStream.flush();
      }
      catch (IOException ex)
      {
        throw new MALException("MAL encoded body encoding error", ex);
      }
    }
    else
    {
      // now encode a count of the number of body parts
      final int count = getElementCount();
      enc.writeElement(new UShort(count), null);

      GENTransport.LOGGER.log(Level.INFO, "GEN Message encoding body ... pc ({0})", count);

      // if we only have a single body part then encode that directly
      if (count == 1)
      {
        encodeBodyPart(streamFactory, enc, wrappedBodyParts, getBodyShortForm(0), getBodyElement(0, null));
      }
      else if (count > 1)
      {
        MALElementOutputStream benc = enc;
        ByteArrayOutputStream bbaos = null;

        if (wrappedBodyParts)
        {
          // we have more than one body part, therefore encode each part into a separate byte buffer, and then encode
          // that byte buffer as a whole. This allows use to be able to return the complete body of the message as a
          // single unit if required.
          bbaos = new ByteArrayOutputStream();
          benc = streamFactory.createOutputStream(bbaos);
        }

        for (int i = 0; i < count; i++)
        {
          encodeBodyPart(streamFactory, benc, wrappedBodyParts, getBodyShortForm(i), getBodyElement(i, null));
        }

        if (wrappedBodyParts)
        {
          benc.flush();
          benc.close();

          enc.writeElement(new Blob(bbaos.toByteArray()), null);
        }
      }
    }

    enc.flush();
    enc.close();
  }

  private static void encodeBodyPart(final MALElementStreamFactory streamFactory,
          final MALElementOutputStream enc,
          final boolean wrapBodyParts,
          final Object sf, final Object o) throws MALException
  {
    // if it is already an encoded element then just write it directly
    if (o instanceof MALEncodedElement)
    {
      enc.writeElement(((MALEncodedElement) o).getEncodedElement(), null);
    }
    // else if it is a MAL data type object
    else if ((null == o) || (o instanceof Element))
    {
      MALElementOutputStream lenc = enc;
      ByteArrayOutputStream lbaos = null;

      if (wrapBodyParts)
      {
        // we encode it into a byte buffer so that it can be extracted as a MALEncodedElement if required
        lbaos = new ByteArrayOutputStream();
        lenc = streamFactory.createOutputStream(lbaos);
      }

      // first encode a TRUE boolean because this is a MAL object
      lenc.writeElement(new Union(Boolean.TRUE), null);

      // then encode the short form if it is not null
      final Element e = (Element) o;
      if (null != e)
      {
        lenc.writeElement(new Union(e.getShortForm()), null);
      }

      // now encode the element
      lenc.writeElement(e, null);

      if (wrapBodyParts)
      {
        lenc.flush();
        lenc.close();

        // write the encoded blob to the stream
        enc.writeElement(new Blob(lbaos.toByteArray()), null);
      }
    }
    // else if it is a JAXB XML object
    else if (o.getClass().isAnnotationPresent(javax.xml.bind.annotation.XmlType.class))
    {
      try
      {
        // get the XML tags for the object
        final String ssf = (String) sf;
        final String schemaURN = ssf.substring(0, ssf.lastIndexOf(':'));
        final String schemaEle = ssf.substring(ssf.lastIndexOf(':') + 1);

        // create the marshaller
        final JAXBContext jc = JAXBContext.newInstance(o.getClass().getPackage().getName());
        final Marshaller marshaller = jc.createMarshaller();

        // encode the XML into a string
        final StringWriter ow = new StringWriter();
        marshaller.marshal(new JAXBElement(new QName(schemaURN, schemaEle), o.getClass(), null, o), ow);
        GENTransport.LOGGER.log(Level.INFO, "GEN Message encoding XML body part : {0}", ow.toString());

        MALElementOutputStream lenc = enc;
        ByteArrayOutputStream lbaos = null;

        if (wrapBodyParts)
        {
          // we encode it into a byte buffer so that it can be extracted as a MALEncodedElement if required
          lbaos = new ByteArrayOutputStream();
          lenc = streamFactory.createOutputStream(lbaos);
        }

        // first encode a FALSE boolean because this is an XML object
        lenc.writeElement(new Union(Boolean.FALSE), null);
        // then encode the short form
        lenc.writeElement(new Union(ssf), null);
        // now encode the element
        lenc.writeElement(new Union(ow.toString()), null);

        if (wrapBodyParts)
        {
          lenc.flush();
          lenc.close();

          // write the encoded blob to the stream
          enc.writeElement(new Blob(lbaos.toByteArray()), null);
        }
      }
      catch (JAXBException ex)
      {
        throw new MALException("XML Encoding error", ex);
      }
    }
    else
    {
      throw new MALException("ERROR: Unable to encode body object of type: " + o.getClass().getSimpleName());
    }
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
        bodyPartCount = ((UShort) encBodyElements.readElement(new UShort(), null)).getValue();
        this.shortForms = new Object[bodyPartCount];
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
        final MALElementFactory ef
                = MALContextFactory.getElementFactoryRegistry().lookupElementFactory(shortForm);

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
