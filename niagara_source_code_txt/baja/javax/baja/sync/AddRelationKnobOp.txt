/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.RelationKnob;
import com.tridium.sys.engine.NRelationKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * AddRelationKnobOp maps to the relationknob added component event.
 *
 * @author    Andy Saunders
 * @creation  18 Apr 14
 * @since     Baja 4.0
 */
public class AddRelationKnobOp
  extends SyncOp
{

  public AddRelationKnobOp(BComponent c, RelationKnob relationKnob)
  {
    super(c);
    this.relationKnob = relationKnob;
  }

  public AddRelationKnobOp()
  {
  }

  @Override
  public int getId()
  { 
    return ADD_RELATION_KNOB;
  }                   
  
  public RelationKnob getKnob() { return relationKnob; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    if (relationKnob != null)
      ((ComponentSlotMap)component.fw(Fw.SLOT_MAP)).installRelationKnob((NRelationKnob)relationKnob, true);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {                   
    super.encode(out); 
    out.endAttr().newLine();
        
    out.key("nrk");
    out.encodeRelationKnob((NRelationKnob)this.relationKnob);
    
    out.end(String.valueOf((char)getId())).newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);   
    in.next();         
    relationKnob = in.decodeRelationKnob(component);
  }                   
  
  public String toString()
  {
    return "AddRelationKnob: " + componentToString() + " " + relationKnob;
  }
  
  RelationKnob relationKnob;
    
}
