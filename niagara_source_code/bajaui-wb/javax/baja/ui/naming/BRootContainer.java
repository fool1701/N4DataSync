/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.naming;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.pane.*;

/**
 * BRootContainer contains the root widget of a 'widget space'.
 * BWidgetScheme uses BRootContainer to resolve widget queries.
 *
 * @author    Mike Jarmy
 * @creation  13 Dec 06
 * @version   $Revision: 3$ $Date: 4/20/07 9:15:30 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "root",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
public class BRootContainer
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.naming.BRootContainer(1336108483)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "root"

  /**
   * Slot for the {@code root} property.
   * @see #getRoot
   * @see #setRoot
   */
  public static final Property root = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code root} property.
   * @see #root
   */
  public BWidget getRoot() { return (BWidget)get(root); }

  /**
   * Set the {@code root} property.
   * @see #root
   */
  public void setRoot(BWidget v) { set(root, v, null); }

  //endregion Property "root"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRootContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an empty root container.
   */
  public BRootContainer()
  {
  }

  /**
   * Construct a root container with the given root.
   */
  public BRootContainer(BWidget root)
  {
    setRoot(root);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
	{
		BWidget root = getRoot();

    root.computePreferredSize();
		setPreferredSize(
			root.getPreferredWidth(),
			root.getPreferredHeight());
	}

  public void doLayout(BWidget[] kids)
	{
		getRoot().setBounds(0, 0, getWidth(), getHeight());
	}
}
