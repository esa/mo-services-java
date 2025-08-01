/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed
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
package org.ccsds.mo.mc.testbed;

import org.ccsds.mo.mc.testbed.SetUpProvidersAndConsumers;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.aggregation.provider.AggregationInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.packet.consumer.PacketStub;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterStub;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

/**
 * The abstract class for all MC Tests.
 *
 * @author Cesar.Coelho
 */
public abstract class MCTest {

    protected static final int TIMEOUT = 500; // In milliseconds
    protected static final String TEST_START = "-------- Running New Test --------";
    protected static final String TEST_END = "Test is completed!";
    protected static final String TEST_SET_UP_CLASS_1 = "-----------------------------------------------------------------------";
    protected static final String TEST_SET_UP_CLASS_2 = "Entered: setUpClass() - The Provider and Consumer will be started here!";
    protected static final SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();

    protected static ActionInheritanceSkeleton actionProviderService = null;
    protected static ActionStub actionConsumerStub = null;
    protected static AggregationInheritanceSkeleton aggregationProviderService = null;
    protected static AggregationStub aggregationConsumerStub = null;
    protected static AlertInheritanceSkeleton alertProviderService = null;
    protected static AlertStub alertConsumerStub = null;
    protected static PacketInheritanceSkeleton packetProviderService = null;
    protected static PacketStub packetConsumerStub = null;
    protected static ParameterInheritanceSkeleton parameterProviderService = null;
    protected static ParameterStub parameterConsumerStub = null;

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Entered: tearDownClass()");
        System.out.println("The Provider and Consumer are being closed!");

        try {
            setUp.tearDown(); // Close all the services
        } catch (IOException ex) {
            Logger.getLogger(MCTest.class.getName()).log(Level.SEVERE,
                    "The tearDown() operation failed!", ex);
        }
    }

    @Before
    public void setUp() {
        System.out.println(TEST_START); // Right before running a test
        actionProviderService = setUp.getActionProvider();
        actionConsumerStub = setUp.getActionConsumer();
        aggregationProviderService = setUp.getAggregationProvider();
        aggregationConsumerStub = setUp.getAggregationConsumer();
        alertProviderService = setUp.getAlertProvider();
        alertConsumerStub = setUp.getAlertConsumer();
        packetProviderService = setUp.getPacketProvider();
        packetConsumerStub = setUp.getPacketConsumer();
        parameterProviderService = setUp.getParameterProvider();
        parameterConsumerStub = setUp.getParameterConsumer();
    }

    @After
    public void tearDown() {
        System.out.println(TEST_END);
    }

    protected static URI getHomeTmpDir() {
        File homeDirectory = new File(System.getProperty("user.home"));
        File targetDir = new File(homeDirectory, "tmp");
        if (!targetDir.exists()) {
            // Create the directory if it does not exist:
            targetDir.mkdirs();
        }
        return new URI("file://" + targetDir.getAbsolutePath());
    }

}
