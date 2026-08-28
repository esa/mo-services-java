/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.broker;

import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALOperation;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Subscription;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;
import org.ccsds.moims.mo.mal.transport.MALRegisterBody;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests the removal of the subscriptions of consumers that the transport
 * reports as gone.
 */
public class MALBrokerHandlerImplTest {

    private static final String BROKER_URI = "maltcp://10.0.0.1:1024/broker";

    /**
     * A consumer that disconnects has all of its subscriptions removed, not
     * just the one that happens to be published to next.
     */
    @Test
    public void testLostConsumerLosesEveryOneOfItsSubscriptions() throws Exception {
        MALBrokerHandlerImpl handler = new MALBrokerHandlerImpl();

        register(handler, "maltcp://10.0.0.1:1025/896229975", "SUB-A");
        register(handler, "maltcp://10.0.0.1:1025/672864643", "SUB-B");

        assertTrue(isRegistered(handler, "maltcp://10.0.0.1:1025/896229975"));
        assertTrue(isRegistered(handler, "maltcp://10.0.0.1:1025/672864643"));

        handler.removeConsumerSubscriptions("maltcp://10.0.0.1:1025/");

        assertFalse(isRegistered(handler, "maltcp://10.0.0.1:1025/896229975"));
        assertFalse(isRegistered(handler, "maltcp://10.0.0.1:1025/672864643"));
    }

    /**
     * The consumers of a peer that is still connected are left alone.
     */
    @Test
    public void testOtherConsumersAreKept() throws Exception {
        MALBrokerHandlerImpl handler = new MALBrokerHandlerImpl();

        register(handler, "maltcp://10.0.0.1:1025/896229975", "SUB-A");
        register(handler, "maltcp://10.0.0.1:2048/111111111", "SUB-B");

        handler.removeConsumerSubscriptions("maltcp://10.0.0.1:1025/");

        assertFalse(isRegistered(handler, "maltcp://10.0.0.1:1025/896229975"));
        assertTrue(isRegistered(handler, "maltcp://10.0.0.1:2048/111111111"));
    }

    /**
     * A peer on port 10250 is not a peer on port 1025, even though one URI
     * starts with the other. This is why the prefix carries the delimiter.
     */
    @Test
    public void testPeerOnALongerPortIsNotMatched() throws Exception {
        MALBrokerHandlerImpl handler = new MALBrokerHandlerImpl();

        register(handler, "maltcp://10.0.0.1:1025/896229975", "SUB-A");
        register(handler, "maltcp://10.0.0.1:10250/222222222", "SUB-B");

        handler.removeConsumerSubscriptions("maltcp://10.0.0.1:1025/");

        assertFalse(isRegistered(handler, "maltcp://10.0.0.1:1025/896229975"));
        assertTrue(isRegistered(handler, "maltcp://10.0.0.1:10250/222222222"));
    }

    /**
     * A prefix that matches nothing, and a null one, are harmless.
     */
    @Test
    public void testUnknownAndNullPrefixesAreHarmless() throws Exception {
        MALBrokerHandlerImpl handler = new MALBrokerHandlerImpl();

        register(handler, "maltcp://10.0.0.1:1025/896229975", "SUB-A");

        handler.removeConsumerSubscriptions("maltcp://10.0.0.1:9999/");
        handler.removeConsumerSubscriptions(null);

        assertTrue(isRegistered(handler, "maltcp://10.0.0.1:1025/896229975"));
    }

    /**
     * Registers a subscription for a consumer against the broker.
     */
    private void register(MALBrokerHandlerImpl handler,
            String consumerURI, String subscriptionId) throws Exception {
        MALMessageHeader header = new MALMessageHeader(
                new Identifier(consumerURI),
                new Blob("".getBytes()),
                new Identifier(BROKER_URI),
                Time.now(),
                InteractionType.PUBSUB,
                new UOctet((short) 1),
                0L,
                new UShort(1),
                new UShort(1),
                new UShort(1),
                new UOctet((short) 1),
                Boolean.FALSE,
                new NamedValueList());

        handler.handleRegister(new InteractionStub(header),
                new RegisterBodyStub(new Subscription(new Identifier(subscriptionId))));
    }

    /**
     * Returns true if the broker still holds subscriptions for a consumer.
     */
    private boolean isRegistered(MALBrokerHandlerImpl handler, String consumerURI) {
        return handler.hasSubscriptions(BROKER_URI, consumerURI);
    }

    /**
     * The smallest MALInteraction that handleRegister needs.
     */
    private static final class InteractionStub implements MALInteraction {

        private final MALMessageHeader header;

        InteractionStub(MALMessageHeader header) {
            this.header = header;
        }

        @Override
        public MALMessageHeader getMessageHeader() {
            return header;
        }

        @Override
        public MALOperation getOperation() {
            return null;
        }

        @Override
        public void setQoSProperty(String name, Object value) {
        }

        @Override
        public Object getQoSProperty(String name) {
            return null;
        }

        @Override
        public Map<String, Object> getQoSProperties() {
            return null;
        }
    }

    /**
     * The smallest MALRegisterBody that handleRegister needs.
     */
    private static final class RegisterBodyStub implements MALRegisterBody {

        private final Subscription subscription;

        RegisterBodyStub(Subscription subscription) {
            this.subscription = subscription;
        }

        @Override
        public Subscription getSubscription() {
            return subscription;
        }

        @Override
        public int getElementCount() {
            return 1;
        }

        @Override
        public Object getBodyElement(int index, Object element) throws MALException {
            return subscription;
        }
    }
}
