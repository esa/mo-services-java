package org.ccsds.moims.mo.comprototype1;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of the COMPrototype1 area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class COMPrototype1ElementFactory implements AreaElementFactory {

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
        return 201;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

}
