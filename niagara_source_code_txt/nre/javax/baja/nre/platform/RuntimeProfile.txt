/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.platform;

/**
 * Describes either:
 * <ul>
 *   <li>The kind of module content that a Niagara system is configured to load, based on
 *   configuration and VM profile</li>
 *   <li>Whether a particular module file should be loaded on a system based on its
 *   configuration and capabilities.</li>
 * </ul>
 *
 * The values are used to ensure that classes that a Niagara System's VM can't use
 * (<i>e.g.</i> classes using Java SE API can't be used in a VM with only the compact3
 * profile) aren't made available to the classloader, and to allow installers to
 * save storage space by skipping the transfer of module files to systems that can't
 * or are configured not to load them.
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public enum RuntimeProfile
{
  rt("VM: Java 8 compact3, UI: headless"),
  ux("VM: Java 8 compact3, UI: web"),
  wb("VM: Java 8 SE, UI: bajaui widgets with few SE dependencies"),
  se("VM: Java 8 SE, UI: any"),
  doc("VM: no Java classes, Contents: Documentation Only");

  RuntimeProfile(String desc)
  {
    this.desc = desc;
  }

  public String getDescription()
  {
    return desc;
  }

  public static RuntimeProfile valueOf(String name, RuntimeProfile defaultValue)
  {
    if (name == null) return defaultValue;
    try
    {
      return valueOf(name);
    }
    catch (IllegalArgumentException iae)
    {
      return defaultValue;
    }
  }

  /**
   * <p>Returns true if the given {@code profile} can depend on
   * {@code this}.</p>
   *
   * <p>For example,
   * {@code RuntimeProfile.rt.supportsDependency(RuntimeProfile.wb)} returns
   * {@code true}, because {@code wb} modules can depend on {@code rt}
   * modules. The inverse,
   * {@code RuntimeProfile.wb.supportsDependency(RuntimeProfile.rt)}, would
   * return {@code false}.</p>
   */
  public boolean supportsDependency(RuntimeProfile profile)
  {
    switch (profile)
    {
      case rt:
        return this == rt;
      case ux:
        /* TODO: prevent ux -> wb dependencies. eventually this may mean an hx-ux module */
        return (this == rt) || (this == ux) || (this == wb);
      case se:
        return this != doc;
      case wb:
        return (this == rt) || (this == ux) || (this == wb);
      case doc:
      default:
        return false;
    }
  }

  private String desc;
}
