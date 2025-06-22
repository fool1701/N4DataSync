/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.baja.data.BIDataValue;
import javax.baja.data.DataTypes;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.gx.IRectGeom;
import javax.baja.gx.Insets;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.BFacets;
import javax.baja.sys.BModule;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.BToggleButton;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BWindowEvent;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.text.TextModel;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.UiEnv;
import com.tridium.ui.theme.Theme;
import com.tridium.workbench.fieldeditors.facets.BFacetsEditor;

/**
 * BFlexFacetsEditor allows viewing and editing of BFacets.
 *
 * @author Andy Frank on 29 Jul 03
 * @since Baja 1.0
 */
@NiagaraType
public class BFlexFacetsEditor
  extends BDialog
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.ui.BFlexFacetsEditor(2979906276)1.0$ @*/
/* Generated Thu Sep 30 12:51:30 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFlexFacetsEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//Factory
////////////////////////////////////////////////////////////////

 /**
  * Convenience for <code>BFacetsEditor.open(owner, facets, false)</code>.
  */
 public static BFacets open(BWidget owner, BFacets facets)
   throws Exception
 {
   return open(owner, facets, false);
 }

 /**
  * Open BFacetsEditor in dialog. Return new facets, or
  * null if dialog was cancelled.
  */
 public static BFacets open(BWidget owner, BFacets facets, boolean readonly)
   throws Exception
 {
   BFlexFacetsEditor editor = new BFlexFacetsEditor(owner, facets, readonly);
   editor.setBoundsCenteredOnOwner();
   editor.open();
   if (editor.result == BDialog.OK)
     return editor.save();
   return null;
 }

////////////////////////////////////////////////////////////////
//Constructor
////////////////////////////////////////////////////////////////

 protected BFlexFacetsEditor(BWidget owner, BFacets facets, boolean readonly)
 {
   super(owner, "Facets Editor", true);

   this.readonly = readonly;
   load(facets);

   typeList = makeTypeList();
   keyList  = makeKeyList(typeList);
   if (keyList.getList().getItemCount() > 0)
     keyList.setText(keyList.getList().getItem(0).toString());

   BButton ok = new BButton(new Ok(this));
   BToggleButton adv = new BToggleButton(new Advanced(this));
   if (readonly) adv.setEnabled(false);

   addPane = new BEdgePane();
   addPane.setTop(new BBorderPane(new BSeparator(), BBorder.none, BInsets.make(5,0,5,0)));
   addPane.setBottom(makeAdd());

   content = new BEdgePane();
   makeContent();

   BGridPane commands = new BGridPane(3);
   commands.setUniformColumnWidth(true);
   commands.setColumnAlign(BHalign.fill);
   commands.add(null, ok);
   commands.add(null, new BButton(new Cancel(this)));
   commands.add(null, adv);

   layout = new BEdgePane();
   layout.setCenter(content);
   layout.setBottom(new BBorderPane(commands, BInsets.make(15,0,0,0)));
   setContent(new BBorderPane(layout, BInsets.make(10)));

   setDefaultButton(ok);
 }

