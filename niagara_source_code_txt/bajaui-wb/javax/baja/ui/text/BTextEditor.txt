/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;
import javax.baja.ui.pane.*;
import javax.baja.ui.text.commands.*;
import javax.baja.ui.transfer.*;

import com.tridium.ui.*;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BTextEditor provides support for all text entry widgets.
 * By itself BTextEditor is a multi-line editor with a preferred 
 * size necessary to display all rows and columns.  BTextField
 * provides a standard single line text entry widget.  The
 * BTextEditorPane provides a BTextEditor automatically wrapped
 * by a BScrollPane.
 * <p>
 * The current design of BTextEditor is designed for simple
 * text fields and to provide color coded editors for writing
 * source code.  Therefore it assume a fixed width font and 
 * only certain features are designed to be extended.  
 * <p>
 * Features included in BTextEditor:
 * <ul>
 *   <li>RequiresConfiguration options</li>
 *   <li>RequiresConfiguration key binding</li>
 *   <li>RequiresConfiguration color coding</li>
 *   <li>Pluggable color coding parsers</li>
 *   <li>Rich set of predefined navigation and text 
 *       manipulation commands</li>
 *   <li>Integration with the undo framework</li>
 *   <li>Pluggable user input processing</li>
 *   <li>Pluggable line rendering</li>
 * </ul>
 *
 * <p>
 * BTextEditor is composed of several pluggable support classes:
 * <ul>
 * <li><b>Model:</b> The TextModel stores the text being edited.
 *   The text model is based on a list of Lines denoted by
 *   explicit line breaks.  Each Line contains its own character
 *   buffer and a list of Segments.  The Segments of a Line
 *   determine where token breaks occur and how to render 
 *   attributes such as color coding.
 * </li>
 * <li><b>Controller:</b> BTextEditor routes all focus, keyboard,
 *   and mouse events to its installed TextController. This
 *   provides an ideal override point to catch user input.
 * </li>
 * <li><b>Renderer:</b> TextRenderer provides a pluggable class
 *   for managing the layout and painting of each Line in
 *   the document.  The default implementation uses a fixed width
 *   font and uses the Segment flags to support color coding.
 * </li>
 * <li><b>Parser:</b> TextParser provides a pluggable parser
 *   for a BTextEditor.  The parser is responsible for translating
 *   char data into Lines and Segments.  A set of standard parsers
 *   is included for color coding Java, C, C++, and XML.
 * </li>
 * <li><b>Options:</b>The BTextEditorOptions integrates with
 *   the options framework to persistently store editor options.
 *   Options are used to to store color coding, key bindings,
 *   and configuration of editor features.
 * </li>
 * </ul>
 *
 * @author    Brian Frank       
 * @creation  30 Nov 00
 * @version   $Revision: 106$ $Date: 6/29/11 12:15:44 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 If editable is true then the text may be modified,
 or if false the text is readonly.
 */
@NiagaraProperty(
  name = "editable",
  type = "boolean",
  defaultValue = "true"
)
/*
 Fired when the text model is modified.
 */
@NiagaraTopic(
  name = "textModified",
  eventType = "BWidgetEvent"
)
/*
 Fired when the text selection is modified.
 */
@NiagaraTopic(
  name = "selectionModified",
  eventType = "BWidgetEvent"
)
/*
 This method runs here, then fires it for any
 potential links.
 */
