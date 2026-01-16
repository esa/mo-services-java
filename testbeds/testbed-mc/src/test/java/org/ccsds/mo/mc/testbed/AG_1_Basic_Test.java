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
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.AggregationListener.MonitorValueUpdate;
import org.ccsds.mo.mc.testbed.backends.AggregationBasicDataset;
import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.ParameterBasicDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterAdapter;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * AG_1_Basic_Test implements the test scenario #AG-1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AG_1_Basic_Test extends AggregationTestClient {

	static AggregationListener aggregationListener = new AggregationListener();
	static Identifier subscriptionId;

	// timeOrigin=1/1/2025 01:00:00
	private static final LocalDateTime testOrigin = LocalDateTime.of(2025, 1, 1, 1, 0, 0);
	private static long now = testOrigin.toEpochSecond(ZoneId.systemDefault().getRules().getOffset(testOrigin))*1000;
	private static final BackendTimerImpl timer = new BackendTimerImpl(now);
	private static final ParameterBasicDataset parameterBackend = new ParameterBasicDataset(timer);
	private static AggregationBasicDataset backend;

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(AG_1_Basic_Test.class.getName()));
		setUp.setUp(null, null, null, null, parameterBackend,
				false, false, false, false, true);

		if (setUp.getParameterProvider() == null) {
			unitTestFail("cannot find the Parameter provider");
		}
		SingleConnectionDetails parameterDetails =
				setUp.getParameterProvider().getConnection().getConnectionDetails();
		backend = new AggregationBasicDataset(timer, parameterDetails);
		setUp.setUp(null, backend, null, null, null,
				false, true, false, false, false);
		aggregationConsumerStub = setUp.getAggregationConsumer();

		// call monitorValue.register with subscription
		// subscription=
		// - subscriptionId=11
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("11");
		execAndCheckMonitorValueRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				aggregationListener,
				System.currentTimeMillis() + TIMEOUT);

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + AG_1_Basic_Test.class.getName() + " tearDownClass()");

		// call monitorValue.deregister with subscriptionIds={11}
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
	 */
	@Test
	public void testCase_01() {
		// additional statement for dependent tests
		TestDependency.reset();
		TestDependency.before(0, this, null, 1);

		System.out.println("Running: testCase_01()");
		long startTime = System.currentTimeMillis();
		
		aggregationListener.reset();
		// update BackendTimer with now
		// now=1/1/2025 01:06:00 (ie +6:00)
		System.out.println("skip time to 1/1/2025 01:06:00");
		timer.skip(6*60*1000);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with domain="fr.cnes.mission.sat1"
		// and keys={aggregationKey="AGG_BC_MTQ1", aggregationVersion=1}:
		// - timestamp=?
		// - values={{validityState=VALID, rawValue=12.00, convertedValue=null}}
		MonitorValueUpdate[] targetUpdates = new MonitorValueUpdate[1];
		targetUpdates[0] =
				new MonitorValueUpdate(
						Constant.DOMAIN_SAT1,
						Constant.ID_BC_MTQ1,
						new UInteger(1),
						null,  // timestamp, unchecked
						new ParameterValueDataList(new ArrayList<>(Arrays.asList(
								new ParameterValueData(ValidityState.VALID,
								new Union(new Double(12.00)),
								null)))));
		waitAndCheckForUpdates(aggregationListener, startTime + TIMEOUT, targetUpdates);

		// additional statement for dependent tests
		TestDependency.after();
	}

	/**
	 * Test Case 2.
	 * Requires previous execution of Test Case 1.
	 */
	@Test
	public void testCase_02() {
		// additional statement for dependent tests
		TestDependency.before(1, this, "testCase_01", 2);

		System.out.println("Running: testCase_02()");
		long startTime = System.currentTimeMillis();

		aggregationListener.reset();
		// call getValue with getValueParams
		// getValueParams=
		// - domain="fr.cnes.mission.sat1"
		// - keys={"AGG_BC_MTQ1"}
		// check response as singleton list:
		// - aggregationRef={"fr.cnes.mission.sat1", "AGG_BC_MTQ1", version=1}
		// - timestamp=?
		// - parameterValues={{validityState=VALID, rawValue=12.00, convertedValue=null}}
		AggregationValueList expected =
				new AggregationValueList(new ArrayList<>(Arrays.asList(
						new AggregationValue(
								AggregationBasicDataset.sat1BcMtq1Ref,
								null,  // timestamp, unchecked
								new ParameterValueDataList(new ArrayList<>(Arrays.asList(
										new ParameterValueData(ValidityState.VALID,
												NA_DOUBLE_1200.getValue(),
												null))))))));
		execAndCheckGetValue(
				Constant.DOMAIN_SAT1,
				new IdentifierList(new ArrayList<>(Arrays.asList(
						Constant.ID_BC_MTQ1))),
				aggregationListener,
				startTime + TIMEOUT,
				expected);
		
		// additional statement for dependent tests
		TestDependency.after();
	}

}
