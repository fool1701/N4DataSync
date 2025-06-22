/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.file;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.baja.file.BIFile;
import javax.baja.file.BLocalFileStore;
import javax.baja.gx.BInsets;
import javax.baja.gx.RectGeom;
import javax.baja.log.Log;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.ViewQuery;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BTextField;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.enums.BScrollBarPolicy;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.pane.BTextEditorPane;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.Line;
import javax.baja.ui.text.Position;
import javax.baja.ui.text.Segment;
import javax.baja.ui.text.TextModel;
import javax.baja.ui.text.TextParser;
import javax.baja.ui.text.TextRenderer;
import javax.baja.util.Lexicon;
import javax.baja.workbench.view.BWbView;

import com.tridium.nre.util.InputStreamInfo;
import com.tridium.ui.theme.Theme;
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
@NiagaraProperty(
  name = "wordWrap",
  type = "boolean",
  defaultValue = "false"
)
public class BTextFileViewer
  extends BWbView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.file.BTextFileViewer(901519296)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "wordWrap"

  /**
   * Slot for the {@code wordWrap} property.
   * @see #getWordWrap
   * @see #setWordWrap
   */
  public static final Property wordWrap = newProperty(0, false, null);

  /**
   * Get the {@code wordWrap} property.
   * @see #wordWrap
   */
  public boolean getWordWrap() { return getBoolean(wordWrap); }

  /**
   * Set the {@code wordWrap} property.
   * @see #wordWrap
   */
  public void setWordWrap(boolean v) { setBoolean(wordWrap, v, null); }

  //endregion Property "wordWrap"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextFileViewer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTextFileViewer()
  {
    setContent(pane);

    status.add("encoding", encodingStatus);

    wordWrapCommand = new WordWrapCommand(this);
  }

////////////////////////////////////////////////////////////////
// BPlugin
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {
    if (getWbShell() != null)
    {
      OrdTarget target = getWbShell().getActiveOrdTarget();
      ViewQuery viewQuery = target.getViewQuery();
      if (viewQuery != null)
      {
        String wordWrapStr = viewQuery.getParameter("wordWrap", "false");
        if (wordWrapStr.equals("true"))
        {
          setWordWrap(true);
        }
      }
    }

    // the file we are loading
    file = (BIFile)value;

    localFile = null;
    if (file.getStore() instanceof BLocalFileStore)
      localFile = ((BLocalFileStore)file.getStore()).getLocalFile();

    pane.setHpolicy(BScrollBarPolicy.asNeeded);
    text.setParser(new WordWrapTextParser());
    text.setModel(new WordWrapTextModel());

    // set readonly
    text.setEditable(false);

    // init plugin commands
    text.updateEnableStates();
    setTransferWidget(text);
    setCommandEnabled(FIND, true);
    setCommandEnabled(FIND_PREV, true);
    setCommandEnabled(FIND_NEXT, true);
    setCommandEnabled(GOTO, true);

    // read file into editor
    try (Reader in = toReader(file.getInputStream()))
    {
      text.getModel().read(in);
    }

    // init file state
    if (getWbShell() != null)
    {
      BOrd ord = getWbShell().getActiveOrd();
      state = fileStates.get(ord);
      if (state == null)
      {
        state = new FileState(ord);
        fileStates.put(ord, state);
      }
    }

    wordWrapCommand.setSelected(getWordWrap());
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

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    return value;  
  }

////////////////////////////////////////////////////////////////
// Menu/Toolbar Merging
////////////////////////////////////////////////////////////////

  public String getDefaultOptionsId()
  {
    return "textEditor";
  }

  public BToolBar getViewToolBar()
  {
    BToolBar bar = new BToolBar();
    BNiagaraWbShell shell = (BNiagaraWbShell)getWbShell();
    bar.add("find", shell.commands.find);
    bar.add("sep0", new BSeparator());
    bar.add("wordWrap", wordWrapCommand);
    return bar;
  }
  
  public BWidget getViewStatusBarSupplement()
  {
    return status;
  }   
  
////////////////////////////////////////////////////////////////
// BDeluxePlugin
////////////////////////////////////////////////////////////////

  public CommandArtifact invokeCommand(int id)
    throws Exception
  {
    switch(id)
    {
      case FIND:      return text.doFind();
      case FIND_NEXT: return text.doFindNext();
      case FIND_PREV: return text.doFindPrev();
      case GOTO:      return text.doGoto();
      default: return super.invokeCommand(id);
    }
  }   

