package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.consumer.MALInteractionListener;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Pair;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALMessage;



/**
 * @version 1.0
 * @created 17-Aug-2006 10:24:12
 */
public class MALServiceMaps
{
  private volatile int transId = 0;
  private final java.util.Map<String, BooleanLock> transMap = new java.util.TreeMap<String, BooleanLock>();
  private final java.util.Map<String, Pair> resolveMap = new java.util.TreeMap<String, Pair>();
  private final java.util.Map<String, MALMessage> resultMap = new java.util.TreeMap<String, MALMessage>();
  private final java.util.Map<String, MALInteractionListener> notifyMap = new java.util.TreeMap<String, MALInteractionListener>();
  
  public MALServiceMaps()
  {
    
  }

  public void registerNotifyListener(MALMessageDetails details, MALPubSubOperation op, Subscription subscription, MALInteractionListener list)
  {
    //TODO: Not correct currently as register can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized(notifyMap)
    {
      if (false == notifyMap.containsKey(id))
      {
        notifyMap.put(id, list);
      }
    }
  }

  public MALInteractionListener getNotifyListener(URI uri)
  {
    final String id = uri.getValue();

    synchronized(notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        return notifyMap.get(id);
      }
    }

    return null;
  }

  public void deregisterNotifyListener(MALMessageDetails details, MALPubSubOperation op, IdentifierList unsubscription)
  {
    //TODO: Not correct currently as deregister can be called multiple times legally by the same consumer to modify their subscription
    final String id = details.endpoint.getURI().getValue();

    synchronized(notifyMap)
    {
      if (notifyMap.containsKey(id))
      {
        notifyMap.remove(id);
      }
    }
  }

  public Identifier getTransactionId()
  {
    final Identifier oTransId = new Identifier(Integer.toString(transId++));
    synchronized(transMap)
    {
      BooleanLock lock = new BooleanLock();
      transMap.put(oTransId.getValue(), lock);
    }
    return oTransId;
  }
  
  public MALMessage waitForResponse(Identifier _transId)
  {
    BooleanLock lock = null;
    final String id = _transId.getValue();
    
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
      while (false == lock.getLock())
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
    final String id = oRtn.getHeader().getTransactionId().getValue();
    BooleanLock lock = null;
    
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
        lock.setLock();
        lock.notifyAll();
      }
    }
    else
    {
      System.out.println("Alarm bells!! lock is null");
    }
  }
  
  public Identifier addTransactionSource(URI urlFrom, Identifier transactionId)
  {
    final Identifier oTransId = new Identifier(Integer.toString(transId++));
    
    synchronized(resolveMap)
    {
      resolveMap.put(oTransId.getValue(), new Pair(urlFrom, transactionId));
    }
    
    return oTransId;
  }
  
  public Pair resolveTransactionSource(Identifier transactionId)
  {
    synchronized(resolveMap)
    {
      if (resolveMap.containsKey(transactionId.getValue()))
      {
        return resolveMap.get(transactionId.getValue());
      }
    }
    
    return null;
  }

  private static final class BooleanLock
  {
    private boolean lock = false;

    public synchronized boolean getLock()
    {
      return lock;
    }

    public synchronized void setLock()
    {
      lock = true;
    }
  }
}