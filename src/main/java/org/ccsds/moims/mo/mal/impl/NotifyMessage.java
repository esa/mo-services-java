/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccsds.moims.mo.mal.impl;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.UShort;

/**
 * A single notify message.
 */
public final class NotifyMessage
{
  /**
   * Message header.
   */
  public MessageDetails details;
  public Long transId;
  public IdentifierList domain;
  public Identifier networkZone;
  public UShort area;
  public UShort service;
  public UShort operation;
  public UOctet version;
  public Object[] updates;
  
}
