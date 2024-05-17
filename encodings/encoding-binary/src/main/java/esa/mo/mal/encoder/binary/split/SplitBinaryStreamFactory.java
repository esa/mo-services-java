/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO Split Binary encoder
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
package esa.mo.mal.encoder.binary.split;

import esa.mo.mal.encoder.binary.base.BinaryTimeHandler;

/**
 * Implements the MALElementStreamFactory interface for a split binary encoding.
 */
public class SplitBinaryStreamFactory extends esa.mo.mal.encoder.binary.base.BaseBinaryStreamFactory {

    public SplitBinaryStreamFactory() {
        super(SplitBinaryElementInputStream.class, 
                SplitBinaryElementOutputStream.class, new BinaryTimeHandler());
    }
}
