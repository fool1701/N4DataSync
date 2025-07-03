/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.y
 */
package javax.baja.lonworks.londata;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import javax.baja.control.BControlPoint;
import javax.baja.lonworks.BLonComponent;
import javax.baja.lonworks.BLonNetwork;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.lonworks.proxy.BLonProxyExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BVector;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

//import java.lang.reflect.*;


/**
 * The BLonData is the superclass for all classes which can
 * be used as the data component of BLonComponents.  These
 * classes can convert their data from and to the byte
 * format used by physical lonworks device. It will contain
 * one or more BLonPrimitives.
 * <p>
 *
 * @author    Robert Adams
 * @creation  5 May 01
 * @version   $Revision: 8$ $Date: 9/28/01 10:20:42 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BLonData
  extends BVector
{
  /**
   * No arg constructor
   */
  public BLonData()
  {
  }

  /**
   * Constructor with qualifiers and units
   */
  public BLonData(BLonPrimitive prim, BLonElementQualifiers elemQual, BUnit units)
  {
    BFacets f = LonFacetsUtil.makeFacets(elemQual,units);
//System.out.println("create BLonData prim=|" + prim.toString(null) +
//                     "|  elemQual=|" + elemQual.toString(null) + "| units=" + units);
    add("value",prim,0,f,null);
  }

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonData(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonData.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
//  Overrides
////////////////////////////////////////////////////////////
  /**
   * Filter out data changes and call dataChanged()
   */
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);


    if(isDataProp(prop))
    {
      dataChanged(context);
    }
  }

  public void added(Property prop, Context context) 
  {
    super.added(prop, context);
    
    // Special handling of BLonEnum.  If a londevice is added and the station crashes before a database flush
    // data recovery will no maintain the enumRange.  DataRecovery add uses encode instead of encodeString. 
    // This will push the enumRange to the slotFacets and restore these if the value losses them.  This address
    // a very narrow case.  See NCCB-8395
    if(prop.getType().is(BLonEnum.TYPE))
    {
      try
      {
        BEnum e = ((BLonEnum)get(prop)).getEnum();
        if(e instanceof BDynamicEnum)
        {
          BFacets f = getSlotFacets(prop);
          BEnumRange er = e.getRange();
          boolean validRng = er.getOrdinals().length>0;
          BString fr = (BString)f.get("rng", null);

          // If lonEnum has no range attempt to get from facets
          if(!validRng && fr!=null) 
          {
            BDynamicEnum de = BDynamicEnum.make(e.getEnum().getOrdinal(), (BEnumRange)BEnumRange.DEFAULT.decodeFromString(fr.toString()));
            set(prop, BLonEnum.make(de));
          }

          // If no facet range attemp to get from enum
          if(validRng && fr==null)
          {
            String s;
            s = er.encodeToString();
            f = BFacets.make(f,"rng", BString.make(s));
            setFacets(prop, f);
          }
        }
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
  }

  /** Is the specified property a BLonPrimitive or BLonData? */
  public static boolean isDataProp(Property prop)
  {
    return (prop.getType().is(BLonPrimitive.TYPE) ||
            prop.getType().is(BLonData.TYPE));
  }

  protected void dataChanged(Context context)
  {
   //System.out.println("BLondata.changed() " + getParent().getDisplayName(null));
    BComponent parent = (BComponent)getParent();
    if(parent==null) return;

    // this call will bubble up to top level parent which will be a BLonComponent(nv,nci,cp)
    if(parent.getType().is(BLonData.TYPE))
    {
      ((BLonData)parent).dataChanged(context);
    }
  }

  /**
   * Callback for notification by parent lonComponent that value
   *  was successfully written to device.  Notify any associated
   *  proxy points.
   */
  public void writeOk()
  {
    // NOTE: nested lonData's register with top most parent
    if(proxyExt==null) return;

    for(int i=0 ; i<proxyExt.size() ; i++)
    {
      BLonProxyExt pext = proxyExt.elementAt(i);
      BStatusValue sval = pext.getStatusValue();
      if(sval!=null) pext.writeOk(sval);
    }

  }

  /**
   * Callback for notification by parent lonComponent that write
   *  failed.  Call setWriteFail on any associated proxy points.
   */
  public void writeFail(String err)
  {
    if(proxyExt==null) return;

    // NOTE: nested lonData's register with top most parent
    for(int i=0 ; i<proxyExt.size() ; i++)
    {
      BLonProxyExt pext = proxyExt.elementAt(i);
      pext.writeFail(err);
    }

  }

  /**
   * Callback for notification by parent lonComponent that read
   *  failed.  Call setReadFault on any associated proxy points.
   */
  public void readFail(String err)
  {
    if(proxyExt==null) return;

    // NOTE: nested lonData's register with top most parent
    for(int i=0 ; i<proxyExt.size() ; i++)
    {
      BLonProxyExt pext = proxyExt.elementAt(i);
      pext.readFail(err);
    }

  }

  /**
   * Callback for notification by parent lonComponent that read
   *  succeeded.  Call readOk on any associated proxy points.
   */
  public void readOk()
  {
    if(proxyExt==null) return;

    // NOTE: nested lonData's register with top most parent
    for(int i=0 ; i<proxyExt.size() ; i++)
    {
      BLonProxyExt pext = proxyExt.elementAt(i);
      BStatusValue sval = pext.getStatusValue();
      if(sval!=null) pext.readOk(sval);
    }

  }

  /**
   * Callback for notification by parent lonComponent that value
   *  is stale.  Call setStale on any associated proxy points.
   */
  public void markStale(boolean s, Context cx)
  {
    if(proxyExt==null) return;

    // NOTE: nested lonData's register with top most parent
    for(int i=0 ; i<proxyExt.size() ; i++)
    {
      proxyExt.elementAt(i).setStale(true, cx);
    }

  }

  /** Register BLonProxyExt. */
  public void registerProxyExt(BLonProxyExt c)
  {
    // RegisterProxyExt at same level as getData returns.
    // If lc has "data" prop then register there else in lc.
    getLC().getData().doRegisterProxyExt(c);

  }

  private void doRegisterProxyExt(BLonProxyExt c)
  {
    // Maintain local list of proxies for transmission of read/write sucesss/fail
    if(proxyExt==null) proxyExt = new Vector<BLonProxyExt>();
    if(proxyExt.contains(c)) return;
    proxyExt.addElement(c);

  }

  /** Unregister BLonProxyExt. */
  public void unregisterProxyExt(BLonProxyExt c)
  {
    // RegisterProxyExt at same level as getData returns.
    // If lc has "data" prop then register there else in lc.
    getLC().getData().doUnregisterProxyExt(c);
  }

  private BLonComponent getLC()
  {
    BComplex p = this;
    while(!p.getType().is(BLonComponent.TYPE)) p = p.getParent();
    return (BLonComponent)p;
  }

  private void doUnregisterProxyExt(BLonProxyExt c)
  {
    if(proxyExt==null) return;
    proxyExt.removeElement(c);
  }

  /** Remove any proxies registered with this data component. */
  public void removeProxies(Context c)
  {
    if(proxyExt==null) return;

    while(!proxyExt.isEmpty())
    {
      BLonProxyExt pe = proxyExt.firstElement();
      proxyExt.removeElementAt(0);

      BComponent o = (BComponent)pe.getParent();
      ((BComponent)o.getParent()).remove(o.getPropertyInParent(),c);
    }
  }

  /** Check if any proxy points are linked to this data component. */
  public boolean hasProxies() { return (proxyExt!=null) && (proxyExt.size()>0); }

  /** Check if any writable proxy points are linked to this data component. */
  public boolean hasWriteProxies()
  {
    if( proxyExt!=null)
    {
      for(int i=0 ; i<proxyExt.size() ; i++)
      {
        BLonProxyExt lp = proxyExt.elementAt(i);
        if(lp.isUnoperational()) continue;  // NCCB-13496 don't write disabled proxies
        BControlPoint cp = (BControlPoint)lp.getParent();
        if(cp.isWritablePoint()) return true;
      }
    }
    return false;
  }

  /**
   * Get an array of all the proxy points linked to this data component.
   * @param writeable set to only return writeable points
   * @return list of control points or null if no proxies.
   */
  public BControlPoint[] getProxies(boolean writeable)
  {
    if(proxyExt==null) return null;

    Array<BControlPoint> a = new Array<>(BControlPoint.class);
    {
      for(int i=0 ; i<proxyExt.size() ; i++)
      {
        BLonProxyExt lp = proxyExt.elementAt(i);
        BControlPoint cp = (BControlPoint)lp.getParent();
        if(!cp.isWritablePoint() && !writeable) continue;
        a.add(cp);
      }
    }
    return a.trim();
  }

  /**
   * For internal use.  Force any writable proxies point to update
   * target with writeValue.
   */
  public final boolean forceProxyUpdates()
  {
    boolean updates = false;
    if( proxyExt!=null)
    {
      for(int i=0 ; i<proxyExt.size() ; i++)
      {
        BLonProxyExt lp = proxyExt.elementAt(i);
        BControlPoint cp = (BControlPoint)lp.getParent();
        if(cp.isWritablePoint())
        {
          lp.forceUpdate();
          updates = true;
        }
      }
    }
    return updates;
  }


  /**
   *  Verify that it would be valid to pass the specified BLonData
   *  in call to this objects copyFrom() method.
   *  @deprecated - use hasEquivalentElements()
   */
  @Deprecated
  public boolean canCopyFrom(BLonData ld)
  {
    return hasEquivalentElements(ld);
  }

  /**
   *  Verify that the specified lonData has the same set of primitive
   *  data elements by type, qualifiers, and name. Will recurse  into
   *  nested datatypes.
   */
  public boolean hasEquivalentElements(BLonData ld)
  {
    if(isUnion()!=ld.isUnion()) return false;

    SlotCursor<Property> c = getProperties();
    SlotCursor<Property> c2 = ld.getProperties();
    while(c.nextObject())
    {
      if(!c2.nextObject())return false;
      Property p = c.property();
      Property p2 = c2.property();

      if(p.getType()!=p2.getType()) return false;

      // Allow recursion into child BLonData
      if(p.getType().is(BLonData.TYPE))
      {
        if( !((BLonData) c.get()).hasEquivalentElements((BLonData)c2.get())) return false;
        continue;
      }
      // Skip properties not part of data set
      if(!p.getType().is(BLonPrimitive.TYPE)) continue;

      // Primitive elements must have same name
      if( !p.getName().equals(p2.getName()) ) return false;

      BLonElementQualifiers e = LonFacetsUtil.getQualifiers(p.getFacets());
      BLonElementQualifiers e2 = LonFacetsUtil.getQualifiers(p2.getFacets());
      if(!e.canCopyFrom(e2)) return false;

      // LonEnums must have same range
      if(p.getType().is(BLonEnum.TYPE))
      {
        BEnum en = ((BLonEnum)c.get()).getEnum();
        BEnum en2 = ((BLonEnum)c2.get()).getEnum();
        if(!en.getRange().equals(en2.getRange())) return false;
      }
    }
    if(c2.nextObject())return false;

    return true;
  }


  private Vector<BLonProxyExt> proxyExt = null;

  public void subscribed()
  {
   // System.out.println("subscribed " + getParent().getDisplayName(null) + ":" + getDisplayName(null));
    readSubscribed();
  }
  public void unsubscribed()
  {
   // System.out.println("unsubscribed " + getParent().getDisplayName(null) + ":" + getDisplayName(null));
    readUnsubscribed();
  }

  /**
   * Receive point subscriptions and pass up to parent. These
   * will ultimately accumulate in BLonComponent and be used
   * to determine if polling should be enabled.
   */
  public void readSubscribed()
  {
    BComplex p = getParent();
    if(p instanceof BLonComponent) ((BLonComponent)p).readSubscribed();
    else ((BLonData)p).readSubscribed();
  }

  /**
   * Receive point unsubscriptions and pass up to parent. These
   * will ultimately accumulate in BLonComponent and be used
   * to determine if polling should be enabled.
   */
  public void readUnsubscribed()
  {
    BComplex p = getParent();
    if(p instanceof BLonComponent) ((BLonComponent)p).readUnsubscribed();
    else ((BLonData)p).readUnsubscribed();
  }

  /** Get the BLonComponent containing this BLonData. */
  public BLonComponent getLonComponent()
  {
    BComplex p = this; //getParent();
    while(!(p instanceof BLonComponent) )p = p.getParent();
    return (BLonComponent)p;
  }

////////////////////////////////////////////////////////////
//  Api
////////////////////////////////////////////////////////////
  /** Initialize data elements with specified array of initial values. */
  public void initDataElements(float[] d)
  {
    initDataElements(d, 0);
  }

  private int initDataElements(float[] d, int n)
  {
  	int cnt = n;
    SlotCursor<Property> c = getProperties();
    while(c.nextObject())
    {
      BObject obj = c.get();
      Type    typ = obj.getType();

      if(typ.is(BLonPrimitive.TYPE))
      {
      	if(cnt>=d.length) throw new BajaRuntimeException("Not enough init elements for " + getLonComponent().getDisplayName(null));
      	
        BLonElementQualifiers e = LonFacetsUtil.getQualifiers(c.property().getFacets());
      	BLonPrimitive newValue = ((BLonPrimitive)obj).makeFromDouble(d[cnt++],e);
      	set(c.property(),newValue,BLonNetwork.lonNoWrite);
      }
      else if(typ.is(BLonData.TYPE)) cnt = initDataElements(d,cnt);
  	}
  	return cnt;
  }

  /**
   * Utility method to get an array of BLonPrimitives in
   * this BLonData instance.  Will recurse into nested BLonData.
   */
  public BLonPrimitive[] getPrimitives()
  {
    Array<BLonPrimitive> a = new Array<>(BLonPrimitive.class);
    getPrimitives(this, a);
    return a.trim();
  }

  void getPrimitives(BLonData ldat, Array<BLonPrimitive> a)
  {
    SlotCursor<Property> c = ldat.getProperties();
    while(c.nextObject())
    {
      BObject obj = c.get();
      Type    typ = obj.getType();

      if(typ.is(BLonPrimitive.TYPE))
      {
        a.add((BLonPrimitive)obj);
      }

      // Allow recursion into child BLonData
      else if(typ.is(BLonData.TYPE))
      {
        getPrimitives((BLonData)obj,a);
      }
    }
  }

  /** Get the length of the network byte data array. */
  public int getByteLength()
  {
    if(byteLength == -1) byteLength = toNetBytes().length;
    return byteLength;
  }
  private int byteLength = -1;

  /**
   * Converts the current value of the data property to network
   * byte format.
   * <p>
   * @return Byte array representation of current value of data.
   */
  public byte[] toNetBytes()
  {
    LonOutputStream out = new LonOutputStream();
    toOutputStream(out);
    return out.toByteArray();
  }

  /**
   * Writes the current value of the data in the correct byte order
   * to the supplied LonOutputStream.
   */
  protected void toOutputStream(LonOutputStream out)
  {
    // Walk data elements
    // For writing to output stream, unions must only use active elements
    Property[] pa = getActiveProps();
    for(int i=0 ; i<pa.length ; ++i)
    {
      BObject obj = get(pa[i]);
      Type    typ = obj.getType();

      if(typ.is(BLonPrimitive.TYPE))
      {
        primitiveToOutputStream(pa[i], out);
      }

      // Allow recursion into child BLonData
      else if(typ.is(BLonData.TYPE))
      {
        // Set bitFieldMark at entry into LonData structs to allow correct handling of
        // byteOffsets in nested LonData's.
        int origMark = out.setBitFieldMark();

        ((BLonData)obj).toOutputStream(out);

        out.resetBitFieldMark(origMark);
      }
    }
  }

  /**
   *  Writes the value of a single primitive to the output stream.  For BLonData with
   *  multiple elements, calls must be made in the proper sequence.
   *  <p>
   *  @param prop primitive slot to be processed
   *  @param out output stream for data.
   */
  protected void primitiveToOutputStream(Property prop, LonOutputStream out)
  {
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(prop.getFacets());
    // For unions need to reset pos based on byt qualifier.
    // Assume: 1) first field can not be in union
    //         2) byt will be specified in all elements in and following a union
    //         3) in non unions byt may not be specified - default is 0
    //         4) some positions may be skipped
    int pos = e.getByteOffset();
    if(pos>0) out.setPosition(pos);
    ((BLonPrimitive)get(prop)).toOutputStream(out,e);
  }

  /**
   *  Translates from network bytes. Sets the value
   *  of the object's data to the state represented
   *  by the given bytes.
   *  <p>
   *  @param netBytes Byte array representation of data.
   */
  public void fromNetBytes(byte[] netBytes)
  {
    fromInputStream(new LonInputStream(netBytes));
  }


  /**
   *  Sets the value of the object's data from
   *  the specified LonInputStream.
   *  <p>
   *  @param in LonInputStream containing byte representation of data.
   */
  protected void fromInputStream(LonInputStream in)
  {
    // Walk data elements
    // For reading from input stream, unions can use all elements since
    // elements in union branches reset offset as needed.
    SlotCursor<Property> c = getProperties();
    while(c.nextObject())
    {
      BObject obj = c.get();
      Type    typ = obj.getType();

      if(typ.is(BLonPrimitive.TYPE))
      {
        primitiveFromInputStream(c.property(), in);
      }
      // Allow recursion into child BLonData
      else if(typ.is(BLonData.TYPE))
      {
        // Set bitFieldMark at entry into LonData structs to allow correct handling of
        // byteOffsets in nested LonData's.
        int origMark = in.setBitFieldMark();

        ((BLonData)obj).fromInputStream(in);

        in.resetBitFieldMark(origMark);
      }
    }
  }

  // Allow override in BLonDataUnion to use getActiveProperties.
  Property[] getActiveProps() { return getPropertiesArray(); }

  /**
   * Set the value of a single primitive from the input stream.  For BLonData with
   *  multiple elements, calls must be made in the proper sequence.
   *  <p>
   *  @param prop primitive slot to be processed
   *  @param in LonInputStream containing byte representation of data.
   */
  protected void primitiveFromInputStream(Property prop, LonInputStream in)
  {
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(prop.getFacets());
    BLonPrimitive newValue;
    try
    {
      // For unions need to reset pos based on byt qualifier.
      // Assume: 1) first field can not be in union
      //         2) byt will be specified in all elements in and following a union
      //         3) in non unions byt may not be specified - default is 0
      int pos = e.getByteOffset();
      if(pos>0) in.reset(pos);

      newValue = ((BLonPrimitive)get(prop)).fromInputStream(in,e);
    }
    catch(Throwable t)
    {
      Logger.getLogger("lonworks").severe("Unable to decode netBytes for " + prop.getName() + ": " + t.getMessage());
      return;
    }

    // Set the value of this element - don't write back to device.
    set(prop,newValue,BLonNetwork.lonNoWrite);
  }

  public String toString(Context cx)
  {
    StringBuilder sb = new StringBuilder();
    int cnt = 0;
    Property[] pa = new Property[0];
    try
    {
      pa = getActiveProps();
    }
    catch (NullPointerException ignored)
    {
      //No active properties
    }
    for (int i = 0; i < pa.length; ++i)
    {
      BObject o = get(pa[i]);
      Type typ = o.getType();

      if (typ.is(BLonPrimitive.TYPE))
      {
        if(cnt>3){ sb.append(", ..."); break; }
        if(cnt>0) sb.append(", ");

        BLonPrimitive lp = (BLonPrimitive) o;
        if (lp.isNumeric())
        {
          double dval = lp.getDataAsDouble();
          // merge the units with context
          BasicContext bcx = new BasicContext(cx, pa[i].getFacets());
          sb.append(BDouble.make(dval).toString(bcx));
        }
        else
        {
          sb.append(lp.toString(cx));
        }

        cnt++;
      }
    }
    return sb.toString();
  }

  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    SlotCursor<Property> c = getProperties();
    while(c.nextObject())
    {
      sb.append(c.property().getName()).append(":").append(c.get().toDebugString()).append("\n");
      BFacets f = c.property().getFacets();
      try {  if(f!=null) sb.append(f.encodeToString()).append("\n"); } catch(Throwable e) {}
      sb.append("\n");
    }
    return sb.toString();
  }

  /** Set BLonData element from int value. */
  public void setLonInt(String propName, int val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(prop.getFacets());
    if(lonPrim.getType().is(BLonInteger.TYPE))
      set(prop,BLonInteger.make(val),cx);
    else
      set(prop,lonPrim.makeFromDouble(val,e),cx);
  }
  /** Set BLonData element from boolean value. */
  public void setLonBoolean(String propName, boolean val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);
    if(lonPrim.getType().is(BLonBoolean.TYPE))
      set(prop,BLonBoolean.make(val),cx);
    else
      set(prop,lonPrim.makeFromBoolean(val),cx);
  }
  /** Set BLonData element from float value. */
  public void setLonFloat(String propName, float val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(prop.getFacets());
    if(lonPrim.getType().is(BLonFloat.TYPE))
      set(prop,BLonFloat.make(val),cx);
    else
      set(prop,lonPrim.makeFromDouble((double)val,e),cx);
  }
  /** Set BLonData element from String value. */
  public void setLonString(String propName, String val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);
    if(lonPrim.getType().is(BLonString.TYPE))
      set(prop,BLonString.make(val),cx);
    else
      set(prop,lonPrim.makeFromString(val),cx);
  }
  /** DEPRECATE. */
  public void setLonEnum(String propName, String val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);

    set(prop,lonPrim.makeFromString(val),cx);
  }
  /** Set BLonData element from enum value. */
  public void setLonEnum(String propName, BEnum val, Context cx)
  {
    Property prop = getProperty(propName);
    BLonPrimitive lonPrim = (BLonPrimitive)get(prop);

    set(prop,lonPrim.makeFromEnum(val),cx);
  }

  /** Get int value from BLonData element. */
  public int getLonInt(String propName)
  {
    BLonPrimitive lonPrim = (BLonPrimitive)get(getProperty(propName));
    if(lonPrim.getType().is(BLonInteger.TYPE))
      return ((BLonInteger)lonPrim).getInt();

    return (int)lonPrim.getDataAsDouble();
  }
  /** Get boolean value from BLonData element. */
  public boolean getLonBoolean(String propName)
  {
    BLonPrimitive lonPrim = (BLonPrimitive)get(getProperty(propName));
    if(lonPrim.getType().is(BLonBoolean.TYPE))
      return ((BLonBoolean)lonPrim).getBoolean();

    return lonPrim.getDataAsBoolean();
  }
  /** Get float value from BLonData element. */
  public float getLonFloat(String propName)
  {
    BLonPrimitive lonPrim = (BLonPrimitive)get(getProperty(propName));
    if(lonPrim.getType().is(BLonFloat.TYPE))
      return ((BLonFloat)lonPrim).getFloat();

    return (float)lonPrim.getDataAsDouble();
  }

  /** Get String value from BLonData element. */
  public String getLonString(String propName)
  {
    BLonPrimitive lonPrim = (BLonPrimitive)get(getProperty(propName));
    if(lonPrim.getType().is(BLonString.TYPE))
      return ((BLonString)lonPrim).getString();

    return lonPrim.getDataAsString();
  }

  /** Get enum value from BLonData element. */
  public BEnum getLonEnum(String propName, BEnum en)
  {
    BLonPrimitive lonPrim = (BLonPrimitive)get(getProperty(propName));
    return lonPrim.getDataAsEnum(en);
  }

  public boolean isUnion() { return false; }
}
