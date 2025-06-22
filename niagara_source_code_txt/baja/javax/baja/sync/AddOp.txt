/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.security.BPermissions;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.DuplicateSlotException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;

import com.tridium.nre.util.IElement;
import com.tridium.sys.schema.ComplexSlotMap;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * AddOp is the implementation of SyncOp for a property add.
 *
 * @author    Brian Frank
 * @creation  16 Jul 01
 * @version   $Revision: 15$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class AddOp
  extends SyncOp
{ 

 /**
  * Constructor with parameters.
  * The BComponent will be fully serialized during encoding.
  */
  public AddOp(BComponent c, String name, BValue value, int flags, BFacets facets)
  {                               
    this(c, name, value, flags, facets, Integer.MAX_VALUE);
  }

  /**
   * Constructor with parameters.
   *
   * @param depth The depth parameter is used to specify how deep to serialize the BComponent tree.
   * @since Niagara 4.8
   */
  public AddOp(BComponent c, String name, BValue value, int flags, BFacets facets, int depth)
  {
    super(c);
    this.name = name;
    this.value = value;
    this.flags = flags;
    this.facets = facets;
    this.depth = depth;
  }

  public AddOp()
  {
  }



  @Override
  public int getId()
  { 
    return ADD; 
  }                  
  
  public String getSlotName() { return name; }

  public BValue getValue() { return value; }

  public int getFlags() { return flags; }

  public BFacets getFacets() { return facets; }
  
  /**
   * Return the name of the newly added Slot.
   * <p>
   * This returns the name of the newly added Property from the add. If the 
   * SyncOp is not yet committed, this method will return null.
   * 
   * @since Niagara 3.7
   */
  public String getNewSlotName() { return newName; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {                                                 
    try
    {
      ComponentSlotMap slotMap = (ComponentSlotMap)component.fw(Fw.SLOT_MAP);
      ComplexSlotMap valueSlotMap = (ComplexSlotMap)value.fw(Fw.SLOT_MAP);
      Property prop = slotMap.add(name, flags, value, valueSlotMap, facets, context, parent.listener);
      
      if (prop != null) newName = prop.getName();
    }
    catch(DuplicateSlotException e)
    {     
      // silently ignore dup slot exceptions if proxy space
      if (!space.isProxyComponentSpace())                                                 
        throw e;
    }
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {
    if (value.isComponent())
    {
      BPermissions permissions = out.getPermissionsFor(value.asComponent());
      if (!permissions.hasOperatorRead())
        throw new SyncOpSecurityException("Missing op read permission on value");
    }
    super.encode(out);
    if (name != null) out.attr("n", name);
    if (flags != 0) out.attr("f", Flags.encodeToString(flags));
    if (facets != null && !facets.isNull()) out.attr("facets", facets.encodeToString());
    out.endAttr().newLine().key("b");

    out.encode(null, value, depth);
    out.end(String.valueOf((char)getId())).newLine();
  }



  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);     
    IElement elem = in.elem();
    name = elem.get("n", null);
    flags = Flags.decodeFromString(elem.get("f", "0"));
    facets = (BFacets)BFacets.DEFAULT.decodeFromString(elem.get("facets", ""));
    in.next();
    value = in.decode();
  }
  
  public String toString()
  {
    return "Add: " + componentToString() + " -> " + name + " = " + value;
  }
  
  String name;
  BValue value;
  int flags;
  BFacets facets;
  String newName;
  int depth;
    
}