////////////////////////////////////////////////////////////////
//Helpers
////////////////////////////////////////////////////////////////

 /**
  * Make dialog content and assign to content property.
  */
 private void makeContent()
 {
   content.setLeft(makeExisting());
   if (advanced)
     content.setBottom(addPane);
   else
     content.setBottom(new BNullWidget());
 }

 /**
  * Build layout for existing facets.
  */
 private BWidget makeExisting()
 {
   BGridPane pane = new BGridPane(advanced ? 3 : 2);
   for (int i=0; i<facets.size(); i++)
   {
     Facet facet = facets.get(i);

     facet.editor = BWbFieldEditor.makeFor((BObject)facet.value);
     facet.editor.loadValue((BObject)facet.value);
     if (readonly) facet.editor.setReadonly(true);

     BButton button = new BButton(new Remove(this, i));
     button.setButtonStyle(BButtonStyle.toolBar);

     if (advanced) pane.add(null, button);
     pane.add(null, new BLabel(facet.key, Theme.widget().getBoldText()));
     pane.add(null, facet.editor);
   }
   return pane;
 }

 /**
  * Build layout for adding new facets.
  */
 private BWidget makeAdd()
 {
   BFont font = Theme.widget().getBoldText();

   BGridPane grid = new BGridPane(2);
   grid.setHalign(BHalign.left);
   grid.add(null, new BLabel("Key", font));
   grid.add(null, new BLabel("Type", font));
   grid.add(null, keyList);
   grid.add(null, typeList);

   BGridPane actions = new BGridPane(1);
   actions.add(null, new BButton(add = new Add(this)));

   BEdgePane pane = new BEdgePane();
   pane.setCenter(grid);
   pane.setRight(new BBorderPane(actions, BInsets.make(0,0,0,10)));
   return pane;
 }

 /**
  * Convenience method to make the type ListDropDown.
  */
 private BListDropDown makeTypeList()
 {
   // get type names sorted
   Type[] types = DataTypes.getTypes();
   String[] names = new String[types.length];
   for(int i=0; i<names.length; ++i)
     names[i] = types[i].getTypeName();
   SortUtil.sort(names);

   // put types into drop down list
   BListDropDown list = new BListDropDown();
   for (int i=0; i<types.length; i++)
     list.getList().addItem(names[i]);
   list.getList().setSelectedIndex(0);
   return list;
 }

 /**
  * Convenience method to make the key TextDropDown.
  */
 private BTextDropDown makeKeyList(BListDropDown types)
 {
   BTextDropDown field = new BTextDropDown();
   field.getEditor().setModel(new FacetKeyModel(types));
   addPredefinedKeys(field.getList());
   return field;
 }

 /**
  * Convenience to make border for actions.
  */
 private BBorder makeBorder()
 {
   BBrush color = BColor.black.toBrush();
   return BBorder.make(0, BBorder.NONE, color,
     1, BBorder.SOLID, color,
     1, BBorder.SOLID, color,
     1, BBorder.SOLID, color);
 }

////////////////////////////////////////////////////////////////
//Methods
////////////////////////////////////////////////////////////////

 public void load(BFacets facets) { load(facets, false); }

 public void load(BFacets facets, boolean readonly)
 {
   // Nuke any existing content
   this.facets.clear();

   if (facets != null)
   {
     String[] s = facets.list();
     for (int i=0; i<s.length; i++)
       this.facets.add(new Facet(s[i],
         (BIDataValue)facets.getFacet(s[i])));
   }
 }

 public BFacets save() throws Exception
 {
   String[] keys = new String[facets.size()];
   BIDataValue[] values = new BIDataValue[facets.size()];

   for (int i=0; i<facets.size(); i++)
   {
     Facet facet = facets.get(i);
     keys[i] = facet.key;
     values[i] = (BIDataValue)facet.editor.saveValue();
   }

   return BFacets.make(keys, values);
 }

////////////////////////////////////////////////////////////////
//Commands
////////////////////////////////////////////////////////////////

 class Ok extends Command
 {
   public Ok(BWidget owner) { super(owner, "Ok"); }
   public CommandArtifact doInvoke()
   {
     result = BDialog.OK;
     close();
     return null;
   }
 }

 class Cancel extends Command
 {
   public Cancel(BWidget owner) { super(owner, "Cancel"); }
   public CommandArtifact doInvoke()
   {
     result = BDialog.CANCEL;
     close();
     return null;
   }
 }

 class Advanced extends ToggleCommand
 {
   public Advanced(BWidget owner) { super(owner, "Advanced"); }
   public CommandArtifact doInvoke()
   {
     advanced = !advanced;
     makeContent();
     layout.setCenter(content);
     updateDialog(getOwner());
     return null;
   }
 }

 class Add extends Command
 {
   public Add(BWidget owner) { super(owner, MODULE, "fieldSheet.facets.add"); }
   public CommandArtifact doInvoke()
   {
     String key = keyList.getText();
     String name = (String)typeList.getSelectedItem();
     BTypeSpec typeSpec = BTypeSpec.make("baja", name);
     BIDataValue value = (BIDataValue)typeSpec.getInstance();

     if (!SlotPath.isValidName(key))
     {
       BDialog.error(getOwner(), lex.getText("fieldSheet.facets.invalidKeyName"));
       return null;
     }

     facets.add(new Facet(key, value));
     content.setLeft(makeExisting());
     updateDialog(getOwner());
     return null;
   }
 }

 class Remove extends Command
 {
   public Remove(BWidget owner, int index)
   {
     super(owner, "");
     this.index = index;
   }
   public BImage getIcon() { return removeIcon; }
   public CommandArtifact doInvoke()
   {
     facets.remove(index);
     content.setLeft(makeExisting());
     updateDialog(getOwner());
     return null;
   }
   int index = 0;
 }

 /**
  * Update the dialog after a model change.
  */
 private void updateDialog(BWidget owner)
 {
   BWidget parent = owner;
   while (!(parent instanceof BFlexFacetsEditor))
     parent = parent.getParentWidget();
   //((BDialog)parent).setBoundsCenteredOnOwner();
   ((BFlexFacetsEditor)parent).resize();
 }

