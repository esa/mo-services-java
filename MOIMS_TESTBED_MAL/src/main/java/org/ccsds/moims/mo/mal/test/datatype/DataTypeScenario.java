/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO MAL Test bed
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
package org.ccsds.moims.mo.mal.test.datatype;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.MALHelper;
import org.ccsds.moims.mo.mal.MALInteractionException;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Element;
import org.ccsds.moims.mo.mal.test.suite.LocalMALInstance;
import org.ccsds.moims.mo.malprototype.datatest.DataTestHelper;
import org.ccsds.moims.mo.malprototype.datatest.body.TestAbstractMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.body.TestExplicitMultiReturnResponse;
import org.ccsds.moims.mo.malprototype.datatest.consumer.DataTestStub;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 */
public class DataTypeScenario extends LoggingBase
{
  public String explicitDurationTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Duration data test...");
    try
    {
      rv = subSingleTest(TestData.testDuration, LocalMALInstance.instance().dataTestStub().testDataDuration(TestData.testDuration), "explicit Duration");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Duration");
    }
    logMessage("Finished explicit Duration data test");

    return rv;
  }

  public String explicitFineTimeTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit FineTime data test...");
    try
    {
      rv = subSingleTest(TestData.testFineTime, LocalMALInstance.instance().dataTestStub().testDataFineTime(TestData.testFineTime), "explicit FineTime");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit FineTime");
    }
    logMessage("Finished explicit FineTime data test");

    return rv;
  }

  public String explicitIdentifierTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Identifier data test...");
    try
    {
      rv = subSingleTest(TestData.testIdentifier, LocalMALInstance.instance().dataTestStub().testDataIdentifier(TestData.testIdentifier), "explicit Identifier");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Identifier");
    }
    logMessage("Finished explicit Identifier data test");

    return rv;
  }

  public String explicitTimeTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Time data test...");
    try
    {
      rv = subSingleTest(TestData.testTime, LocalMALInstance.instance().dataTestStub().testDataTime(TestData.testTime), "explicit Time");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Time");
    }
    logMessage("Finished explicit Time data test");

    return rv;
  }

  public String explicitURITypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit URI data test...");
    try
    {
      rv = subSingleTest(TestData.testURI, LocalMALInstance.instance().dataTestStub().testDataURI(TestData.testURI), "explicit URI");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit URI");
    }
    logMessage("Finished explicit URI data test");

    return rv;
  }

  public String explicitBlobTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Blob data test...");
    logMessage("   testing byte buffer...");
    try
    {
      // first try with byte array
      rv = subSingleTest(TestData.testBlob, LocalMALInstance.instance().dataTestStub().testDataBlob(TestData.testBlob), "explicit byte Blob");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Blob");
    }

    if (rv.equals("OK"))
    {
      logMessage("   testing file buffer...");
      try
      {
        // next try with an actual file
        File tempFile = createTempFile("TestBlobData");
        Blob fileBlob = new Blob(tempFile.toURI().toString());
        rv = subSingleTest(fileBlob, LocalMALInstance.instance().dataTestStub().testDataBlob(fileBlob), "explicit file Blob");
      }
      catch (MALInteractionException ex)
      {
        rv = subSingleTestExceptionHandler(ex, "explicit Blob");
      }
    }
    logMessage("Finished explicit Blob data test");

    return rv;
  }

  public String explicitBooleanTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Boolean data test...");
    try
    {
      rv = subSingleTest(TestData.testBoolean, LocalMALInstance.instance().dataTestStub().testDataBoolean(TestData.testBoolean), "explicit Boolean");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Boolean");
    }
    logMessage("Finished explicit Boolean data test");

    return rv;
  }

  public String explicitOctetTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Octet data test...");
    try
    {
      rv = subSingleTest(TestData.testOctet, LocalMALInstance.instance().dataTestStub().testDataOctet(TestData.testOctet), "explicit Octet");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Octet");
    }
    logMessage("Finished explicit Octet data test");

    return rv;
  }

  public String explicitDoubleTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Double data test...");
    try
    {
      rv = subSingleTest(TestData.testDouble, LocalMALInstance.instance().dataTestStub().testDataDouble(TestData.testDouble), "explicit Double");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Double");
    }
    logMessage("Finished explicit Double data test");

    return rv;
  }

  public String explicitFloatTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Float data test...");
    try
    {
      rv = subSingleTest(TestData.testFloat, LocalMALInstance.instance().dataTestStub().testDataFloat(TestData.testFloat), "explicit Float");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Float");
    }
    logMessage("Finished explicit Float data test");

    return rv;
  }

  public String explicitIntegerTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Integer data test...");
    try
    {
      rv = subSingleTest(TestData.testInteger, LocalMALInstance.instance().dataTestStub().testDataInteger(TestData.testInteger), "explicit Integer");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Integer");
    }
    logMessage("Finished explicit Integer data test");

    return rv;
  }

  public String explicitLongTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Long data test...");
    try
    {
      rv = subSingleTest(TestData.testLong, LocalMALInstance.instance().dataTestStub().testDataLong(TestData.testLong), "explicit Long");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Long");
    }
    logMessage("Finished explicit Long data test");

    return rv;
  }

  public String explicitShortTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit Short data test...");
    try
    {
      rv = subSingleTest(TestData.testShort, LocalMALInstance.instance().dataTestStub().testDataShort(TestData.testShort), "explicit Short");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Short");
    }
    logMessage("Finished explicit Short data test");

    return rv;
  }

  public String explicitUOctetTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit UOctet data test...");
    try
    {
      rv = subSingleTest(TestData.testUOctet, LocalMALInstance.instance().dataTestStub().testDataUOctet(TestData.testUOctet), "explicit UOctet");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit UOctet");
    }
    logMessage("Finished explicit UOctet data test");

    return rv;
  }

  public String explicitUIntegerTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit UInteger data test...");
    try
    {
      rv = subSingleTest(TestData.testUInteger, LocalMALInstance.instance().dataTestStub().testDataUInteger(TestData.testUInteger), "explicit UInteger");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit UInteger");
    }
    logMessage("Finished explicit UInteger data test");

    return rv;
  }

  public String explicitULongTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit ULong data test...");
    try
    {
      rv = subSingleTest(TestData.testULong, LocalMALInstance.instance().dataTestStub().testDataULong(TestData.testULong), "explicit ULong");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit ULong");
    }
    logMessage("Finished explicit ULong data test");

    return rv;
  }

  public String explicitUShortTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit UShort data test...");
    try
    {
      rv = subSingleTest(TestData.testUShort, LocalMALInstance.instance().dataTestStub().testDataUShort(TestData.testUShort), "explicit UShort");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit UShort");
    }
    logMessage("Finished explicit UShort data test");

    return rv;
  }

  public String explicitStringTypeWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting explicit String data test...");
    try
    {
      rv = subSingleTest(TestData.testString, LocalMALInstance.instance().dataTestStub().testDataString(TestData.testString), "explicit String");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit String");
    }
    logMessage("Finished explicit String data test");

    return rv;
  }

  public String attributeTypesWork() throws MALInteractionException, MALException
  {
    logMessage("Starting attribute data test...");
    String rv = subTest(TestData.testIndexes[0], TestData.testAttributes);
    logMessage("Finished attribute data test");

    return rv;
  }

  public String enumerationsWork() throws MALInteractionException, MALException
  {
    logMessage("Starting enumeration data test...");
    String rv = subTest(TestData.testIndexes[1], TestData.testEnumerations);
    logMessage("Finished enumeration data test");

    return rv;
  }

  public String completeCompositesWork() throws MALInteractionException, MALException
  {
    logMessage("Starting composite data test...");
    String rv = subTest(TestData.testIndexes[2], TestData.testComposites);
    logMessage("Finished composite data test");

    return rv;
  }

  public String abstractCompositesWork() throws MALInteractionException, MALException
  {
    logMessage("Starting abstract data test...");
    String rv = subTest(TestData.testIndexes[3], TestData.testAbstracts);
    logMessage("Finished abstract data test");

    return rv;
  }

  public String listsWork() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting list data test...");
    try
    {
      rv = subSingleTest(TestData.testList, LocalMALInstance.instance().dataTestStub().testDataList(TestData.testList), "explicit list");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit list");
    }
    logMessage("Finished list data test");

    return rv;
  }

  public String nullsWork() throws MALInteractionException, MALException
  {
    logMessage("Starting null data test...");
    String rv = subTest(TestData.testIndexes[4], TestData.testNulls);
    logMessage("Finished null data test");

    return rv;
  }

  public String compositesWithNullWork() throws MALInteractionException, MALException
  {
    logMessage("Starting composite null data test...");
    String rv = subTest(TestData.testIndexes[5], TestData.testCompositeWithNulls);
    logMessage("Finished composite null data test");

    return rv;
  }

  public String explicitMultiReturnWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting multi return data test...");
    try
    {
      LocalMALInstance.instance().dataTestStub().setTestDataOffset(54);
      TestExplicitMultiReturnResponse tv = LocalMALInstance.instance().dataTestStub().testExplicitMultiReturn(TestData.testUOctet, TestData.testUShort, TestData.testUInteger, TestData.testULong);
      rv = subMultiTest(TestData.testMultiReturnExplicit.getBodyElement0(), tv.getBodyElement0(), null, "explicit Multi return part 1");
      rv = subMultiTest(TestData.testMultiReturnExplicit.getBodyElement1(), tv.getBodyElement1(), rv, "explicit Multi return part 2");
      rv = subMultiTest(TestData.testMultiReturnExplicit.getBodyElement2(), tv.getBodyElement2(), rv, "explicit Multi return part 3");
      rv = subMultiTest(TestData.testMultiReturnExplicit.getBodyElement3(), tv.getBodyElement3(), rv, "explicit Multi return part 4");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "explicit Multi return");
    }
    logMessage("Finished multi return data test");

    return rv;
  }

  public String abstractMultiReturnWorks() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting abstract multi return data test...");
    try
    {
      TestAbstractMultiReturnResponse tv = LocalMALInstance.instance().dataTestStub().testAbstractMultiReturn(TestData.testUOctet, TestData.testUShort, TestData.testUInteger, TestData.testULong);
      rv = subMultiTest(TestData.testMultiReturnAbstract.getBodyElement0(), tv.getBodyElement0(), null, "abstract Multi return part 1");
      rv = subMultiTest(TestData.testMultiReturnAbstract.getBodyElement1(), tv.getBodyElement1(), rv, "abstract Multi return part 2");
      rv = subMultiTest(TestData.testMultiReturnAbstract.getBodyElement2(), tv.getBodyElement2(), rv, "abstract Multi return part 3");
      rv = subMultiTest(TestData.testMultiReturnAbstract.getBodyElement3(), tv.getBodyElement3(), rv, "abstract Multi return part 4");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "abstract Multi return");
    }
    logMessage("Finished abstract multi return data test");

    return rv;
  }

  public String multiReturnWithNullsWork() throws MALInteractionException, MALException
  {
    String rv;

    logMessage("Starting multi return null data test...");
    try
    {
      LocalMALInstance.instance().dataTestStub().setTestDataOffset(55);
      TestExplicitMultiReturnResponse tv = LocalMALInstance.instance().dataTestStub().testExplicitMultiReturn(TestData.testUOctet, TestData.testUShort, TestData.testUInteger, null);
      rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement0(), tv.getBodyElement0(), null, "null Multi return part 1");
      rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement1(), tv.getBodyElement1(), rv, "null Multi return part 2");
      rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement2(), tv.getBodyElement2(), rv, "null Multi return part 3");
      rv = subMultiTest(TestData.testMultiReturnNull.getBodyElement3(), tv.getBodyElement3(), rv, "null Multi return part 4");
    }
    catch (MALInteractionException ex)
    {
      rv = subSingleTestExceptionHandler(ex, "null Multi return");
    }
    logMessage("Finished multi return null data test");

    return rv;
  }

  protected String subTest(int reportingOffset, Vector testdata) throws MALInteractionException, MALException
  {
    DataTestStub stub = LocalMALInstance.instance().dataTestStub();

    stub.setTestDataOffset(reportingOffset);

    for (int i = 0; i < testdata.size(); i++, reportingOffset++)
    {
      Element testValue = (Element) testdata.get(i);
      try
      {
        Element rspnElement = stub.testData(testValue);

        if (null != testValue)
        {
          if (!testValue.equals(rspnElement))
          {
            String msg = "Test step failed in consumer: " + String.valueOf(reportingOffset);
            logMessage(msg);
            return msg;
          }
        }
        else
        {
          if (null != rspnElement)
          {
            String msg = "Test step failed in consumer: " + String.valueOf(reportingOffset);
            logMessage(msg);
            return msg;
          }
        }
      }
      catch (MALInteractionException ex)
      {
        long errNum = ex.getStandardError().getErrorNumber().getValue();
        if ((MALHelper.BAD_ENCODING_ERROR_NUMBER.getValue() == errNum) || (DataTestHelper.DATA_ERROR_ERROR_NUMBER.getValue() == errNum))
        {
          String msg = "Test step failed in consumer: " + ex.toString();
          logMessage(msg);
          return msg;
        }

        throw ex;
      }

      logMessage("Test step passed: " + String.valueOf(reportingOffset));
    }

    return "OK";
  }

  protected String subSingleTest(Object testValue, Object rspnValue, String testMsg) throws MALException
  {
    if (null != testValue)
    {
      if (!testValue.equals(rspnValue))
      {
        String msg = "Test " + testMsg + " failed in consumer, received value does not match expected";
        logMessage(msg);
        return msg;
      }
    }
    else
    {
      if (null != rspnValue)
      {
        String msg = "Test " + testMsg + " failed in consumer, received value is not expected null value";
        logMessage(msg);
        return msg;
      }
    }

    logMessage("Test " + testMsg + " passed");

    return "OK";
  }

  protected String subMultiTest(Object testValue, Object rspnValue, String previousResult, String testMsg) throws MALException
  {
    if ((null == previousResult) || ("OK".equals(previousResult)))
    {
      previousResult = subSingleTest(testValue, rspnValue, testMsg);
    }

    return previousResult;
  }

  protected String subSingleTestExceptionHandler(MALInteractionException ex, String testMsg) throws MALInteractionException
  {
    long errNum = ex.getStandardError().getErrorNumber().getValue();
    if ((MALHelper.BAD_ENCODING_ERROR_NUMBER.getValue() == errNum) || (DataTestHelper.DATA_ERROR_ERROR_NUMBER.getValue() == errNum))
    {
      String msg = "Test " + testMsg + " failed in consumer, encoding error received: " + ex.getStandardError().getExtraInformation().toString();
      logMessage(msg);
      return msg;
    }

    throw ex;
  }

  protected static File createTempFile(String prefix)
  {
    File fileData = null;

    try
    {
      fileData = createLoggingFile(prefix);
      OutputStream os = new FileOutputStream(fileData);
      os.write(fileData.getCanonicalPath().getBytes());
      os.flush();
      os.close();
    }
    catch (IOException ex)
    {
      ex.printStackTrace();
    }

    return fileData;
  }
}
