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
package esa.mo.mal.transport.http.connection;

import esa.mo.mal.transport.http.api.IPostClient;
import esa.mo.mal.transport.http.util.HttpApiImplException;
import esa.mo.mal.transport.http.util.SSLHelper;
import esa.mo.mal.transport.http.util.UriHelper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import static esa.mo.mal.transport.http.HTTPTransport.RLOGGER;

/**
 * An implementation of the AbstractPostClient interface based on java.net.HttpURLConnection.
 * 
 * Logging property to see the HTTP messages being sent:
 * sun.net.www.protocol.http.HttpURLConnection.level=ALL
 */
public class JdkClient implements IPostClient {

  protected HttpURLConnection connection;

  private String[] asciiHeaders = new String[] { "X-MAL-From", "X-MAL-To", "Host", "request-target" };

  @Override
  public void initAndConnectClient(String remoteUrl, boolean useHttps, String keystoreFilename, String keystorePassword)
      throws HttpApiImplException {
    try {
      if (useHttps) {
        remoteUrl = remoteUrl.replaceAll("http://", "https://");
        URL url = new URL(remoteUrl);
        HttpsURLConnection httpsConnection = (HttpsURLConnection) url.openConnection();
        SSLContext sslContext = SSLHelper.createSSLContext(keystoreFilename, keystorePassword);
        httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
        connection = httpsConnection;
      } else {
        remoteUrl = remoteUrl.replaceAll("https://", "http://");
        URL url = new URL(remoteUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        connection = httpConnection;
      }
      connection.setRequestMethod("POST");
      connection.setDoOutput(true);
    } catch (MalformedURLException ex) {
      throw new HttpApiImplException("JdkClient: MalformedURLException at initAndConnectClient()", ex);
    } catch (IOException ex) {
      throw new HttpApiImplException("JdkClient: IOException at initAndConnectClient()", ex);
    }
  }

  @Override
  public void setRequestReferer(String referer) { // US-ASCII encoding

    setRequestHeader("X-MAL-From", referer);
  }

  @Override
  public void setRequestHeader(String headerName, String headerValue) {
    if (headerValue == null || headerValue.equals(""))
      headerValue = EMPTY_STRING_PLACEHOLDER;

    if (Arrays.asList(asciiHeaders).contains(headerName)) {
      headerValue = UriHelper.uriToAscii(headerValue);
    }
    connection.setRequestProperty(headerName, headerValue);
  }

  @Override
  public void writeFullRequestBody(byte[] data) throws HttpApiImplException {
    connection.setRequestProperty("Content-Length", Integer.toString(data.length));
    try {
      DataOutputStream os = new DataOutputStream(connection.getOutputStream());
      os.write(data);
      os.flush();
    } catch (IOException ex) {
      RLOGGER.severe(ex.getMessage());
      throw new HttpApiImplException("JdkClient: IOException at writeFullRequestBody()", ex);
    }
  }

  @Override
  public void sendRequest() throws HttpApiImplException {
    // do nothing
  }

  @Override
  public int getStatusCode() throws HttpApiImplException {
    try {
      return connection.getResponseCode();
    } catch (IOException ex) {
      throw new HttpApiImplException("JdkClient: IOException at getStatusCode()", ex);
    }
  }

  @Override
  public String getResponseReferer() {
    return getResponseHeader("X-MAL-From");
  }

  @Override
  public String getResponseHeader(String headerName) {
    String headerValue = connection.getHeaderField(headerName);
    if (headerValue == null || headerValue.equals(EMPTY_STRING_PLACEHOLDER))
      headerValue = "";

    if (Arrays.asList(asciiHeaders).contains(headerName)) {
      headerValue = UriHelper.uriToUtf8(headerValue);
    }
    return headerValue;
  }

  @Override
  public byte[] readFullResponseBody() throws HttpApiImplException {
    try {
      int statusCode = connection.getResponseCode();
      int packetSize = connection.getContentLength();
      if (packetSize < 0) {
        packetSize = 0;
      }
      if (statusCode < 300) {
        DataInputStream is = new DataInputStream(connection.getInputStream());
        byte[] data = new byte[packetSize];
        is.readFully(data);
        return data;
      } else {
        return new byte[0];
      }
    } catch (IOException ex) {
      throw new HttpApiImplException("JdkClient: IOException at readFullResponseBody()", ex);
    }
  }

  @Override
  public void shutDown() throws HttpApiImplException {
    connection.disconnect();
  }

  @Override
  public String toString() {

    return "headers: " + (this.connection != null && !this.connection.getHeaderFields().isEmpty()
        ? this.connection.getHeaderFields().toString()
        : "");
  }
}
