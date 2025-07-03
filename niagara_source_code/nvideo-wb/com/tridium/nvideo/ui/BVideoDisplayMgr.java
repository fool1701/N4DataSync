/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.space.Mark;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.folder.BFolderManager;
import javax.baja.workbench.mgr.folder.FolderModel;

import com.tridium.ndriver.ui.device.BNDeviceManager;
import com.tridium.nvideo.display.BVideoLocation;
import com.tridium.nvideo.display.BVideoLocationPrompt;
import com.tridium.workbench.transfer.TransferUtil;


/**
 * BVideoDisplayMgr is manager view for Video Displays
 *
 * @author    Robert Adams
 * @creation  19 Jan  2012
 * @version   $Revision: 49$ $Date: 7/29/04 6:22:07 PM EDT$
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "nvideo:DisplayDeviceExt", "nvideo:VideoLocation" }
  )
)
public class BVideoDisplayMgr
  extends BNDeviceManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nvideo.ui.BVideoDisplayMgr(3042260550)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVideoDisplayMgr.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Returns an instance of VideoNestedDeviceModel, which makes a slight modification
   * to the database list by preventing the exts icon from being painted on
   * any rows that are really for a BVideoCameraFolder and/or BVideoLocation This is necessary
   * since BVideoCameraFolder and/or BVideoLocation are unable to extend BDeviceFolder. Instead,
   * they implement BIDeviceFolder. See the comment for the VideoNestedDeviceExtsColumn
   * class for complete details about why this is necessary.
   */
  protected MgrModel makeModel()
  {
    return new VideoNestedDeviceModel(this);
  } 
  
  
  /**
   * Returns an instance of VideoMgrController, which makes a slight modification
   * to the manager by renaming the 'New Folder' button as the 'New Location' button.
   */
  protected MgrController makeController()
  {
    return new VideoMgrController(this);
  }
  
  /**
   * The Video Manager's controller renames the 'New Folder' button as the
   * 'New Location' button.
   */
  public class VideoMgrController
    extends VideoNestedDeviceController
  {
    /**
     * The 'New Location' button does just as the 'New Folder' button would do except
     * for this manager, we refer to folders as locations.
     */
    class NewLocation extends MgrCommand
    {
      NewLocation(BWidget owner)
      {
        super(owner, LEX, "commands.newLocation");
      }
      public CommandArtifact doInvoke() throws Exception
      {
        return doNewLocation();
      }
      public Command doMerge(Command c)
      {
        return this;
      }
    }
    
    /**
     * The overridden 'New' button does just as the 'New' button would but
     * changes the icon.
     */
    class NewDisplay extends MgrCommand
    {
      NewDisplay(BWidget owner)
      {
        super(owner, LEX, "commands.newDisplay"); 
      }
      
      public CommandArtifact doInvoke() throws Exception
      {
        return doNewDisplay();
      }
      
      public Command doMerge(Command c)
      {
        return this; 
      }
    }
    
    
  /**
   * This is the callback when the new location command is invoked.
   */
    public CommandArtifact doNewLocation()
      throws Exception
    {
      BFolderManager mgr = (BFolderManager)getManager();
      FolderModel model = (FolderModel)mgr.getModel();
      BComponent parent = (BComponent)mgr.getCurrentValue();
      BVideoLocation childLocation  = (BVideoLocation)model.getFolderType().getInstance();

      // Prompts the user for the location name and for the location id
      BVideoLocationPrompt videoLocationArgs = 
          (BVideoLocationPrompt)BWbFieldEditor.dialog(getManager(), 
                                                      LEX.get("NewLocation"),  
                                                      new BVideoLocationPrompt() );

      if (videoLocationArgs==null) return null; 

      String videoLocationName = videoLocationArgs.getLocationName().toString();
      if (videoLocationName!=null)return null; 

      // Transfers the locationId from the user's return data into the new childLocation 
      childLocation.setLocationId(videoLocationArgs.getLocationId());

      // Creates an object that will ask the Niagara AX framework not to prompt the user in the
      // Subsequent 'insert' operation
      BComponent xferParams = new BComponent();
      xferParams.add(TransferUtil.PARAM_PROMPT_FOR_NAME, BBoolean.FALSE);

      // Inserts the 'New Location'. Notice that the 'xferParams' are the second to last argument
      return TransferUtil.insert(mgr, TransferUtil.ACTION_COPY, new Mark(childLocation, videoLocationName), parent, xferParams, null);
    } 
    
    /**
     * This is the callback when the 'New Display' command is invoked.
     */
    public CommandArtifact doNewDisplay()
      throws Exception
    {
      return super.doNew(null);
    }      
    
    /**
     * Constructor
     */
    public VideoMgrController(BVideoDisplayMgr mgr)
    {
      super(mgr);
      
      // Allocates a button that we will label "New Location" and make take
      // the place of the 'New Folder' button
      newLocation = new NewLocation(mgr);
      newLocation.setFlags(BARS | POPUP);
      
      // Allocates a button that we will label "New Display" and make take
      // the place of the 'New' button
      newDisplay = new NewDisplay(mgr);
      newDisplay.setFlags(BARS | POPUP);
    }
    
    
/////////////////////////////////////////////////////////////////////////////
// MgrController
/////////////////////////////////////////////////////////////////////////////
    
    @Override
    protected IMgrCommand[] makeCommands()
    {
      // Gets the commands that would normally appear on a Device Manager
      // NOTE: The devices for this manager are video displays
      IMgrCommand[] superCmds = super.makeCommands();
      Array<IMgrCommand> bajaArray = new Array<>(superCmds);
      
      // The 'newFolder' reference is defined with public access on this class's
      // ancestor 'FolderController'. Let's find it in the array of superCmds
      int newFolderButtonId = bajaArray.indexOf(newFolder);
      
      // Replaces the 'New Folder' MgrCommand (button, menu item, toolbar item) with
      // our own version that is labeled 'New Location' instead.
      bajaArray.set(newFolderButtonId, newLocation);
      
      // The 'newCommand' reference is defined with public access on this class's
      // ancestor 'MgrController'. Let's find it in the array of superCmds
      int newButtonId = bajaArray.indexOf(newCommand);
      
      // Replaces the 'New' MgrCommand (button, menu item, toolbar item) with
      // our own version that is labeled 'New Display' instead.
      bajaArray.set(newButtonId, newDisplay);
      
      
      // Returns the modified array of MgrCommands for the Video Display Mgr
      return bajaArray.trim();
      
    }
    
    /**
     * This is a reference to the MgrCommand (button, menu item, toolbar item) named
     * 'New Location' and replaces 'New Folder' on the manager.
     */
    public NewLocation newLocation; 
    
    /**
     * This is a reference to the MgrCommand (button, menu item, toolbar item) named
     * 'New Display' and replaces 'New' on the manager.
     */
    public NewDisplay newDisplay; 
    
    
  }

  public static final Lexicon LEX = UiLexicon.make(BVideoDisplayMgr.class);


}
