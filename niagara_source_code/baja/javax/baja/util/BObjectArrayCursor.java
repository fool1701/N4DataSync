/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.util;

import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * A cursor that presents an array of BObjects as a Cursor.
 * 
 * @author    John Sublett
 */
@NoSlotomatic
public class BObjectArrayCursor<T extends BObject> implements IterableCursor<T>
{
  public BObjectArrayCursor(T[] objs, Context cx)
  {
    this.objs = objs;
    this.cx = cx;
    
    index = -1;
  }

  @Override
  public void close()
  {
    index = objs.length;
  }

  @Override
  public Context getContext()
  {
    return cx;
  }
  
  @Override
  public T get()
  {
    return objs[index];
  }
  
  @Override
  public boolean next()
  {
    if (index != objs.length)
    {
      index++;
    }
    return index < objs.length;
  }
  
  public boolean nextComponent()
  {
    while (next())
    {
      if (get().isComponent())
      {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean next(Class<?> cls)
  {
    while (next())
    {
      if (cls.isInstance(get()))
      {
        return true;
      }
    }
    
    return false;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private Context   cx;
  private T[] objs;
  private int index;


}
