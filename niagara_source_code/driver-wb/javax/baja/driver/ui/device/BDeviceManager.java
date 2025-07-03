/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;

import java.util.Arrays;

import javax.baja.driver.BIDeviceFolder;
import javax.baja.file.types.image.BImageFile;
import javax.baja.file.types.image.BPngFile;
import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTemplate;
import javax.baja.workbench.mgr.folder.BFolderManager;

import com.tridium.template.file.BNtplFile;
import com.tridium.template.file.TemplateManager;
import com.tridium.template.file.TemplateManager.TemplateInfo;

/**
 * BDeviceManager defines an the default implementation of BAbstractManager
 * used to manage the devices under a BDeviceNetwork.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 4$ $Date: 9/12/04 1:43:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDeviceManager
  extends BFolderManager
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.device.BDeviceManager(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:24 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDeviceManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  protected MgrModel makeModel() { return new DeviceModel(this); }

  @Override
  protected MgrController makeController() { return new DeviceController(this); }

  @Override
  protected MgrState makeState() { return new DeviceState(); }

  @Override
  protected MgrTemplate makeTemplate()
  {
    if(!supportsTemplates()) return null;
    return new DevTemplateMgr(this);
  }

  /** Indicates if driver device manager can support template matching or deployment.
   * @return this default implementation returns true */
  public boolean supportsTemplates() { return true; }

  /*
   * (non-Javadoc)
   * @see javax.baja.driver.ui.tag.BTaggableFolderManager#selectMatchingTag(com.tridium.tag.DriverTagMapInfo, int)
   *
   * Select the matching tag given a learnRow.
   */
  protected void selectMatchingTag( int learnRow)
  {
  }

  /*
   * Return the learnTable row for a given a mgrEditRow.

  public int getLearnTableRow(int mgrEditRow)
  {
    BLearnTable learnTable = getLearn().getTable();
    TableSelection learnSelection = learnTable.getSelection();
    int[] selRows = learnSelection.getRows();
    return selRows[mgrEditRow];
  }
  */
  public class DevTemplateMgr
     extends MgrTemplate
  {
    public DevTemplateMgr(BAbstractManager mgr)
    {
      super(mgr);
    }

    /**
     * This callback is made from <code>manager.doLoadValue()</code>.
     */
    public void load(BComponent target)
    {
      this.target = target;
      updateTemplateTable(null, null);
    }

    /**
     * Update the template table root based on specified discovery objects or deviceType
     * @param deviceType
     * @param disObjs
     */
    public void updateTemplateTable(Type deviceType, Object[] disObjs)
    {
      BIDeviceFolder devFolder = (BIDeviceFolder)getManager().getTarget();
      if(deviceType == null) deviceType = devFolder.getDeviceType();

      // Get list of all templates which match device type
      TemplateInfo[] fa = TemplateManager.INSTANCE.getTemplatesForType(deviceType);
      Arrays.sort(fa);

      // Remove any templates that are not matchable with all discovered objects
      Array<TemplateInfo> a = new Array<>(TemplateInfo.class);
      for(int i=0 ; i<fa.length ; ++i )
      {
        // NCCB-14944: get the template root component
        BComponent c = fa[i].getNtplFile().getBaseComponent();
//        BComponent c = fa[i].getRootType().getInstance().asComponent();
        boolean match = true;
        if(disObjs!=null)
        {
          for(int n=0 ; n<disObjs.length ; ++n)
          {
            if(!getLearn().isMatchable(disObjs[n], c))
            {
              match = false;
              break;
            }
          }
        }

        if (match)
        {
          a.add(fa[i]);
        }
      }

      // Update root with remaining templates
      updateRoots(a.trim());
    }

    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[]
        {
           new NtpColumn("Name")
           {
             public Object ntplGet(TemplateInfo n)
             {
               return n.getName();
             }
           },
           new NtpColumn("Vendor")
           {
             public Object ntplGet(TemplateInfo n) {  return n.getVendor(); }
           },
           new NtpColumn("Version")
           {
             public Object ntplGet(TemplateInfo n) { return n.getVersion(); }
           },
           new NtpColumn("Description")
           {
             public Object ntplGet(TemplateInfo n) { return n.getDescription(); }
           },
        };
    }

    public BImage getIcon(Object template)
    {
      BNtplFile ntp = ((TemplateInfo)template).getNtplFile();
      BImageFile imf = ntp.getImageFile();
      if(imf!=null && imf instanceof BPngFile)
      {
        return BImage.make(imf.getAbsoluteOrd());
      }
      return BImage.make(templateIcon);
    }
    BComponent target;
  }

  private static abstract class NtpColumn
     extends MgrColumn
  {
    public NtpColumn(String name)
    {
      super(name);
    }

    public Object get(Object row)
    {
      TemplateInfo n = (TemplateInfo)row;
      return ntplGet(n);

    }

     public abstract Object ntplGet(TemplateInfo n);
    }
}
