/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceExt;
import javax.baja.driver.BIDeviceFolder;
import javax.baja.driver.ui.device.DeviceExtsColumn;
import javax.baja.gx.BBrush;
import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.gx.RectGeom;
import javax.baja.naming.BOrd;
import javax.baja.sys.BComponent;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.component.table.BComponentTable;
import javax.baja.workbench.component.table.ComponentTableCellRenderer;
import javax.baja.workbench.mgr.BMgrTable;

/**
 * Overcomes bug in DeviceExtsColumn that forces the device folder
 * to extend from BDeviceFolder rather than allowing the device folder
 * to implement BIDeviceFolder instead.
 * 
 * This is necessary since the BVideoCameraFolder and BVideoLocation
 * do not extend BDeviceFolder. Unfortunately, they can not extend BDeviceFolder
 * because BDeviceFolder declares its getDeviceType methods as final and
 * its default implementation returns getNetwork().getDeviceType(). This
 * would return the DVR in the case of BVideoCameraFolder and/or 
 * BVideoLocation. If BDeviceFolder.getDeviceType() was not final then
 * BVideoCameraFolder and BVideoLocation could have extended from it
 * and overridden the getDeviceType() method to return the camera type or
 * display type.
 * 
 * However, that was not possible so instead, BVideoCameraFolder and 
 * BVideoLocation extend BFolder or BComponent and implement BIDeviceFolder.
 * Without customizing the device exts column, the video camera folder and
 * video location folder would be painted with the device extension icons in
 * the table. Then if the user double-clicked them, an exception would be
 * thrown in workbench.
 * 
 * This makes it so that no icons are painted for BVideoCameraFolders or
 * BVideoLocations (more specifically, for BIDeviceFolders).
 * 
 * Since much of the internal details are locked-down in DeviceExtsColumn, this
 * class basically has to copy to source code from DeviceExtsColumn and customize
 * the source code directly.
 * 
 * @author   lperkins (Original ddf code)
 * @author   Robert Adams (rework for ndriver)
 * @creation  25 Jan 2012
 */
public class VideoNestedDeviceExtsColumn
  extends DeviceExtsColumn
{  

  ///////////////////////////////////////////////////////////////
  // Constructor
  ///////////////////////////////////////////////////////////////

  /**
   * Constructor with flags set to zero.
   */
  public VideoNestedDeviceExtsColumn(BDevice protoType)
  {
    this(protoType, 0);
  }

  /**
   * Constructor with specified flags.
   */                        
  public VideoNestedDeviceExtsColumn(BDevice protoType, int flags)
  {                                
    super(protoType, flags);

    BDeviceExt[] exts = protoType.getDeviceExts();
    icons = new BImage[exts.length];
    names = new String[exts.length];
    for(int i=0; i<exts.length; ++i)
    {                               
      BDeviceExt ext = exts[i];
      icons[i] = BImage.make(ext.getIcon());
      if (icons[i] == null || icons[i].isNull()) icons[i] = defaultIcon; 
      names[i] = ext.getName();
    }            
    prefWidth = 4 + icons.length*20 + 4;
  }
      
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

    public Object get(Object row)
    {      
      return (BComponent)row;
    }    

    public TableCellRenderer getCellRenderer()
    {                
      return renderer;
    }

////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

    /**
     * Serves as a replacement for the hidden 'cellDoubleClicked' method
     * from the ancestor class DeviceExtsColumn. The BCameraManager's
     * controller overrides it own, non-locked version of 'cellDoubleClicked'
     * and calls this method instead of 'cellDoubleClicked' since it is
     * locked out in the DeviceExtsColumns ancestor class.
     */
    public void videoCellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
    {
      RectGeom rect = table.getCellBounds(row, col);
      BComponent component = table.getComponentAt(row);
      
      // The following was the one line of code that really needed customized
      // from the super class. The super class checks BDeviceFolder whereas
      // this class checks the interface BIDeviceFolder
      if (component instanceof BIDeviceFolder) return;
      
      int x = (int)(event.getX() - rect.x);
      int index = (x - 4) / 20;
      if (0 <= index && index < names.length)
      {
        BWbShell shell = table.getManager().getWbShell();
        if (shell != null)
        {                                                
          BOrd ord = BOrd.make(component.getNavOrd().toString() + "/" + names[index]);
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
      if (cell.value instanceof BIDeviceFolder)
        return com.tridium.ui.theme.Theme.table().getGridBrush();
      return super.getBackground(cell);
    }
    
    public double getPreferredCellWidth(Cell cell)
    {            
      return prefWidth;
    }
    
    public void paintCell(Graphics g, Cell cell)
    {                                     
      paintCellBackground(g, cell);      
      
      // The following was the one line of code that really needed customized
      // from the super class. The super class checks BDeviceFolder whereas
      // this class checks the interface BIDeviceFolder
      if (cell.value instanceof BIDeviceFolder) return;
      
      for(int i=0; i<icons.length; ++i)
        g.drawImage(icons[i], 4+i*20, (cell.height-16)/2);
    }
    
    protected BComponentTable getComponentTable()
    {            
      return getManager().getModel().getTable();
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BImage defaultIcon = BImage.make("module://icons/x16/object.png");  
  
  TableCellRenderer renderer = new CellRenderer();
  BImage[] icons;
  String[] names;
  double prefWidth;
} 


