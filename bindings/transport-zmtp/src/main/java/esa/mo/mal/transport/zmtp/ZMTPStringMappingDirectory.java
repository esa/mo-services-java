/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.MALException;

/**
 * ZMTP Mapping Directory class.
 *
 * Maps integer indexes to strings.
 */
public class ZMTPStringMappingDirectory {

    private final Map<Integer, String> keyToValueMap = new HashMap<Integer, String>();
    private final Map<String, Integer> valueToKeyMap = new HashMap<String, Integer>();

    /**
     * Cleans currently stored String Mapping Directory and loads String Mapping
     * Directory from given file.
     *
     * File should contain a set of lines, each containing an integer identifier
     * followed by a space and string value, for e.g.:<br>
     * 1234 malzmtp://12.3.4.5:6789/Demo
     *
     * @param filePath Path to the file containing the directory
     * @throws MALException
     */
    public void loadDirectory(String filePath) throws MALException {
        keyToValueMap.clear();
        valueToKeyMap.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    // empty line, skip
                    continue;
                }
                int spaceIndex = line.indexOf(" ");
                if (spaceIndex == -1) {
                    throw new MALException("Cannot find a space character "
                            + "in Mapping Directory entry: " + line);
                }
                if (spaceIndex >= line.length()) {
                    throw new MALException(
                            "Space has to be followed by a value "
                            + "in Mapping Directory entry: " + line);
                }
                int directoryIndex = Integer.parseUnsignedInt(line.substring(0, spaceIndex));
                if (directoryIndex <= 0) {
                    throw new MALException(MessageFormat.format(
                            "Error parsing Mapping Directory entry \"{0}\" - "
                            + "MDK value has to be in range of 1 to 2147483647",
                            line));
                }
                String directoryString = line.substring(spaceIndex + 1);
                addEntry(directoryIndex, directoryString);
            }
        } catch (FileNotFoundException ex) {
            throw new MALException("Cannot find directory file: " + filePath);
        } catch (IOException ex) {
            throw new MALException("Error reading directory file: " + filePath);
        } catch (NumberFormatException ex) {
            throw new MALException("Error parsing integer: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new MALException("Invalid directory data: " + ex.getMessage());
        }
    }

    public void addEntry(int key, String value) throws IllegalArgumentException {
        if (keyToValueMap.get(key) != null) {
            throw new IllegalArgumentException("Key " + key
                    + " already exists in the directory");
        }
        if (valueToKeyMap.get(value) != null) {
            throw new IllegalArgumentException("Value "
                    + value + " already exists in the directory");
        }
        keyToValueMap.put(key, value);
        valueToKeyMap.put(value, key);
    }

    public String getValue(int key) {
        return keyToValueMap.get(key);
    }

    public int getKey(String value) {
        Object key = valueToKeyMap.get(value);
        if (key != null) {
            return (Integer) key;
        }
        return -1;
    }

}
