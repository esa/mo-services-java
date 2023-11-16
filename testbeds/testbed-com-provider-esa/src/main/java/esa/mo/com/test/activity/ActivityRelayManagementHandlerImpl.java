/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
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
package esa.mo.com.test.activity;

import java.util.Map;
import java.util.TreeMap;
import org.ccsds.moims.mo.com.test.provider.TestServiceProvider;
import org.ccsds.moims.mo.comprototype.activityrelaymanagement.provider.ActivityRelayManagementInheritanceSkeleton;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class ActivityRelayManagementHandlerImpl extends ActivityRelayManagementInheritanceSkeleton {

    private final TestServiceProvider testService;
    private final Map<String, ActivityRelayNode> relayMap = new TreeMap();
    private ActivityRelayNode initialRelay = null;

    public ActivityRelayManagementHandlerImpl(TestServiceProvider testService) {
        this.testService = testService;
    }

    public void resetTest(MALInteraction interaction) throws MALInteractionException, MALException {
        LoggingBase.logMessage("ActivityRelayManagementHandlerImpl:resetTest");

        for (Map.Entry<String, ActivityRelayNode> entry : relayMap.entrySet()) {
            ActivityRelayNode activityRelayNode = entry.getValue();
            activityRelayNode.close();
        }

        relayMap.clear();
        initialRelay = null;
    }

    public void createRelay(String name, String relayTo, MALInteraction interaction) throws MALException {
        LoggingBase.logMessage("ActivityRelayManagementHandlerImpl:createRelay " + name + ":" + relayTo);
        ActivityRelayNode node = new ActivityRelayNode(testService, this,
                testService.getProtocol(), name, relayTo);
        LoggingBase.logMessage("ActivityRelayManagementHandlerImpl:createRelay complete");

        node.init();
        node.resetTest();

        relayMap.put(name, node);
        initialRelay = node;
    }

    public void passToRelay(String relayName, StringList _StringList0,
            MALInteraction interaction) throws MALInteractionException, MALException {
        ActivityRelayNode relay;

        if (null == relayName) {
            relay = initialRelay;
        } else {
            relay = relayMap.get(relayName);
        }

        if (null != relay) {
            relay.relayMessage(_StringList0, interaction);
        }
    }
}
