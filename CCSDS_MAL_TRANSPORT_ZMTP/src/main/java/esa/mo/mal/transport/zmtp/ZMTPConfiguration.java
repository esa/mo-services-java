/* ----------------------------------------------------------------------------
 * Copyright (C) 2017      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO ZMTP Transport Framework
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
package esa.mo.mal.transport.zmtp;

import java.util.Map;

/**
 * Small class that holds the mapping and QoS configuration properties for outgoing and incoming
 * messages.
 */
public class ZMTPConfiguration
{

  // Mapping Configuration Properties
  public static final String DEFAULT_AUTHENTICATION_ID
      = "org.ccsds.moims.mo.mal.transport.zmtp.authenticationid.default";
  public static final String DEFAULT_DOMAIN = "org.ccsds.moims.mo.mal.transport.zmtp.domain.default";
  public static final String DEFAULT_NETWORK_ZONE
      = "org.ccsds.moims.mo.mal.transport.zmtp.networkzone.default";
  public static final String DEFAULT_PRIORITY
      = "org.ccsds.moims.mo.mal.transport.zmtp.priority.default";
  public static final String DEFAULT_SESSION_NAME
      = "org.ccsds.moims.mo.mal.transport.zmtp.sessionname.default";

  // QoS properties
  public static final String AUTHENTICATION_ID_FLAG
      = "org.ccsds.moims.mo.mal.transport.zmtp.authenticationid.flag";
  public static final String DOMAIN_FLAG = "org.ccsds.moims.mo.mal.transport.zmtp.domain.flag";
  public static final String NETWORK_ZONE_FLAG
      = "org.ccsds.moims.mo.mal.transport.zmtp.networkzone.flag";
  public static final String PRIORITY_FLAG = "org.ccsds.moims.mo.mal.transport.zmtp.priority.flag";
  public static final String SESSION_NAME_FLAG
      = "org.ccsds.moims.mo.mal.transport.zmtp.sessionname.flag";
  public static final String TIMESTAMP_FLAG = "org.ccsds.moims.mo.mal.transport.zmtp.timestamp.flag";

  protected String defaultAuth;
  protected String defaultDomain;
  protected String defaultNetwork;
  protected int defaultPriority = 0;
  protected String defaultSessionName = "LIVE";

  protected boolean priorityFlag;
  protected boolean timestampFlag;
  protected boolean networkFlag;
  protected boolean sessionNameFlag;
  protected boolean domainFlag;
  protected boolean authFlag;
  protected short flags = 0x0;

  public ZMTPConfiguration()
  {
    this(false, false, false, false, false, false);
  }

  public ZMTPConfiguration(boolean hasPriority,
      boolean hasTimestamp,
      boolean hasNetwork,
      boolean hasSessionName,
      boolean hasDomain,
      boolean hasAuth)
  {
    this.priorityFlag = hasPriority;
    this.timestampFlag = hasTimestamp;
    this.networkFlag = hasNetwork;
    this.sessionNameFlag = hasSessionName;
    this.domainFlag = hasDomain;
    this.authFlag = hasAuth;
    updateFlags();
  }

  public ZMTPConfiguration(ZMTPConfiguration other, final Map properties)
  {
    defaultPriority = getIntegerProperty(properties, DEFAULT_PRIORITY, other.defaultPriority);
    defaultNetwork = getStringProperty(properties, DEFAULT_NETWORK_ZONE, other.defaultNetwork);
    defaultSessionName = getStringProperty(properties, DEFAULT_SESSION_NAME,
        other.defaultSessionName);
    defaultDomain = getStringProperty(properties, DEFAULT_DOMAIN, other.defaultDomain);
    defaultAuth = getStringProperty(properties, DEFAULT_AUTHENTICATION_ID, other.defaultAuth);

    priorityFlag = getBooleanProperty(properties, PRIORITY_FLAG, other.priorityFlag);
    timestampFlag = getBooleanProperty(properties, TIMESTAMP_FLAG, other.timestampFlag);
    networkFlag = getBooleanProperty(properties, NETWORK_ZONE_FLAG, other.networkFlag);
    sessionNameFlag = getBooleanProperty(properties, SESSION_NAME_FLAG, other.sessionNameFlag);
    domainFlag = getBooleanProperty(properties, DOMAIN_FLAG, other.domainFlag);
    authFlag = getBooleanProperty(properties, AUTHENTICATION_ID_FLAG, other.authFlag);
    updateFlags();
  }

  private Integer getIntegerProperty(final Map properties, final String propertyName,
      Integer existingValue)
  {
    if ((null != properties) && properties.containsKey(propertyName)) {
      return Integer.parseInt(properties.get(propertyName).toString());
    }
    return existingValue;
  }

