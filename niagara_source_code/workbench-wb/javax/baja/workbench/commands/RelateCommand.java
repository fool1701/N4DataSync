/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.nre.util.Array;
import javax.baja.sync.Transaction;
import javax.baja.sys.BComponent;
import javax.baja.sys.BRelation;
import javax.baja.sys.Context;
import javax.baja.tag.Id;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.util.Lexicon;
import com.tridium.workbench.util.BRelatePad;

/**
 * RelateCommand manages the creation of a relate between
 * two components.
 *
 * @author    Andrew Saunders
 * @creation  15 Apr 14
 * @version   $Revision: 17$ $Date: 11/2/09 3:51:39 PM EST$
 * @since     Baja 1.0
 */
public class RelateCommand
  extends Command
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a RelateCommand.  T
   */
  public RelateCommand(BWidgetShell shell,
                       BComponent endpoint,
                       BComponent target,
                       String relateId    )
  {
    super(shell, "relate");
    this.sources     = new BComponent[] { endpoint };
    this.targets     = new BComponent[] { target };
    this.relateId  = relateId;
  }

  /**
   * Construct a LinkCommand. Same as above except multiple
   * sources can be linked to multiple targets
   *
   * @since Niagara 3.5
   */
  public RelateCommand(BWidgetShell shell,
                       BComponent[] endpoints,
                       BComponent[] targets   )
  {
    super(shell, "relate");
    this.sources    = endpoints;
    this.targets    = targets;
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
    if(relateId == null || relateId == "")
    {
      try
      {
        if (!queryUser()) return null;
      }
      catch(Exception e)
      {
        BDialog.error(getOwner(), lex.getText("relateCommand.noTagService.error.message"));
        return null;
      }
    }
    
    Array<RelateInfo> relateArray = new Array<>(RelateInfo.class, sources.length * targets.length);

    for (int i = 0; i < sources.length; ++i)
    {
      for (int j = 0; j < targets.length; ++j)
      {
        BRelation addRelation = sources[i].makeRelation(Id.newId(relateId), targets[j], null);
        if(hasRelation(sources[i], addRelation))
        {
          BDialog.error(getOwner(), bajaLex.getText("relationcheck.relationAlreadyExists"));
          continue;
        }
        relateArray.add(new RelateInfo(sources[i], sources[i].makeRelation(Id.newId(relateId), targets[j],  null)));
      }
    }

    Artifact art = new Artifact(relateArray.trim());
    art.redo();  
    return art;
  }

  private boolean hasRelation(BComponent target, BRelation relation)
  {
    for (BRelation bRelation : target.getChildren(BRelation.class))
    {
      if(bRelation.getRelationId().equals(relation.getRelationId()) &&
        bRelation.getEndpointOrd().equals(relation.getEndpointOrd()))
      {
        return true;
      }
    }
    ;
    return false;
  }

  /**
   * Open the LinkPad dialog.  Return false if cancelled.
   */
  private boolean queryUser()
  {
    BRelatePad pad = new BRelatePad( sources, targets);
    if(pad.openInDialog(getOwner()))
    {
      this.relateId = pad.getRelationId();
      return true;
    }
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Artifact
////////////////////////////////////////////////////////////////  

  static class Artifact implements CommandArtifact
  {
    Artifact(RelateInfo[] relations)
    {      
      this.relations = relations;
    }
    
    public void redo() throws Exception
    {      
      // Please note, we can't use a Transaction here because we need to know the unique Slot
      // names for for the undo to work!
      for (int i = 0; i < relations.length; ++i)
        relations[i].add();
    }    

    public void undo() throws Exception
    { 
      if (relations.length == 0) return;
      
      // Buffer the removes into a Transaction
      Context tx = Transaction.start(relations[0].source, null);
      
      for (int i = 0; i < relations.length; ++i)
        relations[i].remove(tx);
      
      Transaction.end(relations[0].source, tx);
    }    
    
    RelateInfo[] relations;
  }
  
////////////////////////////////////////////////////////////////
// LinkInfo
////////////////////////////////////////////////////////////////
  
  private static class RelateInfo
  {
    RelateInfo(BComponent source, BRelation relation)
    {
      this.source = source;
      this.relation = relation;
    }
    
    void add()
    {
      source.add("relation?", relation);
    }
    
    void remove(Context cx)
    {
      source.remove(slotName, cx);
    }

    BComponent source;
    BRelation relation;
    String slotName = "";
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private static Lexicon bajaLex = Lexicon.make("baja");
  private static final Lexicon lex = Lexicon.make(RelateCommand.class);

  BComponent[] sources;
  BComponent[] targets;
  String relateId;

}

