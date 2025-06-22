/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.category.BCategoryMask;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import com.tridium.nre.util.IElement;

/**
 * SetCategoryMaskOp is the implementation of SyncOp for a BCategoryMask change.
 *
 * @author    Brian Frank
 * @creation  19 Mar 02
 * @version   $Revision: 8$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class SetCategoryMaskOp
  extends SyncOp
{      

  public SetCategoryMaskOp(BComponent c, BCategoryMask mask)
  {                      
    super(c);
    this.mask = mask.encodeToString();
  }

  public SetCategoryMaskOp(BComponent c, String mask)
  {                      
    super(c);
    this.mask = mask;
  }

  public SetCategoryMaskOp()
  {
  }

  @Override
  public int getId()
  { 
    return SET_CATEGORY_MASK; 
  }                  
  
  public BCategoryMask getMask() { return BCategoryMask.make(mask); }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {
    component.setCategoryMask(BCategoryMask.make(mask), context);
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    out.attr("c", mask).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    IElement elem = in.elem();
    mask  = elem.get("c");
  }
  
  public String toString()
  {
    StringBuilder  s = new StringBuilder();
    s.append("SetCatMask: ").append(componentToString())
     .append(" -> ").append(mask);
    return s.toString();
  }
  
  String name;
  String mask;
    
}
