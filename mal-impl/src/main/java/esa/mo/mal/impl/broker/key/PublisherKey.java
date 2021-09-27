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

import static esa.mo.mal.impl.broker.key.PublisherKey.HASH_MAGIC_NUMBER;
import java.util.HashMap;
import java.util.Map.Entry;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperAttributes;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.EntityKey;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * Simple class that identifies a publisher in a MAL broker.
 */
public class PublisherKey implements Comparable {

    /**
     * Match all string constant.
     */
    public static final String ALL_ID = "*";
    /**
     * Match all numeric constant.
     */
    public static final Long ALL_NUMBER = 0L;
    /**
     * Match all ushort constant.
     */
    public static final UShort ALL_SHORT = new UShort(0);
    /**
     * Hash function magic number.
     */
    protected static final int HASH_MAGIC_NUMBER = 47;
    /**
     * The set of subkeys.
     */
    private final HashMap<String, Attribute> subkeys = new HashMap<>();

    /**
     * Constructor.
     *
     * @param key Entity key.
     */
    public PublisherKey(final EntityKey key) {
        for (NamedValue subkey : key.getSubkeys()) {
            subkeys.put(subkey.getName().getValue(), subkey.getValue());
        }
    }

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder();
        buf.append('[');
        for (Entry<String, Attribute> entry : subkeys.entrySet()) {
            buf.append(entry.getKey());
            buf.append('=');
            buf.append(entry.getValue());
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
        final PublisherKey other = (PublisherKey) obj;
        if (this.subkeys.size() != other.subkeys.size()) {
            return false;
        }
        for (Entry<String, Attribute> entry : subkeys.entrySet()) {
            String key = entry.getKey();
            if (!this.subkeys.get(key).equals(other.subkeys.get(key))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        for (Entry<String, Attribute> entry : subkeys.entrySet()) {
            Attribute value = entry.getValue();
            hash = HASH_MAGIC_NUMBER * hash + (value != null ? value.hashCode() : 0);
        }
        return hash;
    }

    @Override
    public int compareTo(final Object o) {
        final PublisherKey rhs = (PublisherKey) o;

        int rv = 0;

        for (Entry<String, Attribute> entry : subkeys.entrySet()) {
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
    public boolean matchesWithWildcard(final EntityKey rhs) {
        if (null != rhs) {
            for(NamedValue nv : rhs.getSubkeys()){
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

    /**
     * Returns true if this key matches supplied argument taking into account
     * wildcards.
     *
     * @param rhs Key to match against.
     * @return True if matches.
     */
    public boolean matchesWithWildcard(final PublisherKey rhs) {
        if (null != rhs) {
            for(Entry<String, Attribute> nv : rhs.subkeys.entrySet()){
                boolean matched = matchedSubkeyWithWildcard(nv.getValue(), 
                        this.subkeys.get(nv.getKey()));
                
                if(!matched){
                    return false;
                }
            }
            
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

    /**
     * Compares a String based sub-key.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return -1, 0, or 1 based on how the two values compare using normal
     * comparable rules.
     */
    protected static int compareSubkey(final String myKeyPart, final String theirKeyPart) {
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
     * Compares an Long based sub-key.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return -1, 0, or 1 based on how the two values compare using normal
     * comparable rules.
     */
    protected static int compareSubkey(final Long myKeyPart, final Long theirKeyPart) {
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

    /**
     * Compares two String sub-keys taking into account wildcard values. This
     * method is deprecated because it was being used before we changed to MO
     * v2.0 as the subscription to PUBSUB has now changed.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return True if they match or one is the wildcard.
     */
    @Deprecated
    protected static boolean matchedSubkeyWithWildcard(final String myKeyPart, final String theirKeyPart) {
        if (ALL_ID.equals(myKeyPart) || ALL_ID.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    /**
     * Compares two Long sub-keys taking into account wildcard values. This
     * method is deprecated because it was being used before we changed to MO
     * v2.0 as the subscription to PUBSUB has now changed.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return True if they match or one is the wildcard.
     */
    @Deprecated
    protected static boolean matchedSubkeyWithWildcard(final Long myKeyPart, final Long theirKeyPart) {
        if (ALL_NUMBER.equals(myKeyPart) || ALL_NUMBER.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    /**
     * Compares two UShort sub-keys taking into account wildcard values.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return True if they match or one is the wildcard.
     */
    protected static boolean matchedSubkeyWithWildcard(final UShort myKeyPart, final UShort theirKeyPart) {
        if (ALL_SHORT.equals(myKeyPart) || ALL_SHORT.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }
}
