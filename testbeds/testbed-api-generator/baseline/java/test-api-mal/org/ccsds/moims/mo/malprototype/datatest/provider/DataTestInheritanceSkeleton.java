package org.ccsds.moims.mo.malprototype.datatest.provider;

/**
 * Provider Inheritance skeleton for DataTestInheritanceSkeleton service.
 */
public abstract class DataTestInheritanceSkeleton implements org.ccsds.moims.mo.mal.provider.MALInteractionHandler, org.ccsds.moims.mo.malprototype.datatest.provider.DataTestSkeleton, org.ccsds.moims.mo.malprototype.datatest.provider.DataTestHandler {

    /**
     * The providerSet field.
     */
    private org.ccsds.moims.mo.mal.provider.MALProviderSet providerSet = new org.ccsds.moims.mo.mal.provider.MALProviderSet(org.ccsds.moims.mo.malprototype.datatest.DataTestHelper.DATATEST_SERVICE);

    /**
     * Returns the connection object for this provider.
     * 
     * @return the connection object for this provider
     * @throws java.io.IOException if the method was not implemented yet.
     */
    public org.ccsds.moims.mo.mal.helpertools.connections.ConnectionProvider getConnection() throws java.io.IOException {
        throw new java.io.IOException("This method needs to be overridden!");
    }

    @Override
    public void setSkeleton(org.ccsds.moims.mo.malprototype.datatest.provider.DataTestSkeleton skeleton) {
        // Not used in the inheritance pattern (the skeleton is 'this');
    }

