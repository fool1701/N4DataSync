/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.control.enums.BPriorityLevel;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BInterface;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * The BIWritablePoint interface is implement by Baja control points.
 *
 * @author    Andy Saunders
 * @creation  08 Sept 03
 * @version   $Revision: 6$ $Date: 10/4/06 2:35:09 PM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType
public interface BIWritablePoint
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BIWritablePoint(2979906276)1.0$ @*/
/* Generated Wed Jan 26 11:36:16 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIWritablePoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the active priority level
   */
  public BPriorityLevel getActiveLevel();
  
  /**
   * Get current StatusValue for the specified input level. 
   *
   * @since Niagara 3.2
   */
  public BStatusValue getInStatusValue(BPriorityLevel level);

  /**
   * Get frozen property for the specified input level. 
   *
   * @since Niagara 3.2
   */
  public Property getInProperty(BPriorityLevel level);
}
