/**
 * The Archive service provides a basic interface to a standard archiving
 * function. It follows the basic CRUD principles and allows simple querying
 * of the archive. It provides operations to add new objects to an archive,
 * delete objects from an archive, update existing objects in an archive,
 * and also query the content of the archive.
 * The query operation provides a basic querying ability, allowing a consumer
 * to filter on fields from the object headers (such as domain etc) and also
 * filter on the body of the object if it uses the MAL data type specification.
 * The query operation is extensible but the extensions would be outside this
 * standard.
 * Finally, a consumer of the archive can monitor it for changes by subscribing
 * for archive events from the event service. Any change to the archive is
 * published using the event service if it is supported by an implementation.
*/
package org.ccsds.moims.mo.com.archive;
