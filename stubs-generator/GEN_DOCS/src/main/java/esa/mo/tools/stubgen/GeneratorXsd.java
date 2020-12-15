/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
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

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.AbstractWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.xsd.AnyTypeReference;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CapabilitySetType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.EnumerationType.Item;
import esa.mo.xsd.FundamentalType;
import esa.mo.xsd.InvokeOperationType;
import esa.mo.xsd.NamedElementReferenceWithCommentType;
import esa.mo.xsd.OperationType;
import esa.mo.xsd.ProgressOperationType;
import esa.mo.xsd.PubSubOperationType;
import esa.mo.xsd.RequestOperationType;
import esa.mo.xsd.SendOperationType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.SpecificationType;
import esa.mo.xsd.SubmitOperationType;
import esa.mo.xsd.TypeReference;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;

/**
 * Converts an MO XML service specification that uses MO XML types to use XSD based types. Converts the MO types into
 * XSD equivalent. EXPERIMENTAL
 */
public class GeneratorXsd extends GeneratorDocument
{
  /**
   * Constructor.
   *
   * @param logger The logger to use.
   */
  public GeneratorXsd(org.apache.maven.plugin.logging.Log logger)
  {
    super(logger, new GeneratorConfiguration("", "", "", "", "", "", "", "", "", "", "", ""));

    addAttributeType(StdStrings.MAL, StdStrings.BLOB, true, "xsd:hexBinary", "");
    addAttributeType(StdStrings.MAL, StdStrings.BOOLEAN, true, "xsd:boolean", "");
    addAttributeType(StdStrings.MAL, StdStrings.DOUBLE, true, "xsd:double", "");
    addAttributeType(StdStrings.MAL, StdStrings.DURATION, true, "xsd:duration", "");
    addAttributeType(StdStrings.MAL, StdStrings.FLOAT, true, "xsd:float", "");
    addAttributeType(StdStrings.MAL, StdStrings.INTEGER, true, "xsd:int", "");
    addAttributeType(StdStrings.MAL, StdStrings.IDENTIFIER, true, "xsd:NCName", "");
    addAttributeType(StdStrings.MAL, StdStrings.LONG, true, "xsd:long", "");
    addAttributeType(StdStrings.MAL, StdStrings.OCTET, true, "xsd:byte", "");
    addAttributeType(StdStrings.MAL, StdStrings.SHORT, true, "xsd:short", "");
    addAttributeType(StdStrings.MAL, StdStrings.UINTEGER, true, "xsd:unsignedInt", "");
    addAttributeType(StdStrings.MAL, StdStrings.ULONG, true, "xsd:unsignedLong", "");
    addAttributeType(StdStrings.MAL, StdStrings.UOCTET, true, "xsd:unsignedByte", "");
    addAttributeType(StdStrings.MAL, StdStrings.USHORT, true, "xsd:unsignedShort", "");
    addAttributeType(StdStrings.MAL, StdStrings.STRING, true, "xsd:string", "");
    addAttributeType(StdStrings.MAL, StdStrings.TIME, true, "xsd:dateTime", "");
    addAttributeType(StdStrings.MAL, StdStrings.FINETIME, true, "xsd:dateTime", "");
    addAttributeType(StdStrings.MAL, StdStrings.URI, true, "xsd:anyURI", "");
  }

  @Override
  public String getShortName()
  {
    return "XSD";
  }

  @Override
  public String getDescription()
  {
    return "Generates an XSD type based equivalent of the service specification.";
  }

