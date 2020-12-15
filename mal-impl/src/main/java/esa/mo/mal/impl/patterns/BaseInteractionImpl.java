/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.patterns;

import esa.mo.mal.impl.Address;
import esa.mo.mal.impl.MessageSend;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
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
  private final MALMessage msg;
  private final MALOperation operation;
  private final Map qosProperties = new HashMap();

  BaseInteractionImpl(final MessageSend sender,
          final Address address,
          final MALMessage msg) throws MALInteractionException
  {
    this.sender = sender;
    this.address = address;
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
    return qosProperties.get(name);
  }

  @Override
  public void setQoSProperty(final String name,
          final Object value)
  {
    qosProperties.put(name, value);
  }

  @Override
  public Map<String, Object> getQoSProperties()
  {
    return qosProperties;
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
   * @param result Message body.
   * @return the sent message.
   * @throws MALException On error.
   */
  protected MALMessage returnResponse(final UOctet stage,
          final Object... result) throws MALException
  {
    return sender.returnResponse(address,
            msg.getHeader(),
            msg.getHeader().getQoSlevel(),
            stage,
            operation,
            qosProperties,
            result);
  }

  /**
   * Returns an encoded response to the consumer.
   *
   * @param stage Stage to use.
   * @param body Encoded message body.
   * @return the sent message.
   * @throws MALException On error.
   */
  protected MALMessage returnResponse(final UOctet stage,
          final MALEncodedBody body) throws MALException
  {
    return sender.returnResponse(address,
            msg.getHeader(),
            msg.getHeader().getQoSlevel(),
            stage,
            operation,
            qosProperties,
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
    return sender.returnError(address, msg.getHeader(), stage, error);
  }
}
