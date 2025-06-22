/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import java.io.IOException;

import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;



/**
 *   This class file is the superclass for all primitive
 * lonworks data types used to build up BLonData.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  5 May 01
 * @version   $Revision: 9$ $Date: 9/28/01 10:20:41 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
public abstract class BLonPrimitive
  extends BSimple
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonPrimitive(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPrimitive.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
//  Api
////////////////////////////////////////////////////////////
  /**
   *  Converts data to network byte format
   **/
  public abstract void toOutputStream(LonOutputStream out, BLonElementQualifiers e);

  /**
   *  Translates from network byte. Return the resulting
   *  BLonPrimitive or null if can't convert data in stream.
   **/
  public abstract BLonPrimitive fromInputStream(LonInputStream in, BLonElementQualifiers e);


  /** Subclasses should override this method to return a float representation
  * of their data if feasible.  The default implementation throws BajaRuntimeException. */
  public double   getDataAsDouble()   { throw new BajaRuntimeException("Conversion to float not supported."); }
  /** Subclasses should override this method to return a boolean representation
  * of their data if feasible.  The default implementation throws BajaRuntimeException. */
  public boolean getDataAsBoolean() { throw new BajaRuntimeException("Conversion to boolean not supported."); }
  /** Subclasses should override this method to return a String representation
  * of their data if feasible.  The default implementation throws BajaRuntimeException. */
  public String  getDataAsString()  { throw new BajaRuntimeException("Conversion to string not supported."); }
  /** Subclasses should override this method to return a BDynamicEnum representation
  * of their data if feasible.  The default implementation throws BajaRuntimeException. */
  public BEnum getDataAsEnum(BEnum en) {Thread.dumpStack(); throw new BajaRuntimeException("Conversion to enum not supported."); }
  
  /**  Deprecated - use makeFromDouble(double val, BLonElementQualifiers e)
  * @deprecated since 3.8.27 
  */
  @Deprecated
  public BLonPrimitive  makeFromDouble (double v)  { return makeFromDouble(v,null); }
  /** Subclasses should override this method to return a response of their data type from the specified float
  * or null if conversion not possible.  The default implementation throws BajaRuntimeException. 
  * @since 3.8.27
  */
  public BLonPrimitive makeFromDouble(double val, BLonElementQualifiers e) { throw new BajaRuntimeException("Conversion from float not supported."); }
  
  /** Subclasses should override this method to return a response of their data type from the specified boolean
  * or null if conversion not possible.  The default implementation throws BajaRuntimeException. */
  public BLonPrimitive  makeFromBoolean(boolean v) { throw new BajaRuntimeException("Conversion from boolean not supported."); }
  /** Subclasses should override this method to return a response of their data type from the specified string
  * or null if conversion not possible.  The default implementation throws BajaRuntimeException. */
  public BLonPrimitive  makeFromString (String v)  { throw new BajaRuntimeException("Conversion from string not supported."); }
  /** Subclasses should override this method to return a response of their data type from the specified BDynamicEnum
  * or null if conversion not possible.  The default implementation throws BajaRuntimeException. */
  public BLonPrimitive  makeFromEnum(BEnum v) {Thread.dumpStack(); throw new BajaRuntimeException("Conversion to enum not supported."); }
 
  /** Is this BLonPrimitive a numeric value (float,double,integer).
   *  The default implementation is false. */
  public boolean isNumeric() { return false; }
  
  public String toDebugString()
  {
    return getType().getDisplayName(null) + 
           " value=" + toString(null);
  }
  
////////////////////////////////////////////////////////////
//  Utility Methods
////////////////////////////////////////////////////////////

  /** Encode information to allow recreation of class 
   *  in the form module:class*/
  protected String encodeClass(BSimple s)
  {
    return s.getType().getTypeSpec().toString();
  }

  /** Get an instance of the class encoded as module:class. */
  protected BSimple decodeClass(String cl)
    throws IOException
  {
    try
    {
      String module = cl.substring(0,cl.indexOf(':'));
      String classname = cl.substring(cl.indexOf(':')+1);
      return (BSimple)Sys.loadModule(module).getType(classname).getInstance();
    }
    catch(Throwable e)
    {
      throw new IOException("Unable to getInstance for " + cl + "\n"+ e.toString());
    }
  }
}
