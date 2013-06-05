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

/**
 * Implements the MALListDecoder interface for a split binary encoding.
 */
public class SplitBinaryListDecoder extends SplitBinaryDecoder implements org.ccsds.moims.mo.mal.MALListDecoder
{
  private final int size;
  private final java.util.List list;

  /**
   * Constructor.
   *
   * @param list List to decode into.
   * @param inputStream Input stream to read from.
   * @param srcBuffer Buffer to manage.
   * @throws MALException If cannot decode list size.
   */
  public SplitBinaryListDecoder(final java.util.List list, final java.io.InputStream inputStream, final BufferHolder srcBuffer)
          throws org.ccsds.moims.mo.mal.MALException
  {
    super(inputStream, srcBuffer);

    this.list = list;
    size = getUnsignedInt();
  }

  @Override
  public boolean hasNext()
  {
    return list.size() < size;
  }
}
