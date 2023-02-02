/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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
package esa.mo.mal.impl.pubsub;

import java.util.List;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple structure style class that holds a single notify message body.
 */
public class NotifyMessageBody {

    /**
     * PubSub domain.
     */
    private final IdentifierList domain;
    /**
     * PubSub network zone.
     */
    private final Identifier networkZone;
    /**
     * PubSub area.
     */
    private final UShort area;
    /**
     * PubSub service.
     */
    private final UShort service;
    /**
     * PubSub operation.
     */
    private final UShort operation;
    /**
     * PubSub version.
     */
    private final UOctet version;
    /**
     * PubSub subscription Id.
     */
    private final Identifier subscriptionId;
    /**
     * PubSub update headers.
     */
    private final UpdateHeaderList updateHeaderList;
    /**
     * PubSub updates.
     */
    private final java.util.List[] updateList;

    public NotifyMessageBody(Identifier subscriptionId, UpdateHeaderList updateHeaderList,
            java.util.List[] notifyList, MALMessageHeader srcHdr) {
        this.subscriptionId = subscriptionId;
        this.updateHeaderList = updateHeaderList;
        this.updateList = notifyList;
        this.domain = srcHdr.getDomain();
        this.networkZone = srcHdr.getNetworkZone();
        this.area = srcHdr.getServiceArea();
        this.service = srcHdr.getService();
        this.operation = srcHdr.getOperation();
        this.version = srcHdr.getAreaVersion();
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append(" >> domain: ").append(domain);
        buf.append("\n >> networkZone: ").append(networkZone);
        buf.append("\n >> area/service/version/operation: ");
        buf.append(area).append("/").append(service).append("/");
        buf.append(version).append("/").append(operation);
        buf.append("\n >> subscriptionId: ").append(subscriptionId);
        buf.append("\n >> updateHeaderList: ").append(updateHeaderList);
        return buf.toString();
    }

    public IdentifierList getDomain() {
        return domain;
    }

    public Identifier getNetworkZone() {
        return networkZone;
    }

    public UShort getArea() {
        return area;
    }

    public UShort getService() {
        return service;
    }

    public UShort getOperation() {
        return operation;
    }

    public UOctet getVersion() {
        return version;
    }

    public Identifier getSubscriptionId() {
        return subscriptionId;
    }

    public UpdateHeaderList getUpdateHeaderList() {
        return updateHeaderList;
    }

    public List[] getUpdateList() {
        return updateList;
    }
}