  @Override
  public void compile(String destFolderName, SpecificationType spec, JAXBElement rootNode) throws IOException, JAXBException
  {
    for (AreaType area : spec.getArea())
    {
      XsdWriter xsdTypesFile = new XsdWriter(destFolderName + "/types", "ServiceDef" + area.getName() + "Types", area.getName(), "xsd");

      getLog().info("Processing area: " + area.getName());

      // if area level types exist
      if ((null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty())
      {
        // create area level data types
        for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite())
        {
          if (oType instanceof FundamentalType)
          {
            createFundamentalType(xsdTypesFile, (FundamentalType) oType);
          }
          else if (oType instanceof AttributeType)
          {
            createAttributeType((AttributeType) oType);
          }
          else if (oType instanceof CompositeType)
          {
            createCompositeType(xsdTypesFile, (CompositeType) oType);
          }
          else if (oType instanceof EnumerationType)
          {
            createEnumerationType(xsdTypesFile, (EnumerationType) oType);
          }
          else
          {
            throw new IllegalArgumentException("Unexpected area (" + area.getName() + ") level datatype of " + oType.getClass().getName());
          }
        }
      }

      // process service level types
      for (ServiceType service : area.getService())
      {
        getLog().info("Processing service: " + service.getName());

        // if service level types exist
        if ((null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty())
        {
          for (Object oType : service.getDataTypes().getCompositeOrEnumeration())
          {
            if (oType instanceof EnumerationType)
            {
              createEnumerationType(xsdTypesFile, (EnumerationType) oType);
            }
            else if (oType instanceof CompositeType)
            {
              createCompositeType(xsdTypesFile, (CompositeType) oType);
            }
            else
            {
              throw new IllegalArgumentException("Unexpected service (" + area.getName() + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
            }
          }
        }
      }

      // modify area and services now for XSD/XML service output
      area.setDataTypes(null);

      for (ServiceType service : area.getService())
      {
        for (CapabilitySetType cSet : service.getCapabilitySet())
        {
          for (OperationType op : cSet.getSendIPOrSubmitIPOrRequestIP())
          {
            if (op instanceof SendOperationType)
            {
              SendOperationType lop = (SendOperationType) op;

              updateMessageFields(lop.getMessages().getSend());
            }
            else if (op instanceof SubmitOperationType)
            {
              SubmitOperationType lop = (SubmitOperationType) op;

              updateMessageFields(lop.getMessages().getSubmit());
            }
            else if (op instanceof RequestOperationType)
            {
              RequestOperationType lop = (RequestOperationType) op;

              updateMessageFields(lop.getMessages().getRequest());
              updateMessageFields(lop.getMessages().getResponse());
            }
            else if (op instanceof InvokeOperationType)
            {
              InvokeOperationType lop = (InvokeOperationType) op;

              updateMessageFields(lop.getMessages().getInvoke());
              updateMessageFields(lop.getMessages().getAcknowledgement());
              updateMessageFields(lop.getMessages().getResponse());
            }
            else if (op instanceof ProgressOperationType)
            {
              ProgressOperationType lop = (ProgressOperationType) op;

              updateMessageFields(lop.getMessages().getProgress());
              updateMessageFields(lop.getMessages().getAcknowledgement());
              updateMessageFields(lop.getMessages().getUpdate());
              updateMessageFields(lop.getMessages().getResponse());
            }
            else if (op instanceof PubSubOperationType)
            {
              PubSubOperationType lop = (PubSubOperationType) op;

              updateMessageFields(lop.getMessages().getPublishNotify());
            }
          }
        }

        service.setDataTypes(null);
      }

      xsdTypesFile.flush();

      {
        try
        {
          Writer os = StubUtils.createLowLevelWriter(destFolderName, "ServiceDef" + area.getName(), "xml");

          final JAXBContext jc = JAXBContext.newInstance("esa.mo.xsd");
          Marshaller marshaller = jc.createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
          marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
                  new CustomNamespacePrefixMapper(xsdTypesFile.getAreas()));
          marshaller.marshal(rootNode, os);
        }
        catch (JAXBException ex)
        {
          getLog().error(ex);
        }
      }
    }
  }

  @Override
  protected CompositeField createCompositeElementsDetails(TargetWriter file, boolean checkType, String fieldName, TypeReference elementType, boolean isStructure, boolean canBeNull, String comment)
  {
    CompositeField ele;
    String typeName = elementType.getName();
    if (isAttributeType(elementType))
    {
      AttributeTypeDetails details = getAttributeDetails(elementType);
      ele = new CompositeField(details.getTargetType(), elementType, fieldName, elementType.isList(), canBeNull, false, StdStrings.MAL, "", "", false, "", comment);
    }
    else
    {
      String fqTypeName = elementType.getArea().toLowerCase() + "Types:" + typeName;
      ele = new CompositeField(fqTypeName, elementType, fieldName, elementType.isList(), canBeNull, false, elementType.getArea(), "", elementType.getService(), false, "", comment);
    }
    return ele;
  }

  private void createFundamentalType(XsdWriter xsdFile, FundamentalType type) throws IOException
  {
    getLog().info("Creating type " + type.getName());

    // There are only three fundamental types
    if ("Element".equalsIgnoreCase(type.getName()))
    {

    }
    else if ("Composite".equalsIgnoreCase(type.getName()))
    {
      xsdFile.addXsdStatement(1, "<xsd:complexType name=\"Composite\" abstract=\"true\"/>");
    }
    else if ("Attribute".equalsIgnoreCase(type.getName()))
    {
      xsdFile.addXsdStatement(1, "<xsd:simpleType name=\"" + type.getName() + "\">");
      xsdFile.addXsdStatement(2, "<xsd:union>");
      for (Map.Entry<TypeKey, AttributeTypeDetails> item : getAttributeTypesMap().entrySet())
      {
        xsdFile.addXsdStatement(3, "<xsd:simpleType>");
        xsdFile.addXsdStatement(4, "<xsd:restriction base=\"" + item.getValue().getTargetType() + "\"/>");
        xsdFile.addXsdStatement(3, "</xsd:simpleType>");
      }
      xsdFile.addXsdStatement(2, "</xsd:union>");
      xsdFile.addXsdStatement(1, "</xsd:simpleType>");
    }
    else
    {
      getLog().error("Unexpected fundamental type defined: " + type.getName());
    }
  }

  private void createAttributeType(AttributeType type) throws IOException
  {
    getLog().info("Recording defintion of type " + type.getName());

    // we should probably put out a warning here if the type seen is not one we already know about...
  }

  private void createEnumerationType(XsdWriter xsdFile, EnumerationType type) throws IOException
  {
    getLog().info("Creating type " + type.getName());

    xsdFile.addXsdStatement(1, "<xsd:simpleType name=\"" + type.getName() + "\">");
    xsdFile.addXsdStatement(2, "<xsd:restriction base=\"xsd:string\">");
    for (Item item : type.getItem())
    {
      xsdFile.addXsdStatement(3, "<xsd:enumeration value=\"" + item.getValue() + "\"/>");
    }
    xsdFile.addXsdStatement(2, "</xsd:restriction>");
    xsdFile.addXsdStatement(1, "</xsd:simpleType>");
  }

  private void createCompositeType(XsdWriter xsdFile, CompositeType type) throws IOException
  {
    String compName = type.getName();

    getLog().info("Creating composite class " + compName);

    TypeReference superType;
    if (null == type.getExtends())
    {
      superType = TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.COMPOSITE, false);
    }
    else
    {
      superType = type.getExtends().getType();
    }
    xsdFile.addTypeDependency(superType.getArea());
    String superTypeName = createCompositeElementsDetails(null, false, null, superType, true, true, null).getTypeName();

    xsdFile.addXsdStatement(1, "<xsd:complexType name=\"" + compName + "\">");
    xsdFile.addXsdStatement(2, "<xsd:complexContent>");
    xsdFile.addXsdStatement(3, "<xsd:extension base=\"" + superTypeName + "\">");
    xsdFile.addXsdStatement(4, "<xsd:sequence>");

    List<CompositeField> compElements = createCompositeElementsList(xsdFile, type);

    for (CompositeField field : compElements)
    {
      String extr = "";
      if (field.isCanBeNull())
      {
        extr += " minOccurs=\"0\"";
      }
      if (field.isList())
      {
        extr += " maxOccurs=\"unbounded\"";
      }
      xsdFile.addTypeDependency(field.getTypeReference().getArea());
      xsdFile.addXsdStatement(5, "<xsd:element name=\"" + field.getFieldName() + "\" type=\"" + field.getTypeName() + "\"" + extr + "/>");
    }
    xsdFile.addXsdStatement(4, "</xsd:sequence>");
    xsdFile.addXsdStatement(3, "</xsd:extension>");
    xsdFile.addXsdStatement(2, "</xsd:complexContent>");
    xsdFile.addXsdStatement(1, "</xsd:complexType>");
  }

