/**
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.migration;

import java.util.ArrayList;
import java.util.List;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;
import javax.baja.xml.XElem;

/**
 * BModuleRemovalConverter
 * The module removal converter handles the removal of bog elements when
 * the entire module that provided this type has been removed for Niagara 4.
 * The most common situation is a test module or obsolete driver that was
 * removed during the module cleanup during Niagara 4 development.
 *
 * @author     <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *
 */
@NiagaraType
public class BModuleRemovalConverter
  extends BObject
  implements BIBogElementConverter
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BModuleRemovalConverter(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModuleRemovalConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  private static final List<String> convertTypes = new ArrayList<>();

  /**
   * What modules and types can this converter handle?
   * The ModuleRemovalConverter returns an empty List, because it
   * is the 'fallback' converter for any module that is not found in
   * the system registry.
   * @return a {@link java.util.List} of Strings of types and modules handled by this converter.
   */
  @Override
  public List<String> getConvertTypes()
  {
    return convertTypes;
  }


  @SuppressWarnings("UnusedDeclaration")
  public BModuleRemovalConverter()
  {
  }

  /**
   * Create a module removal converter from the given module name.
   * This also accepts a type name and extracts the module name from it.
   * @param moduleOrTypeName the module name or typespec name.
   */
  public BModuleRemovalConverter(String moduleOrTypeName)
  {
    myModule = moduleOrTypeName;
    if (moduleOrTypeName != null)
    {
      int colon = moduleOrTypeName.indexOf(":");
      if (colon >= 0)
        myModule = moduleOrTypeName.substring(0,colon);
      convertTypes.add(myModule);
    }
  }

  /**
   * Migrate the given XElem to the new type.
   * <p>
   * The BModuleRemovalConverter simply removes any type that was part of the
   * removed module.
   *
   * @param x XElem representing a bog element to be migrated.
   * @param typespecName the typespec name (using the 'preferred symbol' for the module name)
   * @param sourceVersion the {@link javax.baja.util.Version} of the source being converted.
   * @return null if the element should be removed, or the XElem with the new element.
   */
  @Override
  public XElem convertXElem(XElem x, String typespecName, Version sourceVersion)
  {
    String[] moduleAndType = typespecName.split(":");
    if (moduleAndType[MODULE_NDX].equals(myModule))
    {
      // All components which match my module/type should be removed.
      log.severe("Removing " + x.get("n", "unnamedObject") + " of type " + x.get("t", "unknown"));

      return BIBogElementConverter.moduleRemoved(myModule);
    }
    return x;
  }

  /**
   * Given the original typeSpec return modified typeSpec if the original type changed or
   * it was moved to new module or module was renamed.
   * @param typeSpecName the original typeSpec name
   * @return a new typeSpec
   */
  public String newTypeSpec(String typeSpecName)
  {
    return null;
  }

  public String toString(Context c)
  {
    return "RemoveConverter:"+myModule;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final int MODULE_NDX = 0;

  private String myModule = null;
}
