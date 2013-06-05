/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Split Binary encoder
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
package esa.mo.mal.encoder.binary.split;

import org.ccsds.moims.mo.mal.MALException;

/**
 * Implements the MALDecoder interface for a split binary encoding.
 */
public class SplitBinaryDecoder extends esa.mo.mal.encoder.binary.BinaryDecoder
{
  protected final SplitBufferHolder eSourceBuffer;

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   */
  public SplitBinaryDecoder(final byte[] src)
  {
    super(null, new SplitBufferHolder(src, 0, src.length));
    eSourceBuffer = (SplitBufferHolder)sourceBuffer;
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   */
  public SplitBinaryDecoder(final java.io.InputStream is)
  {
    super(is, new SplitBufferHolder(null, 0, 0));
    eSourceBuffer = (SplitBufferHolder)sourceBuffer;
  }

  /**
   * Constructor.
   *
   * @param src Byte array to read from.
   * @param offset index in array to start reading from.
   */
  public SplitBinaryDecoder(final byte[] src, final int offset)
  {
    super(null, new SplitBufferHolder(src, offset, src.length));
    eSourceBuffer = (SplitBufferHolder)sourceBuffer;
  }

  /**
   * Constructor.
   *
   * @param is Input stream to read from.
   * @param src Source buffer holder to use.
   */
  protected SplitBinaryDecoder(final java.io.InputStream is, final BufferHolder src)
  {
    super(is, src);
    eSourceBuffer = (SplitBufferHolder)sourceBuffer;
  }

  @Override
  public org.ccsds.moims.mo.mal.MALListDecoder createListDecoder(final java.util.List list) throws MALException
  {
    return new SplitBinaryListDecoder(list, inputStream, sourceBuffer);
  }

  @Override
  public Boolean decodeBoolean() throws MALException
  {
    return (isNull() ? Boolean.FALSE : Boolean.TRUE);
  }

  @Override
  public Boolean decodeNullableBoolean() throws MALException
  {
    if (!isNull())
    {
      return decodeBoolean();
    }

    return null;
  }

  @Override
  protected boolean isNull() throws MALException
  {
    if (!eSourceBuffer.bitStoreLoaded)
    {
      checkBuffer(1);
    }

    boolean rv = !eSourceBuffer.bitStore.get(eSourceBuffer.bitIndex);
    ++eSourceBuffer.bitIndex;
    return rv;
  }

  @Override
  protected void checkBuffer(final int requiredLength) throws MALException
  {
    // ensure that the bit buffer has been loaded first
    if (!eSourceBuffer.bitStoreLoaded)
    {
      eSourceBuffer.bitStoreLoaded = true;
      eSourceBuffer.bitStore = java.util.BitSet.valueOf(get(getUnsignedInt()));
    }

    super.checkBuffer(requiredLength);
  }

  protected static class SplitBufferHolder extends BufferHolder
  {
    private boolean bitStoreLoaded = false;
    private java.util.BitSet bitStore = null;
    private int bitIndex = 0;

    /**
     * Constructor.
     *
     * @param buf Source buffer to use.
     * @param offset Buffer offset to read from next.
     * @param length Length of readable data held in the array, which may be larger.
     */
    public SplitBufferHolder(final byte[] buf, final int offset, final int length)
    {
      super(buf, offset, length);
    }
  }
}
