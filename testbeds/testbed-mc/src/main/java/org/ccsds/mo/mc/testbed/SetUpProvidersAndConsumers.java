/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Testbed
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
package org.ccsds.mo.mc.testbed;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mc.MCServicesFactory;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.aggregation.provider.AggregationInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.backends.*;
import org.ccsds.moims.mo.mc.packet.consumer.PacketStub;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterStub;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;

/**
 *
 * @author Cesar.Coelho
 */
public class SetUpProvidersAndConsumers {

    private static ActionInheritanceSkeleton actionProviderService = null;
    private static ActionStub actionConsumerStub = null;

    private static AggregationInheritanceSkeleton aggregationProviderService = null;
    private static AggregationStub aggregationConsumerStub = null;

    private static AlertInheritanceSkeleton alertProviderService = null;
    private static AlertStub alertConsumerStub = null;

    private static PacketInheritanceSkeleton packetProviderService = null;
    private static PacketStub packetConsumerStub = null;

    private static ParameterInheritanceSkeleton parameterProviderService = null;
    private static ParameterStub parameterConsumerStub = null;

    public void setUp(ActionBackend actionBackend, AlertBackend alertBackend,
            PacketBackend packetBackend, ParameterBackend parameterBackend,
            boolean startAction, boolean startAggregation, boolean startAlert,
            boolean startPacket, boolean startParameter) throws IOException {
        HelperMisc.loadPropertiesFile();
        ConnectionProvider.resetURILinksFile(); // Resets the providerURIs.properties file

        try {
            // Dynamic load here: It can be either for ESA's or CNES's implementation
            // And also the consumer and provider need to be selectable!
            // This can be achieved with the Factory pattern

            String factoryClassForProvider = System.getProperty("testbed.provider");
            String factoryClassForConsumer = System.getProperty("testbed.consumer");
            System.out.println("  >> factoryClassForProvider: " + factoryClassForProvider);
            System.out.println("  >> factoryClassForConsumer: " + factoryClassForConsumer);

            if ("null".equals(factoryClassForProvider) || "".equals(factoryClassForProvider)) {
                throw new IOException("The classname is empty or null for the provider side! "
                        + "Please select the correct Maven profile before running the test!");
            }

            if ("null".equals(factoryClassForConsumer) || "".equals(factoryClassForConsumer)) {
                throw new IOException("The classname is empty or null for the consumer side! "
                        + "Please select the correct Maven profile before running the test!");
            }

            // Provider Factory:
            Class factoryClassProvider = Class.forName(factoryClassForProvider);
            MCServicesFactory factoryProvider = (MCServicesFactory) factoryClassProvider.newInstance();
            // Consumer Factory:
            Class factoryClassConsumer = Class.forName(factoryClassForConsumer);
            MCServicesFactory factoryConsumer = (MCServicesFactory) factoryClassConsumer.newInstance();

            if (startAction) {
                actionProviderService = factoryProvider.createProviderAction(actionBackend);

                if (actionProviderService == null) {
                    throw new MALException("The Action service provider was not created!");
                }

                SingleConnectionDetails details = actionProviderService.getConnection().getConnectionDetails();
                actionConsumerStub = factoryConsumer.createConsumerStubAction(details);
            }

            if (startAggregation) {
                aggregationProviderService = factoryProvider.createProviderAggregation(parameterBackend);

                if (aggregationProviderService == null) {
                    throw new MALException("The Aggregation service provider was not created!");
                }

                SingleConnectionDetails details = aggregationProviderService.getConnection().getConnectionDetails();
                aggregationConsumerStub = factoryConsumer.createConsumerStubAggregation(details);
            }

            if (startAlert) {
                alertProviderService = factoryProvider.createProviderAlert(alertBackend);

                if (alertProviderService == null) {
                    throw new MALException("The Alert service provider was not created!");
                }

                SingleConnectionDetails details = alertProviderService.getConnection().getConnectionDetails();
                alertConsumerStub = factoryConsumer.createConsumerStubAlert(details);
            }

            if (startPacket) {
                packetProviderService = factoryProvider.createProviderPacket(packetBackend);

                if (packetProviderService == null) {
                    throw new MALException("The Packet service provider was not created!");
                }

                SingleConnectionDetails details = packetProviderService.getConnection().getConnectionDetails();
                packetConsumerStub = factoryConsumer.createConsumerStubPacket(details);
            }

            if (startParameter) {
                parameterProviderService = factoryProvider.createProviderParameter(parameterBackend);

                if (parameterProviderService == null) {
                    throw new MALException("The Parameter service provider was not created!");
                }

                SingleConnectionDetails details = parameterProviderService.getConnection().getConnectionDetails();
                parameterConsumerStub = factoryConsumer.createConsumerStubParameter(details);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MALException ex) {
            Logger.getLogger(SetUpProvidersAndConsumers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ActionInheritanceSkeleton getActionProvider() {
        return actionProviderService;
    }

    public ActionStub getActionConsumer() {
        return actionConsumerStub;
    }

    public AggregationInheritanceSkeleton getAggregationProvider() {
        return aggregationProviderService;
    }

    public AggregationStub getAggregationConsumer() {
        return aggregationConsumerStub;
    }

    public AlertInheritanceSkeleton getAlertProvider() {
        return alertProviderService;
    }

    public AlertStub getAlertConsumer() {
        return alertConsumerStub;
    }

    public PacketInheritanceSkeleton getPacketProvider() {
        return packetProviderService;
    }

    public PacketStub getPacketConsumer() {
        return packetConsumerStub;
    }

    public ParameterInheritanceSkeleton getParameterProvider() {
        return parameterProviderService;
    }

    public ParameterStub getParameterConsumer() {
        return parameterConsumerStub;
    }

    public void tearDown() throws IOException {
        if (actionProviderService != null) {
            actionProviderService.getConnection().closeAll();
        }
        if (aggregationProviderService != null) {
            aggregationProviderService.getConnection().closeAll();
        }
        if (alertProviderService != null) {
            alertProviderService.getConnection().closeAll();
        }
        if (packetProviderService != null) {
            packetProviderService.getConnection().closeAll();
        }
        if (parameterProviderService != null) {
            parameterProviderService.getConnection().closeAll();
        }
    }
}
