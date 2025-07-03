/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIMruWidget is the interface implemented by widget controls
 * which use BMruOptions to store a persistent history of the
 * last selections.
 *
 * @author    Brian Frank       
 * @creation  20 Dec 03
 * @version   $Revision: 2$ $Date: 1/29/04 11:30:13 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIMruWidget
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BIMruWidget(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIMruWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the MRU options name used to key the BMruOptions instance.
   */
  public String getMruOptionsName();

  /**
   * Set the MRU options name used to key the BMruOptions instance.
   */
  public void setMruOptionsName(String mruName);
  
  /**
   * Get the options by name using <code>getMruOptionsName()</code>.
   */
  public BMruOptions getMruOptions();

  /**
   * Get the filter.
   */
  public IFilter getFilter();

  /**
   * Set the filter.
   */
  public void setFilter(IFilter filter);

  
} 
