/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.options;

import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BOptions stores data structures which store persistent options.
 *
 * @author    Brian Frank
 * @creation  24 Jul 01
 * @version   $Revision: 13$ $Date: 7/27/09 12:34:02 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraTopic(
  name = "optionsSaved"
)
public class BOptions
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BOptions(1776910854)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Topic "optionsSaved"

  /**
   * Slot for the {@code optionsSaved} topic.
   * @see #fireOptionsSaved
   */
  public static final Topic optionsSaved = newTopic(0, null);

  /**
   * Fire an event for the {@code optionsSaved} topic.
   * @see #optionsSaved
   */
  public void fireOptionsSaved(BValue event) { fire(optionsSaved, event, null); }

  //endregion Topic "optionsSaved"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  /**
   * Access to the OptionsManager when a station is not running, otherwise returns null.
   */
  public static OptionsManager getOptionsManager()
  {                        
    BWidgetApplication app = BWidget.getApplication();
    if(app == null)
    {
      return null;
    }
    else
    {
      return app.getOptionsManager();
    }
  }
  
  /**
   * If an OptionsManager is available, obtain the Options specified by the Type.
   * If an OptionsManager is not available, create a new default instance from the Type.
   */
  public static BOptions load(Type type)
  {                                                    
    OptionsManager manager = getOptionsManager();
    if(manager != null)
    {
      return manager.load(type);
    }
    return loadDefaultInstance(type);
  }

  /**
   * If an OptionsManager is available, obtain the Options specified by the Type and name
   * If an OptionsManager is not available, create a new default instance from the Type.
   */
  public static BOptions load(String name, Type type)
  {                                                    
    OptionsManager manager = getOptionsManager();
    if(manager != null)
    {
      return manager.load(name, type);
    }
    return loadDefaultInstance(type);
  }

  /**
   * Convenience for <code>getOptionsManager().saveOptions(this)</code>.  
   */
  public void save()
  {
    getOptionsManager().save(this);  
    fireOptionsSaved(null);
  }

  /**
   * Create a new instance of the BOptions for the specified type and call BOptions.loaded on that new instance.
   * @param type
   * @return
   * @since Niagara 4.6
   */
  private static BOptions loadDefaultInstance(Type type)
  {
    BOptions options = (BOptions)type.getInstance();
    try
    {
      options.loaded();
    }
    catch (Exception ignore)
    {}
    return options;
  }


////////////////////////////////////////////////////////////////
// Callback
////////////////////////////////////////////////////////////////

  /**
   * Callback after the options have been loaded.
   *
   * @since Niagara 4.4
   */
  public void loaded()
    throws Exception
  {}

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() 
  { 
    return getType().getTypeInfo().getIcon(null); 
  }
  
}
