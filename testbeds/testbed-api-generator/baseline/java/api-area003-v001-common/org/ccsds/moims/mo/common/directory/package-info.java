/**
 * The Directory service allows service providers to publish information about
 * which services they provide and consumers to discover service provider
 * address and capability information.
 * Service provider information is made available using the Directory service
 * publishProvider operation and removed using the withdrawProvider operation.
 * The lookupProvider operation provides the ability for consumers to query
 * the directory service based on a filter such as required service capability.
 * Finally, a provider may supply its set of service specification XML files
 * when publishing its capabilities. This allows consumers that are able to
 * process MO service XML files obtain the files for further processing. It
 * is expected that this would be used to either ensure that no modifications
 * have been made by a provider to a standard service or, in the case of a
 * consumer that is able to dynamically interact with new service specifications,
 * obtain the service specification to allow interaction with that new service.
*/
package org.ccsds.moims.mo.common.directory;
