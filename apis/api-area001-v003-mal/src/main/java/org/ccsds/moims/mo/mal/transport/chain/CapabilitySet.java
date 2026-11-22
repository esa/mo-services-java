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
package org.ccsds.moims.mo.mal.transport.chain;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * An immutable set of {@link Capability} values. A {@link LinkDriver} advertises
 * the set it provides; a {@link Stage} advertises the set it requires from below
 * and the set it adds above. The chain builder resolves these at build time.
 */
public interface CapabilitySet {

    /**
     * The empty capability set.
     */
    CapabilitySet EMPTY = of();

    /**
     * @param capability The capability to test. Must not be null.
     * @return True if this set contains the given capability.
     */
    boolean has(Capability capability);

    /**
     * @return This set as an immutable {@link Set}. Never null.
     */
    Set<Capability> asSet();

    /**
     * @param required The capabilities to test. Must not be null.
     * @return True if this set contains every capability in {@code required}.
     */
    default boolean containsAll(final CapabilitySet required) {
        return asSet().containsAll(required.asSet());
    }

    /**
     * Builds a capability set from the given capabilities.
     *
     * @param capabilities The capabilities. Must not be null and must not
     * contain null.
     * @return An immutable capability set. Never null.
     */
    static CapabilitySet of(final Capability... capabilities) {
        final EnumSet<Capability> set = EnumSet.noneOf(Capability.class);
        set.addAll(Arrays.asList(capabilities));
        final Set<Capability> immutable = Collections.unmodifiableSet(set);
        return new CapabilitySet() {
            @Override
            public boolean has(final Capability capability) {
                return immutable.contains(capability);
            }

            @Override
            public Set<Capability> asSet() {
                return immutable;
            }

            @Override
            public String toString() {
                return immutable.toString();
            }
        };
    }
}
