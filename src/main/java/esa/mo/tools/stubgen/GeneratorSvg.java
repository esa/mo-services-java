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

import static esa.mo.tools.stubgen.GeneratorDocument.splitString;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.AbstractWriter;
import esa.mo.tools.stubgen.xsd.*;
import esa.mo.tools.stubgen.xsd.EnumerationType.Item;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

/**
 * Generates an XHTML compliant file of the service specification in an ECSS PUS style. EXPERIMENTAL.
 */
public class GeneratorSvg extends GeneratorDocument
{
  private boolean includeDescriptions = true;
  private boolean includeIndexes = true;
  private boolean splitOutSvg = false;
  private boolean includeCollapsedMessages = true;
  private boolean includeExpandedMessages = true;

  /**
   * Constructor.
   *
   * @param logger The logger to use.
   */
  public GeneratorSvg(org.apache.maven.plugin.logging.Log logger)
  {
    super(logger, new GeneratorConfiguration("", "", "", "", "", "", "", "", "", "", "", ""));
  }

  @Override
  public String getShortName()
  {
    return "SVG";
  }

  @Override
  public String getDescription()
  {
    return "Generates a navigable XHTML file in an ECSS PUS style.";
  }

  @Override
  public void init(String destinationFolderName, boolean generateStructures, boolean generateCOM, Map<String, String> extraProperties) throws IOException
  {
    super.init(destinationFolderName, generateStructures, generateCOM, extraProperties);

    if (extraProperties.containsKey("svg.includeDescriptiveText"))
    {
      includeDescriptions = Boolean.parseBoolean(extraProperties.get("svg.includeDescriptiveText"));
    }

    if (extraProperties.containsKey("svg.includeIndexes"))
    {
      includeIndexes = Boolean.parseBoolean(extraProperties.get("svg.includeIndexes"));
    }

    if (extraProperties.containsKey("svg.splitOutSvg"))
    {
      splitOutSvg = Boolean.parseBoolean(extraProperties.get("svg.splitOutSvg"));
      System.out.println("svg.splitOutSvg: " + splitOutSvg);
    }

    if (extraProperties.containsKey("svg.includeCollapsedMessages"))
    {
      includeCollapsedMessages = Boolean.parseBoolean(extraProperties.get("svg.includeCollapsedMessages"));
    }

    if (extraProperties.containsKey("svg.includeExpandedMessages"))
    {
      includeExpandedMessages = Boolean.parseBoolean(extraProperties.get("svg.includeExpandedMessages"));
    }
  }

