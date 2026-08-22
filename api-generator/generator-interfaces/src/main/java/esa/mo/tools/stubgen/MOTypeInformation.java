/* ----------------------------------------------------------------------------
 * Copyright (C) 2026      European Space Agency
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

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.StdStrings;
import esa.mo.tools.stubgen.specification.TypeInformation;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.xsd.AreaType;
import esa.mo.xsd.ServiceType;
import esa.mo.xsd.TypeReference;

/**
 * {@link TypeInformation} implementation that composes a neutral
 * {@link MOTypeRegistry} for type classification and renders the fully
 * qualified type names using a {@link GeneratorConfiguration}.
 * <p>
 * The rendering is driven entirely by the supplied configuration (package
 * prefix, naming separator, structure folder), so a generator for a different
 * target language only needs to provide a different configuration, or its own
 * {@code TypeInformation} implementation if its naming rules differ
 * structurally.
 */
public class MOTypeInformation implements TypeInformation {

    private final MOTypeRegistry registry;
    private final GeneratorConfiguration config;

    /**
     * Constructor.
     *
     * @param registry The neutral type registry to classify types against.
     * @param config The configuration that drives the type-name rendering.
     */
    public MOTypeInformation(MOTypeRegistry registry, GeneratorConfiguration config) {
        this.registry = registry;
        this.config = config;
    }

    @Override
    public boolean isAbstract(TypeReference type) {
        return registry.isAbstract(type);
    }

    @Override
    public boolean isEnum(TypeReference type) {
        return registry.isEnum(type);
    }

    @Override
    public boolean isAttributeType(TypeReference type) {
        return registry.isAttributeType(type);
    }

    @Override
    public boolean isAttributeNativeType(TypeReference type) {
        return registry.isAttributeNativeType(type);
    }

    @Override
    public String getAreaPackage(String area) {
        return config.getAreaPackage(area);
    }

    @Override
    public String createElementType(TypeReference type, boolean isStructure) {
        if (type == null) {
            return null;
        }

        String typeName = type.isObjectRef() ? "ObjectRef<" + type.getName() + ">" : type.getName();
        return createElementType(type.getArea(), type.getService(),
                isStructure ? config.getStructureFolder() : null, typeName);
    }

    @Override
    public String createElementType(String areaName, String serviceName, String typeName) {
        return createElementType(areaName, serviceName, config.getStructureFolder(), typeName);
    }

    /**
     * Creates the full name of a structure type from the supplied details.
     *
     * @param area The area of the type.
     * @param service The service of the type, may be null.
     * @param type The type.
     * @return the full name of the type.
     */
    public String createElementType(AreaType area, ServiceType service, String type) {
        String areaName = (area != null) ? area.getName() : null;
        String serviceName = (service != null) ? service.getName() : null;
        return createElementType(areaName, serviceName, config.getStructureFolder(), type);
    }

    /**
     * Creates the full name of a type from the supplied details.
     *
     * @param area The area of the type.
     * @param service The service of the type, may be null.
     * @param extraPackageLevel String to insert after the area/service before
     * the type name.
     * @param type The type.
     * @return the full name of the type.
     */
    public String createElementType(String area, String service, String extraPackageLevel, String type) {
        if (area == null) {
            return type;
        }

        if (type.contains("ObjectRef<") || type.contains("ObjectRef(")) {
            String internalType = extractTypeFromObjectRef(type);
            internalType = createElementType(area, service, extraPackageLevel, internalType);
            return convertToNamespace("org.ccsds.moims.mo.mal.structures.ObjectRef<" + internalType + ">");
        }

        if (type.contains("ObjectRefList<") || type.contains("ObjectRefList(")) {
            return convertToNamespace("org.ccsds.moims.mo.mal.structures.ObjectRefList");
        }

        String retVal = "";

        if (registry.isAttributeType(TypeUtils.createTypeReference(area, service, type, false))) {
            AttributeTypeDetails details = registry.getAttributeDetails(area, type);
            retVal = details.getTargetType();
        } else {
            if (StdStrings.XML.equals(area)) {
                retVal = config.getAreaPackage(service) + StubUtils.preCap(type);
            } else {
                retVal += config.getAreaPackage(area) + area.toLowerCase() + config.getNamingSeparator();

                if (service != null) {
                    retVal += service.toLowerCase() + config.getNamingSeparator();
                }

                if (extraPackageLevel != null && extraPackageLevel.length() > 0) {
                    retVal += extraPackageLevel + config.getNamingSeparator();
                }

                retVal += type;
            }
        }

        return convertToNamespace(retVal);
    }

