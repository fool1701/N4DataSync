/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * A BLayerTag tags its parent BWidget
 * as belonging to a given Px Layer.
 * 
 * @author    Mike Jarmy
 * @creation  13 Jul 09
 * @version   $Revision: 2$ $Date: 6/10/10 11:05:56 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The name of the layer.
 */
@NiagaraProperty(
  name = "layerName",
  type = "String",
  defaultValue = ""
)
public class BLayerTag
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.px.BLayerTag(4115469605)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "layerName"

  /**
   * Slot for the {@code layerName} property.
   * The name of the layer.
   * @see #getLayerName
   * @see #setLayerName
   */
  public static final Property layerName = newProperty(0, "", null);

  /**
   * Get the {@code layerName} property.
   * The name of the layer.
   * @see #layerName
   */
  public String getLayerName() { return getString(layerName); }

  /**
   * Set the {@code layerName} property.
   * The name of the layer.
   * @see #layerName
   */
  public void setLayerName(String v) { setString(layerName, v, null); }

  //endregion Property "layerName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLayerTag.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLayerTag()
  {
  }

  public BLayerTag(String name) 
  {
    setLayerName(name);
  }
  
  public boolean isNull()
  {
    return this.equals(BLayerTag.NULL) || this.getLayerName().length()==0;
  }
  
  public static final BLayerTag NULL = new BLayerTag(BString.DEFAULT.toString());
}
