/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Test bed utilities
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
package org.ccsds.moims.mo.testbed.suite;

import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.broker.MALBrokerManager;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public abstract class BaseLocalMALInstance extends LoggingBase
{
  static private BaseLocalMALInstance instance = null;
  static private final Object instanceCondition = new Object();
  protected MALContextFactory malFactory = null;
  protected MALContext defaultMal = null;
  protected MALConsumerManager defaultConsumerMgr = null;
  protected MALProviderManager defaultProviderMgr = null;
  protected MALBrokerManager brokerManager = null;
  protected Properties defaultProps;

  public static BaseLocalMALInstance binstance() throws MALException
  {
    synchronized (instanceCondition)
    {
      if (null == instance)
      {
        String clsName = System.getProperty(Configuration.LOCAL_MAL_CLASS);
        try
        {
          Class cls = Class.forName(clsName);

          instance = (BaseLocalMALInstance) cls.newInstance();
        }
        catch (ClassNotFoundException ex)
        {
          logMessage("ERROR: Unable to locate class : " + clsName + " : " + ex.getLocalizedMessage());
        }
        catch (InstantiationException ex)
        {
          logMessage("ERROR: Unable to instantiate class : " + clsName + " : " + ex.getLocalizedMessage());
        }
        catch (IllegalAccessException ex)
        {
          logMessage("ERROR: Unable to instantiate class : " + clsName + " : " + ex.getLocalizedMessage());
        }

        instance.init();
      }
    }

    return instance;
  }

  protected BaseLocalMALInstance()
  {
    super();
  }

  public void init() throws MALException
  {
    try
    {
      logMessage("Started...");
      logMessage("Security manager system property set to: " + System.getProperty(Configuration.SECURITY_FACTORY_PROP_NAME));

      Properties envPrp = Configuration.getProperties("BaseLocalMALInstanceEnv.properties");
      envPrp.putAll(Configuration.getProperties(this.getClass().getSimpleName() + "Env.properties"));

      Properties overrideProps = new Properties();
      
      for (Map.Entry<Object, Object> entrySet : envPrp.entrySet())
      {
        String key = (String)entrySet.getKey();
        Object value = entrySet.getValue();
        
        if (null != System.getProperty(key))
        {
          String sval = System.getProperty(key);
          
          if (!sval.equals(value))
          {
            logMessage("System property overriding setting: " + key);
            overrideProps.setProperty(key, sval);
          }
        }
      }
      
      envPrp.putAll(overrideProps);
      overrideProps.store(new FileOutputStream("target/OverrideTestServiceProviderEnv.properties"), "");
      
      System.getProperties().putAll(envPrp);

      malFactory = MALContextFactory.newFactory();

      defaultProps = Configuration.getProperties("BaseLocalMALInstanceMAL.properties");
      defaultProps.putAll(Configuration.getProperties(this.getClass().getSimpleName() + "MAL.properties"));

      logMessage("Create MAL");
      defaultMal = malFactory.createMALContext(defaultProps);
      logMessage("MAL created");

      MALHelper.init(MALContextFactory.getElementFactoryRegistry());
      initHelpers();

      defaultConsumerMgr = defaultMal.createConsumerManager();
      
      defaultProviderMgr = defaultMal.createProviderManager();

      // Create the shared broker
      String protocol = getProtocol();
      logMessage("Broker protocol: " + protocol);

      brokerManager = defaultMal.createBrokerManager();
      createBrokers();

      logMessage("Local MAL created");
    }
    catch (MALException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    catch (Throwable ex)
    {
      ex.printStackTrace();
    }
  }

  public MALConsumerManager getConsumerManager()
  {
      return defaultConsumerMgr;
  }
  
  public MALProviderManager getProviderManager()
  {
      return defaultProviderMgr;
  }
  
  abstract protected String getProtocol();

  abstract protected void initHelpers() throws MALException;

  abstract protected void createBrokers() throws MALException;

  public synchronized void closeMAL() throws Exception
  {
    logMessage("Closing MAL");
    defaultMal.close();
    logMessage("Closed");
  }

  public static class StubKey
  {
    private Blob authenticationId;
    private IdentifierList domain;
    private Identifier networkZone;
    private SessionType sessionType;
    private Identifier sessionName;
    private QoSLevel qosLevel;
    private UInteger priority;
    private boolean shared;

    public StubKey(Blob authenticationId, IdentifierList domain,
            Identifier networkZone, SessionType sessionType,
            Identifier sessionName, QoSLevel qosLevel, UInteger priority, boolean shared)
    {
      super();
      this.authenticationId = authenticationId;
      this.domain = domain;
      this.networkZone = networkZone;
      this.sessionType = sessionType;
      this.sessionName = sessionName;
      this.qosLevel = qosLevel;
      this.priority = priority;
      this.shared = shared;
    }

    @Override
    public int hashCode()
    {
      return authenticationId.hashCode()
              + domain.hashCode()
              + networkZone.hashCode()
              + sessionType.hashCode()
              + sessionName.hashCode()
              + qosLevel.hashCode()
              + priority.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
      if (obj instanceof StubKey)
      {
        StubKey sk = (StubKey) obj;
        if (!sk.authenticationId.equals(authenticationId))
        {
          return false;
        }
        if (!sk.domain.equals(domain))
        {
          return false;
        }
        if (!sk.networkZone.equals(networkZone))
        {
          return false;
        }
        if (!sk.sessionType.equals(sessionType))
        {
          return false;
        }
        if (!sk.sessionName.equals(sessionName))
        {
          return false;
        }
        if (!sk.qosLevel.equals(qosLevel))
        {
          return false;
        }
        if (!sk.priority.equals(priority))
        {
          return false;
        }
        if (sk.shared != shared)
        {
          return false;
        }
        return true;
      }
      else
      {
        return false;
      }
    }
  }
}
