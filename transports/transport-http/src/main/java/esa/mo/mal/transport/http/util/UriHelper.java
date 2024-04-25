package esa.mo.mal.transport.http.util;

import java.io.UnsupportedEncodingException;

public class UriHelper {

  public static String uriToAscii(String uri) {

    String encodedUri = "";
    try {
      encodedUri = java.net.URLEncoder.encode(uri, "US-ASCII");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return encodedUri;
  }

  public static String uriToUtf8(String uri) {

    String decodedUri = "";
    try {
      decodedUri = java.net.URLDecoder.decode(uri, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return decodedUri;
  }

}
