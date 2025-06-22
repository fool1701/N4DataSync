/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;

import com.tridium.ndriver.ui.device.BNDeviceManager;

@NiagaraType(
  agent = @AgentOn(
    types = { "nvideo:CameraDeviceExt", "nvideo:CameraFolder" }
  )
)
public class BCameraManager
    extends BNDeviceManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nvideo.ui.BCameraManager(734752635)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCameraManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    
  /**
   * Returns an instance of CameraMgrController, which makes a slight modification
   * to the manager by renaming the 'New Folder' button as the 'New Location' button.
   */
  protected MgrController makeController()
  {
    return new CameraMgrController(this);
  }
  
  /**
   * Returns an instance of VideoNestedDeviceModel, which makes a slight modification
   * to the database list by preventing the exts icon from being painted on
   * any rows that are really for a BCameraFolder and/or BVideoLocation This is necessary
   * since BCameraFolder and/or BVideoLocation are unable to extend BDeviceFolder. Instead,
   * they implement BIDeviceFolder. See the comment for the VideoNestedDeviceExtsColumn
   * class for complete details about why this is necessary.
   */
  protected MgrModel makeModel()
  {
    return new VideoNestedDeviceModel(this);
  } 
  
////////////////////////////////////////////////////////////////
// CameraMgrController
////////////////////////////////////////////////////////////////
  
  /**
   * The Video Manager's controller changes the icon of the 'New Folder' button and
   * the 'New' button.
   * 
 * @author   lperkins (Original ddf code)
 * @author   Robert Adams (rework for ndriver)
 * @creation  25 Jan 2012
   */
  public class CameraMgrController
    extends VideoNestedDeviceController
  {
    /**
     * The overridden 'New Folder' button does just as the 'New Folder' button
     * would do but changes the icon.
     */
    class NewCameraFolder extends MgrCommand
    {
      NewCameraFolder(BWidget owner)
      {
        super(owner, LEX, "commands.newCameraFolder");
      }
      
      public CommandArtifact doInvoke() throws Exception
      {
        return doNewCameraFolder(); 
      }
      
      public Command doMerge(Command c)
      {
        return this;
      }
    }
    
    /**
     * The overridden 'New' button does just as the 'New' button would do except
     * for this manager but changes the icon
     */
    class NewCamera extends MgrCommand
    {
      NewCamera(BWidget owner)
      {
        super(owner, LEX, "commands.newCamera");
      }
      
      public CommandArtifact doInvoke() throws Exception
      {
        return doNewCamera();
      }
      
      public Command doMerge(Command c)
      { 
        return this; 
      }
    }
    
    /**
     * This is the callback when the 'New Camera Folder' command is invoked.
     */
    public CommandArtifact doNewCameraFolder()
      throws Exception
    {
      super.doNewFolder();
      return null;
    }     
    
    /**
     * This is the callback when the 'New Camera' command is invoked.
     */
    public CommandArtifact doNewCamera()
      throws Exception
    {
      return super.doNew(null);
    }      
    
    public CameraMgrController(BCameraManager mgr)
    {
      super(mgr);
      
      // Allocates a button that we will label "New Camera Folder" and make take
      // the place of the 'New Folder' button
      newCameraFolder = new NewCameraFolder(mgr);
      newCameraFolder.setFlags(BARS | POPUP);
      
      // Allocates a button that we will label "New Camera" and make take
      // the place of the 'New' button
      newCamera = new NewCamera(mgr);
      newCamera.setFlags(BARS | POPUP);
    }
    
    @Override
    protected IMgrCommand[] makeCommands()
    {
      // Gets the commands that would normally appear on a N Device Manager
      // NOTE: The devices for this manager are video displays
      IMgrCommand[] superCmds = super.makeCommands();
      Array<IMgrCommand> bajaArray = new Array<>(superCmds);
      
      // The 'newFolder' reference is defined with public access on this class's
      // ancestor 'FolderController'. Let's find it in the array of superCmds
      int newFolderButtonId = bajaArray.indexOf(newFolder);
      
      // Replaces the 'New Folder' MgrCommand (button, menu item, toolbar item) with
      // our own version that is labeled 'New Location' instead.
      bajaArray.set(newFolderButtonId, newCameraFolder);
      
      // The 'newCommand' reference is defined with public access on this class's
      // ancestor 'MgrController'. Let's find it in the array of superCmds
      int newButtonId = bajaArray.indexOf(newCommand);
      
      // Replaces the 'New' MgrCommand (button, menu item, toolbar item) with
      // our own version that is labeled 'New Camera' instead.
      bajaArray.set(newButtonId, newCamera);
      
      
      // Returns the modified array of MgrCommands for the Video Display Mgr
      return bajaArray.trim();
      
    }
    
    /**
     * This is a reference to the MgrCommand (button, menu item, toolbar item) named
     * 'New Camera Folder' and replaces 'New Folder' on the manager.
     */
    public NewCameraFolder newCameraFolder; 
    
    /**
     * This is a reference to the MgrCommand (button, menu item, toolbar item) named
     * 'New Camera' and replaces 'New' on the manager.
     */
    public NewCamera newCamera; 
  }

  public static final Lexicon LEX = UiLexicon.make(BCameraManager.class);

}
