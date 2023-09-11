/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Support library
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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALContext;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MALService;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.consumer.MALConsumerManager;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderManager;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;
import org.ccsds.moims.mo.mal.transport.MALTransport;

/**
 * A base class for service providers. Provides much of the boiler plate code
 * needed for an MO service provider. Calling methods, such as Main, should
 * create an instance of a derived class, call init(), call start, and then when
 * finished call stop.
 */
public abstract class BaseMalServer {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("esa.mo.mal");
    public static final Object terminateSignal = new Object();
    protected MALContextFactory malFactory;
    protected MALContext mal;
    protected MALConsumerManager consumerMgr;
    protected MALProviderManager providerMgr;
    protected MALTransport transport;
    protected MALEndpoint ep;
    protected final IdentifierList domain;
    protected final Identifier network;

    /**
     * Constructor.
     *
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseMalServer(IdentifierList domain, Identifier network) {
        this(null, null, null, null, domain, network);
    }

    /**
     * Constructor.
     *
     * @param malFactory The MAL factory to use.
     * @param mal The MAL context to use.
     * @param consumerMgr The consumer manager to use.
     * @param providerMgr The provider manager to use.
     * @param domain the domain of the service provider.
     * @param network The network of the service provider.
     */
    public BaseMalServer(MALContextFactory malFactory, MALContext mal, 
            MALConsumerManager consumerMgr, MALProviderManager providerMgr, 
            IdentifierList domain, Identifier network) {
        this.malFactory = malFactory;
        this.mal = mal;
        this.consumerMgr = consumerMgr;
        this.providerMgr = providerMgr;
        this.domain = domain;
        this.network = network;
    }

    /**
     * Initialises the service provider.
     *
     * @param localname Service provider local name, passed to the transport
     * when creating an Endpoint, may be null.
     * @param protocol The transport protocol to use, may be null to use
     * default.
     * @throws MALException If an error.
     * @throws MALInteractionException If an error.
     */
    public void init(final String localname, final String protocol)
            throws MALException, MALInteractionException {
        if (null == malFactory) {
            malFactory = MALContextFactory.newFactory();
        }
        if (null == mal) {
            mal = malFactory.createMALContext(System.getProperties());
        }
        if (null == consumerMgr) {
            consumerMgr = mal.createConsumerManager();
        }
        if (null == providerMgr) {
            providerMgr = mal.createProviderManager();
        }

        MALHelper.init(MALContextFactory.getElementsRegistry());
        subInitHelpers(MALContextFactory.getElementsRegistry());

        if (null == protocol) {
            transport = mal.getTransport(
                    System.getProperty("org.ccsds.moims.mo.mal.transport.default.protocol"));
        } else {
            transport = mal.getTransport(protocol);
        }

        ep = transport.createEndpoint(localname, System.getProperties());
        ep.startMessageDelivery();

        subInit();
    }

    /**
     * Called to tell the provider that it is starting.
     *
     * @throws MALException If an error.
     * @throws MALInteractionException If an error.
     */
    public void start() throws MALException, MALInteractionException {
        LOGGER.log(Level.INFO, "Provider URI : {0}", ep.getURI());
    }

    /**
     * Stops the MAL and providers.
     *
     * @throws MALException If an error.
     */
    public void stop() throws MALException {
        providerMgr.close();
        mal.close();
    }

    /**
     * Returns the contained MAL context.
     *
     * @return the contained MAL context
     */
    public MALContext getMal() {
        return mal;
    }

    /**
     * Returns the contained MAL consumer manager.
     *
     * @return the contained MAL consumer manager
     */
    public MALConsumerManager getConsumerMgr() {
        return consumerMgr;
    }

    /**
     * Returns the contained MAL provider manager.
     *
     * @return the contained MAL provider manager
     */
    public MALProviderManager getProviderMgr() {
        return providerMgr;
    }

    /**
     * Creates a MALConsumer to the supplied service and URIs.
     *
     * @param service The service for the consumer.
     * @return The MALConsumer instance.
     * @throws MALException On error.
     */
    public MALConsumer createConsumer(final MALService service) throws MALException {
        return createConsumer(null, service, ep.getURI(), ep.getURI());
    }

    /**
     * Creates a MALConsumer to the supplied service and URIs.
     *
     * @param cep MALEndpoint to use, may be NULL in shich case the internal
     * endpoint will be used.
     * @param service The service for the consumer.
     * @param uri The service and broker URI to use.
     * @return The MALConsumer instance.
     * @throws MALException On error.
     */
    public MALConsumer createConsumer(final MALEndpoint cep, 
            final MALService service, final URI uri) throws MALException {
        return createConsumer(cep, service, uri, uri);
    }

    /**
     * Creates a MALConsumer to the supplied service and URIs.
     *
     * @param cep MALEndpoint to use, may be NULL in shich case the internal
     * endpoint will be used.
     * @param service The service for the consumer.
     * @param uri The service URI to use.
     * @param brokerUri The broker URI to use.
     * @return The MALConsumer instance.
     * @throws MALException On error.
     */
    public MALConsumer createConsumer(final MALEndpoint cep, final MALService service, 
            final URI uri, final URI brokerUri) throws MALException {
        return consumerMgr.createConsumer(cep,
                null == uri ? ep.getURI() : uri,
                null == brokerUri ? ep.getURI() : brokerUri,
                service,
                new Blob("".getBytes()),
                domain,
                network,
                SessionType.LIVE,
                new Identifier("LIVE"),
                QoSLevel.BESTEFFORT,
                System.getProperties(),
                new UInteger(0),
                null);
    }

    protected MALProvider createProvider(MALService service, 
            MALInteractionHandler handler, boolean isPublisher) throws MALException {
        return providerMgr.createProvider(ep,
                service,
                new Blob("".getBytes()),
                handler,
                new QoSLevel[]{
                    QoSLevel.BESTEFFORT
                },
                new UInteger(1),
                System.getProperties(),
                isPublisher,
                null,
                null);
    }

    /**
     * Sub classes must implement this and perform their MAL service type
     * initialisation here.
     *
     * @param bodyElementFactory The MAL element factory registry to use.
     * @throws MALException If there is an error registering.
     */
    protected abstract void subInitHelpers(MALElementsRegistry bodyElementFactory) throws MALException;

    /**
     * Sub classes must implement this and perform any initialisation here.
     *
     * @throws MALException On error.
     * @throws MALInteractionException On error.
     */
    protected abstract void subInit() throws MALException, MALInteractionException;
}
