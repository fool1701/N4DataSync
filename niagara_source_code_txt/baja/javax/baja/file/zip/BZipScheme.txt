/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import javax.baja.file.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BZipScheme manages the "zip" scheme with a ZipPath query.
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 6$ $Date: 8/30/07 10:36:55 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "zip"
)
@NiagaraSingleton
public class BZipScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipScheme(3025035894)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BZipScheme INSTANCE = new BZipScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BZipScheme()
  {
    super("zip");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of ZipPath.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new ZipPath(getId(), queryBody);
  }

  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    ZipPath path = (ZipPath)query;
    String body = path.getBody();

    if (base.get() instanceof BZipFile)
    {
      BZipFile zip = (BZipFile)base.get();
      BFileSpace fs = (BZipSpace)zip.open();
      if (body.isEmpty() || body.equals("/")) return new OrdTarget(base, fs);
      return new OrdTarget(base, (BObject)fs.resolveFile(path));
    }
    else if (base.get() instanceof BZipSpace)
    {
      BFileSpace fs = (BZipSpace)base.get();
      if (body.isEmpty() || body.equals("/")) return new OrdTarget(base, fs);
      return new OrdTarget(base, (BObject)fs.resolveFile(path));
    }
    else
    {
      throw new InvalidOrdBaseException(base.get().getType().toString());
    }
  }
}
