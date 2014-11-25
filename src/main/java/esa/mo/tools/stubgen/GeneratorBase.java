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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.logging.Log;
import w3c.xsd.ComplexType;
import w3c.xsd.NoFixedFacet;
import w3c.xsd.OpenAttrs;
import w3c.xsd.Schema;
import w3c.xsd.SimpleType;

/**
 * This class provides the generators with the basic type processing required.
 */
public abstract class GeneratorBase implements Generator, TypeInformation
{
  /**
   * The configuration of the generator.
   */
  private final GeneratorConfiguration config;
  private final Set<TypeKey> enumTypesSet = new TreeSet<TypeKey>();
  private final Set<TypeKey> abstractTypesSet = new TreeSet<TypeKey>();
  private final Map<TypeKey, Object> allTypesMap = new TreeMap<TypeKey, Object>();
  private final Map<TypeKey, CompositeType> compositeTypesMap = new TreeMap<TypeKey, CompositeType>();
  private final Map<TypeKey, AttributeTypeDetails> attributeTypesMap = new TreeMap<TypeKey, AttributeTypeDetails>();
  private final Map<String, NativeTypeDetails> nativeTypesMap = new TreeMap<String, NativeTypeDetails>();
  private final Map<String, ErrorDefinitionType> errorDefinitionMap = new TreeMap<String, ErrorDefinitionType>();
  private final Map<String, String> jaxbBindings = new TreeMap<String, String>();
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
  public void postinit(String destinationFolderName,
          boolean generateStructures,
          boolean generateCOM,
          Map<String, String> extraProperties) throws IOException
  {
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
        loadTypesFromObjectList(area.getName(), null, area.getDataTypes().getFundamentalOrAttributeOrComposite());
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
          loadTypesFromObjectList(area.getName(), service.getName(), service.getDataTypes().getCompositeOrEnumeration());
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
  }

