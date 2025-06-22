/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import com.tridium.nre.util.IElement;

/**
 * SetFlagsOp is the implementation of SyncOp for a flags change.
 *
 * @author    Brian Frank
 * @creation  19 Mar 02
 * @version   $Revision: 10$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class SetFlagsOp
  extends SyncOp
{      

  public SetFlagsOp(BComponent c, String name, int flags)
  {                      
    super(c);
    this.name = name;
    this.flags = flags;
  }

  public SetFlagsOp()
  {
  }

  @Override
  public int getId()
  { 
    return SET_FLAGS; 
  }                      
  
  public String getSlotName() { return name; }

  public int getFlags() { return flags; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    component.setFlags(component.getSlot(name), flags, context);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    out.attr("n", name);
    out.attr("f", Flags.encodeToString(flags)).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    IElement elem = in.elem();
    name  = elem.get("n");
    flags = Flags.decodeFromString(elem.get("f"));
  }
  
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("SetFlags: ").append(componentToString()).append('.').append(name)
     .append(" -> ").append(Flags.encodeToString(flags));
    return s.toString();
  }

  String name;
  int flags;
    
}
