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
package esa.mo.tools.stubgen.specification;

import esa.mo.tools.stubgen.xsd.OperationType;
import java.util.List;

/**
 * Hold information about an operation.
 */
public final class OperationSummary
{
  private final InteractionPatternEnum pattern;
  private final OperationType originalOp;
  private final Integer set;
  private final List<TypeInfo> argTypes;
  private final String argComment;
  private final List<TypeInfo> ackTypes;
  private final String ackComment;
  private final List<TypeInfo> updateTypes;
  private final String updateComment;
  private final List<TypeInfo> retTypes;
  private final String retComment;

  /**
   * Constructor.
   *
   * @param pattern The interaction pattern of the operation.
   * @param op The XML operation details.
   * @param set The capability set of the operation.
   * @param argTypes The initial argument types of the operation.
   * @param argComment The initial argument comments of the operation.
   * @param ackTypes The acknowledgement argument types of the operation if support by the pattern.
   * @param ackComment The acknowledgement argument comments of the operation if support by the pattern.
   * @param updateTypes The update argument types of the operation if support by the pattern.
   * @param updateComment The update argument comments of the operation if support by the pattern.
   * @param retTypes The return argument types of the operation if support by the pattern.
   * @param retComment The return argument comments of the operation if support by the pattern.
   */
  public OperationSummary(InteractionPatternEnum pattern,
          OperationType op,
          Integer set,
          List<TypeInfo> argTypes,
          String argComment,
          List<TypeInfo> ackTypes,
          String ackComment,
          List<TypeInfo> updateTypes,
          String updateComment,
          List<TypeInfo> retTypes,
          String retComment)
  {
    super();
    this.pattern = pattern;
    this.originalOp = op;
    this.set = set;
    this.argTypes = argTypes;
    this.argComment = argComment;
    this.ackTypes = ackTypes;
    this.ackComment = ackComment;
    this.updateTypes = updateTypes;
    this.updateComment = updateComment;
    this.retTypes = retTypes;
    this.retComment = retComment;
  }

  /**
   * Returns the pattern type.
   *
   * @return the pattern
   */
  public InteractionPatternEnum getPattern()
  {
    return pattern;
  }

  /**
   * Returns the XML operation details.
   *
   * @return the originalOp
   */
  public OperationType getOriginalOp()
  {
    return originalOp;
  }

  /**
   * Returns the name of the operation.
   *
   * @return the name
   */
  public String getName()
  {
    return originalOp.getName();
  }

  /**
   * Returns the number of the operation.
   *
   * @return the number
   */
  public Integer getNumber()
  {
    return originalOp.getNumber();
  }

  /**
   * Returns the capability set of the operation.
   *
   * @return the set
   */
  public Integer getSet()
  {
    return set;
  }

  /**
   * Is replay supported by the operation.
   *
   * @return the replay
   */
  public Boolean getReplay()
  {
    return originalOp.isSupportInReplay();
  }

  /**
   * Returns the initial argument types of the operation.
   *
   * @return the argTypes
   */
  public List<TypeInfo> getArgTypes()
  {
    return argTypes;
  }

  /**
   * Returns the initial argument comments of the operation.
   *
   * @return the argComment
   */
  public String getArgComment()
  {
    return argComment;
  }

  /**
   * Returns the acknowledgement argument types of the operation if supported by the pattern otherwise null.
   *
   * @return the ackTypes
   */
  public List<TypeInfo> getAckTypes()
  {
    return ackTypes;
  }

  /**
   * Returns the acknowledgement argument comments of the operation if supported by the pattern otherwise null.
   *
   * @return the ackComment
   */
  public String getAckComment()
  {
    return ackComment;
  }

  /**
   * Returns the update argument types of the operation if supported by the pattern otherwise null.
   *
   * @return the updateTypes
   */
  public List<TypeInfo> getUpdateTypes()
  {
    return updateTypes;
  }

  /**
   * Returns the update argument comments of the operation if supported by the pattern otherwise null.
   *
   * @return the updateComment
   */
  public String getUpdateComment()
  {
    return updateComment;
  }

  /**
   * Returns the return argument types of the operation if supported by the pattern otherwise null.
   *
   * @return the retTypes
   */
  public List<TypeInfo> getRetTypes()
  {
    return retTypes;
  }

  /**
   * Returns the return argument comments of the operation if supported by the pattern otherwise null.
   *
   * @return the retComment
   */
  public String getRetComment()
  {
    return retComment;
  }
}
