/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 * Simple structure style class that holds a single notify message body.
 */
public class NotifyMessage {

    /**
     * PubSub domain.
     */
    public IdentifierList domain;
    /**
     * PubSub network zone.
     */
    public Identifier networkZone;
    /**
     * PubSub area.
     */
    public UShort area;
    /**
     * PubSub service.
     */
    public UShort service;
    /**
     * PubSub operation.
     */
    public UShort operation;
    /**
     * PubSub version.
     */
    public UOctet version;
    /**
     * PubSub subscription Id.
     */
    public Identifier subscriptionId;
    /**
     * PubSub update headers.
     */
    public UpdateHeaderList updateHeaderList;
    /**
     * PubSub updates.
     */
    public java.util.List[] updateList;

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
}