  private String getStringProperty(final Map properties, final String propertyName,
      String existingValue)
  {
    if ((null != properties) && properties.containsKey(propertyName)) {
      return properties.get(propertyName).toString();
    }
    return existingValue;
  }

  private boolean getBooleanProperty(final Map properties, final String propertyName,
      boolean existingValue)
  {
    if ((null != properties) && properties.containsKey(propertyName)) {
      return Boolean.parseBoolean(properties.get(propertyName).toString());
    }
    return existingValue;
  }

  public short getFlags()
  {
    return flags;
  }

  private void updateFlags()
  {
    flags
        = calculateFlags(isPriorityFlag(), isTimestampFlag(), isNetworkFlag(), isSessionNameFlag(),
            isDomainFlag(), isAuthFlag());
  }

  private static short calculateFlags(boolean priority,
      boolean timestamp,
      boolean network,
      boolean session,
      boolean domain,
      boolean auth)
  {
    int flags = 0;
    flags = priority ? (flags | 0x20) : flags;
    flags = timestamp ? (flags | 0x10) : flags;
    flags = network ? (flags | 0x08) : flags;
    flags = session ? (flags | 0x04) : flags;
    flags = domain ? (flags | 0x02) : flags;
    return (short) (auth ? (flags | 0x01) : flags);
  }

  /**
   * @return the defaultAuth
   */
  public String getDefaultAuth()
  {
    return defaultAuth;
  }

  /**
   * @return the defaultDomain
   */
  public String getDefaultDomain()
  {
    return defaultDomain;
  }

  /**
   * @return the defaultNetwork
   */
  public String getDefaultNetwork()
  {
    return defaultNetwork;
  }

  /**
   * @return the defaultPriority
   */
  public int getDefaultPriority()
  {
    return defaultPriority;
  }

  /**
   * @return the defaultSessionName
   */
  public String getDefaultSessionName()
  {
    return defaultSessionName;
  }

  /**
   * @return the priorityFlag
   */
  public boolean isPriorityFlag()
  {
    return priorityFlag;
  }

  /**
   * @return the timestampFlag
   */
  public boolean isTimestampFlag()
  {
    return timestampFlag;
  }

  /**
   * @return the networkFlag
   */
  public boolean isNetworkFlag()
  {
    return networkFlag;
  }

  /**
   * @return the sessionNameFlag
   */
  public boolean isSessionNameFlag()
  {
    return sessionNameFlag;
  }

  /**
   * @return the domainFlag
   */
  public boolean isDomainFlag()
  {
    return domainFlag;
  }

  /**
   * @return the authFlag
   */
  public boolean isAuthFlag()
  {
    return authFlag;
  }

  /**
   * @param defaultAuth the defaultAuth to set
   */
  public void setDefaultAuth(String defaultAuth)
  {
    this.defaultAuth = defaultAuth;
  }

  /**
   * @param defaultDomain the defaultDomain to set
   */
  public void setDefaultDomain(String defaultDomain)
  {
    this.defaultDomain = defaultDomain;
  }

  /**
   * @param defaultNetwork the defaultNetwork to set
   */
  public void setDefaultNetwork(String defaultNetwork)
  {
    this.defaultNetwork = defaultNetwork;
  }

  /**
   * @param defaultPriority the defaultPriority to set
   */
  public void setDefaultPriority(int defaultPriority)
  {
    this.defaultPriority = defaultPriority;
  }

  /**
   * @param defaultSessionName the defaultSessionName to set
   */
  public void setDefaultSessionName(String defaultSessionName)
  {
    this.defaultSessionName = defaultSessionName;
  }

  /**
   * @param priorityFlag the priorityFlag to set
   */
  public void setPriorityFlag(boolean priorityFlag)
  {
    this.priorityFlag = priorityFlag;
    updateFlags();
  }

  /**
   * @param timestampFlag the timestampFlag to set
   */
  public void setTimestampFlag(boolean timestampFlag)
  {
    this.timestampFlag = timestampFlag;
    updateFlags();
  }

  /**
   * @param networkFlag the networkFlag to set
   */
  public void setNetworkFlag(boolean networkFlag)
  {
    this.networkFlag = networkFlag;
    updateFlags();
  }

  /**
   * @param sessionFlag the sessionNameFlag to set
   */
  public void setSessionNameFlag(boolean sessionFlag)
  {
    this.sessionNameFlag = sessionFlag;
    updateFlags();
  }

  /**
   * @param domainFlag the domainFlag to set
   */
  public void setDomainFlag(boolean domainFlag)
  {
    this.domainFlag = domainFlag;
    updateFlags();
  }

  /**
   * @param authFlag the authFlag to set
   */
  public void setAuthFlag(boolean authFlag)
  {
    this.authFlag = authFlag;
    updateFlags();
  }
}
