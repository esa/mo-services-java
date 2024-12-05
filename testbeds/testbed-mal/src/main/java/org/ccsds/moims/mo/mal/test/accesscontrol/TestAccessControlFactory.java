/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.accesscontrol;

import java.util.Map;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControl;
import org.ccsds.moims.mo.mal.accesscontrol.MALAccessControlFactory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class TestAccessControlFactory extends MALAccessControlFactory {

    public static final String FACTORY_PROP_NAME = "org.ccsds.moims.mo.mal.accesscontrol.factory.class";

    private static TestAccessControl manager = null;

    public TestAccessControlFactory() {
        LoggingBase.logMessage("TestAccessControlFactory created");
    }

    public MALAccessControl createAccessControl(Map properties) throws MALException {
        if (manager == null) {
            manager = new TestAccessControl();
        }

        return manager;
    }

    public static boolean securityManagerHasBeenCreated() {
        return null != manager;
    }

    public static TestAccessControl managerInstance() {
        return manager;
    }
}
