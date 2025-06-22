/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BWeekday;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.BDaysOfWeekBits;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BDaysOfWeekBitsFE.
 *
 * @author    Brian Frank       
 * @creation  26 Jul 01
 * @version   $Revision: 7$ $Date: 4/14/05 1:13:12 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:DaysOfWeekBits"
  )
)
public class BDaysOfWeekBitsFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDaysOfWeekBitsFE(2762421921)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDaysOfWeekBitsFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BObject 
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BDaysOfWeekBitsFE()
  {
    BGridPane pane = new BGridPane(7);
    pane.add("c0", sun = new BCheckBox(sunText), null);
    pane.add("c1", mon = new BCheckBox(monText), null);
    pane.add("c2", tue = new BCheckBox(tueText), null);
    pane.add("c3", wed = new BCheckBox(wedText), null);
    pane.add("c4", thu = new BCheckBox(thuText), null);
    pane.add("c5", fri = new BCheckBox(friText), null);
    pane.add("c6", sat = new BCheckBox(satText), null);
    setContent(pane);
    
    linkTo("lksun", sun, BCheckBox.actionPerformed, setModified);
    linkTo("lkmon", mon, BCheckBox.actionPerformed, setModified);
    linkTo("lktue", tue, BCheckBox.actionPerformed, setModified);
    linkTo("lkwed", wed, BCheckBox.actionPerformed, setModified);
    linkTo("lkthu", thu, BCheckBox.actionPerformed, setModified);
    linkTo("lkfri", fri, BCheckBox.actionPerformed, setModified);
    linkTo("lksat", sat, BCheckBox.actionPerformed, setModified);    
  }

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////
  
  protected void doSetReadonly(boolean readonly)
  {
    sun.setEnabled(!readonly);
    mon.setEnabled(!readonly);
    tue.setEnabled(!readonly);
    wed.setEnabled(!readonly);
    thu.setEnabled(!readonly);
    fri.setEnabled(!readonly);
    fri.setEnabled(!readonly);
    sat.setEnabled(!readonly);
  }  

  protected void doLoadValue(BObject value, Context cx)
  {
    BDaysOfWeekBits b = (BDaysOfWeekBits)value;
    sun.setSelected((b.getBits() & BDaysOfWeekBits.SUNDAY) != 0);
    mon.setSelected((b.getBits() & BDaysOfWeekBits.MONDAY) != 0);
    tue.setSelected((b.getBits() & BDaysOfWeekBits.TUESDAY) != 0);
    wed.setSelected((b.getBits() & BDaysOfWeekBits.WEDNESDAY) != 0);
    thu.setSelected((b.getBits() & BDaysOfWeekBits.THURSDAY) != 0);
    fri.setSelected((b.getBits() & BDaysOfWeekBits.FRIDAY) != 0);
    sat.setSelected((b.getBits() & BDaysOfWeekBits.SATURDAY) != 0);
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    int bits = 0;
    if (sun.isSelected()) bits |= BDaysOfWeekBits.SUNDAY;
    if (mon.isSelected()) bits |= BDaysOfWeekBits.MONDAY;
    if (tue.isSelected()) bits |= BDaysOfWeekBits.TUESDAY;
    if (wed.isSelected()) bits |= BDaysOfWeekBits.WEDNESDAY;
    if (thu.isSelected()) bits |= BDaysOfWeekBits.THURSDAY;
    if (fri.isSelected()) bits |= BDaysOfWeekBits.FRIDAY;
    if (sat.isSelected()) bits |= BDaysOfWeekBits.SATURDAY;
    return BDaysOfWeekBits.make(bits);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
    
  static final String sunText = BWeekday.sunday.getShortDisplayTag(null);
  static final String monText = BWeekday.monday.getShortDisplayTag(null);
  static final String tueText = BWeekday.tuesday.getShortDisplayTag(null);
  static final String wedText = BWeekday.wednesday.getShortDisplayTag(null);
  static final String thuText = BWeekday.thursday.getShortDisplayTag(null);
  static final String friText = BWeekday.friday.getShortDisplayTag(null);
  static final String satText = BWeekday.saturday.getShortDisplayTag(null);

  private BCheckBox sun;
  private BCheckBox mon;
  private BCheckBox tue;
  private BCheckBox wed;
  private BCheckBox thu;
  private BCheckBox fri;
  private BCheckBox sat;
  
}
