/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.component.table;

import javax.baja.gx.BBrush;
import javax.baja.ui.table.TableHeaderRenderer;
import com.tridium.ui.theme.Theme;

/**
 * ComponentTableHeaderRenderer is the specialization of 
 * TableHeaderRenderer for the BComponentTable class.
 *
 * @author    Brian Frank
 * @creation  21 Mar 02
 * @version   $Revision: 4$ $Date: 3/28/05 1:40:57 PM EST$
 * @since     Baja 1.0
 */
public class ComponentTableHeaderRenderer
  extends TableHeaderRenderer
{  

  public BBrush getForeground(Header header)
  {
    BComponentTable table = (BComponentTable)getTable();
    if (table.dropActive)
      return Theme.widget().getDropOkForeground();
    else
      return super.getForeground(header);
  }

  public BBrush getBackground(Header header)
  {
    BComponentTable table = (BComponentTable)getTable();
    if (table.dropActive)
      return Theme.widget().getDropOkBackground();
    else
      return super.getBackground(header);
  }
  
}