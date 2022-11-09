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

import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.Executable;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 *
 */
public abstract class BaseTestServiceProvider extends LoggingBase implements Executable
{
  protected MALContextFactory malFactory = null;
  protected MALContext defaultMal = null;
  protected MALProviderManager defaultProviderMgr = null;

  public void execute(Writer out, ExitCondition exitCond, String[] argv) throws Exception
  {
    try
    {
      logMessage("execute() operation entered!");
      Properties envPrp = Configuration.getProperties("BaseTestServiceProviderEnv.properties");
      envPrp.putAll(Configuration.getProperties(this.getClass().getSimpleName() + "Env.properties"));
      envPrp.putAll(Configuration.getProperties("target/OverrideTestServiceProviderEnv.properties", true));

      logMessage("Protocol: env props: " + envPrp);
      System.getProperties().putAll(envPrp);
      
      malFactory = MALContextFactory.newFactory();
      Properties malProps = Configuration.getProperties("BaseTestServiceProviderMAL.properties");
      malProps.putAll(Configuration.getProperties(this.getClass().getSimpleName() + "MAL.properties"));

      defaultMal = malFactory.createMALContext(malProps);

      MALHelper.init(MALContextFactory.getElementFactoryRegistry());
      initHelpers();

      defaultProviderMgr = defaultMal.createProviderManager();

      String protocol = getProtocol();
      logMessage("Protocol: " + protocol);

      logMessage("Creating providers...");

      createProviders();

      logMessage("Ready");

      exitCond.waitForExitSignal();
    }
    catch (MALException exc)
    {
      logMessage("Error: " + exc);
      exc.printStackTrace();
    }
  }

  public MALContext getDefaultMal()
  {
    return defaultMal;
  }

  public MALProviderManager getDefaultProviderMgr()
  {
    return defaultProviderMgr;
  }

  public MALContextFactory getMalFactory()
  {
    return malFactory;
  }

  abstract protected String getProtocol();

  abstract protected void initHelpers() throws MALException;

  abstract protected void createProviders() throws MALException;
}
