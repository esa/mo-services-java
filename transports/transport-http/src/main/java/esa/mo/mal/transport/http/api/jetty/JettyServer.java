/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
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
package esa.mo.mal.transport.http.api.jetty;

import esa.mo.mal.transport.http.api.IContextHandler;
import esa.mo.mal.transport.http.api.IHttpServer;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SSLHelper;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * An implementation of the AbstractHttpServer interface based on
 * org.eclipse.jetty.server.Server.
 */
public class JettyServer implements IHttpServer {

    protected Server server;

    @Override
    public void initServer(InetSocketAddress serverSocket, boolean useHttps,
            String keystoreFilename, String keystorePassword) throws HttpApiImplException {
        if (useHttps) {
            server = new Server();
            SSLContext sslContext = SSLHelper.createSSLContext(keystoreFilename, keystorePassword);
            SslContextFactory sslFactory = new SslContextFactory();
            sslFactory.setSslContext(sslContext);
            ServerConnector serverConnector = new ServerConnector(server, sslFactory);
            serverConnector.setHost(serverSocket.getHostName());
            serverConnector.setPort(serverSocket.getPort());
            server.addConnector(serverConnector);
        } else {
            server = new Server(serverSocket);
        }
    }

    @Override
    public void addContextHandler(IContextHandler contextHandler) {
        ServletContextHandler servletContext = new ServletContextHandler(server, "/");
        servletContext.setAttribute("AbstractContextHandler", contextHandler);
        servletContext.addServlet(JettyServlet.class, "/");
    }

    @Override
    public void startServer() throws HttpApiImplException {
        try {
            server.start();
        } catch (Exception ex) {
            throw new HttpApiImplException("JettyServer: Exception at startServer()", ex);
        }
    }

    @Override
    public void stopServer() throws HttpApiImplException {
        try {
            server.stop();
        } catch (Exception ex) {
            throw new HttpApiImplException("JettyServer: Exception at stopServer()", ex);
        }
    }
}
