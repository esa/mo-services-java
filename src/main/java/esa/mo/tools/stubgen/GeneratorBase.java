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
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.NativeTypeDetails;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeInformation;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.TargetWriter;
import esa.mo.tools.stubgen.xsd.*;
import java.io.*;
import java.util.*;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.logging.Log;

/**
 * This class provides the generators with the basic type processing required.
 */
public abstract class GeneratorBase implements Generator, TypeInformation
{
  /**
   * The configuration of the generator.
   */
  private final GeneratorConfiguration config;
  private final Set<String> enumTypesSet = new TreeSet();
  private final Set<String> abstractTypesSet = new TreeSet();
  private final Map<String, Object> allTypesMap = new TreeMap<String, Object>();
  private final Map<String, CompositeType> compositeTypesMap = new TreeMap<String, CompositeType>();
  private final Map<String, AttributeTypeDetails> attributeTypesMap = new TreeMap();
  private final Map<String, NativeTypeDetails> nativeTypesMap = new TreeMap<String, NativeTypeDetails>();
  private final Map<String, ErrorDefinitionType> errorDefinitionMap = new TreeMap();
  private final Map<String, String> jaxbBindings = new TreeMap<String, String>();
  private final Map<Integer, List<OperationType>> standardCapabilityMap = new TreeMap<Integer, List<OperationType>>();
  private final Log logger;
  private boolean generateCOM;

  /**
   * Constructor.
   *
   * @param logger The logger.
   * @param config The configuration to use.
   */
  protected GeneratorBase(Log logger, GeneratorConfiguration config)
  {
    this.logger = logger;
    this.config = config;
  }

  @Override
  public void init(String destinationFolderName,
          boolean generateStructures,
          boolean generateCOM,
          Map<String, String> extraProperties) throws IOException
  {
    this.generateCOM = generateCOM;
  }

  @Override
  public void setJaxbBindings(Map<String, String> jaxbBindings)
  {
    if (null != jaxbBindings)
    {
      for (Map.Entry<String, String> entry : jaxbBindings.entrySet())
      {
        String pack = entry.getKey();
        String uri = entry.getValue();

        String[] uris = uri.split(",");

        for (String string : uris)
        {
          this.jaxbBindings.put(string, pack);
        }
      }
    }
  }

  @Override
  public void preProcess(SpecificationType spec) throws IOException, JAXBException
  {
    // load in types and error definitions
    for (AreaType area : spec.getArea())
    {
      if (null != area.getDataTypes())
      {
        loadTypesFromObjectList(area.getDataTypes().getFundamentalOrAttributeOrComposite());
      }

      if ((null != area.getErrors()) && (null != area.getErrors().getError()))
      {
        for (ErrorDefinitionType error : area.getErrors().getError())
        {
          errorDefinitionMap.put(error.getName(), error);
        }
      }

      for (ServiceType service : area.getService())
      {
        if (null != service.getDataTypes())
        {
          loadTypesFromObjectList(service.getDataTypes().getCompositeOrEnumeration());
        }

        if ((null != service.getErrors()) && (null != service.getErrors().getError()))
        {
          for (ErrorDefinitionType error : service.getErrors().getError())
          {
            errorDefinitionMap.put(error.getName(), error);
          }
        }
      }
    }

    // now load COM operations
    for (AreaType area : spec.getArea())
    {
      if (StdStrings.COM.equals(area.getName()))
      {
        for (ServiceType service : area.getService())
        {
          if (StdStrings.COM.equals(service.getName()))
          {
            for (CapabilitySetType cap : service.getCapabilitySet())
            {
              standardCapabilityMap.put(cap.getNumber(), cap.getSendIPOrSubmitIPOrRequestIP());
            }

            break;
          }
        }

        break;
      }
    }
  }

  @Override
  public void close(String destinationFolderName) throws IOException
  {
  }

  @Override
  public String getBasePackage()
  {
    return config.getBasePackage();
  }

  /**
   * Returns true is the COM operations should be generated.
   *
   * @return true if COM required.
   */
  public boolean generateCOM()
  {
    return generateCOM;
  }

  @Override
  public boolean isAbstract(TypeReference type)
  {
    return isAbstract(type.getName());
  }

  /**
   * Returns true if the type is abstract.
   *
   * @param type the type to look for.
   * @return true if abstract.
   */
  public boolean isAbstract(String type)
  {
    boolean abstractType = false;
    if (abstractTypesSet.contains(type))
    {
      abstractType = true;
    }
    return abstractType;
  }

  @Override
  public boolean isEnum(TypeReference type)
  {
    boolean enumType = false;
    if (enumTypesSet.contains(type.getName()))
    {
      enumType = true;
    }
    return enumType;
  }

