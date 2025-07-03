/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.sys.BComponent;

/**
 * MgrSupport is the base class for BAbstractManager support classes.
 *
 * @author    Brian Frank
 * @creation  12 Jan 04
 * @version   $Revision: 7$ $Date: 5/20/10 1:53:28 PM EDT$
 * @since     Baja 1.0
 */
public abstract class MgrSupport
{           
                                                                              
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor. 
   */
  public MgrSupport(BAbstractManager manager)
  {                  
    if (manager == null) throw new NullPointerException("manager == null");
    this.manager = manager;    
  }     
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the associated manager.
   */
  public final BAbstractManager getManager()
  {
    return manager;
  }             
  
  /**
   * Init is called once from <code>BAbstractManager.init()</code>
   */
  public void init()
  {
  }

  /**
   * This callback is made from <code>manager.doLoadValue()</code>.  
   */
  public void load(BComponent target)
  { 
  }

  /** Get the manager target component. Convenience for <code>getManager().getTarget()</code> */
  public BComponent getTarget()
  {
    return manager.getTarget();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BAbstractManager manager;
  
}                    


