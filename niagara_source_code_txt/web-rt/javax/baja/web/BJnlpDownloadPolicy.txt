/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Specifies a JNLP file download policy.
 *
 * @author Gareth Johnson on 01/06/2015
 * @since Niagara 4.0
 * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("whenNotAvailable"),
    @Range("never"),
    @Range("always")
  }
)
@Deprecated
public final class BJnlpDownloadPolicy extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BJnlpDownloadPolicy(3904792876)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for whenNotAvailable. */
  public static final int WHEN_NOT_AVAILABLE = 0;
  /** Ordinal value for never. */
  public static final int NEVER = 1;
  /** Ordinal value for always. */
  public static final int ALWAYS = 2;

  /** BJnlpDownloadPolicy constant for whenNotAvailable. */
  public static final BJnlpDownloadPolicy whenNotAvailable = new BJnlpDownloadPolicy(WHEN_NOT_AVAILABLE);
  /** BJnlpDownloadPolicy constant for never. */
  public static final BJnlpDownloadPolicy never = new BJnlpDownloadPolicy(NEVER);
  /** BJnlpDownloadPolicy constant for always. */
  public static final BJnlpDownloadPolicy always = new BJnlpDownloadPolicy(ALWAYS);

  /** Factory method with ordinal. */
  public static BJnlpDownloadPolicy make(int ordinal)
  {
    return (BJnlpDownloadPolicy)whenNotAvailable.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BJnlpDownloadPolicy make(String tag)
  {
    return (BJnlpDownloadPolicy)whenNotAvailable.getRange().get(tag);
  }

  /** Private constructor. */
  private BJnlpDownloadPolicy(int ordinal)
  {
    super(ordinal);
  }

  public static final BJnlpDownloadPolicy DEFAULT = whenNotAvailable;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJnlpDownloadPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
