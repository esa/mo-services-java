/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package esa.mo.mal.impl;

import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Simple struct style class for holding an endpoint and its details.
 */
public final class Address {

    /**
     * The endpoint to use with this Address.
     */
    private final MALEndpoint endpoint;
    /**
     * The authentication Id of this Address.
     */
    private final Blob authenticationId;
    /**
     * The internal interaction handler that uses this address.
     */
    private final MALInteractionHandler handler;

    /**
     * Constructor.
     *
     * @param endpoint Endpoint.
     * @param authenticationId Authentication identifier.
     * @param handler Interaction handler.
     */
    public Address(MALEndpoint endpoint, Blob authenticationId, MALInteractionHandler handler) {
        this.endpoint = endpoint;
        this.authenticationId = authenticationId;
        this.handler = handler;
    }

    public MALEndpoint getEndpoint() {
        return endpoint;
    }

    public Blob getAuthenticationId() {
        return authenticationId;
    }

    public MALInteractionHandler getHandler() {
        return handler;
    }

    @Override
    public String toString() {
        return "Address(uri=" + endpoint.getURI() + " - authenticationId=" + authenticationId + ")";
    }
}
