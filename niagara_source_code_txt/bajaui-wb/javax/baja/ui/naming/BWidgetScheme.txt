/*    
 * Copyright 2005 Tridium, Inc.  All rights reserved.
 * 
 */
package javax.baja.ui.naming;

import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SlotPath;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BComplex;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.BWidgetShell;

/**
 * BWidgetScheme provides a mechanism for locating BComponents
 * and BStructs inside a BRootContainer.
 *
 * @author    Mike Jarmy on 12 Dec 06
 * @version   $Revision: 3$ $Date: 1/24/08 9:36:26 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "widget"
)
@NiagaraSingleton
public class BWidgetScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.naming.BWidgetScheme(1887614964)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BWidgetScheme INSTANCE = new BWidgetScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWidgetScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  protected BWidgetScheme()
  {
    super("widget");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * @return an instance of SlotPath.
   */
  public OrdQuery parse(String queryBody)
  {
    return new SlotPath("widget", queryBody);
  }

  /**
   * @return an OrdTarget that will resolve to a BComplex.
   */
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    try
    {
      BComplex from = ensureResolvable((BComplex) base.get());
      SlotPath path = (SlotPath) query;

      BComplex cur = (path.isAbsolute()) ? getRoot(from) : from;

      // back up
      for (int i = 0; i < path.getBackupDepth(); i++)
        cur = ensureResolvable(cur.getParent());

      // go forwards
      for (int i = 0; i < path.depth(); i++)
        cur = ensureResolvable((BComplex) cur.get(path.nameAt(i)));

      return new OrdTarget(base, cur);
    }
    catch (Exception e)
    {
      throw new UnresolvedException("Could not resolve " + base + ", " + query, e);
    }    
  }

////////////////////////////////////////////////////////////////
// package
////////////////////////////////////////////////////////////////

  /**
   * getRoot
   */
  BComplex getRoot(BComplex child)
  {
    while (true)
    {
      BComplex parent = child.getParent();
      if (parent == null || parent instanceof BRootContainer) break;

      child = parent;
    }

    return child;
  }

  /**
   * ensureResolvable
   */
  BComplex ensureResolvable(BComplex w)
  {
    if (w == null)
      throw new NullPointerException();

    if (w instanceof BRootContainer)
      throw new IllegalStateException(
        "Widgets of type " + w.getType() + " cannot be resolved.");

    return w;
  }

////////////////////////////////////////////////////////////////
// helper
////////////////////////////////////////////////////////////////

  /**
   * make a widget scheme ord for the widget
   */
  public static BOrd makeWidgetOrd(BWidget widget)
  {
    Array<String> arr = new Array<>(String.class);
    while (true)
    {
      BWidget parent = widget.getParentWidget();

      if (parent == null ||
          parent instanceof BRootContainer ||
          parent instanceof BWidgetShell)
      {
        break;
      }

      arr.add(widget.getName());
      widget = parent;
    }

    String path = TextUtil.join(arr.reverse().trim(), ('/'));
    return BOrd.make("widget:/" + path);
  }
}
