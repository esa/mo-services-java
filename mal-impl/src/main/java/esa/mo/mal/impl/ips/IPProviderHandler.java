/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.ips;

import esa.mo.mal.impl.Address;
import esa.mo.mal.impl.MALSender;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.*;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.transport.MALEncodedBody;
import org.ccsds.moims.mo.mal.transport.MALMessage;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Base class for interactions.
 */
public abstract class IPProviderHandler implements MALInteraction {

    private final Map qosProperties = new HashMap();
    private final MALSender sender;
    private final Address address;
    private final MALMessage msg;
    private MALOperation operation;

    public IPProviderHandler(final MALSender sender, final Address address, final MALMessage msg) {
        this.sender = sender;
        this.address = address;
        this.msg = msg;
    }

    @Override
    public MALMessageHeader getMessageHeader() {
        return msg.getHeader();
    }

    @Override
    public MALOperation getOperation() {
        if (operation == null) {
            try {
                operation = msg.getHeader().getMALOperation();
            } catch (NotFoundException ex) {
                Logger.getLogger(IPProviderHandler.class.getName()).log(
                        Level.SEVERE, "The operation was not found!", ex);
            }
        }
        return operation;
    }

    @Override
    public Object getQoSProperty(final String name) {
        return qosProperties.get(name);
    }

    @Override
    public void setQoSProperty(final String name, final Object value) {
        qosProperties.put(name, value);
    }

    @Override
    public Map<String, Object> getQoSProperties() {
        return qosProperties;
    }

    /**
     * Returns the Address object used to create this object.
     *
     * @return the address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns a response to the consumer.
     *
     * @param stage Stage to use.
     * @param result Message body.
     * @return the sent message.
     * @throws MALException On error.
     */
    protected MALMessage returnResponse(final UOctet stage, final Object... result) throws MALException {
        return sender.returnResponse(address, msg.getHeader(),
                stage, operation, qosProperties, result);
    }

    /**
     * Returns an encoded response to the consumer.
     *
     * @param stage Stage to use.
     * @param body Encoded message body.
     * @return the sent message.
     * @throws MALException On error.
     */
    protected MALMessage returnResponse(final UOctet stage, final MALEncodedBody body) throws MALException {
        return sender.returnResponse(address, msg.getHeader(),
                stage, operation, qosProperties, body);
    }

    /**
     * Returns an error to the consumer.
     *
     * @param stage The stage to use.
     * @param error The error to send.
     * @return the sent message.
     * @throws MALException On error.
     */
    protected MALMessage returnError(final UOctet stage, final MOErrorException error) throws MALException {
        return sender.returnError(address, msg.getHeader(), stage, error);
    }
}
