/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.ui;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BEnumWritable;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.BStringPoint;
import javax.baja.control.BStringWritable;
import javax.baja.gx.BImage;
import javax.baja.job.BJob;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrTypeInfo;
import com.tridium.ndriver.discover.BINDiscoveryGroup;
import com.tridium.ndriver.discover.BINDiscoveryIcon;
import com.tridium.ndriver.discover.BINDiscoveryLeaf;
import com.tridium.ndriver.discover.BINDiscoveryObject;
import com.tridium.ndriver.discover.BNDiscoveryJob;
import com.tridium.ndriver.discover.BNDiscoveryLeaf;
import com.tridium.ndriver.ui.device.BNDeviceManager;
import com.tridium.ndriver.ui.point.BNPointManager;

/**
 * This customized version of MgrLearn is used on the point manager and the
 * device manager.
 * <p>
 * This is a fully-functional MgrLearn. The discovery columns are based on
 * introspection.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
public class NMgrLearn
  extends MgrLearn
{
  public NMgrLearn(BAbstractManager manager)
  {
    super(manager);
  }

////////////////////////////////////////////////////////////////
//MgrLearn
////////////////////////////////////////////////////////////////

  /**
   * Override to add subscriber to update table as leafs are added or removed
   */
  @Override
  public void setJob(BJob job)
  {
    super.setJob(job);

    if (job instanceof BNDiscoveryJob)
    {
      BNDiscoveryJob dJob = (BNDiscoveryJob)job;

      // subscribe to discover container events
      discoveryFolderSub.subscribe(dJob.discoveryFolder());

      // Initialize discovery table
      updateRoots(dJob.getRootDiscoveryObjects());
    }
  }


  /**
   * Automatically determines the MgrColumns for the discovery list.
   */
  @Override
  protected MgrColumn[] makeColumns()
  {
    Array<MgrColumn> al = new Array<>(MgrColumn.class);
    NMgrColumnUtil.getColumnsFor((BComplex)NMgrUtil.getDiscoveryLeafInstance(getManager()), al);
    return al.trim();
  }

  /**
   * Automatically synchronizes the data in a discovery object with an instance
   * of MgrEditRow representing a database object to be modified.
   */
  @Override
  public void toRow(Object discovery, MgrEditRow row)
  {
    if (!(discovery instanceof BINDiscoveryLeaf))
    {
      return;
    }

    BINDiscoveryLeaf leaf = (BINDiscoveryLeaf)discovery;

    // Update cell values and 
    leaf.updateTarget(row.getTarget());
    leaf.defaultTargetUpdate(row.getTarget());
    // Pull the target values into the row cells - this will update cells
    // from any changes made by updateTargets()
    try
    {
      row.loadCells();
    }
    catch (Exception ignored)
    {
    }

    // Update name from target
    if (leaf.getDiscoveryName() != null)
    {
      row.setDefaultName(SlotPath.escape(leaf.getDiscoveryName()));
    }
  }

  /**
   * This callback is automatically invoked when the current job set via
   * {@code setJob()} completes.
   */
  @Override
  public void jobComplete(BJob job)
  {
    if (job instanceof BNDiscoveryJob)
    {
      BNDiscoveryJob djob = (BNDiscoveryJob)job;
      updateRoots(djob.getRootDiscoveryObjects());
      discoveryFolderSub.unsubscribe(djob.discoveryFolder());
    }
  }

  /**
   * All depths are potentially expandable.
   */
  @Override
  public boolean isDepthExpandable(int depth)
  {
    return true;
  }

  /**
   * A discovery object hasChildren if the discovery object implements
   * BINDiscoveryGroup.
   */
  @Override
  public boolean hasChildren(Object discovery)
  {
    return (discovery instanceof BINDiscoveryGroup);
  }

  /**
   * Override to call isExisting() on discovery object as BINDiscoveryLeaf
   */
  @Override
  public boolean isExisting(Object discovery, BComponent component)
  {
    // Check if we can do this.
    if (discovery == null || component == null ||
      !(discovery instanceof BINDiscoveryLeaf))
    {
      return false;
    }

    // Pass this check to discoveryLeaf
    return ((BINDiscoveryLeaf)discovery).isExisting(component);
  }

  /**
   * If this is a BINDiscoveryGroup, gets all BINDiscoveryObject children,
   * registers for component return an array of the children.
   */
  @Override
  public Object[] getChildren(Object discovery)
  {
    // Verifies that the given discovery object is BINDiscoveryGroup
    if (!(discovery instanceof BINDiscoveryGroup))
    {
      return null;
    }

    BINDiscoveryGroup discoveryGroup = (BINDiscoveryGroup)discovery;

    // Gets children that implement BINDiscoveryObject
    Object[] discoveryChildren = discoveryGroup.getChildren(BINDiscoveryObject.class);

    // Register of events on children
    for (int i = 0; i < discoveryChildren.length; i++)
    {
      if (discoveryChildren[i] instanceof BComponent)
      {
        getManager().registerForComponentEvents((BComponent)discoveryChildren[i]);
      }
    }

    return discoveryChildren;
  }

  /**
   * Determines the database types that are valid for the given discovery
   * object.
   */
  @Override
  public MgrTypeInfo[] toTypes(Object discovery)
  {
    if (discovery instanceof BINDiscoveryLeaf)
    {
      BINDiscoveryLeaf discoveryLeaf = (BINDiscoveryLeaf)discovery;

      TypeInfo[] validDbTypes = discoveryLeaf.getValidDatabaseTypes();

      // Gets the valid database types, if the driver developer specified them (if not, we'll allow all point types)
      if (validDbTypes != null && validDbTypes.length > 0)
      {
        return makeArray(validDbTypes);
      }
    }
    return null;
  }

  // From MgrTypeInfo - makeArray() without the sort
  private static MgrTypeInfo[] makeArray(TypeInfo[] typeInfo)
  {
    MgrTypeInfo[] r = new MgrTypeInfo[typeInfo.length];
    for (int i = 0; i < r.length; ++i)
    {
      r[i] = MgrTypeInfo.make(typeInfo[i]);
    }
    return r;
  }

  /**
   * A discovery object is a group if it implements BINDiscoveryGroup.
   */
  @Override
  public boolean isGroup(Object discovery)
  {
    return (discovery instanceof BINDiscoveryGroup);
  }

