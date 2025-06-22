/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;

/**
 * RemoveOp is the implementation of SyncOp for a property remove.
 *
 * @author    Brian Frank
 * @creation  20 Aug 01
 * @version   $Revision: 10$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class RemoveOp
  extends SyncOp
{ 

  public RemoveOp(BComponent c, String name)
  {                                                             
    super(c);
    this.name = name;
  }

  public RemoveOp()
  {
  }

  @Override
  public int getId()
  { 
    return REMOVE; 
  }              
  
  public String getSlotName() { return name; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {                 
    Property prop = component.getProperty(name);
    if (prop != null)
      component.remove(prop, context);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    out.attr("n", name).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    name = in.elem().get("n");
  }
  
  public String toString()
  {
    return "Remove: " + componentToString() + "." + name;
  }
  
  String name;
    
}
