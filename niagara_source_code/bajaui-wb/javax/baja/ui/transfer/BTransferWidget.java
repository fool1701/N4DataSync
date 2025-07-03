/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;
import javax.baja.ui.util.*;

import com.tridium.ui.*;

/**
 * BTransferWidget is the base class for BWidgets which
 * support the transfer framework.  Transfer includes
 * cut/copy/paste and drag-and-drop.
 *
 * @author    Brian Frank       
 * @creation  6 Mar 02
 * @version   $Revision: 26$ $Date: 6/30/10 9:52:39 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BTransferWidget
  extends BWidget
  implements TransferConst
{       
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.transfer.BTransferWidget(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTransferWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Clipboard Enables
////////////////////////////////////////////////////////////////  

  /**
   * Is the cut command enabled on this widget.
   */
  public boolean isCutEnabled()
  {
    return cutEnabled;
  }
  
  /**
   * Set the cut command's enable state for this widget.
   */
  public void setCutEnabled(boolean cutEnabled)
  {
    if (this.cutEnabled == cutEnabled) return;   
    this.cutEnabled = cutEnabled;   
    updateStates(); 
  }

  /**
   * Is the copy command enabled on this widget.
   */
  public boolean isCopyEnabled()
  {
    return copyEnabled;
  }
  
  /**
   * Set the copy command's enable state for this widget.
   */
  public void setCopyEnabled(boolean copyEnabled)
  {
    if (this.copyEnabled == copyEnabled) return;
    this.copyEnabled = copyEnabled;
    updateStates(); 
  }

  /**
   * Is the paste command enabled on this widget.
   */
  public boolean isPasteEnabled()
  {
    return pasteEnabled;
  }
  
  /**
   * Set the paste command's enable state for this widget.
   */
  public void setPasteEnabled(boolean pasteEnabled)
  {
    if (this.pasteEnabled == pasteEnabled) return;
    this.pasteEnabled = pasteEnabled;
    updateStates();
  }

  /**
   * Is the paste special command enabled on this widget.
   */
  public boolean isPasteSpecialEnabled()
  {
    return pasteSpecialEnabled;
  }
  
  /**
   * Set the paste special command's enable state for this widget.
   */
  public void setPasteSpecialEnabled(boolean pasteSpecialEnabled)
  {
    if (this.pasteSpecialEnabled == pasteSpecialEnabled) return;
    this.pasteSpecialEnabled = pasteSpecialEnabled;
    updateStates();
  }

  /**
   * Is the duplicate command enabled on this widget.
   */
  public boolean isDuplicateEnabled()
  {
    return duplicateEnabled;
  }
  
  /**
   * Set the duplicate command's enable state for this widget.
   */
  public void setDuplicateEnabled(boolean duplicateEnabled)
  {
    if (this.duplicateEnabled == duplicateEnabled) return;
    this.duplicateEnabled = duplicateEnabled;
    updateStates();
  }

  /**
   * Is the delete command enabled on this widget.
   */
  public boolean isDeleteEnabled()
  {
    return deleteEnabled;
  }

  /**
   * Set the delete command's enable state for this widget.
   */
  public void setDeleteEnabled(boolean deleteEnabled)
  {
    if (this.deleteEnabled == deleteEnabled) return;
    this.deleteEnabled = deleteEnabled;
    updateStates();
  }

  /**
   * Is the rename command enabled on this widget.
   */
  public boolean isRenameEnabled()
  {
    return renameEnabled;
  }

  /**
   * Set the rename command's enable state for this widget.
   */
  public void setRenameEnabled(boolean renameEnabled)
  {
    if (this.renameEnabled == renameEnabled) return;
    this.renameEnabled = renameEnabled;
    updateStates();
  }

  /**
   * If this guy is inside a BDeluxePlugin, then 
   * attempt to match transfer states.
   */
  private void updateStates()
  {
    BWidgetShell shell = getShell();
    if (shell instanceof NiagaraWbShell)
      ((NiagaraWbShell)shell).updateTransferWidgetStates();
  }
  
