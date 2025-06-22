/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BUuid;

/**
 * BITemplate is implemented by template files.
 *
 * @author Robert Adams on 23 Jan 2014
 * @since Niagara 3.2
 */
@NiagaraType
public interface BITemplate
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BITemplate(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BITemplate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the name for this BITemplate
   *
   * @return name
   * @since Niagara 4.13
   */
  default String getTemplateName()
  {
    return "template";
  }

  /**
   * Get the vendor name for this BITemplate
   *
   * @return vendor name
   */
  String getVendor();

  /**
   * Get the version name for this BITemplate
   *
   * @return version
   */
  String getVersion();

  /**
   * Get the description string this BITemplate
   *
   * @return version
   */
  String getDescription();

  /**
   * Get the uID string this BITemplate
   *
   * @return uID
   */
  BUuid getUID();

  /**
   * Get BObject tree encapsulate in this template file;
   *
   * @return the root BComponent
   * @throws UnresolvedException
   */
  BComponent getBaseComponent()
    throws UnresolvedException;

}
