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

/**
 * The MOObject abstract class represents the MO Object type.
 */
public abstract class MOObject implements Composite {

    private ObjectIdentity objectIdentity;

    /**
     * Default Constructor.
     */
    public MOObject() {
        this.objectIdentity = new ObjectIdentity();
    }

    /**
     * Constructor.
     *
     * @param objectIdentity The identity of the MO Object.
     */
    public MOObject(ObjectIdentity objectIdentity) {
        this.objectIdentity = objectIdentity;
    }

    /**
     * Returns the Object Identity.
     *
     * @return The object identity.
     */
    public ObjectIdentity getObjectIdentity() {
        return objectIdentity;
    }

    /**
     * Encodes the value of this object using the provided MALEncoder.
     *
     * @param encoder encoder - the encoder to use for encoding.
     * @throws org.ccsds.moims.mo.mal.MALException if any encoding errors are
     * detected.
     */
    public void encode(org.ccsds.moims.mo.mal.MALEncoder encoder) throws org.ccsds.moims.mo.mal.MALException {
        encoder.encodeElement(objectIdentity);
    }

    /**
     * Decodes the value of this object using the provided MALDecoder.
     *
     * @param decoder decoder - the decoder to use for decoding.
     * @return Returns this object.
     * @throws org.ccsds.moims.mo.mal.MALException if any decoding errors are
     * detected.
     */
    public org.ccsds.moims.mo.mal.structures.Element decode(org.ccsds.moims.mo.mal.MALDecoder decoder) throws org.ccsds.moims.mo.mal.MALException {
        objectIdentity = (ObjectIdentity) decoder.decodeElement(new ObjectIdentity());
        return this;
    }
}
