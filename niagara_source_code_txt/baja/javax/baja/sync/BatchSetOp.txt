/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import java.util.ArrayList;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;

/**
 * BatchSetOp is the implementation of SyncOp for a batch property set.
 * For backwards compatibility, when serialized, the BatchSetOp is 
 * encoded as a set of SetOps.
 *
 * @author    Scott Hoye
 * @creation  11 Jul 13
 * @version   $Revision: 1$ $Date: 7/11/13 3:31:38 PM EST$
 * @since     Niagara 4.0
 */
class BatchSetOp
  extends SyncOp
{ 

  public BatchSetOp(BComponent c, SetOp[] setOps)
  {
    super(c);
      
    this.setOps = new ArrayList<>(setOps.length);
    for(SetOp setOp: setOps)
    {
      addSet(setOp);
    }
  }

  BComplex getTarget()
  {
    return setOps.get(0).getTarget();
  }
  
  /**
   * Appends the given SetOp to this batch set operation.
   * The given SetOp must have the same component and target.
   * 
   * @param op
   */
  void addSet(SetOp op)
  {
    setOps.add(op);
  }
  
  /**
   * Appends the given SetOp to this batch set operation.
   * The given SetOp must have the same component and target.
   * 
   * @param op
   */
  void removeSet(SetOp op)
  {
    setOps.remove(op);
  }
  
  /**
   * Returns the number of SetOps contained in this batch set operation
   */
  int size()
  {
    return setOps.size();
  }
  
  @Override
  public int getId() 
  { 
    return SET; // We use SET for the Id because when this is encoded/decoded, it is
                // handled as separate SetOps (which can later be reformed into the BatchSetOp
                // if needed).  This is necessary for backwards compatibility.
  }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {                 
    int len = setOps.size();
    if (len <= 0) return; // nothing to do
    
    Property[] properties = new Property[len];
    BValue[] values = new BValue[len];
    
    for (int i = 0; i < len; i++)
    {
      SetOp op = setOps.get(i);
      properties[i] = op.getProperty();
      values[i] = op.getValue();
    }
    
    // do the actual batch set
    getTarget().set(properties, values, context);    
  }

  @SuppressWarnings("squid:S1166")
  @Override
  void encode(SyncEncoder out)
    throws Exception
  {           
    Exception e = null;
    for (SetOp setOp: setOps)
    {
      try
      {
        setOp.encode(out);
      }
      catch(SyncOpSecurityException ignore)
      {
        // We can safely ignore these types of exceptions because they are
        // expected when an operator user does not have access to the property
        // that changed.
      }
      catch(Exception ex)
      {
        // This shouldn't ever happen, but just in case it ever did, delay
        // any exceptions until all SetOps get a chance to process
        if (e == null)
        {
          e = ex;
        }
      }
    }

    if (e != null)
    {
      throw e;
    }
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    throw new UnsupportedOperationException("BatchSetOp.decode should never be called.");
  }
  
  @Override
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append("BatchSet: ");
    for (int i = 0; i < setOps.size(); i++)
    {
      SetOp op = setOps.get(i);
      if (i > 0) s.append("\n          ");
      s.append(op.componentToString())
       .append('.').append(op.getSlotName()).append(" -> ").append(op.getValue());
    }
    return s.toString();
  }
  
  ArrayList<SetOp> setOps;
    
}
