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
  }

  @Override
  protected CompositeField createCompositeElementsDetails(TargetWriter file, String fieldName, TypeReference elementType, boolean canBeNull, String comment)
  {
    CompositeField ele;
    String typeName = elementType.getName();
    if (isAttributeType(elementType))
    {
      AttributeTypeDetails details = getAttributeDetails(elementType);
      ele = new CompositeField(details.getTargetType(), fieldName, elementType.isList(), canBeNull, StdStrings.MAL, "", "", false, "", comment);
    }
    else
    {
      String fqTypeName = typeName;
      ele = new CompositeField(fqTypeName, fieldName, elementType.isList(), canBeNull, elementType.getArea(), "", elementType.getService(), false, "", comment);
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
