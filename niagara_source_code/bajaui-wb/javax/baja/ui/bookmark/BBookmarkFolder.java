/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.bookmark;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.util.*;

/**
 * BBookmarkFolder contains BBookmarkItems.
 *
 * @author    Andy Frank
 * @creation  06 Feb 03
 * @version   $Revision: 5$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BBookmarkFolder
  extends BBookmarkItem
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.bookmark.BBookmarkFolder(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBookmarkFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBookmarkFolder() 
  {
    setText(UiLexicon.bajaui().getText("bookmarks.folder"));
    setIcon(defaultIcon);
  }

  public BBookmarkFolder(String displayName)
  {
    setText(displayName);
    setIcon(defaultIcon);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public boolean isChildLegal(BComponent child)
  {
    return (child instanceof BBookmarkItem);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BIcon defaultIcon = BIcon.std("bookmarkFolder.png");
}
