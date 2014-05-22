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
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementOutputStream;
import org.ccsds.moims.mo.mal.encoding.MALElementStreamFactory;
import org.ccsds.moims.mo.mal.structures.*;
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

    this.body = createMessageBody(encFactory, enc);
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

    this.body = createMessageBody(encFactory, enc);
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

    this.body = createMessageBody(encFactory, enc);
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
   * @param lowLevelOutputStream the stream to write to.
   * @throws MALException On encoding error.
   */
  public void encodeMessage(final MALElementStreamFactory streamFactory,
          final MALElementOutputStream enc,
          final OutputStream lowLevelOutputStream) throws MALException
  {
    try
    {
      // if we have a header encode it
      if (null != header)
      {
        enc.writeElement(header, null);
      }

      // now encode the body
      body.encodeMessageBody(streamFactory, enc, lowLevelOutputStream);
    }
    catch (Throwable ex)
    {
      throw new MALException("Internal error encoding message", ex);
    }
  }

  private GENMessageBody createMessageBody(final MALElementStreamFactory encFactory,
          final MALElementInputStream encBodyElements)
  {
    if (header.getIsErrorMessage())
    {
      return new GENErrorBody(wrapBodyParts, encFactory, encBodyElements);
    }

    if (InteractionType._PUBSUB_INDEX == header.getInteractionType().getOrdinal())
    {
      final short stage = header.getInteractionStage().getValue();
      switch (stage)
      {
        case MALPubSubOperation._REGISTER_STAGE:
          return new GENRegisterBody(wrapBodyParts, encFactory, encBodyElements);
        case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
          return new GENPublishRegisterBody(wrapBodyParts, encFactory, encBodyElements);
        case MALPubSubOperation._PUBLISH_STAGE:
          return new GENPublishBody(wrapBodyParts, encFactory, encBodyElements);
        case MALPubSubOperation._NOTIFY_STAGE:
          return new GENNotifyBody(wrapBodyParts, encFactory, encBodyElements);
        case MALPubSubOperation._DEREGISTER_STAGE:
          return new GENDeregisterBody(wrapBodyParts, encFactory, encBodyElements);
        default:
          return new GENMessageBody(wrapBodyParts, encFactory, encBodyElements);
      }
    }

    return new GENMessageBody(wrapBodyParts, encFactory, encBodyElements);
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
