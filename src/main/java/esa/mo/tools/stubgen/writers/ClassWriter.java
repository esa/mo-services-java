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
package esa.mo.tools.stubgen.writers;

import esa.mo.tools.stubgen.specification.CompositeField;
import java.io.IOException;
import java.util.List;

/**
 * Writer used when creating a class.
 */
public interface ClassWriter extends LanguageWriter
{
  /**
   * Adds a class open statement.
   *
   * @param className The class name.
   * @param finalClass Is the class final and should not be extended.
   * @param abstractClass Is the class abstract.
   * @param extendsClass What class does this extend.
   * @param implementsInterface Which interfaces does this class implement.
   * @param comment Comment for the class.
   * @throws IOException If there is an IO error.
   */
  void addClassOpenStatement(String className, boolean finalClass, boolean abstractClass, String extendsClass, String implementsInterface, String comment) throws IOException;

  /**
   * Adds a member variable to the class.
   *
   * @param isStatic True if the variable is static.
   * @param isFinal True if the variable may not be modified.
   * @param scope Scope of the variable.
   * @param type Type of the variable.
   * @param isActual True if the variable should be contained by value rather than reference.
   * @param isArray true if it is an array type.
   * @param variableName The variable name.
   * @param initialValue The initial value of the variable.
   * @param comment The comment for the variable.
   * @throws IOException If there is an IO error.
   */
  void addClassVariable(boolean isStatic, boolean isFinal, String scope, String type, boolean isActual, boolean isArray, String variableName, String initialValue, String comment) throws IOException;

  /**
   * Adds a member variable to the class.
   *
   * @param isStatic True if the variable is static.
   * @param isFinal True if the variable may not be modified.
   * @param scope Scope of the variable.
   * @param type Type of the variable.
   * @param isActual True if the variable should be contained by value rather than reference.
   * @param isObject True if the variable is an object rather than native type.
   * @param isArray true if it is an array type.
   * @param variableName The variable name.
   * @param initialValue The initial value of the variable.
   * @param comment The comment for the variable.
   * @throws IOException If there is an IO error.
   */
  void addClassVariable(boolean isStatic, boolean isFinal, String scope, String type, boolean isActual, boolean isObject, boolean isArray, String variableName, String initialValue, String comment) throws IOException;

  /**
   * Adds a member variable to the class.
   *
   * @param isStatic True if the variable is static.
   * @param isFinal True if the variable may not be modified.
   * @param scope Scope of the variable.
   * @param type Type of the variable.
   * @param isActual True if the variable should be contained by value rather than reference.
   * @param isObject True if the variable is an object rather than native type.
   * @param isArray true if it is an array type.
   * @param variableName The variable name.
   * @param initialValue The initial value of the variable.
   * @param comment The comment for the variable.
   * @throws IOException If there is an IO error.
   */
  void addClassVariable(boolean isStatic, boolean isFinal, String scope, String type, boolean isActual, boolean isObject, boolean isArray, String variableName, List<String> initialValue, String comment) throws IOException;

  /**
   * Add a static constructor to the class.
   *
   * @param returnType Return type of the method.
   * @param methodName Method name.
   * @param args Arguments of the method.
   * @param constructorCall Body of the method.
   * @throws IOException If there is an IO error.
   */
  void addStaticConstructor(String returnType, String methodName, String args, String constructorCall) throws IOException;

  /**
   * Adds a default constructor to the class.
   *
   * @param className The class name.
   * @throws IOException If there is an IO error.
   */
  void addConstructorDefault(String className) throws IOException;

  /**
   * Adds a copy constructor to the class if supported by the language.
   *
   * @param className The class name.
   * @param compElements The elements of the class.
   * @throws IOException If there is an IO error.
   */
  void addConstructorCopy(String className, List<CompositeField> compElements) throws IOException;

