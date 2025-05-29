/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Common services
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
package esa.mo.common.impl.consumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.common.directory.DirectoryHelper;
import org.ccsds.moims.mo.common.directory.consumer.DirectoryStub;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.consumer.MALConsumer;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * The Directory service implementation, consumer side.
 */
public class DirectoryConsumerServiceImpl extends ConsumerServiceImpl {

    private final URI providerURI;

    private DirectoryStub directoryService = null;

    /**
     * Constructor for the Directory service consumer.
     *
     * @param providerURI The URI of the provider.
     * @throws MALException If the Directory service could not be started.
     */
    public DirectoryConsumerServiceImpl(final URI providerURI) throws MALException {
        this(providerURI, null, null);
    }

    /**
     * Constructor for the Directory service consumer.
     *
     * @param providerURI The URI of the provider.
     * @param authenticationId The authenticationId token.
     * @param localNamePrefix The local name prefix.
     * @throws MALException If the Directory service could not be started.
     */
    public DirectoryConsumerServiceImpl(final URI providerURI, final Blob authenticationId,
            final String localNamePrefix) throws MALException {
        this.connectionDetails = null;
        this.providerURI = providerURI;

        // Close old connection
        if (tmConsumer != null) {
            try {
                tmConsumer.close();
            } catch (MALException ex) {
                Logger.getLogger(DirectoryConsumerServiceImpl.class.getName()).log(
                        Level.SEVERE, "The previous connection could not be closed!", ex);
            }
        }

        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("*"));

        tmConsumer = connection.startService(providerURI, null, domain,
                DirectoryHelper.DIRECTORY_SERVICE, authenticationId, localNamePrefix);

        this.directoryService = new DirectoryStub(tmConsumer);
    }

    /**
     * Returns the provider URI.
     *
     * @return The provider URI.
     */
    public URI getProviderURI() {
        return this.providerURI;
    }

    @Override
    public Object generateServiceStub(MALConsumer tmConsumer) {
        return new DirectoryStub(tmConsumer);
    }

    @Override
    public Object getStub() {
        return this.getDirectoryStub();
    }

    /**
     * Returns the Directory service stub.
     *
     * @return The Directory service stub.
     */
    public DirectoryStub getDirectoryStub() {
        return this.directoryService;
    }
}
