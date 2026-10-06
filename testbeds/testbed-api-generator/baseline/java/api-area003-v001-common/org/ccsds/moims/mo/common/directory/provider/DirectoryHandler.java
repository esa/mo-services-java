package org.ccsds.moims.mo.common.directory.provider;

import org.ccsds.moims.mo.com.InvalidException;
import org.ccsds.moims.mo.common.directory.body.PublishProviderResponse;
import org.ccsds.moims.mo.common.directory.structures.ProviderSummaryList;
import org.ccsds.moims.mo.common.directory.structures.PublishDetails;
import org.ccsds.moims.mo.common.directory.structures.ServiceFilter;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.structures.FileList;

/**
 * Interface that providers of the Directory service must implement to handle
 * the operations of that service.
 */
public interface DirectoryHandler {

    /**
     * Implements the operation lookupProvider.
     * 
     * @param filter The filter field shall define the lookup query and be used to match details previously published using the publishProvider operation, the specifics of the ServiceFilter fields are defined in the following requirements.
If the serviceProviderId field is NULL then all service provider identifiers shall be matched.
If the final identifier of the domain field of the filter is the wildcard '*', then all sub-domains shall be searched for matches. See R[2] section 3.5.6.5.g.
If the wildcard is used in any other part of the domain other than the final one then an INVALID error shall be returned.
If the domain field is NULL then all domains shall be matched.
If the network field is NULL then all networks shall be matched.
If the sessionType field is NULL then all session types shall be matched.
If the sessionName field is NULL then all session names shall be matched.
The serviceKey field shall be used to match against ServiceKey fields held in the PublishDetails used to publish a specific provider.
If the serviceKey field is NULL then all areas, services and versions shall be matched.
If the area field is the wildcard '0' then all areas names shall be matched.
If the service field is the wildcard '0' then all services shall be matched.
If the version field is the wildcard '0' then all area versions shall be matched.
If the requiredCapabilitySets field is NULL or an empty list then all service capability sets shall be matched.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException Invalid domain filter value.
     * @throws MALException if there is an implementation exception
     */
    ProviderSummaryList lookupProvider(ServiceFilter filter,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation publishProvider.
     * 
     * @param newProviderDetails The newProviderDetails field shall hold the provider details of the service to be added or updated in the directory service.
If any of the fields of the newProviderDetails domain/sessionName/network fields are either empty or contain the wildcard '*' an INVALID error shall be returned.
If the providerId field of the PublishDetails structure is empty or contains the wildcard '*' an INVALID error shall be returned.
For each contained ServiceKey structure if the area/service/version fields contain '0' then an INVALID error shall be returned.
For each contained supportedCapabilitySets list if the list is empty or contains '0' then an INVALID error shall be returned.
If the supportedLevels list is empty or the priorityLevels field is '0' for each contained AddressDetails structure found either within the ProviderDetails or the inner ServiceCapability structures then an INVALID error shall be returned.
If an error is being returned then no changes shall be made.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException Submitted values are invalid.
     * @throws MALException if there is an implementation exception
     */
    PublishProviderResponse publishProvider(PublishDetails newProviderDetails,
            MALInteraction interaction) throws InvalidException, MALException;
    /**
     * Implements the operation withdrawProvider.
     * 
     * @param providerObjId The providerObjId field shall hold the object instance identifier for the ServiceProvider COM object to remove from the directory service.
If the supplied identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
If an error is being returned then no changes shall be made.
The matched provider shall be removed from the directory service.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws UnknownException Provider to withdraw was not found.
     * @throws InvalidException Submitted values are invalid.
     * @throws MALException if there is an implementation exception
     */
    void withdrawProvider(Long providerObjId,
            MALInteraction interaction) throws UnknownException, InvalidException, MALException;
    /**
     * Implements the operation getServiceXML.
     * 
     * @param providerObjId The providerObjId field shall hold the COM object instance identifier for the ServiceProvider to obtain the service XML for.
If the supplied instance identifier is '0' an INVALID error shall be returned.
If the supplied identifier does not match an existing ServiceProvider COM object then an UNKNOWN error shall be returned.
     * @param interaction The MAL object representing the interaction in the provider.
     * @return The return value of the operation
     * @throws InvalidException Submitted values are invalid.
     * @throws UnknownException Provider was not found.
     * @throws MALException if there is an implementation exception
     */
    FileList getServiceXML(Long providerObjId,
            MALInteraction interaction) throws InvalidException, UnknownException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(DirectorySkeleton skeleton);
}
