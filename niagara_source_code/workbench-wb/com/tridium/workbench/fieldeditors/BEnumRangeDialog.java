/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.ArrayList;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.BString;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BDialog;
import javax.baja.ui.BFrame;
import javax.baja.ui.BLabel;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BOrientation;
import javax.baja.ui.event.BWindowEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.table.BTable;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.ui.table.TableModel;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BTypeSpec;

/**
 * BEnumRangeDialog allows editing of the name/ordinal
 * pairs in a BEnumRange.
 *
 * @author    Brian Frank       
 * @creation  13 Dec 01
 * @version   $Revision: 20$ $Date: 11/22/06 4:43:58 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "fixedChanged"
)
@NiagaraAction(
  name = "selectionChanged"
)
@NiagaraAction(
  name = "ordinalChanged"
)
@NiagaraAction(
  name = "ordinalAction"
)
@NiagaraAction(
  name = "tagChanged"
)
@NiagaraAction(
  name = "tagAction"
)
@NiagaraAction(
  name = "addPressed"
)
@NiagaraAction(
  name = "modifyPressed"
)
@NiagaraAction(
  name = "removePressed"
)
@NiagaraAction(
  name = "okPressed"
)
@NiagaraAction(
  name = "cancelPressed"
)
public class BEnumRangeDialog
  extends BDialog
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BEnumRangeDialog(3598406375)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "fixedChanged"

  /**
   * Slot for the {@code fixedChanged} action.
   * @see #fixedChanged()
   */
  public static final Action fixedChanged = newAction(0, null);

  /**
   * Invoke the {@code fixedChanged} action.
   * @see #fixedChanged
   */
  public void fixedChanged() { invoke(fixedChanged, null, null); }

  //endregion Action "fixedChanged"

  //region Action "selectionChanged"

  /**
   * Slot for the {@code selectionChanged} action.
   * @see #selectionChanged()
   */
  public static final Action selectionChanged = newAction(0, null);

  /**
   * Invoke the {@code selectionChanged} action.
   * @see #selectionChanged
   */
  public void selectionChanged() { invoke(selectionChanged, null, null); }

  //endregion Action "selectionChanged"

  //region Action "ordinalChanged"

  /**
   * Slot for the {@code ordinalChanged} action.
   * @see #ordinalChanged()
   */
  public static final Action ordinalChanged = newAction(0, null);

  /**
   * Invoke the {@code ordinalChanged} action.
   * @see #ordinalChanged
   */
  public void ordinalChanged() { invoke(ordinalChanged, null, null); }

  //endregion Action "ordinalChanged"

  //region Action "ordinalAction"

  /**
   * Slot for the {@code ordinalAction} action.
   * @see #ordinalAction()
   */
  public static final Action ordinalAction = newAction(0, null);

  /**
   * Invoke the {@code ordinalAction} action.
   * @see #ordinalAction
   */
  public void ordinalAction() { invoke(ordinalAction, null, null); }

  //endregion Action "ordinalAction"

  //region Action "tagChanged"

  /**
   * Slot for the {@code tagChanged} action.
   * @see #tagChanged()
   */
  public static final Action tagChanged = newAction(0, null);

  /**
   * Invoke the {@code tagChanged} action.
   * @see #tagChanged
   */
  public void tagChanged() { invoke(tagChanged, null, null); }

  //endregion Action "tagChanged"

  //region Action "tagAction"

  /**
   * Slot for the {@code tagAction} action.
   * @see #tagAction()
   */
  public static final Action tagAction = newAction(0, null);

  /**
   * Invoke the {@code tagAction} action.
   * @see #tagAction
   */
  public void tagAction() { invoke(tagAction, null, null); }

  //endregion Action "tagAction"

  //region Action "addPressed"

  /**
   * Slot for the {@code addPressed} action.
   * @see #addPressed()
   */
  public static final Action addPressed = newAction(0, null);

  /**
   * Invoke the {@code addPressed} action.
   * @see #addPressed
   */
  public void addPressed() { invoke(addPressed, null, null); }

  //endregion Action "addPressed"

  //region Action "modifyPressed"

  /**
   * Slot for the {@code modifyPressed} action.
   * @see #modifyPressed()
   */
  public static final Action modifyPressed = newAction(0, null);

  /**
   * Invoke the {@code modifyPressed} action.
   * @see #modifyPressed
   */
  public void modifyPressed() { invoke(modifyPressed, null, null); }

  //endregion Action "modifyPressed"

  //region Action "removePressed"

  /**
   * Slot for the {@code removePressed} action.
   * @see #removePressed()
   */
  public static final Action removePressed = newAction(0, null);

  /**
   * Invoke the {@code removePressed} action.
   * @see #removePressed
   */
  public void removePressed() { invoke(removePressed, null, null); }

  //endregion Action "removePressed"

  //region Action "okPressed"

  /**
   * Slot for the {@code okPressed} action.
   * @see #okPressed()
   */
  public static final Action okPressed = newAction(0, null);

  /**
   * Invoke the {@code okPressed} action.
   * @see #okPressed
   */
  public void okPressed() { invoke(okPressed, null, null); }

  //endregion Action "okPressed"

  //region Action "cancelPressed"

  /**
   * Slot for the {@code cancelPressed} action.
   * @see #cancelPressed()
   */
  public static final Action cancelPressed = newAction(0, null);

  /**
   * Invoke the {@code cancelPressed} action.
   * @see #cancelPressed
   */
  public void cancelPressed() { invoke(cancelPressed, null, null); }

  //endregion Action "cancelPressed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumRangeDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  public static BEnumRange open(BWidget owner, BEnumRange value, boolean enabled)
  {
    BEnumRangeDialog dialog = new BEnumRangeDialog(owner, value, enabled);
    dialog.setBoundsCenteredOnOwner();
    dialog.open();
    return dialog.result;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  private BEnumRangeDialog(BWidget owner, BEnumRange value, boolean enabled)
  {    
    super(owner, dialogTitle, true);
    
    this.orig = value;
    
    // build list of pairs 
    int[] ordinals = value.getOrdinals();
    for(int i=0; i<ordinals.length; ++i)
    {
      int o = ordinals[i];
      String t = value.getTag(o);
      String d = value.getDisplayTag(o, null);
      boolean fixed = value.isFrozenOrdinal(o);
      pairs.add(new Pair(o, t, d, fixed));
    }
    
    // initialize enum field
    Type frozenType = value.getFrozenType();
    if (frozenType != null)
    {
      fixed = (BFrozenEnum)frozenType.getInstance();
      useFixed.setSelected(fixed != null);
      fixedField.setText(fixed.getType().toString());
    }                                     
    
    // build options pane
    options.add(null, new BLabel(UiLexicon.bajaui().getText("enumRangeDialog.lexicon")));
    options.add(null, lexicon);
    lexicon.setText(orig.getOptions().gets("lexicon", "").trim());
    
    // build history combo
    updateFixedList();
    
    // sort by ordinal
    sort();
    
    // initialize widgets
    table.setMultipleSelection(false);
    table.setCellRenderer(new CellRenderer());
    enumValidLabel.setForeground(BColor.red.toBrush());
    
    // disable if necessary
    this.enabled = enabled;
    if (!enabled)
    {
      ordinalField.setEditable(false);
      tagField.setEditable(false);
      useFixed.setEnabled(false);
      fixedField.setEnabled(false);
    }
    
    // all buttons start off disabled
    add.setEnabled(false);
    modify.setEnabled(false);
    remove.setEnabled(false);
    
    // add widgets to myself (I do layout)
    add("useFixed", useFixed);
    add("fixedField", fixedField);
    add("enumInvalid", enumValidLabel);
    add("table", table);
    add("ordinalField", ordinalField);
    add("tagField", tagField);
    add("add", add);
    add("modify", modify);
    add("remove", remove);
    add("sep1", sep1);
    add("options", options);
    add("sep2", sep2);
    add("ok", ok);
    add("cancel", cancel);
    
    // setup links
    linkTo("lk0", table, BTable.selectionModified, selectionChanged);
    linkTo("lk1", ordinalField, BTextField.textModified, ordinalChanged);
    linkTo("lk2", ordinalField, BTextField.actionPerformed, ordinalAction);
    linkTo("lk3", tagField, BTextField.textModified, tagChanged);
    linkTo("lk4", tagField, BTextField.actionPerformed, tagAction);
    linkTo("lk5", add, BButton.actionPerformed, addPressed);
    linkTo("lk6", modify, BButton.actionPerformed, modifyPressed);
    linkTo("lk7", remove, BButton.actionPerformed, removePressed);
    linkTo("lk9", useFixed, BButton.actionPerformed, fixedChanged);
    linkTo("lkA", fixedField, BTextDropDown.valueModified, fixedChanged);
    linkTo("lkC", ok, BButton.actionPerformed, okPressed);
    linkTo("lkD", cancel, BButton.actionPerformed, cancelPressed);
    
    // check to get all the enable/disable states right
    check();
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    setPreferredSize(350, 400);
  }

  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();
    
    // ---- top down ----
    
    // use fixed
    useFixed.computePreferredSize();
    double uw = useFixed.getPreferredWidth();
    double uh = useFixed.getPreferredHeight();
    useFixed.setBounds(space, space, uw, uh);
    
    // fixed combo
    fixedField.computePreferredSize();
    double fy = space + uh + space;
    double fh = fixedField.getPreferredHeight();
    double fw = 2*w/3;
    fixedField.setBounds(space, fy, fw, fh);
    enumValidLabel.setBounds(space+fw+space, fy, 25, fh);
    
    double toph = space + uh + space + fh + space;
    
    // ---- botton up ----
    
    // ok/cancel buttons
    ok.computePreferredSize();
    cancel.computePreferredSize();
    double ocw = Math.max(ok.getPreferredWidth(), cancel.getPreferredWidth());
    double och = ok.getPreferredHeight();
    double ocy = h - space - och;
    double okx = (w - (ocw + space + ocw))/2;
    ok.setBounds(okx, ocy, ocw, och);
    cancel.setBounds(okx + ocw + space, ocy, ocw, och);
    
    // separator
    sep1.setBounds(space, ocy-space*3, w-space-space, 4);
    
    // options
    options.computePreferredSize();
    double qw = options.getPreferredWidth();
    double qh = options.getPreferredHeight();
    options.setBounds(space, sep1.getY()-space*2-qh, qw, qh);

    // separator
    sep2.setBounds(space, options.getY()-space*2, w-space-space, 4);

    // add/modify/remove buttons
    add.computePreferredSize();
    double bh = add.getPreferredHeight();
    double bw = (w - 4*space)/3;
    double by = sep2.getY() - space*2 - bh;
    add.setBounds(space, by, bw, bh);
    modify.setBounds(space+(bw+space), by, bw, bh);
    remove.setBounds(space+2*(bw+space), by, bw, bh);

    // text field height
    ordinalField.computePreferredSize();
    double th = ordinalField.getPreferredHeight();
    double ty = by - space - th;
    double ow = w/4;  // ordinal width
    double nw = w - ow - 3*space; // tag (name) width
    ordinalField.setBounds(space, ty, ow, th);
    tagField.setBounds(space+ow+space, ty, nw, th);

    // ---- center ----
    
    // table gets left overs
    table.setBounds(space, toph, w-space-space, ty-space-toph-space);
  }