  /**
   * Adds a constructor to the class.
   *
   * @param scope Constructor scope.
   * @param className The class name.
   * @param arg The arguments of the constructor.
   * @param isArgForSuper True if the argument is for the super type.
   * @param throwsSpec The throws specification.
   * @param comment Comment for the constructor.
   * @param throwsComment Comment for the throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addConstructor(String scope, String className, CompositeField arg, boolean isArgForSuper, String throwsSpec, String comment, String throwsComment) throws IOException;

  /**
   * Adds a constructor to the class.
   *
   * @param scope Constructor scope.
   * @param className The class name.
   * @param args The arguments of the constructor.
   * @param superArgs The arguments of any super type.
   * @param throwsSpec The throws specification.
   * @param comment Comment for the constructor.
   * @param throwsComment Comment for the throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addConstructor(String scope, String className, List<CompositeField> args, List<CompositeField> superArgs, String throwsSpec, String comment, String throwsComment) throws IOException;

  /**
   * Add a method to the class.
   *
   * @param isConst Is the method constant.
   * @param isStatic Is it is static method.
   * @param scope Method scope.
   * @param isReturnConst Is the return constant.
   * @param isReturnActual Is the return an instance.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addMethodOpenStatement(boolean isConst, boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual, String rtype, String methodName, List<CompositeField> args, String throwsSpec) throws IOException;

  /**
   * Add a method to the class.
   *
   * @param isConst Is the method constant.
   * @param isStatic Is it is static method.
   * @param scope Method scope.
   * @param isReturnConst Is the return constant.
   * @param isReturnActual Is the return an instance.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @param comment The comment for the method.
   * @param returnComment The comment for the return value.
   * @param throwsComment The comment for the throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addMethodOpenStatement(boolean isConst, boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual, String rtype, String methodName, List<CompositeField> args, String throwsSpec, String comment, String returnComment, List<String> throwsComment) throws IOException;

  /**
   * Add a method to the class.
   *
   * @param isVirtual Is the method a virtual method.
   * @param isConst Is the method constant.
   * @param isStatic Is it is static method.
   * @param scope Method scope.
   * @param isReturnConst Is the return constant.
   * @param isReturnActual Is the return an instance.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addMethodOpenStatement(boolean isVirtual, boolean isConst, boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual, String rtype, String methodName, List<CompositeField> args, String throwsSpec) throws IOException;

  /**
   * Add a method to the class.
   *
   * @param isVirtual Is the method a virtual method.
   * @param isConst Is the method constant.
   * @param isStatic Is it is static method.
   * @param scope Method scope.
   * @param isReturnConst Is the return constant.
   * @param isReturnActual Is the return an instance.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @param comment The comment for the method.
   * @param returnComment The comment for the return value.
   * @param throwsComment The comment for the throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addMethodOpenStatement(boolean isVirtual, boolean isConst, boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual, String rtype, String methodName, List<CompositeField> args, String throwsSpec, String comment, String returnComment, List<String> throwsComment) throws IOException;

  /**
   * Add a method to the class.
   *
   * @param isFinal Is the method final.
   * @param isVirtual Is the method a virtual method.
   * @param isConst Is the method constant.
   * @param isStatic Is it is static method.
   * @param scope Method scope.
   * @param isReturnConst Is the return constant.
   * @param isReturnActual Is the return an instance.
   * @param rtype The return type of the method.
   * @param methodName The method name.
   * @param args The arguments of the method.
   * @param throwsSpec The throws specification.
   * @param comment The comment for the method.
   * @param returnComment The comment for the return value.
   * @param throwsComment The comment for the throws specification.
   * @return Returns a method writer for the method.
   * @throws IOException If there is an IO error.
   */
  MethodWriter addMethodOpenStatement(boolean isFinal, boolean isVirtual, boolean isConst, boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual, String rtype, String methodName, List<CompositeField> args, String throwsSpec, String comment, String returnComment, List<String> throwsComment) throws IOException;

  /**
   * Adds statements to close the class.
   *
   * @throws IOException If there is an IO error.
   */
  void addClassCloseStatement() throws IOException;
}
