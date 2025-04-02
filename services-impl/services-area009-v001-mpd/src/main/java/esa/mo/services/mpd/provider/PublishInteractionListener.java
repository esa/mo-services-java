/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.provider;

import java.util.Map;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.provider.MALPublishInteractionListener;
import org.ccsds.moims.mo.mal.transport.MALErrorBody;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * PublishInteractionListener
 */
public final class PublishInteractionListener implements MALPublishInteractionListener {

    @Override
    public void publishDeregisterAckReceived(final MALMessageHeader header,
            final Map qosProperties) throws MALException {
        Logger.getLogger(PublishInteractionListener.class.getName()).fine(
                "PublishInteractionListener::publishDeregisterAckReceived");
    }

    @Override
    public void publishErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
        Logger.getLogger(PublishInteractionListener.class.getName()).fine(
                "PublishInteractionListener::publishErrorReceived");
    }

    @Override
    public void publishRegisterAckReceived(final MALMessageHeader header,
            final Map qosProperties) throws MALException {
        Logger.getLogger(PublishInteractionListener.class.getName()).fine(
                "PublishInteractionListener::publishRegisterAckReceived");
    }

    @Override
    public void publishRegisterErrorReceived(final MALMessageHeader header,
            final MALErrorBody body, final Map qosProperties) throws MALException {
        Logger.getLogger(PublishInteractionListener.class.getName()).fine(
                "PublishInteractionListener::publishRegisterErrorReceived");
    }

}
