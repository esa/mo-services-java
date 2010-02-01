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

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.mal.impl.util.Logging;

final class PublishedEntry
{
  private final Map<String, CachingSubscriptionDetails> onAll = new TreeMap<String, CachingSubscriptionDetails>();
  private final Map<String, CachingSubscriptionDetails> onChange = new TreeMap<String, CachingSubscriptionDetails>();

  public void report()
  {
    for (CachingSubscriptionDetails key : onAll.values())
    {
      Logging.logMessage("           : All : " + key.getSubscriptionId());
    }
    for (CachingSubscriptionDetails key : onChange.values())
    {
      Logging.logMessage("           : Chg : " + key.getSubscriptionId());
    }
  }
  
  public void addToOnAll(String consumerId, String subId, CachingSubscriptionDetails details)
  {
    onAll.put(makeKey(consumerId, subId), details);
  }
  
  public void addToOnChange(String consumerId, String subId, CachingSubscriptionDetails details)
  {
    onChange.put(makeKey(consumerId, subId), details);
  }
  
  public Collection<CachingSubscriptionDetails> getDetailSet(boolean change)
  {
    if (change)
    {
      return onChange.values();
    }

    return onAll.values();
  }
  
  public void remove(String consumerId, String subId)
  {
    String key = makeKey(consumerId, subId);
    onAll.remove(key);
    onChange.remove(key);
  }
  
  private String makeKey(String consumerId, String subId)
  {
    return consumerId + "::" + subId;
  }
}
