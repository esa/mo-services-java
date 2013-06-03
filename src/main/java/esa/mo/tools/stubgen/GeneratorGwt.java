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

import esa.mo.tools.stubgen.specification.OperationSummary;
import esa.mo.tools.stubgen.specification.ServiceSummary;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.writers.ClassWriter;
import esa.mo.tools.stubgen.writers.InterfaceWriter;
import esa.mo.tools.stubgen.writers.MethodWriter;
import esa.mo.tools.stubgen.xsd.AreaType;
import esa.mo.tools.stubgen.xsd.ServiceType;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Generates stubs and skeletons for CCSDS MO Service specifications for the Java Google Web Toolkit API.
 * Experimental.
 */
public class GeneratorGwt extends GeneratorJava
{
  /**
   * Constructor.
   *
   * @param logger The logger to use.
   */
  public GeneratorGwt(org.apache.maven.plugin.logging.Log logger)
  {
    super(logger);
  }

  @Override
  public String getShortName()
  {
    return "GWT";
  }

  @Override
  public String getDescription()
  {
    return "Experimental: Generates a GWT compatible Java language mapping.";
  }

  @Override
  protected void createAreaHelperClass(File areaFolder, AreaType area) throws IOException
  {
  }

  @Override
  protected void createServiceHelperClass(File serviceFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
  }

