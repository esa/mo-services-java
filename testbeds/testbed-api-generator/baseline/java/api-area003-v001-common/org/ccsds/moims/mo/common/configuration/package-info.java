/**
 * The Configuration service allows a service consumer to activate pre-defined
 * configurations of a service provider.
 * The service uses a COM object to represent the different configurations
 * allowed by a service provider; a service consumer selects one configuration
 * to activate using the Configuration service activate operation.
 * The contents of a configuration for a service provider is deployment specific
 * however the management of these configurations, and the selection of a
 * configuration for current use, is the purpose of this service.
 * Implementations of this service may also use bespoke methods for configuration
 * representation (such as hard-coded configuration) which is outside the
 * scope of this specification, however, the status and management of these
 * configurations can still be managed with this service.
 * NOTE: This service uses the COM Event service to distribute changes in
 * state, therefore participating service providers should ensure they are
 * all using the same shared COM Event service if they wish to receive Configuration
 * service events.
*/
package org.ccsds.moims.mo.common.configuration;
