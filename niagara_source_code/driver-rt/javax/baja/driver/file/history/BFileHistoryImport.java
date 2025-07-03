/*
 * Copyright 2006, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.file.history;

import javax.baja.driver.file.BIFileDevice;
import javax.baja.driver.history.BHistoryImport;
import javax.baja.file.BIFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFileHistoryImport defines an archive action for transferring
 * a history from a file to this station.  It will use the parent
 * BIFileDevice to resolve the file it needs to import.
 *
 * @author    Scott Hoye
 * @creation  06 Apr 06
 * @version   $Revision: 1$ $Date: 5/16/06 12:40:34 PM EDT$
 * @since     Baja 3.1
 */
@NiagaraType
/*
 The ord to the file to be imported.
 */
@NiagaraProperty(
  name = "file",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, \"baja:IFile\")")
)
public abstract class BFileHistoryImport
  extends BHistoryImport
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.file.history.BFileHistoryImport(848428931)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "file"

  /**
   * Slot for the {@code file} property.
   * The ord to the file to be imported.
   * @see #getFile
   * @see #setFile
   */
  public static final Property file = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "baja:IFile"));

  /**
   * Get the {@code file} property.
   * The ord to the file to be imported.
   * @see #file
   */
  public BOrd getFile() { return (BOrd)get(file); }

  /**
   * Set the {@code file} property.
   * The ord to the file to be imported.
   * @see #file
   */
  public void setFile(BOrd v) { set(file, v, null); }

  //endregion Property "file"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileHistoryImport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

/////////////////////////////////////////////////////////////////
// EXECUTION
/////////////////////////////////////////////////////////////////

  /**
   * Execution time.  Resolves the file by calling back
   * to the parent BIFileDevice, and then uses the resolved
   * BIFile to call executeFileImport.
   */
  public void doExecute()
  {
    try
    {
      executeFileImport(((BIFileDevice)getDevice()).resolveFile(getFile()));
    }
    catch (Exception e)
    {
      executeFail(e);
    }
  }
    
  /**
   * Import the history from the target file.
   */
  public abstract void executeFileImport(BIFile file);

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the default icon for a file history import.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make(BIcon.std("file.png"),
                                               BIcon.std("badges/import.png"));
  
}
