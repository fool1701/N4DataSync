/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bacnet.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.point.BBacnetPointDeviceExt;
import javax.baja.bacnet.point.BBacnetProxyExt;
import javax.baja.control.BControlPoint;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFolder;

/**
 * The PriorityArrayDetector enables stations engineered offline
 *  to properly detect the presence or absence of a Priority Array once
 *  the station has been installed.
 *  <p>
 *  The PriorityArrayDetector component can be used to detect or
 *  redetect the presence or absence of a PriorityArray in a
 *  BacnetProxyExt.
 *  <p>
 *  To use the component, add a BPriorityArrayDetector to the
 *  location in the nav tree that needs to be detected.
 *  <p>
 *  For example, to rescan the entire network add the
 *  detector as a child component of the network.
 *  <p>
 *  To detect priority arrays in just a single device,
 *  add the detector as a child of the desired device.
 */

@NiagaraType
@NiagaraAction(
  name = "detect"
)
public class BPriorityArrayDetector
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.util.BPriorityArrayDetector(2858905355)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "detect"

  /**
   * Slot for the {@code detect} action.
   * @see #detect()
   */
  public static final Action detect = newAction(0, null);

  /**
   * Invoke the {@code detect} action.
   * @see #detect
   */
  public void detect() { invoke(detect, null, null); }

  //endregion Action "detect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPriorityArrayDetector.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BPriorityArrayDetector()
  {
  }

  /*
   * The PriorityArrayDetector will function as intended when
   * placed in the component hierachy at any location between
   * the BBBacnetNetwork and BBacnetProxyExts and will recurse
   * down to all the extension leaf nodes. 
   * 
   * This check will not prevent a PriorityArrayDetector from
   * being placed into a non-functional location.
   * 
   * @see javax.baja.sys.BComponent#isParentLegal(javax.baja.sys.BComponent)
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BBacnetNetwork ||
      parent instanceof BFolder ||
      parent instanceof BBacnetDevice ||
      parent instanceof BBacnetPointDeviceExt ||
      parent instanceof BControlPoint ||
      parent instanceof BBacnetProxyExt;
  }

  public void doDetect()
  {
    detectPriorityArray(getParent().asComponent());
  }

  /*
   * Descend the component tree to find any BBacnetProxyExt components.
   * 
   * The discoverPrioritizedPresentValue method will skip or execute 
   * the discovery process based on object type as appropriate. 
   */
  public void detectPriorityArray(BComponent component)
  {
    if (component instanceof BBacnetProxyExt)
    {
      if (logger.isLoggable(Level.FINE))
        logger.log(Level.FINE, "Detecting priority array on: " + component);

      BBacnetProxyExt bacnetProxy = (BBacnetProxyExt)component;
      bacnetProxy.discoverPrioritizedPresentValue(true);
    }

    for (BComponent child : component.getChildComponents())
      if (child != this && isParentLegal(child))
        detectPriorityArray(child);
  }

  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon =
    BIcon.make("module://bacnet/com/tridium/bacnet/ui/icons/override.png");

  static Logger logger = Logger.getLogger("bacnet");
}
