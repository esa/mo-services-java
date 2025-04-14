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
package org.ccsds.moims.mo.testbed.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;

/**
 *
 */
public abstract class Configuration {

    public static final String DEFAULT_PROTOCOL = "org.ccsds.moims.mo.mal.transport.default.protocol";
    public static final String TEST_PROTOCOL = "org.ccsds.moims.mo.testbed.transport.protocol";
    public static final String TEST_FACTORY_PROP_NAME = "org.ccsds.moims.mo.testbed.transport.factory";

    public static final String LOCAL_MAL_CLASS = "org.ccsds.moims.mo.testbed.local.class";
    public static final String LOCAL_CONFIGURATION_DIR = "org.ccsds.moims.mo.testbed.local.configuration.dir";

    public static final String REMOTE_CMD_PROPERTY_PREFIX = "org.ccsds.remote.cmdline.";
    public static final String REMOTE_MAL_CLASS = "org.ccsds.moims.mo.testbed.remote.class";
    public static final String REMOTE_OUTPUT_DIR = "org.ccsds.moims.mo.testbed.remote.output.dir";
    public static final String REMOTE_CONFIGURATION_DIR = "org.ccsds.moims.mo.testbed.remote.configuration.dir";
    public static final String REMOTE_EXTRA_ARGS = "org.ccsds.moims.mo.testbed.remote.extra.args";
    public static final String REMOTE_CLASSPATH_EXTRA_JARS = "org.ccsds.moims.mo.testbed.remote.classpath.extra";
    public static final String REMOTE_CLASSPATH_MAVEN_JARS = "org.ccsds.moims.mo.testbed.remote.classpath.maven";
    public static final String REMOTE_CLASSPATH_FILTER_STRING = "org.ccsds.moims.mo.testbed.remote.classpath.filter";
    public static final String MOM_CLASSPATH_EXTRA_JARS = "org.ccsds.moims.mo.testbed.mom.classpath.extra";
    public static final String MOM_CLASSPATH_MAVEN_JARS = "org.ccsds.moims.mo.testbed.mom.classpath.maven";
    public static final String MOM_CLASSPATH_FILTER_STRING = "org.ccsds.moims.mo.testbed.mom.classpath.filter";

    //public static final String WAIT_TIME_OUT_PROPERTY_NAME = "org.ccsds.moims.mo.testbed.wait.timeout";
    //public static final int WAIT_TIME_OUT = Integer.getInteger(WAIT_TIME_OUT_PROPERTY_NAME, 500).intValue();
    public static final int WAIT_TIME_OUT = 200;
    public static final int PERIOD = 100; // in ms - The code will wait on this PERIOD: ~630 times !
    public static final int MAL_PERIOD_SHORT = 40;
    public static final int MAL_PERIOD_LONG = 100;
    public static final int COM_PERIOD_SHORT = 100; // in ms
    public static final int COM_PERIOD_LONG = 500; // in ms

    public static final Blob DEFAULT_SHARED_BROKER_AUTHENTICATION_ID = new Blob(new byte[]{0x02, 0x01});
    public static final String TRANSPORT_LEVEL_SHARED_BROKER = "org.ccsds.moims.mo.testbed.transport.level.shared.broker";
    public static final String SHARED_BROKER_NAME = "SharedBroker";
    public static final String PRIVATE_BROKER_NAME = "PrivateBroker";

    public static final String SECURITY_FACTORY_PROP_NAME = "org.ccsds.moims.mo.mal.security.factory.class";
    private static String _os = null;

    public static String getOSname() {
        if (_os == null) {
            _os = System.getProperty("os.name").replace(' ', '_');
        }

        return _os;
    }

    public static Properties getProperties(String propFileName) throws MALException {
        return getProperties(propFileName, false);
    }

    public static Properties getProperties(String propFileName, boolean systemLevel) throws MALException {
        if (!systemLevel) {
            String configurationDir = System.getProperty(LOCAL_CONFIGURATION_DIR);

            if ((null != configurationDir) && (0 < configurationDir.length())) {
                propFileName = configurationDir + "/" + propFileName;
            }
        }

        Properties props = new Properties();
        java.io.File prpFile = new File(propFileName);
        if (prpFile.exists()) {
            LoggingBase.logMessage("Loading properties from file: " + prpFile.getPath());
            try {
                props.load(new FileInputStream(prpFile));
            } catch (Exception exc) {
                throw new MALException(exc.toString(), exc);
            }
        } else {
            LoggingBase.logMessage("No properties file found, skipped: " + prpFile.getPath());
        }
        return props;
    }
}
