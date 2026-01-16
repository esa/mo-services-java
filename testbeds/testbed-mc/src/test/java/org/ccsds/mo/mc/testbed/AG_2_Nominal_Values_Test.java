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

import org.ccsds.mo.mc.testbed.AggregationListener.MonitorValueUpdate;
import org.ccsds.mo.mc.testbed.backends.AggregationDefaultDataset;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterDefaultDataset;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AG_2_Nominal_Values_Test implements the test scenario #AG-2.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AG_2_Nominal_Values_Test extends AggregationTestClient {

	static AggregationListener aggregationListener = new AggregationListener();
	static Identifier subscriptionId;

	// timeOrigin=1/1/2025 02:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 2, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDefaultDataset parameterBackend = new ParameterDefaultDataset(timer);
	private static AggregationDefaultDataset backend;

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AG_2_Nominal_Values_Test.class.getName()));
		setUp.setUp(null, null, null, null, parameterBackend,
				false, false, false, false, true);

		if (setUp.getParameterProvider() == null) {
			unitTestFail("cannot find the Parameter provider");
		}
		SingleConnectionDetails parameterDetails =
				setUp.getParameterProvider().getConnection().getConnectionDetails();
		backend = new AggregationDefaultDataset(timer, parameterDetails);
		setUp.setUp(null, backend, null, null, null,
				false, true, false, false, false);
		aggregationConsumerStub = setUp.getAggregationConsumer();

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
				aggregationListener,
				System.currentTimeMillis() + TIMEOUT);

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AG_2_Nominal_Values_Test.class.getName() + " tearDownClass()");

		// call monitorValue.deregister with subscriptionIds={21}
		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscriptionId);
		aggregationListener.reset();
		execAndCheckMonitorValueDeregister(
				subscriptions,
				aggregationListener,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * test the nominal case for an aggregation value monitoring
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		aggregationListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:10:10 (ie +10:10)
		System.out.println("skip time to 1/1/2025 02:10:10");
		timer.skip((10*60+10)*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", aggregationVersion=1}:
		// - timestamp=?, values={
		// -- {validityState=VALID, rawValue=12.04, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		// - timestamp=?, values={
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								new ParameterValueData(ValidityState.VALID,
										NA_DOUBLE_1204.getValue(),
										null),
								MTQ1ENABLED_ENABLED_VALUE)))),
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								new ParameterValueData(ValidityState.VALID,
										NA_DOUBLE_1205.getValue(),
										null),
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test the nominal case for an aggregation getValue
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:11:11 (ie +1:01)
		System.out.println("skip time to 1/1/2025 02:11:11");
		timer.skip((1*60+1)*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// check response as singleton list:
		// - aggregationRef={"fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1}
		// - timestamp=?
		// - parameterValues={
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		AggregationValueList expected =
				new AggregationValueList(new ArrayList<>(Arrays.asList(
						new AggregationValue(
								AggregationDefaultDataset.sat1BcMtq1Ref,
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										new ParameterValueData(ValidityState.VALID,
												NA_DOUBLE_1205.getValue(),
												null),
										MTQ1ENABLED_ENABLED_VALUE)))))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<>(Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test getValue with a missing domain
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:12:12 (ie +1:01)
		System.out.println("skip time to 1/1/2025 02:12:12");
		timer.skip((1*60+1)*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain=null
		// - keys={"AGG_BC_MTQ1"}
		// check response as singleton list:
		// - aggregationRef={"fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1}
		// - timestamp=?
		// - parameterValues={
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		AggregationValueList expected =
				new AggregationValueList(new ArrayList<>(Arrays.asList(
						new AggregationValue(
								AggregationDefaultDataset.sat1BcMtq1Ref,
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										new ParameterValueData(ValidityState.VALID,
												NA_DOUBLE_1205.getValue(),
												null),
										MTQ1ENABLED_ENABLED_VALUE)))))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<>(Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 4.
	 * test getValue does not impact monitoring
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 02:15:15 (ie +3:03)
		System.out.println("skip time to 1/1/2025 02:15:15");
		timer.skip((3*60+3)*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", aggregationVersion=1}:
		// - timestamp=?, values={
		// -- {validityState=VALID, rawValue=12.11, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								new ParameterValueData(ValidityState.VALID,
										NA_DOUBLE_1211.getValue(),
										null),
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
