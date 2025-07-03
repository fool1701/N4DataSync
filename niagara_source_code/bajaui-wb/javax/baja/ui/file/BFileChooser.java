/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.file;

import javax.baja.file.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.ui.*;

import com.tridium.ui.*;

/**
 * FileChooser allows the user to browse a given
 * file system and select a file.
 *
 * @author    Andy Frank
 * @creation  19 May 04
 * @version   $Revision: 70$ $Date: 5/25/05 1:37:59 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BFileChooser
  extends BDialog
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.file.BFileChooser(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileChooser.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Make and show a FileChooser to open a file. Return
   * the file selected, or null if the dialog was cancelled.
   */
  public static BOrd showOpen(BWidget owner)
  {
    BFileChooser chooser = makeOpen(owner);
    return chooser.show();
  }
  
  /**
   * Make and show a FileChooser to save a file. Return
   * the file selected, or null if the dialog was cancelled.
   */
  public static BOrd showSave(BWidget owner)
  {
    BFileChooser chooser = makeSave(owner);
    return chooser.show();
  }
  
  /**
   * Make a FileChooser to open a file.
   */
  public static BFileChooser makeOpen(BWidget owner)
  {
    return UiEnv.get().makeOpenFileChooser(owner);
  }  
  
  /**
   * Make a FileChooser to save a file.
   */
  public static BFileChooser makeSave(BWidget owner)
  {
    return UiEnv.get().makeSaveFileChooser(owner);
  }  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  protected BFileChooser(BWidget owner, String title)
  {
    super(owner, title, true);
  } 

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Convience method for show(false) to return a single 
   * result. Returns null if dialog was cancelled.
   */
  public BOrd show()
  {
    BOrd[] ords = show(false);
    return (ords.length > 0) ? ords[0] : null;
  }
  
  /**
   * Display a new BFileChooser to open a file and return 
   * the selected ords.  If no nodes were selected, or dialog 
   * was cancelled, then return an empty array.
   */
  public abstract BOrd[] show(boolean multipleSelection);

  /**
   * Get the current directory.
   */
  public abstract BOrd getCurrentDirectory();

  /**
   * Set the current directory. If ord points to a
   * file, change to the file's directory and select the file.
   */
  public abstract void setCurrentDirectory(BOrd dir);

  /**
   * Set the default filename to open/save.  The filename
   * cannot contain any path information - only the filename.
   */
  public abstract void setDefaultFileName(String filename);  
  
  /**
   * Set whether or not BFileChooser creates the file
   * during a save operatoin.  Default is true.   
   */
  public abstract void setCreateFileOnSave(boolean create);
  
  /**
   * Set whether or not BFileChooser should prompt the user
   * to confirm an overwrite, from choosing an existing file
   * during a save operation. Default is true.
   */
  public abstract void setConfirmOverwrite(boolean confirm);

  /**
   * For file save dialogs, sets whether the filename extension should be updated
   * to match the extension of the currently selected extension file filter.
   *
   * @param updateFilenameExtensionFromFilter true if the filename extension should be updated
   *
   * @since Niagara 4.8
   */
  public void setUpdateFilenameExtensionFromFilter(boolean updateFilenameExtensionFromFilter)
  {
    throw new UnsupportedOperationException();
  }
  
  /**
   * Add the specifed filter.
   */
  public abstract void addFilter(IFileFilter filter);

  /**
   * Add the specified filters.
   */
  public abstract void addFilters(IFileFilter[] filters);

  /**
   * Remove all file filters.
   *
   * @since Niagara 4.8
   */
  public void clearFilters()
  {
    throw new UnsupportedOperationException();
  }

  /**
   * Return the current list of FileSpaces.
   */
  public abstract BSpace[] getSpaces();
  
  /**
   * Set the list of avaiable FileSpaces.
   */
  public abstract void setSpaces(BSpace[] spaces);
}
