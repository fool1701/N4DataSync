/*
 * Copyright 2022 Tridium Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import javax.baja.file.BDirectory;
import javax.baja.file.BIFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.list.BList;

/**
 * A drop down editor for entering or selecting a subdirectory of a given directory. The
 * subdirectory list is recursive. This editor is best used when there are few subdirectories
 * and shallow nesting of subdirectories.
 *
 * @author M Swainston on 5/26/2022.
 * @since Niagara 4.13
 */
@NiagaraType
/*
 If editable is true then the text may be modified directly or by drop down list selection;
 if false the text is readonly.
 */
@NiagaraProperty(
  name = "editable",
  type = "boolean",
  defaultValue = "true"
)
/*
 The base directory for generating the list of subdirectories.
 */
@NiagaraProperty(
  name = "baseDirectoryOrd",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT"
)
public class BSubdirectoryDropDown
  extends BTextDropDown
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BSubdirectoryDropDown(1031150039)1.0$ @*/
/* Generated Mon Jun 27 13:00:20 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "editable"

  /**
   * Slot for the {@code editable} property.
   * If editable is true then the text may be modified directly or by drop down list selection;
   * if false the text is readonly.
   * @see #getEditable
   * @see #setEditable
   */
  public static final Property editable = newProperty(0, true, null);

  /**
   * Get the {@code editable} property.
   * If editable is true then the text may be modified directly or by drop down list selection;
   * if false the text is readonly.
   * @see #editable
   */
  public boolean getEditable() { return getBoolean(editable); }

  /**
   * Set the {@code editable} property.
   * If editable is true then the text may be modified directly or by drop down list selection;
   * if false the text is readonly.
   * @see #editable
   */
  public void setEditable(boolean v) { setBoolean(editable, v, null); }

  //endregion Property "editable"

  //region Property "baseDirectoryOrd"

  /**
   * Slot for the {@code baseDirectoryOrd} property.
   * The base directory for generating the list of subdirectories.
   * @see #getBaseDirectoryOrd
   * @see #setBaseDirectoryOrd
   */
  public static final Property baseDirectoryOrd = newProperty(0, BOrd.DEFAULT, null);

  /**
   * Get the {@code baseDirectoryOrd} property.
   * The base directory for generating the list of subdirectories.
   * @see #baseDirectoryOrd
   */
  public BOrd getBaseDirectoryOrd() { return (BOrd)get(baseDirectoryOrd); }

  /**
   * Set the {@code baseDirectoryOrd} property.
   * The base directory for generating the list of subdirectories.
   * @see #baseDirectoryOrd
   */
  public void setBaseDirectoryOrd(BOrd v) { set(baseDirectoryOrd, v, null); }

  //endregion Property "baseDirectoryOrd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubdirectoryDropDown.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BSubdirectoryDropDown()
  {
    this(BOrd.DEFAULT, "");
  }

  public BSubdirectoryDropDown(BOrd baseDirectory)
  {
    this(baseDirectory, "");
  }

  public BSubdirectoryDropDown(BOrd baseDirectory, String subdirectory)
  {
    setBaseDirectoryOrd(baseDirectory);
    getEditor().setText(subdirectory);
    loadSubdirectories();
  }

  public BSubdirectoryDropDown(BOrd baseDirectory, String subdirectory, int visibleColumns, boolean editable)
  {
    this(baseDirectory, subdirectory);
    getEditor().setVisibleColumns(visibleColumns);
    getEditor().setEditable(editable);
    setDropDownEnabled(editable);
    setEditable(editable);
  }

  @Override
  public void changed(Property prop, Context context)
  {
    super.changed(prop, context);
    if (prop == baseDirectoryOrd)
    {
      loadSubdirectories();
    }
    else if (prop == editable)
    {
      getEditor().setEditable(getEditable());
      setDropDownEnabled(getEditable());
    }
  }

  private void loadSubdirectories()
  {
    BList subdirectoryList = getList();
    subdirectoryList.removeAllItems();
    BObject baseDirectoryObject;
    try
    {
      baseDirectoryObject = getBaseDirectoryOrd().resolve().get();
    }
    catch (Exception ignore)
    {
      // Failed to resolve the directly, leave the subdirectory list empty.
      baseDirectoryObject = null;
    }
    if (baseDirectoryObject instanceof BDirectory)
    {
      BDirectory baseDirectory = (BDirectory)baseDirectoryObject;
      addSubdirectoriesToList(subdirectoryList, baseDirectory, "");
    }
  }

  private void addSubdirectoriesToList(BList subdirectoryList, BDirectory directory, String directoryPrefix)
  {
    for (BIFile file : directory.listFiles())
    {
      if (file.isDirectory())
      {
        String subdirectoryName = directoryPrefix + file.getFileName();
        subdirectoryList.addItem(subdirectoryName);

        // RECURSIVE
        addSubdirectoriesToList(subdirectoryList, (BDirectory)file, subdirectoryName + '/');
      }
    }
  }
}
