package org.ccsds.moims.smc.mal.impl.broker.caching;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.smc.mal.api.structures.MALEntityKeyList;
import org.ccsds.moims.smc.mal.api.structures.MALEntityRequest;
import org.ccsds.moims.smc.mal.api.structures.MALEntityRequestList;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALSubscriptionUpdate;
import org.ccsds.moims.smc.mal.api.structures.MALUpdate;
import org.ccsds.moims.smc.mal.api.structures.MALUpdateList;
import org.ccsds.moims.smc.mal.impl.broker.MALSubscriptionKey;

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
  private MALSubscriptionUpdate notifySubscriptionUpdate = null;

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

  public void setIds(Map<MALSubscriptionKey, PublishedEntry> published, MALEntityRequestList lst)
  {
    required.clear();
    onAll.clear();
    onChange.clear();

    int count = lst.size();

    for (int idx = 0; idx < count; idx++)
    {
      MALEntityRequest rqst = (MALEntityRequest) lst.get(idx);
      MALEntityKeyList keyList = rqst.getEntityKeys();
      boolean bOnlyOnChange = rqst.getOnlyOnChange().getBooleanValue();

      for (int i = 0; i < keyList.size(); i++)
      {
        MALIdentifierList id = (MALIdentifierList) keyList.get(i);
        MALSubscriptionKey key = new MALSubscriptionKey(id);

        required.add(key);

        for (Map.Entry<MALSubscriptionKey, PublishedEntry> subKey : published.entrySet())
        {
          if (subKey.getKey().matches(key))
          {
            if (bOnlyOnChange)
            {
              subKey.getValue().onChange.add(this);
              onChange.add(key);
            }
            else
            {
              subKey.getValue().onAll.add(this);
              onAll.add(key);
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

  public void populateNotify(MALUpdate update)
  {
    if (null == notifySubscriptionUpdate)
    {
      notifySubscriptionUpdate = new MALSubscriptionUpdate(new MALIdentifier(subscriptionId), new MALUpdateList());
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