/////////////////////////////////////////////////////////////////////////////
// getIcon
/////////////////////////////////////////////////////////////////////////////

  /**
   * Overrides to call getDiscoveryIcon on discovery object if it implements
   * BINDiscoveryIcon. Otherwise return a default icon
   */
  @Override
  public BImage getIcon(Object discovery)
  {

    BIcon icon = null;
    if (discovery instanceof BINDiscoveryIcon)
    {
      icon = ((BINDiscoveryIcon)discovery).getDiscoveryIcon();
    }
    else if (getManager() instanceof BNDeviceManager)
    {
      icon = getDeviceIconDefault(discovery);
    }
    else if (getManager() instanceof BNPointManager)
    {
      icon = getPointIconDefault(discovery);
    }


    return (icon != null) ? BImage.make(icon) : null;
  }

  /*
   * Determines an icon to use for the given discovery object.
   */
  private BIcon getPointIconDefault(Object discovery)
  {
    if (discovery instanceof BINDiscoveryGroup)
    {
      return pointGroupIcon;
    }
    if (discovery instanceof BINDiscoveryLeaf)
    {
      return getPointIconDefault((BINDiscoveryLeaf)discovery);
    }

    // If nothing else, this returns stringElementIconRo -- a gray rectangle.
    return stringElementIconRo;
  }

  /*
   * Returns an icon based on point type.
   */
  private BIcon getPointIconDefault(BINDiscoveryLeaf discovery)
  {
    TypeInfo[] databaseTypes = discovery.getValidDatabaseTypes();
    if (databaseTypes == null || databaseTypes.length == 0)
    {
      return null;
    }

    Type defaultType = databaseTypes[0].getTypeSpec().getResolvedType();
    if (defaultType == null)
    {
      return null;
    }

    // Checks all writables before the corresponding non-writatable point because
    // writable control points subclass their corresponding non-writable counterparts.
    if (defaultType.is(BNumericWritable.TYPE))
    {
      return numericElementIcon;
    }
    if (defaultType.is(BNumericPoint.TYPE))
    {
      return numericElementIconRo;
    }
    if (defaultType.is(BBooleanWritable.TYPE))
    {
      return booleanElementIcon;
    }
    if (defaultType.is(BBooleanPoint.TYPE))
    {
      return booleanElementIconRo;
    }
    if (defaultType.is(BEnumWritable.TYPE))
    {
      return enumElementIcon;
    }
    if (defaultType.is(BEnumPoint.TYPE))
    {
      return enumElementIconRo;
    }
    if (defaultType.is(BStringWritable.TYPE))
    {
      return stringElementIcon;
    }
    if (defaultType.is(BStringPoint.TYPE))
    {
      return stringElementIconRo;
    }

    return null;
  }

  /*
   * Gets an icon for a device manager's discovery object.
   */
  private BIcon getDeviceIconDefault(Object discovery)
  {
    if (discovery instanceof BINDiscoveryGroup)
    {
      return deviceGroupIcon;
    }
    else
    {
      return deviceIcon;
    }
  }

