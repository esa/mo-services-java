/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Test bed
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
package org.ccsds.moims.mo.com.test.suite;

import java.util.List;
import org.ccsds.moims.mo.com.activitytracking.ActivityTrackingHelper;
import org.ccsds.moims.mo.com.archive.ArchiveHelper;
import org.ccsds.moims.mo.com.event.EventHelper;
import org.ccsds.moims.mo.comprototype.COMPrototypeHelper;
import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementHelper;
import org.ccsds.moims.mo.comprototype.activitytest.ActivityTestHelper;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestHelper;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestHelper;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.testbed.suite.BaseTestServiceProvider;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 * Returns the URIs needed for connecting to the remote services.
 */
public abstract class UriCOMTestServiceProvider extends BaseTestServiceProvider {

    public String getProtocol() {
        return System.getProperty(Configuration.DEFAULT_PROTOCOL);
    }

    protected void initHelpers() throws MALException {
        org.ccsds.moims.mo.com.COMHelper.init(MALContextFactory.getElementsRegistry());
        ActivityTrackingHelper.init(MALContextFactory.getElementsRegistry());
        ArchiveHelper.init(MALContextFactory.getElementsRegistry());
        EventHelper.init(MALContextFactory.getElementsRegistry());

        COMPrototypeHelper.init(MALContextFactory.getElementsRegistry());
        ActivityTestHelper.init(MALContextFactory.getElementsRegistry());
        ActivityRelayManagementHelper.init(MALContextFactory.getElementsRegistry());
        ArchiveTestHelper.init(MALContextFactory.getElementsRegistry());
        EventTestHelper.init(MALContextFactory.getElementsRegistry());
    }

    protected void createProviders() throws MALException {
        logMessage("Create Providers Called");

        List<URItriple> triples = initURIs();

        for (URItriple triple : triples) {
            FileBasedDirectory.storeURI(triple.getServiceName().getValue(), triple.getServiceURI(), triple.getBrokerURI());
        }
    }

    abstract protected List<URItriple> initURIs() throws MALException;

    public static final class URItriple {

        private final Identifier serviceName;
        private final URI serviceURI;
        private final URI brokerURI;

        public URItriple(Identifier serviceName, URI serviceURI, URI brokerURI) {
            this.serviceName = serviceName;
            this.serviceURI = serviceURI;
            this.brokerURI = brokerURI;
        }

        public Identifier getServiceName() {
            return serviceName;
        }

        public URI getServiceURI() {
            return serviceURI;
        }

        public URI getBrokerURI() {
            return brokerURI;
        }
    }
}
