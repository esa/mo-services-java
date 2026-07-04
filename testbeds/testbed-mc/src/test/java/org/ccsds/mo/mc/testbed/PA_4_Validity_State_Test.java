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
import org.ccsds.mo.mc.testbed.backends.ParameterDatasetForValidityState;
import org.ccsds.mo.mc.testbed.backends.ParameterDefaultDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
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
 * PA_4_Validity_State_Test implements the test scenario #PA-4.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PA_4_Validity_State_Test extends ParameterTestClient {

	static ParameterListener parameterListener = new ParameterListener();
	static Identifier subscriptionId;

	// initialize BackendTimer with timeOrigin
	// timeOrigin=1/1/2025 04:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 4, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterDatasetForValidityState backend = new ParameterDatasetForValidityState(timer);

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PA_4_Validity_State_Test.class.getName()));
		setUp.setUp(null, null, null, null, backend,
				false, false, false, false, true);
		parameterConsumerStub = setUp.getParameterConsumer();
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PA_4_Validity_State_Test.class.getName() + " tearDownClass()");
		
		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * test state INVALID_RAW
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:01:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 04:01:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=INVALID_RAW, rawValue=null, convertedValue=null}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.INVALID_RAW,
								null,
								null)))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * test state VALID
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:02:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 04:02:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=VALID, rawValue=1, convertedValue="ENABLED"}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.VALID,
								NA_UINT_1.getValue(),
								NA_STRING_ENABLED.getValue())))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * test state EXPIRED
	 * Requires previous execution of Test Case 2.
	 */
	@Test
	public void testCase_03() {
		// additional statement for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:13:00 (ie +11:00)
		System.out.println("skip time to 1/1/2025 04:13:00");
		timer.skip(11*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=EXPIRED, rawValue=1, convertedValue="ENABLED"}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.EXPIRED,
								NA_UINT_1.getValue(),
								NA_STRING_ENABLED.getValue())))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
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
	 * test state INVALID
	 * Requires previous execution of Test Case 3.
	 */
	@Test
	public void testCase_04() {
		// additional statement for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:14:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 04:14:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=INVALID, rawValue=-1, convertedValue="UNKNOWN"}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.INVALID,
								NA_UINT_8.getValue(),
								NA_STRING_UNKNOWN.getValue())))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 5.
	 * test INVALID EXPIRED combination
	 * Requires previous execution of Test Case 4.
	 */
	@Test
	public void testCase_05() {
		// additional statement for dependent tests
		TestDependency.before(4, this, "testCase_04", 5);

		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:25:00 (ie +11:00)
		System.out.println("skip time to 1/1/2025 04:25:00");
		timer.skip(11*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=INVALID, rawValue=-1, convertedValue="UNKNOWN"}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.INVALID,
								NA_UINT_8.getValue(),
								NA_STRING_UNKNOWN.getValue())))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 6.
	 * test state INVALID_CONVERSION
	 * Requires previous execution of Test Case 5.
	 */
	@Test
	public void testCase_06() {
		// additional statement for dependent tests
		TestDependency.before(5, this, "testCase_05", 6);

		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:26:00 (ie +1:00)
		System.out.println("skip time to 1/1/2025 04:26:00");
		timer.skip(1*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=INVALID_CONVERSION, rawValue=2, convertedValue=null}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.INVALID_CONVERSION,
								NA_UINT_2.getValue(),
								null)))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 7.
	 * test INVALID_CONVERSION EXPIRED combination
	 * Requires previous execution of Test Case 6.
	 */
	@Test
	public void testCase_07() {
		// additional statement for dependent tests
		TestDependency.before(6, this, "testCase_06", 7);

		System.out.println("Running: testCase_07()");
		long startTime = System.currentTimeMillis();

		// update BackendTimer with now
		// now=1/1/2025 04:37:00 (ie +11:00)
		System.out.println("skip time to 1/1/2025 04:37:00");
		timer.skip(11*60*1000);

		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"ATT_BC_MTQ1ENABLED"}
		// check response as singleton list:
		// - paramRef={"fr.cnes.mission.sat1", "ATT_BC_MTQ1ENABLED", version=1}
		// - timestamp=?
		// - value={validityState=INVALID_CONVERSION, rawValue=2, convertedValue=null}
		parameterListener.reset();
		ParameterValueList expected = new ParameterValueList(new ArrayList<> (Arrays.asList(
				new ParameterValue(
						ParameterDefaultDataset.sat1Mtq1EnabledRef,
						null,  // timestamp, unchecked
						null,  // samplingTime, unchecked
						new ParameterValueData(
								ValidityState.INVALID_CONVERSION,
								NA_UINT_2.getValue(),
								null)))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1ENABLED))),
				parameterListener,
				startTime + TIMEOUT,
				expected);

		// additional statement for dependent tests
		TestDependency.after();
	}

}
