/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.mo.mal.impl.broker;

import esa.mo.mal.impl.broker.key.SubscriptionConsumer;
import esa.mo.mal.impl.broker.key.UpdateKeyValues;
import java.util.ArrayList;
import java.util.logging.Level;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperAttributes;
import org.ccsds.moims.mo.mal.helpertools.helpers.HelperMisc;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * The BrokerMatcher includes the methods to match keys
 */
public class BrokerMatcher {

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
     * Compare provider's UpdateKeyValues with consumer's subscription filters
     * 
     * @param values which is provided by provider
     * @param searchSet list of subscriptions from customers
     * @return boolean match found or not
     */
    public static boolean keyValuesMatchSubs(final UpdateKeyValues values, final ArrayList<SubscriptionConsumer> searchSet) {
        if(values == null || searchSet == null) return false;
        
        boolean matched = false;
        for (SubscriptionConsumer subscriptionKey : searchSet) {
            MALBrokerImpl.LOGGER.log(Level.FINE, "Checking: {0}\nAgainst: {1}",
                    new Object[]{values, subscriptionKey});
            
            if (subscriptionKey.matchesWithFilters(values)) {
                MALBrokerImpl.LOGGER.fine("    : Matched");
                matched = true;
                break;
            }
            MALBrokerImpl.LOGGER.fine("    : No match");
        }
        return matched;
    }

    /**
     * Compares a String based sub-key.
     *
     * @param myKeyPart The first key part.
     * @param theirKeyPart The second key part.
     * @return -1, 0, or 1 based on how the two values compare using normal
     * comparable rules.
     */
    public static int compareSubkey(final String myKeyPart, final String theirKeyPart) {
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
    public static int compareSubkey(final Long myKeyPart, final Long theirKeyPart) {
        if ((myKeyPart == null) || (theirKeyPart == null)) {
            if ((myKeyPart != null) || (theirKeyPart != null)) {
                return (myKeyPart == null) ? -1 : 1;
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
    public static boolean matchedSubkeyWithWildcard(final Attribute myKeyPart, final Attribute theirKeyPart) {
        if ((myKeyPart == null) || (theirKeyPart == null)) {
            return true;
        }

        // Are we handling strings?
        if (HelperMisc.isStringAttribute(myKeyPart) && HelperMisc.isStringAttribute(theirKeyPart)) {
            String first = HelperAttributes.attribute2string(myKeyPart);
            String second = HelperAttributes.attribute2string(theirKeyPart);

            if (ALL_ID.equals(first) || ALL_ID.equals(second)) {
                return true;
            }
        }

        // Are we not handling strings?
        if (!HelperMisc.isStringAttribute(myKeyPart) && !HelperMisc.isStringAttribute(theirKeyPart)) {
            if ((myKeyPart instanceof Union) || (theirKeyPart instanceof Union)) {
                if (((Union) myKeyPart).isNull() || ((Union) theirKeyPart).isNull()) {
                    return true;
                }
            }

            Object first = HelperAttributes.attribute2JavaType(myKeyPart);
            Object second = HelperAttributes.attribute2JavaType(theirKeyPart);

            if (first instanceof Long) {
                if (ALL_NUMBER.equals((Long) first) || ALL_NUMBER.equals((Long) second)) {
                    return true;
                }
            }
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
    public static boolean matchedSubkeyWithWildcard(final String myKeyPart, final String theirKeyPart) {
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
    public static boolean matchedSubkeyWithWildcard(final Long myKeyPart, final Long theirKeyPart) {
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
    public static boolean matchedSubkeyWithWildcard(final UShort myKeyPart, final UShort theirKeyPart) {
        if (ALL_SHORT.equals(myKeyPart) || ALL_SHORT.equals(theirKeyPart)) {
            return true;
        }

        if ((null == myKeyPart) || (null == theirKeyPart)) {
            return (null == myKeyPart) && (null == theirKeyPart);
        }

        return myKeyPart.equals(theirKeyPart);
    }

    /**
     * Checks if the provided domain matches the subscribed domain
     *
     * @param consumerDomain list of domain subscribed by consumers, it can contain wildcard
     * @param providerDomain list of domain provided by providers 
     * @return True if the domain matches the domain with the wildcard
     */
    public static boolean domainMatchesWildcardDomain(IdentifierList consumerDomain, IdentifierList providerDomain) {
        
        if(consumerDomain == null || providerDomain == null){
            return consumerDomain == null && providerDomain == null;
        }
        
        String firstDomain = consumerDomain.get(0).getValue();
        String lastDomain = consumerDomain.get(consumerDomain.size()-1).getValue();
        if("*".equals(firstDomain) && "*".equals(lastDomain)){  // *.B.*
            if(consumerDomain.size() > providerDomain.size() + 2){
                return false;
            } 
            
            for(int i = 0; i <= providerDomain.size() - consumerDomain.size() + 2; i++){
                boolean matched = true;
                for(int j = 0; j < consumerDomain.size() - 2; j++){
                    if(!providerDomain.get(i + j).getValue().equals(consumerDomain.get(j + 1).getValue())){
                        matched = false;
                        break;
                    }
                }
                if(matched){
                    return true;
                }
            }
            return false;
        }
        else if(!"*".equals(firstDomain) && !"*".equals(lastDomain)){ // A.B.C
            if(consumerDomain.size() != providerDomain.size()){
                return false;
            }
            
            for(int i = 0; i < consumerDomain.size(); i++){
                if(!consumerDomain.get(i).getValue().equals(providerDomain.get(i).getValue())){
                    return false;
                }
            }
        }
        else {
            if(consumerDomain.size() > providerDomain.size() + 1){
                return false;
            }  
            
            if("*".equals(lastDomain)){ // A.B.*
                for(int i = 0; i < consumerDomain.size() - 1 ; i++){
                    if(!consumerDomain.get(i).getValue().equals(providerDomain.get(i).getValue())){
                        return false;
                    }
                }
            }
            else if("*".equals(firstDomain)){ //*.B.C
                int i = consumerDomain.size() - 1;
                int j = providerDomain.size() - 1;
                for(; i >= 1; i--, j--){
                    if(!consumerDomain.get(i).getValue().equals(providerDomain.get(j).getValue())){
                        return false;
                    }
                }
            }
            
        }


        return true;
    }
}
