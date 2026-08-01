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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class RemoteProcessRunner extends LoggingBase {

    private final String defaultClass;
    private static final String osName = System.getProperty("os.name");
    private static final boolean isLinux = osName.contains("Linux");

    private Process proc = null;
    private int portNum = 0;

    public RemoteProcessRunner() {
        this(null);
    }

    public RemoteProcessRunner(String defaultClass) {
        this.defaultClass = defaultClass;
    }

    public boolean startProcess() throws Exception {
        return startProcess(defaultClass);
    }

    public boolean startProcess(String cls) throws Exception {
        logMessage("Starting remote process with class: " + cls);
        logMessage("OS: " + System.getProperty("os.name"));
        {
            ServerSocket sock = new ServerSocket(0);
            portNum = sock.getLocalPort();
            sock.close();
        }

        System.gc();

        Vector v = new Vector();
        v.add("java");

        int x = 1;
        String prop = null;
        while (null != (prop = System.getProperty(Configuration.REMOTE_CMD_PROPERTY_PREFIX
                + "java." + Configuration.getOSname() + "." + String.valueOf(x++)))) {
            v.add(prop);
        }

        v.add("-D" + Configuration.LOCAL_CONFIGURATION_DIR + "="
                + System.getProperty(Configuration.REMOTE_CONFIGURATION_DIR));

        String remoteExtraArgs = System.getProperty(Configuration.REMOTE_EXTRA_ARGS);

        if (null != remoteExtraArgs) {
            logMessage("The REMOTE_EXTRA_ARGS are: " + remoteExtraArgs);
            logMessage("The REMOTE_EXTRA_ARGS will be ignored!");
            //v.addAll(Arrays.asList(remoteExtraArgs.split(" ")));
        }
        v.add("-classpath");
        v.add(createClasspath(new String[]{
            Configuration.REMOTE_CLASSPATH_EXTRA_JARS
        }, new String[]{
            Configuration.REMOTE_CLASSPATH_MAVEN_JARS
        }, new String[]{
            Configuration.REMOTE_CLASSPATH_FILTER_STRING
        }));
        v.add(RemoteProcess.class.getCanonicalName());
        v.add(String.valueOf(runTime.getTime()));
        v.add(String.valueOf(portNum));
        v.add(cls);

        if (null != System.getProperty(Configuration.REMOTE_OUTPUT_DIR)) {
            v.add("\"" + System.getProperty(Configuration.REMOTE_OUTPUT_DIR) + "\"");
        }

        String[] cmdLines = createCommandLine(v);

        logMessage("Cmd line: " + Arrays.toString(cmdLines));

        try {
            proc = Runtime.getRuntime().exec(cmdLines);
        } catch (Exception ex) {
            logMessage("ERROR: Exception thrown starting remote process: " + ex.getMessage());
            throw ex;
        } catch (Throwable ex) {
            logMessage("ERROR: Throwable thrown starting remote process: " + ex.getMessage());
        }

        long waitTime = 3000;
        logMessage("The remote process is starting. Waiting for: " + waitTime + " ms");
        // wait a little here to give it time to start up properly
        Thread.sleep(waitTime);

        return processIsRunning();
    }

    public boolean processIsStopped() {
        return !processIsRunning();
    }

    public boolean processIsRunning() {
        try {
            logMessage("Checking process status...");
            proc.exitValue();
        } catch (IllegalThreadStateException ex) {
            // should be running so we end up here
            logMessage("Process is still running");
            return true;
        }

        try {
            String txtStream = extractString(proc.getInputStream());
            String errorStream = extractString(proc.getErrorStream());
            logMessage("txtStream: " + txtStream);
            logMessage("errorStream: " + errorStream);
        } catch (IOException ex) {
            Logger.getLogger(RemoteProcessRunner.class.getName()).log(
                    Level.SEVERE, "Something went wrong!", ex);
        }

        logMessage("Process has exited");
        // if we are here then the process terminated for some reason
        return false;
    }

    public void waitForProcess() throws Exception {
        proc.waitFor();
    }

    public void stopProcess() {
        try {
            logMessage("Requesting remote process to exit");
            new Socket("localhost", portNum).close();
        } catch (IOException ex) {
            logMessage(ex.getLocalizedMessage());
        }
    }

    public void waitForSeconds(int seconds) throws Exception {
        Thread.sleep(seconds * 1000);
    }

    public static String createClasspath(String[] extraJarList,
            String[] mavenJarList, String[] filterJarList) throws Exception {
        StringBuffer cp = new StringBuffer();
        String sep = System.getProperty("path.separator");

        for (String str : extraJarList) {
            String extra = System.getProperty(str);
            logMessage("Extra JARs: " + extra);

            if (null != extra) {
                cp.append(extra);
            }
        }

        appendMavenDependencies(cp, File.separator, sep, mavenJarList);

        String[] classpath = currentClasspath(sep);

        if (classpath.length > 0) {
            StringBuilder filterEnv = new StringBuilder();

            for (String str : filterJarList) {
                filterEnv.append(System.getProperty(str));
                filterEnv.append(',');
            }

            String[] filterStr = null;

            if (filterEnv.length() > 0) {
                filterStr = filterEnv.toString().toLowerCase().split(",");
            }

            if (!isLinux) {
                cp.append('"'); // Adds a Quotation mark before the jars
            }

            for (String entry : classpath) {
                addClasspathEntry(cp, entry, sep, filterStr);
            }

            if (!isLinux) {
                cp.append('"'); // Adds a Quotation mark after the jars
            }
        }

        return cp.toString();
    }

    /**
     * Returns the entries of the classpath this process was started with, so
     * that the remote process can be started with the same one.
     *
     * Up to Java 8 the class loader could be cast to a URLClassLoader to read
     * them back, and up to surefire 2 the tests ran under such a loader. Neither
     * holds any more, so fall back to the java.class.path system property, which
     * works both on a modern JDK and under the system class loader.
     *
     * @param sep The path separator of this platform.
     * @return The classpath entries, empty if none could be determined.
     */
    private static String[] currentClasspath(String sep) throws Exception {
        ClassLoader loader = RemoteProcessRunner.class.getClassLoader();

        if (loader instanceof URLClassLoader) {
            URL[] urls = ((URLClassLoader) loader).getURLs();

            if (urls.length > 0) {
                String[] entries = new String[urls.length];

                for (int i = 0; i < urls.length; i++) {
                    entries[i] = urls[i].toURI().getPath();
                }

                return entries;
            }
        }

        String classpath = System.getProperty("java.class.path");

        if (classpath == null || classpath.isEmpty()) {
            return new String[0];
        }

        return classpath.split(java.util.regex.Pattern.quote(sep));
    }

    private static void appendMavenDependencies(StringBuffer cp,
            String pathSep, String cpSep, String[] mavenJarList) {
        String localRep = System.getProperty("localRepository");
        StringBuilder extraMaven = new StringBuilder();

        for (String str : mavenJarList) {
            extraMaven.append(System.getProperty(str));
            extraMaven.append(',');
        }

        if ((null != localRep) && (0 < extraMaven.length())) {
            String[] artifacts = extraMaven.toString().split(",");

            for (String artifact : artifacts) {
                if (0 < artifact.length()) {
                    String[] part = artifact.split(":");
                    if (part.length == 3) {
                        String fileName = localRep
                                + pathSep + part[0].replace(".", pathSep)
                                + pathSep + part[1] + pathSep + part[2]
                                + pathSep + part[1] + "-" + part[2] + ".jar";
                        logMessage("Maven extra dependency : " + fileName);
                        addClasspathEntry(cp, fileName, cpSep, null);
                    } else {
                        logMessage("Maven extra dependency is not formatted correctly : " + artifact);
                    }
                }
            }
        }
    }

    private static void addClasspathEntry(final StringBuffer cp,
            final String entry, final String sep, final String[] filterStr) {
        if ((!entry.toLowerCase().endsWith(".jar"))
                || (!filterString(entry.toLowerCase(), filterStr))) {
            if (cp.length() > 0) {
                cp.append(sep);
            }

            if (isLinux) {
                cp.append('"');
                cp.append(entry.replace(" ", "\\ "));
                cp.append('"');
            } else {
                //cp.append('"');
                cp.append(entry);
                //cp.append('"');
            }
        }
    }

    private static boolean filterString(final String entry, final String[] filters) {
        if (null != filters) {
            for (String filt : filters) {
                if (0 < filt.length()) {
                    if (entry.toLowerCase().contains(filt)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static String[] createCommandLine(Vector args) {
        Vector v = new Vector();

        int x = 1;
        String prop = null;
        while (null != (prop = System.getProperty(Configuration.REMOTE_CMD_PROPERTY_PREFIX
                + Configuration.getOSname() + "." + String.valueOf(x++)))) {
            v.add(prop);
        }

        v.addAll(args);

        String[] strArr = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            strArr[i] = (String) v.elementAt(i);
        }

        return strArr;
    }

    private static String extractString(InputStream in) throws IOException {
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