@NiagaraTopic(
  name = "inputMethodEvent",
  eventType = "BInputEvent"
)
@NoSlotomatic //custom fireInputMethodEvent implementation
public class BTextEditor
  extends BTransferWidget
  implements UndoManager.Scope
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.text.BTextEditor(3242683505)1.0$ @*/
/* Generated Thu Nov 18 11:39:02 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Property "editable"

  /**
   * Slot for the {@code editable} property.
   * If editable is true then the text may be modified,
   * or if false the text is readonly.
   * @see #getEditable
   * @see #setEditable
   */
  public static final Property editable = newProperty(0, true, null);

  /**
   * Get the {@code editable} property.
   * If editable is true then the text may be modified,
   * or if false the text is readonly.
   * @see #editable
   */
  public boolean getEditable() { return getBoolean(editable); }

  /**
   * Set the {@code editable} property.
   * If editable is true then the text may be modified,
   * or if false the text is readonly.
   * @see #editable
   */
  public void setEditable(boolean v) { setBoolean(editable, v, null); }

  //endregion Property "editable"

  //region Topic "textModified"

  /**
   * Slot for the {@code textModified} topic.
   * Fired when the text model is modified.
   * @see #fireTextModified
   */
  public static final Topic textModified = newTopic(0, null);

  /**
   * Fire an event for the {@code textModified} topic.
   * Fired when the text model is modified.
   * @see #textModified
   */
  public void fireTextModified(BWidgetEvent event) { fire(textModified, event, null); }

  //endregion Topic "textModified"

  //region Topic "selectionModified"

  /**
   * Slot for the {@code selectionModified} topic.
   * Fired when the text selection is modified.
   * @see #fireSelectionModified
   */
  public static final Topic selectionModified = newTopic(0, null);

  /**
   * Fire an event for the {@code selectionModified} topic.
   * Fired when the text selection is modified.
   * @see #selectionModified
   */
  public void fireSelectionModified(BWidgetEvent event) { fire(selectionModified, event, null); }

  //endregion Topic "selectionModified"

  //region Topic "inputMethodEvent"

  /**
   * Slot for the {@code inputMethodEvent} topic.
   * This method runs here, then fires it for any
   * potential links.
   * @see #fireInputMethodEvent
   */
  public static final Topic inputMethodEvent = newTopic(0, null);

  //endregion Topic "inputMethodEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Fire an event for the {@code inputMethodEvent} topic.
   * This method runs here, then fires it for any
   * potential links.
   * @see #inputMethodEvent
   */
  public final void fireInputMethodEvent(BInputEvent event)
  {
    switch(event.getId())
    {
      case BInputMethodEvent.CARET_POSITION_CHANGED:    caretPositionChanged((BInputMethodEvent) event); break;
      case BInputMethodEvent.INPUT_METHOD_TEXT_CHANGED: imTextChanged((BInputMethodEvent) event); break;
      default: throw new IllegalStateException();
    }
    fire(inputMethodEvent, event, null);
  }

  // Route to controller
  public void caretPositionChanged(BInputMethodEvent event) {
    controller.caretPositionChanged(event);
  }

  public void imTextChanged(BInputMethodEvent event) {
    controller.imTextChanged(event);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with default text and editable state.
   */
  public BTextEditor(String text, boolean editable)
  {
    this();
    setText(text);
    setEditable(editable);
    updateEnableStates();    
  }

  /**
   * Default constructor.
   */
  public BTextEditor()
  {
    // install my support instances
    setModel(new TextModel());
    setController(new TextController());
    setRenderer(new TextRenderer());
    setParser(new TextParser());
    setCommandFactory(new TextCommandFactory());
    setSelection(new TextSelection());
    setOptions(BTextEditorOptions.make());    
    updateEnableStates();
  }

////////////////////////////////////////////////////////////////
// TextSupport
////////////////////////////////////////////////////////////////  

  /**
   * Get the text model currently installed.
   */
  public TextModel getModel()
  {
    return model;
  }

  /**
   * Install the specified model this BTextEditor visualizes.
   */
  public void setModel(TextModel model)
  {
    installSupport(this.model, model);
    this.model = model;
  }

  /**
   * Get the text controller currently installed.
   */
  public TextController getController()
  {
    return controller;
  }

  /**
   * Install the specified controller.
   */
  public void setController(TextController controller)
  {
    installSupport(this.controller, controller);
    this.controller = controller;
  }

  /**
   * Get the text parser currently installed.
   */
  public TextParser getParser()
  {
    return parser;
  }

  /**
   * Install the specified parser.
   */
  public void setParser(TextParser parser)
  {
    installSupport(this.parser, parser);
    this.parser = parser;
  }

  /**
   * Get the text command factory currently installed.
   */
  public TextCommandFactory getCommandFactory()
  {
    return commandFactory;
  }

  /**
   * Install the specified command factory.
   */
  public void setCommandFactory(TextCommandFactory commandFactory)
  {
    installSupport(this.commandFactory, commandFactory);
    this.commandFactory = commandFactory;
  }

  /**
   * Get the current text selection.
   */
  public TextSelection getSelection()
  {
    return selection;
  }

  /**
   * Install the specified selection model.
   */
  public void setSelection(TextSelection selection)
  {
    installSupport(this.selection, selection);
    this.selection = selection;
  }

  /**
   * Get the TextRenderer currently installed.
   */
  public TextRenderer getRenderer()
  {
    return renderer;
  }

  /**
   * Install the specified TextRenderer.
   */
  public void setRenderer(TextRenderer renderer)
  {
    installSupport(this.renderer, renderer);
    this.renderer = renderer;
  }

  /**
   * Get the text editor options currently installed.
   * By default the system wide options are used.
   */
  public BTextEditorOptions getOptions()
  {
    return options;
  }

  /**
   * Install the specified options this BTextEditor uses.
   */
  public void setOptions(BTextEditorOptions options)
  {
    if (options == null) throw new NullPointerException();
    this.options = options;
  }

  /**
   * Check that the specified support is not null and 
   * not installed on another table.
   */
  private void installSupport(TextSupport old, TextSupport support)
  {
    if (support == null) throw new NullPointerException();
    if (old == support) return;
    if (support.editor != null) throw new IllegalArgumentException("Already installed on another text widget");
    if (old != null) old.editor = null;
    support.editor = this;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Convenience method for getEditable.
   */
  public final boolean isEditable()
  {
    return getEditable();
  }

  /**
   * Return true if this is a single line text entry widget.  
   * BTextField is the default implementation of a single 
   * line text editor.
   */
  public boolean isSingleLine()
  {
    return false;
  }

  /**
   * Convenience method for <code>getModel().getText()</code>.
   */
  public String getText()
  {
    return model.getText();
  }

  /**
   * Convenience method for <code>getModel().setText()</code>.
   */
  public void setText(String text)
  {
    model.setText(text);
  }

  /**
   * Is copying enabled from this editor.
   */
  public boolean getAllowCopying()
  {
    return allowCopying;
  }

  /**
   * Enabled or disable copying from this editor.
   */
  public void setAllowCopying(boolean allow)
  {
    allowCopying = allow;
  }

////////////////////////////////////////////////////////////////
// Caret Support
////////////////////////////////////////////////////////////////

  /**
   * Return the logical position of the caret.
   */
  public Position getCaretPosition()
  {
    return caretPos;
  }
  
  /**
   * Move the caret position to the specified position.
   */
  public void moveCaretPosition(Position pos)
  {                                     
//    // save the old caret position
//    RectGeom oldRect = caretLocation();
  
    Position end = model.getEndPosition();
    if (pos.compareTo(end) > 0) 
      pos = end;
     
    // move it
    caretPos = pos;
    if (hasFocus()) Blinker.pauseBlinking();
    scrollToVisible(pos);      

    // this is a special hook used by the 
    // BEditor to track cursor movement
    BComplex parent = getParent();
    if (parent != null)
    {         
      BComplex grandparent = parent.getParent();
      if (grandparent instanceof CaretTracker)
        ((CaretTracker)grandparent).caretMoved(pos);
    }

//    // repaint
//    RectGeom r = caretLocation().union(oldRect);
//    repaint(r.x, r.y, r.width, r.height);
    repaint();
  }
  
  /**
   * Is this text widget's caret currently blinking.
   */
  public boolean isCaretBlinking()
  {
    return this == Blinker.getBlinkingWidget();
  }
  
  /**
   * Set the caret's blinking state to blink or
   * not to blink.
   */
  public void setCaretBlinking(boolean blinking)
  {
    if (blinking)
    {
      Blinker.startBlinking(this);
    }
    else
    {
      Blinker.stopBlinking();
      caretVisible = false;      
    }
    try { repaint(); } catch(RuntimeException e) {}
  }
     
  /**
   * Callback from Blinker to blink the caret.
   */
  void blinkCaret(boolean visible)
  {
    caretVisible = visible;
    
    try
    {
//      RectGeom r = caretLocation();
//      repaint(r.x, r.y, r.width, r.height);
      repaint();
    }
    catch (RuntimeException e) {}    
  }  

//  /**
//   * get the location of the caret
//   */  
//  private RectGeom caretLocation()
//  {
//    int q = caretPos.line;
//    BInsets insets = getInsets();
//      
//    return new RectGeom(
//      renderer.columnToX(model.getLine(q), caretPos.column) + insets.left,
//      q * renderer.getLineHeight() + insets.top,                 
//      2,
//      renderer.cellHeight - 1);                             
//  }

////////////////////////////////////////////////////////////////
// Positioning
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the number of lines current visible.
   */
  public int getVisibleLineCount()
  {
    double h = getHeight();
    BWidget parent = getParentWidget();
    if (parent instanceof BScrollPane)
    {
      h = parent.getHeight() - 4;
    }
    return (int)(h / renderer.getLineHeight());
  }

  /**
   * If the text editor requires scrolling, then
   * ensure the specified position is visible.
   */
  public void scrollToVisible(Position pos)
  {                                       
    if (pos.line >= model.getLineCount()) return;
    
    Line line = model.getLine(pos.line);
    BInsets insets = getInsets();                  
    double lineHeight = renderer.getLineHeight();
    double colWidth = renderer.getColumnWidth(line, pos.column);
    
    // get actual bounds of pos
    double x = renderer.columnToX(line, pos.column) + insets.left;
    double y = lineHeight * pos.line + insets.top;
    double w = colWidth;
    double h = renderer.getLineHeight() + insets.top;
    
    // now leave a one character space around char
    x -= colWidth; w += colWidth*2;    
    y -= lineHeight; h += lineHeight*2;
    
    scrollToVisible(new RectGeom(x, y, w, h));
  }

  /**
   * Translate the specified pixel position to 
   * the a logical document position.
   */
  public Position getPositionAt(double x, double y)
  {
    BInsets insets = getInsets();
    x -= insets.left;
    y -= insets.top;

    int lineIndex = (int)Math.ceil(((int)(y / renderer.getLineHeight())));
    Line line = renderer.getModel().getLine(lineIndex);    
    
    int col = renderer.xToColumn(line, x);    
    return new Position(lineIndex, col);
  }
  
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////


  /**
   * Compute the preferred size of the text editor.
   */
  public void computePreferredSize()
  {
    // the model actually keeps track of my preferred 
    // size, and calls relayout() on me whenever the 
    // document requires a new preferred size
    BInsets insets = getInsets();
    double pw = model.getPreferredWidth() + insets.left + insets.right;
    double ph = model.getPreferredHeight() + insets.top + insets.bottom;
    setPreferredSize(pw, ph);
  }
  
  /**
   * Layout the text editor.
   */
  public void doLayout(BWidget[] kids)
  {
    // do nothing
  }
  
  /**
   * Get the insets used to offset the text lines
   * from the edge of the editor bounds.  Subclasses
   * can override this method to provide different
   * insets.
   */
  protected BInsets getInsets()
  {
    return insets;
  }
    
////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////

  /**
   * Paint the text editor.
   */
  public void paint(Graphics g)
  {
    TextModel model = this.model;
    TextRenderer renderer = this.renderer;
    double lineCount = model.getLineCount();
    double lineHeight = renderer.getLineHeight();
    Position selStart = getSelection().getStart();
    Position selEnd = getSelection().getEnd();
    IRectGeom clip = g.getClipBounds();
    BInsets insets = getInsets();
    int visibleStart = Math.max(0, (int)((clip.y() - insets.top) / lineHeight));
    int visibleEnd = (int)Math.min(lineCount-1, (int)((clip.y() + clip.height() - insets.top) / lineHeight));
    
    // this seems a little hackish
    /*
    if (firstPaint && hasFocus())                
    {
      scrollToVisible(caretPos);
      firstPaint = false;
    } 
    */

    // let render paint background (usually just fill)
    getRenderer().paintBackground(g);

    // translate insets
    g.translate(insets.left, insets.top);
    
    // paint margin
    int margin = getOptions().getShowMargin();
    if (margin > 0 && !isSingleLine())
    {
      g.setBrush(getOptions().getColorCoding().getWhitespace());
      double marginX = margin * renderer.getColumnWidth(null, 0);
      g.strokeLine(marginX, clip.y(), marginX, clip.y()+clip.height());
    }
    
    // paint lines
    for(int q=visibleStart; q<=visibleEnd; ++q)
    {
      // update the LineInfo
      Line line = model.getLine(q);
      lineInfo.line = line;
      lineInfo.lineIndex = q;
      lineInfo.selectionStartColumn = -1;
      lineInfo.selectionEndColumn = -1;
      if (selStart != null)
      {
        if (selStart.line <= q && q <= selEnd.line)
        {
          lineInfo.selectionStartColumn = (selStart.line == q) ? selStart.column : 0;
          lineInfo.selectionEndColumn = (selEnd.line == q) ? selEnd.column : Integer.MAX_VALUE;
        }
      }
      
      // render the line
      g.translate(0, q*lineHeight);
      renderer.paintLine(g, lineInfo);
      g.translate(0, -q*lineHeight);
    }
    
    // paint the caret
    if (caretVisible) 
    {
      int q = caretPos.line;
      lineInfo.line = model.getLine(q);
      lineInfo.lineIndex = q;
      lineInfo.selectionStartColumn = -1;
      lineInfo.selectionEndColumn = -1;
      g.translate(0, q*lineHeight);
      renderer.paintCaret(g, lineInfo, caretPos.column);
      g.translate(0, -q*lineHeight);
    }
    
    // translate back from insets
    g.translate(-insets.left, -insets.top);
  }
  
  public String getStyleSelector() { return "text-editor"; }
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////  

  /**
   * Route to TextController.
   */
  public boolean isFocusTraversable()
  {
    return controller.isFocusTraversable();
  }

  /**
   * Route to TextController.
   */
  public void focusGained(BFocusEvent event)
  {
    controller.focusGained(event);
    scrollToVisible(caretPos);
  }

  /**
   * Route to TextController.
   */
  public void focusLost(BFocusEvent event)
  {
    controller.focusLost(event);
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Route to TextController.
   */
  public void keyPressed(BKeyEvent event)
  {
    controller.keyPressed(event);
  }

  /**
   * Route to TextController.
   */
  public void keyReleased(BKeyEvent event)
  {
    controller.keyReleased(event);
  }

  /**
   * Route to TextController.
   */
  public void keyTyped(BKeyEvent event)
  {
    controller.keyTyped(event);
  }
  
  /**
   * Fire an event for the <code>inputMethod</code> topic.
   * This call first routes the event to one the widget
   * keyX(BKeyEvent) callbacks, then fires it for any
   * potential links.
   *
   * @see javax.baja.ui.BWidget#keyEvent
   */
  public void fireKeyEvent(BKeyEvent event)
  {
    switch(event.getId())
    {
      case BKeyEvent.KEY_PRESSED:  keyPressed(event); break;
      case BKeyEvent.KEY_RELEASED: keyReleased(event); break;
      case BKeyEvent.KEY_TYPED:    keyTyped(event); break;
    }
    fire(keyEvent, event, null);
  }



///////////////////////////////////////////////////////////
// Mouse Input
///////////////////////////////////////////////////////////

  /**
   * Route to TextController.
   */
  public void mousePressed(BMouseEvent event)
  {
    controller.mousePressed(event);
  }

  /**
   * Route to TextController.
   */
  public void mouseReleased(BMouseEvent event)
  {
    controller.mouseReleased(event);
  }

  /**
   * Route to TextController.
   */  
  public void mouseEntered(BMouseEvent event)
  {
    controller.mouseEntered(event);
  }

  /**
   * Route to TextController.
   */  
  public void mouseExited(BMouseEvent event)
  {
    controller.mouseExited(event);
  }

  /**
   * Route to TextController.
   */
  public void mouseMoved(BMouseEvent event)
  {
    controller.mouseMoved(event);
  }

  /**
   * Route to TextController.
   */
  public void mouseDragged(BMouseEvent event)
  {
    controller.mouseDragged(event);
  }

  /**
   * Route to TextController.
   */  
  public void mousePulsed(BMouseEvent event)
  {
    controller.mousePulsed(event);
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  public void changed(Property prop, Context cx)
  {
    if (prop == null)
    {
      updateEnableStates();
    }
    else if (prop == editable)
    {
      StyleUtils.toggleStyleClass(this, "readonly", !this.getEditable());
    }
    else if (prop == enabled)
    {
      StyleUtils.toggleStyleClass(this, "disabled", !this.isEnabled());
    }
    repaint();
  }
  
  /**
   * Update enable states.
   */
  public void updateEnableStates()
  {
    boolean editable = getEditable();
    boolean hasSelection = !selection.isEmpty();
    setCopyEnabled(getAllowCopying() && hasSelection);
    setCutEnabled(getAllowCopying() && editable && hasSelection);
    setPasteEnabled(editable);
    setDeleteEnabled(editable && hasSelection);
  }
  
  /**
   * Updates the text editor's current "anchor" X position, based on the
   * current caret position. Once the anchor is updated, navigating through
   * the text editor by pressing up/down/pgup/pgdown will try to stay as close
   * to this X position as it can. Prevents the caret from slamming hard left
   * when navigating past empty lines, for instance. 
   */
  public void updateAnchorX() 
  {
    updateAnchorX(getCaretPosition());
  }
  
  /**
   * Updates the text editor's current "anchor" X position based on the
   * given Position (column/line).
   * @see javax.baja.ui.text.BTextEditor#updateAnchorX()
   * @param newPos
   */
  public void updateAnchorX(Position newPos)
  {
    TextModel model = getModel();
    Line curLine = model.getLine(newPos.line);
    model.setAnchorX(getRenderer().columnToX(curLine, newPos.column));
  }

////////////////////////////////////////////////////////////////
// Search Commands
////////////////////////////////////////////////////////////////  

  public CommandArtifact doFind()
    throws Exception
  {
    return commandFactory.make(TextCommandFactory.FIND).doInvoke();
  }

  public CommandArtifact doFindPrev()
    throws Exception
  {
    return commandFactory.make(TextCommandFactory.FIND_PREV).doInvoke();
  }

  public CommandArtifact doFindNext()
    throws Exception
  {
    return commandFactory.make(TextCommandFactory.FIND_NEXT).doInvoke();
  }

  public CommandArtifact doReplace()
    throws Exception
  {
    return commandFactory.make(TextCommandFactory.REPLACE).doInvoke();
  }

  public CommandArtifact doGoto()
    throws Exception
  {
    return commandFactory.make(TextCommandFactory.GOTO).doInvoke();
  }

////////////////////////////////////////////////////////////////
// BTransferWidget
////////////////////////////////////////////////////////////////

  /**
   * Handle transfer delete command.
   */
  public CommandArtifact doDelete()
    throws Exception
  {
    return new RemoveText(this).doInvoke();
  }

  /**
   * Get the selection as transfer data.
   */
  public TransferEnvelope getTransferData()
    throws Exception
  {
    String text = getModel().getSelectedText();
    return TransferEnvelope.make(text);
  }
  
  /**
   * Remove the selected text and return artifact.
   */
  public CommandArtifact removeTransferData(TransferContext cx)
    throws Exception
  {
    return new RemoveText(this).doInvoke();
  }

  /**
   * Insert the specified text into the editor.
   */
  public CommandArtifact insertTransferData(TransferContext cx)
    throws Exception
  {
    String text = (String)cx.getEnvelope().getData(TransferFormat.string);
    return getController().doInsertText(text);
  }
    
////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/textEditor.png");
  
////////////////////////////////////////////////////////////////
// TextSupport
////////////////////////////////////////////////////////////////  

  /**
   * Abstract base class for support classes.
   */
  public static abstract class TextSupport
  {
    /**
     * Get the text widget the support instance is installed on.
     */
    public final BTextEditor getEditor()
    {
      return editor;
    }
    
    public TextModel getModel() { return editor.model; }
    public TextController getController() { return editor.controller; }
    public TextRenderer getRenderer() { return editor.renderer; }
    public TextSelection getSelection() { return editor.selection; }
    public BTextEditorOptions getOptions() { return editor.options; }
    public TextParser getParser() { return editor.parser; }
    public TextCommandFactory getCommandFactory() { return editor.commandFactory; }
    public BWidgetShell getShell() { return editor.getShell(); }
    
    BTextEditor editor;
  }

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////  

  /*
  public static void main(String[] args)
    throws Exception
  {
    BTextEditor t = new BTextEditor();
    t.setParser(new javax.baja.ui.text.parsers.JavaParser());
    FileReader in = new FileReader(args[0]);
    t.getModel().read(in);
    t.getModel().dump();
    in.close();
        
    BFrame frame = new BFrame("Text Test");
    frame.setContent(new BBorderPane(new BTextEditorPane(t)));
    frame.setScreenBounds(100,50,500,500);
    frame.open();
  } 
  */ 

////////////////////////////////////////////////////////////////
// Scope
////////////////////////////////////////////////////////////////

  /**
   * Get the UndoManager for this shell.
   */
  public UndoManager getInstalledUndoManager()
  {
    return undoManager;
  }
  
  /**
   * Set the UndoManager for this shell.
   */
  public void setInstalledUndoManager(UndoManager undoManager)
  {
    this.undoManager = undoManager;
  }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static BInsets insets = BInsets.make(0,5,0,5);

  private TextModel model;
  private TextController controller;
  private TextRenderer renderer;
  private TextSelection selection;
  private TextParser parser;
  private TextCommandFactory commandFactory;
  private BTextEditorOptions options;
  private TextRenderer.LineInfo lineInfo = new TextRenderer.LineInfo();
  private Position caretPos = new Position(0,0);
  private boolean caretVisible;
  private boolean firstPaint = true;
  private boolean allowCopying = true;

  private UndoManager undoManager = null;            
}
