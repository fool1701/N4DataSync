/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.componentLinks;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.kitControl.util.BRamp;

@NiagaraType
/*
 A point which may only be linked to a kitControl:Ramp component.
 */
@NiagaraProperty(
  name = "rampPoint",
  type = "double",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
/*
 the offset of the ramp that is connected to our component
 */
@NiagaraProperty(
  name = "rampOffset",
  type = "double",
  defaultValue = "0",
  flags = Flags.SUMMARY
)
public class BLinkCheckTest
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.componentLinks.BLinkCheckTest(4040933943)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "rampPoint"

  /**
   * Slot for the {@code rampPoint} property.
   * A point which may only be linked to a kitControl:Ramp component.
   * @see #getRampPoint
   * @see #setRampPoint
   */
  public static final Property rampPoint = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code rampPoint} property.
   * A point which may only be linked to a kitControl:Ramp component.
   * @see #rampPoint
   */
  public double getRampPoint() { return getDouble(rampPoint); }

  /**
   * Set the {@code rampPoint} property.
   * A point which may only be linked to a kitControl:Ramp component.
   * @see #rampPoint
   */
  public void setRampPoint(double v) { setDouble(rampPoint, v, null); }

  //endregion Property "rampPoint"

  //region Property "rampOffset"

  /**
   * Slot for the {@code rampOffset} property.
   * the offset of the ramp that is connected to our component
   * @see #getRampOffset
   * @see #setRampOffset
   */
  public static final Property rampOffset = newProperty(Flags.SUMMARY, 0, null);

  /**
   * Get the {@code rampOffset} property.
   * the offset of the ramp that is connected to our component
   * @see #rampOffset
   */
  public double getRampOffset() { return getDouble(rampOffset); }

  /**
   * Set the {@code rampOffset} property.
   * the offset of the ramp that is connected to our component
   * @see #rampOffset
   */
  public void setRampOffset(double v) { setDouble(rampOffset, v, null); }

  //endregion Property "rampOffset"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLinkCheckTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * This method determines if the link target is our ramp point.
   * If so, it verifies that the source component is a kitControl
   * Ramp component. If the source does not match, the link
   * cannot be created.
   */
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    //check if our target slot is our rampPoint
    if (targetSlot == rampPoint)
    {
      //if the source component is anything other than a 
      //kitControl:Ramp, link is invalid
      if (!(source.getType().is(BRamp.TYPE)))
      {
        return LinkCheck.makeInvalid("Object not an instance of BRamp");
      }
    }

    return LinkCheck.makeValid();
  }

  /**
   * Check to see if the property added is a BLink. If the value is
   * a BLink, check to see if the target slot is our Ramp point. If
   * the target slot is our Ramp Point, create a new Link between
   * the source Ramp component's offset property and our rampOffset
   * property. Each time the Ramp's offset property is modified, the
   * value will update in our component's Ramp Offset property.
   */
  public void added(Property property, Context context)
  {
    //check if the property added is a BLink. Also check that
    //this is our Station instance of our component and not our
    //workbench instance of the component.
    BValue bValue = get(property);
    if (bValue instanceof BLink && isRunning())
    {
      //if the target slot of the added link is our rampPoint,
      //create a link back to the source component's offset. 
      //We know due to our Check Link logic that the source
      //component must be a BRamp
      BLink link = (BLink)bValue;
      Slot targetSlot = link.getTargetSlot();
      if (targetSlot == rampPoint)
      {
        //We need an indirect link here because we cannot guarantee
        //the order in which the components will start when the 
        //station is restarted. Using an indirect link will ensure
        //we always have an ORD to the source component.
        BComponent src = link.getSourceComponent();
        BOrd sessionOrd = src.getOrdInSession();
        add(null, new BLink(sessionOrd, BRamp.offset.getName(),
          rampOffset.getName(), true
        ));
      }
    }
  }

  /**
   * If the link to our ramp point property is removed, we must
   * remove the corresponding link to our ramp offset property.
   */
  public void removed(Property property, BValue oldValue, Context context)
  {
    if (oldValue instanceof BLink)
    {
      //Again we must check to see if the link corresponds 
      //with our ramp point before moving forward.
      BLink link = (BLink)oldValue;
      String targetSlotName = link.getTargetSlotName();
      if (targetSlotName.equals(rampPoint.getName()))
      {
        //iterate through our links and remove link added
        //when the ramp point link was created
        BLink[] links = getLinks();
        for (int i = 0; i < links.length; i++)
        {
          //if the link targets our ramp offset property,
          //remove the link
          if (links[i].getTargetSlotName().equals(rampOffset.getName()))
          {
            remove(links[i]);
            break;
          }
        }
      }
    }
  }
}
