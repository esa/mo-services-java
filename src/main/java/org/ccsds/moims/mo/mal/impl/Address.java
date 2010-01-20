/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 *
 * @author cooper_sf
 */
public final class Address
{
  public final MALEndPoint endpoint;
  public final URI uri;
  public final Blob authenticationId;
  public final MALInteractionHandler handler;

  public Address(MALEndPoint endpoint, URI uri, Blob authenticationId, MALInteractionHandler handler)
  {
    this.endpoint = endpoint;
    this.uri = uri;
    this.authenticationId = authenticationId;
    this.handler = handler;
  }
}
