/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.file;

/**
 * An interface for file filters that support filtering based on one or more file extensions.
 *
 * @since Niagara 4.8
 */
public interface IExtFileFilter
  extends IFileFilter
{
  /**
   * Check if the given extension is acceptable by this filter.
   *
   * @param extension the extension to check
   * @return true if the extension is acceptable, false otherwise
   *
   * @since Niagara 4.8
   */
  boolean acceptExtension(String extension);

  /**
   * A single file filter can support multiple "valid" extensions (e.g. ".xls" / ".xlsx").
   * This method returns the extension that is the "main" or default extension
   * for this file filter. This could be used to, for example, auto-fill a file extension
   * for a file when the filter is selected.
   *
   * @return the default extension for this filter, or null if there is no default extension
   *
   * @since Niagara 4.8
   */
  public String getDefaultExtension();

}
