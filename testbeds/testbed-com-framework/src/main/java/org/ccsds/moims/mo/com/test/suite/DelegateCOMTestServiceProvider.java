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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.ccsds.moims.mo.com.archive.ArchiveServiceInfo;
import org.ccsds.moims.mo.com.event.EventServiceInfo;
import org.ccsds.moims.mo.comprototype.activityrelaymanagement.ActivityRelayManagementServiceInfo;
import org.ccsds.moims.mo.comprototype.activitytest.ActivityTestServiceInfo;
import org.ccsds.moims.mo.comprototype.archivetest.ArchiveTestServiceInfo;
import org.ccsds.moims.mo.comprototype.eventtest.EventTestServiceInfo;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.testbed.util.Configuration;
import static org.ccsds.moims.mo.testbed.util.LoggingBase.logMessage;

/**
 * Use this to test an implementation that is executed externally from here.
 */
public class DelegateCOMTestServiceProvider extends UriCOMTestServiceProvider {

    protected List<URItriple> initURIs() throws MALException {
        Properties uriPrp = Configuration.getProperties("ServiceProviderURI.properties", true);
        logMessage("Delegate: uri props: " + uriPrp);
        System.getProperties().putAll(uriPrp);

        List<URItriple> returnValues = new ArrayList<>();

        returnValues.add(getServiceURIs(ActivityTestServiceInfo.ACTIVITYTEST_SERVICE_NAME));
        returnValues.add(getServiceURIs(ActivityRelayManagementServiceInfo.ACTIVITYRELAYMANAGEMENT_SERVICE_NAME));
        returnValues.add(getServiceURIs(new Identifier(LocalMALInstance.ACTIVITY_EVENT_NAME)));
        returnValues.add(getServiceURIs(EventTestServiceInfo.EVENTTEST_SERVICE_NAME));
        returnValues.add(getServiceURIs(EventServiceInfo.EVENT_SERVICE_NAME));
        returnValues.add(getServiceURIs(ArchiveServiceInfo.ARCHIVE_SERVICE_NAME));
        returnValues.add(getServiceURIs(ArchiveTestServiceInfo.ARCHIVETEST_SERVICE_NAME));
        returnValues.add(getServiceURIs(new Identifier(LocalMALInstance.ARCHIVE_EVENT_NAME)));

        return returnValues;
    }

    protected URItriple getServiceURIs(Identifier serviceName) {
        String servicePropery = serviceName + "URI";
        String brokerPropery = serviceName + "Broker";
        logMessage("Looking for provider properties : " + servicePropery + " , " + brokerPropery);

        URI serviceURI = new URI(System.getProperty(servicePropery));
        URI brokerURI = new URI(System.getProperty(brokerPropery));
        return new URItriple(serviceName, serviceURI, brokerURI);
    }
}
