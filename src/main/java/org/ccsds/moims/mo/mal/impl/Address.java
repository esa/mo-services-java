/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : cooper_sf
 *
 * ----------------------------------------------------------------------------
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndPoint;

/**
 * Simple class for holding details about and endpoint and its details.
 */
public final class Address
{
  public final MALEndPoint endpoint;
  public final URI uri;
  public final Blob authenticationId;
  public final MALInteractionHandler handler;

  /**
   * Constructor.
   * @param endpoint Endpoint.
   * @param uri URI.
   * @param authenticationId Authentication identifier.
   * @param handler Interaction handler.
   */
  public Address(MALEndPoint endpoint, URI uri, Blob authenticationId, MALInteractionHandler handler)
  {
    this.endpoint = endpoint;
    this.uri = uri;
    this.authenticationId = authenticationId;
    this.handler = handler;
  }
}
