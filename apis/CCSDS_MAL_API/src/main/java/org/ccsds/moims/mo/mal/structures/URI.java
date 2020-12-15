/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.structures;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;

/**
 * Class representing MAL URI type.
 */
public class URI implements Attribute
{
  private String value;

  /**
   * Default constructor.
   */
  public URI()
  {
    this.value = "";
  }

  /**
   * Initialiser constructor.
   *
   * @param value Value to initialise with.
   */
  public URI(final String value)
  {
    if(null == value)
    {
      Logger.getLogger(URI.class.getName()).log(Level.WARNING, 
          "The URI has been initialized with an invalid null value. Problems might occur while encoding the element.", 
          new MALException());
      this.value = "";
    } else {
      this.value = value;
    }
  }

  @Override
  public Element createElement()
  {
    return new URI();
  }

  /**
   * Returns the value of this type.
   *
   * @return the value.
   */
  public String getValue()
  {
    return value;
  }

//  This might be required for XML serialisation and technologies that use that.  
//  public void setValue(String value)
//  {
//    this.value = value;
//  }

  @Override
  public Long getShortForm()
  {
    return Attribute.URI_SHORT_FORM;
  }

  @Override
  public Integer getTypeShortForm()
  {
    return Attribute.URI_TYPE_SHORT_FORM;
  }

  @Override
  public UShort getAreaNumber()
  {
    return UShort.ATTRIBUTE_AREA_NUMBER;
  }

  @Override
  public org.ccsds.moims.mo.mal.structures.UOctet getAreaVersion()
  {
    return UOctet.AREA_VERSION;
  }

  @Override
  public UShort getServiceNumber()
  {
    return UShort.ATTRIBUTE_SERVICE_NUMBER;
  }

  @Override
  public void encode(final MALEncoder encoder) throws MALException
  {
    encoder.encodeURI(this);
  }

  @Override
  public Element decode(final MALDecoder decoder) throws MALException
  {
    return decoder.decodeURI();
  }

  @Override
  public int hashCode()
  {
    return value.hashCode();
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof URI)
    {
      return value.equals(((URI) obj).value);
    }

    return false;
  }

  @Override
  public String toString()
  {
    return value;
  }
  private static final long serialVersionUID = Attribute.URI_SHORT_FORM;
}
