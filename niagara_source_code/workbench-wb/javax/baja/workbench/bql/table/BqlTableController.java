/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.naming.BOrd;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.ui.BMenu;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.TableController;
import javax.baja.ui.table.TableSubject;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.nav.menu.NavMenuUtil;

/**
 * BqlTableController is a controller for a BqlTable.
 * It includes special handling hyperlinking and right-click menu
 * based on an available Nav column.
 * 
 * @author    John Sublett
 * @creation  08 Dec 2004
 * @version   $Revision: 2$ $Date: 5/4/05 8:32:40 PM EDT$
 * @since     Baja 1.0
 */
public class BqlTableController
  extends TableController
{
  protected void cellDoubleClicked(BMouseEvent evt, int row, int column)
  {
    super.cellDoubleClicked(evt, row, column);
    BqlTableModel model = ((BBqlTable)getTable()).getBqlModel();
    BqlTableColumn.Nav nav = model.getNavColumn();
    if (nav == null) return;
    
    String ordString = ((BString)nav.getValueAt(row)).getString();
    BOrd hyperlinkOrd = BOrd.make(ordString);
    BWidgetShell shell = getTable().getShell();
    if ((hyperlinkOrd != null) && (shell instanceof BWbShell))
    {
      ((BWbShell)shell).hyperlink(new HyperlinkInfo(hyperlinkOrd, evt));
    }
  }

  /**
   * Use <code>makePopup(BqlTableSubject)</code>
   */
  protected final BMenu makePopup(TableSubject subject)
  {            
    return makePopup((BqlTableSubject)subject);
  }
  
  /**
   * Build a popup menu for the specified select set.
   */
  protected BMenu makePopup(BqlTableSubject subject)
  {                         
    return NavMenuUtil.makeMenu(getTable(), subject);
  } 

/** Obsolete - use makePopup(BqlTableSubject) */
protected final void makePopup(BWidget owner, BObject target) { throw new IllegalStateException(); }

}