package org.ccsds.moims.mo.mal.impl.broker.caching;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.impl.broker.MALSubscriptionKey;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;

/**
 * A SubscriptionDetails is keyed on subscription Id
 */
class SubscriptionDetails
{
  private final ConsumerDetails parent;
  private final String subscriptionId;
  private final Set<MALSubscriptionKey> required = new TreeSet<MALSubscriptionKey>();
  private final Set<MALSubscriptionKey> onAll = new TreeSet<MALSubscriptionKey>();
  private final Set<MALSubscriptionKey> onChange = new TreeSet<MALSubscriptionKey>();
  private SubscriptionUpdate notifySubscriptionUpdate = null;

  public SubscriptionDetails(ConsumerDetails parent, String subscriptionId)
  {
    super();
    this.parent = parent;
    this.subscriptionId = subscriptionId;
  }

  public void report()
  {
    System.out.println("      START Subscription ( " + subscriptionId + " )");
    System.out.println("      Required: " + String.valueOf(required.size()));
    System.out.println("      END Subscription ( " + subscriptionId + " )");
  }

  public ConsumerDetails getParent()
  {
    return parent;
  }

  public boolean notActive()
  {
    return required.isEmpty();
  }

  public void setIds(Map<MALSubscriptionKey, PublishedEntry> published, EntityRequestList lst)
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
        MALSubscriptionKey key = new MALSubscriptionKey(id);

        required.add(key);

        if (bOnlyOnChange)
        {
          onChange.add(key);
        }
        else
        {
          onAll.add(key);
        }

        for (Map.Entry<MALSubscriptionKey, PublishedEntry> subKey : published.entrySet())
        {
          if (subKey.getKey().matches(key))
          {
            if (bOnlyOnChange)
            {
              subKey.getValue().onChange.add(this);
            }
            else
            {
              subKey.getValue().onAll.add(this);
            }
          }
        }
      }
    }
  }

  public void appendSubscription(PublishedEntry publishedEntry, MALSubscriptionKey key)
  {
    if (matchedUpdate(key, onChange))
    {
      publishedEntry.onChange.add(this);
    }
    else
    {
      if (matchedUpdate(key, onAll))
      {
        publishedEntry.onAll.add(this);
      }
    }
  }

  public void populateNotify(Update update)
  {
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

  public void removeSubscription(Map<MALSubscriptionKey, PublishedEntry> published)
  {
    for (PublishedEntry ent : published.values())
    {
      ent.onAll.remove(this);
      ent.onChange.remove(this);
    }
  }

  private static boolean matchedUpdate(MALSubscriptionKey key, java.util.Set<MALSubscriptionKey> searchSet)
  {
    boolean matched = false;

    for (MALSubscriptionKey subscriptionKey : searchSet)
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
