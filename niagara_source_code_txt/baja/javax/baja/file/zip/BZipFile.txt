/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.nio.charset.Charset;

import javax.baja.file.BIFileStore;
import javax.baja.file.BLocalFileStore;
import javax.baja.file.BSubSpaceFile;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BZipFile is a BIFile that stores its own file space
 * addressed using the "zip" ord scheme.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 10$ $Date: 5/25/11 11:31:36 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "zip")
)
public class BZipFile
  extends BSubSpaceFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipFile(1085672837)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BZipFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct a file with the specified store and charset.
   * @param store the File Store encapsulation of the zip file
   * @param charset the charset of the zip file, defaults to UTF-8 in the underlying java ZipFile if null
   * @since Niagara 4.10u8 / 4.13u3 / 4.14
   */
  public BZipFile(BIFileStore store, Charset charset)
  {
    super(store);
    this.charset = charset;
  }

  /**
   * Construct (must call setStore()).
   */
  public BZipFile()
  {
  }

////////////////////////////////////////////////////////////////
// BSubSpaceFile
////////////////////////////////////////////////////////////////

  /**
   * Always return <code>false</code>.
   */
  @Override
  public boolean isModified()
  {
    return false;
  }

  /**
   * Open the associated space.
   */
  @Override
  protected BSpace doOpen()
  {
    if (zipSpace == null)
    {
      zipSpace = new BZipSpace(this, charset);
    }
    return zipSpace;
  }

  /**
   * Throws Save the associated space.
   */
  @Override
  protected void doSave()
    throws Exception
  {
  }

  /**
   * Close and deallocate the cached BZipSpace.
   *
   * The next call to getZipSpace() will create another instance.
   */
  @Override
  protected void doClose()
  {
    if (zipSpace != null)
    {
      try
      {
        zipSpace.close();
      }
      finally
      {
        zipSpace = null;
      }
    }
  }

////////////////////////////////////////////////////////////////
// BSpaceFile
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean hasNavChildren()
  {
    return getStore() instanceof BLocalFileStore;
  }

  /**
   * Convenience for <code>(BZipSpace)getSubSpace()</code>.
   */
  public BZipSpace getZipSpace()
  {
    return (BZipSpace)getSubSpace();
  }

////////////////////////////////////////////////////////////////
// BIFile
////////////////////////////////////////////////////////////////

  /**
   * Default returns <code>"application/zip"</code>
   */
  @Override
  public String getMimeType()
  {
    return "application/zip";
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get the nav ord.
   */
  @Override
  public BOrd getNavOrd()
  {
    if (getStore() instanceof BLocalFileStore)
    {
      return BOrd.make(super.getNavOrd(), "zip:");
    }
    else
    {
      return super.getNavOrd();
    }
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("files/zip.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected BZipSpace zipSpace;
  private Charset charset; // @since 4.10u8 / 4.13u3 / 4.14
}
