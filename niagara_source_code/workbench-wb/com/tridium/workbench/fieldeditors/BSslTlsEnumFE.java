/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import static javax.baja.security.crypto.BSslTlsEnum.TLSV_1_3;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;
import javax.baja.workbench.BWbShell;

import com.tridium.sys.schema.Fw;

/**
 * BSslTlsEnumFE allows editing of BSslTlsEnum.  It takes into account the remote baja
 * module version to ensure only allowed enum values are presented to the user.
 *
 * @author Robert Staley on 2021-05-17
 * @since Niagara 4.11
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:SslTlsEnum"
  )
)
public class BSslTlsEnumFE
  extends BFrozenEnumFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BSslTlsEnumFE(2936491654)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSslTlsEnumFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BFrozenEnumFE
////////////////////////////////////////////////////////////////

  @Override
  protected boolean isValidOrdinal(int ordinal)
  {
    if (getRemoteVersion().compareTo(MIN_TLS_1_3_VERSION) >= 0)
    {
      return super.isValidOrdinal(ordinal);
    }
    else
    {
      return super.isValidOrdinal(ordinal) && ordinal != TLSV_1_3;
    }
  }

////////////////////////////////////////////////////////////////
// Protected Methods
////////////////////////////////////////////////////////////////

  protected Version getRemoteVersion()
  {
    Version bajaVersion = null;
    try
    {
      BWbShell shell = getWbShell();
      BOrd ord = shell.getActiveOrd();
      BComponent comp = (BComponent) ord.get();

      bajaVersion = (Version)comp.fw(Fw.GET_REMOTE_VERSION, "baja", null, null, null);
    }
    catch(Throwable ignore) {}

    if (bajaVersion == null)
    {
      bajaVersion = Sys.getBajaVersion();
    }

    return bajaVersion;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final Version MIN_TLS_1_3_VERSION = new Version("4.11.0.56");
}
