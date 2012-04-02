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
package org.ccsds.moims.mo.mal.impl.broker.simple;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.impl.NotifyMessage;
import org.ccsds.moims.mo.mal.impl.broker.SubscriptionKey;
import org.ccsds.moims.mo.mal.impl.broker.UpdateKey;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALPublishBody;

/**
 * A SimpleSubscriptionDetails is keyed on subscription Id
 */
class SimpleSubscriptionDetails
{
  private final String subscriptionId;
  private Set<SubscriptionKey> required = new TreeSet<SubscriptionKey>();
  private Set<SubscriptionKey> onAll = new TreeSet<SubscriptionKey>();
  private Set<SubscriptionKey> onChange = new TreeSet<SubscriptionKey>();

  SimpleSubscriptionDetails(MALMessageHeader srcHdr, String subscriptionId)
  {
    this.subscriptionId = subscriptionId;
  }

  void report()
  {
    Logging.logMessage("    START Subscription ( " + subscriptionId + " )");
    Logging.logMessage("     Required: " + String.valueOf(required.size()));
    for (SubscriptionKey key : required)
    {
      Logging.logMessage("            : Rqd : " + key);
    }
    for (SubscriptionKey key : onAll)
    {
      Logging.logMessage("            : All : " + key);
    }
    for (SubscriptionKey key : onChange)
    {
      Logging.logMessage("            : Chg : " + key);
    }
    Logging.logMessage("    END Subscription ( " + subscriptionId + " )");
  }

  boolean notActive()
  {
    return required.isEmpty();
  }

  void setIds(MALMessageHeader srcHdr, EntityRequestList lst)
  {
    required.clear();
    onAll.clear();
    onChange.clear();
    for (int idx = 0; idx < lst.size(); idx++)
    {
      EntityRequest rqst = (EntityRequest) lst.get(idx);
      EntityKeyList keyList = rqst.getEntityKeys();
      boolean bOnChange = rqst.getOnlyOnChange();
      for (int i = 0; i < keyList.size(); i++)
      {
        EntityKey id = (EntityKey) keyList.get(i);
        SubscriptionKey key = new SubscriptionKey(srcHdr, rqst, id);
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

  NotifyMessage populateNotifyList(MALMessageHeader srcHdr, String srcDomainId, UpdateHeaderList updateHeaderList, MALPublishBody publishBody) throws MALException
  {
    Logging.logMessage("INFO: Checking SimSubDetails");

    UpdateHeaderList notifyHeaders = new UpdateHeaderList();

    List[] updateLists = publishBody.getUpdateLists((List[]) null);
    Object[] notifyLists = new Object[updateLists.length+2];
    for (int i = 2; i < notifyLists.length; i++)
    {
      notifyLists[i] = (List)((Element)updateLists[i-2]).createElement();
    }
    for (int i = 0; i < updateHeaderList.size(); ++i)
    {
      populateNotifyList(srcHdr, srcDomainId, updateHeaderList.get(i), updateLists, i, notifyHeaders, notifyLists);
    }

    NotifyMessage retVal = null;
    if (!notifyHeaders.isEmpty())
    {
      retVal = new NotifyMessage();
      notifyLists[0] = new Identifier(subscriptionId);
      notifyLists[1] = notifyHeaders;
      retVal.updates = notifyLists;

      //retVal.header.setPriority(priority);
      //retVal.header.setQoSlevel(qos);
      //retVal.header.setTransactionId(transactionId);
    }

    return retVal;
  }

  private void populateNotifyList(MALMessageHeader srcHdr, String srcDomainId, UpdateHeader updateHeader, List[] updateLists, int index, UpdateHeaderList notifyHeaders, Object[] notifyLists) throws MALException
  {
    UpdateKey key = new UpdateKey(srcHdr, srcDomainId, updateHeader.getKey());
    Logging.logMessage("INFO: Checking " + key);
    boolean updateRequired = matchedUpdate(key, onAll);

    if (!updateRequired && (updateHeader.getUpdateType().getOrdinal() != UpdateType._UPDATE_INDEX))
    {
      updateRequired = matchedUpdate(key, onChange);
    }

    if (updateRequired)
    {
      // add update for this consumer/subscription
      notifyHeaders.add(updateHeader);
      
      for (int i = 2; i < notifyLists.length; i++)
      {
        ((List)notifyLists[i]).add(updateLists[i-2].get(index));
      }
    }
  }

  private static boolean matchedUpdate(UpdateKey key, Set<SubscriptionKey> searchSet)
  {
    boolean matched = false;
    for (SubscriptionKey subscriptionKey : searchSet)
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

  void appendIds(Set<SubscriptionKey> subSet)
  {
    subSet.addAll(required);
  }
}
