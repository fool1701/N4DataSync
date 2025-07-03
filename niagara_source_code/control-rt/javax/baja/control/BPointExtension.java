/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.LinkCheck;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPointExtension is the base class for all point
 * extensions designed to provide plug-in functionality
 * for BControlPoints.
 *
 * @author    Brian Frank
 * @creation  11 Oct 00
 * @version   $Revision: 34$ $Date: 4/23/08 11:54:58 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BPointExtension
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BPointExtension(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:36:16 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointExtension.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get this extension's parent point, or null if
   * the parent is not an instance of BControlPoint.
   */
  public final BControlPoint getParentPoint()
  {
    // issue 11846
    BComplex p = getParent();
    if (p instanceof BControlPoint)
      return (BControlPoint)p;

    return null;
    
//    try
//    {
//      // since 99% of the time this will be in a
//      // child of a control point, this is much
//      // more efficient
//      return (BControlPoint)getParent();
//    }
//    catch(ClassCastException e)
//    {
//      return null;
//    }
  }         
  
  /**
   * Return true if the parent point is available
   * and in the subscribed state.
   */
  public final boolean isParentPointSubscribed()
  {                              
    BComponent parent = (BComponent)getParent();
    if (parent instanceof BControlPoint)
      return parent.isSubscribed();
    return false;
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
  
  /**
   * This causes execute to be called on the parent
   * point as long as getParentPoint() is not null.
   */
  public void executePoint()
  {
    if (!isRunning()) return;
    BControlPoint point = getParentPoint();
    if (point != null) point.execute();
  }                    
  
  /**
   * Return if this extension requires that the point be permanently
   * subscribed to operate correctly.  The default returns false.
   */
  public boolean requiresPointSubscription()
  {
    return false;
  }                        
  
  /**
   * This callback is made when the parent point's facets are modified.
   */
  public void pointFacetsChanged()
  {
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////
  
  /**
   * This callback is invoked when the extension's
   * parent BControlPoint is modified.  Any changes
   * to be made to the output value should be applied
   * to the specified working variable.
   */
  public abstract void onExecute(BStatusValue out, Context cx);

  /** 
   * This callback is invoked during link validation
   * on the parent
   */
  protected LinkCheck doCheckParentLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    return LinkCheck.makeValid();
  }  
  
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Extensions may only be placed in BControlPoints.
   */
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BControlPoint;
  }

  /**
   *  In most cases, only a single instance of a particular
   * type of BPointExtension should exist within a BControlObject.
   * This method is invoked by the parent BControlObject whenever
   * a new BPointExtension is added.  By default, if the types match,
   * the sibling is not allowed.
   */
  protected boolean isSiblingLegal(BComponent sibling)
  {
    if (this.getType().equals(sibling.getType()))
      return false;
    else
      return true;
  }
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/controlExtension.png");
  
}
