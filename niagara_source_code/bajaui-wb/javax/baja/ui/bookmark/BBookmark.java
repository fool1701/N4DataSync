/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.bookmark;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.util.*;

/**
 * BBookmark describes a bookmark.
 *
 * @author    Andy Frank
 * @creation  02 Dec 02
 * @version   $Revision: 8$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The ord that points to the bookmark location.
 */
@NiagaraProperty(
  name = "bookmarkOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT"
)
public class BBookmark
  extends BBookmarkItem
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.bookmark.BBookmark(3206182340)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "bookmarkOrd"

  /**
   * Slot for the {@code bookmarkOrd} property.
   * The ord that points to the bookmark location.
   * @see #getBookmarkOrd
   * @see #setBookmarkOrd
   */
  public static final Property bookmarkOrd = newProperty(0, BOrd.DEFAULT, null);

  /**
   * Get the {@code bookmarkOrd} property.
   * The ord that points to the bookmark location.
   * @see #bookmarkOrd
   */
  public BOrd getBookmarkOrd() { return (BOrd)get(bookmarkOrd); }

  /**
   * Set the {@code bookmarkOrd} property.
   * The ord that points to the bookmark location.
   * @see #bookmarkOrd
   */
  public void setBookmarkOrd(BOrd v) { set(bookmarkOrd, v, null); }

  //endregion Property "bookmarkOrd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBookmark.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default Constructor.
   */
  public BBookmark() 
  {
    setText(UiLexicon.bajaui().getText("bookmarks.bookmark"));
  }

  /**
   * Create a new Bookmark.
   */
  public BBookmark(String displayName)
  {
    setText(displayName);
  }

  /**
   * Create a new Bookmark.
   */
  public BBookmark(String displayName, BOrd bookmark)
  {
    setText(displayName);
    setBookmarkOrd(bookmark);
  }

  /**
   * Create a new Bookmark.
   */
  public BBookmark(String displayName, BOrd bookmark, BIcon icon)
  {
    setText(displayName);
    setBookmarkOrd(bookmark);
    setIcon(icon);
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////
  
  public boolean isChildLegal(BComponent child) { return false; }
}
