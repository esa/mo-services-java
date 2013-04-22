/* ----------------------------------------------------------------------------
 * (C) 2010      European Space Agency
 *               European Space Operations Centre
 *               Darmstadt Germany
 * ----------------------------------------------------------------------------
 * System       : CCSDS MO MAL Implementation
 * Author       : Sam Cooper
 *
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.impl;

import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.MALEndpoint;

/**
 * Simple struct style class for holding an endpoint and its details.
 */
public final class Address
{
  /**
   * The endpoint to use with this Address.
   */
  public final MALEndpoint endpoint;
  /**
   * The URI that this Address represents.
   */
  public final URI uri;
  /**
   * The authentication Id of this Address.
   */
  public final Blob authenticationId;
  /**
   * The internal interaction handler that uses this address.
   */
  public final MALInteractionHandler handler;

  /**
   * Constructor.
   *
   * @param endpoint Endpoint.
   * @param uri URI.
   * @param authenticationId Authentication identifier.
   * @param handler Interaction handler.
   */
  public Address(final MALEndpoint endpoint,
          final URI uri,
          final Blob authenticationId,
          final MALInteractionHandler handler)
  {
    this.endpoint = endpoint;
    this.uri = uri;
    this.authenticationId = authenticationId;
    this.handler = handler;
  }
}
