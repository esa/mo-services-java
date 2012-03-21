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
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.List;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.MALStandardError;
import org.ccsds.moims.mo.mal.impl.MALContextImpl;
import org.ccsds.moims.mo.mal.impl.ServiceComponentImpl;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;

/**
 * Implementation of MALBrokerBinding for MAL level brokers.
 */
public class MALBrokerBindingImpl extends ServiceComponentImpl implements MALInternalBrokerBinding
{
  private final MALBrokerImpl brokerImpl;

  MALBrokerBindingImpl(MALBrokerImpl parent,
          MALContextImpl impl,
          String localName,
          String protocol,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map qosProperties) throws MALException
  {
    super(parent,
            impl,
            localName,
            protocol,
            null,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            qosProperties,
            null);

    this.brokerImpl = parent;
    this.endpoint.startMessageDelivery();

    Logging.logMessage("INFO: Creating internal MAL Broker for localName: "
            + localName + " on protocol: " + protocol + " with URI: " + this.localUri);
  }


  MALBrokerBindingImpl(MALBrokerImpl parent,
          MALContextImpl impl,
          MALEndPoint endPoint,
          Blob authenticationId,
          QoSLevel[] expectedQos,
          UInteger priorityLevelNumber,
          Map qosProperties) throws MALException
  {
    super(parent,
            impl,
            endPoint,
            null,
            authenticationId,
            expectedQos,
            priorityLevelNumber,
            qosProperties,
            null);

    this.brokerImpl = parent;

    Logging.logMessage("INFO: Creating internal MAL Broker for localName: "
            + localName + " with URI: " + this.localUri);
  }

  @Override
  public Blob getAuthenticationId()
  {
    return authenticationId;
  }

  public MALMessage sendNotify(UShort area, UShort service, UShort operation, UOctet version, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, Identifier subscriptionId, UpdateHeaderList updateHeaderList, List... updateList) throws IllegalArgumentException, MALInteractionException, MALException
  {
    Object[] body = new Object[2 + updateList.length];
    body[0] = subscriptionId;
    body[1] = updateHeaderList;
    int i = 2;
    for (Object object : updateList)
    {
      body[i++] = object;
    }

    MALMessage msg = endpoint.createMessage(authenticationId,
            subscriber,
            new Time(new java.util.Date().getTime()),
            notifyQos,
            notifyPriority,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            InteractionType.PUBSUB,
            MALPubSubOperation.NOTIFY_STAGE,
            transactionId,
            area,
            service,
            operation,
            version,
            Boolean.FALSE,
            notifyQosProps,
            body);
    
    endpoint.sendMessage(msg);
    
    return msg;
  }

  public MALMessage sendNotifyError(UShort area, UShort service, UShort operation, UOctet version, URI subscriber, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel notifyQos, Map notifyQosProps, UInteger notifyPriority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MALMessage msg = endpoint.createMessage(authenticationId,
            subscriber,
            new Time(new java.util.Date().getTime()),
            notifyQos,
            notifyPriority,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            InteractionType.PUBSUB,
            MALPubSubOperation.NOTIFY_STAGE,
            transactionId,
            area,
            service,
            operation,
            version,
            Boolean.TRUE,
            notifyQosProps,
            error);
    
    endpoint.sendMessage(msg);
    
    return msg;
  }

  public MALMessage sendPublishError(UShort area, UShort service, UShort operation, UOctet version, URI publisher, Long transactionId, IdentifierList domainId, Identifier networkZone, SessionType sessionType, Identifier sessionName, QoSLevel qos, Map qosProps, UInteger priority, MALStandardError error) throws IllegalArgumentException, MALInteractionException, MALException
  {
    MALMessage msg = endpoint.createMessage(authenticationId,
            publisher,
            new Time(new java.util.Date().getTime()),
            qos,
            priority,
            domainId,
            networkZone,
            sessionType,
            sessionName,
            InteractionType.PUBSUB,
            MALPubSubOperation.PUBLISH_STAGE,
            transactionId,
            area,
            service,
            operation,
            version,
            Boolean.TRUE,
            qosProps,
            error);
    
    endpoint.sendMessage(msg);
    
    return msg;
  }

  /**
   * Returns the reference to the top level broker object.
   * @return The parent broker.
   */
  public MALBrokerBaseImpl getBrokerImpl()
  {
    return brokerImpl;
  }
}
