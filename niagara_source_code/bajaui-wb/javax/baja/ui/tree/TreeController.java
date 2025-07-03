/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.tree;

import javax.baja.gx.Point;
import javax.baja.sys.Clock;
import javax.baja.ui.BMenu;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BMouseWheelEvent;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.pane.BScrollPane;

/**
 * The TreeController manages input for the BTree.
 *
 * @author    John Sublett
 * @author    Mike Jarmy
 * @creation  04 Dec 2001
 * @version   $Revision: 27$ $Date: 10/24/07 11:24:48 AM EDT$
 * @since     Baja 1.0
 */
public class TreeController
  extends BTree.TreeSupport
{
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////

  /**
   * Return if the BTree is focus traversable.
   * Default returns true.
   */
  public boolean isFocusTraversable()
  {
    return true;
  }

  /**
   * Callback when focusLost() on BTree.  The default
   * implementation is to do nothing.
   *
   */
  public void focusGained(BFocusEvent event)
  {
    getTree().repaint();
  }

  /**
   * Callback when focusLost() on BTree.  The default
   * implementation is to do nothing.
   */
  public void focusLost(BFocusEvent event)
  {
    getTree().repaint();
  }

////////////////////////////////////////////////////////////////
// Expansion
////////////////////////////////////////////////////////////////

  /**
   * Handle an expansion of the specified node.
   */
  public void expanded(TreeNode node)
  {
  }

  /**
   * Handle the collapse of the specified node.
   */
  public void collapsed(TreeNode node)
  {
    if (focus == null) return;
    if (focus.isDescendantOf(node))
      setFocus(null);
  }

///////////////////////////////////////////////////////////
// Focus
///////////////////////////////////////////////////////////

  /**
   * Get the node that currently has focus.
   */
  public TreeNode getFocus()
  {
    return focus;
  }

  /**
   * Set the specified node to be the focused node in the tree.
   */
  public void setFocus(TreeNode node)
  {
    if (focus != null)
      focus.setFocus(false);

    focus = node;

    if (focus != null)
    {
      focus.setFocus(true);
      getTree().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTree()));
    }

    getTree().repaint();
  }

///////////////////////////////////////////////////////////
// Mouse input
///////////////////////////////////////////////////////////

  /**
   * Handle a mouse selection.
   */
  protected void mouseSelection(TreeNode target, BMouseEvent evt)
  {
    boolean pressed  = (evt.getId() == BMouseEvent.MOUSE_PRESSED);
    boolean released = (evt.getId() == BMouseEvent.MOUSE_RELEASED);

    if (target == null)
    {
      getSelection().deselectAll();
      startTarget = lastTarget = null;
      setFocus(null);
      return;
    }

    // ctrl down
    if (evt.isControlDown())
    {
      // unselect
      if (target.isSelected() && target != lastTarget && released)
      {
        getSelection().deselect(target);
        lastTarget = null;
      }
      // unselect
      else if (target.isSelected() && pressed && evt.getClickCount() != 2)
      {
        getSelection().deselect(target);
        lastTarget = null;
      }
      // add to selection
      else if (!target.isSelected() && pressed)
      {
        getSelection().select(target);
        lastTarget = target;
      }
    }
    // shift down
    else if (evt.isShiftDown())
    {
      // select (and maybe unselect)
      if (pressed)
      {
        if (startTarget != null)
        {
          getSelection().deselectAll();
          getSelection().select(startTarget);
          selectBetween(target, startTarget);
        }
        getSelection().select(target);
        lastTarget = target;
      }
    }
    // select
    else if (!target.isSelected() || released)
    {
      getSelection().deselectAll();
      getSelection().select(target);
      startTarget = lastTarget = target;
    }

    setFocus(target);
    if (startTarget == null) startTarget = target;
  }

  public void mouseWheel(BMouseWheelEvent evt) {}

  private void selectBetween(TreeNode node1, TreeNode node2)
  {
    if (node1 == node2) return;

    TreeNode[] list = getTree().getVisibleNodes();
    int idx1 = getNodeIndex(list, node1);
    int idx2 = getNodeIndex(list, node2);
    if ((idx1 == -1) || (idx2 == -1)) return;

    getSelection().supressUpdates(true);
    if (idx2 < idx1) { int a = idx1; idx1 = idx2; idx2 = a; }
    for (int i = idx1+1; i < idx2; i++)
    {
      getSelection().select(list[i]);
    }
    getSelection().supressUpdates(false);
  }

  private int getNodeIndex(TreeNode[] list, TreeNode node)
  {
    for (int i = 0; i < list.length; i++)
    {
      if (node == list[i]) return i;
    }
    return -1;
  }

  /**
   * Handle a mouse press.
   */
  public void mousePressed(BMouseEvent evt)
  {
    getTree().requestFocus();

    double x = evt.getX();
    double y = evt.getY();

    TreeNode target = getTree().yToTreeNode(y);
    if (target != null)
    {
      if (target.inExpander(x, y))
        getTree().toggleExpanded(target);
      else if (target.isSelection(x, y))
      {
        mouseSelection(target, evt);
        if (!evt.isButton3Down() && (evt.getClickCount() == 2))
        {
          getSelection().deselectAll();
          getSelection().select(target);
          nodeDoubleClicked(evt, target);
        }
      }
      else
      {
        target = null;
        mouseSelection(null, evt);
      }
    }
    else
      mouseSelection(null, evt);

    if (evt.isPopupTrigger())
      popup(evt, target);
  }

  /**
   * Handle a mouse release.
   */
  public void mouseReleased(BMouseEvent evt)
  {
    if (evt.isPopupTrigger())
    {
      double x = evt.getX();
      double y = evt.getY();

      TreeNode target = getFocus();
      if (target == null)
      {
        target = getTree().yToTreeNode(y);
        if ((target != null) && !target.isSelection(x, y))
          target = null;
      }

      popup(evt, target);
    }
    else
    {
      TreeNode target = getFocus();
      mouseSelection(target, evt);
    }
  }

  /**
   * Handle a mouse move.
   */
  public void mouseMoved(BMouseEvent evt)
  {
    double x = evt.getX();
    double y = evt.getY();

    TreeNode target = getTree().yToTreeNode(y);
    if ((target != null) && !target.isSelection(x, y))
      target = null;

    setMouseNode(target);
  }

  /**
   * Set the current mouse over node.
   */
  private void setMouseNode(TreeNode node)
  {
    if (mouseNode == node) return;

    TreeNode oldMouseNode = mouseNode;
    mouseNode = node;

    if (oldMouseNode != null)
      nodeExited(oldMouseNode);
    if (mouseNode != null)
      nodeEntered(mouseNode);
  }

  /**
   * Handle a mouse entry.
   */
  public void mouseEntered(BMouseEvent evt)
  {
    mouseMoved(evt);
  }

  /**
   * Handle a mouse exit.
   */
  public void mouseExited(BMouseEvent evt)
  {
    setMouseNode(null);
  }

  /**
   * Receive notification that the mouse has entered the
   * selection area of the specified tree node.
   */
  protected void nodeEntered(TreeNode node) {}

  /**
   * Receive notification that the mouse has exited the
   * selection area of the specified tree node.
   */
  protected void nodeExited(TreeNode node) {}

  public void mouseDragged(BMouseEvent evt) {}

  public void mousePulsed(BMouseEvent evt)  {}  // dragOver() should handle

