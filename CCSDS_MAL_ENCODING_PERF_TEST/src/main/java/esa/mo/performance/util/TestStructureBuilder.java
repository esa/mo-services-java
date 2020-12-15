/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.mo.performance.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.ccsds.moims.mo.mal.structures.*;
import org.ccsds.moims.mo.perftest.structures.AggregationValueList;
import org.ccsds.moims.mo.perftest.structures.GenerationMode;
import org.ccsds.moims.mo.perftest.structures.ObjectIdList;
import org.ccsds.moims.mo.perftest.structures.ParameterValue;
import org.ccsds.moims.mo.perftest.structures.ParameterValueList;
import org.ccsds.moims.mo.perftest.structures.Report;
import org.ccsds.moims.mo.perftest.structures.Validity;

/**
 *
 * @author cooper_sf
 */
public abstract class TestStructureBuilder
{
  public static Composite createTestMALComposite(Time timestamp, int pktsPerReport, int paramsPerPkt)
  {
    org.ccsds.moims.mo.mal.structures.UpdateHeaderList updateHeader = new UpdateHeaderList();
    org.ccsds.moims.mo.perftest.structures.ObjectIdList objectId = new ObjectIdList();
    org.ccsds.moims.mo.perftest.structures.AggregationValueList value = new AggregationValueList();

    IdentifierList domain = new IdentifierList();
//    domain.add(new Identifier("ccsds"));
//    domain.add(new Identifier("mission"));
//    domain.add(null);

    for (int i = 0; i < pktsPerReport; i++)
    {
      updateHeader.add(new UpdateHeader(timestamp, new URI(""), UpdateType.UPDATE, new EntityKey(new Identifier("1"), 1L, (long) (i + 1), null)));
      //objectId.add(new ObjectId(new ObjectType(new UShort(4), new UShort(6), new UOctet((short)1), new UShort(2)), new ObjectKey(domain, (long)i)));
      objectId.add(null);
      value.add(createTestMALValueUpdate(paramsPerPkt));
    }

    return new Report(updateHeader, objectId, value);
  }

  public static org.ccsds.moims.mo.perftest.structures.AggregationValue createTestMALValueUpdate(int paramsPerPkt)
  {
    //ParameterValue val = new ParameterValue(Validity.VALID, new Blob("1234".getBytes()), null);
    ParameterValueList valueList = new ParameterValueList();
    for (int i = 0; i < paramsPerPkt; i++)
    {
      //ParameterValue val = new ParameterValue(Validity.VALID, new UInteger(4294967295L), null);
      ParameterValue val = new ParameterValue(Validity.VALID, new UInteger(i), null);
      //ParameterValue val = new ParameterValue(Validity.VALID, new Union(Boolean.FALSE), null);
      valueList.add(val);
    }
    return new org.ccsds.moims.mo.perftest.structures.AggregationValue(GenerationMode.PERIODIC, Boolean.FALSE, null, null, null, valueList);
  }

  public static org.ccsds.moims.mo.xml.test.Report createTestXMLComposite(Date timestamp, int pktsPerReport, int paramsPerPkt) throws Exception
  {
    DatatypeFactory xmlDatatypeFactory = DatatypeFactory.newInstance();
    GregorianCalendar gcal = new GregorianCalendar();
    gcal.setTime(timestamp);

    List<String> domain = new ArrayList<String>();
//    domain.add("ccsds");
//    domain.add("mission");

    org.ccsds.moims.mo.xml.test.Report report = new org.ccsds.moims.mo.xml.test.Report();
    List<org.ccsds.moims.mo.xml.test.UpdateHeader> updateHeader = report.getUpdateHeader();
    List<org.ccsds.moims.mo.xml.test.ObjectId> objectId = report.getObjectId();
    List<org.ccsds.moims.mo.xml.test.AggregationValue> value = report.getValue();

    for (int i = 0; i < pktsPerReport; i++)
    {
      org.ccsds.moims.mo.xml.test.EntityKey ek = new org.ccsds.moims.mo.xml.test.EntityKey();
      ek.setFirstSubKey("1");
      ek.setSecondSubKey(1L);
      ek.setThirdSubKey((long) (i + 1));
      ek.setFourthSubKey(null);
      org.ccsds.moims.mo.xml.test.UpdateHeader uh = new org.ccsds.moims.mo.xml.test.UpdateHeader();
      uh.setTimestamp(xmlDatatypeFactory.newXMLGregorianCalendar(gcal));
      uh.setSourceURI("");
      uh.setUpdateType(org.ccsds.moims.mo.xml.test.UpdateType.UPDATE);
      uh.setKey(ek);
      updateHeader.add(uh);
      //objectId.add(new ObjectId(new ObjectType(new UShort(4), new UShort(6), new UOctet((short)1), new UShort(2)), new ObjectKey(domain, (long)i)));
      objectId.add(null);
      value.add(createTestXMLValueUpdate(paramsPerPkt));
    }

    return report;
  }

  public static org.ccsds.moims.mo.xml.test.AggregationValue createTestXMLValueUpdate(int paramsPerPkt)
  {
    //ParameterValue val = new ParameterValue(Validity.VALID, new Blob("1234".getBytes()), null);
    org.ccsds.moims.mo.xml.test.AggregationValue rv = new org.ccsds.moims.mo.xml.test.AggregationValue();
    rv.setGenerationMode(org.ccsds.moims.mo.xml.test.GenerationMode.PERIODIC);
    rv.setFiltered(false);
    rv.setIntervalTime(null);
    rv.setDeltaTime(null);
    rv.setSetIntervalTime(null);
    List<org.ccsds.moims.mo.xml.test.ParameterValue> valueList = rv.getValues();

    for (int i = 0; i < paramsPerPkt; i++)
    {
      org.ccsds.moims.mo.xml.test.ParameterValue val = new org.ccsds.moims.mo.xml.test.ParameterValue();
      val.setValidityState(org.ccsds.moims.mo.xml.test.Validity.VALID);
      //val.setRawValue(4294967295L);
      val.setRawValue(i);
      val.setConvertedValue(null);

      valueList.add(val);
    }
    return rv;
  }

