/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Knob;

import com.tridium.sys.engine.NKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * AddKnobOp maps to the knob added component event.
 *
 * @author    Brian Frank
 * @creation  17 Nov 03
 * @version   $Revision: 11$ $Date: 6/15/11 6:26:43 AM EDT$
 * @since     Baja 1.0
 */
public class AddKnobOp
  extends SyncOp
{ 

  public AddKnobOp(BComponent c, Knob knob)
  {                   
    super(c);
    this.knob = knob;
  }

  public AddKnobOp()
  {
  }

  @Override
  public int getId()
  { 
    return ADD_KNOB; 
  }                   
  
  public Knob getKnob() { return knob; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    if (knob != null)
      ((ComponentSlotMap)component.fw(Fw.SLOT_MAP)).installKnob((NKnob)knob, true);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {                   
    super.encode(out); 
    out.endAttr().newLine();
        
    out.key("nk");
    out.encodeKnob((NKnob)this.knob);
    
    out.end(String.valueOf((char)getId())).newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);   
    in.next();         
    knob = in.decodeKnob(component);
  }                   
  
  public String toString()
  {
    return "AddKnob: " + componentToString() + " " + knob;
  }
  
  Knob knob;
    
}
