/**
 * The Login service defines the primary mechanism for the submission of authentication
 * credentials to a deployment specific security system. It supports operations
 * to allow a user to login, logout, report available roles, and also handover
 * the login to another user.
 * The service is closely tied to the Access Control aspect of the MAL where
 * the returned authentication identifiers are used in the MAL message header
 * to authenticate and authorise message via Access Control.
 * The login service supports the concept of roles, where users may log in
 * with a specific role; the meaning of each role is mission-specific, however
 * it is expected that a specific role allocates the user privileges to invoke
 * operations on mission operation services.
 * The use of login roles is optional but if they are used then the role details
 * are held in the COM archive and the COM archive operations are used to
 * manage the role definitions. The responsibility for maintenance of the
 * login roles is outside the scope of this specification as it is a deployment
 * issue to define the possible roles and associate users to those roles.
 * The form in which the password is sent to the Login service provider must
 * be agreed upon beforehand and is dependent on the security system deployed.
 * For example, most security implementations do not recommend the use of
 * plain text passwords but rather some encrypted version of the password.
 * For this reason the contents of the messages sent between the login service
 * consumer and provider during authentication handshaking are implementation-specific.
 * The authentication and authorisation concept of the MO services is covered
 * in section 3.6 of the Reference Model (R2).
 * The Reference Model (R2) also provides sequence diagrams for security and
 * login in sections 5.2 and 5.3.
*/
package org.ccsds.moims.mo.common.login;
