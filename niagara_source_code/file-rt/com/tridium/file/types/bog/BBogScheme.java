/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.bog;

import javax.baja.naming.BOrdScheme;
import javax.baja.naming.InvalidOrdBaseException;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BBogScheme is the portal from BogFile to BogSpace.
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 2$ $Date: 8/28/07 5:41:54 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "bog"
)
@NiagaraSingleton
public class BBogScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.types.bog.BBogScheme(952903566)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BBogScheme INSTANCE = new BBogScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBogScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BBogScheme()
  {
    super("bog");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return base.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    if (!(base.get() instanceof BBogFile))
    {
      throw new InvalidOrdBaseException("Must be BogFile: " + base.get().getType());
    }

    BBogFile bogFile = (BBogFile)base.get();
    BBogSpace space = (BBogSpace)bogFile.open();
    return new OrdTarget(base, space);
  }
}
