/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.registry;

import javax.baja.sys.BAbsTime;
import javax.baja.nre.platform.RuntimeProfile;

public interface LexiconInfo
{
  /**
   * Get the brand pattern, used to match against runtime brand ID.
   */
  public String getBrandPattern();

  /**
   * Get the module name.
   */
  public String getModuleName();

  /**
   * Get the resource path.
   */
  public String getResourcePath();

  /**
   * Get the language.
   */
  public String getLanguage();

  /**
   * Get the module name for the lexicon container.
   */
  public String getContainerModuleName();

  /**
   * Get the runtime profile for the module jar that provides this lexicon
   */
  public RuntimeProfile getContainerRuntimeProfile();

  /**
   * Get the last modified info for lexicon container.
   */
  public BAbsTime getLastModified();
  
  /**
   * Set the last modified info for lexicon container.
   */
  public void setLastModified(long l);
  public void setLastModified(BAbsTime t);

  /**
   * Check if this is a default lexicon set.
   */
  public boolean isDefault();
}
