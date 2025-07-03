/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.ModuleInfo;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.list.BList;
import javax.baja.ui.options.BMruButton;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.BTypeSpec;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.fox.sys.BFoxSession;

/**
 * BTypeSpecFE allows viewing and editing of a BTypeSpec.
 *
 * @author    Brian Frank
 * @creation  11 Jun 02
 * @version   $Revision: 31$ $Date: 7/14/11 2:40:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:TypeSpec"
  )
)
@NiagaraAction(
  name = "nullChanged"
)
@NiagaraAction(
  name = "mboxChanged"
)
@NiagaraAction(
  name = "tboxChanged"
)
public class BTypeSpecFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTypeSpecFE(2043951795)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "nullChanged"

  /**
   * Slot for the {@code nullChanged} action.
   * @see #nullChanged()
   */
  public static final Action nullChanged = newAction(0, null);

  /**
   * Invoke the {@code nullChanged} action.
   * @see #nullChanged
   */
  public void nullChanged() { invoke(nullChanged, null, null); }

  //endregion Action "nullChanged"

  //region Action "mboxChanged"

  /**
   * Slot for the {@code mboxChanged} action.
   * @see #mboxChanged()
   */
  public static final Action mboxChanged = newAction(0, null);

  /**
   * Invoke the {@code mboxChanged} action.
   * @see #mboxChanged
   */
  public void mboxChanged() { invoke(mboxChanged, null, null); }

  //endregion Action "mboxChanged"

  //region Action "tboxChanged"

  /**
   * Slot for the {@code tboxChanged} action.
   * @see #tboxChanged()
   */
  public static final Action tboxChanged = newAction(0, null);

  /**
   * Invoke the {@code tboxChanged} action.
   * @see #tboxChanged
   */
  public void tboxChanged() { invoke(tboxChanged, null, null); }

  //endregion Action "tboxChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeSpecFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BTypeSpecFE()
  {
    linkTo("lk1", isNull, BCheckBox.selected, nullChanged);
    linkTo("lk3", mbox, BListDropDown.actionPerformed, actionPerformed);
    linkTo("lk4", mbox, BListDropDown.valueModified,   mboxChanged);
    linkTo("lk5", mbox, BListDropDown.valueModified,   setModified);
    linkTo("lk6", tbox, BListDropDown.valueModified,   tboxChanged);
    linkTo("lk7", tbox, BListDropDown.valueModified,   setModified);
    linkTo("lk8", tbox, BListDropDown.actionPerformed, actionPerformed);

    history = new BMruButton("typeSpecFE", new MruFilter());
    history.setMruController(new MruController());
    history.setButtonStyle(BButtonStyle.toolBar);
  }

  protected void doSetReadonly(boolean readonly)
  {
    isNull.setEnabled(!readonly);
    mbox.setDropDownEnabled(!readonly);
    tbox.setDropDownEnabled(!readonly);
    history.setEnabled(!readonly);
  }

  private void toNull()
  {
    BList mlist = mbox.getList();
    if (mlist.getItemCount() > 0)
    {
      if (mlist.getItem(0).equals(""))
        return;
    }

    BList tlist = tbox.getList();

    defModule = (String)mlist.getSelectedItem();
    defType = (String)tlist.getSelectedItem();

    mlist.insertItem(0, "");
    mlist.setSelectedIndex(0);
    tlist.insertItem(0, "");
    tlist.setSelectedIndex(0);
  }

  private void fromNull()
  {
    BList mlist = mbox.getList();
    if (mlist.getItemCount() > 0)
    {
      if (mlist.getItem(0).equals(""))
      {
        BList tlist = tbox.getList();

        mlist.removeItem(0);
        tlist.removeItem(0);
      }
    }

    mlist.setSelectedItem(defModule);
    tbox.getList().setSelectedItem(defType);
  }

  protected boolean getShowInterface()
  {
    return showInterface;
  }
  
  protected boolean getShowAbstract()
  {
    return showAbstract;
  }
  
  protected TypeInfo getBaseType()
  {
    return baseType;
  }
  
  protected void doLoadValue(BObject value, Context cx)
  {
    if (cx != null && cx.getFacets() != null)
    {
      BBoolean b = (BBoolean)cx.getFacet("mruAutoSave");
      if (b != null) setAutoSaveHistory(b.getBoolean());

      allowNull = cx.getFacets().getb(BFacets.ALLOW_NULL, allowNull);
      showAbstract = cx.getFacets().getb(SHOW_ABSTRACT, showAbstract);
      showInterface = cx.getFacets().getb(SHOW_INTERFACE, showInterface);
      baseType = Sys.getRegistry().getType(cx.getFacets().gets(BFacets.TARGET_TYPE, baseType.toString()));
    }

    // populate mbox
    mbox.getList().removeAllItems();
    String[] modules = getModules();
    for(int i=0; i<modules.length; ++i)
    {
      loadTypes(modules[i]);
      if (tbox.getList().getItemCount() > 0)
        mbox.getList().addItem(modules[i]);
    }

    BTypeSpec typeSpec = (BTypeSpec)value;

    // set null field
    isNull.setSelected(typeSpec.isNull());

    // get typespec
    String m = defModule = typeSpec.getModuleName();
    String t = defType = typeSpec.getTypeName();

    // if null then blank the fields
    if (m == null)
    {
      defModule = "baja";
      defType = "String";
      toNull();
    }
    else
    {
      fromNull();
      mbox.getList().setSelectedItem(m);
      loadTypes(m);
      tbox.getList().setSelectedItem(t);
      if (tbox.getList().getItemCount() > 0 &&
          tbox.getList().getSelectedIndex() == -1)
      {
        tbox.getList().setSelectedIndex(0);
        defType = (String)tbox.getList().getSelectedItem();
      }
    }

    if (pane != null)
    {
      if (pane.get("isNull") != null) pane.remove("isNull");
      pane.remove("mbox");
      pane.remove("tbox");
      pane.remove("history");
    }

    if (mbox.getList().getSelectedIndex() == -1)
      mbox.getList().setSelectedIndex(0);

    if (tbox.getList().getSelectedIndex() == -1)
    {
      loadTypes((String)mbox.getList().getSelectedItem());
      tbox.getList().setSelectedIndex(0);
    }

    pane = new BGridPane(allowNull ? 4 : 3);
    pane.setHalign(BHalign.left);
    if (allowNull) pane.add("isNull", isNull);
    pane.add("mbox", mbox);
    pane.add("tbox", tbox);
    pane.add("history", history);
    setContent(pane);

    repaint();
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    if (isNull.isSelected()) return BTypeSpec.NULL;
    String module = (String)mbox.getSelectedItem();
    String type   = (String)tbox.getSelectedItem();
    if (module == null || type == null) return BTypeSpec.NULL;

    BTypeSpec newSpec = BTypeSpec.make(module, type);
    if (autoSaveHistory) saveHistory(newSpec);
    return newSpec;
  }

  /**
   * Save the TypeSpec history. This uses the current value, so
   * if you have disabled auto saving, then you must perform a
   * saveValue first to get the correct value.
   */
  public void saveHistory()
  {
    saveHistory((BTypeSpec)getCurrentValue());
  }

  private void saveHistory(BTypeSpec spec)
  {
    try
    {
      history.getMruOptions().save(spec.encodeToString());
    }
    catch (Exception e) { e.printStackTrace(); }
  }

  /**
   * Set the autoSaveHistory state.  If true, then the history
   * will be saved on every call to BTypeSpecFE.saveValue(). If
   * false, then the history will not be saved. This is defaulted
   * to true.
   */
  public void setAutoSaveHistory(boolean b) { autoSaveHistory = b; }
  public boolean getAutoSaveHistory() { return autoSaveHistory; }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Handle a change to the null checkbox.
   */
  public void doNullChanged()
  {
    if (!allowNull) return;
    boolean isNull = this.isNull.getSelected();
    setModified();
    mbox.setEnabled(!isNull);
    tbox.setEnabled(!isNull);
    history.setEnabled(!isNull);

    if (isNull)
      toNull();
    else
      fromNull();

    if (!isNull && mbox.getList().getItemCount() > 0)
    {
      if (mbox.getList().indexOfItem(defModule) == -1 )
        defModule = (String)mbox.getList().getItem(0);

      mbox.setSelectedItem(defModule);
      loadTypes(defModule);
      tbox.setSelectedIndex(0);
    }
  }

  /**
   * Handle a change to the module drop down.
   */
  public void doMboxChanged()
  {
    isNull.setSelected(false); // force unset if not visible
    String moduleName = (String)mbox.getSelectedItem();
    loadTypes(moduleName);
  }

  /**
   * Handle a change to the type drop down.
   */
  public void doTboxChanged()
  {
    isNull.setSelected(false); // force unset if not visible
  }

  /**
   * Return a list of module names which contain one or more types.
   */
  protected String[] getModules()
  {
    TreeSet<String> moduleNames = new TreeSet<>();
    
    ModuleInfo[] modules = Sys.getRegistry().getModules();
    for(int i=0; i<modules.length; ++i)
      if (modules[i].getTypes().length > 0)
        moduleNames.add(modules[i].getModuleName());
        
    return moduleNames.toArray(new String[moduleNames.size()]);
  }

  /**
   * Return a list of type names for the specified module
   */  
  protected String[] getTypes(String moduleName)
  {
    Set<String> v = new TreeSet<>();
    for (ModuleInfo mi : Sys.getRegistry().getModules(moduleName))
    {
      for (TypeInfo type : mi.getTypes())
      {
        if (type.isAbstract() && !showAbstract) continue;
        if (type.isInterface() && !showInterface) continue;

        // if we made past the abstract check, then we
        // let all interfaces pass muster since they won't
        // ever meet the base type restriction (even if
        // using baja:Object)
        if (!type.isInterface() && !type.is(baseType)) continue;

        v.add(type.getTypeName());
      }
    }
    return v.toArray(new String[v.size()]);
  }
 
  /**
   * Load the type drop down with the types for the specified module.
   */
  protected void loadTypes(String moduleName)
  {
    enterBusy();
    try
    {
      tbox.getList().removeAllItems();

      String[] typeNames = getTypes(moduleName);

      // Filter the type names to only those available on the remote VM (if necessary)
      Collection<TypeInfo> typeInfos = Arrays.stream(typeNames)
        .map(typeName -> Sys.getRegistry().getType(moduleName + ":" + typeName))
        .collect(Collectors.toList());
      typeInfos = BFoxSession.getRemoteTypesForSession(typeInfos);
      typeNames = typeInfos.stream().map(typeInfo -> typeInfo.getTypeName()).toArray(String[]::new);

      if (typeNames.length == 0)
      {
        tbox.setSelectedIndex(-1);
      }
      else
      {
        SortUtil.sort(typeNames);
        for(int i=0; i<typeNames.length; ++i) tbox.getList().addItem(typeNames[i]);
        tbox.setSelectedIndex(0);
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    finally
    {
      exitBusy();
    }
  }

////////////////////////////////////////////////////////////////
// MruController
////////////////////////////////////////////////////////////////

  class MruController extends BMruButton.MruController
  {
    public void select(String value)
    {
      try
      {
        loadValue(BTypeSpec.make(value), getCurrentContext());
        setModified();
      }
      catch (Exception e) { e.printStackTrace(); }
    }
  }

  class MruFilter implements IFilter
  {
    public boolean accept(Object obj)
    {
      try
      {
        TypeInfo info = Sys.getRegistry().getType((String)obj);
        return info.is(baseType);
      }
      catch (Exception e) { return false; }
    }
  }

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////

  /*
  public static void main(String[] args)
    throws Exception
  {
    BTypeSpecFE e = new BTypeSpecFE();
    BFacets facets = BFacets.make("allowNull", BBoolean.TRUE);
    //facets = facets.make("showAbstract", BBoolean.FALSE);
    facets = facets.make("typeSpec", BString.make("baja:Simple"));

    e.loadValue(BTypeSpec.make("bajaui", "Alignment"), facets);
    e.setReadonly(false);

    BGridPane pane = new BGridPane();
    pane.add("x", e);

    BFrame frame = new BFrame("Test", pane);
    frame.setScreenBounds(100,100,600,100);
    frame.open();
  }
  */

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final BCheckBox isNull = new BCheckBox("null");
  protected BListDropDown mbox = new BListDropDown();
  protected BListDropDown tbox = new BListDropDown();
  protected BMruButton history;

  private String defModule = "baja";
  private String defType   = "String";
  private TypeInfo baseType = BObject.TYPE.getTypeInfo();

  public static final String SHOW_ABSTRACT = "showAbstract";
  public static final String SHOW_INTERFACE = "showInterface";

  private boolean allowNull = false;
  private boolean showAbstract = false;
  private boolean showInterface = true;
  private boolean autoSaveHistory = true;

  protected BGridPane pane;
}
