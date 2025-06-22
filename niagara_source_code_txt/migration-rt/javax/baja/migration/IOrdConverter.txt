/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import java.util.logging.Logger;
import javax.baja.naming.BOrd;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.Property;
import javax.baja.util.Version;

/**
 * OrdConverter
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 4/1/2015
 *         Time: 4:35 PM
 */
public interface IOrdConverter
{
  /**
   * Getter for base component passed in the constructor.
   * @return base component
   */
  BComponent getBase();

  /**
   * Attempt to resolve ord. If not resolved attempt to convert to ord that will resolve.
   * If both fail return BOrd.NULL.  If ord resolved return the original ord. If ord
   * converted return the new ord.
   * @param ord Ord to resolve or convert
   * @param relBase Base for relative slot ord
   * @param prop Property to resolve or convert
   * @param sourceVersion the {@link javax.baja.util.Version} of the source being converted.
   * @param log logger to post error or change notice.  If null caller deals with errors and changes.
   * @return  original ord, converted ord or BOrd.NULL.  BOrd.NULL returned if could not resolve
   *          an ord that should be resolvable.
   */
  BOrd convertOrd(BOrd ord,  BComplex relBase, Property prop, Version sourceVersion, Logger log);
}
