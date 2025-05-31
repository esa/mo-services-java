/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
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

import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.IntegerList;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * A holder for the details of the connection to a provider
 */
public class SingleConnectionDetails {

    private URI providerURI;
    private URI brokerURI;
    private IdentifierList domain;
    private IntegerList serviceKey;

    /**
     * This is the original method. The are now moving to a class without
     * setters so this will be removed in the future.
     *
     * @deprecated
     */
    @Deprecated
    public SingleConnectionDetails() {

    }

    /**
     * Constructor.
     *
     * @param providerURI The Provider URI.
     * @param brokerURI The Broker URI.
     * @param domain The domain.
     * @param serviceKey The service key.
     */
    public SingleConnectionDetails(String providerURI, String brokerURI,
            IdentifierList domain, IntegerList serviceKey) {
        this(new URI(providerURI), new URI(brokerURI), domain, serviceKey);
    }

    /**
     * Constructor.
     *
     * @param providerURI The Provider URI.
     * @param brokerURI The Broker URI.
     * @param domain The domain.
     */
    public SingleConnectionDetails(URI providerURI, URI brokerURI, IdentifierList domain) {
        this(providerURI, brokerURI, domain, new IntegerList());
    }

    /**
     * Constructor.
     *
     * @param providerURI The Provider URI.
     * @param brokerURI The Broker URI.
     * @param domain The domain.
     * @param serviceKey The service key.
     */
    public SingleConnectionDetails(URI providerURI, URI brokerURI,
            IdentifierList domain, IntegerList serviceKey) {
        this.providerURI = providerURI;
        this.brokerURI = brokerURI;
        this.domain = domain;
        this.serviceKey = (serviceKey == null) ? new IntegerList() : serviceKey;
    }

    @Deprecated
    public void setProviderURI(String providerURI) {
        this.providerURI = new URI(providerURI);
    }

    @Deprecated
    public void setProviderURI(URI providerURI) {
        this.providerURI = providerURI;
    }

    @Deprecated
    public void setBrokerURI(String brokerURI) {
        this.brokerURI = new URI(brokerURI);
    }

    @Deprecated
    public void setBrokerURI(URI brokerURI) {
        this.brokerURI = brokerURI;
    }

    @Deprecated
    public void setDomain(IdentifierList domain) {
        this.domain = domain;
    }

    @Deprecated
    public void setServiceKey(IntegerList serviceKey) {
        this.serviceKey = serviceKey;
    }

    public URI getProviderURI() {
        return this.providerURI;
    }

    public URI getBrokerURI() {
        return this.brokerURI;
    }

    public IdentifierList getDomain() {
        return this.domain;
    }

    public IntegerList getServiceKey() {
        return this.serviceKey;
    }

    @Override
    public String toString() {
        return "providerURI=" + providerURI + ", brokerURI=" + brokerURI + ", domain=" + domain;
    }

}
