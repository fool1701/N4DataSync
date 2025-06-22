/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.table;

import javax.baja.gx.*;
import com.tridium.ui.theme.*;

/**
 * TableHeaderRenderer is used to render the individual 
 * column headers of a BTable.
 *
 * @author    Brian Frank
 * @creation  3 Aug 01
 * @version   $Revision: 12$ $Date: 3/28/05 10:32:31 AM EST$
 * @since     Baja 1.0
 */
public class TableHeaderRenderer
  extends BTable.TableSupport
{

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Get the foreground used to paint the header text.
   */
  public BBrush getForeground(Header header)
  {
    return Theme.table().getHeaderTextBrush();
  }

  /**
   * Get the background used to paint the header.  Return 
   * null if the standard background should be used.
   */
  public BBrush getBackground(Header header)
  {
    return null;
  }

  /**
   * Get the fixed height of the table header.
   */
  public double getHeaderHeight()
  {
    return Theme.table().getHeaderFont().getHeight() + 12;
  }

  /**
   * Get the preferred width of the specified header.
   */
  public double getPreferredHeaderWidth(Header header)
  {
    int sortArray = 0;
    //if (getTable().getModel().isColumnSortable(header.column))
      sortArray += Theme.table().getSortIconWidth();
    
    double sw = Theme.table().getHeaderFont().width(header.name);
    return sw + sortArray + 6;
  }
  
  /**
   * Paint the header to the specified graphics context.  The
   * graphics context will be translated so that the origin 
   * of the header is 0,0.
   */
  public void paintHeader(Graphics g, Header header)
  {
    Theme.table().paintHeaderBackground(g, header, getBackground(header));
    String s = String.valueOf(header.name);
    g.setBrush(getForeground(header));
    g.setFont(Theme.table().getHeaderFont());
    g.drawString(s, 4, Theme.table().getHeaderFont().getAscent() + 6);
    
    if (getTable().getSortColumn() == header.column)
    {
      boolean ascending = getTable().isSortAscending();
      Theme.table().paintSortIcon(g, header, ascending);
    }
  }

////////////////////////////////////////////////////////////////
// Header
////////////////////////////////////////////////////////////////  

  /**
   * This structure is used to pass the header information 
   * to the the paintHeader() method.
   */
  public static class Header
  {
    /** Zero based index table column of the header. */
    public int column;
    
    /** String name of the header as returned from TableModel. */
    public String name;    

    /** Width in pixels of the header. */
    public double width;
    
    /** Height in pixels of the header. */
    public double height;        
  }

}