////////////////////////////////////////////////////////////////
// Drag and Drop
////////////////////////////////////////////////////////////////  

  /**
   * This method starts a drag operation within in the process.  
   * The most common way to start a drag is the mouseDragStarted() 
   * callback.  The startDrag() method should be passed the mouse 
   * operation which started the drag as well as the data which 
   * will be transferred (usually created via makeTransferContext()).  
   * <p>
   * The DragRenderer passed to this method is used to
   * paint an effect under the mouse cursor representing
   * the data being dragged. 
   *
   * @param event MouseEvent which originated the drag gesture.
   * @param context The TransferContext which will be used during
   *   the drag operation.  It is immaterial what action is specified
   *   as both the action and coordinate will be updated as the mouse
   *   is dragged over potential drop targets.
   * @param dragRenderer Is used to visualize the data being dragged
   *   as the mouse is dragged around.
   */
  public void startDrag(BMouseEvent event, TransferContext context, DragRenderer dragRenderer) 
  {
    ShellManager manager = (ShellManager)widgetSupport(null);
    manager.startDragOperation(this, event, context, dragRenderer);
  }

  /**
   * Convenience for <code>startDrag(event, makeTransferContext(envelope), dragRenderer)</code>
   */
  public void startDrag(BMouseEvent event, TransferEnvelope envelope, DragRenderer dragRenderer)
  {
    startDrag(event, makeTransferContext(envelope), dragRenderer);
  }

  /**
   * This is called when the cursor enters this widget
   * during a drag operation.  The TransferContext specifies
   * the TransferEnvelope being dragged and the location of
   * the mouse cursor over this widget.
   */
  public void dragEnter(TransferContext cx)
  {
  }

  /**
   * This is called when the cursor is dragged over this widget
   * during a drag operation.  The TransferContext specifies
   * the TransferEnvelope being dragged and the location of the mouse 
   * cursor over this widget.
   * <p>
   * If this widget can accept a drop from the data specified
   * in the given TransferContext then return a mask of the
   * available actions that may result.  This mask should be
   * a bitwise OR of the TransferConst.ACTION_x constants.  
   * If the drop is not legal over this widget then return 0 
   * to indicate no legal actions.  The default implementation 
   * returns 0.
   */
  public int dragOver(TransferContext cx)
  {
    return 0;
  }

  /**
   * This is called when the cursor exits this widget
   * during a drag operation.  The TransferContext specifies
   * the TransferEnvelope being dragged and the location of
   * the mouse cursor over this widget.
   */
  public void dragExit(TransferContext cx)
  {
  }
  
  /**
   * This callback is invoked when a drop occurs over this
   * widget during a drag operation.  In order to reach
   * this stage the dragOver() method must have returned a
   * non-zero bit mask of the valid transfer actions.  Return
   * the artifact used to undo the operation.  If dropping
   * the source into a BComponent, then use the dropOnComponent()
   * method.
   */
  public CommandArtifact drop(TransferContext cx)
    throws Exception
  {
    return null;
  }
    
