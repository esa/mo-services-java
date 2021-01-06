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

import esa.mo.tools.stubgen.specification.StdStrings;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the configuration of the generator when used to generate a programming
 * language.
 */
public class GeneratorConfiguration {

    private final String defaultPackage;
    private final Map<String, String> areaPackages = new HashMap();
    private final String structureFolder;
    private final String factoryFolder;
    private final String bodyFolder;
    private final String namingSeparator;
    private final String nullValue;
    private final String sendOperationType;
    private final String submitOperationType;
    private final String requestOperationType;
    private final String invokeOperationType;
    private final String progressOperationType;
    private final String pubsubOperationType;

    /**
     * Constructor.
     *
     * @param defaultPackage Default package all classes are contained in.
     * @param structurePackage Folder used to hold generated type classes.
     * @param factoryPackage Folder used to hold generated type factory classes.
     * @param bodyPackage Folder used to hold generated message body classes.
     * @param separator Package/namespace separator.
     * @param nullValue How is null represented.
     * @param sendOpType Class used to represent a SEND operation.
     * @param submitOpType Class used to represent a SUBMIT operation.
     * @param requestOpType Class used to represent a REQUEST operation.
     * @param invokeOpType Class used to represent a INVOKE operation.
     * @param progressOpType Class used to represent a PROGRESS operation.
     * @param pubsubOpType Class used to represent a PUBSUB operation.
     */
    public GeneratorConfiguration(String defaultPackage, String structurePackage,
            String factoryPackage, String bodyPackage, String separator,
            String nullValue, String sendOpType, String submitOpType,
            String requestOpType, String invokeOpType, String progressOpType,
            String pubsubOpType) {
        this.defaultPackage = defaultPackage;
        this.structureFolder = structurePackage;
        this.factoryFolder = factoryPackage;
        this.bodyFolder = bodyPackage;
        this.namingSeparator = separator;
        this.nullValue = nullValue;
        this.sendOperationType = sendOpType;
        this.submitOperationType = submitOpType;
        this.requestOperationType = requestOpType;
        this.invokeOperationType = invokeOpType;
        this.progressOperationType = progressOpType;
        this.pubsubOperationType = pubsubOpType;
    }

    /**
     * Add a new package specification for a specific area.
     *
     * @param area the area being set.
     * @param newPackage the package to use.
     */
    public void addAreaPackage(String area, String newPackage) {
        if (StdStrings.XML.equalsIgnoreCase(area)) {
            String[] strs = newPackage.split("\\|");
            areaPackages.put(strs[1].toUpperCase(), strs[0] + ".");
        } else {
            areaPackages.put(area, newPackage + ".");
        }
    }

    /**
     * Resets package specification for specific areas.
     *
     */
    public void resetAreaPackages() {
        areaPackages.clear();
    }

    /**
     * Returns the structure folder.
     *
     * @return the structure folder
     */
    public String getStructureFolder() {
        return structureFolder;
    }

    /**
     * Returns the factory folder.
     *
     * @return the factory folder
     */
    public String getFactoryFolder() {
        return factoryFolder;
    }

    /**
     * Returns the body folder.
     *
     * @return the body folder
     */
    public String getBodyFolder() {
        return bodyFolder;
    }

    /**
     * Returns the area package.
     *
     * @param area The area to return the package for.
     * @return the area package
     */
    public String getAreaPackage(String area) {
        String value = areaPackages.get(area.toUpperCase());

        return (null == value) ? defaultPackage : value;
    }

    /**
     * Returns the naming separator.
     *
     * @return the naming separator
     */
    public String getNamingSeparator() {
        return namingSeparator;
    }

    /**
     * Returns the null value.
     *
     * @return the null value
     */
    public String getNullValue() {
        return nullValue;
    }

    /**
     * Returns the send operation type.
     *
     * @return the send operation type
     */
    public String getSendOperationType() {
        return sendOperationType;
    }

    /**
     * Returns the submit operation type.
     *
     * @return the submit operation type
     */
    public String getSubmitOperationType() {
        return submitOperationType;
    }

    /**
     * Returns the request operation type.
     *
     * @return the request operation type
     */
    public String getRequestOperationType() {
        return requestOperationType;
    }

    /**
     * Returns the invoke operation type.
     *
     * @return the invoke operation type
     */
    public String getInvokeOperationType() {
        return invokeOperationType;
    }

    /**
     * Returns the progress operation type.
     *
     * @return the progress operation type
     */
    public String getProgressOperationType() {
        return progressOperationType;
    }

    /**
     * Returns the pubsub operation type.
     *
     * @return the pubsub operation type
     */
    public String getPubsubOperationType() {
        return pubsubOperationType;
    }
}
