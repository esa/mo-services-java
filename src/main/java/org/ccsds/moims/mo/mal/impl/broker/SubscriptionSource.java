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
import org.ccsds.moims.mo.mal.structures.DomainIdentifier;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * Base class for subscription sources.
 */
public abstract class SubscriptionSource
{
  protected final QoSLevel qosLevel;
  protected final Integer priority;
  protected final Identifier transactionId;
  protected final DomainIdentifier domain;
  protected final Identifier networkZone;
  protected final SessionType session;
  protected final Identifier area;
  protected final Identifier service;
  protected final Identifier operation;
  protected final Byte version;

  /**
   * Constructor.
   * @param hdr Source message.
   */
  public SubscriptionSource(MessageHeader hdr)
  {
    this.qosLevel = hdr.getQoSlevel();
    this.priority = hdr.getPriority();
    this.transactionId = hdr.getTransactionId();
    this.domain = hdr.getDomain();
    this.networkZone = hdr.getNetworkZone();
    this.session = hdr.getSession();
    this.area = hdr.getArea();
    this.service = hdr.getService();
    this.operation = hdr.getOperation();
    this.version = hdr.getVersion();
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
  public abstract void addSubscription(MessageHeader srcHdr, Subscription subscription);

  /**
   * Adds messages to the list of notify messages to be sent out.
   * @param srcHdr Source publish message.
   * @param lst List of broker messages.
   * @param updateList update list.
   */
  public abstract void populateNotifyList(MessageHeader srcHdr, List<BrokerMessage> lst, UpdateList updateList);
  
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
