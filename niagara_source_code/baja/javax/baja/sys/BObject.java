/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.agent.AgentList;
import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.Registry;
import javax.baja.registry.TypeInfo;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BObject is the base class required for all 
 * objects which conform to the Baja model.
 * 
 * @author    Brian Frank
 */
@NiagaraType
public abstract class BObject implements BIObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BObject(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  protected BObject() 
  {
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /** Does this BObject extend from BValue. */
  public final boolean isValue() { return this instanceof BValue; }
  /** Does this BObject extend from BSimple. */
  public final boolean isSimple() { return this instanceof BSimple; }
  /** Does this BObject extend from BComplex. */
  public final boolean isComplex() { return this instanceof BComplex; }
  /** Does this BObject extend from BStruct. */
  public final boolean isStruct() { return this instanceof BStruct; }
  /**  Does this BObject extend from BComponent. */
  public final boolean isComponent() { return this instanceof BComponent; }

  @SuppressWarnings("unchecked")
  @Override
  public final <T extends BIObject> T as(Class<T> cls)
  {
    return (T)this;
  }

  /**  Return this. */
  public final BObject asObject() { return this; }
  /**  Get the BObject narrowed to BValue. */
  public final BValue asValue() { return (BValue)this; }
  /**  Get the BObject narrowed to BSimple. */
  public final BSimple asSimple() { return (BSimple)this; }
  /**  Get the BObject narrowed to BComplex. */
  public final BComplex asComplex() { return (BComplex)this; }
  /**  Get the BObject narrowed to BStruct. */
  public final BStruct asStruct() { return (BStruct)this; }
  /** Get the BObject narrowed to BComponent. */
  public final BComponent asComponent() { return (BComponent)this; }

////////////////////////////////////////////////////////////////
// Equality
////////////////////////////////////////////////////////////////  
  
  /**
   * All BObjects should support an implementation of equals.
   * BSimples are equal by comparing their value.  BComplexes 
   * are equal using the "==" operator.  If you wish to compare
   * value equality use {@link #equivalent(Object)}.  The default
   * implementation of this method is to return reference equality
   * using the == operator.
   */
  public boolean equals(Object obj)
  {
    return this == obj;
  }

  @Override
  public boolean equivalent(Object obj)
  {
    return equals(obj);
  }
      
  /**
   * Some types of BObject's are used to indicate
   * a null value.  This method allows those types to
   * declare their null status by overriding this common
   * method.  The default is to return false.
   */
  public boolean isNull()
  {
    return false;
  }

  @Override
  public BIDataValue toDataValue()
  {
    return BString.make(toString());
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@link #getAgents(Context) getAgents(null)}.
   */
  public final AgentList getAgents()
  {
    return getAgents(null);
  }

  /**
   * Get the list of agents for this BObject.  The 
   * default implementation of this method returns
   * {@link Registry#getAgents(TypeInfo)
   * Registry.getAgents(getType().getTypeInfo())}.
   */
  public AgentList getAgents(Context cx)
  {
    return Sys.getRegistry().getAgents(getType().getTypeInfo());
  }

  /**
   * Convenience for {@code getType().getModule().getLexicon()}.
   */
  public Lexicon getLexicon()
  {
    return getType().getModule().getLexicon();
  }                         
  
  /**
   * Get the Object's type as a localized displayable string.
   */
  public String getTypeDisplayName(Context context)
  {                       
    return getType().getDisplayName(context);
  }  
  
  /**
   * Get the BIcon used to represent this object.  The
   * general pattern for overriding this method is:
   * <pre>
   *   public BIcon getIcon() { return icon; }
  *    private static final BIcon icon = BIcon.std("object.png");
   * </pre>
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("object.png");
    
  /**
   * All BObject's should implement toString(Context)
   * to return a possibly localized string summary of
   * their object.
   */
  public final String toString()
  {                        
    return toString(null);
  }
  
  @Override
  public String toString(Context context)
  {
    if (getType() != null)
    {
      return getTypeDisplayName(context);
    }
    else
    {
      return getClass().getName();
    }
  }

  /**
   * Get a debug string for the obect.
   */
  public String toDebugString()
  {
    StringBuilder s = new StringBuilder();
    
    if (getType() != null)
    {
      s.append( getType().toString() );
    }
    else
    {
      s.append( getClass().getName() );
    }
        
    int id = System.identityHashCode(this);
    s.append(':').append(Integer.toString(id, 36));
    
    if (isComponent())
    {
      s.append('[');
      BComponent c = as(BComponent.class);
      if (c.getComponentSpace() != null) 
      {
        //s.append(TextUtil.getClassName(c.getComponentSpace().getClass()))
        s.append("h:").append(c.getHandle())
         .append(" \"").append(c.toPathString()).append('"');
      }
      else
      {
        s.append("unmounted");
      }
      s.append(']');
    }  
    
    return s.toString();
  }
  
  /**
   * Dump diagnostics for this object in HTML format
   * using the specified SpyWriter.
   */
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    out.trTitle("Object", 2);
    out.prop("type",     getType());
    out.prop("class",    getClass().getName());
    out.prop("toString", toString());
    out.prop("toDebugString", toDebugString());
    out.endProps();
  }

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public final Object fw(int x)
  {
    return fw(x, null, null, null, null);
  }

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    return null;
  }  
          
}
