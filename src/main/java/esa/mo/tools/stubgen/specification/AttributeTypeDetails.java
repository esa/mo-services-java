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

/**
 * Holds details about MAL attribute types.
 */
public final class AttributeTypeDetails
{
  /**
   * True if this Attribute type is represented by a language native type.
   */
  private final boolean nativeType;
  /**
   * The language type to use.
   */
  private final String targetType;
  /**
   * An example of the default value for this type.
   */
  private final String defaultValue;

  /**
   * Constructor.
   *
   * @param ti The source of type resolution.
   * @param isNativeType True if native type.
   * @param targetType The type to generate too.
   * @param defaultValue An example of a default value.
   */
  public AttributeTypeDetails(final TypeInformation ti,
          final boolean isNativeType,
          final String targetType,
          final String defaultValue)
  {
    this.nativeType = isNativeType;

    if (!nativeType)
    {
      this.targetType = ti.createElementType(null, StdStrings.MAL, null, targetType);
    }
    else
    {
      this.targetType = targetType;
    }

    this.defaultValue = defaultValue;
  }

  public boolean isNativeType()
  {
    return nativeType;
  }

  public String getTargetType()
  {
    return targetType;
  }

  public String getDefaultValue()
  {
    return defaultValue;
  }
}
