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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.ccsds.mo.mc.testbed.PacketListener.DeliverPacketUpdate;
import org.ccsds.mo.mc.testbed.ParameterListener.MonitorValueUpdate;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UIntegerList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;
import org.ccsds.moims.mo.mc.structures.ParameterValueList;
import org.ccsds.moims.mo.mc.structures.ReportConfigurationList;

import org.junit.Assert;

/**
* This class provides shared functions for all Packet test clients.
*/
public class PacketTestClient extends MCTest {

	public static final Duration DURATION_0 = new Duration(0e0);
	public static final Duration DURATION_60 = new Duration(60e3);
	public static final Duration DURATION_300 = new Duration(300e3);
	public static final Duration DURATION_600 = new Duration(600e3);
	
	public static final UShort US_3 = new UShort(3);
	public static final UShort US_4 = new UShort(4);
	public static final UOctet UO_11 = new UOctet(11);
	public static final UOctet UO_12 = new UOctet(12);

	static final Random random = new Random(System.currentTimeMillis());
	byte[] generateTestPacket(int size, int apid) {
		byte[] result = new byte[size];
		random.nextBytes(result);
		result[0] = (byte) ((result[0] & 0xF8) | ((apid >> 8) & 0x07));
		result[1] = (byte) (apid & 0xFF);
		return result;
	}
	protected static void execAndCheckDeliverPacketRegister(
			Subscription subscription,
			PacketListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			packetConsumerStub.asyncDeliverPacketRegister(
					subscription,
					listener);

			// ------------------------------------------------------------------------
			// Wait while ACK has not been received and TIMOUT has not passed yet...
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.registerAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate it
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.registerAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	protected static void execAndCheckDeliverPacketDeregister(
			IdentifierList subscriptions,
			PacketListener listener,
			long maxTime) {

		try {
			long timeout = maxTime - System.currentTimeMillis();
			packetConsumerStub.asyncDeliverPacketDeregister(
					subscriptions,
					listener);

			// ------------------------------------------------------------------------
			// Wait while ACK has not been received and TIMOUT has not passed yet...
			synchronized(listener) {
				while (!listener.hasError() &&
						!listener.deregisterAckReceived &&
						timeout > 0) {
					try {
						listener.wait(timeout);
					} catch (InterruptedException e) {}
					// Recalculate it
					timeout = maxTime - System.currentTimeMillis();
				}
				if (listener.hasError())
					unitTestFail(listener.getError());
				if (!listener.deregisterAckReceived)
					unitTestFail("The ACK was not received!");
			}
		} catch (MALInteractionException exc) {
			unitTestFail(exc);
		} catch (MALException exc) {
			unitTestFail(exc);
		}
	}

	/**
	 * Waits for expected updates and checks them.
	 * All updates relates to the same Packet subscription.
	 * 
	 * @param listener	callback listener
	 * @param maxTime	max waiting time
	 * @param updates	expected updates
	 */
	protected static void waitAndCheckForUpdates(
			PacketListener listener,
			long maxTime,
			DeliverPacketUpdate[] updates) {
		// ------------------------------------------------------------------------
		// Wait for all updates or TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		int nbUpdates = updates.length;
		synchronized(listener) {
			while (!listener.hasError() &&
					listener.packetUpdates.size() != nbUpdates &&
					timeout > 0) {
				try {
					System.out.println("wait for Updates from subscription");
					listener.wait(timeout);
				} catch (InterruptedException e) {}
				// Recalculate timer
				timeout = maxTime - System.currentTimeMillis();
			}
			System.out.println("end wait, listener=" + listener);
			if (listener.hasError())
				unitTestFail(listener.getError());
			if (listener.packetUpdates.size() != nbUpdates)
				unitTestFail("Incorrect number of updates received: " +
						"expected " + nbUpdates +
						" was " + listener.packetUpdates.size());
			// check received updates
			Iterator<DeliverPacketUpdate> it = listener.packetUpdates.iterator();
			for (int idx = 0; it.hasNext(); idx++) {
				assertEquals("Update[" + idx + "]", updates[idx], it.next());
			}
		}
	}

	protected static void waitAndCheckNoUpdate(
			PacketListener listener,
			long maxTime) {
		// ------------------------------------------------------------------------
		// Wait for TIMOUT
		long timeout = maxTime - System.currentTimeMillis();
		synchronized(listener) {
			while (!listener.hasError() &&
					timeout > 0) {
				try {
					System.out.println("wait for timeout");
					listener.wait(timeout);
				} catch (InterruptedException e) {}
				// Recalculate timer
				timeout = maxTime - System.currentTimeMillis();
			}
			if (listener.hasError())
				unitTestFail(listener.getError());

			// check no new message from subscription
			if (!listener.packetUpdates.isEmpty())
			unitTestFail("received unexpected updates");
		}
	}

	protected static void assertEquals(
			String error,
			DeliverPacketUpdate expected,
			DeliverPacketUpdate actual) {
		if (expected == null) {
			if (actual == null)
				return;
			unitTestFail(error + ", expecting null, was " + actual);
		}
		if (actual == null)
			unitTestFail(error + ", expecting " + expected + ", was " + actual);
		Assert.assertEquals(error + " unexpected domain", expected.domain, actual.domain);
		Assert.assertEquals(error + " unexpected apid", expected.apid, actual.apid);
		Assert.assertEquals(error + " unexpected destID", expected.destID, actual.destID);
		Assert.assertEquals(error + " unexpected spacePacket", expected.spacePacket, actual.spacePacket);
	}
}