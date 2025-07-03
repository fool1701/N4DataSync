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
 * A field editor for hostnames. Validates that the provided String
 * represents a hostname. IPv4 and IPv6 accepted.
 *
 * @author Melanie Coggan
 * @creation 8/25/2016
 * @since Niagara 4.3
 */
@NiagaraType
public class BHTTPHostnameFE
  extends BStringFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BHTTPHostnameFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHTTPHostnameFE.class);

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
  protected BObject doSaveValue(BObject value, Context cx) throws CannotSaveException
  {
    Lexicon lex = Lexicon.make(BHTTPHostnameFE.class);
    BString hostname = (BString)super.doSaveValue(value, cx);
    String hostString = hostname.getString();

    // Check that we start with http:// or https://
    int index = hostString.indexOf("//");
    if (index == -1)
      throw new CannotSaveException(lex.get(LEX_EXCEPTION_SCHEME), true);

    String scheme = hostString.substring(0, index);
    if (!"https:".equals(scheme) && !"http:".equals(scheme))
      throw new CannotSaveException(lex.get(LEX_EXCEPTION_SCHEME), true);

    // Check that we don't have a trailing slash
    if (hostString.endsWith("/"))
      hostString = hostString.substring(0, hostString.length()-1);

    // Check that the hostname is in a valid format
    String host = hostString.substring(index+2);
    if (!IPAddressUtil.isHostname(host) && !IPAddressUtil.isNumericAddr(host))
      throw new CannotSaveException(lex.get(LEX_EXCEPTION_HOST), true);

    return BString.make(hostString);
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  private static final String LEX_EXCEPTION_SCHEME="HTTPHostnameFE.exception.scheme";
  private static final String LEX_EXCEPTION_HOST="HTTPHostnameFE.exception.invalid";
}
