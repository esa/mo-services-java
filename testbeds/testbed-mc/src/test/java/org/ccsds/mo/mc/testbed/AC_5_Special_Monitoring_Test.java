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
public class AC_5_Special_Monitoring_Test extends ActionTestClient {

	static ActionListener actionListener1 = new ActionListener();
	static ActionListener actionListener2 = new ActionListener();
	static Identifier subscription1Id = new Identifier("51");
	static Identifier subscription2Id = new Identifier("52");

	private static final ActionDefaultDataset backend = new ActionDefaultDataset();

	private static int testStep = 0;
	private static boolean testStatus = true;

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AC_5_Special_Monitoring_Test.class.getName()));
		setUp.setUp(backend, null, null, null, null,
				true, false, false, false, false);
		actionConsumerStub = setUp.getActionConsumer();

		// call monitorExecution.register with subscription-1
		// subscription-1=
		// - subscriptionId=51
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		execAndCheckMonitorExecutionRegister(
				new Subscription(
						subscription1Id,
						Constant.DOMAIN_WILDCARD,
						null, null),
				actionListener1,
				System.currentTimeMillis() + TIMEOUT);
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AC_5_Special_Monitoring_Test.class.getName() + " tearDownClass()");

		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscription1Id);
		subscriptions.add(subscription2Id);
		actionListener1.reset();
		execAndCheckMonitorExecutionDeregister(
				subscriptions,
				actionListener1,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 * Test a complete nominal case for an action execution monitoring.
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();

		actionListener1.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=511
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=true
		// - stageProgressRequired=true
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="wait"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId = new Long(511);
		NullableAttributeList argumentValues = (NullableAttributeList) DEFATTITUDE_DFLT_ARGS.clone();
		argumentValues.set(2, NA_STRING_WAIT);
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
				actionListener1,
				startTime + TIMEOUT);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription-1:
		// - ActionStartEvent: success=true
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=1
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
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(1)));
		waitAndCheckForUpdates(actionListener1, startTime + TIMEOUT, targetUpdates);

		// call monitorExecution.register with subscription-2
		// subscription-2=
		// - subscriptionId=52
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		actionListener2.reset();
		execAndCheckMonitorExecutionRegister(
				new Subscription(
						subscription2Id,
						Constant.DOMAIN_WILDCARD,
						null, null),
				actionListener2,
				startTime + TIMEOUT);
		
		actionListener1.reset();
		actionListener2.reset();
		// call backend:resumeAction with requestId=511
		System.out.println("call backend:resumeAction");
		backend.resumeAction(requestId);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription-1 and subscription-2:
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=2
		// - ActionInProgressEvent: success=true, stageCount=3, executionStage=3
		// - ActionCompleteEvent: success=true
		targetUpdates = new MonitorExecutionUpdate[3];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(2)));
		targetUpdates[1] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionInProgressEvent(true, new UInteger(3), new UInteger(3)));
		targetUpdates[2] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener1, startTime + TIMEOUT, targetUpdates);
		waitAndCheckForUpdates(actionListener2, startTime + TIMEOUT, targetUpdates);
	}

}
