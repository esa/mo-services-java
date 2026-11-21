/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.helpertools.connections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Properties;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.QoSLevelList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Checks that ConnectionConsumer creates the MAL consumer with the
 * authentication id, the local name prefix and the QoS level it is given.
 */
public class TestConnectionConsumer {

    private static final URI PROVIDER = new URI("maltcp://localhost:1024/provider");
    private static final Blob AUTHENTICATION_ID = new Blob("credential".getBytes());

    Properties props;

    @Before
    public void useRecordingMAL() {
        props = (Properties) System.getProperties().clone();
        System.setProperty(MALContextFactory.MAL_FACTORY_PROPERTY, RecordingMALContextFactory.class.getName());
        RecordingMALContextFactory.lastCreated = null;
    }

    @After
    public void restoreProps() {
        System.setProperties(props);
    }

    @Test
    public void theConsumerIsCreatedWithTheGivenAuthenticationIdAndLocalName() throws Exception {
        new ConnectionConsumer().startService(PROVIDER, null, new IdentifierList(), null,
                AUTHENTICATION_ID, "ground");

        Object[] args = RecordingMALContextFactory.lastCreated;
        assertTrue("The local name must start with the prefix: " + args[0],
                args[0] != null && ((String) args[0]).startsWith("ground_"));
        assertArrayEquals(AUTHENTICATION_ID.getValue(), ((Blob) args[4]).getValue());
    }

    @Test
    public void withoutAnAuthenticationIdOrLocalNameTheConsumerGetsTheDefaults() throws Exception {
        new ConnectionConsumer().startService(PROVIDER, null, new IdentifierList(), null, null, null);

        Object[] args = RecordingMALContextFactory.lastCreated;
        assertNull(args[0]);
        assertEquals(0, ((Blob) args[4]).getLength());
    }

    @Test
    public void assuredIsChosenWhenTheProviderOffersIt() throws Exception {
        QoSLevelList offered = new QoSLevelList();
        offered.add(QoSLevel.BESTEFFORT);
        offered.add(QoSLevel.ASSURED);
        new ConnectionConsumer().startService(PROVIDER, null, new IdentifierList(), offered,
                new UInteger(1), null, AUTHENTICATION_ID, "ground");

        assertEquals(QoSLevel.ASSURED, RecordingMALContextFactory.lastCreated[9]);
    }

    @Test
    public void bestEffortIsChosenWhenTheProviderDoesNotOfferAssured() throws Exception {
        QoSLevelList offered = new QoSLevelList();
        offered.add(QoSLevel.BESTEFFORT);
        new ConnectionConsumer().startService(PROVIDER, null, new IdentifierList(), offered,
                new UInteger(1), null, AUTHENTICATION_ID, "ground");

        assertEquals(QoSLevel.BESTEFFORT, RecordingMALContextFactory.lastCreated[9]);
    }

    /**
     * A MAL that records the arguments of the last createConsumer call.
     */
    public static class RecordingMALContextFactory extends MALContextFactory {

        static Object[] lastCreated;

        @Override
        public MALContext createMALContext(Map props) {
            MALConsumer consumer = proxy(MALConsumer.class, (p, m, a) -> null);
            MALConsumerManager manager = proxy(MALConsumerManager.class, (p, m, a) -> {
                if (m.getName().equals("createConsumer")) {
                    lastCreated = a;
                    return consumer;
                }
                return null;
            });
            return proxy(MALContext.class,
                    (p, m, a) -> m.getName().equals("createConsumerManager") ? manager : null);
        }

        private static <T> T proxy(Class<T> type, java.lang.reflect.InvocationHandler handler) {
            return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler));
        }
    }
}
