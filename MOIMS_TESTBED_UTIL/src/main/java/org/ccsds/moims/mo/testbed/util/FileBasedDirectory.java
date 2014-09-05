/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Test bed utilities
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
package org.ccsds.moims.mo.testbed.util;

import java.io.IOException;
import java.util.Properties;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 *
 */
public abstract class FileBasedDirectory
{
  public static final String FILENAME_EXT = ".uri";
  public static final String URI_PROPERTY = "uri";
  public static final String BROKER_PROPERTY = "broker";
  public static final String AUTH_FILENAME_EXT = ".auth";
  public static final String AUTHENTICATION_ID_PROPERTY = "authenticationId";

  public static final class URIpair
  {
    public URI uri = null;
    public URI broker = null;
  }

  public static boolean storeURI(String name, URI uri, URI broker)
  {
    java.util.Properties prop = new Properties();

    String uriValue = "";
    String brokerValue = "";

    if (null != uri)
    {
      uriValue = uri.getValue();
    }

    if (null != broker)
    {
      brokerValue = broker.getValue();
    }

    prop.setProperty(URI_PROPERTY, uriValue);
    prop.setProperty(BROKER_PROPERTY, brokerValue);

    try
    {
      prop.store(new java.io.FileOutputStream(name + FILENAME_EXT), "Created: " + new java.util.Date().toString());
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      return false;
    }

    return true;
  }

  public static URIpair loadURIs(String name)
  {
    java.util.Properties prop = new Properties();

    try
    {
      prop.load(new java.io.FileInputStream(name + FILENAME_EXT));
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      return null;
    }

    URIpair pair = new URIpair();

    String uriStr = prop.getProperty(URI_PROPERTY);
    String brokerStr = prop.getProperty(BROKER_PROPERTY);

    if (null != uriStr)
    {
      pair.uri = new URI(uriStr);
    }

    if (null != brokerStr)
    {
      pair.broker = new URI(brokerStr);
    }

    return pair;
  }

  public static boolean storeSharedBrokerAuthenticationId(Blob authId) throws MALException
  {
    return storeBrokerAuthenticationId(authId, Configuration.SHARED_BROKER_NAME);
  }
  
  public static boolean storePrivateBrokerAuthenticationId(Blob authId) throws MALException
  {
    return storeBrokerAuthenticationId(authId, Configuration.PRIVATE_BROKER_NAME);
  }
  
  public static boolean storeBrokerAuthenticationId(Blob authId, String brokerName) throws MALException
  {
    java.util.Properties prop = new Properties();

    prop.setProperty(AUTHENTICATION_ID_PROPERTY, byteArrayToHexString(authId.getValue()));

    try
    {
      prop.store(new java.io.FileOutputStream(brokerName + AUTH_FILENAME_EXT),
              "Created: " + new java.util.Date().toString());
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      return false;
    }

    return true;
  }
  
  public static Blob loadSharedBrokerAuthenticationId()
  {
    return loadBrokerAuthenticationId(Configuration.SHARED_BROKER_NAME);
  }
  
  public static Blob loadPrivateBrokerAuthenticationId()
  {
    return loadBrokerAuthenticationId(Configuration.PRIVATE_BROKER_NAME);
  }

  public static Blob loadBrokerAuthenticationId(String brokerName)
  {
    java.util.Properties prop = new Properties();

    try
    {
      prop.load(new java.io.FileInputStream(brokerName + AUTH_FILENAME_EXT));
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
      return null;
    }

    Blob authenticationId;

    String authenticationIdStr = prop.getProperty(AUTHENTICATION_ID_PROPERTY);

    if (null != authenticationIdStr)
    {
      byte[] buf = hexStringToByteArray(authenticationIdStr);
      authenticationId = new Blob(buf);
    } else {
      authenticationId = new Blob(new byte[0]);
    }

    return authenticationId;
  }

  public static String byteArrayToHexString(byte[] data)
  {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < data.length; i++)
    {
      String hex = Integer.toHexString(0xFF & data[i]);
      if (hex.length() == 1)
      {
        // could use a for loop, but we're only dealing with a single byte
        hexString.append('0');
      }
      hexString.append(hex);
    }

    return hexString.toString();
  }

  public static byte[] hexStringToByteArray(String s)
  {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2)
    {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
