/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
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

  BaseInteractionImpl(MessageSend sender, Address address, Long internalTransId, MALMessage msg) throws MALInteractionException
  {
    this.sender = sender;
    this.address = address;
    this.internalTransId = internalTransId;
    this.msg = msg;
    this.operation = MALContextFactory.lookupArea(msg.getHeader().getServiceArea()).getServiceByNumberAndVersion(msg.getHeader().getService(), msg.getHeader().getServiceVersion()).getOperationByNumber(msg.getHeader().getOperation());

    if (null == this.operation)
    {
      throw new MALInteractionException(new MALStandardError(MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER,
              new Union(msg.getHeader().getServiceArea()
              + "::" + msg.getHeader().getService() + "::" + msg.getHeader().getOperation())));
    }
  }

  @Override
  /**
   *
   * @return
   */
  public MALMessageHeader getMessageHeader()
  {
    return msg.getHeader();
  }

  @Override
  /**
   *
   * @return
   */
  public MALOperation getOperation()
  {
    return operation;
  }

  @Override
  /**
   *
   * @param name
   * @return
   */
  public Element getQoSProperty(String name)
  {
    return (Element) qosProperties.get(name);
  }

  @Override
  /**
   *
   * @param name
   * @param value
   */
  public void setQoSProperty(String name, Element value)
  {
    qosProperties.put(name, value);
  }

  public Map getQoSProperties()
  {
    return qosProperties;
  }
  
  /**
   * Returns a response to the consumer.
   * @param stage Stage to use.
   * @param result Message body.
   * @throws MALException On error.
   */
  protected org.ccsds.moims.mo.mal.transport.MALMessage returnResponse(UOctet stage, Object... result) throws MALException
  {
    return sender.returnResponse(address, internalTransId, msg.getHeader(), stage, result);
  }
  
  /**
   * Returns an error ro the consumer.
   * @param stage The stage to use.
   * @param error The error to send.
   * @throws MALException On error.
   */
  protected org.ccsds.moims.mo.mal.transport.MALMessage returnError(UOctet stage, MALStandardError error) throws MALException
  {
    return sender.returnError(address, internalTransId, msg.getHeader(), stage, error);
  }
}
