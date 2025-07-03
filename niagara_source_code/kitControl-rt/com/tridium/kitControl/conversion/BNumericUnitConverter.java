/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.conversion;

import javax.baja.log.Log;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.UnitDatabase;

/**
 * @author    Bill Smith
 * @creation  18 June 2004
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets represent the in property
 */
@NiagaraProperty(
  name = "inFacets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.UNITS, UnitDatabase.getUnit(\"null\"))"
)
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
/*
 These facets represent the out property
 */
@NiagaraProperty(
  name = "outFacets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.UNITS, UnitDatabase.getUnit(\"null\"))"
)
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric()",
  flags = Flags.TRANSIENT | Flags.SUMMARY | Flags.READONLY
)
@NiagaraAction(
  name = "refresh"
)
public class BNumericUnitConverter
  extends BComponent
  implements BIStatus, BINumeric
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.conversion.BNumericUnitConverter(3800388908)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "inFacets"

  /**
   * Slot for the {@code inFacets} property.
   * These facets represent the in property
   * @see #getInFacets
   * @see #setInFacets
   */
  public static final Property inFacets = newProperty(0, BFacets.make(BFacets.UNITS, UnitDatabase.getUnit("null")), null);

  /**
   * Get the {@code inFacets} property.
   * These facets represent the in property
   * @see #inFacets
   */
  public BFacets getInFacets() { return (BFacets)get(inFacets); }

  /**
   * Set the {@code inFacets} property.
   * These facets represent the in property
   * @see #inFacets
   */
  public void setInFacets(BFacets v) { set(inFacets, v, null); }

  //endregion Property "inFacets"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusNumeric(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "outFacets"

  /**
   * Slot for the {@code outFacets} property.
   * These facets represent the out property
   * @see #getOutFacets
   * @see #setOutFacets
   */
  public static final Property outFacets = newProperty(0, BFacets.make(BFacets.UNITS, UnitDatabase.getUnit("null")), null);

  /**
   * Get the {@code outFacets} property.
   * These facets represent the out property
   * @see #outFacets
   */
  public BFacets getOutFacets() { return (BFacets)get(outFacets); }

  /**
   * Set the {@code outFacets} property.
   * These facets represent the out property
   * @see #outFacets
   */
  public void setOutFacets(BFacets v) { set(outFacets, v, null); }

  //endregion Property "outFacets"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY | Flags.READONLY, new BStatusNumeric(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Action "refresh"

  /**
   * Slot for the {@code refresh} action.
   * @see #refresh()
   */
  public static final Action refresh = newAction(0, null);

  /**
   * Invoke the {@code refresh} action.
   * @see #refresh
   */
  public void refresh() { invoke(refresh, null, null); }

  //endregion Action "refresh"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericUnitConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNumericUnitConverter()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    convert();
  }

  public void added(Property p, Context cx)
  {
    super.added(p, cx);
    if (!isRunning()) return;    
    if (checkLinkAdded())
    {
      syncSourceUnits();
    }
  }

  public void removed(Property p, BValue ov, Context cx)
  {
    super.removed(p, ov, cx);
    if (!isRunning()) return;
    checkLinkRemoved();
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;

    if (p == in || p == inFacets || p == outFacets)
    {
      convert();
    }
  }

  public void doRefresh()
  {
    syncSourceUnits();
  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

  /**
   * Get the facets for the specified slot.
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == in) return getInFacets();
    if (slot == out) return getOutFacets();
    return super.getSlotFacets(slot);
  }

  private synchronized boolean checkLinkRemoved()
  {
    BLink[] links = getLinks(in);
    if (links.length <= 0 && linked == true)
    {
      linked = false;
      return true;
    }
    
    return false;    
  }
  
  private synchronized boolean checkLinkAdded()
  {
    BLink[] links = getLinks(in);
    if (links.length > 0 && linked == false)
    {
      linked = true;
      return true;
    }
    
    return false;    
  }
  
  private synchronized void syncSourceUnits()
  {
    checkLinkAdded();
    if (linked)
    {
      BLink[] links = getLinks(in);
      if (links.length > 0)
      {
        Slot srcSlot = links[0].getSourceSlot();
        BComponent srcComponent = links[0].getSourceComponent();
        
        if (srcSlot != null && srcComponent != null)
        {
          BFacets srcFacets = srcComponent.getSlotFacets(srcSlot);          
          if (srcFacets != null)
          {
            setInFacets(srcFacets);
            setOutFacets(srcFacets);
          }        
        }
      }
    }
  }
  
  private void convert()
  {
    getOut().setStatus(getIn().getStatus());
    try
    {
      BUnit inUnit = (BUnit)getInFacets().get(BFacets.UNITS, BUnit.NULL);
      BUnit outUnit = (BUnit)getOutFacets().get(BFacets.UNITS, BUnit.NULL);

      double inValue = getIn().getValue();
      double outValue;

      if (inUnit.isNull() || outUnit.isNull() || inUnit == outUnit)
      {
        outValue = inValue;
      }
      else
      {
        outValue = inUnit.convertTo(outUnit, inValue);
      }

      getOut().setValue(outValue);
    }
    catch(Exception e)
    {
      getOut().setValue(getIn().getValue());
      getOut().setStatusFault(true);
    }
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  public double getNumeric() { return getOut().getValue(); }

  public final BFacets getNumericFacets() { return getOutFacets(); }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");
  private boolean linked = false;

  public static Log log = Log.getLog("kitControl");
}
