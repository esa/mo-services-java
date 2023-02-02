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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class LoggingBase {

    static protected PrintStream oldOut = System.out;
    static protected boolean teeToOut = true;
    static protected Writer out = null;
    static protected Date runTime = null;
    static final private SimpleDateFormat fileFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss");
    static final private SimpleDateFormat logFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    public LoggingBase() {
        Date now = new Date();

        setRuntime(now.getTime());
    }

    public LoggingBase(boolean ignoreStdOut) {
        teeToOut = false;
    }

    static protected void setRuntime(long newValue) {
        if (null == runTime) {
            runTime = new Date(newValue);
        }
    }

    static protected File createLoggingFile(String filename) throws IOException {
        return createLoggingFile(filename, null);
    }

    static protected File createLoggingFile(String filename, File dir) throws IOException {
        String time = "";

        if (null != runTime) {
            try {
                time = fileFmt.format(runTime);
            } catch (Exception ex) {
            }
        }

        return File.createTempFile("zzz_CCSDS_" + time + "_" + filename, ".txt", dir);
    }

    static protected void openLogFile(String filename, String dirname) {
        if (null == out) {
            File outDir = null;

            if (null != dirname) {
                outDir = new File(dirname);
                if ((!outDir.canWrite()) || (!outDir.isDirectory())) {
                    outDir = null;
                }
            }

            if (null != filename) {
                try {
                    FileOutputStream os = new FileOutputStream(createLoggingFile(filename, outDir));
                    out = new OutputStreamWriter(os);
                    System.setOut(new PrintStream(os));
                    System.setErr(new PrintStream(os));

                    logMessage("LOGFILE : " + filename + " : " + outDir);
                    if (null != dirname) {
                        outDir = new File(dirname);
                        logMessage("        : " + outDir.canWrite() + " : " + outDir.isDirectory() + " : " + outDir.toString());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    out = null;
                }
            }
        }
    }

    static public void logMessage(String msg) {
        logMessage(out, msg);
    }

    static protected void logMessage(Writer ow, String msg) {
        Date now = new Date();
        String str = logFmt.format(now) + " : " + msg;

        if (null != ow) {
            try {
                ow.write(str);
                ow.write("\n");
                ow.flush();

                if (teeToOut) {
                    oldOut.println(str);
                    oldOut.flush();
                }
            } catch (IOException ex) {
                // nop
            }
        } else {
            System.out.println(str);
        }
    }
}
