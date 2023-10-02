package org.ccsds.moims.mo.mal.test.util;

import java.math.BigInteger;

import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.AttributeType;
import org.ccsds.moims.mo.mal.structures.AttributeTypeList;
import org.ccsds.moims.mo.mal.structures.Blob;
import org.ccsds.moims.mo.mal.structures.Duration;
import org.ccsds.moims.mo.mal.structures.FineTime;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.NamedValueList;
import org.ccsds.moims.mo.mal.structures.ObjectRef;
import org.ccsds.moims.mo.mal.structures.Time;
import org.ccsds.moims.mo.mal.structures.UInteger;
import org.ccsds.moims.mo.mal.structures.ULong;
import org.ccsds.moims.mo.mal.structures.UOctet;
import org.ccsds.moims.mo.mal.structures.URI;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.malprototype.structures.MyFirstObject;

/**
 *
 * @author mansuruddin.khan
 */
public final class Helper {

    public static final Identifier key1 = new Identifier("K1");
    public static final Identifier key2 = new Identifier("K2");
    public static final Identifier key3 = new Identifier("K3");
    public static final Identifier key4 = new Identifier("K4");

    private static final Attribute valueA = new Union("A");
    private static final Attribute value0 = new Union(0L);
    private static final Attribute valueNull = null;

    public static final AttributeList valuesA = new AttributeList(valueA);
    public static final AttributeList values0 = new AttributeList(value0);
    public static final AttributeList valuesNull = new AttributeList(valueNull);

    public static NamedValueList supplementsEmpty;
    public static NamedValueList supplementsFull;
    public static NamedValueList supplementsIPTestProvider;

    public static final String EMPTY_SUPPLEMENTS = "Empty";
    public static final String FULL_SUPPLEMENTS = "Full";
    
    static {
      IdentifierList testDomain = new IdentifierList();
      testDomain.add(new Identifier("CCSDS"));
      testDomain.add(new Identifier("MO"));
      testDomain.add(new Identifier("TESTBED_MAL"));
      
      supplementsEmpty = new NamedValueList();
      supplementsFull = new NamedValueList();
      supplementsFull.add(new NamedValue(new Identifier("Blob suppl"), new Blob("Blob suppl".getBytes())));
      supplementsFull.add(new NamedValue(new Identifier("Boolean suppl"), new Union(true)));
      supplementsFull.add(new NamedValue(new Identifier("Duration suppl"), new Duration(Double.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Float suppl"), new Union(Float.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Double suppl"), new Union(Double.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Identifier suppl"), new Identifier("Identifier suppl")));
      supplementsFull.add(new NamedValue(new Identifier("Octet suppl"), new Union(Byte.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("UOctet suppl"), new UOctet(Byte.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Short suppl"), new Union(Short.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("UShort suppl"), new UShort(Short.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Integer suppl"), new Union(Integer.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("UInteger suppl"), new UInteger(Integer.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("Long suppl"), new Union(Long.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("ULong suppl"), new ULong(BigInteger.TEN)));
      supplementsFull.add(new NamedValue(new Identifier("String suppl"), new Union("String suppl")));
      supplementsFull.add(new NamedValue(new Identifier("Time suppl"), new Time(Long.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("FineTime suppl"), new FineTime(Long.MAX_VALUE)));
      supplementsFull.add(new NamedValue(new Identifier("URI suppl"), new URI("URI suppl")));
      supplementsFull.add(new NamedValue(new Identifier("ObjectRef suppl"), new ObjectRef(testDomain, MyFirstObject.SHORT_FORM, new Identifier("ObjectRef suppl"), new UInteger(1))));
      supplementsIPTestProvider = new NamedValueList();
      supplementsIPTestProvider.add(new NamedValue(new Identifier("Provider name"), new Identifier("IPTest")));
    }
    
    private Helper() {
    }

    public static IdentifierList get4TestKeys() {
        IdentifierList list = new IdentifierList();
        list.add(key1);
        list.add(key2);
        list.add(key3);
        list.add(key4);
        return list;
    }

    public static IdentifierList get1TestKey() {
        IdentifierList list = new IdentifierList();
        list.add(key1);
        return list;
    }

    public static AttributeTypeList get1TestKeyType() {
        AttributeTypeList list = new AttributeTypeList();
        list.add(AttributeType.IDENTIFIER);
        return list;
    }

    public static AttributeTypeList get4TestKeyType() {
        AttributeTypeList list = new AttributeTypeList();
        list.add(AttributeType.IDENTIFIER);
        list.add(AttributeType.IDENTIFIER);
        list.add(AttributeType.IDENTIFIER);
        list.add(AttributeType.IDENTIFIER);
        return list;
    }

    public static NamedValueList parseSupplements(String supplements) throws Exception {
      if (EMPTY_SUPPLEMENTS.equals(supplements)) {
        return supplementsEmpty;
      }
      if (FULL_SUPPLEMENTS.equals(supplements)) {
        return supplementsFull;
      }
      throw new Exception("Unknown supplements code:" + supplements);
    }
}
