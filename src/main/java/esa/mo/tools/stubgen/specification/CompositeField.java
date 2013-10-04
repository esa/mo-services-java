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
 * Holds details about a filed of a composite structure.
 */
public final class CompositeField
{
  private final String typeName;
  private final TypeReference typeReference;
  private final String fieldName;
  private final boolean list;
  private final boolean canBeNull;
  private final String encodeCall;
  private final String decodeCast;
  private final String decodeCall;
  private final boolean decodeNeedsNewCall;
  private final String newCall;
  private final String comment;

  /**
   * Constructor.
   *
   * @param typeName The type of the field.
   * @param typeReference The original type reference of the type
   * @param fieldName The name of the field.
   * @param isList True if the field is a list.
   * @param canBeNull True if the field may be null.
   * @param encodeCall The method for encoding the field.
   * @param decodeCast The type to cast to on a decode call.
   * @param decodeCall The method for decode the field.
   * @param decodeNeedsNewCall True if the decode call need a new instance of the type to be passed.
   * @param newCall The method to create a new instance.
   * @param comment The field comment.
   */
  public CompositeField(String typeName, TypeReference typeReference, String fieldName, boolean isList, boolean canBeNull, String encodeCall, String decodeCast, String decodeCall, boolean decodeNeedsNewCall, String newCall, String comment)
  {
    this.typeName = typeName;
    this.typeReference = typeReference;
    this.fieldName = fieldName;
    this.list = isList;
    this.canBeNull = canBeNull;
    this.encodeCall = encodeCall;
    this.decodeCast = decodeCast;
    this.decodeCall = decodeCall;
    this.decodeNeedsNewCall = decodeNeedsNewCall;
    this.newCall = newCall;
    this.comment = comment;
  }

  /**
   * Returns the type name.
   *
   * @return the typeName
   */
  public String getTypeName()
  {
    return typeName;
  }

  /**
   * Returns the type reference.
   *
   * @return the type reference
   */
  public TypeReference getTypeReference()
  {
    return typeReference;
  }

  /**
   * Returns the field name.
   *
   * @return the fieldName
   */
  public String getFieldName()
  {
    return fieldName;
  }

  /**
   * Returns True if a list field.
   *
   * @return the list
   */
  public boolean isList()
  {
    return list;
  }

  /**
   * Returns True if field may be null.
   *
   * @return the canBeNull
   */
  public boolean isCanBeNull()
  {
    return canBeNull;
  }

  /**
   * Returns the encode call.
   *
   * @return the encodeCall
   */
  public String getEncodeCall()
  {
    return encodeCall;
  }

  /**
   * Returns the decode cast.
   *
   * @return the decodeCast
   */
  public String getDecodeCast()
  {
    return decodeCast;
  }

  /**
   * Returns the decode call.
   *
   * @return the decodeCall
   */
  public String getDecodeCall()
  {
    return decodeCall;
  }

  /**
   * Returns true if decode needs a new instance.
   *
   * @return the decodeNeedsNewCall
   */
  public boolean isDecodeNeedsNewCall()
  {
    return decodeNeedsNewCall;
  }

  /**
   * Returns the new instance method..
   *
   * @return the newCall
   */
  public String getNewCall()
  {
    return newCall;
  }

  /**
   * Returns the field comment.
   *
   * @return the comment
   */
  public String getComment()
  {
    return comment;
  }
}
