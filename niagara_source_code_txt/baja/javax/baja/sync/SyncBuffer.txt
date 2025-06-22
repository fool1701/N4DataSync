/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import static javax.baja.sync.ProxyBroker.LOG;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.baja.category.BCategoryMask;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.xml.XParser;
import com.tridium.nre.util.IElement;
import com.tridium.sys.transfer.TransferListener;
import com.tridium.sys.transfer.TransferStrategy;

/**
 * Batch stores a collection of cached changes to a set of 
 * components including support for coalescing of property 
 * changes.  Batches are used for bi-directional synchronization 
 * between two BComponentSpaces.
 *
 * @author    Brian Frank
 * creation  11 Nov 03
 * @version   $Revision: 21$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class SyncBuffer
{ 
                                    
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with component space.
   */                
  public SyncBuffer(BComponentSpace space, boolean coalesce)
  {
    this.space = space;           
    this.loads = new OpLinkedList();            
    this.ops   = new OpLinkedList();            
    this.coalesce = coalesce;
    if (coalesce) this.propChanges = new HashMap<>();
  }                    

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the component space this batch maps changes for.
   */
  public BComponentSpace getSpace()
  {
    return space;
  }                     
  
  /**
   * Return true if this buffer is coalescing operations.
   */
  public boolean isCoalesced()
  {                                
    return coalesce;
  }

////////////////////////////////////////////////////////////////
// Encode
////////////////////////////////////////////////////////////////
  
  /**
   * Encode this batch of operations to the encoder.
   */
  public void encode(SyncEncoder out)
    throws Exception
  {                      
    out.start("sync").attr("ver", "1.0");   
    if (id > -1) out.attr("id", id); 
    out.endAttr().newLine(); 
    
    out.startArray("ops");
    
    SyncOp[] ops = list();
    for(int i=0; i<ops.length; ++i)
    {
      try
      {
        ops[i].encode(out);
      }
      catch(SyncOp.SyncOpSecurityException e) { } // safe to suppress
    }  
    
    out.endArray();    
    out.end("sync").newLine();
  }
  
  /**
   * Decode a batch of operations.
   */
  public void decode(SyncDecoder in)
    throws Exception
  {                                      
    if (loads.head != null || ops.head != null)
      throw new IllegalStateException("Already contains changes");
    
    IElement root = in.elem();
    if (root == null) { in.next(); root = in.elem(); }
    
    // if still null, then we've gotten disconnected, just bail
    if (root == null) return;

    if (!root.name().equals("sync"))
      throw in.err("Invalid root element " + root.name());
    
    id = root.geti("id", -1);

    while(true)
    { 
      // advance parse to next element
      int ptype = in.next();
      
      // if we have reached end element, we are done
      if (ptype == XParser.ELEM_END) 
      {
        if (!in.elem().name().equals("sync"))
          throw in.err("Expected end sync");
        break;
      }
      
      // otherwise we expect to be at an 
      // element node, not a text node
      if (ptype != XParser.ELEM_START) 
        throw in.err("Expected element start");

      // get starting depth
      int depth = in.depth();
      
      // parse the current element into a SyncOp
      IElement elem = in.elem();                                              
      int id = elem.name().charAt(0);
      SyncOp change = SyncOp.make(id);
      change.decode(this, space, in);
      add(change);
      
      // skip anything which might have gotten left behind
      in.skip(depth);
    }
  }
  
