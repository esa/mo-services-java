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
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.writers.ClassWriter;
import java.io.IOException;

/**
 * Small extension to the standard ClassWriter interface to add methods for proposed features.
 */
public interface ClassWriterProposed extends ClassWriter
{
  /**
   * Adds a proposed member variable to the class.
   *
   * @param isStatic True if the variable is static.
   * @param isFinal True if the variable may not be modified.
   * @param scope Scope of the variable.
   * @param type Type of the variable.
   * @param isObject True if the variable is an object rather than native type.
   * @param initialValue The initial value of the variable.
   * @throws IOException If there is an IO error.
   */
  void addClassVariableProposed(boolean isStatic, boolean isFinal, String scope, CompositeField type, boolean isObject, String initialValue) throws IOException;
}
