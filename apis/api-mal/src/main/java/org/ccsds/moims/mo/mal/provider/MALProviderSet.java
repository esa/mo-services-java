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
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.structures.*;

/**
 * The MALProviderSet class manages a list of MALProvider providing the same
 * service through different protocols and create instances of MALPublisherSet,
 * register their references and update them when a MALProvider is either added
 * or removed.
 *
 */
public class MALProviderSet {

    private final MALService service;
    private final Set<MALProvider> providers = new HashSet<MALProvider>();
    private final Set<MALPublisherSet> publisherSets = new HashSet<MALPublisherSet>();

    /**
     * Constructs a provider set.
     *
     * @param service The service to be provided by the MALProviders added in
     * this MALProviderSet
     * @throws java.lang.IllegalArgumentException If the parameter ‘service’ is
     * NULL
     */
    public MALProviderSet(final MALService service) throws java.lang.IllegalArgumentException {
        this.service = service;
    }

    /**
     * The method creates a MALPublisherSet and registers its reference.
     *
     * @param op PUBLISH-SUBSCRIBE operation
     * @param domain Domain of the PUBLISH messages
     * @param networkZone Network zone of the PUBLISH messages
     * @param sessionType Session type of the PUBLISH messages
     * @param sessionName Session name of the PUBLISH messages
     * @param remotePublisherQos QoS level of the PUBLISH messages
     * @param remotePublisherQosProps QoS properties of the PUBLISH messages
     * @param remotePublisherPriority Priority of the PUBLISH messages
     * @return The created publisher set.
     * @throws java.lang.IllegalArgumentException If the parameters ‘op’ or
     * ‘domain’ or ‘networkZone’ or ‘sessionType’ or ‘sessionName’ or
     * ‘remotePublisherQos’ or ‘remotePublisherPriority’ are NULL
     * @throws MALException If an error occurs.
     */
    public MALPublisherSet createPublisherSet(
            final MALPubSubOperation op,
            final IdentifierList domain,
            final Identifier networkZone,
            final SessionType sessionType,
            final Identifier sessionName,
            final QoSLevel remotePublisherQos,
            final Map remotePublisherQosProps,
            final UInteger remotePublisherPriority)
            throws java.lang.IllegalArgumentException, MALException {
        final MALPublisherSet rv = new MALPublisherSet(this,
                op,
                domain,
                networkZone,
                sessionType,
                sessionName,
                remotePublisherQos,
                remotePublisherQosProps,
                remotePublisherPriority);
        publisherSets.add(rv);

        for (MALProvider e : providers) {
            rv.createPublisher(e);
        }

        return rv;
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
        if ((provider.getService().getArea().getNumber().getValue() != service.getArea().getNumber().getValue())
                || (provider.getService().getNumber().getValue() != service.getNumber().getValue())) {
            throw new MALException("Adding provider of service "
                    + provider.getService().getName()
                    + " to a provider set for service "
                    + service.getName());
        }

        providers.add(provider);

        for (MALPublisherSet e : publisherSets) {
            e.createPublisher(provider);
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
        for (MALPublisherSet e : publisherSets) {
            try {
                e.deletePublisher(provider);
            } catch (MALException ex) {
                // ToDo
            }
        }
        return true;
    }
}
