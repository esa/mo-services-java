/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Transport - HTTP
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
package esa.mo.mal.transport.http.connection;

import esa.mo.mal.transport.http.api.IContextHandler;
import esa.mo.mal.transport.http.api.IHttpServer;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SSLHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import javax.net.ssl.SSLContext;

/**
 * An implementation of the AbstractHttpServer interface based on
 * com.sun.net.httpserver.HttpServer.
 */
public class JdkServer implements IHttpServer {

    protected HttpServer server;

    @SuppressWarnings("restriction")
    @Override
    public void initServer(InetSocketAddress serverSocket, boolean useHttps, String keystoreFilename,
            String keystorePassword) throws HttpApiImplException {
        try {
            if (useHttps) {
                SSLContext sslContext = SSLHelper.createSSLContext(keystoreFilename, keystorePassword);
                HttpsServer httpsServer = HttpsServer.create(serverSocket, 0);
                httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext));
                server = httpsServer;
            } else {
                HttpServer httpServer = HttpServer.create(serverSocket, 0);
                server = httpServer;
            }
            server.setExecutor(Executors.newCachedThreadPool());
        } catch (IOException ex) {
            throw new HttpApiImplException("JdkServer: IOException at initServer()", ex);
        }
    }

    @SuppressWarnings("restriction")
    @Override
    public void addContextHandler(final IContextHandler contextHandler) {
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                try {
                    contextHandler.processRequest(new JdkHttpRequest(httpExchange));
                    contextHandler.processResponse(new JdkHttpResponse(httpExchange));
                    contextHandler.finishHandling();
                } catch (HttpApiImplException ex) {
                    RLOGGER.severe(ex.getMessage());
                    throw new IOException("JdkServer.addContextHandler(): HttpApiImplException at HttpHandler.handle()", ex);
                } catch (Throwable ex) {
                    RLOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                    throw ex;
                }
            }
        });
    }

    @SuppressWarnings("restriction")
    @Override
    public void startServer() throws HttpApiImplException {
        server.start();
    }

    @SuppressWarnings("restriction")
    @Override
    public void stopServer() throws HttpApiImplException {
        server.stop(0);
    }
}