////////////////////////////////////////////////////////////////
// Fixed Enum
////////////////////////////////////////////////////////////////
  
  /**
   * Update the fixed enum.
   */
  void updateFixed(BFrozenEnum fixed)
  {
    this.fixed = fixed;
    
    // remove fixed pairs
    if (fixed == null)
    {
      Pair[] p = pairs.toArray(new Pair[pairs.size()]);
      ArrayList<Pair> temp = new ArrayList<>();
      for(int i=0; i<p.length; ++i)
        if (!p[i].fixed) temp.add(p[i]);
      pairs = temp;
    }
    
    // add new pairs
    else
    {
      ArrayList<Pair> temp = new ArrayList<>();
      
      // add fixed                               
      BEnumRange range = fixed.getRange();
      int[] o = range.getOrdinals();
      for(int i=0; i<o.length; ++i)
        temp.add(new Pair(o[i], range.getTag(o[i]), range.getDisplayTag(o[i], null), true));
      
      // add dynamics which aren't dups in fixed
      Pair[] p = pairs.toArray(new Pair[pairs.size()]);
      for(int i=0; i<p.length; ++i)
      {
        Pair x = p[i];
        if (x.fixed) continue;
        if (range.isOrdinal(x.ordinal)) continue;
        if (range.isTag(x.tag)) continue;
        temp.add(p[i]);
      }
      
      pairs = temp;
    }

    sort();
    model.updateTable();
    check();
  }
  
  /**
   * Change the enum enumInvalidLabel flag.
   */
  void setEnumValid(boolean valid)
  {                        
    enumValid = valid;            
    
    boolean ok = valid || fixedField.getText().length() == 0;
    enumValidLabel.setText(ok ? "" : "***");
    
    enumValidLabel.relayout();
    repaint();
  }
  
  /**
   * Update the combo box with the list of all known enums.
   */
  void updateFixedList()
  {                                       
    TypeInfo[] types = Sys.getRegistry().getConcreteTypes(BFrozenEnum.TYPE.getTypeInfo());
    SortUtil.sort(types);
    
    fixedField.getList().removeAllItems();
    for(int i=0; i<types.length; ++i)
      fixedField.getList().addItem(types[i]);
  }

