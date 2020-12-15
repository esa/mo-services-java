/**
 * *****************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a framework for the CCSDS Mission Operations
 * services.
 *
 * This software is governed by the CeCILL-C license under French law and abiding by the rules of distribution of free
 * software. You can use, modify and/ or redistribute the software under the terms of the CeCILL-C license as circulated
 * by CEA, CNRS and INRIA at the following URL "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and rights to copy, modify and redistribute granted by the license,
 * users are provided only with a limited warranty and the software's author, the holder of the economic rights, and the
 * successive licensors have only limited liability.
 *
 * In this respect, the user's attention is drawn to the risks associated with loading, using, modifying and/or
 * developing or reproducing the software by the user in light of its specific status of free software, that may mean
 * that it is complicated to manipulate, and that also therefore means that it is reserved for developers and
 * experienced professionals having in-depth computer knowledge. Users are therefore encouraged to load and test the
 * software's suitability as regards their requirements in conditions enabling the security of their systems and/or data
 * to be ensured and, more generally, to use and operate it in the same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had knowledge of the CeCILL-C license and that you
 * accept its terms.
 ******************************************************************************
 */
package org.ccsds.moims.mo.testbed.suite;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.ccsds.moims.mo.testbed.util.Configuration;
import org.ccsds.moims.mo.testbed.util.LoggingBase;
import org.ccsds.moims.mo.testbed.util.RemoteProcessRunner;

public class MOMServer
{
  public static final String SERVER_START_CLASS = "org.ccsds.moims.mo.mal.test.mom.server.start.class";
  public static final String SERVER_START_ARGS = "org.ccsds.moims.mo.mal.test.mom.server.start.args";
  public static final String SERVER_START_PROPS = "org.ccsds.moims.mo.mal.test.mom.server.start.props";
  public static final String SERVER_STOP_CLASS = "org.ccsds.moims.mo.mal.test.mom.server.stop.class";
  public static final String SERVER_STOP_ARGS = "org.ccsds.moims.mo.mal.test.mom.server.stop.args";
  public static final String SERVER_STOP_PROPS = "org.ccsds.moims.mo.mal.test.mom.server.stop.props";
  private static MOMServer instance;

  public static boolean isRequired()
  {
    return (System.getProperty(SERVER_START_CLASS) != null);
  }

  public static MOMServer instance()
  {
    if (instance == null)
    {
      instance = new MOMServer();
    }
    return instance;
  }
  private Process momServerProcess;

  private MOMServer()
  {
    momServerProcess = null;
  }

  public void start() throws Exception
  {
    // Need to load the "org.ccsds.remote.cmdline." properties
    Properties prp = Configuration.getProperties("RemoteMALInstance.properties", true);
    System.getProperties().putAll(prp);

    String startClass = System.getProperty(SERVER_START_CLASS);
    if (startClass != null)
    {
      momServerProcess = exec(System.getProperty(SERVER_START_PROPS),
              startClass, System.getProperty(SERVER_START_ARGS));

      String res = waitServerStarting(momServerProcess);

      LoggingBase.logMessage("MOM started: " + res);

      closeServerStream(momServerProcess);

      try
      {
        LoggingBase.logMessage("Checking process status...");

        Thread.sleep(1000);

        momServerProcess.exitValue();
      }
      catch (IllegalThreadStateException ex)
      {
        // should be running so we end up here
        LoggingBase.logMessage("MOM is still running");
        return;
      }

      LoggingBase.logMessage("MOM has exited");
    }
  }

  public String waitServerStarting(Process p) throws Exception
  {
    BufferedReader br =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
    String line = br.readLine();
    if (line != null)
    {
      if (line.endsWith("ERROR"))
      {
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(line);
        while (((line = br.readLine()) != null)
                && (!line.equals("END")))
        {
          strBuf.append('\n');
          strBuf.append(line);
        }
        line = strBuf.toString();
      }
    }
    return line;
  }

  public void closeServerStream(Process p) throws Exception
  {
    try
    {
      p.getInputStream().close();
    }
    catch (Exception exc)
    {
    }
    try
    {
      p.getOutputStream().close();
    }
    catch (Exception exc)
    {
    }
    try
    {
      p.getErrorStream().close();
    }
    catch (Exception exc)
    {
    }
  }

  public void stop() throws Exception
  {
    if (momServerProcess != null)
    {
      Process stopCommand = exec(System.getProperty(SERVER_STOP_PROPS),
              System.getProperty(SERVER_STOP_CLASS),
              System.getProperty(SERVER_STOP_ARGS));
      LoggingBase.logMessage("Stop the MOM server");
      stopCommand.waitFor();
      LoggingBase.logMessage("MOM server is stopping...");
      momServerProcess.waitFor();
      LoggingBase.logMessage("MOM server is stopped.");
    }
  }

  private Process exec(String props, String className, String args) throws Exception
  {
    try
    {
      LoggingBase.logMessage("MOMServer.exec()");

      StringBuffer command = new StringBuffer();

      int x = 1;
      String prop;
      while (null != (prop = System.getProperty(Configuration.REMOTE_CMD_PROPERTY_PREFIX + Configuration.getOSname() + "." + String.valueOf(x++))))
      {
        command.append(prop);
        command.append(' ');
      }

      command.append("java ");
      if (props != null)
      {
        command.append(props);
      }
      command.append(" -classpath ");
      command.append(RemoteProcessRunner.createClasspath(new String[]
      {
        Configuration.REMOTE_CLASSPATH_EXTRA_JARS,
        Configuration.MOM_CLASSPATH_EXTRA_JARS
      }, new String[]
      {
        Configuration.REMOTE_CLASSPATH_MAVEN_JARS,
        Configuration.MOM_CLASSPATH_MAVEN_JARS
      }, new String[]
      {
        Configuration.REMOTE_CLASSPATH_FILTER_STRING,
        Configuration.MOM_CLASSPATH_FILTER_STRING
      }));
      command.append(" ");
      command.append(className);
      command.append(" ");
      if (args != null)
      {
        command.append(args);
      }

      LoggingBase.logMessage("Exec command: " + command);

      return Runtime.getRuntime().exec(command.toString());
    }
    catch (Throwable exc)
    {
      exc.printStackTrace();
      return null;
    }
  }
}
