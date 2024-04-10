ESA CCSDS MO services - Release Notes
========================

These Release Notes include a short summary of the updates done for each version.
The different versions and respective updates are the following:

### Version 10.1 (Expected in 2024)
* Updates all the xml files for their respective latest updates
* The yaml workflows for building the project and running the testbeds are now operational for: Java 8, 11, 17
* Simplifies and optimizes parts of the code in the Transport and in the API generation process
* Adds the HTTP project

### Version 10.0 (2023 November 16)
* Full alignment of the MAL API and MAL Impl to the MAL Standard book version 521.0-B-3
* Adds more tests to the MAL Testbed project
* Removal of unused classes and refactoring old code
* Adds yaml workflows for building the project and running the testbeds for different LTS Java versions (8, 11, 17, 21)
* Improves the Interface between the MAL and the Encoders/Decoders. Now, it passes the name, nullability and type for the field to be encoded/decoded

### Version 9 (2023 February)
* This release is a hybrid between the old and the new MAL (top API layer -> new MAL; low Transport layer -> old MAL)
* Fixes the MAL testbed for the new MAL updates
* Lowers memory footprint
* Removes the generation of the Type Factories from the APIs. Introduces new MALElementsRegistry class as a replacement.
* Adds first iteration of the MO Navigator project
* MAL Broker updated to follow the new MAL PUB-SUB
* Removes the Generic Encoding project (merged into the MAL API)

### Version 8 (2021 January)
* Merged multiple repos into a single one
* Increased the Java supported version from 1.6 to 1.8 (Java 8 has Long Term Support until 2030)
* Improvements in the TCP/IP implementation
* Updated the Common API to follow the latest version of the MO Standards
* Added the ZMTP implementation which has passed interoperability tests
* Improved Generic Transport to support execution of the software with Java 9

### Version 7 (2017 December)
* The APIs now include the xml file as a resource (will enable the provision of the XML via the Directory service)
* Increased the Java supported version from 1.5 to 1.6
* Added the latest version of the TCP/IP transport and Binary encoding implementations
* Added the latest version of the ZMTP
* Optimizations on the MAL level for efficiency

### Version 6 (2017 January)
* Untracked
