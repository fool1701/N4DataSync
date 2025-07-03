 /*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

//import com.tridium.lonworks.util.NmUtil;

/**
 *  BMessageTag represents a single network variable
 *  in a LonDevice. It provides specific support for runtime
 *  updates and contains data needed to support network managment.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Nov 00
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Tag index.
 */
@NiagaraProperty(
  name = "index",
  type = "int",
  defaultValue = "-1"
)
/*
 Flag to indicate if message tag is input or output.
 */
@NiagaraProperty(
  name = "direction",
  type = "BLonNvDirection",
  defaultValue = "BLonNvDirection.input"
)
public class BMessageTag
  extends BVector
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BMessageTag(2519486242)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "index"

  /**
   * Slot for the {@code index} property.
   * Tag index.
   * @see #getIndex
   * @see #setIndex
   */
  public static final Property index = newProperty(0, -1, null);

  /**
   * Get the {@code index} property.
   * Tag index.
   * @see #index
   */
  public int getIndex() { return getInt(index); }

  /**
   * Set the {@code index} property.
   * Tag index.
   * @see #index
   */
  public void setIndex(int v) { setInt(index, v, null); }

  //endregion Property "index"

  //region Property "direction"

  /**
   * Slot for the {@code direction} property.
   * Flag to indicate if message tag is input or output.
   * @see #getDirection
   * @see #setDirection
   */
  public static final Property direction = newProperty(0, BLonNvDirection.input, null);

  /**
   * Get the {@code direction} property.
   * Flag to indicate if message tag is input or output.
   * @see #direction
   */
  public BLonNvDirection getDirection() { return (BLonNvDirection)get(direction); }

  /**
   * Set the {@code direction} property.
   * Flag to indicate if message tag is input or output.
   * @see #direction
   */
  public void setDirection(BLonNvDirection v) { set(direction, v, null); }

  //endregion Property "direction"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageTag.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  /**
   * No arg constructor
   */
  public BMessageTag()
  {
  }
  
////////////////////////////////////////////////////////////////
// Api 
////////////////////////////////////////////////////////////////

  public boolean isInput() { return getDirection()==BLonNvDirection.input; }
  public boolean isOutput() { return getDirection()==BLonNvDirection.output; }
  
////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/mtag.png");
  
  
  public BMessageTag(int index, BLonNvDirection direction)
  {
    setIndex(index);
    setDirection(direction);
  } 
  
}
