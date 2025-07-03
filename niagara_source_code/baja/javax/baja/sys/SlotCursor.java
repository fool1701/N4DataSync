/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Cursor are used to iterate through a BObject's 
 * slots, properties, actions, or topics. 
 *
 * @author    Brian Frank
 */
public interface SlotCursor<S extends Slot> extends Cursor<BValue>, Iterable<S>
{

////////////////////////////////////////////////////////////////
// Target
////////////////////////////////////////////////////////////////

  // TODO: target() should return BIObject?
  /**
   * Get the target which the cursor is iterating upon.
   * This is always the object which is the parent of the
   * current slot and property value.
   */
  BObject target();
  
////////////////////////////////////////////////////////////////
// Next
////////////////////////////////////////////////////////////////  

  /**
   * This is special implementation of next() which is only 
   * applicable when iterating through properties.  It 
   * automatically skips any properties which do not have 
   * a type access of Slot.BOBJECT_TYPE.  This allows iteration 
   * without the need for wrapper object allocation.
   *
   * @throws CursorException if not iterating through properties.
   */
  boolean nextObject();

  /**
   * This is a convenience method which is semantically equivalent to {@code next(BComponent.class)}
   * @see #next(Class)
   */
  boolean nextComponent();

  /**
   * This is a convenience method to iterate the cursor until the current value is an instance
   * of the given class.
   */
  boolean next(Class<?> cls);

////////////////////////////////////////////////////////////////
// Slot
////////////////////////////////////////////////////////////////  

  /**
   * Get the cursor's current slot.
   * This really should return S, but this is incompatable with the implementations of TopicCursor and ActionCursor
   * which can sometimes return an NProperty (which is a Slot, but not a Topic or Action)
   */
  S slot();
  
  /**
   * Get the cursor's current slot as a Property.  This method is 
   * semantically equivalent to {@code slot().asProperty()}.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  Property property();
    
  /**
   * Get the cursor's current slot as an Action.  This method is
   * semantically equivalent to {@code slot().asAction()}.
   *
   * @throws CursorException if slot is not an instance of Action.
   */
  Action action();

  /**
   * Get the cursor's current slot as a Topic.  This method is
   * semantically equivalent to {@code slot().asTopic()}.
   *
   * @throws CursorException if slot is not an instance of Topic.
   */
  Topic topic();


////////////////////////////////////////////////////////////////
// Property Value
////////////////////////////////////////////////////////////////

  /**
   * If the cursor is positioned on a Property, then  return the 
   * Property's type access constant.  This method is semantically
   * equivalent to {@code slot().asProperty().getTypeAccess().}
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  int getTypeAccess();

  /**
   * Get current value as a boolean.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  boolean getBoolean();

  /**                                         
   * Get current value as an int.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  int getInt();

  /**                                         
   * Get current value as a long.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  long getLong();

  /**
   * Get current value as a float.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  float getFloat();

  /**
   * Get current value as a double.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  double getDouble();

  /**
   * Get current value as a String.
   *
   * @throws CursorException if slot is not an instance of Property.
   */
  String getString();

  /**
   * Get the object at the current cursor position.
   */
  BValue get();


////////////////////////////////////////////////////////////////
// Iteration
////////////////////////////////////////////////////////////////

  @Override
  default Iterator<S> iterator()
  {
    return stream().iterator();
  }

  @Override
  default Spliterator<S> spliterator()
  {
    return new Spliterators.AbstractSpliterator<S>(/*initial estimate size*/0l, /*characteristics*/Spliterator.NONNULL)
    {
      @Override
      public boolean tryAdvance(Consumer<? super S> consumer)
      {
        boolean next = next();
        if (next)
          consumer.accept(slot());
        return next;
      }
    };
  }

  /**
   * Returns a Stream for the Cursor.
   */
  default Stream<S> stream()
  {
    return StreamSupport.stream(spliterator(), /*parallel*/false);
  }
}
