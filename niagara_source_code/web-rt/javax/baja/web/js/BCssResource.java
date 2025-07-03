/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web.js;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * <p>
 *   This class declares a bundle of CSS that can be referenced as a dependency by other
 *   {@link BIWebResource}s. Typically, a {@link BJsBuild} would depend on a {@code BCssResource} to
 *   indicate what CSS would be required in order to load the views in that JS bundle.
 * </p>
 * <p>
 *   There would usually be one {@code BCssResource} per Niagara module, declaring all CSS files in
 *   that module. But individual {@code BJsBuild}s can declare their own individual dependencies as
 *   needed.
 * </p>
 *
 * @since Niagara 4.13
 */
@NiagaraType
public abstract class BCssResource extends BSingleton implements BIWebResource
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.js.BCssResource(2979906276)1.0$ @*/
/* Generated Fri Jun 03 16:39:47 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCssResource.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  private final BOrd[] cssFiles;

  /**
   * Create an instance with an array of CSS files.
   *
   * @param cssFiles array of css files
   * @throws IllegalArgumentException if CSS file is missing
   */
  protected BCssResource(BOrd... cssFiles)
  {
    if (!isArrayOfOrds(cssFiles))
    {
      throw new IllegalArgumentException("css files required");
    }

    this.cssFiles = cssFiles.clone();
  }

  /**
   * @return array of CSS files represented by this build.
   */
  @Override
  public BOrd[] getFiles()
  {
    return cssFiles.clone();
  }

  private static boolean isArrayOfOrds(BOrd[] ords)
  {
    if (ords == null || ords.length == 0) { return false; }
    for (BOrd ord : ords) { if (ord == null) { return false; } }
    return true;
  }
}
