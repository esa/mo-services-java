/* ----------------------------------------------------------------------------
 * Copyright (C) 2016      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.regression.fastprovider.fasttransport;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.broker.MALBrokerBinding;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 *
 */
public class FastTransport implements MALTransport {

    protected static final Random RANDOM_NAME = new Random();
    protected Map<String, FastEndpoint> endpointMap = new HashMap<>();

    @Override
    public MALEndpoint createEndpoint(String localName, Map map, NamedValueList supplements) throws MALException {
        FastEndpoint ep = new FastEndpoint(this, getLocalName(localName, map));
        endpointMap.put(ep.getURI().getValue(), ep);
        return ep;
    }

    @Override
    public MALEndpoint getEndpoint(String string) throws IllegalArgumentException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MALEndpoint getEndpoint(URI uri) throws IllegalArgumentException, MALException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteEndpoint(String string) throws IllegalArgumentException, MALException {
    }

    @Override
    public MALBrokerBinding createBroker(String string, Blob blob, QoSLevel[] qsls,
            UInteger ui, Map map) throws IllegalArgumentException, MALException {
        return null;
    }

    @Override
    public MALBrokerBinding createBroker(MALEndpoint male, Blob blob, QoSLevel[] qsls,
            UInteger ui, Map map) throws IllegalArgumentException, MALException {
        return null;
    }

    @Override
    public boolean isSupportedQoSLevel(QoSLevel qsl) {
        return true;
    }

    @Override
    public boolean isSupportedInteractionType(InteractionType interactionType) {
        return !InteractionType.PUBSUB.equals(interactionType);
    }

    @Override
    public void close() throws MALException {
    }

    protected void internalSendMessage(MALMessage malm) {
        endpointMap.get(malm.getHeader().getTo().getValue()).internalSendMessage(malm);
    }

    protected String getLocalName(String localName, final java.util.Map properties) {
        if ((localName == null) || (localName.length() == 0)) {
            localName = String.valueOf(RANDOM_NAME.nextInt());
        }

        return localName;
    }
}
