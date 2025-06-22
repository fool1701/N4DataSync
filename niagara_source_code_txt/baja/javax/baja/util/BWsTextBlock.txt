/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BWsTextBlock is a Niagara specific component used to visualize
 * text blocks on a WireSheet.
 *
 * @author    Brian Frank
 * @creation  29 Oct 03
 * @version   $Revision: 5$ $Date: 2/4/08 10:12:28 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The text to display in the block
 */
@NiagaraProperty(
  name = "text",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE)")
)
/*
 String encoding of foreground color (see gx:Color)
 */
@NiagaraProperty(
  name = "foreground",
  type = "String",
  defaultValue = "#ff000000",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ColorFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"gx:ColorEditor\"))")
)
/*
 String encoding of background color (see gx:Color)
 */
@NiagaraProperty(
  name = "background",
  type = "String",
  defaultValue = "#00000000",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ColorFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"gx:ColorEditor\"))")
)
/*
 String encoding of text font (see gx:Font)
 */
@NiagaraProperty(
  name = "font",
  type = "String",
  defaultValue = "12pt Arial",
  facets = @Facet("BFacets.make(BFacets.make(BFacets.FIELD_EDITOR,\"workbench:FontFE\"),BFacets.make(BFacets.UX_FIELD_EDITOR,\"gx:FontEditor\"))")
)
/*
 Specified whether a border should be drawn using foreground color.
 */
@NiagaraProperty(
  name = "border",
  type = "boolean",
  defaultValue = "false"
)
/*
 Allows the block to be selectable or unselectable on wire sheet.
 */
@NiagaraProperty(
  name = "selectable",
  type = "boolean",
  defaultValue = "true"
)
/*
 Positioning on the wiresheet.
 */
@NiagaraProperty(
  name = "wsAnnotation",
  type = "BWsAnnotation",
  defaultValue = "BWsAnnotation.make(1, 1, 20, 2)",
  flags = Flags.READONLY
)
public class BWsTextBlock
  extends BComponent
{                                                      
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BWsTextBlock(912248871)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "text"

  /**
   * Slot for the {@code text} property.
   * The text to display in the block
   * @see #getText
   * @see #setText
   */
  public static final Property text = newProperty(0, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code text} property.
   * The text to display in the block
   * @see #text
   */
  public String getText() { return getString(text); }

  /**
   * Set the {@code text} property.
   * The text to display in the block
   * @see #text
   */
  public void setText(String v) { setString(text, v, null); }

  //endregion Property "text"

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * String encoding of foreground color (see gx:Color)
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, "#ff000000", BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ColorFE"), BFacets.UX_FIELD_EDITOR, BString.make("gx:ColorEditor")));

  /**
   * Get the {@code foreground} property.
   * String encoding of foreground color (see gx:Color)
   * @see #foreground
   */
  public String getForeground() { return getString(foreground); }

  /**
   * Set the {@code foreground} property.
   * String encoding of foreground color (see gx:Color)
   * @see #foreground
   */
  public void setForeground(String v) { setString(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * String encoding of background color (see gx:Color)
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, "#00000000", BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ColorFE"), BFacets.UX_FIELD_EDITOR, BString.make("gx:ColorEditor")));

  /**
   * Get the {@code background} property.
   * String encoding of background color (see gx:Color)
   * @see #background
   */
  public String getBackground() { return getString(background); }

  /**
   * Set the {@code background} property.
   * String encoding of background color (see gx:Color)
   * @see #background
   */
  public void setBackground(String v) { setString(background, v, null); }

  //endregion Property "background"

  //region Property "font"

  /**
   * Slot for the {@code font} property.
   * String encoding of text font (see gx:Font)
   * @see #getFont
   * @see #setFont
   */
  public static final Property font = newProperty(0, "12pt Arial", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR,"workbench:FontFE"),BFacets.make(BFacets.UX_FIELD_EDITOR,"gx:FontEditor")));

  /**
   * Get the {@code font} property.
   * String encoding of text font (see gx:Font)
   * @see #font
   */
  public String getFont() { return getString(font); }

  /**
   * Set the {@code font} property.
   * String encoding of text font (see gx:Font)
   * @see #font
   */
  public void setFont(String v) { setString(font, v, null); }

  //endregion Property "font"

  //region Property "border"

  /**
   * Slot for the {@code border} property.
   * Specified whether a border should be drawn using foreground color.
   * @see #getBorder
   * @see #setBorder
   */
  public static final Property border = newProperty(0, false, null);

  /**
   * Get the {@code border} property.
   * Specified whether a border should be drawn using foreground color.
   * @see #border
   */
  public boolean getBorder() { return getBoolean(border); }

  /**
   * Set the {@code border} property.
   * Specified whether a border should be drawn using foreground color.
   * @see #border
   */
  public void setBorder(boolean v) { setBoolean(border, v, null); }

  //endregion Property "border"

  //region Property "selectable"

  /**
   * Slot for the {@code selectable} property.
   * Allows the block to be selectable or unselectable on wire sheet.
   * @see #getSelectable
   * @see #setSelectable
   */
  public static final Property selectable = newProperty(0, true, null);

  /**
   * Get the {@code selectable} property.
   * Allows the block to be selectable or unselectable on wire sheet.
   * @see #selectable
   */
  public boolean getSelectable() { return getBoolean(selectable); }

  /**
   * Set the {@code selectable} property.
   * Allows the block to be selectable or unselectable on wire sheet.
   * @see #selectable
   */
  public void setSelectable(boolean v) { setBoolean(selectable, v, null); }

  //endregion Property "selectable"

  //region Property "wsAnnotation"

  /**
   * Slot for the {@code wsAnnotation} property.
   * Positioning on the wiresheet.
   * @see #getWsAnnotation
   * @see #setWsAnnotation
   */
  public static final Property wsAnnotation = newProperty(Flags.READONLY, BWsAnnotation.make(1, 1, 20, 2), null);

  /**
   * Get the {@code wsAnnotation} property.
   * Positioning on the wiresheet.
   * @see #wsAnnotation
   */
  public BWsAnnotation getWsAnnotation() { return (BWsAnnotation)get(wsAnnotation); }

  /**
   * Set the {@code wsAnnotation} property.
   * Positioning on the wiresheet.
   * @see #wsAnnotation
   */
  public void setWsAnnotation(BWsAnnotation v) { set(wsAnnotation, v, null); }

  //endregion Property "wsAnnotation"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWsTextBlock.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("text.png");  

}
