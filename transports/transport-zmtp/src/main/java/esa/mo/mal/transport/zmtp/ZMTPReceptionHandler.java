/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import java.util.logging.Level;
import org.ccsds.moims.mo.mal.MALException;
import esa.mo.mal.transport.gen.sending.MessageSender;
import esa.mo.mal.transport.gen.ReceptionHandler;

/**
 *
 * @author Dominik Marszk
 */
public class ZMTPReceptionHandler implements ReceptionHandler {

    /**
     * Reference to the parent transport.
     */
    private final ZMTPTransport transport;

    /**
     * Associated channel source.
     */
    private ZMTPChannelSource sender;

    /**
     * Remote root MAL URI.
     */
    private String remoteURI;

    public ZMTPReceptionHandler(ZMTPTransport transport) {
        this.transport = transport;
    }

    @Override
    public String getRemoteURI() {
        return remoteURI;
    }

    @Override
    public void setRemoteURI(String newURI) {
        remoteURI = newURI;
    }

    @Override
    public MessageSender getMessageSender() {
        // Message Sender is instantiated by transport for each URL when it is needed
        if (sender == null) {
            try {
                sender = transport.createMessageSender(getRemoteURI());
            } catch (MALException ex) {
                ZMTPTransport.LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        return sender;
    }

    @Override
    public void close() {
        // If there's associated sender, close it
        if (sender != null) {
            sender.close();
        }
    }
}
