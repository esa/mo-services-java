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

import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import java.io.IOException;
import java.util.List;

/**
 * Extension of the base writer to add in support common to generators of programming languages.
 */
public interface LanguageWriter extends TargetWriter
{
  /**
   * Add a top level statement to the file.
   *
   * @param string The string to add.
   * @throws IOException If there is a problem writing the line.
   */
  void addStatement(String string) throws IOException;

  /**
   * Add a package namespace declaration to the file.
   *
   * @param area The area.
   * @param service The service.
   * @param packageName The package name.
   * @throws IOException If there is an IO error.
   */
  void addPackageStatement(AreaType area, ServiceType service, String packageName) throws IOException;

  /**
   * Add a package namespace declaration to the file.
   *
   * @param packageName The package name.
   * @throws IOException If there is an IO error.
   */
  void addPackageStatement(String packageName) throws IOException;

  /**
   * Add a package namespace declaration to the file.
   *
   * @param packageName The package name.
   * @param prefix Namespace prefix.
   * @throws IOException If there is an IO error.
   */
  void addPackageStatement(String packageName, String prefix) throws IOException;

  /**
   * Adds a multiline comment to the file.
   *
   * @param comment the comment.
   * @throws IOException If there is an IO error.
   */
  void addMultilineComment(String comment) throws IOException;

  /**
   * Adds a multi-line comment to the file.
   *
   * @param tabCount Indentation level.
   * @param preBlankLine True is a blank line is required before the comment.
   * @param comment the comment.
   * @param postBlankLine True is a blank line is required after the comment.
   * @throws IOException If there is an IO error.
   */
  void addMultilineComment(int tabCount, boolean preBlankLine, String comment, boolean postBlankLine)
          throws IOException;

  /**
   * Adds a set of comment to the file.
   *
   * @param tabCount Indentation level.
   * @param preBlankLine True is a blank line is required before the comment.
   * @param comments the comments.
   * @param postBlankLine True is a blank line is required after the comment.
   * @throws IOException If there is an IO error.
   */
  void addMultilineComment(int tabCount, boolean preBlankLine, List<String> comments, boolean postBlankLine)
          throws IOException;
}
