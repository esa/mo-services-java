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
package esa.mo.tools.stubgen.writers;

import esa.mo.tools.stubgen.specification.CompositeField;
import java.io.IOException;
import java.util.List;

/**
 * Writer used when creating an interface style class (such as a C++ pure virtual base class).
 */
public interface InterfaceWriter extends LanguageWriter
{
  /**
   * Adds an interface open statement.
   *
   * @param interfaceName The interface name.
   * @param extendsInterface Which other interfaces does this interface extend.
   * @param comment Comment for the interface.
   * @throws IOException If there is an IO error.
   */
  void addInterfaceOpenStatement(String interfaceName, String extendsInterface, String comment) throws IOException;

  /**
   * Adds a method declaration to the interface.
   *
   * @param scope The scope of the method.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @param comment The main comment for the method.
   * @param returnComment The comment about the return type.
   * @param throwsComment The comment for the throws specification.
   * @throws IOException If there is an IO error.
   */
  void addInterfaceMethodDeclaration(String scope,
          CompositeField rtype,
          String methodName,
          List<CompositeField> args,
          String throwsSpec,
          String comment,
          String returnComment,
          List<String> throwsComment) throws IOException;

  /**
   * Adds statements to close the interface.
   *
   * @throws IOException If there is an IO error.
   */
  void addInterfaceCloseStatement() throws IOException;
}