////////////////////////////////////////////////////////////////
// WordWrap
////////////////////////////////////////////////////////////////

  class WordWrapCommand extends ToggleCommand
  {
    public WordWrapCommand(BWidget owner)
    {                     
      super(owner, lex, "TextFileViewer.wordWrap");
    }
    
    public CommandArtifact doInvoke()
      throws Exception
    {                                      
      // save       
      setWordWrap(isSelected());
      layoutText();
      return null;
    }
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
  
  public void caretMoved(Position pos)
  {                   
    // save in state so if we leave page and come
    // back we return to the same caret location
    state.lastPosition = pos;
  }                       

  public void doLayout(BWidget[] kids)
  {                           
    super.doLayout(kids);
    layoutText();
  }
  
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

  void layoutText()
  {
    // Position start = text.getSelection().getStart();
    // Position end = text.getSelection().getEnd();
    if (text.getModel() instanceof WordWrapTextModel)
    {
      ((WordWrapTextModel)text.getModel()).update(text.getParser().parse(text.getText().toCharArray()));
      text.getSelection().deselect();
      repaint();
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
    public String toString() { return ord.toString() + " " + lastPosition; }
  }

////////////////////////////////////////////////////////////////
// TextParser
////////////////////////////////////////////////////////////////

class WordWrapTextParser 
  extends TextParser
{
  public synchronized Line[] parse(char[] buffer)
  {
    // initialize the parse
    this.buffer  = buffer;
    this.length  = buffer.length;
    this.pos     = 0;
    this.last    = -1;
    this.current = (length > 0) ? buffer[0] : -1;;
    this.next    = (length > 1) ? buffer[1] : -1;
    this.segmentStart = 0;
    this.lineStart = 0;

    double viewWidth = 0;
    if (getWordWrap() && pane != null)
    {
      viewWidth = getWidth();

      //It would be nice if we could be smarter about no needing to leave
      // space for the AS_NEEDED vScrollBar, but it would require another pass
      // at parsing the text file
      boolean vsbShow = false;
      switch(pane.getVpolicy().getOrdinal())
      {
        case BScrollBarPolicy.AS_NEEDED:  vsbShow = true; break;
        case BScrollBarPolicy.ALWAYS: vsbShow = true; break;
        case BScrollBarPolicy.NEVER: vsbShow = false; break;
      }
      if (vsbShow)
      {
        double editorInsets = 20;//BTextEditor has protected insets
        BInsets scrollInsets = Theme.scrollPane().getInsets();
        double totalInsets = editorInsets + scrollInsets.right() + scrollInsets.left();
        viewWidth = viewWidth - Theme.scrollBar().getFixedWidth() - totalInsets;
      }
    }
    
    // loop until nextSegment returns null
    ArrayList<Line> lines = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();
    while(pos < length)
    {
      Segment seg = nextSegment();
      segments.add( seg );
      if (seg.type == Segment.NEWLINE)
      {
        int len = pos-lineStart;
        if (seg.modifiers == Segment.MOD_NEWLINE_RN) len--;
        lines.add(new Line(buffer, lineStart, len, segments));
        segments.clear();
        lineStart = pos;
      }
      else if (getWordWrap())
      {
        int len = pos-lineStart;
        Line line = new Line(buffer, lineStart, len, segments);
        
        //WORD WRAP
        if (viewWidth > 0 && !seg.isWhitespace() && getRenderer().getLineWidth(line) > viewWidth)
        {
          segments.remove(seg);
          pos -= seg.length;
          len = pos-lineStart;
          
          lines.add(new Line(buffer, lineStart, len, segments));
          segments.clear();
          Segment newSeg = new Segment(seg.type, seg.modifiers, 0, seg.length);
          segments.add( newSeg );
          lineStart = pos;
          pos += newSeg.length;
        }
      }
      segmentStart = pos;
    }
    
    // ensure we got last line if not a newline
    if (segments.size() > 0)
      lines.add(new Line(buffer, lineStart, buffer.length-lineStart, segments));

    // ensure we have at least one line
    if (lines.size() == 0) lines.add( new Line(new char[0], new Segment[0]) );
    
    // return result
    return lines.toArray(new Line[lines.size()]);
  }
}

////////////////////////////////////////////////////////////////
// TextModel
////////////////////////////////////////////////////////////////

public class WordWrapTextModel
  extends TextModel
{
  protected void update(Line[] lines)
  {
    super.update(lines);
   
    BTextEditor editor = getEditor();
    boolean singleLine = editor.isSingleLine();
    
    //walk through the text to determine our preferred width
    double pw = prefWidth;
    if (!singleLine) walkLines();
    
    // if the preferred size changes, then we need
    // more than a simple repaint on the BTextEditor
    if (pw != prefWidth && !singleLine)
      editor.relayout();
    else
      editor.repaint();
  }

  private void walkLines()
  {
    TextRenderer renderer = getRenderer();
    double maxw = 10;
    for (int ixLines = 0; ixLines < getLineCount(); ixLines++)
    {
      //if word wrap, our preferred width is the longest segment
      // otherwise it's our longest line
      if (getWordWrap())
      {
        Segment[] segments = getLine(ixLines).segments;
        for (int ixSegs = 0; ixSegs<segments.length; ixSegs++)
        {
          maxw = Math.max(maxw, renderer.getLineWidth(getLine(ixLines), segments[ixSegs].offset, (segments[ixSegs].length)));
        }
      }
      else
      {
        maxw = Math.max(maxw, renderer.getLineWidth(getLine(ixLines)));
      }
    }
    prefWidth = maxw;
  }
  
  public double getPreferredWidth()
  {
    return prefWidth;
  }

  public double getPreferredHeight()
  {
    return super.getPreferredHeight() + 5;
  }
  
  double prefWidth;
}
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final Log log = Log.getLog("textEditor");
  static final Lexicon lex = Lexicon.make("workbench");
  
  private static HashMap<BOrd, FileState> fileStates = new HashMap<>();
  
  public BTextEditorPane pane = new BTextEditorPane("", 80, 40, true);
  public BTextEditor text = pane.getEditor();
  public FileState state;
  public BTextField encodingStatus = new BTextField("", 0, false);
  public ToggleCommand wordWrapCommand;
  BGridPane status = new BGridPane(4);        
  BIFile file;        
  File localFile;
  boolean zipped;     
  String encoding;
} 
