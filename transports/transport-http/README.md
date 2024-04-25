MAL HTTP XML Transport Binding
This is an implementation for a MAL transport binding using the HTTP protocol and XML body encoding. This quick start guide provides information on how to run a MAL application using the http xml transport binding.

## Getting started
### Prerequisites
In order to start using this transport binding, the properties `org.ccsds.moims.mo.mal.transport.http.host` and `org.ccsds.moims.mo.mal.transport.http.port` must be set in the configuration file that is used by a consumer or provider. Moreover, the protocol `malhttp` must be passed when instantiating a consumer or provider, in order to ensure that the HTTP transport is used. See below for a full list of configuration parameters.

The system running this transport binding is assumed to have a UTC timezone.

### Demo apps
The demo apps, found in `mo-services-java/tooling`, prove an excellent base to test and understand the transport binding. To use the apps in combination with this binding, ensure that the following configuration parameters are set. Moreover, ensure to replace `<LOCAL IP ADDRESS>` by your actual local ip address.

demoProvider.properties:
```
org.ccsds.moims.mo.mal.transport.default.protocol = malhttp://

org.ccsds.moims.mo.mal.transport.protocol.malhttp=esa.mo.mal.transport.http.HTTPTransportFactoryImpl
org.ccsds.moims.mo.mal.transport.http.port=61617
org.ccsds.moims.mo.mal.transport.http.host=<LOCAL IP ADDRESS>
org.ccsds.moims.mo.mal.transport.http.debug=FINEST
org.ccsds.moims.mo.mal.encoding.protocol.malhttp=esa.mo.mal.encoder.xml.XMLStreamFactory
```

demoConsumer.properties:
```
org.ccsds.moims.mo.mal.transport.default.protocol = malhttp://

org.ccsds.moims.mo.mal.transport.protocol.malhttp=esa.mo.mal.transport..http.HTTPTransportFactoryImpl
org.ccsds.moims.mo.mal.transport.http.debug=FINEST
org.ccsds.moims.mo.mal.encoding.protocol.malhttp=esa.mo.mal.encoder.xml.XMLStreamFactory
```

demoServiceURI.properties:
```
uri=malhttp://<LOCAL IP ADDRESS>:61617/Demo
broker=malhttp://<LOCAL IP ADDRESS>:61617/DemoInternalBroker
```

## URI Routing
This transport binding makes use of the ip address and port number to route messages. Each message contains
a source and destination URI, which contain the ip address and port of the provider and consumer respectively. 

The protocol used is `malhttp://`. Message urls have the format `malhttp://ipaddr:port/serviceDescriptor`.

According to the specifications, both providers and consumers create a http server to communicate and receive `POST` requests.
However, every service instance, be it a provider, consumer, or both, can only have one unique address. Therefore, end users may have to configure their system to route requests to the http server correctly.
Ensure that the application can open a new port if necessary.

## Testing the binding using the testbed
The testbed, used to validate the MAL HTTP transport binding, is found in `mo-services-java/testbeds/testbed-mal`

### Parameter setup
Some parameters have to be setup correctly in order to run the testbed successfully. These can be found in the section "Configuration parameters".

### Running the testbed
This section assumes that CCSDS has been built correctly, including the MAL HTTP transport binding.
The testbed is configured in maven with the `ESA_HTTP` profile. In order to run the testbed for the HTTP binding, one has to execute the following:
```
  cd mo-services-java/testbeds/testbed-pom
  mvn clean install
  cd ../testbed-util
  mvn -P ESA clean install
  cd ../testbed-mal
  mvn -P ESA_HTTP clean install
```


## Configuration parameters
Each transport service which is launched, uses a configuration file to load necessary configuration parameters. The MAL framework, and the HTTP Transport binding, expect at least one parameter to be configured: `org.ccsds.moims.mo.mal.transport.http.host`.
Below a list of all configuration parameters is given.

| Property name     | Description |
|:------------------|:------------|
| org.ccsds.moims.mo.mal.transport.default.protocol | malhttp |
| org.ccsds.moims.mo.mal.factory.class | esa.mo.mal.impl.MALContextFactoryImpl |
| org.ccsds.moims.mo.mal.transport.protocol.malhttp | esa.mo.mal.transport.http.HTTPTransportFactoryImpl |
| org.ccsds.moims.mo.mal.encoding.protocol.malhttp | esa.mo.mal.encoder.xml.XMLStreamFactory |
| org.ccsds.moims.mo.mal.transport.http.host | adapter (host / IP Address) that the transport will use for incoming connections. In case of a pure client (i.e. not offering any services) this property should be omitted. Note that the transport binding only accepts full ip4 or ip6 addresses, no hostnames. |
| org.ccsds.moims.mo.mal.transport.http.port | port that the transport listens to. In case this is a pure client, this property should be omitted. Defaults to a random port number. |
| org.ccsds.moims.mo.mal.transport.http.timeout | The timeout in seconds before the MAL framework stops waiting for an expected message. Defaults to 60 seconds. |
| org.ccsds.moims.mo.mal.transport.http.serverimpl | set this parameter to the class name of a custom server implementation. The implementation must implement the `AbstractHttpServer` interface. |
| org.ccsds.moims.mo.mal.transport.http.clientimpl | set this parameter to the class name of a custom client implementation. The implementation must implement the `AbstractPostClient` interface. |
| org.ccsds.moims.mo.mal.transport.http.bindingmode | Select the binding mode to use. Can be either one of three options: `NoEncoding`, `NoResponse`, and `RequestResponse`. Defaults to `RequestResponse`. |
| org.ccsds.moims.mo.mal.transport.http.usehttps | set to `true` to use https. |
| org.ccsds.moims.mo.mal.transport.http.keystore.filename | the filename of the keystore to use for initialization of the http server. |
| org.ccsds.moims.mo.mal.transport.http.keystore.password | the password of the keystore to use for initialization of the http server. |
| org.ccsds.moims.mo.mal.transport.http.debug | The level of debug messages to show. The value passed must equal one of Java.util.logging values, as defined [here](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Level.html). This property only influences the level of debug traces generated by the HTTP Transport binding. Any debug traces from other parts of the MAL framework have to be handled separately. |
| org.ccsds.moims.mo.mal.transport.http.hostalias.X   | Property allowing setting up an alias for a provider and routing messages over ssh. This property has the following structure: alias@routedIp where:<ul><li>alias - an alias for the provider</li><li>routedIp - an ip address to which the messages should be routed to</li></ul> The `X` at the end of the property should be replaced with an index of the alias (starting from 0, even if there is only one alias) |
