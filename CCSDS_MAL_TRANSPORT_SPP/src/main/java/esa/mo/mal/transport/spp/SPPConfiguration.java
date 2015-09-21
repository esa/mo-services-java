/* ----------------------------------------------------------------------------
 * Copyright (C) 2015      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO SPP Transport Framework
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
package esa.mo.mal.transport.spp;

/**
 * Small class that holds the encoding configuration for out going messages.
 */
public class SPPConfiguration
{
  private int flags = 0x0;
  private boolean srcSubId;
  private boolean dstSubId;
  private boolean priority;
  private boolean timestamp;
  private boolean network;
  private boolean session;
  private boolean domain;
  private boolean auth;

  public SPPConfiguration(boolean hasSrcSubId,
          boolean hasDstSubId,
          boolean hasPriority,
          boolean hasTimestamp,
          boolean hasNetwork,
          boolean hasSession,
          boolean hasDomain,
          boolean hasAuth)
  {
    flags = hasSrcSubId ? (flags | 0x80) : flags;
    flags = hasDstSubId ? (flags | 0x40) : flags;
    flags = hasPriority ? (flags | 0x20) : flags;
    flags = hasTimestamp ? (flags | 0x10) : flags;
    flags = hasNetwork ? (flags | 0x08) : flags;
    flags = hasSession ? (flags | 0x04) : flags;
    flags = hasDomain ? (flags | 0x02) : flags;
    flags = hasAuth ? (flags | 0x01) : flags;

    srcSubId = hasSrcSubId;
    dstSubId = hasDstSubId;
    priority = hasPriority;
    timestamp = hasTimestamp;
    network = hasNetwork;
    session = hasSession;
    domain = hasDomain;
    auth = hasAuth;
  }

  public int getFlags()
  {
    return flags;
  }

  public boolean isSrcSubId()
  {
    return srcSubId;
  }

  public boolean isDstSubId()
  {
    return dstSubId;
  }

  public boolean isPriority()
  {
    return priority;
  }

  public boolean isTimestamp()
  {
    return timestamp;
  }

  public boolean isNetwork()
  {
    return network;
  }

  public boolean isSession()
  {
    return session;
  }

  public boolean isDomain()
  {
    return domain;
  }

  public boolean isAuth()
  {
    return auth;
  }
}
