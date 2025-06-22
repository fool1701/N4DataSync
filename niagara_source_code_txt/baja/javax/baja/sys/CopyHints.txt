/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * CopyHints provides instructions for {@code BValue.newCopy()}.
 *
 * @author    Brian Frank
 * @creation  7 Dec 04
 * @version   $Revision: 2$ $Date: 12/8/04 3:46:41 PM EST$
 * @since     Baja 1.0
 */
public class CopyHints
{

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  public String toString()
  {
    return "CopyHints defaultOnClone=" + defaultOnClone +
                    " swizzleHandles=" + swizzleHandles;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////


  /**
   * If this flag is true, then properties marked with the
   * {@code Flag.DEFAULT_ON_CLONE} flag are not copied
   * and remain in the default state.  The default for this
   * value is true.
   */
  public boolean defaultOnClone = true;

  /**
   * The copy process will regard properties with the
   * {@code Flag.READ_ONLY} flag to be status properties and
   * all others to be configuration properties.
   *
   * If this flag is true, then status properties marked with
   * the {@code Flag.DEFAULT_ON_CLONE} flag are not copied
   * and remain in the default state. Configuration properties
   * with this flag are copied. The default for this value is
   * false.
   *
   * @since Niagara 4.7
   */
  public boolean defaultStatus = false;

  /**
   * If this flag is true, then all the handles in the graph
   * being copied are swizzled.  For example if the handle
   * was originally "foo", it might be converted to "copy-foo".
   * The actual swizzle algorithm used is opaque and you
   * should not count on the format remaining constant.  Swizzling
   * occurs on both BComponent handles and any BOrds inside
   * the graph using the handle "h:" scheme.  Components with
   * swizzled handles receive special treatment to maintain
   * internal consistency when using the {@code Mark.copyTo}
   * API.  If this flag is false, then handles are governed by
   * the {@code keepHandles} flag. When both this flag and the
   * {@code keepHandles} flag are false, then any Component in
   * the copy will have a null handle and any ords will remain
   * untouched.
   * The default for this flag is false.
   */
  public boolean swizzleHandles = false;

  /**
   * If this flag is true, then all the handles in the graph
   * being copied are kept in the copy. If this flag is false,
   * then handles are governed by the {@code swizzleHandles}
   * flag. When both this flag and the {@code swizzleHandles}
   * flag are false, then any Component in the copy will have
   * a null handle and any ords will remain untouched.
   * The default for this flag is false.
   *
   * @since Niagara 4.9
   */
  public boolean keepHandles = false;

  /**
   * The context to be used for permissions checking during
   * the copy process.
   */
  public Context cx = null;

}
