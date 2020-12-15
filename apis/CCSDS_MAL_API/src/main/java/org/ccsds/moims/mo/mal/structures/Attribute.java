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
package org.ccsds.moims.mo.mal.structures;

/**
 * The Attribute interface represents the MAL Attribute type.
 */
public interface Attribute extends Element
{
  /**
   * Holds the MAL Area/Service/Version number in absolute type short form.
   */
  long ABSOLUTE_AREA_SERVICE_NUMBER = 0x1000001000000L;
  /**
   * Relative type short form for BLOB, can be using in switch statements.
   */
  int _BLOB_TYPE_SHORT_FORM = 1;
  /**
   * Relative type short form for BLOB.
   */
  Integer BLOB_TYPE_SHORT_FORM = _BLOB_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for BLOB.
   */
  Long BLOB_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _BLOB_TYPE_SHORT_FORM;
  /**
   * Relative type short form for BOOLEAN, can be using in switch statements.
   */
  int _BOOLEAN_TYPE_SHORT_FORM = 2;
  /**
   * Relative type short form for BOOLEAN.
   */
  Integer BOOLEAN_TYPE_SHORT_FORM = _BOOLEAN_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for BOOLEAN.
   */
  Long BOOLEAN_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _BOOLEAN_TYPE_SHORT_FORM;
  /**
   * Relative type short form for DURATION, can be using in switch statements.
   */
  int _DURATION_TYPE_SHORT_FORM = 3;
  /**
   * Relative type short form for DURATION.
   */
  Integer DURATION_TYPE_SHORT_FORM = _DURATION_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for DURATION.
   */
  Long DURATION_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _DURATION_TYPE_SHORT_FORM;
  /**
   * Relative type short form for FLOAT, can be using in switch statements.
   */
  int _FLOAT_TYPE_SHORT_FORM = 4;
  /**
   * Relative type short form for FLOAT.
   */
  Integer FLOAT_TYPE_SHORT_FORM = _FLOAT_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for FLOAT.
   */
  Long FLOAT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _FLOAT_TYPE_SHORT_FORM;
  /**
   * Relative type short form for DOUBLE, can be using in switch statements.
   */
  int _DOUBLE_TYPE_SHORT_FORM = 5;
  /**
   * Relative type short form for DOUBLE.
   */
  Integer DOUBLE_TYPE_SHORT_FORM = _DOUBLE_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for DOUBLE.
   */
  Long DOUBLE_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _DOUBLE_TYPE_SHORT_FORM;
  /**
   * Relative type short form for IDENTIFIER, can be using in switch statements.
   */
  int _IDENTIFIER_TYPE_SHORT_FORM = 6;
  /**
   * Relative type short form for IDENTIFIER.
   */
  Integer IDENTIFIER_TYPE_SHORT_FORM = _IDENTIFIER_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for IDENTIFIER.
   */
  Long IDENTIFIER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _IDENTIFIER_TYPE_SHORT_FORM;
  /**
   * Relative type short form for OCTET, can be using in switch statements.
   */
  int _OCTET_TYPE_SHORT_FORM = 7;
  /**
   * Relative type short form for OCTET.
   */
  Integer OCTET_TYPE_SHORT_FORM = _OCTET_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for OCTET.
   */
  Long OCTET_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _OCTET_TYPE_SHORT_FORM;
  /**
   * Relative type short form for UOCTET, can be using in switch statements.
   */
  int _UOCTET_TYPE_SHORT_FORM = 8;
  /**
   * Relative type short form for UOCTET.
   */
  Integer UOCTET_TYPE_SHORT_FORM = _UOCTET_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for UOCTET.
   */
  Long UOCTET_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _UOCTET_TYPE_SHORT_FORM;
  /**
   * Relative type short form for SHORT, can be using in switch statements.
   */
  int _SHORT_TYPE_SHORT_FORM = 9;
  /**
   * Relative type short form for SHORT.
   */
  Integer SHORT_TYPE_SHORT_FORM = _SHORT_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for SHORT.
   */
  Long SHORT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _SHORT_TYPE_SHORT_FORM;
  /**
   * Relative type short form for USHORT, can be using in switch statements.
   */
  int _USHORT_TYPE_SHORT_FORM = 10;
  /**
   * Relative type short form for USHORT.
   */
  Integer USHORT_TYPE_SHORT_FORM = _USHORT_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for USHORT.
   */
  Long USHORT_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _USHORT_TYPE_SHORT_FORM;
  /**
   * Relative type short form for INTEGER, can be using in switch statements.
   */
  int _INTEGER_TYPE_SHORT_FORM = 11;
  /**
   * Relative type short form for INTEGER.
   */
  Integer INTEGER_TYPE_SHORT_FORM = _INTEGER_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for INTEGER.
   */
  Long INTEGER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _INTEGER_TYPE_SHORT_FORM;
  /**
   * Relative type short form for UINTEGER, can be using in switch statements.
   */
  int _UINTEGER_TYPE_SHORT_FORM = 12;
  /**
   * Relative type short form for UINTEGER.
   */
  Integer UINTEGER_TYPE_SHORT_FORM = _UINTEGER_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for UINTEGER.
   */
  Long UINTEGER_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _UINTEGER_TYPE_SHORT_FORM;
  /**
   * Relative type short form for LONG, can be using in switch statements.
   */
  int _LONG_TYPE_SHORT_FORM = 13;
  /**
   * Relative type short form for LONG.
   */
  Integer LONG_TYPE_SHORT_FORM = _LONG_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for LONG.
   */
  Long LONG_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _LONG_TYPE_SHORT_FORM;
  /**
   * Relative type short form for ULONG, can be using in switch statements.
   */
  int _ULONG_TYPE_SHORT_FORM = 14;
  /**
   * Relative type short form for ULONG.
   */
  Integer ULONG_TYPE_SHORT_FORM = _ULONG_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for ULONG.
   */
  Long ULONG_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _ULONG_TYPE_SHORT_FORM;
  /**
   * Relative type short form for STRING, can be using in switch statements.
   */
  int _STRING_TYPE_SHORT_FORM = 15;
  /**
   * Relative type short form for STRING.
   */
  Integer STRING_TYPE_SHORT_FORM = _STRING_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for STRING.
   */
  Long STRING_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _STRING_TYPE_SHORT_FORM;
  /**
   * Relative type short form for TIME, can be using in switch statements.
   */
  int _TIME_TYPE_SHORT_FORM = 16;
  /**
   * Relative type short form for TIME.
   */
  Integer TIME_TYPE_SHORT_FORM = _TIME_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for TIME.
   */
  Long TIME_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _TIME_TYPE_SHORT_FORM;
  /**
   * Relative type short form for FINETIME, can be using in switch statements.
   */
  int _FINETIME_TYPE_SHORT_FORM = 17;
  /**
   * Relative type short form for FINETIME.
   */
  Integer FINETIME_TYPE_SHORT_FORM = _FINETIME_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for FINETIME.
   */
  Long FINETIME_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _FINETIME_TYPE_SHORT_FORM;
  /**
   * Relative type short form for URI, can be using in switch statements.
   */
  int _URI_TYPE_SHORT_FORM = 18;
  /**
   * Relative type short form for URI.
   */
  Integer URI_TYPE_SHORT_FORM = _URI_TYPE_SHORT_FORM;
  /**
   * Absolute type short form for URI.
   */
  Long URI_SHORT_FORM = ABSOLUTE_AREA_SERVICE_NUMBER + _URI_TYPE_SHORT_FORM;
}
