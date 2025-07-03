/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import javax.baja.file.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.util.Lexicon;

/**
 * BSpyScheme is used to lookup Spy with a FilePath.
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 3$ $Date: 4/1/03 1:46:04 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "spy"
)
@NiagaraSingleton
public class BSpyScheme
  extends BSpaceScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.spy.BSpyScheme(3551876753)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BSpyScheme INSTANCE = new BSpyScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpyScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BSpyScheme()
  {
    super("spy");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of FilePath.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new FilePath("spy", queryBody);
  }

  /**
   * The spy scheme maps to "baja:SpySpace".
   */
  @Override
  public Type getSpaceType()
  {
    return BSpySpace.TYPE;
  }
  
  /**
   * Resolve using resolveSpy
   * Only Super Users or local Users of workbench should have access to SPY 
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query, BSpace space)
  {
    if(base.getUser() == null || base.getUser().getPermissions().isSuperUser())
	  {  
      FilePath path = (FilePath)query;
      BSpySpace ss = (BSpySpace)space;
      return new OrdTarget(base, ss.resolveSpy(path));
    } else {
      Lexicon lexicon = Lexicon.make("baja", base.getLanguage());
      throw new UnresolvedException(lexicon.getText("NonSuperUserUnresolvedSpyOrdException"));
    }
  }  
}
