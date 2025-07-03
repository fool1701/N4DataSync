/*
 * Copyright 2022 Tridium Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.file.BFileScheme;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.workbench.file.BSubdirectoryDropDown;

/**
 * This field editor presents a simple drop down selection for all subdirectories of a
 * given directory. The edited value is a single ORD specifying one or two (non-normalized)
 * file ORD entries.
 *
 * The first ORD must resolve to a directory. It represents the base directory from which the
 * subdirectory selection list will be derived. It will not be modified by the editor.
 *
 * The second ORD is a relative file ORD. It will be created or modified through the editor.
 * When the second ORD is missing, the base directory itself is, in effect, the selected
 * subdirectory. This ORD, when normalized with the first ORD must also specify a directory.
 * New subdirectories can be specified by typing directly into the field editor rather than
 * selecting from the drop down list. In this way, it is also possible to enter an absolute
 * path rather than the more typical relative path.
 *
 * No directories are created by the field editor so there is no guarantee that the directory
 * specified by the resulting ORD exists.
 *
 * Examples:
 * The ORD "file:~templates|file:specials" results in a drop-down list of all subdirectories
 * of "~templates" with "specials" initially selected, meaning "~templates/specials" is
 * effectively selected, which is the ultimate resolution of that ORD.
 *
 * The ORD "file:~applicationTemplates" results in a drop-down list of all subdirectories of
 * "~applicationTemplates" with no subdirectory initially selected, meaning the base directory
 * is selected.
 *
 * The ORD "file:~stationTemplates|file:~templates" results in a drop-down list of all
 * subdirectories of "~stationTemplates", but since the second ORD is an absolute path, its full
 * value is displayed, as in "~templates", which is the ultimate resolution of that ORD. This
 * case is an outlier and requires direct input because the drop-down list does not include the
 * value.
 *
 * @author M Swainston on 5/27/2022.
 * @since Niagara 4.13
 */
@NiagaraType
public class BSubdirectoryFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BSubdirectoryFE(2979906276)1.0$ @*/
/* Generated Fri May 27 12:17:39 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSubdirectoryFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    getDropDown().setEditable(!readonly);
  }

  @Override
  protected void doLoadValue(BObject value, Context context)
    throws Exception
  {
    BOrd baseDirectory = BOrd.DEFAULT;
    String subdirectory = "";
    if (value instanceof BOrd)
    {
      OrdQuery[] queries = ((BOrd)value).parse();
      baseDirectory = getBaseDirectoryOrd(queries);
      subdirectory = getSubdirectoryName(queries);
    }

    BSubdirectoryDropDown dropDown = getDropDown();
    dropDown.setBaseDirectoryOrd(baseDirectory);
    dropDown.setText(subdirectory);
  }

  private BSubdirectoryDropDown getDropDown()
  {
    if (dropDown == null)
    {
      BSubdirectoryDropDown field = new BSubdirectoryDropDown();
      linkTo(field, BSubdirectoryDropDown.valueModified, setModified);
      linkTo(field, BSubdirectoryDropDown.actionPerformed, actionPerformed);
      linkTo(field, BSubdirectoryDropDown.listActionPerformed, actionPerformed);
      dropDown = field;
      setContent(dropDown);
    }
    return dropDown;
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    BSubdirectoryDropDown dropDown = getDropDown();
    return buildValue(dropDown.getBaseDirectoryOrd(), dropDown.getText());
  }

  public static BOrd buildValue(BOrd baseDirectoryOrd, String subdirectoryName)
  {
    if (subdirectoryName.isEmpty())
    {
      return baseDirectoryOrd;
    }
    return BOrd.make(baseDirectoryOrd, "file:" + subdirectoryName);
  }

  public static String getSubdirectoryNameFromValue(BOrd ord)
  {
    return getSubdirectoryName(ord.parse());
  }

  public static BOrd getBaseDirectoryOrdFromValue(BOrd ord)
  {
    return getBaseDirectoryOrd(ord.parse());
  }

  private static String getSubdirectoryName(OrdQuery[] queries)
  {
    if (queries.length > 0 && queries[queries.length - 1].getScheme().equals(BFileScheme.INSTANCE.getId()))
    {
      if (queries.length > 1 && queries[queries.length - 2].getScheme().equals(BFileScheme.INSTANCE.getId()))
      {
        // There are two file schemes at the tail of this ORD. The last one is the subdirectory.
        return queries[queries.length - 1].getBody();
      }
    }
    return "";
  }

  private static BOrd getBaseDirectoryOrd(OrdQuery[] queries)
  {
    if (queries.length > 0 && queries[queries.length - 1].getScheme().equals(BFileScheme.INSTANCE.getId()))
    {
      if (queries.length > 1 && queries[queries.length - 2].getScheme().equals(BFileScheme.INSTANCE.getId()))
      {
        // There are two file schemes at the tail of this ORD. The penultimate is the base directory.
        return BOrd.make(queries, 0, queries.length - 1);
      }
    }
    return BOrd.make(queries);
  }

  BSubdirectoryDropDown dropDown;
}
