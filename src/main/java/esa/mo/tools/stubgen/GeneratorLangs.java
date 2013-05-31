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
package esa.mo.tools.stubgen;

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.InteractionPatternEnum;
import esa.mo.tools.stubgen.specification.MultiReturnType;
import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInfo;
import esa.mo.tools.stubgen.specification.TypeInformation;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.LanguageWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.xsd.*;
import esa.mo.tools.stubgen.xsd.EnumerationType.Item;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.xml.bind.JAXBException;
import org.apache.maven.plugin.logging.Log;

/**
 * Main generator class for programming languages. Iterates over the specification model drilling down into the parts of
 * the model and inspecting them. Generates stubs and skeletons appropriate to an object orientated language.
 */
public abstract class GeneratorLangs extends GeneratorBase
{
  /**
   * The bit shift value for the area part of a type short form.
   */
  public static final int AREA_BIT_SHIFT = 48;
  /**
   * The bit shift value for the service part of a type short form.
   */
  public static final int SERVICE_BIT_SHIFT = 32;
  /**
   * The bit shift value for the version part of a type short form.
   */
  public static final int VERSION_BIT_SHIFT = 24;
  /**
   * The folder that consumer interfaces are created.
   */
  public static final String CONSUMER_FOLDER = "consumer";
  /**
   * The folder that provider interfaces are created.
   */
  public static final String PROVIDER_FOLDER = "provider";
  /**
   * The folder that transport interfaces are held.
   */
  public static final String TRANSPORT_FOLDER = "transport";
  private final String baseFolder;
  private final String registerMethodName;
  private final String deregisterMethodName;
  private final Map<String, MultiReturnType> multiReturnTypeMap = new TreeMap<String, MultiReturnType>();
  private final Map<String, String> reservedWordsMap = new TreeMap<String, String>();
  private final Map<String, OperationSummary> requiredPublishers = new TreeMap<String, OperationSummary>();
  private boolean supportsToString;
  private boolean supportsEquals;
  private boolean supportsToValue;
  private boolean supportsAsync;
  private boolean requiresDefaultConstructors;
  private boolean generateStructures;

  /**
   * Constructor.
   *
   * @param logger The logger to use.
   * @param supportsToString True if should generate to string methods in types.
   * @param supportsEquals True if should generate equals methods in types.
   * @param supportsToValue True if should generate generic get value methods in types.
   * @param supportsAsync True if should generate async consumer methods.
   * @param requiresDefaultConstructors True if type require a default constructor.
   * @param baseFolder Base folder that output should be generated in to.
   * @param registerMethodName Name of the register method.
   * @param deregisterMethodName Name of the deregister method.
   * @param config The generator configuration.
   */
  public GeneratorLangs(Log logger, boolean supportsToString, boolean supportsEquals, boolean supportsToValue, boolean supportsAsync, boolean requiresDefaultConstructors, String baseFolder, String registerMethodName, String deregisterMethodName, GeneratorConfiguration config)
  {
    super(logger, config);

    this.baseFolder = baseFolder;
    this.registerMethodName = registerMethodName;
    this.deregisterMethodName = deregisterMethodName;
    this.supportsToString = supportsToString;
    this.supportsEquals = supportsEquals;
    this.supportsToValue = supportsToValue;
    this.supportsAsync = supportsAsync;
    this.requiresDefaultConstructors = requiresDefaultConstructors;
  }

  @Override
  public void init(String destinationFolderName, boolean generateStructures, boolean generateCOM, Map<String, String> extraProperties) throws IOException
  {
    super.init(destinationFolderName, generateStructures, generateCOM, extraProperties);

    this.generateStructures = generateStructures;
  }

  @Override
  public void compile(String destinationFolderName, SpecificationType spec) throws IOException, JAXBException
  {
    File destFolder = StubUtils.createFolder(new File(destinationFolderName), baseFolder);
    for (AreaType area : spec.getArea())
    {
      processArea(destFolder, area, requiredPublishers);
    }

    for (Map.Entry<String, MultiReturnType> entry : multiReturnTypeMap.entrySet())
    {
      String string = entry.getKey();
      MultiReturnType rt = entry.getValue();

      createMultiReturnType(destinationFolderName, string, rt);
    }
  }

  @Override
  public void close(String destinationFolderName) throws IOException
  {
    // create any extra classes
    for (Map.Entry<String, OperationSummary> ele : requiredPublishers.entrySet())
    {
      String string = ele.getKey();

      if (!string.contains(".com.com.provider.") || generateCOM())
      {
        createRequiredPublisher(destinationFolderName, string, requiredPublishers.get(string));
      }
    }
  }

  /**
   * Does the generator support a string generator.
   *
   * @return True if it supports ToString.
   */
  public boolean isSupportsToString()
  {
    return supportsToString;
  }

  /**
   * Sets the generator support a string generator value.
   *
   * @param supportsToString the supportsToString to set
   */
  public void setSupportsToString(boolean supportsToString)
  {
    this.supportsToString = supportsToString;
  }

  /**
   * Does the generator support an equals method on structures.
   *
   * @return the supportsEquals
   */
  public boolean isSupportsEquals()
  {
    return supportsEquals;
  }

  /**
   * Sets the generator support equals value.
   *
   * @param supportsEquals the supportsEquals to set
   */
  public void setSupportsEquals(boolean supportsEquals)
  {
    this.supportsEquals = supportsEquals;
  }

  /**
   * Does the generator support a getMALValue method on structures.
   *
   * @return the supportsToValue
   */
  public boolean isSupportsToValue()
  {
    return supportsToValue;
  }

  /**
   * Sets the generator support value generator value.
   *
   * @param supportsToValue the supportsToValue to set
   */
  public void setSupportsToValue(boolean supportsToValue)
  {
    this.supportsToValue = supportsToValue;
  }

  /**
   * Does the generator need to generate async operation methods.
   *
   * @return the supportsAsync
   */
  public boolean isSupportsAsync()
  {
    return supportsAsync;
  }

  /**
   * Sets the generator async support value.
   *
   * @param supportsAsync the supportsAsync to set
   */
  public void setSupportsAsync(boolean supportsAsync)
  {
    this.supportsAsync = supportsAsync;
  }

  /**
   * Does the generator need to generate default constructors on structures and enumerations.
   *
   * @return the requiresDefaultConstructors
   */
  public boolean isRequiresDefaultConstructors()
  {
    return requiresDefaultConstructors;
  }

  /**
   * Sets the generator default constructor value.
   *
   * @param requiresDefaultConstructors the requiresDefaultConstructors to set
   */
  public void setRequiresDefaultConstructors(boolean requiresDefaultConstructors)
  {
    this.requiresDefaultConstructors = requiresDefaultConstructors;
  }

  /**
   * Does the generator need to generate structures.
   *
   * @return the generateStructures
   */
  public boolean isGenerateStructures()
  {
    return generateStructures;
  }

  /**
   * Sets the generate structures value.
   *
   * @param generateStructures the generateStructures to set
   */
  public void setGenerateStructures(boolean generateStructures)
  {
    this.generateStructures = generateStructures;
  }

  /**
   * To be used by derived generators to add an entry to the reserved word map.
   *
   * @param word The word to look for.
   * @param replacement The replacement to use.
   */
  protected void addReservedWord(String word, String replacement)
  {
    reservedWordsMap.put(word, replacement);
  }

  protected void processArea(File destinationFolder, AreaType area, Map<String, OperationSummary> requiredPublishers) throws IOException
  {
    if ((!area.getName().equalsIgnoreCase(StdStrings.COM)) || (generateCOM()))
    {
      getLog().info("Processing area: " + area.getName());
      // create folder
      final File areaFolder = StubUtils.createFolder(destinationFolder, area.getName());
      // create a comment for the area folder if supported
      createAreaFolderComment(areaFolder, area);
      // create area helper
      createAreaHelperClass(areaFolder, area);
      // if area level types exist
      if (generateStructures && (null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty())
      {
        // create area structure folder
        File structureFolder = StubUtils.createFolder(areaFolder, getConfig().getStructureFolder());
        // create a comment for the structure folder if supported
        createAreaStructureFolderComment(structureFolder, area);

        // create area level data types
        for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite())
        {
          if (oType instanceof FundamentalType)
          {
            createFundamentalClass(structureFolder, area, null, (FundamentalType) oType);
          }
          else if (oType instanceof AttributeType)
          {
            createListClass(structureFolder, area, null, ((AttributeType) oType).getName(), false, ((AttributeType) oType).getShortFormPart());
            createFactoryClass(structureFolder, area, null, ((AttributeType) oType).getName(), true, false);
          }
          else if (oType instanceof CompositeType)
          {
            createCompositeClass(structureFolder, area, null, (CompositeType) oType);
          }
          else if (oType instanceof EnumerationType)
          {
            createEnumerationClass(structureFolder, area, null, (EnumerationType) oType);
          }
          else
          {
            throw new IllegalArgumentException("Unexpected area (" + area.getName() + ") level datatype of " + oType.getClass().getName());
          }
        }
      }
      // create services
      for (ServiceType service : area.getService())
      {
        processService(areaFolder, area, service, requiredPublishers);
      }
    }
  }

