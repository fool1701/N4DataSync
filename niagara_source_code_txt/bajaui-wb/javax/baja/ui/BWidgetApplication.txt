/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.options.*;

/**
 * BWidgetApplication is the root of the widget namespace.  
 * Its children are the open BWidgetShells.  You can get a 
 * reference to the WidgetApplication using the static
 * method: <code>BWidget.getApplication()</code>.
 *
 * @author    Brian Frank
 * @creation  7 Jul 01
 * @version   $Revision: 8$ $Date: 7/27/05 3:01:49 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWidgetApplication
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BWidgetApplication(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWidgetApplication.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Shells
////////////////////////////////////////////////////////////////

  /**
   * Get the list of all open BWidgetShells.
   */
  public BWidgetShell[] getShells()
  {
    BWidgetShell[] temp = new BWidgetShell[getSlotCount()];
    int count = 0;

    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject kid = c.get();
      if (kid instanceof BWidgetShell)
        temp[count++] = (BWidgetShell)kid;
    }

    BWidgetShell[] result = new BWidgetShell[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;    
  }

  /**
   * Enter a section of code which could potentially
   * require a lengthy amount of time.  This changes
   * the every shell's cursor to a busy cursor, and may 
   * also provide additional visual feedback to the user
   * that the shell is "working".  It is critical that
   * the caller of this method also call exitBusy() the
   * same number of times that enterBusy was called.
   */
  public void enterBusy()
  {
    BWidgetShell[] shells = getShells();
    for(int i=0; i<shells.length; ++i)
      shells[i].enterBusy();
  }
  
  /**
   * Exit a busy section of code.  This call should be
   * called exactly once for every call to enterBusy.
   */
  public void exitBusy()
  {
    BWidgetShell[] shells = getShells();
    for(int i=0; i<shells.length; ++i)
      shells[i].exitBusy();
  }

////////////////////////////////////////////////////////////////
// Options
////////////////////////////////////////////////////////////////

  /**
   * Get the OptionsManager used to manage persistent options.
   */
  public abstract OptionsManager getOptionsManager();
    
}
