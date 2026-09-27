package org.ccsds.moims.mo.comprototype;

/**
 * Creates the Elements of the COMPrototype area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class COMPrototypeElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
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
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the EventTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createEventTestElement(int typeNumber) {
        switch (typeNumber) {
            case -13: return new org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnumList();
            case -12: return new org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateCompositeList();
            case -11: return new org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectBList();
            case -10: return new org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectAList();
            case -3: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdateList();
            case -2: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletionList();
            case -1: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreationList();
            case 1: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectCreation();
            case 2: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectDeletion();
            case 3: return new org.ccsds.moims.mo.comprototype.eventtest.structures.ObjectUpdate();
            case 10: return new org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectA();
            case 11: return new org.ccsds.moims.mo.comprototype.eventtest.structures.TestObjectB();
            case 12: return new org.ccsds.moims.mo.comprototype.eventtest.structures.UpdateComposite();
            case 13: return new org.ccsds.moims.mo.comprototype.eventtest.structures.BasicEnum();
            default: return null;
        }
    }

    /**
     * Creates an Element declared by the ActivityTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createActivityTestElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the ActivityRelayManagement service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createActivityRelayManagementElement(int typeNumber) {
        return null;
    }

    /**
     * Creates an Element declared by the ArchiveTest service.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createArchiveTestElement(int typeNumber) {
        switch (typeNumber) {
            case -3: return new org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObjectList();
            case -2: return new org.ccsds.moims.mo.comprototype.archivetest.structures.SubCompositeList();
            case -1: return new org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayloadList();
            case 1: return new org.ccsds.moims.mo.comprototype.archivetest.structures.TestObjectPayload();
            case 2: return new org.ccsds.moims.mo.comprototype.archivetest.structures.SubComposite();
            case 3: return new org.ccsds.moims.mo.comprototype.archivetest.structures.EnumeratedObject();
            default: return null;
        }
    }

}
