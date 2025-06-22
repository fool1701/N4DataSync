/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.text;

import javax.baja.file.BDataFile;
import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * HTML5 Application Cache File
 * 
 * @see <a href='http://www.whatwg.org/specs/web-apps/current-work/#applicationcache'>HTML5 Application Cache Spec</a>
 *
 * @author    Gareth Johnson 
 * @creation  29 Nov 2011
 * @version   $Revision: 2$ $Date: 1/5/10 9:42:47 AM EST$
 * @since     Niagara 3.7
 */
@NiagaraType(
  ext = @FileExt(name = "appcache")
)
public final class BAppCacheFile
  extends BDataFile
  implements BITextFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BAppCacheFile(3913736923)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAppCacheFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BAppCacheFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BAppCacheFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "text/cache-manifest"}.
   */
  @Override
  public String getMimeType()
  {
    return "text/cache-manifest";
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("file.png");
}
