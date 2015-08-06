/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Encoder Framework
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
package esa.mo.mal.encoder.gen;

import org.ccsds.moims.mo.mal.MALContextFactory;
import org.ccsds.moims.mo.mal.MALElementFactory;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Extends the MALElementInputStream interface to enable aware transport access to the encoded data stream.
 */
public abstract class GENElementInputStream implements MALElementInputStream
{
  protected final GENDecoder dec;

  /**
   * Sub class constructor.
   *
   * @param pdec Decoder to use.
   */
  protected GENElementInputStream(GENDecoder pdec)
  {
    dec = pdec;
  }

  @Override
  public Object readElement(final Object element, final MALEncodingContext ctx)
          throws IllegalArgumentException, MALException
  {
    if ((null != ctx) && (element == ctx.getHeader()))
    {
      return dec.decodeElement((Element) element);
    }
    else
    {
      if (null == element)
      {
        Long shortForm;

        // dirty check to see if we are trying to decode an abstract Attribute (and not a list of them either)
        Object[] finalEleShortForms = ctx.getOperation().getOperationStage(ctx.getHeader().getInteractionStage()).getLastElementShortForms();
        if ((null != finalEleShortForms) && (Attribute._URI_TYPE_SHORT_FORM == finalEleShortForms.length) && ((((Long)finalEleShortForms[0]) & 0x800000L) == 0))
        {
          Byte sf = dec.decodeNullableOctet();
          if (null == sf)
          {
            return null;
          }

          shortForm = Attribute.ABSOLUTE_AREA_SERVICE_NUMBER + dec.internalDecodeAttributeType(sf);
        }
        else
        {
          shortForm = dec.decodeNullableLong();
        }

        if (null == shortForm)
        {
          return null;
        }

        final MALElementFactory ef
                = MALContextFactory.getElementFactoryRegistry().lookupElementFactory(shortForm);

        if (null == ef)
        {
          throw new MALException("GEN transport unable to find element factory for short type: " + shortForm);
        }

        return dec.decodeElement((Element) ef.createElement());
      }
      else
      {
        return dec.decodeNullableElement((Element) element);
      }
    }
  }

  /**
   * Returns a new byte array containing the remaining encoded data for this stream. Expected to be used for creating an
   * MAL encoded body object.
   *
   * @return a byte array containing the remaining encoded data for this stream.
   * @throws MALException On error.
   */
  public byte[] getRemainingEncodedData() throws MALException
  {
    return dec.getRemainingEncodedData();
  }

  @Override
  public void close() throws MALException
  {
    // Nothing to do for this decoder
  }
}
