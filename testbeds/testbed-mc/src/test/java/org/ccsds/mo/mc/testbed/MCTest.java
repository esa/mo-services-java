/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
<<<<<<< HEAD
 * System                : CCSDS MO Testbed
=======
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed - M&C
>>>>>>> 1644edb5 (M&C testbed)
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.SetUpProvidersAndConsumers;
import org.ccsds.mo.mc.testbed.AlertListener.MonitorAlertUpdate;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectIdentity;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mc.ActionDataset;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.aggregation.provider.AggregationInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.packet.consumer.PacketStub;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterStub;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;
import org.ccsds.moims.mo.mc.structures.ActionCompleteEvent;
import org.ccsds.moims.mo.mc.structures.ActionEvent;
import org.ccsds.moims.mo.mc.structures.ActionInProgressEvent;
import org.ccsds.moims.mo.mc.structures.ActionStartEvent;
import org.ccsds.moims.mo.mc.structures.AggregationValue;
import org.ccsds.moims.mo.mc.structures.AggregationValueList;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueDataList;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfiguration;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;
import org.ccsds.moims.mo.mc.structures.Severity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * The abstract class for all M&C Tests.
 *
 * @author Cesar.Coelho
 */
public abstract class MCTest {

	private static final Logger logger = Logger.getLogger(MCTest.class.getName());
	protected static final int TIMEOUT = 500; // In milliseconds
	protected static final int NOUPDATE_TIMEOUT = 50; // In milliseconds
	protected static final String TEST_START = "-------- Running New Test --------";
	protected static final String TEST_END = "Test is completed!";
	protected static final String TEST_SET_UP_CLASS_1 = "-----------------------------------------------------------------------";
	protected static final String TEST_SET_UP_CLASS_2 = "Entered: setUpClass() - The Provider and Consumer will be started here!";
	protected static final SetUpProvidersAndConsumers setUp = new SetUpProvidersAndConsumers();

	protected static ActionInheritanceSkeleton actionProviderService = null;
	protected static ActionStub actionConsumerStub = null;
	protected static AggregationInheritanceSkeleton aggregationProviderService = null;
	protected static AggregationStub aggregationConsumerStub = null;
	protected static AlertInheritanceSkeleton alertProviderService = null;
	protected static AlertStub alertConsumerStub = null;
	protected static PacketInheritanceSkeleton packetProviderService = null;
	protected static PacketStub packetConsumerStub = null;
	protected static ParameterInheritanceSkeleton parameterProviderService = null;
	protected static ParameterStub parameterConsumerStub = null;

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: tearDownClass()");
		System.out.println("The Provider and Consumer are being closed!");

