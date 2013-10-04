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

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.tools.stubgen.xsd.TypeReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.maven.plugin.logging.Log;

/**
 * Base generator class for generators that product documents rather than programming language APIs.
 */
public abstract class GeneratorDocument extends GeneratorBase
{
  /**
   * Constructor.
   *
   * @param logger The logger.
   * @param config The configuration to use.
   */
  protected GeneratorDocument(Log logger, GeneratorConfiguration config)
  {
    super(logger, config);

    addAttributeType(StdStrings.BLOB, new AttributeTypeDetails(this, true, "Blob", ""));
    addAttributeType(StdStrings.BOOLEAN, new AttributeTypeDetails(this, true, "Boolean", ""));
    addAttributeType(StdStrings.DOUBLE, new AttributeTypeDetails(this, true, "Double", ""));
    addAttributeType(StdStrings.DURATION, new AttributeTypeDetails(this, true, "Duration", ""));
    addAttributeType(StdStrings.FLOAT, new AttributeTypeDetails(this, true, "Float", ""));
    addAttributeType(StdStrings.INTEGER, new AttributeTypeDetails(this, true, "Integer", ""));
    addAttributeType(StdStrings.IDENTIFIER, new AttributeTypeDetails(this, true, "Identifier", ""));
    addAttributeType(StdStrings.LONG, new AttributeTypeDetails(this, true, "Long", ""));
    addAttributeType(StdStrings.OCTET, new AttributeTypeDetails(this, true, "Octet", ""));
    addAttributeType(StdStrings.SHORT, new AttributeTypeDetails(this, true, "Short", ""));
    addAttributeType(StdStrings.UINTEGER, new AttributeTypeDetails(this, true, "UInteger", ""));
    addAttributeType(StdStrings.ULONG, new AttributeTypeDetails(this, true, "ULong", ""));
    addAttributeType(StdStrings.UOCTET, new AttributeTypeDetails(this, true, "UOctet", ""));
    addAttributeType(StdStrings.USHORT, new AttributeTypeDetails(this, true, "UShort", ""));
    addAttributeType(StdStrings.STRING, new AttributeTypeDetails(this, true, "String", ""));
    addAttributeType(StdStrings.TIME, new AttributeTypeDetails(this, true, "Time", ""));
    addAttributeType(StdStrings.FINETIME, new AttributeTypeDetails(this, true, "FineTime", ""));
    addAttributeType(StdStrings.URI, new AttributeTypeDetails(this, true, "URI", ""));
  }

  @Override
  protected CompositeField createCompositeElementsDetails(TargetWriter file, String fieldName, TypeReference elementType, boolean canBeNull, String comment)
  {
    CompositeField ele;
    String typeName = elementType.getName();
    if (isAttributeType(elementType))
    {
      AttributeTypeDetails details = getAttributeDetails(elementType);
      ele = new CompositeField(details.getTargetType(), elementType, fieldName, elementType.isList(), canBeNull, StdStrings.MAL, "", "", false, "", comment);
    }
    else
    {
      String fqTypeName = typeName;
      ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(), canBeNull, elementType.getArea(), "", elementType.getService(), false, "", comment);
    }
    return ele;
  }

  protected static List<String> splitString(List<String> srcArr, String str)
  {
    if (null == srcArr)
    {
      srcArr = new LinkedList<String>();
    }
    if (null != str)
    {
      srcArr.addAll(Arrays.asList(str.split("(  |\n)")));
    }
    return srcArr;
  }
}
