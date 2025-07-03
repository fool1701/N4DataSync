/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.agent.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BDirectory is the BIFile type used to represent directories 
 * in file space implementations.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 11$ $Date: 3/28/05 9:22:55 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDirectory
  extends BAbstractFile
  implements BIDirectory
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BDirectory(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDirectory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store and lexicon text.
   */
  public BDirectory(BIFileStore store, LexiconText lexText)
  {
    super(store);
    this.lexText = lexText;
  }

  /**
   * Construct a file with the specified store.
   */
  public BDirectory(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BDirectory()
  {  
  }

////////////////////////////////////////////////////////////////
// BIDirectory
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getFileSpace().getChildren(this)</code>.
   */
  @Override
  public BIFile[] listFiles()
  {
    return getFileSpace().getChildren(this);
  }

////////////////////////////////////////////////////////////////
// BIFile
////////////////////////////////////////////////////////////////

  /**
   * Default returns <code>"application/x-baja-directory"</code>
   */
  @Override
  public String getMimeType()
  {
    return "application/x-baja-directory";
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Return the lexicon text used to get the display 
   * name, null if one is not installed.
   */
  public LexiconText getLexiconText()
  {
    return lexText;
  }

  /**
   * Install a lexicon text used to get the display name.
   */
  public void setLexiconText(LexiconText lexText)
  {
    this.lexText = lexText;
  }

  /**
   * Get the display text of the navigation node.  If
   * a LexiconText is installed then use it to get the
   * display name.  Otherwise default to use getNavName().
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    if (lexText != null) return lexText.getText(cx);
    return getNavName();  
  }

  /**
   * Get a short description.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    if (lexText != null) 
      return Lexicon.make(lexText.module, cx).get(lexText.key + ".description", null);
    return null;  
  }
  
  /**
   * Get navigation parent.
   */              
  @Override
  public BINavNode getNavParent()
  {
    if (navParent != null) return navParent;
    else return super.getNavParent();
  }           
                           
  /**
   * Return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }
  
  /**
   * Return <code>getFileSpace().getChild(this, navName)</code>.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    return getFileSpace().getChild(this, navName);
  }
  
  /**
   * Return <code>getFileSpace().getChildren(this)</code>.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return getFileSpace().getChildren(this);
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon ref.
   */
  @Override
  public BIcon getIcon()
  { 
    if (icon != null) return icon;
    return defaultIcon; 
  }
  
  /**
   * Set the icon or pass null to use default.
   */
  public void setIcon(BIcon icon)
  {
    this.icon = icon;
  }
  
  private static final BIcon defaultIcon = BIcon.std("folder.png");

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("workbench:DirectoryList");
    agents.toTop("hx:HxDirectoryView");
    agents.toBottom("web:FileUploadView");
    return agents;
  }
      
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private LexiconText lexText;
  BIcon icon;
  BINavNode navParent;

}
