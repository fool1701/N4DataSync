/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.commands;

import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.util.UiLexicon;
import com.tridium.ui.BOptionDialog;
import com.tridium.workbench.util.WbUtil;

/**
 * FileRenameCommand.
 *
 * @author    Brian Frank
 * @creation  16 Aug 01
 * @version   $Revision: 10$ $Date: 6/11/07 12:41:48 PM EDT$
 * @since     Baja 1.0
 */
public class FileRenameCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a FileRenameCommand for the specified list of 
   * files and new names.  If newNames is null, then the
   * is prompted to for the new names.
   */
  public FileRenameCommand(BWidget owner, BIFile[] files, String[] newNames)
  {
    super(owner, UiLexicon.bajaui().module, "commands.rename");
    
    if (newNames != null)
    {
      if (files.length != newNames.length) throw new IllegalArgumentException();
      for(int i=0; i<newNames.length; ++i)
        if (newNames[i] == null) throw new IllegalArgumentException();
    }
    
    this.files = files;
    this.newNames = newNames;  
  }  

  /**
   * Create a FileRenameCommand for the specified list of files.
   * The user is prompted for the new names.
   */
  public FileRenameCommand(BWidget owner, BIFile[] files)
  {
    this(owner, files, null);
  }  

  /**
   * Create a FileRenameCommand for the specified file and newName.
   */
  public FileRenameCommand(BWidget owner, BIFile file, String newName)
  {
    this(owner, new BIFile[] {file}, new String[] {newName});
  }  

  /**
   * Create a FileRenameCommand for the specified file.
   * The user is prompted for the new name.
   */
  public FileRenameCommand(BWidget owner, BIFile file)
  {
    this(owner, new BIFile[] {file}, null);
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
    int len = files.length;

    // get the list of old names
    oldNames = new String[len];
    for(int i=0; i<len; ++i)
      oldNames[i] = files[i].getFileName();
                                
    // prompt to fill in newNames
    if (newNames == null)
    {   
      // prompt differently for one file versus a list
      if (files.length == 1) 
        promptSingle();
      else 
        promptList();
      
      // if still no names, that means prompt was canceled  
      if (newNames == null) return null;
    }
    
    // verify new names
    for(int i=0; i<len; ++i)
    {
      // string leading and tailing whitespace
      newNames[i] = newNames[i].trim();
      
      // don't allow funny characters              
      FilePath.verifyValidName(newNames[i]);
    }    
    
    // do a move operation on each one
    for(int i=0; i<len; ++i)
    {
      FilePath oldPath = files[i].getFilePath();
      FilePath newPath = new FilePath(newNames[i]);
      files[i].getFileSpace().move(oldPath, newPath);
    }
    
    // no undo for now
    return null;
  }
    
  void promptSingle()
  {
    String s = BOptionDialog.promptFileName(getShell(), getLabel(), oldNames[0]);
    if (s == null) return;
    newNames = new String[] { s };
  }
    
  void promptList()
  {          
    WbUtil.BatchReplace batch = new WbUtil.BatchReplace(getOwner(), oldNames);
    newNames = batch.prompt(lexTitle); 
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  final UiLexicon lex = UiLexicon.bajaui();  
  final String lexTitle = lex.getText("commands.rename.label");
    
  BIFile[] files;
  String[] oldNames;
  String[] newNames;
  
}

