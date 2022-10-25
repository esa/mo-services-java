/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package esa.mo.mal.impl.broker.key;

import java.util.ArrayList;
import java.util.List;
import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.NamedValue;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.UShort;
import org.ccsds.moims.mo.mal.structures.Union;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mansuruddin.khan
 */
public class SubscriptionConsumerTest {
    
    public SubscriptionConsumerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    

    @Test
    public void testMatchesWithFilters() {
        System.out.println("matchesWithFilters: rsh and filters are both null");
        UpdateKeyValues rhs = null;
        SubscriptionConsumer instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), null);
        boolean expResult = false;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result);    
    }
    
    @Test
    public void testMatchesWithFilters2() {
        System.out.println("matchesWithFilters: keyValues of rsh and filters are null");
        UpdateKeyValues rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), null);
        SubscriptionConsumer instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), null);
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }
    
    
    @Test
    public void testMatchesWithFilters3() {
        System.out.println("matchesWithFilters: filters is null");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");         
        Attribute value = new Union("X");       
        NamedValue keyVal = new NamedValue(key, value);
        keyVals.add(keyVal);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);
        
        SubscriptionConsumer instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), null);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }
    
    @Test
    public void testMatchesWithFilters4() {
        System.out.println("matchesWithFilters: keyValues of rsh is null");

        Identifier key = new Identifier("parameter name"); 
        Attribute value =  new Union("X");
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), null);
        
        AttributeList values= new AttributeList(value);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }  
        
    @Test
    public void testMatchesWithFilters5() {
        System.out.println("matchesWithFilters: parameter name as key and X as value for both provider and consumer's filter with one filter");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name"); 
        Attribute value =  new Union("X");   
        NamedValue keyVal = new NamedValue(key, value);
        keyVals.add(keyVal);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);
        
        AttributeList values= new AttributeList(value);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }  
        
    @Test
    public void testMatchesWithFilters6() {
        System.out.println("matchesWithFilters: functional test with one filter");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);
        
        AttributeList values= new AttributeList(value2);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = false;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }  
        
    @Test
    public void testMatchesWithFilters7() {
        System.out.println("matchesWithFilters: functional test for case sensitive value with one filter");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);
        
        Attribute value3 = new Union("x");
        AttributeList values= new AttributeList(value3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = false;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }  
              
    @Test
    public void testMatchesWithFilters8() {
        System.out.println("matchesWithFilters: functional test of ORed values with two ANDed filters");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);
        Attribute value3 = new Union(3);
        Attribute value4 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        NamedValue keyVal3 = new NamedValue(key2, value3);
        NamedValue keyVal4 = new NamedValue(key2, value4);        
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        keyVals.add(keyVal3);
        keyVals.add(keyVal4);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);

        Attribute value_1_2 = new Union("Y");
        Attribute value_1_3 = new Union("Z");        
        AttributeList values = new AttributeList(value);
        values.add(value_1_2);
        values.add(value_1_3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);       

        Attribute value_2_2 = new Union(5);
        Attribute value_2_3 = new Union(7);        
        AttributeList values2 = new AttributeList(value2);
        values2.add(value_2_2);
        values2.add(value_2_3);
        SubscriptionFilter subFilter2 = new SubscriptionFilter(key2, values2); 
        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        subFilters.add(subFilter2);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }  
        
    @Test
    public void testMatchesWithFilters9() {
        System.out.println("matchesWithFilters:: functional test of ANDed keys with two filters");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Identifier key3 = new Identifier("parameter definition");
        Identifier key4 = new Identifier("parameterValueInstance");        
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);
        Attribute value3 = new Union(3);
        Attribute value4 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        NamedValue keyVal3 = new NamedValue(key3, value3);
        NamedValue keyVal4 = new NamedValue(key4, value4);        
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        keyVals.add(keyVal3);
        keyVals.add(keyVal4);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);

        Attribute value_1_2 = new Union("Y");
        Attribute value_1_3 = new Union("Z");        
        AttributeList values = new AttributeList(value);
        values.add(value_1_2);
        values.add(value_1_3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);       

        Attribute value_2_2 = new Union(5);
        Attribute value_2_3 = new Union(7);        
        AttributeList values2 = new AttributeList(value2);
        values2.add(value_2_2);
        values2.add(value_2_3);
        Identifier key5 = new Identifier("parameter property");  
        SubscriptionFilter subFilter2 = new SubscriptionFilter(key5, values2); 
        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        subFilters.add(subFilter2);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = false;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    } 
        
    @Test
    public void testMatchesWithFilters10() {
        System.out.println("matchesWithFilters: functional test of ORed values with two Anded filters, mismatched value in one filter");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Identifier key3 = new Identifier("parameter definition");
        Identifier key4 = new Identifier("parameterValueInstance");        
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);
        Attribute value3 = new Union(3);
        Attribute value4 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        NamedValue keyVal3 = new NamedValue(key3, value3);
        NamedValue keyVal4 = new NamedValue(key4, value4);        
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        keyVals.add(keyVal3);
        keyVals.add(keyVal4);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);

        Attribute value_1_1 = new Union("A");
        Attribute value_1_2 = new Union("Y");
        Attribute value_1_3 = new Union("Z");        
        AttributeList values = new AttributeList(value_1_1);
        values.add(value_1_2);
        values.add(value_1_3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);       

        Attribute value_2_2 = new Union(5);
        Attribute value_2_3 = new Union(7);        
        AttributeList values2 = new AttributeList(value2);
        values2.add(value_2_2);
        values2.add(value_2_3);
        SubscriptionFilter subFilter2 = new SubscriptionFilter(key2, values2); 
        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        subFilters.add(subFilter2);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = false;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    } 
          
    @Test
    public void testMatchesWithFilters11() {
        System.out.println("matchesWithFilters: functional test of ORed values with null wildcard, two filters");

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Identifier key3 = new Identifier("parameter definition");
        Identifier key4 = new Identifier("parameterValueInstance");        
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);
        Attribute value3 = new Union(3);
        Attribute value4 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        NamedValue keyVal3 = new NamedValue(key3, value3);
        NamedValue keyVal4 = new NamedValue(key4, value4);        
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        keyVals.add(keyVal3);
        keyVals.add(keyVal4);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(null, new UShort(1), new UShort(2), new UShort(3), keyVals);

        Attribute value_1_2 = new Union("Y");
        Attribute value_1_3 = new Union("Z");        
        AttributeList values = new AttributeList(value);
        values.add(value_1_2);
        values.add(value_1_3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);       

        Attribute value_2_2 = new Union(5);
        Attribute value_2_3 = new Union(7);        
        AttributeList values2 = new AttributeList(null);
        values2.add(value_2_2);
        values2.add(value_2_3);
        SubscriptionFilter subFilter2 = new SubscriptionFilter(key2, values2); 
        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        subFilters.add(subFilter2);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(null, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }   
              
    @Test
    public void testMatchesWithFilters12() {
        System.out.println("matchesWithFilters: functional test of ORed values with two ANDed filters including domain");
        
        Identifier domain1 = new Identifier("spacecraftA");
        Identifier domain2 = new Identifier("payload");
        IdentifierList domains = new IdentifierList();
        domains.add(domain1);
        domains.add(domain2);

        List<NamedValue> keyVals = new ArrayList<>();
        Identifier key = new Identifier("parameter name");
        Identifier key2 = new Identifier("parameter id");
        Attribute value = new Union("X");
        Attribute value2 = new Union(3);
        Attribute value3 = new Union(3);
        Attribute value4 = new Union(3);        
        NamedValue keyVal = new NamedValue(key, value);
        NamedValue keyVal2 = new NamedValue(key2, value2);
        NamedValue keyVal3 = new NamedValue(key2, value3);
        NamedValue keyVal4 = new NamedValue(key2, value4);        
        keyVals.add(keyVal);
        keyVals.add(keyVal2);
        keyVals.add(keyVal3);
        keyVals.add(keyVal4);
        
        UpdateKeyValues rhs ;
        rhs = new UpdateKeyValues(domains, new UShort(1), new UShort(2), new UShort(3), keyVals);

        Attribute value_1_2 = new Union("Y");
        Attribute value_1_3 = new Union("Z");        
        AttributeList values = new AttributeList(value);
        values.add(value_1_2);
        values.add(value_1_3);
        SubscriptionFilter subFilter = new SubscriptionFilter(key, values);       

        Attribute value_2_2 = new Union(5);
        Attribute value_2_3 = new Union(7);        
        AttributeList values2 = new AttributeList(value2);
        values2.add(value_2_2);
        values2.add(value_2_3);
        SubscriptionFilter subFilter2 = new SubscriptionFilter(key2, values2); 
        
        SubscriptionFilterList subFilters = new SubscriptionFilterList();
        subFilters.add(subFilter);
        subFilters.add(subFilter2);
        
        SubscriptionConsumer instance;
        instance = new SubscriptionConsumer(domains, new UShort(1), new UShort(2), new UShort(3), subFilters);
        
        boolean expResult = true;
        boolean result = instance.matchesWithFilters(rhs);
        assertEquals(expResult, result); 
    }     
}
