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
import org.ccsds.mo.mc.testbed.backends.AggregationDynamicDataset;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterDatasetForReportConfig;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mc.structures.AggregationDefinition;
import org.ccsds.moims.mo.mc.structures.AggregationDefinitionList;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
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
 * AG_4_Dynamic_Test implements the test scenario #AG-4.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AG_4_Dynamic_Test extends AggregationTestClient {

	static AggregationListener aggregationListener = new AggregationListener();
	static Identifier subscriptionId;

	// timeOrigin=1/1/2025 04:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 4, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDatasetForReportConfig parameterBackend = new ParameterDatasetForReportConfig(timer);
	private static AggregationDynamicDataset backend;

	public static final ObjectRef<AggregationDefinition> dynSat1BcMtq1RevRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					AggregationDefinition.TYPE_ID.getTypeId(),
					Constant.ID_BC_MTQ1_REV,
					new UInteger(1));
	public static final AggregationDefinition dynSat1BcMtq1RevDefinition =
			new AggregationDefinition(
					getAggregationIdentity(dynSat1BcMtq1RevRef),
					"",
					null,
					new ObjectRefList(new ArrayList<> (Arrays.asList(
							ParameterDatasetForReportConfig.sat1Mtq1EnabledRef,
							ParameterDatasetForReportConfig.sat1Mtq1VoltageRef))));

	public static final ObjectRef<AggregationDefinition> dynSat1BcMtq1Ref =
			new ObjectRef<>(
					Constant.DOMAIN_SAT1,
					AggregationDefinition.TYPE_ID.getTypeId(),
					Constant.ID_BC_MTQ1,
					new UInteger(1));
	public static final AggregationDefinition dynSat1BcMtq1Definition =
			new AggregationDefinition(
					getAggregationIdentity(dynSat1BcMtq1Ref),
					"",
					null,
					new ObjectRefList(new ArrayList<> (Arrays.asList(
							ParameterDatasetForReportConfig.sat1Mtq1VoltageRef))));
	
	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AG_4_Dynamic_Test.class.getName()));
		setUp.setUp(null, null, null, null, parameterBackend,
				false, false, false, false, true);

		if (setUp.getParameterProvider() == null) {
			unitTestFail("cannot find the Parameter provider");
		}
		SingleConnectionDetails parameterDetails =
				setUp.getParameterProvider().getConnection().getConnectionDetails();
		backend = new AggregationDynamicDataset(timer, parameterDetails);
		setUp.setUp(null, backend, null, null, null,
				false, true, false, false, false);
		aggregationConsumerStub = setUp.getAggregationConsumer();

		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=41
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("41");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				aggregationListener,
				System.currentTimeMillis() + TIMEOUT);

		// update BackendTimer with now
		// now=1/1/2025 04:06:06 (ie +6:06)
		System.out.println("skip time to 1/1/2025 04:06:06");
		timer.skip((6*60+6)*1000);
		
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AG_4_Dynamic_Test.class.getName() + " tearDownClass()");

		// call monitorValue.deregister with subscriptionIds={41}
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
	 * test the nominal case for an aggregation listDefinition
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		aggregationListener.reset();
		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// check response as singleton list:
		// - identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// - description: ""
		// - category: null
		// - parameters: {
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}}
		AggregationDefinitionList expected =
				new AggregationDefinitionList(new ArrayList<> (Arrays.asList(
						new AggregationDefinition(
								getAggregationIdentity(AggregationDynamicDataset.sat1BcMtq1Ref),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterDatasetForReportConfig.sat1Mtq1VoltageRef,
										ParameterDatasetForReportConfig.sat1Mtq1EnabledRef)))))));
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test listDefinition with null keys
	 * Requires previous execution of Test Case 1 (not true).
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		// check response as singleton list:
		// - identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// - description: ""
		// - category: null
		// - parameters: {
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}}
		AggregationDefinitionList expected =
				new AggregationDefinitionList(new ArrayList<> (Arrays.asList(
						new AggregationDefinition(
								getAggregationIdentity(AggregationDynamicDataset.sat1BcMtq1Ref),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterDatasetForReportConfig.sat1Mtq1VoltageRef,
										ParameterDatasetForReportConfig.sat1Mtq1EnabledRef)))))));
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test the nominal case for an aggregation addAggregation 
	 * Requires previous execution of Test Case 2 (not true).
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		execAndCheckAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						dynSat1BcMtq1RevDefinition))),
				aggregationListener,
				startTime + TIMEOUT);
		
		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		// check response, order not relevant:
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}}
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		aggregationListener.reset();
		AggregationDefinitionList expected =
				new AggregationDefinitionList(new ArrayList<> (Arrays.asList(
						new AggregationDefinition(
								getAggregationIdentity(AggregationDynamicDataset.sat1BcMtq1Ref),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterDatasetForReportConfig.sat1Mtq1VoltageRef,
										ParameterDatasetForReportConfig.sat1Mtq1EnabledRef)))),
						dynSat1BcMtq1RevDefinition)));
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// keep track of the order of the returned keys for next test
		test4Expected = new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
				aggregationListener.listDefinitionResponse.get(1),
				aggregationListener.listDefinitionResponse.get(0))));
		
		// additional statement for dependent tests
		TestDependency.after();
	}

	private static AggregationDefinitionList test4Expected;
	/**
	 * Test Case 4.
	 * test order of listDefinition result
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);
		if (test4Expected == null)
			throw new IllegalStateException("test4KeysOrder not set");

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1_REV", "AGG_BC_MTQ1"}*
		// check response, order is relevant*:
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}}
		// *order of keys and result is reverse of order returned by listDefinition in UC 3 above
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						test4Expected.get(0).getObjectIdentity().getKey(),
						test4Expected.get(1).getObjectIdentity().getKey()))),
				aggregationListener,
				startTime + TIMEOUT,
				test4Expected);

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
		// update BackendTimer with now
		// now=1/1/2025 04:08:08 (ie +2:02)
		System.out.println("skip time to 1/1/2025 04:08:08");
		timer.skip((2*60+2)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={parameterKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 04:08:06, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.00, convertedValue=null}}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[] {
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1_REV,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								MTQ1ENABLED_ENABLED_VALUE,
								new ParameterValueData(ValidityState.VALID,
										NA_DOUBLE_1200.getValue(),
										null)))))
		};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 6.
	 * test the nominal case for an aggregation removeAggregation 
	 * Requires previous execution of Test Case 5.
	 */
	@Test
	public void testCase_06() {
		// additional statement for dependent tests
		TestDependency.before(5, this, "testCase_05", 6);

		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call removeAggregation with removeAggregParams
		// removeAggregParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		execAndCheckRemoveAggregation(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT);

		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		// check response as singleton list:
		// - identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1_REV", version=1)
		// - description: ""
		// - category: null
		// - parameters: {
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}
		// -- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		aggregationListener.reset();
		AggregationDefinitionList expected =
				new AggregationDefinitionList(new ArrayList<> (Arrays.asList(
						dynSat1BcMtq1RevDefinition)));
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 04:10:10 (ie +2:02)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 04:10:10");
		timer.skip((2*60+2)*1000);

		// check reception of 1 NOTIFY message from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 04:10:06, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}}
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
												NA_DOUBLE_1205.getValue(),
												null)))))
				});

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 7.
	 * test reusing an aggregation key
	 * Requires previous execution of Test Case 6.
	 */
	@Test
	public void testCase_07() {
		// additional statement for dependent tests
		TestDependency.before(6, this, "testCase_06", 7);

		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		execAndCheckAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						dynSat1BcMtq1Definition))),
				aggregationListener,
				startTime + TIMEOUT);
		
		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys=null
		// check response, order not relevant:
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1ENABLED", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		aggregationListener.reset();
		AggregationDefinitionList expected =
				new AggregationDefinitionList(new ArrayList<> (Arrays.asList(
						dynSat1BcMtq1Definition,
						dynSat1BcMtq1RevDefinition)));
		execAndCheckListDefinition(
				Constant.DOMAIN_SAT1,
				null,
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// update BackendTimer with now
		// now=1/1/2025 04:12:12 (ie +2:02)
		aggregationListener.reset();
		System.out.println("skip time to 1/1/2025 04:12:12");
		timer.skip((2*60+2)*1000);

		// check reception of 2 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1", order not relevant,
		// 1 with keys={parameterKey="AGG_BC_MTQ1", parameterVersion=1}:
		// - timestamp=1/1/2025 03:11:11, values={
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}}
		// 1 with keys={parameterKey="AGG_BC_MTQ1_REV", parameterVersion=1}:
		// - timestamp=1/1/2025 03:11:11, values={
		// -- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		// -- {validityState=VALID, rawValue=12.05, convertedValue=null}}
		ParameterValueData mtq1VoltageValue = new ParameterValueData(
				ValidityState.VALID,
				NA_DOUBLE_1205.getValue(),
				null);
		MonitorValueUpdate[][] targetUpdates = new MonitorValueUpdate[][] {
			new MonitorValueUpdate[] {
					new MonitorValueUpdate(
							Constant.DOMAIN_SAT1,
							Constant.ID_BC_MTQ1,
							new UInteger(1),
							null,  // timestamp, unchecked
							new ParameterValueDataList(new ArrayList<>(Arrays.asList(
									mtq1VoltageValue))))
			},
			new MonitorValueUpdate[] {
					new MonitorValueUpdate(
							Constant.DOMAIN_SAT1,
							Constant.ID_BC_MTQ1_REV,
							new UInteger(1),
							null,  // timestamp, unchecked
							new ParameterValueDataList(new ArrayList<>(Arrays.asList(
									MTQ1ENABLED_ENABLED_VALUE,
									mtq1VoltageValue))))
			}};
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);
		
		// additional statement for dependent tests
		TestDependency.after();
	}

}
