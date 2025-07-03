/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.naming.BOrdScheme;
import javax.baja.naming.BasicQuery;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdQueryList;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconModule;

/**
 * BNavScheme can traverse a BINavNode further down by providing
 * the names of the desired descendants. If there is more than one
 * child to traverse, use a "/" as the separator. When using the NavScheme, make
 * sure to provide a BINavNode base; the path will be resolved relative to this base.
 *
 * <p>
 * Here are some examples of ords using the nav scheme:
 *  <code>
 *  root:|nav:folder
 *  root:|nav:folder/subFolder
 *  root:|nav:folder/subFolder/myHost
 *  </code>
 *
 * @author JJ Frankovich
 * @since Niagara 4.13
 */
@NiagaraType(
  ordScheme = "nav"
)
@NiagaraSingleton
public class BNavScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavScheme(3496712882)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BNavScheme INSTANCE = new BNavScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private BNavScheme()
  {
    super("nav");
  }


  @Override
  public OrdQuery parse(String queryBody)
  {
    return new NavQuery(getId(), queryBody);
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    BObject baseObject = base.get();
    if(!(baseObject instanceof BINavNode))
    {
      throw new SyntaxException(LEX.getText("navScheme.error.missingNavNodeBase", base));
    }
    BINavNode node = (BINavNode) baseObject;
    checkPermissions(node, base);
    String body = query.getBody();
    if(body.isEmpty())
    {
      throw new SyntaxException(LEX.getText("navScheme.error.emptyBody", base));
    }
    if(body.startsWith("/"))
    {
      throw new SyntaxException(LEX.getText("navScheme.error.startingSlash", base));
    }
    String[] path = TextUtil.split(query.getBody(), '/');
    for (String navName : path)
    {
      if(navName.isEmpty())
      {
        throw new SyntaxException(LEX.getText("navScheme.error.missingNavNodeName", base));
      }
      node = node.getNavChild(navName);
      if(node == null)
      {
        throw new UnresolvedException(LEX.getText("navScheme.error.navNodeMissing", base, navName));
      }
      checkPermissions(node, base);
    }
    return new OrdTarget(base, node.asObject());
  }

  private void checkPermissions(BINavNode node, Context cx)
  {
    //See BNavContainer.java#filter
    if (node instanceof BIProtected)
    {
      BPermissions p = ((BIProtected)node).getPermissions(cx);
      if (!p.has(BPermissions.OPERATOR_READ))
        throw new PermissionException(LEX.getText("navScheme.error.noOperatorRead", cx));
    }
  }

  private static class NavQuery
    extends BasicQuery
  {
    public NavQuery(String scheme, String body)
    {
      super(scheme, body);
    }

    @Override
    public void normalize(OrdQueryList list, int index)
    {
      if (list.isSameScheme(index, index + 1))
      {
        NavQuery append = (NavQuery) list.get(index + 1);
        list.merge(index, merge(append));
      }
    }


    /**
     * Merge this path with the specified path.  Since there is no notion of an
     * absolute path for the NavScheme, each NavQuery will be treated as relative path and
     * will be combined into a single NavQuery with each child name separeted by "/".
     * For example <code>root:|nav:parent|nav:child|nav:grandChild</code> will be normalized to
     * <code>root:|nav:parent/child/grandChild</code>
     */
    public NavQuery merge(NavQuery a)
    {
      return new NavQuery(getScheme(), getBody() + "/" + a.getBody());
    }
  }
  private static final LexiconModule LEX = LexiconModule.make("baja");
}
