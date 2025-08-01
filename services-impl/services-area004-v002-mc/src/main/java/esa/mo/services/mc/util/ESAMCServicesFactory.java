/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO services
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
package esa.mo.services.mc.util;

import esa.mo.services.mc.consumer.ParameterConsumerServiceImpl;
import esa.mo.services.mc.provider.ParameterProviderServiceImpl;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mal.helpertools.misc.ConsumerServiceImpl;
import org.ccsds.moims.mo.mc.MCServicesFactory;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.aggregation.provider.AggregationInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.backends.ActionBackend;
import org.ccsds.moims.mo.mc.backends.AggregationBackend;
import org.ccsds.moims.mo.mc.backends.AlertBackend;
import org.ccsds.moims.mo.mc.backends.PacketBackend;
import org.ccsds.moims.mo.mc.backends.ParameterBackend;
import org.ccsds.moims.mo.mc.packet.consumer.PacketStub;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterStub;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;

/**
 * The factory class to instantiate the MC services.
 */
public class ESAMCServicesFactory extends MCServicesFactory {

    private ParameterProviderServiceImpl parameterService = null;

    /**
     * Constructor.
     */
    public ESAMCServicesFactory() {
    }

    @Override
    public ActionInheritanceSkeleton createProviderAction(ActionBackend backend) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AggregationInheritanceSkeleton createProviderAggregation(AggregationBackend backend) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AlertInheritanceSkeleton createProviderAlert(AlertBackend backend) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PacketInheritanceSkeleton createProviderPacket(PacketBackend backend) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ParameterInheritanceSkeleton createProviderParameter(ParameterBackend backend) throws MALException {
        parameterService = new ParameterProviderServiceImpl();
        parameterService.init(backend);
        return parameterService;
    }

    @Override
    public ActionStub createConsumerStubAction(SingleConnectionDetails details) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AggregationStub createConsumerStubAggregation(SingleConnectionDetails details) throws MALException {
        if (parameterService == null) {
            throw new MALException("The parameterService needs to be instantiated before!");
        }

        /*
        aggregationService = new AggregationProviderServiceImpl();
        aggregationService.init(parameterService);
        return aggregationService;
         */
        return null;
    }

    @Override
    public AlertStub createConsumerStubAlert(SingleConnectionDetails details) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PacketStub createConsumerStubPacket(SingleConnectionDetails details) throws MALException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ParameterStub createConsumerStubParameter(SingleConnectionDetails details) throws MALException {
        if (details == null) {
            throw new MALException("The provider details are null!");
        }

        ConsumerServiceImpl consumerService = new ParameterConsumerServiceImpl(details);
        return (ParameterStub) consumerService.getStub();
    }
}
