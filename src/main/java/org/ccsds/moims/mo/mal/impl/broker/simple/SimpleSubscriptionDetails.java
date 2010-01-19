package org.ccsds.moims.mo.mal.impl.broker.simple;

import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.impl.broker.MALBrokerMessage;
import org.ccsds.moims.mo.mal.impl.broker.MALSubscriptionKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.EntityRequest;
import org.ccsds.moims.mo.mal.structures.EntityRequestList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.MessageHeader;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SubscriptionUpdate;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;
import org.ccsds.moims.mo.mal.structures.UpdateType;

/**
 * A SimpleSubscriptionDetails is keyed on subscription Id
 */
class SimpleSubscriptionDetails
{
  private final String subscriptionId;
  private final Identifier transactionId;
  private final QoSLevel qos;
  private final Integer priority;
  private Set<MALSubscriptionKey> required = new TreeSet<MALSubscriptionKey>();
  private Set<MALSubscriptionKey> onAll = new TreeSet<MALSubscriptionKey>();
  private Set<MALSubscriptionKey> onChange = new TreeSet<MALSubscriptionKey>();

  public SimpleSubscriptionDetails(MessageHeader srcHdr, String subscriptionId)
  {
    this.subscriptionId = subscriptionId;
    this.transactionId = srcHdr.getTransactionId();
    this.qos = srcHdr.getQoSlevel();
    this.priority = srcHdr.getPriority();
  }

  public void report()
  {
    Logging.logMessage("      START Subscription ( " + subscriptionId + " )");
    Logging.logMessage("      Required: " + String.valueOf(required.size()));
    for (MALSubscriptionKey key : required)
    {
      Logging.logMessage("              : Rqd : " + key);
    }
    for (MALSubscriptionKey key : onAll)
    {
      Logging.logMessage("              : All : " + key);
    }
    for (MALSubscriptionKey key : onChange)
    {
      Logging.logMessage("              : Chg : " + key);
    }
    Logging.logMessage("      END Subscription ( " + subscriptionId + " )");
  }

  public boolean notActive()
  {
    return required.isEmpty();
  }

  public void setIds(EntityRequestList lst)
  {
    required.clear();
    onAll.clear();
    onChange.clear();
    for (int idx = 0; idx < lst.size(); idx++)
    {
      EntityRequest rqst = (EntityRequest) lst.get(idx);
      EntityKeyList keyList = rqst.getEntityKeys();
      boolean bOnChange = rqst.isOnlyOnChange();
      for (int i = 0; i < keyList.size(); i++)
      {
        EntityKey id = (EntityKey) keyList.get(i);
        MALSubscriptionKey key = new MALSubscriptionKey(id);
        required.add(key);
        if (bOnChange)
        {
          onChange.add(key);
        }
        else
        {
          onAll.add(key);
        }
      }
    }
  }

  public MALBrokerMessage.NotifyMessage populateNotifyList(UpdateList updateList)
  {
    Logging.logMessage("INFO: Checking SimSubDetails");
    UpdateList sendList = new UpdateList();
    for (int i = 0; i < updateList.size(); ++i)
    {
      Update update = (Update) updateList.get(i);
      populateNotifyList(sendList, update);
    }
    MALBrokerMessage.NotifyMessage retVal = null;
    if (false == sendList.isEmpty())
    {
      retVal = new MALBrokerMessage.NotifyMessage();
      SubscriptionUpdate update = new SubscriptionUpdate();
      update.setSubscriptionId(new Identifier(subscriptionId));
      update.setUpdateList(sendList);
      retVal.updates.add(update);

      retVal.header.setPriority(priority);
      retVal.header.setQoSlevel(qos);
      retVal.header.setTransactionId(transactionId);
    }
    return retVal;
  }

  private void populateNotifyList(UpdateList lst, Update update)
  {
    MALSubscriptionKey key = new MALSubscriptionKey(update.getKey());
    Logging.logMessage("INFO: Checking " + key);
    boolean updateRequired = matchedUpdate(key, onAll);
    if (!updateRequired && (update.getUpdateType().getOrdinal() != UpdateType._UPDATE_INDEX))
    {
      updateRequired = matchedUpdate(key, onChange);
    }
    if (updateRequired)
    {
      // add update for this consumer/subscription
      lst.add(update);
    }
  }

  private static boolean matchedUpdate(MALSubscriptionKey key, Set<MALSubscriptionKey> searchSet)
  {
    boolean matched = false;
    for (MALSubscriptionKey subscriptionKey : searchSet)
    {
      Logging.logMessage("INFO: Checking " + key + " against " + subscriptionKey);
      if (subscriptionKey.matches(key))
      {
        Logging.logMessage("    : Matched");
        matched = true;
        break;
      }
      Logging.logMessage("    : No match");
    }
    return matched;
  }

  protected void appendIds(Set<MALSubscriptionKey> new_set)
  {
    new_set.addAll(required);
  }
}
