/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Test bed utilities
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
package org.ccsds.moims.mo.testbed.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 */
public class RemoteProcess extends LoggingBase implements Executable.ExitCondition
{
  private ServerSocket socket;

  public static void main(String[] argv) throws Exception
  {
    new RemoteProcess().runProcess(argv);
  }

  public RemoteProcess()
  {
    super(true);
  }

  public void runProcess(String[] argv) throws Exception
  {
    String logFilename = "RemoteProviderRunner";
    String logDir = null;

    if (argv.length > 2)
    {
      try
      {
        long logNumber = Long.parseLong(argv[0]);
        setRuntime(logNumber);

        logFilename = argv[2].substring(argv[2].lastIndexOf('.') + 1);
      }
      catch (Exception ex)
      {
        logMessage(new FileWriter(File.createTempFile("zzz_ERROR_LOG", ".txt")), "ERROR: First parameter to RemoteProcess must be a numeric log file number, received: " + argv[0]);
      }
    }

    if (argv.length > 3)
    {
      logDir = argv[3];
    }

    openLogFile(logFilename, logDir);

    /** Should have at least three arguments, the second is the port number to
     * open and listen on for the termination signal the second is the class
     * to instatiate and execute
     */
    if (argv.length > 2)
    {
      Executable obj = null;

      try
      {
        int port = Integer.parseInt(argv[1]);
        socket = new ServerSocket(port);

        logMessage("Opening termination signal port: " + socket.toString());

        Class cls = Class.forName(argv[2]);

        if (Executable.class.isAssignableFrom(cls))
        {
          obj = (Executable) cls.newInstance();
        }
        else
        {
          logMessage("ERROR: Third parameter class must implement interface " + Executable.class.getName() + ", received: " + argv[2]);
        }
      }
      catch (NumberFormatException ex)
      {
        logMessage("ERROR: Second parameter to RemoteProcess must be a numeric port number, received: " + argv[1]);
      }
      catch (IOException ex)
      {
        logMessage("ERROR: Unable to open port: " + argv[1] + " : " + ex.getLocalizedMessage());
      }
      catch (ClassNotFoundException ex)
      {
        logMessage("ERROR: Unable to locate class : " + argv[2] + " : " + ex.getLocalizedMessage());
      }
      catch (InstantiationException ex)
      {
        logMessage("ERROR: Unable to instantiate class : " + argv[2] + " : " + ex.getLocalizedMessage());
      }
      catch (IllegalAccessException ex)
      {
        logMessage("ERROR: Unable to instantiate class : " + argv[2] + " : " + ex.getLocalizedMessage());
      }

      /** we execute the test outside of the above exception handlers to that
       * any exceptions thrown by the test are not hidden.
       */
      if (null != obj)
      {
        // copy args and execute test
        String[] testArgv = new String[argv.length - 3];
        for (int i = 0; i < testArgv.length; i++)
        {
          testArgv[i] = argv[i + 3];
        }

        // execute the test
        logMessage("Executing test");
        obj.execute(out, this, testArgv);
        logMessage("Finished test");

        logMessage("Exiting VM: " + socket.toString());
        System.exit(-1);

        return;
      }
    }
    else
    {
      logMessage("ERROR: Incorrect number of arguments received: " + String.valueOf(argv.length));
    }

    logMessage("Usage: <log file number> <port number> <class to execute> [arguments for class]");
  }

  public void waitForExitSignal()
  {
    try
    {
      logMessage("Listening for termination signal on port: " + socket.toString());

      socket.accept();

      logMessage("Received kill signal: " + socket.toString());
    }
    catch (IOException ex)
    {
      logMessage("ERROR: IO Exception on kill socket, exiting VM : " + ex.getLocalizedMessage());
    }

    System.out.flush();
  }

  public void startExitSignalWaitingThread()
  {
    // fire up the port listener
    VMKiller killer = new VMKiller(socket);
    killer.start();
  }

  private static class VMKiller extends Thread
  {
    public boolean stopping = false;
    private final ServerSocket socket;

    public VMKiller(ServerSocket socket)
    {
      super("VMKiller");

      this.socket = socket;
    }

    public void run()
    {
      boolean killVM = true;

      try
      {
        logMessage("Listening for termination signal on port: " + socket.toString());

        socket.accept();

        synchronized (this)
        {
          if (stopping)
          {
            logMessage("Accept returned but internal stop signaled: " + socket.toString());

            killVM = false;
          }
          else
          {
            logMessage("Received kill signal: " + socket.toString());
          }
        }
      }
      catch (IOException ex)
      {
        synchronized (this)
        {
          if (stopping)
          {
            logMessage("Received internal stop signal: " + socket.toString());
            killVM = false;
          }
          else
          {
            logMessage("ERROR: IO Exception on kill socket, exiting VM : " + ex.getLocalizedMessage());
          }
        }
      }

      System.out.flush();

      if (killVM)
      {
        logMessage("Exiting VM: " + socket.toString());
        System.exit(-1);
      }
    }

    public synchronized void pleaseStop()
    {
      stopping = true;

      try
      {
        socket.close();
      }
      catch (IOException ex)
      {
        logMessage("ERROR: IO Exception attempting to close kill socket : " + ex.getLocalizedMessage());
      }
    }
  }
}