    @Override
    public String convertToNamespace(String targetType) {
        return targetType;
    }

    // ------------------------------------------------------------------------
    // Convenience lookups delegating to the composed registry, so a single
    // type object covers all type queries used by the generators and helpers.
    // ------------------------------------------------------------------------
    /**
     * Returns enumeration details if enumeration type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public esa.mo.xsd.EnumerationType getEnum(TypeReference type) {
        return registry.getEnum(type);
    }

    /**
     * Returns attribute details if attribute type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public AttributeTypeDetails getAttributeDetails(TypeReference type) {
        return registry.getAttributeDetails(type);
    }

    /**
     * Returns attribute details if attribute type.
     *
     * @param area the type area.
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public AttributeTypeDetails getAttributeDetails(String area, String type) {
        return registry.getAttributeDetails(area, type);
    }

    /**
     * Returns attribute details if attribute type.
     *
     * @param area the type area.
     * @param service the type service.
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public AttributeTypeDetails getAttributeDetails(String area, String service, String type) {
        return registry.getAttributeDetails(area, service, type);
    }

    /**
     * Returns true if the type is a native type.
     *
     * @param type the type to look for.
     * @return true if native.
     */
    public boolean isNativeType(String type) {
        return registry.isNativeType(type);
    }

    /**
     * Returns native details if native type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public esa.mo.tools.stubgen.specification.NativeTypeDetails getNativeType(String type) {
        return registry.getNativeType(type);
    }

    /**
     * Returns true if the type definition has been loaded.
     *
     * @param type the type to look for.
     * @return true if a known type.
     */
    public boolean isKnownType(TypeReference type) {
        return registry.isKnownType(type);
    }

    /**
     * Returns true if the type is a composite.
     *
     * @param type the type to look for.
     * @return true if a composite.
     */
    public boolean isComposite(TypeReference type) {
        return registry.isComposite(type);
    }

    /**
     * Returns composite details if composite type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public esa.mo.xsd.CompositeType getCompositeDetails(TypeReference type) {
        return registry.getCompositeDetails(type);
    }

    /**
     * Returns error details if defined.
     *
     * @param area The area that declares the error, which is part of its identity.
     * @param error The name of the error to look for.
     * @return the details if found, otherwise null.
     */
    public esa.mo.xsd.ErrorDefinitionType getErrorDefinition(String area, String error) {
        return registry.getErrorDefinition(area, error);
    }

    /**
     * Strips the outer {@code ObjectRef}/{@code ObjectRefList} wrapper from a
     * type name, returning the contained type name.
     *
     * @param type The type name.
     * @return the contained type name.
     */
    public static String extractTypeFromObjectRef(String type) {
        if (!type.contains(StdStrings.OBJECTREF)) {
            return type;
        }
        if (type.contains(StdStrings.OBJECTREF + "<") || type.contains(StdStrings.OBJECTREF + "List<")) {
            return type.substring(type.indexOf('<') + 1, type.indexOf('>'));
        }
        if (type.contains(StdStrings.OBJECTREF + "(") || type.contains(StdStrings.OBJECTREF + "List(")) {
            return type.substring(type.indexOf('(') + 1, type.indexOf(')'));
        }
        return type;
    }

    /**
     * Returns true if the supplied type is an {@code ObjectRef}.
     *
     * @param elementType The type to check.
     * @return true if it is an ObjectRef.
     */
    public static boolean isObjectRef(TypeReference elementType) {
        if (elementType.isObjectRef()) {
            return true;
        }

        // The code below is to allow compatibility with the old style. Example: ObjectRef(xyz)
        String type = elementType.getName();
        return !extractTypeFromObjectRef(type).equals(type);
    }
}
