/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComplex;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSpyReset provides a mechanism to do a reset on statistics collected for spy
 * pages from the spy page. If a SpyReset property is accessed it will call
 * spyReset on parent and then redisplay the parents spyPage.
 * <p>
 *
 * @author Robert Adams
 * @creation 14 June 2012
 * @since Niagara 3.7
 */
@NiagaraType
public class BSpyReset
  extends BVector
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BSpyReset(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:23:11 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpyReset.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor.
   */
  public BSpyReset() {}

  @Override
  public String toString(Context context)
  {
    return "view this property in spy page to reset comm statistics";
  }

////////////////////////////////////////////////////////////////
//Spy
////////////////////////////////////////////////////////////////

  /**
   * Provide some spy debug
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    BComplex p = getParent();

    // Reset the comm statistics
    if (p instanceof BCommConfig)
    {
      ((BCommConfig)p).spyReset();
    }

    // message to user
    out.write("<br><b>comm statistics are reset</b><br><br>");

    // Create return link
    String b = out.getPath().getBody();
    out.startTable(false).tr().td().w("<b>")
      .a(b.substring(0, b.lastIndexOf('/')), "return")
      .w("</b>").endTd().endTr().endTable();
  }
}
