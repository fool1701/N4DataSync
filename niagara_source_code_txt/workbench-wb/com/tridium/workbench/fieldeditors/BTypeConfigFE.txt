/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.baja.control.trigger.BTriggerMode;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.naming.BISession;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.BTypeConfig;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.fox.sys.BFoxSession;
import com.tridium.util.CompUtil;

/**
 * BTypeConfigFE is used to edit BTypeConfig and handle the
 * special cases when the typeSpec property is modified.
 *
 * @author    Brian Frank       
 * @creation  6 Sept 01
 * @version   $Revision: 10$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:TypeConfig"
  )
)
@NiagaraAction(
  name = "typeSelectionChanged"
)
public class BTypeConfigFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTypeConfigFE(586401309)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "typeSelectionChanged"

  /**
   * Slot for the {@code typeSelectionChanged} action.
   * @see #typeSelectionChanged()
   */
  public static final Action typeSelectionChanged = newAction(0, null);

  /**
   * Invoke the {@code typeSelectionChanged} action.
   * @see #typeSelectionChanged
   */
  public void typeSelectionChanged() { invoke(typeSelectionChanged, null, null); }

  //endregion Action "typeSelectionChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeConfigFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Construction
////////////////////////////////////////////////////////////////

  public BTypeConfigFE()
  {                  
    linkTo(types, BListDropDown.valueModified, typeSelectionChanged);        
    setContent(new BBorderPane(content, BBorder.inset, BInsets.make(2d)));
  }
  
  void build()
  {                        
    // Do initial build:
    //   EdgePane
    //     top: GridPane
    //       frozen editors[]
    //      type drop down
    //     center: doTypeSelectionChanged()
    BGridPane top = new BGridPane(2);
    top.setColumnGap(10);
    top.setHalign(BHalign.left);   

    // get frozen non-hidden slots
    Array<Property> acc = new Array<>(Property.class);
    Property[] fprops = value.getFrozenPropertiesArray();
    for(int i=1; i<fprops.length; ++i) // always skip type
      if (!Flags.isHidden(value, fprops[i])) acc.add(fprops[i]);
    
    // populate frozen slots and their editors
    frozenProps = acc.trim();
    frozenEditors = new BWbFieldEditor[frozenProps.length];
    for(int i=0; i<frozenEditors.length; ++i)
    {            
      Property prop  = frozenProps[i];             
      String display = value.getDisplayName(prop, null);
      BValue kid     = value.get(prop);
      BFacets facets = value.getSlotFacets(prop);   
      frozenEditors[i] = addEditor(top, "f_"+i, display, kid, facets);
    }                         
    
    // make sure readonly applied correctly
    doSetReadonly(isReadonly());
    
    // add row for type drop down
    top.add(null, new BLabel("Type"));
    top.add(null, types);   
    content.setTop(top);         
  }
  
  void buildUnavailable()
  {
    if(builtUnavailable)
      return;
    builtUnavailable=true;
    BGridPane top = new BGridPane(2);
    top.setColumnGap(10);
    top.setHalign(BHalign.left);
    
    types.getList().addItem(lex.getText("unavailable"));
    types.setSelectedIndex(0);
    types.setDropDownEnabled(false);
    top.add(null, types);
    content.setTop(top);
  }
  
  
