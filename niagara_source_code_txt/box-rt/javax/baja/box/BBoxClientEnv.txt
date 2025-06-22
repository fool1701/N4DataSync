/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.box;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <p>Represents a BOX client environment. Every client environment for BOX
 * should extend this class.</p>
 *
 * @author    gjohnson on 20 Oct 2010
 * @since     Niagara 3.7
 */
@NiagaraType
public abstract class BBoxClientEnv
    extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.box.BBoxClientEnv(2979906276)1.0$ @*/
/* Generated Thu Nov 18 16:22:08 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBoxClientEnv.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  protected BBoxClientEnv() {}

  /**
   * @since Niagara 4.2
   * @return the kind of runtime environment in which this client environment is
   * currently operating.
   */
  public abstract EnvironmentType getEnvironmentType();

  /**
   * The different types of environments in which BOX can currently operate.
   */
  public enum EnvironmentType
  {
    STATION,
    WORKBENCH
  }
}
