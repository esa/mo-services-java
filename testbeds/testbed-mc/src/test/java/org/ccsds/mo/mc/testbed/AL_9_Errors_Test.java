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

import org.ccsds.mo.mc.testbed.backends.AlertErrorDataset;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mc.MCHelper;
import org.ccsds.moims.mo.mc.structures.AlertConfiguration;
import org.ccsds.moims.mo.mc.structures.AlertConfigurationList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AL_9_Errors_Test implements the test scenario #AL-9.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AL_9_Errors_Test extends AlertTestClient {

	static AlertListener alertListener = new AlertListener();
	static Identifier subscriptionId;

	private static final AlertErrorDataset backend = new AlertErrorDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AL_9_Errors_Test.class.getName()));
		setUp.setUp(null, null, backend, null, null,
				false, false, true, false, false);
		alertConsumerStub = setUp.getAlertConsumer();

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AL_9_Errors_Test.class.getName() + " tearDownClass()");

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

		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain=null
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check ERROR message with code Ambiguous
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorGetAlertConfiguration(
				null,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				MCHelper.AMBIGUOUS_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(0)))));
		
	}

	/**
	 * Test Case 2.
	 * test unknown alert
	 */
	@Test
	public void testCase_02() {
		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_LOW", "MTQ1VOLTAGE_HIGH"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 0
		execAndCheckErrorGetAlertConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_LOW,
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(0)))));
		
	}

	/**
	 * Test Case 3.
	 * test atomicity of enableGeneration operation
	 */
	@Test
	public void testCase_03() {
		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call enableGeneration with enableGenParams
		// enableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH", "MTQ1VOLTAGE_LOW"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorEnableGeneration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH,
						Constant.ID_MTQ1VOLTAGE_LOW))),
				alertListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(1)))));
		
		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check response as singleton list:
		// - {generationEnabled=false}
		execAndCheckGetAlertConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				new AlertConfigurationList(new ArrayList<>(Arrays.asList(
						new AlertConfiguration(false)))));
		
	}

	/**
	 * Test Case 4.
	 * test atomicity of disableGeneration operation
	 */
	@Test
	public void testCase_04() {
		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		alertListener.reset();
		// call enableGeneration with enableGenParams
		// enableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check ACK message
		execAndCheckEnableGeneration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT);

		alertListener.reset();
		// call disableGeneration with disableGenParams
		// disableGenParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH", "MTQ1VOLTAGE_LOW"}
		// check ERROR message with code Unknown
		// check the extraInfo field as a singleton list holding element 1
		execAndCheckErrorDisableGeneration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH,
						Constant.ID_MTQ1VOLTAGE_LOW))),
				alertListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(1)))));
		
		alertListener.reset();
		// call getAlertConfiguration with getAlertConfigParams
		// getAlertConfigParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"MTQ1VOLTAGE_HIGH"}
		// check response as singleton list:
		// - {generationEnabled=true}
		execAndCheckGetAlertConfiguration(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<> (Arrays.asList(
						Constant.ID_MTQ1VOLTAGE_HIGH))),
				alertListener,
				startTime + TIMEOUT,
				new AlertConfigurationList(new ArrayList<>(Arrays.asList(
						new AlertConfiguration(true)))));
		
	}

}
