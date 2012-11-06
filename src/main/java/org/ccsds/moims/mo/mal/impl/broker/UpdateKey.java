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
package org.ccsds.moims.mo.mal.impl.broker;

import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Simple class that represents a MAL update key.
 */
public final class UpdateKey extends ElementKey
{
  public final String domain;
  public final UShort area;
  public final UShort service;
  public final UShort operation;

  /**
   * Constructor.
   *
   * @param lst Entity key.
   */
  public UpdateKey(MALMessageHeader srcHdr, String domainId, EntityKey lst)
  {
    super(getIdValue(lst.getFirstSubKey()), lst.getSecondSubKey(), lst.getThirdSubKey(), lst.getFourthSubKey());

    this.domain = domainId;
    this.area = srcHdr.getServiceArea();
    this.service = srcHdr.getService();
    this.operation = srcHdr.getOperation();
  }

  @Override
  public int hashCode()
  {
    return HASH_MAGIC_NUMBER * super.hashCode() + (this.domain != null ? this.domain.hashCode() : 0);
  }

  @Override
  public String toString()
  {
    StringBuilder buf = new StringBuilder();
    buf.append('[');
    buf.append(this.domain);
    buf.append(':');
    buf.append(this.area);
    buf.append(':');
    buf.append(this.service);
    buf.append(':');
    buf.append(this.operation);
    buf.append(':');
    buf.append(this.key1);
    buf.append('.');
    buf.append(this.key2);
    buf.append('.');
    buf.append(this.key3);
    buf.append('.');
    buf.append(this.key4);
    buf.append(']');
    return buf.toString();
  }
}
