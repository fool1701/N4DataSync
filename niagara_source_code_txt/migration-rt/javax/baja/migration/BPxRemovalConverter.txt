/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.migration;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;
import javax.baja.xml.XElem;

/**
 * BPxRemovalConverter
 * This removal converter handles the removal of Px elements when the entire
 * Px element that provided this type has been removed for Niagara 4.
 *
 * @author     Erik Test
 *
 */
@NiagaraType
public class BPxRemovalConverter
  extends BObject
  implements BIPxElementConverter
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.migration.BPxRemovalConverter(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:16:47 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxRemovalConverter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @SuppressWarnings("UnusedDeclaration")
  public BPxRemovalConverter()
  {
  }

  /**
   * Create a module removal converter from the given module name.
   * This also accepts a type name and extracts the module name from it.
   * @param moduleOrTypeName the module name or typespec name.
   */
  BPxRemovalConverter(String moduleOrTypeName)
  {
    myModule = moduleOrTypeName;
    if (moduleOrTypeName != null)
    {
      int colon = moduleOrTypeName.indexOf(":");
      if (colon >= 0)
        myModule = moduleOrTypeName.substring(0,colon);
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
   * @param sourceVersion the {@link Version} of the source being converted.
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
