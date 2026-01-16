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

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.ActionListener.MonitorExecutionUpdate;
import org.ccsds.mo.mc.testbed.backends.ActionBasicDataset;
import org.ccsds.mo.mc.testbed.backends.ActionDefaultDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.action.consumer.ActionAdapter;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEvent;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionEvent;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ActionStartEvent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * AC_3_Extended_Execution_Test implements the test scenario #AC-3.
 */
public class AC_3_Extended_Execution_Test extends ActionTestClient {
	
	static ActionListener actionListener = new ActionListener();
	static Identifier subscriptionId = new Identifier("31");

	private static final ActionDefaultDataset backend = new ActionDefaultDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AC_3_Extended_Execution_Test.class.getName()));
		setUp.setUp(backend, null, null, null, null,
				true, false, false, false, false);
		actionConsumerStub = setUp.getActionConsumer();

		// call monitorExecution.register with subscription
		// subscription=
		// - subscriptionId=31
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		execAndCheckMonitorExecutionRegister(
				new Subscription(
						subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				actionListener,
				System.currentTimeMillis() + TIMEOUT);
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AC_3_Extended_Execution_Test.class.getName() + " tearDownClass()");

		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscriptionId);
		actionListener.reset();
		execAndCheckMonitorExecutionDeregister(
				subscriptions,
				actionListener,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * Test the nominal case for an action execution.
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);
		
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=311
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - actionRef version=1
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=10000} }
		// check ACK message
		Long requestId = new Long(311);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1ChgTAbsValRef,
						null,
						false,
						false,
						false,
						CHGTABSVAL_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);

		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * Requires previous execution of Test Case 1.
	 * Test the nominal case with monitoring
	 */
	@Test
	public void testCase_02() {
		// additional statements for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=321
		// - actionRef domain="fr.cnes.mission.sat2"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - actionRef version=1
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=true
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=10000} }
		// check ACK message
		Long requestId = new Long(321);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat2ChgTAbsValRef,
						null,
						true,
						true,
						true,
						CHGTABSVAL_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription:
		// ActionStartEvent: success=true
		// ActionCompleteEvent: success=true
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[2];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT2,
				requestId,
				Constant.ID_CHGTABSVAL,
				ActionCategory.DEFAULT,
				new ActionStartEvent(true));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT2,
				requestId,
				Constant.ID_CHGTABSVAL,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * Requires previous execution of Test Case 2.
	 * Test the unfilled version for actionRef.
	 */
	@Test
	public void testCase_03() {
		// additional statements for dependent tests
		TestDependency.before(2, this, "testCase_02", 3);

		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=331
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - actionRef version=0
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="ok"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(331);
		ObjectRef<ActionDefinition> actionRef =
				new ObjectRef<>(
						Constant.DOMAIN_SAT1,
						ActionDefinition.TYPE_ID.getTypeId(),
						Constant.ID_DEFATTITUDE,
						new UInteger(0));
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						actionRef,
						null,
						false,
						false,
						false,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);

		// additional statements for dependent tests
		TestDependency.after();
	}
}
