/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import javax.baja.gx.BColor;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;

import com.tridium.ui.theme.Theme;
import com.tridium.ui.theme.custom.nss.StyleUtils;
import com.tridium.workbench.shell.BJobStatusBar;
import com.tridium.workbench.shell.WbCommands;

/**
 * BWbStatusBar is used to display status information in a WbShell.
 *
 * @author    Brian Frank       
 * @creation  21 Jul 04
 * @version   $Revision: 4$ $Date: 12/7/10 10:14:44 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWbStatusBar
  extends BEdgePane   
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbStatusBar(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbStatusBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Framework use only
   */
  public BWbStatusBar()
  {
    throw new IllegalStateException();
  }
  
  /**
   * Constructor.
   */
  public BWbStatusBar(BWbShell shell)
  {    
    label = new BLabel(" ", BHalign.left);
    StyleUtils.addStyleClass(label, "statusBar");
    right = new BGridPane(2);
    right.setColumnGap(1);
    right.add("supplement", new BNullWidget());
    right.add("job", new BJobStatusBar(shell));
    
    setLeft(new BBorderPane(label, 3, 3, 3, 3));
    setRight(right);
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * This callback is invoked when the status bar message
   * should be updated with the specified String.
   */
  public void showStatus(String msg)
  {                    
    // In order to keep the status bar height static, we need to make sure
    // the label isn't empty (which results in status bar height = 0)
    if (msg == null || msg.length() == 0) msg = " ";
    label.setText(msg);
  }                
  
  /**
   * This method is called to update the status bar with
   * a view specific supplement area.
   */
  public void setSupplement(BWidget widget)
  {
    right.set("supplement", widget);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BLabel label;
  BGridPane right;
}
