/*******************************************************************************
 * Copyright or © or Copr. CNES
 *
 * This software is a computer program whose purpose is to provide a 
 * framework for the CCSDS Mission Operations services.
 *
 * This software is governed by the CeCILL-C license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-C
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 *******************************************************************************/
package org.ccsds.moims.mo.com.test.util;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.URI;

public class COMParseHelper
{
  public static final String INVALID_ERROR = "INVALID";
  public static final String READONLY_ERROR = "READONLY";

  public static final String NULL = "NULL";

  public static Integer parseInt(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return Integer.parseInt(toParse);
    }
  }

  public static UInteger parseUInt(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return new UInteger(Integer.parseInt(toParse));
    }
  }

  public static Long parseLong(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return Long.parseLong(toParse);
    }
  }

  public static FineTime parseFineTime(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return new FineTime(Long.parseLong(toParse));
    }
  }

  public static Boolean parseBoolean(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return Boolean.parseBoolean(toParse);
    }
  }

  public static String parseString(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return toParse;
    }
  }

  public static Identifier parseIdentifier(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return new Identifier(toParse);
    }
  }

  public static URI parseURI(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return new URI(toParse);
    }
  }

  public static Blob parseBlob(String toParse)
  {
    if (toParse.equals(NULL))
    {
      return null;
    }
    else
    {
      return new Blob(toParse.getBytes());
    }
  }
}
