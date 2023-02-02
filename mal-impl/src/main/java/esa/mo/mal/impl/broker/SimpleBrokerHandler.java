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
package esa.mo.mal.impl.broker;

import esa.mo.mal.impl.pubsub.SubscriptionSource;
import esa.mo.mal.impl.util.MALClose;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Extends the base broker handler for the Simple broker implementation.
 */
public class SimpleBrokerHandler extends MALBrokerHandlerImpl {

    /**
     * Constructor
     *
     * @param parent The parent of this class.
     */
    public SimpleBrokerHandler(MALClose parent) {
        super(parent);
    }

    @Override
    protected SubscriptionSource createEntry(final MALMessageHeader hdr) {
        return new SubscriptionSource(hdr);
    }
}
