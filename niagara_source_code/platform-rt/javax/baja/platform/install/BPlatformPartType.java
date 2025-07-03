/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;

/**
 * Represents one of the types a PlatformPart can have
 * 
 * @author    Matt Boon       
 * @creation  3 Apr 07
 * @version   $Revision: 3$ $Date: 5/1/07 3:15:15 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("arch"),
    @Range("brand"),
    @Range("model"),
    @Range("module"),
    @Range("nre"),
    @Range("os"),
    @Range("vm"),
    @Range("other")
  }
)
public final class BPlatformPartType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.platform.install.BPlatformPartType(1369674257)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for arch. */
  public static final int ARCH = 0;
  /** Ordinal value for brand. */
  public static final int BRAND = 1;
  /** Ordinal value for model. */
  public static final int MODEL = 2;
  /** Ordinal value for module. */
  public static final int MODULE = 3;
  /** Ordinal value for nre. */
  public static final int NRE = 4;
  /** Ordinal value for os. */
  public static final int OS = 5;
  /** Ordinal value for vm. */
  public static final int VM = 6;
  /** Ordinal value for other. */
  public static final int OTHER = 7;

  /** BPlatformPartType constant for arch. */
  public static final BPlatformPartType arch = new BPlatformPartType(ARCH);
  /** BPlatformPartType constant for brand. */
  public static final BPlatformPartType brand = new BPlatformPartType(BRAND);
  /** BPlatformPartType constant for model. */
  public static final BPlatformPartType model = new BPlatformPartType(MODEL);
  /** BPlatformPartType constant for module. */
  public static final BPlatformPartType module = new BPlatformPartType(MODULE);
  /** BPlatformPartType constant for nre. */
  public static final BPlatformPartType nre = new BPlatformPartType(NRE);
  /** BPlatformPartType constant for os. */
  public static final BPlatformPartType os = new BPlatformPartType(OS);
  /** BPlatformPartType constant for vm. */
  public static final BPlatformPartType vm = new BPlatformPartType(VM);
  /** BPlatformPartType constant for other. */
  public static final BPlatformPartType other = new BPlatformPartType(OTHER);

  /** Factory method with ordinal. */
  public static BPlatformPartType make(int ordinal)
  {
    return (BPlatformPartType)arch.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BPlatformPartType make(String tag)
  {
    return (BPlatformPartType)arch.getRange().get(tag);
  }

  /** Private constructor. */
  private BPlatformPartType(int ordinal)
  {
    super(ordinal);
  }

  public static final BPlatformPartType DEFAULT = arch;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPlatformPartType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
