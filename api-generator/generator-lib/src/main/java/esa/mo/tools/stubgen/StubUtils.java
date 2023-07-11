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

import esa.mo.tools.stubgen.specification.CompositeField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Set of helper methods for stub generation.
 */
public abstract class StubUtils {

    /**
     * Creates a file with the supplied contents.
     *
     * @param destinationFolderName The destination folder for the created file.
     * @param fileName The file name to use.
     * @param ext The file extension to use.
     * @param contents The contents of the file.
     * @throws IOException If there is an IO error.
     */
    public static void createResource(String destinationFolderName,
            String fileName, String ext, String contents) throws IOException {
        final Writer file = createLowLevelWriter(destinationFolderName, fileName, ext);
        file.append(contents);

        file.flush();
        file.close();
    }

    /**
     * Creates a Zip file containing the supplied files.
     *
     * @param destinationFolderName Destination folder for the zip file.
     * @param filenames The files to contain.
     * @param outFilename The file name of the created zip file.
     */
    public static void createZipfile(String destinationFolderName,
            String[] filenames, String outFilename) {
        byte[] buf = new byte[1024];

        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(
                    new FileOutputStream(destinationFolderName + outFilename));

            // Compress the files
            for (int i = 0; i < filenames.length; ++i) {
                FileInputStream in = new FileInputStream(destinationFolderName + "/" + filenames[i]);

                try {
                    // Add ZIP entry to output stream.
                    out.putNextEntry(new ZipEntry(filenames[i]));

                    // Transfer bytes from the file to the ZIP file
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }

                    // Complete the entry
                    out.closeEntry();
                } finally {
                    in.close();
                }
            }

            // Complete the ZIP file
            out.close();
        } catch (IOException e) {
        }
    }

    /**
     * Creates an output file.
     *
     * @param folder The folder to create the file in.
     * @param name The name of the file.
     * @param ext The extension of the file.
     * @return The new file.
     * @throws IOException If there is a problem creating the file.
     */
    public static File createLowLevelFile(String folder, String name,
            String ext) throws IOException {
        File file = new File(folder, name + "." + ext);
        if (!file.exists()) {
            if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file: "
                            + file.getPath());
                }
            } else {
                throw new IOException("Unable to create directory: "
                        + file.getParentFile().getPath());
            }
        }

        return file;
    }

    /**
     * Creates an output stream writer for a file.
     *
     * @param folder The folder to create the file in.
     * @param name The name of the file.
     * @param ext The extension of the file.
     * @return The new writer.
     * @throws IOException If there is a problem creating the file.
     */
    public static Writer createLowLevelWriter(String folder, String name,
            String ext) throws IOException {
        File file = createLowLevelFile(folder, name, ext);

        return new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"));
    }

    /**
     * Creates an output stream writer for a file.
     *
     * @param folder The folder to create the file in.
     * @param name The name of the file.
     * @param ext The extension of the file.
     * @return The new write.
     * @throws IOException If there is a problem creating the file.
     */
    public static Writer createLowLevelWriter(File folder, String name,
            String ext) throws IOException {
        File file = new File(folder, name + "." + ext);
        file.createNewFile();

        if (!file.exists()) {
            throw new IOException("Unable to create file: " + file.getPath());
        }

        return new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"));
    }

    /**
     * Creates a low level folder.
     *
     * @param parentFolder The folder to create the new folder in.
     * @param name The name of the new folder.
     * @return The new folder.
     * @throws FileNotFoundException If there is a problem creating the folder.
     */
    public static File createFolder(File parentFolder, String name) throws FileNotFoundException {
        String prefix = (parentFolder != null) ? parentFolder.getPath() + File.separator : "";
        File folder = new File(prefix + name.toLowerCase());
        folder.mkdirs();

        if (!folder.exists()) {
            throw new FileNotFoundException("Failed to create directory: " + folder.getPath());
        }

        return folder;
    }

    /**
     * Makes the first letter in a supplied string uppercase.
     *
     * @param str String to precap.
     * @return the converted string.
     */
    public static String preCap(String str) {
        if ((null != str) && (0 < str.length())) {
            str = String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
        }

        return str;
    }

    /**
     * Return the conditional string with the prefix string if it is not null or
     * empty.
     *
     * @param prefix Prefix string.
     * @param cond Conditional string list.
     * @return the processed list.
     */
    public static List<String> conditionalAdd(String prefix, List<String> cond) {
        List<String> rv = null;

        if (null != cond) {
            rv = new LinkedList<>();

            for (String string : cond) {
                rv.add(conditionalAdd(prefix, string));
            }
        }

        return rv;
    }

    /**
     * Return the conditional string with the prefix string if it is not null or
     * empty.
     *
     * @param prefix Prefix string.
     * @param cond Conditional string.
     * @return the concatenated string or null.
     */
    public static String conditionalAdd(String prefix, String cond) {
        if ((cond != null) && (cond.length() > 0)) {
            return prefix + cond;
        }

        return null;
    }

    /**
     * Concatenates an array of strings with intervening commas if required.
     *
     * @param preArgs First string.
     * @param args The string arguments.
     * @return The concatenated string.
     */
    public static String concatenateStringArguments(boolean preArgs, String... args) {
        StringBuilder buf = new StringBuilder("");

        for (String str : args) {
            if ((str != null) && !str.isEmpty()) {
                if (preArgs || (buf.length() > 0)) {
                    buf.append(", ");
                }
                buf.append(str);
            }
        }

        return buf.toString();
    }

    /**
     * Concatenates an array of arguments.
     *
     * @param args The arguments.
     * @return The concatenated arguments.
     */
    public static List<CompositeField> concatenateArguments(CompositeField... args) {
        return Arrays.asList(args);
    }

    /**
     * Concatenates an array of arguments.
     *
     * @param firstArg The initial argument to add.
     * @param args The arguments.
     * @return The concatenated arguments.
     */
    public static List<CompositeField> concatenateArguments(CompositeField firstArg,
            List<CompositeField>... args) {
        List<CompositeField> lst = new LinkedList<>();
        lst.add(firstArg);

        for (List<CompositeField> fields : args) {
            lst.addAll(fields);
        }

        return lst;
    }

    /**
     * Concatenates an array of arguments.
     *
     * @param firstArg The initial argument list to add to.
     * @param args The arguments.
     * @return The concatenated arguments.
     */
    public static List<CompositeField> concatenateArguments(
            List<CompositeField> firstArg, CompositeField... args) {
        firstArg.addAll(Arrays.asList(args));

        return firstArg;
    }
}
