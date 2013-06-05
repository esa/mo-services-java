/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java Implementation
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
package esa.mo.mal.impl.util;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;

/**
 * Helper class that contains useful utility functions. It also stores a list of loaded property files so that the same
 * property file is not loaded multiple times.
 *
 */
public abstract class StructureHelper
{
  private static final Set LOADED_PROPERTIES = new TreeSet();

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
      final java.net.URL url = ClassLoader.getSystemClassLoader().getResource(configFile);
      if ((null != url) && (!LOADED_PROPERTIES.contains(url.toString())))
      {
        LOADED_PROPERTIES.add(url.toString());

        try
        {
          final Properties myProps = new Properties();
          myProps.load(url.openStream());

          final Properties subProps = loadProperties(myProps.getProperty(chainProperty), chainProperty);

          Logger.getLogger("org.ccsds.moims.mo.mal.impl.util").log(Level.INFO,
                  "Loading properties from {0}", url.toString());
          topProps.putAll(subProps);
          topProps.putAll(myProps);
        }
        catch (Exception ex)
        {
          Logger.getLogger("org.ccsds.moims.mo.mal.impl.util").log(Level.WARNING,
                  "Failed to load properties file {0} {1}", new Object[]
          {
            url, ex
          });
        }
      }
    }

    return topProps;
  }

  /**
   * Converts a identifier list version of a domain name to a single, dot delimited, String.
   *
   * @param domain The list of identifiers to concatenate.
   * @return The dot delimited version of the domain name.
   */
  public static String domainToString(final IdentifierList domain)
  {
    String retVal = null;

    if (null != domain)
    {
      final StringBuilder buf = new StringBuilder();
      int i = 0;
      final int e = domain.size();
      while (i < e)
      {
        if (0 < i)
        {
          buf.append('.');
        }

        buf.append((Identifier) domain.get(i));

        ++i;
      }

      retVal = buf.toString();
    }

    return retVal;
  }

  /**
   * Determines if one domain is a sub-domain, or the same domain, of another. For example, a.b.c is a sub-domain of a.b
   *
   * @param srcDomain The main domain.
   * @param testDomain The sub-domain.
   * @return True if tesDomain is a sub-domain of srcDomain, else false.
   */
  public static boolean isSubDomainOf(final IdentifierList srcDomain, final IdentifierList testDomain)
  {
    if ((null != srcDomain) && (null != testDomain))
    {
      if (srcDomain.size() <= testDomain.size())
      {
        int i = 0;
        final int e = srcDomain.size();
        while (i < e)
        {
          final Identifier sId = srcDomain.get(i);
          final Identifier tId = testDomain.get(i);

          if ((sId == null) ? (tId != null) : !sId.equals(tId))
          {
            return false;
          }

          ++i;
        }

        return true;
      }
    }
    else
    {
      if ((null == srcDomain) && (null == testDomain))
      {
        return true;
      }
    }

    return false;
  }
}
