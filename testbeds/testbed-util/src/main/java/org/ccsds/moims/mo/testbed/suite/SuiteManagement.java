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

import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class SuiteManagement extends LoggingBase {

    public boolean suiteSetup() throws Exception {
        /*
	  System.err.println("DEBUG: "+System.getProperties());
		// Change logger configuration
		URL properties = this.getClass().getResource("/logging.properties");
		System.err.println("DEBUG: logging.properties "+properties);
	    LogManager.getLogManager().readConfiguration(properties.openStream()); 
         */

        // load deployment specific properties
        Properties prp = Configuration.getProperties(this.getClass().getSimpleName() + "Env.properties");
        System.getProperties().putAll(prp);

        // load test specific properties
        prp = Configuration.getProperties(this.getClass().getSimpleName() + ".properties", true);
        System.getProperties().putAll(prp);

        final String loggingCfg = System.getProperty("java.util.logging.config.file");
        if (loggingCfg != null) {
            try {
                LogManager.getLogManager().readConfiguration();
            } catch (IOException ex) {
                logMessage("Failed to load configuration file for java.util.logging: " + ex.getLocalizedMessage());
            }
        }

        if (MOMServer.isRequired()) {
            // Start the MOM server if needed
            logMessage("Start the MOM server");
            MOMServer.instance().start();
            logMessage("Wait for the MOM server to be started");
            Thread.sleep(5000);
        }

        // Need to create the shared broker before the remote process starts.
        BaseLocalMALInstance.binstance();

        boolean res = RemoteMALInstance.instance().startProcess();

        // If it didn't start correctly lock the code here forever and display an error!
        if (!res) {
            logMessage("The remote process is not running therefore the tests will all fail. "
                    + "Make sure the remote process is running before running the tests! "
                    + "Check the log file (zzz_*) in the surefire-reports folder.");
            Thread.sleep(Long.MAX_VALUE);
        }

        Thread.sleep(5000);

        return res;
    }

    public boolean suiteTeardown() throws Exception {
        RemoteMALInstance.instance().stopProcess();
        BaseLocalMALInstance.binstance().closeMAL();
        RemoteMALInstance.instance().waitForSeconds(2);
        MOMServer.instance().stop();

        return RemoteMALInstance.instance().processIsStopped();
    }
}
