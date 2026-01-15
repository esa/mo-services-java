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
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.ParameterListener.MonitorValueUpdate;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterDefaultDataset;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * PA_2_Nominal_Values_Test implements the test scenario #PA-2.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PA_2_Nominal_Values_Test extends ParameterTestClient {

	static ParameterListener parameterListener = new ParameterListener();
	static Identifier subscriptionId;

	// initialize BackendTimer with timeOrigin
	// timeOrigin=1/1/2025 02:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 2, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDefaultDataset backend = new ParameterDefaultDataset(timer);

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PA_2_Nominal_Values_Test.class.getName()));
		setUp.setUp(null, null, null, null, backend,
				false, false, false, false, true);
		parameterConsumerStub = setUp.getParameterConsumer();

		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=21
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("21");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				parameterListener,
				System.currentTimeMillis() + TIMEOUT);
		
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PA_2_Nominal_Values_Test.class.getName() + " tearDownClass()");

		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscriptionId);
		parameterListener.reset();
		execAndCheckMonitorValueDeregister(
				subscriptions,
				parameterListener,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * test the nominal case for a parameter value monitoring
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		parameterListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:10:00 (ie +10:00)
		System.out.println("skip time to 1/1/2025 02:10:00");
		timer.skip(10*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1" and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.04, convertedValue=null}
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.05, convertedValue=null}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[2];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new Union(new Double(12.04)),
								null));
		targetUpdates[1] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new Union(new Double(12.05)),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test the nominal case for a parameter getValue
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();
		
		parameterListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:11:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 02:11:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=12.08, convertedValue=null}
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1VoltageRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new Union(new Double(12.08)),
								null)),
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new UInteger(1),
								NA_STRING_ENABLED.getValue())))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
					Constant.ID_MTQ1VOLTAGE,
					Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test with a missing domain
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();
		
		parameterListener.reset();
		// call getValue with getValueParams
		// getValueParams=
		// - domain=null
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new UInteger(1),
								NA_STRING_ENABLED.getValue())))));
		execAndCheckGetValue(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 4.
	 * test the nominal case for a parameter setValue
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// update BackendTimer with now-1
		// now-1=1/1/2025 02:12:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 02:12:00");
		timer.skip(1*60*1000);

		// call setValue with setValueParams
		// setValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// - newRawValues={13.04, 1}
		execAndCheckSetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1304,
						NA_UINT_1))),
				parameterListener,
				startTime + TIMEOUT);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=13.04, convertedValue=null}
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1VoltageRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1304.getValue(),
								null)),
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_UINT_1.getValue(),
								NA_STRING_ENABLED.getValue())))));
		parameterListener.reset();
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// update BackendTimer with now-2
		// now-2=1/1/2025 02:15:00 (ie +3:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 02:15:00");
		timer.skip(3*60*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1" and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=13.04, convertedValue=null}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1304.getValue(),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
