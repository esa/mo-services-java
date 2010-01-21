package org.ccsds.moims.mo.mal.impl.broker;

import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdateList;

public final class BrokerMessage
{
  public final MALBrokerBindingImpl binding;
  public final List<NotifyMessage> msgs = new LinkedList<NotifyMessage>();

  public BrokerMessage(MALBrokerBindingImpl binding)
  {
    this.binding = binding;
  }

  public static final class NotifyMessage
  {
  public final MessageHeader header = new MessageHeader();
  public final SubscriptionUpdateList updates = new SubscriptionUpdateList();
  }
}
