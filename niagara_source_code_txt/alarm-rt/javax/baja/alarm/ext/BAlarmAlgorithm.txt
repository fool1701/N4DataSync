/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext;

import java.io.*;

import javax.baja.control.*;
import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BAlarmAlgorithm is the base class for all alarm
 * algorithms designed to alarm algorithms for 
 * BAlarmSourceExt.
 *
 * @author    Andy Saunders
 * @creation  13 Dec 03
 * @version   $Revision: 6$ $Date: 4/23/08 11:54:50 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BAlarmAlgorithm
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.BAlarmAlgorithm(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:02 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get this parent as a BAlarmSourceExt.
   */
  public final BAlarmSourceExt getParentExt()
  {
    BComplex parent = getParent();
    if (parent instanceof BAlarmSourceExt) // issue 11846
      return (BAlarmSourceExt)parent;

    return null;
  }

  /**
   * Get this extension's parent point, or null if
   * the parent is not an instance of BControlPoint.
   */
  public final BControlPoint getParentPoint()
  {
    BAlarmSourceExt parent = getParentExt();
    if(parent != null)
      return parent.getParentPoint();
    else
      return null;
  }

  /**
   * This causes execute to be called on the parent
   * point if getParentPoint() is not null.
   */
  public void executePoint()
  {
    if (!isRunning()) return;
    BControlPoint point = getParentPoint();
    if (point != null) point.execute();
  }  

  /**
   * Get this extension's parent point's facets, or BFacets.NULL
   * if the parent point  is not an instance of BControlPoint.
   */
  public final BFacets getPointFacets()
  {
    try
    {
      return getParentPoint().getFacets();
    }
    catch(Exception e)
    {
      return BFacets.NULL;
    }
  }

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BAlarmAlgorithm's parent must be a BAlarmSourceExt
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    if (parent instanceof BAlarmSourceExt)
      return true;
    else
      return false;
  }


  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return true;
  }

}
