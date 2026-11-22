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

/**
 * A typed key for a per-{@link Exchange} scratch attribute, used by a stage to
 * pass context to a later stage on the same message without widening the
 * {@link Exchange} interface. Identity-based: two keys are the same key only if
 * they are the same instance, so distinct stages holding their own keys never
 * collide. The type parameter binds the key to the value type so
 * {@link Exchange#attr(Attr)} and {@link Exchange#setAttr(Attr, Object)} are
 * type-safe.
 *
 * @param <T> The type of the value stored under this key.
 */
public final class Attr<T> {

    private final String name;

    private Attr(final String name) {
        this.name = name;
    }

    /**
     * Creates a new, distinct attribute key.
     *
     * @param <T> The type of the value stored under this key.
     * @param name A descriptive name, used only for diagnostics. Must not be
     * null.
     * @return A new key, not equal to any other key.
     */
    public static <T> Attr<T> of(final String name) {
        return new Attr<T>(name);
    }

    /**
     * @return The descriptive name of this key.
     */
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "Attr(" + name + ")";
    }
}
