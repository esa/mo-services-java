/** *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 ****************************************************************************** */
package org.ccsds.moims.mo.mal.test.patterns.pubsub;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.test.suite.TestServiceProvider;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

public class HeaderTestProcedure extends LoggingBase {

    public static final IdentifierList DOMAIN = getDomain(0);
    public static final Blob AUTHENTICATION_ID = new Blob(new byte[]{0x00, 0x01});
    public static final Identifier NETWORK_ZONE = new Identifier("NetworkZone");
    public static final UInteger PRIORITY = new UInteger(1);
    public static final Identifier RIGHT_KEY_NAME = new Identifier("A");
    public static final Identifier WRONG_KEY_NAME = new Identifier("B");
    public static final Identifier PUBLISH_REGISTER_ERROR_KEY_VALUE = new Identifier("PublishRegisterErrorEntity");
    public static final Identifier SUBSCRIPTION_ID = new Identifier("sub1");
    public static final Identifier REGISTER_ERROR_SUBSCRIPTION_ID = new Identifier("RegisterErrorSubscription");
    private static HeaderTestProcedureImpl realInstance = null;

    public HeaderTestProcedure() {
    }

    public void createFixtureSingleInstance() {
        realInstance = new HeaderTestProcedureImpl();
    }

    public void deleteFixtureSingleInstance() {
        realInstance = null;
    }

    public boolean logTime(String str) {
        logMessage("TIMESTAMP (" + str + ") " + new java.util.Date().getTime());
        return true;
    }

    public boolean initiatePublishRegisterWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiatePublishRegisterWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean CallTheOperationGetResult() throws MALInteractionException, MALException {
        return realInstance.CallTheOperationGetResult();
    }

    public boolean theProviderAssertions() {
        return realInstance.theProviderAssertions();
    }

    public boolean initiateRegisterWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiateRegisterWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean theConsumerAssertions() {
        return realInstance.theConsumerAssertions();
    }

    public boolean initiatePublishWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiatePublishWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean getNotifyWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.getNotifyWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiateNotifyErrorWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiateNotifyErrorWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiatePublishErrorWithQosAndSessionAndSharedBrokerAndDomain(String qosLevel,
            String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiatePublishErrorWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiateDeregisterWithQosAndSessionAndSharedBrokerAndDomain(
            String qosLevel, String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiateDeregisterWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiatePublishDeregisterWithQosAndSessionAndSharedBrokerAndDomain(
            String qosLevel, String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiatePublishDeregisterWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiatePublishRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(
            String qosLevel, String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiatePublishRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public boolean initiateRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(
            String qosLevel, String sessionType, String sharedBroker, int domain) throws Exception {
        return realInstance.initiateRegisterErrorWithQosAndSessionAndSharedBrokerAndDomain(qosLevel, sessionType, sharedBroker, domain);
    }

    public static IdentifierList getDomain(int index) {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("Test"));
        domain.add(new Identifier("Domain" + index));
        return domain;
    }

    public static boolean isSharedbroker(URI brokerURI) {
        FileBasedDirectory.URIpair uris
                = FileBasedDirectory.loadURIs(TestServiceProvider.IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME);
        return (brokerURI.equals(uris.broker));
    }

    public static Blob getBrokerAuthId(boolean shared) {
        if (shared) {
            return FileBasedDirectory.loadSharedBrokerAuthenticationId();
        } else {
            return FileBasedDirectory.loadPrivateBrokerAuthenticationId();
        }
    }
}
