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
package esa.mo.mal.transport.gen;

import esa.mo.mal.transport.gen.body.GENDeregisterBody;
import esa.mo.mal.transport.gen.body.GENErrorBody;
import esa.mo.mal.transport.gen.body.GENMessageBody;
import esa.mo.mal.transport.gen.body.GENNotifyBody;
import esa.mo.mal.transport.gen.body.GENPublishBody;
import esa.mo.mal.transport.gen.body.GENPublishRegisterBody;
import esa.mo.mal.transport.gen.body.GENRegisterBody;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEncodedElement;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * A generic implementation of the message interface.
 */
public class GENMessage implements MALMessage, java.io.Serializable
{
  private final GENMessageHeader header;
  private final GENMessageBody body;
  private final Map qosProperties;
  private final boolean wrapBodyParts;
  private static final long serialVersionUID = 222222222222222L;

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param header The message header to use.
   * @param qosProperties The QoS properties for this message.
   * @param shortForms The short forms for the body parts.
   * @param body the body of the message.
   */
  public GENMessage(final boolean wrapBodyParts,
          final GENMessageHeader header,
          final Map qosProperties,
          final Object[] shortForms,
          final Object... body)
  {
    this.header = header;
    this.body = createMessageBody(shortForms, body);
    this.qosProperties = qosProperties;
    this.wrapBodyParts = wrapBodyParts;
  }

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param packet The message in encoded form.
   * @param encFactory The stream factory to use for decoding.
   * @throws MALException On decoding error.
   */
  public GENMessage(final boolean wrapBodyParts, final byte[] packet, final MALElementStreamFactory encFactory) throws MALException
  {
    this.qosProperties = new TreeMap();
    this.wrapBodyParts = wrapBodyParts;

    final ByteArrayInputStream bais = new ByteArrayInputStream(packet);
    final MALElementInputStream enc = encFactory.createInputStream(bais);

    this.header = (GENMessageHeader) enc.readElement(new GENMessageHeader(), null);
    final int count = ((UShort) enc.readElement(new UShort(), null)).getValue();

    this.body = createMessageBody(count, encFactory, enc);
  }

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param ios The message in encoded form.
   * @param encFactory The stream factory to use for decoding.
   * @throws MALException On decoding error.
   */
  public GENMessage(final boolean wrapBodyParts,
          final java.io.InputStream ios, final MALElementStreamFactory encFactory) throws MALException
  {
    this.qosProperties = new TreeMap();
    this.wrapBodyParts = wrapBodyParts;

    final MALElementInputStream enc = encFactory.createInputStream(ios);

    this.header = (GENMessageHeader) enc.readElement(new GENMessageHeader(), null);
    final int count = ((UShort) enc.readElement(new UShort(), null)).getValue();

    this.body = createMessageBody(count, encFactory, enc);
  }

  /**
   * Constructor.
   *
   * @param wrapBodyParts True if the encoded body parts should be wrapped in BLOBs.
   * @param header The message header to use.
   * @param packet The message body in encoded form.
   * @param encFactory The stream factory to use for decoding.
   * @throws MALException On decoding error.
   */
  public GENMessage(final boolean wrapBodyParts, final GENMessageHeader header, final byte[] packet, final MALElementStreamFactory encFactory)
          throws MALException
  {
    this.qosProperties = new TreeMap();
    this.header = header;
    this.wrapBodyParts = wrapBodyParts;

    final ByteArrayInputStream bais = new ByteArrayInputStream(packet);
    final MALElementInputStream enc = encFactory.createInputStream(bais);

    final int count = ((UShort) enc.readElement(new UShort(), null)).getValue();

    this.body = createMessageBody(count, encFactory, enc);
  }

  @Override
  public MALMessageHeader getHeader()
  {
    return header;
  }

  @Override
  public MALMessageBody getBody()
  {
    return body;
  }

  @Override
  public Map getQoSProperties()
  {
    return qosProperties;
  }

  @Override
  public void free() throws MALException
  {
  }

  public boolean isWrapBodyParts()
  {
    return wrapBodyParts;
  }

