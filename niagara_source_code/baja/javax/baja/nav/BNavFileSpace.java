/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import java.util.*;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BNavFileSpace contains a tree of BNavFileNodes and their associated
 * lookup data structures.  BNavFileSpaces are created by parsing a
 * nav file using NavFileDecoder.
 *
 * @author    Brian Frank       
 * @creation  1 Sept 04
 * @version   $Revision: 6$ $Date: 4/26/05 3:46:30 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNavFileSpace
  extends BSpace
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavFileSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.
   */
  public BNavFileSpace(BNavFileNode root)
  {
    super("navfile", null);
    this.root = root;
    addNavChild(root);   
    buildMap(root);
  }

  /**
   * Recursively build an ord to node map for reverse lookups.
   */
  void buildMap(BINavNode node)
  {           
    // map by ord      
    BOrd ord = node.getNavOrd();
    map.put(ord, node); 
    
    // recurse
    BINavNode[] kids = node.getNavChildren();
    for(int i=0; i<kids.length; ++i)
      buildMap(kids[i]);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the root node of the nav file.
   */
  public BNavFileNode getRootNode()
  {
    return root;
  }
  
  /**
   * Given a ord, lookup the NavFileNode that maps to that 
   * ord, or return null if no match found.
   */
  public BNavFileNode lookup(BOrd ord)
  {                 
    // first try ord as is    
    BNavFileNode node = (BNavFileNode)map.get(ord);
    if (node != null) return node;
    
    // next try to strip off view query if present
    OrdQuery[] q = ord.parse();    
    if (q.length > 0 && q[q.length-1] instanceof ViewQuery)
    {           
      ord = BOrd.make(q, 0, q.length-1);  
      node = (BNavFileNode)map.get(ord);
      if (node != null) return node;
    }
    
    // no luck
    return null;
  }
  
////////////////////////////////////////////////////////////////
// BSpace
////////////////////////////////////////////////////////////////  

  @Override
  public BOrd getOrdInSession()
  {                   
    // this should never be used since the nav file tree
    // doesn't have its own scheme, but rather is just a tree
    // of hyperlinks to other ords
    return BOrd.make("dummy:");
  }
  
////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////

  /*  
  public BPermissions getPermissions(Context cx)
  { 
    if (cx != null && cx.getUser() != null)
      return cx.getUser().getPermissionsFor(this);
    else
      return BPermissions.all;
  }
  
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorRead();
  }
  
  public boolean canWrite(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorWrite();
  }

  public boolean canInvoke(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasOperatorInvoke();
  } 
  */
    
////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////  

  @Override
  public String getNavDisplayName(Context cx)
  {
    if (root == null) return super.getNavDisplayName(cx);
    return root.getNavDisplayName(cx);
  }

  @Override
  public String getNavDescription(Context cx)
  {                              
    return Lexicon.make("baja", cx).getText("nav.navFileSpace.description");
  }

  @Override
  public boolean hasNavChildren()
  {
    return root != null;
  }

  @Override
  public BINavNode getNavChild(String name)
  {
    if (root == null) return null;
    return root.getNavChild(name);
  }
  
  @Override
  public BINavNode[] getNavChildren()
  {
    if (root == null) return new BINavNode[0];
    return root.getNavChildren();
  }
  
  @Override
  public BIcon getNavIcon()
  {
    if (root == null) return getIcon();
    return root.getNavIcon();
  }

  @Override
  public BOrd getNavOrd()
  {
    if (root == null) return super.getNavOrd();
    return root.getNavOrd();
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  BNavFileNode root;
  Map<BOrd, BINavNode> map = new HashMap<>();
}
