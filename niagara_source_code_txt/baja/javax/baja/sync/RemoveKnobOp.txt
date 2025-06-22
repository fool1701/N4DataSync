/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Knob;
import javax.baja.sys.Slot;

import com.tridium.nre.util.IElement;
import com.tridium.sys.engine.NKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * RemoveKnobOp maps to the knob removed component event.
 *
 * @author    Brian Frank
 * @creation  17 Nov 03
 * @version   $Revision: 10$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class RemoveKnobOp
  extends SyncOp
{ 

  public RemoveKnobOp(BComponent c, Knob knob)
  {                   
    super(c);
    this.knob = knob;
  }

  public RemoveKnobOp()
  {
  }

  @Override
  public int getId()
  { 
    return REMOVE_KNOB; 
  }             
  
  public Knob getKnob() { return knob; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  { 
    if (knob != null)
     ((ComponentSlotMap)component.fw(Fw.SLOT_MAP)).uninstallKnob((NKnob)knob);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out); 
    NKnob knob = (NKnob)this.knob;
    
    out.attr("id", String.valueOf(knob.id));
    out.attr("ss", String.valueOf(knob.getSourceSlotName())).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);     
    if (component == null) return;
    IElement elem = in.elem();
    int id = elem.geti("id");
    Slot slot = component.getSlot(elem.get("ss"));
    if (slot != null)
    {
      Knob[] knobs = component.getKnobs(slot);
      for(int i=0; i<knobs.length; ++i)
      {
        if (((NKnob)knobs[i]).id == id)
        {
          knob = knobs[i];
          break;
        }
      }
    }
  }
  
  public String toString()
  {
    return "RemoveKnob: " + componentToString() + " " + knob;
  }
  
  Knob knob;
    
}
