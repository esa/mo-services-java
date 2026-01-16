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
import org.ccsds.mo.mc.testbed.backends.AggregationDatasetForReportConfig;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterDatasetForReportConfig;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AG_3_Report_Config_Test implements the test scenario #AG-3.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AG_3_Report_Config_Test extends AggregationTestClient {

	static AggregationListener aggregationListener = new AggregationListener();
	static Identifier subscriptionId;

	// timeOrigin=1/1/2025 03:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 3, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDatasetForReportConfig parameterBackend = new ParameterDatasetForReportConfig(timer);
	private static AggregationDatasetForReportConfig backend;

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AG_3_Report_Config_Test.class.getName()));
		setUp.setUp(null, null, null, null, parameterBackend,
				false, false, false, false, true);

		if (setUp.getParameterProvider() == null) {
			unitTestFail("cannot find the Parameter provider");
		}
		SingleConnectionDetails parameterDetails =
				setUp.getParameterProvider().getConnection().getConnectionDetails();
		backend = new AggregationDatasetForReportConfig(timer, parameterDetails);
		setUp.setUp(null, backend, null, null, null,
				false, true, false, false, false);
		aggregationConsumerStub = setUp.getAggregationConsumer();

		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=31
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("31");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				aggregationListener,
				System.currentTimeMillis() + TIMEOUT);

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AG_3_Report_Config_Test.class.getName() + " tearDownClass()");

		// call monitorValue.deregister with subscriptionIds={31}
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
	 * test the nominal case for an aggregation getReportingConfiguration
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		aggregationListener.reset();
		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=301s}
		// - {reportingEnabled=false, reportInterval=301s}
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_301),
						new ReportConfiguration(
								false,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test the nominal case for an aggregation enableReporting
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// update BackendTimer with now-1
		// now-1=1/1/2025 03:01:01 (ie +1:01)
		System.out.println("skip time to 1/1/2025 03:01:01");
		timer.skip((1*60+1)*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		aggregationListener.reset();
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT);
		
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:01:01, values={
		// -- {validityState=INVALID_RAW, rawValue=null, convertedValue=null}
		// -- {validityState=INVALID_RAW, rawValue=null, convertedValue=null}}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1_REV,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								INVALID_RAW_VALUE,
								INVALID_RAW_VALUE))))