  protected void processService(File areaFolder, AreaType area, ServiceType service, Map<String, OperationSummary> requiredPublishers) throws IOException
  {
    // create service folders
    File serviceFolder = StubUtils.createFolder(areaFolder, service.getName());
    // load service operation details
    ServiceSummary summary = createOperationElementList(service);
    // create a comment for the service folder if supported
    createServiceFolderComment(serviceFolder, area, service);
    // create service helper
    createServiceHelperClass(serviceFolder, area, service, summary);

    // don't create operation classes for COM as this is autogenerated in the specific services
    if (!summary.isComService())
    {
      // create consuer classes
      createServiceConsumerClasses(serviceFolder, area, service, summary);
      // create provider classes
      createServiceProviderClasses(serviceFolder, area, service, summary, requiredPublishers);
    }

    // if service level types exist
    if (generateStructures && (null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty())
    {
      // create structure folder
      File structureFolder = StubUtils.createFolder(serviceFolder, getConfig().getStructureFolder());
      // create a comment for the structure folder if supported
      createServiceStructureFolderComment(structureFolder, area, service);

      for (Object oType : service.getDataTypes().getCompositeOrEnumeration())
      {
        if (oType instanceof EnumerationType)
        {
          createEnumerationClass(structureFolder, area, service, (EnumerationType) oType);
        }
        else if (oType instanceof CompositeType)
        {
          createCompositeClass(structureFolder, area, service, (CompositeType) oType);
        }
        else
        {
          throw new IllegalArgumentException("Unexpected service (" + area.getName() + ":" + service.getName() + ") level datatype of " + oType.getClass().getName());
        }
      }
    }
  }

  protected void createServiceConsumerClasses(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating consumer classes: " + service.getName());
    File consumerFolder = StubUtils.createFolder(serviceFolder, CONSUMER_FOLDER);
    // create a comment for the consumer folder if supported
    createServiceConsumerFolderComment(consumerFolder, area, service);
    createServiceConsumerInterface(consumerFolder, area, service, summary);
    createServiceConsumerAdapter(consumerFolder, area, service, summary);
    createServiceConsumerStub(consumerFolder, area, service, summary);
  }

  protected void createServiceProviderClasses(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary, Map<String, OperationSummary> requiredPublishers) throws IOException
  {
    getLog().info("Creating provider classes: " + service.getName());
    File providerFolder = StubUtils.createFolder(serviceFolder, PROVIDER_FOLDER);
    // create a comment for the provider folder if supported
    createServiceProviderFolderComment(providerFolder, area, service);
    createServiceProviderHandler(providerFolder, area, service, summary);
    createServiceProviderSkeleton(providerFolder, area, service, summary, requiredPublishers);
    createServiceProviderDelegation(providerFolder, area, service, summary);
    createServiceProviderInheritance(providerFolder, area, service, summary);
    createServiceProviderInteractions(providerFolder, area, service, summary);
  }

  protected void createServiceProviderDelegation(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating provider delegate class: " + service.getName());
    createServiceProviderSkeletonHandler(providerFolder, area, service, summary, true);
  }

  protected void createServiceProviderInheritance(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating provider inheritance class: " + service.getName());
    createServiceProviderSkeletonHandler(providerFolder, area, service, summary, false);
  }

  protected void createServiceProviderInteractions(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.INVOKE_OP)
      {
        createServiceProviderInvokeInteractionClass(providerFolder, area, service, op);
      }
      else if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP)
      {
        createServiceProviderProgressInteractionClass(providerFolder, area, service, op);
      }
    }
  }

  protected void createServiceConsumerAdapter(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating consumer adapter: " + service.getName());

    String areaName = area.getName();
    String serviceName = service.getName();
    String className = serviceName + "Adapter";

    ClassWriter file = createClassFile(consumerFolder, className);

    file.addPackageStatement(area, service, CONSUMER_FOLDER);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String msgHdrType = createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALMessageHeader");
    String areaHelper = createElementType(file, areaName, null, null, areaName + "Helper");
    String serviceHelper = createElementType(file, areaName, serviceName, null, serviceName + "Helper");
    String stdMsgBodyType = createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALMessageBody");
    String stdNotifyBodyType = createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALNotifyBody");
    String stdErrBodyType = createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALErrorBody");

    String stdHeaderArg = msgHdrType + " msgHeader";
    String stdQosArg = "Map qosProperties";
    String stdErrorArgs = stdHeaderArg + ", org.ccsds.moims.mo.mal.MALStandardError error, Map qosProperties";

    String stdHeaderArgComment = "msgHeader The header of the received message";
    String stdQosArgComment = "qosProperties The QoS properties associated with the message";
    List<String> stdSimpleArgComments = Arrays.asList(stdHeaderArgComment, stdQosArgComment);
    List<String> stdErrorArgComments = Arrays.asList(stdHeaderArgComment, "error The received error message", stdQosArgComment);

    file.addClassOpenStatement(className, false, true, createElementType(file, StdStrings.MAL, null, CONSUMER_FOLDER, "MALInteractionAdapter"), null, "Consumer adapter for " + serviceName + " service.");

    // Implement the generation of the adapter
    boolean submitRequired = false;
    boolean requestRequired = false;
    boolean invokeRequired = false;
    boolean progressRequired = false;
    boolean pubsubRequired = false;

    if (supportsToValue)
    {
      file.addConstructor(StdStrings.PUBLIC, className, "org.ccsds.moims.mo.mal.consumer.MALConsumer consumer", "consumer", null).addMethodCloseStatement();
    }

    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case SUBMIT_OP:
        {
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "AckReceived", StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null, "Called by the MAL when a SUBMIT acknowledgement is received from a provider for the operation " + op.getName(), null, stdSimpleArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ErrorReceived", stdErrorArgs, null, "Called by the MAL when a SUBMIT acknowledgement error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          submitRequired = true;
          break;
        }
        case REQUEST_OP:
        {
          List<String> opArgComments = new LinkedList<String>();
          opArgComments.add(stdHeaderArgComment);

          String opArgs = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getRetTypes(), opArgComments), stdQosArg);
          opArgComments.add(stdQosArgComment);

          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ResponseReceived", opArgs, null, "Called by the MAL when a REQUEST response is received from a provider for the operation " + op.getName(), null, opArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ErrorReceived", stdErrorArgs, null, "Called by the MAL when a REQUEST response error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          requestRequired = true;
          break;
        }
        case INVOKE_OP:
        {
          List<String> opArgAComments = new LinkedList<String>();
          List<String> opArgRComments = new LinkedList<String>();
          opArgAComments.add(stdHeaderArgComment);
          opArgRComments.add(stdHeaderArgComment);

          String opArgsA = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getAckTypes(), opArgAComments), stdQosArg);
          opArgAComments.add(stdQosArgComment);
          String opArgsR = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getRetTypes(), opArgRComments), stdQosArg);
          opArgRComments.add(stdQosArgComment);

          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "AckReceived", opArgsA, null, "Called by the MAL when an INVOKE acknowledgement is received from a provider for the operation " + op.getName(), null, opArgAComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ResponseReceived", opArgsR, null, "Called by the MAL when an INVOKE response is received from a provider for the operation " + op.getName(), null, opArgRComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "AckErrorReceived", stdErrorArgs, null, "Called by the MAL when an INVOKE acknowledgement error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ResponseErrorReceived", stdErrorArgs, null, "Called by the MAL when an INVOKE response error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          invokeRequired = true;
          break;
        }
        case PROGRESS_OP:
        {
          List<String> opArgAComments = new LinkedList<String>();
          List<String> opArgUComments = new LinkedList<String>();
          List<String> opArgRComments = new LinkedList<String>();
          opArgAComments.add(stdHeaderArgComment);
          opArgUComments.add(stdHeaderArgComment);
          opArgRComments.add(stdHeaderArgComment);

          String opArgsA = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getAckTypes(), opArgAComments), stdQosArg);
          opArgAComments.add(stdQosArgComment);
          String opArgsU = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getUpdateTypes(), opArgUComments), stdQosArg);
          opArgUComments.add(stdQosArgComment);
          String opArgsR = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, op.getRetTypes(), opArgRComments), stdQosArg);
          opArgRComments.add(stdQosArgComment);

          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "AckReceived", opArgsA, null, "Called by the MAL when a PROGRESS acknowledgement is received from a provider for the operation " + op.getName(), null, opArgAComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "UpdateReceived", opArgsU, null, "Called by the MAL when a PROGRESS update is received from a provider for the operation " + op.getName(), null, opArgUComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ResponseReceived", opArgsR, null, "Called by the MAL when a PROGRESS response is received from a provider for the operation " + op.getName(), null, opArgRComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "AckErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS acknowledgement error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "UpdateErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS update error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "ResponseErrorReceived", stdErrorArgs, null, "Called by the MAL when a PROGRESS response error is received from a provider for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          progressRequired = true;
          break;
        }
        case PUBSUB_OP:
        {
          List<TypeInfo> retTypes = new LinkedList<TypeInfo>();
          retTypes.add(0, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
          retTypes.add(1, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", true)));
          for (TypeInfo ti : op.getRetTypes())
          {
            retTypes.add(TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(ti.getSourceType().getArea(), ti.getSourceType().getService(), ti.getSourceType().getName(), true)));
          }

          List<String> opArgUComments = new LinkedList<String>();
          opArgUComments.add(stdHeaderArgComment);

          String opArgsU = StubUtils.concatenateArguments(stdHeaderArg, createOperationArguments(getConfig(), file, retTypes, opArgUComments), stdQosArg);
          opArgUComments.add(stdQosArgComment);

          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "RegisterAckReceived", StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null, "Called by the MAL when a PubSub register acknowledgement is received from a broker for the operation " + op.getName(), null, stdSimpleArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "RegisterErrorReceived", stdErrorArgs, null, "Called by the MAL when a PubSub register acknowledgement error is received from a broker for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "DeregisterAckReceived", StubUtils.concatenateArguments(stdHeaderArg, stdQosArg), null, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker for the operation " + op.getName(), null, stdSimpleArgComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "NotifyReceived", opArgsU, null, "Called by the MAL when a PubSub update is received from a broker for the operation " + op.getName(), null, opArgUComments, null).addMethodCloseStatement();
          file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "NotifyErrorReceived", stdErrorArgs, null, "Called by the MAL when a PubSub update error is received from a broker for the operation " + op.getName(), null, stdErrorArgComments, null).addMethodCloseStatement();
          pubsubRequired = true;
          break;
        }
      }
    }

    if (submitRequired || supportsToValue)
    {
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.SUBMIT_OP, "submitAck", "Ack", 0, msgHdrType, "", serviceHelper, throwsMALException, summary, "Called by the MAL when a SUBMIT acknowledgement is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.SUBMIT_OP, "submit", "", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a SUBMIT acknowledgement error is received from a provider.");
    }

    if (requestRequired || supportsToValue)
    {
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.REQUEST_OP, "requestResponse", "Response", 3, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when a REQUEST response is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.REQUEST_OP, "request", "", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a REQUEST response error is received from a provider.");
    }

    if (invokeRequired || supportsToValue)
    {
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeAck", "Ack", 1, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE acknowledgement is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeAck", "Ack", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE acknowledgement error is received from a provider.");
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeResponse", "Response", 3, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE response is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.INVOKE_OP, "invokeResponse", "Response", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when an INVOKE response error is received from a provider.");
    }

    if (progressRequired || supportsToValue)
    {
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressAck", "Ack", 1, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS acknowledgement is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressAck", "Ack", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS acknowledgement error is received from a provider.");
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressUpdate", "Update", 2, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS update is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressUpdate", "Update", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS update error is received from a provider.");
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressResponse", "Response", 3, msgHdrType, stdMsgBodyType + " body, ", serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS response is received from a provider.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PROGRESS_OP, "progressResponse", "Response", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a PROGRESS response error is received from a provider.");
    }

    if (pubsubRequired || supportsToValue)
    {
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP, "registerAck", "RegisterAck", 1, msgHdrType, "", serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub register acknowledgement is received from a broker.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP, "register", "Register", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub register acknowledgement error is received from a broker.");
      createServiceConsumerAdapterNotifyMethod(file, InteractionPatternEnum.PUBSUB_OP, "notify", "Notify", 2, msgHdrType, stdNotifyBodyType + " body, ", areaHelper, areaName, serviceHelper, serviceName, throwsMALException, summary, "Called by the MAL when a PubSub update is received from a broker.");
      createServiceConsumerAdapterErrorMethod(file, InteractionPatternEnum.PUBSUB_OP, "notify", "Notify", msgHdrType, stdErrBodyType, serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub update error is received from a broker.");
      createServiceConsumerAdapterMessageMethod(file, InteractionPatternEnum.PUBSUB_OP, "deregisterAck", "DeregisterAck", 1, msgHdrType, "", serviceHelper, throwsMALException, summary, "Called by the MAL when a PubSub deregister acknowledgement is received from a broker.");

      file.addMethodOpenStatement(true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "notifyReceivedFromOtherService", msgHdrType + " msgHeader, org.ccsds.moims.mo.mal.transport.MALNotifyBody body, Map qosProperties", throwsMALException, "Called by the MAL when a PubSub update from another service is received from a broker.", null, Arrays.asList("msgHeader The header of the received message", "body The body of the received message", "qosProperties The QoS properties associated with the message"), Arrays.asList(throwsMALException + " if an error is detected processing the message.")).addMethodCloseStatement();
    }

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createServiceConsumerAdapterMessageMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, int opTypeIndex, String msgHdrType, String stdMsgBodyType, String serviceHelper, String throwsMALException, ServiceSummary summary, String comment) throws IOException
  {
    MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, opname + "Received", msgHdrType + " msgHeader, " + stdMsgBodyType + "Map qosProperties", throwsMALException, comment, null, Arrays.asList("msgHeader The header of the received message", ((0 == stdMsgBodyType.length()) ? null : "body The body of the received message"), "qosProperties The QoS properties associated with the message"), Arrays.asList(throwsMALException + " if an error is detected processing the message."));
    method.addMethodStatement("switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (optype == op.getPattern())
      {
        String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        List<TypeInfo> opTypes = null;
        if (1 == opTypeIndex)
        {
          opTypes = op.getAckTypes();
        }
        else if (2 == opTypeIndex)
        {
          opTypes = op.getUpdateTypes();
        }
        else if (3 == opTypeIndex)
        {
          opTypes = op.getRetTypes();
        }
        String opArgs = createAdapterMethodsArgs(opTypes, "body", true, false);
        method.addMethodStatement("    " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    method.addMethodStatement("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();
  }

  protected void createServiceConsumerAdapterNotifyMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, int opTypeIndex, String msgHdrType, String stdMsgBodyType, String areaHelper, String areaName, String serviceHelper, String serviceName, String throwsMALException, ServiceSummary summary, String comment) throws IOException
  {
    MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, opname + "Received", msgHdrType + " msgHeader, " + stdMsgBodyType + "Map qosProperties", throwsMALException, comment, null, Arrays.asList("msgHeader The header of the received message", ((0 == stdMsgBodyType.length()) ? null : "body The body of the received message"), "qosProperties The QoS properties associated with the message"), Arrays.asList(throwsMALException + " if an error is detected processing the message."));

    method.addMethodStatement("if ((" + areaHelper + "." + areaName.toUpperCase() + "_AREA_NUMBER.equals(msgHeader.getServiceArea())) && (" + serviceHelper + "." + serviceName.toUpperCase() + "_SERVICE_NUMBER.equals(msgHeader.getService())))", false);
    method.addMethodStatement("{", false);
    method.addMethodStatement("  switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
    method.addMethodStatement("  {", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (optype == op.getPattern())
      {
        String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("    case " + ns, ns, false);
        List<TypeInfo> opTypes = new LinkedList<TypeInfo>();
        opTypes.add(0, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, StdStrings.IDENTIFIER, false)));
        opTypes.add(1, TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(StdStrings.MAL, null, "UpdateHeader", true)));
        for (TypeInfo ti : op.getRetTypes())
        {
          opTypes.add(TypeUtils.convertTypeReference(this, TypeUtils.createTypeReference(ti.getSourceType().getArea(), ti.getSourceType().getService(), ti.getSourceType().getName(), true)));
        }

        String opArgs = createAdapterMethodsArgs(opTypes, "body", true, false);
        method.addMethodStatement("      " + op.getName() + subopPostname + "Received(msgHeader" + opArgs + ", qosProperties)");
        method.addMethodStatement("      break");
      }
    }
    method.addMethodStatement("    default:", false);
    method.addMethodStatement("      throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
    method.addMethodStatement("  }", false);
    method.addMethodStatement("}", false);
    method.addMethodStatement("else", false);
    method.addMethodStatement("{", false);
    method.addMethodStatement("  notifyReceivedFromOtherService(msgHeader, body, qosProperties)");
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();
  }

  protected void createServiceConsumerAdapterErrorMethod(ClassWriter file, InteractionPatternEnum optype, String opname, String subopPostname, String msgHdrType, String stdErrBodyType, String serviceHelper, String throwsMALException, ServiceSummary summary, String comment) throws IOException
  {
    MethodWriter method = file.addMethodOpenStatement(true, true, false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, opname + "ErrorReceived", msgHdrType + " msgHeader, " + stdErrBodyType + " body, Map qosProperties", throwsMALException, comment, null, Arrays.asList("msgHeader The header of the received message", "body The body of the received message", "qosProperties The QoS properties associated with the message"), Arrays.asList(throwsMALException + " if an error is detected processing the message."));
    method.addMethodStatement("switch (" + createMethodCall("msgHeader.getOperation().getValue()") + ")", false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (optype == op.getPattern())
      {
        String ns = convertToNamespace(serviceHelper + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        method.addMethodStatement("    " + op.getName() + subopPostname + "ErrorReceived(msgHeader, body.getError(), qosProperties)");
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    method.addMethodStatement("    throw new " + throwsMALException + "(\"Consumer adapter was not expecting operation number \" + msgHeader.getOperation().getValue())");
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

  }

  protected void createServiceConsumerInterface(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    String serviceName = service.getName();

    getLog().info("Creating consumer interface: " + serviceName);

    InterfaceWriter file = createInterfaceFile(consumerFolder, serviceName);

    file.addPackageStatement(area, service, CONSUMER_FOLDER);

    file.addInterfaceOpenStatement(serviceName, null, "Consumer interface for " + serviceName + " service.");

    String serviceAdapterArg = createElementType(file, area.getName(), service.getName(), CONSUMER_FOLDER, serviceName + "Adapter") + " adapter";
    String serviceAdapterArgComment = "adapter Listener in charge of receiving the messages from the service provider";
    String continueOpArgs = createElementType(file, StdStrings.MAL, null, StdStrings.UOCTET) + " lastInteractionStage, " + createElementType(file, StdStrings.MAL, null, StdStrings.TIME) + " initiationTimestamp, " + createElementType(file, StdStrings.MAL, null, StdStrings.LONG) + " transactionId, " + serviceAdapterArg;
    List<String> continueOpArgComments = Arrays.asList("lastInteractionStage The last stage of the interaction to continue", "initiationTimestamp Timestamp of the interaction initiation message", "transactionId Transaction identifier of the interaction to continue", "adapter Listener in charge of receiving the messages from the service provider");

    List<String> registerOpArgComments = Arrays.asList("subscription the subscription to register for", "adapter Listener in charge of receiving the updates from the service provider");
    List<String> deregisterOpArgComments = Arrays.asList("identifierList the subscription identifiers to deregister");
    List<String> aderegisterOpArgComments = Arrays.asList("identifierList the subscription identifiers to deregister", "adapter Listener in charge of receiving the messages from the service provider");

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
    String msgType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, StdStrings.MALMESSAGE));

    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, createElementType(file, StdStrings.MAL, null, CONSUMER_FOLDER, "MALConsumer"), "getConsumer", "", null, "Returns the internal MAL consumer object used for sending of messages from this interface", "The MAL consumer object.", null, null);

    for (OperationSummary op : summary.getOperations())
    {
      List<String> opArgComments = new LinkedList<String>();

      switch (op.getPattern())
      {
        case SEND_OP:
        {
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments), throwsInteractionAndMALException, op.getOriginalOp().getComment(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          break;
        }
        case SUBMIT_OP:
        case REQUEST_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments);
          String opRetType = createOperationReturnType(file, area, service, op);
          String opRetComment = (StdStrings.VOID.equals(opRetType)) ? null : "The return value of the interaction";
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          if (supportsAsync)
          {
            String asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
            opArgComments.add(serviceAdapterArgComment);
            file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          }
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, continueOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          break;
        }
        case INVOKE_OP:
        case PROGRESS_OP:
        {
          String opArgs = StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments), serviceAdapterArg);
          opArgComments.add(serviceAdapterArgComment);
          String opRetType = createOperationReturnType(file, area, service, op);
          String opRetComment = (StdStrings.VOID.equals(opRetType)) ? null : "The acknowledge value of the interaction";
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          if (supportsAsync)
          {
            file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()), opArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          }
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, continueOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          break;
        }
        case PUBSUB_OP:
        {
          String subStr = createReturnReference(createElementType(file, StdStrings.MAL, null, "Subscription")) + " subscription";
          String idStr = createReturnReference(createElementType(file, StdStrings.MAL, null, "IdentifierList")) + " identifierList";
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName() + "Register", subStr + ", " + serviceAdapterArg, throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null, registerOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName() + "Deregister", idStr, throwsInteractionAndMALException, "Deregister method for the " + op.getName() + " PubSub interaction", null, deregisterOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          if (supportsAsync)
          {
            file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Register", subStr + ", " + serviceAdapterArg, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction", registerOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister", idStr + ", " + serviceAdapterArg, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister", "the MAL message sent to initiate the interaction", aderegisterOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          }
          break;
        }
      }
    }

    file.addInterfaceCloseStatement();

    file.flush();
  }

  protected void createServiceConsumerStub(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating consumer stub: " + service.getName());

    String serviceName = service.getName();
    String className = serviceName + "Stub";

    ClassWriter file = createClassFile(consumerFolder, className);

    file.addPackageStatement(area, service, CONSUMER_FOLDER);

    String serviceAdapterArg = createElementType(file, area.getName(), service.getName(), CONSUMER_FOLDER, serviceName + "Adapter") + " adapter";

    String serviceAdapterArgComment = "adapter Listener in charge of receiving the messages from the service provider";
    String continueOpArgs = createReturnReference(createElementType(file, StdStrings.MAL, null, StdStrings.UOCTET)) + " lastInteractionStage, " + createReturnReference(createElementType(file, StdStrings.MAL, null, StdStrings.TIME)) + " initiationTimestamp, " + createReturnReference(createElementType(file, StdStrings.MAL, null, StdStrings.LONG)) + " transactionId, " + serviceAdapterArg;
    List<String> continueOpArgComments = Arrays.asList("lastInteractionStage The last stage of the interaction to continue", "initiationTimestamp Timestamp of the interaction initiation message", "transactionId Transaction identifier of the interaction to continue", "adapter Listener in charge of receiving the messages from the service provider");

    List<String> registerOpArgComments = Arrays.asList("subscription the subscription to register for", "adapter Listener in charge of receiving the updates from the service provider");
    List<String> deregisterOpArgComments = Arrays.asList("identifierList the subscription identifiers to deregister");
    List<String> aderegisterOpArgComments = Arrays.asList("identifierList the subscription identifiers to deregister", "adapter Listener in charge of receiving the messages from the service provider");

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;

    String msgType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, StdStrings.MALMESSAGE));
    String msgBodyType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALMessageBody"));
    String uriType = createReturnReference(createElementType(file, StdStrings.MAL, null, StdStrings.URI));
    String helperType = createElementType(file, area.getName(), service.getName(), null, serviceName + "Helper") + getConfig().getNamingSeparator();
    String consumerType = createElementType(file, StdStrings.MAL, null, CONSUMER_FOLDER, "MALConsumer");
    String consumerMethodCall = createMethodCall("consumer.");

    file.addClassOpenStatement(className, false, false, null, createElementType(file, area.getName(), serviceName, CONSUMER_FOLDER, serviceName), "Consumer stub for " + serviceName + " service.");

    file.addClassVariable(false, false, StdStrings.PRIVATE, createReturnReference(consumerType), false, false, false, "consumer", (String) null, null);

    MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, "org.ccsds.moims.mo.mal.consumer.MALConsumer consumer", "", null, "Wraps a MALconsumer connection with service specific methods that map from the high level service API to the generic MAL API.", Arrays.asList("consumer The MALConsumer to use in this stub."), null);
    method.addMethodStatement(createMethodCall("this.consumer = consumer"));
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, createReturnReference(consumerType), "getConsumer", "", null, "Returns the internal MAL consumer object used for sending of messages from this interface", "The MAL consumer object.", null, null);
    method.addMethodStatement(createMethodCall("return consumer"));
    method.addMethodCloseStatement();

    if (supportsToValue)
    {
      method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, uriType, "getURI", "", null);
      method.addMethodStatement(createMethodCall("return consumer.getUri()"));
      method.addMethodCloseStatement();
    }

    for (OperationSummary op : summary.getOperations())
    {
      List<String> opArgComments = new LinkedList<String>();

      String operationInstanceVar = addressOf(helperType) + op.getName().toUpperCase() + "_OP";
      switch (op.getPattern())
      {
        case SEND_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments);
          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodWithDependencyStatement("return " + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", " + createArgNameOrNull(op.getArgTypes()) + ")", helperType, true);
          method.addMethodCloseStatement();
          break;
        }
        case SUBMIT_OP:
        case REQUEST_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments);
          String opRetType = createOperationReturnType(file, area, service, op);
          String opRetComment = null;
          String rv = "";
          if (!StdStrings.VOID.equals(opRetType))
          {
            rv = msgBodyType + " body = ";
            opRetComment = "The return value of the interaction";
          }
          String opGet = rv + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", " + createArgNameOrNull(op.getArgTypes()) + ")";
          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodWithDependencyStatement(opGet, helperType, true);
          createOperationReturn(file, method, op, opRetType);
          method.addMethodCloseStatement();

          if (supportsAsync)
          {
            String asyncOpArgs = StubUtils.concatenateArguments(opArgs, serviceAdapterArg);
            opArgComments.add(serviceAdapterArgComment);
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()), asyncOpArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")");
            method.addMethodCloseStatement();
          }

          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, continueOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodStatement(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
          method.addMethodCloseStatement();
          break;
        }
        case INVOKE_OP:
        case PROGRESS_OP:
        {
          String opArgs = StubUtils.concatenateArguments(createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments), serviceAdapterArg);
          opArgComments.add(serviceAdapterArgComment);
          String opRetType = createOperationReturnType(file, area, service, op);
          String opRetComment = null;
          String rv = "";
          if (!StdStrings.VOID.equals(opRetType))
          {
            rv = msgBodyType + " body = ";
            opRetComment = "The acknowledge value of the interaction";
          }
          String opGet = rv + consumerMethodCall + createConsumerPatternCall(op) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")";
          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, opRetType, op.getName(), opArgs, throwsInteractionAndMALException, op.getOriginalOp().getComment(), opRetComment, opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodWithDependencyStatement(opGet, helperType, true);
          createOperationReturn(file, method, op, opRetType);
          method.addMethodCloseStatement();

          if (supportsAsync)
          {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()), opArgs, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName(), "the MAL message sent to initiate the interaction", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(createConsumerPatternCall(op)) + "(" + operationInstanceVar + ", adapter, " + createArgNameOrNull(op.getArgTypes()) + ")");
            method.addMethodCloseStatement();
          }

          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "continue" + StubUtils.preCap(op.getName()), continueOpArgs, throwsInteractionAndMALException, "Continues a previously started interaction", null, continueOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodStatement(consumerMethodCall + "continueInteraction(" + operationInstanceVar + ", lastInteractionStage, initiationTimestamp, transactionId, adapter)");
          method.addMethodCloseStatement();
          break;
        }
        case PUBSUB_OP:
        {
          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "Register", "org.ccsds.moims.mo.mal.structures.Subscription subscription, " + serviceAdapterArg, throwsInteractionAndMALException, "Register method for the " + op.getName() + " PubSub interaction", null, registerOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodWithDependencyStatement(consumerMethodCall + registerMethodName + "(" + operationInstanceVar + ", subscription, adapter)", helperType, true);
          method.addMethodCloseStatement();

          if (supportsAsync)
          {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()) + "Register", "org.ccsds.moims.mo.mal.structures.Subscription subscription, " + serviceAdapterArg, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Register", "the MAL message sent to initiate the interaction", registerOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(registerMethodName) + "(" + operationInstanceVar + ", subscription, adapter)");
            method.addMethodCloseStatement();
          }

          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName() + "Deregister", "org.ccsds.moims.mo.mal.structures.IdentifierList identifierList", throwsInteractionAndMALException, "Deregister method for the " + op.getName() + " PubSub interaction", null, deregisterOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
          method.addMethodStatement(consumerMethodCall + deregisterMethodName + "(" + operationInstanceVar + ", identifierList)");
          method.addMethodCloseStatement();

          if (supportsAsync)
          {
            method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "async" + StubUtils.preCap(op.getName()) + "Deregister", "org.ccsds.moims.mo.mal.structures.IdentifierList identifierList, " + serviceAdapterArg, throwsInteractionAndMALException, "Asynchronous version of method " + op.getName() + "Deregister", "the MAL message sent to initiate the interaction", aderegisterOpArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
            method.addMethodStatement("return " + consumerMethodCall + "async" + StubUtils.preCap(deregisterMethodName) + "(" + operationInstanceVar + ", identifierList, adapter)");
            method.addMethodCloseStatement();
          }
          break;
        }
      }
    }

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createServiceProviderHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating provider handler interface: " + service.getName());

    String handlerName = service.getName() + "Handler";
    InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    file.addInterfaceOpenStatement(handlerName, null, "Interface that providers of the " + service.getName() + " service must implement to handle the operatoins of that service.");

    String intHandlerStr = createReturnReference(createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, StdStrings.MALINTERACTION)) + " interaction";
    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;

    for (OperationSummary op : summary.getOperations())
    {
      if (InteractionPatternEnum.PUBSUB_OP != op.getPattern())
      {
        List<String> opArgComments = new LinkedList<String>();
        String opArgs = convertToNamespace(createOperationArguments(getConfig(), file, op.getArgTypes(), opArgComments));
        String opRetType = StdStrings.VOID;
        String opRetComment = null;
        String serviceHandlerStr = intHandlerStr;
        opArgComments.add("interaction The MAL object representing the interaction in the provider.");

        if (InteractionPatternEnum.REQUEST_OP == op.getPattern())
        {
          opRetType = createOperationReturnType(file, area, service, op);
          opRetComment = "The return value of the operation";
        }
        else if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern()))
        {
          serviceHandlerStr = createReturnReference(createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction")) + " interaction";
        }

        file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), StubUtils.concatenateArguments(opArgs, serviceHandlerStr), throwsInteractionAndMALException, "Implements the operation " + op.getName(), opRetComment, opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
      }
    }

    file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, "setSkeleton", createReturnReference(createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Skeleton")) + " skeleton", null, "Sets the skeleton to be used for creation of publishers.", null, Arrays.asList("skeleton The skeleton to be used"), null);

    file.addInterfaceCloseStatement();

    file.flush();
  }

  protected void createServiceProviderInvokeInteractionClass(File providerFolder, AreaType area, ServiceType service, OperationSummary op) throws IOException
  {
    String className = StubUtils.preCap(op.getName()) + "Interaction";
    getLog().info("Creating provider invoke interaction class: " + className);

    ClassWriter file = createClassFile(providerFolder, className);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
    String msgType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, StdStrings.MALMESSAGE));
    String opType = createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALInvoke");
    String errType = createElementType(file, StdStrings.MAL, null, null, "MALStandardError");

    file.addClassOpenStatement(className, false, false, null, null, "Provider INVOKE interaction class for " + service.getName() + "::" + op.getName() + " operation.");

    file.addClassVariable(false, false, StdStrings.PRIVATE, opType, false, false, false, "interaction", (String) null, null);

    MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, opType + " interaction", "", null, "Wraps the provided MAL interaction object with methods for sending responses to an INVOKE interaction from a provider.", Arrays.asList("interaction The MAL interaction action object to use."), null);
    method.addMethodStatement(createMethodCall("this.interaction = interaction"));
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, opType, "getInteraction", "", null, "Returns the MAL interaction object used for returning messages from the provider.", "The MAL interaction object provided in the constructor", null, null);
    method.addMethodStatement("return interaction");
    method.addMethodCloseStatement();

    List<String> opArgComments = new LinkedList<String>();
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendAcknowledgement", createOperationArguments(getConfig(), file, op.getAckTypes(), opArgComments), throwsInteractionAndMALException,
            "Sends a INVOKE acknowledge to the consumer", "Returns the MAL message created by the acknowledge", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
    method.addMethodCloseStatement();

    opArgComments = new LinkedList<String>();
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendResponse", createOperationArguments(getConfig(), file, op.getRetTypes(), opArgComments), throwsInteractionAndMALException,
            "Sends a INVOKE response to the consumer", "Returns the MAL message created by the response", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
    method.addMethodCloseStatement();

    createServiceProviderInteractionErrorHandlers(file, false, msgType, errType, throwsInteractionException, throwsMALException);

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createServiceProviderProgressInteractionClass(File providerFolder, AreaType area, ServiceType service, OperationSummary op) throws IOException
  {
    String className = StubUtils.preCap(op.getName()) + "Interaction";
    getLog().info("Creating provider progress interaction class: " + className);

    ClassWriter file = createClassFile(providerFolder, className);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsInteractionAndMALException = throwsInteractionException + ", " + throwsMALException;
    String msgType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, StdStrings.MALMESSAGE));
    String opType = createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALProgress");
    String errType = createElementType(file, StdStrings.MAL, null, null, "MALStandardError");

    file.addClassOpenStatement(className, false, false, null, null, "Provider PROGRESS interaction class for " + service.getName() + "::" + op.getName() + " operation.");

    file.addClassVariable(false, false, StdStrings.PRIVATE, opType, false, false, false, "interaction", (String) null, null);

    MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, opType + " interaction", "", null, "Wraps the provided MAL interaction object with methods for sending responses to a PROGRESS interaction from a provider.", Arrays.asList("interaction The MAL interaction action object to use."), null);
    method.addMethodStatement(createMethodCall("this.interaction = interaction"));
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, opType, "getInteraction", "", null, "Returns the MAL interaction object used for returning messages from the provider.", "The MAL interaction object provided in the constructor", null, null);
    method.addMethodStatement("return interaction");
    method.addMethodCloseStatement();

    List<String> opArgComments = new LinkedList<String>();
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendAcknowledgement", createOperationArguments(getConfig(), file, op.getAckTypes(), opArgComments), throwsInteractionAndMALException,
            "Sends a PROGRESS acknowledge to the consumer", "Returns the MAL message created by the acknowledge", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendAcknowledgement(") + createArgNameOrNull(op.getAckTypes()) + ")");
    method.addMethodCloseStatement();

    opArgComments = new LinkedList<String>();
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendUpdate", createOperationArguments(getConfig(), file, op.getUpdateTypes(), opArgComments), throwsInteractionAndMALException,
            "Sends a PROGRESS update to the consumer", "Returns the MAL message created by the update", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendUpdate(") + createArgNameOrNull(op.getUpdateTypes()) + ")");
    method.addMethodCloseStatement();

    opArgComments = new LinkedList<String>();
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendResponse", createOperationArguments(getConfig(), file, op.getRetTypes(), opArgComments), throwsInteractionAndMALException,
            "Sends a PROGRESS response to the consumer", "Returns the MAL message created by the response", opArgComments, Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendResponse(") + createArgNameOrNull(op.getRetTypes()) + ")");
    method.addMethodCloseStatement();

    createServiceProviderInteractionErrorHandlers(file, true, msgType, errType, throwsInteractionException, throwsMALException);

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createServiceProviderInteractionErrorHandlers(ClassWriter file, boolean withUpdate, String msgType, String errType, String throwsInteractionException, String throwsMALException) throws IOException
  {
    MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendError", errType + " error", throwsInteractionException + ", " + throwsMALException,
            "Sends an error to the consumer", "Returns the MAL message created by the error", Arrays.asList("error The MAL error to send to the consumer."), Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
    method.addMethodStatement(createMethodCall("return interaction.sendError(error)"));
    method.addMethodCloseStatement();

    if (withUpdate)
    {
      method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, msgType, "sendUpdateError", errType + " error", throwsInteractionException + ", " + throwsMALException,
              "Sends an update error to the consumer", "Returns the MAL message created by the error", Arrays.asList("error The MAL error to send to the consumer."), Arrays.asList(throwsInteractionException + " if there is a problem during the interaction as defined by the MAL specification.", throwsMALException + " if there is an implementation exception"));
      method.addMethodStatement(createMethodCall("return interaction.sendUpdateError(error)"));
      method.addMethodCloseStatement();
    }
  }

  protected void createServiceProviderSkeleton(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, Map<String, OperationSummary> requiredPublishers) throws IOException
  {
    getLog().info("Creating provider skeleton interface: " + service.getName());

    String skeletonName = service.getName() + "Skeleton";
    InterfaceWriter file = createInterfaceFile(providerFolder, skeletonName);
    String malUInteger = createElementType(file, StdStrings.MAL, null, StdStrings.UINTEGER);
    String malDomId = createElementType(file, StdStrings.MAL, null, "IdentifierList");
    String malIdentifier = createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
    String malSession = createElementType(file, StdStrings.MAL, null, "SessionType");
    String malqos = createElementType(file, StdStrings.MAL, null, "QoSLevel");
    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    file.addInterfaceOpenStatement(skeletonName, null, "The skeleton interface for the " + service.getName() + " service.");

    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case PUBSUB_OP:
        {
          String updateType = getConfig().getBasePackage() + area.getName().toLowerCase() + getConfig().getNamingSeparator() + service.getName().toLowerCase() + getConfig().getNamingSeparator() + PROVIDER_FOLDER + getConfig().getNamingSeparator() + StubUtils.preCap(op.getName()) + "Publisher";
          requiredPublishers.put(updateType, op);
          file.addTypeDependency("Map<_String;_String>");
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, updateType, "create" + StubUtils.preCap(op.getName()) + "Publisher", malDomId + " domain, " + malIdentifier + " networkZone, " + malSession + " sessionType, " + malIdentifier + " sessionName, " + malqos + " qos, Map<_String;_String> qosProps, " + malUInteger + " priority", throwsMALException,
                  "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(), "The new publisher object.", Arrays.asList("domain the domain used for publishing", "networkZone the network zone used for publishing", "sessionType the session used for publishing", "sessionName the session name used for publishing", "qos the QoS used for publishing", "qosProps the QoS properties used for publishing", "priority the priority used for publishing"), Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
          break;
        }
      }
    }

    file.addInterfaceCloseStatement();

    file.flush();
  }

  protected void createServiceProviderSkeletonHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, boolean isDelegate) throws IOException
  {
    String className = service.getName();
    String comment;
    if (isDelegate)
    {
      className += "DelegationSkeleton";
      comment = "Provider Delegation skeleton for " + className + " service.";
    }
    else
    {
      className += "InheritanceSkeleton";
      comment = "Provider Inheritance skeleton for " + className + " service.";
    }

    ClassWriter file = createClassFile(providerFolder, className);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String throwsInteractionException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALINTERACTIONEXCEPTION);
    String throwsMALAndInteractionException = throwsInteractionException + ", " + throwsMALException;
    String malHelper = createElementType(file, StdStrings.MAL, null, null, "MALHelper");
    String providerType = createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALProvider");
    String helperName = createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
    String handlerName = createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
    String skeletonName = createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Skeleton");
    String malString = malStringAsElement(file);
    String malInteger = createElementType(file, StdStrings.MAL, null, StdStrings.INTEGER);
    String malUInteger = createElementType(file, StdStrings.MAL, null, StdStrings.UINTEGER);
    String msgBody = createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, "MALMessageBody");
    String stdError = createElementType(file, StdStrings.MAL, null, null, "MALStandardError");
    String stdErrorNs = convertToNamespace("," + stdError + ".," + malString + ".," + malInteger + ".");

    String implementsList = "org.ccsds.moims.mo.mal.provider.MALInteractionHandler, " + skeletonName;
    if (!isDelegate)
    {
      implementsList += ", " + createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
    }
    file.addClassOpenStatement(className, false, !isDelegate, null, implementsList, comment);

    file.addClassVariable(false, false, StdStrings.PRIVATE, createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALProviderSet"), true, false, false, "providerSet", "(" + helperName + getConfig().getNamingSeparator() + service.getName().toUpperCase() + "_SERVICE)", null);

    if (isDelegate)
    {
      file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, false, false, "delegate", (String) null, null);
    }

    if (isDelegate)
    {
      MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, handlerName + " delegate", "", null, "Creates a delegation skeleton using the supplied delegate.", Arrays.asList("delegate The interation handler used for delegation"), null);
      method.addMethodStatement(createMethodCall("this.delegate = delegate"));
      method.addMethodStatement(createMethodCall("delegate.setSkeleton(this)"));
      method.addMethodCloseStatement();
    }
    else
    {
      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "setSkeleton", skeletonName + " skeleton", null, "Implements the setSkeleton method of the handler interface but does nothing as this is the skeleton.", null, Arrays.asList("skeleton Not used in the inheritance pattern (the skeleton is 'this'"), null);
      method.addMethodStatement("// Not used in the inheritance pattern (the skeleton is 'this')");
      method.addMethodCloseStatement();

    }
    MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "malInitialize", providerType + " provider", throwsMALException, "Adds the supplied MAL provider to the internal list of providers used for PubSub", null, Arrays.asList("provider The provider to add"), Arrays.asList(throwsMALException + " If an error is detected."));
    if (supportsToValue)
    {
      method.addMethodStatement("m_provider = provider");
      method.addMethodStatement("provider->setInteractionHandlerRef(this)");
      method.addMethodStatement("provider->init()");
    }
    else
    {
      method.addMethodStatement("providerSet.addProvider(provider)");
    }
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "malFinalize", providerType + " provider", throwsMALException, "Removes the supplied MAL provider from the internal list of providers used for PubSub", null, Arrays.asList("provider The provider to add"), Arrays.asList(throwsMALException + " If an error is detected."));
    if (supportsToValue)
    {
      addVectorRemoveStatement(file, method, "providers", "provider");
    }
    else
    {
      method.addMethodStatement("providerSet.removeProvider(provider)");
    }
    method.addMethodCloseStatement();

    // add publisher handler code
    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case PUBSUB_OP:
        {
          String updateType = getConfig().getBasePackage() + area.getName().toLowerCase() + getConfig().getNamingSeparator() + service.getName().toLowerCase() + getConfig().getNamingSeparator() + PROVIDER_FOLDER + getConfig().getNamingSeparator() + StubUtils.preCap(op.getName()) + "Publisher";
          method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, updateType, "create" + StubUtils.preCap(op.getName()) + "Publisher", getConfig().getBasePackage() + "mal.structures.IdentifierList domain, " + getConfig().getBasePackage() + "mal.structures.Identifier networkZone, " + getConfig().getBasePackage() + "mal.structures.SessionType session, " + getConfig().getBasePackage() + "mal.structures.Identifier sessionName, " + getConfig().getBasePackage() + "mal.structures.QoSLevel qos, Map<_String;_String> qosProps, " + malUInteger + " priority", throwsMALException,
                  "Creates a publisher object using the current registered provider set for the PubSub operation " + op.getName(), "The new publisher object.", Arrays.asList("domain the domain used for publishing", "networkZone the network zone used for publishing", "session the session used for publishing", "sessionName the session name used for publishing", "qos the QoS used for publishing", "qosProps the QoS properties used for publishing", "priority the priority used for publishing"), Arrays.asList(throwsMALException + " if a problem is detected during creation of the publisher"));
          String ns = convertToNamespace(helperName + "." + op.getName().toUpperCase() + "_OP");
          method.addMethodWithDependencyStatement("return new " + convertToNamespace(convertClassName(updateType)) + "(providerSet.createPublisherSet(" + addressOf(ns) + ", domain, networkZone, session, sessionName, qos, qosProps, priority))", ns, true);
          method.addMethodCloseStatement();
          break;
        }
      }
    }

    // for each IP type add handler code
    String delegateCall = "";
    if (isDelegate)
    {
      delegateCall = createMethodCall("delegate.");
    }

    // SEND handler
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "handleSend", createServiceProviderSkeletonSendHandler(file) + " interaction, " + msgBody + " body", throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList("interaction the interaction object", "body the message body"), Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
    method.addMethodStatement(createMethodCall("switch (interaction.getOperation().getNumber().getValue())"), false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.SEND_OP)
      {
        String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
        String ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    String ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
    method.addMethodWithDependencyStatement("    throw new " + throwsInteractionException + "(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    // SUBMIT handler
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "handleSubmit", createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALSubmit") + " interaction, " + msgBody + " body", throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList("interaction the interaction object", "body the message body"), Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
    method.addMethodStatement(createMethodCall("switch (interaction.getOperation().getNumber().getValue())"), false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.SUBMIT_OP)
      {
        String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
        ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "interaction)");
        method.addMethodStatement(createMethodCall("    interaction.sendAcknowledgement()"));
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
    method.addMethodWithDependencyStatement("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    // REQUEST handler
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "handleRequest", createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALRequest") + " interaction, " + msgBody + " body", throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList("interaction the interaction object", "body the message body"), Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
    method.addMethodStatement(createMethodCall("switch (interaction.getOperation().getNumber().getValue())"), false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.REQUEST_OP)
      {
        String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
        String opResp = delegateCall + op.getName() + "(" + opArgs + "interaction)";
        if ((1 == op.getRetTypes().size()) && (op.getRetTypes().get(0).isNativeType()))
        {
          opResp = "new " + getConfig().getBasePackage() + "mal." + getConfig().getStructureFolder() + ".Union(" + opResp + ")";
        }
        ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        createRequestResponseDecompose(method, op, opResp, createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes()));
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
    method.addMethodWithDependencyStatement("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    // INVOKE handler
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "handleInvoke", createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALInvoke") + " interaction, " + msgBody + " body", throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList("interaction the interaction object", "body the message body"), Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
    method.addMethodStatement(createMethodCall("switch (interaction.getOperation().getNumber().getValue())"), false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.INVOKE_OP)
      {
        String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
        ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "new " + convertClassName(StubUtils.preCap(op.getName()) + "Interaction") + "(interaction))");
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
    method.addMethodWithDependencyStatement("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    // PROGRESS handler
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "handleProgress", createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, "MALProgress") + " interaction, " + msgBody + " body", throwsMALAndInteractionException, "Called by the provider MAL layer on reception of a message to handle the interaction", null, Arrays.asList("interaction the interaction object", "body the message body"), Arrays.asList(throwsMALException + " if there is a internal error", throwsInteractionException + " if there is a operation interaction error"));
    method.addMethodStatement(createMethodCall("switch (interaction.getOperation().getNumber().getValue())"), false);
    method.addMethodStatement("{", false);
    for (OperationSummary op : summary.getOperations())
    {
      if (op.getPattern() == InteractionPatternEnum.PROGRESS_OP)
      {
        String opArgs = createAdapterMethodsArgs(op.getArgTypes(), "body", false, true);
        ns = convertToNamespace(helperName + "._" + op.getName().toUpperCase() + "_OP_NUMBER:");
        method.addMethodWithDependencyStatement("  case " + ns, ns, false);
        method.addMethodStatement("    " + delegateCall + op.getName() + "(" + opArgs + "new " + convertClassName(StubUtils.preCap(op.getName()) + "Interaction") + "(interaction))");
        method.addMethodStatement("    break");
      }
    }
    method.addMethodStatement("  default:", false);
    ns = convertToNamespace(malHelper + ".UNSUPPORTED_OPERATION_ERROR_NUMBER");
    method.addMethodWithDependencyStatement("    interaction.sendError(new " + convertToNamespace(stdError + "(" + errorCodeAsReference(file, ns) + ", new " + malString + "(\"Unknown operation\"") + ")))", ns + stdErrorNs, true);
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    file.addClassCloseStatement();

    file.flush();
  }

  private void createRequestResponseDecompose(MethodWriter method, OperationSummary op, String opCall, String opRetType) throws IOException
  {
    List<TypeInfo> targetTypes = op.getRetTypes();

    if ((null != targetTypes) && (0 < targetTypes.size()))
    {
      if (1 == targetTypes.size())
      {
        method.addMethodStatement(createMethodCall("    interaction.sendResponse(" + opCall + ")"));
      }
      else
      {
        String arg = op.getName() + "Rt";
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < targetTypes.size(); i++)
        {
          TypeInfo ti = targetTypes.get(i);
          if (i > 0)
          {
            buf.append(", ");
          }
          if (ti.isNativeType())
          {
            buf.append("(").append(arg).append(".getBodyElement").append(i).append("()").append(" == null) ? null : new ").append(getConfig().getBasePackage()).append("mal.").append(getConfig().getStructureFolder()).append(".Union(").append(arg).append(".getBodyElement").append(i).append("()").append(")");
          }
          else
          {
            buf.append(arg).append(".getBodyElement").append(i).append("()");
          }
        }

        method.addMethodStatement("    " + opRetType + " " + arg + " = " + opCall);
        method.addMethodStatement(createMethodCall("    interaction.sendResponse(" + buf.toString() + ")"));
      }
    }
  }

  protected void createAreaHelperClass(File areaFolder, AreaType area) throws IOException
  {
    getLog().info("Creating area helper class: " + area.getName());
    ClassWriter file = createClassFile(areaFolder, area.getName() + "Helper");

    String areaName = area.getName();
    String areaNameCaps = area.getName().toUpperCase();
    String areaPackageName = areaName.toLowerCase();

    file.addPackageStatement(areaPackageName);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String uoctetType = createElementType(file, StdStrings.MAL, null, StdStrings.UOCTET);
    String ushortType = createElementType(file, StdStrings.MAL, null, StdStrings.USHORT);
    String uintegerType = createElementType(file, StdStrings.MAL, null, StdStrings.UINTEGER);
    String identifierType = createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);
    String areaType = createElementType(file, StdStrings.MAL, null, null, "MALArea");

    file.addClassOpenStatement(areaName + "Helper", false, false, null, null, "Helper class for " + areaName + " area.");

    file.addClassVariable(true, true, StdStrings.PUBLIC, "int", true, false, "_" + areaNameCaps + "_AREA_NUMBER", String.valueOf(area.getNumber()), "Area number literal.");
    file.addClassVariable(true, true, StdStrings.PUBLIC, ushortType, true, false, areaNameCaps + "_AREA_NUMBER", "(_" + areaNameCaps + "_AREA_NUMBER)", "Area number instance.");

    file.addClassVariable(true, true, StdStrings.PUBLIC, identifierType, true, false, areaNameCaps + "_AREA_NAME", "(\"" + areaName + "\")", "Area name constant.");

    file.addClassVariable(true, true, StdStrings.PUBLIC, "short", true, false, "_" + areaNameCaps + "_AREA_VERSION", String.valueOf(area.getVersion()), "Area version literal.");
    file.addClassVariable(true, true, StdStrings.PUBLIC, uoctetType, true, false, areaNameCaps + "_AREA_VERSION", "(_" + areaNameCaps + "_AREA_VERSION)", "Area version instance.");

    String areaObjectInitialValue = createAreaHelperClassInitialValue(areaNameCaps, area.getVersion());
    file.addClassVariable(true, true, StdStrings.PUBLIC, areaType, true, true, false, areaNameCaps + "_AREA", areaObjectInitialValue, "Area singleton instance");

    // create error numbers
    if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty())
    {
      for (ErrorDefinitionType error : area.getErrors().getError())
      {
        String errorNameCaps = error.getName().toUpperCase();
        file.addClassVariable(true, true, StdStrings.PUBLIC, "long", true, false, "_" + errorNameCaps + "_ERROR_NUMBER", String.valueOf(error.getNumber()), "Error literal for error " + errorNameCaps);
        file.addClassVariable(true, true, StdStrings.PUBLIC, uintegerType, true, false, errorNameCaps + "_ERROR_NUMBER", "(_" + errorNameCaps + "_ERROR_NUMBER)", "Error instance for error " + errorNameCaps);
      }
    }

    List<String> typeCalls = new LinkedList<String>();

    if ((null != area.getDataTypes()) && !area.getDataTypes().getFundamentalOrAttributeOrComposite().isEmpty())
    {
      for (Object oType : area.getDataTypes().getFundamentalOrAttributeOrComposite())
      {
        if (oType instanceof AttributeType)
        {
          AttributeType dt = (AttributeType) oType;

          String clsName = createElementType(file, area.getName(), null, StdStrings.ATTRIBUTE);
          String factoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "Factory");
          String lclsName = createElementType(file, area.getName(), null, dt.getName() + "List");
          String lfactoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "ListFactory");
          typeCalls.add(clsName + getConfig().getNamingSeparator() + dt.getName().toUpperCase() + "_SHORT_FORM, new " + factoryName + "()");
          typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
        }
        else if (oType instanceof CompositeType)
        {
          CompositeType dt = (CompositeType) oType;

          if (null != dt.getShortFormPart())
          {
            String clsName = createElementType(file, area.getName(), null, dt.getName());
            String factoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "Factory");
            String lclsName = createElementType(file, area.getName(), null, dt.getName() + "List");
            String lfactoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "ListFactory");
            typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
            typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
          }
        }
        else if (oType instanceof EnumerationType)
        {
          EnumerationType dt = (EnumerationType) oType;
          String clsName = createElementType(file, area.getName(), null, dt.getName());
          String factoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "Factory");
          String lclsName = createElementType(file, area.getName(), null, dt.getName() + "List");
          String lfactoryName = createElementType(file, area.getName(), null, getConfig().getFactoryFolder(), dt.getName() + "ListFactory");
          typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
          typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
        }
      }
    }

    String factoryType = createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");
    MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, StdStrings.VOID, "init", "org.ccsds.moims.mo.mal.MALElementFactoryRegistry bodyElementFactory", throwsMALException, "Registers all aspects of this area with the provided element factory", null, Arrays.asList("bodyElementFactory The element factory registry to initialise with this helper."), Arrays.asList(throwsMALException + " If cannot initialise this helper."));
    if (supportsToValue)
    {
      method.addMethodStatement(convertToNamespace(factoryType + ".instance()->registerArea(" + areaNameCaps + "_AREA)"));
    }
    else
    {
      method.addMethodStatement(convertToNamespace(factoryType + ".registerArea(" + areaNameCaps + "_AREA)"));
    }

    if (0 < typeCalls.size())
    {
      for (String typeCall : typeCalls)
      {
        method.addMethodStatement("bodyElementFactory.registerElementFactory(" + typeCall + ")");
      }
    }

    // register error numbers
    if ((null != area.getErrors()) && !area.getErrors().getError().isEmpty())
    {
      for (ErrorDefinitionType error : area.getErrors().getError())
      {
        String errorNameCaps = error.getName().toUpperCase();
        method.addMethodStatement(convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
      }
    }

    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, StdStrings.VOID, "deepInit", "org.ccsds.moims.mo.mal.MALElementFactoryRegistry bodyElementFactory", throwsMALException, "Registers all aspects of this area with the provided element factory and any referenced areas and contained services.", null, Arrays.asList("bodyElementFactory The element factory registry to initialise with this helper."), Arrays.asList(throwsMALException + " If cannot initialise this helper."));
    method.addMethodStatement("init(bodyElementFactory)");
    for (ServiceType service : area.getService())
    {
      String helperType = createElementType(file, area.getName(), service.getName(), null, service.getName() + "Helper");
      String ns = convertToNamespace(helperType + ".deepInit(bodyElementFactory)");
      method.addMethodWithDependencyStatement(ns, ns, true);
    }
    method.addMethodCloseStatement();

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createServiceHelperClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating service helper class: " + service.getName());
    ClassWriter file = createClassFile(serviceFolder, service.getName() + "Helper");

    String serviceName = service.getName();
    String servicePackageName = area.getName().toLowerCase() + "." + serviceName.toLowerCase();
    String serviceVar = serviceName.toUpperCase();

    file.addPackageStatement(servicePackageName);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String ushortType = createElementType(file, StdStrings.MAL, null, StdStrings.USHORT);
    String uintegerType = createElementType(file, StdStrings.MAL, null, StdStrings.UINTEGER);
    String identifierType = createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER);

    file.addClassOpenStatement(serviceName + "Helper", false, false, null, null, "Helper class for " + serviceName + " service.");

    // create error numbers
    if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty())
    {
      for (ErrorDefinitionType error : service.getErrors().getError())
      {
        String errorNameCaps = error.getName().toUpperCase();
        file.addClassVariable(true, true, StdStrings.PUBLIC, "int", true, false, "_" + errorNameCaps + "_ERROR_NUMBER", String.valueOf(error.getNumber()), "Error literal for error " + errorNameCaps);
        file.addClassVariable(true, true, StdStrings.PUBLIC, uintegerType, true, false, errorNameCaps + "_ERROR_NUMBER", "(_" + errorNameCaps + "_ERROR_NUMBER)", "Error instance for error " + errorNameCaps);
      }
    }

    // COM service should not have its operations generated, these are generated as part of the specific services
    if (!summary.isComService())
    {
      file.addClassVariable(true, true, StdStrings.PUBLIC, "int", true, false, "_" + serviceVar + "_SERVICE_NUMBER", String.valueOf(service.getNumber()), "Service number literal.");
      file.addClassVariable(true, true, StdStrings.PUBLIC, ushortType, true, false, serviceVar + "_SERVICE_NUMBER", "(_" + serviceVar + "_SERVICE_NUMBER)", "Service number instance.");
      file.addClassVariable(true, true, StdStrings.PUBLIC, identifierType, true, false, serviceVar + "_SERVICE_NAME", "(\"" + serviceName + "\")", "Service name constant.");

      String serviceObjectInitialValue = createServiceHelperClassInitialValue(serviceVar);
      file.addClassVariable(true, true, StdStrings.PUBLIC, createElementType(file, StdStrings.MAL, null, null, "MALService"), true, true, false, serviceVar + "_SERVICE", serviceObjectInitialValue, "Service singleton instance.");

      for (OperationSummary op : summary.getOperations())
      {
        String operationInstanceVar = op.getName().toUpperCase();
        file.addClassVariable(true, true, StdStrings.PUBLIC, "int", true, false, "_" + operationInstanceVar + "_OP_NUMBER", op.getNumber().toString(), "Operation number literal for operation " + operationInstanceVar);
        file.addClassVariable(true, true, StdStrings.PUBLIC, ushortType, true, false, operationInstanceVar + "_OP_NUMBER", "(_" + operationInstanceVar + "_OP_NUMBER)", "Operation number instance for operation " + operationInstanceVar);
        List<String> opArgs = new LinkedList<String>();
        addServiceHelperOperationArgs(file, op, opArgs);
        String opInstanceType = getOperationInstanceType(op);
        file.addTypeDependency(opInstanceType);
        file.addClassVariable(true, true, StdStrings.PUBLIC, opInstanceType, true, false, false, operationInstanceVar + "_OP", opArgs, "Operation instance for operation " + operationInstanceVar);
      }
    }

    MethodWriter method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, StdStrings.VOID, "init", "org.ccsds.moims.mo.mal.MALElementFactoryRegistry bodyElementFactory", throwsMALException, "Registers all aspects of this service with the provided element factory", null, Arrays.asList("bodyElementFactory The element factory registry to initialise with this helper."), Arrays.asList(throwsMALException + " If cannot initialise this helper."));
    if (!summary.isComService())
    {
      addServiceConstructor(method, serviceVar, String.valueOf(area.getVersion()), summary);
      String hlp = createElementType(file, area.getName(), null, null, area.getName() + "Helper");
      String ns = convertToNamespace(hlp + "." + area.getName().toUpperCase() + "_AREA");
      method.addMethodWithDependencyStatement(ns + ".addService(" + addressOf(serviceVar + "_SERVICE)"), ns, true);
    }

    List<String> typeCalls = new LinkedList<String>();
    if ((null != service.getDataTypes()) && !service.getDataTypes().getCompositeOrEnumeration().isEmpty())
    {
      for (Object oType : service.getDataTypes().getCompositeOrEnumeration())
      {
        String typeName = "";
        boolean isAbstract = false;
        if (oType instanceof EnumerationType)
        {
          typeName = ((EnumerationType) oType).getName();
        }
        else if (oType instanceof CompositeType)
        {
          typeName = ((CompositeType) oType).getName();
          isAbstract = null == ((CompositeType) oType).getShortFormPart();
        }

        if (!isAbstract)
        {
          String clsName = createElementType(file, area.getName(), service.getName(), typeName);
          String factoryName = createElementType(file, area.getName(), service.getName(), getConfig().getFactoryFolder(), typeName + "Factory");
          String lclsName = createElementType(file, area.getName(), service.getName(), typeName + "List");
          String lfactoryName = createElementType(file, area.getName(), service.getName(), getConfig().getFactoryFolder(), typeName + "ListFactory");
          typeCalls.add(clsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + factoryName + "()");
          typeCalls.add(lclsName + getConfig().getNamingSeparator() + "SHORT_FORM, new " + lfactoryName + "()");
        }
      }
    }

    if (0 < typeCalls.size())
    {
      for (String typeCall : typeCalls)
      {
        method.addMethodStatement("bodyElementFactory.registerElementFactory(" + typeCall + ")");
      }
    }

    // register error numbers
    if ((null != service.getErrors()) && !service.getErrors().getError().isEmpty())
    {
      String factoryType = createElementType(file, StdStrings.MAL, null, null, "MALContextFactory");

      for (ErrorDefinitionType error : service.getErrors().getError())
      {
        String errorNameCaps = error.getName().toUpperCase();
        method.addMethodStatement(convertToNamespace(factoryType + ".registerError(" + errorNameCaps + "_ERROR_NUMBER, new " + identifierType + "(\"" + error.getName() + "\"))"));
      }
    }

    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, true, StdStrings.VOID, "deepInit", "org.ccsds.moims.mo.mal.MALElementFactoryRegistry bodyElementFactory", throwsMALException, "Registers all aspects of this service with the provided element factory and any referenced areas/services.", null, Arrays.asList("bodyElementFactory The element factory registry to initialise with this helper."), Arrays.asList(throwsMALException + " If cannot initialise this helper."));
    method.addMethodStatement("init(bodyElementFactory)");
    method.addMethodCloseStatement();

    file.addClassCloseStatement();

    file.flush();
  }

  protected void createFundamentalClass(File folder, AreaType area, ServiceType service, FundamentalType enumeration) throws IOException
  {
    // fundamental types are usually hand created as part of a language mapping, but we have this here in case this
    // is not the case for a particular language
  }

  protected void createEnumerationClass(File folder, AreaType area, ServiceType service, EnumerationType enumeration) throws IOException
  {
    String enumName = enumeration.getName();

    getLog().info("Creating enumeration class " + enumName);

    ClassWriter file = createClassFile(folder, enumName);

    file.addPackageStatement(area, service, getConfig().getStructureFolder());

    file.addClassOpenStatement(enumName, true, false, createElementType(file, StdStrings.MAL, null, StdStrings.ENUMERATION), null, "Enumeration class for " + enumName + ".");

    String elementType = createElementType(file, StdStrings.MAL, null, StdStrings.ELEMENT);
    String uintType = createElementType(file, StdStrings.MAL, null, StdStrings.UINTEGER);
    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String intCallMethod = getIntCallMethod();
    String fqEnumName = createElementType(file, area, service, enumName);

    addTypeShortForm(file, enumeration.getShortFormPart());
    file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "AREA_SHORT_FORM", "(" + area.getNumber() + ")", "Short form for area.");
    file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.UOCTET, true, false, false, "AREA_VERSION", "((short)" + area.getVersion() + ")", "Version for area.");
    long asf = ((long) area.getNumber()) << AREA_BIT_SHIFT;
    asf += ((long) area.getVersion()) << VERSION_BIT_SHIFT;
    asf += enumeration.getShortFormPart();
    if (null != service)
    {
      file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "SERVICE_SHORT_FORM", "(" + service.getNumber() + ")", "Short form for service.");
      asf += ((long) service.getNumber()) << SERVICE_BIT_SHIFT;
    }
    else
    {
      file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "SERVICE_SHORT_FORM", "(0)", "Short form for service.");
    }

    addShortForm(file, asf);

    if (supportsToValue)
    {
      file.addStaticConstructor(createElementType(file, StdStrings.MAL, null, elementType), "create", createElementType(file, StdStrings.MAL, null, null, "MALDecoder") + " decoder", createMethodCall(convertClassName(fqEnumName) + getConfig().getNamingSeparator() + "fromInt(decoder.decodeOrdinal())"));
    }
    else
    {
      file.addStaticConstructor(createElementType(file, StdStrings.MAL, null, elementType), "create", createElementType(file, StdStrings.MAL, null, null, "MALDecoder") + " decoder", createMethodCall(convertClassName(fqEnumName) + getConfig().getNamingSeparator() + "fromInt(decoder.decodeInteger()." + intCallMethod + "())"));
    }

    // create attributes
    for (int i = 0; i < enumeration.getItem().size(); i++)
    {
      Item item = enumeration.getItem().get(i);
      String value = item.getValue();

      file.addClassVariable(true, true, StdStrings.PUBLIC, "int", true, false, "_" + value + "_INDEX", String.valueOf(i), "Enumeration ordinal index for value " + value);
      file.addClassVariable(true, true, StdStrings.PUBLIC, uintType, true, true, false, value + "_NUM_VALUE", "(" + item.getNvalue() + ")", "Enumeration numeric value for value " + value);
      file.addClassVariable(true, true, StdStrings.PUBLIC, fqEnumName, true, true, false, value, "(" + convertToNamespace(convertClassName(fqEnumName) + "._" + value + "_INDEX)"), "Enumeration singleton for value " + value);
    }

    // create arrays
    List<String> opStr = new LinkedList<String>();
    List<String> stStr = new LinkedList<String>();
    List<String> vaStr = new LinkedList<String>();
    for (int i = 0; i < enumeration.getItem().size(); i++)
    {
      opStr.add(enumeration.getItem().get(i).getValue());
      stStr.add("\"" + enumeration.getItem().get(i).getValue() + "\"");
      vaStr.add(enumeration.getItem().get(i).getValue() + "_NUM_VALUE");
    }
    file.addClassVariable(true, true, StdStrings.PRIVATE, fqEnumName, false, true, true, "_ENUMERATIONS", opStr, "Set of enumeration instances.");
    file.addClassVariable(true, true, StdStrings.PRIVATE, StdStrings.STRING, false, true, true, "_ENUMERATION_NAMES", stStr, "Set of enumeration string values.");
    file.addClassVariable(true, true, StdStrings.PRIVATE, uintType, false, true, true, "_ENUMERATION_NUMERIC_VALUES", vaStr, "Set of enumeration values.");

    // create private constructor
    MethodWriter method = file.addConstructor(StdStrings.PRIVATE, enumName, "int" + " ordinal", "ordinal", null);
    method.addMethodCloseStatement();

    if (requiresDefaultConstructors)
    {
      method = file.addConstructor(StdStrings.PUBLIC, enumName, "", "", null);
      method.addMethodCloseStatement();
    }

    // add getters and setters
    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, "_String", "toString", "", null, "Returns a String object representing this type's value.", "a string representation of the value of this object", null, null);
    method.addMethodStatement("switch (getOrdinal())", false);
    method.addMethodStatement("{", false);
    for (Item item : enumeration.getItem())
    {
      method.addMethodStatement("  case _" + item.getValue() + "_INDEX:", false);
      method.addMethodStatement("    return \"" + item.getValue() + "\"");
    }
    method.addMethodStatement("  default:", false);
    method.addMethodStatement("    throw new RuntimeException(\"Unknown ordinal!\")");
    method.addMethodStatement("}", false);
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, fqEnumName, "fromString", "_String s", null, "Returns the enumeration element represented by the supplied string, or null if not matched.", "The matched enumeration element, or null if not matched.", Arrays.asList("s The string to search for."), null);
    method.addMethodStatement("for (int i = 1; i < _ENUMERATION_NAMES.length; i++)", false);
    method.addMethodStatement("{", false);
    method.addMethodStatement("  if (_ENUMERATION_NAMES[i].equals(s))", false);
    method.addMethodStatement("  {", false);
    method.addMethodStatement("    return _ENUMERATIONS[i]");
    method.addMethodStatement("  }", false);
    method.addMethodStatement("}", false);
    method.addMethodStatement("return null");
    method.addMethodCloseStatement();

    // create getMALValue method
    if (supportsToValue)
    {
      method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, false, "org.ccsds.moims.mo.mal.structures.Element", "getMALValue", "", null);
      method.addMethodStatement("return this");
      method.addMethodCloseStatement();
    }

    method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, false, fqEnumName, "fromOrdinal", "int" + " ordinal", null, "Returns the nth element of the enumeration", "The matched enumeration element", Arrays.asList("ordinal The index of the enumeration element to return."), null);
    method.addArrayMethodStatement("_ENUMERATIONS", "ordinal", "_INDEX_COUNT");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, true, StdStrings.PUBLIC, false, false, fqEnumName, "fromNumericValue", uintType + " value", null, "Returns the enumeration element represented by the supplied numeric value, or null if not matched.", "The matched enumeration value, or null if not matched.", Arrays.asList("value The numeric value to search for."), null);
    method.addMethodStatement("for (int i = 1; i < _ENUMERATION_NUMERIC_VALUES.length; i++)", false);
    method.addMethodStatement("{", false);
    method.addMethodStatement("  if (_ENUMERATION_NUMERIC_VALUES[i].equals(value))", false);
    method.addMethodStatement("  {", false);
    method.addMethodStatement("    return _ENUMERATIONS[i]");
    method.addMethodStatement("  }", false);
    method.addMethodStatement("}", false);
    method.addMethodStatement("return null");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, uintType, "getNumericValue", "", null, "Returns the numeric value of the enumeration element.", "The numeric value", null, null);
    method.addArrayMethodStatement("_ENUMERATION_NUMERIC_VALUES", "ordinal", "_INDEX_COUNT");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, elementType, "createElement", "", null, "Returns an instance of this type using the first element of the enumeration. It is a generic factory method but just returns an existing element of the enumeration as new values of enumerations cannot be created at runtime.", "The first element of the enumeration.", null, null);
    method.addMethodStatement("return _ENUMERATIONS[0]");
    method.addMethodCloseStatement();

    // create encode method
    long maxValue = 0;
    for (Item itm : enumeration.getItem())
    {
      if (itm.getNvalue() > maxValue)
      {
        maxValue = itm.getNvalue();
      }
    }
    String enumEncoderValue = "UInteger(new org.ccsds.moims.mo.mal.structures.UInteger(ordinal.longValue())";
    String enumDecoderValue = StdStrings.UINTEGER;
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "encode", "org.ccsds.moims.mo.mal.MALEncoder encoder", throwsMALException, "Encodes the value of this object using the provided MALEncoder.", null, Arrays.asList("encoder - the encoder to use for encoding"), Arrays.asList(throwsMALException + " if any encoding errors are detected."));
    if (maxValue < 256)
    {
      enumEncoderValue = "UOctet(new org.ccsds.moims.mo.mal.structures.UOctet(ordinal.shortValue()))";
      enumDecoderValue = StdStrings.UOCTET;
    }
    else if (maxValue < 65536)
    {
      enumEncoderValue = "UShort(new org.ccsds.moims.mo.mal.structures.UShort(ordinal.intValue())";
      enumDecoderValue = StdStrings.USHORT;
    }

    method.addMethodStatement("encoder.encode" + enumEncoderValue);
    method.addMethodCloseStatement();

    // create decode method
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, elementType, "decode", "org.ccsds.moims.mo.mal.MALDecoder decoder", throwsMALException, "Decodes the value of this object using the provided MALDecoder.", "Returns this object.", Arrays.asList("decoder - the decoder to use for decoding"), Arrays.asList(throwsMALException + " if any decoding errors are detected."));
    method.addMethodStatement("return fromOrdinal(decoder.decode" + enumDecoderValue + "().getValue())");
    method.addMethodCloseStatement();

    addShortFormMethods(file);

    file.addClassCloseStatement();

    file.flush();

    createListClass(folder, area, service, enumName, false, enumeration.getShortFormPart());
    createFactoryClass(folder, area, service, enumName, false, true);
  }

  protected void createCompositeClass(File folder, AreaType area, ServiceType service, CompositeType composite) throws IOException
  {
    String compName = composite.getName();

    getLog().info("Creating composite class " + compName);

    ClassWriter file = createClassFile(folder, compName);

    String parentClass = null;
    String parentType = null;
    String parentInterface = createElementType(file, StdStrings.MAL, null, StdStrings.COMPOSITE);
    if ((null != composite.getExtends()) && (!StdStrings.COMPOSITE.equals(composite.getExtends().getType().getName())))
    {
      parentClass = createElementType(file, composite.getExtends().getType());
      parentType = composite.getExtends().getType().getName();
      parentInterface = null;
    }

    file.addPackageStatement(area, service, getConfig().getStructureFolder());

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String elementType = createElementType(file, StdStrings.MAL, null, StdStrings.ELEMENT);

    List<CompositeField> compElements = createCompositeElementsList(file, composite);
    List<CompositeField> superCompElements = new LinkedList<CompositeField>();
    createCompositeSuperElementsList(file, parentType, superCompElements);

    boolean abstractComposite = (null == composite.getShortFormPart());
    file.addClassOpenStatement(compName, !abstractComposite, abstractComposite, parentClass, parentInterface, composite.getComment());
    String fqName = createElementType(file, area, service, compName);

    if (!abstractComposite)
    {
      addTypeShortForm(file, composite.getShortFormPart());
      file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "AREA_SHORT_FORM", "(" + area.getNumber() + ")", "Short form for area.");
      file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.UOCTET, true, false, false, "AREA_VERSION", "((short)" + area.getVersion() + ")", "Version for area.");
      long asf = ((long) area.getNumber()) << AREA_BIT_SHIFT;
      asf += ((long) area.getVersion()) << VERSION_BIT_SHIFT;
      asf += composite.getShortFormPart();
      if (null != service)
      {
        file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "SERVICE_SHORT_FORM", "(" + service.getNumber() + ")", "Short form for service.");
        asf += ((long) service.getNumber()) << SERVICE_BIT_SHIFT;
      }
      else
      {
        file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.USHORT, true, false, false, "SERVICE_SHORT_FORM", "(0)", "Short form for service.");
      }

      addShortForm(file, asf);
    }

    // create attributes
    if (!compElements.isEmpty())
    {
      for (CompositeField element : compElements)
      {
        file.addClassVariable(false, false, StdStrings.PRIVATE, element.getTypeName(), false, false, false, element.getFieldName(), (String) null, element.getComment());
      }
    }

    // create blank constructor
    file.addConstructorDefault(compName);

    // if we or our parents have attributes then we need a typed constructor
    if (!compElements.isEmpty() || !superCompElements.isEmpty())
    {
      List<String> argComments = new LinkedList<String>();
      StringBuilder conArgsList = new StringBuilder();
      StringBuilder superArgsList = new StringBuilder();
      boolean firstTime = true;

      for (CompositeField element : superCompElements)
      {
        if (firstTime)
        {
          firstTime = false;
        }
        else
        {
          conArgsList.append(", ");
          superArgsList.append(", ");
        }

        conArgsList.append(element.getTypeName()).append(" ").append(element.getFieldName());
        superArgsList.append(element.getFieldName());
        argComments.add(element.getFieldName() + " " + element.getComment());
      }

      for (CompositeField element : compElements)
      {
        if (firstTime)
        {
          firstTime = false;
        }
        else
        {
          conArgsList.append(", ");
        }

        conArgsList.append(element.getTypeName()).append(" ").append(element.getFieldName());
        argComments.add(element.getFieldName() + " " + element.getComment());
      }

      MethodWriter method = file.addConstructor(StdStrings.PUBLIC, compName, conArgsList.toString(), superArgsList.toString(), null, "Constructor that initialises the values of the structure.", argComments, null);

      for (CompositeField element : compElements)
      {
        method.addMethodStatement(createMethodCall("this." + element.getFieldName() + " = " + element.getFieldName()));
      }

      method.addMethodCloseStatement();

      // create copy constructor
      if (supportsToValue && !abstractComposite)
      {
        method = file.addConstructor(StdStrings.PUBLIC, compName, "const " + compName + "& object", "object", null);

        for (CompositeField element : compElements)
        {
          method.addMethodStatement("if (0 != object." + element.getFieldName() + ")", false);
          method.addMethodStatement("  this->" + element.getFieldName() + " = (" + createReturnReference(convertToNamespace(convertClassName(element.getTypeName()))) + ")object." + element.getFieldName() + "->getMALValue()");
          method.addMethodStatement("else", false);
          method.addMethodStatement("  this->" + element.getFieldName() + " = 0");
        }

        method.addMethodCloseStatement();
      }
    }

    if (!abstractComposite)
    {
      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, "org.ccsds.moims.mo.mal.structures.Element", "createElement", "", null, "Creates an instance of this type using the default constructor. It is a generic factory method.", "A new instance of this type with default field values.", null, null);
      method.addMethodStatement("return new " + convertClassName(fqName) + "()");
      method.addMethodCloseStatement();
    }

    // add getters and setters
    for (CompositeField element : compElements)
    {
      addGetter(file, element);
      addSetter(file, element);
    }

    // create equals method
    if (supportsEquals)
    {
      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, "boolean", "equals", "Object obj", null, "Compares this object to the specified object. The result is true if and only if the argument is not null and is the same type that contains the same value as this object.", "true if the objects are the same; false otherwise.", Arrays.asList("obj - the object to compare with."), null);
      method.addMethodStatement("if (obj instanceof " + compName + ")", false);
      method.addMethodStatement("{", false);
      if (null != parentClass)
      {
        method.addMethodStatement("  if (! super.equals(obj))", false);
        method.addMethodStatement("  {", false);
        method.addMethodStatement("    return false");
        method.addMethodStatement("  }", false);
      }
      if (!compElements.isEmpty())
      {
        method.addMethodStatement("  " + compName + " other = (" + compName + ") obj");
        for (CompositeField element : compElements)
        {
          method.addMethodStatement("  if (" + element.getFieldName() + " == null)", false);
          method.addMethodStatement("  {", false);
          method.addMethodStatement("    if (other." + element.getFieldName() + " != null)", false);
          method.addMethodStatement("    {", false);
          method.addMethodStatement("      return false");
          method.addMethodStatement("    }", false);
          method.addMethodStatement("  }", false);
          method.addMethodStatement("  else", false);
          method.addMethodStatement("  {", false);
          method.addMethodStatement("    if (! " + element.getFieldName() + ".equals(other." + element.getFieldName() + "))", false);
          method.addMethodStatement("    {", false);
          method.addMethodStatement("      return false");
          method.addMethodStatement("    }", false);
          method.addMethodStatement("  }", false);
        }
      }
      method.addMethodStatement("  return true");
      method.addMethodStatement("}", false);
      method.addMethodStatement("return false");
      method.addMethodCloseStatement();

      method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, "int", "hashCode", "", null, "Returns a hash code for this object", "a hash code value for this object.", null, null);
      if (null != parentClass)
      {
        method.addMethodStatement("int hash = super.hashCode()");
      }
      else
      {
        method.addMethodStatement("int hash = 7");
      }
      for (CompositeField element : compElements)
      {
        method.addMethodStatement("hash = 83 * hash + (" + element.getFieldName() + " != null ? " + element.getFieldName() + ".hashCode() : 0)");
      }
      method.addMethodStatement("return hash");
      method.addMethodCloseStatement();
    }

    // create toString method
    if (supportsToString)
    {
      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, "_String", "toString", "", null, "Returns a String object representing this type's value.", "a string representation of the value of this object", null, null);
      method.addMethodStatement("StringBuilder buf = new StringBuilder()");
      method.addMethodStatement("buf.append('(')");
      if (null != parentClass)
      {
        method.addMethodStatement("buf.append(super.toString())");
      }
      for (CompositeField element : compElements)
      {
        method.addMethodStatement("buf.append(\"," + element.getFieldName() + "=\")");
        method.addMethodStatement("buf.append(" + element.getFieldName() + ")");
      }
      method.addMethodStatement("buf.append(')')");
      method.addMethodStatement("return buf.toString()");
      method.addMethodCloseStatement();
    }

    // create getMALValue method
    if (supportsToValue && !abstractComposite)
    {
      addCompositeCloneMethod(file, fqName);
    }

    // create encode method
    MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "encode", "org.ccsds.moims.mo.mal.MALEncoder encoder", throwsMALException, "Encodes the value of this object using the provided MALEncoder.", null, Arrays.asList("encoder - the encoder to use for encoding"), Arrays.asList(throwsMALException + " if any encoding errors are detected."));
    if (null != parentClass)
    {
      method.addSuperMethodStatement("encode", "encoder");
    }
    for (CompositeField element : compElements)
    {
      method.addMethodStatement(createMethodCall("encoder.encode" + (element.isCanBeNull() ? "Nullable" : "") + element.getEncodeCall() + "(" + element.getFieldName() + ")"));
    }
    method.addMethodCloseStatement();

    // create decode method
    method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, elementType, "decode", "org.ccsds.moims.mo.mal.MALDecoder decoder", throwsMALException, "Decodes the value of this object using the provided MALDecoder.", "Returns this object.", Arrays.asList("decoder - the decoder to use for decoding"), Arrays.asList(throwsMALException + " if any decoding errors are detected."));
    if (null != parentClass)
    {
      method.addSuperMethodStatement("decode", "decoder");
    }
    for (CompositeField element : compElements)
    {
      method.addMethodStatement(element.getFieldName() + " = " + element.getDecodeCast() + "decoder.decode" + (element.isCanBeNull() ? "Nullable" : "") + element.getDecodeCall() + "(" + (element.isDecodeNeedsNewCall() ? element.getNewCall() : "") + ")");
    }
    method.addMethodStatement("return this");
    method.addMethodCloseStatement();

    if (!abstractComposite)
    {
      addShortFormMethods(file);
    }

    file.addClassCloseStatement();

    file.flush();

    createListClass(folder, area, service, compName, abstractComposite, composite.getShortFormPart());

    if (!abstractComposite)
    {
      createFactoryClass(folder, area, service, compName, false, false);
    }
  }

  protected abstract void createListClass(File folder, AreaType area, ServiceType service, String srcTypeName, boolean isAbstract, Long shortFormPart) throws IOException;

  protected void createFactoryClass(File structureFolder, AreaType area, ServiceType service, String srcTypeName, boolean isAttr, boolean isEnum) throws IOException
  {
    // create area structure folder
    File folder = StubUtils.createFolder(structureFolder.getParentFile(), getConfig().getFactoryFolder());
    // create a comment for the structure factory folder if supported
    createStructureFactoryFolderComment(folder, area, service);

    String factoryName = srcTypeName + "Factory";

    getLog().info("Creating factory class " + factoryName);

    ClassWriter file = createClassFile(folder, factoryName);

    file.addPackageStatement(area, service, getConfig().getFactoryFolder());

    file.addClassOpenStatement(factoryName, true, false, null, createElementType(file, StdStrings.MAL, null, null, "MALElementFactory"), "Factory class for " + srcTypeName + ".");

    MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, createElementType(file, StdStrings.MAL, null, StdStrings.ELEMENT), "createElement", "", null, "Creates an instance of the source type using the default constructor. It is a generic factory method.", "A new instance of the source type with default field values.", null, null);
    if (isAttr)
    {
      AttributeTypeDetails details = getAttributeDetails(srcTypeName);
      if (details.isNativeType())
      {
        method.addMethodStatement("return new " + createElementType(file, StdStrings.MAL, null, StdStrings.UNION) + "(" + details.getDefaultValue() + ")");
      }
      else
      {
        method.addMethodStatement("return new " + createElementType(file, area, service, srcTypeName) + "()");
      }
    }
    else if (isEnum)
    {
      EnumerationType typ = getEnum(srcTypeName);
      method.addMethodStatement("return " + createElementType(file, area, service, srcTypeName) + "." + typ.getItem().get(0).getValue());
    }
    else
    {
      method.addMethodStatement("return new " + createElementType(file, area, service, srcTypeName) + "()");
    }
    method.addMethodCloseStatement();

    file.addClassCloseStatement();

    file.flush();
  }

  protected final void createMultiReturnType(String destinationFolderName, String returnTypeFqName, MultiReturnType returnTypeInfo) throws IOException
  {
    getLog().info("Creating multiple return class class " + returnTypeFqName);

    String publisherPackage = returnTypeFqName.substring(0, returnTypeFqName.lastIndexOf('.'));
    // create a comment for the body folder if supported
    createServiceMessageBodyFolderComment(destinationFolderName, publisherPackage.toLowerCase());

    ClassWriter file = createClassFile(destinationFolderName, returnTypeFqName.replace('.', '/'));

    file.addPackageStatement(publisherPackage.toLowerCase(), "");

    file.addClassOpenStatement(returnTypeInfo.getShortName(), true, false, null, null, "Multi body return class for " + returnTypeInfo.getShortName() + ".");

    // create attributes
    for (int i = 0; i < returnTypeInfo.getReturnTypes().size(); i++)
    {
      TypeInfo element = returnTypeInfo.getReturnTypes().get(i);
      file.addClassVariable(false, false, StdStrings.PRIVATE, element.getTargetType(), false, false, false, "bodyElement" + i, (String) null, null);
    }

    // create blank constructor
    file.addConstructorDefault(returnTypeInfo.getShortName());

    // if we or our parents have attributes then we need a typed constructor
    StringBuilder conArgsList = new StringBuilder();
    List<String> argComments = new LinkedList<String>();
    for (int i = 0; i < returnTypeInfo.getReturnTypes().size(); i++)
    {
      TypeInfo element = returnTypeInfo.getReturnTypes().get(i);
      if (i > 0)
      {
        conArgsList.append(", ");
      }

      conArgsList.append(element.getTargetType()).append(" arg").append(i);
      argComments.add("arg" + i + " Initial value for argument " + i);
    }

    MethodWriter method = file.addConstructor(StdStrings.PUBLIC, returnTypeInfo.getShortName(), conArgsList.toString(), null, null, "Constructs an instance of this type using provided values.", argComments, null);

    for (int i = 0; i < returnTypeInfo.getReturnTypes().size(); i++)
    {
      method.addMethodStatement(createMethodCall("this.bodyElement" + i + " = arg" + i));
    }

    method.addMethodCloseStatement();

    // add getters and setters
    for (int i = 0; i < returnTypeInfo.getReturnTypes().size(); i++)
    {
      TypeInfo element = returnTypeInfo.getReturnTypes().get(i);
      addGetter(file, element.getTargetType(), "bodyElement" + i);
      addSetter(file, element.getTargetType(), "bodyElement" + i);
    }

    file.addClassCloseStatement();

    file.flush();
  }

  protected void addTypeShortForm(ClassWriter file, long sf) throws IOException
  {
    file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.INTEGER, true, false, false, "TYPE_SHORT_FORM", "(" + sf + ")", "Short form for type.");
  }

  protected void addShortForm(ClassWriter file, long sf) throws IOException
  {
    file.addClassVariable(true, true, StdStrings.PUBLIC, StdStrings.LONG, true, false, false, "SHORT_FORM", "(" + sf + "L)", "Absolute short form for type.");
  }

  protected void addShortFormMethods(ClassWriter file) throws IOException
  {
    MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, StdStrings.LONG, "getShortForm", "", null, "Returns the absolute short form of this type.", "The absolute short form of this type.", null, null);
    method.addMethodStatement("return SHORT_FORM");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, StdStrings.INTEGER, "getTypeShortForm", "", null, "Returns the type short form of this type which is unique to the area/service it is defined in but not unique across all types.", "The type short form of this type.", null, null);
    method.addMethodStatement("return TYPE_SHORT_FORM");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, StdStrings.USHORT, "getAreaNumber", "", null, "Returns the area number of this type.", "The area number of this type.", null, null);
    method.addMethodStatement("return AREA_SHORT_FORM");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, StdStrings.UOCTET, "getAreaVersion", "", null, "Returns the area version of this type.", "The area number of this type.", null, null);
    method.addMethodStatement("return AREA_VERSION");
    method.addMethodCloseStatement();

    method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, true, StdStrings.USHORT, "getServiceNumber", "", null, "Returns the service number of this type.", "The service number of this type.", null, null);
    method.addMethodStatement("return SERVICE_SHORT_FORM");
    method.addMethodCloseStatement();
  }

  protected static void addGetter(ClassWriter file, CompositeField element) throws IOException
  {
    addGetter(file, element.getTypeName(), element.getFieldName());
  }

  protected static void addGetter(ClassWriter file, String typeName, String attributeName) throws IOException
  {
    String getOpPrefix = "get";
    String getOpName = StubUtils.preCap(attributeName);

    MethodWriter method = file.addMethodOpenStatement(true, false, StdStrings.PUBLIC, false, false, typeName, getOpPrefix + getOpName, "", null, "Returns the field " + attributeName, "The field " + attributeName, null, null);
    method.addMethodStatement("return " + attributeName);
    method.addMethodCloseStatement();
  }

  protected static void addSetter(ClassWriter file, CompositeField element) throws IOException
  {
    addSetter(file, element.getTypeName(), element.getFieldName());
  }

  protected static void addSetter(ClassWriter file, String typeName, String attributeName) throws IOException
  {
    String getOpName = StubUtils.preCap(attributeName);

    if (StdStrings.BOOLEAN.equals(typeName) && getOpName.startsWith("Is"))
    {
      getOpName = getOpName.substring(2);
    }

    MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "set" + getOpName, typeName + " __newValue", null, "Sets the field " + attributeName, null, Arrays.asList("__newValue The new value"), null);
    method.addMethodStatement(attributeName + " = __newValue");
    method.addMethodCloseStatement();
  }

  protected void addCompositeCloneMethod(ClassWriter file, String fqName) throws IOException
  {
  }

  protected String createServiceProviderSkeletonSendHandler(ClassWriter file)
  {
    return createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, StdStrings.MALINTERACTION);
  }

  protected void addServiceHelperOperationArgs(LanguageWriter file, OperationSummary op, List<String> opArgs)
  {
    opArgs.add(op.getName().toUpperCase() + "_OP_NUMBER");
    opArgs.add("new " + createElementType(file, StdStrings.MAL, null, StdStrings.IDENTIFIER) + "(\"" + op.getName() + "\")");
    opArgs.add("" + op.getReplay());
    opArgs.add("new " + createElementType(file, StdStrings.MAL, null, StdStrings.USHORT) + "(" + op.getSet() + ")");

    switch (op.getPattern())
    {
      case SEND_OP:
        addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
        break;
      case SUBMIT_OP:
        addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
        break;
      case REQUEST_OP:
        addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
        addMalTypes(this, opArgs, 2, op.getRetTypes(), false);
        break;
      case INVOKE_OP:
        addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
        addMalTypes(this, opArgs, 2, op.getAckTypes(), false);
        addMalTypes(this, opArgs, 3, op.getRetTypes(), false);
        break;
      case PROGRESS_OP:
        addMalTypes(this, opArgs, 1, op.getArgTypes(), false);
        addMalTypes(this, opArgs, 2, op.getAckTypes(), false);
        addMalTypes(this, opArgs, 3, op.getUpdateTypes(), false);
        addMalTypes(this, opArgs, 4, op.getRetTypes(), false);
        break;
      case PUBSUB_OP:
        addMalTypes(this, opArgs, 1, op.getRetTypes(), true);
        break;
    }
  }

  protected void addMalTypes(TypeInformation tiSource, List<String> opArgs, int index, List<TypeInfo> ti, boolean isPubSub)
  {
    ArrayList<String> typeArgs = new ArrayList();
    boolean needXmlSchema = false;
    boolean needMalTypes = false;

    for (TypeInfo typeInfo : ti)
    {
      if (StdStrings.XML.equals(typeInfo.getSourceType().getArea()))
      {
        needXmlSchema = true;
      }
      else
      {
        needMalTypes = true;
      }

      if (tiSource.isAbstract(typeInfo.getSourceType()))
      {
        typeArgs.add("null");
      }
      else
      {
        if (isPubSub)
        {
          // this is a bit of a hack for now
          if (tiSource.isNativeType(typeInfo.getSourceType()))
          {
            TypeReference tr = typeInfo.getSourceType();
            tr.setList(true);
            TypeInfo lti = TypeUtils.convertTypeReference(tiSource, tr);
            typeArgs.add(lti.getMalShortFormField());
            tr.setList(false);
          }
          else
          {
            typeArgs.add(typeInfo.getMalShortFormField().substring(0, typeInfo.getMalShortFormField().length() - 11) + "List.SHORT_FORM");
          }
        }
        else
        {
          typeArgs.add(typeInfo.getMalShortFormField());
        }
      }
    }

    if (needMalTypes && needXmlSchema)
    {
      throw new IllegalArgumentException("WARNING: Service specification uses multiple type specifications in the same message! This is not supported.");
    }

    String shortFormType = (needXmlSchema ? StdStrings.STRING : StdStrings.LONG);
    String arrayArgs = StubUtils.concatenateArguments(typeArgs.toArray(new String[0]));
    if (isPubSub)
    {
      opArgs.add("new " + shortFormType + "[] {" + arrayArgs + "}, new " + shortFormType + "[0]");
    }
    else
    {
      opArgs.add("new org.ccsds.moims.mo.mal.MALOperationStage(new org.ccsds.moims.mo.mal.structures.UOctet((short) " + index + "), new " + shortFormType + "[] {" + arrayArgs + "}, new " + shortFormType + "[] {})");
    }
  }

  protected String createAdapterMethodsArgs(List<TypeInfo> typeInfos, String argNamePrefix, boolean precedingArgs, boolean moreArgs)
  {
    StringBuilder buf = new StringBuilder();

    if (null != typeInfos)
    {
      for (int i = 0; i < typeInfos.size(); i++)
      {
        TypeInfo ti = typeInfos.get(i);

        buf.append(createAdapterMethodsArgs(ti, argNamePrefix, i, precedingArgs || (i > 0), moreArgs && i == (typeInfos.size() - 1)));
      }
    }

    return buf.toString();
  }

  protected String createAdapterMethodsArgs(TypeInfo ti, String argName, int argIndex, boolean precedingArgs, boolean moreArgs)
  {
    String retStr = "";

    if ((null != ti.getTargetType()) && !(StdStrings.VOID.equals(ti.getTargetType())))
    {
      if (precedingArgs)
      {
        retStr = ", ";
      }

      if (ti.isNativeType())
      {
        AttributeTypeDetails details = getAttributeDetails(ti.getSourceType());
        String av = argName + ".getBodyElement(" + argIndex + ", new " + getConfig().getBasePackage() + "mal." + getConfig().getStructureFolder() + ".Union(" + details.getDefaultValue() + "))";
        retStr += "(" + av + " == null) ? null : ((" + getConfig().getBasePackage() + "mal." + getConfig().getStructureFolder() + ".Union) " + av + ").get" + ti.getActualMalType() + "Value()";
      }
      else
      {
        String ct = createReturnReference(convertClassName(ti.getTargetType()));
        String at = null;
        if (!isAbstract(ti.getSourceType()))
        {
          CompositeField ce = createCompositeElementsDetails(null, "", ti.getSourceType(), true, null);
          at = ce.getNewCall();
        }

        String av = argName + ".getBodyElement(" + argIndex + ", " + at + ")";
        retStr += "(" + ct + ") " + av;
      }

      if (moreArgs)
      {
        retStr += ", ";
      }
    }

    return retStr;
  }

  protected String checkForReservedWords(String arg)
  {
    if (null != arg)
    {
      String replacementWord = reservedWordsMap.get(arg);
      if (null != replacementWord)
      {
        return replacementWord;
      }
    }

    return arg;
  }

  protected String createConsumerPatternCall(OperationSummary op)
  {
    switch (op.getPattern())
    {
      case SEND_OP:
        return "send";
      case SUBMIT_OP:
        return "submit";
      case REQUEST_OP:
        return "request";
      case INVOKE_OP:
        return "invoke";
      case PROGRESS_OP:
        return "progress";
    }

    return null;
  }

  protected String getOperationInstanceType(OperationSummary op)
  {
    switch (op.getPattern())
    {
      case SEND_OP:
        return getConfig().getSendOperationType();
      case SUBMIT_OP:
        return getConfig().getSubmitOperationType();
      case REQUEST_OP:
        return getConfig().getRequestOperationType();
      case INVOKE_OP:
        return getConfig().getInvokeOperationType();
      case PROGRESS_OP:
        return getConfig().getProgressOperationType();
      case PUBSUB_OP:
        return getConfig().getPubsubOperationType();
    }

    return null;
  }

  protected String createOperationReturnType(LanguageWriter file, AreaType area, ServiceType service, OperationSummary op)
  {
    switch (op.getPattern())
    {
      case REQUEST_OP:
      {
        if (null != op.getRetTypes())
        {
          return createReturnReference(createReturnType(file, area, service, op.getName(), "Response", op.getRetTypes()));
        }
        break;
      }
      case INVOKE_OP:
      case PROGRESS_OP:
      {
        if ((null != op.getAckTypes()) && (0 < op.getAckTypes().size()))
        {
          return createReturnReference(createReturnType(file, area, service, op.getName(), "Ack", op.getAckTypes()));
        }
        break;
      }
    }

    return StdStrings.VOID;
  }

  protected String createOperationArguments(GeneratorConfiguration config, LanguageWriter file, List<TypeInfo> opArgs, List<String> opArgComments)
  {
    if (null != opArgs)
    {
      StringBuilder buf = new StringBuilder();

      for (int i = 0; i < opArgs.size(); i++)
      {
        TypeInfo ti = opArgs.get(i);
        if (i > 0)
        {
          buf.append(", ");
        }
        buf.append(createReturnReference(ti.getTargetType()));
        buf.append(" _");
        buf.append(TypeUtils.shortTypeName(config.getNamingSeparator(), ti.getTargetType()));
        buf.append(i);

        if (null != opArgComments)
        {
          opArgComments.add("_" + TypeUtils.shortTypeName(config.getNamingSeparator(), ti.getTargetType()) + i + " Argument number " + i + " as defined by the service operation");
        }
      }
      return buf.toString();
    }
    return "";
  }

  private void createOperationReturn(LanguageWriter file, MethodWriter method, OperationSummary op, String opRetType) throws IOException
  {
    List<TypeInfo> targetTypes = op.getRetTypes();

    if ((InteractionPatternEnum.INVOKE_OP == op.getPattern()) || (InteractionPatternEnum.PROGRESS_OP == op.getPattern()))
    {
      targetTypes = op.getAckTypes();
    }

    if ((null != targetTypes) && (0 < targetTypes.size()))
    {
      if (1 == targetTypes.size())
      {
        if (targetTypes.get(0).isNativeType())
        {
          method.addMethodStatement("return " + createOperationArgReturn(file, method, targetTypes.get(0), "body", 0));
        }
        else
        {
          method.addMethodStatement("return " + createOperationArgReturn(file, method, targetTypes.get(0), "body", 0));
        }
      }
      else
      {
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < targetTypes.size(); i++)
        {
          TypeInfo ti = targetTypes.get(i);
          if (i > 0)
          {
            buf.append(", ");
          }
          buf.append(createOperationArgReturn(file, method, ti, "body", i));
        }

        method.addMethodStatement("return new " + opRetType + "(" + buf.toString() + ")");
      }
    }
  }

  protected String createOperationArgReturn(LanguageWriter file, MethodWriter method, TypeInfo ti, String argName, int argIndex) throws IOException
  {
    if ((null != ti.getTargetType()) && !(StdStrings.VOID.equals(ti.getTargetType())))
    {
      String eleType = "Object";
      String tv = argName + argIndex;
      String av;
      String rv;
      if (ti.isNativeType())
      {
        AttributeTypeDetails details = getAttributeDetails(ti.getSourceType());
        av = argName + ".getBodyElement(" + argIndex + ", new " + createElementType(file, StdStrings.MAL, null, StdStrings.UNION) + "(" + details.getDefaultValue() + "))";
        rv = "(" + tv + " == null) ? null : ((" + createElementType(file, StdStrings.MAL, null, StdStrings.UNION) + ") " + tv + ").get" + ti.getActualMalType() + "Value()";
      }
      else
      {
        String ct = createReturnReference(convertClassName(ti.getTargetType()));
        String at = null;
        if (!isAbstract(ti.getSourceType()))
        {
          CompositeField ce = createCompositeElementsDetails(null, "", ti.getSourceType(), true, null);
          at = ce.getNewCall();
        }

        av = argName + ".getBodyElement(" + argIndex + ", " + at + ")";
        rv = "(" + ct + ") " + tv;
      }

      method.addMethodStatement(eleType + " " + tv + " = (" + eleType + ") " + av);
      return rv;
    }

    return "";
  }

  protected String createReturnType(LanguageWriter file, AreaType area, ServiceType service, String opName, String messageType, List<TypeInfo> returnTypes)
  {
    if ((null != returnTypes) && (0 < returnTypes.size()))
    {
      if (1 == returnTypes.size())
      {
        return returnTypes.get(0).getTargetType();
      }
      else
      {
        String shortName = StubUtils.preCap(opName) + messageType;
        String rt = createElementType(file, area.getName(), service.getName(), getConfig().getBodyFolder(), shortName);
        if (!multiReturnTypeMap.containsKey(rt))
        {
          multiReturnTypeMap.put(rt, new MultiReturnType(rt, area, service, shortName, returnTypes));
        }

        return rt;
      }
    }

    return "";
  }

  /**
   * Creates a set of argument names based on the type, wrapping the type in a Union if a native type.
   *
   * @param config The Generator configuration to use.
   * @param typeNames The list of arguments.
   * @return The argument string.
   */
  public String createArgNameOrNull(List<TypeInfo> typeNames)
  {
    if ((null != typeNames) && (!typeNames.isEmpty()))
    {
      StringBuilder buf = new StringBuilder();
      for (int i = 0; i < typeNames.size(); i++)
      {
        TypeInfo ti = typeNames.get(i);
        if (i > 0)
        {
          buf.append(", ");
        }
        if (ti.isNativeType())
        {
          buf.append("(_");
          buf.append(TypeUtils.shortTypeName(getConfig().getNamingSeparator(), ti.getTargetType()));
          buf.append(i);
          buf.append(" == null) ? null : new ");
          buf.append(getConfig().getBasePackage());
          buf.append("mal.");
          buf.append(getConfig().getStructureFolder());
          buf.append(".Union(_");
          buf.append(TypeUtils.shortTypeName(getConfig().getNamingSeparator(), ti.getTargetType()));
          buf.append(i);
          buf.append(")");
        }
        else
        {
          buf.append("_");
          buf.append(TypeUtils.shortTypeName(getConfig().getNamingSeparator(), ti.getTargetType()));
          buf.append(i);
        }
      }

      return buf.toString();
    }

    return getConfig().getNullValue();
  }

  protected String createReturnReference(String targetType)
  {
    return targetType;
  }

  protected String createMethodCall(String call)
  {
    return call;
  }

  protected void createAreaFolderComment(File structureFolder, AreaType area) throws IOException
  {
  }

  protected void createServiceFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException
  {
  }

  protected void createAreaStructureFolderComment(File structureFolder, AreaType area) throws IOException
  {
  }

  protected void createServiceConsumerFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException
  {
  }

  protected void createServiceProviderFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException
  {
  }

  protected void createServiceMessageBodyFolderComment(String baseFolder, String packageName) throws IOException
  {
  }

  protected void createServiceStructureFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException
  {
  }

  protected void createStructureFactoryFolderComment(File structureFolder, AreaType area, ServiceType service) throws IOException
  {
  }

  protected abstract void addServiceConstructor(MethodWriter method, String serviceVar, String serviceVersion, ServiceSummary summary) throws IOException;

  protected abstract String createAreaHelperClassInitialValue(String areaVar, short areaVersion);

  protected abstract String createServiceHelperClassInitialValue(String serviceVar);

  protected abstract void createRequiredPublisher(String destinationFolderName, String fqPublisherName, OperationSummary op) throws IOException;

  protected abstract void addVectorAddStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

  protected abstract void addVectorRemoveStatement(LanguageWriter file, MethodWriter method, String variable, String parameter) throws IOException;

  protected abstract String createStaticClassReference(String type);

  protected abstract String addressOf(String object);

  protected abstract String createArraySize(boolean isActual, String type, String variable);

  protected abstract String malStringAsElement(LanguageWriter file);

  protected abstract String errorCodeAsReference(LanguageWriter file, String ref);

  protected abstract String getIntCallMethod();

  protected abstract String getOctetCallMethod();

  protected abstract ClassWriter createClassFile(File folder, String className) throws IOException;

  protected abstract ClassWriter createClassFile(String destinationFolderName, String className) throws IOException;

  protected abstract InterfaceWriter createInterfaceFile(File folder, String className) throws IOException;

  protected abstract InterfaceWriter createInterfaceFile(String destinationFolderName, String className) throws IOException;
}
