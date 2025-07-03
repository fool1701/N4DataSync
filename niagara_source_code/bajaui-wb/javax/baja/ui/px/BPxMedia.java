/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;

/**
 * BPxMedia is used to define environments where PX 
 * customization is required while using the PxEditor.
 * 
 * @author    Brian Frank
 * @creation  14 Oct 04
 * @version   $Revision: 1$ $Date: 10/14/04 12:06:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BPxMedia
  extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.px.BPxMedia(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxMedia.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Return if the specified widget type is supported 
   * by the target media.  Default is to return true.
   */
  public boolean isWidgetSupported(TypeInfo type)
  {
    return true;
  }

  /**
   * Return if the specified widget type is supported 
   * by the target media.  Default is to return true.
   */
  public boolean isBindingSupported(TypeInfo type)
  {
    return true;
  }

  /**
   * Return a non-null Localized String if something in the widget's configuration is not configured correctly
   * to work with the target media. To show multiple warnings separate them with a newline character for best display
   * results.
   * @since Niagara 4.2
   */
  public String validateWidget(BWidget widget, Context cx)
  {
    return null;
  }

  /**
   * Return a non-null Localized String if something in the binding's configuration is not configured correctly
   * to work with the target media. To show multiple warnings separate them with a newline character for best display
   * results.
   * @since Niagara 4.2
   */
  public String validateBinding(BBinding binding, Context cx)
  {
    return null;
  }

  /**
  * Return the child widgets that require validation. Usually this returns all the widgets, but
  * if the media doesn't render the widget children for this media, then return a subset of the widgets.
  * @since Niagara 4.2
  */
  public BWidget[] getChildWidgetsToValidate(BWidget widget, Context cx)
  {
    return widget.getChildWidgets();
  }

  /**
   * Return false to skip validation of a child. Usually this returns true for all widgets, but
   * if the media doesn't render the widget child, then return false. This method only gets calls for the
   * children that have returned from `getChildWidgetsToValidate()`. This `isChildWidgetValidationRequired` method allows modules
   * without knowledge of BWidget to provide input into the validation process.
   * @since Niagara 4.10
   */
  public boolean isChildWidgetValidationRequired(BComponent widget, BComponent childWidget, Context cx)
  {
    return true;
  }

  /**
   * Return false to skip validation of a binding. Usually this returns true for all bindings, but
   * if the media doesn't require the binding to work, then return false.
   * @since Niagara 4.10
   */
  public boolean isBindingValidationRequired(BComponent widget, BComponent binding, Context cx)
  {
    return true;
  }

  /**
   * Return the file that is used to create 
   * new PX drawings.  Default returns
   * BOrd.make("file:!defaults/workbench/newfiles/PxFile.px").
   */
  public BOrd getPxFileOrd()
  {
    return DEFAULT_PX_FILE;
  }

  public static final BOrd DEFAULT_PX_FILE = BOrd.make("file:!defaults/workbench/newfiles/PxFile.px");
}
