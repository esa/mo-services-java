package org.ccsds.moims.mo.malprototype2;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of the MALPrototype2 area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class MALPrototype2ElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        return null;
    }

    @Override
    public int getAreaNumber() {
        return 101;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

}
