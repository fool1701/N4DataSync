/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.nav.*;

/**
 * ObjectSpy is used to dump <code>BObject.spy()</code>.
 *
 * @author    Brian Frank
 * @creation  6 Mar 03
 * @version   $Revision: 3$ $Date: 7/1/04 1:29:01 PM EDT$
 * @since     Niagara 3.0
 */
public class ObjectSpy
  extends Spy
  implements ISpyDir
{     

  /**
   * Construct for the specified target object.
   */
  public ObjectSpy(BObject object)
  {
    this.object = object;
  }
  
  /**
   * Return empty array.
   */
  @Override
  public String[] list()
  {
    return new String[0];
  }

  /**
   * Implementation based on BINavNode interface.
   */
  @Override
  public Spy find(String name)
  {
    BObject child = null;
    
    if (object instanceof BINavNode)    
    {
      child = (BObject)((BINavNode)object).getNavChild(SlotPath.unescape(name));
    }
    
    if (child == null) return null;
    return new ObjectSpy(child);    
  }
  
  /**
   * Get the target object.
   */
  public BObject getObject()
  {
    return object;
  }
  
  /**
   * Route to <code>BObject.spy()</code>.
   */
  @Override
  public void write(SpyWriter out)
    throws Exception
  {
    object.spy(out);
  }    
  
  private BObject object;
  
}
