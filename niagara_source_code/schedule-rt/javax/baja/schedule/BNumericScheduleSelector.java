/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BFacets;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BInteger;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

/**
 * BNumericScheduleSelector is a component that allows a user to
 * select the schedule that is controlling a particular component
 * from a list of preconfigured schedules.
 *
 * @author    John Huffman
 * @creation  20 June 2007
 * @version   $Revision: 1$ $Date: 6/28/07 3:38:37 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.make(BFacets.UNITS, BUnit.NULL, BFacets.PRECISION, BInteger.make(1) )"
)
/*
 An input to be linked to the selected schedule's output
 Setting the hidden flag causes a "ERROR: LinkStubGlyph.noTargetSlot: ..." message to display when
 we create the BLink
 */
@NiagaraProperty(
  name = "in",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0d, BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.TRANSIENT
)
/*
 An output that propagates the linked schedule's output
 */
@NiagaraProperty(
  name = "out",
  type = "BStatusNumeric",
  defaultValue = "new BStatusNumeric(0.0d, BStatus.nullStatus)",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT
)
public class BNumericScheduleSelector
  extends BAbstractScheduleSelector
  implements BIStatus, BINumeric
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BNumericScheduleSelector(1116782473)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.make(BFacets.UNITS, BUnit.NULL, BFacets.PRECISION, BInteger.make(1) ), null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * An input to be linked to the selected schedule's output
   * Setting the hidden flag causes a "ERROR: LinkStubGlyph.noTargetSlot: ..." message to display when
   * we create the BLink
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.OPERATOR | Flags.TRANSIENT, new BStatusNumeric(0.0d, BStatus.nullStatus), null);

  /**
   * Get the {@code in} property.
   * An input to be linked to the selected schedule's output
   * Setting the hidden flag causes a "ERROR: LinkStubGlyph.noTargetSlot: ..." message to display when
   * we create the BLink
   * @see #in
   */
  public BStatusNumeric getIn() { return (BStatusNumeric)get(in); }

  /**
   * Set the {@code in} property.
   * An input to be linked to the selected schedule's output
   * Setting the hidden flag causes a "ERROR: LinkStubGlyph.noTargetSlot: ..." message to display when
   * we create the BLink
   * @see #in
   */
  public void setIn(BStatusNumeric v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * An output that propagates the linked schedule's output
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT, new BStatusNumeric(0.0d, BStatus.nullStatus), null);

  /**
   * Get the {@code out} property.
   * An output that propagates the linked schedule's output
   * @see #out
   */
  public BStatusNumeric getOut() { return (BStatusNumeric)get(out); }

  /**
   * Set the {@code out} property.
   * An output that propagates the linked schedule's output
   * @see #out
   */
  public void setOut(BStatusNumeric v) { set(out, v, null); }

  //endregion Property "out"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNumericScheduleSelector.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNumericScheduleSelector()
  {
  }

  /**
   * Init if started after steady state has been reached.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    updateOut();
  }

  /**
   * handle any properties that have changed
   */
  @Override
  public void changed(Property p, Context cx)
  {
    if( !Sys.atSteadyState() )
      return;

    if ( ( cx == noWriteContext ) || ( cx == Context.decoding ) || ( !isRunning() ) )
      return;

  	super.changed( p, cx );

    if ( p.equals( in ) )
    {
      // update the 'out' property based on the value of the 'in' property
      updateOut();
    }
  }


  /*
   * update the 'out' property with the current value of the 'in' property
   */
  private void updateOut()
  {
    try
    {
      // make sure the input is valid
      if ( getIn().getStatus().isValid() )
      {
        getOut().setValue( getIn().getValue() );
        getOut().setStatusNull( false );
      }

      else
      {
        getOut().setStatusNull( true );
      }
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if ( ( slot == in ) || ( slot == out ) )
    {
      return getFacets();
    }
    return super.getSlotFacets(slot);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  @Override
  public BStatus getStatus() { return getOut().getStatus(); }

////////////////////////////////////////////////////////////////
// BINumeric interface
////////////////////////////////////////////////////////////////

  /**
   * Return the vaule as a numeric.
   */
  @Override
  public double getNumeric() { return getOut().getValue(); }

  /**
   * Return getFacets().
   */
  @Override
  public final BFacets getNumericFacets() { return getFacets(); }
}
