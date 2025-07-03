/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.bookmark;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.options.*;
import javax.baja.ui.util.*;

/**
 * BBookmarkOptions holds bookmarks.
 *
 * @author    Andy Frank
 * @creation  18 Mar 03
 * @version   $Revision: 6$ $Date: 3/28/05 10:32:23 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The root folder for all bookmarks.
 */
@NiagaraProperty(
  name = "rootFolder",
  type = "BBookmarkFolder",
  defaultValue = "new BBookmarkFolder(UiLexicon.bajaui().getText(\"bookmarks.bookmark\"))"
)
public class BBookmarkOptions
  extends BOptions
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.bookmark.BBookmarkOptions(376757262)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "rootFolder"

  /**
   * Slot for the {@code rootFolder} property.
   * The root folder for all bookmarks.
   * @see #getRootFolder
   * @see #setRootFolder
   */
  public static final Property rootFolder = newProperty(0, new BBookmarkFolder(UiLexicon.bajaui().getText("bookmarks.bookmark")), null);

  /**
   * Get the {@code rootFolder} property.
   * The root folder for all bookmarks.
   * @see #rootFolder
   */
  public BBookmarkFolder getRootFolder() { return (BBookmarkFolder)get(rootFolder); }

  /**
   * Set the {@code rootFolder} property.
   * The root folder for all bookmarks.
   * @see #rootFolder
   */
  public void setRootFolder(BBookmarkFolder v) { set(rootFolder, v, null); }

  //endregion Property "rootFolder"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBookmarkOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the BBookmarkOptions.
   */
  public static BBookmarkOptions make()
  {
    if (options == null)
      options = (BBookmarkOptions)load(BBookmarkOptions.TYPE);
    return options;

  }
  private static BBookmarkOptions options;
}
