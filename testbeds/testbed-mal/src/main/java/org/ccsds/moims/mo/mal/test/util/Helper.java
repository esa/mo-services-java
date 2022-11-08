package org.ccsds.moims.mo.mal.test.util;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import org.ccsds.moims.mo.mal.structures.Attribute;
import org.ccsds.moims.mo.mal.structures.AttributeList;
import org.ccsds.moims.mo.mal.structures.Identifier;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilter;
import org.ccsds.moims.mo.mal.structures.SubscriptionFilterList;
import org.ccsds.moims.mo.mal.structures.Union;
import org.ccsds.moims.mo.mal.structures.UpdateHeader;
import org.ccsds.moims.mo.mal.structures.UpdateHeaderList;

/**
 *
 * @author mansuruddin.khan
 */
public final class Helper {
    
  private static final Attribute valueA = new Union("A");
  private static final Attribute value0 = new Union(0L);
  private static final Attribute valueNull = null;
  
  public static final Identifier key1 = new Identifier("K1");
  public static final Identifier key2 = new Identifier("K2");  
  public static final Identifier key3 = new Identifier("K3");  
  public static final Identifier key4 = new Identifier("K4");  
  
  public static final AttributeList valuesA = new AttributeList(valueA);
  public static final AttributeList values0 = new AttributeList(value0);
  public static final AttributeList valuesNull = new AttributeList(valueNull);  
  //private static final SubscriptionFilter subFilter = new SubscriptionFilter(key1, valuesA);
  
  private Helper(){
      
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
  
  @Deprecated
  public static SubscriptionFilterList getTestFilterlist(){
    SubscriptionFilterList subFilterList = new SubscriptionFilterList();
    subFilterList.add(new SubscriptionFilter(key1, values0));
    subFilterList.add(new SubscriptionFilter(key2, values0));
    subFilterList.add(new SubscriptionFilter(key3, values0));
    subFilterList.add(new SubscriptionFilter(key4, values0));   
    
    return subFilterList;
  }

  public static SubscriptionFilterList getTestFilterlistNull(){
    SubscriptionFilterList subFilterList = new SubscriptionFilterList();
    subFilterList.add(new SubscriptionFilter(key1, valuesNull));
    subFilterList.add(new SubscriptionFilter(key2, valuesNull));
    subFilterList.add(new SubscriptionFilter(key3, valuesNull));
    subFilterList.add(new SubscriptionFilter(key4, valuesNull));   
    
    return subFilterList;
  }  
  
  public static SubscriptionFilterList getTestFilterlistNull(AttributeList values){
    SubscriptionFilterList subFilterList = new SubscriptionFilterList();
    subFilterList.add(new SubscriptionFilter(key1, values));
    subFilterList.add(new SubscriptionFilter(key2, valuesNull));
    subFilterList.add(new SubscriptionFilter(key3, valuesNull));
    subFilterList.add(new SubscriptionFilter(key4, valuesNull));   
    
    return subFilterList;
  }  
  
  public static UpdateHeaderList getTestUpdateHeaderlist(){
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("Test"));
    domain.add(new Identifier("Domain0"));
    UpdateHeaderList updateHeaderList = new UpdateHeaderList();
    updateHeaderList.add(new UpdateHeader(key1, domain, valuesA));
    updateHeaderList.add(new UpdateHeader(key2, domain, valuesNull));
    updateHeaderList.add(new UpdateHeader(key3, domain, valuesNull));
    updateHeaderList.add(new UpdateHeader(key4, domain, valuesNull));
    
    return updateHeaderList;
  }  
public static UpdateHeaderList getTestUpdateHeaderlist(AttributeList values){
    IdentifierList domain = new IdentifierList();
    domain.add(new Identifier("Test"));
    domain.add(new Identifier("Domain0"));    
    UpdateHeaderList updateHeaderList = new UpdateHeaderList();
    updateHeaderList.add(new UpdateHeader(key1, domain, values));
    updateHeaderList.add(new UpdateHeader(key2, domain, valuesNull));
    updateHeaderList.add(new UpdateHeader(key3, domain, valuesNull));
    updateHeaderList.add(new UpdateHeader(key4, domain, valuesNull));
    
    return updateHeaderList;
  }  
}
