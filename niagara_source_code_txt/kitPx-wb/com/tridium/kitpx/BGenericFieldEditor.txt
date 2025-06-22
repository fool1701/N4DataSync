/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BBrush;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.schema.Fw;

/**
 * BGenericFieldEditor is a specialized field editor which wraps
 * a specific BWbFieldEditor created during loadValue() based on the 
 * target object.  It's useful for building weakly typed px forms.
 *
 * @author    Brian Frank       
 * @creation  16 May 02
 * @version   $Revision$ $Date: 18-Dec-03 5:31:54 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Brush to use to render the editor foreground.  If set to
 BBrush.NULL then a context sensitive defaults fallback will be
 used.  This property has varying effects depending on what the
 editor is bound to (in some cases it may do nothing at all).
 */
@NiagaraProperty(
  name = "foreground",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
/*
 Brush to use to render the editor background.  If set to
 BBrush.NULL then a context sensitive defaults fallback will be
 used.  This property has varying effects depending on what the
 editor is bound to (in some cases it may do nothing at all).
 */
@NiagaraProperty(
  name = "background",
  type = "BBrush",
  defaultValue = "BBrush.NULL"
)
public class BGenericFieldEditor
  extends BWbFieldEditor
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BGenericFieldEditor(405975712)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * Brush to use to render the editor foreground.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code foreground} property.
   * Brush to use to render the editor foreground.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #foreground
   */
  public BBrush getForeground() { return (BBrush)get(foreground); }

  /**
   * Set the {@code foreground} property.
   * Brush to use to render the editor foreground.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #foreground
   */
  public void setForeground(BBrush v) { set(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "background"

  /**
   * Slot for the {@code background} property.
   * Brush to use to render the editor background.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #getBackground
   * @see #setBackground
   */
  public static final Property background = newProperty(0, BBrush.NULL, null);

  /**
   * Get the {@code background} property.
   * Brush to use to render the editor background.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #background
   */
  public BBrush getBackground() { return (BBrush)get(background); }

  /**
   * Set the {@code background} property.
   * Brush to use to render the editor background.  If set to
   * BBrush.NULL then a context sensitive defaults fallback will be
   * used.  This property has varying effects depending on what the
   * editor is bound to (in some cases it may do nothing at all).
   * @see #background
   */
  public void setBackground(BBrush v) { set(background, v, null); }

  //endregion Property "background"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BGenericFieldEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the actual editor being used inside of the generic wrapper.
   * Return null if an inner editor has not been initialized.
   */
  public BWbFieldEditor getInnerEditor()
  {               
    BWidget content = getContent();
    if (content instanceof BWbFieldEditor)
      return (BWbFieldEditor)content;
    else
      return null;
  }
  
////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {                    
    BWbFieldEditor inner = getInnerEditor();
    if (inner != null) inner.setReadonly(readonly);
  }

  protected void doLoadValue(BObject value, Context context)
    throws Exception
  {
    BWbFieldEditor inner = getInnerEditor();
    if (inner == null || 
        inner.getCurrentValue() == null || 
        inner.getCurrentValue().getClass() != value.getClass())
    {
      inner = makeFor(value, context);
      inner.setReadonly(isReadonly());
      
      // TODO I have seen a deadlock where the add() operation
      // called by this method blocks due to started() [26 Jan 05]
      linkTo(inner, actionPerformed, actionPerformed);
      linkTo(inner, setModified,     setModified);
      
      setContent(inner);
    }
    inner.loadValue(value, context);
    syncColors();
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException, Exception
  {
    BWbFieldEditor inner = getInnerEditor();
    if (inner != null) return inner.saveValue(value, cx);
    return value;
  }

////////////////////////////////////////////////////////////////
// Sync
////////////////////////////////////////////////////////////////

  public void changed(Property prop, Context cx)
  {
    if (prop == foreground || prop == background) syncColors();
    super.changed(prop, cx);
  }      
  
  void syncColors()
  {                      
    BWbFieldEditor editor = getInnerEditor();
    if (editor != null)
      editor.fw(Fw.UPDATE_COLORS, getForeground(), getBackground(), null, null);
  }

}
