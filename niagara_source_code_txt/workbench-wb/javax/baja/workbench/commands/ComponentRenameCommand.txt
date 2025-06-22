/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.naming.SlotPath;
import javax.baja.sync.Transaction;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.BLink;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Knob;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import com.tridium.workbench.util.WbUtil;

/**
 * ComponentRenameCommand.
 *
 * @author    Brian Frank
 * @creation  16 Aug 01
 * @version   $Revision: 23$ $Date: 8/18/09 4:26:55 PM EDT$
 * @since     Baja 1.0
 */
public class ComponentRenameCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a ComponentRenameCommand for the specified component.  
   * This version of the command will prompt the user for
   * a new name.
   */
  public ComponentRenameCommand(BWidget owner, BComponent component)
  {
    this(owner, component, (String)null);
  }  

  /**
   * Create a ComponentRenameCommand for the specified component.  
   * This version of the command uses the newName provided.
   */
  public ComponentRenameCommand(BWidget owner, BComponent component, String newName)
  {
    this(owner, 
         (BComponent)component.getParent(), 
         component.getPropertyInParent(), 
         newName);
  }  
  
  /**
   * Create a ComponentRenameCommand for the specified component's slot.  
   * This version of the command will prompt the user for a new name.
   */
  public ComponentRenameCommand(BWidget owner, BComponent component, Property slot)
  {
    this(owner, component, slot, null);
  }  

  /**
   * Create a ComponentRenameCommand for the specified component's slot.  
   * This version of the command uses newName provided, or if null 
   * prompts the user.
   */
  public ComponentRenameCommand(BWidget owner, BComponent component, Property slot, String newName)
  {
    this(owner, 
         new BIPropertyContainer[] { component }, 
         new Property[] { slot }, 
         newName == null ? null : new String[] { newName });
  }  

  /**
   * Create a ComponentRenameCommand for the specified list of 
   * components and their slots.  If newName is provides it is
   * used, otherwise if null then the user is prompted. 
   */
  public ComponentRenameCommand(BWidget owner, BComponent[] components, Property[] slots, String[] newNames)
  {
    this(owner, (BIPropertyContainer[])components, slots, newNames);
  }
  
  /**
   * Create a ComponentRenameCommand for the specified list of 
   * BIPropertyContainers and their slots.  If newName is provides it is
   * used, otherwise if null then the user is prompted. 
   *
   * @since Niagara 3.5
   */
  public ComponentRenameCommand(BWidget owner, BIPropertyContainer[] containers, Property[] slots, String[] newNames)
  {
    super(owner, UiLexicon.bajaui().module, "commands.rename");
    
    // sanity checking
    if (containers.length != slots.length) throw new IllegalArgumentException();
    if (newNames != null)
    {
      if (containers.length != newNames.length) throw new IllegalArgumentException();
      for(int i=0; i<newNames.length; ++i)
        if (newNames[i] == null) throw new IllegalArgumentException();
    }
    
    // init fields
    this.components = containers;
    this.slots      = slots;
    this.newNames   = newNames;
  }  

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception       
  {                    
    int len = components.length;

    // get the list of old names and do sanity checking
    oldNames = new String[len];
    oldDisplay = new String[len];
    for(int i=0; i<len; ++i)
    { 
      // save old name
      String name   = slots[i].getName();
      oldNames[i]   = name;   
      oldDisplay[i] = SlotPath.unescape(name);
      
      // can't rename a MixIn property
      BValue val = components[i].get(name);
      if (val instanceof BIMixIn)
      {
        BDialog.error(getShell(), getLabel(), UiLexicon.bajaui().getText("commands.rename.errorMixIn"));
        return null;
      }
    }
                                
    // prompt to fill in newNames
    if (newNames == null)
    {   
      // prompt differently for one component versus a list
      if (components.length == 1) 
        promptSingle();
      else 
        promptList();
      
      // if still no names, that means prompt was canceled  
      if (newNames == null) return null;
      
      // trim and escape our newNames 
      for(int i=0; i<len; ++i)
        newNames[i] = SlotPath.escape( newNames[i].trim() );
    }
    
    // create artificat and do the do
    Artifact artifact = new Artifact();
    artifact.redo();
    return artifact;
  }
  
  void promptSingle()
  {
    String s = BDialog.prompt(getShell(), getLabel(), oldDisplay[0], 40);
    if (s == null) return;
    newNames = new String[] { s };
  }

  void promptList()
  {
    WbUtil.BatchReplace batch = new WbUtil.BatchReplace(getOwner(), oldDisplay);
    newNames = batch.prompt(lexTitle); 
  }
  
////////////////////////////////////////////////////////////////
// Artifact
////////////////////////////////////////////////////////////////  

  class Artifact implements CommandArtifact
  {
    public void undo() throws Exception 
    {             
      rename(newNames, oldNames);
    }
    
    public void redo() throws Exception
    {
      rename(oldNames, newNames);
    }
    
    private void rename(String[] oldNames, String[] newNames) 
      throws Exception
    {
      getShell().enterBusy();
      try
      {                
        boolean useTx = (components[0] instanceof BComponent);
        Context tx = (useTx)?Transaction.start((BComponent)components[0], null):null;
        
        for(int i=0; i<components.length; ++i)
        {
          BIPropertyContainer c = components[i];  
          String oldName = oldNames[i];
          String newName = newNames[i];
          Property prop = c.getProperty(oldName);
          if (c instanceof BComponent)
            fixLinks((BComponent)c, prop, BString.make(newName), tx);
          c.rename(prop, newName, tx);
        }
        
        if (useTx) Transaction.end((BComponent)components[0], tx);
      }
      finally
      {
        getShell().exitBusy();
      }
    }
  }                
  
  static void fixLinks(BComponent c, Property prop, BString newName, Context tx)
  {
    // fix links targeted on this property
    BLink[] links = c.getLinks(prop);
    for(int i=0; i<links.length; ++i)
    {
      BLink link = links[i];
      try
      {
        link.set(BLink.targetSlotName, newName, tx);
      }
      catch(Exception e)
      {
        System.out.println("ERROR: Cannot fix link: " + link);
        e.printStackTrace();
      }
    }
    
    // fix links sources on this property
    Knob[] knobs = c.getKnobs(prop);
    for(int i=0; i<knobs.length; ++i)
    {
      Knob knob = knobs[i];           
      try
      {
        BComponent target = (BComponent)knob.getTargetOrd().get(c);  
        target.lease();
        Property targetProp = target.getProperty(knob.getTargetSlotName());
        BLink[] targetLinks = target.getLinks(targetProp);
        for(int j=0; j<targetLinks.length; ++j)
        {                   
          BLink link = targetLinks[j];
          if (isMatch(knob, link))
          {
            link.set(BLink.sourceSlotName, newName, tx);
            break;
          }
        }
      }
      catch(Exception e)
      {
        System.out.println("ERROR: Cannot fix knob: " + knob);
        e.printStackTrace();
      }
    }
  }  
  
  static boolean isMatch(Knob knob, BLink link)
  {     
    return link.getSourceOrd().equals(knob.getSourceOrd()) &&
           link.getSourceSlotName().equals(knob.getSourceSlotName());
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  final UiLexicon lex = UiLexicon.bajaui();  
  final String lexTitle = lex.getText("commands.rename.label");
    
  BIPropertyContainer[] components;
  Slot[] slots;
  String[] oldNames;
  String[] oldDisplay;
  String[] newNames;
  
}

