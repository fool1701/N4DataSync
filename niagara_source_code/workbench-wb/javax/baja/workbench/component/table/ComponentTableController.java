/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import javax.baja.naming.BOrd;
import javax.baja.sys.BComponent;
import javax.baja.ui.BMenu;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.commands.CopyCommand;
import javax.baja.ui.commands.CutCommand;
import javax.baja.ui.commands.DeleteCommand;
import javax.baja.ui.commands.DuplicateCommand;
import javax.baja.ui.commands.PasteCommand;
import javax.baja.ui.commands.RenameCommand;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.TableController;
import javax.baja.ui.table.TableSubject;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.commands.ComponentReorderCommand;
import javax.baja.workbench.nav.menu.NavMenuUtil;
import com.tridium.workbench.commands.PasteSpecialCommand;

/**
 * ComponentTableController is the specialization of 
 * TableController for the BComponentTable class.
 *
 * @author    Brian Frank
 * @creation  21 Mar 02
 * @version   $Revision: 16$ $Date: 11/21/06 12:24:19 PM EST$
 * @since     Baja 1.0
 */
public class ComponentTableController
  extends TableController
{  

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the hyperlink on double click flag; default is true.
   */
  public boolean isHyperlinkOnDoubleClick()
  {
    return hyperlinkOnDoubleClick;
  }

  /**
   * Set the hyperlink on double click flag; default is true.
   */
  public void setHyperlinkOnDoubleClick(boolean hyperlinkOnDoubleClick)
  {
    this.hyperlinkOnDoubleClick = hyperlinkOnDoubleClick;
  }

////////////////////////////////////////////////////////////////
// TableController
////////////////////////////////////////////////////////////////  

  /**
   * If hyperlinkOnDoubleClick is true then hyperlink.
   */
  protected void handleEnter(BKeyEvent event)
  {
    super.handleEnter(event);
    if (hyperlinkOnDoubleClick)
    {
      ComponentTableModel model = ((BComponentTable)getTable()).getComponentModel();
      int row = getSelection().getRow();
      BComponent component = model.getComponentAt(row);
      BOrd ord = component.getNavOrd();
      BWidgetShell shell = getTable().getShell();
      if (ord != null && shell instanceof BWbShell)
        ((BWbShell)shell).hyperlink(ord);
    }
  }

  /**
   * If hyperlinkOnDoubleClick is true then hyperlink.
   */
  protected void cellDoubleClicked(BMouseEvent event, int row, int column)
  {
    super.cellDoubleClicked(event, row, column);
    if (hyperlinkOnDoubleClick)
    {
      ComponentTableModel model = ((BComponentTable)getTable()).getComponentModel();
      BComponent component = model.getComponentAt(row);
      BOrd ord = component.getNavOrd();
      BWidgetShell shell = getTable().getShell();
      if (ord != null && shell instanceof BWbShell)
      {
        ((BWbShell)shell).hyperlink(new HyperlinkInfo(ord, event));
      }
    }
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////
  
  /**
   * Use <code>makePopup(ComponentTableSubject)</code>.
   */
  public final BMenu makePopup(TableSubject subject) 
  { 
    return makePopup((ComponentTableSubject)subject);
  }
  
  /**
   * Make the popup menu for the current selection.
   */
  public BMenu makePopup(ComponentTableSubject subject)
  {                                  
    BComponentTable table = (BComponentTable)getTable();
    BComponent container = table.getContainer();
    
    // if multiple selection, then only return merged menu
    if (subject.size() > 1)
      return NavMenuUtil.makeMenu(table, subject);
      
    // if over a row, then provide a full nav menu
    BComponent active = (BComponent)subject.getActive();
    if (active != null)
      return NavMenuUtil.makeMenu(table, active);
    
    // otherwise return a default menu
    BMenu menu = new BMenu();
    menu.add("copy", new CopyCommand(table));
    menu.add("cut", new CutCommand(table));
    menu.add("paste", new PasteCommand(table));
    menu.add("pasteSpecial", new PasteSpecialCommand(table, container));
    menu.add("duplicate", new DuplicateCommand(table));
    menu.add("sep1", new BSeparator());
    menu.add("delete", new DeleteCommand(table));
    menu.add("sep2", new BSeparator());
    menu.add("rename", new RenameCommand(table));
    menu.add("reorder", new ComponentReorderCommand(table, container));
    return menu;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private boolean hyperlinkOnDoubleClick = true;
  
}