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
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.action.consumer.ActionAdapter;
import org.ccsds.moims.mo.mc.structures.ActionCategory;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEvent;
import org.ccsds.moims.mo.mc.structures.ActionDefinition;
import org.ccsds.moims.mo.mc.structures.ActionEvent;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;
import org.ccsds.moims.mo.mc.structures.ActionInProgressEvent;
import org.ccsds.moims.mo.mc.structures.ActionStartEvent;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class AC_4_Extended_Monitoring_Test extends ActionTestClient {

	static ActionListener actionListener = new ActionListener();
	static Identifier subscriptionId = new Identifier("41");

	private static final ActionDefaultDataset backend = new ActionDefaultDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AC_4_Extended_Monitoring_Test.class.getName()));
		setUp.setUp(backend, null, null, null, null,
				true, false, false, false, false);
		actionConsumerStub = setUp.getActionConsumer();

		// call monitorExecution.register with subscription
		// subscription=
		// - subscriptionId=41
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
		System.out.println("Entered: " + AC_4_Extended_Monitoring_Test.class.getName() + " tearDownClass()");

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
	 * Test a complete nominal case for an action execution monitoring.
	 */
	@Test
	public void testCase_01() {
		// additional statements for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);
		
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=411
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=true
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="ok"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(411);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						true,
						true,
						true,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={requestId=411, actionKey="MIS_TC_DEFATTITUDE", actionCategory=DEFAULT}:
		// - ActionStartEvent: success=true
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=1
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=2
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=3
		// - ActionCompleteEvent: success=true
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[5];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionStartEvent(true));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(1)));
		targetUpdates[2] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(2)));
		targetUpdates[3] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(3)));
		targetUpdates[4] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);
		
		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * Requires previous execution of Test Case 1.
	 * Test partial sending of ActionEvents
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
		// - requestId=421
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=true
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="ok"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(421);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						false,
						true,
						true,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription:
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=1
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=2
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=3
		// - ActionCompleteEvent: success=true
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[4];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(1)));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(2)));
		targetUpdates[2] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(3)));
		targetUpdates[3] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 3.
	 * Requires previous execution of Test Case 2.
	 * Test partial sending of ActionEvents
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
		// - requestId=411
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=false
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="ok"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(431);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						true,
						false,
						true,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription:
		// - ActionStartEvent: success=true
		// - ActionCompleteEvent: success=true
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[2];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionStartEvent(true));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 4.
	 * Requires previous execution of Test Case 3.
	 * Test partial sending of ActionEvents
	 */
	@Test
	public void testCase_04() {
		// additional statements for dependent tests
		TestDependency.before(3, this, "testCase_03", 4);

		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=441
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=true
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="ok"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(441);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						true,
						true,
						false,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription:
		// - ActionStartEvent: success=true
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=1
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=2
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=3
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[4];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionStartEvent(true));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(1)));
		targetUpdates[2] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(2)));
		targetUpdates[3] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(3)));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// additional statements for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 5.
	 * Requires previous execution of Test Case 4.
	 * Test interrupted sequence of ActionInProgressEvent messages
	 */
	@Test
	public void testCase_05() {
		// additional statements for dependent tests
		TestDependency.before(4, this, "testCase_04", 5);

		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();

		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=451
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=true
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="fail-2"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(451);
		NullableAttributeList argumentValues = (NullableAttributeList) DEFATTITUDE_DFLT_ARGS.clone();
		argumentValues.set(2, NA_STRING_FAIL2);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						true,
						true,
						true,
						argumentValues),
				actionListener,
				startTime + TIMEOUT);
		
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription:
		// - ActionStartEvent: success=true
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=1
		// - ActionInProgressEvent: success=false, stageCount=3, executionStage=2
		// - ActionCompleteEvent: success=false
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[4];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionStartEvent(true));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(1)));
		targetUpdates[2] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(false, new UInteger(3), new UInteger(2)));
		targetUpdates[3] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(false));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// additional statements for dependent tests
		TestDependency.after();
	}
}
