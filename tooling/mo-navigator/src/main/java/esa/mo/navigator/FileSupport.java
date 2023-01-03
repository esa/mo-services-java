/* ----------------------------------------------------------------------------
 * Copyright (C) 2023      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA MO Navigator
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
package esa.mo.navigator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * The FileSupport class supports the reading and writing of files.
 *
 * @author Cesar Coelho
 */
public class FileSupport {

    /**
     * The readFile method opens a file and returns a textual string with the
     * content of the file.
     *
     * @param filepath The name of the file that has the content
     * @return The textual string
     * @throws FileNotFoundException if the file was not found
     * @throws IOException if there is a problem reading the next line of the
     * file
     */
    public static String readFile(String filepath) throws FileNotFoundException, IOException {
        // Open the File and read the content
        File file = new File(filepath); // Open the file
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(isr);

        String line = br.readLine(); // Reads the first line!
        StringBuilder str = new StringBuilder();

        for (int i = 1; line != null; i++) {
            str.append(line);
            str.append("\n");
            line = br.readLine(); // Read the next line
        }

        br.close();
        return str.toString();
    }

    /**
     * The writeFile method writes a file in the provided path with the provided
     * content.
     *
     * @param filepath The path to store the file
     * @param content The content of the file
     * @throws FileNotFoundException if the file was not found
     * @throws IOException if there is a problem writing the file
     */
    public static void writeFile(String filepath, String content) throws FileNotFoundException, IOException {
        File file = new File(filepath);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.write(content.getBytes());
        fos.flush();
        fos.close();
    }
}
