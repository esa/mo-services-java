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

import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterErrorDataset;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * PA_9_Errors_Test implements the test scenario #PA-9.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PA_9_Errors_Test extends ParameterTestClient {

	static ParameterListener parameterListener = new ParameterListener();
	static Identifier subscriptionId;

	// initialize BackendTimer with timeOrigin
	// timeOrigin=1/1/2025 09:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 9, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterErrorDataset backend = new ParameterErrorDataset(timer);

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PA_9_Errors_Test.class.getName()));
		setUp.setUp(null, null, null, null, backend,
				false, false, false, false, true);
		parameterConsumerStub = setUp.getParameterConsumer();
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

		parameterListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 09:01:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 09:01:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check response as list of 2 items:
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=12.00, convertedValue=null}
		// -- paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		//  - timestamp=?
		//  - value={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
					Constant.ID_MTQ1VOLTAGE,
					Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				new ParameterValueList(new ArrayList<> (Arrays.asList(
						new ParameterValue(
								ParameterErrorDataset.sat1Mtq1VoltageRef,
								null,  // timestamp, unchecked
								null,  // samplingTime, unchecked
								new ParameterValueData(
										ValidityState.VALID,
										NA_DOUBLE_1200.getValue(),
										null)),
						new ParameterValue(
								ParameterErrorDataset.sat1Mtq1EnabledRef,
								null,  // timestamp, unchecked
								null,  // samplingTime, unchecked
								new ParameterValueData(
										ValidityState.VALID,
										NA_UINT_1.getValue(),
										NA_STRING_ENABLED.getValue()))))));

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PA_9_Errors_Test.class.getName() + " tearDownClass()");

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * test ambiguity with a null domain
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		// call getValue with getValueParams
		// getValueParams=
		// - domain=null
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check ERROR message with code Ambiguous
		// check the extraInfo field as a singleton list holding element 0
		parameterListener.reset();
		execAndCheckErrorGetValue(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				MCHelper.AMBIGUOUS_ERROR_NUMBER,
				EXTRA_UIL_0);

	}

	/**
	 * Test Case 2.
	 * test unknown parameter
	 */
	@Test
	public void testCase_02() {
		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		// call getValue with getValueParams
		// getValueParams=
		// - domain=domain="fr.cnes.mission.sat2"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		parameterListener.reset();
		execAndCheckErrorGetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

	}

	/**
	 * Test Case 3.
	 * test inconsistent parameters
	 */
	@Test
	public void testCase_03() {
		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		// call setValue with setValueParams
		// setValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// - newRawValues={19.03, 1}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 1
		parameterListener.reset();
		execAndCheckErrorSetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1903,
						NA_UINT_1))),
				parameterListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_1);
		
		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=12.00, convertedValue=null}
		parameterListener.reset();
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				new ParameterValueList(new ArrayList<> (Arrays.asList(
						new ParameterValue(
								ParameterErrorDataset.sat1Mtq1VoltageRef,
								null,  // timestamp, unchecked
								null,  // samplingTime, unchecked
								new ParameterValueData(
										ValidityState.VALID,
										NA_DOUBLE_1200.getValue(),
										null))))));

	}

	/**
	 * Test Case 4.
	 * test setting a read only parameter
	 */
	@Test
	public void testCase_04() {
		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		// call setValue with setValueParams
		// setValueParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// - newRawValues={19.04}
		// check ERROR message with code Read Only
		// check the extraInfo field as a singleton list holding element 0
		parameterListener.reset();
		execAndCheckErrorSetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1904))),
				parameterListener,
				startTime + TIMEOUT,
				MCHelper.READ_ONLY_ERROR_NUMBER,
				EXTRA_UIL_0);
		
		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat2"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat2", "ATT_BC_MTQ1VOLTAGE", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=22.00, convertedValue=null}
		parameterListener.reset();
		execAndCheckGetValue(
				Constant.DOMAIN_SAT2,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				new ParameterValueList(new ArrayList<> (Arrays.asList(
						new ParameterValue(
								ParameterErrorDataset.sat2Mtq1VoltageRef,
								null,  // timestamp, unchecked
								null,  // samplingTime, unchecked
								new ParameterValueData(
										ValidityState.VALID,
										NA_DOUBLE_2200.getValue(),
										null))))));

	}

	/**
	 * Test Case 5.
	 * test invalid value type
	 */
	@Test
	public void testCase_05() {
		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		// call setValue with setValueParams
		// setValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// - newRawValues={19.05, "DISABLED"}
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 1
		parameterListener.reset();
		execAndCheckErrorSetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_DOUBLE_1905,
						NA_STRING_DISABLED))),
				parameterListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_1);
		
		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1VOLTAGE", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=12.00, convertedValue=null}
		parameterListener.reset();
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				new ParameterValueList(new ArrayList<> (Arrays.asList(
						new ParameterValue(
								ParameterErrorDataset.sat1Mtq1VoltageRef,
								null,  // timestamp, unchecked
								null,  // samplingTime, unchecked
								new ParameterValueData(
										ValidityState.VALID,
										NA_DOUBLE_1200.getValue(),
										null))))));

	}

	/**
	 * Test Case 6.
	 * test atomicity of enableReporting operation
	 */
	@Test
	public void testCase_06() {
		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		// call enableReporting with enableReportParams
		// enableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"UNKNOWN", "ATT_BC_MTQ1ENABLED"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 0
		parameterListener.reset();
		execAndCheckErrorEnableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_UNKNOWN,
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_0);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as a singleton list:
		// - {reportingEnabled=false, reportInterval=300s}
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				new ReportConfigurationList(new ArrayList<>(Arrays.asList(
						new ReportConfiguration(
								false,
								DURATION_300)))));

	}

	/**
	 * Test Case 7.
	 * test atomicity of disableReporting operation
	 */
	@Test
	public void testCase_07() {
		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		// call disableReporting with disableReportParams
		// disableReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "UNKNOWN"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		parameterListener.reset();
		execAndCheckErrorDisableReporting(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_UNKNOWN))),
				parameterListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as a singleton list:
		// - {reportingEnabled=true, reportInterval=300s}
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				new ReportConfigurationList(new ArrayList<>(Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_300)))));

	}

	/**
	 * Test Case 8.
	 * test atomicity of setReportingPeriod operation
	 */
	@Test
	public void testCase_08() {
		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		// call setReportingPeriod with setReportParams
		// setReportParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE", "ATT_BC_MTQ1ENABLED"}
		// - reportInterval=60s
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 1
		parameterListener.reset();
		execAndCheckErrorSetReportingPeriod(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE,
						Constant.ID_MTQ1ENABLED))),
				DURATION_60,
				parameterListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				EXTRA_UIL_1);

		// call getReportingConfiguration with getReportConfigParams
		// getReportConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1VOLTAGE"}
		// check response as a singleton list:
		// - {reportingEnabled=true, reportInterval=300s}
		parameterListener.reset();
		execAndCheckGetReportingConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE))),
				parameterListener,
				startTime + TIMEOUT,
				new ReportConfigurationList(new ArrayList<>(Arrays.asList(
						new ReportConfiguration(
								true,
								DURATION_300)))));

	}

}
