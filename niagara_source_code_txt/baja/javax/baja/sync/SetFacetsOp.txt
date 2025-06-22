/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import com.tridium.nre.util.IElement;

/**
 * SetFacetsOp is the implementation of SyncOp for a facets change.
 *
 * @author    Brian Frank
 * @creation  19 Mar 02
 * @version   $Revision: 8$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class SetFacetsOp
  extends SyncOp
{      

  public SetFacetsOp(BComponent c, String name, BFacets facets)
  {                      
    super(c);
    this.name = name;
    this.facets = facets;
  }

  public SetFacetsOp()
  {
  }

  @Override
  public int getId()
  { 
    return SET_FACETS; 
  }                    
  
  public String getSlotName() { return name; }
  
  public BFacets getFacets() { return facets; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    component.setFacets(component.getSlot(name), facets, context);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    out.attr("n", name);
    out.attr("x", facets.encodeToString()).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    IElement elem = in.elem();
    name = elem.get("n");
    facets = BFacets.make(elem.get("x"));
  }
  
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("SetFacets: ").append(componentToString()).append('.').append(name)
     .append(" -> ").append(facets);
    return s.toString();
  }
  
  String name;
  BFacets facets;
    
}
