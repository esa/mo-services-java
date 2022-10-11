# CCSDS MO services - ESA's Java implementation

CCSDS Mission Operations (MO) services are a set of standard end-to-end services based on a service-oriented architecture defined by the Consultative Committee for Space Data Systems (CCSDS) and it is intended to be used for mission operations of future space missions.

The architecture allows mission operation services to be specified in an implementation and communication agnostic manner. The core of the MO service framework is its Message Abstraction Layer (MAL) which ensures interoperability between mission operation services deployed on different framework implementations. The MO services are specified in compliance to a reference service model, using an abstract service description language, the MAL. This is similar to how Web Services are specified in terms of Web Services Description Language WSDL. For each concrete deployment, the abstract service interface must be bound to the selected software implementation and communication technology.

Standardization of a Mission Operations Service Framework offers a number of potential benefits for the development, deployment and maintenance of mission operations infrastructure:
- Increased interoperability between agencies;
- Re-usage between missions;
- Reduced costs;
- Greater flexibility in deployment boundaries;
- Increased competition and vendor independence;
- Improved long-term maintainability.

The deployment of standardized interoperable interfaces between operating Agencies, the spacecraft and internally on-board would in itself bring a number of benefits. Each organization would be able to develop or integrate their own multi-mission systems that can then be rapidly made compliant with the spacecraft. It does not preclude the reuse of legacy spacecraft, simply requiring an adaptation layer on the ground to support it, rather than many missionspecific bespoke interfaces. In the on-board environment, where software development costs are considerably higher due to platform constraints and reliability requirements, software reuse can bring immense savings.

![layerImage]

# List of relevant Links:
- Wikipedia page: https://github.com/esa/CCSDS_MO/wiki
- Documentation: https://github.com/esa/CCSDS_MO/wiki/Documentation
- Service interfaces: https://dmarszk.github.io/MOWebViewer4NMF/
- CCSDS website: https://public.ccsds.org/
- Online CCSDS Books: https://public.ccsds.org/review/default.aspx
- CCSDS Mission Operations services for Newbies:  https://github.com/esa/CCSDS_MO/wiki/CCSDS-Mission-Operations-services-for-Newbies
- GitHub CCSDS MO services source code: https://github.com/esa
- MO Training material: https://github.com/esa/CCSDS_MO_TRAINING
- Online video:  https://www.youtube.com/watch?v=XdGeaJE7yEk


## Release
The latest release note is available in [Releases]. However, the currently recommended distribution channel is directly from Git repository.

## Building Prerequisites

1. Install Java SDK 1.8 (will work with higher SDKs but 1.8 is the recommended)
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

## Getting Started

### SDK and examples

More documentation about code examples, SDK packaging and usage is available under [sdk](sdk) directory.

### Logging

This project uses the default Java logger (java.util.Logger) to generate log messages. The verbosity of these log messages can be changed by configuring the logging.properties file inside the NMF\_HOME directory.

## Source Code

The source code of the NanoSat MO Framework can be found on [GitHub].

## Bugs Reporting

Bug Reports can be submitted on: [Issues]

Or directly in the respective source code repository.

## License

The NanoSat MO Framework is **licensed** under:

**[European Space Agency Public License (ESA-PL) Weak Copyleft â€“ v2.4]**.

[![][ESAImage]][website]
	

[ESAImage]: https://upload.wikimedia.org/wikipedia/commons/a/af/ESA_logo.png
[European Space Agency Public License - v2.0]: https://github.com/esa/CCSDS_MO_TRANS/blob/master/LICENCE.md
[GitHub]: https://github.com/esa/mo-services-java
[Releases]: https://github.com/esa/mo-services-java/blob/master/Release_Notes.txt
[Issues]: https://github.com/esa/mo-services-java/issues
[website]: http://www.esa.int/
[layerImage]: https://upload.wikimedia.org/wikipedia/commons/9/9a/Ccsds_mo_service_layers.jpg
