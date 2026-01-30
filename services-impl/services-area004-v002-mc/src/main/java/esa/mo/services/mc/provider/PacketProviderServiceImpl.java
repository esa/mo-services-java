/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
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

package esa.mo.services.mc.provider;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConfigurationProviderSingleton;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.NullableAttribute;
import org.ccsds.moims.mo.mal.structures.NullableAttributeList;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mc.backends.PacketBackend;
import org.ccsds.moims.mo.mc.packet.PacketHelper;
import org.ccsds.moims.mo.mc.packet.provider.DeliverPacketPublisher;

/**
 * The Packet service implementation, provider side.
 */
public class PacketProviderServiceImpl extends PacketInheritanceSkeleton {

    private static final Logger LOGGER = Logger.getLogger(PacketProviderServiceImpl.class.getName());

    private final ConnectionProvider connection = new ConnectionProvider();
    private PacketBackend backend;
    private MALProvider service;
    private DeliverPacketPublisher deliverPacketPublisher;
    private boolean running = false;
    private boolean isRegistered = false;

    /**
     * Initializes the service.
     *
     * @param backend The backend of this service.
     * @throws MALException On initialisation error.
     */
    public synchronized void init(PacketBackend backend) throws MALException {
        if (backend == null) {
            throw new IllegalArgumentException("The backend cannot be null!");
        }

        this.backend = backend;

        // shut down old service transport
        if (service != null) {
            connection.closeAll();
        }

        // Start service with pub-sub enabled 
        service = connection.startService(PacketHelper.PACKET_SERVICE, true, this);

        // Create publisher for deliverPacket operation
        IdentifierList domain = connection.getConnectionDetails().getDomain();
        Identifier network = ConfigurationProviderSingleton.getNetwork();
        if (network == null) {
            network = new Identifier("");
        }

        deliverPacketPublisher = super.createDeliverPacketPublisher(
                domain,
                network,
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                null,
                new UInteger(0));

        // Register publisher with subscription keys (apid + custom keys from backend)
        try {
            IdentifierList keyNames = new IdentifierList();
            keyNames.add(new Identifier("apid"));

            AttributeTypeList keyTypes = new AttributeTypeList();
            keyTypes.add(AttributeType.USHORT);

            // Add custom subscription keys from backend if any
            IdentifierList customKeyNames = backend.getCustomSubscriptionKeyNames();
            AttributeTypeList customKeyTypes = backend.getCustomSubscriptionKeyTypes();
            if (customKeyNames != null && customKeyTypes != null) {
                for (int i = 0; i < customKeyNames.size(); i++) {
                    keyNames.add(customKeyNames.get(i));
                    keyTypes.add(customKeyTypes.get(i));
                }
            }

            deliverPacketPublisher.register(keyNames, keyTypes, new PublishInteractionListener());
            isRegistered = true;
        } catch (MALInteractionException ex) {
            LOGGER.log(Level.SEVERE, "Failed to register deliverPacket publisher", ex);
            throw new MALException("Failed to register deliverPacket publisher", ex);
        }

        // Register backend listener to receive packet notifications
        backend.register(new PacketBackendListener());

        running = true;
        LOGGER.info("Packet service READY");
    }

    /**
     * Closes all running threads and releases the MAL resources.
     */
    public void close() {
        try {
            if (isRegistered && deliverPacketPublisher != null) {
                try {
                    deliverPacketPublisher.deregister();
                } catch (MALInteractionException | MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher deregistration {0}", ex);
                }
                isRegistered = false;
            }

            if (deliverPacketPublisher != null) { 
                try {
                    deliverPacketPublisher.close();
                } catch (MALException ex) {
                    LOGGER.log(Level.WARNING, "Exception during publisher close {0}", ex);
                }
            }

            if (service != null) {
                service.close();
            }

            connection.closeAll();
            running = false;
        } catch (MALException ex) {
            LOGGER.log(Level.WARNING, "Exception during close down of the provider {0}", ex);
        }
    }

    @Override
    public ConnectionProvider getConnection() {
        return this.connection;
    }

    /**
     * Listener implementation to receive packet notifications from the backend
     * and publish them via the MAL publisher.
     */
    private class PacketBackendListener implements PacketBackend.PacketListener {

        /**
         * Called by the backend when a new packet is available.
         * Extracts APID from the Space Packet primary header (bits 0-10 of first 16-bit word)
         * and publishes keyValues = [apid, ...custom keys]. Packets shorter than 6 bytes are not published.
         *
         * @param domain Domain identifier list
         * @param keyValues Values of custom subscription keys
         * @param timestamp Packet timestamp
         * @param spacePacket Full raw space packet
         */
        @Override
        public void notifyPacket(
                IdentifierList domain,
                NullableAttributeList keyValues,
                Time timestamp,
                Blob spacePacket) {
            try {
                byte[] packetBytes = spacePacket != null ? spacePacket.getValue() : null;
                if (packetBytes == null || packetBytes.length < 6) {
                    LOGGER.log(Level.WARNING, "Packet shorter than 6-byte primary header, not publishing");
                    return;
                }

                // Extract APID from packet header
                int pkIdent = ((packetBytes[0] & 0xFF) << 8) | (packetBytes[1] & 0xFF);
                int apid = pkIdent & 0x07FF;

                // Publish APID + N custom keys
                int nCustom = (backend.getCustomSubscriptionKeyNames() != null) ? backend.getCustomSubscriptionKeyNames().size() : 0;
                NullableAttributeList keyValuesToPublish = new NullableAttributeList();
                keyValuesToPublish.add(new NullableAttribute(new UShort((short) apid)));
                for (int i = 0; i < nCustom; i++) {
                    keyValuesToPublish.add((keyValues != null && i < keyValues.size()) ? keyValues.get(i) : new NullableAttribute(null));
                }

                Identifier source = new Identifier(connection.getConnectionDetails().getProviderURI().getValue());
                IdentifierList useDomain = (domain != null) ? domain : connection.getConnectionDetails().getDomain();
                UpdateHeader updateHeader = new UpdateHeader(source, useDomain, keyValuesToPublish);
                deliverPacketPublisher.publish(updateHeader, timestamp, spacePacket);
            } catch (IllegalArgumentException | MALInteractionException | MALException ex) {
                LOGGER.log(Level.WARNING, "Exception during deliverPacket: " + ex.getMessage(), ex);
            }
        }
    }

}