  public static boolean compareUpdateHeader(List<org.ccsds.moims.mo.xml.test.UpdateHeader> left, List<org.ccsds.moims.mo.xml.test.UpdateHeader> right)
  {
    boolean theSame = left.size() == right.size();
    for (int i = 0; theSame && (i < right.size()); i++)
    {
      org.ccsds.moims.mo.xml.test.UpdateHeader u_left = left.get(i);
      org.ccsds.moims.mo.xml.test.UpdateHeader u_right = right.get(i);
      theSame = compareUpdateHeader(u_left, u_right);
    }
    return theSame;
  }

  public static boolean compareUpdateHeader(org.ccsds.moims.mo.xml.test.UpdateHeader left, org.ccsds.moims.mo.xml.test.UpdateHeader right)
  {
    boolean theSame = true;
    theSame &= 0 == left.getTimestamp().compare(right.getTimestamp());
    theSame &= left.getSourceURI().equals(right.getSourceURI());
    theSame &= 0 == left.getUpdateType().compareTo(right.getUpdateType());
    theSame &= left.getKey().getFirstSubKey().equals(right.getKey().getFirstSubKey());
    theSame &= left.getKey().getSecondSubKey().equals(right.getKey().getSecondSubKey());
    theSame &= left.getKey().getThirdSubKey().equals(right.getKey().getThirdSubKey());
    //theSame &= left.getKey().getFourthSubKey().equals(right.getKey().getFourthSubKey());
    return theSame;
  }

  public static boolean compareObjectId(List<org.ccsds.moims.mo.xml.test.ObjectId> left, List<org.ccsds.moims.mo.xml.test.ObjectId> right)
  {
    boolean theSame = left.size() == right.size();
    for (int i = 0; theSame && (i < right.size()); i++)
    {
      org.ccsds.moims.mo.xml.test.ObjectId u_left = left.get(i);
      org.ccsds.moims.mo.xml.test.ObjectId u_right = right.get(i);

      if ((null != u_left) || (null != u_right))
      {
        if ((null != u_left) && (null != u_right))
        {
          theSame = compareObjectId(u_left, u_right);
        }
        else
        {
          theSame = false;
        }
      }
      else
      {
        theSame = true;
      }
    }
    return theSame;
  }

  public static boolean compareObjectId(org.ccsds.moims.mo.xml.test.ObjectId left, org.ccsds.moims.mo.xml.test.ObjectId right)
  {
    boolean theSame = true;
    theSame &= left.getType().getArea() == right.getType().getArea();
    theSame &= left.getType().getService() == right.getType().getService();
    theSame &= left.getType().getVersion() == right.getType().getVersion();
    theSame &= left.getType().getNumber() == right.getType().getNumber();

    theSame &= java.util.Arrays.equals(left.getKey().getDomain().toArray(new String[0]), right.getKey().getDomain().toArray(new String[0]));
    theSame &= left.getKey().getInstId() == right.getKey().getInstId();
    return theSame;
  }

  public static boolean compareAggregationValue(List<org.ccsds.moims.mo.xml.test.AggregationValue> left, List<org.ccsds.moims.mo.xml.test.AggregationValue> right)
  {
    boolean theSame = left.size() == right.size();
    for (int i = 0; theSame && (i < right.size()); i++)
    {
      org.ccsds.moims.mo.xml.test.AggregationValue u_left = left.get(i);
      org.ccsds.moims.mo.xml.test.AggregationValue u_right = right.get(i);

      if ((null != u_left) || (null != u_right))
      {
        if ((null != u_left) && (null != u_right))
        {
          theSame = compareAggregationValue(u_left, u_right);
        }
        else
        {
          theSame = false;
        }
      }
      else
      {
        theSame = true;
      }
    }
    return theSame;
  }

  public static boolean compareAggregationValue(org.ccsds.moims.mo.xml.test.AggregationValue left, org.ccsds.moims.mo.xml.test.AggregationValue right)
  {
    boolean theSame = true;
    theSame &= 0 == left.getGenerationMode().compareTo(right.getGenerationMode());
    theSame &= left.isFiltered() == right.isFiltered();
//    theSame &= 0 == left.getDeltaTime().compare(right.getDeltaTime());
//    theSame &= 0 == left.getIntervalTime().compare(right.getIntervalTime());
//    theSame &= 0 == left.getSetIntervalTime().compare(right.getSetIntervalTime());

    theSame &= left.getValues().size() == right.getValues().size();
    for (int i = 0; theSame && (i < right.getValues().size()); i++)
    {
      org.ccsds.moims.mo.xml.test.ParameterValue u_left = left.getValues().get(i);
      org.ccsds.moims.mo.xml.test.ParameterValue u_right = right.getValues().get(i);

      theSame &= 0 == u_left.getValidityState().compareTo(u_right.getValidityState());
      theSame &= u_left.getRawValue().equals(u_right.getRawValue());
//      theSame &= u_left.getConvertedValue().equals(u_right.getConvertedValue());
    }

    return theSame;
  }
}
