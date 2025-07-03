/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.fieldeditor;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.gx.BSize;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BExpandablePane;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.BWbProfile;
import javax.baja.workbench.BWbShell;

import com.tridium.util.CompUtil;
import com.tridium.workbench.shell.WbMain;

/**
 * BWbFieldEditor is an editor designed to view and edit a BObject on a field
 * sheet. Field editors are designed to be laid out on BPanes and are built with
 * standard widgets like BButtons, BTextFields, BCheckBoxes, etc.
 * <p>
 * Beginning in Niagara 3.5, the field editor will check the context used during
 * {@link #loadValue(BObject, Context)} to see if any
 * {@link javax.baja.util.BIValidator BIValidators} were declared. When
 * {@link #saveValue(BObject, Context)} is called, the validators will be
 * invoked on the saved value to check and see if the saved value is valid.
 * 
 * @author Brian Frank
 * @creation 16 May 02
 * @version $Revision: 14$ $Date: 7/19/10 8:38:37 AM EDT$
 * @since Baja 1.0
 */
@NiagaraType
public abstract class BWbFieldEditor
  extends BWbEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.fieldeditor.BWbFieldEditor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbFieldEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>makeFor(obj, null)</code>.
   */
  public static BWbFieldEditor makeFor(BObject obj)
  {                                               
    return makeFor(obj, null);
  }

  /**
   * Return the registered BWbFieldEditor agent for the
   * specified obj, or throw NoSuchAgentException.  If
   * the context contains the the facet FIELD_EDITOR, then
   *  use that type explicitly.
   */
   
  public static BWbFieldEditor makeFor(BObject obj, Context cx)
  {
    return makeFor(obj, cx, null);
  }
   
  public static BWbFieldEditor makeFor(BObject obj, Context cx, BWbShell shell)
  {  
    if (cx != null && cx.getFacet(BFacets.FIELD_EDITOR) != null)
    {
      String typeSpec = cx.getFacet(BFacets.FIELD_EDITOR).toString();
      return (BWbFieldEditor)Sys.getRegistry().getType(typeSpec).getInstance();
    }
    
    //get our field editor agents from our object
    AgentList agents = obj.getAgents().filter(FEAgentFilter);
    if( agents.size() == 0)
      throw new LocalizableRuntimeException("workbench","errorpanel.nofieldeditors", new Object[]{obj.getType()});
    
    agents = obj.getAgents().filter(getAgentFilter(shell));
    if( agents.size() == 0)
      throw new LocalizableRuntimeException("workbench","errorpanel.fieldeditorrestricted",new Object[]{obj.getType()});

    //return our default agent in our list of FE agents
    return (BWbFieldEditor)agents.getDefault().getInstance();
  }
    
////////////////////////////////////////////////////////////////
// Dialog
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>dialog(parent, title, target, null)</code>.
   */
  public static BObject dialog(BWidget parent, String title, BObject target)
    throws Exception
  {
    return dialog(parent, title, target, null);
  }
  
  /**
   * Create a field editor to edit the specified target 
   * object in a dialog.
   *
   * @return null if the user cancels the dialog, or the
   *    object (potentially modified) if they hit OK.
   */
  public static BObject dialog(BWidget parent, String title, BObject target, Context loadContext)
    throws Exception
  {
    BWbFieldEditor editor = makeFor(target, loadContext);
    editor.loadValue(target, loadContext);
    
    //Issue 10999, if the target has nothing but hidden slots, it is 
    //unnecessary to open a dialog for it--return the object immediately
    //in that case.
    boolean showDialog = false;
    
    if (target instanceof BComplex)
    {
      //However, if the object has a special type registered on it,
      //always show the dialog.
      if (defaultEditor != null && !editor.getType().equals(defaultEditor.getType()))
      {
        showDialog = true;
      }
      else
      {     
        //If target has at least one property that is not hidden, then
        //show the dialog.
        BComplex asComplex = target.asComplex();
        Slot current = null;
        for (SlotCursor<Slot> c = asComplex.loadSlots().getSlots(); c.next();)
        {
          current = c.slot();        
          if (current.isProperty() && ((asComplex.getFlags(current) & Flags.HIDDEN) == 0))
          {
            showDialog = true;
          }
        }
      }
    }
    else
    {
      showDialog = true;
    }
    
    if (showDialog)
    {
      BWidget content = editor;
      if(content instanceof IDialogContentProvider)
      {
        content = ((IDialogContentProvider) content).getDialogContent();
      }
      int result = BDialog.open(parent, title, content, BDialog.OK_CANCEL);
      if (result == BDialog.CANCEL) return null;
    }
    
    return editor.saveValue();
  }

  /**
   * The interface allows a BWbFieldEditor to control how it is initially seen in a dialog when its used
   * with BWbFieldEditor#dialog. Dialogs compute their preferred size when they are launched, so this callback is
   * a good opportunity to either expand your WbFieldEditor to its max size for a better preferred size, or you can use
   * this callback to wrap your field editor in a ScrollPane and/or a ConstrainedPane to provide better dialog experience
   * for your WbFieldEditor.
   *
   * @since Niagara 4.13
   * @see BWbFieldEditor#dialog
   */
  public interface IDialogContentProvider
  {
    BWidget getDialogContent();
  }


