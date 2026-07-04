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

import org.ccsds.mo.mc.testbed.backends.ActionBasicDataset;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * AC_1_Basic_Execution_Test implements the test scenario #AC-1.
 */
public class AC_1_Basic_Execution_Test extends ActionTestClient {

	static ActionListener actionListener = new ActionListener();

	private static final ActionBasicDataset backend = new ActionBasicDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AC_1_Basic_Execution_Test.class.getName()));
		setUp.setUp(backend, null, null, null, null,
				true, false, false, false, false);
		actionConsumerStub = setUp.getActionConsumer();
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AC_1_Basic_Execution_Test.class.getName() + " tearDownClass()");
		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * Simplest nominal case for an action execution
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=111
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=10000} }
		// check ACK message
		Long requestId = new Long(111);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionBasicDataset.sat1ChgTAbsValRef,
						null,
						false,
						false,
						false,
						CHGTABSVAL_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
	}

}
