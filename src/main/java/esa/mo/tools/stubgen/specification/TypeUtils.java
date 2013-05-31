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

import esa.mo.tools.stubgen.xsd.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import org.w3c.dom.Element;

/**
 * Utility methods for types.
 */
public abstract class TypeUtils
{
  /**
   * Converts a type reference from the XML format to the internal format.
   *
   * @param tiSource Source of generator specific type information.
   * @param tr The XML type reference.
   * @return the converted type information.
   */
  public static TypeInfo convertTypeReference(TypeInformation tiSource, TypeReference tr)
  {
    if (null != tr)
    {
      String argTypeStr = tiSource.createElementType(null, tr);
      String argVersionStr = tiSource.getBasePackage() + tr.getArea().toLowerCase() + "." + tr.getArea() + "Helper." + tr.getArea().toUpperCase() + "_AREA_VERSION";
      TypeInfo ti;
      if (tr.isList())
      {
        if (StdStrings.XML.equals(tr.getArea()))
        {
          // lists of XML types are not supported
          throw new IllegalArgumentException("XML type of (" + tr.getService() + ":" + tr.getName() + ") with maxOccurrs <> 1 is not permitted");
        }
        else
        {
          if (tiSource.isNativeType(tr))
          {
            String fqName = tiSource.getBasePackage() + "mal.structures." + tr.getName() + "List";
            ti = new TypeInfo(tr, tr.getName() + "List", fqName, false, fqName + ".SHORT_FORM", argVersionStr);
          }
          else
          {
            ti = new TypeInfo(tr, tr.getName() + "List", argTypeStr + "List", false, getTypeShortForm(tiSource, tr, argTypeStr), argVersionStr);
          }
        }
      }
      else
      {
        ti = new TypeInfo(tr, tr.getName(), argTypeStr, tiSource.isNativeType(tr), getTypeShortForm(tiSource, tr, argTypeStr), argVersionStr);
      }
      return ti;
    }
    return null;
  }

  /**
   * Converts a list of type references from the XML format to the internal format.
   *
   * @param tiSource Source of generator specific type information.
   * @param trList The XML type reference list.
   * @return the converted type information.
   */
  public static List<TypeInfo> convertTypeReferences(TypeInformation tiSource, List<TypeReference> trList)
  {
    if (null != trList)
    {
      List<TypeInfo> tiList = new ArrayList<TypeInfo>(trList.size());
      for (TypeReference tr : trList)
      {
        tiList.add(convertTypeReference(tiSource, tr));
      }
      return tiList;
    }
    return null;
  }

  /**
   * Creates an XML format type reference.
   *
   * @param area Type area.
   * @param service Type service, may be null.
   * @param name Type name.
   * @param isList True if type is a list.
   * @return the new XML type information.
   */
  public static TypeReference createTypeReference(String area, String service, String name, boolean isList)
  {
    TypeReference tr = new TypeReference();
    tr.setArea(area);
    tr.setService(service);
    tr.setName(name);
    tr.setList(isList);
    return tr;
  }

  /**
   * Creates a short name for a type based on the long name.
   *
   * @param nameSeparator the separator for the long name.
   * @param longName The long name of the type.
   * @return the short name.
   */
  public static String shortTypeName(String nameSeparator, String longName)
  {
    if (null != longName)
    {
      if (longName.contains(nameSeparator))
      {
        longName = longName.substring(longName.lastIndexOf(nameSeparator) + nameSeparator.length());
      }
      if (longName.contains("*"))
      {
        longName = longName.substring(0, longName.length() - 1);
      }
      return longName;
    }
    return null;
  }

  /**
   * Returns the short form field for a type.
   *
   * @param tiSource Generator type information source.
   * @param type The type reference.
   * @param targetType The type.
   * @return The short form value.
   */
  public static String getTypeShortForm(TypeInformation tiSource, TypeReference type, String targetType)
  {
    if (null != type)
    {
      if (StdStrings.XML.equals(type.getArea()))
      {
        return "\"" + type.getService() + ":" + type.getName() + "\"";
      }
      else
      {
        if (tiSource.isAttributeType(type))
        {
          return tiSource.convertToNamespace(tiSource.getBasePackage() + tiSource.convertToNamespace("mal.structures.Attribute.") + type.getName().toUpperCase() + "_SHORT_FORM");
        }

        if (tiSource.convertToNamespace("org.ccsds.moims.mo.mal.structures.Element").equals(targetType))
        {
          return null;
        }

        return tiSource.convertToNamespace(targetType + ".SHORT_FORM");
      }
    }

    return null;
  }

  /**
   * Converts an XML any field into a list of types.
   *
   * @param any the XML any field.
   * @param comTypeSubs The COM type substitution map.
   * @return the convert type list.
   */
  public static List<TypeReference> getTypeListViaXSDAny(Object any, Map<String, TypeReference> comTypeSubs)
  {
    if (null != any)
    {
      if (any instanceof List)
      {
        List li = (List) any;
        ArrayList<TypeReference> rv = new ArrayList<TypeReference>(li.size());
        for (Object e : li)
        {
          rv.add(getTypeViaXSDAny(e, comTypeSubs));
        }
        return rv;
      }
      else
      {
        throw new IllegalArgumentException("Unexpected type in message body of : " + any.getClass().getSimpleName());
      }
    }

    return null;
  }

  /**
   * Converts an XML any field into a type reference.
   *
   * @param any the XML any field.
   * @param comTypeSubs The COM type substitution map.
   * @return the converted type.
   */
  public static TypeReference getTypeViaXSDAny(Object any, Map<String, TypeReference> comTypeSubs)
  {
    if (null != any)
    {
      if (any instanceof JAXBElement)
      {
        JAXBElement re = (JAXBElement) any;
        if (re.getValue() instanceof TypeReference)
        {
          TypeReference tr = (TypeReference) re.getValue();
          if ((null != comTypeSubs) && !comTypeSubs.isEmpty() && StdStrings.COM.equals(tr.getArea()) && StdStrings.COM.equals(tr.getService()))
          {
            TypeReference sub = comTypeSubs.get(tr.getName());
            if (null != sub)
            {
              TypeReference newTr = new TypeReference();
              newTr.setArea(sub.getArea());
              newTr.setService(sub.getService());
              newTr.setName(sub.getName());
              newTr.setList(tr.isList());
              tr = newTr;
            }
          }
          return tr;
        }
        else
        {
          throw new IllegalArgumentException("Unexpected type in message body of : " + re.getValue().getClass().getSimpleName());
        }
      }
      else if (any instanceof Element)
      {
        Element re = (Element) any;
        String stype = re.getAttribute("type");

        if (!"".equals(stype))
        {
          String uri = stype.substring(0, stype.indexOf(':'));
          String type = stype.substring(uri.length() + 1);
          boolean isList = false;
          String smaxOccurrs = re.getAttribute("maxOccurs");
          if (!"".equals(smaxOccurrs))
          {
            int maxOccurrs = Integer.parseInt(smaxOccurrs);
            if (1 < maxOccurrs)
            {
              isList = true;
            }
          }
          TypeReference newTr = new TypeReference();
          newTr.setArea(StdStrings.XML);
          newTr.setService(re.lookupNamespaceURI(uri));
          newTr.setName(type);
          newTr.setList(isList);
          return newTr;
        }
      }
      else
      {
        throw new IllegalArgumentException("Unexpected type in message body of : " + any.getClass().getSimpleName());
      }
    }

    return null;
  }

  private TypeUtils()
  {
  }
}