    @Override
    public void malInitialize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.addProvider(provider);
    }

    @Override
    public void malFinalize(org.ccsds.moims.mo.mal.provider.MALProvider provider) throws org.ccsds.moims.mo.mal.MALException {
        providerSet.removeProvider(provider);
    }

    @Override
    public void handleSend(org.ccsds.moims.mo.mal.provider.MALInteraction interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleSubmit(org.ccsds.moims.mo.mal.provider.MALSubmit interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._SETTESTDATAOFFSET_OP_NUMBER:
            setTestDataOffset((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE))).getIntegerValue(),
                interaction);
            interaction.sendAcknowledgement();
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._DELETEOBJECT_OP_NUMBER:
            deleteObject((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()),
                interaction);
            interaction.sendAcknowledgement();
            break;
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (org.ccsds.moims.mo.mal.MOErrorException error) {
          throw new org.ccsds.moims.mo.mal.MALInteractionException(error);
        }
    }

    @Override
    public void handleRequest(org.ccsds.moims.mo.mal.provider.MALRequest interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        try {
        switch (opNumber) {
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATA_OP_NUMBER:
            interaction.sendResponse(testData((org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABLOB_OP_NUMBER:
            interaction.sendResponse(testDataBlob((org.ccsds.moims.mo.mal.structures.Blob) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Blob()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATABOOLEAN_OP_NUMBER:
            Boolean testDataBooleanRt = testDataBoolean((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                interaction);
            interaction.sendResponse((testDataBooleanRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataBooleanRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADOUBLE_OP_NUMBER:
            Double testDataDoubleRt = testDataDouble((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Double.MAX_VALUE))).getDoubleValue(),
                interaction);
            interaction.sendResponse((testDataDoubleRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataDoubleRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATADURATION_OP_NUMBER:
            interaction.sendResponse(testDataDuration((org.ccsds.moims.mo.mal.structures.Duration) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Duration()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFINETIME_OP_NUMBER:
            interaction.sendResponse(testDataFineTime((org.ccsds.moims.mo.mal.structures.FineTime) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.FineTime()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAFLOAT_OP_NUMBER:
            Float testDataFloatRt = testDataFloat((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Float.MAX_VALUE))).getFloatValue(),
                interaction);
            interaction.sendResponse((testDataFloatRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataFloatRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAIDENTIFIER_OP_NUMBER:
            interaction.sendResponse(testDataIdentifier((org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Identifier()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAINTEGER_OP_NUMBER:
            Integer testDataIntegerRt = testDataInteger((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Integer.MAX_VALUE))).getIntegerValue(),
                interaction);
            interaction.sendResponse((testDataIntegerRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataIntegerRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALONG_OP_NUMBER:
            Long testDataLongRt = testDataLong((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                interaction);
            interaction.sendResponse((testDataLongRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataLongRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOCTET_OP_NUMBER:
            Byte testDataOctetRt = testDataOctet((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Byte.MAX_VALUE))).getOctetValue(),
                interaction);
            interaction.sendResponse((testDataOctetRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataOctetRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASHORT_OP_NUMBER:
            Short testDataShortRt = testDataShort((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Short.MAX_VALUE))).getShortValue(),
                interaction);
            interaction.sendResponse((testDataShortRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataShortRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATASTRING_OP_NUMBER:
            String testDataStringRt = testDataString((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                interaction);
            interaction.sendResponse((testDataStringRt == null) ? null : new org.ccsds.moims.mo.mal.structures.Union(testDataStringRt));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATATIME_OP_NUMBER:
            interaction.sendResponse(testDataTime((org.ccsds.moims.mo.mal.structures.Time) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Time()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAURI_OP_NUMBER:
            interaction.sendResponse(testDataURI((org.ccsds.moims.mo.mal.structures.URI) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.URI()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATACOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testDataComposite((org.ccsds.moims.mo.malprototype.structures.Assertion) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Assertion()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAENUMERATION_OP_NUMBER:
            interaction.sendResponse(testDataEnumeration((org.ccsds.moims.mo.mal.structures.SessionType) body.getBodyElement(0, org.ccsds.moims.mo.mal.structures.SessionType.LIVE),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATALIST_OP_NUMBER:
            interaction.sendResponse(testDataList((org.ccsds.moims.mo.malprototype.structures.AssertionList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AssertionList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUINTEGER_OP_NUMBER:
            interaction.sendResponse(testDataUInteger((org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UInteger()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAULONG_OP_NUMBER:
            interaction.sendResponse(testDataULong((org.ccsds.moims.mo.mal.structures.ULong) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ULong()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUOCTET_OP_NUMBER:
            interaction.sendResponse(testDataUOctet((org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAUSHORT_OP_NUMBER:
            interaction.sendResponse(testDataUShort((org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UShort()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEXPLICITMULTIRETURN_OP_NUMBER:
            org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse testExplicitMultiReturnRt = testExplicitMultiReturn((org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort()),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger()),
                (org.ccsds.moims.mo.mal.structures.ULong) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ULong()),
                interaction);
            interaction.sendResponse(
                    testExplicitMultiReturnRt.getOut1(),
                    testExplicitMultiReturnRt.getOut2(),
                    testExplicitMultiReturnRt.getOut3(),
                    testExplicitMultiReturnRt.getOut4()
            );
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTMULTIRETURN_OP_NUMBER:
            org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse testAbstractMultiReturnRt = testAbstractMultiReturn((org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.UShort) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.UShort()),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.UInteger()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(3, null),
                interaction);
            interaction.sendResponse(
                    testAbstractMultiReturnRt.getOut1(),
                    testAbstractMultiReturnRt.getOut2(),
                    testAbstractMultiReturnRt.getOut3(),
                    testAbstractMultiReturnRt.getOut4()
            );
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTEMPTYBODY_OP_NUMBER:
            testEmptyBody(interaction);
            interaction.sendResponse();
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTE_OP_NUMBER:
            interaction.sendResponse(testMalAttribute((org.ccsds.moims.mo.mal.structures.Attribute) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testMalComposite((org.ccsds.moims.mo.mal.structures.Composite) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITE_OP_NUMBER:
            interaction.sendResponse(testAbstractComposite((org.ccsds.moims.mo.malprototype.structures.TestPublish) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALATTRIBUTELIST_OP_NUMBER:
            interaction.sendResponse(testMalAttributeList((org.ccsds.moims.mo.mal.structures.AttributeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.AttributeList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALELEMENTLIST_OP_NUMBER:
            interaction.sendResponse(testMalElementList((org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTMALCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testMalCompositeList((org.ccsds.moims.mo.mal.structures.CompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTABSTRACTCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testAbstractCompositeList((org.ccsds.moims.mo.malprototype.structures.TestPublishList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.TestPublishList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTDATAOBJECTREF_OP_NUMBER:
            interaction.sendResponse(testDataObjectRef((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTINNERABSTRACTMULTIRETURN_OP_NUMBER:
            org.ccsds.moims.mo.malprototype.datatest.body.TestInnerAbstractMultiReturnResponse testInnerAbstractMultiReturnRt = testInnerAbstractMultiReturn((org.ccsds.moims.mo.mal.structures.UOctet) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.UOctet()),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(1, null),
                (org.ccsds.moims.mo.mal.structures.Element) body.getBodyElement(2, null),
                (org.ccsds.moims.mo.mal.structures.UInteger) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.UInteger()),
                interaction);
            interaction.sendResponse(
                    testInnerAbstractMultiReturnRt.getOut1(),
                    testInnerAbstractMultiReturnRt.getOut2(),
                    testInnerAbstractMultiReturnRt.getOut3(),
                    testInnerAbstractMultiReturnRt.getOut4()
            );
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICABSTRACTCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicAbstractCompositeList((org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.AbstractCompositeList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALCOMPOSITELIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicMalCompositeList((org.ccsds.moims.mo.mal.structures.CompositeList) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.CompositeList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICMALELEMENTLIST_OP_NUMBER:
            interaction.sendResponse(testPolymorphicMalElementList((org.ccsds.moims.mo.mal.structures.HeterogeneousList) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._TESTPOLYMORPHICOBJECTREFTYPES_OP_NUMBER:
            org.ccsds.moims.mo.malprototype.datatest.body.TestPolymorphicObjectRefTypesResponse testPolymorphicObjectRefTypesRt = testPolymorphicObjectRefTypes((org.ccsds.moims.mo.malprototype.structures.Garage) body.getBodyElement(0, new org.ccsds.moims.mo.malprototype.structures.Garage()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                (org.ccsds.moims.mo.mal.structures.ObjectRefList) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.ObjectRefList()),
                interaction);
            interaction.sendResponse(
                    testPolymorphicObjectRefTypesRt.getOutput1(),
                    testPolymorphicObjectRefTypesRt.getOutput2(),
                    testPolymorphicObjectRefTypesRt.getOutput3(),
                    testPolymorphicObjectRefTypesRt.getOutput4()
            );
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECT_OP_NUMBER:
            interaction.sendResponse(createObject((org.ccsds.moims.mo.malprototype.structures.Auto) body.getBodyElement(0, null),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._CREATEOBJECTFROMFIELDS_OP_NUMBER:
            interaction.sendResponse(createObjectFromFields((body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.Union(Long.MAX_VALUE))).getLongValue(),
                (org.ccsds.moims.mo.mal.structures.Identifier) body.getBodyElement(1, new org.ccsds.moims.mo.mal.structures.Identifier()),
                (body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE)) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(2, new org.ccsds.moims.mo.mal.structures.Union(Boolean.FALSE))).getBooleanValue(),
                (body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(3, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                (body.getBodyElement(4, new org.ccsds.moims.mo.mal.structures.Union("")) == null) ? null : ((org.ccsds.moims.mo.mal.structures.Union) body.getBodyElement(4, new org.ccsds.moims.mo.mal.structures.Union(""))).getStringValue(),
                (org.ccsds.moims.mo.mal.structures.StringList) body.getBodyElement(5, new org.ccsds.moims.mo.mal.structures.StringList()),
                interaction));
            break;
          case org.ccsds.moims.mo.malprototype.datatest.DataTestServiceInfo._GETOBJECT_OP_NUMBER:
            interaction.sendResponse(getObject((org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>) body.getBodyElement(0, new org.ccsds.moims.mo.mal.structures.ObjectRef<org.ccsds.moims.mo.malprototype.structures.Auto>()),
                interaction));
            break;
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
        } catch (org.ccsds.moims.mo.mal.MOErrorException error) {
          throw new org.ccsds.moims.mo.mal.MALInteractionException(error);
        }
    }

    @Override
    public void handleInvoke(org.ccsds.moims.mo.mal.provider.MALInvoke interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

    @Override
    public void handleProgress(org.ccsds.moims.mo.mal.provider.MALProgress interaction,
            org.ccsds.moims.mo.mal.transport.MALMessageBody body) throws org.ccsds.moims.mo.mal.MALException, org.ccsds.moims.mo.mal.MALInteractionException {
        int opNumber = interaction.getOperation().getNumber().getValue();
        switch (opNumber) {
          default:
            interaction.sendError(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
            throw new org.ccsds.moims.mo.mal.MALInteractionException(new org.ccsds.moims.mo.mal.UnsupportedOperationException(
                    org.ccsds.moims.mo.mal.provider.MALInteractionHandler.ERROR_MSG_UNSUPPORTED + opNumber));
        }
    }

}
