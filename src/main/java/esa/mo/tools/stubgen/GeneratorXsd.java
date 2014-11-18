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
import esa.mo.tools.stubgen.writers.AbstractWriter;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.tools.stubgen.xsd.AnyTypeReference;
import esa.mo.tools.stubgen.xsd.AreaType;
import esa.mo.tools.stubgen.xsd.AttributeType;
import esa.mo.tools.stubgen.xsd.CapabilitySetType;
import esa.mo.tools.stubgen.xsd.CompositeType;
import esa.mo.tools.stubgen.xsd.EnumerationType;
import esa.mo.tools.stubgen.xsd.EnumerationType.Item;
import esa.mo.tools.stubgen.xsd.FundamentalType;
import esa.mo.tools.stubgen.xsd.InvokeOperationType;
import esa.mo.tools.stubgen.xsd.NamedElementReferenceWithCommentType;
import esa.mo.tools.stubgen.xsd.OperationType;
import esa.mo.tools.stubgen.xsd.ProgressOperationType;
import esa.mo.tools.stubgen.xsd.PubSubOperationType;
import esa.mo.tools.stubgen.xsd.RequestOperationType;
import esa.mo.tools.stubgen.xsd.SendOperationType;
import esa.mo.tools.stubgen.xsd.ServiceType;
import esa.mo.tools.stubgen.xsd.SpecificationType;
import esa.mo.tools.stubgen.xsd.SubmitOperationType;
import esa.mo.tools.stubgen.xsd.TypeReference;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
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
 * Converts an MO XML service specification that uses MO XML types to use XSD
 * based types. Converts the MO types into XSD equivalent. EXPERIMENTAL
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

    addAttributeType(StdStrings.BLOB, new AttributeTypeDetails(this, true, "xsd:hexBinary", ""));
    addAttributeType(StdStrings.BOOLEAN, new AttributeTypeDetails(this, true, "xsd:boolean", ""));
    addAttributeType(StdStrings.DOUBLE, new AttributeTypeDetails(this, true, "xsd:double", ""));
    addAttributeType(StdStrings.DURATION, new AttributeTypeDetails(this, true, "xsd:duration", ""));
    addAttributeType(StdStrings.FLOAT, new AttributeTypeDetails(this, true, "xsd:float", ""));
    addAttributeType(StdStrings.INTEGER, new AttributeTypeDetails(this, true, "xsd:int", ""));
    addAttributeType(StdStrings.IDENTIFIER, new AttributeTypeDetails(this, true, "xsd:NCName", ""));
    addAttributeType(StdStrings.LONG, new AttributeTypeDetails(this, true, "xsd:long", ""));
    addAttributeType(StdStrings.OCTET, new AttributeTypeDetails(this, true, "xsd:byte", ""));
    addAttributeType(StdStrings.SHORT, new AttributeTypeDetails(this, true, "xsd:short", ""));
    addAttributeType(StdStrings.UINTEGER, new AttributeTypeDetails(this, true, "xsd:unsignedInt", ""));
    addAttributeType(StdStrings.ULONG, new AttributeTypeDetails(this, true, "xsd:unsignedLong", ""));
    addAttributeType(StdStrings.UOCTET, new AttributeTypeDetails(this, true, "xsd:unsignedByte", ""));
    addAttributeType(StdStrings.USHORT, new AttributeTypeDetails(this, true, "xsd:unsignedShort", ""));
    addAttributeType(StdStrings.STRING, new AttributeTypeDetails(this, true, "xsd:string", ""));
    addAttributeType(StdStrings.TIME, new AttributeTypeDetails(this, true, "xsd:dateTime", ""));
    addAttributeType(StdStrings.FINETIME, new AttributeTypeDetails(this, true, "xsd:dateTime", ""));
    addAttributeType(StdStrings.URI, new AttributeTypeDetails(this, true, "xsd:anyURI", ""));
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
            createAttributeType(xsdTypesFile, (AttributeType) oType);
          }
          else if (oType instanceof CompositeType)
          {
            createCompositeType(xsdTypesFile, area, (CompositeType) oType);
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
              createCompositeType(xsdTypesFile, area, (CompositeType) oType);
            }
            else
            {
              throw new IllegalArgumentException("Unexpected service (" + area.getName() + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
            }
          }
        }
      }

      xsdTypesFile.flush();

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

      {
        try
        {
          Writer os = StubUtils.createLowLevelWriter(destFolderName, "ServiceDef" + area.getName(), "xml");

          String schemaURN = "http://www.ccsds.org/schema/ServiceSchema";
          String schemaEle = "specification";
          final JAXBContext jc = JAXBContext.newInstance("esa.mo.tools.stubgen.xsd");
          Marshaller marshaller = jc.createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
          marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",
                  new CustomNamespacePrefixMapper(xsdTypesFile.getAreas()));
          marshaller.marshal(rootNode, os);
        }
        catch (JAXBException ex)
        {
          ex.printStackTrace();
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

    }
    else if ("Attribute".equalsIgnoreCase(type.getName()))
    {
      xsdFile.addXsdStatement(1, "<xsd:simpleType name=\"" + type.getName() + "\">");
      xsdFile.addXsdStatement(2, "<xsd:union>");
      for (Map.Entry<String, AttributeTypeDetails> item : getAttributeTypesMap().entrySet())
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

  private void createAttributeType(XsdWriter xsdFile, AttributeType type) throws IOException
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

  private void createCompositeType(XsdWriter xsdFile, AreaType area, CompositeType type) throws IOException
  {
    String compName = type.getName();

    getLog().info("Creating composite class " + compName);

    // ToDo support type extension
    // ToDo extend Composite by default
    xsdFile.addXsdStatement(1, "<xsd:complexType name=\"" + compName + "\">");
    xsdFile.addXsdStatement(2, "<xsd:sequence>");

    List<CompositeField> compElements = createCompositeElementsList(xsdFile, type);

    for (CompositeField field : compElements)
    {
      xsdFile.addTypeDependency(field.getTypeReference().getArea());
      xsdFile.addXsdStatement(3, "<xsd:element name=\"" + field.getFieldName() + "\" type=\"" + field.getTypeName() + "\"/>");
    }
    xsdFile.addXsdStatement(2, "</xsd:sequence>");
    xsdFile.addXsdStatement(1, "</xsd:complexType>");
  }

  private void updateMessageFields(AnyTypeReference msg)
  {
    List<Object> lst = msg.getAny();

    for (int i = 0; i < lst.size(); i++)
    {
      lst.set(i, updateMessageField(i, lst.get(i)));
    }
  }

  public Object updateMessageField(int index, Object any)
  {
    if (null != any)
    {
      if (any instanceof JAXBElement)
      {
        JAXBElement re = (JAXBElement) any;
        if (re.getValue() instanceof TypeReference)
        {
          Element e = new DOMElement("ELE", Namespace.NO_NAMESPACE);
          return e;
          //return new XsdElement((TypeReference) re.getValue());
        }
        else if (re.getValue() instanceof NamedElementReferenceWithCommentType)
        {
          // <xsd:element name="referenceSystem" type="ndm:ndmHeader"/>

          NamedElementReferenceWithCommentType ne = (NamedElementReferenceWithCommentType) re.getValue();

          DOMElement e = new DOMElement(new org.dom4j.QName("element", Namespace.get("http://www.w3.org/2001/XMLSchema")), 2);
          e.setAttribute("name", ne.getName());
          e.setAttribute("type", createCompositeElementsDetails(null, false, null, ne.getType(), true, true, null).getTypeName());

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
      for (Iterator<String> it = areas.iterator(); it.hasNext();)
      {
        String elem = it.next();

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

    protected XsdWriter(String folder, String fileName, String areaName, String ext) throws IOException
    {
      areas.add(areaName);

      file = StubUtils.createLowLevelWriter(folder, fileName, ext);
      file.append(addFileStatement(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false));
      file.append(addFileStatement(0, "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"", false));
      file.append(addFileStatement(0, "            targetNamespace=\"http://www.ccsds.org/schema/" + areaName + "Types\"", false));
      buffer.append(addFileStatement(0, "            elementFormDefault=\"qualified\">", false));

      getLog().info("Creating file " + folder + " " + fileName + "." + ext);
    }

    /**
     * Creates a String indented correctly with a semicolon at the end if
     * required.
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
      file.append(buffer.toString());
      file.append(addFileStatement(0, "</xsd:schema>", false));
      file.flush();
    }

    public Set<String> getAreas()
    {
      return areas;
    }
  }
}