////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////  
  
  protected void doSetReadonly(boolean readonly)
  {                      
    super.doSetReadonly(readonly);
    
    if(isUnavailable((BTypeConfig)getCurrentValue()))
      return;
    
    boolean ro = readonly;
    if (value != null) ro |= Flags.isHidden(value, BTypeConfig.typeSpec);
    if (value != null) ro |= Flags.isReadonly(value, BTypeConfig.typeSpec);
    
    types.setDropDownEnabled(!ro);
    if (configEditors != null) {
      for (int i = 0; i < configEditors.length; i++) {
        if (configEditors[i] != null) {
          configEditors[i].setReadonly(ro);
        }
      }
    }
    
    for(int i=0; i<frozenProps.length; ++i)
    {
      Property prop = frozenProps[i];
      ro = readonly;
      if (value != null) ro |= Flags.isReadonly(value, prop);
      frozenEditors[i].setReadonly(ro);
    }
  }  

  protected void doLoadValue(BObject raw, Context cx)
    throws Exception
  {                     
    value = (BTypeConfig)raw;
    
    if(isUnavailable(value))
    {
      buildUnavailable();      
      return;
    }
    
    // get current type info
    TypeInfo current = null;
    try
    {
      if (!value.getTypeSpec().isNull())
        current = value.getTypeSpec().getTypeInfo();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }             
    
    // if first time thru, then build frozen section
    if (frozenProps == null)
      build();          
    
    // reload frozen editors
    for(int i=0; i<frozenEditors.length; ++i)
    {
      BValue kid = value.get(frozenProps[i]).newCopy();
      BFacets facets = value.getSlotFacets(frozenProps[i]);
      frozenEditors[i].loadValue(kid, facets);
    }

    // populate types drop down
    int preselect = 0;
    types.getList().removeAllItems();

    // Filter the types to only those that are available on the remote VM
    BFoxSession foxSession = null;
    BISession session = getCurrentValueSession();
    if (session instanceof BFoxSession)
    {
      foxSession = (BFoxSession)session;
      foxSession.setThreadLocalSessionId();
    }

    try
    {
      TypeInfo[] targetTypes = Sys.getRegistry().getConcreteTypes(value.getTargetType());
      Collection<TypeInfo> filtered = BFoxSession.getRemoteTypesForSession(Arrays.asList(targetTypes));
      int index = 0;
      for (TypeInfo ti: filtered)
      {
        Item item = new Item(ti);
        types.getList().addItem(item.icon, item);
        if (ti == current) preselect = index;
        index++;
      }
    }
    finally
    {
      if (foxSession != null)
        foxSession.clearThreadLocalSessionId();
    }
    
    // preselect the current type or the first 
    // if that isn't a viable option 
    types.setSelectedIndex(preselect);
    
    doTypeSelectionChanged();
  }
  
  protected BObject doSaveValue(BObject raw, Context cx)
    throws Exception
  {                         
    if(isUnavailable((BTypeConfig)getCurrentValue()))
      return value;
      
    // save all the frozen editors
    for(int i=0; i<frozenProps.length; ++i)
    {
      Property prop = frozenProps[i];
      BValue val = (BValue)frozenEditors[i].saveValue();
      value.set(prop, val, cx);        
    }
        
    // save all the editors first in case one fails       
    int configCount = configKeys.length;
    BValue[] configValues = new BValue[configCount];
    for(int i=0; i<configCount; ++i)
      configValues[i] = (BValue)configEditors[i].saveValue();
        
    // reset the value's config properties
    Property[] props = value.getDynamicPropertiesArray();
    int initialCapacity = props.length - configKeys.length;
    if (initialCapacity < 1)
      initialCapacity = 1;
    HashSet<Property> toRemove = new HashSet<>(initialCapacity);
    List<String> configKeysList = Arrays.asList(configKeys);
    for (Property prop: props)
    {
      if (!configKeysList.contains(prop.getName()))
        toRemove.add(prop);
    }

    for (Property prop: toRemove)
    {
      value.remove(prop, cx);
    }
    
    // walk thru all the config editors and save
    for(int i=0; i<configCount; ++i)
      CompUtil.setOrAdd(value, configKeys[i], configValues[i], Flags.READONLY, configFacets[i], cx);

    // update type spec
    // NOTE: do this last because that is how TypeConfig knows
    // it is safe to perform the configChanged() callback
    value.set(BTypeConfig.typeSpec, currentType.getTypeSpec(), cx);
    
    // return value
    return value;
  }       
  
