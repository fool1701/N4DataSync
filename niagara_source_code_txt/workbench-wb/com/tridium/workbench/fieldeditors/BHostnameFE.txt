/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;

import com.tridium.nre.util.IPAddressUtil;

/**
 * A field editor for hostnames. Validates that the hostname is the correct
 * format (e.g. www.domain.com) or (.domain.com).
 *
 * @author Melanie Coggan
 * @creation 8/26/2016
 * @since Niagara 4.3
 */
@NiagaraType
public class BHostnameFE
  extends BStringFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BHostnameFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHostnameFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////
  @Override
  protected void doLoadValue(BObject value, Context cx)
  {
    super.doLoadValue(value, cx);
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException
  {
    Lexicon lex = Lexicon.make(BHostnameFE.class);
    BString hostname = (BString) super.doSaveValue(value, cx);

    if (hostname.getString().isEmpty())
    {
      return hostname;
    }

    // Check that the hostname is in a valid format
    if (!IPAddressUtil.isHostname(hostname.getString()))
    {
      throw new CannotSaveException(lex.get(LEX_EXCEPTION_HOST), true);
    }

    return hostname;
  }


  ////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  private static final String LEX_EXCEPTION_HOST = "HostnameFE.exception.invalid";
}
