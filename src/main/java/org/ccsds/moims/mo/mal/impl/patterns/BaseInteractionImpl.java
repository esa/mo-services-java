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
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.impl.Address;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Base class for interactions.
 */
public abstract class BaseInteractionImpl implements MALInteraction
{
  private final MessageSend sender;
  private final Address address;
  private final Identifier internalTransId;
  private final MALMessage msg;
  private final MALOperation operation;
  private final HashMap qosProperties = new HashMap();

  BaseInteractionImpl(MessageSend sender, Address address, Identifier internalTransId, MALMessage msg) throws MALException
  {
    this.sender = sender;
    this.address = address;
    this.internalTransId = internalTransId;
    this.msg = msg;
    this.operation = MALContextFactory.lookupOperation(msg.getHeader().getArea(),
            msg.getHeader().getService(),
            msg.getHeader().getOperation());

    if (null == this.operation)
    {
      throw new MALException(new StandardError(MALHelper.UNSUPPORTED_OPERATION_ERROR_NUMBER,
              new Union(msg.getHeader().getArea()
              + "::" + msg.getHeader().getService() + "::" + msg.getHeader().getOperation())));
    }
  }

  @Override
  /**
   *
   * @return
   */
  public MessageHeader getMessageHeader()
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
  
  /**
   * Returns a response to the consumer.
   * @param stage Stage to use.
   * @param result Message body.
   * @throws MALException On error.
   */
  protected void returnResponse(Byte stage, Element result) throws MALException
  {
    sender.returnResponse(address, internalTransId, msg.getHeader(), stage, result);
  }
  
  /**
   * Returns an error ro the consumer.
   * @param stage The stage to use.
   * @param error The error to send.
   * @throws MALException On error.
   */
  protected void returnError(Byte stage, StandardError error) throws MALException
  {
    sender.returnError(address, internalTransId, msg.getHeader(), stage, error);
  }
}
