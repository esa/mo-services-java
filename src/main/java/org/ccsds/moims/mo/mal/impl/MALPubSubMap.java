package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALPubSubMap
{
  private final java.util.Map<String, MALInteractionListener> notifyMap = new java.util.TreeMap<String, MALInteractionListener>();

  public MALPubSubMap()
  {
  }

  public void registerNotifyListener(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list)
  {
    //TODO: Not correct currently as register can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized (notifyMap)
    {
      if (false == notifyMap.containsKey(id))
      {
        notifyMap.put(id, list);
      }
    }
  }

  public MALInteractionListener getNotifyListener(URI uri)
  {
    final String id = uri.getValue();

    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        return notifyMap.get(id);
      }
    }

    return null;
  }

  public void deregisterNotifyListener(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription)
  {
    //TODO: Not correct currently as deregister can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        notifyMap.remove(id);
      }
    }
  }
}
