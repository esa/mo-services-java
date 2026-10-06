package org.ccsds.moims.mo.malprototype.datatest.provider;

import java.io.IOException;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.MOErrorException;
import org.ccsds.moims.mo.mal.UnsupportedOperationException;
import org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mal.provider.MALInteractionHandler;
import org.ccsds.moims.mo.mal.provider.MALInvoke;
import org.ccsds.moims.mo.mal.provider.MALProgress;
import org.ccsds.moims.mo.mal.provider.MALProvider;
import org.ccsds.moims.mo.mal.provider.MALProviderSet;
import org.ccsds.moims.mo.mal.provider.MALRequest;
import org.ccsds.moims.mo.mal.provider.MALSubmit;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Composite;
import org.ccsds.moims.mo.mal.structures.CompositeList;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.HeterogeneousList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.ObjectRefList;
import org.ccsds.moims.mo.mal.structures.SessionType;
import org.ccsds.moims.mo.mal.structures.StringList;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.transport.MALMessageBody;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse;
import org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList;
import org.ccsds.moims.mo.malprototype.structures.Assertion;
import org.ccsds.moims.mo.malprototype.structures.AssertionList;
import org.ccsds.moims.mo.malprototype.structures.Auto;
import org.ccsds.moims.mo.malprototype.structures.Garage;
import org.ccsds.moims.mo.malprototype.structures.TestPublish;
import org.ccsds.moims.mo.malprototype.structures.TestPublishList;

/**
 * Provider Inheritance skeleton for DataTestInheritanceSkeleton service.
 */
public abstract class DataTestInheritanceSkeleton implements MALInteractionHandler, DataTestSkeleton, DataTestHandler {

    /**
     * The providerSet field.
     */
    private MALProviderSet providerSet = new MALProviderSet(DataTestHelper.DATATEST_SERVICE);

    /**
     * Returns the connection object for this provider.
     * 
     * @return the connection object for this provider
     * @throws IOException if the method was not implemented yet.
     */
    public ConnectionProvider getConnection() throws IOException {
        throw new IOException("This method needs to be overridden!");
    }

    @Override
    public void setSkeleton(DataTestSkeleton skeleton) {
        // Not used in the inheritance pattern (the skeleton is 'this');
    }

    @Override
    public void malInitialize(MALProvider provider) throws MALException {
        providerSet.addProvider(provider);
    }

    @Override
    public void malFinalize(MALProvider provider) throws MALException {
        providerSet.removeProvider(provider);
    }