////////////////////////////////////////////////////////////////
// Commit
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code commit(null)}.
   */
  public final void commit() 
    throws Exception
  {             
    commit(null);
  }

  /**
   * Start the commit process.  This is a subclass hook
   * for two purposes.  First, to set up transaction
   * boundaries and second, to manipulate the ops to
   * be committed if necessary. It is guaranteed that
   * either endCommit() or abortCommit() will be called
   * after a call to startCommit();
   *
   * @param ops The list of ops to be committed.  If the
   *   subclass does not wish to manipulate the ops, the
   *   original list must be returned.
   */
  protected SyncOp[] startCommit(SyncOp[] ops, Context cx)
  {
    return ops;
  }

  /**
   * End the commit process.  This is a subclass hook
   * to mark the end of a successful commit. It is
   * guaranteed that either endCommit() or abortCommit()
   * will be called after a call to startCommit();
   */
  protected void endCommit()
    throws Exception
  {
  }

  /**
   * Abort the commit process.  This is a subclass hook
   * to mark the end of an aborted commit.  It is
   * guaranteed that either endCommit() or abortCommit()
   * will be called after a call to startCommit();
   *
   * @param cause The exception, if any, that caused the
   *   abort.  This may be null.
   */
  protected void abortCommit(Exception cause)
  {
  }

  /**
   * Commit all the changes stored up in this batch to 
   * the component space.
   */
  public void commit(Context context) 
    throws Exception
  {
    // get the list of changes                  
    SyncOp[] ops = startCommit(list(), context);
    
    try
    {
      // don't auto start any components added until 
      // the entire transaction is complete
      Context commitContext = context;
      if (!space.isProxyComponentSpace())
        commitContext = new BasicContext(context, noAutoStartFacets);
  
      // commit each change
      for(int i=0; i<ops.length; ++i)
      {
        if (ops[i].committed) continue;
        commitOp(ops[i], commitContext);
      }
      
      // now attempt to start any components set/added
      // If this is the recursive case, make sure this only
      // happens once (when all SyncOps in the list have completed)!
      if (checkForAutoStart)
      {
        checkForAutoStart = false;
        for(int i=0; i<ops.length; ++i)
        {
          try
          {
            SyncOp op = ops[i];
            if (op instanceof AddOp) checkAutoStart( ((AddOp)op).value );
            if (op instanceof SetOp) checkAutoStart( ((SetOp)op).value );
            if (op instanceof BatchSetOp)
              ((BatchSetOp)op).setOps.forEach(setOp -> checkAutoStart(setOp.value));
          }
          catch(Exception e)
          {
            e.printStackTrace();
          }
        }
      }
      
      endCommit();
    }
    catch(Exception ex)
    {
      abortCommit(ex);
      throw ex;
    }
  }
  
  protected void commitOp(SyncOp op, Context cx)
    throws Exception
  {
    try
    {                
      if (op.committed) return; // If already committed this op, skip it
      op.committed = true;
      
      // Check for the special case where the op.component is null.  One of the
      // previous operations may have resolved this component now (ie. an AddOp or LoadOp).  
      // So we should recheck the handle to see if it now exists.
      if (op.component == null)
      {
        try { op.component = findByHandle(op.handle, false); }
        catch(Exception e) { e.printStackTrace(); }
      }
      
      if (op.component != null)
        op.commit(this, space, cx);
    }
    catch(Exception e)
    {
      if (LOG.isLoggable(Level.SEVERE))
      {
        LOG.log(Level.SEVERE, "ERROR: SyncOp.commit: " + op, e);
      }
      
      // if this isn't a proxy space, then re-throw the 
      // exception so that client knows an error occured
      if (!space.isProxyComponentSpace())                             
        throw e;
    }
  }
  
  /**
   * Check if value is a BComponent which requires auto start.
   */
  protected void checkAutoStart(BValue value)
  {
    if (value instanceof BComponent)
    {
      BComponent comp = (BComponent)value;
      BComponent parent = (BComponent)comp.getParent();
      if (parent != null && parent.isRunning())
      {
        if (listener != null) 
          listener.updateStatus("Starting \"" + TransferStrategy.toString(comp) + "\"...");
        comp.start();
      }
    }
  }

  /**
   * Find a component by handle in this buffer's component space.
   */
  protected BComponent findByHandle(Object handle, boolean autoLoad)
  {
    return space.findByHandle(handle, autoLoad);
  }
  
