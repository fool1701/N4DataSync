/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.util.LexiconText;

/**
 * BNavContainer is a generic BINavNode that allows mounting
 * and unmounting of other arbitary BINavNodes.
 *
 * @author    Brian Frank       
 * @creation  22 Jan 03
 * @version   $Revision: 20$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BNavContainer
  extends BObject
  implements BINavNode, BINavContainer
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavContainer(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor 
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.
   */
  protected BNavContainer(String name, LexiconText lexText)
  {
    this.name = name;
    this.lexText = lexText;
  }

  /**
   * Constructor.
   */
  protected BNavContainer(String name)
  {
    this.name = name;
  }

////////////////////////////////////////////////////////////////
// Access
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

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get the name passed to the constructor.
   */
  @Override
  public String getNavName()
  {
    return name;
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
   * If a LexiconText is installed, then attempt to return
   * it's module's lexicon key + ".description".
   */
  @Override
  public String getNavDescription(Context cx)
  {    
    if (lexText != null) 
      return Lexicon.make(lexText.module, cx).get(lexText.key + ".description", null);
    return null;
  }

  /**
   * If this instance has been mounted under another
   * BNavContainer, then return it.  Otherwise the default
   * implementation is to return null.
   */
  @Override
  public BINavNode getNavParent()
  {
    return parent;
  }

  /**
   * Return is any children have been added.
   */
  @Override
  public boolean hasNavChildren()
  {
    return !byName.isEmpty();
  }

  /**
   * Get the child by the specified name, or 
   * return null if not found.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    return byName.get(navName);
  }

  /**
   * Get the child by the specified name, or throw
   * UnresolvedException if not found.
   */
  @Override
  public BINavNode resolveNavChild(String navName)
  {
    BINavNode node = getNavChild(navName);
    if (node == null) throw new UnresolvedException(navName);
    return node;
  }
  
  /**
   * Get the children nodes for this navigation node.
   * Return an array of length zero if there are no 
   * children.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return byOrder.toArray(new BINavNode[byOrder.size()]);
  }

  /**
   * Default implementation is to call BObject.getIcon().
   */
  @Override
  public BIcon getNavIcon()
  {
    return getIcon();
  }

////////////////////////////////////////////////////////////////
// Child Management
////////////////////////////////////////////////////////////////  
  
  /**
   * Add the specified BINavNode to this container.
   */
  @Override
  public void addNavChild(BINavNode child)
  {
    // error checking
    String name = child.getNavName();
    if (byName.get(name) != null)
      throw new IllegalArgumentException("duplicate name " + name);
        
    // add to data structures  
    byName.put(name, child);
    byOrder.add(child);    
    
    // if container
    if (child instanceof BNavContainer)
    {
      BNavContainer container = (BNavContainer)child;
      container.parent = this;
    }
    
    // fire event      
    fireNavEvent(NavEvent.makeAdded(this, name, null));
  }

  /**
   * Remove the specified BINavNode from this container.
   */
  @Override
  public void removeNavChild(BINavNode child)
  {
    // error checking
    String name = child.getNavName();
    if (byName.get(name) != child)
      throw new IllegalArgumentException("not child " + name);
        
    // add to data structures  
    byName.remove(name);
    byOrder.remove(child);  
    
    // if container
    if (child instanceof BNavContainer)
    {
      BNavContainer container = (BNavContainer)child;
      container.parent = null;
    }
    
    // fire event
    fireNavEvent(NavEvent.makeRemoved(this, name, null));
  }

  /**
   * Clear the list of nav children so the subclass can re-add
   * @since Niagara 4.0
   */
  protected void clearNavChildren()
  {
    byName.clear();
    byOrder.clear();
  }

  /**
   * Reorder the nav children.  The list of children passed must
   * be instances returned from <code>getNavChildren</code>.
   */
  @Override
  public void reorderNavChildren(BINavNode[] children)
  {                                                 
    if (children.length != this.byOrder.size())
      throw new IllegalArgumentException("children.length != getNavChildren().length");
      
    List<BINavNode> newList = new ArrayList<>(children.length);
    String[] newNames = new String[children.length];
    for(int i=0; i<children.length; ++i)
    {                        
      BINavNode child = children[i];
      String name = child.getNavName();
      
      if (byName.get(name) != child)
        throw new IllegalStateException("children at " + i + " not in getNavChildren()");
      
      newList.add(child);       
      newNames[i] = name;
    }                                         
              
    this.byOrder = newList;
    fireNavEvent(NavEvent.makeReordered(this, newNames, null));
  }                   
  
  /**
   * This callback is used to provide a hook for processing the 
   * NavEvents generated by child add, remove, and reorder.  The 
   * default implementation routes to BNavRoot.INSTANCE.fireNavEvent.
   */
  protected void fireNavEvent(NavEvent event)
  {                                                            
    BNavRoot.INSTANCE.fireNavEvent(event);
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  
  
  /**
   * To string.
   */
  @Override
  public String toString(Context cx)
  {
    return name;
  }

  /**
   * Dump links to the children.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    
    out.startProps();
    out.trTitle("NavContainer", 2);
    out.prop("navName", getNavName());
    out.prop("navDisplayName", getNavDisplayName(null));
    out.prop("navOrd", getNavOrd());
    out.endProps();   
    
    BINavNode[] kids = getNavChildren();
    if (kids != null && kids.length > 0)
    {
      out.startTable(false);
      out.trTitle("Children", 1);
      for(int i=0; i<kids.length; ++i)
      {
        BObject kid = (BObject)kids[i];
        if (kid == null) { out.tr("ERROR NULL"); continue; }
        String navName = kids[i].getNavName();
        //out.tr("<a href='" + SlotPath.escape(navName) + "'>" + navName + "</a> [" + kid.getType() + "] " + kid.toString());
        out.tr().td()
          .a(SlotPath.escape(navName), navName)
          .w(" [").safe(kid.getType()).w("] ")
          .safe(kid)
        .endTd().endTr();
      }
      out.endTable();
    }
  }                       

  
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  /**
   * Filter an array of nav nodes to produce a new array of
   * nodes by checking security permissions for the specified
   * context.  This method is typically used server side such
   * as in a web servlet.
   */
  public static BINavNode[] filter(BINavNode[] nodes, Context cx)
  {                
    try
    {                     
      // short circuit
      if (cx == null || cx.getUser() == null) 
        return nodes; 
      
      // check each node against user
      List<BINavNode> acc = new ArrayList<>(nodes.length);
      for(int i=0; i<nodes.length; ++i)
      {
        BINavNode node = nodes[i];
        if (node instanceof BIProtected)
        {      
          BPermissions p = ((BIProtected)node).getPermissions(cx);
          if (!p.has(BPermissions.OPERATOR_READ))
            continue;
        }
        acc.add(node);
      }                              
      
      // return nodes which passed
      return acc.toArray(new BINavNode[acc.size()]);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return nodes;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BINavNode parent;
  String name;
  LexiconText lexText;
  List<BINavNode> byOrder = new ArrayList<>();
  Map<String, BINavNode> byName = new HashMap<>();
  
  
}
