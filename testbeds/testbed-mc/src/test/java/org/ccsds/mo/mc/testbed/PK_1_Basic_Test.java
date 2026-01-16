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
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.ccsds.mo.mc.testbed.backends.BackendTimerImpl;
import org.ccsds.mo.mc.testbed.backends.PacketBasicDataset;
import org.ccsds.mo.mc.testbed.AlertListener.MonitorAlertUpdate;
import org.ccsds.mo.mc.testbed.PacketListener.DeliverPacketUpdate;
import org.ccsds.mo.mc.testbed.backends.AlertBasicDataset;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.alert.consumer.AlertAdapter;
import org.ccsds.moims.mo.mc.structures.AlertDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValue;
import org.ccsds.moims.mo.mc.structures.ParameterValueData;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.Severity;
import org.ccsds.moims.mo.mc.structures.ValidityState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PK_1_Basic_Test extends PacketTestClient {

	static PacketListener packetListener = new PacketListener();
	static Identifier subscriptionId;

	private static final PacketBasicDataset backend = new PacketBasicDataset();

	@BeforeClass
	public static void setUpClass() throws IOException {
		System.out.println(TEST_SET_UP_CLASS_1);
		System.out.println(TEST_SET_UP_CLASS_2);
		setUnitTestLogger(Logger.getLogger(PK_1_Basic_Test.class.getName()));
		setUp.setUp(null, null, null, backend, null,
				false, false, false, true, false);
		packetConsumerStub = setUp.getPacketConsumer();

		// call deliverPacket.register with subscription
		// subscription=
		// - subscriptionId=11
		// - domain="fr.cnes.mission.*"
		// - selectedKeys=null
		// - filters=null
		subscriptionId = new Identifier("11");
		execAndCheckDeliverPacketRegister(
				new Subscription(subscriptionId,
						Constant.DOMAIN_WILDCARD,
						null, null),
				packetListener,
				System.currentTimeMillis() + TIMEOUT);

	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("Entered: " + PK_1_Basic_Test.class.getName() + " tearDownClass()");

		IdentifierList subscriptions = new IdentifierList();
		subscriptions.add(subscriptionId);
		packetListener.reset();
		execAndCheckDeliverPacketDeregister(
				subscriptions,
				packetListener,
				System.currentTimeMillis() + TIMEOUT);

		MCTest.tearDownClass();
	}

	/**
	 * Test Case 1.
	 */
	@Test
	public void testCase_01() {
		System.out.println("Running: testCase_01()");

		long startTime = System.currentTimeMillis();
		// call backend.publishPacket with testPacket
		// testPacket=
		// - domain="fr.cnes.mission.sat1"
		// - keys={apid=3, destID=11}
		// - spacePacket: first byte=0, length=20
		packetListener.reset();
		NullableAttributeList packetKeys =
				new NullableAttributeList(new ArrayList<> (Arrays.asList(
						NA_USHORT_3, NA_UOCTET_11)));
		byte testPacketFirstByte = (byte) 0;
		Blob testPacket = new Blob(generateTestPacket(20, testPacketFirstByte));
		backend.publishPacket(
				Constant.DOMAIN_SAT1,
				packetKeys,
				new Time(System.currentTimeMillis()),
				testPacket);

		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		// check reception of 1 NOTIFY messages from subscription
		// with testPacket values
		DeliverPacketUpdate[] targetUpdates = new DeliverPacketUpdate[1];
		targetUpdates[0] =
				new DeliverPacketUpdate(
						Constant.DOMAIN_SAT1,
						US_3,
						UO_11,
						null,  // timestamp, unchecked
						testPacket);
		waitAndCheckForUpdates(
				packetListener,
				startTime + TIMEOUT,
				targetUpdates);
	}

}