////////////////////////////////////////////////////////////////
// Direct Operations
////////////////////////////////////////////////////////////////

  /**
   * Add new SetOp to buffer.
   */
  public void set(BComponent c, Property[] propertyPath, BValue value, Context context)
  {                 
    // if we are coalescing then it is safe to use just
    // the root property and null for the value - we
    // will fetch the value when we are encoded
    if (coalesce)
    {
      add(new SetOp(c, propertyPath[0].getName(), null));
      return;
    }
    
    // get the name, which may actually be a path
    String name;
    if (propertyPath.length == 1)    
    {        
      name = propertyPath[0].getName();
    }       
    else
    {
      StringBuilder s = new StringBuilder(propertyPath[0].getName());
      for(int i=1; i<propertyPath.length; ++i)    
        s.append('/').append(propertyPath[i].getName());
      name = s.toString();
    }
    add(new SetOp(c, name, value));
  }
  
  /**
   * Add new AddOp to buffer.
   */
  public void add(BComponent c, String name, BValue value, int flags, BFacets facets, Context context)
  {
    add(new AddOp(c, name, value, flags, facets));
  }
  
  /**
   * Add new RemoveOp to buffer.
   */
  public void remove(BComponent c, Property prop, Context context)
  {
    add(new RemoveOp(c, prop.getName()));
  }
  
  /**
   * Add new RenameOp to buffer.
   */
  public void rename(BComponent c, Property prop, String newName, Context context)
  {
    add(new RenameOp(c, prop.getName(), newName));
  }
  
  /**
   * Add new ReorderOp to buffer.
   */
  public void reorder(BComponent c, Property[] order, Context context)
  {
    String[] names = new String[order.length];
    for(int i=0; i<names.length; ++i)
      names[i] = order[i].getName();
    add(new ReorderOp(c, names));
  }

  /**
   * Add new SetFlagsOp to buffer.
   */
  public void setFlags(BComponent c, Slot slot, int flags, Context context)
  {            
    add(new SetFlagsOp(c, slot.getName(), flags));
  }

  /**
   * Add new SetFacetsOp to buffer.
   */
  public void setFacets(BComponent c, Slot slot, BFacets facets, Context context)
  {            
    add(new SetFacetsOp(c, slot.getName(), facets));
  }

  /**
   * Add new SetCategoryMaskOp to buffer.
   */
  public void setCategoryMask(BComponent c, BCategoryMask mask, Context context)
  {            
    add(new SetCategoryMaskOp(c, mask));
  }
  
  /**
   * Give the buffer an id number (used when encoding).
   */
  public void setId(int id)
  {
    this.id = id;
  }
  
  /**
   * Return the id for the buffer.
   */
  public int getId()
  {
    return id;
  }

////////////////////////////////////////////////////////////////
// Changes Linked List
////////////////////////////////////////////////////////////////
  
  /**
   * Get a list of all the pending changes.
   */
  public synchronized SyncOp[] list()
  {                                        
    SyncOp[] r = new SyncOp[loads.size + ops.size];
    
    SyncOp p;
    int i = 0;
    for(p = loads.head; p != null; ++i) { r[i] = p; p = p.next; }
    for(p = ops.head; p != null; ++i) { r[i] = p; p = p.next; }
    
    return r;
  }  
  
  /**
   * Add a change into the batch.  If the change is a property
   * change then this overwrites any previous property changes.
   */
  public synchronized void add(SyncOp op)
  { 
    // make sure no in another linked list                         
    if (op.prev != null || op.next != null)
      throw new IllegalStateException("Already in linked list " + op);
    
    // Whenever a SetOp is added, we must check to see if it needs to 
    // be merged into a single BatchSetOp with any consecutively prior 
    // SetOps (or BatchSetOps) for the same target complex    
    if (op instanceof SetOp)
    {                                   
      if (coalesce)
        coalesceSet((SetOp)op);
      else
        appendSet((SetOp)op);
    }                                 
    else if (op instanceof LoadOp)
    {
      loads.append(op);
    }
    else
    {
      ops.append(op);
    }
  }
  
  /**
   * Add a property change and coalesce if duplicate is found.
   */
  private void coalesceSet(SetOp op)
  {                   
    BComponent component = op.component;          
    String name = op.name;
    
    Map<String, SyncOp> props = propChanges.get(component);
    if (props != null)
    {                       
      // if old found remove it  
      SyncOp old = props.get(name);
      if (old != null)
      {
        if (old instanceof SetOp)
          ops.remove(old);
        else
        {
          BatchSetOp batch = (BatchSetOp)old;
          batch.setOps.removeIf(setOp -> ((setOp != null) && (setOp.name.equals(name))));

          if (batch.size() <= 0)
            ops.remove(old);
        }
      }
    }
    else
    {      
      props = new HashMap<>(13);
      propChanges.put(component, props);
    }
    
    // update props index and add to end of list
    SyncOp committedOp = appendSet(op); 
    if (committedOp instanceof SetOp)
      props.put(name, committedOp);
    else
    {
      BatchSetOp batch = (BatchSetOp)committedOp;
      for (SetOp setOp: batch.setOps)
      {
        props.put(setOp.name, committedOp);
      }
    }
  }
  
  private SyncOp appendSet(SetOp op)
  {
    // First check to see if the last op in the list was a SetOp or BatchSetOp for
    // the same target complex
    SyncOp lastOp = ops.tail;
    BComplex target = null;
    BComponent comp = null;
    boolean isSetOp = lastOp instanceof SetOp; 
    if (isSetOp)
    {
      SetOp lastSet = (SetOp)lastOp;
      comp = lastSet.getComponent();
      if (comp != null)
      {
        try
        {
          target = lastSet.getTarget();
        }
        catch(IllegalStateException ise)
        {
          //we might be attempted to set a property that's being added in this 
          // sync buffer but hasn't been commited yet, so just append to the
          // list of ops and handle it in sequence.
          ops.append(op);
          return op;
        }
      }
    }
    else if (lastOp instanceof BatchSetOp)
    {
      BatchSetOp lastSet = (BatchSetOp)lastOp;
      comp = lastSet.getComponent();
      if (comp != null)
        target = lastSet.getTarget();      
    }
    
    if ((target != null) && (comp != null) &&
        (comp == op.getComponent()) && (target == op.getTarget()))
    { 
      BatchSetOp batchSetOp = null;
      if (isSetOp)
      {
        batchSetOp = new BatchSetOp(comp, new SetOp[] { (SetOp)lastOp, op });
        ops.replace(lastOp, batchSetOp);
      }
      else
      {
        batchSetOp = (BatchSetOp)lastOp; 
        batchSetOp.addSet(op);
      }
      return batchSetOp;
    }
    else
    {
      ops.append(op);
      return op;
    }
  }
  