////////////////////////////////////////////////////////////////
// Clipboard
////////////////////////////////////////////////////////////////  
  
  /**
   * Implement the copy command.  Default implementation:
   * <ol>
   * <li>Mark.setCurrent(null)</li>
   * <li>if copy not enabled bail</li>
   * <li>getTransferData(); if null bail</li>
   * <li>put data on clipboard</li>
   * <li>if envelope supports mark format then Mark.setCurrent</li>
   * <li>return null</li>
   * </ol>
   */
  public CommandArtifact doCopy()
    throws Exception
  {    
    setCurrentMark(null);
    
    if (!copyEnabled) return null;    

    TransferEnvelope envelope = getTransferData();
    if (envelope == null) return null;
    
    Clipboard.getDefault().setContents(envelope);
    
    if (envelope.supports(TransferFormat.mark))
    {
      Mark mark = (Mark)envelope.getData(TransferFormat.mark);
      setCurrentMark(mark);
    }
    
    return null;
  }
  
  /**
   * Implement the cut command.  Default implementation:
   * <ol>
   * <li>Mark.setCurrent(null)</li>
   * <li>if cut not enabled bail</li>
   * <li>getTransferData(); if null bail</li>
   * <li>put data on clipboard</li>
   * <li>if envelope supports mark format then Mark.setCurrent</li>
   * <li>if envelope supports mark format then setPendingMove(true)</li>
   * <li>removeTransferData()</li>
   * </ol>
   */
  public CommandArtifact doCut()
    throws Exception
  {
    setCurrentMark(null);
    
    if (!cutEnabled) return null;
    
    TransferEnvelope envelope = getTransferData();
    if (envelope == null) return null;
    
    Clipboard.getDefault().setContents(envelope);

    if (envelope.supports(TransferFormat.mark))
    {
      Mark mark = (Mark)envelope.getData(TransferFormat.mark);
      mark.setPendingMove(true);
      setCurrentMark(mark);
    }
    
    TransferContext cx = makeTransferContext(null, ACTION_MOVE, envelope);
    return removeTransferData(cx);
  }

  /**
   * Implement the paste command.  Default implementation:
   * <ol>
   * <li>if paste not enabled bail</li>
   * <li>get contents from clipboard, if null bail</li>
   * <li>if contents are a mark, then clear pending move flag</li>
   * <li>insertTransferData()</li>
   * </ol>
   */
  public CommandArtifact doPaste()
    throws Exception
  {
    if (!pasteEnabled) return null;
    
    try
    {
      TransferEnvelope envelope = Clipboard.getDefault().getContents();
      if (envelope == null) 
      {
        BDialog.error(this, UiLexicon.bajaui().getText("command.paste.emptyClipboard"));
        return null;
      }
  
      int action = ACTION_COPY;
      if (envelope.supports(TransferFormat.mark))
      {
        Mark mark = (Mark)envelope.getData(TransferFormat.mark);
        if (mark.isPendingMove()) 
        {
          action = ACTION_MOVE;
          
          // we clear the clipboard here because the original 
          // data has been moved (another option would be to 
          // put the new data on the clipboard)
          Clipboard.getDefault().setContents(null);
          setCurrentMark(null);
          mark.setPendingMove(false);
        }
      }
      
      TransferContext cx = makeTransferContext(null, action, envelope);
      return insertTransferData(cx);
    }
    catch(UnsupportedFormatException e)
    {                          
      BDialog.error(this, UiLexicon.bajaui().getText("command.paste.unsupportedFormat"));
      return null;
    }
  }

  /**
   * Implement the paste special command.  Default implementation:
   * <ol>
   * <li>if paste special not enabled bail</li>
   * <li>throw UnsupportedOperation</li>
   * </ol>
   */
  public CommandArtifact doPasteSpecial()
    throws Exception
  {
    if (!pasteSpecialEnabled) return null;
    
    throw new UnsupportedOperationException();
  }
  
  /**
   * Implement the duplicate command.  Default implementation:
   * <ol>
   * <li>if duplicate not enabled bail</li>
   * <li>getTransferData(); if null bail</li>
   * <li>insertTransferData()</li>
   * </ol>
   */
  public CommandArtifact doDuplicate()
    throws Exception
  {
    if (!duplicateEnabled) return null;
    
    TransferEnvelope envelope = getTransferData();
    if (envelope == null) return null;
    
    TransferContext cx = makeTransferContext(null, ACTION_COPY, envelope);
    return insertTransferData(cx);
  }

  /**
   * Implement the delete command.  Default implementation:
   * <ol>
   * <li>if delete not enabled bail</li>
   * <li>throw UnsupportedOperation</li>
   * </ol>
   */
  public CommandArtifact doDelete()
    throws Exception
  {
    if (!deleteEnabled) return null;
    
    throw new UnsupportedOperationException();
  }

  /**
   * Implement the rename command.  Default implementation:
   * <ol>
   * <li>if rename not enabled bail</li>
   * <li>throw UnsupportedOperation</li>
   * </ol>
   */
  public CommandArtifact doRename()
    throws Exception
  {
    if (!renameEnabled) return null;
    
    throw new UnsupportedOperationException();
  }
    
////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /**
   * This is the callback to get the current selection
   * as an instance of TransferEnvelope.  This data will be
   * the source of a cut, copy, or drag operation.
   */
  public abstract TransferEnvelope getTransferData()
    throws Exception;

  /**
   * This callback is used to insert the data into the
   * target widget.  This callback results from a paste 
   * or drop operation.  The data is accessed via the
   * TransferContext.getEnvelope() method.  Return an artifact
   * used to undo the operation or null.
   */
  public abstract CommandArtifact insertTransferData(TransferContext cx)
    throws Exception;

  /**
   * This callback is invoked on the source of a cut or
   * drag-move operation after it has been inserted into
   * the target.  The source should remove the data and
   * return an artifact used to undo the operation or return
   * null if not undoable.
   */
  public abstract CommandArtifact removeTransferData(TransferContext cx)
    throws Exception;

  /**
   * Convenience for <code>makeTransferContext(null, ACTION_COPY, envelope)</code>.
   */
  public final TransferContext makeTransferContext(TransferEnvelope envelope)
  {
    return makeTransferContext(null, ACTION_COPY, envelope);
  }
  
  /**
   * This is a hook for subclasses of BTransferWidget to create 
   * specialized a TransferContext which is used in the standard
   * implementations of doCut(), doPaste(), and doDuplicate().
   */
  public TransferContext makeTransferContext(Context sourceContext, int action, TransferEnvelope envelope)
  {
    return new TransferContext(sourceContext, action, envelope);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * This is a wrapper for Mark.setCurrent() 
   * which repaints the shell afterwards (for
   * the delayed cut effect).
   */
  void setCurrentMark(Mark mark)
  {
    Mark.setCurrent(mark);
    getShell().repaint();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  boolean cutEnabled = false;
  boolean copyEnabled = false;
  boolean pasteEnabled = false;
  boolean pasteSpecialEnabled = false;
  boolean duplicateEnabled = false;
  boolean deleteEnabled = false;
  boolean renameEnabled = false;

}
