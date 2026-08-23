/**
 * The parameter service allows the user to subscribe to parameter value report
 * and optionally be able to set new values. A single PUBSUB operation is
 * provided for monitoring and publishing of parameter values.
 * A parameter value also contains a calculation of the validity of the parameter,
 * the flow chart for this calculation is provided in Figure 3-3:
 * validity calculation flow chart
 * 
 * This standard supports the concept of non-standard invalidity states but
 * the meaning and calculation of these is outside the scope of this standard.
 * The generation of value reports can be controlled using the enableGeneration
 * operation, which supports the use of groups. Groups must reference parameter
 * identities or groups of parameter identities only.
 * The parameter service does not include any value checking, this is delegated
 * to the check service.
 * Parameter definitions are maintained using the operations defined in this
 * service but storage of definitions is delegated to the COM archive.
*/
package org.ccsds.moims.mo.mc.parameter;
