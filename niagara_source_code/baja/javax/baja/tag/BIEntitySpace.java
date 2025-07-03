/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BISpace;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * EntitySpace
 *
 * @author John Sublett
 * @creation 3/6/14
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIEntitySpace
  extends BISpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tag.BIEntitySpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIEntitySpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the TagDictionaryService that is in effect
   * for this space.
   *
   * @return Returns the TagDictionaryService for this space.
   */
  TagDictionaryService getTagDictionaryService();

  /**
   * Set a TagDictionaryService to be referenced by this space.
   * This allows the space objects to include tags implied by
   * an installed SmartTagDictionary.  If a service has
   * already been set, it will be replaced by this one.
   *
   * @param service The service to be used by the space.
   */
  void setTagDictionaryService(TagDictionaryService service);

  /**
   * Remove a TagDictionaryService from being referenced by this
   * space.  If the service is not the current service that is
   * set on the space, this has no effect.
   *
   * @param service The service to remove.
   */
  void removeTagDictionaryService(TagDictionaryService service);
}
