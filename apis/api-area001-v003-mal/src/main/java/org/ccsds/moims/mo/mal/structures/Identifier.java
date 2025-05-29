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
package org.ccsds.moims.mo.mal.structures;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccsds.moims.mo.mal.MALDecoder;
import org.ccsds.moims.mo.mal.MALEncoder;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.TypeId;

/**
 * Class representing MAL Identifier type.
 */
public class Identifier implements Attribute {

    private final static long serialVersionUID = Attribute.IDENTIFIER_SHORT_FORM;
    private final String value;
    private final static String WILDCARD = "*";

    /**
     * Default constructor.
     */
    public Identifier() {
        this.value = "";
    }

    /**
     * Initialiser constructor.
     *
     * @param value Value to initialise with.
     */
    public Identifier(final String value) {
        if (value == null) {
            Logger.getLogger(Identifier.class.getName()).log(
                    Level.WARNING,
                    "The Identifier has been initialized with an invalid null "
                    + "value. Problems might occur while encoding the element.",
                    new MALException());
            this.value = "";
        } else {
            this.value = value;
        }
    }

    @Override
    public Element createElement() {
        return new Identifier();
    }

    /**
     * Returns the value of this type.
     *
     * @return the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Check if this object equals the wildcard.
     *
     * @return True if it equals the wildcard, false otherwise.
     */
    public boolean isWildcard() {
        return WILDCARD.equals(value);
    }

    @Override
    public TypeId getTypeId() {
        return new TypeId(Attribute.IDENTIFIER_SHORT_FORM);
    }

    @Override
    public void encode(final MALEncoder encoder) throws MALException {
        encoder.encodeIdentifier(this);
    }

    @Override
    public Element decode(final MALDecoder decoder) throws MALException {
        return decoder.decodeIdentifier();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Identifier)) {
            return false;
        }
        return this.value.equals(((Identifier) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
