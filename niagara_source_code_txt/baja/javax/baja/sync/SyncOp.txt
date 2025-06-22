/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;

/**
 * SyncOp is the base class for a record describing a 
 * operation to make for synchronization.
 *
 * @author    Brian Frank
 * creation  16 Jul 01
 * @version   $Revision: 15$ $Date: 2/2/11 10:06:02 AM EST$
 * @since     Baja 1.0
 */
public abstract class SyncOp
{                  

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int LOAD                 = 'l';
  public static final int SET                  = 's';
  public static final int ADD                  = 'a';
  public static final int REMOVE               = 'v';
  public static final int RENAME               = 'r';
  public static final int REORDER              = 'o';
  public static final int SET_FLAGS            = 'f';
  public static final int SET_FACETS           = 'x';
  public static final int ADD_KNOB             = 'k';
  public static final int REMOVE_KNOB          = 'j';
  public static final int ADD_RELATION_KNOB    = 'm';
  public static final int REMOVE_RELATION_KNOB = 'n';
  public static final int FIRE_TOPIC           = 't';
  public static final int SET_CATEGORY_MASK    = 'c';
  
  public static SyncOp make(int id)
  {
    switch(id)
    {
      case LOAD:                 return new LoadOp();
      case SET:                  return new SetOp();
      case ADD:                  return new AddOp();
      case REMOVE:               return new RemoveOp();
      case RENAME:               return new RenameOp();
      case REORDER:              return new ReorderOp();
      case SET_FLAGS:            return new SetFlagsOp();
      case SET_FACETS:           return new SetFacetsOp();
      case ADD_KNOB:             return new AddKnobOp();
      case REMOVE_KNOB:          return new RemoveKnobOp();
      case ADD_RELATION_KNOB:    return new AddRelationKnobOp();
      case REMOVE_RELATION_KNOB: return new RemoveRelationKnobOp();
      case FIRE_TOPIC:           return new FireTopicOp();
      case SET_CATEGORY_MASK:    return new SetCategoryMaskOp();
      default: throw new IllegalArgumentException(String.valueOf(id));
    }
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor.
   */
  protected SyncOp(BComponent c)
  {
    component = c;
    if (component != null)
    {
      handle = String.valueOf(component.getHandle());
    }
  } 

  /**
   * Constructor.
   */
  protected SyncOp()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the ID constant for the operation.
   */
  public abstract int getId();
  
  /**
   * Get target component.
   */
  public BComponent getComponent() { return component; }

  /**
   * Commit the operation.
   */
  abstract void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception;       
      
////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////

  /**
   * Encode the operation as XML.
   */
  void encode(SyncEncoder out)
    throws Exception
  {
    handle = "?";
    if (component != null)
    {
      handle = String.valueOf(component.getHandle());
    }
    
    out.start(String.valueOf((char)getId())).attr("h", handle);
    
    // Give SyncEncoder subclasses their chance
    out.encodingSyncOp(this);
  }

  /**
   * Decode the operation from XML.
   */
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                      
    handle = in.elem().get("h");
    
    if (buffer != null)
    {
      component = buffer.findByHandle(handle, false);
    }
    else
    {
      component = space.findByHandle(handle, false);
    }
  }
    
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Get a string representation of this operation.
   */
  public abstract String toString();
  
  /**
   * Package private convenience method to return the component's toPathString().
   *
   * @since Niagara 3.2
   */
  String componentToString()
  {
    return component != null ? component.toPathString():"null";
  }

  /**
   * Package private SyncOpSecurityException is a SecurityException
   * indicating that the SyncOp should not be encoded due to a 
   * permission restriction.  It is used to skip SyncOps that shouldn't
   * be encoded from the SyncBuffer.
   *
   * @since Niagara 3.4
   */
  static class SyncOpSecurityException
    extends SecurityException
  {
    /**
     * Constructs a {@code SyncOpSecurityException} with no detail message.
     */
    SyncOpSecurityException() 
    {
    }

    /**
     * Constructs a {@code SyncOpSecurityException} with the specified
     * detail message. 
     *
     * @param s the detail message.
     */
    SyncOpSecurityException(String s) 
    {
      super(s);
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  SyncBuffer buffer;
  BComponent component;
  
  SyncOp prev;   // used for SyncBuffer linked list
  SyncOp next;   // used for SyncBuffer linked list
  
  String handle; // keep a copy of the handle for the component,
                 // as we may need to reuse this to retrieve
                 // the component
                 
  boolean committed; // this flag gets set when this SyncOp is committed by the SyncBuffer
  
} 


