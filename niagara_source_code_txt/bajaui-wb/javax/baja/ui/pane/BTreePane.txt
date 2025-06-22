/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.tree.*;

import com.tridium.ui.theme.*;

/**
 * BTreePane is a special BScrollPane which is designed 
 * to provide scrolling support to a BTree.
 *
 * @author    John Sublett
 * @creation  12 Dec 01
 * @version   $Revision: 8$ $Date: 3/28/05 10:32:29 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BTree()",
  override = true
)
public class BTreePane
  extends BScrollPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BTreePane(3051143255)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BTree(), null);

  //endregion Property "content"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTreePane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with tree for content.
   */
  public BTreePane(BTree tree)
  {
    this();
    setContent(tree);
  }

  /**
   * No argument constructor.
   */
  public BTreePane()
  {
    getVscrollBar().setSnapToUnitIncrement(true);
  }

////////////////////////////////////////////////////////////////
// Tree
////////////////////////////////////////////////////////////////

  /**
   * Get the underlying BTree for this pane.
   */
  public BTree getTree()
  {
    return (BTree)getContent();
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Layout the text editor.
   */
  public void doLayout(BWidget[] kids)
  {
    BTree tree = getTree();
    double rowHeight = tree.getRowHeight();

    BScrollBar vsb = getVscrollBar();
    vsb.setUnitIncrement((int)rowHeight);
    vsb.setBlockIncrement((int)(rowHeight * 5));
    
    BScrollBar hsb = getHscrollBar();
    hsb.setUnitIncrement((int)Theme.tree().getExpanderWidth());
    hsb.setBlockIncrement(hsb.getUnitIncrement() * 5);
    super.doLayout(kids);
  }
}
