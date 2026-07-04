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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.AlertListener.MonitorAlertUpdate;
import org.ccsds.mo.mc.testbed.backends.AlertDefaultDataset;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.structures.AlertConfiguration;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AL_2_Generation_Test implements the test scenario #AL-2.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AL_2_Generation_Test extends AlertTestClient {

	static AlertListener alertListener = new AlertListener();
	static Identifier subscriptionId;

	private static final AlertDefaultDataset backend = new AlertDefaultDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AL_2_Generation_Test.class.getName()));
		setUp.setUp(null, null, backend, null, null,
				false, false, true, false, false);
		alertConsumerStub = setUp.getAlertConsumer();

		// call monitorAlert.register with subscription
		// subscription=
		// - subscriptionId=21
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("21");
		execAndCheckMonitorAlertRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				alertListener,
				System.currentTimeMillis() + TIMEOUT);
		
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AL_2_Generation_Test.class.getName() + " tearDownClass()");

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
	 * Checks that Alert generation is initially disabled.
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check response as singleton list:
		// - {generationEnabled=false}
		AlertConfigurationList expected =
				new AlertConfigurationList(new ArrayList<>(Arrays.asList(
						new AlertConfiguration(false))));
		execAndCheckGetAlertConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * Checks that no Alert is published when Alert generation is disabled.
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		
		alertListener.reset();
		// call backend.reportAlertCondition with reportCondParams
		// reportCondParams=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH"}
		// - status=true
		// - arguments={{value=13.05}}
		NullableAttributeList argumentValues =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1305)));
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageHighAdId,
				true,
				argumentValues);

		// check no new message from subscription
		waitAndCheckNoUpdate(alertListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * Enables Alert generation and receives an Alert.
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
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

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={alertKey="MTQ1VOLTAGE_HIGH", alertVersion=1, alertSeverity=SEVERE}:
		// - timestamp=?,  arguments={{value=13.05}}
		MonitorAlertUpdate[] targetUpdates = new MonitorAlertUpdate[1];
		targetUpdates[0] =
				new MonitorAlertUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE_HIGH,
						new UInteger(1),
						UO_SEVERE,
						null,  // timestamp, unchecked
						new NullableAttributeList(new ArrayList<> (Arrays.asList(
								NA_DOUBLE_1305))));
		waitAndCheckForUpdates(alertListener, startTime + TIMEOUT, targetUpdates);
		
		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 4.
	 * Checks the null keys option.
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call enableGeneration with enableGenParams
		// enableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		execAndCheckEnableGeneration(
				Constant.DOMAIN_SAT1,
				null,
				alertListener,
				startTime + TIMEOUT);

		// check no new message from subscription
		waitAndCheckNoUpdate(alertListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// call backend.reportAlertCondition with reportCondParams1
		// reportCondParams1=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH"}
		// - status=false
		// - arguments={{value=9.10}}
		NullableAttributeList argumentValues =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_910)));
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageHighAdId,
				false,
				argumentValues);

		// call backend.reportAlertCondition with reportCondParams2
		// reportCondParams2=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_LOW"}
		// - status=true
		// - arguments={{value=9.10}}
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageLowAdId,
				true,
				argumentValues);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={alertKey="MTQ1VOLTAGE_LOW", alertVersion=1, alertSeverity=SEVERE}:
		// - timestamp=?,  arguments={{value=9.10}}
		MonitorAlertUpdate[] targetUpdates = new MonitorAlertUpdate[1];
		targetUpdates[0] =
				new MonitorAlertUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE_LOW,
						new UInteger(1),
						UO_SEVERE,
						null,  // timestamp, unchecked
						argumentValues);
		waitAndCheckForUpdates(alertListener, startTime + TIMEOUT, targetUpdates);
		
		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 5.
	 * Disables Alert generation and receives no Alert.
	 * Requires previous execution of Test Case 4.
	 */
	@Test
	public void testCase_05() {
		// additional statement for dependent tests
		TestDependency.before(4, this, "testCase_04", 5);

		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call disableGeneration with disableGenParams
		// disableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		execAndCheckDisableGeneration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT);

		// call backend.reportAlertCondition with reportCondParams1
		// reportCondParams1=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH"}
		// - status=true
		// - arguments={{value=13.15}}
		NullableAttributeList argumentValues =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1315)));
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageHighAdId,
				true,
				argumentValues);

		// call backend.reportAlertCondition with reportCondParams2
		// reportCondParams2=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_LOW"}
		// - status=false
		// - arguments={{value=13.15}}
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageLowAdId,
				false,
				argumentValues);

		// check no new message from subscription
		waitAndCheckNoUpdate(alertListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 6.
	 * Checks the null keys option.
	 * Requires previous execution of Test Case 5.
	 */
	@Test
	public void testCase_06() {
		// additional statement for dependent tests
		TestDependency.before(5, this, "testCase_05", 6);

		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call disableGeneration with disableGenParams
		// disableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		execAndCheckDisableGeneration(
				Constant.DOMAIN_SAT1,
				null,
				alertListener,
				startTime + TIMEOUT);

		// call backend.reportAlertCondition with reportCondParams1
		// reportCondParams1=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_HIGH"}
		// - status=false
		// - arguments={{value=9.20}}
		NullableAttributeList argumentValues =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_920)));
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageHighAdId,
				false,
				argumentValues);

		// call backend.reportAlertCondition with reportCondParams2
		// reportCondParams2=
		// - alertID={"fr.cnes.mission.sat1", "MTQ1VOLTAGE_LOW"}
		// - status=true
		// - arguments={{value=9.20}}
		alertListener.reset();
		backend.reportAlertCondition(
				backend.sat1Mtq1VoltageLowAdId,
				true,
				argumentValues);

		// check no new message from subscription
		waitAndCheckNoUpdate(alertListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 7.
	 * Checks getAlertConfiguration with a null domain.
	 * Requires previous execution of Test Case 6.
	 */
	@Test
	public void testCase_07() {
		// additional statement for dependent tests
		TestDependency.before(6, this, "testCase_06", 7);

		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain=null
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check response as singleton list:
		// - {generationEnabled=false}
		AlertConfigurationList expected =
				new AlertConfigurationList(new ArrayList<>(Arrays.asList(
						new AlertConfiguration(false))));
		execAndCheckGetAlertConfiguration(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