  /**
   * Returns true if the type is an enumeration.
   *
   * @param type the type to look for.
   * @return true if an enumeration.
   */
  public boolean isEnum(String type)
  {
    boolean enumType = false;
    if (enumTypesSet.contains(type))
    {
      enumType = true;
    }
    return enumType;
  }

  /**
   * Returns enumeration details if enumeration type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public EnumerationType getEnum(String type)
  {
    if (enumTypesSet.contains(type))
    {
      return (EnumerationType) allTypesMap.get(type);
    }
    return null;
  }

  @Override
  public boolean isAttributeType(TypeReference type)
  {
    return (null != type) && isAttributeType(type.getArea(), type.getName());
  }

  /**
   * Returns true if the type is an attribute.
   *
   * @param area the type area, must be MAL.
   * @param type the type to look for.
   * @return true if an attribute.
   */
  public boolean isAttributeType(String area, String type)
  {
    return (StdStrings.MAL.equalsIgnoreCase(area)) && (attributeTypesMap.containsKey(type));
  }

  /**
   * Returns attribute details if attribute type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public AttributeTypeDetails getAttributeDetails(TypeReference type)
  {
    if (null != type)
    {
      return attributeTypesMap.get(type.getName());
    }
    return null;
  }

  /**
   * Returns attribute details if attribute type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public AttributeTypeDetails getAttributeDetails(String type)
  {
    if (null != type)
    {
      return attributeTypesMap.get(type);
    }
    return null;
  }

  /**
   * Returns true if the type is a native type.
   *
   * @param type the type to look for.
   * @return true if native.
   */
  public boolean isNativeType(String type)
  {
    if (type.contains("<"))
    {
      type = type.substring(0, type.indexOf('<'));
    }

    return nativeTypesMap.containsKey(type);
  }

  @Override
  public boolean isNativeType(TypeReference type)
  {
    boolean attrType = isAttributeType(type);
    if ((attrType) && (attributeTypesMap.get(type.getName()).isNativeType()))
    {
      return true;
    }
    return false;
  }

  /**
   * Returns native details if native type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public NativeTypeDetails getNativeType(String type)
  {
    if (type.contains("<"))
    {
      type = type.substring(0, type.indexOf('<'));
    }
    NativeTypeDetails rType = nativeTypesMap.get(type);
    if (null == rType)
    {
      rType = new NativeTypeDetails("<Unknown native type of " + type + ">", false, false, null);
    }
    return rType;
  }

  /**
   * Returns true if the type is a composite.
   *
   * @param type the type to look for.
   * @return true if a composite.
   */
  public boolean isComposite(TypeReference type)
  {
    boolean compType = false;
    if (compositeTypesMap.containsKey(type.getName()))
    {
      compType = true;
    }
    return compType;
  }

  /**
   * Returns true if the type is a composite.
   *
   * @param type the type to look for.
   * @return true if a composite.
   */
  public boolean isComposite(String type)
  {
    boolean compType = false;
    if (compositeTypesMap.containsKey(type))
    {
      compType = true;
    }
    return compType;
  }

  /**
   * Returns composite details if composite type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public CompositeType getCompositeDetails(TypeReference type)
  {
    if (null != type)
    {
      return compositeTypesMap.get(type.getName());
    }
    return null;
  }

  /**
   * Returns composite details if composite type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public CompositeType getCompositeDetails(String type)
  {
    if (null != type)
    {
      return compositeTypesMap.get(type);
    }
    return null;
  }

  /**
   * Returns error details if defined.
   *
   * @param type The error to look for.
   * @return the details if found, otherwise null.
   */
  public ErrorDefinitionType getErrorDefinition(String error)
  {
    if (errorDefinitionMap.containsKey(error))
    {
      return errorDefinitionMap.get(error);
    }
    return null;
  }

  @Override
  public String createElementType(TargetWriter file, TypeReference type)
  {
    if (null != type)
    {
      return createElementType(file, type.getArea(), type.getService(), type.getName());
    }
    return null;
  }

  @Override
  public String createElementType(TargetWriter file, String areaName, String serviceName, String typeName)
  {
    return createElementType(file, areaName, serviceName, config.getStructureFolder(), typeName);
  }

  /**
   * Creates the full name of a structure type from the supplied details.
   *
   * @param file The writer to add any type dependencies to.
   * @param area The area of the type.
   * @param service The service of the type, may be null.
   * @param type The type.
   * @return the full name of the type.
   */
  public String createElementType(TargetWriter file, AreaType area, ServiceType service, String type)
  {
    String areaName = null;
    String serviceName = null;

    if (null != area)
    {
      areaName = area.getName();
    }
    if (null != service)
    {
      serviceName = service.getName();
    }

    return createElementType(file, areaName, serviceName, config.getStructureFolder(), type);
  }