    @Override
    public void handleSend(MALInteraction interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(MALSubmit interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffset((body.getBodyElement(0, new Union(Integer.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Integer.MAX_VALUE))).getIntegerValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObject((ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleRequest(MALRequest interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case DataTestServiceInfo._TESTDATA_OP_NUMBER:
            interaction.sendResponse(testData((Element) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            interaction.sendResponse(testDataBlob((Blob) body.getBodyElement(0, new Blob()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            Boolean testDataBooleanRt = testDataBoolean((body.getBodyElement(0, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendResponse((testDataBooleanRt == null) ? null : new Union(testDataBooleanRt));
            break;
          case DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            Double testDataDoubleRt = testDataDouble((body.getBodyElement(0, new Union(Double.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Double.MAX_VALUE))).getDoubleValue(),
                interaction);
            interaction.sendResponse((testDataDoubleRt == null) ? null : new Union(testDataDoubleRt));
            break;
          case DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            interaction.sendResponse(testDataDuration((Duration) body.getBodyElement(0, new Duration()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            interaction.sendResponse(testDataFineTime((FineTime) body.getBodyElement(0, new FineTime()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            Float testDataFloatRt = testDataFloat((body.getBodyElement(0, new Union(Float.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Float.MAX_VALUE))).getFloatValue(),
                interaction);
            interaction.sendResponse((testDataFloatRt == null) ? null : new Union(testDataFloatRt));
            break;
          case DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            interaction.sendResponse(testDataIdentifier((Identifier) body.getBodyElement(0, new Identifier()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            Integer testDataIntegerRt = testDataInteger((body.getBodyElement(0, new Union(Integer.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Integer.MAX_VALUE))).getIntegerValue(),
                interaction);
            interaction.sendResponse((testDataIntegerRt == null) ? null : new Union(testDataIntegerRt));
            break;
          case DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            Long testDataLongRt = testDataLong((body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                interaction);
            interaction.sendResponse((testDataLongRt == null) ? null : new Union(testDataLongRt));
            break;
          case DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            Byte testDataOctetRt = testDataOctet((body.getBodyElement(0, new Union(Byte.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Byte.MAX_VALUE))).getOctetValue(),
                interaction);
            interaction.sendResponse((testDataOctetRt == null) ? null : new Union(testDataOctetRt));
            break;
          case DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            Short testDataShortRt = testDataShort((body.getBodyElement(0, new Union(Short.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Short.MAX_VALUE))).getShortValue(),
                interaction);
            interaction.sendResponse((testDataShortRt == null) ? null : new Union(testDataShortRt));
            break;
          case DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            String testDataStringRt = testDataString((body.getBodyElement(0, new Union("")) == null) ? null : ((Union) body.getBodyElement(0, new Union(""))).getStringValue(),
                interaction);
            interaction.sendResponse((testDataStringRt == null) ? null : new Union(testDataStringRt));
            break;
          case DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            interaction.sendResponse(testDataTime((Time) body.getBodyElement(0, new Time()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            interaction.sendResponse(testDataURI((URI) body.getBodyElement(0, new URI()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testDataComposite((Assertion) body.getBodyElement(0, new Assertion()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            interaction.sendResponse(testDataEnumeration((SessionType) body.getBodyElement(0, SessionType.LIVE),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            interaction.sendResponse(testDataList((AssertionList) body.getBodyElement(0, new AssertionList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            interaction.sendResponse(testDataUInteger((UInteger) body.getBodyElement(0, new UInteger()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            interaction.sendResponse(testDataULong((ULong) body.getBodyElement(0, new ULong()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            interaction.sendResponse(testDataUOctet((UOctet) body.getBodyElement(0, new UOctet()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            interaction.sendResponse(testDataUShort((UShort) body.getBodyElement(0, new UShort()),
                interaction));
            break;
          case DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            TestExplicitMultiReturnResponse testExplicitMultiReturnRt = testExplicitMultiReturn((UOctet) body.getBodyElement(0, new UOctet()),
                (UShort) body.getBodyElement(1, new UShort()),
                (UInteger) body.getBodyElement(2, new UInteger()),
                (ULong) body.getBodyElement(3, new ULong()),
                interaction);
            interaction.sendResponse(
                    testExplicitMultiReturnRt.getOut1(),
                    testExplicitMultiReturnRt.getOut2(),
                    testExplicitMultiReturnRt.getOut3(),
                    testExplicitMultiReturnRt.getOut4()
            );
            break;
          case DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            TestAbstractMultiReturnResponse testAbstractMultiReturnRt = testAbstractMultiReturn((UOctet) body.getBodyElement(0, new UOctet()),
                (UShort) body.getBodyElement(1, new UShort()),
                (UInteger) body.getBodyElement(2, new UInteger()),
                (Element) body.getBodyElement(3, null),
                interaction);
            interaction.sendResponse(
                    testAbstractMultiReturnRt.getOut1(),
                    testAbstractMultiReturnRt.getOut2(),
                    testAbstractMultiReturnRt.getOut3(),
                    testAbstractMultiReturnRt.getOut4()
            );
            break;
          case DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBody(interaction);
            interaction.sendResponse();
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            interaction.sendResponse(testMalAttribute((Attribute) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testMalComposite((Composite) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testAbstractComposite((TestPublish) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            interaction.sendResponse(testMalAttributeList((AttributeList) body.getBodyElement(0, new AttributeList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            interaction.sendResponse(testMalElementList((HeterogeneousList) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testMalCompositeList((CompositeList) body.getBodyElement(0, new CompositeList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testAbstractCompositeList((TestPublishList) body.getBodyElement(0, new TestPublishList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            interaction.sendResponse(testDataObjectRef((ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()),
                interaction));
            break;
          case DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturnRt = testInnerAbstractMultiReturn((UOctet) body.getBodyElement(0, new UOctet()),
                (Element) body.getBodyElement(1, null),
                (Element) body.getBodyElement(2, null),
                (UInteger) body.getBodyElement(3, new UInteger()),
                interaction);
            interaction.sendResponse(
                    testInnerAbstractMultiReturnRt.getOut1(),
                    testInnerAbstractMultiReturnRt.getOut2(),
                    testInnerAbstractMultiReturnRt.getOut3(),
                    testInnerAbstractMultiReturnRt.getOut4()
            );
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicAbstractCompositeList((AbstractCompositeList) body.getBodyElement(0, new AbstractCompositeList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicMalCompositeList((CompositeList) body.getBodyElement(0, new CompositeList()),
                interaction));
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicMalElementList((HeterogeneousList) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypesRt = testPolymorphicObjectRefTypes((Garage) body.getBodyElement(0, new Garage()),
                (ObjectRefList) body.getBodyElement(1, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(2, new ObjectRefList()),
                (ObjectRefList) body.getBodyElement(3, new ObjectRefList()),
                interaction);
            interaction.sendResponse(
                    testPolymorphicObjectRefTypesRt.getOutput1(),
                    testPolymorphicObjectRefTypesRt.getOutput2(),
                    testPolymorphicObjectRefTypesRt.getOutput3(),
                    testPolymorphicObjectRefTypesRt.getOutput4()
            );
            break;
          case DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            interaction.sendResponse(createObject((Auto) body.getBodyElement(0, null),
                interaction));
            break;
          case DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            interaction.sendResponse(createObjectFromFields((body.getBodyElement(0, new Union(Long.MAX_VALUE)) == null) ? null : ((Union) body.getBodyElement(0, new Union(Long.MAX_VALUE))).getLongValue(),
                (Identifier) body.getBodyElement(1, new Identifier()),
                (body.getBodyElement(2, new Union(Boolean.FALSE)) == null) ? null : ((Union) body.getBodyElement(2, new Union(Boolean.FALSE))).getBooleanValue(),
                (body.getBodyElement(3, new Union("")) == null) ? null : ((Union) body.getBodyElement(3, new Union(""))).getStringValue(),
                (body.getBodyElement(4, new Union("")) == null) ? null : ((Union) body.getBodyElement(4, new Union(""))).getStringValue(),
                (StringList) body.getBodyElement(5, new StringList()),
                interaction));
            break;
          case DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            interaction.sendResponse(getObject((ObjectRef<Auto>) body.getBodyElement(0, new ObjectRef<Auto>()),
                interaction));
            break;
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (MOErrorException error) {
          throw new MALInteractionException(error);
        }
    }

    @Override
    public void handleInvoke(MALInvoke interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleProgress(MALProgress interaction,
            MALMessageBody body) throws MALException, MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new MALInteractionException(new UnsupportedOperationException(
                    MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