////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("workbench:WbFieldEditorBinding");
    return agents;
  }
  
////////////////////////////////////////////////////////////////
//Agent Filter
////////////////////////////////////////////////////////////////
  /**
   * Static method returns the Profile Agent filter which checks that the editor agent declares an
   * app name suitable for the current profile. In cases where the agent does not declare an app
   * name, the editor will be used for any profile.
   * 
   * @param shell
   *          - {@link BWbShell} to use to retrieve our current profile.
   * 
   * @return {@link AgentFilter}
   */
  public static AgentFilter getAgentFilter(BWbShell shell)
  {
    BWbProfile profile = null;
    if (shell != null)
      profile = shell.getProfile();
    else
    {
      try
      {
        profile = BWbProfile.make(null, WbMain.defaultProfileType);
      }
      catch (Exception e)
      {
        return FEAgentFilter;
      }
    }

    return AgentFilter.and(new ProfileFilter(profile), FEAgentFilter);
  }

  /**
   * The profile filter provides an agent filter based on whether the agent is registered for the
   * app name of the given profile. In cases where the agent does not declare an app name, the
   * editor will be used for any profile.
   * 
   */
  public static class ProfileFilter
      extends AgentFilter
  {
    /**
     * Constructor
     * 
     * @param profile
     *          - The profile whose app name is used when determining if a field editor agent is
     *          available.
     */
    public ProfileFilter(BWbProfile profile)
    {
      this.profile = profile;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.baja.agent.AgentFilter#include(javax.baja.agent.AgentInfo)
     */
    public boolean include(AgentInfo agent)
    {
      String appName = agent.getAppName();

      // no check required -
      if (null == appName)
        return true;

      // If we have an app name, make sure it matches up.
      if (null != appName)
      {
        // if our agent app name isn't declared in the profile, return false.
        if (!isProfileApp(appName))
          return false;
      }

      return true;
    }

    /**
     * This method checks that the given app name is supported by the profile.
     * 
     * @param appName
     * @return
     */
    private boolean isProfileApp(String appName)
    {
      // sanity check
      if (null == appName || appName.length() == 0)
        return true;

      // iterate over all app names declared in the profile. If the app name
      // matches one of the profile app names, return true.
      String[] profileAppNames = profile.getAppNames();
      for (int i = 0; i < profileAppNames.length; i++)
      {
        if (profileAppNames[i].equals(appName))
          return true;
      }

      return false;
    }

    // ////////////////////////////////////////
    // Attributes
    // ////////////////////////////////////////
    BWbProfile profile; 
}   




  /** Filter for agents of type BWbFieldEditor */
  static AgentFilter FEAgentFilter = AgentFilter.is(TYPE);
  
  //Determine the default editor for a BComponent in the
  //current environment. Override the dialog-bypass behavior
  //if the component passed in to dialog uses a custom editor.
  private static BWbFieldEditor defaultEditor = null;
  static
  {
    try
    {
      defaultEditor = makeFor(new BComponent());
    }
    catch (BajaRuntimeException e)
    {
      defaultEditor = null;
    }      
  }  
}
