ESA CCSDS MO services - Release Notes
========================

These Release Notes include a short summary of the updates done for each version.
The different versions and respective updates are the following:

### Version 12.0 (08 May 2025)
* Fixes the Enumerations: Removal of the "ordinal" number
* Simplifies the api-generator

### Version 11.3 (16 April 2025)
* Updates the yml files to use 'temurin' instead of 'adopt'
* Improvements to the API and Word generator
* Simplification of the pom variables
* Multiple updates to the MPD specs and respective testbed
* Updates the MPS spec with the final round of RIDs
* Updates the M&C spec with the latest changes
* Adds jetty to the http transport implementation

### Version 11.2 (23 January 2025)
* Fixes the MAL Broker bug that did not allow multiple PUB-SUB operations within the same service
* MPD specification - Updates to include many of the RIDs found during implementation
* Word API Generator: Supports the new "Requirements" section on operations. These are obtained from the XML file of the specs
* Word API Generator: Supports the new "Type Signature" section on operations
* Updated the Hybrid XSD to be closer to the latest version. Also, testbed XML specs had to be updated for compliance to the Hybrid XSD
* Adds more tests to the MPD Testbed

### Version 11.1 (05 December 2024)
* Adds dedicated yaml workflows for testing the MAL with tcpip, http, and zmtp
* Merges the xml-jaxb project into the generator-interfaces project
* Improves the Enumerations auto-generated code
* Simplifies the parent POM file
* Improvements to the MPD services boilerplate code and also the simple-mo-provider and simple-mo-consumer

### Version 11.0 (13 June 2024)
* Updates all the API artifactIds to include the area number and area version
* Replaces the auto-generated getShortForm() and getTypeShortForm(), with getTypeId()
* Adds a simple Directory service implementation
* Adds the boilerplate code for the MPD services
* Adds a Simple MO Provider project and a Simple MO Consumer project

### Version 10.1 (17 May 2024)
* Updates all the xml files for their respective latest updates
* The yaml workflows for building the project and running the testbeds are now operational for: Java 8, 11, 17
* Simplifies and optimizes parts of the code in the Transport and in the API generation process
* Adds the HTTP project
* The HTTP and the ZMTP projects are now passing the testbed

### Version 10.0 (16 November 2023)
* Full alignment of the MAL API and MAL Impl to the MAL Standard book version 521.0-B-3
* Adds more tests to the MAL Testbed project
* Removal of unused classes and refactoring old code
* Adds yaml workflows for building the project and running the testbeds for different LTS Java versions (8, 11, 17, 21)
* Improves the Interface between the MAL and the Encoders/Decoders. Now, it passes the name, nullability and type for the field to be encoded/decoded

### Version 9 (02 February 2023)
* This release is a hybrid between the old and the new MAL (top API layer -> new MAL; low Transport layer -> old MAL)
* Fixes the MAL testbed for the new MAL updates
* Lowers memory footprint
* Removes the generation of the Type Factories from the APIs. Introduces new MALElementsRegistry class as a replacement.
* Adds first iteration of the MO Navigator project
* MAL Broker updated to follow the new MAL PUB-SUB
* Removes the Generic Encoding project (merged into the MAL API)

### Version 8 (January 2021)
* Merged multiple repos into a single one
* Increased the Java supported version from 1.6 to 1.8 (Java 8 has Long Term Support until 2030)
* Improvements in the TCP/IP implementation
* Updated the Common API to follow the latest version of the MO Standards
* Added the ZMTP implementation which has passed interoperability tests
* Improved Generic Transport to support execution of the software with Java 9

### Version 7 (December 2017)
* The APIs now include the xml file as a resource (will enable the provision of the XML via the Directory service)
* Increased the Java supported version from 1.5 to 1.6
* Added the latest version of the TCP/IP transport and Binary encoding implementations
* Added the latest version of the ZMTP
* Optimizations on the MAL level for efficiency

### Version 6 (January 2017)
* Untracked
