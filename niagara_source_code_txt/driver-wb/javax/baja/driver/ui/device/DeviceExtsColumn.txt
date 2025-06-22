/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;

import java.util.HashMap;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceExt;
import javax.baja.driver.BDeviceFolder;
import javax.baja.gx.BBrush;
import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import javax.baja.naming.BOrd;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComponent;
import javax.baja.sys.Flags;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.component.table.BComponentTable;
import javax.baja.workbench.component.table.ComponentTableCellRenderer;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrColumn;

/**
 * DeviceExtColumns is used to provide a table column with quick
 * links to the device's BDeviceExt.  Each extension is displayed
 * as an icon.  By convention the DeviceExtsColumn should go right
 * after the type column.  Starting in 3.4, it won't display 
 * device exts marked as hidden.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 3$ $Date: 7/22/08 3:11:49 PM EDT$
 * @since     Baja 1.0
 */
public class DeviceExtsColumn
  extends MgrColumn
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with flags set to zero.
   */
  public DeviceExtsColumn(BDevice protoType)
  {
    this(protoType, 0, false);
  }

  /**
   * Constructor with specified flags.
   */
  public DeviceExtsColumn(BDevice protoType, int flags)
  {
    this(protoType, flags, false);
  }
  
  /**
   * Constructor with flags set to zero and dynamic specifier.
   * 
   * @param dynamicExts When set to true, this causes each row to compute its own
   * selection of available device exts.  This comes at the expense
   * of performance, however.  If you know that all rows in your
   * table support the same device exts, you should leave the default
   * (false).
   * 
   * @since Niagara 3.4
   */
  public DeviceExtsColumn(BDevice protoType, boolean dynamicExts)
  {
    this(protoType, 0, dynamicExts);
  }

  /**
   * Constructor with specified flags and dynamic specifier.
   * 
   * @param dynamicExts When set to true, this causes each row to compute its own
   * selection of available device exts.  This comes at the expense
   * of performance, however.  If you know that all rows in your
   * table support the same device exts, you should leave the default
   * (false).
   * 
   * @since Niagara 3.4
   */
  public DeviceExtsColumn(BDevice protoType, int flags, boolean dynamicExts)
  {
    super(Lexicon.make(DeviceExtsColumn.class).getText("deviceExtsColumn"), flags);

    this.dynamicExts = dynamicExts;
    icons = initIcons(protoType);
    names = initNames(protoType); 
    prefWidth = 4 + icons.length*20 + 4;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * When set to true, this causes each row to compute its own
   * selection of available device exts.  This comes at the expense
   * of performance, however.  If you know that all rows in your
   * table support the same device exts, you should leave the default
   * (false).
   *
   * @since Niagara 3.4
   */
  public void setDynamicExts(boolean dynamicExts)
  {
    this.dynamicExts = dynamicExts;
  }

  public Object get(Object row)
  {
    return row;
  }

  public TableCellRenderer getCellRenderer()
  {
    return renderer;
  }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {
    RectGeom rect = table.getCellBounds(row, col);
    BComponent component = table.getComponentAt(row);
    if (component instanceof BDeviceFolder) return;
    String[] n = null;
    if (dynamicExts && (component instanceof BDevice))
      n = initNames((BDevice)component);
    else
      n = names;
    int x = (int)(event.getX() - rect.x);
    int index = (x - 4) / 20;
    if (0 <= index && index < n.length)
    {
      BWbShell shell = table.getManager().getWbShell();
      if (shell != null)
      {
        BOrd ord = BOrd.make(component.getNavOrd().toString() + "/" + n[index]);
        shell.hyperlink(new HyperlinkInfo(ord, event));
      }
    }
  }

////////////////////////////////////////////////////////////////
// CellRenderer
////////////////////////////////////////////////////////////////

  class CellRenderer extends ComponentTableCellRenderer
  {
    public BBrush getBackground(Cell cell)
    {
      if (cell.value instanceof BDeviceFolder)
        return com.tridium.ui.theme.Theme.table().getGridBrush();
      return super.getBackground(cell);
    }

    public double getPreferredCellWidth(Cell cell)
    {
      if (dynamicExts && (cell.value instanceof BDevice))
      {
        BImage[] images = cache.get(cell.value);
        if (images == null)
          cache.put(cell.value, images = initIcons((BDevice)cell.value));
        return (4 + images.length*20 + 4);
      }
      return prefWidth;
    }

    public void paintCell(Graphics g, Cell cell)
    {
      paintCellBackground(g, cell);

      if (cell.value instanceof BDeviceFolder) return;

      BImage[] images = null;
      if (dynamicExts && (cell.value instanceof BDevice))
      {
        images = cache.get(cell.value);
        if (images == null)
          cache.put(cell.value, images = initIcons((BDevice)cell.value));
      }
      else
        images = icons;

      for(int i=0; i<images.length; ++i)
        g.drawImage(images[i], 4+i*20, (cell.height-16)/2);
    }

    protected BComponentTable getComponentTable()
    {
      return getManager().getModel().getTable();
    }
    
    HashMap<Object, BImage[]> cache = new HashMap<>();
  }

////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////  

  /**
   * Convenience method to return an array of 
   * device ext icons for the given BDevice prototype
   * 
   * @since Niagara 3.4
   */
  static BImage[] initIcons(BDevice protoType)
  {
    BDeviceExt[] exts = protoType.getDeviceExts();
    Array<BImage> result = new Array<>(BImage.class);
    for(int i=0; i<exts.length; ++i)
    {
      if ((exts[i].getParent().getFlags(exts[i].getPropertyInParent()) & Flags.HIDDEN) != 0)
        continue;
      BImage icon = BImage.make(exts[i].getIcon());
      if (icon == null || icon.isNull()) icon = defaultIcon;
      result.add(icon);
    }
    return result.trim();
  }

  /**
   * Convenience method to return an array of 
   * device ext names for the given BDevice prototype
   * 
   * @since Niagara 3.4
   */
  static String[] initNames(BDevice protoType)
  {
    BDeviceExt[] exts = protoType.getDeviceExts();
    Array<String> result = new Array<>(String.class);
    for(int i=0; i<exts.length; ++i)
    {
      if ((exts[i].getParent().getFlags(exts[i].getPropertyInParent()) & Flags.HIDDEN) != 0)
        continue;
      result.add(exts[i].getName());
    }
    return result.trim();
  }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BImage defaultIcon = BImage.make("module://icons/x16/object.png");

  TableCellRenderer renderer = new CellRenderer();
  BImage[] icons;
  String[] names;
  double prefWidth;
  boolean dynamicExts = false;
}


