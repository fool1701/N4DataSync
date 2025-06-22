/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.baja.file.BIFile;
import javax.baja.file.BLocalFileStore;
import javax.baja.gx.RectGeom;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BTextField;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BTextEditorPane;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Position;
import javax.baja.ui.text.TextController;
import javax.baja.ui.text.TextParser;
import javax.baja.util.Lexicon;
import javax.baja.workbench.view.BWbView;

import com.tridium.nre.util.InputStreamInfo;
import com.tridium.sys.Nre;
import com.tridium.ui.CaretTracker;
import com.tridium.workbench.shell.BNiagaraWbShell;

/**
 * BTextFileEditor
 *
 * @author    Brian Frank
 * @creation  25 Jul 01
 * @version   $Revision: 43$ $Date: 11/20/09 11:02:21 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:IDataFile",
    requiredPermissions = "r"
  )
)
public class BTextFileEditor
  extends BWbView
  implements CaretTracker
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BTextFileEditor(4265906083)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextFileEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTextFileEditor()
  {
    text.setController(new Controller());

    setContent(pane);
    linkTo("lk0", pane.getEditor(), BTextEditor.textModified, setModified);

    status.add("encoding", encodingStatus);
    status.add("rw", rwStatus);
    status.add("line", lineStatus);
    status.add("col", columnStatus);

  }

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  @Override
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {
    getUndoManager().setMaxArtifacts(10000);

    // the file we are loading
    file = (BIFile)value;
    lastModified = file.getLastModified();

    localFile = null;
    if (file.getStore() instanceof BLocalFileStore)
      localFile = ((BLocalFileStore)file.getStore()).getLocalFile();

    // install color coding
    String ext = file.getExtension();
    TextParser parser = getParser(ext);
    if (parser != null)
      text.setParser(parser);

    // set readonly
    boolean isEditable = !file.isReadonly();
    text.setEditable(isEditable);
    rwStatus.setText(isEditable ? "RW" : "RO");

    // init plugin commands
    text.updateEnableStates();
    setTransferWidget(text);
    setCommandEnabled(FIND, true);
    setCommandEnabled(FIND_PREV, true);
    setCommandEnabled(FIND_NEXT, true);
    setCommandEnabled(REPLACE, isEditable);
    setCommandEnabled(GOTO, true);

    // read file into editor
    try (Reader in = toReader(file.getInputStream()))
    {
      text.getModel().read(in);
    }

    // init file state
    BOrd ord = getWbShell().getActiveOrd();
    state = fileStates.get(ord);
    if (state == null)
    {
      state = new FileState(ord);
      fileStates.put(ord, state);
    }
    text.moveCaretPosition(state.lastPosition);
  }

  private Reader toReader(InputStream in)
    throws Exception
  {
    InputStreamInfo inputStreamInfo = new InputStreamInfo(in);
    if (inputStreamInfo.isZipped())
    {
      zipped = true;
    }

    // update status bar status
    encoding = inputStreamInfo.getEncodingTag();
    String s = encoding;
    if (zipped) { s = "Zip/" + s; }
    encodingStatus.setText(s);
    encodingStatus.setVisibleColumns(s.length());
    return inputStreamInfo.toReader();
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    BIFile file = (BIFile)value;
    
    // The resources created by file.getOutputStream could be leaked if something in toWriter throws
    // an exception. One solution would be to wrap the file output stream in a try-with-resources.
    // However, that would cause close to be called twice on that stream- once when the Writer is
    // closed and again when the file output stream itself is closed. Calling close on an output
    // stream should be ok but ChunckedOutputStream used to have a bug that would throw on
    // subsequent calls to close. That bug has been fixed but to prevent issues with other output
    // streams that do not support multiple calls to close (though they should), this code is being
    // left alone.
    try (Writer out = toWriter(file.getOutputStream()))
    {
      text.getModel().write(out);
      out.flush();
    }
    lastModified = file.getLastModified();
    return value;
  }

////////////////////////////////////////////////////////////////
// Menu/Toolbar Merging
////////////////////////////////////////////////////////////////

  public String getDefaultOptionsId()
  {
    return "textEditor";
  }

  @Override
  public BToolBar getViewToolBar()
  {
    BToolBar bar = new BToolBar();
    BNiagaraWbShell shell = (BNiagaraWbShell)getWbShell();
    bar.add("find", shell.commands.find);
    bar.add("replace", shell.commands.replace);
    bar.add("sep0", new BSeparator());
    bar.add("findFiles", shell.commands.findFiles);
    bar.add("replaceInFiles", shell.commands.replaceInFiles);
    bar.add("sep1", new BSeparator());
    bar.add("consolePrev", shell.commands.consolePrev);
    bar.add("consoleNext", shell.commands.consoleNext);
    return bar;
  }

  @Override
  public BWidget getViewStatusBarSupplement()
  {
    return status;
  }

  @Override
  public void prime()
  {
    text.requestFocus();
  }

////////////////////////////////////////////////////////////////
// BDeluxePlugin
////////////////////////////////////////////////////////////////

  @Override
  public CommandArtifact invokeCommand(int id)
    throws Exception
  {
    switch(id)
    {
      case FIND:      return text.doFind();
      case FIND_NEXT: return text.doFindNext();
      case FIND_PREV: return text.doFindPrev();
      case REPLACE:   return text.doReplace();
      case GOTO:      return text.doGoto();
      default: return super.invokeCommand(id);
    }
  }

  public static File findBuildFile(File f, String filename)
  {
    if (f == null) return null;
    if (f.isDirectory())
    {
      File x = new File(f, filename);
      if (x.exists()) return x;
    }
    else
    {
      if (f.getName().equals(filename))
        return f;
    }
    String parent = f.getParent();
    if (parent == null) return null;
    return findBuildFile(new File(f.getParent()), filename);
  }   
  
  public static File findParentDirectory(File f, String filename)
  {                      
    if (f == null) return null;
    if (f.isDirectory())
    {   
      if (f.getName().equals(filename))
        return f;                         
    }                             
    String parent = f.getParent();
    if (parent == null) return null;
    return findParentDirectory(new File(f.getParent()), filename);
  }

////////////////////////////////////////////////////////////////
// Encoding
////////////////////////////////////////////////////////////////

  /**
   * Convert output stream to char Writer based on
   * encoding field set during toReader call.
   */
  Writer toWriter(OutputStream out)
    throws Exception
  {
    // zip up if that is how we read it
    if (zipped)
    {
      ZipOutputStream zip = new ZipOutputStream(out);
      zip.putNextEntry(new ZipEntry("file.xml"));
      out = zip;
    }

    // write byte order mark for UTF-16
    if (encoding.equals("UTF-16LE")) { out.write(0xFF); out.write(0xFE); }
    if (encoding.equals("UTF-16BE")) { out.write(0xFE); out.write(0xFF); }

    // now return output stream
    return new OutputStreamWriter(out, encoding);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  public String getSelectedText()
  {
    return text.getModel().getSelectedText();
  }

  public void highlight(int line1, int col1, int line2, int col2)
  {
    Position end = text.getModel().getEndPosition();
    Position p1 = new Position(line1-1, col1-1);
    Position p2 = new Position(line2-1, col2-1);
    if (p1.compareTo(end) > 0) p1 = end;
    if (p2.compareTo(end) > 0) p2 = end;
    state.viewport = null;
    text.getSelection().select(p1, p2);
    text.moveCaretPosition(p2);
    text.requestFocus();
  }

  @Override
  public void caretMoved(Position pos)
  {
    // save in state so if we leave page and come
    // back we return to the same caret location
    state.lastPosition = pos;

    // update status bar
    String line = String.valueOf(pos.line+1);
    String col = String.valueOf(pos.column+1);
    lineStatus.setText(lexLine + TextUtil.padLeft(line, linePad));
    columnStatus.setText(lexCol + TextUtil.padLeft(col, colPad));
  }

  @Override
  public void doLayout(BWidget[] kids)
  {
    super.doLayout(kids);

    // this is a weird place to do this, but the text editor
    // tries very very hard to make sure that when it has
    // focus that the caret is visible
    if (state != null && state.viewport != null)
    {
      pane.scrollToVisible(state.viewport);
      state.viewport = null;
    }
  }

  @Override
  public void deactivated()
  {
    super.deactivated();

    //Do not attempt to save the state if it is null--this
    //can happen when doLoadValue threw an exception and did not finish.
    //One example is when a java.io.UTFDataFormatException occurs while
    //the file is being read.
    if (state != null)
    {
      state.viewport = pane.getViewport();
    }
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  public void checkFileTimestamp()
  {
    if (lastModified == null) return;
    if (lastModified.equals(file.getLastModified())) return;

    // gotta do this in its own thread to prevent
    // blocking of the AWT thread
    lastModified = file.getLastModified();
    new Thread()
    {
      @Override
      public void run() { confirmReload(); }
    }.start();
  }

  public void confirmReload()
  {
    int r = BDialog.confirm(this, lex.getText("fileEditor.fileHasChangedReload"));
    if (r == BDialog.YES)
      getWbShell().getRefreshCommand().invoke();
  }

  class Controller extends TextController
  {
    @Override
    public void focusGained(BFocusEvent event)
    {
      super.focusGained(event);
      checkFileTimestamp();
    }
  }

////////////////////////////////////////////////////////////////
// Parser
////////////////////////////////////////////////////////////////

  public static TextParser getParser(String ext)
  {
    if (ext == null) return null;

    // first time initialization
    if (parserPropsFile == null)
    {
      // try
      // {
      //   DefaultFileCopy.copyFile("colorCoding.properties");
      // }
      // catch(Exception e)
      // {
      //   log.log(Level.WARNING, "Unable to initialize colorCoding.properties", e);
      // }
      parserPropsFile = new File(Nre.getNiagaraHome() + File.separator + "defaults" + File.separator + "colorCoding.properties");
    }

    // check if we need to reload our properties
    if (parserPropsFile.lastModified() > parserPropsLastModified)
    {
      log.info("Load \"" + parserPropsFile + "\"");
      parserPropsLastModified = parserPropsFile.lastModified();
      try
      {
        parserProps.clear();
        try (InputStream in = new FileInputStream(parserPropsFile))
        {
          parserProps.load(in);
        }
      }
      catch(Exception e)
      {
        log.log(Level.INFO, "Load failed \"" + parserPropsFile + "\"", e);
      }
    }

    // do it
    String x = parserProps.getProperty(ext);
    if (x == null) return null;
    try
    {
      StringTokenizer st = new StringTokenizer(x, ":");
      String modName = st.nextToken();
      String className = st.nextToken();
      return (TextParser)Sys.loadModule(modName).loadClass(className).getDeclaredConstructor().newInstance();
    }
    catch(Exception e)
    {
      log.log(Level.INFO, "Parser not found \"" + x + "\"", e);
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// FileState
////////////////////////////////////////////////////////////////

  public static class FileState
  {
    public FileState(BOrd ord) { this.ord = ord; }
    public BOrd ord;
    public Position lastPosition = new Position(0,0);
    public RectGeom viewport;
    @Override
    public String toString() { return ord.toString() + " " + lastPosition; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final Logger log = Logger.getLogger("textEditor");
  static final Lexicon lex = Lexicon.make("workbench");
  static final String lexLine = lex.getText("fileEditor.line");
  static final String lexCol = lex.getText("fileEditor.col");
  static final int linePad = 6;
  static final int colPad = 4;

  private static Properties parserProps = new Properties();
  private static File parserPropsFile;
  private static long parserPropsLastModified;
  private static HashMap<BOrd, FileState> fileStates = new HashMap<>();

  public BTextEditorPane pane = new BTextEditorPane("", 80, 40, true);
  public BTextEditor text = pane.getEditor();
  public FileState state;
  public BTextField encodingStatus = new BTextField("", 0, false);
  public BTextField rwStatus = new BTextField("", 2, false);
  public BTextField lineStatus = new BTextField(lexLine, lexLine.length()+linePad, false);
  public BTextField columnStatus = new BTextField(lexCol, lexCol.length()+colPad, false);
  BGridPane status = new BGridPane(4);
  BIFile file;
  File localFile;
  BAbsTime lastModified;
  boolean zipped;
  String encoding;
}
