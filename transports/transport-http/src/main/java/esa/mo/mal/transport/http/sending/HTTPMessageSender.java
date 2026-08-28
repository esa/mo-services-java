/* ----------------------------------------------------------------------------
 * Copyright (C) 2021      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO HTTP Transport Framework
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
package esa.mo.mal.transport.http.sending;

import esa.mo.mal.transport.gen.sending.MessageSender;
import esa.mo.mal.transport.http.HTTPTransport;
import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import java.io.IOException;
import org.ccsds.moims.mo.mal.transport.MALMessageHeader;

/**
 * Common ground for the message senders of the HTTP transport.
 *
 * One sender exists per HTTP binding mode. They differ in how much of the MAL
 * message is mapped onto HTTP, and in whether they take part in the HTTP
 * request/response paradigm, so each writes its own send method rather than
 * refining another sender's.
 */
public abstract class HTTPMessageSender implements MessageSender<byte[]> {

    protected final HTTPTransport transport;
    protected final String abstractPostClientImpl;

    /**
     * Constructor.
     *
     * @param transport The parent HTTP transport.
     * @param abstractPostClientImpl AbstractPostClient interface implementation
     */
    protected HTTPMessageSender(final HTTPTransport transport, final String abstractPostClientImpl) {
        this.transport = transport;
        this.abstractPostClientImpl = abstractPostClientImpl;
    }

    /**
     * Maps the MAL header fields of a message onto the HTTP headers of the
     * request that carries it. Binding modes that do not map the MAL header
     * onto HTTP leave this empty.
     *
     * @param malMessageHeader the MALMessageHeader
     * @param client the AbstractPostClient
     * @throws IOException in case an internal error occurs
     */
    public abstract void setRequestHeaders(MALMessageHeader malMessageHeader,
            IPostClient client) throws IOException;

    /**
     * Returns the URL to send to, derived from the To field of the MAL header.
     *
     * @param malMessageHeader The MAL message header.
     * @return The remote URL.
     */
    protected String getRemoteUrl(final MALMessageHeader malMessageHeader) {
        String remoteUrl = malMessageHeader.getTo().getValue();

        if (transport.useHttps()) {
            return remoteUrl.replaceAll("malhttp://", "https://");
        }

        return remoteUrl.replaceAll("malhttp://", "http://");
    }

    /**
     * Creates a client and connects it to the given URL.
     *
     * @param remoteUrl The URL to connect to.
     * @return The connected client.
     * @throws HttpApiImplException in case the client could not be created or
     * connected.
     */
    protected IPostClient connectPostClient(final String remoteUrl) throws HttpApiImplException {
        IPostClient client = createPostClient();
        client.initAndConnectClient(remoteUrl, transport.useHttps(),
                transport.getKeystoreFilename(), transport.getKeystorePassword());
        return client;
    }

    /**
     * Creates an instance of the AbstractPostClient interface.
     *
     * @return the AbstractPostClient implementation
     * @throws HttpApiImplException in case an error occurs when trying to
     * instantiate the AbstractPostClient
     */
    public IPostClient createPostClient() throws HttpApiImplException {
        try {
            return (IPostClient) Class.forName(abstractPostClientImpl).newInstance();
        } catch (ClassNotFoundException ex) {
            throw new HttpApiImplException("HTTPMessageSender: ClassNotFoundException at createPostClient()", ex);
        } catch (InstantiationException ex) {
            throw new HttpApiImplException("HTTPMessageSender: InstantiationException at createPostClient()", ex);
        } catch (IllegalAccessException ex) {
            throw new HttpApiImplException("HTTPMessageSender: IllegalAccessException at createPostClient()", ex);
        }
    }

    /**
     * Lets the current thread sleep during the specified number of
     * milliseconds.
     *
     * @param millis the duration of time to sleep in milliseconds
     */
    protected void threadSleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            // do nothing
        }
    }

    @Override
    public void close() {
        // nothing to close
    }
}