  private void updateMessageFields(AnyTypeReference msg)
  {
    List<Object> lst = msg.getAny();

    for (int i = 0; i < lst.size(); i++)
    {
      lst.set(i, updateMessageField(lst.get(i)));
    }
  }

  private Object updateMessageField(Object any)
  {
    if (null != any)
    {
      if (any instanceof JAXBElement)
      {
        JAXBElement re = (JAXBElement) any;
        if (re.getValue() instanceof TypeReference)
        {
          throw new IllegalArgumentException("Direct type not supported in message body of : " + re.getValue().getClass().getSimpleName());
        }
        else if (re.getValue() instanceof NamedElementReferenceWithCommentType)
        {
          NamedElementReferenceWithCommentType ne = (NamedElementReferenceWithCommentType) re.getValue();
          String partType = createCompositeElementsDetails(null, false, null, ne.getType(), true, true, null).getTypeName();

          if (ne.getType().isList())
          {
            // the message part is a list, XML schema does not support this at this level so we need to warn
            getLog().warn("XML Schema does not support top level elements with multiple occurrences of type " + partType);
          }

          DOMElement e = new DOMElement(new org.dom4j.QName("element", Namespace.get("http://www.w3.org/2001/XMLSchema")), 2);
          e.setAttribute("name", ne.getName());
          e.setAttribute("type", partType);

          return e;
        }
        else
        {
          throw new IllegalArgumentException("Unexpected type in message body of : " + re.getValue().getClass().getSimpleName());
        }
      }
      else if (!(any instanceof Element))
      {
        throw new IllegalArgumentException("Unexpected type in message body of : " + any.getClass().getSimpleName());
      }
    }

    return any;
  }