  @Override
  protected void createServiceConsumerInterface(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating consumer interface: " + service.getName());

    InterfaceWriter file = createInterfaceFile(consumerFolder, service.getName() + "GWT");

    String serviceName = service.getName();

    file.addPackageStatement(area, service, CONSUMER_FOLDER);

    file.addStatement("@com.google.gwt.user.client.rpc.RemoteServiceRelativePath(\"" + service.getName() + "GWT\")");
    file.addInterfaceOpenStatement(serviceName + "GWT", "com.google.gwt.user.client.rpc.RemoteService", null);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String msgType = createReturnReference(createElementType(file, StdStrings.MAL, null, TRANSPORT_FOLDER, StdStrings.MALMESSAGE));

    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case SEND_OP:
        {
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, msgType, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), null), throwsMALException, null, null, null, null);
          break;
        }
        case SUBMIT_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs, throwsMALException, null, null, null, null);
          break;
        }
        case REQUEST_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          String opRetType = createOperationReturnType(file, area, service, op);
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs, throwsMALException, null, null, null, null);
          break;
        }
        case INVOKE_OP:
        {
          break;
        }
        case PROGRESS_OP:
        {
          break;
        }
        case PUBSUB_OP:
        {
          break;
        }
      }
    }

    file.addInterfaceCloseStatement();

    file.flush();
  }

  @Override
  protected void createServiceConsumerStub(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating consumer stub: " + service.getName());

    String serviceName = service.getName();

    InterfaceWriter file = createInterfaceFile(consumerFolder, serviceName + "GWTAsync");

    file.addPackageStatement(area, service, CONSUMER_FOLDER);

    file.addInterfaceOpenStatement(serviceName + "GWTAsync", null, null);

    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case SEND_OP:
        {
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), null), null, null, null, null, null);
          break;
        }
        case SUBMIT_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs, null, null, null, null, null);
          break;
        }
        case REQUEST_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          String opRetType = createOperationReturnType(file, area, service, op);
          String asyncOpArgs = StubUtils.concatenateArguments(opArgs, "com.google.gwt.user.client.rpc.AsyncCallback<" + opRetType + "> _callback");
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), asyncOpArgs, null, null, null, null, null);
          break;
        }
        case INVOKE_OP:
        {
          break;
        }
        case PROGRESS_OP:
        {
          break;
        }
        case PUBSUB_OP:
        {
          break;
        }
      }
    }

    file.addInterfaceCloseStatement();

    file.flush();
  }

  @Override
  protected void createServiceConsumerAdapter(File consumerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
  }

  protected void createServiceProviderHandler2(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
    getLog().info("Creating provider handler interface: " + service.getName());

    String handlerName = service.getName() + "Handler";
    InterfaceWriter file = createInterfaceFile(providerFolder, handlerName);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    file.addInterfaceOpenStatement(handlerName, null, null);

    String intHandlerStr = createReturnReference(createElementType(file, StdStrings.MAL, null, PROVIDER_FOLDER, StdStrings.MALINTERACTION)) + " interaction";
    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case SEND_OP:
        {
          String opArgs = convertToNamespace(createOperationArguments(getConfig(), file, op.getArgTypes(), null));
          if (0 < opArgs.length())
          {
            opArgs += ", ";
          }
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs + intHandlerStr, throwsMALException, null, null, null, null);
          break;
        }
        case SUBMIT_OP:
        {
          String opArgs = convertToNamespace(createOperationArguments(getConfig(), file, op.getArgTypes(), null));
          if (0 < opArgs.length())
          {
            opArgs += ", ";
          }
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs + intHandlerStr, throwsMALException, null, null, null, null);
          break;
        }
        case REQUEST_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          if (0 < opArgs.length())
          {
            opArgs += ", ";
          }
          String opRetType = createOperationReturnType(file, area, service, op);
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, opRetType, op.getName(), opArgs + intHandlerStr, throwsMALException, null, null, null, null);
          break;
        }
        case INVOKE_OP:
        {
          String opArgs = convertToNamespace(createOperationArguments(getConfig(), file, op.getArgTypes(), null));
          if (0 < opArgs.length())
          {
            opArgs += ", ";
          }
          String serviceHandlerStr = createReturnReference(createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction")) + " interaction";
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs + serviceHandlerStr, throwsMALException, null, null, null, null);
          break;
        }
        case PROGRESS_OP:
        {
          String opArgs = createOperationArguments(getConfig(), file, op.getArgTypes(), null);
          if (0 < opArgs.length())
          {
            opArgs += ", ";
          }
          String serviceHandlerStr = createReturnReference(createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, StubUtils.preCap(op.getName()) + "Interaction")) + " interaction";
          file.addInterfaceMethodDeclaration(StdStrings.PUBLIC, StdStrings.VOID, op.getName(), opArgs + serviceHandlerStr, throwsMALException, null, null, null, null);
          break;
        }
        case PUBSUB_OP:
        {
          break;
        }
      }
    }

    file.addInterfaceCloseStatement();

    file.flush();
  }

  @Override
  protected void createServiceProviderSkeleton(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, Map<String, OperationSummary> requiredPublishers) throws IOException
  {
  }

  @Override
  protected void createServiceProviderInteractions(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary) throws IOException
  {
  }

  @Override
  protected void createServiceProviderSkeletonHandler(File providerFolder, AreaType area, ServiceType service, ServiceSummary summary, boolean isDelegate) throws IOException
  {
    String className = service.getName();
    String comment;
    if (isDelegate)
    {
      className += "DelegationSkeletonGWT";
      comment = "Provider Delegation skeleton for " + className + " service.";
    }
    else
    {
      className += "InheritanceSkeletonGWT";
      comment = "Provider Inheritance skeleton for " + className + " service.";
    }

    ClassWriter file = createClassFile(providerFolder, className);

    file.addPackageStatement(area, service, PROVIDER_FOLDER);

    String throwsMALException = createElementType(file, StdStrings.MAL, null, null, StdStrings.MALEXCEPTION);
    String handlerName = createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");

    String implementsList = createElementType(file, area.getName(), service.getName(), CONSUMER_FOLDER, service.getName() + "GWT");
    if (!isDelegate)
    {
      implementsList += ", " + createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Handler");
    }

    file.addClassOpenStatement(className, false, !isDelegate, "com.google.gwt.user.server.rpc.RemoteServiceServlet", implementsList, comment);

    if (isDelegate)
    {
      file.addClassVariable(false, false, StdStrings.PRIVATE, handlerName, false, false, false, "delegate", (String) null, null);
    }

    if (isDelegate)
    {
      MethodWriter method = file.addConstructor(StdStrings.PUBLIC, className, handlerName + " delegate", "", null);
      method.addMethodStatement(createMethodCall("this.delegate = delegate"));
      method.addMethodCloseStatement();
    }
    else
    {
      String skeletonName = createElementType(file, area.getName(), service.getName(), PROVIDER_FOLDER, service.getName() + "Skeleton");
      MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, "setSkeleton", skeletonName + " skeleton", null);
      method.addMethodStatement("// Not used in the inheritance pattern (the skeleton is 'this')");
      method.addMethodCloseStatement();
    }

    // for each IP type add handler code
    String delegateCall = "";
    if (isDelegate)
    {
      delegateCall = createMethodCall("delegate.");
    }

    for (OperationSummary op : summary.getOperations())
    {
      switch (op.getPattern())
      {
        case SEND_OP:
        {
          MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), null), throwsMALException);

          String opArgs = createArgNameOrNull(op.getArgTypes());
          method.addMethodStatement(createMethodCall(delegateCall + op.getName() + "(" + opArgs + ", null)"));

          method.addMethodCloseStatement();
          break;
        }
        case SUBMIT_OP:
        {
          MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, StdStrings.VOID, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), null), throwsMALException);

          String opArgs = createArgNameOrNull(op.getArgTypes());
          method.addMethodStatement(createMethodCall(delegateCall + op.getName() + "(" + opArgs + ", null)"));

          method.addMethodCloseStatement();
          break;
        }
        case REQUEST_OP:
        {
          String opRetType = createOperationReturnType(file, area, service, op);
          MethodWriter method = file.addMethodOpenStatement(false, false, StdStrings.PUBLIC, false, true, opRetType, op.getName(), createOperationArguments(getConfig(), file, op.getArgTypes(), null), throwsMALException);

          String opArgs = createArgNameOrNull(op.getArgTypes());
          method.addMethodStatement(createMethodCall("return " + delegateCall + op.getName() + "(" + opArgs + ", null)"));

          method.addMethodCloseStatement();
          break;
        }
        case INVOKE_OP:
        {
          break;
        }
        case PROGRESS_OP:
        {
          break;
        }
        case PUBSUB_OP:
        {
          break;
        }
      }
    }

    file.addClassCloseStatement();

    file.flush();
  }
}
