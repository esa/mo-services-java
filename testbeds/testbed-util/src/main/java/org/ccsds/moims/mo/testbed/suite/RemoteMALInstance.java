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

import java.util.Properties;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.RemoteProcessRunner;

/**
 *
 */
public class RemoteMALInstance extends RemoteProcessRunner {

    static private RemoteMALInstance instance = null;

    public static RemoteMALInstance instance() throws Exception {
        if (null == instance) {
            // load test specific properties
            Properties prp = Configuration.getProperties("RemoteMALInstance.properties", true);
            System.getProperties().putAll(prp);

            instance = new RemoteMALInstance();
        }

        return instance;
    }

    private RemoteMALInstance() throws Exception {
        super(System.getProperty(Configuration.REMOTE_MAL_CLASS));
    }
}
