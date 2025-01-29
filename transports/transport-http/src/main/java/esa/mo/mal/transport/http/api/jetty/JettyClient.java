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

import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SSLHelper;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.SSLContext;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 * An implementation of the AbstractPostClient interface based on
 * org.eclipse.jetty.client.HttpClient.
 */
public class JettyClient implements IPostClient {

    protected HttpClient client;
    protected Request request;
    protected ContentResponse response;

    @Override
    public void initAndConnectClient(String remoteUrl, boolean useHttps,
            String keystoreFilename, String keystorePassword) throws HttpApiImplException {
        try {
            if (useHttps) {
                remoteUrl = remoteUrl.replaceAll("http://", "https://");
                SSLContext sslContext = SSLHelper.createSSLContext(keystoreFilename, keystorePassword);
                SslContextFactory sslFactory = new SslContextFactory();
                sslFactory.setSslContext(sslContext);
                client = new HttpClient(sslFactory);
            } else {
                remoteUrl = remoteUrl.replaceAll("https://", "http://");
                client = new HttpClient();
            }
            client.start();
            request = client.POST(remoteUrl);
        } catch (Exception ex) {
            throw new HttpApiImplException("JettyClient: Exception at initAndConnectClient()", ex);
        }
    }

    @Override
    public void setRequestReferer(String referer) {
        setRequestHeader("Referer", referer);
    }

    @Override
    public void setRequestHeader(String headerName, String headerValue) {
        if (headerValue.equals("")) {
            headerValue = EMPTY_STRING_PLACEHOLDER;
        }
        request.header(headerName, headerValue);
    }

    @Override
    public void writeFullRequestBody(byte[] data) throws HttpApiImplException {
        request.header("Content-Length", String.valueOf(data.length)).content(new BytesContentProvider(data));
    }

    @Override
    public void sendRequest() throws HttpApiImplException {
        try {
            response = request.send();
        } catch (InterruptedException ex) {
            throw new HttpApiImplException("JettyClient: InterruptedException at sendRequest()", ex);
        } catch (TimeoutException ex) {
            throw new HttpApiImplException("JettyClient: TimeoutException at sendRequest()", ex);
        } catch (ExecutionException ex) {
            throw new HttpApiImplException("JettyClient: ExecutionException at sendRequest()", ex);
        }
    }

    @Override
    public int getStatusCode() throws HttpApiImplException {
        return response.getStatus();
    }

    @Override
    public String getResponseReferer() {
        return getResponseHeader("Referer");
    }

    @Override
    public String getResponseHeader(String headerName) {
        String headerValue = response.getHeaders().get(headerName);
        if (headerValue.equals(EMPTY_STRING_PLACEHOLDER)) {
            headerValue = "";
        }
        return headerValue;
    }

    @Override
    public byte[] readFullResponseBody() throws HttpApiImplException {
        //int packetSize = Integer.parseInt(response.getHeaders().get("Content-Length"));
        byte[] data = response.getContent();
        return data;
    }

    @Override
    public void shutDown() throws HttpApiImplException {
        try {
            client.stop();
        } catch (Exception ex) {
            throw new HttpApiImplException("JettyClient: Exception at shutDown()", ex);
        }
    }
}