///////////////////////////////////////////////////////////
// Keyboard input
///////////////////////////////////////////////////////////

  protected final void initFocus()
  {
    TreeNode node = getSelection().getNode();
    if (node == null)
    {
      TreeModel model = getModel();
      int rootCount = model.getRootCount();
      if (rootCount == 0) return;
      node = model.getRoot(0);
    }

    setFocus(node);
    getSelection().deselectAll();
    getSelection().select(node);
  }

  /**
   * Handle a key press.
   */
  public void keyPressed(BKeyEvent evt)
  {
    // don't do further processing if control or alt is down
    if (evt.isControlDown() || evt.isAltDown())
      return;

    int keyCode = evt.getKeyCode();

    TreeNode target = getFocus();
    if (target != null)
    {
      switch(keyCode)
      {
        case BKeyEvent.VK_RIGHT:
          getTree().setExpanded(target, true);
          evt.consume();
          searchText = "";
          return;

        case BKeyEvent.VK_LEFT:
          getTree().setExpanded(target, false);
          evt.consume();
          searchText = "";
          return;

        case BKeyEvent.VK_ENTER:
          doSelectAction(target, target.getX(), target.getY());
          evt.consume();
          searchText = "";
          return;
          
        case BKeyEvent.VK_CONTEXT_MENU:
            popup(new BMouseEvent(BMouseEvent.MOUSE_PRESSED, getTree(),0, getSelection().getNode().getX(),getSelection().getNode().getY(), 0, true), getSelection().getNode());
      }
      
      // try to map 0-9 or a-z to a list item
      char ch = evt.getKeyChar();
      if ((BKeyEvent.VK_SPACE <= keyCode && keyCode <= BKeyEvent.VK_9) ||
          (BKeyEvent.VK_A <= keyCode && keyCode <= BKeyEvent.VK_Z))
      {
        if(Clock.ticks()-750<lastSearchTime)
          searchText = searchText + ch;
        else
          searchText = ""+ch;

        for(int i=0; i<getModel().getRootCount(); i++)
          if(search(getModel().getRoot(i), searchText))
            break;

        lastSearchTime = Clock.ticks();
        evt.consume();
      }
    }

    switch(keyCode)
    {
      case BKeyEvent.VK_UP:
        previousSelection(evt);
        evt.consume();
        searchText = "";
        break;

      case BKeyEvent.VK_DOWN:
        nextSelection(evt);
        evt.consume();
        searchText = "";
        break;
    }
  }

  /**
   * When a key is pressed, try to auto select the item.
   */
  private boolean search(TreeNode node, String searchText)
  {
    if( null == node) return false;
    TreeSelection sel = getSelection();
    
    String text = node.getText();
    if (text != null && text.length() > 0 && 
        text.toLowerCase().startsWith(searchText.toLowerCase()))
    {
      sel.deselectAll();
      sel.select(node); //TODO: add in a way to refresh from this method
      setFocus(node);
      getTree().scrollNodeToVisible(node);
      return true;
    }

    if(node.isExpanded())
      for(int i=0; i<node.getChildCount(); ++i)
        if(search(node.getChild(i), searchText))
          return true;

    return false;
  }

  /**
   * Callback when keyReleased() on BTree.  The default
   * implementation is to process the Enter and Esc keys.
   */
  public void keyReleased(BKeyEvent event)
  {
    if (event.getModifiersEx() != 0) return;
    if (event.getKeyCode() == BKeyEvent.VK_ENTER)
    {
      // have to do this on release, otherwise we will
      // close the popup and the release will be directed
      // to the text field
      handleEnter(event);
    }
    else if (event.getKeyCode() == BKeyEvent.VK_ESCAPE)
    {
      handleEscape(event);
    }
  }

  /**
   * Handle a typed key.  This is the combination of a key press
   * and a key release.
   */
  public void keyTyped(BKeyEvent evt) {}

  /**
   * Default implementation is to fire actionPerformed.
   */
  protected void handleEnter(BKeyEvent event)
  {
    event.consume();
    getTree().fireActionPerformed(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTree()));
  }

  /**
   * Default implementation is to fire cancelled.
   */
  protected void handleEscape(BKeyEvent event)
  {
    event.consume();
    getTree().fireCancelled(new BWidgetEvent(BWidgetEvent.ACTION_PERFORMED, getTree()));
  }

