/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl.broker.simple;

import esa.mo.mal.impl.broker.MALBrokerImpl;
import esa.mo.mal.impl.broker.NotifyMessageSet.NotifyMessage;
import esa.mo.mal.impl.broker.key.SubscriptionKey;
import esa.mo.mal.impl.broker.key.UpdateKey;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.mal.transport.MALEncodedElementList;
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

  SimpleSubscriptionDetails(final String subscriptionId)
  {
    this.subscriptionId = subscriptionId;
  }

  void report()
  {
    MALBrokerImpl.LOGGER.log(Level.FINE, "    START Subscription ( {0} )", subscriptionId);
    MALBrokerImpl.LOGGER.log(Level.FINE, "     Required: {0}", required.size());
    for (SubscriptionKey key : required)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "            : Rqd : {0}", key);
    }
    for (SubscriptionKey key : onAll)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "            : All : {0}", key);
    }
    for (SubscriptionKey key : onChange)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "            : Chg : {0}", key);
    }
    MALBrokerImpl.LOGGER.log(Level.FINE, "    END Subscription ( {0} )", subscriptionId);
  }

  void setIds(final MALMessageHeader srcHdr, final EntityRequestList lst)
  {
    required.clear();
    onAll.clear();
    onChange.clear();
    for (EntityRequest rqst : lst)
    {
      final EntityKeyList keyList = rqst.getEntityKeys();
      final boolean bOnChange = rqst.getOnlyOnChange();

      for (EntityKey id : keyList)
      {
        final SubscriptionKey key = new SubscriptionKey(srcHdr, rqst, id);
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

  NotifyMessage populateNotifyList(final MALMessageHeader srcHdr,
          final String srcDomainId,
          final UpdateHeaderList updateHeaderList,
          final MALPublishBody publishBody) throws MALException
  {
    MALBrokerImpl.LOGGER.fine("Checking SimSubDetails");

    final UpdateHeaderList notifyHeaders = new UpdateHeaderList();

    final List[] updateLists = publishBody.getUpdateLists((List[]) null);
    final List[] notifyLists;

    // have to check for the case where the pubsub message does not contain a body
    if (null == updateLists)
    {
      notifyLists = null;
    }
    else
    {
      notifyLists = new List[updateLists.length];

      for (int i = 0; i < notifyLists.length; i++)
      {
        if (null != updateLists[i])
        {
          if (updateLists[i] instanceof MALEncodedElementList)
          {
            MALEncodedElementList encodedElementList = (MALEncodedElementList) updateLists[i];
            notifyLists[i] = new MALEncodedElementList(encodedElementList.getShortForm(), encodedElementList.size());
          }
          else
          {
            notifyLists[i] = (List) ((Element) updateLists[i]).createElement();
          }
        }
        else
        {
          // publishing an empty list
          notifyLists[i] = null;
        }
      }
    }

    for (int i = 0; i < updateHeaderList.size(); ++i)
    {
      populateNotifyList(srcHdr, srcDomainId, updateHeaderList.get(i), updateLists, i, notifyHeaders, notifyLists);
    }

    NotifyMessage retVal = null;
    if (!notifyHeaders.isEmpty())
    {
      retVal = new NotifyMessage();
      retVal.subscriptionId = new Identifier(subscriptionId);
      retVal.updateHeaderList = notifyHeaders;
      retVal.updateList = notifyLists;
    }

    return retVal;
  }

  private void populateNotifyList(final MALMessageHeader srcHdr,
          final String srcDomainId,
          final UpdateHeader updateHeader,
          final List[] updateLists,
          final int index,
          final UpdateHeaderList notifyHeaders,
          final List[] notifyLists) throws MALException
  {
    final UpdateKey key = new UpdateKey(srcHdr, srcDomainId, updateHeader.getKey());
    MALBrokerImpl.LOGGER.log(Level.FINE, "Checking {0}", key);
    boolean updateRequired = matchedUpdate(key, onAll);

    if (!updateRequired && (updateHeader.getUpdateType().getOrdinal() != UpdateType._UPDATE_INDEX))
    {
      updateRequired = matchedUpdate(key, onChange);
    }

    if (updateRequired)
    {
      // add update for this consumer/subscription
      notifyHeaders.add(updateHeader);

      if (null != notifyLists)
      {
        for (int i = 0; i < notifyLists.length; i++)
        {
          if ((null != notifyLists[i]) && (null != updateLists[i]))
          {
            notifyLists[i].add(updateLists[i].get(index));
          }
        }
      }
    }
  }

  private static boolean matchedUpdate(final UpdateKey key, final Set<SubscriptionKey> searchSet)
  {
    boolean matched = false;
    for (SubscriptionKey subscriptionKey : searchSet)
    {
      MALBrokerImpl.LOGGER.log(Level.FINE, "Checking {0} against {1}", new Object[]
      {
        key, subscriptionKey
      });
      if (subscriptionKey.matchesWithWildcard(key))
      {
        MALBrokerImpl.LOGGER.fine("    : Matched");
        matched = true;
        break;
      }
      MALBrokerImpl.LOGGER.fine("    : No match");
    }
    return matched;
  }

  void appendIds(final Set<SubscriptionKey> subSet)
  {
    subSet.addAll(required);
  }
}
