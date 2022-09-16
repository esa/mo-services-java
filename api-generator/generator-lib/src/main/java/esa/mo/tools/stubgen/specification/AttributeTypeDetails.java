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
 * Holds details about MAL attribute types.
 */
public final class AttributeTypeDetails {

    /**
     * The MAL type.
     */
    private final String malType;
    /**
     * True if this Attribute type is represented by a language native type.
     */
    private final boolean nativeType;
    /**
     * The language type to use.
     */
    private final String targetType;
    /**
     * An example of the default value for this type.
     */
    private final String defaultValue;

    /**
     * Constructor.
     *
     * @param ti The source of type resolution.
     * @param malType The MAL type this represents.
     * @param isNativeType True if native type.
     * @param targetType The type to generate too.
     * @param defaultValue An example of a default value.
     */
    public AttributeTypeDetails(final TypeInformation ti,
            final String malType,
            final boolean isNativeType,
            final String targetType,
            final String defaultValue) {
        this.malType = malType;
        this.nativeType = isNativeType;

        if (!nativeType) {
            this.targetType = ti.createElementType(null, StdStrings.MAL, null, targetType);
        } else {
            this.targetType = targetType;
        }

        this.defaultValue = defaultValue;
    }

    /**
     * Returns the MAL type.
     *
     * @return the MAL type.
     */
    public String getMalType() {
        return malType;
    }

    /**
     * Returns true if the attribute is represented by a native type.
     *
     * @return true if attribute is a native type.
     */
    public boolean isNativeType() {
        return nativeType;
    }

    /**
     * Returns the target type.
     *
     * @return the target type.
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * Returns the default value.
     *
     * @return the default value.
     */
    public String getDefaultValue() {
        return defaultValue;
    }
}
