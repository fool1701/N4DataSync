/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;

import java.util.ArrayList;

import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrColumn.Name;
import javax.baja.workbench.mgr.MgrEdit;
import javax.baja.workbench.mgr.MgrEditRow;

import com.tridium.template.BTemplateConfig;

/**
 * Created by E333968 on 4/30/2017.
 * @since     Niagara 4.3
 * DeficeTemplateEdit provides a seperate DeviceManager template config property editor for station devices
 * that are deployed templates.
 *
 */
public class DeviceTemplateEdit extends MgrEdit
{
  /**
   * Constructor.
   *
   * @param manager
   * @param title
   */
  public DeviceTemplateEdit(BAbstractManager manager, String title)
  {
    super(manager, title);
  }

  /**
   * super will Loop through all the rows and insure that each
   * target has a valid name.
   * then
   * Make sure BTemplateConfig components are leased.
   */
  public void checkTargetNames()
  {
    super.checkTargetNames();
    // make sure TemplateConfig's are subscrbed
    int depth = getManager().getModel().getSubscribeDepth();
    if(depth >= 2)  // if >= 2 then it would be subscribed.
      return;
    // skip if no rows
    MgrEditRow[] rows = getRows();
    if (rows.length == 0 )
      return;

    // all rows should have a BTemplateConfig
    // since the EditTemplate command will not be enabled
    // if all selected rows don't have the same type of template.
    BComponent[] tcs = new BComponent[rows.length];
    for(int i=0; i<rows.length; ++i)
    {
      tcs[i] = getTemplateConfig(rows[i].getTarget());
    }
    BComponent.lease(tcs, 1);
  }


  protected MgrColumn[] makeColumns()
  {
    BComponent target = getManager().getModel().getTable().getSelectedComponent();
    if(target != null)
      return makeColumns(target);
    return new MgrColumn[0];
  }

  /**
   * Return an array of Columns for editable properties of the BTemplateConfig child of the given
   * target component.
   */
  public MgrColumn[] makeColumns(BComponent target)
  {
    ArrayList<MgrColumn> columns = new ArrayList<MgrColumn>();
    BTemplateConfig templateConfig = getTemplateConfig(target);
    if(templateConfig != null)
    {
      // first add name column of the template root component
      final Name colName = new Name();
      colName.setFlags(MgrColumn.READONLY);
      columns.add(colName);
      final Property propertyInParent = templateConfig.getPropertyInParent();
      // add a column for each of the Template Config component's persistent
      // editable properties.
      for (Property property : templateConfig.getProperties())
      {
        final int flags = templateConfig.getFlags(property);
        if ((flags & (Flags.READONLY | Flags.TRANSIENT | Flags.HIDDEN)) == 0)
        {
          columns.add(new PropStringPath(new Property[] { propertyInParent, property }, MgrColumn.EDITABLE));
        }
      }
    }
    MgrColumn[] retColumns = new MgrColumn[columns.size()];
    return columns.toArray(retColumns);
  }

  private BTemplateConfig getTemplateConfig(BComponent target)
  {
    return BTemplateConfig.getConfigForRoot(target);
  }

////////////////////////////////////////////////////////////////
// PropPath
///////////////////////////////////////////////////////////////

  /**
   * The MgrColumn.PropStringPath implementation is used to display
   * and manage a property of a row component.
   */
  public static class PropStringPath extends MgrColumn.PropPath
  {
    public PropStringPath(Property props[], int flags)
    {
      super(props, flags);
    }

    /**
     * Overrides super class to use the property name to read the current property values.
     */
    public BValue load(MgrEditRow row)
    {
      BValue val = getTargetBase(row);
      for(int i=0; i<props.length; ++i)
      {
        val = ((BComplex)val).get(props[i].getName());
      }
      return val.newCopy();
    }

    /**
     * Overrides super class to use the property name to set the property values.
     */
    public void save(MgrEditRow row, BValue value, Context cx)
    {
      BComplex target = getTargetBase(row);

      int len = props.length;
      for(int i=0; i<len-1; ++i)
        target = (BComplex)target.get(props[i].getName());

      String prop = props[len-1].getName();

      BValue old = target.get(prop);
      if (!old.equivalent(value)) target.set(prop, value.newCopy(), cx);
    }

    public Object get(Object row)
    {
      BComplex val = getRowBase(row);
      for(int i=0; i<props.length-1; ++i)
        val = (BComplex)val.get(props[i].getName());
      String prop = props[props.length-1].getName();
      return val.get(prop);
    }

  }
}
