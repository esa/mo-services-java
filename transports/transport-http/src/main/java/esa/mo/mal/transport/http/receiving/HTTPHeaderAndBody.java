/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.receiving;

import java.util.Arrays;

import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Small class to hold the HTTP header and body information.
 */
public class HTTPHeaderAndBody {
  private final MALMessageHeader header;
  private final byte[] encodedPacketData;
  private final int statusCode;

  /**
   * Constructor
   * 
   * @param header
   *            The message header
   * @param encodedPacketData
   *            Encoded message body
   */
  public HTTPHeaderAndBody(MALMessageHeader header, byte[] encodedPacketData, int statusCode) {
    this.header = header;
    this.encodedPacketData = encodedPacketData;
    this.statusCode = statusCode;
  }

  public MALMessageHeader getHeader() {
    return header;
  }

  public byte[] getEncodedPacketData() {
    return encodedPacketData;
  }

  public int getStatusCode() {
    return statusCode;
  }

  @Override
  public String toString() {
    return "HTTPHeaderAndBody [header=" + header + ", encodedPacketData=" + Arrays.toString(encodedPacketData)
        + ", statusCode=" + statusCode + "]";
  }

}
