/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;        

import javax.baja.sys.*;

/**
 * Invocation stores an action invocation.  It may be used
 * with Worker and Queue/CoalesceQueue.
 *
 * @author    Brian Frank
 * @creation  7 Feb 04
 * @version   $Revision: 2$ $Date: 2/7/04 4:27:13 PM EST$
 * @since     Baja 1.0
 */
public class Invocation
  implements Runnable, ICoalesceable
{                    

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct using arguments from <code>BComponent.post()</code>
   */
  public Invocation(BComponent instance, Action action, BValue argument, Context context)
  {                   
    this.hashCode = instance.hashCode() ^ action.getName().hashCode();
    this.instance = instance;
    this.action   = action;
    this.argument = argument;
    this.context = context;       
  }           

////////////////////////////////////////////////////////////////
// Runnable
////////////////////////////////////////////////////////////////
  
  /**
   * Run called <code>BComponent.doInvoke</code>.
   */
  @Override
  public void run()
  {
    instance.doInvoke(action, argument, context);
  }
  
////////////////////////////////////////////////////////////////
// ICoalesceable
////////////////////////////////////////////////////////////////
  
  /**
   * Hash code is based on instance and action.
   */
  public int hashCode()
  {                             
    return hashCode;
  }             
  
  /**
   * Equality is based on instance and action.
   */
  public boolean equals(Object object)
  {                                  
    if (object instanceof Invocation)
    {                               
      Invocation o = (Invocation)object;
      return instance == o.instance && action == o.action;
    }
    return false;
  }
  
  /**
   * Return this.
   */
  @Override
  public Object getCoalesceKey()
  {
    return this;
  }
  
  /**
   * Return c - last Invocation wins.
   */
  @Override
  public ICoalesceable coalesce(ICoalesceable c)
  {                                 
    return c;  
  }
                 
////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////
  
  /**
   * Get a String representation.
   */
  public String toString()
  {
    return instance.toPathString() + "." + action.getName() + 
      "(" + (argument == null ? "" : argument.toString()) + ")";
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  protected int hashCode;
  protected BComponent instance;
  protected Action action;
  protected BValue argument;
  protected Context context;
      
}
