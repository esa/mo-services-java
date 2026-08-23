/**
 * The alert service defines the structures and patterns for the publishing
 * and monitoring of alerts. The alert service uses COM event service to monitor
 * and publish alert events.
 * The generation of alerts can be controlled using the enableGeneration operation,
 * which supports the use of groups. Groups must reference either other groups
 * or alerts only.
 * Alert definitions are maintained using the operations defined in this service
 * but storage of definitions is delegated to the COM archive.
*/
package org.ccsds.moims.mo.mc.alert;
