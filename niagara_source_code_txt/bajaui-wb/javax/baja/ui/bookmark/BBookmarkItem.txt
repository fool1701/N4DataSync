/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.bookmark;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BBookmarkItem.
 *
 * @author    Andy Frank
 * @creation  11 Feb 03
 * @version   $Revision: 4$ $Date: 2/17/04 2:58:18 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The human readable name for this bookmark.
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = "BookmarkItem"
)
/*
 The ord to the icon to use for this bookmark.
 */
@NiagaraProperty(
  name = "iconOrd",
  type = "BOrdList",
  defaultValue = "BIcon.std(\"bookmark.png\").getOrdList()"
)
public class BBookmarkItem
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.bookmark.BBookmarkItem(443933586)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The human readable name for this bookmark.
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "BookmarkItem", null);

  /**
   * Get the {@code text} property.
   * The human readable name for this bookmark.
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The human readable name for this bookmark.
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "iconOrd"

  /**
   * Slot for the {@code iconOrd} property.
   * The ord to the icon to use for this bookmark.
   * @see #getIconOrd
   * @see #setIconOrd
   */
  public static final Property iconOrd = newProperty(0, BIcon.std("bookmark.png").getOrdList(), null);

  /**
   * Get the {@code iconOrd} property.
   * The ord to the icon to use for this bookmark.
   * @see #iconOrd
   */
  public BOrdList getIconOrd() { return (BOrdList)get(iconOrd); }

  /**
   * Set the {@code iconOrd} property.
   * The ord to the icon to use for this bookmark.
   * @see #iconOrd
   */
  public void setIconOrd(BOrdList v) { set(iconOrd, v, null); }

  //endregion Property "iconOrd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBookmarkItem.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get icon.
   */
  public BIcon getIcon()
  {
    if (icon == null) icon = BIcon.make(getIconOrd());
    return icon;
  }
  private BIcon icon = null;

  /**
   * Convience for <code>setIconOrd(icon.getOrdList())</code>.
   */
  public void setIcon(BIcon icon)
  {
    setIconOrd(icon.getOrdList());
  }
}
