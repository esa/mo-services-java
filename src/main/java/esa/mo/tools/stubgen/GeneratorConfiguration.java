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

/**
 * Holds the configuration of the generator when used to generate a programming language.
 */
public class GeneratorConfiguration
{
  private final String basePackage;
  private final String structureFolder;
  private final String factoryFolder;
  private final String bodyFolder;
  private final String namingSeparator;
  private final String nullValue;
  private final String sendOperationType;
  private final String submitOperationType;
  private final String requestOperationType;
  private final String invokeOperationType;
  private final String progressOperationType;
  private final String pubsubOperationType;

  /**
   * Constructor.
   *
   * @param basePackage Base package all classes are contained in.
   * @param structurePackage Folder used to hold generated type classes.
   * @param factoryPackage Folder used to hold generated type factory classes.
   * @param bodyPackage Folder used to hold generated message body classes.
   * @param separator Package/namespace separator.
   * @param nullValue How is null represented.
   * @param sendOpType Class used to represent a SEND operation.
   * @param submitOpType Class used to represent a SUBMIT operation.
   * @param requestOpType Class used to represent a REQUEST operation.
   * @param invokeOpType Class used to represent a INVOKE operation.
   * @param progressOpType Class used to represent a PROGRESS operation.
   * @param pubsubOpType Class used to represent a PUBSUB operation.
   */
  public GeneratorConfiguration(String basePackage, String structurePackage, String factoryPackage, String bodyPackage, String separator, String nullValue, String sendOpType, String submitOpType, String requestOpType, String invokeOpType, String progressOpType, String pubsubOpType)
  {
    this.basePackage = basePackage;
    this.structureFolder = structurePackage;
    this.factoryFolder = factoryPackage;
    this.bodyFolder = bodyPackage;
    this.namingSeparator = separator;
    this.nullValue = nullValue;
    this.sendOperationType = sendOpType;
    this.submitOperationType = submitOpType;
    this.requestOperationType = requestOpType;
    this.invokeOperationType = invokeOpType;
    this.progressOperationType = progressOpType;
    this.pubsubOperationType = pubsubOpType;
  }

  /**
   * Returns the structure folder.
   *
   * @return the structure folder
   */
  public String getStructureFolder()
  {
    return structureFolder;
  }

  /**
   * Returns the factory folder.
   *
   * @return the factory folder
   */
  public String getFactoryFolder()
  {
    return factoryFolder;
  }

  /**
   * Returns the body folder.
   *
   * @return the body folder
   */
  public String getBodyFolder()
  {
    return bodyFolder;
  }

  /**
   * Returns the base package.
   *
   * @return the base package
   */
  public String getBasePackage()
  {
    return basePackage;
  }

  /**
   * Returns the naming separator.
   *
   * @return the naming separator
   */
  public String getNamingSeparator()
  {
    return namingSeparator;
  }

  /**
   * Returns the null value.
   *
   * @return the null value
   */
  public String getNullValue()
  {
    return nullValue;
  }

  /**
   * Returns the send operation type.
   *
   * @return the send operation type
   */
  public String getSendOperationType()
  {
    return sendOperationType;
  }

  /**
   * Returns the submit operation type.
   *
   * @return the submit operation type
   */
  public String getSubmitOperationType()
  {
    return submitOperationType;
  }

  /**
   * Returns the request operation type.
   *
   * @return the request operation type
   */
  public String getRequestOperationType()
  {
    return requestOperationType;
  }

  /**
   * Returns the invoke operation type.
   *
   * @return the invoke operation type
   */
  public String getInvokeOperationType()
  {
    return invokeOperationType;
  }

  /**
   * Returns the progress operation type.
   *
   * @return the progress operation type
   */
  public String getProgressOperationType()
  {
    return progressOperationType;
  }

  /**
   * Returns the pubsub operation type.
   *
   * @return the pubsub operation type
   */
  public String getPubsubOperationType()
  {
    return pubsubOperationType;
  }
}
