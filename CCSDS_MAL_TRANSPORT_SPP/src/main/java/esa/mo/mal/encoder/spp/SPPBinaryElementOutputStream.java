/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.encoder.spp;

import esa.mo.mal.encoder.gen.GENEncoder;
import java.io.ByteArrayOutputStream;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALListEncoder;
import org.ccsds.moims.mo.mal.MALPubSubOperation;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.InteractionType;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Implements the MALElementOutputStream interface for a fixed length binary encoding.
 */
public class SPPBinaryElementOutputStream extends esa.mo.mal.encoder.binary.fixed.FixedBinaryElementOutputStream
{
  private final boolean smallLengthField;

  /**
   * Constructor.
   *
   * @param os Output stream to write to.
   * @param smallLengthField True if length field is 16bits, otherwise assumed to be 32bits.
   */
  public SPPBinaryElementOutputStream(final java.io.OutputStream os, final boolean smallLengthField)
  {
    super(os);

    this.smallLengthField = smallLengthField;
  }

  @Override
  protected GENEncoder createEncoder(java.io.OutputStream os)
  {
    return new SPPBinaryEncoder(os, smallLengthField);
  }

  @Override
  public void writeElement(Object element, MALEncodingContext ctx) throws MALException
  {
    if (null == enc)
    {
      this.enc = createEncoder(dos);
    }

    if ((null != element)
            && (element != ctx.getHeader())
            && (!ctx.getHeader().getIsErrorMessage())
            && (InteractionType._PUBSUB_INDEX == ctx.getHeader().getInteractionType().getOrdinal()))
    {
      switch (ctx.getHeader().getInteractionStage().getValue())
      {
        case MALPubSubOperation._REGISTER_STAGE:
        case MALPubSubOperation._PUBLISH_REGISTER_STAGE:
        case MALPubSubOperation._DEREGISTER_STAGE:
          ((Element) element).encode(enc);
          return;
        case MALPubSubOperation._PUBLISH_STAGE:
          if (0 < ctx.getBodyElementIndex())
          {
            encodePubSubPublishUpdate((Element) element, ctx);
          }
          else
          {
            ((Element) element).encode(enc);
          }
          return;
        case MALPubSubOperation._NOTIFY_STAGE:
          if ((1 < ctx.getBodyElementIndex()) && (null == ctx.getOperation().getOperationStage(ctx.getHeader().getInteractionStage()).getElementShortForms()[ctx.getBodyElementIndex() - 2]))
          {
            encodeSubElement((Element) element, null, null);
          }
          else
          {
            ((Element) element).encode(enc);
          }
          return;
        default:
          encodeSubElement((Element) element, null, null);
      }
    }
    else
    {
      super.writeElement(element, ctx);
    }
  }

  protected void encodePubSubPublishUpdate(Element element, MALEncodingContext ctx) throws MALException
  {
    ElementList<Element> updateList = (ElementList<Element>) element;

    if (null == ctx.getOperation().getOperationStage(ctx.getHeader().getInteractionStage()).getElementShortForms()[ctx.getBodyElementIndex()])
    {
      enc.encodeLong(updateList.getShortForm());
    }

    MALListEncoder listEncoder = enc.createListEncoder(updateList);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    GENEncoder updateEncoder = createEncoder(baos);

    for (Object e : updateList)
    {
      if (null == e)
      {
        enc.encodeNullableBlob(null);
      }
      else
      {
        if (e instanceof Element)
        {
          ((Element) e).encode(updateEncoder);
        }
        else
        {
          encodeNativeType(e, updateEncoder);
        }
        listEncoder.encodeNullableBlob(new Blob(baos.toByteArray()));
        baos.reset();
      }
    }
    
    listEncoder.close();
  }

  protected static void encodeNativeType(final Object element, final GENEncoder updateEncoder) throws MALException
  {
    if (element instanceof Boolean)
    {
      updateEncoder.encodeBoolean((Boolean) element);
    }
    else if (element instanceof Float)
    {
      updateEncoder.encodeFloat((Float) element);
    }
    else if (element instanceof Double)
    {
      updateEncoder.encodeDouble((Double) element);
    }
    else if (element instanceof Byte)
    {
      updateEncoder.encodeOctet((Byte) element);
    }
    else if (element instanceof Short)
    {
      updateEncoder.encodeShort((Short) element);
    }
    else if (element instanceof Integer)
    {
      updateEncoder.encodeInteger((Integer) element);
    }
    else if (element instanceof Long)
    {
      updateEncoder.encodeLong((Long) element);
    }
    else if (element instanceof String)
    {
      updateEncoder.encodeString((String) element);
    }
  }
}
