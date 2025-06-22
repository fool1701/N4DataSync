/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.history;

import javax.baja.driver.util.BDescriptorDeviceExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryDeviceExt is the base class for mapping historical
 * data in a device to Baja history databases.
 *
 * @author    John Sublett
 * @creation  17 Oct 01
 * @version   $Revision: 9$ $Date: 5/19/09 2:54:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BHistoryDeviceExt
  extends BDescriptorDeviceExt
  implements BIArchiveFolder
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BHistoryDeviceExt(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryDeviceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

///////////////////////////////////////////////////////////
// Lifecycle
///////////////////////////////////////////////////////////

  /**
   * Get the Type for import descriptors managed by this extension.
   * If null, then the extension does not support imports.
   *
   * @return Returns the protocol specific import descriptor type
   *   or null if this extension does not support history imports.
   */
  public Type getImportDescriptorType()
  {
    return null;
  }

  /**
   * Get the Type for export descriptors managed by this extension.
   * If null, then the extension does not support exports.
   *
   * @return Returns the protocol specific export descriptor type
   *   or null if this extension does not support history exports.
   */
  public Type getExportDescriptorType()
  {
    return null;
  }
  
  /**
   * Return this.
   *
   * @since Niagara 3.5
   */
  public BHistoryDeviceExt getDeviceExt()
  {
    return this;
  }
  
  /**
   * Get the type of ArchiveFolder for this driver.
   * The default implementation returns the generic
   * BArchiveFolder type.
   *
   * @since Niagara 3.5
   */
  public Type getArchiveFolderType()
  {
    return BArchiveFolder.TYPE;
  }
  
  /**
   * The BArchiveFolder implementation of BIArchiveFolder can be
   * generically used by all drivers if they don't wish to create
   * their own subclasses of BIArchiveFolder.  In those cases, they may 
   * wish to override this method in their BHistoryDeviceExt subclass to tell the
   * BArchiveFolder to take on the agents (ie. Manager view) of the
   * BHistoryDeviceExt subclass.  This can save the need for more BIArchiveFolder
   * subclasses for each driver, however the agents inherited must
   * be able to handle a BArchiveFolder target (ie. the Manager views
   * cannot assume the loadValue target is a BHistoryDeviceExt subclass,
   * they must be careful to resolve the BHistoryDeviceExt for all possible
   * cases).
   *
   * The default return value for this method is false, meaning that the
   * parent BHistoryDeviceExt's agents won't be inherited.  Subclasses
   * may wish to override this method to true if their manager views can
   * handle the generic BArchiveFolder case.
   *
   * @since Niagara 3.5
   */
  public boolean supportsGenericArchiveFolder()
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Display
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
//  private static final BIcon icon = BIcon.make
//    ("module://driver/com/tridium/driver/ui/history/archive.png");
  private static final BIcon icon = BIcon.std("navOnly/histories.png");

  //private boolean archiveLocked = false;
  //private Object archiveLock = new Object();
}
