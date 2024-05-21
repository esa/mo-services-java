/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package org.ccsds.moims.mo.mal.provider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.ServiceInfo;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The MALProviderSet class manages a list of MALProvider providing the same
 * service through different protocols and create instances of MALPublisherSet,
 * register their references and update them when a MALProvider is either added
 * or removed.
 *
 */
public class MALProviderSet {

    private final Set<MALPublisherSet> publisherSets = new HashSet<>();
    private final Set<MALProvider> providers = new HashSet<>();
    private final ServiceInfo service;

    /**
     * Constructs a provider set.
     *
     * @param service The service to be provided by the MALProviders added in
     * this MALProviderSet
     * @throws java.lang.IllegalArgumentException If the parameter ‘service’ is
     * NULL
     */
    public MALProviderSet(final ServiceInfo service) throws java.lang.IllegalArgumentException {
        this.service = service;
    }

    /**
     * The method creates a MALPublisherSet and registers its reference.
     *
     * @param op PUBLISH-SUBSCRIBE operation
     * @param domain Domain of the PUBLISH messages
     * @param sessionType Session type of the PUBLISH messages
     * @param sessionName Session name of the PUBLISH messages
     * @param remotePublisherQos QoS level of the PUBLISH messages
     * @param remotePublisherQosProps QoS properties of the PUBLISH messages
     * @param supplements Set of optional named values
     * @return The created publisher set.
     * @throws java.lang.IllegalArgumentException If the parameters ‘op’ or
     * ‘domain’ or ‘networkZone’ or ‘sessionType’ or ‘sessionName’ or
     * ‘remotePublisherQos’ or ‘remotePublisherPriority’ are NULL
     * @throws MALException If an error occurs.
     */
    public MALPublisherSet createPublisherSet(
            final MALPubSubOperation op,
            final IdentifierList domain,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel remotePublisherQos,
            final Map remotePublisherQosProps,
            final NamedValueList supplements)
            throws java.lang.IllegalArgumentException, MALException {
        final MALPublisherSet publisherSet = new MALPublisherSet(
                op,
                domain,
                sessionType,
                sessionName,
                remotePublisherQos,
                remotePublisherQosProps);
        publisherSets.add(publisherSet);

        for (MALProvider provider : providers) {
            publisherSet.createPublisher(provider);
        }

        return publisherSet;
    }

    /**
     * The method adds a MALProvider to this MALProviderSet.
     *
     * @param provider MALProvider to be added
     * @throws java.lang.IllegalArgumentException If the parameter ‘provider’ is
     * NULL
     * @throws MALException If the added MALProvider provides a MALService which
     * is not the same as the MALService of this MALProviderSet
     */
    public void addProvider(final MALProvider provider)
            throws java.lang.IllegalArgumentException, MALException {
        if ((provider.getService().getAreaNumber().getValue() != service.getAreaNumber().getValue())
                || (provider.getService().getServiceNumber().getValue() != service.getServiceNumber().getValue())) {
            throw new MALException("Adding provider of service number "
                    + provider.getService().getServiceNumber()
                    + " to a provider set for service number "
                    + service.getServiceNumber());
        }

        providers.add(provider);

        for (MALPublisherSet publisherSet : publisherSets) {
            publisherSet.createPublisher(provider);
        }
    }

    /**
     * The method removes a MALProvider from this MALProviderSet.
     *
     * @param provider MALProvider to be removed
     * @return If this MALProviderSet contains the specified provider then the
     * method returns ‘true’ otherwise it returns ‘false’.
     * @throws java.lang.IllegalArgumentException If the parameter ‘provider’ is
     * NULL
     */
    public boolean removeProvider(final MALProvider provider) throws java.lang.IllegalArgumentException {
        providers.remove(provider);
        for (MALPublisherSet publisherSet : publisherSets) {
            try {
                publisherSet.deletePublisher(provider);
            } catch (MALException ex) {
                // ToDo
            }
        }
        return true;
    }
}
