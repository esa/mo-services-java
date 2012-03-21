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
package org.ccsds.moims.mo.mal.impl.provider;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.impl.AddressKey;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.impl.MessageSend;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublisher;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Implementation of the MALPublisher interface.
 */
class MALPublisherImpl implements MALPublisher
{
  private final MALProviderImpl parent;
  private final MessageSend handler;
  private final MALPubSubOperation operation;
  private final IdentifierList domain;
  private final Identifier networkZone;
  private final SessionType sessionType;
  private final Identifier sessionName;
  private final QoSLevel remotePublisherQos;
  private final Map remotePublisherQosProps;
  private final UInteger remotePublisherPriority;
  private Map<AddressKey, Long> transId = new TreeMap<AddressKey, Long>();

  MALPublisherImpl(MALProviderImpl parent, MessageSend handler, MALPubSubOperation operation,
          IdentifierList domain,
          Identifier networkZone,
          SessionType sessionType,
          Identifier sessionName,
          QoSLevel remotePublisherQos,
          Map remotePublisherQosProps,
          UInteger remotePublisherPriority)
  {
    this.parent = parent;
    this.handler = handler;
    this.operation = operation;
    this.domain = domain;
    this.networkZone = networkZone;
    this.sessionType = sessionType;
    this.sessionName = sessionName;
    this.remotePublisherQos = remotePublisherQos;
    this.remotePublisherQosProps = remotePublisherQosProps;
    this.remotePublisherPriority = remotePublisherPriority;
  }

  public void close() throws MALException
  {
  }

  public MALProvider getProvider()
  {
    return parent;
  }

  /**
   *
   * @param entityKeys
   * @param listener
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param remotePublisherQos
   * @param remotePublisherQosProps
   * @param remotePublisherPriority
   * @throws MALException
   */
  @Override
  public void register(EntityKeyList entityKeys, MALPublishInteractionListener listener) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    setTransId(parent.getPublishEndpoint().getURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue(),
            handler.publishRegister(details, operation, entityKeys, listener));
  }

  /**
   *
   * @param entityKeys
   * @param listener
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param remotePublisherQos
   * @param remotePublisherQosProps
   * @param remotePublisherPriority
   * @throws MALException
   */
  @Override
  public MALMessage asyncRegister(EntityKeyList entityKeys, MALPublishInteractionListener listener) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    org.ccsds.moims.mo.mal.transport.MALMessage msg = handler.publishRegisterAsync(details, operation, entityKeys, listener);

    setTransId(parent.getPublishEndpoint().getURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue(),
            msg.getHeader().getTransactionId());

    return msg;
  }

  /**
   *
   * @param updateList
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param publishQos
   * @param publishQosProps
   * @param publishPriority
   * @throws MALException
   */
  @Override
  public MALMessage publish(UpdateHeaderList updateHeaderList, List... updateLists) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    Long tid = getTransId(parent.getPublishEndpoint().getURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());

    if (null != tid)
    {
      Logging.logMessage("INFO: Publisher using transaction Id of: " + tid);

      Object[] body = new Object[updateLists.length + 1];
      body[0] = updateHeaderList;
      System.arraycopy(updateLists, 0, body, 1, updateLists.length);
      
      return handler.onewayInteraction(details, tid, operation, MALPubSubOperation.PUBLISH_STAGE, body);
    }
    else
    {
      // this means that we haven't successfully registered, need to throw an exception
      throw new MALInteractionException(new MALStandardError(MALHelper.INCORRECT_STATE_ERROR_NUMBER, null));
    }
  }

  /**
   *
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param remotePublisherQos
   * @param remotePublisherQosProps
   * @param remotePublisherPriority
   * @throws MALException
   */
  @Override
  public void deregister() throws MALInteractionException, MALException
  {
    MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    handler.publishDeregister(details, operation);

    clearTransId(parent.getPublishEndpoint().getURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());
  }

  /**
   *
   * @param listener
   * @param domain
   * @param networkZone
   * @param sessionType
   * @param sessionName
   * @param remotePublisherQos
   * @param remotePublisherQosProps
   * @param remotePublisherPriority
   * @throws MALException
   */
  @Override
  public MALMessage asyncDeregister(MALPublishInteractionListener listener) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MessageDetails details = new MessageDetails(parent.getEndpoint(),
            parent.getURI(),
            null,
            parent.getBrokerURI(),
            operation.getService(),
            parent.getAuthenticationId(),
            domain,
            networkZone,
            sessionType,
            sessionName,
            remotePublisherQos,
            remotePublisherQosProps,
            remotePublisherPriority);

    org.ccsds.moims.mo.mal.transport.MALMessage msg = handler.publishDeregisterAsync(details, operation, listener);

    clearTransId(parent.getPublishEndpoint().getURI(),
            domain,
            networkZone.getValue(),
            sessionType,
            sessionName.getValue());

    return msg;
  }

  private synchronized void setTransId(URI brokerUri,
          IdentifierList domain,
          String networkZone,
          SessionType session,
          String sessionName,
          Long id)
  {
    AddressKey key = new AddressKey(brokerUri, domain, networkZone, session, sessionName);

    if (!transId.containsKey(key))
    {
      Logging.logMessage("INFO: Publisher setting transaction Id to: " + id);
      transId.put(key, id);
    }
  }

  private synchronized void clearTransId(URI brokerUri,
          IdentifierList domain,
          String networkZone,
          SessionType session,
          String sessionName)
  {
    AddressKey key = new AddressKey(brokerUri, domain, networkZone, session, sessionName);

    Long id = transId.get(key);
    if (null != id)
    {
      Logging.logMessage("INFO: Publisher removing transaction Id of: " + id);
      transId.remove(key);
    }
  }

  private synchronized Long getTransId(URI brokerUri,
          IdentifierList domain,
          String networkZone,
          SessionType session,
          String sessionName)
  {
    return transId.get(new AddressKey(brokerUri, domain, networkZone, session, sessionName));
  }
}