///////////////////////////////////////////////////////////
// Navigation
///////////////////////////////////////////////////////////

  /**
   * Move the current selection and focus up in the tree.
   */
  private void previousSelection(BKeyEvent evt)
  {
    TreeNode current = getFocus();
    if (current == null)
      initFocus();
    else
    {
      TreeNode newSelection = getTree().getPrevious(current);
      if (newSelection != null)
      {
        if ((evt != null) && (evt.isControlDown() || evt.isShiftDown()))
        {
          getSelection().getSelection().select(newSelection);
        }
        else
        {
          getSelection().deselectAll();
          getSelection().select(newSelection);
        }
        setFocus(newSelection);
        getTree().scrollNodeToVisible(newSelection);
      }
    }
  }

  /**
   * Move the current selection and focus down in the tree.
   */
  private void nextSelection(BKeyEvent evt)
  {
    TreeNode current = getFocus();
    if (current == null)
      initFocus();
    else
    {
      TreeNode newSelection = getTree().getNext(current);
      if (newSelection != null)
      {
        if ((evt != null) && (evt.isControlDown() || evt.isShiftDown()))
        {
          getSelection().select(newSelection);
        }
        else
        {
          getSelection().deselectAll();
          getSelection().select(newSelection);
        }
        setFocus(newSelection);
        getTree().scrollNodeToVisible(newSelection);
      }
    }
  }

///////////////////////////////////////////////////////////
// Common event support
///////////////////////////////////////////////////////////

  /**
   * Handle a double click selection at the specified location
   * for the specified node.
   */
  protected void nodeDoubleClicked(BMouseEvent event, TreeNode node)
  {
    doSelectAction(node, event.getX(), event.getY());
  }

  /**
   * Handle a double click selection at the specified location
   * for the specified node.
   *
   * @param target The target node of the double click.  This will
   *   be null if the double click occurred in the tree background
   *   and not on a node.
   *
   * @param x The x location where the double click occurred in the
   *   bounds of the tree.
   * @param y The y location where the double click occurred in the
   *   bounds of the tree.
   */
  protected void doSelectAction(TreeNode target, double x, double y)
  {
  }

  /**
   * If the tree is inside a ScrollPane then call
   * <code>BScrollPane.pulseViewport()</code>
   */
  public boolean pulseViewport(double x, double y)
  {
    Object parent = getTree().getParent();
    if (parent instanceof BScrollPane)
      return ((BScrollPane)parent).pulseViewport(new Point(x, y), getNodeRenderer().getHeight());
    return false;
  }

////////////////////////////////////////////////////////////////
// Popup
////////////////////////////////////////////////////////////////

  /**
   * Callback when a mouse event triggers a popup menu.  The
   * node specifies the TreeNode the user clicked over, or
   * null if the user clicked over the background.  This method
   * routes to makePopup() and opens the popup.
   */
  protected void popup(BMouseEvent event, TreeNode node)
  {
    TreeSubject subject = getSelection().getSubject(node);
    BMenu menu = makePopup(subject);
    if (menu != null)
    {
      menu.removeConsecutiveSeparators();
      menu.open(event);
    }
  }

  /**
   * Make a popup menu for the specified subject
   * selection or return null to display no popup menu.
   */
  protected BMenu makePopup(TreeSubject subject)
  {
    return null;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private String searchText = "";  // Current text search string
  private long lastSearchTime = 0; // Clock ticks when the user modified their search text
  
  private TreeNode focus;
  private TreeNode mouseNode;
  private TreeNode lastTarget = null;
  private TreeNode startTarget = null;
}
