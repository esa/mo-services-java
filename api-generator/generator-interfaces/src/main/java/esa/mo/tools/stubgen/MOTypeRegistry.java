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

import esa.mo.tools.stubgen.specification.AttributeTypeDetails;
import esa.mo.tools.stubgen.specification.NativeTypeDetails;
import esa.mo.tools.stubgen.specification.TypeUtils;
import esa.mo.xsd.AttributeType;
import esa.mo.xsd.CompositeType;
import esa.mo.xsd.EnumerationType;
import esa.mo.xsd.ErrorDefinitionType;
import esa.mo.xsd.FundamentalType;
import esa.mo.xsd.TypeReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.bind.JAXBElement;
import w3c.xsd.ComplexType;
import w3c.xsd.NoFixedFacet;
import w3c.xsd.OpenAttrs;
import w3c.xsd.SimpleType;

/**
 * Language-neutral registry of the MO types parsed from the service
 * specifications. It owns the parsed type data and answers classification
 * questions about it (is a type an enum, abstract, composite, attribute or
 * native type, and lookups for their details).
 * <p>
 * It deliberately knows nothing about how types are rendered in any target
 * language; that is the job of a {@link esa.mo.tools.stubgen.specification.TypeInformation}
 * implementation, which composes this registry.
 */
public class MOTypeRegistry {

    private final Set<TypeKey> enumTypesSet = new TreeSet<>();
    private final Set<TypeKey> abstractTypesSet = new TreeSet<>();
    private final Map<TypeKey, Object> allTypesMap = new HashMap<>();
    private final Map<TypeKey, CompositeType> compositeTypesMap = new HashMap<>();
    private final Map<TypeKey, AttributeTypeDetails> attributeTypesMap = new HashMap<>();
    private final Map<String, NativeTypeDetails> nativeTypesMap = new HashMap<>();
    private final Map<String, ErrorDefinitionType> errorDefinitionMap = new HashMap<>();

    /**
     * Clears all the registered types.
     */
    public void reset() {
        enumTypesSet.clear();
        abstractTypesSet.clear();
        allTypesMap.clear();
        compositeTypesMap.clear();
        attributeTypesMap.clear();
        nativeTypesMap.clear();
        errorDefinitionMap.clear();
    }

    /**
     * Registers an error definition.
     *
     * @param error The error definition to register.
     */
    public void registerError(ErrorDefinitionType error) {
        errorDefinitionMap.put(error.getName(), error);
    }

    /**
     * Registers an attribute type.
     *
     * @param area The area of the type.
     * @param name The name of the type.
     * @param details The attribute type details.
     */
    public void registerAttributeType(String area, String name, AttributeTypeDetails details) {
        attributeTypesMap.put(new TypeKey(area, null, name), details);
    }

    /**
     * Registers a native type.
     *
     * @param name The name of the type.
     * @param details The native type details.
     */
    public void registerNativeType(String name, NativeTypeDetails details) {
        nativeTypesMap.put(name, details);
    }

