/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.RelationKnob;
import com.tridium.nre.util.IElement;
import com.tridium.sys.engine.NRelationKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * RemoveRelationKnobOp maps to the relation knob removed component event.
 *
 * @author    Andy Saunders
 * @creation  179 Apr 14
 * @since     Baja 4.0
 */
public class RemoveRelationKnobOp
  extends SyncOp
{

  public RemoveRelationKnobOp(BComponent c, RelationKnob knob)
  {
    super(c);
    this.knob = knob;
  }

  public RemoveRelationKnobOp()
  {
  }

  @Override
  public int getId()
  { 
    return REMOVE_RELATION_KNOB;
  }             
  
  public RelationKnob getRelationKnob() { return knob; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  { 
    if (knob != null)
     ((ComponentSlotMap)component.fw(Fw.SLOT_MAP)).uninstallRelationKnob((NRelationKnob)knob);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    NRelationKnob knob = (NRelationKnob)this.knob;
    
    out.attr("id", String.valueOf(knob.id)).end().newLine();
//    out.attr("ss", String.valueOf(knob.getSourceSlotName())).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);     
    if (component == null) return;
    IElement elem = in.elem();
    int id = elem.geti("id");
    NRelationKnob[] knobs = (NRelationKnob[])component.getRelationKnobs();
    for(int i=0; i<knobs.length; ++i)
    {
      if (knobs[i].id == id)
      {
        knob = knobs[i];
        break;
      }
    }
  }
  
  public String toString()
  {
    return "RemoveKnob: " + componentToString() + " " + knob;
  }
  
  RelationKnob knob;
    
}
