/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import java.util.regex.Pattern;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;

/**
 * A field editor for the path portion of the URL.
 *
 * @author Melanie Coggan
 * @creation 8/25/2016
 * @since Niagara 4.3
 */
@NiagaraType
public class BURLPathFE
  extends BStringFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BURLPathFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BURLPathFE.class);

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
    Lexicon lex = Lexicon.make(BURLPathFE.class);
    BString path = (BString)super.doSaveValue(value, cx);
    String pathString = path.getString();

    if (!pathString.startsWith("/"))
      pathString = "/" + pathString;

    if (!pattern.matcher(pathString).matches())
      throw new CannotSaveException(lex.get(LEX_EXCEPTION_MALFORMED), true);

    return BString.make(pathString);
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  private static final Pattern pattern = Pattern.compile("([^?#]*)");
  private static final String LEX_EXCEPTION_MALFORMED = "URLPathFE.exception.malformed";
}
