/*
 * Copyright 2010, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.commands; 

import java.util.HashMap;

import javax.baja.ui.*;
import javax.baja.ui.transfer.*;
import javax.baja.ui.util.*; 
import javax.baja.sys.*; 
import javax.baja.util.*;  


 
/**
 * 
 * 
 * @author    Daryl McCuiston
 * @creation  15 Feb 2010
 * @version   $Revision: 1$ $Date: 2/26/10 5:15:33 PM EST$
 */
public class SetDisplayNameCommand
  extends Command
{ 

  /**
   * Create a RenameCommand which uses the specified BWidget.
   */
  public SetDisplayNameCommand(BWidget owner, BComponent comp)
  {
    super(owner, UiLexicon.bajaui().module, "commands.setDisplayName");
    myComponent = comp;             
  }
  
  
  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {                                
    BComponent parent = (BComponent)myComponent.getParent(); 
    
    String initVal = "";
    BFormat currName = parent.getDisplayNameFormat(myComponent.getPropertyInParent());
    if(currName!= null)
      initVal = currName.getFormat();
    
    // Prompt user for a new display name       
    String newDisplayName = promptForNewDisplayName(getOwner(), parent, initVal);
    
    if (newDisplayName == null)
      return null;
    
    // Create the new DisplayNameArtifact
    DisplayNameArtifact artifact =
      new DisplayNameArtifact(parent, myComponent.getPropertyInParent(), BFormat.make(initVal), BFormat.make(newDisplayName)); 
    artifact.redo();
    
    return artifact;
  }
  
   //To Clear
  //parent.setDisplayName(myComponent.getPropertyInParent(),BFormat.make(""),null);
  
  /**
   * Prompts the user for a new display name - takes care of checking for duplicates
   */
  public static String promptForNewDisplayName(BWidget owner, BIPropertyContainer parent, String oldDisplayName)
  {
    boolean done = false;
    String initVal = oldDisplayName;
    String newDisplayName = null;
    
    while (!done)
    {
      // Prompt user for a new display name       
      newDisplayName = BDialog.prompt(owner,
                                      lex.getText("commands.setDisplayName.prompt"),
                                      initVal,
                                      20);
   
      // Check if new name is null
      if(newDisplayName == null)
        return null;
      
      // Check if new name is the same as the old name
      newDisplayName = newDisplayName.trim();
      if(newDisplayName.equals(oldDisplayName))
        return null;
      
      // Check if this displayName is already in use
      if (isDuplicateDisplayName(newDisplayName, parent))
      {
        int dialogResponse = BDialog.confirm(owner,
                             lex.getText("commands.setDisplayName.duplicate.title"),
                             lex.getText("commands.setDisplayName.duplicate.confirm"),
                             BDialog.OK_CANCEL);
        
        if (dialogResponse == BDialog.OK)
          done = true;                                                                                                                                                                       
      }
      else
      {
        done = true;
      }
    }
    
    return newDisplayName;
  }
  
  /**
   * Check for duplicate display names
   */
  private static boolean isDuplicateDisplayName(String newDisplayName, BIPropertyContainer parent)
  {
    boolean isDuplicate = false;
    
    // Get the current name map
    HashMap<String, BFormat> map = new HashMap<>();
    BNameMap currNameMap = BNameMap.make(map);
    BValue x = parent.get("displayNames");
    if (x instanceof BNameMap)
      currNameMap = (BNameMap)x;
    
    // Walk through each slot looking for display names
    // We'll also take the opportunity to clear old entries in the NameMap    
    Slot[] slots = parent.getSlotsArray();
    
    // Check each slot for a duplicate display name
    for (int i = 0; i < slots.length; i++)
    {
      Slot slot = slots[i];
      String name = slot.getName();
      BFormat displayNameFormat = currNameMap.get(name);
      
      // If the displayName is in the NameMap, compare to that
      if (displayNameFormat != null)
      {
        // Check if the displayName is our newDisplayName
        if (newDisplayName.equals(displayNameFormat.getFormat()))
          isDuplicate = true;
      }
      // If the displayName is not in the NameMap,
      // compare to the default display name
      else
      {
        if (newDisplayName.equals(slot.getDefaultDisplayName(null)))
          isDuplicate = true;
      }      
    }      
      
    return isDuplicate;
  }
        
  
  /**
   * RenameCommands are merged by virtue of routing to same TransferWidget.
   */
  protected Command doMerge(Command c)
  {
    return this;
  }
  
  public class DisplayNameArtifact
    implements CommandArtifact
  {
    public DisplayNameArtifact(BComponent parent, Property property, BFormat oldValue, BFormat newValue)
    {
      this.parent = parent;
      this.property = property;
      this.oldValue = oldValue;
      this.newValue = newValue;
    }
    
    public void redo()
    {
      parent.setDisplayName(property, newValue, null);
    }
    
    public void undo()
    {
      parent.setDisplayName(property, oldValue, null);
    }
    
    private BComponent parent;
    private Property property;
    private BFormat oldValue;
    private BFormat newValue;
  }
  
  private static final UiLexicon lex = UiLexicon.bajaui();
  private BComponent myComponent;
   
 }

