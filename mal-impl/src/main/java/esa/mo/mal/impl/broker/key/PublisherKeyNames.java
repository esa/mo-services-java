/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.broker.key;

import java.util.ArrayList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;

/**
 * Simple class that identifies a publisher in a MAL broker.
 */
//public class PublisherKeyNames implements Comparable {
public class PublisherKeyNames {

    /**
     * Hash function magic number.
     */
    protected static final int HASH_MAGIC_NUMBER = 47;
    /**
     * The set of keys.
     */
    private final ArrayList<String> keyNames = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param key Entity key.
     */
    public PublisherKeyNames(final ArrayList<String> keyNames) {
        this.keyNames.addAll(keyNames);
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append("[keys=");
        for (String name : keyNames) {
            buf.append(name);
            buf.append(';');
        }
        buf.append(']');
        return buf.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PublisherKeyNames other = (PublisherKeyNames) obj;
        if (this.keyNames.size() != other.keyNames.size()) {
            return false;
        }
        for (String name : keyNames) {
            if (other.keyNames.contains(name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (String name : keyNames) {
            hash = HASH_MAGIC_NUMBER * hash + (name != null ? name.hashCode() : 0);
        }
        return hash;
    }

    /*
    @Override
    public int compareTo(final Object o) {
        final PublisherKeyNames rhs = (PublisherKeyNames) o;
        int rv = 0;
        
        new Exception().printStackTrace(); // Code should never be here!!

        for (Entry<String, AttributeList> entry : this.keys.entrySet()) {
            String key = entry.getKey();
            AttributeList value = entry.getValue();

            if (value instanceof Union) {
                Union subThis = (Union) value;
                Object valueRhs = rhs.keys.get(key);
                if(!(valueRhs instanceof Union)){
                    return -1;
                }
                   
                Union subRhs = (Union) rhs.keys.get(key);
                
                if (subThis.isNull() && subRhs.isNull()) {
                    return 0;
                }

                if (subThis.isNull() && !subRhs.isNull()) {
                    return 1;
                }
                
                if (!subThis.isNull() && subRhs.isNull()) {
                    return -1;
                }
                
                rv = compareSubkey(subThis.getLongValue(), subRhs.getLongValue());

                if (rv != 0) {
                    return rv;
                }
            }
        }

        return rv;
    }
    */

    /**
     * Returns true if this key matches supplied argument taking into account
     * wildcards.
     *
     * @param rhs Key to match against.
     * @return True if matches.
     */
    public boolean matchesWithWildcard(final NamedValueList rhs) {
        if (null != rhs) {
            for (NamedValue nv : rhs) {
                new Exception().printStackTrace(); // Code should never be here!!
                
                /*
                boolean matched = matchedSubkeyWithWildcard(nv.getValue(),
                        this.keys.get(nv.getName().getValue()));

                if (!matched) {
                    return false;
                }
                */
            }

            return true;
        }

        return false;
    }

    /**
     * Returns true if this key matches supplied argument taking into account
     * wildcards.
     *
     * @param rhs Key to match against.
     * @return True if matches.
     */
    public boolean matchesWithWildcard(final PublisherKeyNames rhs) {
        if (null != rhs) {
            new Exception().printStackTrace(); // Code should never be here!!
            /*
            for (Entry<String, Attribute> nv : rhs.keys.entrySet()) {
                boolean matched = matchedSubkeyWithWildcard(nv.getValue(),
                        this.keys.get(nv.getKey()));

                if (!matched) {
                    return false;
                }
            }
            */

            return true;
        }

        return false;
    }

    /**
     * Helper method to return the string value from an Identifier.
     *
     * @param id The identifier.
     * @return The value or null.
     */
    protected static String getIdValue(final Identifier id) {
        if ((null != id) && (null != id.getValue())) {
            return id.getValue();
        }
        return null;
    }

}
