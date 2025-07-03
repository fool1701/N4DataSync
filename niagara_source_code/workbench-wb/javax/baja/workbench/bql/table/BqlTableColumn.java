/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.workbench.bql.table;

import javax.baja.collection.BIRandomAccessTable;
import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.Tables;
import javax.baja.data.DataTypes;
import javax.baja.gx.BImage;
import javax.baja.status.BStatus;
import javax.baja.sys.BIObject;
import javax.baja.sys.BIcon;
import javax.baja.sys.BSimple;

/**
 * BqlTableColumn stores the meta data about a column in the BqlTable.  It
 * is used to build the BQL query.
 * 
 * @author    John Sublett
 * @creation  06 Dec 2004
 * @version   $Revision: 2$ $Date: 12/13/04 2:02:27 PM EST$
 * @since     Baja 1.0
 */
public class BqlTableColumn
{
  /**
   * Create a column with the specified name and a default display name.
   */
  public BqlTableColumn(String name)
  {
    this(name, null, 0);
  }

  /**
   * Create a column with the specified name and display name.
   *
   * @param name The column name that is inserted into the BQL projection.
   * @param displayName The display name used in the as clause.  displayName
   * @param flags Flags that specify the display behavior for this column.
   */
  public BqlTableColumn(String name, String displayName, int flags)
  {
    this.name = name;
    this.displayName = displayName;
    this.flags = flags;
  }

  /**
   * Initialize this column to expose the specified column of the
   * result table.
   */
  public void load(BITable<BIObject> table, Column column)
  {
    this.table = Tables.slurp(table);
    this.column = column;
  }

  /**
   * Get the column name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the column display name.  This is displayed in the
   * table header.
   */
  public String getDisplayName()
  {
    return displayName;
  }

  /**
   * Get the flags that control the display behavior of this column.
   */
  public int getFlags()
  {
    return flags;
  }

  /**
   * Get the projection for this column.
   */
  public String getProjection()
  {
    if ((displayName == null) || (displayName.length() == 0))
      return name;
    else
      return name + " as '" + displayName + "'";
  }
  
  /**
   * Get the value at the specified row location.
   */
  public Object getValueAt(int row)
  {
    return table.get(row).cell(column);
  }

////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////

