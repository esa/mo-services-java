package org.ccsds.moims.smc.mal.impl.broker;

import java.util.LinkedList;
import java.util.List;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifier;
import org.ccsds.moims.smc.mal.api.structures.MALIdentifierList;

public final class MALSubscriptionKey implements Comparable
{
  public static final String ALL_ID = "*";
  private final List<String> keys = new LinkedList<String>();

  public MALSubscriptionKey(MALIdentifierList lst)
  {
    super();
    for (int idx = 0; idx < lst.size(); idx++)
    {
      MALIdentifier id = (MALIdentifier) lst.get(idx);
      String val = null;
      if (null != id)
      {
        val = id.getIdentifierValue();
      }
      keys.add(val);
    }
  }

  public int compareTo(Object o)
  {
    MALSubscriptionKey rhs = (MALSubscriptionKey) o;
    for (int i = 0; (i < keys.size()) && (i < rhs.keys.size()); i++)
    {
      String myKeyPart = keys.get(i);
      String theirKeyPart = rhs.keys.get(i);

      if ((null == myKeyPart) || (null == theirKeyPart))
      {
        if ((null != myKeyPart) || (null != theirKeyPart))
        {
          if (null == myKeyPart)
          {
            return -1;
          }

          return 1;
        }
      }
      else
      {
        if (!myKeyPart.equals(theirKeyPart))
        {
          return myKeyPart.compareTo(theirKeyPart);
        }
      }
    }

    return keys.size() - rhs.keys.size();
  }

  public boolean matches(MALSubscriptionKey rhs)
  {
    boolean matched = true;
    for (int i = 0; (i < keys.size()) && (i < rhs.keys.size()); ++i)
    {
      String keyPart = keys.get(i);
      String rhsPart = rhs.keys.get(i);
      if ((null == keyPart) || (null == rhsPart))
      {
        if ((null == keyPart) && (null == rhsPart))
        {
          matched = false;
          break;
        }
      }
      else
      {
        if (ALL_ID.equals(keyPart) || ALL_ID.equals(rhsPart))
        {
          break;
        }
        else
        {
          if (!keyPart.equals(rhsPart))
          {
            matched = false;
            break;
          }
        }
      }
    }
    return matched;
  }
}
