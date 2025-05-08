/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package org.ccsds.moims.mo.mal.test.suite;

import java.io.Writer;
import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementsRegistry;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.QoSLevel;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.test.accesscontrol.TestAccessControlFactory;
import org.ccsds.moims.mo.mal.test.datatype.DataTestHandlerImpl;
import org.ccsds.moims.mo.mal.test.errors.ErrorTestHandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.IPTest2HandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.IPTestFromArea2HandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.IPTestHandlerImpl;
import org.ccsds.moims.mo.mal.test.patterns.pubsub.IPTestHandlerWithSharedBroker;
import org.ccsds.moims.mo.mal.test.transport.MALTestEndPointSendInterceptor;
import org.ccsds.moims.mo.mal.test.util.Helper;
import org.ccsds.moims.mo.malprototype.MALPrototypeHelper;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestHelper;
import org.ccsds.moims.mo.malprototype.errortest.ErrorTestServiceInfo;
import org.ccsds.moims.mo.malprototype.iptest.IPTestHelper;
import org.ccsds.moims.mo.malprototype.iptest.IPTestServiceInfo;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2Helper;
import org.ccsds.moims.mo.malprototype.iptest2.IPTest2ServiceInfo;
import org.ccsds.moims.mo.malprototype2.MALPrototype2Helper;
import org.ccsds.moims.mo.testbed.suite.BaseTestServiceProvider;
import org.ccsds.moims.mo.testbed.transport.TransportInterceptor;
import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory;
import org.ccsds.moims.mo.testbed.util.FileBasedDirectory.URIpair;

public class TestServiceProvider extends BaseTestServiceProvider {

    public static final Blob DATA_TEST_AUTHENTICATION_ID = new Blob(new byte[]{
        0x01, 0x00
    });
    public static final Blob ERROR_TEST_AUTHENTICATION_ID = new Blob(new byte[]{
        0x01, 0x01
    });
    public static final Blob IP_TEST_AUTHENTICATION_ID = new Blob(new byte[]{
        0x01, 0x02
    });
    public static final String IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME
            = IPTestServiceInfo.IPTEST_SERVICE_NAME.getValue() + "SharedBroker";
    public static final String IP_TEST_PROVIDER_FROM_AREA2_NAME
            = IPTestServiceInfo.IPTEST_SERVICE_NAME.getValue() + "FromArea2";
    public static final String IP_TEST_PROVIDER_FROM_AREA2_WITH_SHARED_BROKER_NAME
            = IP_TEST_PROVIDER_FROM_AREA2_NAME + "SharedBroker";

    @Override
    public void execute(Writer out, ExitCondition exitCond, String[] argv) throws Exception {
        System.getProperties().setProperty(TestAccessControlFactory.FACTORY_PROP_NAME, TestAccessControlFactory.class.getName());
        logMessage("Access control system property set to: " + System.getProperty(TestAccessControlFactory.FACTORY_PROP_NAME));

        super.execute(out, exitCond, argv);
    }

    @Override
    protected String getProtocol() {
        return System.getProperty(Configuration.TEST_PROTOCOL);
    }

    protected void initHelpers() throws MALException {
        MALElementsRegistry registry = MALContextFactory.getElementsRegistry();
        registry.loadFullArea(MALPrototypeHelper.MALPROTOTYPE_AREA);
        registry.loadFullArea(MALPrototype2Helper.MALPROTOTYPE2_AREA);

        TransportInterceptor.instance().setEndpointSendInterceptor(new MALTestEndPointSendInterceptor());
    }

