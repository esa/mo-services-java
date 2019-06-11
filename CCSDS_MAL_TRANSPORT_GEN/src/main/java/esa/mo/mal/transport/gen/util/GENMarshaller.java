/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Generic Transport Framework
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
package esa.mo.mal.transport.gen.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.encoding.MALElementInputStream;
import org.ccsds.moims.mo.mal.encoding.MALEncodingContext;
import org.ccsds.moims.mo.mal.structures.Union;

/**
 * This class allows marshalling and unmarshalling objects.
 */
public abstract class GENMarshaller
{

  private GENMarshaller()
  {
    // private contructor as not a real class but a place for static methods
  }

  /**
   * Marshals the object.
   *
   * @param ssf The string short form.
   * @param o   The object to be marshalled.
   * @return The marshalled object.
   * @throws MALException if any error detected.
   */
  public static StringWriter marshall(final String ssf, final Object o) throws MALException
  {
    final StringWriter ow = new StringWriter();

    try {
      final String schemaURN = ssf.substring(0, ssf.lastIndexOf(':'));
      final String schemaEle = ssf.substring(ssf.lastIndexOf(':') + 1);

      // create the marshaller
      final JAXBContext jc = JAXBContext.newInstance(o.getClass().getPackage().getName());
      final Marshaller marshaller = jc.createMarshaller();

      // encode the XML into a string
      marshaller.marshal(new JAXBElement(new QName(schemaURN, schemaEle), o.getClass(), null, o), ow);
    } catch (JAXBException ex) {
      throw new MALException("XML Encoding error", ex);
    }

    return ow;
  }

  /**
   * Unmarshals the object.
   *
   * @param shortForm The string short form.
   * @param ctx       The encoding context to use.
   * @param lenc      The decoder to be used.
   * @return The unmarshalled object.
   * @throws MALException if any error detected.
   */
  public static Object unmarshall(final String shortForm, MALEncodingContext ctx,
      MALElementInputStream lenc) throws MALException
  {
    try {
      final String schemaURN = shortForm.substring(0, shortForm.lastIndexOf(':'));
      final String packageName = (String) ctx.getEndpointQosProperties().get(schemaURN);

      final JAXBContext jc = JAXBContext.newInstance(packageName);
      final Unmarshaller unmarshaller = jc.createUnmarshaller();

      final String srcString = ((Union) lenc.readElement(new Union(""), null)).getStringValue();
      final StringReader ir = new StringReader(srcString);
      final JAXBElement rootElement = (JAXBElement) unmarshaller.unmarshal(ir);
      return rootElement.getValue();
    } catch (JAXBException ex) {
      throw new MALException("XML Decoding error", ex);
    }
  }
}
