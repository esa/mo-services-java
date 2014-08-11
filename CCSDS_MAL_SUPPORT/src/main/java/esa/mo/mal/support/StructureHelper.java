/* ----------------------------------------------------------------------------
 * Copyright (C) 2014      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Support library
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
package esa.mo.mal.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.URI;

/**
 * Helper class that contains useful utility functions. It also stores a list of loaded property files so that the same
 * property file is not loaded multiple times.
 *
 */
public abstract class StructureHelper
{
  private static final Set LOADED_PROPERTIES = new TreeSet();

  /**
   * Clears the list of loaded property files.
   */
  public static void clearLoadedPropertiesList()
  {
    LOADED_PROPERTIES.clear();
  }

  /**
   * Loads in a property file and optionally searches for a contained property that contains the next file to load.
   * loaded properties are then stored in the system properties.
   *
   * @param chainProperty The property name that contains the name of the file to load.
   * @param defaultFile The name of the property file to load if the chain property has not been set.
   */
  public static void loadPropertiesIntoSystem(final String chainProperty, final String defaultFile)
  {
    final Properties sysProps = System.getProperties();

    File file = new File(System.getProperty(chainProperty, defaultFile));

    if (file.exists())
    {
      try
      {
        sysProps.putAll(StructureHelper.loadProperties(file.toURI().toURL(), chainProperty));
      }
      catch (MalformedURLException ex)
      {
        Logger.getLogger("esa.mo.mal").log(Level.WARNING,
                "Failed to load properties file {0} {1}", new Object[]
                {
                  file, ex
                });
      }
    }

    System.setProperties(sysProps);
  }

  /**
   * Loads in a property file and optionally searches for a contained property that contains the next file to load.
   *
   * @param configFile The name of the property file to load. May be null, in which case nothing is loaded.
   * @param chainProperty The property name that contains the name of the next file to load.
   * @return The loaded properties or an empty list if no file loaded.
   */
  public static Properties loadProperties(final String configFile, final String chainProperty)
  {
    Properties topProps = new Properties();

    if (null != configFile)
    {
      topProps = loadProperties(ClassLoader.getSystemClassLoader().getResource(configFile), chainProperty);
    }

    return topProps;
  }

  /**
   * Loads in a property file and optionally searches for a contained property that contains the next file to load.
   *
   * @param url The URL of the property file to load. May be null, in which case nothing is loaded.
   * @param chainProperty The property name that contains the name of the next file to load.
   * @return The loaded properties or an empty list if no file loaded.
   */
  public static Properties loadProperties(final java.net.URL url, final String chainProperty)
  {
    final Properties topProps = new Properties();

    if ((null != url) && (!LOADED_PROPERTIES.contains(url.toString())))
    {
      LOADED_PROPERTIES.add(url.toString());

      try
      {
        final Properties myProps = new Properties();
        myProps.load(url.openStream());

        final Properties subProps = loadProperties(myProps.getProperty(chainProperty), chainProperty);

        Logger.getLogger("esa.mo.mal").log(Level.INFO,
                "Loading properties from {0}", url.toString());
        topProps.putAll(subProps);
        topProps.putAll(myProps);
      }
      catch (IOException ex)
      {
        Logger.getLogger("esa.mo.mal").log(Level.WARNING,
                "Failed to load properties file {0} {1}", new Object[]
                {
                  url, ex
                });
      }
    }

    return topProps;
  }

  public static void storeURIs(String filename, URI mainUri, URI brokerUri)
  {
    if ((null != filename) && (0 < filename.length()))
    {
      try
      {
        File file = new File(filename);
        final FileOutputStream fos = new FileOutputStream(file);
        final OutputStreamWriter osw = new OutputStreamWriter(fos);
        final BufferedWriter wrt = new BufferedWriter(osw);

        if ((null != mainUri) && (null != mainUri.getValue()))
        {
          wrt.append("uri=" + mainUri);
          wrt.newLine();
        }
        if ((null != brokerUri) && (null != brokerUri.getValue()))
        {
          wrt.append("broker=" + brokerUri);
          wrt.newLine();
        }
        wrt.close();
      }
      catch (IOException ex)
      {
        Logger.getLogger("esa.mo.mal").log(Level.WARNING, "Unable to write URI information to properties file {0}", ex);
      }
    }
  }
}
