/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import static javax.baja.sync.ProxyBroker.LOG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.baja.io.ValueDocDecoder.ITypeResolver;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BSimple;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import com.tridium.nre.util.IElement;
import com.tridium.sys.engine.NKnob;
import com.tridium.sys.engine.NRelationKnob;
import com.tridium.sys.engine.ProxyKnob;
import com.tridium.sys.engine.ProxyRelationKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * LoadOp is a passive operation used to load a 
 * BComponent into a BComponentSpace.
 *
 * @author    Brian Frank
 * @creation  16 Jul 01
 * @version   $Revision: 18$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class LoadOp
  extends SyncOp
{                    

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////


  public LoadOp(BComponent c, int depth)
  {                               
    super(c);                          
    this.depth = depth;
  }

  public LoadOp()
  {
  }

  /**
   * This constructor also passes the partialLoad boolean
   * constraint.  If partialLoad is true, then this means
   * that a partial load state is allowed for the given
   * component.  This constructor should only be used
   * with caution, as it is intended for supporting the 
   * virtual component scenario.  It basically allows for
   * partial loading, which is ONLY used in the case of
   * virtuals.
   *
   * @since     Niagara 3.2
   */
  public LoadOp(BComponent c, int depth, boolean partialLoad)
  {                               
    this(c, depth);
    this.partialLoad = partialLoad;
  }

////////////////////////////////////////////////////////////////
// SyncOp
////////////////////////////////////////////////////////////////

  @Override
  public int getId()
  { 
    return LOAD; 
  }               
  
  public int getDepth() { return depth; }
  
////////////////////////////////////////////////////////////////
// Commit
////////////////////////////////////////////////////////////////

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {                          
    syncComponent(this.current, this.component, this.partialLoad);   
    if (!partialLoad) // only loadKnobs for non-virtual case (partialLoad is false)
    {
      loadKnobs(space);
      loadRelationKnobs(space);
    }
  }
  
  void syncComponent(BComponent from, BComponent to, boolean partialLoad)
    throws Exception
  {              
    // sanity check - must be same type
    if (from.getType() != to.getType())
      throw new IllegalStateException(from.getType() + " != " + to.getType());
    
    // sanity check - must have same handle at this point
    if (to.getHandle() == null || !to.getHandle().equals(from.getHandle()))
      throw new IllegalStateException("from.h != to.h -> " + from.getHandle() + " != " + to.getHandle());
    
    // map cached permissions
    ComponentSlotMap fromMap = (ComponentSlotMap)from.fw(Fw.SLOT_MAP);
    ComponentSlotMap toMap = (ComponentSlotMap)to.fw(Fw.SLOT_MAP);
    toMap.setCachedPermissions(fromMap.getCachedPermissions());

    // map category mask
    toMap.setCategoryMask(fromMap.getCategoryMask(), Context.commit);  

    // check if the from object has not been completely loaded,
    // if not then don't bother to recurse thru the slots 
    // because they weren't encoded anyhow (the exception to this
    // rule is the icon slot, see SyncEncoder).
    boolean fromLoaded = fromMap.isBrokerPropsLoaded();
    if (!fromLoaded)             
    {     
      syncIcon(from, to);
      if (!partialLoad) // if partial loading enabled, fall through to continue with partial load
        return;
    }
    
    // if the from object was loaded completely, 
    // then set the loaded flag on to
    // Only do this for the non-virtual case (partialLoad is false)
    if (!partialLoad)
      toMap.setBrokerPropsLoaded(true);
      
    // first get a hashmap of the original properties on "to"
    Map<String, Property> propsBefore = new HashMap<>();
    Property[] props = to.getPropertiesArray();
    for(int i=0; i<props.length; ++i)
      propsBefore.put(props[i].getName(), props[i]);

    // In case the dynamic properties have been reordered, we need to keep track of
    // dynamic properties in their correct order.
    List<Property> orderedDynamicProps = new ArrayList<>();
    boolean orderChanged = false;
    int index = 0;
    
    // then do a property by slot synchronization
    SlotCursor<Slot> c = from.getSlots();
    while(c.next())
    {                          
      // get slot for both sides
      String name = c.slot().getName();
      Slot fromSlot = c.slot();
      Slot toSlot   = to.getSlot(name);
      int fromFlags = from.getFlags(fromSlot);
      BFacets fromFacets = from.getSlotFacets(fromSlot);
      
      // if not present on "to", we need to add
      if (toSlot == null)
      {
        Property fromProp = (Property)fromSlot;
        
        BValue fromValue = from.get(fromProp);   
        BValue toValue = newCopy(fromValue);
                
        orderedDynamicProps.add(to.add(name, toValue, fromFlags, fromFacets, Context.commit));
        index++;
        
        if (fromValue.isComponent())
        {                                    
          syncComponent((BComponent)fromValue, (BComponent)toValue, false);
        }
          
        continue;        
      }

      // remove from before list so we know that it still exists
      propsBefore.remove(name);        
      
      // map flags (applicable to all slot types)
      to.setFlags(toSlot, fromFlags, Context.commit);

      // map facets if dynamic (frozen ones will be handled by a separate "slotFacets_" property)
      if (toSlot.isDynamic())
      {
        to.setFacets(toSlot, fromFacets, Context.commit);
        orderedDynamicProps.add(toSlot.asProperty());
        if (!orderChanged && toMap.getDynamicPropertyByIndex(index++) != toSlot)
        {
          orderChanged = true;
        }
      }
      
      // if not a property then we are done
      if (!fromSlot.isProperty()) continue;
            
      // map property value   
      syncValue(from, (Property)fromSlot, to, (Property)toSlot, false);
    }
    
    // at this point if there were any props before that we didn't 
    // sync we need to remove them now; since they weren't found 
    // on "from" that means they have since been removed
    if (!propsBefore.isEmpty())
    {
      Iterator<String> it = propsBefore.keySet().iterator();
      while(it.hasNext())
      {
        String name = it.next();
        to.remove(name, Context.commit);
      }
    }

    // Only reorder if the order actually changed.
    if (orderChanged)
    {
      to.reorder(orderedDynamicProps.toArray(new Property[orderedDynamicProps.size()]), Context.commit);
    }
  }       
  
  void syncValue(BComplex from, Property fromProp, BComplex to, Property toProp, boolean partialLoad)
    throws Exception                        
  {
    BValue fromValue = from.get(fromProp);
    BValue toValue = to.get(toProp);
      
    // we need to do a full replace if:
    //  - the type has changed 
    //  - the value is a simple
    //  - we need to assign component handle 
    //  - the Component isn't mounted (issue 20764)
    if (fromValue.getClass() != toValue.getClass() || fromValue.isSimple() ||
        (toValue.isComponent() && (toValue.asComponent().getHandle() == null || toValue.asComponent().getComponentSpace() == null)))
    {                                                                           
    
      // if the from value is a component, but doesn't have a handle
      // then something is wrong because that means the fromValue either
      // wasn't included in the XML sent to us or was included but didn't
      // specify a handle attribute;  typically this happens when we
      // add a frozen BComponent slot - this side is running the newer 
      // version with the slot and the remote side is running the older 
      // version without the frozen slot
      if (fromValue.isComponent() && fromValue.asComponent().getHandle() == null)
      {                        
        // sanity check - if the slot is frozen it's a encoding error  
        if (fromValue.asComponent().getPropertyInParent().isDynamic())
          throw new IllegalStateException("LoadOp - dynamic slot with no handle");
        
        // output warning, and hide the slot
        if (LOG.isLoggable(Level.WARNING))
        {
          LOG.warning("remote VM does not have frozen slot \"" +
            from.getType() + '.' + fromProp.getName() + '\"');
        }
        to.setFlags(toProp, to.getFlags(toProp)|Flags.HIDDEN, Context.commit);
        return;
      }
    
      toValue = newCopy(fromValue);                         
      
      to.set(toProp, toValue, Context.commit);
      
      if (fromValue.isComponent())
        syncComponent((BComponent)fromValue, (BComponent)toValue, partialLoad);
          
      return;                                                   
    }                                    
    
    // otherwise if this is a component, then 
    // we have to do a full sync
    if (fromValue.isComponent())
    {                      
      BComponent fromComp = (BComponent)fromValue;
      BComponent toComp = (BComponent)toValue;
      if (toComp.getHandle() == null)
        throw new IllegalStateException();
        
      syncComponent(fromComp, toComp, partialLoad);
      return;
    }
    
    // otherwise this is a struct, so we sync in-place
    BStruct fromStruct = (BStruct)fromValue;
    BStruct toStruct   = (BStruct)toValue;
    SlotCursor<Property> c = fromStruct.getProperties();
    while(c.next())
    {
      Property fromStructProp = c.property();
      Property toStructProp = toStruct.getProperty(fromStructProp.getName());
      if (fromStructProp != toStructProp)
        throw new IllegalStateException("Structs should have same prop " + fromStructProp + " != " + toStructProp); 
      syncValue(fromStruct, fromStructProp, toStruct, toStructProp, partialLoad);
    }                      
  }                       
  
  /**
   * Given a value which we need to add or set, get 
   * a safe unmounted copy to use.
   */
  BValue newCopy(BValue from)
  {     
    // if from is a component, then get a blank copy using the 
    // from's handle (then when set/add the framework will ensure 
    // it is mapped into the space's cache)
    if (from.isComponent())
    {                                                 
      BComponent fromComp = (BComponent)from;
      BComponent to = (BComponent)fromComp.getType().getInstance();     
      ((ComponentSlotMap)to.fw(Fw.SLOT_MAP)).setHandle(fromComp.getHandle());
      return to;
    }  
    
    // otherwise just use standard clone method
    else
    {           
      return from.newCopy(true);
    }
  }

  /**
   * Map all the knobs for all the components we just loaded.
   * The map is keyed by components decoded (from), and we need 
   * to decode the knobs against the cached components (to).
   */
  void loadKnobs(BComponentSpace space)
    throws Exception
  {
    Iterator<BComponent> it = knobs.keySet().iterator();
    while(it.hasNext())
    {
      // knobs are keyed by from components
      BComponent from = it.next();

      // Issue 20250: only load the knobs if the Component isn't a stub
      if (!((ComponentSlotMap)from.fw(Fw.SLOT_MAP)).isBrokerPropsLoaded())
        continue;

      // map from -> to
      BComponent to = space.findByHandle(from.getHandle(), false);
      if (to == null)
        throw new IllegalStateException(""+from.getHandle());

      // now get the knobs which are still stored as XElems  
      List<IElement> elems = knobs.get(from);
      List<NKnob> toMerge = new ArrayList<>(elems.size());
      for(int i=0; i<elems.size(); ++i)
      {
        IElement elem = elems.get(i);
        ProxyKnob knob = SyncDecoder.decodeKnob(to, elem);
        if (knob != null) toMerge.add(knob);
      }

      // merge the knobs
      ((ComponentSlotMap)to.fw(Fw.SLOT_MAP)).mergeKnobs(toMerge);
    }
  }

  /**
   * Map all the knobs for all the components we just loaded.
   * The map is keyed by components decoded (from), and we need
   * to decode the knobs against the cached components (to).
   */
  void loadRelationKnobs(BComponentSpace space)
    throws Exception
  {
    Iterator<BComponent> it = rknobs.keySet().iterator();
    while(it.hasNext())
    {
      // knobs are keyed by from components
      BComponent from = it.next();

      // Issue 20250: only load the knobs if the Component isn't a stub
      if (!((ComponentSlotMap)from.fw(Fw.SLOT_MAP)).isBrokerPropsLoaded())
        continue;

      // map from -> to
      BComponent to = space.findByHandle(from.getHandle(), false);
      if (to == null)
        throw new IllegalStateException(""+from.getHandle());

      // now get the knobs which are still stored as XElems
      List<IElement> elems = rknobs.get(from);
      List<NRelationKnob> toMerge = new ArrayList<>(elems.size());
      for(int i=0; i<elems.size(); ++i)
      {
        IElement elem = elems.get(i);
        ProxyRelationKnob knob = SyncDecoder.decodeRelationKnob(to, elem);
        if (knob != null) toMerge.add(knob);
      }

      // merge the knobs
      ((ComponentSlotMap)to.fw(Fw.SLOT_MAP)).mergeRelationKnobs(toMerge);
    }
  }

  void syncIcon(BComponent from,  BComponent to)
  { 
    // see if from has an icon
    Property fromProp = from.getProperty("icon");
    if (fromProp == null) return;
    
    // if it isn't a simple then bail because that 
    // is complicated code we are trying to skip                                           
    BValue val = from.get(fromProp);
    if (!(val instanceof BSimple)) return;
    
    // see if the to already has an icon, if so
    // then set it, otherwise add it
    Property toProp = to.getProperty("icon");
    if (toProp == null)
      to.add("icon", val, Context.commit);
    else
      to.set(toProp, val, Context.commit);
  }
  
////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {                
    // start tag   
    super.encode(out);              
    if (partialLoad) // check for partialLoad enabled
      out.attr("partialLoad", true);
      
    out.endAttr().newLine().key("b");  
    
    // encode slots in p element   
    out.encodeForLoad = true;
    boolean trans = out.setEncodeTransients(true);
    out.encode(null, component, depth);
    out.setEncodeTransients(trans);
    out.encodeForLoad = true;     

    // end tag   
    out.end(String.valueOf((char)getId())).newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    IElement elem = in.elem();
    partialLoad = elem.get("partialLoad", "false").equals("true"); // false default for partialLoad
    
    // start with fresh knobs just in case
    in.knobs = null;
    in.rknobs = null;
    
    // decode the component graph to be loaded
    in.next();                  
    boolean wasSkippingLegacyEncodings = false;
    ITypeResolver typeResolver = in.getTypeResolver();
    try
    { // Only in LoadOp do we skip legacy encodings in order to allow
      // the decoder to continue.
      wasSkippingLegacyEncodings = typeResolver.getSkipLegacyEncodings();
      typeResolver.setSkipLegacyEncodings(true);
      current = (BComponent)in.decode();
    }
    finally
    {
      typeResolver.setSkipLegacyEncodings(wasSkippingLegacyEncodings);
    }
        
    // save away knob xml elements
    this.knobs = in.knobs;
    this.rknobs = in.rknobs;
    in.knobs = null;
    in.rknobs = null;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("Load: " + componentToString());
    if (partialLoad)
      sb.append(", partialLoad enabled");
    return sb.toString();
  }        
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  int depth;
  BComponent current;
  Map<BComponent, List<IElement>> knobs;
  Map<BComponent, List<IElement>> rknobs;
  boolean partialLoad = false;
  
}
