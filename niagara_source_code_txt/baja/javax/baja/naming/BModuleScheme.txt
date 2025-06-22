/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.Optional;

import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BModule;
import javax.baja.sys.BModuleSpace;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.module.BModuleTypesContainer;
import com.tridium.util.ModuleDev;

/**
 * BModuleScheme is used to resolve modules and their contents
 * using a ModuleQuery.
 *
 * @author    Brian Frank on 10 Mar 03
 * @version   $Revision: 6$ $Date: 5/26/11 10:32:58 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "module"
)
@NiagaraSingleton
public class BModuleScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BModuleScheme(2381780313)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BModuleScheme INSTANCE = new BModuleScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModuleScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BModuleScheme()
  {
    super("module");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of FilePath.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new FilePath("module", queryBody);
  }

  /**
   * Resolve the query into a BModule, palette or BIFile.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    FilePath path = (FilePath)query;

    // module: -> ModuleSpace
    if (path.getBody().equals(""))
      return new OrdTarget(base, BModuleSpace.INSTANCE);

    // otherwise must have //<name>
    if (!path.isAuthorityAbsolute())
      throw new UnresolvedException("Modules must be authority absolute");

    // otherwise load the module
    BModule module = Sys.loadModule(path.getAuthority());

    // if no file path then return the module itself
    if (path.depth() == 0) return new OrdTarget(base, module);

    //TODO is this the right way to do this?
    if (path.depth()==1 )
    {
      if(path.getName().equals("types"))
      {
        return new OrdTarget(base, BModuleTypesContainer.get(module));
      }
      else if (path.getName().equals("module.palette"))
      {
        if (module.hasPalette())
        {
          return new OrdTarget(base, (BObject)module.getNavChild("module.palette"));
        }
        else
        {
          throw new UnresolvedException(String.format("Module %s has no palette", module.getModuleName()));
        }
      }
    }
    else if (path.depth()==2)
    {
      String[] names = path.getNames();
      if(names[0].equals("types"))
      {
        return new OrdTarget(base, (BObject)BModuleTypesContainer.get(module).getNavChild(names[1]));
      }
    }

    Optional<OrdTarget> target = moduleDev.makeModuleDevOrdTarget(path);
    if (target.isPresent())
    {
      return target.get();
    }
    else
    {
      // otherwise fetch the file inside the module
      BIFile file = module.resolveFile(path);
      return new OrdTarget(base, (BObject)file);
    }
  }

  /**
   * Return true if the module development mode is enabled. 
   * If true then resolved module ORDs can resolve from a source
   * directory instead of a module zip file.
   *
   * @since Niagara 4.0
   *
   */
  public static boolean isModuleDevEnabled()
  {
    return moduleDev.isEnabled();
  }
  private static final ModuleDev moduleDev = new ModuleDev();
}
