/* ----------------------------------------------------------------------------
 * Copyright (C) 2025      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package org.ccsds.moims.mo.mc;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.helpertools.connections.SingleConnectionDetails;
import org.ccsds.moims.mo.mc.action.consumer.ActionStub;
import org.ccsds.moims.mo.mc.action.provider.ActionInheritanceSkeleton;
import org.ccsds.moims.mo.mc.aggregation.consumer.AggregationStub;
import org.ccsds.moims.mo.mc.aggregation.provider.AggregationInheritanceSkeleton;
import org.ccsds.moims.mo.mc.alert.consumer.AlertStub;
import org.ccsds.moims.mo.mc.alert.provider.AlertInheritanceSkeleton;
import org.ccsds.moims.mo.mc.backends.ActionsBackend;
import org.ccsds.moims.mo.mc.backends.AggregationsBackend;
import org.ccsds.moims.mo.mc.backends.AlertsBackend;
import org.ccsds.moims.mo.mc.backends.PacketsBackend;
import org.ccsds.moims.mo.mc.backends.ParametersBackend;
import org.ccsds.moims.mo.mc.packet.consumer.PacketStub;
import org.ccsds.moims.mo.mc.packet.provider.PacketInheritanceSkeleton;
import org.ccsds.moims.mo.mc.parameter.consumer.ParameterStub;
import org.ccsds.moims.mo.mc.parameter.provider.ParameterInheritanceSkeleton;

/**
 * The interface for the MC services Factory.
 */
public abstract class MCServicesFactory {

    public abstract ActionInheritanceSkeleton createProviderAction(ActionsBackend backend) throws MALException;

    public abstract AggregationInheritanceSkeleton createProviderAggregation(AggregationsBackend backend) throws MALException;

    public abstract AlertInheritanceSkeleton createProviderAlert(AlertsBackend backend) throws MALException;

    public abstract PacketInheritanceSkeleton createProviderPacket(PacketsBackend backend) throws MALException;

    public abstract ParameterInheritanceSkeleton createProviderParameter(ParametersBackend backend) throws MALException;

    public abstract ActionStub createConsumerStubAction(SingleConnectionDetails details) throws MALException;

    public abstract AggregationStub createConsumerStubAggregation(SingleConnectionDetails details) throws MALException;

    public abstract AlertStub createConsumerStubAlert(SingleConnectionDetails details) throws MALException;

    public abstract PacketStub createConsumerStubPacket(SingleConnectionDetails details) throws MALException;

    public abstract ParameterStub createConsumerStubParameter(SingleConnectionDetails details) throws MALException;

}
