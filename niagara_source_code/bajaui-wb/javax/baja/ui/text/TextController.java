/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.lang.reflect.Constructor;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import javax.baja.nre.util.TextUtil;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.MouseCursor;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BInputMethodEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.text.commands.ComposeText;
import javax.baja.ui.text.commands.InsertText;
import com.tridium.ui.UiEnv;

/**
 * TextController is responsible for processing all
 * user input on a BTextWidget.  All focus, keyboard
 * and mouse events are routed to this class.
 *
 * @author    Brian Frank       
 * @creation  5 Aug 01
 * @version   $Revision: 28$ $Date: 8/6/09 6:32:40 PM EDT$
 * @since     Baja 1.0
 */
public class TextController
  extends BTextEditor.TextSupport
{

  public TextController()
  {
    // Dynamically create an instance of the underlying TextController implementation.
    ITextControllerImpl im;
    try
    {
      Class<?> awtComponentClass = Class.forName("java.awt.Component");
      Constructor<?> ctor = Class.forName("com.tridium.ui.awt.se.AwtTextControllerImpl").getConstructor(TextController.class);
      im = (ITextControllerImpl) ctor.newInstance(this);
    }
    catch (Throwable e)
    {
      im = new TextControllerImpl(this);
    }
    this.impl = im;
  }
////////////////////////////////////////////////////////////////
// Focus
////////////////////////////////////////////////////////////////

    /**
    * Return if the BTextEditor is focus traversable.
    * Default returns true.
    */
   public boolean isFocusTraversable()
   {
     return true;
   }

  /**
   * Callback when focusGained() on BTextEditor.  The
   * default implementation starts the caret blinking.
   */
  public void focusGained(BFocusEvent event)
  {
    BTextEditor editor = getEditor();
    editor.setCaretBlinking(true);
    if (editor.isSingleLine() && editor.isEditable()) getSelection().selectAll();
  }

  /**
   * Callback when focusLost() on BTextEditor.  The
   * default implementation stops the caret blinking
   * and deselects.
   */
  public void focusLost(BFocusEvent event)
  {
    getEditor().setCaretBlinking(false);
    getSelection().deselect();
  }

////////////////////////////////////////////////////////////////
// Keyboard Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when keyPressed() on BTextEditor.  The
   * default implementation attempts to map the key event
   * to a command using the installed BKeyBindings and
   * TextCommandFactory.
   */
  public void keyPressed(BKeyEvent event)
  {
    int keyCode = event.getKeyCode();

    // clear bracket match
    clearMatchHighlight();

    // don't process the navigation keys for a single line
    if (getEditor().isSingleLine())
    {
      if (keyCode == BKeyEvent.VK_TAB ||
        keyCode == BKeyEvent.VK_UP ||
        keyCode == BKeyEvent.VK_DOWN)
        return;
    }

    // map the key press to a command, and if
    // we find a valid one consume the event
    // and invoke the command
    BKeyBindings keys = getOptions().getKeyBindings();
    Command command = keys.eventToCommand(getEditor(), event);
    if (command != null)
    {
      event.consume();
      command.invoke();
    }

    // debugging
    if (event.isAltDown() && event.isControlDown() && event.getKeyCode() == BKeyEvent.VK_F12)
      getEditor().getModel().dump();

    // consume enter key press so we don't close a dialog
    if (keyCode == BKeyEvent.VK_ENTER && !getEditor().isSingleLine())
      event.consume();
  }

  /**
   * Callback when keyReleased() on BTextEditor.
   */
  public void keyReleased(BKeyEvent event)
  {
    int keyCode = event.getKeyCode();

    // consume enter key release so we don't close a dialog
    if (keyCode == BKeyEvent.VK_ENTER && !getEditor().isSingleLine())
      event.consume();
  }

  /**
   * Callback when keyTyped() on BTextEditor.  The default
   * implement is to insert the character into the document.
   */
  public void keyTyped(BKeyEvent event)
  {
    char key = event.getKeyChar();

    // don't do further processing if control or alt is down
    if (event.isControlDown() || event.isAltDown())
      return;

    // consume everything else
    event.consume();

    // don't process these characters
    if (key == BACKSPACE || key == TAB || key == ESCAPE || key == DELETE)
      return;

    // the text to insert
    String text = String.valueOf(key);

    // if we are inserting a newline, then we want
    // to position the text indented logically
    if (key == NEWLINE)
    {
      int col = columnForNewline();
      if (col > 0) text = text + TextUtil.getSpaces(col);
    }

    insertText(text);

    // Check to see if there is composed text. If so, then
    // it is handled the same as the ComposeText for committed=1.
    // This event handling is new to Java 7
    if (composedText != null)
    {
      composedText = null;
      committedText = new StringBuffer();
      setComposedPosition(null);
      setTextBeginIndex(0);
      setTextEndIndex(0);
    }

    // check match
    BTextEditorOptions options = getEditor().getOptions();
    if (key == '}' && options.getMatchBraces()) matchPrev('{', '}');
    if (key == ')' && options.getMatchParens()) matchPrev('(', ')');
    if (key == ']' && options.getMatchBrackets()) matchPrev('[', ']');
  }

  /**
   * Insert the given text (if possible). The underlying InsertText command will
   * have its invoke() method called, meaning the result will be registered
   * with the UndoManager.
   *
   * @param text
   */
  private void insertText(String text)
  {
    if (validateTextToInsert(text))
      new InsertText(getEditor(), text).invoke();
  }

  /**
   * Insert the given text (if possible). The underlying InsertText command
   * will have its doInvoke method called, meaning it is up to the caller to
   * register the resulting artifact with the UndoManager if needed.
   *
   * @param text
   * @return InsertText command artifact
   */
  protected CommandArtifact doInsertText(String text)
  {
    if (validateTextToInsert(text))
      return new InsertText(getEditor(), text).doInvoke();
    return null;
  }

  /**
   * Will inserting this text result in text that is valid for this controller?
   *
   * @param text
   * @return true if the text is okay to insert
   */
  protected boolean validateTextToInsert(String text)
  {
    return true;
  }

  /**
   * This method is called before a newline is inserted
   * entered.  It positions the caret under the first
   * non-whitepace character from the line above.  If
   * no non-whitspace characters are found, then it
   * positions the caret at its last column.
   */
  protected int columnForNewline()
  {
    Position cur = getEditor().getCaretPosition();
    if (cur.line == 0) return 0;

    Line line = getModel().getLine(cur.line);

    int n = -1;
    for (int i = 0; i < line.segments.length; ++i)
      if (!line.segments[i].isWhitespace())
      {
        n = i;
        break;
      }

    if (n != -1)
      return line.segments[n].offset;
    else
      return cur.column;
  }

  /**
   * Callback when inputMethodEvent.caretPositionChanged() on BTextEditor.
   */
  public void caretPositionChanged(BInputMethodEvent event)
  {
    System.out.println("TextController - change the character at the current position");
  }

  /**
   * Callback when inputMethodEvent.imTextChanged() on BTextEditor.
   * Handles changes to the text entered through an input method.
   * Committed text contained in the event is appended to the
   * committed text of the text component. Composed text contained
   * in the event replaces any existing composed text in the text
   * component.
   * The caret defined in the event is saved and will
   * be returned by getCaret if there is composed text. The
   * component is redrawn.
   */
  public void imTextChanged(BInputMethodEvent event)
  {
    // If composed text is empty, there is no need to remove it
    // before adding the new composed text.
    boolean deleteComposed = true;
    if (composedText == null)
      deleteComposed = false;

    int committedCharacterCount = event.getCommittedCharacterCount();
    AttributedCharacterIterator combinedText = event.getText();
    composedText = null;
    char c = '\u0000';
    if (combinedText != null)
    {
      // copy the composed text
      int toCopy = committedCharacterCount;
      c = combinedText.first();
      while (toCopy-- > 0)
      {
        committedText.append(c);
        c = combinedText.next();
      }
      // copy the composed text
      if (combinedText.getEndIndex() - (combinedText.getBeginIndex() + committedCharacterCount) > 0)
      {
        AttributedString composedTextString = new AttributedString(combinedText,
          combinedText.getBeginIndex() + committedCharacterCount,
          combinedText.getEndIndex(), impl.getIMAtrributes());

        composedText = composedTextString.getIterator();
      }
    }

    event.consume();

    String composedTextString = getText(composedText);
    int composedBeginIndex = (composedText == null) ? 0 : combinedText.getBeginIndex();
    int composedEndIndex = (composedText == null) ? 0 : combinedText.getEndIndex();

    boolean commit = false;
    if (committedCharacterCount > 0)
    {
      // Commit the text
      commit = true;
    }

    new ComposeText(getEditor(), composedTextString, committedText.toString(),
      composedBeginIndex, composedEndIndex,
      event.getCaret().getCharIndex(),
      commit, deleteComposed).invoke();

    // Reset committedText if needed
    if (committedCharacterCount == 0)
      committedText = new StringBuffer();
  }

  /**
   * Extract the raw text from an ACI.
   *
   * @param aci ACI to inspect
   * @return the extracted text
   */
  protected String getText(AttributedCharacterIterator aci)
  {
    if (aci == null) return "";
    StringBuilder sb = new StringBuilder(aci.getEndIndex() - aci.getBeginIndex());
    for (char c = aci.first(); c != AttributedCharacterIterator.DONE; c = aci.next())
    {
      sb.append(c);
    }
    return sb.toString();
  }

////////////////////////////////////////////////////////////////
// Match
////////////////////////////////////////////////////////////////

  /**
   * Find the specified matching open character.  If
   * we do then highlight it and the character we just
   * typed.
   */
  private void matchPrev(char open, char close)
  {
    // start walking backwards
    Position pos = getEditor().getCaretPosition();
    int depth = 1;
    int q = pos.line;
    int c = pos.column - 2;
    if (c < 0)
    {
      q--;
      c = -1;
    }
    search:
    while (q >= 0)
    {
      Line line = getModel().getLine(q);
      char[] buf = line.buffer;
      if (c == -1) c = buf.length - 1;
      while (c >= 0)
      {
        char ch = buf[c];
        if (ch == close) depth++;
        if (ch == open) depth--;
        if (depth == 0) break search;
        c--;
      }
      q--;
    }
    if (q == -1) return;

    // if the match is right next to each other
    // such as () or [], then don't match
    if (q == pos.line && c == pos.column - 2) return;

    TextRenderer renderer = getEditor().getRenderer();
    renderer.matchOpenPos = new Position(q, c);
    renderer.matchClosePos = new Position(pos.line, pos.column - 1);
  }

  public void clearMatchHighlight()
  {
    TextRenderer renderer = getEditor().getRenderer();
    if (renderer != null)
    {
      renderer.matchOpenPos = null;
      renderer.matchClosePos = null;
    }
  }

////////////////////////////////////////////////////////////////
// Mouse Input
////////////////////////////////////////////////////////////////

  /**
   * Callback when mousePressed() on BTextEditor.  The
   * default implementation requestsFocus, clears the
   * selection, and moves the caret position.  If the
   * click count is two then the current word is selected.
   * If the click count is three then the entire line is
   * selected.
   */
  public void mousePressed(BMouseEvent event)
  {
    getEditor().requestFocus();
    Position pos = mouseEventToPosition(event);

    if (event.isShiftDown())
    {
      mouseAnchor = getSelection().getAnchor();
      if (mouseAnchor == null) mouseAnchor = getEditor().getCaretPosition();
      getSelection().select(mouseAnchor, pos);
    }
    else
    {
      mouseAnchor = pos;
      getSelection().deselect();
    }
    getEditor().moveCaretPosition(pos);
    getEditor().updateAnchorX();

    if ((event.getClickCount() % 2) == 0)
    {
      Line line = getModel().getLine(pos.line);
      Segment seg = line.getSegmentAt(pos.column);
      if (seg == null)
      {
        int len = line.segments.length;
        if (len > 1) seg = line.segments[len - 2];
        else if (len > 0) seg = line.segments[len - 1];
      }
      if (seg != null)
      {
        Position start = new Position(pos.line, seg.offset);
        Position end = new Position(pos.line, seg.offset + seg.length);
        getSelection().select(start, end);
      }
    }

    else if ((event.getClickCount() % 3) == 0)
    {
      Line line = getModel().getLine(pos.line);
      Position start = new Position(pos.line, 0);
      Position end = new Position(pos.line + 1, 0);

      Position docEnd = getModel().getEndPosition();
      if (end.compareTo(docEnd) > 0) end = docEnd;

      getSelection().select(start, end);
      getEditor().moveCaretPosition(pos); // move caret back
    }
  }

  /**
   * Callback when mouseReleased() on BTextEditor.
   */
  public void mouseReleased(BMouseEvent event)
  {
    BTextEditor editor = getEditor();
    if (!UiEnv.get().hasKeyboard() && editor.isEditable())
    {
      UiEnv.get().input(editor);
      return;
    }
  }

  /**
   * Callback when mouseEntered() on BTextEditor.  The
   * default implementation is to change the cursor to
   * the text cursor.
   */
  public void mouseEntered(BMouseEvent event)
  {
    getEditor().setMouseCursor(MouseCursor.text);
  }

  /**
   * Callback when mouseExited() on BTextEditor.  The
   * default implementation is to change the cursor to
   * the normal cursor.
   */
  public void mouseExited(BMouseEvent event)
  {
    getEditor().setMouseCursor(MouseCursor.normal);
  }

  /**
   * Callback when mouseMoved() on BTextEditor.
   */
  public void mouseMoved(BMouseEvent event)
  {
  }

  /**
   * Callback when mouseDragged() on BTextEditor.  The
   * default implementation selects the drag from the
   * anchor position set at mouse press.
   */
  public void mouseDragged(BMouseEvent event)
  {
    //issue 10913, only button 1 drags should highlight text
    if (event.isButton1Down())
    {
      Position pos = mouseEventToPosition(event);
      if (!pos.equals(getEditor().getCaretPosition()))
      {
        getSelection().select(mouseAnchor, pos);
        getEditor().moveCaretPosition(pos);
      }
    }
  }

  /**
   * Callback when mousePulsed() on BTextEditor.
   */
  public void mousePulsed(BMouseEvent event)
  {
  }

  /**
   * Translate a mouse position to a document position.
   */
  private Position mouseEventToPosition(BMouseEvent event)
  {
    Position pos = getEditor().getPositionAt(event.getX(), event.getY());
    Position end = getModel().getEndPosition();
    int line = pos.line;
    int col = pos.column;

    if (line < 0) line = 0;
    else if (line > end.line) line = end.line;

    if (col < 0) col = 0;
    else if (line == end.line && col > end.column) col = end.column;

    return new Position(line, col);
  }

////////////////////////////////////////////////////////////////
//InputMethodRequests
////////////////////////////////////////////////////////////////

  // These methods are not used because Niagara has its own event handlers,
  //  but the FocusManager must implement the InputMethodRequests in order
  //  for the InputMethodEvents to be generated, so these methods must exist.
  public AttributedCharacterIterator cancelLatestCommittedText(Attribute[] attributes)
  {
    return null;
  }

  public AttributedCharacterIterator getCommittedText(int beginIndex, int endIndex, Attribute[] attributes)
  {
    AttributedString string = new AttributedString(committedText.toString());
    return string.getIterator(null, beginIndex, endIndex);
  }

  public int getCommittedTextLength()
  {
    return committedText.length();
  }

  public int getInsertPositionOffset()
  {
    return 0;
  }

  public Object getLocationOffset(int x, int y)
  {
    return impl.getLocationOffset(x, y);
  }

  public AttributedCharacterIterator getSelectedText(Attribute[] attributes)
  {
    return null;
  }

  public Object getTextLocation(Object offset)
  {
    return impl.getTextLocation(offset);
  }



////////////////////////////////////////////////////////////////
//InputMethod support
////////////////////////////////////////////////////////////////

  public int getTextBeginIndex()
  {
    return composedStart;
  }

  public int getTextEndIndex()
  {
    return composedEnd;
  }

  public void setTextBeginIndex(int index)
  {
    composedStart = index;
  }

  public void setTextEndIndex(int index)
  {
    composedEnd = index;
  }


  public Position getComposedPosition()
  {
    return composedOriginPos;
  }

  public void setComposedPosition(Position pos)
  {
    composedOriginPos = pos;
  }

  // Committed text derived from the input method event
  private StringBuffer committedText = new StringBuffer();

  // Composed text received from the input method event
  private AttributedCharacterIterator composedText = null;

  // Position of the start of the composed text
  private Position composedOriginPos = null;

  // Start and end index of text being composed
  private int composedStart = 0;
  private int composedEnd = 0;

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  static final char BACKSPACE = 0x08;
  static final char TAB = '\t';
  static final char NEWLINE = 0x0A;
  static final char ESCAPE = 0x1B;
  static final char DELETE = 0x7F;

  private Position mouseAnchor;


  protected final ITextControllerImpl impl;

////////////////////////////////////////////////////////////////
// ITextControllerImpl
////////////////////////////////////////////////////////////////

  public class TextControllerImpl
    implements ITextControllerImpl
  {
    public TextControllerImpl(TextController textController){
      this.textController = textController;
    }
    private TextController textController;
  }
  /**
   * An interface for a TextController implementation. This is required because
   * TextController has direct AWT dependencies. Because an instance of a TextController
   * could be used on an Hx Px page on a JACE (running Java 8 Compact 3),
   * the AWT dependencies need to be dynamically loaded at runtime.
   * <p>
   * Any implementations require a constructor that takes a TextController instance
   * as an argument.
   */
  public interface ITextControllerImpl
  {
    default Object getLocationOffset(int x, int y)
    {
      return null;
    }

    default Attribute[] getIMAtrributes(){
      return new Attribute[0];
    }

    /**
     * @param offset TextHitInfo
     * @return Rectangle
     */
    default Object getTextLocation(Object offset)
    {
      return null;
    }
  }
}