  /**
   * A Simple column gets special handling because it
   * requests the string encoding of the target and then decodes
   * it to the original type.  So, getValueAt(row, col) on the table model
   * will always return the precise type when the column is a Simple column.
   * <p>
   * The limitation of using this column type is that all values in the
   * column MUST be of the same type.  For values that are not of the
   * correct type, the default value is returned.
   */
  public static class Simple
    extends BqlTableColumn
  {
    /**
     * Create a new Simple column.
     *
     * @param name The name of the field or property to fetch.
     * @param def The default value.  This cannot be null.
     */
    public Simple(String name, BSimple def)
    {
      super(name);
      this.def = def;
    }
 
    /**
     * Create a new Simple column.
     *
     * @param name The name of the field or property to fetch.
     * @param displayName The display name for the column.  This
     *   is what appears in the table header.
     * @param flags Flags that specify the display behavior for
     *   this column.
     * @param def The default value.  This cannot be null.
     */
    public Simple(String name, String displayName, int flags, BSimple def)
    {
      super(name, displayName, flags);
      this.def = def;
    }

    /**
     * Get the text to include in the projection for this
     * column.
     */
    public String getProjection()
    {
      if ((displayName == null) || (displayName.length() == 0))
        return name + ".encodeToString as '" + name + "'";
      else
        return name + ".encodeToString as '" + displayName + "'";
    }

    /**
     * Initialize this column to expose the specified column of the table.
     */
    public void load(BITable<BIObject> t, Column column)
    {
      super.load(t, column);
      
      int rowCount = table.size();
      columnValues = new BSimple[rowCount];
      for (int row = 0; row < rowCount; row++)
      {
        columnValues[row] = parse(DataTypes.otos(table.get(row).cell(column)));
      }
    }

    /**
     * Parse the simple.
     */
    protected BSimple parse(String s)
    {
      try
      {
        return (BSimple)def.decodeFromString(s);
      }
      catch(Exception ex)
      {
        return def;
      }
    }

    public Object getValueAt(int row)
    {
      return columnValues[row];
    }

    BSimple def;
    BSimple[] columnValues;
  }


////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Status.  The specified name must resolve to a BStatus.
   * <p>
   * A Status column receives special handling in that 
   * the table rows are color coded by the status.  If you want
   * BStatus values without the color coding, just use a Simple
   * column.  If more than one Status column is specified, it is
   * undefined as to which one is used for color coding.
   */
  public static class Status
    extends Simple
  {
    /**
     * Default constructor.  This uses "status" for the column
     * name and no flags.
     */
    public Status()
    {
      this("status"); 
    }
    
    /**
     * Create a new Status column.  This uses "status" for the
     * column name.
     */
    public Status(int flags)
    {
      this("status"); 
    }

    /**
     * Create a new Status column.
     * 
     * @param name The name of the field that resolves to a BStatus.
     */
    public Status(String name)
    {
      this(name, "%lexicon(workbench:bqlTable.status)%", 0);
    }

    /**
     * Create a new Status column.
     * 
     * @param name The name of the field that resolves to a BStatus.
     * @param displayName The display name for the column.  This
     *   is what appears in the table header.
     * @param flags Flags that specify the display behavior for
     *   this column.
     */
    public Status(String name, String displayName, int flags)
    {
      super(name, displayName, flags, BStatus.DEFAULT);
    }
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  /**
   * Icon.  The specified name must resolve to a BIcon.
   * <p>
   * An Icon column receives special handling in that 
   * the icon is displayed at in the first cell of each row.
   * If you want BIcon values without the display, just use a Simple
   * column.  If more than one Icon column is specified, it is
   * undefined as to which one is used for display.
   * <p>
   * 
   */
  public static class Icon
    extends Simple
  {
    /**
     * Create a new Icon column with a column
     * name of "icon".
     */
    public Icon()
    {
      this("icon");
    }

    /**
     * Create a new Icon column.  Icon columns are
     * hidden by default.
     */
    public Icon(String name)
    {
      super(name, null, HIDDEN, BIcon.DEFAULT);
    }

    /**
     * Create a new Icon column.
     * 
     * @param name The name of the field that resolves to a BIcon.
     * @param displayName The display name for the column.  This
     *   is what appears in the table header.
     * @param flags Flags that specify the display behavior for
     *   this column.
     */
    public Icon(String name, String displayName, int flags)
    {
      super(name, displayName, flags, BIcon.DEFAULT);
    }
    
    public BSimple parse(String s)
    {
      BIcon icon = (BIcon)super.parse(s);
      return BImage.make(icon);
    }
  }

////////////////////////////////////////////////////////////////
// Nav
////////////////////////////////////////////////////////////////

  /**
   * Nav.  The specified name must resolve to a BOrd.
   * <p>
   * A Nav column receives special handling in that
   * the ord serves as a hyperlink target when double clicking
   * a row.  The target of the ord is also used to build the
   * menu on a right-click.
   * <p>
   * getValue(row) will return a BString.  To convert it
   * to an ord, the caller should use
   * <code>BOrd.make(((BString)getValue(row)).getString())</code>.
   */
  public static class Nav
    extends BqlTableColumn
  {
    /**
     * Default constructor.  Uses "ordInSession" by default.
     */
    public Nav()
    {
      this("ordInSession");
    }
  
    /**
     * Create a new Nav column.  Nav columns are unseen by
     * default.
     *
     * @param name The name of the field or property to fetch.
     */
    public Nav(String name)
    {
      super(name, null, UNSEEN);
    }
 
    /**
     * Create a new Nav column.
     *
     * @param name The name of the field or property to fetch.
     * @param displayName The display name for the column.  This
     *   is what appears in the table header.
     * @param flags Flags that specify the display behavior for
     *   this column.
     */
    public Nav(String name, String displayName, int flags)
    {
      super(name, displayName, flags);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final int HIDDEN = 0x1;
  public static final int UNSEEN = 0x2;
  public static final int UNESCAPE = 0x4;

  protected String name;
  protected String displayName;
  protected int flags = 0;
  
  protected BIRandomAccessTable<BIObject> table;
  protected Column column;
}
