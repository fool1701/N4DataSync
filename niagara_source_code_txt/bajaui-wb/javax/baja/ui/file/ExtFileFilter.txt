/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.file;

import javax.baja.file.BIFile;
import javax.baja.file.IExtFileFilter;
import javax.baja.file.types.image.BImageFile;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.ui.util.UiLexicon;

/**
 * ExtFileFilter filter files by extension.
 *
 * @author Andy Frank on 29 Apr 03
 * @since Baja 1.0
 */
public class ExtFileFilter
  implements IExtFileFilter
{
////////////////////////////////////////////////////////////////
// Common Filters
////////////////////////////////////////////////////////////////

  /**
   * Filter by Images.
   */
  public static ExtFileFilter images =
    new ExtFileFilter(UiLexicon.bajaui().getText("filter.images"), getRegisteredImages());

  /**
   * Supply a string of all the available image files that are registered in Niagara.
   *
   * @since Niagara 4.10
   */
  public static String getRegisteredImages()
  {
    StringBuilder b = new StringBuilder();

    //by adding this first, `png` is the getDefaultExtension
    b.append("png");

    for (String ext : Sys.getRegistry().getFileExtensions(BImageFile.TYPE.getTypeInfo()))
    {
      if ("png".equals(ext))
      {
        continue;
      }

      if (b.length() > 0)
      {
        b.append(',');
      }

      b.append(ext);
    }

    return b.toString();
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Constructor. Example: ExtFileFilter("Image files", "gif,jpg,png,svg")
   *
   * @param desc The filter description
   * @param ext  Comma-delimited list (no spaces) of allowed extensions.
   */
  public ExtFileFilter(String desc, String ext)
  {
    this(desc, TextUtil.split(ext, ','));
  }

  /**
   * Constructor. Example:
   * ExtFileFilter("Image files", new String[] { "gif", "jpg", "png", "svg" });
   *
   * @param desc The filter description
   * @param ext  An array of allowed extensions
   */
  public ExtFileFilter(String desc, String[] ext)
  {
    this.ext = ext;
    this.desc = desc;
  }

  /**
   * Accept the specified BIFile.
   */
  @Override
  public boolean accept(BIFile file)
  {
    for (String s : ext)
    {
      if (!file.isDirectory() &&
        file.getExtension() != null &&
        file.getExtension().equalsIgnoreCase(s))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Return the description of this filter.
   */
  @Override
  public String getDescription(Context cx)
  {
    return desc;
  }

  /**
   * Check if the given extension is acceptable by this filter.
   *
   * @param extension the patch to check
   * @return true if the extension is acceptable, false otherwise
   * @since Niagara 4.8
   */
  @Override
  public boolean acceptExtension(String extension)
  {
    for (String s : ext)
    {
      if (extension != null && extension.equalsIgnoreCase(s))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * A single file filter can support multiple "valid" extensions (e.g. ".xls" / ".xlsx").
   * This method returns the extension that is the "main" or default extension
   * for this file filter. This could be used to, for example, auto-fill a file extension
   * for a file when the filter is selected.
   *
   * @return the default extension for this filter, or null if there is no default extension
   * @since Niagara 4.8
   */
  @Override
  public String getDefaultExtension()
  {
    if (ext.length > 0)
    {
      return ext[0];
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final String[] ext;
  private final String desc;
}
