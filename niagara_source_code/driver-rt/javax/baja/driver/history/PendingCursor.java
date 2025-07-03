/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import javax.baja.driver.util.BDescriptorState;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Cursor;
import javax.baja.sys.IterableCursor;

import com.tridium.util.ComponentTreeCursor;

/**
 * PendingCursor is a cursor of archive descriptors in the
 * pending state.
 *
 * @author    John Sublett
 */
public class PendingCursor implements IterableCursor<BArchiveDescriptor>
{
  public PendingCursor(BComponent root, Context context)
  {
    inner = new ComponentTreeCursor(root, BArchiveDescriptor.TYPE, context);
  }

  @Override
  public Context getContext() { return null; }

  public void reset()
  {
    inner.reset();
  }

  @Override
  public boolean next()
  {
    while (inner.next(BArchiveDescriptor.class))
    {
      BArchiveDescriptor ad = (BArchiveDescriptor)inner.get();
      if (ad.getState() == BDescriptorState.pending)
      {
        return true;
      }
    }
    
    return false;
  }

  public boolean next(Class<?> cls)
  {
    if (!BArchiveDescriptor.class.isAssignableFrom(cls))
    {
      throw new IllegalArgumentException();
    }
    else
    {
      return next();
    }
  }
  
  @Override
  public BArchiveDescriptor get()
  {
    return (BArchiveDescriptor)inner.get();
  }

  @Override
  public void close()
  {
    // Since this cursor can be reset, we don't actually implement this.
    // TODO: maybe still enforce closed even though this can be reset??? If so, extend AbstractCursor
  }

  ComponentTreeCursor inner;
}
