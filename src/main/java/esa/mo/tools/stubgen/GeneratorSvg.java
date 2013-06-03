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

import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.writers.AbstractWriter;
import esa.mo.tools.stubgen.xsd.*;
import esa.mo.tools.stubgen.xsd.EnumerationType.Item;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.JAXBException;

/**
 * Generates an XHTML compliant file of the service specification in an ECSS PUS style. EXPERIMENTAL.
 */
public class GeneratorSvg extends GeneratorDocument
{
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
  public void compile(String destinationFolderName, SpecificationType spec) throws IOException, JAXBException
  {
    for (AreaType area : spec.getArea())
    {
      Map<String, String> indexMap = new TreeMap();

      if ((!area.getName().equalsIgnoreCase(StdStrings.COM)) || (generateCOM()))
      {
        SvgWriter svgFile = new SvgWriter(destinationFolderName, "output" + area.getName(), "xhtml");
        SvgBufferWriter svgBuff = new SvgBufferWriter();

        getLog().info("Processing area: " + area.getName());
        svgBuff.addTitle(1, "Specification: ", createId(null, null), area.getName(), false);
        svgBuff.addComment(area.getComment());

        // create services
        for (ServiceType service : area.getService())
        {
          svgBuff.addTitle(2, "Service: ", createId(service, null), service.getName(), false);
          svgBuff.addComment(service.getComment());

          Set<Map.Entry<String, String>> tocMap = new LinkedHashSet();
          SvgBufferWriter serviceBodyBuff = new SvgBufferWriter();

          if (service instanceof ExtendedServiceType)
          {
            ExtendedServiceType eService = (ExtendedServiceType) service;

            List<String> cmts = new LinkedList<String>();
            addCOMUsageComments(cmts, ComAspectEnum.ENTITY, eService);
            addCOMUsageComments(cmts, ComAspectEnum.DEFINITION, eService);
            addCOMUsageComments(cmts, ComAspectEnum.OCCURRENCE, eService);
            addCOMUsageComments(cmts, ComAspectEnum.STATUS, eService);

            serviceBodyBuff.addComment(cmts);
          }

          ServiceSummary summary = createOperationElementList(service);

          for (OperationSummary op : summary.getOperations())
          {
            serviceBodyBuff.addTitle(3, "Operation: ", createId(service, op.getName()), op.getName(), false);
            serviceBodyBuff.addComment(op.getOriginalOp().getComment());

            tocMap.add(new AbstractMap.SimpleEntry<String, String>(op.getName(), createXlink(null, service.getName(), op.getName())));

            drawOperationMessages(serviceBodyBuff, op);
          }

          if (!tocMap.isEmpty())
          {
            svgBuff.addIndex("Operations", tocMap);
            svgBuff.appendBuffer(serviceBodyBuff.getBuffer());
          }
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

        if (!indexMap.isEmpty())
        {
          svgBuff.addIndex("Index", indexMap.entrySet());
        }
        svgBuff.flush();

        svgFile.appendBuffer(svgBuff.getBuffer());
        svgFile.flush();
      }
    }
  }

  private static void addCOMUsageComments(List<String> cmts, ComAspectEnum aspect, ExtendedServiceType service) throws IOException
  {
    switch (aspect)
    {
      case ENTITY:
      {
//        getCOMIdentifier(cmts, aspect, service.getIncludesDefinition());
//        getCOMIdentifier(cmts, aspect, service.getIncludesOccurrence(), service.getIncludesDefinition());
//        getCOMIdentifier(cmts, aspect, service.getIncludesStatus(), service.getIncludesOccurrence(), service.getIncludesDefinition());
      }
      break;

      case DEFINITION:
      {
//        getCOMIdentifier(cmts, aspect, service.getIncludesDefinition());
//        getCOMIdentifier(cmts, aspect, service.getIncludesOccurrence(), service.getIncludesDefinition());
//        getCOMIdentifier(cmts, aspect, service.getIncludesStatus(), service.getIncludesOccurrence(), service.getIncludesDefinition());
      }
      break;

      case OCCURRENCE:
      {
//        getCOMIdentifier(cmts, aspect, service.getIncludesOccurrence());
//        getCOMIdentifier(cmts, aspect, service.getIncludesStatus(), service.getIncludesOccurrence());
      }
      break;

      case STATUS:
      {
//        getCOMIdentifier(cmts, aspect, service.getIncludesStatus());
      }
      break;
    }
  }

//  private static void getCOMIdentifier(List<String> cmts, ComAspectEnum aspect, ModelAspectType... eSets) throws IOException
//  {
//    for (int i = 0; i < eSets.length; i++)
//    {
//      ModelAspectType eSet = eSets[i];
//      if (null != eSet)
//      {
//        switch (aspect)
//        {
//          case ENTITY:
//          {
//            if (null != eSet.getEntityId())
//            {
//              if (0 == i)
//              {
//                cmts.add(eSet.getEntityId().getComment());
//              }
//              else
//              {
//                return;
//              }
//            }
//          }
//          break;
//          case DEFINITION:
//          {
//            if (null != eSet.getDefinitionId())
//            {
//              if (0 == i)
//              {
//                cmts.add(eSet.getDefinitionId().getComment());
//              }
//              else
//              {
//                return;
//              }
//            }
//          }
//          break;
//          case OCCURRENCE:
//          {
//            if (null != eSet.getOccurrenceId())
//            {
//              if (0 == i)
//              {
//                cmts.add(eSet.getOccurrenceId().getComment());
//              }
//              else
//              {
//                return;
//              }
//            }
//          }
//          break;
//          case STATUS:
//          {
//            if (null != eSet.getStatusId())
//            {
//              if (0 == i)
//              {
//                cmts.add(eSet.getStatusId().getComment());
//              }
//              else
//              {
//                return;
//              }
//            }
//          }
//          break;
//        }
//      }
//    }
//  }
  private void drawOperationMessages(SvgBaseWriter svgFile, OperationSummary op) throws IOException
  {
    switch (op.getPattern())
    {
      case SEND_OP:
      {
        svgFile.addTitle(4, "Telecommand application data:", null, "", false);
        drawOperationTypes(svgFile, op.getArgTypes(), op.getArgComment());
        break;
      }
      case SUBMIT_OP:
      {
        svgFile.addTitle(4, "Telecommand application data:", null, "", false);
        drawOperationTypes(svgFile, op.getArgTypes(), op.getArgComment());
        break;
      }
      case REQUEST_OP:
      {
        svgFile.addTitle(4, "Telecommand application data:", null, "", false);
        drawOperationTypes(svgFile, op.getArgTypes(), op.getArgComment());
        svgFile.addTitle(4, "Response telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getRetTypes(), op.getRetComment());
        break;
      }
      case INVOKE_OP:
      {
        svgFile.addTitle(4, "Telecommand application data:", null, "", false);
        drawOperationTypes(svgFile, op.getArgTypes(), op.getArgComment());
        svgFile.addTitle(4, "Acknowledgement telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getAckTypes(), op.getAckComment());
        svgFile.addTitle(4, "Response telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getRetTypes(), op.getRetComment());
        break;
      }
      case PROGRESS_OP:
      {
        svgFile.addTitle(4, "Telecommand application data:", null, "", false);
        drawOperationTypes(svgFile, op.getArgTypes(), op.getArgComment());
        svgFile.addTitle(4, "Acknowledgement telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getAckTypes(), op.getAckComment());
        svgFile.addTitle(4, "Progress telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getUpdateTypes(), op.getUpdateComment());
        svgFile.addTitle(4, "Response telemetry report application data:", null, "", false);
        drawOperationTypes(svgFile, op.getRetTypes(), op.getRetComment());
        break;
      }
      case PUBSUB_OP:
      {
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

  private void drawOperationTypes(SvgBaseWriter svgFile, List<TypeInfo> types, String comment) throws IOException
  {
    svgFile.startDrawing();
    for (TypeInfo e : types)
    {
      drawOperationPart(svgFile, e);
    }
    svgFile.endDrawing();

    svgFile.addComment(comment);
  }

  private void drawOperationPart(SvgBaseWriter svgFile, TypeInfo type) throws IOException
  {
    if (null != type)
    {
      if (type.getSourceType().isList())
      {
        svgFile.addField("N", StdStrings.INTEGER, createXlink(StdStrings.MAL, null, StdStrings.INTEGER), false, false);
        svgFile.addSpan(1, 1, "Repeated N times");
        svgFile.addField("Part", type.getSourceType().getName(), createXlink(type.getSourceType().getArea(), type.getSourceType().getService(), type.getSourceType().getName()), false, true);
      }
      else
      {
        svgFile.addField("Part", type.getActualMalType(), createXlink(type.getSourceType().getArea(), type.getSourceType().getService(), type.getActualMalType()), false, true);
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
          svgFile.addSpan(c, c, "Optional");
        }

        if (element.isList())
        {
          svgFile.addField("N", StdStrings.INTEGER, createXlink(StdStrings.MAL, null, StdStrings.INTEGER), false, false);
          svgFile.addSpan(1, 1, "Repeated N times");
        }
        svgFile.addField(element.getFieldName(), element.getTypeName(), createXlink(element.getEncodeCall(), element.getDecodeCall(), element.getTypeName()), isAbstract(element.getTypeName()), isEnum(element.getTypeName()));
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

    if ((null != areaName) && (0 < areaName.length()))
    {
      return "output" + areaName + ".xhtml#" + serviceName + section;
    }
    else
    {
      return "#" + serviceName + section;
    }
  }

  private class SvgWriter extends SvgBaseWriter
  {
    protected SvgWriter(String folder, String className, String ext) throws IOException
    {
      super(StubUtils.createLowLevelWriter(folder, className, ext));

      getLog().info("Creating file " + folder + " " + className + "." + ext);

      getFile().append(addFileStatement(0, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>", false));
      getFile().append(addFileStatement(0, "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">", false));
      getFile().append(addFileStatement(0, "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">", false));
      getFile().append(addFileStatement(1, "<head>", false));
      getFile().append(addFileStatement(2, "<title></title>", false));
      getFile().append(addFileStatement(1, "</head>", false));
      getFile().append(addFileStatement(1, "<body>", false));
    }

    @Override
    public void flush() throws IOException
    {
      getFile().append(addFileStatement(1, "</body>", false));
      getFile().append(addFileStatement(0, "</html>", false));
      getFile().flush();
    }
  }

  private class SvgBufferWriter extends SvgBaseWriter
  {
    protected SvgBufferWriter()
    {
      super(new StringWriter());
    }

    protected StringBuffer getBuffer()
    {
      return ((StringWriter) getFile()).getBuffer();
    }
  }

  private abstract class SvgBaseWriter extends AbstractWriter
  {
    private final Writer file;
    private final StringBuffer tbuffer = new StringBuffer();
    private static final String PARENT_COLOUR = "grey";
    private static final String FIELD_COLOUR = "yellow";
    private static final int WIDTH = 160;
    private static final int HALF_TEXT_HEIGHT = 4;
    private static final int PRIMARY_HEIGHT = 30;
    private static final int SECONDARY_HEIGHT = 20;
    private static final int LINE_HEIGHT = 100;
    private int baseLine = 10;
    private int offsetNextLine = 0;
    private int fieldNumber = 1;
    private int maxWidth = 2;
    private int maxHeight = baseLine;

    protected SvgBaseWriter(Writer file)
    {
      this.file = file;
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
      file.append(addFileStatement(2, "<svg:svg version=\"1.1\" width=\"" + w + "px\" height=\"" + h + "px\">", false));
      file.append(tbuffer);
      file.append(addFileStatement(2, "</svg:svg>", false));
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
      if (0 < cmts.size())
      {
        for (String str : cmts)
        {
          addComment(str);
        }
      }
    }

    protected void addComment(String text) throws IOException
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

    protected void addFieldComment(String name, String text) throws IOException
    {
      file.append(addFileStatement(2, "<p>", false));
      file.append(addFileStatement(3, "<h4>" + name + ":</h4>", false));
      file.append(addFileStatement(3, text, false));
      file.append(addFileStatement(2, "</p>", false));
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

    protected void addIndex(String title, Set<Map.Entry<String, String>> entries) throws IOException
    {
      file.append(addFileStatement(2, "<div>", false));
      addTitle(1, title, null, "", false);
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