////////////////////////////////////////////////////////////////
//Facet
////////////////////////////////////////////////////////////////

 class Facet
 {
   public Facet(String key, BIDataValue value)
   {
     this.key = key;
     this.value = value;
   }
   public String key;
   public BIDataValue value;
   public BWbFieldEditor editor;
 }

////////////////////////////////////////////////////////////////
//FacetKeyModel
////////////////////////////////////////////////////////////////

 static class FacetKeyModel
   extends TextModel
 {
   FacetKeyModel(BListDropDown list)
   {
     this.list = list;
   }

   public void textModified()
   {
     super.textModified();
     String sym = getPredefinedKeys().getProperty(getText());
     if (sym != null)
     {
       Type type = DataTypes.getBySymbol(sym.charAt(0));
       if (type != null)
         list.setSelectedItem(type.getTypeName());
       list.relayout();
     }
   }

   BListDropDown list;
 }

////////////////////////////////////////////////////////////////
//Predefined
////////////////////////////////////////////////////////////////

  public static Properties getPredefinedKeys()
  {
    if (predefinedKeys == null)
    {
      predefinedKeys = new Properties();
      try
      {
        BModule module = (BModule)BOrd.make("module://flexSerial").get();
        BIFile file = module.findFile(new FilePath("/com/tridium/flexSerial/properties/facetKeys.prop"));
        if (file == null)
        {
          System.err.println("SEVERE [flexSerial] Could not resolve 'facetKeys.prop' file");
        }
        else
        {
          InputStream in = file.getInputStream();
          predefinedKeys.load(in);
          in.close();
        }
      }
      catch (Exception e)
      {
        System.err.println("SEVERE [flexSerial] Exception occurred while loading 'facetKeys.prop' file");
        e.printStackTrace();
      }
    }
    return predefinedKeys;
  }

 public static void addPredefinedKeys(BList list)
 {
   Properties props = getPredefinedKeys();
   String[] keys = props.keySet().toArray(new String[props.size()]);
   SortUtil.sort(keys);
   for(int i=0; i<keys.length; ++i)
     list.addItem(keys[i]);
 }

 // key names matched to single char data type code
 private static Properties predefinedKeys;

////////////////////////////////////////////////////////////////
//Layout
////////////////////////////////////////////////////////////////

 /**
  * Resize dialog to preferred size.
  */
 private void resize()
 {
   Insets insets = UiEnv.get().getWindowInsets(this);
   computePreferredSize();
   double w = getPreferredWidth() + insets.left + insets.right;
   double h = getPreferredHeight() + insets.top + insets.bottom;
   IRectGeom cur = getScreenBounds();
   setScreenBounds(cur.x(), cur.y(), w, h);
 }

////////////////////////////////////////////////////////////////
//WindowEvents
////////////////////////////////////////////////////////////////

 public void windowClosing(BWindowEvent event)
 {
   close();
 }

////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////

 static BModule MODULE = Sys.getModuleForClass(BFacetsEditor.class);
 static Lexicon lex = Lexicon.make("workbench");
 static BImage removeIcon = BImage.make("module://icons/x16/delete.png");

 ArrayList<Facet> facets = new ArrayList<>();

 BListDropDown typeList;
 BTextDropDown keyList;
 BEdgePane layout;
 BEdgePane content;
 BEdgePane addPane;

 Command add;
 Command modify;
 Command remove;

 boolean readonly = false;
 boolean advanced = false;
 private int result = BDialog.CANCEL;
}