  @Override
  public void compile(String destinationFolderName, SpecificationType spec, JAXBElement rootNode) throws IOException, JAXBException
  {
    for (AreaType area : spec.getArea())
    {
      Map<String, String> indexMap = new TreeMap<String, String>();

      if ((!area.getName().equalsIgnoreCase(StdStrings.COM)) || (generateCOM()))
      {
        String outputName = "output" + area.getName();
        SvgWriter svgFile = new SvgWriter(destinationFolderName, outputName, "xhtml", true);
        SvgBufferWriter svgBuff = new SvgBufferWriter(destinationFolderName, outputName, true);

        getLog().info("Processing area: " + area.getName());
        svgBuff.addTitle(1, "Specification: ", createId(null, null), area.getName(), false);
        svgBuff.addComment(area.getComment());

        Set<Map.Entry<String, String>> svcTocMap = new LinkedHashSet<Map.Entry<String, String>>();
        SvgBufferWriter areaBodyBuff = new SvgBufferWriter(destinationFolderName, outputName, true);

        // create services
        for (ServiceType service : area.getService())
        {
          areaBodyBuff.addTitle(2, "Service: ", createId(service, null), service.getName(), false);
          areaBodyBuff.addComment(service.getComment());

          svcTocMap.add(new AbstractMap.SimpleEntry<String, String>(service.getName(), createXlink(null, service.getName(), null)));

          Set<Map.Entry<String, String>> opTocMap = new LinkedHashSet<Map.Entry<String, String>>();
          SvgBufferWriter serviceBodyBuff = new SvgBufferWriter(destinationFolderName, outputName, true);

          ServiceSummary summary = createOperationElementList(service);

          for (OperationSummary op : summary.getOperations())
          {
            serviceBodyBuff.addTitle(3, "Operation: ", createId(service, op.getName()), op.getName(), false);
            serviceBodyBuff.addComment(op.getOriginalOp().getComment());

            opTocMap.add(new AbstractMap.SimpleEntry<String, String>(op.getName(), createXlink(null, service.getName(), op.getName())));

            drawOperationMessages(serviceBodyBuff, summary, op);
          }

          if (!opTocMap.isEmpty())
          {
            if (includeIndexes)
            {
              areaBodyBuff.addIndex("Operations", 3, opTocMap);
            }
            areaBodyBuff.appendBuffer(serviceBodyBuff.getBuffer());
          }

          if (service instanceof ExtendedServiceType)
          {
            ExtendedServiceType eservice = (ExtendedServiceType) service;

            if ((null != eservice.getFeatures()) && (null != eservice.getFeatures().getEvents()))
            {
              Set<Map.Entry<String, String>> evTocMap = new LinkedHashSet<Map.Entry<String, String>>();
              SvgBufferWriter eventBodyBuff = new SvgBufferWriter(destinationFolderName, outputName, true);

              for (ModelObjectType evt : eservice.getFeatures().getEvents().getEvent())
              {
                eventBodyBuff.addTitle(3, "Event: ", createId(service, evt.getName()), evt.getName(), false);

                evTocMap.add(new AbstractMap.SimpleEntry<String, String>(evt.getName(), createXlink(null, service.getName(), evt.getName())));

                List<TypeInfo> types = new LinkedList<TypeInfo>();
                if (null != evt.getObjectType())
                {
                  types = TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(evt.getObjectType().getAny()));
                }
                drawOperationTypes(eventBodyBuff, summary, (int)evt.getNumber(), "Event additional application data:", types, evt.getComment(), evt.getName(), "EVENT");
              }

              if (!evTocMap.isEmpty())
              {
                if (includeIndexes)
                {
                  areaBodyBuff.addIndex("Operations", 3, evTocMap);
                }
                areaBodyBuff.appendBuffer(eventBodyBuff.getBuffer());
              }
            }
          }
        }

        if (!svcTocMap.isEmpty())
        {
          if (includeIndexes)
          {
            svgBuff.addIndex("Services", 2, svcTocMap);
          }
          svgBuff.appendBuffer(areaBodyBuff.getBuffer());
        }

        // process data types
        svgBuff.addTitle(1, "Data types", null, "", false);

        // if area level types exist
        if ((null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty())
        {
          svgBuff.addTitle(2, "Area data types: ", null, area.getName(), false);
          // create area level data types
          for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite())
          {
            if (oType instanceof FundamentalType)
            {
              createFundamentalClass(svgBuff, indexMap, (FundamentalType) oType);
            }
            else if (oType instanceof AttributeType)
            {
              createAttributeClass(svgBuff, indexMap, (AttributeType) oType);
            }
            else if (oType instanceof CompositeType)
            {
              createCompositeClass(svgBuff, indexMap, null, (CompositeType) oType);
            }
            else if (oType instanceof EnumerationType)
            {
              createEnumerationClass(svgBuff, indexMap, null, (EnumerationType) oType);
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
          // if service level types exist
          if ((null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty())
          {
            svgBuff.addTitle(2, "Service data types: ", null, service.getName(), false);
            for (Object oType : service.getDataTypes().getCompositeOrEnumeration())
            {
              if (oType instanceof EnumerationType)
              {
                createEnumerationClass(svgBuff, indexMap, service, (EnumerationType) oType);
              }
              else if (oType instanceof CompositeType)
              {
                createCompositeClass(svgBuff, indexMap, service, (CompositeType) oType);
              }
              else
              {
                throw new IllegalArgumentException("Unexpected service (" + area.getName() + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
              }
            }
          }
        }

        if (!indexMap.isEmpty() && includeIndexes)
        {
          svgBuff.addIndex("Index", 1, indexMap.entrySet());
        }
        svgBuff.flush();

        svgFile.appendBuffer(svgBuff.getBuffer());
        svgFile.flush();
      }
    }
  }

  private void drawOperationMessages(SvgBaseWriter svgFile, ServiceSummary summary, OperationSummary op) throws IOException
  {
    Integer opNumber = op.getNumber();
    
    switch (op.getPattern())
    {
      case SEND_OP:
      {
        drawOperationTypes(svgFile, summary, opNumber, "Telecommand application data:", op.getArgTypes(), op.getArgComment(), op.getName(), "SEND");
        break;
      }
      case SUBMIT_OP:
      {
        drawOperationTypes(svgFile, summary, opNumber, "Telecommand application data:", op.getArgTypes(), op.getArgComment(), op.getName(), "SUBMIT");
        break;
      }
      case REQUEST_OP:
      {
        drawOperationTypes(svgFile, summary, opNumber, "Telecommand application data:", op.getArgTypes(), op.getArgComment(), op.getName(), "REQUEST");
        drawOperationTypes(svgFile, summary, opNumber, "Response telemetry report application data:", op.getRetTypes(), op.getRetComment(), op.getName(), "RESPONSE");
        break;
      }
      case INVOKE_OP:
      {
        drawOperationTypes(svgFile, summary, opNumber, "Telecommand application data:", op.getArgTypes(), op.getArgComment(), op.getName(), "INVOKE");
        drawOperationTypes(svgFile, summary, opNumber, "Acknowledgement telemetry report application data:", op.getAckTypes(), op.getAckComment(), op.getName(), "INVOKE_ACK");
        drawOperationTypes(svgFile, summary, opNumber, "Response telemetry report application data:", op.getRetTypes(), op.getRetComment(), op.getName(), "INVOKE_RESPONSE");
        break;
      }
      case PROGRESS_OP:
      {
        drawOperationTypes(svgFile, summary, opNumber, "Telecommand application data:", op.getArgTypes(), op.getArgComment(), op.getName(), "PROGRESS");
        drawOperationTypes(svgFile, summary, opNumber, "Acknowledgement telemetry report application data:", op.getAckTypes(), op.getAckComment(), op.getName(), "PROGRESS_ACK");
        drawOperationTypes(svgFile, summary, opNumber, "Progress telemetry report application data:", op.getUpdateTypes(), op.getUpdateComment(), op.getName(), "PROGRESS_UPDATE");
        drawOperationTypes(svgFile, summary, opNumber, "Response telemetry report application data:", op.getRetTypes(), op.getRetComment(), op.getName(), "PROGRESS_RESPONSE");
        break;
      }
      case PUBSUB_OP:
      {
        List<esa.mo.tools.stubgen.specification.TypeInfo> types = new ArrayList<TypeInfo>();
        TypeReference subId = new TypeReference();
        subId.setArea("MAL");
        subId.setName("Identifier");
        TypeReference updateHdr = new TypeReference();
        updateHdr.setArea("MAL");
        updateHdr.setName("UpdateHeader");
        updateHdr.setList(Boolean.TRUE);
        types.add(0, TypeUtils.convertTypeReference(this, subId));
        types.add(1, TypeUtils.convertTypeReference(this, updateHdr));
        List<esa.mo.tools.stubgen.specification.TypeInfo> rt = op.getRetTypes();
        for (TypeInfo typeInfo : rt)
        {
          TypeReference refType = typeInfo.getSourceType();
          refType.setList(Boolean.TRUE);
          types.add(TypeUtils.convertTypeReference(this, refType));
        }
        drawOperationTypes(svgFile, summary, opNumber, "Notify telemetry report application data:", types, op.getRetComment(), op.getName(), "PUBSUB");
        break;
      }

      default:
      {
        // do nothing
      }
    }
  }

  private void createFundamentalClass(SvgBaseWriter svgFile, Map<String, String> indexMap, FundamentalType fundamental) throws IOException
  {
    String fundName = fundamental.getName();

    getLog().info("Creating fundamental class " + fundName);

    svgFile.addTitle(3, "Fundamental: ", createId(null, fundName), fundName, true);

    if ((null != fundamental.getComment()) && (0 < fundamental.getComment().length()))
    {
      svgFile.addComment(fundamental.getComment());
    }

    indexMap.put(fundName, createXlink(null, null, fundName));
  }

  private void createAttributeClass(SvgBaseWriter svgFile, Map<String, String> indexMap, AttributeType attribute) throws IOException
  {
    String attrName = attribute.getName();

    getLog().info("Creating attribute class " + attrName);

    svgFile.addTitle(3, "Attribute: ", createId(null, attrName), attrName, true);

    if ((null != attribute.getComment()) && (0 < attribute.getComment().length()))
    {
      svgFile.addComment(attribute.getComment());
    }

    indexMap.put(attrName, createXlink(null, null, attrName));
  }

  private void createEnumerationClass(SvgBaseWriter svgFile, Map<String, String> indexMap, ServiceType service, EnumerationType enumeration) throws IOException
  {
    String enumName = enumeration.getName();

    getLog().info("Creating enumeration class " + enumName);

    svgFile.addTitle(3, "Enum: ", createId(service, enumName), enumName, false);

    if ((null != enumeration.getComment()) && (0 < enumeration.getComment().length()))
    {
      svgFile.addComment(enumeration.getComment());
    }

    // create attributes
    for (Item item : enumeration.getItem())
    {
      svgFile.addFieldComment(item.getValue(), item.getComment());
    }

    indexMap.put(enumName, createXlink(null, (service == null ? null : service.getName()), enumName));
  }

  private void createCompositeClass(SvgBaseWriter svgFile, Map<String, String> indexMap, ServiceType service, CompositeType composite) throws IOException
  {
    String compName = composite.getName();

    getLog().info("Creating composite class " + compName);

    boolean abstractComposite = (null == composite.getShortFormPart());

    svgFile.addTitle(3, "Composite: ", createId(service, compName), compName, abstractComposite);

    if ((null != composite.getComment()) && (0 < composite.getComment().length()))
    {
      svgFile.addComment(composite.getComment());
    }

    List<CompositeField> compElements = createCompositeElementsList(svgFile, composite);

    drawCompositeType(svgFile, composite, compElements);

    if (!compElements.isEmpty())
    {
      for (CompositeField element : compElements)
      {
        svgFile.addFieldComment(element.getFieldName(), element.getComment());
      }
    }

    indexMap.put(compName, createXlink(null, (service == null ? null : service.getName()), compName));
  }

  private void drawOperationTypes(SvgBaseWriter svgFile, ServiceSummary summary, Integer number, String title, List<TypeInfo> types, String comment, String name, String phase) throws IOException
  {
    if (0 < types.size())
    {
      svgFile.addTitle(4, title, null, "", false);
    }
    else
    {
      svgFile.addTitle(4, title, null, " None", false);
    }

    List<String> cmts = new LinkedList<String>();
    cmts.add(comment);
    for (TypeInfo e : types)
    {
      cmts.add(e.getFieldComment());
    }

    if (includeCollapsedMessages)
    {
      if (0 < types.size())
      {
        SvgBaseWriter svgOutput = getSvgOutputFile(summary, number, name, phase, svgFile);
        svgOutput.startDrawing();
        for (TypeInfo e : types)
        {
          drawOperationPart(svgOutput, e);
          cmts.add(e.getFieldComment());
        }
        svgOutput.endDrawing();
        svgOutput.flush();
      }
    }

    if (includeExpandedMessages)
    {
      if (0 < types.size())
      {
        SvgBaseWriter svgOutput = getSvgOutputFile(summary, number, name, phase, svgFile);
        TopContainer cnt = new TopContainer();
        cnt.addOperationTypes(types);
        cnt.expandType();

        cnt.drawElement(svgOutput, 1, 0, cnt.getDepth(0));
        svgOutput.flush();
      }
    }

    svgFile.addComment(cmts);
  }

  private void drawOperationPart(SvgBaseWriter svgFile, TypeInfo type) throws IOException
  {
    if (null != type)
    {
      String partName = "Part";

      if ((null != type.getFieldName()) && (0 < type.getFieldName().length()))
      {
        partName = type.getFieldName();
      }

      if (type.getSourceType().isList())
      {
        svgFile.addField("N", StdStrings.INTEGER, createXlink(StdStrings.MAL, null, StdStrings.INTEGER), false, false);
        svgFile.addSpan(1, 1, "Repeated N times");
        svgFile.addField(partName, type.getSourceType().getName(), createXlink(type.getSourceType().getArea(), type.getSourceType().getService(), type.getSourceType().getName()), isAbstract(type.getSourceType()), isEnum(type.getSourceType()));
      }
      else
      {
        svgFile.addField(partName, type.getActualMalType(), createXlink(type.getSourceType().getArea(), type.getSourceType().getService(), type.getActualMalType()), isAbstract(type.getSourceType()), isEnum(type.getSourceType()));
      }
    }
  }

  private void drawCompositeType(SvgBaseWriter svgFile, CompositeType composite, List<CompositeField> compElements) throws IOException
  {
    svgFile.startDrawing();

    if (null == compElements)
    {
      compElements = createCompositeElementsList(svgFile, composite);
    }

    if ((null != composite.getExtends()) && (!StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName())))
    {
      TypeReference type = composite.getExtends().getType();
      svgFile.addParent(type.getName(), createXlink(type.getArea(), type.getService(), type.getName()));
    }

    // create attributes
    if (!compElements.isEmpty())
    {
      for (CompositeField element : compElements)
      {
        if (element.isCanBeNull())
        {
          int c = 1;
          if (element.isList())
          {
            ++c;
          }
          svgFile.addSpan(c, c, "Nullable");
        }

        if (element.isList())
        {
          svgFile.addField("N", StdStrings.INTEGER, createXlink(StdStrings.MAL, null, StdStrings.INTEGER), false, false);
          svgFile.addSpan(1, 1, "Repeated N times");
        }
        svgFile.addField(element.getFieldName(), element.getTypeName(), createXlink(element.getEncodeCall(), element.getDecodeCall(), element.getTypeName()), isAbstract(element.getTypeReference()), isEnum(element.getTypeReference()));
      }
    }
    svgFile.endDrawing();
  }

  private String createId(ServiceType service, String name)
  {
    StringBuilder buf = new StringBuilder();

    if (null != service)
    {
      buf.append(service.getName());
    }

    buf.append('_');

    if (null != name)
    {
      buf.append(name);
    }

    return buf.toString();
  }

  private String createXlink(String areaName, String serviceName, String section)
  {
    if (null == serviceName)
    {
      serviceName = "_";
    }
    else
    {
      serviceName += "_";
    }

    if (null == section)
    {
      section = "";
    }

    if ((null != areaName) && (0 < areaName.length()))
    {
      return "output" + areaName + ".xhtml#" + serviceName + section;
    }
    else
    {
      return "#" + serviceName + section;
    }
  }

  private SvgBaseWriter getSvgOutputFile(ServiceSummary service, Integer number, String name, String phase, SvgBaseWriter mainSvgFile)
  {
    SvgBaseWriter rv = mainSvgFile;

    if (splitOutSvg)
    {
      try
      {
        String filename = mainSvgFile.getClassName() + "_" + service.getService().getNumber() + "_" + number + "_" + name + "_" + phase;
        rv = new SvgWriter(mainSvgFile.getFolder(), filename, "svg", false);
        mainSvgFile.addComment(filename, true);
      }
      catch (IOException ex)
      {
        // nop
      }
    }

    return rv;
  }

  private abstract class ContainerElement
  {
    protected final Container container;
    protected final TypeReference typeRef;
    protected final String name;
    protected final String type;
    protected final boolean isList;
    protected final boolean isOptional;
    protected boolean expanded = false;

    public ContainerElement(Container container, TypeReference typeRef, String name, String type, boolean isList, boolean isOptional)
    {
      this.container = container;
      this.typeRef = typeRef;
      this.name = name;
      this.type = type;
      this.isList = isList;
      this.isOptional = isOptional;
    }

    public int getDepth(int parentDepth)
    {
      return parentDepth + 1;
    }

    public int getWidth()
    {
      return 1 + (isList ? 1 : 0);
    }

    public int getSpanDepth()
    {
      if (isList)
      {
        if (isOptional)
        {
          return 2;
        }

        return 1;
      }
      else
      {
        if (isOptional)
        {
          return 1;
        }
      }

      return 0;
    }

    public void drawElement(SvgBaseWriter svgFile, int xOff, int yOff, int fullDepth) throws IOException
    {
      int thisDepth = fullDepth - getDepth(0) + 1;
      int spanDepth = getSpanDepth();
      int width = getWidth() - 1;

      if (isList)
      {
        int repSpanDepth = spanDepth;

        int li = getListIndex();
        svgFile.addSubField("N" + li, StdStrings.INTEGER, createXlink(StdStrings.MAL, null, StdStrings.INTEGER), xOff, yOff, 1, fullDepth, false, false, false);
        svgFile.addSubField(name, type, createXlink(typeRef.getArea(), typeRef.getService(), typeRef.getName()), xOff + 1, yOff, width, thisDepth, isAbstract(typeRef), isEnum(typeRef), isComposite(typeRef));

        if (isOptional)
        {
          svgFile.addSubSpan(xOff, yOff + fullDepth, getWidth(), spanDepth, "Nullable");
          --repSpanDepth;
        }

        svgFile.addSubSpan(xOff + 1, yOff + fullDepth, width, repSpanDepth, "Repeated N" + li + " times");
      }
      else
      {
        svgFile.addSubField(name, type, createXlink(typeRef.getArea(), typeRef.getService(), typeRef.getName()), xOff, yOff, getWidth(), thisDepth, isAbstract(typeRef), isEnum(typeRef), isComposite(typeRef));

        if (isOptional)
        {
          svgFile.addSubSpan(xOff, yOff + fullDepth, getWidth(), spanDepth, "Nullable");
        }
      }
    }

    public abstract void expandType();

    protected int getListIndex()
    {
      return container.getListIndex();
    }
  }

  private class AbstractElement extends ContainerElement
  {
    public AbstractElement(Container container, TypeReference typeRef, String name, String type, boolean isList, boolean isOptional)
    {
      super(container, typeRef, name, type, isList, isOptional);
    }

    @Override
    public void expandType()
    {
    }

    @Override
    public String toString()
    {
      return "Abstract{name=" + name + ", type=" + type + '}';
    }
  }

  private class ContainerAttribute extends ContainerElement
  {
    public ContainerAttribute(Container container, TypeReference typeRef, String name, String type, boolean isList, boolean isOptional)
    {
      super(container, typeRef, name, type, isList, isOptional);
      expanded = true;
    }

    @Override
    public void expandType()
    {
    }

    @Override
    public String toString()
    {
      return "ContainerAttribute{name=" + name + ", type=" + type + '}';
    }
  }

  private abstract class Container extends ContainerElement
  {
    protected final List<ContainerElement> elements = new ArrayList<ContainerElement>();

    public Container(Container container, TypeReference typeRef, String name, String type, boolean isList, boolean isOptional)
    {
      super(container, typeRef, name, type, isList, isOptional);
    }

    @Override
    public int getDepth(int parentDepth)
    {
      final int myDepth = super.getDepth(parentDepth);
      int returnDepth = myDepth;

      for (ContainerElement containerElement : elements)
      {
        int childDepth = containerElement.getDepth(myDepth);

        if (childDepth > returnDepth)
        {
          returnDepth = childDepth;
        }
      }

      return returnDepth;
    }

    @Override
    public int getWidth()
    {
      int width = isList ? 1 : 0;

      for (ContainerElement containerElement : elements)
      {
        width += containerElement.getWidth();
      }

      return width;
    }

    @Override
    public int getSpanDepth()
    {
      int returnDepth = 0;

      for (ContainerElement containerElement : elements)
      {
        int childDepth = containerElement.getSpanDepth();

        if (childDepth > returnDepth)
        {
          returnDepth = childDepth;
        }
      }

      return returnDepth + super.getSpanDepth();
    }

    public void addTypeElement(String eName, TypeReference type, boolean isOptional)
    {
      if (isAttributeType(type) || isEnum(type))
      {
        addElement(new ContainerAttribute(this, type, eName, type.getName(), type.isList(), isOptional));
      }
      else
      {
        if (isComposite(type))
        {
          addElement(new CompositeContainer(this, type, eName, type.getName(), type.isList(), isOptional, type));
        }
        else
        {
          addElement(new AbstractElement(this, type, eName, type.getName(), type.isList(), isOptional));
        }
      }
    }

    @Override
    public void expandType()
    {
      if (!expanded)
      {
        expanded = true;

        for (ContainerElement containerElement : elements)
        {
          containerElement.expandType();
        }
      }
    }

    @Override
    public void drawElement(SvgBaseWriter svgFile, int xOff, int yOff, int fullDepth) throws IOException
    {
      super.drawElement(svgFile, xOff, yOff, fullDepth);

      int i = isList ? 1 : 0;

      for (ContainerElement elem : elements)
      {
        if (null != elem)
        {
          elem.drawElement(svgFile, xOff + i, yOff + 1, fullDepth - 1);
          i = i + elem.getWidth();
        }
      }
    }

    private void addElement(ContainerElement ele)
    {
      elements.add(ele);
    }
  }

  private class TopContainer extends Container
  {
    private int listIndex = 1;

    public TopContainer()
    {
      super(null, null, "", "", false, false);
    }

    public void addOperationTypes(List<TypeInfo> types)
    {
      for (TypeInfo e : types)
      {
        if (null != e)
        {
          String pname = "Part";

          if ((null != e.getFieldName()) && (0 < e.getFieldName().length()))
          {
            pname = e.getFieldName();
          }

          addTypeElement(pname, e.getSourceType(), false);
        }
      }
    }

    @Override
    public int getDepth(int parentDepth)
    {
      return super.getDepth(parentDepth) - 1;
    }

    @Override
    public void drawElement(SvgBaseWriter svgFile, int xOff, int yOff, int fullDepth) throws IOException
    {
      svgFile.startDrawing();

      int i = 0;

      for (ContainerElement elem : elements)
      {
        if (null != elem)
        {
          elem.drawElement(svgFile, xOff + i, yOff, fullDepth);
          i = i + elem.getWidth();
        }
      }

      svgFile.endDrawing();
    }

    @Override
    public String toString()
    {
      return "TopContainer{name=" + name + ", type=" + type + ", elements=" + elements + '}';
    }

    @Override
    protected int getListIndex()
    {
      return listIndex++;
    }
  }

  private class CompositeContainer extends Container
  {
    private final TypeReference tr;
    private boolean fullyExpanded = false;

    public CompositeContainer(Container container, TypeReference typeRef, String name, String type, boolean isList, boolean isOptional, TypeReference tr)
    {
      super(container, typeRef, name, type, isList, isOptional);
      this.tr = tr;
    }

    @Override
    public void expandType()
    {
      if (!fullyExpanded)
      {
        fullyExpanded = true;

        CompositeType composite = getCompositeDetails(tr);

        List<CompositeField> compElements = createCompositeElementsList(null, composite);

        if ((null != composite.getExtends()) && (!StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName())))
        {
          TypeReference ltr = composite.getExtends().getType();
          addTypeElement(ltr.getName(), ltr, false);
        }

        // create attributes
        if (!compElements.isEmpty())
        {
          for (CompositeField element : compElements)
          {
            addTypeElement(element.getFieldName(), element.getTypeReference(), element.isCanBeNull());
          }
        }

        super.expandType();
      }
    }

    @Override
    public String toString()
    {
      return "CompositeContainer{name=" + name + ", type=" + type + ", elements=" + elements + '}';
    }
  }

  private class SvgWriter extends SvgBaseWriter
  {
    protected SvgWriter(String folder, String className, String ext, boolean withXhtml) throws IOException
    {
      super(folder, className, StubUtils.createLowLevelWriter(folder, className, ext), withXhtml);

      getLog().info("Creating file " + folder + " " + className + "." + ext);

      if (withXhtml)
      {
        getFile().append(addFileStatement(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false));
        getFile().append(addFileStatement(0, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">", false));
        getFile().append(addFileStatement(0, "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">", false));
        getFile().append(addFileStatement(1, "<head>", false));
        getFile().append(addFileStatement(2, "<title></title>", false));
        getFile().append(addFileStatement(1, "</head>", false));
        getFile().append(addFileStatement(1, "<body>", false));
      }
    }

    @Override
    public void flush() throws IOException
    {
      if (withXhtml)
      {
        getFile().append(addFileStatement(1, "</body>", false));
        getFile().append(addFileStatement(0, "</html>", false));
      }
      getFile().flush();
    }
  }

  private class SvgBufferWriter extends SvgBaseWriter
  {
    protected SvgBufferWriter(String folder, String className, boolean withXhtml)
    {
      super(folder, className, new StringWriter(), withXhtml);
    }

    protected StringBuffer getBuffer()
    {
      return ((StringWriter) getFile()).getBuffer();
    }
  }

  private abstract class SvgBaseWriter extends AbstractWriter
  {
    private final String folder;
    private final String className;
    private final Writer file;
    protected final boolean withXhtml;
    private final StringBuffer tbuffer = new StringBuffer();
    private static final String PARENT_COLOUR = "grey";
    private static final String HEADER_COLOUR = "yellow";
    private static final String COMPOSITE_COLOUR = "lavender";
    private static final String FIELD_COLOUR = "lightsteelblue";
    private static final int WIDTH = 160;
    private static final int HALF_TEXT_HEIGHT = 4;
    private static final int PRIMARY_HEIGHT = 30;
    private static final int SECONDARY_HEIGHT = 20;
    private static final int ROW_HEIGHT = PRIMARY_HEIGHT + SECONDARY_HEIGHT;
    private static final int LINE_HEIGHT = 100;
    private int baseLine = 10;
    private int offsetNextLine = 0;
    private int fieldNumber = 1;
    private int maxWidth = 2;
    private int maxHeight = baseLine;

    protected SvgBaseWriter(String folder, String className, Writer file, boolean withXhtml)
    {
      this.folder = folder;
      this.className = className;
      this.file = file;
      this.withXhtml = withXhtml;
    }

    public String getFolder()
    {
      return folder;
    }

    public String getClassName()
    {
      return className;
    }

    protected void startDrawing() throws IOException
    {
      maxWidth = 2;
      baseLine = 10;
      maxHeight = baseLine;
      offsetNextLine = 0;
      fieldNumber = 1;
      tbuffer.setLength(0);
    }

    protected void endDrawing() throws IOException
    {
      int w = (maxWidth * WIDTH) + 10;
      int h = maxHeight + 10;

      String prefix = "";
      String postfix = "";

      if (withXhtml)
      {
        prefix = "<p>";
        postfix = "</p>";
      }

      file.append(addFileStatement(2, prefix + "<svg:svg version=\"1.1\" width=\"" + w + "px\" height=\"" + h + "px\">", false));
      file.append(tbuffer);
      file.append(addFileStatement(2, "</svg:svg>" + postfix, false));
    }

    protected void startNewLine()
    {
      baseLine += LINE_HEIGHT + (offsetNextLine * 16);
      offsetNextLine = 0;
      fieldNumber = 1;
    }

    protected void addTitle(int level, String section, String id, String text, boolean italic) throws IOException
    {
      if (null == id)
      {
        id = "";
      }
      else
      {
        id = " id=\"" + id + "\"";
      }

      if (italic)
      {
        text = "<i>" + text + "</i>";
      }
      file.append(addFileStatement(2, "<h" + level + id + ">" + section + text + "</h" + level + ">", false));
    }

    protected void addComment(List<String> cmts) throws IOException
    {
      if (includeDescriptions)
      {
        if (0 < cmts.size())
        {
          for (String str : cmts)
          {
            addComment(str);
          }
        }
      }
    }

    protected void addComment(String text) throws IOException
    {
      if (includeDescriptions)
      {
        List<String> strings = splitString(null, text);
        if (0 < strings.size())
        {
          for (String str : strings)
          {
            if (null != str)
            {
              file.append(addFileStatement(2, "<p>" + escape(str) + "</p>", false));
            }
          }
        }
      }
    }

    protected void addComment(String text, boolean override) throws IOException
    {
      if (override)
      {
        List<String> strings = splitString(null, text);
        if (0 < strings.size())
        {
          for (String str : strings)
          {
            if (null != str)
            {
              file.append(addFileStatement(2, "<p>" + escape(str) + "</p>", false));
            }
          }
        }
      }
    }

    protected void addFieldComment(String name, String text) throws IOException
    {
      if (includeDescriptions)
      {
        file.append(addFileStatement(2, "<p>", false));
        file.append(addFileStatement(3, "<h4>" + name + ":</h4>", false));
        file.append(addFileStatement(3, text, false));
        file.append(addFileStatement(2, "</p>", false));
      }
    }

    protected void addSpan(int index, int count, String text) throws IOException
    {
      int x1 = fieldNumber * WIDTH;
      int y1 = baseLine + PRIMARY_HEIGHT + SECONDARY_HEIGHT + (index * 16);
      int x2 = (fieldNumber + count) * WIDTH;
      int y2 = baseLine + PRIMARY_HEIGHT + SECONDARY_HEIGHT + 10 + (index * 16);
      int ymid = y1 + (y2 - y1) / 2;

      addLine(x1, y1, x1, y2);
      addLine(x2, y1, x2, y2);
      addLine(x1, ymid, x1 + SECONDARY_HEIGHT, ymid);
      addLine(x2, ymid, x2 - SECONDARY_HEIGHT, ymid);

      addText(x1, y1, x2 - x1, y2 - y1, text, null, false, false);

      if (offsetNextLine < index)
      {
        offsetNextLine = (index * 16);
      }

      setMaxHeight(y2);
    }

    protected void addSubSpan(int column, int row, int span, int nesting, String text) throws IOException
    {
      int x1 = column * WIDTH;
      int y1 = (row * (PRIMARY_HEIGHT + SECONDARY_HEIGHT)) + (nesting * 16);
      int x2 = (column + span) * WIDTH;
      int y2 = y1 + 10;
      int ymid = y1 + (y2 - y1) / 2;

      addLine(x1, y1, x1, y2);
      addLine(x2, y1, x2, y2);
      addLine(x1, ymid, x2, ymid);

      int len = text.length() * 8;
      addSubRectangle(x1 + ((x2 - x1) / 2 - (len / 2)), y1, len, y2 - y1, "white");
      addText(x1, y1, x2 - x1, 4, text, null, false, false);

      setMaxHeight(y2);
    }

    protected void addParent(String type, String linkTo) throws IOException
    {
      addRect(fieldNumber * WIDTH, baseLine, WIDTH, PRIMARY_HEIGHT, PARENT_COLOUR, "EXTENDS", null, false, false);
      addRect(fieldNumber * WIDTH, baseLine + PRIMARY_HEIGHT, WIDTH, SECONDARY_HEIGHT, PARENT_COLOUR, type, linkTo, true, false);

      ++fieldNumber;
    }

    protected void addField(String name, String type, String linkTo, boolean isAbstract, boolean isEnum) throws IOException
    {
      addRect(fieldNumber * WIDTH, baseLine, WIDTH, PRIMARY_HEIGHT, FIELD_COLOUR, name, null, false, false);
      addRect(fieldNumber * WIDTH, baseLine + PRIMARY_HEIGHT, WIDTH, SECONDARY_HEIGHT, FIELD_COLOUR, type, linkTo, isAbstract, isEnum);

      ++fieldNumber;

      if (maxWidth < fieldNumber)
      {
        maxWidth = fieldNumber;
      }
    }

    protected void addSubField(String name, String type, String linkTo, int xOff, int yOff, int spanX, int spanY, boolean isAbstract, boolean isEnum, boolean isComposite) throws IOException
    {
      String fieldTypeColour = FIELD_COLOUR;
      if (isComposite)
      {
        fieldTypeColour = COMPOSITE_COLOUR;
      }
      addSubRect(xOff * WIDTH, baseLine + (yOff * ROW_HEIGHT), WIDTH * spanX, PRIMARY_HEIGHT, HEADER_COLOUR, name, null, false, false);
      addSubRect(xOff * WIDTH, baseLine + PRIMARY_HEIGHT + (yOff * ROW_HEIGHT), WIDTH * spanX, (spanY * ROW_HEIGHT) - PRIMARY_HEIGHT, fieldTypeColour, type, linkTo, isAbstract, isEnum);

      ++fieldNumber;

      if (maxWidth < (fieldNumber + spanX))
      {
        maxWidth = (fieldNumber + spanX);
      }
    }

    protected void addLine(int x1, int y1, int x2, int y2) throws IOException
    {
      tbuffer.append(addFileStatement(3, "<svg:line x1=\"" + x1 + "\" y1=\"" + y1 + "\" x2=\"" + x2 + "\" y2=\"" + y2 + "\" stroke=\"navy\" stroke-width=\"1\"/>", false));
    }

    protected void addRect(int x, int y, int width, int height, String colour, String text, String linkTo, boolean italic, boolean bold) throws IOException
    {
      tbuffer.append(addFileStatement(3, "<svg:rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" fill=\"" + colour + "\" stroke=\"navy\" stroke-width=\"2\"/>", false));
      addText(x, y, width, height, text, linkTo, italic, bold);

      setMaxHeight(y + height);
    }

    protected void addSubRect(int x, int y, int width, int height, String colour, String text, String linkTo, boolean italic, boolean bold) throws IOException
    {
      tbuffer.append(addFileStatement(3, "<svg:rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" fill=\"" + colour + "\" stroke=\"navy\" stroke-width=\"2\"/>", false));
      addText(x, y, width, 20, text, linkTo, italic, bold);

      setMaxHeight(y + height);
    }

    protected void addSubRectangle(int x, int y, int width, int height, String colour) throws IOException
    {
      tbuffer.append(addFileStatement(3, "<svg:rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + width + "\" height=\"" + height + "\" fill=\"" + colour + "\" stroke=\"navy\" stroke-width=\"0\"/>", false));

      setMaxHeight(y + height);
    }

    protected void addText(int x, int y, int width, int height, String text, String linkTo, boolean italic, boolean bold) throws IOException
    {
      String styleStr = "";

      if (italic)
      {
        styleStr = " font-style=\"italic\"";
      }
      if (bold)
      {
        styleStr += " font-weight=\"bold\"";
      }
      if ((null != linkTo) && (0 < linkTo.length()))
      {
        text = "<svg:a xlink:href=\"" + linkTo + "\">" + text + "</svg:a>";
      }

      tbuffer.append(addFileStatement(3, "<svg:text x=\"" + (x + width / 2) + "\" y=\"" + (y + height / 2 + HALF_TEXT_HEIGHT) + "\" font-family=\"Verdana\" font-size=\"12\"" + styleStr + " fill=\"navy\" text-anchor=\"middle\" alignment-baseline=\"middle\">", false));
      tbuffer.append(addFileStatement(4, text, false));
      tbuffer.append(addFileStatement(3, "</svg:text>", false));
    }

    protected void addIndex(String title, int titleLevel, Set<Map.Entry<String, String>> entries) throws IOException
    {
      file.append(addFileStatement(2, "<div>", false));
      addTitle(titleLevel, title, null, "", false);
      for (Map.Entry<String, String> e : entries)
      {
        file.append(addFileStatement(2, "<p>" + "<a href=\"" + e.getValue() + "\">" + e.getKey() + "</a>" + "</p>", false));
      }
      file.append(addFileStatement(2, "</div>", false));
    }

    protected void appendBuffer(StringBuffer buf) throws IOException
    {
      file.append(buf);
    }

    @Override
    public void flush() throws IOException
    {
    }

    protected Writer getFile()
    {
      return file;
    }

    protected void setMaxHeight(int newMax)
    {
      if (maxHeight < newMax)
      {
        maxHeight = newMax;
      }
    }

    private String escape(String t)
    {
      if (null != t)
      {
        t = t.replaceAll("&", "&amp;");
        t = t.replaceAll("<", "&lt;");
        t = t.replaceAll(">", "&gt;");

        return t;
      }

      return "";
    }
  }
}
