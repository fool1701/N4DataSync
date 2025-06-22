/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.*;

/**
 * ToggleCommandGroup insures exclusive selection for widgets
 * which are using a ToggleCommand.
 *
 * @author    Brian Frank
 * @creation  8 Dec 00
 * @version   $Revision: 3$ $Date: 5/29/05 10:42:07 PM EDT$
 * @since     Baja 1.0
 */
public class ToggleCommandGroup <T extends ToggleCommand>
  implements Iterable<T>
{

  /**
   * Add a ToggleCommand to the exclusive selection list.
   */
  public void add(T command)
  {
    list.add(command);
    command.group = this;
    if (selected == null) 
    {
      selected = command;
      selected.setSelected(true);
      selected(command);
    }
  }

  /**
   * Remove a ToggleCommand from the exclusive selection list.
   */
  public void remove(T command)
  {
    list.remove(command);
    command.group = null;
    // NOTE: check if selected?
  }
  
  /**
   * Get the ToggleCommand currently selected.
   */
  public T getSelected()
  {
    return selected;
  }
  
  /**
   * Callback when the current selection is
   * changed to the specified command.
   */
  protected void selected(T command)
  {
  }
  
  /**
   * Check the exclusive
   */
  void checkExclusive(T s)
  {
    // we are going to get a callback when we
    // clear the old selection (just ignore it)
    if (s == ignore) return;
    
    // if this is already the selected one,
    // then must stay selected
    if (s == selected)
    {
      if (!s.isSelected()) s.setSelected(true);
      return;
    }
    
    // clear the current selection
    ignore = selected;
    selected.setSelected(false);
    ignore = null;
    
    // setup the new selection
    if (!s.isSelected()) throw new IllegalStateException();
    selected = s;
    selected(s);
  }

  @Override
  public Iterator<T> iterator()
  {
    return list.iterator();
  }

  private List<T> list = new ArrayList<>();
  private T selected;
  private T ignore;
}
