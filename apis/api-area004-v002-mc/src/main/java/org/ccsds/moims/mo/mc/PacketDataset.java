/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * Copyright (C) 2025      CNES, France
 * Copyright (C) 2025      Serge Lacourte
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package org.ccsds.moims.mo.mc;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mc.backends.PacketBackend;

/**
 * A abstract class for all backend PacketDataset.
 */
public abstract class PacketDataset implements PacketBackend {

	/** class logger */
	private static final Logger logger = Logger.getLogger(PacketDataset.class.getName());

	/** Names of the custom subscription keys of the deliverPacket operation. */
	private IdentifierList customSubscriptionKeyNames;
	/** Types of the custom subscription keys of the deliverPacket operation. */
	private AttributeTypeList customSubscriptionKeyTypes;

	@Override
	public IdentifierList getCustomSubscriptionKeyNames() {
		return customSubscriptionKeyNames;
	}
	@Override
	public AttributeTypeList getCustomSubscriptionKeyTypes() {
		return customSubscriptionKeyTypes;
	}

	/**
	 * Defines the custom subscription keys of the deliverPacket operation.
	 * This method is expected to be called in the constructor of derived classes.
	 * 
	 * @param customSubscriptionKeyNames	names of the custom subscription keys
	 * @param customSubscriptionKeyTypes	types of the custom subscription keys
	 */
	protected void setCustomSubscriptionKeys(
			IdentifierList customSubscriptionKeyNames,
			AttributeTypeList customSubscriptionKeyTypes) {
		this.customSubscriptionKeyNames = customSubscriptionKeyNames;
		this.customSubscriptionKeyTypes = customSubscriptionKeyTypes;
	}

	/** list of registered listeners to signal for new packets */
	private final Set<PacketListener> listeners = new HashSet<>();

	@Override
	public void register(PacketListener listener) {
		listeners.add(listener);
	}
	@Override
	public void deregister(PacketListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Publishes a new Packet. Notifies all listeners.
	 * 
	 * In the testbed, this method is expected to be called by the test clients.
	 * 
	 * @param domain		domain of the packet to publish
	 * @param keyValues		values of the custom subscription keys
	 * @param timestamp		timestamp of the packet
	 * @param spacePacket	body of the packet
	 */
	public void publishPacket(
			IdentifierList domain,
			NullableAttributeList keyValues,
			Time timestamp,
			Blob spacePacket) {
		for (PacketListener listener : listeners) {
			listener.notifyPacket(domain, keyValues, timestamp, spacePacket);
		}
	}
}
