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
package org.ccsds.moims.mo.mal.transport;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALException;

/**
 * The MALTransportFactory class enables the MAL layer to instantiate and
 * configure a MALTransport.
 */
public abstract class MALTransportFactory {

    /**
     * The system property prefix that resolves the specific MALTransportFactory
     * class name for a protocol.
     */
    public static final String FACTORY_PROP_NAME_PREFIX = "org.ccsds.moims.mo.mal.transport.protocol";
    private static final Map<String, Class> FACTORIES = new HashMap<>();
    private final String protocol;

    /**
     * Constructor.
     *
     * @param protocol Name of the protocol to be handled by the instantiated
     * MALTransportFactory
     * @throws IllegalArgumentException If the parameter ‘protocol’ is NULL
     */
    public MALTransportFactory(final String protocol) throws IllegalArgumentException {
        this.protocol = protocol;
    }

    /**
     * The method allows an implementation to register the class of a specific
     * MALTransportFactory. NOTE – This method may be useful in environments
     * where several class loaders are involved (e.g., OSGi platforms).
     *
     * @param factoryClass Class extending MALTransportFactory
     * @throws IllegalArgumentException if the argument does not extend
     * MALTransportFactory
     */
    public static void registerFactoryClass(final Class factoryClass) throws IllegalArgumentException {
        if (!MALTransportFactory.class.isAssignableFrom(factoryClass)) {
            throw new IllegalArgumentException("Not compliant: " + factoryClass.getName());
        }

        FACTORIES.put(factoryClass.getName(), factoryClass);
    }

    /**
     * The method allows an implementation to deregister the class of a specific
     * MALTransportFactory.
     *
     * @param factoryClass The class to deregister
     * @throws IllegalArgumentException if the argument does not extend
     * MALTransportFactory
     */
    public static void deregisterFactoryClass(final java.lang.Class factoryClass)
            throws IllegalArgumentException {
        if (!MALTransportFactory.class.isAssignableFrom(factoryClass)) {
            throw new IllegalArgumentException("Not compliant: " + factoryClass.getName());
        }

        if (factoryClass != null) {
            FACTORIES.remove(factoryClass.getName());
        }
    }

    /**
     * The method creates a factory instance from a protocol name. The method
     * resolves the specific MALTransportFactory class name through the system
     * property org.ccsds.moims.mo.mal.transport.protocol.[protocol name] NOTE –
     * Each of those protocol properties is assigned with the class name of the
     * transport factory. It should be noted that two different protocol
     * properties can share the same transport factory class name.
     *
     * @param protocol Name of the protocol to be handled by the instantiated
     * MALTransportFactory
     * @return The instance.
     * @throws IllegalArgumentException If the parameter ‘protocol’ is NULL
     * @throws MALException If no MALTransportFactory can be returned
     */
    public static MALTransportFactory newFactory(final String protocol)
            throws IllegalArgumentException, MALException {
        final String className = System.getProperty(FACTORY_PROP_NAME_PREFIX + '.' + protocol);

        if (null != className) {
            try {
                Class factoryClass;

                if (FACTORIES.containsKey(className)) {
                    factoryClass = (Class) FACTORIES.get(className);
                } else {
                    factoryClass = Class.forName(className);
                    registerFactoryClass(factoryClass);
                    Logger.getLogger(MALTransportFactory.class.getName()).log(
                            Level.INFO,
                            "New transport factory registered with classname: {0}",
                            className);
                }

                return (MALTransportFactory) factoryClass.getConstructor(
                        new Class[]{String.class}).newInstance(new Object[]{protocol});
            } catch (ClassNotFoundException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (InstantiationException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (IllegalAccessException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (NoSuchMethodException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            } catch (InvocationTargetException exc) {
                throw new MALException(exc.getLocalizedMessage(), exc);
            }
        } else {
            throw new MALException(
                    "Unknown transport factory for protocol: " + protocol);
        }
    }

    /**
     * Returns the protocol passed into the constructor.
     *
     * @return The protocol string.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * The method to instantiate a MALTransport.
     *
     * @param properties Configuration properties
     * @return The transport instance.
     * @throws MALException If no MALTransport can be returned
     */
    public abstract MALTransport createTransport(java.util.Map properties) throws MALException;
}