    /**
     * Loads the enumeration, fundamental, attribute and composite types from a
     * parsed area or service data-type list.
     *
     * @param area The area the types belong to.
     * @param service The service the types belong to, may be null.
     * @param typeList The list of parsed type objects.
     */
    public void loadTypesFromObjectList(String area, String service, List<Object> typeList) {
        for (Object object : typeList) {
            if (object instanceof EnumerationType) {
                EnumerationType ty = (EnumerationType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                allTypesMap.put(key, object);
                enumTypesSet.add(key);
            } else if (object instanceof FundamentalType) {
                FundamentalType ty = (FundamentalType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                allTypesMap.put(key, object);
                abstractTypesSet.add(key);
            } else if (object instanceof AttributeType) {
                AttributeType ty = (AttributeType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                allTypesMap.put(key, object);
            } else if (object instanceof CompositeType) {
                CompositeType ty = (CompositeType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                allTypesMap.put(key, object);
                compositeTypesMap.put(key, ty);
                if (((CompositeType) object).getShortFormPart() == null) {
                    abstractTypesSet.add(key);
                }
            }
        }
    }

    /**
     * Loads the simple (enumeration) and complex types from a parsed XSD type
     * list.
     *
     * @param area The area the types belong to.
     * @param service The service the types belong to, may be null.
     * @param typeList The list of parsed XSD type objects.
     */
    public void loadTypesFromXsdList(String area, String service, List<OpenAttrs> typeList) {
        for (OpenAttrs object : typeList) {
            if (object instanceof SimpleType) {
                SimpleType ty = (SimpleType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                if (null != ty.getRestriction()) {
                    for (Object o : ty.getRestriction().getFacetOrAny()) {
                        if ("enumeration".equalsIgnoreCase(((JAXBElement) o).getName().getLocalPart())) {
                            EnumerationType e = new EnumerationType();
                            EnumerationType.Item i = new EnumerationType.Item();
                            i.setValue(((NoFixedFacet) ((JAXBElement) o).getValue()).getValue());
                            e.getItem().add(i);
                            allTypesMap.put(key, e);
                            enumTypesSet.add(key);
                            break;
                        }
                    }
                } else {
                    // ignore unexpected type, maybe warn in future?
                }
            } else if (object instanceof ComplexType) {
                ComplexType ty = (ComplexType) object;
                TypeKey key = new TypeKey(TypeUtils.createTypeReference(area, service, ty.getName(), false));
                allTypesMap.put(key, object);
                compositeTypesMap.put(key, new CompositeType());
                if (ty.isAbstract()) {
                    abstractTypesSet.add(key);
                }
            }
        }
    }

    /**
     * Returns true if the type is abstract.
     *
     * @param type the type to look for.
     * @return true if abstract.
     */
    public boolean isAbstract(TypeReference type) {
        return abstractTypesSet.contains(new TypeKey(type));
    }

    /**
     * Returns true if the type is an enumeration.
     *
     * @param type the type to look for.
     * @return true if an enumeration.
     */
    public boolean isEnum(TypeReference type) {
        return enumTypesSet.contains(new TypeKey(type));
    }

    /**
     * Returns enumeration details if enumeration type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public EnumerationType getEnum(TypeReference type) {
        if (isEnum(type)) {
            return (EnumerationType) allTypesMap.get(new TypeKey(type));
        }

        return null;
    }

    /**
     * Returns true if the type is an attribute type.
     *
     * @param type the type to look for.
     * @return true if an attribute type.
     */
    public boolean isAttributeType(TypeReference type) {
        if (type == null) {
            return false;
        }

        return attributeTypesMap.containsKey(new TypeKey(type));
    }

    /**
     * Returns attribute details if attribute type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public AttributeTypeDetails getAttributeDetails(TypeReference type) {
        if (type == null) {
            return null;
        }

        return attributeTypesMap.get(new TypeKey(type));
    }

    /**
     * Returns attribute details if attribute type.
     *
     * @param area the type area.
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public AttributeTypeDetails getAttributeDetails(String area, String type) {
        if (type != null) {
            return attributeTypesMap.get(new TypeKey(area, null, type));
        }
        return null;
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
        if (null != type) {
            return attributeTypesMap.get(new TypeKey(area, service, type));
        }
        return null;
    }

    /**
     * Returns true if the type is a native type.
     *
     * @param type the type to look for.
     * @return true if native.
     */
    public boolean isNativeType(String type) {
        if (type.contains("<")) {
            type = type.substring(0, type.indexOf('<'));
        }
        return nativeTypesMap.containsKey(type);
    }

    /**
     * Returns true if the type is an attribute native type.
     *
     * @param type the type to look for.
     * @return true if an attribute native type.
     */
    public boolean isAttributeNativeType(TypeReference type) {
        return isAttributeType(type) && getAttributeDetails(type).isNativeType();
    }

    /**
     * Returns native details if native type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public NativeTypeDetails getNativeType(String type) {
        if (type.contains("<")) {
            type = type.substring(0, type.indexOf('<'));
        }
        NativeTypeDetails rType = nativeTypesMap.get(type);
        if (rType == null) {
            rType = new NativeTypeDetails("<Unknown native type of " + type + ">", false, false, null);
        }
        return rType;
    }

    /**
     * Returns true if the type definition has been loaded.
     *
     * @param type the type to look for.
     * @return true if a known type.
     */
    public boolean isKnownType(TypeReference type) {
        return allTypesMap.containsKey(new TypeKey(type));
    }

    /**
     * Returns true if the type is a composite.
     *
     * @param type the type to look for.
     * @return true if a composite.
     */
    public boolean isComposite(TypeReference type) {
        boolean compType = false;
        if (compositeTypesMap.containsKey(new TypeKey(type))) {
            compType = true;
        }
        return compType;
    }

    /**
     * Returns composite details if composite type.
     *
     * @param type The type to look for.
     * @return the details if found, otherwise null.
     */
    public CompositeType getCompositeDetails(TypeReference type) {
        if (type == null) {
            return null;
        }

        return compositeTypesMap.get(new TypeKey(type));
    }

    /**
     * Returns error details if defined.
     *
     * @param error The error to look for.
     * @return the details if found, otherwise null.
     */
    public ErrorDefinitionType getErrorDefinition(String error) {
        if (errorDefinitionMap.containsKey(error)) {
            return errorDefinitionMap.get(error);
        }
        return null;
    }
}
