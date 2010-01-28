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
package org.ccsds.moims.mo.mal.impl.broker;

import java.util.Set;
import java.util.TreeSet;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.impl.util.Logging;
import org.ccsds.moims.mo.mal.structures.EntityKeyList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.StandardError;
import org.ccsds.moims.mo.mal.structures.Update;
import org.ccsds.moims.mo.mal.structures.UpdateList;

final class ProviderDetails
{
  private final String uri;
  private final QoSLevel qosLevel;
  private final Set<SubscriptionKey> keySet = new TreeSet<SubscriptionKey>();

  ProviderDetails(String uri, QoSLevel qosLevel)
  {
    super();
    this.uri = uri;
    this.qosLevel = qosLevel;
  }

  QoSLevel getQosLevel()
  {
    return qosLevel;
  }

  void report()
  {
    Logging.logMessage("  START Provider ( " + uri + " )");
    for (SubscriptionKey key : keySet)
    {
      Logging.logMessage("  Allowed: " + key);
    }
    Logging.logMessage("  END Provider ( " + uri + " )");
  }

  void setKeyList(EntityKeyList l)
  {
    keySet.clear();
    for (int i = 0; i < l.size(); i++)
    {
      keySet.add(new SubscriptionKey(l.get(i)));
    }
  }

  void checkPublish(UpdateList updateList) throws MALException
  {
    EntityKeyList lst = new EntityKeyList();
    for (int i = 0; i < updateList.size(); i++)
    {
      Update update = (Update) updateList.get(i);
      SubscriptionKey publishKey = new SubscriptionKey(update.getKey());
      boolean matched = false;
      for (SubscriptionKey key : keySet)
      {
        if (key.matches(publishKey))
        {
          matched = true;
          break;
        }
      }
      if (!matched)
      {
        lst.add(update.getKey());
      }
    }
    if (0 < lst.size())
    {
      throw new MALException(new StandardError(MALHelper.UNKNOWN_ERROR_NUMBER, lst));
    }
  }
}
