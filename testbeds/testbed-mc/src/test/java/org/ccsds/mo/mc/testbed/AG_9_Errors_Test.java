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
import org.ccsds.mo.mc.testbed.backends.AggregationDefaultDataset;
import org.ccsds.mo.mc.testbed.backends.AggregationDynamicDataset;
import org.ccsds.mo.mc.testbed.backends.AggregationErrorDataset;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterDatasetForReportConfig;
import org.ccsds.mo.mc.testbed.backends.ParameterErrorDataset;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.MCHelper;
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
 * AG_9_Errors_Test implements the test scenario #AG-9.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AG_9_Errors_Test extends AggregationTestClient {

	static AggregationListener aggregationListener = new AggregationListener();
	static Identifier subscriptionId;

	// timeOrigin=1/1/2025 09:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 9, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterErrorDataset parameterBackend = new ParameterErrorDataset(timer);
	private static AggregationErrorDataset backend;

	public static final ParameterValueData MTQ1ENABLED_ENABLED_VALUE =
			new ParameterValueData(
					ValidityState.VALID,
					NA_UINT_1.getValue(),
					NA_STRING_ENABLED.getValue());
	public static final ParameterValueData MTQ1ENABLED_EXPIRED_VALUE =
			new ParameterValueData(
					ValidityState.EXPIRED,
					NA_UINT_1.getValue(),
					NA_STRING_ENABLED.getValue());

	public static final ObjectRef<AggregationDefinition> dynSat2BcMtq1RevRef =
			new ObjectRef<>(
					Constant.DOMAIN_SAT2,
					AggregationDefinition.TYPE_ID.getTypeId(),
					Constant.ID_BC_MTQ1_REV,
					new UInteger(1));
	public static final AggregationDefinition dynSat2BcMtq1RevDefinition =
			new AggregationDefinition(
					getAggregationIdentity(dynSat2BcMtq1RevRef),
					"",
					null,
					new ObjectRefList(new ArrayList<> (Arrays.asList(
							ParameterErrorDataset.sat2Mtq1VoltageRef,
							ParameterErrorDataset.sat1Mtq1VoltageRef))));

