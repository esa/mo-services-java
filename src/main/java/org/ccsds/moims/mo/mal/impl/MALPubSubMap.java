package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALPubSubMap
{
  private final java.util.Map<StringPair, MALPublishInteractionListener> publisherMap = new java.util.TreeMap<StringPair, MALPublishInteractionListener>();
  private final java.util.Map<String, Map<String, MALInteractionListener>> errorMap = new java.util.TreeMap<String, Map<String, MALInteractionListener>>();
  private final java.util.Map<StringPair, MALInteractionListener> notifyMap = new java.util.TreeMap<StringPair, MALInteractionListener>();

  public MALPubSubMap()
  {
  }

  public void registerPublishListener(MALMessageDetails details, MALPublishInteractionListener listener)
  {
    final StringPair id = new StringPair(details.uriFrom.getValue(), details.sessionName.getValue());

    synchronized (publisherMap)
    {
      //if (false == publisherMap.containsKey(id))
      {
        publisherMap.put(id, listener);

        System.out.println("INFO: Adding publisher: " + id);
      }
    }
  }

  public MALPublishInteractionListener getPublishListener(URI uri, Identifier sessionName)
  {
    final StringPair id = new StringPair(uri.getValue(), sessionName.getValue());
    MALPublishInteractionListener list = null;

    synchronized (publisherMap)
    {
      list = publisherMap.get(id);
    }

    if(null != list)
    {
      System.out.println("INFO: Getting publisher: " + id);
    }

    return list;
  }

  public MALPublishInteractionListener getPublishListenerAndRemove(URI uri, Identifier sessionName)
  {
    final StringPair id = new StringPair(uri.getValue(), sessionName.getValue());
    MALPublishInteractionListener list = null;

    synchronized (publisherMap)
    {
      list = publisherMap.remove(id);
    }

    if(null != list)
    {
      System.out.println("INFO: Removing publisher: " + id);
    }

    return list;
  }

  public void registerNotifyListener(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list)
  {
    final String uri = details.endpoint.getURI().getValue();
    final String subId = subscription.getSubscriptionId().getValue();
    final StringPair id = new StringPair(uri, subId);

    synchronized (notifyMap)
    {
      //if (false == notifyMap.containsKey(id))
      {
        notifyMap.put(id, list);
        Map<String, MALInteractionListener> ent = errorMap.get(uri);

        if (null == ent)
        {
          ent = new TreeMap<String, MALInteractionListener>();
          errorMap.put(uri, ent);
        }

        ent.put(subId, list);
      }
    }
  }

  public MALInteractionListener getNotifyListener(URI uri, Identifier subscription)
  {
    final StringPair id = new StringPair(uri.getValue(), subscription.getValue());

    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        return notifyMap.get(id);
      }
    }

    return null;
  }

  public Map<String, MALInteractionListener> getNotifyListenersAndRemove(URI uriValue)
  {
    synchronized (notifyMap)
    {
      String uri = uriValue.getValue();
      Map<String, MALInteractionListener> ent = errorMap.get(uri);

      if (null != ent)
      {
        for (Map.Entry<String, MALInteractionListener> e : ent.entrySet())
        {
          notifyMap.remove(new StringPair(uri, e.getKey()));
        }
      }

      return ent;
    }
  }

  public void deregisterNotifyListener(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription)
  {
    synchronized (notifyMap)
    {
      final String uri = details.endpoint.getURI().getValue();

      for (int i = 0; i < unsubscription.size(); i++)
      {
        final String unsubId = unsubscription.get(i).getValue();
        final StringPair id = new StringPair(uri, unsubId);

        if (notifyMap.containsKey(id))
        {
          notifyMap.remove(id);

          Map<String, MALInteractionListener> ent = errorMap.get(uri);
          if (null != ent)
          {
            ent.remove(unsubId);

            if (ent.isEmpty())
            {
              errorMap.remove(uri);
            }
          }
        }
      }
    }
  }
}
