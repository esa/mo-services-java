package org.ccsds.moims.smc.mal.impl;

import org.ccsds.moims.smc.mal.api.MALPubSubOperation;
import org.ccsds.moims.smc.mal.api.consumer.MALInteractionListener;
import org.ccsds.moims.smc.mal.api.structures.MALBoolean;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;
import org.ccsds.moims.smc.mal.api.structures.MALPair;
import org.ccsds.moims.smc.mal.api.structures.MALSubscription;
import org.ccsds.moims.smc.mal.api.structures.MALURI;
import org.ccsds.moims.smc.mal.api.transport.MALMessage;



/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceMaps
{
  private volatile int transId = 0;
  private final java.util.Map<String, MALBoolean> transMap = new java.util.TreeMap<String, MALBoolean>();
  private final java.util.Map<String, MALPair> resolveMap = new java.util.TreeMap<String, MALPair>();
  private final java.util.Map<String, MALMessage> resultMap = new java.util.TreeMap<String, MALMessage>();
  private final java.util.Map<String, MALInteractionListener> notifyMap = new java.util.TreeMap<String, MALInteractionListener>();
  
  public MALServiceMaps()
  {
    
  }

  public void registerNotifyListener(MALMessageDetails details, MALPubSubOperation op, MALSubscription subscription, MALInteractionListener list)
  {
    //TODO: Not correct currently as register can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getURIValue();

    synchronized(notifyMap)
    {
      if (false == notifyMap.containsKey(id))
      {
        notifyMap.put(id, list);
      }
    }
  }

  public MALInteractionListener getNotifyListener(MALURI uri)
  {
    final String id = uri.getURIValue();

    synchronized(notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        return notifyMap.get(id);
      }
    }

    return null;
  }

  public void deregisterNotifyListener(MALMessageDetails details, MALPubSubOperation op, MALIdentifierList unsubscription)
  {
    //TODO: Not correct currently as deregister can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getURIValue();

    synchronized(notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        notifyMap.remove(id);
      }
    }
  }

  public MALIdentifier getTransactionId()
  {
    final MALIdentifier oTransId = new MALIdentifier(Integer.toString(transId++));
    synchronized(transMap)
    {
      MALBoolean lock = new MALBoolean(false);
      transMap.put(oTransId.getIdentifierValue(), lock);
    }
    return oTransId;
  }
  
  public MALMessage waitForResponse(MALIdentifier _transId)
  {
    MALBoolean lock = null;
    final String id = _transId.getIdentifierValue();
    
    synchronized(transMap)
    {
      if (transMap.containsKey(id))
      {
        lock = transMap.get(id);
      }
      else
      {
        System.out.println("Alarm bells!!! no key available " + id);
      }
    }
    
    // do the wait
    synchronized(lock)
    {
      while (false == lock.getBooleanValue())
      {
        try
        {
          lock.wait();
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }
      }
    }
    
    // must have value now
    final MALMessage retVal = resultMap.get(id);
    
    // delete entry from trans map
    synchronized(transMap)
    {
      transMap.remove(id);
      resultMap.remove(id);
    }
    
    return retVal;
  }
  
  public void signalResponse(MALMessage oRtn)
  {
    final String id = oRtn.getHeader().getTransactionId().getIdentifierValue();
    MALBoolean lock = null;
    
    synchronized(transMap)
    {
      if (transMap.containsKey(id))
      {
        lock = transMap.get(id);
        resultMap.put(id, oRtn);
      }
      else
      {
        System.out.println("Alarm bells!!! no key available " + id);
      }
    }
    
    if (null != lock)
    {
      // do the wait
      synchronized(lock)
      {
        lock.setBooleanValue(true);
        lock.notifyAll();
      }
    }
    else
    {
      System.out.println("Alarm bells!! lock is null");
    }
  }
  
  public MALIdentifier addTransactionSource(MALURI urlFrom, MALIdentifier transactionId)
  {
    final MALIdentifier oTransId = new MALIdentifier(Integer.toString(transId++));
    
    synchronized(resolveMap)
    {
      resolveMap.put(oTransId.getIdentifierValue(), new MALPair(urlFrom, transactionId));
    }
    
    return oTransId;
  }
  
  public MALPair resolveTransactionSource(MALIdentifier transactionId)
  {
    synchronized(resolveMap)
    {
      if (resolveMap.containsKey(transactionId.getIdentifierValue()))
      {
        return resolveMap.get(transactionId.getIdentifierValue());
      }
    }
    
    return null;
  }
}