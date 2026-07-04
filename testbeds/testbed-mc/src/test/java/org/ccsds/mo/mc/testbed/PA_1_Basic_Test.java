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
import org.ccsds.mo.mc.testbed.backends.ParameterBasicDataset;
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
 * PA_1_Basic_Test implements the test scenario #PA-1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PA_1_Basic_Test extends ParameterTestClient {

	static ParameterListener parameterListener = new ParameterListener();
	static Identifier subscriptionId;

	// initialize BackendTimer with timeOrigin
	// timeOrigin=1/1/2025 01:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 1, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterBasicDataset backend = new ParameterBasicDataset(timer);

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PA_1_Basic_Test.class.getName()));
		setUp.setUp(null, null, null, null, backend,
				false, false, false, false, true);
		parameterConsumerStub = setUp.getParameterConsumer();

		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=11
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("11");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				parameterListener,
				System.currentTimeMillis() + TIMEOUT);

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PA_1_Basic_Test.class.getName() + " tearDownClass()");

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
	 * test reception of a parameter value update
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
		// now=1/1/2025 01:05:00 (ie +5:00)
		System.out.println("skip time to 1/1/2025 01:05:00");
		timer.skip(5*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.00, convertedValue=null}
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
								new Union(new Double(12.00)),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test a simple setValue/getValue sequence
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
		// now=1/1/2025 01:06:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 01:06:00");
		timer.skip(1*60*1000);

		// call setValue with setValueParams
		// setValueParams=
		// - domain=null
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// - newRawValues={13.00}
		execAndCheckSetValue(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1300))),
				parameterListener,
				startTime + TIMEOUT);

		// call getValue with getValueParams
		// getValueParams=
		// - domain=null
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=13.00, convertedValue=null}
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterBasicDataset.sat1Mtq1VoltageRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new Union(new Double(13.00)),
								null)))));
		execAndCheckGetValue(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test a user set value in an update
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 01:10:00 (ie +4:00)
		System.out.println("skip time to 1/1/2025 01:10:00");
		timer.skip(4*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription with domain="fr.cnes.mission.sat1" and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=13.00, convertedValue=null}
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
								new Union(new Double(13.00)),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
