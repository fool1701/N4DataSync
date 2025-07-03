/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.nre.util.Array;
import javax.baja.sync.Transaction;
import javax.baja.sys.BComponent;
import javax.baja.sys.BLink;
import javax.baja.sys.Context;
import javax.baja.sys.Slot;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.workbench.util.BLinkPad;

/**
 * LinkCommand manages the creation of a link between
 * two components.  If both slots are known then no
 * dialog is needed (but the link should already be
 * validated).  If either side is unknown then we pop
 * up our link dialog.
 *
 * @author    Brian Frank
 * @creation  16 Aug 01
 * @version   $Revision: 17$ $Date: 11/2/09 3:51:39 PM EST$
 * @since     Baja 1.0
 */
public class LinkCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct a LinkCommand.  The sourceSlot and targetSlot
   * may be unspecified using null. If they are null, then a 
   * dialog is raised to allow the user to choose the link.
   */
  public LinkCommand(BWidgetShell shell, 
                     BComponent source, Slot sourceSlot, 
                     BComponent target, Slot targetSlot)
  {
    super(shell, "link");
    this.sources     = new BComponent[] { source };
    this.sourceSlotName = sourceSlot == null ? null : sourceSlot.getName();
    this.targets     = new BComponent[] { target };
    this.targetSlotName = targetSlot == null ? null : targetSlot.getName();
  }
  
  /**
   * Construct a LinkCommand. Same as above except multiple
   * sources can be linked to multiple targets
   * 
   * @since Niagara 3.5
   */
  public LinkCommand(BWidgetShell shell, 
                     BComponent[] sources, String sourceSlotName, 
                     BComponent[] targets, String targetSlotName)
  {
    super(shell, "link");
    this.sources    = sources;
    this.sourceSlotName = sourceSlotName;
    this.targets    = targets;
    this.targetSlotName = targetSlotName;
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////  

  /**
   * Invoke the command.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    // check if we need to open a dialog
    if (sourceSlotName == null || targetSlotName == null)
      if (!queryUser()) return null;
    
    Array<LinkInfo> linkArray = new Array<>(LinkInfo.class, sources.length * targets.length);
    
    for (int i = 0; i < targets.length; ++i)
    {
      for (int j = 0; j < sources.length; ++j)
        linkArray.add(new LinkInfo(targets[i], targets[i].makeLink(sources[j], sources[j].getSlot(sourceSlotName), targets[i].getSlot(targetSlotName), null))); 
    }
        
    Artifact art = new Artifact(linkArray.trim());
    art.redo();  
    return art;
  }

  /**
   * Open the LinkPad dialog.  Return false if cancelled.
   */
  private boolean queryUser()
  {
    BLinkPad pad = new BLinkPad(sources, targets);
    if (pad.openInDialog(getOwner(), sourceSlotName, targetSlotName))
    {                                
      // reset components in case of reverse
      this.sources     = pad.getSourceArray();  
      this.targets     = pad.getTargetArray();
      this.sourceSlotName = pad.getSourceSlotName();
      this.targetSlotName = pad.getTargetSlotName();
      return true;
    }
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Artifact
////////////////////////////////////////////////////////////////  

  static class Artifact implements CommandArtifact
  {
    Artifact(LinkInfo[] links) 
    {      
      this.links = links;
    }
    
    public void redo() throws Exception
    {      
      // Please note, we can't use a Transaction here because we need to know the unique Slot
      // names for for the undo to work!
      for (int i = 0; i < links.length; ++i)
        links[i].add();
    }    

    public void undo() throws Exception
    { 
      if (links.length == 0) return;
      
      // Buffer the removes into a Transaction
      Context tx = Transaction.start(links[0].target, null);
      
      for (int i = 0; i < links.length; ++i)        
        links[i].remove(tx);
      
      Transaction.end(links[0].target, tx);
    }    
    
    LinkInfo[] links;               
  }
  
////////////////////////////////////////////////////////////////
// LinkInfo
////////////////////////////////////////////////////////////////
  
  private static class LinkInfo
  {
    LinkInfo(BComponent target, BLink link)
    {
      this.target = target;
      this.link = link;
    }
    
    void add()
    {
      slotName = ((ComponentSlotMap)target.fw(Fw.SLOT_MAP)).generateUniqueSlotName("Link");
      target.add(slotName, link);
    }
    
    void remove(Context cx)
    {
      target.remove(slotName, cx);
    }
    
    BComponent target;
    BLink link;
    String slotName = "";
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BComponent[] sources;
  BComponent[] targets;
  
  String sourceSlotName;
  String targetSlotName;    
}

