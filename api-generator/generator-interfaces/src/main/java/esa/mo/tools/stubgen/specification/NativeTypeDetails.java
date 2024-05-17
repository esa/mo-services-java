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
package esa.mo.tools.stubgen.specification;

/**
 * Holds details about native types used by the auto generator.
 */
public class NativeTypeDetails {

    /**
     * The language specific type to use.
     */
    private final String languageTypeName;
    /**
     * True is this type is an object.
     */
    private final boolean object;
    /**
     * True is this type is a parameterised type.
     */
    private final boolean parameterised;
    /**
     * If an import is needed, then this is the file to import. If null then no
     * import needed.
     */
    private final String importFileName;

    /**
     * Constructor.
     *
     * @param languageTypeName the language type.
     * @param isObject True if this is an object.
     * @param isParameterised true if parameterised.
     * @param importFileName Import file name.
     */
    public NativeTypeDetails(final String languageTypeName,
            final boolean isObject,
            final boolean isParameterised,
            final String importFileName) {
        this.languageTypeName = languageTypeName;
        this.object = isObject;
        this.parameterised = isParameterised;
        this.importFileName = importFileName;
    }

    /**
     * Returns true if an import statement is needed for this type.
     *
     * @return the import requirement.
     */
    public boolean needsImport() {
        return null != importFileName;
    }

    /**
     * Returns the language type.
     *
     * @return the language type name.
     */
    public String getLanguageTypeName() {
        return languageTypeName;
    }

    /**
     * True if it is an object type.
     *
     * @return True if an object.
     */
    public boolean isObject() {
        return object;
    }

    /**
     * True if it is parameterised.
     *
     * @return True if parameterised.
     */
    public boolean isParameterised() {
        return parameterised;
    }

    /**
     * The import file name if needed.
     *
     * @return the import file name.
     */
    public String getImportFileName() {
        return importFileName;
    }
}
