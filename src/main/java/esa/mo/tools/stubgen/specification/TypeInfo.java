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
package esa.mo.tools.stubgen.specification;

import esa.mo.tools.stubgen.xsd.TypeReference;

/**
 * Holds information about a type.
 */
public class TypeInfo
{
  private final TypeReference sourceType;
  private final String actualMalType;
  private final String targetType;
  private final boolean nativeType;
  private final String malShortFormField;
  private final String malVersionField;

  /**
   * Constructor.
   *
   * @param sourceType XML type reference.
   * @param actualMalType The name of the type in the specification.
   * @param targetType The programming language type.
   * @param isNative True if the type is represented by a native language type.
   * @param malShortFormField The short form field value for the type.
   * @param versionInfo The version information for the type.
   */
  public TypeInfo(TypeReference sourceType, String actualMalType, String targetType, boolean isNative, String malShortFormField, String versionInfo)
  {
    this.sourceType = sourceType;
    this.actualMalType = actualMalType;
    this.targetType = targetType;
    this.nativeType = isNative;
    this.malShortFormField = malShortFormField;
    this.malVersionField = versionInfo;
  }

  /**
   * Returns the source XML type.
   *
   * @return the source type.
   */
  public TypeReference getSourceType()
  {
    return sourceType;
  }

  /**
   * Returns the MAL type.
   *
   * @return the actual MAL type.
   */
  public String getActualMalType()
  {
    return actualMalType;
  }

  /**
   * Returns the language type.
   *
   * @return the target type.
   */
  public String getTargetType()
  {
    return targetType;
  }

  /**
   * True if represented by a language native type.
   *
   * @return the native type.
   */
  public boolean isNativeType()
  {
    return nativeType;
  }

  /**
   * Returns the MAL short form field value.
   *
   * @return the MAL short form field.
   */
  public String getMalShortFormField()
  {
    return malShortFormField;
  }

  /**
   * Returns the type version information.
   *
   * @return the MAL version field.
   */
  public String getMalVersionField()
  {
    return malVersionField;
  }
}
