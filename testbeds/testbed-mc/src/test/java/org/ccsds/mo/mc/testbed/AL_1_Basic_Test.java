/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 *                         Adapted to the M&C testbed from the MPD testbed
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.AlertListener.MonitorAlertUpdate;
import org.ccsds.mo.mc.testbed.backends.AlertBasicDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.Severity;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AL_1_Basic_Test implements the test scenario #AL-1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AL_1_Basic_Test extends AlertTestClient {

	static AlertListener alertListener = new AlertListener();
	static Identifier subscriptionId;

	private static final AlertBasicDataset backend = new AlertBasicDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AL_1_Basic_Test.class.getName()));
		setUp.setUp(null, null, backend, null, null,
				false, false, true, false, false);
		alertConsumerStub = setUp.getAlertConsumer();

		// call monitorAlert.register with subscription
		// subscription=
		// - subscriptionId=11
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("11");
		execAndCheckMonitorAlertRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				alertListener,
				System.currentTimeMillis() + TIMEOUT);
		
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AL_1_Basic_Test.class.getName() + " tearDownClass()");

		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscriptionId);
		alertListener.reset();
		execAndCheckMonitorAlertDeregister(
				subscriptions,
				alertListener,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call enableGeneration with enableGenParams
		// enableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		execAndCheckEnableGeneration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT);

		// call backend.reportAlertCondition with reportCondParams
		// reportCondParams=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH"}
		// - status=true
		// - arguments={{value=13.00}}
		NullableAttributeList argumentValues =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1300)));
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageHighAdId,
				true,
				argumentValues);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={alertKey="MTQ1VOLTAGE_HIGH", alertVersion=1, alertSeverity=SEVERE}:
		// - timestamp=?,  arguments={{value=13.00}}
		MonitorAlertUpdate[] targetUpdates = new MonitorAlertUpdate[1];
		targetUpdates[0] =
				new MonitorAlertUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE_HIGH,
						new UInteger(1),
						UO_SEVERE,
						null,  // timestamp, unchecked
						argumentValues);
		waitAndCheckForUpdates(alertListener, startTime + TIMEOUT, targetUpdates);
		
	}
}
