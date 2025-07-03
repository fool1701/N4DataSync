/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Optional;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIObject;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A DataPolicyRef provides reference to a DataPolicy.
 *
 * @author Andrew Saunders
 */
@NiagaraType
public interface BIDataPolicy
  extends BIObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tag.BIDataPolicy(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDataPolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  Optional<DataPolicy> getDataPolicy();
}
