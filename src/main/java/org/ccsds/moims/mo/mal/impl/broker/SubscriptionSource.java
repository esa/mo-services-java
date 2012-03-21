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
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.MessageDetails;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * Base class for subscription sources.
 */
public abstract class SubscriptionSource
{
  protected final Long transactionId;
  protected final UShort area;
  protected final UShort service;
  protected final UShort operation;
  protected final UOctet version;
  protected final MessageDetails msgDetails;

  /**
   * Constructor.
   * @param hdr Source message.
   */
  public SubscriptionSource(MALMessageHeader hdr, URI uriTo, MALBrokerBindingImpl binding)
  {
    this.msgDetails = new MessageDetails(binding.getEndpoint(),
            binding.getURI(),
            uriTo,
            uriTo,
            MALContextFactory.lookupArea(hdr.getServiceArea()).getServiceByNumberAndVersion(hdr.getService(), hdr.getServiceVersion()),
            binding.getAuthenticationId(),
            hdr.getDomain(),
            hdr.getNetworkZone(),
            hdr.getSession(),
            hdr.getSessionName(),
            hdr.getQoSlevel(),
            null,
            hdr.getPriority());
    
    this.transactionId = hdr.getTransactionId();
    this.area = hdr.getServiceArea();
    this.service = hdr.getService();
    this.operation = hdr.getOperation();
    this.version = hdr.getServiceVersion();
  }

  /**
   * Returns the signature for this source.
   * @return signature.
   */
  public abstract String getSignature();

  /**
   * Determines if this source is active.
   * @return true if this source is active.
   */
  public abstract boolean active();

  /**
   * Debugging report.
   */
  public abstract void report();

  /**
   * Adds a subscription to this source.
   * @param srcHdr Source message.
   * @param subscription New subscription.
   */
  public abstract void addSubscription(MALMessageHeader srcHdr, Subscription subscription);

  /**
   * Adds messages to the list of notify messages to be sent out.
   * @param srcHdr Source publish message.
   * @param lst List of broker messages.
   * @param updateList update list.
   */
  public abstract void populateNotifyList(MALMessageHeader srcHdr, List<BrokerMessage> lst, UpdateHeaderList updateHeaderList, MALPublishBody publishBody) throws MALException;
  
  /**
   * Removes a subscription.
   * @param subscriptions List of subscription identifiers to remove.
   */
  public abstract void removeSubscriptions(IdentifierList subscriptions);

  /**
   * Removes all subscriptions for a consumer.
   */
  public abstract void removeAllSubscriptions();
}
