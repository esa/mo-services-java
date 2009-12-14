/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author cooper_sf
 */
public abstract class SubscriptionSource
{
  protected final QoSLevel QoSlevel;
  protected final Integer priority;
  protected final Identifier transactionId;
  protected final DomainIdentifier domain;
  protected final Identifier networkZone;
  protected final SessionType session;
  protected final Identifier area;
  protected final Identifier service;
  protected final Identifier operation;
  protected final Byte version;
  protected final String signature;

  public SubscriptionSource(MessageHeader hdr)
  {
    this.QoSlevel = hdr.getQoSlevel();
    this.priority = hdr.getPriority();
    this.transactionId = hdr.getTransactionId();
    this.domain = hdr.getDomain();
    this.networkZone = hdr.getNetworkZone();
    this.session = hdr.getSession();
    this.area = hdr.getArea();
    this.service = hdr.getService();
    this.operation = hdr.getOperation();
    this.version = hdr.getVersion();
    this.signature = MALBaseBrokerHandler.makeSig(hdr);
  }

  public String getSignature()
  {
    return signature;
  }

  public QoSLevel getQoSlevel()
  {
    return QoSlevel;
  }

  public Integer getPriority()
  {
    return priority;
  }

  public abstract boolean notActive();

  public abstract void report();

  public abstract void addSubscription(MessageHeader srcHdr, String consumer, Subscription subscription, MALBrokerBindingImpl binding);

  public abstract void populateNotifyList(MessageHeader srcHdr, List<MALBrokerMessage> lst, UpdateList updateList);
  
  public abstract void removeSubscriptions(String consumer, IdentifierList subscriptions);

  public abstract void removeAllSubscriptions(String consumer);
}