		try {
			setUp.tearDown(); // Close all the services
		} catch (IOException ex) {
			Logger.getLogger(MCTest.class.getName()).log(Level.SEVERE,
					"The tearDown() operation failed!", ex);
		}
	}

	@Before
	public void setUp() {
		System.out.println(TEST_START); // Right before running a test
		actionProviderService = setUp.getActionProvider();
		actionConsumerStub = setUp.getActionConsumer();
		aggregationProviderService = setUp.getAggregationProvider();
		aggregationConsumerStub = setUp.getAggregationConsumer();
		alertProviderService = setUp.getAlertProvider();
		alertConsumerStub = setUp.getAlertConsumer();
		packetProviderService = setUp.getPacketProvider();
		packetConsumerStub = setUp.getPacketConsumer();
		parameterProviderService = setUp.getParameterProvider();
		parameterConsumerStub = setUp.getParameterConsumer();
	}

	@After
	public void tearDown() {
		System.out.println(TEST_END);
	}

	protected static URI getHomeTmpDir() {
		File homeDirectory = new File(System.getProperty("user.home"));
		File targetDir = new File(homeDirectory, "tmp");
		if (!targetDir.exists()) {
			// Create the directory if it does not exist:
			targetDir.mkdirs();
		}
		return new URI("file://" + targetDir.getAbsolutePath());
	}

	// factorize the logging stuff
	private static Logger unitTestLogger;
	protected static void setUnitTestLogger(Logger logger) {
		unitTestLogger = logger;
	}
	protected static void unitTestFail(String message) {
		unitTestLogger.log(Level.SEVERE, message);
		fail(message);
	}
	protected static void unitTestFail(Throwable thrown) {
		unitTestLogger.log(Level.SEVERE, null, thrown);
		fail(thrown.toString());
	}

	protected static void assertEqualsUpdateHeaders(String error, Iterable<UpdateHeader> expected, Iterable<UpdateHeader> actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		Iterator<UpdateHeader> eit = expected.iterator();
		Iterator<UpdateHeader> ait = actual.iterator();
		int eltIdx = 1;
		while (eit.hasNext()) {
			if (!ait.hasNext())
				unitTestFail(error + " missing update header #" + eltIdx);
			assertEquals(error + " event #" + eltIdx, eit.next(), ait.next());
			eltIdx++;
		}
	}
	protected static void assertEquals(String error, UpdateHeader expected, UpdateHeader actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + ", expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + ", expecting " + expected + ", was " + actual);
		// check domain and key values
		if (expected.getDomain() == null) {
			if (actual.getDomain() != null)
				unitTestFail(error + " wrong domain" +
						", expected " + expected.getDomain() + ", was " + actual.getDomain());
		} else if (!expected.getDomain().equals(actual.getDomain())) {
			unitTestFail(error + " wrong domain" +
					", expected " + expected.getDomain() + ", was " + actual.getDomain());
		}
		assertEquals(error + " wrong key values", expected.getKeyValues(), actual.getKeyValues());
	}

	protected static void assertEqualsActionEvents(String error, Iterable<ActionEvent> expected, Iterable<ActionEvent> actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		Iterator<ActionEvent> eit = expected.iterator();
		Iterator<ActionEvent> ait = actual.iterator();
		int eventIdx = 1;
		while (eit.hasNext()) {
			if (!ait.hasNext())
				unitTestFail(error + " missing event #" + eventIdx);
			assertEquals(error + " event #" + eventIdx, eit.next(), ait.next());
			eventIdx++;
		}
	}
	protected static void assertEquals(String error, ActionEvent expected, ActionEvent actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected event, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected event, expecting " + expected + ", was " + actual);
		if (actual.getSuccess() != expected.getSuccess())
			unitTestFail(error + " unexpected success field, expecting " + expected.getSuccess() + ", was " + actual.getSuccess());
		if (expected instanceof ActionStartEvent) {
			if (!(actual instanceof ActionStartEvent))
				unitTestFail(error + " unexpected event type, expecting " + expected.getClass().getName() + ", was " + actual.getClass().getName());
		} else if (expected instanceof ActionCompleteEvent) {
			if (!(actual instanceof ActionCompleteEvent))
				unitTestFail(error + " unexpected event type, expecting " + expected.getClass().getName() + ", was " + actual.getClass().getName());
		} else if (expected instanceof ActionInProgressEvent) {
			if (!(actual instanceof ActionInProgressEvent))
				unitTestFail(error + " unexpected event type, expecting " + expected.getClass().getName() + ", was " + actual.getClass().getName());
			ActionInProgressEvent eipEvent = (ActionInProgressEvent) expected;
			ActionInProgressEvent aipEvent = (ActionInProgressEvent) actual;
			if (!eipEvent.getStageCount().equals(aipEvent.getStageCount()))
				unitTestFail(error + " unexpected stageCount field, expecting " + eipEvent.getStageCount() + ", was " + aipEvent.getStageCount());
			if (!eipEvent.getExecutionStage().equals(aipEvent.getExecutionStage()))
				unitTestFail(error + " unexpected executionStage field, expecting " + eipEvent.getExecutionStage() + ", was " + aipEvent.getExecutionStage());
		}
	}

	protected static void assertEquals(String error, NullableAttributeList expected, NullableAttributeList actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
		}
	}
	protected static void assertEquals(String error, NullableAttribute expected, NullableAttribute actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected nullable attribute, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected nullable attribute, expecting " + expected + ", was " + actual);
		assertEquals(error, expected.getValue(), actual.getValue());
	}
	protected static void assertEquals(String error, Attribute expected, Attribute actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected attribute, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected attribute, expecting " + expected + ", was " + actual);
		if (!expected.equals(actual))
			unitTestFail(error + " unexpected attribute, expecting " + expected + ", was " + actual);
	}

	protected static void assertEquals(String error, UIntegerList expected, UIntegerList actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected list, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
		if (actual.size() != expected.size())
			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
		}
	}
	protected static void assertEquals(String error, UInteger expected, UInteger actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + " unexpected value, expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
		if (!expected.equals(actual))
			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
	}

	// test values used in Parameter and Alert tests
	public static final NullableAttribute NA_DOUBLE_910 = new NullableAttribute(new Union(new Double(9.10e0)));
	public static final NullableAttribute NA_DOUBLE_920 = new NullableAttribute(new Union(new Double(9.20e0)));
	public static final NullableAttribute NA_DOUBLE_1200 = new NullableAttribute(new Union(new Double(12.00e0)));
	public static final NullableAttribute NA_DOUBLE_1204 = new NullableAttribute(new Union(new Double(12.04e0)));
	public static final NullableAttribute NA_DOUBLE_1205 = new NullableAttribute(new Union(new Double(12.05e0)));
	public static final NullableAttribute NA_DOUBLE_1208 = new NullableAttribute(new Union(new Double(12.08e0)));
	public static final NullableAttribute NA_DOUBLE_1210 = new NullableAttribute(new Union(new Double(12.10e0)));
	public static final NullableAttribute NA_DOUBLE_1211 = new NullableAttribute(new Union(new Double(12.11e0)));
	public static final NullableAttribute NA_DOUBLE_1215 = new NullableAttribute(new Union(new Double(12.15e0)));
	public static final NullableAttribute NA_DOUBLE_1220 = new NullableAttribute(new Union(new Double(12.20e0)));
	public static final NullableAttribute NA_DOUBLE_1300 = new NullableAttribute(new Union(new Double(13.00e0)));
	public static final NullableAttribute NA_DOUBLE_1304 = new NullableAttribute(new Union(new Double(13.04e0)));
	public static final NullableAttribute NA_DOUBLE_1305 = new NullableAttribute(new Union(new Double(13.05e0)));
	public static final NullableAttribute NA_DOUBLE_1315 = new NullableAttribute(new Union(new Double(13.15e0)));
	public static final NullableAttribute NA_DOUBLE_1903 = new NullableAttribute(new Union(new Double(19.03e0)));
	public static final NullableAttribute NA_DOUBLE_1904 = new NullableAttribute(new Union(new Double(19.04e0)));
	public static final NullableAttribute NA_DOUBLE_1905 = new NullableAttribute(new Union(new Double(19.05e0)));
	public static final NullableAttribute NA_DOUBLE_2200 = new NullableAttribute(new Union(new Double(22.00e0)));
	public static final NullableAttribute NA_UINT_1 = new NullableAttribute(new UInteger(1));
	public static final NullableAttribute NA_UINT_2 = new NullableAttribute(new UInteger(2));
	public static final NullableAttribute NA_UINT_8 = new NullableAttribute(new UInteger(8));
	public static final NullableAttribute NA_STRING_ENABLED = new NullableAttribute(new Union(Constant.STR_ENABLED));
	public static final NullableAttribute NA_STRING_DISABLED = new NullableAttribute(new Union(Constant.STR_DISABLED));
	public static final NullableAttribute NA_STRING_UNKNOWN = new NullableAttribute(new Union(Constant.STR_UNKNOWN));

	// UOctet value of the Severity.SEVERE enumeration value
	public static final UOctet UO_SEVERE = new UOctet(Severity.SEVERE.getValue());

	// subscription key values used in Packet tests
	public static final NullableAttribute NA_USHORT_3 = new NullableAttribute(new UShort(3));
	public static final NullableAttribute NA_USHORT_4 = new NullableAttribute(new UShort(4));
	public static final NullableAttribute NA_UOCTET_11 = new NullableAttribute(new UOctet(11));
	public static final NullableAttribute NA_UOCTET_12 = new NullableAttribute(new UOctet(12));
	public static final NullableAttribute NA_NULL = new NullableAttribute(null);
	
	// extra info values
	public static final UIntegerList EXTRA_UIL_0 =
			new UIntegerList(new ArrayList<>(Arrays.asList(
					new UInteger(0))));
	public static final UIntegerList EXTRA_UIL_1 =
			new UIntegerList(new ArrayList<>(Arrays.asList(
					new UInteger(1))));
