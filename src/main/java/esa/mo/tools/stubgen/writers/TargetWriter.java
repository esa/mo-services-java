/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an “as is” basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.tools.stubgen.writers;

import java.io.IOException;

/**
 * The basic class that all writers extend.
 */
public interface TargetWriter
{
  /**
   * Adds a dependency on a supplied type to the file.
   *
   * @param typeName The type to depend on.
   */
  void addTypeDependency(String typeName);

  /**
   * Returns the current line separator.
   *
   * @return the line separator.
   */
  String getLineSeparator();

  /**
   * Flushes the contents of the writer.
   *
   * @throws IOException if there is an IO problem.
   */
  void flush() throws IOException;
}
