/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package org.ccsds.moims.mo.mc.backends;

import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mc.backends.AlertBackend.AlertListener;

/**
 * The Backend interface to the Packet service.
 * 
 * The Packet service specifically allows (and recommends) to define custom subscription keys to its main
 * PubSub operation. The Backend interface includes methods to retrieve the details of these keys.
 * 
 * 
 */
public interface PacketBackend {

	/**
	 * Retrieves the names of the custom subscription keys of the deliverPacket operation.
	 * 
	 * @return	the list of key names
	 */
	public IdentifierList getCustomSubscriptionKeyNames();
	/**
	 * Retrieves the types of the custom subscription keys of the deliverPacket operation.
	 * 
	 * @return	the list of key types
	 */
	public AttributeTypeList getCustomSubscriptionKeyTypes();

	/**
	 * Listener interface used in calling {@link register}.
	 */
	public interface PacketListener {
		/**
		 * Notifies a new Packet.
		 * 
		 * @param domain		domain of the packet to publish
		 * @param keyValues		values of all subscription keys, including the standard apid and the custom ones
		 * @param timestamp		timestamp of the packet
		 * @param spacePacket	body of the packet
		 */
		public void notifyPacket(
				IdentifierList domain,
				NullableAttributeList keyValues,
				Time timestamp,
				Blob spacePacket);
	}

	/**
	 * Registers a listener for receiving all new Packets.
	 * 
	 * @param listener	listener to notify
	 */
	public void register(PacketListener listener);

	/**
	 * Unregisters a listener for the new Packets.
	 * Returns silently if the provided listener was not previously registered.
	 * 
	 * @param listener	listener to unregister for notifications
	 */
	public void deregister(PacketListener listener);

}
