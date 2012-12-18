/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl.patterns;

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Base class for interactions.
 */
public abstract class BaseInteractionImpl implements MALInteraction
{
  private final MessageSend sender;
  private final Address address;
  private final Long internalTransId;
  private final MALMessage msg;
  private final MALOperation operation;
  private final Map qosProperties = new HashMap();

  BaseInteractionImpl(final MessageSend sender,
          final Address address,
          final Long internalTransId,
          final MALMessage msg) throws MALInteractionException
  {
    this.sender = sender;
    this.address = address;
    this.internalTransId = internalTransId;
    this.msg = msg;
    this.operation = MALContextFactory.lookupArea(msg.getHeader().getServiceArea(), msg.getHeader().getAreaVersion())
            .getServiceByNumber(msg.getHeader().getService())
            .getOperationByNumber(msg.getHeader().getOperation());

    if (null == this.operation)
    {
      throw new MALInteractionException(new MALStandardError(MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER,
              new Union(msg.getHeader().getServiceArea()
              + "::" + msg.getHeader().getService() + "::" + msg.getHeader().getOperation())));
    }
  }

  @Override
  public MALMessageHeader getMessageHeader()
  {
    return msg.getHeader();
  }

  @Override
  public MALOperation getOperation()
  {
    return operation;
  }

  @Override
  public Object getQoSProperty(final String name)
  {
    return (Element) qosProperties.get(name);
  }

  @Override
  public void setQoSProperty(final String name,
          final Object value)
  {
    qosProperties.put(name, value);
  }

  /**
   * Returns the Address object used to create this object.
   *
   * @return the address.
   */
  public Address getAddress()
  {
    return address;
  }

  /**
   * Returns a response to the consumer.
   *
   * @param stage Stage to use.
   * @param isFinalStage true is this is the final stage of the interaction.
   * @param result Message body.
   * @return the sent message.
   * @throws MALException On error.
   */
  protected MALMessage returnResponse(final UOctet stage,
          final boolean isFinalStage,
          final Object... result) throws MALException
  {
    return sender.returnResponse(address,
            internalTransId,
            msg.getHeader(),
            msg.getHeader().getQoSlevel(),
            stage,
            isFinalStage,
            operation,
            result);
  }

  /**
   * Returns an encoded response to the consumer.
   *
   * @param stage Stage to use.
   * @param isFinalStage true is this is the final stage of the interaction.
   * @param body Encoded message body.
   * @return the sent message.
   * @throws MALException On error.
   */
  protected MALMessage returnResponse(final UOctet stage,
          final boolean isFinalStage,
          final MALEncodedBody body) throws MALException
  {
    return sender.returnResponse(address,
            internalTransId,
            msg.getHeader(),
            msg.getHeader().getQoSlevel(),
            stage,
            isFinalStage,
            operation,
            body);
  }

  /**
   * Returns an error to the consumer.
   *
   * @param stage The stage to use.
   * @param error The error to send.
   * @return the sent message.
   * @throws MALException On error.
   */
  protected MALMessage returnError(final UOctet stage,
          final MALStandardError error) throws MALException
  {
    return sender.returnError(address, internalTransId, msg.getHeader(), stage, error);
  }
}
