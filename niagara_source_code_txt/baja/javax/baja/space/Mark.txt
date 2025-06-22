/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import java.io.PrintWriter;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BINavNode;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;

import com.tridium.sys.transfer.TransferResult;
import com.tridium.sys.transfer.TransferStrategy;

/**
 * Mark is a wrapper for a set of BObjects which serve as the
 * source for space operations: copy, move, rename, delete.  There
 * is always exactly one mark current in a VM (although it could
 * be null).  The current mark acts as the clipboard.
 *
 * @author    Brian Frank
 * @creation  25 Aug 01
 * @version   $Revision: 21$ $Date: 11/21/06 12:24:18 PM EST$
 * @since     Baja 1.0
 */
public class Mark
{

////////////////////////////////////////////////////////////////
// Current
////////////////////////////////////////////////////////////////

  /**
   * Get the current mark for the VM.  Return null
   * if no mark is currently set.
   */
  public static Mark getCurrent()
  {
    return current;
  }

  /**
   * Set the current mark for the VM.  Pass null
   * to clear the mark.
   */
  public static void setCurrent(Mark mark)
  {
    if (current != null) current.setPendingMove(false);
    current = mark;
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a Mark for the specified array of source
   * objects and their matching suggested names.
   */
  public Mark(BObject[] values, String[] names)
  {
    // sanity checks
    if (values.length != names.length)
      throw new IllegalArgumentException("values.length != names.length");

    // sanity checks
    for(int i=0; i<values.length; ++i)
      if (values[i] == null || names[i] == null) throw new NullPointerException();

    // save away
    this.values = values.clone();
    this.names  = names.clone();
  }

  /**
   * Convenience for <code>this(new BObject[] { value }, new String[] { name })</code>.
   */
  public Mark(BObject value, String name)
  {
    this(new BObject[] { value }, new String[] { name });
  }

  /**
   * Convenience for <code>this(new BObject[] { value }, new String[] { makeName(value) })</code>.
   */
  public Mark(BObject value)
  {
    this(new BObject[] { value }, new String[] { makeName(value) });
  }

  /**
   * Convenience for <code>this(values, makeNames(values))</code>.
   */
  public Mark(BObject[] values)
  {
    this(values, makeNames(values));
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the transfer value at the specified index.
   */
  public BObject getValue(int index)
  {
    return values[index];
  }

  /**
   * Get the array of source values to transfer.
   */
  public BObject[] getValues()
  {
    return values.clone();
  }

  /**
   * Get the array of source values to transfer.
   */
  public Object[] getValues(Object[] array)
  {
    if (array.length < values.length)
      array = (Object[])java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), values.length);
    System.arraycopy(values, 0, array, 0, values.length);
    return array;
  }

  /**
   * Get the suggested name at the specified index.
   */
  public String getName(int index)
  {
    return names[index];
  }

  /**
   * Get the list of suggested names to use in a move
   * or copy transfer.  These may be modified if duplicate
   * names are encountered during an actual transfer.
   */
  public String[] getNames()
  {
    return names.clone();
  }

  /**
   * Return the number of values to transfer.
   */
  public int size()
  {
    return values.length;
  }

  /**
   * Return if every value is an instance of BComponent.
   */
  public boolean areAllValuesComponents()
  {
    for (int i=0; i<values.length; ++i)
      if (!(values[i] instanceof BComponent))
        return false;
    return true;
  }

  /**
   * Get the Mark in String format.  The default
   * implementation is to return the first value's
   * ord in space.
   */
  public String toStringFormat()
  {
    try
    {
      BObject  value = values[0];

      if (value instanceof BComponent)
      {
        SlotPath path = ((BComponent)value).getSlotPath();
        if (path != null) return "station:|" + path;
      }

      if (value instanceof BISpaceNode)
      {
        BOrd ord = ((BISpaceNode)value).getOrdInSession();
        if (ord != null) return ord.toString();
      }

      return value.toString();
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return e.toString();
    }
  }

////////////////////////////////////////////////////////////////
// Pending Move
////////////////////////////////////////////////////////////////

  /**
   * Is the pending move flag set.
   */
  public boolean isPendingMove()
  {
    return pendingMove;
  }

  /**
   * Set the pending move flag.  This allows cut operations
   * to be marked, but not actually moved until the paste
   * operation.  While the mark is set pendingMove all the
   * BComponents within the mark will return true for their
   * isPendingMove() methods.  This method should be used to
   * paint cut components differently to illustrate their
   * pending move operation.
   */
  public void setPendingMove(boolean pendingMove)
  {
    if (this.pendingMove == pendingMove) return;

    // store the flag myself
    this.pendingMove = pendingMove;

    // set/clear the flag on each component
    for(int i=0; i<values.length; ++i)
      ((BISpaceNode)values[i]).setPendingMove(pendingMove);
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Copy this mark to the specified target object.
   */
  public TransferResult copyTo(BObject target, Context context)
    throws Exception
  {
    return TransferStrategy.make(TransferStrategy.ACTION_COPY, this, target, null, context).transfer();
  }

  /**
   * Copy this mark to the specified target object.
   */
  public TransferResult copyTo(BObject target, BComponent params, Context context)
    throws Exception
  {
    return TransferStrategy.make(TransferStrategy.ACTION_COPY, this, target, params, context).transfer();
  }

  /**
   * Move this mark to the specified target object.
   */
  public TransferResult moveTo(BObject target, Context context)
    throws Exception
  {
    return TransferStrategy.make(TransferStrategy.ACTION_MOVE, this, target, null, context).transfer();
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Dump the contents of the Mark.
   */
  public void dump(PrintWriter out)
  {
    out.println("Mark [" + values.length +"] " + Integer.toString(hashCode(), 36));
    for(int i=0; i<values.length; ++i)
      out.println("  " + names[i] + " = " + values[i].toDebugString());
  }

  /**
   * Dump to standard out.
   */
  public void dump()
  {
    PrintWriter out = new PrintWriter(System.out);
    dump(out);
    out.flush();
  }

  /**
   * Get a suggested name for the object.  By default this is
   * BISpaceNode.getNavName(), or as a fallback the type name is
   * used.
   */
  public static String makeName(BObject object)
  {
    String name = null;

    if (object instanceof BINavNode)
    {
      name = ((BINavNode) object).getNavName();
    }

    if (name == null)
      name = object.getType().getTypeName();

    return name;
  }

  /**
   * Get an array of suggested names using the method
   * <code>makeName(BObject object)</code>.
   */
  public static String[] makeNames(BObject[] values)
  {
    String[] names = new String[values.length];
    for(int i=0; i<names.length; ++i)
      names[i] = makeName(values[i]);
    return names;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Mark current;

  private BObject[] values;
  private String[] names;
  private boolean pendingMove;

}
