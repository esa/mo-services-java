/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MPD services
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
package esa.mo.services.mpd.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperAttributes;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperDomain;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mpd.structures.AttributeFilterList;
import org.ccsds.moims.mo.mpd.structures.Product;
import org.ccsds.moims.mo.mpd.structures.ProductFilter;
import org.ccsds.moims.mo.mpd.structures.ProductMetadata;
import org.ccsds.moims.mo.mpd.structures.StringPattern;
import org.ccsds.moims.mo.mpd.structures.ValueRange;
import org.ccsds.moims.mo.mpd.structures.ValueSet;

/**
 * A utilities helper class to aid with filtering products.
 */
public class HelperProductFilters {

    /**
     * Checks if the metadata matches the provided filter.
     *
     * @param productMetadata The product metadata.
     * @param productFilter The product Filter.
     * @return true if the metadata matches the filter, false otherwise.
     * @throws java.io.IOException if the type in one of the attributes from the
     * filter does not match the type of the Product.
     */
    public static boolean productMetadataMatchesFilter(ProductMetadata productMetadata,
            ProductFilter productFilter) throws IOException {
        Identifier productType = productFilter.getProductType();
        IdentifierList domain = productFilter.getDomain();
        IdentifierList sources = productFilter.getSources();
        AttributeFilterList attributeFilters = productFilter.getAttributeFilter();

        if (productType != null) {
            if (!productType.getValue().equals(productMetadata.getProductType().getName().getValue())) {
                return false;
            }
        }

        if (domain != null) {
            ObjectRef<Product> productRef = productMetadata.getProductRef();
            IdentifierList productDomain = productRef.getDomain();
            boolean matchesDomain = HelperDomain.domainMatchesWildcardDomain(productDomain, domain);

            if (!matchesDomain) {
                return false;
            }
        }

        if (sources != null) {
            boolean matchesSource = false;

            for (Identifier source : sources) {
                Identifier metadataSource = productMetadata.getSource();

                if (source == null && metadataSource == null) {
                    matchesSource = true;
                    break;
                }

                if (source != null && metadataSource == null) {
                    break;
                }

                if (source != null && source.getValue().equals(metadataSource.getValue())) {
                    matchesSource = true;
                    break;
                }
            }

            if (!matchesSource) {
                return false;
            }
        }

        if (attributeFilters != null) {
            for (Object attributeFilter : attributeFilters) {
                NamedValueList attributes = productMetadata.getAttributes();
                if (attributeFilter instanceof ValueSet) {
                    ValueSet valueSet = (ValueSet) attributeFilter;
                    if (!matchValueSet(valueSet, attributes)) {
                        return false;
                    }
                }
                if (attributeFilter instanceof ValueRange) {
                    ValueRange valueRange = (ValueRange) attributeFilter;
                    if (!matchValueRange(valueRange, attributes)) {
                        return false;
                    }
                }
                if (attributeFilter instanceof StringPattern) {
                    StringPattern stringPattern = (StringPattern) attributeFilter;
                    if (!matchStringPattern(stringPattern, attributes)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean matchValueSet(ValueSet valueSet, NamedValueList attsFromProduct) throws IOException {
        Identifier nameToMatch = valueSet.getName();
        Boolean include = valueSet.getInclude();
        AttributeList attributesToMatch = valueSet.getValues();

        // If there's no value to match that, the filter is successful
        if (attributesToMatch.isEmpty()) {
            return true;
        }

        // For the code below, it is assumed that there is at least one value in the filter!
        // If there are no atts in the Product:
        if (attsFromProduct == null || attsFromProduct.isEmpty()) {
            return !include;
        }

        boolean matchedAtLeastOne = false;

        for (int i = 0; i < attributesToMatch.size(); i++) {
            Attribute valueToMatch = (Attribute) attributesToMatch.get(i);

            // Iterate through all the Attributes from the Product
            // Then check: Name, Type (Exception if wrong), Value
            for (NamedValue keyValue : attsFromProduct) {
                if (!nameToMatch.equals(keyValue.getName())) {
                    continue; // Jump over if the Attribute name is different
                }

                Attribute value = keyValue.getValue();
                // Type validation:
                if (!valueToMatch.getTypeId().equals(value.getTypeId())) {
                    String errorMsg = "The Attribute types do not match for name: " + nameToMatch;
                    errorMsg += "\n Filter type: " + valueToMatch.getClass().getSimpleName()
                            + " - " + valueToMatch.getTypeId().toString();
                    errorMsg += "\n Product type: " + value.getClass().getSimpleName()
                            + " - " + value.getTypeId().toString();
                    throw new IOException(errorMsg);
                }

                if (valueToMatch.equals(value)) {
                    matchedAtLeastOne = true;
                }
            }
        }

        return (include) ? matchedAtLeastOne : !matchedAtLeastOne;
    }

    private static boolean matchValueRange(ValueRange valueRange, NamedValueList attsFromProduct) throws IOException {
        Identifier nameToMatch = valueRange.getName();
        Boolean include = valueRange.getInclude();

        boolean matchedAtLeastOne = false;
        // Iterate through all the Attributes from the Product
        // Then check: Name, Value
        for (NamedValue keyValue : attsFromProduct) {
            if (!nameToMatch.equals(keyValue.getName())) {
                continue; // Jump over if the Attribute name is different
            }

            Attribute value = keyValue.getValue();
            Attribute max = valueRange.getMaximum();
            Attribute min = valueRange.getMinimum();

            if (value == null) {
                continue; // No match if the product just have a null
            }

            if (Attribute.isStringAttribute(value)
                    || Attribute.isStringAttribute(max) || Attribute.isStringAttribute(min)) {
                throw new IOException("The comparison cannot be performed on String types!");
            }

            if (value instanceof Union) {
                Double doubleValue = ((Union) value).getDoubleValue();

                if (doubleValue >= ((Union) min).getDoubleValue()
                        && doubleValue <= ((Union) max).getDoubleValue()) {
                    matchedAtLeastOne = true;
                    continue;
                }
            }

            try {
                Double doubleMin = HelperAttributes.attribute2double(min);
                Double doubleMax = HelperAttributes.attribute2double(max);
                Double doubleValue = HelperAttributes.attribute2double(value);

                if (doubleValue >= doubleMin && doubleValue <= doubleMax) {
                    matchedAtLeastOne = true;
                    continue;
                }
            } catch (Exception ex) {
                // Type validation:
                String errorMsg = "The current types matching are unsupported for Attribute name: " + nameToMatch;
                errorMsg += "\n Filter type max: " + max.getClass().getSimpleName()
                        + " - " + max.getTypeId().toString();
                errorMsg += "\n Filter type min: " + min.getClass().getSimpleName()
                        + " - " + min.getTypeId().toString();
                errorMsg += "\n Product type: " + value.getClass().getSimpleName()
                        + " - " + value.getTypeId().toString();
                throw new IOException(errorMsg);
            }
        }

        return (include) ? matchedAtLeastOne : !matchedAtLeastOne;
    }

    private static boolean matchStringPattern(StringPattern stringPattern, NamedValueList attsFromProduct) throws IOException {
        Identifier nameToMatch = stringPattern.getName();
        Boolean include = stringPattern.getInclude();

        boolean matchedAtLeastOne = false;
        // Iterate through all the Attributes from the Product
        // Then check: Name, Value
        for (NamedValue keyValue : attsFromProduct) {
            if (!nameToMatch.equals(keyValue.getName())) {
                continue; // Jump over if the Attribute name is different
            }

            Attribute value = keyValue.getValue();

            if (value instanceof Union || value instanceof Identifier || value instanceof URI) {
                String strValue = null;

                if (value instanceof Union) {
                    strValue = ((Union) value).getStringValue();
                }

                if (value instanceof Identifier) {
                    strValue = ((Identifier) value).getValue();
                }

                if (value instanceof URI) {
                    strValue = ((URI) value).getValue();
                }

                Pattern pattern = Pattern.compile(stringPattern.getRegex());
                Matcher matcher = pattern.matcher(strValue);

                if (matcher.matches()) {
                    matchedAtLeastOne = true;
                }
            } else {
                // Type validation:
                String errorMsg = "The Attribute types do not match for name: " + nameToMatch;
                errorMsg += "\n Filter type: " + value.getClass().getSimpleName()
                        + " - " + value.getTypeId().toString();
                errorMsg += "\n Product type: " + value.getClass().getSimpleName()
                        + " - " + value.getTypeId().toString();
                throw new IOException(errorMsg);
            }
        }

        return (include) ? matchedAtLeastOne : !matchedAtLeastOne;
    }

}
