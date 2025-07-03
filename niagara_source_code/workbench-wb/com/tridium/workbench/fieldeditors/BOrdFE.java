/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.ArrayList;
import java.util.Arrays;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.file.BFileScheme;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.ViewQuery;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BModule;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BMenu;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.WbSys;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.ord.BIOrdChooser;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.ord.BFileOrdChooser;

/**
 * BOrdFE allows viewing and editing of a BOrd
 *
 * @author    Brian Frank on 23 Nov 01
 * @version   $Revision: 67$ $Date: 6/29/11 1:58:51 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Ord"
  )
)
/*
 columns to use to display Ord field
 */
@NiagaraProperty(
  name = "ordFieldLength",
  type = "int",
  defaultValue = "60",
  flags = Flags.TRANSIENT
)
@NiagaraAction(
  name = "textModified"
)
public class BOrdFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BOrdFE(3724671132)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ordFieldLength"

  /**
   * Slot for the {@code ordFieldLength} property.
   * columns to use to display Ord field
   * @see #getOrdFieldLength
   * @see #setOrdFieldLength
   */
  public static final Property ordFieldLength = newProperty(Flags.TRANSIENT, 60, null);

  /**
   * Get the {@code ordFieldLength} property.
   * columns to use to display Ord field
   * @see #ordFieldLength
   */
  public int getOrdFieldLength() { return getInt(ordFieldLength); }

  /**
   * Set the {@code ordFieldLength} property.
   * columns to use to display Ord field
   * @see #ordFieldLength
   */
  public void setOrdFieldLength(int v) { setInt(ordFieldLength, v, null); }

  //endregion Property "ordFieldLength"

  //region Action "textModified"

  /**
   * Slot for the {@code textModified} action.
   * @see #textModified()
   */
  public static final Action textModified = newAction(0, null);

  /**
   * Invoke the {@code textModified} action.
   * @see #textModified
   */
  public void textModified() { invoke(textModified, null, null); }

  //endregion Action "textModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BOrdFE()
  {
    loadMenu(loadTypes());

    views.setFont(Theme.widget().getTextFont());
    views.setMenuController(new ViewController());
    views.setButtonStyle(BButtonStyle.toolBar);

    button = new BButton(defaultBrowse);
    button.setMenuController(new MenuController());
    button.setButtonStyle(BButtonStyle.toolBar);
    if(button!=null)
      button.setVisible(menu.getMenuItems().length>0);
    
    linkButton = new BButton(new Hyperlink());
    linkButton.setButtonStyle(BButtonStyle.toolBar);
    
    viewQueryFE = new BViewQueryFE();

    BGridPane grid = new BGridPane(4);
    grid.setStretchColumn(0);
    grid.add(null, field);
    grid.add(null, button);
    grid.add(null, views);
    grid.add(null, linkButton);
    grid.add(null, viewQueryFE);
    setContent(grid);
    
    linkTo("lk0", field, BTextField.textModified, textModified);
    linkTo("lk1", field, BTextField.actionPerformed, actionPerformed);
    linkTo("lk2", viewQueryFE, BViewQueryFE.pluginModified, setModified);
  }

  @SuppressWarnings("unused")
  public void doTextModified()
  {
    views.setText(lex.getText("ordFE.default"));
    viewQueryFE.loadValue(BString.DEFAULT);
    selectedView = null;
    setModified();
  }
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void started()
  {
    // Cache wbshell
    shell = getWbShell();

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

  public void setEnabled(boolean v)
  {
    super.setEnabled(v);
    field.setEnabled(v);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    field.setEditable(!readonly);
    button.setEnabled(!readonly);
    views.setEnabled(!readonly);
    viewQueryFE.setReadonly(readonly);
  }
  
  /**
   * Get the agent info on the currently loaded ORD that corresponds to the
   * given agent ID (view ID).
   * @param agentId The Agent Id.
   * @return the agent info, or null if not found
   */
  private AgentInfo getAgentInfo(String agentId)
  {
    if (agentId == null) return null;
    
    AgentList agents = getAgentList();
    for (int i=0; i<agents.size(); i++)
    {
      AgentInfo info = agents.get(i);
      if (agentId.equals(info.getAgentId())) 
      {
        return info;
      }
    }
    return null;
  }
  
  /**
   * Update the selected view dropdown with the given agent info.
   *
   * @param agentInfo The Agent Info to update the selected view dropdown.
   */
  private void updateSelectedView(AgentInfo agentInfo)
  {
    views.setText(agentInfo.getDisplayName(null));
    views.setImage(BImage.make(agentInfo.getIcon(null)));
    selectedView = agentInfo.getAgentId();
  }

  protected void doLoadValue(BObject v, Context cx)
  {
    BOrd ord = (BOrd)v;
    String ordStr = ord.toString();
    field.setText(ordStr);
    field.setVisibleColumns(getOrdFieldLength());
    views.setVisible(false);
    viewQueryFE.setVisible(false);
    
    // Issue 12409. Don't show link button for Niagara Virtual case
    if (isReadonly() && allowLinkButton && cx != null && cx.getFacets().getb("nVirtual", false))
      allowLinkButton = false;

    linkButton.setVisible(allowLinkButton && (getShell() != null && !(getShell() instanceof BDialog)));

    // Try to pick the best ord chooser to use by default
    try
    {
      defaultBrowse.info = BFileOrdChooser.TYPE.getTypeInfo();

      TypeInfo info = null;
      if (cx != null)
      {
        BBoolean b = (BBoolean)cx.getFacet("chooseView");
        if (b != null && b.getBoolean())
        {
          int index = ordStr.indexOf("|view:");
          int lastIndex = ordStr.lastIndexOf("|view:");
          if (index == -1) views.setText(lex.getText("ordFE.default"));
          else if (getWbShell() != null)
          {
            field.setText(ordStr.substring(0, index));
            String queryBody = ordStr.substring(lastIndex+6);
            viewQueryFE.loadValue(BString.make(queryBody));
            
            ViewQuery vq = new ViewQuery(queryBody);
            String agentId = vq.getViewId();
            AgentInfo agentInfo = getAgentInfo(agentId);
            if (agentInfo != null)
            {
              updateSelectedView(agentInfo);
            }
          }
          views.setVisible(b.getBoolean());
          viewQueryFE.setVisible(b.getBoolean());
        }

        BString str = (BString)cx.getFacet(BFacets.TARGET_TYPE);
        if (str != null) info = Sys.getRegistry().getType(str.toString());
      }

      if ((info == null) && !ord.isNull())
      {
        try
        {
          OrdQuery[] q = ord.parse();
          if (q.length != 0)
          {
            BOrdScheme scheme = BOrdScheme.lookup(q[q.length-1].getScheme());
            info = scheme.getType().getTypeInfo();
          }
        }
        // we aren't really worried if there is a syntax exception here
        catch (SyntaxException e)
        {
          //System.out.println("WARNING: " + e.getMessage());
        }
      }

      if (info == null) return;

      AgentList agents = Sys.getRegistry().getAgents(info);
      agents = agents.filter(AgentFilter.is("workbench:IOrdChooser"));

      // Hack file schemes to default to the FileOrdChooser
      if (info.getTypeSpec().getResolvedType() == BFileScheme.TYPE || agents.size() == 0) return;
      for (TypeInfo typeInfo : list)
      {
        if (agents.get(0).getAgentType().is(typeInfo))
        {
          defaultBrowse.info = typeInfo;
          break;
        }
      }
    }
    catch (Exception e) { e.printStackTrace(); } // Just ignore it
  }

  protected BObject doSaveValue(BObject v, Context cx)
    throws Exception
  {
    return getOrdValue();
  }

  /**
   * Get the ord value from the editor without
   * changing the modified state.
   */
  protected BOrd getOrdValue()
    throws Exception
  {
    if (field.getText().length() == 0) return BOrd.NULL;

    String s = field.getText();
    String viewQueryString = viewQueryFE.saveValue().toString();
    String viewParams = BViewQueryFE.getParamString(viewQueryString);
    
    boolean hasParams = viewParams.length() > 0;

    if (views.isVisible() && selectedView != null)
    {
      s += "|view:" + selectedView;
      if (hasParams) s += "?" + viewParams;
    }
    else if (hasParams)
    {
      s += "|view:?" + viewParams;
    }
    

    BOrd ord = BOrd.make(s);
    if (!ord.isNull() && !ord.hasVariables() && isRelativize(ord))
    {
      ord = ord.relativizeToSession();

      // If the ord failed to relativize, reset to original
      if (ord.isNull() || ord.toString().length() == 0)
        ord = BOrd.make(s);
    }

    return ord;
  }

  public boolean isRelativize(BOrd currentOrd)
  {
    return isRelativize(currentOrd, getCurrentContext(), shell);
  }

  public static boolean isRelativize(BOrd currentOrd, Context cx, BWbShell shell)
  {
    // Check for facets
    if (cx != null)
    {
      BFacets facets = cx.getFacets();
      if (facets != null)
      {
        BObject val = facets.get(BFacets.ORD_RELATIVIZE);
        if (val instanceof BBoolean) return ((BBoolean)val).getBoolean();
      }
    }

    // Dont't relativize if ord is not in same station
    if (shell != null)
    {
      BOrd activeOrd = shell.getActiveOrd();
      OrdQuery[] active  = activeOrd.parse();
      OrdQuery[] current = currentOrd.parse();

      if (active.length > 1 && current.length > 1)
      {
        if (!active[0].toString().equals(current[0].toString())) return false;
        if (!active[1].toString().equals(current[1].toString())) return false;
      }
    }

    // Default is true
    return true;
  }

////////////////////////////////////////////////////////////////
// MenuController
////////////////////////////////////////////////////////////////

  AgentList getAgentList()
  {
    BObject base = getWbShell().getActiveOrdTarget().get();
    BObject obj = BOrd.make(field.getText()).resolve(base).get();

    if (obj instanceof BComponent)
      obj.asComponent().lease(); // make sure we pick up pxviews

    return WbSys.getFilteredViewList(this,
      obj,
      agent -> pxEditor == null || !agent.getAgentType().is(pxEditor));
  }

  class ViewController implements BAbstractButton.MenuController
  {
    public boolean isMenuDistinct() { return false; }
    public BMenu getMenu(BAbstractButton b)
    {
      BMenu menu = new BMenu();
      menu.add(null, new Cmd(BOrdFE.this, null));
      try
      {
        AgentList list = getAgentList();
        for (int i=0; i<list.size(); i++)
          menu.add(null, new Cmd(BOrdFE.this, list.get(i)));
      }
      catch (Exception ignore) {}
      return menu;
    }

    class Cmd extends Command
    {
      public Cmd(BWidget owner, AgentInfo agent)
      {
        super(owner, "");
        if (agent == null)
        {
          this.text = lex.getText("ordFE.default");
          this.icon = BImage.make("module://icons/x16/views/view.png");
        }
        else
        {
          this.agent = agent;
          this.text  = agent.getDisplayName(null);
          this.icon  = BImage.make(agent.getIcon(null));
        }
      }
      public String getLabel() { return text; }
      public BImage getIcon() { return icon; }
      public CommandArtifact doInvoke()
      {
        if (agent == null)
        {
          selectedView = null;
          viewQueryFE.loadValue(BString.DEFAULT);
        }
        else
        {
          String id = agent.getAgentId();
          selectedView = id;
          viewQueryFE.loadValue(BString.make(id));
        }
        
        views.setText(text);
        views.setImage(icon);
        setModified();
        return null;
      }
      AgentInfo agent;
      String text;
      BImage icon;
    }
  }

  static TypeInfo pxEditor;
  static
  {
    try { pxEditor = Sys.getType("pxEditor:PxEditor").getTypeInfo(); }
    catch (Exception ignore) {}
  } 

  /**
   * This method returns and array of {@link TypeInfo} objects that represent
   * the {@link BObject} types that may be chosen as valid ORD targets for this
   * field editor.
   *   
   * @return An array of type infos that could be chosen as valid ORD targets.
   */
  public TypeInfo[] loadTypes()
  {
    return Arrays.stream(Sys.getRegistry().getTypes(BIOrdChooser.TYPE.getTypeInfo()))
      .sorted((a, b) -> a.getDisplayName(null).compareTo(b.getDisplayName(null)))
      .toArray(TypeInfo[]::new);
  }
  
  public void loadMenu(TypeInfo[] types)
  {
    menu = new BMenu();
    for (TypeInfo typeInfo : types)
    {
      if (!typeInfo.isAbstract())
      {
        if (defaultBrowse == null) defaultBrowse = new Browse(typeInfo, true);
        menu.add(null, new Browse(typeInfo, false));
        list.add(typeInfo);
      }
    }
    if(button!=null)
      button.setVisible(menu.getMenuItems().length>0);
  }  
  
  class MenuController implements BAbstractButton.MenuController
  {
    public boolean isMenuDistinct() { return true; }
    public BMenu getMenu(BAbstractButton b) { return menu; }
  }

  protected class Browse extends Command
  {
    public Browse(TypeInfo info, boolean def)
    {
      super(BOrdFE.this, info.getDisplayName(null));
      this.info = info;
      this.def = def;
    }

    public CommandArtifact doInvoke()
    {
      BObject base = null;
      BWbShell shell = getWbShell();
      if (shell != null) base = shell.getActiveOrdTarget().get();

      BOrd temp = BOrd.make(field.getText());
      BIOrdChooser chooser = (BIOrdChooser)info.getInstance();
      BOrd ord = chooser.openChooser(BOrdFE.this, base, temp, getCurrentContext());
      if (ord != null)
      {
        if (isRelativize(ord))
          ord = ord.relativizeToSession();

        field.setText(ord.toString());
        views.setText(lex.getText("ordFE.default"));
        viewQueryFE.loadValue(BString.DEFAULT);
      }
      return null;
    }

    public String getLabel() { return def ? null : super.getLabel(); }
    public BImage getIcon() { return def ? browseIcon : null; }

    public TypeInfo info;
    public boolean def;
  }

  class Hyperlink extends Command
  {
    public Hyperlink() { super(BOrdFE.this, module, "ordFE.hyperlink"); }
    public CommandArtifact doInvoke()
    {
      try
      {
        BOrd ord = getOrdValue();
        getWbShell().hyperlink(ord);
      }
      catch (Exception e) { BDialog.error(getOwner(), "Error", "Hyperlink Failed.", e); }
      return null;
    }
    public String getLabel() { return null; }
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public void allowLinkButton(boolean allow)
  {
    this.allowLinkButton = allow;
  }

  public BTextField getTextField() { return field; }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BImage browseIcon = BImage.make("module://icons/x16/open.png");
  private static BModule module = Sys.getModuleForClass(BOrdFE.class);
  private static Lexicon lex = Lexicon.make("workbench");

  private BWbShell shell = null;

  protected BTextField field = new BTextField("", 60);
  protected BButton views = new BButton();
  protected String selectedView = null;
  protected BMenu menu;
  protected Browse defaultBrowse;
  protected BButton button;
  protected BButton linkButton;
  private boolean allowLinkButton = true;
  private ArrayList<TypeInfo> list = new ArrayList<>();
  private BViewQueryFE viewQueryFE;
}