////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  public void doTypeSelectionChanged()
  {
    if(isUnavailable((BTypeConfig)getCurrentValue()))
      return;

    BFoxSession foxSession = null;
    BISession session = getCurrentValueSession();
    if (session instanceof BFoxSession)
    {
      foxSession = (BFoxSession)session;
      foxSession.setThreadLocalSessionId();
    }

    try
    {
      // get the current type, and short circuit if not changed
      Item item = (Item)types.getSelectedItem();
      if (currentType == item.typeInfo) return;
      currentType = item.typeInfo; 
     
      // remove any existing editor links
      BLink[] links = getLinks();
      for(int i=0; i<links.length; ++i)
      {
        BLink link = links[i];
        if (link.getName().startsWith("d_"))
          remove(link);
      }      
      
      // update ConfigType
      BObject target = currentType.getInstance();
      BTypeConfig working = (BTypeConfig)this.value.newCopy();
      working.syncFrom(target);   

      // rebuild config sheet      
      BGridPane pane = new BGridPane(2);
      pane.setColumnGap(10);
      pane.setHalign(BHalign.left);
      Property[] props = working.getDynamicPropertiesArray();
      configKeys = new String[props.length];
      configFacets = new BFacets[props.length];
      configEditors = new BWbFieldEditor[props.length];
      for(int i=0; i<props.length; ++i)
      { 
        // get working variables                                                               
        Property prop  = props[i];             
        String name = prop.getName();
        String display = working.getDisplayName(prop, null);
        BValue value   = working.get(prop);
        BFacets facets = working.getSlotFacets(prop);   
        
        // attempt to use current value if available
        BValue reuse = this.value.get(name);
        if (isReusable(value, reuse))
        {
          value = reuse;
        }

        // add editor to pane
        BWbFieldEditor editor = addEditor(pane, "d_"+i, display, value, facets);
        
        // save in instance field
        configKeys[i] = name;      
        configFacets[i] = facets;
        configEditors[i] = editor;
      }
      content.setCenter(new BBorderPane(pane, 10, 0, 0, 20));
      
      // check if we should issue a dialog resize; this
      // is a pretty awful hack and a little disconcerting
      // but is needed for use on ProfileManager.WebProfileExt
      int dialogDepth = Integer.MAX_VALUE;
      BWidget p = getParentWidget();
      for(int i=0; p != null; ++i)
      {
        if (p instanceof BDialog) { dialogDepth = i; break; }
        p = p.getParentWidget();
      }
      if (dialogDepth < 4) ((BDialog)p).setScreenSizeToPreferredSize();

      // fire modified event
      doSetReadonly(isReadonly());
      setModified();
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (foxSession != null)
        foxSession.clearThreadLocalSessionId();
    }
  }

  private static boolean isReusable(BValue workingValue, BValue currentValue)
  {
    if (currentValue == null) { return false; }
    // special case for NCCB-38218
    Type workingType = workingValue.getType();
    Type currentType = currentValue.getType();
    if (workingType.is(BTriggerMode.TYPE) && currentType.is(BTriggerMode.TYPE))
    {
      return true;
    }
    return workingType == currentType;
  }
  
  /**
   * This field editor is only available for configuration if there is at least one concrete profile
   */
  public boolean isUnavailable(BTypeConfig typeConfig)
  {
    TypeInfo[] targetTypes = Sys.getRegistry().getConcreteTypes(value.getTargetType());
    return targetTypes.length == 0;
  }

  BWbFieldEditor addEditor(BGridPane pane, String unique, String display, BValue value, BFacets facets)
  {
    // make editor        
    BWbFieldEditor editor = BWbFieldEditor.makeFor(value, facets);        
    editor.loadValue(value, facets);
    editor.setReadonly(isReadonly());
                
    // add to config sheet
    pane.add(unique+"L", new BLabel(display));
    pane.add(unique+"E"+unique, editor);

    // setup editor links
    linkTo(unique+"LinkM"+unique, editor, BWbFieldEditor.setModified, setModified);       
    linkTo(unique+"LinkA"+unique, editor, BWbFieldEditor.actionPerformed, actionPerformed);       

    return editor;
  }
  
////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////

  static class Item
  {                
    Item(TypeInfo typeInfo) 
    { 
      this.typeInfo = typeInfo;
      this.display = typeInfo.getDisplayName(null);
      this.icon = BImage.make(typeInfo.getIcon(null));
    }              
    
    public String toString()
    {
      return display;
    }
    
    String display;
    TypeInfo typeInfo;                                
    BImage icon;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
   
   static Lexicon lex = Lexicon.make("workbench");
   static final BImage defaultIcon = BImage.make("module://icons/x16/object.png");
  
   BEdgePane content = new BEdgePane();
   Property[] frozenProps;  
   BWbFieldEditor[] frozenEditors;  
   BListDropDown types = new BListDropDown();   
   BTypeConfig value;    
   TypeInfo currentType;    
   String[] configKeys;
   BFacets[] configFacets;
   BWbFieldEditor[] configEditors;  
   boolean builtUnavailable = false;
   
}