  /**
   * Encodes the contents of the message into the provided stream
   *
   * @param streamFactory The stream factory to use for encoder creation.
   * @param enc The output stream to use for encoding.
   * @throws MALException On encoding error.
   */
  public void encodeMessage(final MALElementStreamFactory streamFactory,
          final MALElementOutputStream enc) throws MALException
  {
    try
    {
      // if we have a header encode it
      if (null != header)
      {
        enc.writeElement(header, null);
      }

      // now encode a count of the number of body parts
      final int count = body.getElementCount();
      enc.writeElement(new UShort(count), null);

      GENTransport.LOGGER.log(Level.INFO, "GEN Message encoding body ... pc ({0})", count);

      // if we only have a single body part then encode that directly
      if (count == 1)
      {
        encodeBodyPart(streamFactory, enc, wrapBodyParts, body.getBodyShortForm(0), body.getBodyElement(0, null));
      }
      else if (count > 1)
      {
        MALElementOutputStream benc = enc;
        ByteArrayOutputStream bbaos = null;

        if (wrapBodyParts)
        {
          // we have more than one body part, therefore encode each part into a separate byte buffer, and then encode
          // that byte buffer as a whole. This allows use to be able to return the complete body of the message as a
          // single unit if required.
          bbaos = new ByteArrayOutputStream();
          benc = streamFactory.createOutputStream(bbaos);
        }

        for (int i = 0; i < count; i++)
        {
          encodeBodyPart(streamFactory, benc, wrapBodyParts, body.getBodyShortForm(i), body.getBodyElement(i, null));
        }

        if (wrapBodyParts)
        {
          benc.flush();
          benc.close();

          enc.writeElement(new Blob(bbaos.toByteArray()), null);
        }
      }
      enc.flush();
      enc.close();
    }
    catch (Throwable ex)
    {
      throw new MALException("Internal error encoding message", ex);
    }
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

  private GENMessageBody createMessageBody(final int count,
          final MALElementStreamFactory encFactory,
          final MALElementInputStream encBodyElements)
  {
    if (header.getIsErrorMessage())
    {
      return new GENErrorBody(wrapBodyParts, count, encFactory, encBodyElements);
    }

    if (InteractionType._PUBSUB_INDEX == header.getInteractionType().getOrdinal())
    {
      final short stage = header.getInteractionStage().getValue();
      switch (stage)
      {
        case MALPubSubOperation._REGISTER_STAGE:
          return new GENRegisterBody(wrapBodyParts, count, encFactory, encBodyElements);
        case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
          return new GENPublishRegisterBody(wrapBodyParts, count, encFactory, encBodyElements);
        case MALPubSubOperation._PUBLISH_STAGE:
          return new GENPublishBody(wrapBodyParts, count, encFactory, encBodyElements);
        case MALPubSubOperation._NOTIFY_STAGE:
          return new GENNotifyBody(wrapBodyParts, count, encFactory, encBodyElements);
        case MALPubSubOperation._DEREGISTER_STAGE:
          return new GENDeregisterBody(wrapBodyParts, count, encFactory, encBodyElements);
        default:
          return new GENMessageBody(wrapBodyParts, count, encFactory, encBodyElements);
      }
    }

    return new GENMessageBody(wrapBodyParts, count, encFactory, encBodyElements);
  }

  private GENMessageBody createMessageBody(final Object[] shortForms, final Object[] bodyElements)
  {
    if (header.getIsErrorMessage())
    {
      return new GENErrorBody(bodyElements);
    }

    if (InteractionType._PUBSUB_INDEX == header.getInteractionType().getOrdinal())
    {
      final short stage = header.getInteractionStage().getValue();
      switch (stage)
      {
        case MALPubSubOperation._REGISTER_STAGE:
          return new GENRegisterBody(bodyElements);
        case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
          return new GENPublishRegisterBody(bodyElements);
        case MALPubSubOperation._PUBLISH_STAGE:
          return new GENPublishBody(bodyElements);
        case MALPubSubOperation._NOTIFY_STAGE:
          return new GENNotifyBody(bodyElements);
        case MALPubSubOperation._DEREGISTER_STAGE:
          return new GENDeregisterBody(bodyElements);
        default:
          return new GENMessageBody(shortForms, bodyElements);
      }
    }

    return new GENMessageBody(shortForms, bodyElements);
  }
}
