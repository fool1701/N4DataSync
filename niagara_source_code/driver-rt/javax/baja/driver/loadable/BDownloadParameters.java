/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BDownloadParameters is the base class for arguments
 * which are used to tailor the download process.
 *
 * @author    Brian Frank
 * @creation  13 Jan 01
 * @version   $Revision: 6$ $Date: 3/10/04 8:39:11 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDownloadParameters
  extends BLoadableActionParameters
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.loadable.BDownloadParameters(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDownloadParameters.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor.
   */
  public BDownloadParameters() {}

  /**
   * Constructor.
   * @param recursive
   */
  public BDownloadParameters(boolean recursive)
  {
    super(recursive);
  }


}
