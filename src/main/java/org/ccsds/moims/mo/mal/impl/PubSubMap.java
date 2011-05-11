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
package org.ccsds.moims.mo.mal.impl;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 */
class PubSubMap
{
  private final java.util.Map<StringPair, MALPublishInteractionListener> publisherMap
          = new java.util.TreeMap<StringPair, MALPublishInteractionListener>();
  private final java.util.Map<String, Map<String, MALInteractionListener>> errorMap
          = new java.util.TreeMap<String, Map<String, MALInteractionListener>>();
  private final java.util.Map<StringPair, MALInteractionListener> notifyMap
          = new java.util.TreeMap<StringPair, MALInteractionListener>();

  PubSubMap()
  {
  }

  void registerPublishListener(MessageDetails details, MALPublishInteractionListener listener)
  {
    final StringPair id = new StringPair(details.uriFrom.getValue(), details.sessionName.getValue());

    synchronized (publisherMap)
    {
      publisherMap.put(id, listener);

      Logging.logMessage("INFO: Adding publisher: " + id);
    }
  }

  MALPublishInteractionListener getPublishListener(URI uri, Identifier sessionName)
  {
    final StringPair id = new StringPair(uri.getValue(), sessionName.getValue());
    MALPublishInteractionListener list = null;

    synchronized (publisherMap)
    {
      list = publisherMap.get(id);
    }

    if (null != list)
    {
      Logging.logMessage("INFO: Getting publisher: " + id);
    }

    return list;
  }

  MALPublishInteractionListener getPublishListenerAndRemove(URI uri, Identifier sessionName)
  {
    final StringPair id = new StringPair(uri.getValue(), sessionName.getValue());
    MALPublishInteractionListener list = null;

    synchronized (publisherMap)
    {
      list = publisherMap.remove(id);
    }

    if (null != list)
    {
      Logging.logMessage("INFO: Removing publisher: " + id);
    }

    return list;
  }

  void registerNotifyListener(MessageDetails details,
          MALPubSubOperation op,
          Subscription subscription,
          MALInteractionListener list)
  {
    final String uri = details.endpoint.getURI().getValue();
    final String subId = subscription.getSubscriptionId().getValue();
    final StringPair id = new StringPair(uri, subId);

    synchronized (notifyMap)
    {
      notifyMap.put(id, list);
      Map<String, MALInteractionListener> ent = errorMap.get(uri);

      if (null == ent)
      {
        ent = new TreeMap<String, MALInteractionListener>();
        errorMap.put(uri, ent);
      }

      Logging.logMessage("INFO: PubSubMap(" + this + "), adding notify handler: " + uri + " : " + subId + " : " + list);
      ent.put(subId, list);
    }
  }

  MALInteractionListener getNotifyListener(URI uri, Identifier subscription)
  {
    final StringPair id = new StringPair(uri.getValue(), subscription.getValue());

    Logging.logMessage("INFO: PubSubMap(" + this + "), looking for notify handler: " + uri + " : " + subscription);
    synchronized (notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        Logging.logMessage("INFO: PubSubMap(" + this + "), found notify handler: " + uri + " : " + subscription);
        return notifyMap.get(id);
      }
    }

    Logging.logMessage("INFO: PubSubMap(" + this + "), failed to find notify handler: " + uri + " : " + subscription);
    return null;
  }

  Map<String, MALInteractionListener> getNotifyListenersAndRemove(URI uriValue)
  {
    synchronized (notifyMap)
    {
      String uri = uriValue.getValue();
      Map<String, MALInteractionListener> ent = errorMap.get(uri);

      if (null != ent)
      {
        for (Map.Entry<String, MALInteractionListener> e : ent.entrySet())
        {
          Logging.logMessage("INFO: PubSubMap(" + this + "), removing notify handler: " + uri + " : *");
          notifyMap.remove(new StringPair(uri, e.getKey()));
        }
      }

      return ent;
    }
  }

  void deregisterNotifyListener(MessageDetails details, MALPubSubOperation op, IdentifierList unsubscription)
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
          Logging.logMessage("INFO: PubSubMap(" + this + "), removing notify handler: " + uri + " : " + unsubId);
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
