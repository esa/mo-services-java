/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Transport Bridge Application
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.mal.transport.bridge;

import esa.mo.mal.impl.util.StructureHelper;
import java.io.BufferedWriter;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.Properties;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.transport.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (2 <= args.length) {
            String protocolA = args[0];
            String protocolB = args[1];

            System.out.println("Starting bridge between transport "
                    + protocolA + " and " + protocolB);
            loadProperties();

            MALTransport transportA = createTransport(protocolA);
            MALTransport transportB = createTransport(protocolB);

            MALEndpoint epA = createEndpoint(protocolA, transportA);
            MALEndpoint epB = createEndpoint(protocolB, transportB);

            storeURIs(System.getProperty("protocolA.uri.filename"), epA.getURI());
            storeURIs(System.getProperty("protocolB.uri.filename"), epB.getURI());

            wrapURIs(System.getProperty("protocolA.wrap.filename"),
                    System.getProperty("protocolA.wrap.filename") + ".wrapped", epA.getURI());
            wrapURIs(System.getProperty("protocolB.wrap.filename"),
                    System.getProperty("protocolB.wrap.filename") + ".wrapped", epB.getURI());

            System.out.println("Linking transports");
            epA.setMessageListener(new BridgeMessageHandler(epB));
            epB.setMessageListener(new BridgeMessageHandler(epA));

            System.out.println("Staring message delivery");
            epA.startMessageDelivery();
            epB.startMessageDelivery();

            System.out.println("Waiting...");
        } else {
            System.err.println("Requires two arguments!");
        }
    }

    protected static MALTransport createTransport(String protocol) throws Exception {
        System.out.println("Creating transport " + protocol);
        return MALTransportFactory.newFactory(protocol).createTransport(null, null);
    }

    protected static MALEndpoint createEndpoint(String protocol, MALTransport trans) throws Exception {
        System.out.println("Creating end point for transport " + protocol);
        MALEndpoint ep = trans.createEndpoint("BRIDGE", null);

        System.out.println("Transport " + protocol + " URI is " + ep.getURI().getValue());

        return ep;
    }

    protected static void loadProperties() throws MalformedURLException {
        java.util.Properties sysProps = System.getProperties();

        String configFile = System.getProperty("provider.properties", "bridge.properties");

        java.io.File file = new java.io.File(configFile);
        if (file.exists()) {
            sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL().toString(), "bridge.properties"));
        }

        System.setProperties(sysProps);
    }

    private static void wrapURIs(String srcFilename, String outputfileName, URI uri) throws Exception {
        java.io.File file = new java.io.File(srcFilename);
        if (file.exists()) {
            Properties props = StructureHelper.loadProperties(file.toURI().toURL().toString(), "");

            storeURIs(outputfileName,
                    uri.getValue() + "@" + props.getProperty("uri"),
                    uri.getValue() + "@" + props.getProperty("broker")
            );
        }
    }

    protected static void storeURIs(String filename, URI uri) throws Exception {
        storeURIs(filename, uri.getValue(), uri.getValue());
    }

    protected static void storeURIs(String filename, String uri, String buri) throws Exception {
        java.io.BufferedWriter wrt = new BufferedWriter(new java.io.FileWriter(new java.io.File(filename)));
        wrt.append("uri=").append(uri);
        wrt.newLine();
        wrt.append("broker=").append(buri);
        wrt.newLine();
        wrt.close();
    }

    protected static class BridgeMessageHandler implements MALMessageListener {

        private final MALEndpoint destination;

        public BridgeMessageHandler(MALEndpoint destination) {
            this.destination = destination;
        }

        public void onInternalError(MALEndpoint callingEndpoint, Throwable err) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void onTransmitError(MALEndpoint callingEndpoint,
                MALMessageHeader srcMessageHeader, MOErrorException err, Map qosMap) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void onMessage(MALEndpoint callingEndpoint, MALMessage srcMessage) {
            try {
                System.out.println("Received message from: "
                        + srcMessage.getHeader().getFrom().getValue());

                // copy source message into destination message format
                MALMessage dMsg = cloneForwardMessage(destination, srcMessage);
                destination.sendMessage(dMsg);
            } catch (MALException ex) {
                // ToDo need to bounce this back to source
            } catch (MALTransmitErrorException ex) {
                // ToDo need to bounce this back to source
            }
        }

        public void onMessages(MALEndpoint callingEndpoint, MALMessage[] srcMessageList) {
            try {
                MALMessage[] dMsgList = new MALMessage[srcMessageList.length];
                for (int i = 0; i < srcMessageList.length; i++) {
                    dMsgList[i] = cloneForwardMessage(destination, srcMessageList[i]);
                }

                destination.sendMessages(dMsgList);
            } catch (MALException ex) {
                // ToDo need to bounce this back to source
            }
        }
    }

    protected static MALMessage cloneForwardMessage(MALEndpoint destination,
            MALMessage srcMessage) throws MALException {
        MALMessageHeader sourceHdr = srcMessage.getHeader();
        MALMessageBody body = srcMessage.getBody();

        System.out.println("cloneForwardMessage from : " + sourceHdr.getFrom()
                + "    :    " + sourceHdr.getTo());
        String endpointUriPart = sourceHdr.getTo().getValue();
        final int iSecond = endpointUriPart.indexOf("@");
        endpointUriPart = endpointUriPart.substring(iSecond + 1, endpointUriPart.length());
        URI to = new URI(endpointUriPart);
        Identifier from = new Identifier(destination.getURI().getValue() + "@" + sourceHdr.getFrom().getValue());
        System.out.println("cloneForwardMessage      : " + from + "    :    " + to);

        MALMessage destMessage = destination.createMessage(
                sourceHdr.getAuthenticationId(),
                to,
                sourceHdr.getTimestamp(),
                sourceHdr.getInteractionType(),
                sourceHdr.getInteractionStage(),
                sourceHdr.getTransactionId(),
                sourceHdr.getServiceArea(),
                sourceHdr.getService(),
                sourceHdr.getOperation(),
                sourceHdr.getServiceVersion(),
                sourceHdr.getIsErrorMessage(),
                sourceHdr.getSupplements(),
                srcMessage.getQoSProperties(),
                body.getEncodedBody()
        );

        destMessage.getHeader().setFrom(from);

        return destMessage;
    }
}
