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
package org.ccsds.moims.mo.mal;

/**
 * The MALException class provides a generic exception class for the MAL API to raise an error as an exception.
 */
public class MALException extends Exception
{
  /**
   * Constructs a empty exception.
   */
  public MALException()
  {
    super();
  }

  /**
   * Constructs an exception with a string message.
   * @param message The string message.
   */
  public MALException(final String message)
  {
    super(message);
  }

  /**
   * Constructs an exception with a message and a cause exception.
   * @param message The string message.
   * @param cause The exception that caused this exception to be raised.
   */
  public MALException(final String message, final Throwable cause)
  {
    super(message, cause);
  }
}
