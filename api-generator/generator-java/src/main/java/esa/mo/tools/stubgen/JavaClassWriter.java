/* ----------------------------------------------------------------------------
 * Copyright (C) 2022      European Space Agency
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

import static esa.mo.tools.stubgen.GeneratorJava.JAVA_FILE_EXT;
import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.CompositeField;
import esa.mo.tools.stubgen.specification.NativeTypeDetails;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.writers.AbstractLanguageWriter;
import static esa.mo.tools.stubgen.writers.AbstractLanguageWriter.normaliseComment;
import static esa.mo.tools.stubgen.writers.AbstractLanguageWriter.normaliseComments;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class JavaClassWriter extends AbstractLanguageWriter implements ClassWriterProposed, InterfaceWriter, MethodWriter {

    private final Writer file;
    private final GeneratorJava generator;

    /**
     * Constructor.
     *
     * @param folder The folder to create the file in.
     * @param className The class name.
     * @param generator The java code generator.
     * @throws IOException If any problems creating the file.
     */
    public JavaClassWriter(File folder, String className, GeneratorJava generator) throws IOException {
        this.file = StubUtils.createLowLevelWriter(folder, className, JAVA_FILE_EXT);
        this.generator = generator;
    }

    /**
     * Constructor.
     *
     * @param destinationFolderName Folder to create the file in.
     * @param className The file name.
     * @param generator The java code generator.
     * @throws IOException If any problems creating the file.
     */
    public JavaClassWriter(String destinationFolderName, String className, GeneratorJava generator) throws IOException {
        this.file = StubUtils.createLowLevelWriter(destinationFolderName, className, JAVA_FILE_EXT);
        this.generator = generator;
    }

    @Override
    public void addStatement(String string) throws IOException {
        file.append(makeLine(0, string, false));
    }

    @Override
    public void addClassCloseStatement() throws IOException {
        file.append(makeLine(0, "}", false));
    }

    @Override
    public void addClassOpenStatement(String className, boolean finalClass, boolean abstractClass,
            String extendsClass, String implementsInterface, String comment) throws IOException {
        addMultilineComment(0, true, comment, false);

        file.append("public ");
        if (finalClass) {
            file.append("final ");
        }
        if (abstractClass) {
            file.append("abstract ");
        }
        file.append("class ").append(className);
        if (null != extendsClass) {
            file.append(" extends ").append(extendsClass);
        }
        if (null != implementsInterface) {
            file.append(" implements ").append(implementsInterface);
        }
        file.append(" {");
        file.append(getLineSeparator()).append(getLineSeparator());
    }

    @Override
    public void addClassVariableProposed(boolean isStatic, boolean isFinal, String scope,
            CompositeField arg, boolean isObject, String initialValue) throws IOException {
        addClassVariable(true, isStatic, isFinal, scope, arg, isObject, false, initialValue);
    }

    @Override
    public void addClassVariable(boolean isStatic, boolean isFinal, String scope,
            CompositeField arg, boolean isObject, String initialValue) throws IOException {
        addClassVariable(false, isStatic, isFinal, scope, arg, isObject, false, initialValue);
    }

    @Override
    public void addClassVariable(boolean isStatic, boolean isFinal, String scope, CompositeField arg,
            boolean isObject, boolean isArray, List<String> initialValues) throws IOException {
        StringBuilder iniVal = new StringBuilder();

        for (int i = 0; i < initialValues.size(); i++) {
            if (0 < i) {
                iniVal.append(", ");
            }
            String initialValue = initialValues.get(i);
            iniVal.append(initialValue);
        }

        String val = (isArray) ? iniVal.toString() : "(" + iniVal.toString() + ")";
        addClassVariable(false, isStatic, isFinal, scope, arg, isObject, isArray, val);
    }

    protected void addClassVariable(boolean isProposed, boolean isStatic, boolean isFinal, String scope,
            CompositeField arg, boolean isObject, boolean isArray, String initialValue) throws IOException {
        addMultilineComment(1, false, arg.getComment(), false);

        StringBuilder buf = new StringBuilder(scope);
        buf.append(" ");
        if (isStatic) {
            buf.append("static ");
        }
        if (isFinal) {
            buf.append("final ");
        }
        String ltype = createLocalType(arg);
        buf.append(ltype);
        if (isArray) {
            buf.append("[]");
        }
        buf.append(" ");
        buf.append(arg.getFieldName());

        if (null != initialValue) {
            if (isArray) {
                buf.append(" = {").append(initialValue).append("}");
            } else if (generator.isNativeType(arg.getTypeName())) {
                NativeTypeDetails dets = generator.getNativeType(arg.getTypeName());
                if (dets.isObject()) {
                    buf.append(" = new ").append(ltype).append(initialValue);
                } else {
                    buf.append(" = ").append(initialValue);
                }
            } else {
                buf.append(" = new ").append(ltype).append(initialValue);
            }
        }

        if (isProposed) {
            file.append(makeLine(1, "@org.ccsds.moims.mo.com.Proposed", false));
        }
        file.append(makeLine(1, buf.toString(), true));
        file.append(getLineSeparator());
    }

    @Override
    public void addClassVariableNewInit(boolean isStatic, boolean isFinal, String scope, CompositeField arg,
            boolean isObject, boolean isArray, String initialValue, boolean isNewInit) throws IOException {
        addMultilineComment(1, false, arg.getComment(), false);

        StringBuilder buf = new StringBuilder(scope);
        buf.append(" ");
        if (isStatic) {
            buf.append("static ");
        }
        if (isFinal) {
            buf.append("final ");
        }
        String ltype = createLocalType(arg);
        buf.append(ltype);
        if (isArray) {
            buf.append("[]");
        }
        buf.append(" ");
        buf.append(arg.getFieldName());

        if (null != initialValue) {
            if (isArray) {
                buf.append(" = {").append(initialValue).append("}");
            } else if (!isNewInit) {
                buf.append(" = ").append(initialValue);
            } else if (generator.isNativeType(arg.getTypeName())) {
                NativeTypeDetails dets = generator.getNativeType(arg.getTypeName());
                if (dets.isObject()) {
                    buf.append(" = new ").append(ltype).append(initialValue);
                } else {
                    buf.append(" = ").append(initialValue);
                }
            } else {
                buf.append(" = new ").append(ltype).append(initialValue);
            }
        }

        file.append(makeLine(1, buf.toString(), true));
        file.append(getLineSeparator());
    }

    @Override
    public void addStaticConstructor(String returnType, String methodName, String args, String constructorCall) throws IOException {
    }

    @Override
    public void addConstructorDefault(String className) throws IOException {
        String comment = "Default constructor for " + className;
        addConstructor(StdStrings.PUBLIC, className, null, null, null, comment, null).addMethodCloseStatement();
    }

    @Override
    public void addConstructorCopy(String className, List<CompositeField> compElements) throws IOException {
    }

    @Override
    public MethodWriter addConstructor(String scope, String className, CompositeField arg,
            boolean isArgForSuper, String throwsSpec, String comment, String throwsComment) throws IOException {
        List<CompositeField> fields = (isArgForSuper ? Arrays.asList(arg) : ((List<CompositeField>) null));
        return addConstructor(scope, className, Arrays.asList(arg), fields, throwsSpec, comment, throwsComment);
    }

    @Override
    public MethodWriter addConstructor(String scope, String className, List<CompositeField> args,
            List<CompositeField> superArgs, String throwsSpec, String comment, String throwsComment) throws IOException {
        List<String> comments = normaliseArgComments(comment, null, args, Arrays.asList(throwsComment));
        addMultilineComment(1, false, comments, false);

        StringBuilder signature = new StringBuilder(scope + " " + className);
        signature.append("(").append(processArgs(args, true)).append(")");

        if (null != throwsSpec) {
            signature.append(" throws ");
            signature.append(throwsSpec);
        }

        signature.append(" {");
        file.append(makeLine(1, signature.toString(), false));
        if ((null != superArgs) && (!superArgs.isEmpty())) {
            file.append(makeLine(2, "super(" + processArgs(superArgs, false) + ")", true));
        }

        return this;
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isConst, boolean isStatic,
            String scope, boolean isReturnConst, boolean isReturnActual, CompositeField rtype,
            String methodName, List<CompositeField> args, String throwsSpec) throws IOException {
        return addMethodOpenStatement(isConst, isStatic, scope, isReturnConst, isReturnActual, rtype,
                methodName, args, throwsSpec, null, null, null);
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isConst, boolean isStatic,
            String scope, boolean isReturnConst, boolean isReturnActual, CompositeField rtype,
            String methodName, List<CompositeField> args, String throwsSpec, String comment,
            String returnComment, List<String> throwsComment) throws IOException {
        return addMethodOpenStatement(false, isConst, isStatic, scope, isReturnConst,
                isReturnActual, rtype, methodName, args, throwsSpec, comment, returnComment, throwsComment);
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isVirtual, boolean isConst, boolean isStatic,
            String scope, boolean isReturnConst, boolean isReturnActual, CompositeField rtype,
            String methodName, List<CompositeField> args, String throwsSpec) throws IOException {
        return addMethodOpenStatement(isVirtual, isConst, isStatic, scope, isReturnConst,
                isReturnActual, rtype, methodName, args, throwsSpec, null, null, null);
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isVirtual, boolean isConst,
            boolean isStatic, String scope, boolean isReturnConst, boolean isReturnActual,
            CompositeField rtype, String methodName, List<CompositeField> args, String throwsSpec,
            String comment, String returnComment, List<String> throwsComment) throws IOException {
        return addMethodOpenStatement(false, isVirtual, isConst, isStatic, scope, isReturnConst,
                isReturnActual, rtype, methodName, args, throwsSpec, comment, returnComment, throwsComment);
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isFinal, boolean isVirtual,
            boolean isConst, boolean isStatic, String scope, boolean isReturnConst,
            boolean isReturnActual, CompositeField rtype, String methodName, List<CompositeField> args,
            String throwsSpec, String comment, String returnComment, List<String> throwsComment) throws IOException {
        List<String> comments = normaliseArgComments(comment, returnComment, args, throwsComment);
        addMultilineComment(1, false, comments, false);

        String nStatic = (isStatic) ? "static " : "";
        String nFinal = (isFinal) ? "final " : "";

        String srtype = createLocalType(rtype);
        String argString = processArgs(args, true);

        StringBuilder methodSignature = new StringBuilder();
        methodSignature.append(scope).append(" ").append(nStatic);
        methodSignature.append(nFinal).append(srtype).append(" ").append(methodName);
        methodSignature.append("(").append(argString).append(")");

        if (null != throwsSpec) {
            methodSignature.append(" throws ");
            methodSignature.append(throwsSpec);
        }

        methodSignature.append(" {");
        file.append(makeLine(1, methodSignature.toString(), false));

        return this;
    }

    @Override
    public MethodWriter addMethodOpenStatement(boolean isFinal, boolean isVirtual,
            boolean isConst, boolean isStatic, String scope, boolean isReturnConst,
            boolean isReturnActual, CompositeField rtype, String methodName,
            List<CompositeField> args, String throwsSpec, String comment, String returnComment,
            List<String> throwsComment, boolean isDeprecated) throws IOException {
        List<String> comments = normaliseArgComments(comment, returnComment, args, throwsComment);
        addMultilineComment(1, false, comments, false);

        if (isDeprecated) {
            file.append("  @Deprecated\n");
        }

        String nStatic = isStatic ? "static " : "";
        String nFinal = isFinal ? "final " : "";
        String srtype = createLocalType(rtype);
        String argString = processArgs(args, true);

        StringBuilder methodSignature = new StringBuilder(scope + " " + nStatic + nFinal + srtype + " " + methodName + "(" + argString + ")");

        if (null != throwsSpec) {
            methodSignature.append(" throws ");
            methodSignature.append(throwsSpec);
        }

        methodSignature.append(" {");
        file.append(makeLine(1, methodSignature.toString(), false));

        return this;
    }

    @Override
    public void addPackageStatement(AreaType area, ServiceType service, String extraPackage) throws IOException {
        String packageName = "";

        if (null == area) {
            packageName = generator.getConfig().getAreaPackage("");
        } else {
            packageName += generator.getConfig().getAreaPackage(area.getName()) + area.getName().toLowerCase();

            if (null != service) {
                packageName += "." + service.getName().toLowerCase();
            }
        }

        if (null != extraPackage) {
            if (0 < packageName.length()) {
                packageName += ".";
            }
            packageName += extraPackage;
        }

        file.append(makeLine(0, "package " + packageName, true));
    }

    @Override
    public void flush() throws IOException {
        file.flush();
    }

    @Override
    public void addInterfaceCloseStatement() throws IOException {
        file.append(makeLine(0, "}", false));
    }

    @Override
    public void addInterfaceMethodDeclaration(String scope, CompositeField rtype,
            String methodName, List<CompositeField> args, String throwsSpec, String comment,
            String returnComment, List<String> throwsComment) throws IOException {
        String srtype = createLocalType(rtype);
        String argString = processArgs(args, true);

        List<String> comments = normaliseArgComments(comment, null == rtype ? null : returnComment, args, throwsComment);
        addMultilineComment(1, false, comments, false);

        StringBuilder buf = new StringBuilder(srtype + " " + methodName);
        buf.append("(").append(argString).append(")");

        if (null != throwsSpec) {
            buf.append(" throws ");
            buf.append(throwsSpec);
        }

        file.append(makeLine(1, buf.toString(), true));
    }

    @Override
    public void addInterfaceOpenStatement(String interfaceName,
            String extendsInterface, String comment) throws IOException {
        addMultilineComment(0, true, comment, false);

        file.append("public interface ");
        file.append(interfaceName);
        if (null != extendsInterface) {
            file.append(" extends ").append(extendsInterface);
        }
        file.append(" {");
        file.append(getLineSeparator()).append(getLineSeparator());
    }

    @Override
    public void addArrayMethodStatement(String arrayVariable,
            String indexVariable, String arrayMaxSize) throws IOException {
        addLine("return " + arrayVariable + "[" + indexVariable + "]", true);
    }

    @Override
    public void addSuperMethodStatement(String method, String args) throws IOException {
        addLine("super." + method + "(" + args + ")", true);
    }

    @Override
    public void addLine(String statement) throws IOException {
        addLine(statement, true);
    }

    @Override
    public void addMethodWithDependencyStatement(String statement,
            String dependency, boolean addSemi) throws IOException {
        addLine(statement, addSemi);
    }

    @Override
    public void addLine(String statement, boolean addSemi) throws IOException {
        if (0 < statement.trim().length()) {
            file.append(makeLine(2, statement, addSemi));
        }
    }

    @Override
    public void addMethodCloseStatement() throws IOException {
        file.append(makeLine(1, "}", false));
        file.append(getLineSeparator());
    }

    @Override
    public void addMultilineComment(int tabCount, boolean preBlankLine,
            List<String> comments, boolean postBlankLine) throws IOException {
        if (!comments.isEmpty()) {
            if (preBlankLine) {
                file.append(getLineSeparator());
            }

            file.append(makeLine(tabCount, "/**", false));

            for (String comment : comments) {
                if (!comment.contains("<T>")) {
                    comment = comment.replaceAll("<", "_");
                    comment = comment.replaceAll(">", "_");
                }
                // comment = comment.replace("\n", "\n     * ");
                file.append(makeLine(tabCount, " * " + comment, false));
            }
            file.append(makeLine(tabCount, " */", false));

            if (postBlankLine) {
                file.append(getLineSeparator());
            }
        }
    }

    private List<String> normaliseArgComments(String comment, String returnComment,
            List<CompositeField> argsComments, List<String> throwsComment) {
        List<String> rv = new LinkedList<>();

        if (null != argsComments) {
            for (CompositeField arg : argsComments) {
                rv.add(arg.getFieldName() + " " + arg.getComment());
            }
        }

        return normaliseComments(comment, returnComment, rv, throwsComment);
    }

    private List<String> normaliseComments(String comment, String returnComment,
            List<String> argsComments, List<String> throwsComment) {
        List<String> rv = new LinkedList<>();

        normaliseComment(rv, comment);
        rv.add(""); // Separation between the comment and params
        normaliseComments(rv, StubUtils.conditionalAdd("@param ", argsComments));
        normaliseComment(rv, StubUtils.conditionalAdd("@return ", returnComment));
        normaliseComments(rv, StubUtils.conditionalAdd("@throws ", throwsComment));

        return rv;
    }

    private String processArgs(List<CompositeField> args, boolean includeType) {
        StringBuilder buf = new StringBuilder();
        if (null != args && (!args.isEmpty())) {
            boolean firstTime = true;

            for (CompositeField arg : args) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    buf.append(", ");
                }

                if (includeType) {
                    buf.append(createLocalType(arg)).append(" ");
                }

                String name = generator.checkForReservedWords(arg.getFieldName());
                name = name.replaceAll("<", "_");
                name = name.replaceAll(">", "_");
                buf.append(name);
            }
        }

        return buf.toString();
    }

    private String createLocalType(CompositeField type) {
        if (null != type) {
            String fullType = type.getTypeName();

            if (generator.isNativeType(fullType)) {
                NativeTypeDetails dets = generator.getNativeType(fullType);
                return dets.getLanguageTypeName();
            } else if (!type.isList() && generator.isAttributeType(type.getTypeReference())) {
                AttributeTypeDetails dets = generator.getAttributeDetails(type.getTypeReference());
                if (null != dets) {
                    return dets.getTargetType();
                }
            }

            return fullType;
        }

        return StdStrings.VOID;
    }

//    private String createLocalType(String type)
//    {
//      if (null != type)
//      {
//        String head = "";
//        String tail = "";
//
//        if (type.startsWith("new "))
//        {
//          head = "new ";
//          int tindex = type.indexOf('(');
//
//          if (tindex == -1)
//          {
//            tindex = type.indexOf('[');
//          }
//
//          tail = type.substring(tindex);
//          type = type.substring(4, type.length() - tail.length());
//        }
//
//        if (isNativeType(type))
//        {
//          NativeTypeDetails dets = getNativeType(type);
//
//          type = dets.getLanguageTypeName();
//        }
//        else
//        {
//          AttributeTypeDetails dets = getAttributeDetails(type);
//          if (null != dets)
//          {
//            type = dets.getTargetType();
//          }
//        }
//
//        return head + type + tail;
//      }
//
//      return type;
//    }
}