  /**
   * Creates the full name of a type from the supplied details.
   *
   * @param file The writer to add any type dependencies to.
   * @param area The area of the type.
   * @param service The service of the type, may be null.
   * @param extraPackageLevel String to insert after the area/service before the type name.
   * @param type The type.
   * @return the full name of the type.
   */
  public String createElementType(TargetWriter file, String area, String service, String extraPackageLevel, String type)
  {
    String retVal = "";

    if (StdStrings.XML.equals(area))
    {
      retVal = jaxbBindings.get(service) + config.getNamingSeparator() + type;
    }
    else
    {
      if (isAttributeType(area, type))
      {
        AttributeTypeDetails details = attributeTypesMap.get(type);
        retVal = details.getTargetType();
      }
      else
      {
        retVal += config.getBasePackage();

        if (null != area)
        {
          retVal += area.toLowerCase() + config.getNamingSeparator();
        }

        if (null != service)
        {
          retVal += service.toLowerCase() + config.getNamingSeparator();
        }

        if ((null != extraPackageLevel) && (0 < extraPackageLevel.length()))
        {
          retVal += extraPackageLevel + config.getNamingSeparator();
        }

        retVal += type;
      }
    }

    if (null != file)
    {
      file.addTypeDependency(retVal);
      retVal = convertClassName(retVal);
    }

    return convertToNamespace(retVal);
  }

  @Override
  public String convertToNamespace(String targetType)
  {
    return targetType;
  }

  @Override
  public String convertClassName(String call)
  {
    return call;
  }

  /**
   * To be used by derived generators to add an entry to the attribute type details map.
   *
   * @param name The name of the type.
   * @param details The new details.
   */
  protected void addAttributeType(String name, AttributeTypeDetails details)
  {
    attributeTypesMap.put(name, details);
  }

  /**
   * To be used by derived generators to add an entry to the native type details map.
   *
   * @param name The name of the type.
   * @param details The new details.
   */
  protected void addNativeType(String name, NativeTypeDetails details)
  {
    nativeTypesMap.put(name, details);
  }

  /**
   * Creates a list of composite element details for each field of the composite, not including those of its super type.
   *
   * @param file Writer to add any type dependencies to.
   * @param composite the composite to inspect.
   * @return a list of the element details.
   */
  protected List<CompositeField> createCompositeElementsList(TargetWriter file, CompositeType composite)
  {
    List<CompositeField> lst = new LinkedList<CompositeField>();
    for (NamedElementReferenceWithCommentType element : composite.getField())
    {
      CompositeField ele = createCompositeElementsDetails(file,
              element.getName(),
              element.getType(),
              element.isCanBeNull(),
              element.getComment());
      lst.add(ele);
    }
    return lst;
  }

  /**
   * Creates a list of composite element details for each field of the composite, including those of its super type.
   *
   * @param file Writer to add any type dependencies to.
   * @param composite the composite to inspect.
   * @param lst a list of the element details to populate.
   */
  protected void createCompositeSuperElementsList(TargetWriter file, String composite, List<CompositeField> lst)
  {
    if ((null != composite) && (!StdStrings.COMPOSITE.equals(composite)))
    {
      CompositeType type = compositeTypesMap.get(composite);
      if (null != type)
      {
        // first looks for super types of this one and add their details
        if ((null != type.getExtends()) && (!StdStrings.COMPOSITE.equals(type.getExtends().getType().getName())))
        {
          createCompositeSuperElementsList(file, type.getExtends().getType().getName(), lst);
        }

        // now add the details of this type
        for (NamedElementReferenceWithCommentType element : type.getField())
        {
          CompositeField ele = createCompositeElementsDetails(file,
                  element.getName(),
                  element.getType(),
                  element.isCanBeNull(),
                  element.getComment());
          lst.add(ele);
        }
      }
      else
      {
        throw new IllegalStateException("Unknown super type of (" + composite + ") for composite");
      }
    }
  }

  /**
   * Returns the super type of a composite.
   *
   * @param typeName the composite to look for.
   * @return The super type of the composite or null if extends fundamental type Composite.
   */
  protected TypeReference getCompositeElementSuperType(String typeName)
  {
    if ((null != typeName) && (!StdStrings.COMPOSITE.equals(typeName)))
    {
      CompositeType theType = compositeTypesMap.get(typeName);

      if ((null != theType) && (null != theType.getExtends())
              && (!StdStrings.COMPOSITE.equals(theType.getExtends().getType().getName())))
      {
        return theType.getExtends().getType();
      }
    }

    return null;
  }

