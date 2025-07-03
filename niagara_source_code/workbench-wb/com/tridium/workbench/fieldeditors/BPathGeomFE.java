/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.gx.BPathGeom;
import javax.baja.gx.IPathGeom;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BRadioButton;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.ToggleCommandGroup;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BPathGeomFE.
 *
 * @author    Mike Jarmy
 * @creation  07 Oct 04
 * @version   $Revision: 6$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:PathGeom"
  )
)
@NiagaraAction(
  name = "segmentSelectionChanged"
)
@NiagaraAction(
  name = "typeChanged"
)
@NiagaraAction(
  name = "field1Modified"
)
@NiagaraAction(
  name = "field2Modified"
)
@NiagaraAction(
  name = "field3Modified"
)
@NiagaraAction(
  name = "field4Modified"
)
@NiagaraAction(
  name = "field5Modified"
)
@NiagaraAction(
  name = "field6Modified"
)
public class BPathGeomFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BPathGeomFE(52283971)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "segmentSelectionChanged"

  /**
   * Slot for the {@code segmentSelectionChanged} action.
   * @see #segmentSelectionChanged()
   */
  public static final Action segmentSelectionChanged = newAction(0, null);

  /**
   * Invoke the {@code segmentSelectionChanged} action.
   * @see #segmentSelectionChanged
   */
  public void segmentSelectionChanged() { invoke(segmentSelectionChanged, null, null); }

  //endregion Action "segmentSelectionChanged"

  //region Action "typeChanged"

  /**
   * Slot for the {@code typeChanged} action.
   * @see #typeChanged()
   */
  public static final Action typeChanged = newAction(0, null);

  /**
   * Invoke the {@code typeChanged} action.
   * @see #typeChanged
   */
  public void typeChanged() { invoke(typeChanged, null, null); }

  //endregion Action "typeChanged"

  //region Action "field1Modified"

  /**
   * Slot for the {@code field1Modified} action.
   * @see #field1Modified()
   */
  public static final Action field1Modified = newAction(0, null);

  /**
   * Invoke the {@code field1Modified} action.
   * @see #field1Modified
   */
  public void field1Modified() { invoke(field1Modified, null, null); }

  //endregion Action "field1Modified"

  //region Action "field2Modified"

  /**
   * Slot for the {@code field2Modified} action.
   * @see #field2Modified()
   */
  public static final Action field2Modified = newAction(0, null);

  /**
   * Invoke the {@code field2Modified} action.
   * @see #field2Modified
   */
  public void field2Modified() { invoke(field2Modified, null, null); }

  //endregion Action "field2Modified"

  //region Action "field3Modified"

  /**
   * Slot for the {@code field3Modified} action.
   * @see #field3Modified()
   */
  public static final Action field3Modified = newAction(0, null);

  /**
   * Invoke the {@code field3Modified} action.
   * @see #field3Modified
   */
  public void field3Modified() { invoke(field3Modified, null, null); }

  //endregion Action "field3Modified"

  //region Action "field4Modified"

  /**
   * Slot for the {@code field4Modified} action.
   * @see #field4Modified()
   */
  public static final Action field4Modified = newAction(0, null);

  /**
   * Invoke the {@code field4Modified} action.
   * @see #field4Modified
   */
  public void field4Modified() { invoke(field4Modified, null, null); }

  //endregion Action "field4Modified"

  //region Action "field5Modified"

  /**
   * Slot for the {@code field5Modified} action.
   * @see #field5Modified()
   */
  public static final Action field5Modified = newAction(0, null);

  /**
   * Invoke the {@code field5Modified} action.
   * @see #field5Modified
   */
  public void field5Modified() { invoke(field5Modified, null, null); }

  //endregion Action "field5Modified"

  //region Action "field6Modified"

  /**
   * Slot for the {@code field6Modified} action.
   * @see #field6Modified()
   */
  public static final Action field6Modified = newAction(0, null);

  /**
   * Invoke the {@code field6Modified} action.
   * @see #field6Modified
   */
  public void field6Modified() { invoke(field6Modified, null, null); }

  //endregion Action "field6Modified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPathGeomFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BPathGeomFE()
  {
    ///////////////////////////////////////////
    // left side

    segments = new BList();
    segments.setMultipleSelection(false);

    BConstrainedPane segPane = new BConstrainedPane(segments);
    segPane.setMinHeight(segments.getRenderer().getItemHeight()*5);
    segPane.setMaxHeight(segments.getRenderer().getItemHeight()*5);
    segPane.setMaxWidth(200);
    segPane.setMinWidth(200);

    BGridPane buttons = new BGridPane(4);
    btnAdd    = newButton(new Add(this));
    btnRemove = newButton(new Remove(this));
    btnUp     = newButton(new Up(this));
    btnDown   = newButton(new Down(this));
    buttons.add(null, btnAdd);
    buttons.add(null, btnRemove);
    buttons.add(null, btnUp);
    buttons.add(null, btnDown);

    BEdgePane left = new BEdgePane();
    left.setCenter(segPane);
    left.setBottom(new BBorderPane(buttons, 5, 0, 0, 0));

    ///////////////////////////////////////////
    // right side

    types = new BListDropDown();
    types.getList().addItem(text("moveTo"));
    types.getList().addItem(text("lineTo"));
    types.getList().addItem(text("hLineTo"));
    types.getList().addItem(text("vLineTo"));
    types.getList().addItem(text("curveTo"));
    types.getList().addItem(text("smoothCurveTo"));
    types.getList().addItem(text("quadTo"));
    types.getList().addItem(text("smoothQuadTo"));
    types.getList().addItem(text("arcTo"));
    types.getList().addItem(text("closePath"));

    ToggleCommandGroup<ToggleCommand> group = new ToggleCommandGroup<>();
    group.add(absolute = new Absolute());
    group.add(relative = new Relative());   
    rdbAbsolute = new BRadioButton(absolute);
    rdbRelative = new BRadioButton(relative);
    rdbAbsolute.setHalign(BHalign.right);
    rdbRelative.setHalign(BHalign.right);
   
    label1 = new BLabel("???", BHalign.right);
    label2 = new BLabel("???", BHalign.right);
    label3 = new BLabel("???", BHalign.right);
    label4 = new BLabel("???", BHalign.right);
    label5 = new BLabel("???", BHalign.right);
    label6 = new BLabel("???", BHalign.right);
    field1 = new BTextField("", 10);
    field2 = new BTextField("", 10);
    field3 = new BTextField("", 10);
    field4 = new BTextField("", 10);
    field5 = new BTextField("", 10);
    field6 = new BTextField("", 10);
    chkLargeArc = new BCheckBox(new LargeArc());
    chkSweep = new BCheckBox(new Sweep());
    chkLargeArc.setHalign(BHalign.right);
    chkSweep.setHalign(BHalign.right);

    config = new BGridPane(1);
    config.setHalign(BHalign.left);
    config.setValign(BValign.top);
    BConstrainedPane configPane = configPane();
    configPane.setContent(config);

    BEdgePane right = new BEdgePane();
    right.setTop(types);
    right.setCenter(new BBorderPane(configPane, 5, 0, 0, 0));

    right.computePreferredSize();
    BConstrainedPane rightPane = new BConstrainedPane(right);
    rightPane.setMinWidth(right.getPreferredWidth());

    ///////////////////////////////////////////
    // done

    BEdgePane contents = new BEdgePane();
    contents.setLeft(left);
    contents.setRight(new BBorderPane(rightPane, 0, 0, 0, 5));
    setContent(contents);

    linkTo(null, types, BListDropDown.listActionPerformed, typeChanged);
    linkTo(null, segments, BList.selectionModified, segmentSelectionChanged);
    linkTo("lnkField1Modified", field1, BTextField.textModified, field1Modified);
    linkTo("lnkField2Modified", field2, BTextField.textModified, field2Modified);
    linkTo("lnkField3Modified", field3, BTextField.textModified, field3Modified);
    linkTo("lnkField4Modified", field4, BTextField.textModified, field4Modified);
    linkTo("lnkField5Modified", field5, BTextField.textModified, field5Modified);
    linkTo("lnkField6Modified", field6, BTextField.textModified, field6Modified);
  }

  /**
   * configPane
   */
  BConstrainedPane configPane()
  {
    unconfig(); 
    configMoveTo();
    config.computePreferredSize();
    double w = config.getPreferredWidth();
    double h = config.getPreferredHeight();

    unconfig(); 
    configLineTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configHLineTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configVLineTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configCurveTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configSmoothCurveTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configQuadTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configSmoothQuadTo();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    unconfig(); 
    configArc();
    config.computePreferredSize();
    w = Math.max(w, config.getPreferredWidth());
    h = Math.max(h, config.getPreferredHeight());

    BConstrainedPane pane = new BConstrainedPane();
    pane.setMinWidth(w);
    pane.setMinHeight(h);
    return pane;
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  /**
   * doSegmentSelectionChanged
   */
  public void doSegmentSelectionChanged()
  {
    int n = segments.getSelectedIndex();

    if (!isReadonly())
    {
      btnRemove.setEnabled(!(n == -1));
      btnUp.setEnabled(    !((n == -1) || (n == 0)));
      btnDown.setEnabled(  !((n == -1) || (n == segments.getItemCount()-1)));
    }


    if (n == -1)
    {
      unconfig();
      types.setVisible(false);
    }
    else
    {
      types.setVisible(true);
      BPathGeom.Segment s = (BPathGeom.Segment) segments.getItem(n);
      types.getList().setSelectedIndex(segmentIndex(s.getCommand()));
      editSegment(s);
    }

    relayout();
  }

  /**
   * segmentIndex
   */
  int segmentIndex(char ch)
  {
    switch(ch)
    {
      case 'Z':           return 9;
      case 'M': case 'm': return 0;
      case 'L': case 'l': return 1;
      case 'H': case 'h': return 2;
      case 'V': case 'v': return 3;
      case 'C': case 'c': return 4;
      case 'S': case 's': return 5;
      case 'Q': case 'q': return 6;
      case 'T': case 't': return 7;
      case 'A': case 'a': return 8;
      default: throw new IllegalStateException();
    }
  }

  /**
   * editSegment
   */
  void editSegment(BPathGeom.Segment s)
  {
    frozen = true;
    remove("lnkField1Modified");
    remove("lnkField2Modified");
    remove("lnkField3Modified");
    remove("lnkField4Modified");
    remove("lnkField5Modified");
    remove("lnkField6Modified");

    unconfig();

    switch(s.getCommand())
    {
      // close
      case 'Z':           
        break;

      // moveto
      case 'M': case 'm': 

        configMoveTo();

        IPathGeom.MoveTo mv = (IPathGeom.MoveTo) s;
        editAbsXY(mv.isAbsolute(), mv.getX(), mv.getY());

        break;

      // lineto
      case 'L': case 'l': 

        configLineTo();

        IPathGeom.LineTo ln = (IPathGeom.LineTo) s;
        editAbsXY(ln.isAbsolute(), ln.getX(), ln.getY());

        break;

      // hlineto
      case 'H': case 'h': 

        configHLineTo();

        IPathGeom.HLineTo hl = (IPathGeom.HLineTo) s;
        if (hl.isAbsolute()) absolute.setSelected(true);
        else relative.setSelected(true);
        field1.setText(Double.toString(hl.getX()));

        break;

      // vlineto
      case 'V': case 'v': 

        configVLineTo();

        IPathGeom.VLineTo vl = (IPathGeom.VLineTo) s;
        if (vl.isAbsolute()) absolute.setSelected(true);
        else relative.setSelected(true);
        field1.setText(Double.toString(vl.getY()));

        break;

      // curveto
      case 'C': case 'c': 
        
        configCurveTo();

        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        editAbsXY(cv.isAbsolute(), cv.getX(), cv.getY());
        field3.setText(Double.toString(cv.getX1()));
        field4.setText(Double.toString(cv.getY1()));
        field5.setText(Double.toString(cv.getX2()));
        field6.setText(Double.toString(cv.getY2()));

        break;

      // smoothcurveto
      case 'S': case 's': 

        configSmoothCurveTo();

        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        editAbsXY(sc.isAbsolute(), sc.getX(), sc.getY());
        field3.setText(Double.toString(sc.getX2()));
        field4.setText(Double.toString(sc.getY2()));

        break;

      // quadto
      case 'Q': case 'q': 

        configQuadTo();

        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        editAbsXY(qd.isAbsolute(), qd.getX(), qd.getY());
        field3.setText(Double.toString(qd.getX1()));
        field4.setText(Double.toString(qd.getY1()));

        break;

      // smoothquadto
      case 'T': case 't': 

        configSmoothQuadTo();

        IPathGeom.SmoothQuadTo sq = (IPathGeom.SmoothQuadTo) s;
        editAbsXY(sq.isAbsolute(), sq.getX(), sq.getY());

        break;

      // arc
      case 'A': case 'a': 

        configArc();

        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        editAbsXY(arc.isAbsolute(), arc.getX(), arc.getY());

        field3.setText(Double.toString(arc.getRadiusX()));
        field4.setText(Double.toString(arc.getRadiusY()));
        field5.setText(Double.toString(arc.getXAxisRotation()));
        chkLargeArc.setSelected(arc.getLargeArcFlag());
        chkSweep.setSelected(arc.getSweepFlag());

        break;

      default: throw new IllegalStateException();
    }

    linkTo("lnkField1Modified", field1, BTextField.textModified, field1Modified);
    linkTo("lnkField2Modified", field2, BTextField.textModified, field2Modified);
    linkTo("lnkField3Modified", field3, BTextField.textModified, field3Modified);
    linkTo("lnkField4Modified", field4, BTextField.textModified, field4Modified);
    linkTo("lnkField5Modified", field5, BTextField.textModified, field5Modified);
    linkTo("lnkField6Modified", field6, BTextField.textModified, field6Modified);
    frozen = false;
  }

  /**
   * unconfig
   */
  void unconfig()
  {
    config.removeAll();
    unparent(label1);
    unparent(label2);
    unparent(label3);
    unparent(label4);
    unparent(label5);
    unparent(label6);
    unparent(field1);
    unparent(field2);
    unparent(field3);
    unparent(field4);
    unparent(field5);
    unparent(field6);
    unparent(chkLargeArc);
    unparent(chkSweep);
    unparent(rdbAbsolute);
    unparent(rdbRelative);
  }

  /**
   * unparent
   */
  void unparent(BWidget w)
  {
    BWidget p = w.getParentWidget();
    if (p != null) 
      p.remove(w.getPropertyInParent());
  }

  /**
   * configMoveTo
   */
  void configMoveTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));

    label1.setText(text("x"));
    label2.setText(text("y"));
  }

  /**
   * configLineTo
   */
  void configLineTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));

    label1.setText(text("x"));
    label2.setText(text("y"));
  }

  /**
   * configHLineTo
   */
  void configHLineTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));

    label1.setText(text("x"));
  }

  /**
   * configVLineTo
   */
  void configVLineTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));

    label1.setText(text("y"));
  }

  /**
   * configCurveTo
   */
  void configCurveTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));
    config.add(null, makeGrid(label3, field3));
    config.add(null, makeGrid(label4, field4));
    config.add(null, makeGrid(label5, field5));
    config.add(null, makeGrid(label6, field6));

    label1.setText(text("x"));
    label2.setText(text("y"));
    label3.setText(text("x1"));
    label4.setText(text("y1"));
    label5.setText(text("x2"));
    label6.setText(text("y2"));
  }

  /**
   * configSmoothCurveTo
   */
  void configSmoothCurveTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));
    config.add(null, makeGrid(label3, field3));
    config.add(null, makeGrid(label4, field4));

    label1.setText(text("x"));
    label2.setText(text("y"));
    label3.setText(text("x2"));
    label4.setText(text("y2"));
  }

  /**
   * configQuadTo
   */
  void configQuadTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));
    config.add(null, makeGrid(label3, field3));
    config.add(null, makeGrid(label4, field4));

    label1.setText(text("x"));
    label2.setText(text("y"));
    label3.setText(text("x1"));
    label4.setText(text("y1"));
  }

  /**
   * configSmoothQuadTo
   */
  void configSmoothQuadTo()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));

    label1.setText(text("x"));
    label2.setText(text("y"));
  }

  /**
   * configArc
   */
  void configArc()
  {
    config.add(null, makeGrid(rdbAbsolute, rdbRelative));
    config.add(null, makeGrid(label1, field1));
    config.add(null, makeGrid(label2, field2));
    config.add(null, makeGrid(label3, field3));
    config.add(null, makeGrid(label4, field4));
    config.add(null, makeGrid(label5, field5));
    config.add(null, chkLargeArc);
    config.add(null, chkSweep);

    label1.setText(text("x"));
    label2.setText(text("y"));
    label3.setText(text("radX"));
    label4.setText(text("radY"));
    label5.setText(text("xAxisRot"));
  }

  /**
   * editAbsXY
   */
  void editAbsXY(boolean abs, double x, double y)
  {
    if (abs) absolute.setSelected(true);
    else relative.setSelected(true);
    field1.setText(Double.toString(x));
    field2.setText(Double.toString(y));
  }

  /**
   * makeGrid
   */
  BGridPane makeGrid(BWidget w1, BWidget w2)
  {
    BGridPane g = new BGridPane(2);
    g.setHalign(BHalign.left);
    g.setValign(BValign.top);
    g.add(null, w1);
    g.add(null, w2);

    return g;
  }


  /**
   * doTypeChanged
   */
  public void doTypeChanged()
  {
    BPathGeom.Segment s = makeSegment(types.getSelectedIndex());
    segments.setItem(segments.getSelectedIndex(), s);
    editSegment(s);

    setModified();
    repaint();
  }

  /**
   * makeSegment
   */
  private static BPathGeom.Segment makeSegment(int n)
  {
    switch(n)
    {
      case 0: return new IPathGeom.MoveTo(       true, 0, 0);
      case 1: return new IPathGeom.LineTo(       true, 0, 0);
      case 2: return new IPathGeom.HLineTo(      true, 0);
      case 3: return new IPathGeom.VLineTo(      true, 0);
      case 4: return new IPathGeom.CurveTo(      true, 0, 0, 0, 0, 0, 0);
      case 5: return new IPathGeom.SmoothCurveTo(true, 0, 0, 0, 0);
      case 6: return new IPathGeom.QuadTo(       true, 0, 0, 0, 0);
      case 7: return new IPathGeom.SmoothQuadTo( true, 0, 0);
      case 8: return new IPathGeom.ArcTo(        true, 0, 0, 0, true, true, 0, 0);
      case 9: return new IPathGeom.ClosePath();
      default: throw new IllegalStateException();
    }
  }

  /**
   * doField1Modified
   */
  public void doField1Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // moveto
      case 'M': case 'm': 
        IPathGeom.MoveTo mv = (IPathGeom.MoveTo) s;
        s = new IPathGeom.MoveTo(
          mv.isAbsolute(),
          atod(field1.getText()),
          mv.getY());
        break;

      // lineto
      case 'L': case 'l': 
        IPathGeom.LineTo ln = (IPathGeom.LineTo) s;
        s = new IPathGeom.LineTo(
          ln.isAbsolute(),
          atod(field1.getText()),
          ln.getY());
        break;

      // hlineto
      case 'H': case 'h': 
        IPathGeom.HLineTo hl = (IPathGeom.HLineTo) s;
        s = new IPathGeom.HLineTo(
          hl.isAbsolute(),
          atod(field1.getText()));
        break;

      // vlineto
      case 'V': case 'v': 
        IPathGeom.VLineTo vl = (IPathGeom.VLineTo) s;
        s = new IPathGeom.VLineTo(
          vl.isAbsolute(),
          atod(field1.getText()));
        break;

      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          cv.getX1(),
          cv.getY1(),
          cv.getX2(),
          cv.getY2(),
          atod(field1.getText()),
          cv.getY());
        break;

      // smoothcurveto
      case 'S': case 's': 
        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        s = new IPathGeom.SmoothCurveTo(
          sc.isAbsolute(), 
          sc.getX2(),
          sc.getY2(),
          atod(field1.getText()),
          sc.getY());
        break;

      // quadto
      case 'Q': case 'q': 
        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        s = new IPathGeom.QuadTo(
          qd.isAbsolute(), 
          qd.getX1(),
          qd.getY1(),
          atod(field1.getText()),
          qd.getY());
        break;

      // smoothquadto
      case 'T': case 't': 
        IPathGeom.SmoothQuadTo sq = (IPathGeom.SmoothQuadTo) s;
        s = new IPathGeom.SmoothQuadTo(
          sq.isAbsolute(),
          atod(field1.getText()),
          sq.getY());
        break;

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        s = new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(),
          arc.getRadiusY(),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(),
          arc.getSweepFlag(),
          atod(field1.getText()),
          arc.getY());
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * doField2Modified
   */
  public void doField2Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // moveto
      case 'M': case 'm': 
        IPathGeom.MoveTo mv = (IPathGeom.MoveTo) s;
        s = new IPathGeom.MoveTo(
          mv.isAbsolute(),
          mv.getX(),
          atod(field2.getText()));
        break;

      // lineto
      case 'L': case 'l': 
        IPathGeom.LineTo ln = (IPathGeom.LineTo) s;
        s = new IPathGeom.LineTo(
          ln.isAbsolute(),
          ln.getX(),
          atod(field2.getText()));
        break;

      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          cv.getX1(),
          cv.getY1(),
          cv.getX2(),
          cv.getY2(),
          cv.getX(),
          atod(field2.getText()));
        break;

      // smoothcurveto
      case 'S': case 's': 
        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        s = new IPathGeom.SmoothCurveTo(
          sc.isAbsolute(), 
          sc.getX2(),
          sc.getY2(),
          sc.getX(),
          atod(field2.getText()));
        break;

      // quadto
      case 'Q': case 'q': 
        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        s = new IPathGeom.QuadTo(
          qd.isAbsolute(), 
          qd.getX1(),
          qd.getY1(),
          qd.getX(),
          atod(field2.getText()));
        break;

      // smoothquadto
      case 'T': case 't': 
        IPathGeom.SmoothQuadTo sq = (IPathGeom.SmoothQuadTo) s;
        s = new IPathGeom.SmoothQuadTo(
          sq.isAbsolute(),
          sq.getX(),
          atod(field2.getText()));
        break;

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        s = new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(),
          arc.getRadiusY(),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(),
          arc.getSweepFlag(),
          arc.getX(),
          atod(field2.getText()));
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * doField3Modified
   */
  public void doField3Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          atod(field3.getText()),
          cv.getY1(),
          cv.getX2(),
          cv.getY2(),
          cv.getX(),
          cv.getY());
        break;

      // smoothcurveto
      case 'S': case 's': 
        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        s = new IPathGeom.SmoothCurveTo(
          sc.isAbsolute(), 
          atod(field3.getText()),
          sc.getY2(),
          sc.getX(),
          sc.getY());
        break;

      // quadto
      case 'Q': case 'q': 
        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        s = new IPathGeom.QuadTo(
          qd.isAbsolute(), 
          atod(field3.getText()),
          qd.getY1(),
          qd.getX(),
          qd.getY());
        break;

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        s = new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          atod(field3.getText()),
          arc.getRadiusY(),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(),
          arc.getSweepFlag(),
          arc.getX(),
          arc.getY());
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * doField4Modified
   */
  public void doField4Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          cv.getX1(),
          atod(field4.getText()),
          cv.getX2(),
          cv.getY2(),
          cv.getX(),
          cv.getY());
        break;

      // smoothcurveto
      case 'S': case 's': 
        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        s = new IPathGeom.SmoothCurveTo(
          sc.isAbsolute(), 
          sc.getX2(),
          atod(field4.getText()),
          sc.getX(),
          sc.getY());
        break;

      // quadto
      case 'Q': case 'q': 
        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        s = new IPathGeom.QuadTo(
          qd.isAbsolute(), 
          qd.getX1(),
          atod(field4.getText()),
          qd.getX(),
          qd.getY());
        break;

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        s = new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(),
          atod(field4.getText()),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(),
          arc.getSweepFlag(),
          arc.getX(),
          arc.getY());
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * doField5Modified
   */
  public void doField5Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          cv.getX1(),
          cv.getY1(),
          atod(field5.getText()),
          cv.getY2(),
          cv.getX(),
          cv.getY());
        break;

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        s = new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(),
          arc.getRadiusY(),
          atod(field5.getText()),
          arc.getLargeArcFlag(),
          arc.getSweepFlag(),
          arc.getX(),
          arc.getY());
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * doField6Modified
   */
  public void doField6Modified()
  {
    int n = segments.getSelectedIndex();
    IPathGeom.Segment s = (IPathGeom.Segment) segments.getItem(n);
    switch(s.getCommand())
    {
      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        s = new IPathGeom.CurveTo(
          cv.isAbsolute(), 
          cv.getX1(),
          cv.getY1(),
          cv.getX2(),
          atod(field6.getText()),
          cv.getX(),
          cv.getY());
        break;

      default: throw new IllegalStateException();
    }

    segments.setItem(n, s);
    setModified();
    repaint();
  }

  /**
   * atod
   */
  double atod(String text)
  {
    try
    {
      return (Double.valueOf(text)).doubleValue();
    }
    catch (NumberFormatException e)
    {
      return 0;
    }    
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * setReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    btnAdd.setEnabled(false);
    btnRemove.setEnabled(false);
    btnUp.setEnabled(false);
    btnDown.setEnabled(false);
    types.setEnabled(false);
    field1.setEnabled(false);
    field2.setEnabled(false);
  }  

  /**
   * doLoadValue
   */
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {                       
    BPathGeom p = (BPathGeom) value;

    BPathGeom.Segment[] segs = p.segments();
    segments.removeAllItems();
    for (int i = 0; i < segs.length; i++)
      segments.addItem(segs[i]);

    segments.getSelection().deselectAll();
    if (segs.length > 0) segments.getSelection().select(0);
  }

  /**
   * doSaveValue
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {         
    int len = segments.getItemCount();
    BPathGeom.Segment[] segs = new BPathGeom.Segment[len];
    for (int i = 0; i < len; i++)
      segs[i] = (BPathGeom.Segment) segments.getItem(i);

    return BPathGeom.make(segs);
  }

////////////////////////////////////////////////////////////////
// Button Commands
////////////////////////////////////////////////////////////////

  /**
   * Add
   */
  class Add
    extends Command
  {
    Add(BWidget owner) 
    { 
      super(owner, null, add, null, null);
    }

    public CommandArtifact doInvoke() 
    {
      segments.addItem(new IPathGeom.MoveTo(false, 0, 0));
      segments.getSelection().deselectAll();
      segments.getSelection().select(segments.getItemCount() - 1);

      segments.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Remove
   */
  class Remove
    extends Command
  {
    Remove(BWidget owner) 
    {
      super(owner, null, remove, null, null);
    }

    public CommandArtifact doInvoke()
    {
      int n = segments.getSelectedIndex();
      if (n == -1) throw new IllegalStateException(); 

      segments.removeItem(n);

      segments.getSelection().deselectAll();
      int len = segments.getItemCount();
      if (len > 0) segments.getSelection().select((n == len) ? n-1 : n);

      segments.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Up
   */
  class Up
    extends Command
  {
    Up(BWidget owner) 
    { 
      super(owner, null, up, null, null);
    }

    public CommandArtifact doInvoke() 
    {
      int n = segments.getSelectedIndex();
      if ((n == -1) || (n == 0))
        throw new IllegalStateException();

      Object item = segments.getItem(n);
      segments.removeItem(n);
      segments.insertItem(n-1, item);
      segments.getSelection().deselectAll();
      segments.getSelection().select(n-1);

      segments.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Down
   */
  class Down
    extends Command
  {
    Down(BWidget owner) 
    {
      super(owner, null, down, null, null);
    }

    public CommandArtifact doInvoke()
    {
      int n = segments.getSelectedIndex();
      if ((n == -1) || (n == segments.getItemCount()-1))
        throw new IllegalStateException();

      Object item = segments.getItem(n);
      segments.removeItem(n);
      segments.insertItem(n+1, item);
      segments.getSelection().deselectAll();
      segments.getSelection().select(n+1);

      segments.relayout();
      setModified();
      return null;
    }
  }

  /**
   * newButton
   */
  static BButton newButton(Command cmd)
  {
    BButton b = new BButton(cmd, true, true);
    b.setHalign(BHalign.left);
    return b;
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  /**
   * Absolute
   */
  class Absolute extends ToggleCommand
  {
    Absolute() { super(BPathGeomFE.this, text("absolute")); }

    public CommandArtifact doInvoke() throws Exception
    {
      if (!isSelected()) return null;
      if (frozen) return null;

      int n = segments.getSelectedIndex();
      segments.setItem(n, changeAbs(true, (BPathGeom.Segment) segments.getItem(n)));
      setModified();
      repaint();

      return null;
    }                
  }

  /**
   * Relative
   */
  class Relative extends ToggleCommand
  {
    Relative() { super(BPathGeomFE.this, text("relative")); }

    public CommandArtifact doInvoke() throws Exception
    {
      if (!isSelected()) return null;
      if (frozen) return null;

      int n = segments.getSelectedIndex();
      segments.setItem(n, changeAbs(false, (BPathGeom.Segment) segments.getItem(n)));
      setModified();
      repaint();

      return null;
    }                
  }

  /**
   * changeAbs
   */
  static IPathGeom.Segment changeAbs(boolean abs, BPathGeom.Segment s)
  {
    switch(s.getCommand())
    {
      // moveto
      case 'M': case 'm': 
        IPathGeom.MoveTo mv = (IPathGeom.MoveTo) s;
        return new IPathGeom.MoveTo(abs, mv.getX(), mv.getY());

      // lineto
      case 'L': case 'l': 
        IPathGeom.LineTo ln = (IPathGeom.LineTo) s;
        return new IPathGeom.LineTo(abs, ln.getX(), ln.getY());

      // hlineto
      case 'H': case 'h': 
        IPathGeom.HLineTo hl = (IPathGeom.HLineTo) s;
        return new IPathGeom.HLineTo(abs, hl.getX());

      // vlineto
      case 'V': case 'v': 
        IPathGeom.VLineTo vl = (IPathGeom.VLineTo) s;
        return new IPathGeom.VLineTo(abs, vl.getY());

      // curveto
      case 'C': case 'c': 
        IPathGeom.CurveTo cv = (IPathGeom.CurveTo) s;
        return new IPathGeom.CurveTo(abs, 
          cv.getX1(), cv.getY1(),
          cv.getX2(), cv.getY2(),
          cv.getX(),  cv.getY());

      // smoothcurveto
      case 'S': case 's': 
        IPathGeom.SmoothCurveTo sc = (IPathGeom.SmoothCurveTo) s;
        return new IPathGeom.SmoothCurveTo(abs, 
          sc.getX2(), sc.getY2(),
          sc.getX(),  sc.getY());

      // quadto
      case 'Q': case 'q': 
        IPathGeom.QuadTo qd = (IPathGeom.QuadTo) s;
        return new IPathGeom.QuadTo(abs, 
          qd.getX1(), qd.getY1(),
          qd.getX(),  qd.getY());

      // smoothquadto
      case 'T': case 't': 
        IPathGeom.SmoothQuadTo sq = (IPathGeom.SmoothQuadTo) s;
        return new IPathGeom.SmoothQuadTo(abs, sq.getX(), sq.getY());

      // arc
      case 'A': case 'a': 
        IPathGeom.ArcTo arc = (IPathGeom.ArcTo) s;
        return new IPathGeom.ArcTo(abs, 
          arc.getRadiusX(), arc.getRadiusY(),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(), arc.getSweepFlag(),
          arc.getX(), arc.getY());

      default: throw new IllegalStateException();
    }
  }

  /**
   * LargeArc
   */
  class LargeArc extends ToggleCommand
  {
    LargeArc() { super(BPathGeomFE.this, text("largeArc")); }

    public CommandArtifact doInvoke() throws Exception
    {
      if (frozen) return null;

      int n = segments.getSelectedIndex();
      IPathGeom.ArcTo arc = (IPathGeom.ArcTo) segments.getItem(n);
      segments.setItem(n, new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(), arc.getRadiusY(),
          arc.getXAxisRotation(),
          isSelected(), arc.getSweepFlag(),
          arc.getX(), arc.getY()));
      setModified();
      repaint();

      return null;
    }                
  }

  /**
   * Sweep
   */
  class Sweep extends ToggleCommand
  {
    Sweep() { super(BPathGeomFE.this, text("sweep")); }

    public CommandArtifact doInvoke() throws Exception
    {
      if (frozen) return null;

      int n = segments.getSelectedIndex();
      IPathGeom.ArcTo arc = (IPathGeom.ArcTo) segments.getItem(n);
      segments.setItem(n, new IPathGeom.ArcTo(
          arc.isAbsolute(), 
          arc.getRadiusX(), arc.getRadiusY(),
          arc.getXAxisRotation(),
          arc.getLargeArcFlag(), isSelected(), 
          arc.getX(), arc.getY()));
      setModified();
      repaint();
      return null;
    }                
  }

////////////////////////////////////////////////////////////////
// Static
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make("workbench");
  static String text(String s) { return lex.getText("pathGeomFE." + s); }
  
  static final BImage add    = BImage.make("module://icons/x16/add.png");
  static final BImage remove = BImage.make("module://icons/x16/delete.png");
  static final BImage up     = BImage.make("module://icons/x16/arrowUp.png");
  static final BImage down   = BImage.make("module://icons/x16/arrowDown.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BList segments;
  
  BButton btnAdd;
  BButton btnRemove;
  BButton btnUp;
  BButton btnDown;

  BListDropDown types;
  BLabel label1;
  BLabel label2;
  BLabel label3;
  BLabel label4;
  BLabel label5;
  BLabel label6;
  BTextField field1;
  BTextField field2;
  BTextField field3;
  BTextField field4;
  BTextField field5;
  BTextField field6;
  BCheckBox chkLargeArc;
  BCheckBox chkSweep;

  Absolute absolute;
  Relative relative;
  BRadioButton rdbAbsolute;
  BRadioButton rdbRelative;

  boolean frozen = false;
  BGridPane config;
}
