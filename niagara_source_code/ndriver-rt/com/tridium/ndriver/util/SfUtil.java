/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.ndriver.util;

import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import com.tridium.sys.schema.NProperty;

/**
 * SfUtil contains utilities for adding slotfacets need to facilitate inclusion
 * of slots in manager views.
 *
 * @author Robert A Adams
 * @creation Nov 29, 2011
 */
public final class SfUtil
{
  private SfUtil() {}

  public static BFacets incl()
  {
    return BFacets.make(KEY_MGR, "");
  }

  public static BFacets incl(String mgrFlag)
  {
    return BFacets.make(KEY_MGR, mgrFlag);
  }

  public static BFacets incl(String mgrFlag, BFacets f)
  {
    return BFacets.make(incl(mgrFlag), f);
  }

  public static BFacets incl(BFacets f)
  {
    return BFacets.make(incl(), f);
  }

  public static boolean isMgrInclude(BComplex owner, Property prop)
  {
    BObject mgrVal = owner.getSlotFacets(prop).get(KEY_MGR);
    return mgrVal != null;
  }

  public static int getMgrColumnFlags(BComplex owner, Property prop)
  {
    int mgrColumnFlags = 0;

    String mgrVal = owner.getSlotFacets(prop).gets(KEY_MGR, "").toLowerCase();
    if (mgrVal.indexOf("ed") >= 0)
    {
      mgrColumnFlags |= EDITABLE;
    }
    if (mgrVal.indexOf("un") >= 0)
    {
      mgrColumnFlags |= UNSEEN;
    }
    if (Flags.isReadonly(owner, prop) || mgrVal.indexOf("ro") >= 0)
    {
      mgrColumnFlags |= READONLY;
    }

    return mgrColumnFlags;
  }

  public static boolean isMgrInclude(Property prop)
  {
    BObject mgrVal = ((NProperty)prop).getFacets().get(KEY_MGR);
    return mgrVal != null;
  }

  public static int getMgrColumnFlags(Property prop)
  {
    int mgrColumnFlags = 0;
    String mgrVal = ((NProperty)prop).getFacets().gets(KEY_MGR, "").toLowerCase();
    if (mgrVal.indexOf("ed") >= 0)
    {
      mgrColumnFlags |= EDITABLE;
    }
    if (mgrVal.indexOf("un") >= 0)
    {
      mgrColumnFlags |= UNSEEN;
    }

    boolean readOnlyProp = ((((NProperty)prop).getDefaultFlags() & Flags.READONLY) != 0);
    if (readOnlyProp || mgrVal.indexOf("ro") >= 0)
    {
      mgrColumnFlags |= READONLY;
    }
    return mgrColumnFlags;
  }


  public static final String KEY_MGR = "Mgr";

  // Static strings to pass as mgrFlag arguments in incl() utilities
  // See javax.baja.workbench.mgr.MgrColumn for definition of flags
  public static final String MGR_EDIT = "ed";
  public static final String MGR_UNSEEN = "un";
  public static final String MGR_EDIT_UNSEEN = "ed.un";
  public static final String MGR_EDIT_READONLY = "ed.ro";
  public static final String MGR_EDIT_UNSEEN_READONLY = "ed.un.ro";


////////////////////////////////////////////////////////////////
// Flags from MgrColumn
////////////////////////////////////////////////////////////////

  /**
   * Editable indicates a column which is included for edits via the MgrEdit
   * APIs.
   */
  public static final int EDITABLE = 0x0001;

  /**
   * The unseen flag is used on columns which are not shown by default until the
   * user turns them on via table options
   */
  public static final int UNSEEN = 0x0002;

  /**
   * The Readonly flag is used on editable columns which are displayed via the
   * MgrEdit dialog, but not user modifiable
   */
  public static final int READONLY = 0x0004;
}
