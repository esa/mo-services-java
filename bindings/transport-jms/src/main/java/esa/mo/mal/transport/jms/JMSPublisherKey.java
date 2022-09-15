/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO JMS Transport Framework
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
package esa.mo.mal.transport.jms;

import java.util.HashMap;
import java.util.Map;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperAttributes;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Simple class that represents a MAL update key.
 */
public final class JMSPublisherKey implements Comparable {

    /**
     * Match all constant.
     */
    private static final String ALL_ID = "*";
    private static final Long ALL_NUMBER = 0L;
    private static final int HASH_MAGIC_NUMBER = 47;
    /**
     * The set of subkeys.
     */
    private final HashMap<String, Attribute> subkeys = new HashMap<>();

    /**
     * Constructor.
     *
     * @param key Entity key.
     */
    public JMSPublisherKey(final NamedValueList keys) {
        super();

        for (NamedValue subkey : keys) {
            subkeys.put(subkey.getName().getValue(), subkey.getValue());
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JMSPublisherKey other = (JMSPublisherKey) obj;
        if (this.subkeys.size() != other.subkeys.size()) {
            return false;
        }
        for (Map.Entry<String, Attribute> entry : subkeys.entrySet()) {
            String key = entry.getKey();
            Object otherValue = other.subkeys.get(key);
            if (otherValue == null || !this.subkeys.get(key).equals(otherValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (Map.Entry<String, Attribute> entry : subkeys.entrySet()) {
            Attribute value = entry.getValue();
            hash = HASH_MAGIC_NUMBER * hash + (value != null ? value.hashCode() : 0);
        }
        return hash;
    }

    @Override
    public int compareTo(final Object o) {
        final JMSPublisherKey rhs = (JMSPublisherKey) o;

        int rv = 0;

        for (Map.Entry<String, Attribute> entry : subkeys.entrySet()) {
            String key = entry.getKey();
            Attribute value = entry.getValue();

            if (value instanceof Union) {
                rv = compareSubkey(
                        ((Union) this.subkeys.get(key)).getLongValue(),
                        ((Union) rhs.subkeys.get(key)).getLongValue()
                );

                if (rv != 0) {
                    return rv;
                }
            }
        }
        return rv;
    }

    /**
     * Returns true if this key matches supplied argument taking into account
     * wildcards.
     *
     * @param rhs Key to match against.
     * @return True if matches.
     */
    public boolean matches(final NamedValueList keys) {
        if (null != keys) {
            for(NamedValue nv : keys){
                boolean matched = matchedSubkeyWithWildcard(nv.getValue(), 
                        this.subkeys.get(nv.getName().getValue()));
                
                if(!matched){
                    return false;
                }
            }
            
            return true;
        }

        return false;
    }

    private static int compareSubkey(final String myKeyPart, final String theirKeyPart) {
        if ((null == myKeyPart) || (null == theirKeyPart)) {
            if ((null != myKeyPart) || (null != theirKeyPart)) {
                if (null == myKeyPart) {
                    return -1;
                }

                return 1;
            }
        } else {
            if (!myKeyPart.equals(theirKeyPart)) {
                return myKeyPart.compareTo(theirKeyPart);
            }
        }

        return 0;
    }

    private static int compareSubkey(final Long myKeyPart, final Long theirKeyPart) {
        if ((null == myKeyPart) || (null == theirKeyPart)) {
            if ((null != myKeyPart) || (null != theirKeyPart)) {
                if (null == myKeyPart) {
                    return -1;
                }

                return 1;
            }
        } else {
            if (!myKeyPart.equals(theirKeyPart)) {
                return myKeyPart.compareTo(theirKeyPart);
            }
        }

        return 0;
    }

    /**
     * Compares two String sub-keys taking into account wildcard values.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return True if they match or one is the wildcard.
     */
    protected static boolean matchedSubkeyWithWildcard(final Attribute myKeyPart, final Attribute theirKeyPart) {
        // Are we handling strings?
        if(HelperMisc.isStringAttribute(myKeyPart) && HelperMisc.isStringAttribute(theirKeyPart)){
            String first = HelperAttributes.attribute2string(myKeyPart);
            String second = HelperAttributes.attribute2string(theirKeyPart);
            
            if (ALL_ID.equals(first) || ALL_ID.equals(second)) {
                return true;
            }
        }
        
        // Are we not handling strings?
        if(!HelperMisc.isStringAttribute(myKeyPart) && !HelperMisc.isStringAttribute(theirKeyPart)){
            Object first = HelperAttributes.attribute2JavaType(myKeyPart);
            Object second = HelperAttributes.attribute2JavaType(theirKeyPart);
            
            if(first instanceof Long){
                if (ALL_NUMBER.equals((Long) first) || ALL_NUMBER.equals((Long) second)) {
                    return true;
                }
            }
        }
        
        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    @Deprecated
    private static boolean matchedSubkey(final String myKeyPart, final String theirKeyPart) {
        if (ALL_ID.equals(myKeyPart) || ALL_ID.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    @Deprecated
    private static boolean matchedSubkey(final Long myKeyPart, final Long theirKeyPart) {
        if (ALL_NUMBER.equals(myKeyPart) || ALL_NUMBER.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    private static String getIdValue(final Identifier id) {
        if ((null != id) && (null != id.getValue())) {
            return id.getValue();
        }

        return null;
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        for (Map.Entry<String, Attribute> entry : subkeys.entrySet()) {
            buf.append(entry.getKey());
            buf.append('=');
            buf.append(entry.getValue());
            buf.append(';');
        }
        buf.append(']');
        return buf.toString();
    }
}
