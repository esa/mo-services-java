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
package esa.mo.tools.stubgen.specification;

import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.tools.stubgen.xsd.TypeReference;

/**
 * Interface for querying basic type information.
 */
public interface TypeInformation
{
  /**
   * Returns true if the type is abstract.
   *
   * @param type the type to look for.
   * @return true if abstract.
   */
  boolean isAbstract(TypeReference type);

  /**
   * Returns true if the type is an enumeration.
   *
   * @param type the type to look for.
   * @return true if an enumeration.
   */
  boolean isEnum(TypeReference type);

  /**
   * Returns true if the type is a native type.
   *
   * @param type the type to look for.
   * @return true if native.
   */
  boolean isNativeType(TypeReference type);

  /**
   * Returns true if the type is an attribute.
   *
   * @param area the type area, must be MAL.
   * @param type the type to look for.
   * @return true if an attribute.
   */
  boolean isAttributeType(TypeReference type);

  /**
   * Returns the base package all generated classes should be created in.
   *
   * @return the base package.
   */
  String getBasePackage();

  /**
   * Converts a standard type name to the language specific format.
   *
   * @param targetType the type to convert.
   * @return the converted type.
   */
  String convertToNamespace(String targetType);

  /**
   * Converts a class name to a language specific version. Useful when a language native type is used to represent a MAL
   * type.
   *
   * @param call the source type.
   * @return the converted type.
   */
  String convertClassName(String call);

  /**
   * Creates the full name of a structure type from the supplied details.
   *
   * @param file The writer to add any type dependencies to.
   * @param type The type.
   * @return the full name of the type.
   */
  String createElementType(TargetWriter file, TypeReference type);

  /**
   * Creates the full name of a structure type from the supplied details.
   *
   * @param file The writer to add any type dependencies to.
   * @param area The area of the type.
   * @param service The service of the type, may be null.
   * @param type The type.
   * @return the full name of the type.
   */
  String createElementType(TargetWriter file,
          String area,
          String service,
          String type);
}
