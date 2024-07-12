/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
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
package org.ccsds.moims.mo.mal.structures;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL URI type.
 */
public class URI implements Attribute {

    private static final long serialVersionUID = Attribute.URI_SHORT_FORM;

    /**
     * Map of cachedRootURIs. This associates a full URI to its root URI.
     */
    private final static ConcurrentHashMap<String, String> cachedRootURIs
            = new ConcurrentHashMap<>();

    private String value;

    /**
     * Default constructor.
     */
    public URI() {
        this.value = "";
    }

    /**
     * Initialiser constructor.
     *
     * @param value Value to initialise with.
     */
    public URI(final String value) {
        if (value == null) {
            Logger.getLogger(URI.class.getName()).log(
                    Level.WARNING,
                    "The URI has been initialized with an invalid null value. "
                    + "Problems might occur while encoding the element.",
                    new MALException());
            this.value = "";
        } else {
            this.value = value;
        }
    }

    @Override
    public Element createElement() {
        return new URI();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the "root" URI from the full URI.The root URI only contains the
     * protocol and the main destination and is something unique for all URIs of
     * the same MAL.Example full URI: maltcp://10.0.0.1:61616-serviceXYZ
     *
     * @param delimiter the delimiter character.
     * @param count The number of occurrences to skip.
     * @return the root URI, for example maltcp://10.0.0.1:61616
     */
    public String getRootURI(char delimiter, int count) {
        String rootURI = cachedRootURIs.get(value);

        if (rootURI == null) {
            int serviceDelimPosition = nthIndexOf(value, delimiter, count);

            if (serviceDelimPosition < 0) {
                // does not exist, return as is
                return value;
            }

            rootURI = value.substring(0, serviceDelimPosition);
            cachedRootURIs.put(value, rootURI);
        }

        return rootURI;
    }

    /**
     * Returns the nth index of a character in a String
     *
     * @param uri The uri.
     * @param delimiter the delimiter character.
     * @param count The number of occurrences to skip.
     * @return the routing part of the URI
     */
    public static int nthIndexOf(String uri, char delimiter, int count) {
        int index = -1;

        while (count >= 0) {
            index = uri.indexOf(delimiter, index + 1);

            if (index == -1) {
                return index;
            }

            --count;
        }

        return index;
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.URI_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeURI(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeURI();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof URI) {
            return value.equals(((URI) obj).value);
        }

        return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
