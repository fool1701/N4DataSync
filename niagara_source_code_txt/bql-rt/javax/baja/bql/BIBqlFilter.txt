/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.agent.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIBqlFilter is a BIFilter that can provide a predicate for
 * a BQL query.
 *
 * @author    John Sublett
 * @creation  16 Oct 2003
 * @version   $Revision: 5$ $Date: 8/22/07 4:23:49 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIBqlFilter
  extends BInterface, BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bql.BIBqlFilter(2979906276)1.0$ @*/
/* Generated Wed Jan 26 10:50:35 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BIBqlFilter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Initialize the filter for objects of the same type
   * as the specified object.  This allows subclass specific
   * behavior for classes
   */
  public void init(BObject o);
  
  /**
   * Get the predicate text for this filter using the
   * specified column name.
   * <p>
   * Example:
   *   For a float value filter and a column name of "x":
   *   {@code "x >= 0 and x <= 100"}
   * 
   * @param columnName The name of the column to use in the
   *   predicate expression.
   */
  public String getPredicate(String columnName);
  
  /**
   * Test an object to see if it falls within the range of this filter.
   *
   * @param o The object to be tested.
   */
  public boolean accept(BObject o);
}
