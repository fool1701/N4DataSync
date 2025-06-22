/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Id;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagDictionaryService;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.list.BList;
import javax.baja.ui.options.BMruButton;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.BTypeSpec;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BRelationIdFE
 *
 * @author    Andrew Saunders
 * @creation  16 Apr 14
 * @since     Baja 4.0
 */
@NiagaraType
@NiagaraAction(
  name = "dctnryChanged"
)
@NiagaraAction(
  name = "nameChanged"
)
public class BRelationIdFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BRelationIdFE(3937154958)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "dctnryChanged"

  /**
   * Slot for the {@code dctnryChanged} action.
   * @see #dctnryChanged()
   */
  public static final Action dctnryChanged = newAction(0, null);

  /**
   * Invoke the {@code dctnryChanged} action.
   * @see #dctnryChanged
   */
  public void dctnryChanged() { invoke(dctnryChanged, null, null); }

  //endregion Action "dctnryChanged"

  //region Action "nameChanged"

  /**
   * Slot for the {@code nameChanged} action.
   * @see #nameChanged()
   */
  public static final Action nameChanged = newAction(0, null);

  /**
   * Invoke the {@code nameChanged} action.
   * @see #nameChanged
   */
  public void nameChanged() { invoke(nameChanged, null, null); }

  //endregion Action "nameChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelationIdFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BRelationIdFE()
  {
    linkTo("lk3", dctnrybox, BListDropDown.actionPerformed, actionPerformed);
    linkTo("lk4", dctnrybox, BListDropDown.valueModified,   dctnryChanged);
    linkTo("lk5", dctnrybox, BListDropDown.valueModified,   setModified);
    linkTo("lk6", namebox, BListDropDown.valueModified,   nameChanged);
    linkTo("lk7", namebox, BListDropDown.valueModified,   setModified);
    linkTo("lk8", namebox, BListDropDown.actionPerformed, actionPerformed);

    history = new BMruButton("relIdFE", new MruFilter());
    history.setMruController(new MruController());
    history.setButtonStyle(BButtonStyle.toolBar);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  // copied from BOrdFE 
  public void started()
  {
    // Cache wbshell
    wbShell = getWbShell();

    // This is a big hack - but we need the shell so we can
    // query our agents correctly - so loadValue again here

    // Mike James, August 5th, 2009: Since doLoadValue, under
    // normal conditions, originates from the parent BWbEditor
    // class, its modified state is locked while loading. Recreate
    // that lock so that the modified state of the FE does not change
    // while calling doLoadValue. See issue 11113
    BObject val = getCurrentValue();

    if (val != null)
    {
      lockModifiedState();
      doLoadValue(val, getCurrentContext());
      unlockModifiedState();
    }
  }

  protected void doSetReadonly(boolean readonly)
  {
    dctnrybox.setDropDownEnabled(!readonly);
    namebox.setDropDownEnabled(!readonly);
    history.setEnabled(!readonly);
  }

  public void computePreferredSize()
  {
    super.computePreferredSize();

    BListDropDown fe = new BListDropDown();
    fe.computePreferredSize();
    setPreferredSize(getPreferredWidth(), fe.getPreferredHeight());
  }

  protected void doLoadValue(BObject value, Context cx)
  {
    // wait for started to init wbShell.
    if(wbShell == null)
      return;
    BString curRelationId = (BString)value;
    // populate dctnrybox
    loadDictionaryList(dctnrybox);
    if (pane != null)
    {
      pane.remove("dctnrybox");
      pane.remove("namebox");
      pane.remove("history");
    }

    initToCurrentSelection(curRelationId.getString());
    if (dctnrybox.getList().getSelectedIndex() == -1)
      dctnrybox.getList().setSelectedIndex(0);

    if (namebox.getList().getSelectedIndex() == -1 &&
        dctnrybox.getSelectedItem() != null)
    {
      loadRelationsList((TagDictionary)dctnrybox.getSelectedItem(), namebox);
      namebox.getList().setSelectedIndex(0);
    }
    initToCurrentSelection(curRelationId.getString());

    pane = new BGridPane(3);
    pane.setHalign(BHalign.left);
    pane.add("dctnrybox", dctnrybox);
    pane.add("namebox", namebox);
    pane.add("history", history);
    setContent(pane);

    repaint();
  }

  private void initToCurrentSelection(String qname)
  {
    Id id = null;
    try{id = Id.newId(qname);}
    catch(Exception e){}
    if(id != null)
    {
      String dName = id.getDictionary();
      String name = id.getName();
      getTagDictionaryService();
      Optional<TagDictionary> optional = service.getTagDictionary(dName);
      if (optional.isPresent())
        dctnrybox.getList().setSelectedItem(optional.get());
      else
        dctnrybox.getList().setSelectedIndex(0);

      namebox.getList().setSelectedItem(id);
    }
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    String qname   = ((Id)namebox.getSelectedItem()).getQName();
    if ( qname == null)
      return BString.DEFAULT;
    BString rtnStr = BString.make(qname);

    if (autoSaveHistory) saveHistory(rtnStr);
    return rtnStr;
  }

  /**
   * Save the TypeSpec history. This uses the current value, so
   * if you have disabled auto saving, then you must perform a
   * saveValue first to get the correct value.
   */
  public void saveHistory()
  {
    saveHistory((BString)getCurrentValue());
  }

  private void saveHistory(BString qname)
  {
    try
    {
      history.getMruOptions().save(qname.encodeToString());
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
   * Handle a change to the dictionary drop down.
   */
  public void doDctnryChanged()
  {
    TagDictionary dictionary = (TagDictionary)dctnrybox.getSelectedItem();
    loadRelationsList(dictionary, namebox);
  }

  /**
   * Handle a change to the type drop down.
   */
  public void doNameChanged()
  {
    System.out.println("relationId.name changed: " + ((Id)namebox.getSelectedItem()).getQName());
  }

  /**
   * Load the listDropDown with the installed tag dictionaries that have relationsInfos.
   */
  protected void loadDictionaryList(BListDropDown listBox)
  {
    if(service == null)
      service = getTagDictionaryService();
    Collection<TagDictionary> tagDictionaries = service.getTagDictionaries();
    BList list = listBox.getList();
    list.removeAllItems();
    for (TagDictionary dictionary : tagDictionaries)
    {
      if(dictionary.getRelations().hasNext())
        list.addItem(dictionary);
    }
  }

  /**
   * Load the relations list drop down with the relations defined in the currently selected tag dictionary
   */  
  protected void loadRelationsList(TagDictionary dictionary, BListDropDown listBox)
  {
    Iterator<RelationInfo> relations = dictionary.getRelations();
    BList list = listBox.getList();
    list.removeAllItems();
    while(relations.hasNext())
    {
      list.addItem(relations.next().getRelationId());
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
      return obj instanceof RelationInfo;
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

  /**
   * Lookup the TagDictionaryService based on the session of
   * the target component.
   *
   * @return Returns the TagDictionaryService for the current
   *   session or null if the TagDictionaryService is not available.
   */
  public TagDictionaryService getTagDictionaryService()
  {
    if(service != null)
      return service;
    try
    {
      OrdTarget activeOrdTarget = wbShell.getActiveOrdTarget();
      BComponent baseComp = activeOrdTarget.getComponent();

      service = baseComp.getTagDictionaryService();
      ((BComponent)service).lease(Integer.MAX_VALUE);
      return service;
    }
    catch(UnresolvedException ex)
    {
      return null;
    }
  }



////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BString curRelationId;
  private BWbShell wbShell;
  private TagDictionaryService service;
  private BListDropDown dctnrybox = new BListDropDown();
  private BListDropDown namebox = new BListDropDown();
  private BMruButton history;

  private boolean autoSaveHistory = true;

  private BGridPane pane;
}
