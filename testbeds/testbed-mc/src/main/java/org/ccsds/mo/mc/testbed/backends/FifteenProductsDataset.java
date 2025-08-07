/* ----------------------------------------------------------------------------
 * Copyright (C) 2024      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : ESA CCSDS MO Services
 * ----------------------------------------------------------------------------
 * Licensed under European Space Agency Public License (ESA-PL) Weak Copyleft – v2.4
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
package org.ccsds.mo.mc.testbed.backends;

import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mc.ParameterDataset;
import org.ccsds.moims.mo.mc.structures.ParameterDefinition;

/**
 * A dummy backend in order to try out the provider.
 */
public class FifteenProductsDataset extends ParameterDataset {

    public ObjectRef<ParameterDefinition> ref;

    public FifteenProductsDataset() {
        IdentifierList domain = new IdentifierList();
        domain.add(new Identifier("myDomain"));

        // ---------------------------------------------------
        // Product Types
        // ---------------------------------------------------
        //ProductType type1 = new ProductType(new Identifier("type1"));
        // ---------------------------------------------------
        // Products
        // ---------------------------------------------------
        for (int i = 0; i < 15; i++) {
            Identifier name = new Identifier("product_" + i);
            //ref = new ObjectRef(domain, Product.TYPE_ID.getTypeId(), name, new UInteger(1));
            Blob productBody = new Blob();
            //ProductMetadata metadata = new ProductMetadata(type1, ref, Time.now(), timeWindow);
            //super.addNewProduct(ref, productBody, metadata);
        }
    }

}
