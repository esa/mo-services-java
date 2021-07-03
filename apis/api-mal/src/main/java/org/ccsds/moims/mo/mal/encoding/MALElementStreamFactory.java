/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Java API
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
package org.ccsds.moims.mo.mal.encoding;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.structures.Blob;

/**
 * The MALElementStreamFactory class creates and configures
 * MALElementInputStream instances and MALElementOutputStream instances.
 */
public abstract class MALElementStreamFactory {

    /**
     * The system property that resolves the specific MALElementStreamFactory
     * class name.
     */
    public static final String FACTORY_PROP_NAME_PREFIX = "org.ccsds.moims.mo.mal.encoding.protocol";
    private static final Map<String, Class> _FACTORY_MAP = new HashMap<>();

    /**
     * The method allows an implementation to register the class of a specific
     * MALElementStreamFactory. NOTE – This method may be useful in environments
     * where several class loaders are involved (e.g., OSGi platforms).
     *
     * @param factoryClass Class extending MALElementStreamFactory
     * @throws IllegalArgumentException if the argument does not extend
     * MALElementStreamFactory
     */
    public static void registerFactoryClass(final java.lang.Class factoryClass)
            throws IllegalArgumentException {
        if (!MALElementStreamFactory.class.isAssignableFrom(factoryClass)) {
            throw new IllegalArgumentException("Not compliant: " + factoryClass.getName());
        }

        _FACTORY_MAP.put(factoryClass.getName(), factoryClass);
    }

    /**
     * The method allows an implementation to deregister the class of a specific
     * MALElementStreamFactory.
     *
     * @param factoryClass The class to deregister
     * @throws IllegalArgumentException if the argument does not extend
     * MALElementStreamFactory
     */
    public static void deregisterFactoryClass(final java.lang.Class factoryClass) 
            throws IllegalArgumentException {
        if (null != factoryClass) {
            _FACTORY_MAP.remove(factoryClass.getName());
        }
    }

    /**
     * The method returns a MALElementStreamFactory instance.
     *
     * @param protocol Name of the protocol to be handled by the instantiated
     * MALElementStreamFactory
     * @param qosProperties Configuration properties
     * @return The new factory.
     * @throws MALException If an error detected during instantiation.
     */
    public static MALElementStreamFactory newFactory(final String protocol, 
            final Map qosProperties) throws MALException {
        final String propName = FACTORY_PROP_NAME_PREFIX + '.' + protocol;
        final String className = System.getProperty(propName);
        if (null != className) {
            try {
                Class factoryClass;

                if (_FACTORY_MAP.containsKey(className)) {
                    factoryClass = (Class) _FACTORY_MAP.get(className);
                } else {
                    factoryClass = Class.forName(className);
                    registerFactoryClass(factoryClass);
                    Logger.getLogger(MALElementStreamFactory.class.getName()).log(
                            Level.INFO, 
                            "New encoding factory registered with classname: {0}", 
                            className);
                }

                final MALElementStreamFactory factory = 
                        (MALElementStreamFactory) factoryClass.newInstance();
                factory.init(protocol, qosProperties);

                return factory;
            } catch (ClassNotFoundException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (InstantiationException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (IllegalAccessException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            }
        } else {
            throw new MALException("Unknown encoding factory for protocol: " + protocol);
        }
    }

    /**
     * The method enables the specific implementation class to initialize the
     * encoding module.
     *
     * @param protocol Name of the protocol passed through the instantiation
     * method
     * @param properties Properties passed through the instantiation method, may
     * be null
     * @throws java.lang.IllegalArgumentException If the protocol string is
     * null.
     * @throws MALException If an internal error occurs
     */
    protected abstract void init(String protocol, Map properties)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * Creates a MALElementInputStream using a java.io.InputStream as the data
     * source.
     *
     * @param is The data source.
     * @return The new MALElementInputStream.
     * @throws java.lang.IllegalArgumentException if the data source is null.
     * @throws MALException If a MALElementInputStream cannot be created
     */
    public abstract MALElementInputStream createInputStream(java.io.InputStream is)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * Creates a MALElementInputStream using a byte array as the data source.
     *
     * @param bytes Bytes to be decoded
     * @param offset Index of the first byte to decode
     * @return The new MALElementInputStream.
     * @throws java.lang.IllegalArgumentException if the data source is null.
     * @throws MALException If a MALElementInputStream cannot be created
     */
    public abstract MALElementInputStream createInputStream(byte[] bytes, int offset)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * Creates a MALElementOutputStream using a java.io.OutputStream as the data
     * sink.
     *
     * @param os The data sink.
     * @return The new MALElementOutputStream.
     * @throws java.lang.IllegalArgumentException if the data sink is null.
     * @throws MALException If a MALElementOutputStream cannot be created
     */
    public abstract MALElementOutputStream createOutputStream(java.io.OutputStream os)
            throws java.lang.IllegalArgumentException, MALException;

    /**
     * The method encodes an element array and returns the encoding result as a
     * byte array.
     *
     * @param elements Elements to encode
     * @param ctx MALEncodingContext to be used in order to encode the elements
     * @return The encoded elements as a byte array.
     * @throws java.lang.IllegalArgumentException if the arguments are null.
     * @throws MALException If an encoding error occurs
     */
    public abstract Blob encode(Object[] elements, MALEncodingContext ctx)
            throws java.lang.IllegalArgumentException, MALException;
}