////////////////////////////////////////////////////////////////
// Save
////////////////////////////////////////////////////////////////

  public BEnumRange save()
  {
    // get dynamic pairs
    ArrayList<Pair> dynamic = new ArrayList<>();
    for(int i=0; i<pairs.size(); ++i)
    {
      Pair p = pairs.get(i);
      if (!p.fixed) dynamic.add(p);
    }
    
    // build dynamic
    int count = dynamic.size();
    int[] o = new int[count];
    String[] t = new String[count];
    for(int i=0; i<count; ++i)
    {
      Pair pair = dynamic.get(i);
      o[i] = pair.ordinal;
      t[i] = pair.tag;
    }
    
    // options
    BFacets newOptions;
    String lex = lexicon.getText().trim();
    if (lex.length() == 0)
      newOptions = BFacets.makeRemove(orig.getOptions(), "lexicon");
    else
      newOptions = BFacets.make(orig.getOptions(), "lexicon", BString.make(lex));
    
    return BEnumRange.make(toFrozenType(fixed), o, t, count, newOptions);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////  

  public void windowClosing(BWindowEvent event) { doCancelPressed(); }
    
  public void doCancelPressed() { result = null; close(); }

  public void doOkPressed()  { result = save(); close(); }

  public void doSelectionChanged()
  {
    if (!enabled) return;
    int i = table.getSelection().getRow();
    if (i >= 0 && i<pairs.size())
    {
      Pair pair = pairs.get(i);
      ordinalField.setText(String.valueOf(pair.ordinal));
      tagField.setText(pair.display);
    }
    check();
  }

  public void doFixedChanged() 
  {                             
    BFrozenEnum fixed = null;
    try
    {
      String text = fixedField.getText();
      BTypeSpec spec = BTypeSpec.make(text);
      Type type = spec.getResolvedType();
      BFrozenEnum frozen = (BFrozenEnum)type.getInstance();
      if (useFixed.isSelected()) fixed = frozen;
      setEnumValid(true);
    }
    catch(Throwable e)
    {
      setEnumValid(false);
    }
    updateFixed(fixed);
    check();
  }

  public void doOrdinalChanged() { check(); }

  public void doTagChanged() { check(); }
  
  public void doOrdinalAction() { commitCurrent(); }

  public void doTagAction() 
  { 
    commitCurrent(); 
    ordinalField.requestFocus();
  }

  public void doAddPressed()
  {
    if (!enabled) return;
    int ordinal = Integer.parseInt(ordinalField.getText());
    String display = tagField.getText();
    String tag = SlotPath.escape(display);
    if (!checkTag(tag)) return;
    pairs.add(new Pair(ordinal, tag, display, false));
    sort();
    model.updateTable();
    check();    
    table.getSelection().deselectAll();
  }

  public void doModifyPressed()
  {
    if (!enabled) return;
    String display = tagField.getText();
    String tag = SlotPath.escape(display);
    if (!checkTag(tag)) return;
    Pair pair = pairs.get(ordinalToIndex());
    pair.tag = tag;
    pair.display = display;
    sort();
    model.updateTable();
    check();
  }

  public void doRemovePressed()
  {
    if (!enabled) return;
    pairs.remove(table.getSelection().getRow());
    sort();
    model.updateTable();
    check();
  }
  
  private void commitCurrent()
  {
    if (add.isEnabled()) doAddPressed();
    else if (modify.isEnabled()) doModifyPressed();
  }

    
  private boolean checkTag(String tag)
  {
    if (containsTag(tag))
    {
      Object[] args = { tag };
      String title = UiLexicon.bajaui().getText("dialog.error");
      String msg = UiLexicon.bajaui().getText("enumRangeDialog.duplicateTag", args);
      BDialog.error(this, title, msg);
      return false;
    }
    
    /* escape tag
    if (!SlotPath.isValidName(tag))
    {
      Object[] args = { tag };
      String title = UiLexicon.bajaui().getText("dialog.error");
      String msg = UiLexicon.bajaui().getText("enumRangeDialog.invalidName", args);
      BDialog.error(this, title, msg);
      return false;
    }         
    */
    
    return true;
  }
  
  private void check()
  {
    if (!enabled) return;
    
    int ix = ordinalToIndex();
    Pair pair = null;
    if (ix  >= 0) pair = pairs.get(ix);

    int sel = table.getSelection().getRow();

    add.setEnabled(ix == NEW);
    modify.setEnabled(pair != null && !pair.fixed && !pair.tag.equals(tagField.getText()));
    remove.setEnabled(pair != null && !pair.fixed);
    fixedField.setEnabled(useFixed.isSelected());
  }                  
  
  static Type toFrozenType(BFrozenEnum fixed)
  {
    if (fixed == null) return null;
    return fixed.getType();
  }

////////////////////////////////////////////////////////////////
// Table Model
////////////////////////////////////////////////////////////////  

  void sort()
  {
    Pair[] p = pairs.toArray(new Pair[pairs.size()]);
    SortUtil.sort(p);
    pairs.clear();
    for(int i=0; i<p.length; ++i) pairs.add(p[i]); 
  }
  
  boolean containsTag(String tag)
  {
    for(int i=0; i<pairs.size(); ++i)
    {
      Pair p = pairs.get(i);
      if (p.tag.equals(tag)) return true;
    }
    return false;
  }

  int ordinalToIndex()
  {
    try
    {
      int ordinal = Integer.parseInt(ordinalField.getText());
      for(int i=0; i<pairs.size(); ++i)
      {
        Pair pair = pairs.get(i);
        if (ordinal == pair.ordinal) return i;
      }
      return NEW;
    }
    catch(Exception e)
    {
      return ERR;
    }
  }

  class Model extends TableModel
  {
    public int getColumnCount() { return 3; }
    
    public int getRowCount() { return pairs.size(); }
    
    public String getColumnName(int col)
    {
      switch(col)
      {
        case 0: return "Ordinal";
        case 1: return "Tag";
        case 2: return "Display";
        default: throw new IllegalStateException();
      }
    }
    
    public Object getValueAt(int row, int col)
    {
      Pair pair = pairs.get(row);
      switch(col)
      {
        case 0: return String.valueOf(pair.ordinal);
        case 1: return pair.tag;
        case 2: return pair.display;
        default: throw new IllegalStateException();
      }
    }
  }

////////////////////////////////////////////////////////////////
// CellRenderer
////////////////////////////////////////////////////////////////

  class CellRenderer
    extends TableCellRenderer
  {
    public BBrush getForeground(Cell cell)
    {    
      Pair pair = pairs.get(cell.row);
      return pair.fixed ? BColor.blue.toBrush() : BColor.black.toBrush();
    }
  }

////////////////////////////////////////////////////////////////
// Pair
////////////////////////////////////////////////////////////////

  static class Pair
    implements Comparable<Pair>
  {
    Pair(int o, String t, String d, boolean f) 
      { ordinal = o; tag = t;  display = d; fixed = f; }

    public int compareTo(Pair o)
    {
      int p = o.ordinal;
      if (ordinal == p) return 0;
      return ordinal < p ? -1 : 1;
    }

    public int hashCode(){return System.identityHashCode(this);}
    public boolean equals(Pair o)
    {
      return ordinal == o.ordinal;
    }
    
    int ordinal;
    String tag;          
    String display;
    boolean fixed;
  }

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////  

  public static void main(String[] args)
  {
    try
    {
      int[] o = {2, 3, 4};
      String[] t = {"two", "three", "four"};
      BFrozenEnum fixed = BOrientation.horizontal;
      BEnumRange range = BEnumRange.make(toFrozenType(fixed), o, t);

      BEnumRangeDialog x = new BEnumRangeDialog(new BFrame(), range, true);
      x.setScreenBounds(100, 100, 350, 400);
      x.open();
      if (x.result == null) { System.out.println("canceled"); }
      else { System.out.println(x.result.encodeToString()); }
      System.exit(0);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.exit(-1);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static String dialogTitle = UiLexicon.bajaui().getText("enumRangeDialog.title");

  static final int space = 4;
  static final int ERR = -1;
  static final int NEW = -2;
      
  ArrayList<Pair> pairs = new ArrayList<>();
  
  BEnumRange orig;
  BEnumRange result;
  BFrozenEnum fixed;
  BCheckBox useFixed = new BCheckBox(UiLexicon.bajaui().getText("enumRangeDialog.useFrozen"));
  BTextDropDown fixedField = new BTextDropDown("", 20, true);
  boolean enumValid;
  BLabel enumValidLabel = new BLabel("");
  boolean enabled;
  TableModel model = new Model();
  BTable table = new BTable(model);
  BTextField ordinalField = new BTextField("", 5);
  BTextField tagField = new BTextField("", 20);
  BButton add = new BButton(UiLexicon.bajaui().getText("action.add"));
  BButton modify = new BButton(UiLexicon.bajaui().getText("action.modify"));
  BButton remove = new BButton(UiLexicon.bajaui().getText("action.remove"));
  BSeparator sep1 = new BSeparator(BOrientation.horizontal);
  BGridPane options = new BGridPane(2);
  BTextField lexicon = new BTextField("", 20);
  BSeparator sep2 = new BSeparator(BOrientation.horizontal);
  BButton ok = new BButton(UiLexicon.bajaui().getText("action.ok"));
  BButton cancel = new BButton(UiLexicon.bajaui().getText("action.cancel"));
  
}
