package org.ccsds.moims.mo.malprototype;

import org.ccsds.moims.mo.mal.AreaElementFactory;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.BadHeaderReport;
import org.ccsds.moims.mo.malprototype.structures.BadHeaderReportList;
import org.ccsds.moims.mo.malprototype.structures.BasicAbstractComposite;
import org.ccsds.moims.mo.malprototype.structures.BasicAbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.ComplexStructure;
import org.ccsds.moims.mo.malprototype.structures.ComplexStructureList;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.GarageList;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinition;
import org.ccsds.moims.mo.malprototype.structures.IPTestDefinitionList;
import org.ccsds.moims.mo.malprototype.structures.IPTestResult;
import org.ccsds.moims.mo.malprototype.structures.IPTestResultList;
import org.ccsds.moims.mo.malprototype.structures.IPTestTransition;
import org.ccsds.moims.mo.malprototype.structures.IPTestTransitionList;
import org.ccsds.moims.mo.malprototype.structures.IPTestTransitionType;
import org.ccsds.moims.mo.malprototype.structures.IPTestTransitionTypeList;
import org.ccsds.moims.mo.malprototype.structures.InteractionKey;
import org.ccsds.moims.mo.malprototype.structures.InteractionKeyList;
import org.ccsds.moims.mo.malprototype.structures.Lamborghini;
import org.ccsds.moims.mo.malprototype.structures.LamborghiniList;
import org.ccsds.moims.mo.malprototype.structures.MessageHeader;
import org.ccsds.moims.mo.malprototype.structures.MessageHeaderList;
import org.ccsds.moims.mo.malprototype.structures.MyFirstObject;
import org.ccsds.moims.mo.malprototype.structures.MyFirstObjectList;
import org.ccsds.moims.mo.malprototype.structures.Porsche;
import org.ccsds.moims.mo.malprototype.structures.PorscheList;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractField;
import org.ccsds.moims.mo.malprototype.structures.StructureWithAbstractFieldList;
import org.ccsds.moims.mo.malprototype.structures.TestBody;
import org.ccsds.moims.mo.malprototype.structures.TestBodyList;
import org.ccsds.moims.mo.malprototype.structures.TestObject;
import org.ccsds.moims.mo.malprototype.structures.TestObjectList;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishDeregisterList;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegister;
import org.ccsds.moims.mo.malprototype.structures.TestPublishRegisterList;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestPublishUpdateList;
import org.ccsds.moims.mo.malprototype.structures.TestUpdate;
import org.ccsds.moims.mo.malprototype.structures.TestUpdateList;

/**
 * Creates the Elements of the MALPrototype area, without holding an instance
 * of each of them, so that the class of a type is only loaded once a message
 * carries that type.
 */
public final class MALPrototypeElementFactory implements AreaElementFactory {

    @Override
    public Element createElement(int serviceNumber,
            int typeNumber) {
        if (serviceNumber != 0) {
            return null; // This Area declares no types under a service
        }
        switch (typeNumber) {
            case -20: return new TestObjectList();
            case -13: return new MessageHeaderList();
            case -12: return new IPTestResultList();
            case -11: return new InteractionKeyList();
            case -10: return new TestUpdateList();
            case -9: return new TestPublishUpdateList();
            case -8: return new TestPublishDeregisterList();
            case -7: return new TestPublishRegisterList();
            case -6: return new BadHeaderReportList();
            case -5: return new IPTestTransitionList();
            case -4: return new IPTestDefinitionList();
            case -3: return new IPTestTransitionTypeList();
            case -2: return new MyFirstObjectList();
            case -1: return new AssertionList();
            case 1: return new Assertion();
            case 2: return new MyFirstObject();
            case 3: return new IPTestTransitionType();
            case 4: return new IPTestDefinition();
            case 5: return new IPTestTransition();
            case 6: return new BadHeaderReport();
            case 7: return new TestPublishRegister();
            case 8: return new TestPublishDeregister();
            case 9: return new TestPublishUpdate();
            case 10: return new TestUpdate();
            case 11: return new InteractionKey();
            case 12: return new IPTestResult();
            case 13: return new MessageHeader();
            case 20: return new TestObject();
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
    private static Element createAreaElementOutOfBand(int typeNumber) {
        switch (typeNumber) {
            case -347: return new StructureWithAbstractFieldList();
            case -346: return new BasicAbstractCompositeList();
            case -345: return new ComplexStructureList();
            case -123: return new TestBodyList();
            case -122: return new GarageList();
            case -121: return new PorscheList();
            case -120: return new LamborghiniList();
            case 120: return new Lamborghini();
            case 121: return new Porsche();
            case 122: return new Garage();
            case 123: return new TestBody();
            case 345: return new ComplexStructure();
            case 346: return new BasicAbstractComposite();
            case 347: return new StructureWithAbstractField();
            default: return null;
        }
    }

}