/////////////////////////////////////////////////////////////////////////////
// NMgrLearn - table update subscriber support
/////////////////////////////////////////////////////////////////////////////
  Subscriber discoveryFolderSub = new Subscriber()
  {
    @Override
    public void event(BComponentEvent event)
    {
      if (!(event.getValue() instanceof BNDiscoveryLeaf))
      {
        return;
      }

      if (event.getId() == BComponentEvent.PROPERTY_ADDED)
      {
        if (hasRoot(event.getValue()))
        {
          return;
        }
        updateRoots(((BNDiscoveryJob)getJob()).getRootDiscoveryObjects());
      }
      else if (event.getId() == BComponentEvent.PROPERTY_REMOVED)
      {
        if (!hasRoot(event.getValue()))
        {
          return;
        }
        updateRoots(((BNDiscoveryJob)getJob()).getRootDiscoveryObjects());
      }
    }
  };

  private boolean hasRoot(BValue value)
  {
    for (int i = 0; i < getRootCount(); i++)
    {
      if (value.equivalent(getRoot(i)))
      {
        return true;
      }
    }
    return false;
  }


/////////////////////////////////////////////////////////////////////////
// Statics 
/////////////////////////////////////////////////////////////////////////

  /**
   * Default BINDeviceDiscoveryLeaf icon
   */
  public static final BIcon deviceIcon = BIcon.make("module://icons/x16/device.png");

  /**
   * Point group icon
   */
  public static final BIcon pointGroupIcon = BIcon.make("module://icons/x16/pointFolder.png");

  /**
   * Device group icon
   */
  public static final BIcon deviceGroupIcon = BIcon.make("module://icons/x16/deviceFolder.png");

  /**
   * NumericWritable icon
   */
  public static final BIcon numericElementIcon = BIcon.make("module://icons/x16/control/numericPoint.png");

  /**
   * BooleanWritable icon
   */
  public static final BIcon booleanElementIcon = BIcon.make("module://icons/x16/control/booleanPoint.png");

  /**
   * EnumWritable icon
   */
  public static final BIcon enumElementIcon = BIcon.make("module://icons/x16/control/enumPoint.png");

  /**
   * StringWritable icon
   */
  public static final BIcon stringElementIcon = BIcon.make("module://icons/x16/control/stringPoint.png");

  /**
   * NumericPoint icon
   */
  public static final BIcon numericElementIconRo = BIcon.make("module://icons/x16/statusNumeric.png");

  /**
   * BooleanPoint icon
   */
  public static final BIcon booleanElementIconRo = BIcon.make("module://icons/x16/statusBoolean.png");

  /**
   * EnumPoint icon
   */
  public static final BIcon enumElementIconRo = BIcon.make("module://icons/x16/statusEnum.png");

  /**
   * StringPoint icon
   */
  public static final BIcon stringElementIconRo = BIcon.make("module://icons/x16/statusString.png");

/////////////////////////////////////////////////////////////////////////
//Attributes 
/////////////////////////////////////////////////////////////////////////
}