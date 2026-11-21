/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO RMI Transport
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
package esa.mo.mal.transport.rmi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;

/**
 * Installs a process-wide Java deserialization allow-list that constrains the
 * classes the RMI transport's client-side {@code Naming.lookup()} may
 * deserialize.
 *
 * <p>
 * {@code Naming.lookup()} unmarshals whatever the remote RMI registry returns
 * with an unfiltered {@code ObjectInputStream}, before the result is cast to
 * {@link RMIReceiveInterface}. A received message whose From URI is
 * attacker-controlled steers the lookup at a rogue endpoint, which can return
 * an arbitrary serialized object and have its {@code readObject()} /
 * {@code readResolve()} run before the type check (CWE-502). The RMI client
 * path exposes no per-call filter, so the only hook is the JVM-wide serial
 * filter. This class installs one that permits only the classes a legitimate
 * RMI stub resolution needs and rejects everything else.
 *
 * <p>
 * The filter is installed once, and only when the application has not already
 * configured a process-wide filter, so an application that sets its own policy
 * keeps it. Installation can be disabled with the system property
 * {@value #INSTALL_PROPERTY}{@code =false}, in which case the operator is
 * expected to configure {@code jdk.serialFilter} instead.
 *
 * <p>
 * Being process-wide, the filter also governs any other Java deserialization in
 * the host application; an application that relies on Java serialization of its
 * own classes should configure its own filter (which this class then respects)
 * or disable this one.
 */
final class RMISerialFilter {

    /**
     * System property to disable installation of the serial filter.
     */
    static final String INSTALL_PROPERTY
            = "org.ccsds.moims.mo.mal.transport.rmi.installSerialFilter";

    private RMISerialFilter() {
    }

    /**
     * Installs the serial filter, unless disabled or one is already set.
     */
    static void install() {
        if ("false".equalsIgnoreCase(System.getProperty(INSTALL_PROPERTY))) {
            RMITransport.RLOGGER.log(Level.CONFIG,
                    "RMI serial filter not installed: disabled by {0}", INSTALL_PROPERTY);
            return;
        }

        // Java 9+ exposes java.io.ObjectInputFilter; Java 8 (8u121+) the same
        // API under sun.misc. Both are reached reflectively so the transport
        // still compiles and runs on either.
        Class<?> filterClass = resolveFilterApi();
        if (filterClass == null) {
            RMITransport.RLOGGER.log(Level.WARNING, "RMI serial filter not installed: the "
                    + "ObjectInputFilter API is not available on this JRE. Set the JVM-wide "
                    + "filter (jdk.serialFilter) to guard the RMI lookup against CWE-502.");
            return;
        }

        try {
            Class<?> configClass = Class.forName(filterClass.getName() + "$Config");
            if (configClass.getMethod("getSerialFilter").invoke(null) != null) {
                RMITransport.RLOGGER.log(Level.WARNING, "RMI serial filter not installed: a "
                        + "process-wide serial filter is already configured. Ensure it rejects "
                        + "unexpected classes returned by the RMI client lookup.");
                return;
            }

            Object filter = Proxy.newProxyInstance(filterClass.getClassLoader(),
                    new Class<?>[]{filterClass}, new AllowListHandler(filterClass));
            configClass.getMethod("setSerialFilter", filterClass).invoke(null, filter);
            RMITransport.RLOGGER.log(Level.CONFIG,
                    "RMI installed a process-wide serial filter for the client lookup");
        } catch (ReflectiveOperationException | RuntimeException ex) {
            RMITransport.RLOGGER.log(Level.WARNING, "RMI serial filter not installed", ex);
        }
    }

    private static Class<?> resolveFilterApi() {
        try {
            return Class.forName("java.io.ObjectInputFilter");
        } catch (ClassNotFoundException ex) {
            // Not Java 9+, fall through to the Java 8 location.
        }
        try {
            return Class.forName("sun.misc.ObjectInputFilter");
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    /**
     * Returns true for the classes a legitimate RMI stub resolution
     * deserializes: the remote stub (a dynamic proxy), its invocation handler,
     * remote reference, live reference and endpoint (all under java.rmi or
     * sun.rmi), the host name String, and the transport's own remote interface.
     * Everything else is rejected.
     */
    private static boolean isAllowed(final Class<?> clazz) {
        Class<?> type = clazz;
        while (type.isArray()) {
            type = type.getComponentType();
        }
        if (type.isPrimitive() || Proxy.isProxyClass(type) || type == RMIReceiveInterface.class) {
            return true;
        }
        final String name = type.getName();
        return name.equals("java.lang.String")
                || name.equals("java.lang.reflect.Proxy")
                || name.startsWith("java.rmi.")
                || name.startsWith("sun.rmi.");
    }

    /**
     * Invocation handler implementing the ObjectInputFilter interface, used so
     * the same logic serves both the java.io and sun.misc variants.
     */
    private static final class AllowListHandler implements InvocationHandler {

        private final Method serialClassMethod;
        private final Object allowed;
        private final Object rejected;
        private final Object undecided;

        AllowListHandler(final Class<?> filterClass) throws ReflectiveOperationException {
            final String base = filterClass.getName();
            this.serialClassMethod = Class.forName(base + "$FilterInfo").getMethod("serialClass");
            final Class<?> statusClass = Class.forName(base + "$Status");
            this.allowed = statusClass.getField("ALLOWED").get(null);
            this.rejected = statusClass.getField("REJECTED").get(null);
            this.undecided = statusClass.getField("UNDECIDED").get(null);
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            final String methodName = method.getName();
            if ("checkInput".equals(methodName)) {
                final Class<?> clazz = (Class<?>) serialClassMethod.invoke(args[0]);
                // Only object classes carry a serial class; the resource-limit
                // checks pass a null class and are left to the defaults.
                if (clazz == null) {
                    return undecided;
                }
                return isAllowed(clazz) ? allowed : rejected;
            }
            switch (methodName) {
                case "toString":
                    return "RMISerialFilter";
                case "hashCode":
                    return System.identityHashCode(proxy);
                case "equals":
                    return proxy == args[0];
                default:
                    return undecided;
            }
        }
    }
}
