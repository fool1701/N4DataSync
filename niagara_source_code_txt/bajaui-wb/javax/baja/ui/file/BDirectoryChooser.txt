/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.file;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.ui.*;

import com.tridium.ui.*;

/**
 * DirectoryChooser allows the user to browse a given
 * file system and select a directory.
 *
 * @author    Andy Frank
 * @creation  19 May 04
 * @version   $Revision: 42$ $Date: 1/26/10 10:06:43 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BDirectoryChooser
  extends BDialog
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.file.BDirectoryChooser(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDirectoryChooser.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Make and show a DirectoryChooser to select a directory.
   * Return the directory selected, or null if dialog was
   * cancelled.
   */
  public static BOrd show(BWidget owner)
  {
    BDirectoryChooser chooser = make(owner);
    return chooser.show();
  }
  
  /**
   * Make a DirectoryChooser to select a directory.
   */
  public static BDirectoryChooser make(BWidget owner)
  {
    return UiEnv.get().makeDirectoryChooser(owner);
  }  
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  protected BDirectoryChooser(BWidget owner, String title)
  {
    super(owner, title, true);
  } 

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Display a new BDirectoryChooser and return the ord of
   * the directory selected, or null if the dialog was cancelled.
   */
  public abstract BOrd show();

  /**
   * Get the current directory.
   */
  public abstract BOrd getCurrentDirectory();

  /**
   * Set the current directory.
   */
  public abstract void setCurrentDirectory(BOrd dir);

  /**
   * Return the current list of FileSpaces.
   */
  public abstract BSpace[] getSpaces();
    
  /**
   * Set the list of available FileSpaces.
   */
  public abstract void setSpaces(BSpace[] spaces);
}
