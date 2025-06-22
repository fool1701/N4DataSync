/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.migration;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;
import javax.baja.xml.XElem;

/**
 * BIPxElementConverter
 *
 * This interface is implemented by classes that handle the migration of a
 * particular Niagara AX PxElement to Niagara 4.  This may entail removal
 * of the PxElement or changing the AX type to an N4 new type, possibly in a
 * new module.
 * <p>
 * Subclasses of BIPxElementConverter should include log entries which will
 * be included in feedback to users of migration tools.  Use the log object from
 * this interface.  Add log.info() entries to indicate items that were changed.
 * Add log.warning() entries to indicate items which will require special user
 * attention.
 *
 * @author     Erik Test
 * @since      4.4
 */

@NiagaraType
public interface BIPxElementConverter extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BIPxElementConverter(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIPxElementConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * The default implementation returns an empty list, indicating no type specs
   * are handled.
   * @return a {@link java.util.List} of Strings of types and modules handled by this converter.
   */
  default List<String> getConvertTypeSpecs()
  {
    return Collections.emptyList();
  }

  /**
   * Return a list of package conversions strings. Entries shall have the form
   * "origMod:origPackage,newMod:newPackage".
   * (i.e. "historyFunc:com.tridium.historyFunc,history:com.tridium.history" )
   * @return  list of package conversion Strings
   */
  default List<String> getPackageConversions()
  {
    return Collections.emptyList();
  }

  /**
   * Convert the given XElem to the new type.
   * <p>
   * This is used in the offline conversion of a Px file.
   * The default implementation simply returns the original element.
   * Subclasses can override to remove a Px element, or modify its type or
   * properties to something else.
   * @param x XElem representing a Px element to be converted.
   * @param typeName the typespec name.
   * @param sourceVersion the {@link javax.baja.util.Version} of the source being converted.
   * @return null if the element should be removed, or the XElem with the new element.
   */
  default XElem convertXElem(XElem x, String typeName, Version sourceVersion)
      throws Exception
  {
    return x;
  }

  /**
   * This is used during Px decoding when an obsoleted or removed type has
   * been replaced by a new type, and the handler needs to provide an on-the-fly
   * updated type.
   * @param typeName the type name
   * @return a new instance of the type specified by the module and type names
   */
  default BValue newInstance(String typeName)
  {
    return null;
  }

  /**
   * Given the original type, return modified type if the original type changed or
   * it was moved to new module or module was renamed.
   * @param typeName the original typeSpec name
   * @return a new typeSpec
   */
  default Optional<String> newTypeName(String typeName)
  {
    return Optional.empty();
  }

  /**
   * Given the original type name, return the modified typeSpec if the original type changed or
   * it was moved to new module or module was renamed.
   * @param typeName the original typeSpec name
   * @return a new typeSpec
   */
  default Optional<String> newTypeSpec(String typeName)
  {
    return Optional.empty();
  }

  /**
   * Given the original module return modified module if the original changed or
   * module was renamed.
   * @param moduleName the original module name
   * @return a new module name
   */
  default Optional<String> newModule(String moduleName)
  {
    return Optional.empty();
  }

  Logger log = Logger.getLogger("migration");
}
