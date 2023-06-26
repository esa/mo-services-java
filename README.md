<p align="left">
  <a href="https://github.com/esa/mo-services-java">
      <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/af/ESA_logo.png/800px-ESA_logo.png" alt="esa logo" title="esa" width="400"/>
  </a>
</p>

CCSDS MO services - ESA's Java implementation
========================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/int.esa.ccsds.mo/mo-services-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/int.esa.ccsds.mo/mo-services-java)

CCSDS Mission Operations (MO) services are a set of standard end-to-end services based on a service-oriented architecture defined by the Consultative Committee for Space Data Systems (CCSDS) and it is intended to be used for mission operations of future space missions.

The architecture allows mission operation services to be specified in an implementation and communication agnostic manner. The core of the MO service framework is its Message Abstraction Layer (MAL) which ensures interoperability between mission operation services deployed on different framework implementations. The MO services are specified in compliance to a reference service model, using an abstract service description language, the MAL. This is similar to how Web Services are specified in terms of Web Services Description Language WSDL. For each concrete deployment, the abstract service interface must be bound to the selected software implementation and communication technology.

Standardization of a Mission Operations Service Framework offers a number of potential benefits for the development, deployment and maintenance of mission operations infrastructure:
- Increased interoperability between agencies;
- Re-usage between missions;
- Reduced costs;
- Greater flexibility in deployment boundaries;
- Increased competition and vendor independence;
- Improved long-term maintainability.

The deployment of standardized interoperable interfaces between operating Agencies, the spacecraft and internally on-board would in itself bring a number of benefits. Each organization would be able to develop or integrate their own multi-mission systems that can then be rapidly made compliant with the spacecraft. It does not preclude the reuse of legacy spacecraft, simply requiring an adaptation layer on the ground to support it, rather than many mission-specific bespoke interfaces. In the on-board environment, where software development costs are considerably higher due to platform constraints and reliability requirements, software reuse can bring immense savings.

## MO stack

![layerImage]

## List of relevant Links:

- Wikipedia page: https://github.com/esa/CCSDS_MO/wiki
- Documentation: https://github.com/esa/CCSDS_MO/wiki/Documentation
- Service interfaces: https://dmarszk.github.io/MOWebViewer4NMF/
- CCSDS website: https://public.ccsds.org/
- Online CCSDS Books: https://public.ccsds.org/review/default.aspx
- CCSDS Mission Operations services for Newbies:  https://github.com/esa/CCSDS_MO/wiki/CCSDS-Mission-Operations-services-for-Newbies
- GitHub CCSDS MO services source code: https://github.com/esa/mo-services-java
- MO Training material: https://github.com/esa/CCSDS_MO_TRAINING
- Online video:  https://www.youtube.com/watch?v=XdGeaJE7yEk

## Building Prerequisites

1. Install Java SDK 1.8 (will also work with JAVA 11, but 1.8 is the recommended version. SDK 17 is not supported yet - See GitHub Workflows)
```bash
sudo apt-get install openjdk-8-jdk
```
2. Install Apache Maven
```bash
sudo apt-get install maven
```

## Building Instructions

3. Clone this repository
```bash
git clone https://github.com/esa/mo-services-java.git
```

4. Build the cloned CCSDS MO Framework project:
```bash
mvn clean install
```

## Testbed

After building the main repository, navigate to the `testbeds` directory. When building the testbed, an agency profile needs to be provided, e.g. ESA:
```bash
mvn clean install -P ESA
```
This will download and install the required dependencies and execute the tests. After running `mvn clean install` once, the tests can be executed by running `mvn surefire:test -P ESA`. Once finished, navigate to the report directory `testbed-mal/target/surefire-reports/` and open `TestDocument.html` to see the test results.

Logs of the test run can be found in the report directory in `zzz_CCSDS_[...].txt`.

#### Selective Testing
Running all test procedures usually takes several minutes. However, this is often not necessary during development. The testbed can be configured to only run selected tests as follows.

Navigate to `testbeds/testbed-mal/src/main/fitness/FitNesseRoot/MalTests/TestDocument` and open `content.txt`. By adding `#` at the beginnig of a line, the corresponding test will not be executed.

#### Troubleshooting

`shared broker = true` tests fail: Eventhough the framework supports the use of shared brokers, this is rarely used in practice as it bringt unnecessary overhead in most of the cases. Errors of this kind are usually results of issues on the transport layer.

## Release

The Releases can be found in: [Releases]

The release notes are available in [Release Notes].

## More information

More code examples are available under [tooling](tooling) directory.

## Bugs Reporting

Bug Reports are directly in the source code repository can be submitted on: [Issues]

## License

The CCSDS MO services are **licensed** under: **[European Space Agency Public License (ESA-PL) Weak Copyleft - v2.0]**
	
[ESAImage]: https://upload.wikimedia.org/wikipedia/commons/a/af/ESA_logo.png
[European Space Agency Public License (ESA-PL) Weak Copyleft - v2.0]: LICENCE.md
[GitHub]: https://github.com/esa/mo-services-java
[Release Notes]: RELEASE_NOTES.md
[Releases]: https://github.com/esa/mo-services-java/releases
[Issues]: https://github.com/esa/mo-services-java/issues
[website]: http://www.esa.int/
[layerImage]: https://upload.wikimedia.org/wikipedia/commons/9/9a/Ccsds_mo_service_layers.jpg