////////////////////////////////////////////////////////////////
// Listener Hooks
////////////////////////////////////////////////////////////////
  
  /**
   * Framework support access; this method 
   * should never be used by developers.
   */
  public void fw(Object a)
  {  
    if (a instanceof TransferListener)
      listener = (TransferListener)a;
  }
  
////////////////////////////////////////////////////////////////
// OpList
////////////////////////////////////////////////////////////////
  
  /**
   * Linked list of SyncOps.
   */
  static class OpLinkedList       
  {    
    /**
     * Append the change to the end of the linked list.
     */
    void append(SyncOp op)
    {
      if (tail == null) { head = tail = op; }
      else { tail.next = op; op.prev = tail; tail = op; }
      size++;  
    }
  
    /**
     * Remove the change from the linked list.
     */
    void remove(SyncOp op)
    { 
      SyncOp prev = op.prev;                            
      SyncOp next = op.next;                            
      
      if (prev != null) { prev.next = next; }
      else { head = next; }                                      
      
      if (next != null) { next.prev = prev; }
      else { tail = prev; }   
      
      size--;
    }
  
    /**
     * Replace the change in the linked list.
     */
    void replace(SyncOp oldOp, SyncOp newOp)
    {                                                       
      // update new change's pointers                                
      newOp.prev = oldOp.prev;
      newOp.next = oldOp.next;
      
      // update prev to point to new
      if (oldOp.prev != null) { oldOp.prev.next = newOp; }
      else { head = newOp; }                                      
      
      // update next to point to new
      if (oldOp.next != null) { oldOp.next.prev = newOp; }
      else { tail = newOp; }
    }                                                     

    /* invariant assertion
    public void verify()
    {      
      SyncOp[] list = list();
      for(int i=0; i<list.length; ++i)
      {                      
        SyncOp c = list[i];
        if (i == 0) { verify(c == head); verify(c.prev == null); }                        
        else { verify(c.prev == list[i-1]); }
                                    
        if (i >= list.length-1) { verify(c == tail); verify(c.next == null); }
        else { verify(c.next == list[i+1]); }
      }
    }    
    public void verify(boolean v) { if (!v) throw new IllegalStateException(); }
    */
    
    SyncOp head;
    SyncOp tail;
    int size;
  }
  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static BFacets noAutoStartFacets;
  static
  {
    Map<String, BBoolean> map = new HashMap<>();
    map.put("niagaraAutoStart", BBoolean.FALSE);
    noAutoStartFacets = BFacets.make(map);
  }

  BComponentSpace space;      // parent space
  OpLinkedList loads;         // linked list of LoadOps
  OpLinkedList ops;           // linked list of SyncOps (non-Loads)
  boolean coalesce;           // coalesce prop changes
  Map<BComponent, Map<String, SyncOp>> propChanges; // map[component] -> map[propName] -> SetOp
  TransferListener listener;  // for transfer updates
  private int id = -1;

  boolean checkForAutoStart = true; // Needed so that the check for auto start only happens once (in case of a recursive commit)

}

