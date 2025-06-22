/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.popup;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.BWbEditor;

/**
 * The popup editor interface is implemented by classes that will be used for
 * rounded window popup displays in the workbench. 
 *
 * @author J. Spangler
 * @creation Apr 17, 2012
 *
 */
@NiagaraType
public interface BIPopupEditor
    extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.popup.BIPopupEditor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIPopupEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  

  /**
   * This method returns the {@link BWbEditor} displayed in the rounded popup
   * display.
   * 
   * @return {@link BWbEditor}
   */
  public BWbEditor getEditor();

}
