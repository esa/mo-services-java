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
package org.ccsds.moims.mo.mal.impl.broker.caching;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * A CachingSubscriptionDetails is keyed on subscription Id
 */
class CachingSubscriptionDetails
{
  private final CachingConsumerDetails parent;
  private final String subscriptionId;
  private final Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
  private final Set<SubscriptionKey> onAll = new TreeSet<SubscriptionKey>();
  private final Set<SubscriptionKey> onChange = new TreeSet<SubscriptionKey>();
  private SubscriptionUpdate notifySubscriptionUpdate = null;

  public CachingSubscriptionDetails(CachingConsumerDetails parent, String subscriptionId)
  {
    super();
    this.parent = parent;
    this.subscriptionId = subscriptionId;
  }

  public String getSubscriptionId()
  {
    return subscriptionId;
  }

  public void report()
  {
    Logging.logMessage("      START Subscription ( " + subscriptionId + " )");
    Logging.logMessage("      Required: " + String.valueOf(required.size()));
    for (SubscriptionKey key : required)
    {
      Logging.logMessage("              : Rqd : " + key);
    }
    for (SubscriptionKey key : onAll)
    {
      Logging.logMessage("              : All : " + key);
    }
    for (SubscriptionKey key : onChange)
    {
      Logging.logMessage("              : Chg : " + key);
    }
    Logging.logMessage("      END Subscription ( " + subscriptionId + " )");
  }

  public CachingConsumerDetails getParent()
  {
    return parent;
  }

  public boolean notActive()
  {
    return required.isEmpty();
  }

  public void setIds(Map<SubscriptionKey, PublishedEntry> published, EntityRequestList lst)
  {
    required.clear();
    onAll.clear();
    onChange.clear();

    int count = lst.size();

    for (int idx = 0; idx < count; idx++)
    {
      EntityRequest rqst = (EntityRequest) lst.get(idx);
      EntityKeyList keyList = rqst.getEntityKeys();
      boolean bOnlyOnChange = rqst.isOnlyOnChange();

      for (int i = 0; i < keyList.size(); i++)
      {
        EntityKey id = (EntityKey) keyList.get(i);
        SubscriptionKey key = new SubscriptionKey(id);

        required.add(key);

        if (bOnlyOnChange)
        {
          onChange.add(key);
        }
        else
        {
          onAll.add(key);
          onChange.add(key);
        }

        for (Map.Entry<SubscriptionKey, PublishedEntry> subKey : published.entrySet())
        {
          if (subKey.getKey().matches(key))
          {
            if (bOnlyOnChange)
            {
              subKey.getValue().addToOnChange(parent.getConsumerId(), subscriptionId, this);
            }
            else
            {
              subKey.getValue().addToOnAll(parent.getConsumerId(), subscriptionId, this);
              subKey.getValue().addToOnChange(parent.getConsumerId(), subscriptionId, this);
            }
          }
        }
      }
    }
  }

  public void appendSubscription(PublishedEntry publishedEntry, SubscriptionKey key)
  {
    if (matchedUpdate(key, onChange))
    {
      publishedEntry.addToOnChange(parent.getConsumerId(), subscriptionId, this);
    }
    else
    {
      if (matchedUpdate(key, onAll))
      {
        publishedEntry.addToOnAll(parent.getConsumerId(), subscriptionId, this);
        publishedEntry.addToOnChange(parent.getConsumerId(), subscriptionId, this);
      }
    }
  }

  public void populateNotify(Update update)
  {
    Logging.logMessage("INFO: Checking CacheSubDetails");

    if (null == notifySubscriptionUpdate)
    {
      notifySubscriptionUpdate = new SubscriptionUpdate(new Identifier(subscriptionId), new UpdateList());
      parent.populateNotifyList(notifySubscriptionUpdate);
    }

    notifySubscriptionUpdate.getUpdateList().add(update);
  }

  public void clearNotify()
  {
    notifySubscriptionUpdate = null;
  }

  public void removeSubscription(Map<SubscriptionKey, PublishedEntry> published)
  {
    for (PublishedEntry ent : published.values())
    {
      ent.remove(parent.getConsumerId(), this.subscriptionId);
    }
  }

  private static boolean matchedUpdate(SubscriptionKey key, java.util.Set<SubscriptionKey> searchSet)
  {
    boolean matched = false;

    for (SubscriptionKey subscriptionKey : searchSet)
    {
      if (subscriptionKey.matches(key))
      {
        matched = true;
        break;
      }
    }

    return matched;
  }
}