    protected void createProviders() throws MALException {
        String protocol = getProtocol();

        URIpair sharedBrokerUriPair
                = FileBasedDirectory.loadURIs(Configuration.SHARED_BROKER_NAME);

        MALInteractionHandler dthandler = new DataTestHandlerImpl();
        MALInteractionHandler erhandler = new ErrorTestHandlerImpl();
        MALInteractionHandler iphandler = new IPTestHandlerImpl();
        MALInteractionHandler ipFromArea2handler = new IPTestFromArea2HandlerImpl();
        MALInteractionHandler ip2handler = new IPTest2HandlerImpl();

        MALProvider dtprovider = defaultProviderMgr.createProvider(
                "DataTest",
                protocol,
                DataTestHelper.DATATEST_SERVICE,
                DATA_TEST_AUTHENTICATION_ID,
                dthandler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.FALSE, // isPublisher
                null,
                null);

        MALProvider erprovider = defaultProviderMgr.createProvider(
                "ErrorTest",
                protocol,
                ErrorTestHelper.ERRORTEST_SERVICE,
                ERROR_TEST_AUTHENTICATION_ID,
                erhandler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.FALSE, // isPublisher
                null,
                null);

        MALProvider ipprovider = defaultProviderMgr.createProvider(
                "IPTest",
                protocol,
                IPTestHelper.IPTEST_SERVICE,
                IP_TEST_AUTHENTICATION_ID,
                iphandler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.TRUE, // isPublisher
                null,
                Helper.supplementsIPTestProvider);

        FileBasedDirectory.storePrivateBrokerAuthenticationId(
                ipprovider.getBrokerAuthenticationId());

        MALInteractionHandler iphandlerWithSharedBroker = new IPTestHandlerWithSharedBroker();
        MALProvider ipproviderSharedBroker = defaultProviderMgr.createProvider(
                IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME,
                protocol,
                IPTestHelper.IPTEST_SERVICE,
                IP_TEST_AUTHENTICATION_ID,
                iphandlerWithSharedBroker,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.TRUE, // isPublisher
                sharedBrokerUriPair.broker,
                null);
        FileBasedDirectory.storeURI(IP_TEST_PROVIDER_WITH_SHARED_BROKER_NAME,
                ipproviderSharedBroker.getURI(), ipproviderSharedBroker.getBrokerURI());

        MALProvider ipFromArea2providerWithSharedBroker = defaultProviderMgr.createProvider(
                IP_TEST_PROVIDER_FROM_AREA2_WITH_SHARED_BROKER_NAME,
                protocol,
                org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE,
                IP_TEST_AUTHENTICATION_ID,
                ipFromArea2handler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.TRUE, // isPublisher
                sharedBrokerUriPair.broker,
                null);

        MALProvider ipFromArea2provider = defaultProviderMgr.createProvider(
                IP_TEST_PROVIDER_FROM_AREA2_NAME,
                protocol,
                org.ccsds.moims.mo.malprototype2.iptest.IPTestHelper.IPTEST_SERVICE,
                IP_TEST_AUTHENTICATION_ID,
                ipFromArea2handler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.TRUE, // isPublisher
                null,
                null);

        MALProvider ip2provider = defaultProviderMgr.createProvider(
                "IPTest2",
                protocol,
                IPTest2Helper.IPTEST2_SERVICE,
                IP_TEST_AUTHENTICATION_ID,
                ip2handler,
                new QoSLevel[]{
                    QoSLevel.ASSURED
                },
                new UInteger(1), // number of priority levels
                null,
                Boolean.TRUE, // isPublisher
                sharedBrokerUriPair.broker,
                null);

        FileBasedDirectory.storePrivateBrokerAuthenticationId(
                ipprovider.getBrokerAuthenticationId());

        FileBasedDirectory.storeURI(DataTestServiceInfo.DATATEST_SERVICE_NAME.getValue(), dtprovider.getURI(), dtprovider.getBrokerURI());
        FileBasedDirectory.storeURI(ErrorTestServiceInfo.ERRORTEST_SERVICE_NAME.getValue(), erprovider.getURI(), erprovider.getBrokerURI());
        FileBasedDirectory.storeURI(IPTestServiceInfo.IPTEST_SERVICE_NAME.getValue(), ipprovider.getURI(), ipprovider.getBrokerURI());
        FileBasedDirectory.storeURI(IP_TEST_PROVIDER_FROM_AREA2_NAME, ipFromArea2provider.getURI(), ipFromArea2provider.getBrokerURI());
        FileBasedDirectory.storeURI(IP_TEST_PROVIDER_FROM_AREA2_WITH_SHARED_BROKER_NAME, ipFromArea2providerWithSharedBroker.getURI(), ipFromArea2providerWithSharedBroker.getBrokerURI());
        FileBasedDirectory.storeURI(IPTest2ServiceInfo.IPTEST2_SERVICE_NAME.getValue(), ip2provider.getURI(), ip2provider.getBrokerURI());
    }
}
