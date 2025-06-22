/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryExport defines an archive action for transferring
 * one or more histories from the local source to a remote destination.
 *
 * @author    John Sublett
 * @creation  31 Mar 2003
 * @version   $Revision: 9$ $Date: 5/19/09 2:54:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BHistoryExport
  extends BArchiveDescriptor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BHistoryExport(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryExport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHistoryExport()
  {
  }

  /**
   * The specified parent is only legal if it or one
   * of its ancestors is an IArchiveFolder that
   * supports export.
   */
  public boolean isParentLegal(BComponent parent)
  {
    while ((parent != null) && !(parent instanceof BIArchiveFolder))
      parent = (BComponent)parent.getParent();
    
    if (parent == null) return false;
    
    try
    {
      return ((BIArchiveFolder)parent).getExportDescriptorType() != null;
    }
    catch(IllegalStateException e) { return true; }
  }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make
    ("module://driver/com/tridium/driver/ui/history/exportHistory.png");
}
