/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.io.InputStream;

import javax.baja.file.BIFile;
import javax.baja.file.types.text.BXmlFile;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BScrollBarPolicy;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BWidgetEvent;
import javax.baja.ui.file.ExtFileFilter;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BScrollPane;
import javax.baja.ui.text.Position;
import javax.baja.util.Lexicon;
import javax.baja.xml.XElem;
import javax.baja.xml.XParser;
import javax.baja.xml.XText;
import javax.baja.xml.XWriter;

import com.tridium.nrio.NrioException;
import com.tridium.nrio.conv.BNrioTabularThermistorConversion;
import com.tridium.nrio.conv.BNrioTabularThermistorConversion.XYPoint;
import com.tridium.ui.theme.Theme;
import com.tridium.workbench.ord.BFileOrdChooser;

/**
 * @author    Bill Smith
 * @creation  15 Feb 2005
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */

@NiagaraType
@NiagaraAction(
  name = "okButtonPressed",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
@NiagaraAction(
  name = "cancelButtonPressed",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
@NiagaraAction(
  name = "setModified",
  parameterType = "BWidgetEvent",
  defaultValue = "new BWidgetEvent()"
)
public class BNrioTabularThermistorDialog
  extends BDialog
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioTabularThermistorDialog(755133510)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "okButtonPressed"

  /**
   * Slot for the {@code okButtonPressed} action.
   * @see #okButtonPressed(BWidgetEvent parameter)
   */
  public static final Action okButtonPressed = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code okButtonPressed} action.
   * @see #okButtonPressed
   */
  public void okButtonPressed(BWidgetEvent parameter) { invoke(okButtonPressed, parameter, null); }

  //endregion Action "okButtonPressed"

  //region Action "cancelButtonPressed"

  /**
   * Slot for the {@code cancelButtonPressed} action.
   * @see #cancelButtonPressed(BWidgetEvent parameter)
   */
  public static final Action cancelButtonPressed = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code cancelButtonPressed} action.
   * @see #cancelButtonPressed
   */
  public void cancelButtonPressed(BWidgetEvent parameter) { invoke(cancelButtonPressed, parameter, null); }

  //endregion Action "cancelButtonPressed"

  //region Action "setModified"

  /**
   * Slot for the {@code setModified} action.
   * @see #setModified(BWidgetEvent parameter)
   */
  public static final Action setModified = newAction(0, new BWidgetEvent(), null);

  /**
   * Invoke the {@code setModified} action.
   * @see #setModified
   */
  public void setModified(BWidgetEvent parameter) { invoke(setModified, parameter, null); }

  //endregion Action "setModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioTabularThermistorDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  public static final BNrioTabularThermistorConversion show(BWidget owner, BNrioTabularThermistorConversion src)
  {
    BNrioTabularThermistorDialog dialog = new BNrioTabularThermistorDialog(owner, src);

    dialog.setBoundsCenteredOnOwner();
    dialog.setResizable(false);
    dialog.open();

    return dialog.getConversion();
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  private BNrioTabularThermistorDialog(BWidget parent, BNrioTabularThermistorConversion src)
  {
    super(parent, nrioLex.get("tabularDialog.title"), true);

    // description box

    BLabel descLabel = new BLabel(nrioLex.get("tabularDialog.descriptionLabel"));
    desc = new BTextField("");

    desc.setText(src.getDescription());
    desc.getSelection().deselect();
    desc.moveCaretPosition(new Position(0,0));
    linkTo(null, desc, BTextField.textModified, setModified);

    BGridPane descPane = new BGridPane(2);
    descPane.add(null, descLabel);
    descPane.add(null, desc);
    descPane.setHalign(BHalign.left);
    descPane.setStretchColumn(1);
    descPane.setColumnAlign(BHalign.fill);

    // Table

    tablePane = new BGridPane(3);
    tablePane.setColumnAlign(BHalign.center);
    tablePane.setRowAlign(BValign.fill);
    tablePane.setValign(BValign.top);
    //tablePane.setColorRows(true);

    Array<BNrioTabularThermistorConversion.XYPoint> points = src.getPoints();
    buildTablePane(points);

    // Add/Resort Buttons

    BButton addButton = new BButton(new AddCmd());
    BButton resortButton = new BButton(new ResortCmd());
    BButton deleteAllButton = new BButton(new DeleteAllCmd());
    BButton importButton = new BButton(new ImportCmd());
    BButton exportButton = new BButton(new ExportCmd());

    linkTo(null, addButton, BButton.actionPerformed, setModified);
    linkTo(null, resortButton, BButton.actionPerformed, setModified);
    linkTo(null, deleteAllButton, BButton.actionPerformed, setModified);
    linkTo(null, importButton, BButton.actionPerformed, setModified);
    linkTo(null, exportButton, BButton.actionPerformed, setModified);

    BGridPane addbuttonPane = new BGridPane(1);
    addbuttonPane.setUniformColumnWidth(true);
    addbuttonPane.setColumnAlign(BHalign.fill);
    addbuttonPane.setValign(BValign.top);

    addbuttonPane.add(null, addButton);
    addbuttonPane.add(null, resortButton);
    addbuttonPane.add(null, deleteAllButton);
    addbuttonPane.add(null, importButton);
    addbuttonPane.add(null, exportButton);

    // OK/Cancel Buttons

    BGridPane okbuttonPane = new BGridPane(2);
    okbuttonPane.setColumnAlign(BHalign.fill);
    okbuttonPane.setUniformColumnWidth(true);

    okButton = new BButton(nrioLex.get("tabularDialog.okButtonLabel"));
    okbuttonPane.add(null, okButton);
    linkTo("linkA", okButton, BButton.actionPerformed, okButtonPressed);
    okButton.setEnabled(false);

    BButton cancelButton = new BButton(nrioLex.get("tabularDialog.cancelButtonLabel"));
    okbuttonPane.add(null, cancelButton);
    linkTo("linkB", cancelButton, BButton.actionPerformed, cancelButtonPressed);

    // Pull it all together

    BEdgePane outerPane = new BEdgePane();
    outerPane.setTop(new BBorderPane(descPane, 7, 7, 0, 7));

    BScrollPane scrollPane = new BScrollPane(new BBorderPane(tablePane,7,7,7,7));
    scrollPane.setVpolicy(BScrollBarPolicy.always);
    scrollPane.setHpolicy(BScrollBarPolicy.never);
    scrollPane.setViewportBackground(Theme.widget().getControlBackground());
    BConstrainedPane constrainedPane = new BConstrainedPane(new BBorderPane(scrollPane, BBorder.inset, BInsets.DEFAULT));
    constrainedPane.setFixedSize(205, 250);
    outerPane.setCenter(new BBorderPane(constrainedPane, 7, 7, 7, 7));
    outerPane.setRight(new BBorderPane(addbuttonPane, 7, 7, 0, 0));
    outerPane.setBottom(new BBorderPane(okbuttonPane, 0, 0, 7, 0));

    setContent(outerPane);
    setDefaultButton(okButton);
  }

  @SuppressWarnings("rawtypes")
  private void buildTablePane(Array points)
  {
    BTextField xfield, yfield;

    tablePane.removeAll();
    map.clear();
    nextIndex = 0;
    int size = points.size();

    if (size > 0)
    {
      tablePane.add("xh", new BLabel(nrioLex.get("tabularDialog.ohmsColumnLabel")));
      tablePane.add("yh", new BLabel(nrioLex.get("tabularDialog.celsiusColumnLabel")));
      tablePane.add("dh", new BNullWidget());

      for (int i = 0; i < size; i++)
      {
        if (points.get(i) instanceof BNrioTabularThermistorConversion.XYPoint)
        {
          BNrioTabularThermistorConversion.XYPoint pnt = (BNrioTabularThermistorConversion.XYPoint)points.get(i);
          xfield = new BTextField(pnt.x() + "", 10);
          yfield = new BTextField(pnt.y() + "", 10);
        }
        else
        {
          TextXYPoint pnt = (TextXYPoint) points.get(i);
          xfield = new BTextField(pnt.x(), 10);
          yfield = new BTextField(pnt.y(), 10);
        }

        DeleteCmd deleteCmd = new DeleteCmd(i);
        BButton dfield = new BButton(deleteCmd);
        linkTo(null, dfield, BButton.actionPerformed, setModified);
        dfield.setButtonStyle(BButtonStyle.toolBar);

        xfield.setExpandHeight(true);
        yfield.setExpandHeight(true);

        tablePane.add("x" + i, xfield);
        tablePane.add("y" + i, yfield);
        tablePane.add("d" + i, dfield);
        linkTo(null, xfield, BTextField.textModified, setModified);
        linkTo(null, yfield, BTextField.textModified, setModified);
        linkTo(null, dfield, BButton.actionPerformed, setModified);

        map.add(Integer.valueOf(i));
      }

      nextIndex = size;
    }
  }

  public BNrioTabularThermistorConversion getConversion()
  {
    return conversion;
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public void doOkButtonPressed(BWidgetEvent event)
  {
    // Need to validate the fields
    if (map.size() < 2)
    {
       BDialog.error(this, nrioLex.get("tabularDialog.twoPointsError"));
       return;
    }

    BNrioTabularThermistorConversion conv = BNrioTabularThermistorConversion.make();

    double x, y;
    for (int i = 0; i < map.size(); i++)
    {
      String xname = "x" + map.get(i);
      String yname = "y" + map.get(i);

      BTextField xfield = (BTextField) tablePane.get(xname);
      BTextField yfield = (BTextField) tablePane.get(yname);

      try
      {
        x = Double.parseDouble(xfield.getText());
        y = Double.parseDouble(yfield.getText());
      }
      catch(NumberFormatException n)
      {
        //show error dialog
        BDialog.error(this, nrioLex.get("tabularDialog.invalidFormatError"));
        return;
      }

      conv.add(x, y);
    }

    // If field validation is ok
    conv.regen();

    conv.setDescription(desc.getText());
    conversion = conv;

    // close the dialog
    close();
  }

  public void doCancelButtonPressed(BWidgetEvent event)
  {
    close();
  }

  public void doSetModified(BWidgetEvent event)
  {
    okButton.setEnabled(true);
  }

////////////////////////////////////////////////////////////////
// Command Inner Classes
////////////////////////////////////////////////////////////////

  class DeleteCmd extends Command
  {
    public DeleteCmd(int index)
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.deleteButton");
      this.index = index;
    }

    public CommandArtifact doInvoke()
    {
      tablePane.remove("x" + index);
      tablePane.remove("y" + index);
      tablePane.remove("d" + index);
      map.remove(Integer.valueOf(index));

      if (map.size() == 0)
      {
        tablePane.remove("xh");
        tablePane.remove("yh");
        tablePane.remove("dh");
      }

      return null;
    }

    private int index;
  }

  class AddCmd extends Command
  {
    public AddCmd()
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.addButton");
    }

    public CommandArtifact doInvoke()
    {
      BTextField xfield = new BTextField("", 10);
      BTextField yfield = new BTextField("" + "", 10);

      DeleteCmd deleteCmd = new DeleteCmd(nextIndex);
      BButton dfield = new BButton(deleteCmd);
      dfield.setButtonStyle(BButtonStyle.toolBar);
      linkTo(null, dfield, BButton.actionPerformed, setModified);

      if (map.size() == 0)
      {
        tablePane.add("xh", new BLabel(nrioLex.get("tabularDialog.ohmsColumnLabel")));
        tablePane.add("yh", new BLabel(nrioLex.get("tabularDialog.celsiusColumnLabel")));
        tablePane.add("dh", new BNullWidget());
      }

      xfield.setExpandHeight(true);
      yfield.setExpandHeight(true);

      tablePane.add("x" + nextIndex, xfield);
      tablePane.add("y" + nextIndex, yfield);
      tablePane.add("d" + nextIndex, dfield);

      linkTo(null, xfield, BTextField.textModified, setModified);
      linkTo(null, yfield, BTextField.textModified, setModified);
      linkTo(null, dfield, BButton.actionPerformed, setModified);

      map.add(Integer.valueOf(nextIndex));
      nextIndex++;

      return null;
    }
  }

  class ResortCmd extends Command
  {
    public ResortCmd()
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.resortButton");
    }

    public CommandArtifact doInvoke()
    {
      Array<TextXYPoint> array = new Array<>(TextXYPoint.class);

      for (int i = 0; i < map.size(); i++)
      {
        String xname = "x" + map.get(i);
        String yname = "y" + map.get(i);

        BTextField xfield = (BTextField) tablePane.get(xname);
        BTextField yfield = (BTextField) tablePane.get(yname);

        TextXYPoint pnt = new TextXYPoint(xfield.getText(), yfield.getText());
        array.add(pnt);
      }

      array = array.sort();
      buildTablePane(array);

      return null;
    }
  }

  class DeleteAllCmd extends Command
  {
    public DeleteAllCmd()
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.deleteAllButton");
    }

    public CommandArtifact doInvoke()
    {
      if (map.size() == 0) return null;
      for (int i = 0; i < map.size(); i++)
      {
        String xname = "x" + map.get(i);
        String yname = "y" + map.get(i);
        String dname = "d" + map.get(i);

        tablePane.remove(xname);
        tablePane.remove(yname);
        tablePane.remove(dname);
      }

      map.clear();

      tablePane.remove("xh");
      tablePane.remove("yh");
      tablePane.remove("dh");
      nextIndex = 0;
      desc.setText("");
      desc.getSelection().deselect();
      desc.moveCaretPosition(new Position(0,0));

      return null;
    }
  }

  class ImportCmd extends Command
  {
    public ImportCmd()
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.importButton");
    }

    public CommandArtifact doInvoke()
    {
      BOrd ord;
      BFileOrdChooser chooser = new BFileOrdChooser();
      chooser.addFilter(new ExtFileFilter("Xml Files (*.xml)", "xml"));

      ord = chooser.openChooser(BNrioTabularThermistorDialog.this, BNrioTabularThermistorDialog.this, BOrd.make("file:^"), null);

      if (ord == null)
        return null;

      if (BDialog.confirm(BNrioTabularThermistorDialog.this, BDialog.TITLE_CONFIRM,
          nrioLex.get("tabularDialog.confirmMessage")) == BDialog.NO)
        return null;

      try
      {
        loadFile(ord);
      }
      catch(NrioException e)
      {
        BDialog.error(BNrioTabularThermistorDialog.this, BDialog.TITLE_ERROR, nrioLex.get("tabularDialog.importError"), e);
      }

      return null;
    }

    private void loadFile(BOrd fileOrd) throws NrioException
    {
      BNrioTabularThermistorConversion conv = BNrioTabularThermistorConversion.make();

      double[] ohms = null;
      double[] celsius = null;
      InputStream stream = null;

      try{
        BXmlFile file = (BXmlFile) fileOrd.get();
        if (file == null){
          throw new NrioException("File not defined.");
        }

        stream = file.getInputStream();

        XParser parser = XParser.make(stream);
        XElem thermistorElem = parser.parse();

        XElem descriptionElem = thermistorElem.elem("description");
        String description = descriptionElem.string();
        if (description != null)
          conv.setDescription(description);
        else
          conv.setDescription("");

        XElem tableElem = thermistorElem.elem("table");

        if (tableElem == null)
        {
          throw new NrioException("Improperly formatted file: No 'table' elemnent.");
        }

        XElem[] pointElems = tableElem.elems("point");
        if (pointElems.length < 2){
          throw new NrioException("Must have at least two points.");
        }

        ohms = new double[pointElems.length];
        celsius = new double[pointElems.length];

        for (int i = 0; i < pointElems.length; i++){
          ohms[i] = pointElems[i].getd("ohms");
          celsius[i] = pointElems[i].getd("celsius");

          //if (i > 0 && ohms[i] < ohms[i-1]){
          //  throw new NrioException("Ohms must have increasing values.");
          //}

          conv.add(ohms[i], celsius[i]);
        }
      }
      catch(NrioException n){
        try{
          if (stream != null)
            stream.close();
        }
        catch(Exception c){}

        throw n;
      }
      catch(Exception e){
        try{
          if (stream != null)
            stream.close();
        }
        catch(Exception c){}

        throw new NrioException(e.getMessage());
      }

      try{
        if (stream != null)
          stream.close();
      }
      catch(Exception c){}

      //set the new points and description
      desc.setText(conv.getDescription());
      desc.getSelection().deselect();
      desc.moveCaretPosition(new Position(0,0));
      conv.regen();
      buildTablePane(conv.getPoints());
    }
  }

  class ExportCmd extends Command
  {
    public ExportCmd()
    {
      super(BNrioTabularThermistorDialog.this, Lexicon.make("nrio"), "tabularThermistor.exportButton");
    }

    public CommandArtifact doInvoke()
    {
      BOrd ord;
      BFacets facets = BFacets.make("save", BBoolean.TRUE);

      String defaultName = "thermistor_table.xml";
      try
      {
        defaultName = desc.getText();
        defaultName = defaultName.replaceAll("\\s+", "_");
        defaultName = defaultName.replaceAll("[^a-zA-Z0-9_]+", "");
        defaultName = defaultName.toLowerCase();
        if (defaultName.trim().length() == 0)
          defaultName = "thermistor_table";
        defaultName = defaultName + ".xml";
      }
      catch(Exception e){}

      facets = BFacets.make(facets, "defaultFileName", BString.make(defaultName));
      BFileOrdChooser chooser = new BFileOrdChooser();
      chooser.addFilter(new ExtFileFilter("Xml Files (*.xml)", "xml"));
      ord = chooser.openChooser(BNrioTabularThermistorDialog.this, BNrioTabularThermistorDialog.this, BOrd.make("file:^"), facets);

      if (ord == null)
        return null;

      try
      {
        saveFile(ord);
      }
      catch(NrioException e)
      {
        BDialog.error(BNrioTabularThermistorDialog.this, BDialog.TITLE_ERROR, nrioLex.get("tabularDialog.exportError"), e);
      }

      return null;
    }

    private void saveFile(BOrd fileOrd) throws NrioException
    {
      if (map.size() < 2)
      {
         BDialog.error(BNrioTabularThermistorDialog.this, nrioLex.get("tabularDialog.twoPointsError"));
         return;
      }

      XElem xroot = new XElem("thermistor");
      XElem xdesc = new XElem("description");
      xdesc.addContent(new XText(desc.getText()));
      xroot.addContent(xdesc);
      XElem xtable = new XElem("table");
      xroot.addContent(xtable);

      for (int i = 0; i < map.size(); i++)
      {
        String xname = "x" + map.get(i);
        String yname = "y" + map.get(i);

        BTextField xfield = (BTextField) tablePane.get(xname);
        BTextField yfield = (BTextField) tablePane.get(yname);

        try
        {
          double x = Double.parseDouble(xfield.getText());
          double y = Double.parseDouble(yfield.getText());

          XElem point = new XElem("point");
          point.setAttr("ohms", xfield.getText());
          point.setAttr("celsius", yfield.getText());
          xtable.addContent(point);
        }
        catch(NumberFormatException n)
        {
          BDialog.error(BNrioTabularThermistorDialog.this, nrioLex.get("tabularDialog.invalidFormatError"));
          return;
        }
      }

      try
      {
        BIFile file = (BIFile) fileOrd.get();
        XWriter xwriter = new XWriter(file.getOutputStream());
        xwriter.prolog();
        xroot.write(xwriter);
        xwriter.close();
      }
      catch(Exception e)
      {
        BDialog.error(BNrioTabularThermistorDialog.this, nrioLex.get("tabularDialog.writingError"), e);
        return;
      }
    }
  }

  class TextXYPoint implements Comparable<Object>
  {
    public TextXYPoint(String x, String y)
    {
      this.x = x;
      this.y = y;
    }

    public int compareTo(Object o)
    {
      if (o instanceof TextXYPoint)
      {
        TextXYPoint src = (TextXYPoint) o;
        try
        {
          double srcxv = Double.parseDouble(src.x);
          double xv = Double.parseDouble(x);
          if (srcxv > xv) return -1;
          if (srcxv < xv) return 1;
          return 0;
        }
        catch(NumberFormatException e)
        {
          return 1;
        }
      }

      throw new ClassCastException("can't compare TextXYPoint");
    }

    public String toString()
    {
      return "{x=" + x + ",y=" + y + "}";
    }

    public String x()
    {
      return x;
    }

    public String y()
    {
      return y;
    }

    public int hashCode()
    {
      return toString().hashCode();
    }

    String x;
    String y;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Lexicon nrioLex = Lexicon.make("nrio");
  static BImage editIcon = BImage.make("module://icons/x32/edit.png");
  private BGridPane tablePane;
  private BButton okButton;
  private BTextField desc;
  private int nextIndex;
  private Array<Integer> map = new Array<>(Integer.class);
  private BNrioTabularThermistorConversion conversion = null;
}
