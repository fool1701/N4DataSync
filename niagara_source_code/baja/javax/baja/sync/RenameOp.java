/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import com.tridium.nre.util.IElement;

/**
 * RenameOp is the implementation of SyncOp for a property rename.
 *
 * @author    Brian Frank
 * @creation  16 Jul 01
 * @version   $Revision: 11$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class RenameOp
  extends SyncOp
{ 

  public RenameOp(BComponent c, String oldName, String newName)
  {                   
    super(c);
    this.oldName = oldName;
    this.newName = newName;
  }

  public RenameOp()
  {
  }

  @Override
  public int getId()
  { 
    return RENAME; 
  }

  public String getOldName() { return oldName; }

  public String getNewName() { return newName; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    Property prop = component.getProperty(oldName);
    if (prop != null)
      component.rename(prop, newName, context);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    out.attr("o", oldName);
    out.attr("n", newName).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    IElement elem = in.elem();
    oldName = elem.get("o");
    newName = elem.get("n");
  }
  
  public String toString()
  {
    return "Rename: " + componentToString() + "." + oldName + " -> " + newName;
  }
  
  String oldName;
  String newName;
    
}
