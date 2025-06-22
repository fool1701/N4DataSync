/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;


import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This interface file is implemented by any BSimple which
 *  may be used in a BLonSimple.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  5 Sept 01
 * @version   $Revision: 2$ $Date: 9/28/01 11:22:56 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
public interface BILonNetworkSimple
  extends BInterface
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BILonNetworkSimple(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BILonNetworkSimple.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   *  Converts data to network byte format
   */
  public void toOutputStream(LonOutputStream out);

  /**
   *  Translates from network bytes. Creates a new 
   *  BILonNetworkSimple from the next bytes read from out.
   */
  public BILonNetworkSimple fromInputStream(LonInputStream in);
  
  /**
   *  Get the network byte length.
   */
  public int getNetLength();


}
