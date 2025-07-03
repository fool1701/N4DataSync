/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.crypto.core.io.CryptoStoreId;

/**
 * This field editor allows selecting a certificate from the user trust store.
 *
 * @author Melanie Coggan
 * @creation 8/11/2016
 * @since Niagara 4.3
 */
@NiagaraType
public class BUserTrustCertificateAliasFE
  extends BCertificateAliasFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BUserTrustCertificateAliasFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserTrustCertificateAliasFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  public BUserTrustCertificateAliasFE()
  {
    super(CryptoStoreId.USER_TRUST_STORE);
  }
}