  @Override
  public void preProcess(Schema spec) throws IOException, JAXBException
  {
    // load in types
    if (null != spec.getSimpleTypeOrComplexTypeOrGroup())
    {
      loadTypesFromXsdList(StdStrings.XML, spec.getTargetNamespace(), spec.getSimpleTypeOrComplexTypeOrGroup());
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
    return abstractTypesSet.contains(new TypeKey(type));
  }

  @Override
  public boolean isEnum(TypeReference type)
  {
    return enumTypesSet.contains(new TypeKey(type));
  }

  /**
   * Returns enumeration details if enumeration type.
   *
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public EnumerationType getEnum(TypeReference type)
  {
    if (isEnum(type))
    {
      return (EnumerationType) allTypesMap.get(new TypeKey(type));
    }

    return null;
  }

  @Override
  public boolean isAttributeType(TypeReference type)
  {
    return (null != type) && attributeTypesMap.containsKey(new TypeKey(type));
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
      return attributeTypesMap.get(new TypeKey(type));
    }
    return null;
  }

  /**
   * Returns attribute details if attribute type.
   *
   * @param area the type area.
   * @param type The type to look for.
   * @return the details if found, otherwise null.
   */
  public AttributeTypeDetails getAttributeDetails(String area, String type)
  {
    if (null != type)
    {
      return attributeTypesMap.get(new TypeKey(area, null, type));
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
  public boolean isAttributeNativeType(TypeReference type)
  {
    return isAttributeType(type) && (getAttributeDetails(type).isNativeType());
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
   * Returns true if the type definition has been loaded.
   *
   * @param type the type to look for.
   * @return true if a known type.
   */
  public boolean isKnownType(TypeReference type)
  {
    boolean knownType = false;
    if (allTypesMap.containsKey(new TypeKey(type)))
    {
      knownType = true;
    }
    return knownType;
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
    if (compositeTypesMap.containsKey(new TypeKey(type)))
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
      return compositeTypesMap.get(new TypeKey(type));
    }
    return null;
  }

  /**
   * Returns error details if defined.
   *
   * @param error The error to look for.
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
  public String createElementType(TargetWriter file, TypeReference type, boolean isStructure)
  {
    if (null != type)
    {
      return createElementType(file, type.getArea(), type.getService(), isStructure ? config.getStructureFolder() : null, type.getName());
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

    if (null == area)
    {
      return type;
    }
    else
    {
      if (isAttributeType(TypeUtils.createTypeReference(area, service, type, false)))
      {
        AttributeTypeDetails details = getAttributeDetails(area, type);
        retVal = details.getTargetType();
      }
      else
      {
        if (StdStrings.XML.equals(area))
        {
          retVal = jaxbBindings.get(service) + config.getNamingSeparator() + StubUtils.preCap(type);
        }
        else
        {
          retVal += config.getBasePackage() + area.toLowerCase() + config.getNamingSeparator();

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
   * @param area The area of the type.
   * @param name The name of the type.
   * @param details The new details.
   */
  protected void addAttributeType(final String area, final String name, AttributeTypeDetails details)
  {
    attributeTypesMap.put(new TypeKey(area, null, name), details);
  }

  /**
   * To be used by derived generators to add an entry to the attribute type details map.
   *
   * @param area The area of the type.
   * @param name The name of the type.
   * @param isNativeType True if native type.
   * @param targetType The type to generate too.
   * @param defaultValue An example of a default value.
   */
  protected void addAttributeType(final String area, final String name,
          final boolean isNativeType,
          final String targetType,
          final String defaultValue)
  {
    attributeTypesMap.put(new TypeKey(area, null, name), new AttributeTypeDetails(this, name, isNativeType, targetType, defaultValue));
  }

  protected Map<TypeKey, AttributeTypeDetails> getAttributeTypesMap()
  {
    return attributeTypesMap;
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
              true,
              element.getName(),
              element.getType(),
              true,
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
   * @param type the composite to inspect.
   * @param lst a list of the element details to populate.
   */
  protected void createCompositeSuperElementsList(TargetWriter file, TypeReference type, List<CompositeField> lst)
  {
    if ((null != type) && (!StdStrings.COMPOSITE.equals(type.getName())))
    {
      CompositeType theType = compositeTypesMap.get(new TypeKey(type));
      if (null != theType)
      {
        // first looks for super types of this one and add their details
        if ((null != theType.getExtends()) && (!StdStrings.COMPOSITE.equals(theType.getExtends().getType().getName())))
        {
          createCompositeSuperElementsList(file, theType.getExtends().getType(), lst);
        }

        // now add the details of this type
        for (NamedElementReferenceWithCommentType element : theType.getField())
        {
          CompositeField ele = createCompositeElementsDetails(file,
                  true,
                  element.getName(),
                  element.getType(),
                  true,
                  element.isCanBeNull(),
                  element.getComment());
          lst.add(ele);
        }
      }
      else
      {
        throw new IllegalStateException("Unknown super type of (" + type.getName() + ") for composite");
      }
    }
  }

  /**
   * Returns the super type of a composite.
   *
   * @param type the composite to look for.
   * @return The super type of the composite or null if extends fundamental type Composite.
   */
  protected TypeReference getCompositeElementSuperType(TypeReference type)
  {
    if ((null != type) && (!StdStrings.COMPOSITE.equals(type.getName())))
    {
      CompositeType theType = compositeTypesMap.get(new TypeKey(type));

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
    ServiceSummary summary = new ServiceSummary(service, StdStrings.COM.equalsIgnoreCase(service.getName()));

    // only load operations if this is not the COM service
    if (!summary.isComService())
    {
      for (CapabilitySetType capabilitySet : service.getCapabilitySet())
      {
        for (OperationType op : capabilitySet.getSendIPOrSubmitIPOrRequestIP())
        {
          createOperationSummary(op, capabilitySet.getNumber(), summary);
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
          ServiceSummary summary)
  {
    OperationSummary ele = null;
    if (op instanceof SendOperationType)
    {
      SendOperationType lop = (SendOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.SEND_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSend().getAny())), lop.getMessages().getSend().getComment(),
              null, "",
              null, "",
              null, "");
    }
    else if (op instanceof SubmitOperationType)
    {
      SubmitOperationType lop = (SubmitOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.SUBMIT_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getSubmit().getAny())), lop.getMessages().getSubmit().getComment(),
              null, "",
              null, "",
              null, "");
    }
    else if (op instanceof RequestOperationType)
    {
      RequestOperationType lop = (RequestOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.REQUEST_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getRequest().getAny())), lop.getMessages().getRequest().getComment(),
              null, "",
              null, "",
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny())), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof InvokeOperationType)
    {
      InvokeOperationType lop = (InvokeOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.INVOKE_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getInvoke().getAny())), lop.getMessages().getInvoke().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny())), lop.getMessages().getAcknowledgement().getComment(),
              null, "",
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny())), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof ProgressOperationType)
    {
      ProgressOperationType lop = (ProgressOperationType) op;
      ele = new OperationSummary(InteractionPatternEnum.PROGRESS_OP, op, capNum,
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getProgress().getAny())), lop.getMessages().getProgress().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getAcknowledgement().getAny())), lop.getMessages().getAcknowledgement().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getUpdate().getAny())), lop.getMessages().getUpdate().getComment(),
              TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getResponse().getAny())), lop.getMessages().getResponse().getComment());
    }
    else if (op instanceof PubSubOperationType)
    {
      PubSubOperationType lop = (PubSubOperationType) op;
      List<TypeInfo> riList = TypeUtils.convertTypeReferences(this, TypeUtils.getTypeListViaXSDAny(lop.getMessages().getPublishNotify().getAny()));

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

  private void loadTypesFromObjectList(String area, String service, List<Object> typeList)
  {
    for (Object object : typeList)
    {
      if (object instanceof EnumerationType)
      {
        EnumerationType ty = (EnumerationType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        allTypesMap.put(key, object);
        enumTypesSet.add(key);
      }
      else if (object instanceof FundamentalType)
      {
        FundamentalType ty = (FundamentalType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        allTypesMap.put(key, object);
        abstractTypesSet.add(key);
      }
      else if (object instanceof AttributeType)
      {
        AttributeType ty = (AttributeType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        allTypesMap.put(key, object);
      }
      else if (object instanceof CompositeType)
      {
        CompositeType ty = (CompositeType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        allTypesMap.put(key, object);
        compositeTypesMap.put(key, ty);
        if (null == ((CompositeType) object).getShortFormPart())
        {
          abstractTypesSet.add(key);
        }
      }
    }
  }

  private void loadTypesFromXsdList(String area, String service, List<OpenAttrs> typeList)
  {
    for (OpenAttrs object : typeList)
    {
      if (object instanceof SimpleType)
      {
        SimpleType ty = (SimpleType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        if (null != ty.getRestriction())
        {
          for (Object o : ty.getRestriction().getFacetOrAny())
          {
            if ("enumeration".equalsIgnoreCase(((JAXBElement) o).getName().getLocalPart()))
            {
              EnumerationType e = new EnumerationType();
              EnumerationType.Item i = new EnumerationType.Item();
              i.setValue(((NoFixedFacet)((JAXBElement) o).getValue()).getValue());
              e.getItem().add(i);
              allTypesMap.put(key, e);
              enumTypesSet.add(key);
              break;
            }
          }
        }
        else
        {
          // ignore unexpected type, maybe warn in future?
        }
      }
      else if (object instanceof ComplexType)
      {
        ComplexType ty = (ComplexType) object;
        TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
        allTypesMap.put(key, object);
        compositeTypesMap.put(key, new CompositeType());
        if (ty.isAbstract())
        {
          abstractTypesSet.add(key);
        }
      }
    }
  }

  public static final class TypeKey implements Comparable<TypeKey>
  {
    private final String key;

    public TypeKey(TypeReference type)
    {
      this.key = createTypeKey(type);
    }

    public TypeKey(String area, String service, String name)
    {
      this.key = createTypeKey(TypeUtils.createTypeReference(area, service, name, false));
    }

    @Override
    public int hashCode()
    {
      int hash = 5;
      hash = 59 * hash + (this.key != null ? this.key.hashCode() : 0);
      return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (obj == null)
      {
        return false;
      }
      if (getClass() != obj.getClass())
      {
        return false;
      }
      final TypeKey other = (TypeKey) obj;

      return !((this.key == null) ? (other.key != null) : !this.key.equals(other.key));
    }

    public int compareTo(TypeKey o)
    {
      return this.key.compareTo(o.key);
    }

    @Override
    public String toString()
    {
      return "TypeKey{" + key + '}';
    }

    private static String createTypeKey(TypeReference type)
    {
      StringBuilder buf = new StringBuilder();
      buf.append(type.getArea());
      if (null != type.getService())
      {
        buf.append(':');
        buf.append(type.getService());
        buf.append(':');
      }
      else
      {
        buf.append(":_:");
      }
      buf.append(type.getName());

      return buf.toString();
    }
  }

  /**
   * Creates a composite element detail object for a field of a composite.
   *
   * @param file Writer to add any type dependencies to.
   * @param checkType True if the type of the field should be checked for validity.
   * @param fieldName The field name in the composite.
   * @param elementType the type of the field.
   * @param isStructure True if field is a structure.
   * @param canBeNull True if the field is allowed to be null.
   * @param comment The comment with the field.
   * @return the element details.
   */
  protected abstract CompositeField createCompositeElementsDetails(TargetWriter file,
          boolean checkType,
          String fieldName,
          TypeReference elementType,
          boolean isStructure,
          boolean canBeNull,
          String comment);

  /**
   * @return the configuration.
   */
  public GeneratorConfiguration getConfig()
  {
    return config;
  }
}