//						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
//								MTQ1ENABLED_ENABLED_VALUE,
//								new ParameterValueData(ValidityState.VALID,
//										NA_DOUBLE_1200.getValue(),
//										null)))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=301s}
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_301),
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now-2
		// now-2=1/1/2025 03:05:05 (ie +4:04)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 03:05:05");
		timer.skip((4*60+4)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:05:05, values={
		// -- {validityState=VALID, rawValue=12.00, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		targetUpdates = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								new ParameterValueData(ValidityState.VALID,
										NA_DOUBLE_1200.getValue(),
										null),
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test the nominal case for an aggregation disableReporting
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1_REV"}
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=301s}
		// - {reportingEnabled=false, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_301),
						new ReportConfiguration(
								false,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:10:10 (ie +5:05)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 03:10:10");
		timer.skip((5*60+5)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:05:05, values={
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
										NA_DOUBLE_1205.getValue(),
										null),
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 4.
	 * test disableReporting with null keys
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=false, reportInterval=301s}
		// - {reportingEnabled=false, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_301),
						new ReportConfiguration(
								false,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:11:11 (ie +1:01)
		System.out.println("skip time to 1/1/2025 03:11:11");
		timer.skip((1*60+1)*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 5.
	 * test enableReporting with null keys
	 * Requires previous execution of Test Case 4.
	 */
	@Test
	public void testCase_05() {
		// additional statement for dependent tests
		TestDependency.before(4, this, "testCase_04", 5);

		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT);

		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1", order not relevant,
		// 1 with keys={parameterKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:11:11, values={
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		// 1 with keys={parameterKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:11:11, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}}
		ParameterValueData mtq1VoltageValue = new ParameterValueData(
				ValidityState.VALID,
				NA_DOUBLE_1205.getValue(),
				null);
		MonitorValueUpdate[][] targetUpdates = new MonitorValueUpdate[2][];
		targetUpdates[0] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								mtq1VoltageValue,
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		targetUpdates[1] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1_REV,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								MTQ1ENABLED_ENABLED_VALUE,
								mtq1VoltageValue))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=301s}
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_301),
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:15:15 (ie +4:04)
		System.out.println("skip time to 1/1/2025 03:15:15");
		timer.skip((4*60+4)*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 6.
	 * test the nominal case for an aggregation setReportingPeriod
	 * Requires previous execution of Test Case 5.
	 */
	@Test
	public void testCase_06() {
		// additional statement for dependent tests
		TestDependency.before(5, this, "testCase_05", 6);

		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// - reportInterval=602s
		execAndCheckSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				DURATION_602,
				aggregationListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=602s}
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_602),
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now-1
		// now-1=1/1/2025 03:20:20 (ie +5:05)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 03:20:20");
		timer.skip((5*60+5)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:16:16, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.10, convertedValue=null}}
		waitAndCheckForUpdates(
				aggregationListener,
				startTime + TIMEOUT,
				new MonitorValueUpdate[] {
						new MonitorValueUpdate(
								Constant.DOMAIN_SAT1,
								Constant.ID_BC_MTQ1_REV,
								new UInteger(1),
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										MTQ1ENABLED_ENABLED_VALUE,
										new ParameterValueData(
												ValidityState.VALID,
												NA_DOUBLE_1210.getValue(),
												null)))))
				});

		// update BackendTimer with now-2
		// now-2=1/1/2025 03:21:21 (ie +1:01)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 03:21:21");
		timer.skip((1*60+1)*1000);

		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1", order not relevant,
		// 1 with keys={parameterKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:21:21, values={
		// -- {validityState=VALID, rawValue=12.15, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		// 1 with keys={parameterKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:21:21, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.15, convertedValue=null}}
		ParameterValueData mtq1VoltageValue = new ParameterValueData(
				ValidityState.VALID,
				NA_DOUBLE_1215.getValue(),
				null);
		MonitorValueUpdate[][] targetUpdates = new MonitorValueUpdate[2][];
		targetUpdates[0] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								mtq1VoltageValue,
								MTQ1ENABLED_ENABLED_VALUE))))
		};
		targetUpdates[1] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1_REV,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								MTQ1ENABLED_ENABLED_VALUE,
								mtq1VoltageValue))))
		};
		waitAndCheckForUpdates(
				aggregationListener,
				startTime + TIMEOUT,
				targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 7.
	 * test setReportingPeriod of a disabled aggregation
	 * Requires previous execution of Test Case 6.
	 */
	@Test
	public void testCase_07() {
		// additional statement for dependent tests
		TestDependency.before(6, this, "testCase_06", 7);

		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT);

		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// - reportInterval=120s
		aggregationListener.reset();
		execAndCheckSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				DURATION_120,
				aggregationListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=false, reportInterval=120s}
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_120),
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now-1
		// now-1=1/1/2025 03:22:22 (ie +1:01)
		System.out.println("skip time to 1/1/2025 03:22:22");
		timer.skip((1*60+1)*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(aggregationListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		aggregationListener.reset();
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT);
		
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:22:22, values={
		// -- {validityState=VALID, rawValue=12.15, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		ParameterValueData mtq1VoltageValue = new ParameterValueData(
				ValidityState.VALID,
				NA_DOUBLE_1215.getValue(),
				null);
		waitAndCheckForUpdates(
				aggregationListener,
				startTime + TIMEOUT,
				new MonitorValueUpdate[] {
						new MonitorValueUpdate(
								Constant.DOMAIN_SAT1,
								Constant.ID_BC_MTQ1,
								new UInteger(1),
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										mtq1VoltageValue,
										MTQ1ENABLED_ENABLED_VALUE))))
				});

		// update BackendTimer with now-2
		// now-2=1/1/2025 03:24:24 (ie +2:02)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 03:24:24");
		timer.skip((2*60+2)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:24:22, values={
		// -- {validityState=VALID, rawValue=12.15, convertedValue=null}
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}
		waitAndCheckForUpdates(
				aggregationListener,
				startTime + TIMEOUT,
				new MonitorValueUpdate[] {
						new MonitorValueUpdate(
								Constant.DOMAIN_SAT1,
								Constant.ID_BC_MTQ1,
								new UInteger(1),
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										mtq1VoltageValue,
										MTQ1ENABLED_ENABLED_VALUE))))
				});

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 8.
	 * test setReportingPeriod with value 0
	 * Requires previous execution of Test Case 7.
	 */
	@Test
	public void testCase_08() {
		// additional statement for dependent tests
		TestDependency.before(7, this, "testCase_07", 8);

		System.out.println("Running: testCase_08()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// - reportInterval=0s
		execAndCheckSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				DURATION_0,
				aggregationListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=0s}
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_0),
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:26:26 (ie +2:02)
		System.out.println("skip time to 1/1/2025 03:26:26");
		timer.skip((2*60+2)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:26:26, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.20, convertedValue=null}}
		waitAndCheckForUpdates(
				aggregationListener,
				startTime + TIMEOUT,
				new MonitorValueUpdate[] {
						new MonitorValueUpdate(
								Constant.DOMAIN_SAT1,
								Constant.ID_BC_MTQ1_REV,
								new UInteger(1),
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										MTQ1ENABLED_ENABLED_VALUE,
										new ParameterValueData(
												ValidityState.VALID,
												NA_DOUBLE_1220.getValue(),
												null)))))
				});
		
		// additional statement for dependent tests
		TestDependency.after();
	}

}
