/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.proxy;

import javax.baja.driver.point.BPointFolder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Lonworks implementation of PointFolder
 *                      
 * @author    Robert Adams
 * @creation  1 Jul 04
 * @version   $Revision: 1$ $Date: 6/29/2004 4:30:18 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
public class BLonPointFolder
  extends BPointFolder
{                           
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.proxy.BLonPointFolder(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

 
  public boolean isParentLegal(BComponent parent)
  {
    return ( parent.getType().is(BLonPointDeviceExt.TYPE) ||
             parent.getType().is(BLonPointFolder.TYPE) );
  }
  
  public boolean isChildLegal(BComponent child)
  {
    if( child.getType().is(BPointFolder.TYPE) &&
        !child.getType().is(BLonPointFolder.TYPE) )
    {
      return false;
    }    
    return true;
  }
  
}
