/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
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

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mc.packet.consumer.PacketAdapter;

public class PacketListener extends PacketAdapter {

	static class DeliverPacketUpdate {
		IdentifierList domain;
		UShort apid;
		UOctet destID;
		Time timestamp;
		Blob spacePacket;
		public DeliverPacketUpdate(
				IdentifierList domain,
				UShort apid,
				UOctet destID,
				Time timestamp,
				Blob spacePacket) {
			this.domain = domain;
			this.apid = apid;
			this.destID = destID;
			this.timestamp = timestamp;
			this.spacePacket = spacePacket;
		}
		public String toString() {
			StringBuilder out = new StringBuilder();
			out.append(this.getClass().getName());
			out.append("{domain=").append(domain);
			out.append(", apid=").append(apid);
			out.append(", destID=").append(destID);
			out.append(", timestamp=").append(timestamp);
			out.append(", spacePacket=");
			if (spacePacket == null || spacePacket.getValue() == null)
				out.append("null");
			else {
				byte[] body = spacePacket.getValue();
				out.append("[");
				if (body.length > 0)
					out.append(body[0]).append(";");
				out.append(body.length).append("]");
			}
			out.append("}");
			return out.toString();
		}
	}

	String testException = null;
	MOErrorException error = null;
	boolean registerAckReceived = false;
	boolean deregisterAckReceived = false;
	ConcurrentLinkedQueue<DeliverPacketUpdate> packetUpdates = new ConcurrentLinkedQueue<>();

	synchronized void reset() {
		testException = null;
		error = null;
		registerAckReceived = false;
		deregisterAckReceived = false;
		packetUpdates.clear();
	}

	public boolean hasError() {
		return testException != null || error != null;
	}
	public String getError() {
		if (error != null)
			return error.toString();
		return testException;
	}

	private void addTestException(String testException) {
		if (this.testException == null)
			this.testException = testException;
	}

	@Override
	public void deliverPacketRegisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: deliverPacketRegisterAckReceived()");
		synchronized(this) {
			registerAckReceived = true;
			notify();
		}
	}
	@Override
	public void deliverPacketRegisterErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: deliverPacketRegisterErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void deliverPacketNotifyReceived(
			MALMessageHeader msgHeader,
			Identifier subscriptionId,
			UpdateHeader updateHeader,
			Time timestamp,
			Blob spacePacket,
			Map qosProperties) {
		System.out.println("Reached: deliverPacketNotifyReceived() -> " + timestamp);
		// all subscription keys are used in all the tests
		NullableAttributeList keyValues = updateHeader.getKeyValues();
		UShort apid = null;
		UOctet destID = null;
		if (keyValues == null || keyValues.size() != 2) {
			addTestException("Unexpected number of subscription key values");
		} else {
			Attribute pApid = keyValues.get(0).getValue();
			if (pApid != null)
				apid = (UShort) pApid;
			Attribute pDestID = keyValues.get(1).getValue();
			if (pDestID != null)
				destID = (UOctet) pDestID;
		}
		synchronized(this) {
			packetUpdates.add(new DeliverPacketUpdate(
					updateHeader.getDomain(),
					apid, destID,
					timestamp, spacePacket));
			notify();
		}
	}
	@Override
	public void deliverPacketNotifyErrorReceived(
			MALMessageHeader msgHeader,
			MOErrorException error,
			Map qosProperties) {
		System.out.println("Reached: deliverPacketNotifyErrorReceived()");
		synchronized(this) {
			this.error = error;
			notify();
		}
	}
	@Override
	public void deliverPacketDeregisterAckReceived(
			MALMessageHeader msgHeader,
			Map qosProperties) {
		System.out.println("Reached: deliverPacketDeregisterAckReceived()");
		synchronized(this) {
			deregisterAckReceived = true;
			notify();
		}
	}

}