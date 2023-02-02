/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Support library
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
package esa.mo.com.support;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.com.event.provider.EventInheritanceSkeleton;
import org.ccsds.moims.mo.com.event.provider.MonitorEventPublisher;
import org.ccsds.moims.mo.com.structures.ObjectDetails;
import org.ccsds.moims.mo.com.structures.ObjectDetailsList;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.NotFoundException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Small class that wraps up access to the COM Event service such that clients
 * of it can just call the publish operation and have the event published.
 */
public class EventServiceProvider extends EventInheritanceSkeleton {

    private final MALPublishInteractionListener eventPublishListener;
    private MonitorEventPublisher monitorEventPublisher = null;

    /**
     * Constructor
     *
     * @param eventPublishListener The publish event listener.
     */
    public EventServiceProvider(MALPublishInteractionListener eventPublishListener) {
        this.eventPublishListener = eventPublishListener;
    }

    /**
     * Initialises the event publisher. Must be called before any events are
     * published.
     *
     * @param domain The domain of the events.
     * @param network The network of the events.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void init(final IdentifierList domain, final Identifier network)
            throws MALInteractionException, MALException {
        BaseMalServer.LOGGER.fine("EventServiceHandler:init");

        if (null == monitorEventPublisher) {
            BaseMalServer.LOGGER.fine("EventServiceHandler:creating event publisher");

            monitorEventPublisher = createMonitorEventPublisher(
                    null == domain ? new IdentifierList() : domain,
                    null == network ? new Identifier("SPACE") : network,
                    SessionType.LIVE,
                    new Identifier("LIVE"),
                    QoSLevel.BESTEFFORT,
                    null,
                    new UInteger(0));
            
            monitorEventPublisher.register(new IdentifierList(), eventPublishListener);
        }
    }

    /**
     * Publishes a set of COM events.
     *
     * @param updateHeaderList The update headers
     * @param eventLinks The COM event links.
     * @param eventBody The Com event bodies.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishEvents(final UpdateHeaderList updateHeaderList,
            final ObjectDetailsList eventLinks,
            final ElementList eventBody)
            throws MALInteractionException, MALException {
        monitorEventPublisher.publish(updateHeaderList, eventLinks, eventBody);
    }

    /**
     * Publishes a single COM event.
     *
     * @param updateHeader The update header
     * @param eventLink The COM event links.
     * @param eventBody The Com event body.
     * @throws MALInteractionException On error.
     * @throws MALException On error.
     */
    public void publishSingleEvent(final UpdateHeader updateHeader,
            final ObjectDetails eventLink,
            final Element eventBody)
            throws MALInteractionException, MALException {
        // Produce header
        UpdateHeaderList updateHeaderList = new UpdateHeaderList();
        updateHeaderList.add(updateHeader);

        // Produce ObjectDetails
        ObjectDetailsList eventLinks = new ObjectDetailsList();
        eventLinks.add(eventLink);

        // Produce ActivityTransferList
        try {
            ElementList eventBodies = MALElementsRegistry.elementToElementList(eventBody);
            eventBodies.add(eventBody);

            // We can now publish the event
            monitorEventPublisher.publish(updateHeaderList, eventLinks, eventBodies);
        } catch (NotFoundException ex) {
            Logger.getLogger(EventServiceProvider.class.getName()).log(
                    Level.SEVERE, "The ElementList could not be created!", ex);
        }
    }

    /**
     * Simple default implementation of the event publish listener for publish
     * callbacks.
     */
    public static class EventPublisher implements MALPublishInteractionListener {

        @Override
        public void publishRegisterAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            // do nothing
        }

        @Override
        public void publishRegisterErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            // do nothing
        }

        @Override
        public void publishErrorReceived(final MALMessageHeader header,
                final MALErrorBody body, final Map qosProperties) throws MALException {
            BaseMalServer.LOGGER.log(Level.WARNING,
                    "EventPublisher:publishErrorReceived - {0}", body.toString());
        }

        @Override
        public void publishDeregisterAckReceived(final MALMessageHeader header,
                final Map qosProperties) throws MALException {
            // do nothing
        }
    }
}
