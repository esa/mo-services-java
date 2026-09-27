package org.ccsds.moims.mo.malprototype;

/**
 * Creates the Elements of the MALPrototype area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class MALPrototypeElementFactory implements org.ccsds.moims.mo.mal.AreaElementFactory {

    @Override
    public org.ccsds.moims.mo.mal.structures.Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -20: return new org.ccsds.moims.mo.malprototype.structures.TestObjectList();
            case -13: return new org.ccsds.moims.mo.malprototype.structures.MessageHeaderList();
            case -12: return new org.ccsds.moims.mo.malprototype.structures.IPTestResultList();
            case -11: return new org.ccsds.moims.mo.malprototype.structures.InteractionKeyList();
            case -10: return new org.ccsds.moims.mo.malprototype.structures.TestUpdateList();
            case -9: return new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdateList();
            case -8: return new org.ccsds.moims.mo.malprototype.structures.TestPublishDeregisterList();
            case -7: return new org.ccsds.moims.mo.malprototype.structures.TestPublishRegisterList();
            case -6: return new org.ccsds.moims.mo.malprototype.structures.BadHeaderReportList();
            case -5: return new org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList();
            case -4: return new org.ccsds.moims.mo.malprototype.structures.IPTestDefinitionList();
            case -3: return new org.ccsds.moims.mo.malprototype.structures.IPTestTransitionTypeList();
            case -2: return new org.ccsds.moims.mo.malprototype.structures.MyFirstObjectList();
            case -1: return new org.ccsds.moims.mo.malprototype.structures.AssertionList();
            case 1: return new org.ccsds.moims.mo.malprototype.structures.Assertion();
            case 2: return new org.ccsds.moims.mo.malprototype.structures.MyFirstObject();
            case 3: return new org.ccsds.moims.mo.malprototype.structures.IPTestTransitionType();
            case 4: return new org.ccsds.moims.mo.malprototype.structures.IPTestDefinition();
            case 5: return new org.ccsds.moims.mo.malprototype.structures.IPTestTransition();
            case 6: return new org.ccsds.moims.mo.malprototype.structures.BadHeaderReport();
            case 7: return new org.ccsds.moims.mo.malprototype.structures.TestPublishRegister();
            case 8: return new org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister();
            case 9: return new org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate();
            case 10: return new org.ccsds.moims.mo.malprototype.structures.TestUpdate();
            case 11: return new org.ccsds.moims.mo.malprototype.structures.InteractionKey();
            case 12: return new org.ccsds.moims.mo.malprototype.structures.IPTestResult();
            case 13: return new org.ccsds.moims.mo.malprototype.structures.MessageHeader();
            case 20: return new org.ccsds.moims.mo.malprototype.structures.TestObject();
            default: return createAreaElementOutOfBand(typeNumber);
        }
    }

    @Override
    public int getAreaNumber() {
        return 100;
    }

    @Override
    public int getAreaVersion() {
        return 1;
    }

    /**
     * Creates an Element whose type number lies too far out to be held in the
     * jump table that is asked first. This says nothing about how often the type
     * is asked for: the numbers of an Area are not handed out in the order of
     * use.
     * 
     * @param typeNumber The typeNumber field.
     */
    private static org.ccsds.moims.mo.mal.structures.Element createAreaElementOutOfBand(int typeNumber) {
        switch (typeNumber) {
            case -347: return new org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractFieldList();
            case -346: return new org.ccsds.moims.mo.malprototype.structures.BasicAbstractCompositeList();
            case -345: return new org.ccsds.moims.mo.malprototype.structures.ComplexStructureList();
            case -123: return new org.ccsds.moims.mo.malprototype.structures.TestBodyList();
            case -122: return new org.ccsds.moims.mo.malprototype.structures.GarageList();
            case -121: return new org.ccsds.moims.mo.malprototype.structures.PorscheList();
            case -120: return new org.ccsds.moims.mo.malprototype.structures.LamborghiniList();
            case 120: return new org.ccsds.moims.mo.malprototype.structures.Lamborghini();
            case 121: return new org.ccsds.moims.mo.malprototype.structures.Porsche();
            case 122: return new org.ccsds.moims.mo.malprototype.structures.Garage();
            case 123: return new org.ccsds.moims.mo.malprototype.structures.TestBody();
            case 345: return new org.ccsds.moims.mo.malprototype.structures.ComplexStructure();
            case 346: return new org.ccsds.moims.mo.malprototype.structures.BasicAbstractComposite();
            case 347: return new org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractField();
            default: return null;
        }
    }

}
