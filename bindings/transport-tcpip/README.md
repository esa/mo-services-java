# MAL TCPIP Transport Binding
This is an implementation for a MAL transport binding using the TCP/IP protocol. This quick start guide provides information on how to run a MAL application using the tcpip bind

## Getting started
In order to start using this transport binding, one has to set the properties `org.ccsds.moims.mo.mal.transport.tcpip.host` and `org.ccsds.moims.mo.mal.transport.tcpip.port` in the configuration file that is used by a consumer or provider. Another option is to use the `org.ccsds.moims.mo.mal.transport.tcpip.autohost` property to automatically assign the host and a port. Moreover, one has pass the protocol `maltcp` when instantiating a consumer or provider, in order to ensure that the TCPIP transport is used. See below for a full list of configuration parameters.

The demo apps, found in the [CCSDS_MO_APPS](https://github.com/esa/CCSDS_MO_APPS) git repository, prove an excellent base to test and understand the transport binding. To use the apps in combination with this binding, ensure that the following configuration parameters are set. Moreover, ensure to replace `<LOCAL IP ADDRESS>` by your actual local ip address.

demoProvider.properties:
```
esa.mo.mal.demo.provider.protocols=RMI,rmi,TCP/IP,maltcp,JMS,ccsdsjms,File,file
org.ccsds.moims.mo.mal.transport.default.protocol = maltcp://

# TCPIP protocol properties
org.ccsds.moims.mo.mal.transport.protocol.maltcp=esa.mo.mal.transport.tcpip.TCPIPTransportFactoryImpl
org.ccsds.moims.mo.mal.encoding.protocol.maltcp=esa.mo.mal.encoder.binary.BinaryStreamFactory
org.ccsds.moims.mo.mal.transport.tcpip.autohost=true
#org.ccsds.moims.mo.mal.transport.tcpip.host=xxx.xxx.xxx.xxx
#org.ccsds.moims.mo.mal.transport.tcpip.port=54321
#org.ccsds.moims.mo.mal.transport.tcpip.isServer=true
org.ccsds.moims.mo.mal.transport.tcpip.debug=FINEST
```

demoConsumer.properties:
```
org.ccsds.moims.mo.mal.transport.default.protocol = maltcp://
# TCPIP protocol properties
org.ccsds.moims.mo.mal.transport.protocol.maltcp=esa.mo.mal.transport.tcpip.TCPIPTransportFactoryImpl
org.ccsds.moims.mo.mal.encoding.protocol.maltcp=esa.mo.mal.encoder.binary.BinaryStreamFactory
org.ccsds.moims.mo.mal.transport.tcpip.autohost=true
org.ccsds.moims.mo.mal.transport.tcpip.debug=FINEST
```

demoServiceURI.properties:
```
uri=maltcp://<LOCAL IP ADDRESS>:61617/Demo
broker=maltcp://<LOCAL IP ADDRESS>:61617/DemoInternalBroker
```

## URI Routing
This transport binding makes use of the TCP/IP ip address and port number to route messages. Each message contains
a source and destination URI, which contain the ip address and port of the provider and consumer respectively. 

The protocol used is `maltcp://`. Message urls have the format `maltcp://ipaddr:port/serviceDescriptor`.

According to the socket implementation, providers create a server socket for accepting messages and a socket for communication with clients.
However, every service instance, be it a provider, consumer, or both, can only have one unique address. Therefore, URIs are routed 
to the actual port that reads incoming messages, but internally they are mapped to the port of the server socket, if the service
is a provider. When a new socket is created, it will be assigned a random available port.

This means that end users may have to configure their system to allow more ports than only the port that they set their server instance to.
Ensure that the application can open a new port if necessary.

## Testing the binding using the testbed
The testbed, used to validate the MAL TCPIP transport binding, is found in the git repository [CCSDS_MO_TESTBEDS](https://github.com/esa/CCSDS_MO_TESTBEDS). In order to use this testbed to validate the transport binding, its source must be cloned in a location on the same machine.
The following directory structure is proposed:
```
<top-level-directory>
	CCSDS_MO_TRANS
		CCSDS_MAL_TRANSPORT_TCPIP
		...
	CCSDS_MO_TESTBEDS
		MOIMS_TESTBED_MAL
		...		
```

### Parameter setup
Some parameters have to be setup correctly in order to run the testbed successfully. These can be found in the section "Configuration parameters".

### Running the testbed
This section assumes that CCSDS has been built correctly, including the MAL TCPIP transport binding.
The testbed is configured in maven with the `ESA_TCPIP` profile. In order to run the testbed for the TCPIP binding, one has to execute the following:
```
	cd CCSDS_MO_TESTBEDS/MOIMS_TESTBED_POM
	mvn clean install
	cd ../MOIMS_TESTBED_UTIL
	mvn -P ESA clean install
	cd ../MOIMS_TESTBED_MAL
	mvn -P ESA_TCPIP clean install
```


## Configuration parameters
Each transport service which is launched, uses a configuration file to load necessary configuration parameters. The MAL framework, and the TCPIP Transport binding, expect at least one parameter to be configured: `org.ccsds.moims.mo.mal.transport.tcpip.host`.
Below a list of all possible configuration parameters is given.

| Property name		                                    | Description                                                                                                                                                                                                                                                                                                                                                                          |
|:---------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| org.ccsds.moims.mo.mal.transport.default.protocol  | maltcp                                                                                                                                                                                                                                                                                                                                                                               |
| org.ccsds.moims.mo.mal.factory.class               | esa.mo.mal.impl.MALContextFactoryImpl                                                                                                                                                                                                                                                                                                                                                |
| org.ccsds.moims.mo.mal.transport.protocol.maltcp   | esa.mo.mal.transport.tcpip.TCPIPTransportFactoryImpl                                                                                                                                                                                                                                                                                                                                 |
| org.ccsds.moims.mo.mal.encoding.protocol.maltcp    | esa.mo.mal.encoder.binary.BinaryStreamFactory                                                                                                                                                                                                                                                                                                                                        |
| org.ccsds.moims.mo.mal.transport.tcpip.host        | adapter (host / IP Address) that the transport will use for incoming connections. In case of a pure client (i.e. not offering any services) this property should be omitted. Note that the transport binding only accepts full ip4 or ip6 addresses, no hostnames. This property alows binding to `0.0.0.0`.                                                                         |
| org.ccsds.moims.mo.mal.transport.tcpip.publishedip | ip or provider alias that will be used to generate services addresses. If not specified the value of `org.ccsds.moims.mo.mal.transport.tcpip.host` will be used. This property is required if `org.ccsds.moims.mo.mal.transport.tcpip.host` is set to `0.0.0.0`                                                                                                                      |
| org.ccsds.moims.mo.mal.transport.tcpip.port        | port that the transport listens to. In case this is a pure client, this property should be omitted. Defaults to 61616.                                                                                                                                                                                                                                                               |
| org.ccsds.moims.mo.mal.transport.tcpip.debug       | The level of debug messages to show. The value passed must equal one of Java.util.logging values, as defined [here](https://docs.oracle.com/javase/8/docs/api/java/util/logging/Level.html). This property only influences the level of debug traces generated by the TCPIP Transport binding. Any debug traces from other parts of the MAL framework have to be handled separately. |
| org.ccsds.moims.mo.mal.transport.tcpip.hostalias   | Property allowing setting up an alias for a provider and routing messages over ssh. This property has the following structure: alias@routedIp where:<ul><li>alias - an alias for the provider</li><li>routedIp - an ip address to which the messages should be routed to</li></ul>                                                                                                   |

## Binding to `0.0.0.0` and host aliasing example
First you need to set the `org.ccsds.moims.mo.mal.transport.tcpip.host` property to `0.0.0.0`. Then it is required to set the `org.ccsds.moims.mo.mal.transport.tcpip.publishedip`
property. This property can be set to whatever hostname you want, for example `test_backend`. After that the provider configuration is done.

Let's assume that one of the network interfaces available on the provider has this ip `123.23.3.4`.

On the consumer side there are two ways that you can connect to the provider:
1. Using `/etc/hosts`. In the hosts file add this entry: `123.23.3.4 test_backend`
2. Using the `org.ccsds.moims.mo.mal.transport.tcpip.hostalias`. Set this property to `test_backend@123.23.3.4`

After using one of the methods all messages sent to `maltcp://test_backend:1024/provider_name-Service_name` will be 
redirected to `maltcp://123.23.3.4:1024/provider_name-Service_name`.

It is also possible to connect to the provider that is behind an ssh tunnel. In this case you need to set up ssh port forwarding like this:
`ssh -L localhost:1024:123.23.3.4:1024 some_ssh_server` and reconfigure the `/etc/hosts` file entry to `127.0.0.1 test_backend` or change the
`org.ccsds.moims.mo.mal.transport.tcpip.hostalias` property to `test_backend@127.0.0.1`. After that all messages sent to `test_backend` will be redirected to
`localhost` on your machine and then ssh port forwarding will redirect them further to `123.23.3.4` on the provider machine.
