/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import static javax.baja.sync.ProxyBroker.LOG;

import java.util.logging.Level;
import javax.baja.security.BPermissions;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;

/**
 * SetOp is the implementation of SyncOp for a property set.
 *
 * @author    Brian Frank on 16 Jul 01
 * @version   $Revision: 13$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class SetOp
  extends SyncOp
{

  /**
   * Constructor with parameters.
   * The BComponent will be fully serialized during encoding.
   */
  public SetOp(BComponent c, String name, BValue value)
  {
    this(c, name, value, Integer.MAX_VALUE);
  }

  /**
   * Constructor with parameters.
   *
   * @param depth The depth parameter is used to specify how deep to serialize the BComponent tree.
   * @since Niagara 4.8
   */
  public SetOp(BComponent c, String name, BValue value, int depth)
  {
    super(c);
    this.name = name;
    this.value = value;
    this.timestamp = BAbsTime.now();
    this.depth = depth;
  }

  public SetOp()
  {
  }

  @Override
  public int getId()
  { 
    return SET; 
  }                
  
  public String getSlotName() { return name; }

  public BValue getValue() { return value; }

  public BAbsTime getTimestamp() { return timestamp; }

  BComplex getTarget()
  {
    if (target == null)
    {
      target = component;
      
      // walk down until we get the target complex
      String path = this.name; 
      String curName = path;
      int index = 0;      
      while(true)
      {   
        // get the next name in the segment
        int slash = name.indexOf('/', index);
        
        // if that is the last name, then break
        if (slash < 0) 
        {
          if (index > 0) curName = path.substring(index);
          break;
        }
        
        // otherwise dive into struct
        curName = path.substring(index, slash);
        index = slash+1;
        target = (BComplex)target.get(curName);      
        if (target == null)
          throw new IllegalStateException("Missing property " + path);
      }           
      
      // lookup the property to set
      property = target.getProperty(curName);
      if (property == null)
        throw new IllegalStateException("Missing property " + path);
    }
    return target;
  }
  
  Property getProperty()
  {
    if (property == null)
      getTarget();
    return property;
  }
  
  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {                 
    // do the actual set
    if (getProperty() == null)
    {
      //force a re-sync the target & property in the getTarget() call below
      //  in case the property was added earlier in the same SyncBuffer
      target = null;
    }
    getTarget().set(getProperty(), value, context);    
  }                 

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {           
    BValue v = value;
    if (v == null) v = component.get(name);  
    
    // if no value that means we are setting a value
    // which has already been removed, just use a dummy
    // value - the other side should ignore the set when
    // it fails to lookup the property
    if (v == null)
    {
      if (LOG.isLoggable(Level.WARNING))
      {
        LOG.warning("SetOp null value: " + name);
      }
      return;
    }

    if (v.isComponent())
    {
      BPermissions permissions = out.getPermissionsFor(v.asComponent());

      if (!permissions.hasOperatorRead())
        throw new SyncOpSecurityException("Missing op read permission on component");
    }
    else
    {
      // If the value isn't a Component then run a security check using the operator slot flag.

      // Judging by 'getTarget()', it appears the property name can contain slashes to
      // indicate a property path. Therefore, we need to take this into account when
      // attempting to find the top Component's property.
      String propName = name;
      int index = propName.indexOf("/");
      if (index > -1) propName = propName.substring(0, index);

      Slot slot = component.getSlot(propName);
      if (slot != null && slot instanceof Property)
      {
        Property prop = (Property)slot;
        BPermissions permissions = out.getPermissionsFor(component);

        // Check whether the user has permissions based upon the 'operator' slot flag.
        int flags = component.getFlags(prop);
        if ((flags & Flags.OPERATOR) != 0)
        {
          if (!permissions.has(BPermissions.operatorRead))
            throw new SyncOpSecurityException("Missing op read permission on value");
        }
        else
        {
          if (!permissions.has(BPermissions.adminRead))
            throw new SyncOpSecurityException("Missing admin read permission on value");
        }
      }
    }
    
    super.encode(out);
    
    out.attr("n", name).endAttr().newLine().key("b");
    out.encode(null, v, depth);
    out.end(String.valueOf((char)getId())).newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    name = in.elem().get("n"); 
    in.next();
    value = in.decode();
  }
  
  public String toString()
  {
    return "Set: " + componentToString() + "." + name + " -> " + value;
  }
  
  String name;
  BValue value;
  BComplex target;
  Property property;
  private BAbsTime timestamp;
  private int depth;
}
