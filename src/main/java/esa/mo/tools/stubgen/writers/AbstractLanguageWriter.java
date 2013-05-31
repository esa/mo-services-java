/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Service Stub Generator
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an “as is” basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.tools.stubgen.writers;

import esa.mo.tools.stubgen.xsd.AreaType;
import esa.mo.tools.stubgen.xsd.ServiceType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Basic implementation of the language writer that adds a few extra support methods.
 */
public abstract class AbstractLanguageWriter extends AbstractWriter implements LanguageWriter
{
  /**
   * Constructor.
   *
   * @param lineSeparator The line separator to use.
   */
  public AbstractLanguageWriter(String lineSeparator)
  {
    super(lineSeparator);
  }

  /**
   * Constructor.
   */
  public AbstractLanguageWriter()
  {
    super();
  }

  @Override
  public void addMultilineComment(String comment) throws IOException
  {
    addMultilineComment(1, false, comment, false);
  }

  @Override
  public void addMultilineComment(int tabCount, boolean preBlankLine, String comment, boolean postBlankLine) throws IOException
  {
    addMultilineComment(tabCount, preBlankLine, normaliseComment(new LinkedList<String>(), comment), postBlankLine);
  }

  @Override
  public void addPackageStatement(AreaType area, ServiceType service, String packageName) throws IOException
  {
    String cls = area.getName().toLowerCase() + ".";

    if (null != service)
    {
      cls += service.getName().toLowerCase() + ".";
    }

    addPackageStatement(cls + packageName);
  }

  /**
   * Processes a list of comments making sure they contain a full stop at the end.
   *
   * @param rv The list to return.
   * @param comments List of comments to check.
   * @return the processed list.
   */
  public static List<String> normaliseComments(List<String> rv, List<String> comments)
  {
    if (null != comments)
    {
      for (String comment : comments)
      {
        normaliseComment(rv, comment);
      }
    }

    return rv;
  }

  /**
   * Processes a comment making sure it contain a full stop at the end.
   *
   * @param rv The list to return.
   * @param comment The comment to check.
   * @return the supplied list.
   */
  public static List<String> normaliseComment(List<String> rv, String comment)
  {
    if ((null != rv) && (null != comment) && (0 < comment.length()))
    {
      if (!comment.endsWith("."))
      {
        comment += ".";
      }

      rv.add(comment);
    }

    return rv;
  }
}
