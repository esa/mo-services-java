/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.helpertools.helpers;

import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * A Helper for handling domains.
 */
public class HelperDomain {

    /**
     * Generates the domain field in an IdentifierList from a String separated
     * by dots
     *
     * @param domainId The domain Id
     * @return The domain as IdentifierList or an empty IdentifierList if
     * domainId == null OR domainId.isEmpty
     */
    public static IdentifierList domainId2domain(String domainId) {
        if (domainId == null || domainId.isEmpty()) {
            return new IdentifierList();
        }

        IdentifierList output = new IdentifierList();
        String[] parts = domainId.split("\\.");
        for (String part : parts) {
            output.add(new Identifier(part));
        }

        return output;
    }

    /**
     * Generates the domain string from an IdentifierList
     *
     * @param domain The domain
     * @return The domain Id
     */
    public static String domain2domainId(final IdentifierList domain) {
        if (domain == null) {
            return null;
        }
        if (domain.isEmpty()) {
            return "";
        }
        String domainId = "";
        for (Identifier subdomain : domain) {
            domainId += subdomain.getValue() + ".";
        }

        // Remove the last dot and return the string
        return domainId.substring(0, domainId.length() - 1);
    }

    /**
     * Checks if the domain contains a wildcard in any of the parts
     *
     * @param receivedDomain Domain
     * @return Name of the COM object
     */
    public static Boolean domainContainsWildcard(final IdentifierList receivedDomain) {
        // Do we have the '*' on any part of the domain?
        if (receivedDomain == null) {
            return true;
        }

        for (Identifier part : receivedDomain) {
            if ("*".equals(part.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the provided domain matches the supplied domain with a wildcard
     *
     * @param domain Domain
     * @param wilcardDomain Domain with wildcard
     * @return True if the domain matches the domain with the wildcard
     */
    public static boolean domainMatchesWildcardDomain(IdentifierList domain, IdentifierList wilcardDomain) {
        // The domain of the wildcard can never be greater than the real domain
        if (wilcardDomain.size() > domain.size() + 1) {
            return false;
        }

        // cycle through the parts of the domains
        for (int i = 0; i < wilcardDomain.size(); i++) {
            Identifier domainPart1 = wilcardDomain.get(i);

            if ("*".equals(domainPart1.toString())) {
                return true;  // Wildcard found!
            }

            Identifier domainPart2 = domain.get(i);

            // The parts are different, return false
            if (!domainPart1.toString().equals(domainPart2.toString())) {
                return false;
            }
        }

        return true;
    }

}
