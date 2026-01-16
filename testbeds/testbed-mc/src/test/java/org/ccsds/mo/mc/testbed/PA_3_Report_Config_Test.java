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
import org.ccsds.mo.mc.testbed.backends.ParameterDatasetForReportConfig;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * PA_3_Report_Config_Test implements the test scenario #PA-3.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PA_3_Report_Config_Test extends ParameterTestClient {

	static ParameterListener parameterListener = new ParameterListener();
	static Identifier subscriptionId;

	// initialize BackendTimer with timeOrigin
	// timeOrigin=1/1/2025 03:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 3, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDatasetForReportConfig backend = new ParameterDatasetForReportConfig(timer);

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PA_3_Report_Config_Test.class.getName()));
		setUp.setUp(null, null, null, null, backend,
				false, false, false, false, true);
		parameterConsumerStub = setUp.getParameterConsumer();
		long startTime = System.currentTimeMillis();

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
				parameterListener,
				startTime + TIMEOUT);
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PA_3_Report_Config_Test.class.getName() + " tearDownClass()");

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
	 * test the nominal case for a parameter getReportingConfiguration
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=300s}
		// - {reportingEnabled=false, reportInterval=300s}
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_300),
						new ReportConfiguration(
								false,
								DURATION_300))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test the nominal case for a parameter enableReporting
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// update BackendTimer with now1
		// now1=1/1/2025 03:01:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 03:01:00");
		timer.skip(1*60*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, startTime + NOUPDATE_TIMEOUT);

		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		parameterListener.reset();
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1" and keys={parameterKey="ATT_BC_MTQ1ENABLED", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1ENABLED,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_UINT_1.getValue(),
								NA_STRING_ENABLED.getValue()));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=300s}
		// - {reportingEnabled=true, reportInterval=300s}
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<>(Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_300),
						new ReportConfiguration(
								true,
								DURATION_300))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		parameterListener.reset();
		// update BackendTimer with now2
		// now2=1/1/2025 03:05:00 (ie +4:00)
		System.out.println("skip time to 1/1/2025 03:05:00");
		timer.skip(4*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.00, convertedValue=null}
		targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1200.getValue(),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test the nominal case for a parameter enableReporting
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=300s}
		// - {reportingEnabled=false, reportInterval=300s}
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_300),
						new ReportConfiguration(
								false,
								DURATION_300))));
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:10:00 (ie +5:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:10:00");
		timer.skip(5*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1" and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.05, convertedValue=null}
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
								NA_DOUBLE_1205.getValue(),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

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

		parameterListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				null,
				parameterListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=false, reportInterval=300s}
		// - {reportingEnabled=false, reportInterval=300s}
		ReportConfigurationList expected = new ReportConfigurationList(new ArrayList<> (Arrays.asList(
				new ReportConfiguration(
						false,
						DURATION_300),
				new ReportConfiguration(
						false,
						DURATION_300))));
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:11:00 (ie +1:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:11:00");
		timer.skip(1*60*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

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

		parameterListener.reset();
		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				null,
				parameterListener,
				startTime + TIMEOUT);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1", order not relevant,
		// 1 with keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.10, convertedValue=null}
		// 1 with keys={parameterKey="ATT_BC_MTQ1ENABLED", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		MonitorValueUpdate[][] targetUpdates = new MonitorValueUpdate[2][];
		targetUpdates[0] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1210.getValue(),
								null))
		};
		targetUpdates[1] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1ENABLED,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new UInteger(1),
								NA_STRING_ENABLED.getValue()))
		};
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);
		
		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=300s}
		// - {reportingEnabled=true, reportInterval=300s}
		ReportConfigurationList expected = new ReportConfigurationList(new ArrayList<> (Arrays.asList(
				new ReportConfiguration(
						true,
						DURATION_300),
				new ReportConfiguration(
						true,
						DURATION_300))));
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 03:15:00 (ie +4:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:15:00");
		timer.skip(4*60*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 6.
	 * test the nominal case for a parameter setReportingPeriod
	 * Requires previous execution of Test Case 5.
	 */
	@Test
	public void testCase_06() {
		// additional statement for dependent tests
		TestDependency.before(5, this, "testCase_05", 6);

		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// - reportInterval=600s
		execAndCheckSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				DURATION_600,
				parameterListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=true, reportInterval=600s}
		// - {reportingEnabled=true, reportInterval=300s}
		ReportConfigurationList expected = new ReportConfigurationList(new ArrayList<> (Arrays.asList(
				new ReportConfiguration(
						true,
						DURATION_600),
				new ReportConfiguration(
						true,
						DURATION_300))));
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now1
		// now1=1/1/2025 03:20:00 (ie +5:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:20:00");
		timer.skip(5*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="ATT_BC_MTQ1ENABLED", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1ENABLED,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new UInteger(1),
								NA_STRING_ENABLED.getValue()));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// update BackendTimer with now2
		// now2=1/1/2025 03:21:00 (ie +1:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:21:00");
		timer.skip(1*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1", order not relevant,
		// 1 with keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12.20, convertedValue=null}
		// 1 with keys={parameterKey="ATT_BC_MTQ1ENABLED", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		MonitorValueUpdate[][] targetUpdates2 = new MonitorValueUpdate[2][];
		targetUpdates2[0] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1220.getValue(),
								null))
		};
		targetUpdates2[1] = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1ENABLED,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								new UInteger(1),
								NA_STRING_ENABLED.getValue()))
		};
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates2);
		
		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 7.
	 * test setReportingPeriod with value 0
	 * Requires previous execution of Test Case 6.
	 */
	@Test
	public void testCase_07() {
		// additional statement for dependent tests
		TestDependency.before(6, this, "testCase_06", 7);

		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		parameterListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		execAndCheckDisableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT);

		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// - reportInterval=0s
		execAndCheckSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				DURATION_0,
				parameterListener,
				startTime + TIMEOUT);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// - {reportingEnabled=false, reportInterval=60s}
		// - {reportingEnabled=true, reportInterval=300s}
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_60),
						new ReportConfiguration(
								true,
								DURATION_300))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now1
		// now1=1/1/2025 03:22:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 03:22:00");
		timer.skip(1*60*1000);

		// check no new message from subscription
		waitAndCheckNoUpdate(parameterListener, System.currentTimeMillis() + NOUPDATE_TIMEOUT);

		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		parameterListener.reset();
		execAndCheckEnableReporting(
				Constant.DOMAIN_SAT1,
				null,
				parameterListener,
				startTime + TIMEOUT);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12:20, convertedValue=null}
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
								NA_DOUBLE_1220.getValue(),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// update BackendTimer with now2
		// now2=1/1/2025 03:23:00 (ie +1:00)
		parameterListener.reset();
		System.out.println("skip time to 1/1/2025 03:23:00");
		timer.skip(1*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="ATT_BC_MTQ1VOLTAGE", parameterVersion=1}:
		// - timestamp=?, newValue={validityState=VALID, rawValue=12:20, convertedValue=null}
		targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_MTQ1VOLTAGE,
						new UInteger(1),
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_DOUBLE_1220.getValue(),
								null));
		waitAndCheckForUpdates(parameterListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