//	public static final ObjectRef<AggregationDefinition> dynSat1BcMtq1Ref =
//			new ObjectRef<>(
//					Constant.DOMAIN_SAT1,
//					AggregationDefinition.TYPE_ID.getTypeId(),
//					Constant.ID_BC_MTQ1,
//					new UInteger(1));
//	public static final AggregationDefinition dynSat1BcMtq1Definition =
//			new AggregationDefinition(
//					getAggregationIdentity(dynSat1BcMtq1Ref),
//					"",
//					null,
//					new ObjectRefList(new ArrayList<> (Arrays.asList(
//							ParameterErrorDataset.sat1Mtq1VoltageRef))));
	
	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AG_9_Errors_Test.class.getName()));
		setUp.setUp(null, null, null, null, parameterBackend,
				false, false, false, false, true);

		if (setUp.getParameterProvider() == null) {
			unitTestFail("cannot find the Parameter provider");
		}
		SingleConnectionDetails parameterDetails =
				setUp.getParameterProvider().getConnection().getConnectionDetails();
		backend = new AggregationErrorDataset(timer, parameterDetails);
		setUp.setUp(null, backend, null, null, null,
				false, true, false, false, false);
		aggregationConsumerStub = setUp.getAggregationConsumer();

		long startTime = System.currentTimeMillis();
		
		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=91
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("91");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
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
		// now=1/1/2025 09:06:00 (ie +6:00)
		System.out.println("skip time to 1/1/2025 09:06:00");
		timer.skip(6*60*1000);
		
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AG_9_Errors_Test.class.getName() + " tearDownClass()");

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
	 * test ambiguity with a null domain in getValue
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		aggregationListener.reset();
		// call getValue with getValueParams
		// getValueParams=
		// - domain=null
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check ERROR message with code Ambiguous
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorGetValue(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.AMBIGUOUS_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

	/**
	 * Test Case 2.
	 * test unknown parameter in getValue
	 */
	@Test
	public void testCase_02() {
		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorGetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		aggregationListener.reset();
		execAndCheckAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						dynSat2BcMtq1RevDefinition))),
				aggregationListener,
				startTime + TIMEOUT);
		
		// call getValue with getValueParams
		// check response as list of 2 items:
		// - {
		// -- aggregationRef={"fr.cnes.mission.sat2", "AGG_BC_MTQ1", version=1}
		// -- timestamp=1/1/2025 09:01:00
		// -- parameterValues={
		// --- {validityState=VALID, rawValue=12.00, convertedValue=null}
		// --- {validityState=VALID, rawValue=22.00, convertedValue=null}
		// --- {validityState=VALID, rawValue=1, convertedValue="ENABLED"}}}
		// - {
		// -- aggregationRef={"fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1}
		// -- timestamp=1/1/2025 02:11:00
		// -- parameterValues={
		// --- {validityState=VALID, rawValue=22.00, convertedValue=null}
		// --- {validityState=VALID, rawValue=12.00, convertedValue=null}}}
		aggregationListener.reset();
		AggregationValueList expected =
				new AggregationValueList(new ArrayList<>(Arrays.asList(
						new AggregationValue(
								AggregationErrorDataset.sat2BcMtq1Ref,
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										MTQ1VOLTAGE_1200_VALUE,
										MTQ1VOLTAGE_2200_VALUE,
										MTQ1ENABLED_ENABLED_VALUE)))),
						new AggregationValue(
								dynSat2BcMtq1RevRef,
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										MTQ1VOLTAGE_2200_VALUE,
										MTQ1VOLTAGE_1200_VALUE)))))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

		// call removeAggregation with removeAggregParams
		// removeAggregParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1_REV"}
		aggregationListener.reset();
		execAndCheckRemoveAggregation(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT);

		// call getValue with getValueParams
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		aggregationListener.reset();
		execAndCheckErrorGetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

	}

	/**
	 * Test Case 3.
	 * test inconsistent parameters in setReportingPeriod
	 */
	@Test
	public void testCase_03() {
		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// - reportInterval=-1s
		// check ERROR message with code Invalid
		execAndCheckErrorSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				DURATION__1,
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				null);
		
	}

	/**
	 * Test Case 4.
	 * test an invalid identity in addAggregation
	 */
	@Test
	public void testCase_04() {
		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "INVALID_ID", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						new AggregationDefinition(
								new ObjectIdentity(
										Constant.DOMAIN_SAT2,
										new Identifier("INVALID_ID"),
										new UInteger(1)),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterErrorDataset.sat2Mtq1VoltageRef,
										ParameterErrorDataset.sat1Mtq1VoltageRef))))))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_0);
		
	}

	/**
	 * Test Case 5.
	 * test a duplicate identity in addAggregation
	 */
	@Test
	public void testCase_05() {
		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						new AggregationDefinition(
								getAggregationIdentity(AggregationErrorDataset.sat1BcMtq1Ref),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterErrorDataset.sat2Mtq1VoltageRef,
										ParameterErrorDataset.sat1Mtq1VoltageRef))))))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.DUPLICATE_ERROR_NUMBER,
				EXTRA_UIL_0);
		
	}

	/**
	 * Test Case 6.
	 * test atomicity of enableReporting operation
	 */
	@Test
	public void testCase_06() {
		System.out.println("Running: testCase_06()");
		long 
		startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"UNKNOWN", "AGG_BC_MTQ1_REV"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorEnableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_UNKNOWN,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_0);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1_REV"}
		// check response as a singleton list:
		// - {reportingEnabled=false, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

	}

	/**
	 * Test Case 7.
	 * test atomicity of disableReporting operation
	 */
	@Test
	public void testCase_07() {
		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "UNKNOWN"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorEnableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_UNKNOWN))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// check response as a singleton list:
		// - {reportingEnabled=true, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

	}

	/**
	 * Test Case 8.
	 * test atomicity of setReportingPeriod operation
	 */
	@Test
	public void testCase_08() {
		System.out.println("Running: testCase_08()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// - reportInterval=-1s
		// check ERROR message with code Invalid
		// check the extraInfo field as list of 2 items: {0, 1}
		execAndCheckErrorSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				DURATION__1,
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(0),
						new UInteger(1)))));

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

	}

	/**
	 * Test Case 9.
	 * test atomicity of addAggregation operation
	 */
	@Test
	public void testCase_09() {
		System.out.println("Running: testCase_09()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		// - {
		// -- identity: ("fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		// check ERROR message with code Duplicate
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						dynSat2BcMtq1RevDefinition,
						new AggregationDefinition(
								getAggregationIdentity(AggregationErrorDataset.sat1BcMtq1Ref),
								"",
								null,
								new ObjectRefList(new ArrayList<> (Arrays.asList(
										ParameterErrorDataset.sat2Mtq1VoltageRef,
										ParameterErrorDataset.sat1Mtq1VoltageRef))))))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.DUPLICATE_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1_REV"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 0
		aggregationListener.reset();
		execAndCheckErrorListDefinition(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

	/**
	 * Test Case 10.
	 * test atomicity of removeAggregation operation
	 */
	@Test
	public void testCase_10() {
		System.out.println("Running: testCase_10()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call removeAggregation with removeAggregParams
		// removeAggregParams={
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1", "AGG_BC_MTQ1_REV"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorRemoveAggregation(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1,
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1"}
		// check response as singleton list:
		// - {reportingEnabled=false, reportInterval=301s}
		aggregationListener.reset();
		ReportConfigurationList expected =
				new ReportConfigurationList(new ArrayList<> (Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_301))));
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);

	}

	/**
	 * Test Case 11.
	 * test an invalid value for category
	 */
	@Test
	public void testCase_11() {
		System.out.println("Running: testCase_11()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: "UNKNOWN CATEGORY"
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						new AggregationDefinition(
								dynSat2BcMtq1RevDefinition.getObjectIdentity(),
								"",
								new Identifier("UNKNOWN CATEGORY"),
								dynSat2BcMtq1RevDefinition.getParameters())))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

	/**
	 * Test Case 12.
	 * test an empty parameters list
	 */
	@Test
	public void testCase_12() {
		System.out.println("Running: testCase_12()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {}}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						new AggregationDefinition(
								dynSat2BcMtq1RevDefinition.getObjectIdentity(),
								"",
								null,
								new ObjectRefList())))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

	/**
	 * Test Case 13.
	 * test a duplicate identity in addAggregation
	 */
	@Test
	public void testCase_13() {
		System.out.println("Running: testCase_13()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call addAggregation with addAggregParams
		// addAggregParams={
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}
		// - {
		// -- identity: ("fr.cnes.mission.sat2", "AGG_BC_MTQ1_REV", version=1)
		// -- description: ""
		// -- category: null
		// -- parameters: {
		// --- {domain="fr.cnes.mission.sat2", key="ATT_BC_MTQ1VOLTAGE", version=1}
		// --- {domain="fr.cnes.mission.sat1", key="ATT_BC_MTQ1VOLTAGE", version=1}}}
		// check ERROR message with code Duplicate
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorAddAggregation(
				new AggregationDefinitionList(new ArrayList<>(Arrays.asList(
						dynSat2BcMtq1RevDefinition,
						dynSat2BcMtq1RevDefinition))),
				aggregationListener,
				startTime + TIMEOUT,
				MCHelper.DUPLICATE_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call listDefinition with listDefinitionParams
		// listDefinitionParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"AGG_BC_MTQ1_REV"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 0
		aggregationListener.reset();
		execAndCheckErrorListDefinition(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_BC_MTQ1_REV))),
				aggregationListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

}
