/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.fieldeditor;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.pane.BGridPane;

/**
 * BComponentEventMaskFE is an editor for values of
 * BComponentEventMaskBits.
 *
 * @author    Lee Adcock
 * @creation  8 Apr 11
 * @version   $Revision: 1$ $Date: 5/25/11 11:37:30 AM EDT$
 * @since     Niagara 3.7
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:ComponentEventMask"
  )
)
public class BComponentEventMaskFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.fieldeditor.BComponentEventMaskFE(575385594)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComponentEventMaskFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    if (!init) initCheckBoxes(null);
    for(int i=0; i<checkboxes.length; i++)
      checkboxes[i].setEnabled(!readonly);
  }

  protected void doLoadValue(BObject value, Context cx)
  {
    BComponentEventMask b = (BComponentEventMask)value;
    if (!init) initCheckBoxes(cx);
    for(int i=0; i<checkboxes.length; i++)
      checkboxes[i].setSelected(b.includes(i));
  }

  protected BObject doSaveValue(BObject value, Context cx)
  {
    int bits = 0;
    for(int i=0; i<checkboxes.length; i++)
      if(checkboxes[i].getSelected())
        bits |= (0x01 << i);

    return BComponentEventMask.make(bits);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  private void initCheckBoxes(Context cx)
  {
    init = true;

    BGridPane pane = new BGridPane(1);
    setContent(pane);

    checkboxes = new BCheckBox[idStrings.length];
    for(int i=0; i<idStrings.length; i++)
    {
      checkboxes[i] = new BCheckBox(displayStrings[i]);
      pane.add(null, checkboxes[i], cx);
      linkTo(null, checkboxes[i], BCheckBox.actionPerformed, setModified);
    }
  }

////////////////////////////////////////////////////////////////
//Static initializer
////////////////////////////////////////////////////////////////

  static
  {
    String[] _idStrings = null;
    String[] _displayStrings = null;
    try
    {
      // Load the private id and display strings from BComponentEvent.
      _idStrings = BComponentEvent.getIdStrings();
      _displayStrings = BComponentEvent.getDisplayStrings();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if(_idStrings==null || _displayStrings==null)
    {
      idStrings = displayStrings = new String[] {};
    } else
    {
      idStrings = _idStrings;
      displayStrings = _displayStrings;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String[] idStrings;
  private static final String[] displayStrings;

  private boolean init = false;
  private BCheckBox[] checkboxes;
}
