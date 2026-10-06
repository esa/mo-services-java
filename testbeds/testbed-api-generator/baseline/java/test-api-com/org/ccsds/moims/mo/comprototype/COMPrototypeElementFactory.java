package org.ccsds.moims.mo.comprototype;

import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject;
import org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObjectList;
import org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite;
import org.ccsds.moims.mo.comprototype.archivetest.structures.SubCompositeList;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload;
import org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayloadList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum;
import org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnumList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreationList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletionList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate;
import org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdateList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectA;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectAList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectB;
import org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectBList;
import org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite;
import org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateCompositeList;
import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;

/**
 * Creates the Elements of the COMPrototype area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class COMPrototypeElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        switch (serviceNumber) {
            case 0: return createAreaElement(typeNumber);
            case 2: return createEventTestElement(typeNumber);
            case 4: return createActivityTestElement(typeNumber);
            case 5: return createActivityRelayManagementElement(typeNumber);
            case 6: return createArchiveTestElement(typeNumber);
            default: return null;
        }
    }

    @Override
    public int getAreaNumber() {
        return 200;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

    /**
     * Creates an Element declared by the area itself.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createAreaElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the EventTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createEventTestElement(int typeNumber) {
        switch (typeNumber) {
            case -13: return new BasicEnumList();
            case -12: return new UpdateCompositeList();
            case -11: return new TestObjectBList();
            case -10: return new TestObjectAList();
            case -3: return new ObjectUpdateList();
            case -2: return new ObjectDeletionList();
            case -1: return new ObjectCreationList();
            case 1: return new ObjectCreation();
            case 2: return new ObjectDeletion();
            case 3: return new ObjectUpdate();
            case 10: return new TestObjectA();
            case 11: return new TestObjectB();
            case 12: return new UpdateComposite();
            case 13: return new BasicEnum();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the ActivityTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createActivityTestElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the ActivityRelayManagement service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createActivityRelayManagementElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the ArchiveTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static Element createArchiveTestElement(int typeNumber) {
        switch (typeNumber) {
            case -3: return new EnumeratedObjectList();
            case -2: return new SubCompositeList();
            case -1: return new TestObjectPayloadList();
            case 1: return new TestObjectPayload();
            case 2: return new SubComposite();
            case 3: return new EnumeratedObject();
            default: return null;
        }
    }

}
