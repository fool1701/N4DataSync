/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.sys.schema.Fw;

/**
 * BWbPlugin is a widget designed to provide plugin 
 * functionality in the workbench tool environment.
 *
 * @author    Brian Frank       
 * @creation  7 Jan 01
 * @version   $Revision: 19$ $Date: 8/15/07 3:46:56 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Default content is the null widget.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()",
  flags = Flags.TRANSIENT | Flags.HIDDEN
)
/*
 The setModified action sets the modified flag
 and fires the pluginModified event.
 */
@NiagaraAction(
  name = "setModified"
)
/*
 This event is fired when changes to the plugin's value
 are made by a user.  Programatic changes should never
 fire an plugin modified event.  The firePluginModified()
 method should never be called directly, rather use
 the setModified() method.
 */
@NiagaraTopic(
  name = "pluginModified",
  eventType = "BWidgetEvent"
)
/*
 Action performed indicates an action event in
 one of the plugin's sub-widgets.  It is usually
 used to provide an automatic commit of the
 changes (for example applying all the changes on
 a property sheet when the user hits Enter in a
 text field).
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public class BWbPlugin
  extends BWidget
  implements BIAgent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbPlugin(1832127255)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * Default content is the null widget.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(Flags.TRANSIENT | Flags.HIDDEN, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * Default content is the null widget.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * Default content is the null widget.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Action "setModified"

  /**
   * Slot for the {@code setModified} action.
   * The setModified action sets the modified flag
   * and fires the pluginModified event.
   * @see #setModified()
   */
  public static final Action setModified = newAction(0, null);

  /**
   * Invoke the {@code setModified} action.
   * The setModified action sets the modified flag
   * and fires the pluginModified event.
   * @see #setModified
   */
  public void setModified() { invoke(setModified, null, null); }

  //endregion Action "setModified"

  //region Topic "pluginModified"

  /**
   * Slot for the {@code pluginModified} topic.
   * This event is fired when changes to the plugin's value
   * are made by a user.  Programatic changes should never
   * fire an plugin modified event.  The firePluginModified()
   * method should never be called directly, rather use
   * the setModified() method.
   * @see #firePluginModified
   */
  public static final Topic pluginModified = newTopic(0, null);

  /**
   * Fire an event for the {@code pluginModified} topic.
   * This event is fired when changes to the plugin's value
   * are made by a user.  Programatic changes should never
   * fire an plugin modified event.  The firePluginModified()
   * method should never be called directly, rather use
   * the setModified() method.
   * @see #pluginModified
   */
  public void firePluginModified(BWidgetEvent event) { fire(pluginModified, event, null); }

  //endregion Topic "pluginModified"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Action performed indicates an action event in
   * one of the plugin's sub-widgets.  It is usually
   * used to provide an automatic commit of the
   * changes (for example applying all the changes on
   * a property sheet when the user hits Enter in a
   * text field).
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Action performed indicates an action event in
   * one of the plugin's sub-widgets.  It is usually
   * used to provide an automatic commit of the
   * changes (for example applying all the changes on
   * a property sheet when the user hits Enter in a
   * text field).
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbPlugin.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Convenience for <code>BWbShell.getWbShell(this)</code>.
   */
  public final BWbShell getWbShell()
  {                         
    return BWbShell.getWbShell(this);
  }
       
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  public void computePreferredSize()
  {
    BWidget c = getContent();
    c.computePreferredSize();
    setPreferredSize(c.getPreferredWidth(), c.getPreferredHeight());
  }

  public void doLayout(BWidget[] kids)
  {
    BWidget c = getContent();
    c.setBounds(0, 0, getWidth(), getHeight()); 
  }

////////////////////////////////////////////////////////////////
// Modifications
////////////////////////////////////////////////////////////////  

  /**
   * Has this plugin been modified by the user.  Changes
   * made programatically should not effect the modified
   * flag.
   */
  public final boolean isModified()
  {
    return modified;
  }
  
  /**
   * Is the plugin's modified state currently locked. 
   * While locked any calls to setModified() are ignored.   
   */
  public final boolean isModifiedStateLocked()
  {
    synchronized(monitor)
    {
      return modifiedStateLocked;
    }
  }
  
  /**
   * Lock the plugin's modified state so that further calls 
   * to setModified() are ignored until unlockModifiedState()
   * is called.  This is useful when making a programatic 
   * change, but you want to prevent normal event handling 
   * from firing a modified event.  The modified state for
   * BWbEditors is automatically locked during loadValue().
  */
  public final void lockModifiedState()
  {
    synchronized(monitor)
    {
      modifiedStateLocked = true;
    }
  }
  
  /**
   * Unlock the modified state so that calls to setModified
   * will set the modified flag and fire the modified event.
   */
  public final void unlockModifiedState()
  {                     
    synchronized(monitor)
    {
      modifiedStateLocked = false;
    }
  }
  
  /**
   * Set the modified flag to false.
   */
  public void clearModified()
  {
    synchronized(monitor)
    {
      modified = false;
    }
  }

  /**
   * Set this plugin as modified.  If the plugin is already
   * modified then nothing happens.  If the plugin is not
   * currently modified, then set the modified flag and fire
   * the pluginModified event.  If the plugin currently has
   * the modified state locked, then this call is ignored. The 
   * modified state is automatically locked during loadValue().
   */
  public final void doSetModified()
  {
    synchronized(monitor)
    {
      if (modifiedStateLocked) return;
      if (modified) return;
      modified = true;
    }
    
    // if this is the active plugin or if this 
    // is a PxView notify the shell
    BWidgetShell shell = getShell();
    if (shell instanceof BWbShell)
    {
      BWbShell wbShell = (BWbShell)shell;
      BWbPlugin view = wbShell.getActiveView();
      if (view == this)
        ((com.tridium.workbench.shell.BNiagaraWbShell)wbShell).setModified(this);
      else if (isInPxView(wbShell))
        view.setModified();
    }          
        
    firePluginModified(new BWidgetEvent(BWidgetEvent.MODIFIED, this));
  }
  
  /**
   * Fire the action performed event.
   */
  public void doPerformAction()
  {    
    fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, this));
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.FIRED: fwTopicFired((Topic)a, (BValue)b); break;
    }                                    
    return super.fw(x, a, b, c, d);
  }
    
  private void fwTopicFired(Topic topic, BValue event)
  {  
    // if action performed was fired
    if (topic == actionPerformed)
    {                               
      // if this is a PxView the do a save
      BWidgetShell shell = getShell();
      if (shell instanceof BWbShell)
      {                       
        BWbShell wbShell = (BWbShell)shell;
        if (isInPxView(wbShell))
          wbShell.getSaveCommand().invoke();
      }          
    }
  }  
  
  /**
   * Return if this plugin is being used in a PxView. Note
   * that this doesn't include a plugin inside a plugin inside 
   * a PxView.
   */
  private boolean isInPxView(BWbShell shell)                                 
  {                                                          
    BWbPlugin view = shell.getActiveView();
    if (view instanceof javax.baja.workbench.px.BWbPxView)
    {
      BComplex p = getParent();
      while(p != null)
      {
        if (p == view) return true;
        if (p instanceof BWbPlugin) return false;
        p = p.getParent();
      }
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  boolean modified;
  boolean modifiedStateLocked;
  Object monitor = new Object();
  
}