//	
//	public void waitAndCheckForParameterMonitorValueUpdates(
//			ParameterListener parameterListener, long maxtime, ParameterListener.MonitorValueUpdate[] updates) {
//		// ------------------------------------------------------------------------
//		// Wait for all updates or TIMOUT
//		long timeout = maxtime - System.currentTimeMillis();
//		int nbUpdates = updates.length;
//		synchronized(parameterListener) {
//			while (!parameterListener.hasError() &&
//					parameterListener.valueUpdates.size() != nbUpdates &&
//					timeout > 0) {
//				try {
//					System.out.println("wait for Updates from subscription");
//					parameterListener.wait(timeout);
//				} catch (InterruptedException e) {}
//				// Recalculate timer
//				timeout = maxtime - System.currentTimeMillis();
//			}
//			System.out.println("end wait, parameterListener=" + parameterListener);
//			if (parameterListener.hasError())
//				unitTestFail(parameterListener.getError());
//			if (parameterListener.valueUpdates.size() != nbUpdates)
//				unitTestFail("Incorrect number of updates received: " +
//						"expected " + nbUpdates +
//						" was " + parameterListener.valueUpdates.size());
//			// check received updates
//			Iterator<ParameterListener.MonitorValueUpdate> it = parameterListener.valueUpdates.iterator();
//			for (int idx = 0; it.hasNext(); idx++) {
//				String error = "Update[" + idx + "]";
//				ParameterListener.MonitorValueUpdate update = it.next();
//				if (!update.domain.equals(updates[idx].domain))
//					unitTestFail(error + " unexpected domain, expecting " + updates[idx].domain + ", was " + update.domain);
//				if (!update.parameterKey.equals(updates[idx].parameterKey))
//					unitTestFail(error + " unexpected parameterKey subscription key, expecting " + updates[idx].parameterKey + ", was " + update.parameterKey);
//				if (!update.parameterVersion.equals(updates[idx].parameterVersion))
//					unitTestFail(error + " unexpected parameterVersion subscription key, expecting " + updates[idx].parameterVersion + ", was " + update.parameterVersion);
//				assertEquals(error + " value data", updates[idx].newValue, update.newValue);
//			}
//		}
//	}
//
//	public void waitAndCheckForAggregationMonitorValueUpdates(
//			AggregationListener aggregationListener, long maxtime, AggregationListener.MonitorValueUpdate[] updates) {
//		// ------------------------------------------------------------------------
//		// Wait for all updates or TIMOUT
//		long timeout = maxtime - System.currentTimeMillis();
//		int nbUpdates = updates.length;
//		synchronized(aggregationListener) {
//			while (!aggregationListener.hasError() &&
//					aggregationListener.valueUpdates.size() != nbUpdates &&
//					timeout > 0) {
//				try {
//					System.out.println("wait for Updates from subscription");
//					aggregationListener.wait(timeout);
//				} catch (InterruptedException e) {}
//				// Recalculate timer
//				timeout = maxtime - System.currentTimeMillis();
//			}
//			System.out.println("end wait, parameterListener=" + aggregationListener);
//			if (aggregationListener.hasError())
//				unitTestFail(aggregationListener.getError());
//			if (aggregationListener.valueUpdates.size() != nbUpdates)
//				unitTestFail("Incorrect number of updates received: " +
//						"expected " + nbUpdates +
//						" was " + aggregationListener.valueUpdates.size());
//			// check received updates
//			Iterator<AggregationListener.MonitorValueUpdate> it = aggregationListener.valueUpdates.iterator();
//			for (int idx = 0; it.hasNext(); idx++) {
//				String error = "Update[" + idx + "]";
//				AggregationListener.MonitorValueUpdate update = it.next();
//				if (!update.domain.equals(updates[idx].domain))
//					unitTestFail(error + " unexpected domain, expecting " + updates[idx].domain + ", was " + update.domain);
//				if (!update.aggregationKey.equals(updates[idx].aggregationKey))
//					unitTestFail(error + " unexpected aggregationKey subscription key, expecting " + updates[idx].aggregationKey + ", was " + update.aggregationKey);
//				if (!update.aggregationVersion.equals(updates[idx].aggregationVersion))
//					unitTestFail(error + " unexpected aggregationVersion subscription key, expecting " + updates[idx].aggregationVersion + ", was " + update.aggregationVersion);
//				assertEquals(error + " values", updates[idx].values, update.values);
//			}
//		}
//	}
//
//	public void waitAndCheckForMonitorAlertUpdates(AlertListener alertListener, long maxtime, MonitorAlertUpdate[] updates) {
//		// ------------------------------------------------------------------------
//		// Wait for all updates or TIMOUT
//		long timeout = maxtime - System.currentTimeMillis();
//		int nbUpdates = updates.length;
//		synchronized(alertListener) {
//			while (!alertListener.hasError() &&
//					alertListener.alertUpdates.size() != nbUpdates &&
//					timeout > 0) {
//				try {
//					System.out.println("wait for Updates from subscription");
//					alertListener.wait(timeout);
//				} catch (InterruptedException e) {}
//				// Recalculate timer
//				timeout = maxtime - System.currentTimeMillis();
//			}
//			System.out.println("end wait, alertListener=" + alertListener);
//			if (alertListener.hasError())
//				unitTestFail(alertListener.getError());
//			if (alertListener.alertUpdates.size() != nbUpdates)
//				unitTestFail("Incorrect number of updates received: " +
//						"expected " + nbUpdates +
//						" was " + alertListener.alertUpdates.size());
//			// check received updates
//			Iterator<MonitorAlertUpdate> it = alertListener.alertUpdates.iterator();
//			for (int idx = 0; it.hasNext(); idx++) {
//				String error = "Update[" + idx + "]";
//				MonitorAlertUpdate update = it.next();
//				if (!update.domain.equals(updates[idx].domain))
//					unitTestFail(error + " unexpected domain, expecting " + updates[idx].domain + ", was " + update.domain);
//				if (!update.alertKey.equals(updates[idx].alertKey))
//					unitTestFail(error + " unexpected alertKey subscription key, expecting " + updates[idx].alertKey + ", was " + update.alertKey);
//				if (!update.alertSeverity.equals(updates[idx].alertSeverity))
//					unitTestFail(error + " unexpected alertSeverity subscription key, expecting " + updates[idx].alertSeverity + ", was " + update.alertSeverity);
//				if (!update.alertVersion.equals(updates[idx].alertVersion))
//					unitTestFail(error + " unexpected alertVersion subscription key, expecting " + updates[idx].alertVersion + ", was " + update.alertVersion);
//				assertEquals(error + " arguments", updates[idx].arguments, update.arguments);
//			}
//		}
//	}
//
//	public void waitAndCheckForDeliverPacketUpdates(
//			PacketListener packetListener,
//			long maxtime,
//			PacketListener.DeliverPacketUpdate[] updates) {
//		// ------------------------------------------------------------------------
//		// Wait for all updates or TIMOUT
//		long timeout = maxtime - System.currentTimeMillis();
//		int nbUpdates = updates.length;
//		synchronized(packetListener) {
//			while (!packetListener.hasError() &&
//					packetListener.packetUpdates.size() != nbUpdates &&
//					timeout > 0) {
//				try {
//					System.out.println("wait for Updates from subscription");
//					packetListener.wait(timeout);
//				} catch (InterruptedException e) {}
//				// Recalculate timer
//				timeout = maxtime - System.currentTimeMillis();
//			}
//			System.out.println("end wait, packetListener=" + packetListener);
//			if (packetListener.hasError())
//				unitTestFail(packetListener.getError());
//			if (packetListener.packetUpdates.size() != nbUpdates)
//				unitTestFail("Incorrect number of updates received: " +
//						"expected " + nbUpdates +
//						" was " + packetListener.packetUpdates.size());
//			// check received updates
//			Iterator<PacketListener.DeliverPacketUpdate> it = packetListener.packetUpdates.iterator();
//			for (int idx = 0; it.hasNext(); idx++) {
//				String error = "Update[" + idx + "]";
//				PacketListener.DeliverPacketUpdate update = it.next();
//				if (!update.domain.equals(updates[idx].domain))
//					unitTestFail(error + " unexpected domain, expecting " + updates[idx].domain + ", was " + update.domain);
//				if (!update.apid.equals(updates[idx].apid))
//					unitTestFail(error + " unexpected apid subscription key, expecting " + updates[idx].apid + ", was " + update.apid);
//				if (!update.destID.equals(updates[idx].destID))
//					unitTestFail(error + " unexpected destID subscription key, expecting " + updates[idx].destID + ", was " + update.destID);
//				assertEquals(error + " packet", updates[idx].spacePacket, update.spacePacket);
//			}
//		}
//	}
//
//	public static void assertEquals(String error, ParameterValueList expected, ParameterValueList actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected list, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
//		if (actual.size() != expected.size())
//			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
//		for (int i = 0; i < expected.size(); i++) {
//			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
//		}
//	}
//	public static void assertEquals(String error, ParameterValue expected, ParameterValue actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected value, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
//		assertEquals(error + " paramRef", expected.getParamRef(), actual.getParamRef());
//		// ignore timestamp and samplingTime
//		assertEquals(error + " value", expected.getValue(), actual.getValue());
//	}
//	public static void assertEquals(String error, ParameterValueDataList expected, ParameterValueDataList actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected list, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
//		if (actual.size() != expected.size())
//			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
//		for (int i = 0; i < expected.size(); i++) {
//			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
//		}
//	}
//	public static void assertEquals(String error, ParameterValueData expected, ParameterValueData actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected value, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
//		if (!expected.getValidityState().equals(actual.getValidityState()))
//			unitTestFail(error + " unexpected validity state, expecting " + expected.getValidityState() + ", was " + actual.getValidityState());
//		if (expected.getRawValue() == null && actual.getRawValue() != null ||
//				expected.getRawValue() != null && !expected.getRawValue().equals(actual.getRawValue()))
//			unitTestFail(error + " unexpected raw value, expecting " + expected.getRawValue() + ", was " + actual.getRawValue());
//		if (expected.getConvertedValue() == null && actual.getConvertedValue() != null ||
//				expected.getConvertedValue() != null && !expected.getConvertedValue().equals(actual.getConvertedValue()))
//			unitTestFail(error + " unexpected converted value, expecting " + expected.getConvertedValue() + ", was " + actual.getConvertedValue());
//	}
//
//	public static void assertEquals(String error, ReportConfigurationList expected, ReportConfigurationList actual) {
//		if (expected == null) {
//			if (actual != null)
//				unitTestFail(error + " unexpected list, expecting null, was " + actual);
//			return;
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
//		if (actual.size() != expected.size())
//			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
//		for (int i = 0; i < expected.size(); i++) {
//			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
//		}
//	}
//	public static void assertEquals(String error, ReportConfiguration expected, ReportConfiguration actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected value, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
//		if (expected.getGenerationEnabled() == null && actual.getGenerationEnabled() != null ||
//				expected.getGenerationEnabled() != null && !expected.getGenerationEnabled().equals(actual.getGenerationEnabled()))
//			unitTestFail(error + " unexpected generationEnabled, expecting " + expected.getGenerationEnabled() + ", was " + actual.getGenerationEnabled());
//		if (expected.getReportInterval() == null && actual.getReportInterval() != null ||
//				expected.getReportInterval() != null && !expected.getReportInterval().equals(actual.getReportInterval()))
//			unitTestFail(error + " unexpected reportInterval, expecting " + expected.getReportInterval() + ", was " + actual.getReportInterval());
//	}
//	
//	public static void assertEquals(String error, ObjectRef<Element> expected, ObjectRef<Element> actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected value, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
//		if (!expected.getDomain().equals(actual.getDomain()))
//			unitTestFail(error + " unexpected domain, expecting " + expected.getDomain() + ", was " + actual.getDomain());
//		if (!expected.getKey().equals(actual.getKey()))
//			unitTestFail(error + " unexpected key, expecting " + expected.getKey() + ", was " + actual.getKey());
//		if (!expected.getTypeId().equals(actual.getTypeId()))
//			unitTestFail(error + " unexpected type, expecting " + expected.getTypeId() + ", was " + actual.getTypeId());
//		if (!expected.getObjectVersion().equals(actual.getObjectVersion()))
//			unitTestFail(error + " unexpected version, expecting " + expected.getObjectVersion() + ", was " + actual.getObjectVersion());
//	}
//	public static void assertEquals(String error, AggregationValueList expected, AggregationValueList actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected list, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected list, expecting " + expected + ", was " + actual);
//		if (actual.size() != expected.size())
//			unitTestFail(error + " wrong list size, expecting " + expected.size() + ", was " + actual.size());
//		for (int i = 0; i < expected.size(); i++) {
//			assertEquals(error + " [" + i + "]", expected.get(i), actual.get(i));
//		}
//	}
//	public static void assertEquals(String error, AggregationValue expected, AggregationValue actual) {
//		if (expected == null) {
//			if (actual == null)
//				return;
//			unitTestFail(error + " unexpected value, expecting null, was " + actual);
//		}
//		if (actual == null)
//			unitTestFail(error + " unexpected value, expecting " + expected + ", was " + actual);
//		assertEquals(error + " aggregRef", expected.getAggregationRef(), actual.getAggregationRef());
//		// ignore timestamp
//		assertEquals(error + " parameter values", expected.getParameterValues(), actual.getParameterValues());
//	}


}
