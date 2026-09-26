/**
 * This service aims at testing each Interaction Pattern (IP). It provides
 * one operation for Send, Submit, Request, Invoke and Progress. The input
 * parameter is an IPTestDefinition that contains:  - The parameters used
 * by the consumer in order to initiate the interaction. These parameters
 * enable the provider to check whether the received message header is correct
 * or not.  - A list of interaction transitions expected by the consumer.
 * An operation &quot;getResult&quot; is provided in order to enable the
 * consumer to get:  - the interaction transaction identifier  - and the assertions
 * evaluated on the provider side during an interaction.  Finally four operations
 * &quot;monitor&quot;, &quot;publishUpdates&quot; and &quot;publishError&quot;
 * are provided in order to test the Pub/Sub interaction.
*/
package org.ccsds.moims.mo.malprototype.iptest;
