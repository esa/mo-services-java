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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.ActionListener.MonitorExecutionUpdate;
import org.ccsds.mo.mc.testbed.backends.ActionBasicDataset;
import org.ccsds.mo.mc.testbed.backends.ActionDefaultDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
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
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.MCHelper;
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
public class AC_9_Execution_Error_Test extends ActionTestClient {

	static ActionListener actionListener = new ActionListener();
	static Identifier subscriptionId = new Identifier("91");

	private static final ActionDefaultDataset backend = new ActionDefaultDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AC_9_Execution_Error_Test.class.getName()));
		setUp.setUp(backend, null, null, null, null,
				true, false, false, false, false);
		actionConsumerStub = setUp.getActionConsumer();

		// call monitorExecution.register with subscription
		// subscription=
		// - subscriptionId=91
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
		System.out.println("Entered: " + AC_9_Execution_Error_Test.class.getName() + " tearDownClass()");

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
	 * execute an action which is not declared
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=911
		// - actionRef domain="fr.cnes.mission.unknown"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=10000} }
		// check ERROR message with code Unknown
		Long requestId = new Long(911);
		System.out.println("call execute with executionRequest");
		execAndCheckErrorExecute(
				new ActionExecutionRequest(
						requestId,
						new ObjectRef<>(
								Constant.DOMAIN_UNKNOWN,
								ActionDefinition.TYPE_ID.getTypeId(),
								Constant.ID_CHGTABSVAL,
								new UInteger(0)),
						null,
						false,
						false,
						false,
						CHGTABSVAL_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT,
				MALHelper.UNKNOWN_ERROR_NUMBER,
				null);
	}

	/**
	 * Test Case 2.
	 * reuse the same id in two executionRequests
	 */
	@Test
	public void testCase_02() {
		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=921
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=10000} }
		Long requestId = new Long(921);
		ActionExecutionRequest request =
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1ChgTAbsValRef,
						null,
						false,
						false,
						false,
						CHGTABSVAL_DFLT_ARGS);
		System.out.println("call execute with executionRequest");
		execAndCheckExecute(
				request,
				actionListener,
				startTime + TIMEOUT);

		actionListener.reset();
		// call execute again with same executionRequest
		// check ERROR message with code Duplicate
		execAndCheckErrorExecute(
				request,
				actionListener,
				startTime + TIMEOUT,
				MCHelper.DUPLICATE_ERROR_NUMBER,
				null);
	}

	/**
	 * Test Case 3.
	 * provide an incorrect null value as arguments list of the executionRequest
	 */
	@Test
	public void testCase_03() {
		System.out.println("Running: testCase_03()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=931
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues=null
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		Long requestId = new Long(931);
		System.out.println("call execute with executionRequest");
		execAndCheckErrorExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1ChgTAbsValRef,
						null,
						false,
						false,
						false,
						null),
				actionListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(0)))));
	}

	/**
	 * Test Case 4.
	 * provide an incorrect typed value as argument of the executionRequest
	 */
	@Test
	public void testCase_04() {
		System.out.println("Running: testCase_04()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=941
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="SAT_TC_CHGTABSVAL"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value="incorrect type"} }
		// check ERROR message with code Invalid
		// check the extraInfo field as a singleton list holding element 0
		Long requestId = new Long(941);
		System.out.println("call execute with executionRequest");
		execAndCheckErrorExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1ChgTAbsValRef,
						null,
						false,
						false,
						false,
						new NullableAttributeList(new ArrayList<> (Arrays.asList(
								new NullableAttribute(new Union(new String("incorrect type"))))))),
				actionListener,
				startTime + TIMEOUT,
				MCHelper.INVALID_ERROR_NUMBER,
				new UIntegerList(new ArrayList<>(Arrays.asList(
						new UInteger(0)))));
	}

	/**
	 * Test Case 5.
	 * trigger the provider specific check to fail
	 */
	@Test
	public void testCase_05() {
		System.out.println("Running: testCase_05()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest
		// executionRequest=
		// - requestId=951
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=false
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="error Rejected"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ERROR message with code Rejected
		Long requestId = new Long(951);
		NullableAttributeList argumentValues = (NullableAttributeList) DEFATTITUDE_DFLT_ARGS.clone();
		argumentValues.set(2, NA_STRING_ERROR_REJECTED);
		System.out.println("call execute with executionRequest");
		execAndCheckErrorExecute(
				new ActionExecutionRequest(
						requestId,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						false,
						false,
						false,
						argumentValues),
				actionListener,
				startTime + TIMEOUT,
				MCHelper.REJECTED_ERROR_NUMBER,
				null);
		// check the extraInfo field as a String if not null
		if (actionListener.error.getExtraInformation() != null) {
			// the Java mapping types the extraInformation field as a Java Object
			// it is unclear how the Java mapping types the returned String
			// we test the Java String and a MAL Union holding a String
			Object extraInfo = actionListener.error.getExtraInformation();
			if (!(extraInfo instanceof String ||
					extraInfo instanceof Union && ((Union) extraInfo).isStringAttribute()))
				unitTestFail("Wrong type for extraInfo field, expected String, was " +
						extraInfo.getClass().getName());
		}
	}

	/**
	 * Test Case 6.
	 * check action execution depends on provider specific check
	 */
	@Test
	public void testCase_06() {
		System.out.println("Running: testCase_06()");
		long startTime = System.currentTimeMillis();
		
		actionListener.reset();
		// call execute with executionRequest-1
		// executionRequest-1=
		// - requestId=961
		// - actionRef domain="fr.cnes.mission.sat1"
		// - actionRef key="MIS_TC_DEFATTITUDE"
		// - actionRef version=0
		// - source=null
		// - stageStartedRequired=false
		// - stageProgressRequired=false
		// - stageCompletedRequired=true
		// - argumentsValues={ {value=2.1E9}, {value=6.0E1}, {value="skip"}, {value=0}, {value=1.3E0}, {value=null}, {value=null} }
		// check ACK message
		Long requestId1 = new Long(961);
		NullableAttributeList argumentValues = (NullableAttributeList) DEFATTITUDE_DFLT_ARGS.clone();
		argumentValues.set(2, NA_STRING_SKIP);
		System.out.println("call execute with executionRequest1");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId1,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						false,
						false,
						true,
						argumentValues),
				actionListener,
				startTime + TIMEOUT);

		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription
		// - ActionCompleteEvent: success=true
		MonitorExecutionUpdate[] targetUpdates = new MonitorExecutionUpdate[1];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId1,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

		// call execute with executionRequest-2
		// executionRequest-2= same as executionRequest-1 but
		// -requestId=962
		// check ERROR message with code Rejected
		actionListener.reset();
		Long requestId2 = new Long(962);
		System.out.println("call execute with executionRequest2");
		execAndCheckErrorExecute(
				new ActionExecutionRequest(
						requestId2,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						false,
						false,
						true,
						DEFATTITUDE_DFLT_ARGS),
				actionListener,
				startTime + TIMEOUT,
				MCHelper.REJECTED_ERROR_NUMBER,
				new Union(Constant.STR_SKIPPED));
		
		// call execute with executionRequest-3
		// executionRequest-3= same as executionRequest-1 but
		// -requestId=963
		// -- {value="ok"}
		// check ACK message
		actionListener.reset();
		Long requestId3 = new Long(963);
		argumentValues = (NullableAttributeList) DEFATTITUDE_DFLT_ARGS.clone();
		argumentValues.set(2, NA_STRING_OK);
		System.out.println("call execute with executionRequest3");
		execAndCheckExecute(
				new ActionExecutionRequest(
						requestId3,
						ActionDefaultDataset.sat1DefAttitude2Ref,
						null,
						false,
						false,
						true,
						argumentValues),
				actionListener,
				startTime + TIMEOUT);

		// Wait for all updates or TIMOUT
		// check reception of ActionEvents from subscription
		// - ActionCompleteEvent: success=true
		targetUpdates = new MonitorExecutionUpdate[1];
		targetUpdates[0] = new MonitorExecutionUpdate(
				Constant.DOMAIN_SAT1,
				requestId3,
				Constant.ID_DEFATTITUDE,
				ActionCategory.DEFAULT,
				new ActionCompleteEvent(true));
		waitAndCheckForUpdates(actionListener, startTime + TIMEOUT, targetUpdates);

	}

}
