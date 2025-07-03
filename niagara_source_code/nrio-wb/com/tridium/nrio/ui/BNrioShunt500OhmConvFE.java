/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import javax.baja.driver.point.BProxyConversion;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.BWbPlugin;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.driver.ui.point.BProxyConversionFE;
import com.tridium.nrio.conv.BNrioShunt500OhmConversion;

/**
 * BShunt500OhmConvFE
 *
 * @author    Bill Smith
 * @creation  9 Feb 05
 * @version   $Revision: 3$ $Date: 2/9/2005 5:49:43 PM$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "nrio:NrioShunt500OhmConversion"
  )
)
@NiagaraAction(
  name = "proxyListChanged"
)
public class BNrioShunt500OhmConvFE
  extends BWbFieldEditor
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioShunt500OhmConvFE(2406207397)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "proxyListChanged"

  /**
   * Slot for the {@code proxyListChanged} action.
   * @see #proxyListChanged()
   */
  public static final Action proxyListChanged = newAction(0, null);

  /**
   * Invoke the {@code proxyListChanged} action.
   * @see #proxyListChanged
   */
  public void proxyListChanged() { invoke(proxyListChanged, null, null); }

  //endregion Action "proxyListChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioShunt500OhmConvFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BNrioShunt500OhmConvFE()
  {    
    linkTo("lk0", proxyConversionFE, BWbPlugin.pluginModified, setModified);
    linkTo("lk1", proxyConversionFE, BWbPlugin.actionPerformed, actionPerformed);
    linkTo("lk2", proxyConversionFE, BProxyConversionFE.listChanged, proxyListChanged);
    BGridPane grid = new BGridPane(1);
    grid.add(null, proxyConversionFE);
    grid.setStretchColumn(0);
    grid.setColumnAlign(BHalign.fill);

    
    setContent(grid);
  }

////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    proxyConversionFE.setReadonly(readonly);
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    BNrioShunt500OhmConversion conversion = ( BNrioShunt500OhmConversion) value;                                
    proxyConversionFE.loadValue(conversion.getSubConversion(), cx);
  }

  protected BObject doSaveValue(BObject value, Context cx)        
    throws Exception
  {         
    return BNrioShunt500OhmConversion.make((BProxyConversion) proxyConversionFE.saveValue());
  }
  
  public void doProxyListChanged()
  {
    try
    {
      getParentWidget().getParentWidget().relayout(); 
    }
    catch(Exception e){}
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BNrioProxyConversionFE proxyConversionFE = new BNrioProxyConversionFE();
}