  /**
   * Creates a summary of the operations of a service.
   *
   * @param service The service to convert.
   * @return the operation summary.
   */
  protected ServiceSummary createOperationElementList(ServiceType service)
  {
    ServiceSummary summary = new ServiceSummary(StdStrings.COM.equalsIgnoreCase(service.getName()));

    // only load operations if this is not the COM service
    if (!summary.isComService())
    {
      for (CapabilitySetType capabilitySet : service.getCapabilitySet())
      {
        for (OperationType op : capabilitySet.getSendIPOrSubmitIPOrRequestIP())
        {
          createOperationSummary(op, capabilitySet.getNumber(), null, summary);
        }
      }
    }
    return summary;
  }

  /**
   * Returns the logger.
   *
   * @return the logger.
   */
  protected Log getLog()
  {
    return logger;
  }

  private OperationSummary createOperationSummary(OperationType op,
          int capNum,
          Map<String, TypeReference> comTypeSubs,
          ServiceSummary summary)
  {
    OperationSummary ele = null;
    if (op instanceof SendOperationType)
    {
      SendOperationType lop = (SendOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.SEND_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSend().getAny(), comTypeSubs)), lop.getMessages().getSend().getComment(),
              null, "",
              null, "",
              null, "");
    }
    else if (op instanceof SubmitOperationType)
    {
      SubmitOperationType lop = (SubmitOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.SUBMIT_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSubmit().getAny(), comTypeSubs)), lop.getMessages().getSubmit().getComment(),
              null, "",
              null, "",
              null, "");
    }
    else if (op instanceof RequestOperationType)
    {
      RequestOperationType lop = (RequestOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.REQUEST_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getRequest().getAny(), comTypeSubs)), lop.getMessages().getRequest().getComment(),
              null, "",
              null, "",
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny(), comTypeSubs)), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof InvokeOperationType)
    {
      InvokeOperationType lop = (InvokeOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.INVOKE_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getInvoke().getAny(), comTypeSubs)), lop.getMessages().getInvoke().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny(), comTypeSubs)), lop.getMessages().getAcknowledgement().getComment(),
              null, "",
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny(), comTypeSubs)), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof ProgressOperationType)
    {
      ProgressOperationType lop = (ProgressOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.PROGRESS_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getProgress().getAny(), comTypeSubs)), lop.getMessages().getProgress().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny(), comTypeSubs)), lop.getMessages().getAcknowledgement().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getUpdate().getAny(), comTypeSubs)), lop.getMessages().getUpdate().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny(), comTypeSubs)), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof PubSubOperationType)
    {
      PubSubOperationType lop = (PubSubOperationType) op;
      List<TypeReference> ty = TypeUtils.getTypeListViaXSDAny(lop.getMessages().getPublishNotify().getAny(), comTypeSubs);
      List<TypeInfo> riList = TypeUtils.convertTypeReferences(this, ty);

      ele = new OperationSummary(InteractionPatternEnum.PUBSUB_OP, op, capNum,
              null, "",
              null, "",
              riList, "",
              riList, lop.getMessages().getPublishNotify().getComment());
    }

    if (null != summary)
    {
      summary.getOperations().add(ele);
    }

    return ele;
  }

  private void loadTypesFromObjectList(List<Object> typeList)
  {
    for (Object object : typeList)
    {
      if (object instanceof EnumerationType)
      {
        allTypesMap.put(((EnumerationType) object).getName(), object);
        enumTypesSet.add(((EnumerationType) object).getName());
      }
      else if (object instanceof FundamentalType)
      {
        allTypesMap.put(((FundamentalType) object).getName(), object);
        abstractTypesSet.add(((FundamentalType) object).getName());
      }
      else if (object instanceof AttributeType)
      {
        allTypesMap.put(((AttributeType) object).getName(), object);
      }
      else if (object instanceof CompositeType)
      {
        CompositeType c = (CompositeType) object;
        allTypesMap.put(c.getName(), object);
        compositeTypesMap.put(c.getName(), c);
        if (null == ((CompositeType) object).getShortFormPart())
        {
          abstractTypesSet.add(c.getName());
        }
      }
    }
  }

  /**
   * Creates a composite element detail object for a field of a composite.
   *
   * @param file Writer to add any type dependencies to.
   * @param fieldName The field name in the composite.
   * @param elementType the type of the field.
   * @param canBeNull True if the field is allowed to be null.
   * @param comment The comment with the field.
   * @return the element details.
   */
  protected abstract CompositeField createCompositeElementsDetails(TargetWriter file,
          String fieldName,
          TypeReference elementType,
          boolean canBeNull,
          String comment);

  /**
   * @return the config
   */
  public GeneratorConfiguration getConfig()
  {
    return config;
  }
}