  private static class CustomNamespacePrefixMapper extends NamespacePrefixMapper
  {
    private final Set<String> areas;

    /**
     * Constructor.
     *
     * @param areas Set of XML areas.
     */
    public CustomNamespacePrefixMapper(Set<String> areas)
    {
      this.areas = areas;
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion,
            boolean requirePrefix)
    {
      return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris2()
    {
      String[] strs = new String[4 + (areas.size() * 2)];

      strs[0] = "xsd";
      strs[1] = "http://www.w3.org/2001/XMLSchema";
      strs[2] = "xsi";
      strs[3] = "http://www.w3.org/2001/XMLSchema-instance";

      int i = 4;
      for (String elem : areas)
      {
        strs[i] = elem.toLowerCase() + "Types";
        strs[i + 1] = "http://www.ccsds.org/schema/" + elem + "Types";

        i = i + 2;
      }

      return strs;
    }
  }

  private class XsdWriter extends AbstractWriter
  {
    private final Writer file;
    private final StringBuffer buffer = new StringBuffer();
    private final Set<String> areas = new TreeSet<String>();
    private final String thisArea;

    protected XsdWriter(String folder, String fileName, String areaName, String ext) throws IOException
    {
      thisArea = areaName;
      areas.add(areaName);

      file = StubUtils.createLowLevelWriter(folder, fileName, ext);
      file.append(addFileStatement(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false));
      file.append(addFileStatement(0, "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"", false));
      file.append(addFileStatement(0, "            targetNamespace=\"http://www.ccsds.org/schema/" + areaName + "Types\"", false));

      getLog().info("Creating file " + folder + " " + fileName + "." + ext);
    }

    /**
     * Creates a String indented correctly.
     *
     * @param tabCount Indentation level.
     * @param statement The file statement.
     */
    public void addXsdStatement(int tabCount, String statement)
    {
      buffer.append(addFileStatement(tabCount, statement, false));
    }

    @Override
    public void addTypeDependency(String typeName)
    {
      areas.add(typeName);
    }

    @Override
    public void flush() throws IOException
    {
      for (String area : areas)
      {
        file.append(addFileStatement(0, "            xmlns:" + area.toLowerCase() + "Types=\"http://www.ccsds.org/schema/" + area + "Types\"", false));
      }
      file.append(addFileStatement(0, "            elementFormDefault=\"qualified\">", false));

      for (String area : areas)
      {
        if (!area.equalsIgnoreCase(thisArea))
        {
          file.append(addFileStatement(1, "<xsd:import namespace=\"http://www.ccsds.org/schema/" + area + "Types\" schemaLocation=\"ServiceDef" + area + "Types.xsd\"/>", false));
        }
      }

      file.append(buffer.toString());
      file.append(addFileStatement(0, "</xsd:schema>", false));
      file.flush();
    }

    /**
     * Returns the set of XML areas.
     * 
     * @return the XML areas.
     */
    public Set<String> getAreas()
    {
      return areas;
    }
  }
}
