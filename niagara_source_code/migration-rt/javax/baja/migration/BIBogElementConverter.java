/**
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.migration;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.baja.io.ValueDocDecoder.ITypeResolver;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInterface;
import javax.baja.sys.BValue;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;
import javax.baja.xml.XElem;


/**
 * BIBogElementConverter
 *
 * This interface is implemented by classes that handle the migration of a
 * particular Niagara AX module or type to Niagara 4.  This may entail removal
 * of the type entirely, or it may require changing the type to a new type,
 * possibly in a new module.
 * <p>
 * Subclasses of BIBogElementConverter should include log entries which will
 * be included in feedback to users of migration tools.  Use the log object from
 * this interface.  Add log.info() entries to indicate items that were changed.
 * Add log.warning() entries to indicate items which will require special user
 * attention.
 *
 * @author     <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *
 */
@NiagaraType
@SuppressWarnings("UnusedParameters")
public interface BIBogElementConverter
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BIBogElementConverter(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIBogElementConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * What modules and types can this converter handle?
   * The default implementation returns an empty list, indicating no types
   * are handled.
   * @return a {@link java.util.List} of Strings of types and modules handled by this converter.
   */
  default List<String> getConvertTypes()
  {
    return Collections.emptyList();
  }

  /**
   * Convert the given XElem to the new type.
   * <p>
   * This is used in the offline conversion of a bog file.
   * The default implementation simply returns the original element.
   * Subclasses can override to remove a bog element, or modify its type or
   * properties to something else.  With no {@link javax.baja.util.Version} argument, this
   * method calls {@code convertXElem()} with Version 3.8.
   * @param x XElem representing a bog element to be converted.
   * @param typeSpecName the typespec name.
   * @return null if the element should be removed, or the XElem with the new element.
   */
  default XElem convertXElem(XElem x, String typeSpecName)
    throws Exception
  {
    return convertXElem(x, typeSpecName, VERSION_3_8);
  }

  /**
   * Convert the given XElem to the new type.
   * <p>
   * This is used in the offline conversion of a bog file.
   * The default implementation simply returns the original element.
   * Subclasses can override to remove a bog element, or modify its type or
   * properties to something else.
   * @param x XElem representing a bog element to be converted.
   * @param typeSpecName the typespec name.
   * @param sourceVersion the {@link Version} of the source being converted.
   * @return null if the element should be removed, or the XElem with the new element.
   */
  default XElem convertXElem(XElem x, String typeSpecName, Version sourceVersion)
    throws Exception
  {
    return x;
  }

  /**
   * Convert the given XElem to the new type while using a deep copy of the bog.
   * <p>
   * This is used in the offline conversion of a bog file.
   * The default implementation simply calls convertXElem without a root element.
   * Subclasses can override to remove a bog element, or modify its type or
   * properties to something else.
   *
   * @since Niagara 4.8
   * @param x XElem representing a bog element to be converted.
   * @param typeSpecName the typespec name.
   * @param sourceVersion the {@link Version} of the source being converted.
   * @param root XElem representing the bog's root element. This element is not expected to be modified.
   * @return null if the element should be removed, or the XElem with the new element.
   */
  default XElem convertXElem(XElem x, String typeSpecName, Version sourceVersion, XElem root)
    throws Exception
  {
    return convertXElem(x, typeSpecName, sourceVersion);
  }

  /**
   * Perform any conversion that must be done with a Component tree view of the bog.
   * @param root the root station component
   * @param resolver bog type resolver, used for resolving bog elements
   * @param toConvert the {@link BComplex} to be converted
   * @param sourceVersion the {@link Version} of the source being converted.
   */
  default void convertComplex(BComponent root, ITypeResolver resolver, BComplex toConvert, Version sourceVersion)
    throws Exception
  {
  }

  /**
   * Generate a new instance of the specified type in the specified module.
   * This is used during bog decoding when an obsoleted or removed type has
   * been replaced by a new type, and the handler needs to provide an on-the-fly
   * updated type.
   * @param moduleName the module name
   * @param typeName the type name
   * @return a new instance of the type specified by the module and type names
   */
  default BValue newInstance(String moduleName, String typeName)
  {
    return null;
  }

  /**
   * Returns an indication that the ord at the specified property can be resolved.
   * Some ords are not intended to be resolved locally.  This method calls classes
   * to constrain the migration process by indicating that no attempt should be
   * made to resolve any of the ords in the child property.
   *
   * @param obj reference object
   * @param prop property of ord
   * @return  true if the ord at the specified property can be resolved in the local station
   */
  default boolean resolvableOrd(BComplex obj, Property prop)
  {
    return true;
  }

  /**
   * The given property in the specified object is a relative ord.  Return the absolute
   * ord of the reference object used to resolve that ord.  If unknown or the specified object is
   * the reference for the ord return BOrd.NULL.
   *
   * @param obj reference object
   * @param prop property of ord
   * @return  the absolute ord for the relative ord in the given object property
   */
  default BOrd getOrdBase(BComplex obj, Property prop)
  {
    return BOrd.NULL;
  }

  /**
   * Given an ord referencing a slot with an original property name return an equivalent
   * ord referencing new name.  If there is no valid equivalent return null.
   * This callback is used during station migration.   This call is only made
   * if the originalOrd will not resolve.
   *
   * @param originalOrd the original property name to be converted
   * @return  converted ord or null
   */
  default BOrd fixOrd(BOrd originalOrd)
  {
    return null;
  }

  /**
   * Given the original typeSpec return modified typeSpec if the original type changed or
   * it was moved to new module or module was renamed.
   * @param typeSpecName the original typeSpec name
   * @return a new typeSpec
   */
  default String newTypeSpec(String typeSpecName)
  {
    return typeSpecName;
  }

  /**
   * Return a list of package conversions strings. Entries shall have the form
   * "origMod:origPackage,newMod:newPackage".
   * (i.e. "historyFunc:com.tridium.historyFunc,history:com.tridium.history" )
   * @return  list of package conversion Strings
   */
  default List<String> getPackageConversions()
  {
    return null;
  }

  static XElem typeRemoved(String typeName)
  {
    return new XElem(TYPE_REMOVED).addAttr(TYPE_ATTR, typeName);
  }

  static XElem moduleRemoved(String moduleName)
  {
    return new XElem(MODULE_REMOVED).addAttr(MODULE_ATTR, moduleName);
  }

  Logger log = Logger.getLogger("migration");

  String TYPE_REMOVED = "typeRemoved";
  String MODULE_REMOVED = "moduleRemoved";
  String TYPE_ATTR = "t";
  String MODULE_ATTR = "m";
  Version VERSION_3_8 = new Version("3.8");
}
