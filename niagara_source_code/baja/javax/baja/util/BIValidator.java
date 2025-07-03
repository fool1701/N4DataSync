/**
 * Copyright 2009 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Defines an interface for validating BObjects. Also defines a facet
 * for declaring BIValidators on a slot.
 * <p>
 * One use of a BIValidator is that a Workbench field editor will check
 * to see if any are defined on the slot and invoke them during save to make
 * sure that the value is valid.
 * <p>
 * In general though, this class defines a generic model for validation.
 * 
 * @see #VALIDATOR_FACET
 * 
 * @author Matthew Giannini
 * @creation Mar 3, 2009
 * @version $Revision: 3$ $Date: 9/24/09 8:57:55 AM EDT$
 * @since Niagara 3.5
 * 
 */
@NiagaraType
public interface BIValidator
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BIValidator(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIValidator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Validates the given value.
   * 
   * @param value The BObject to validate.
   * @param cx a Context to use during validation. Might be null.
   * 
   * @throws CannotValidateException if the value failed to validate.
   * @see CannotValidateException
   */
  public void validate(BObject value, Context cx) throws CannotValidateException;
  
  /**
   * Facet keys beginning with this value will be assumed to define the 
   * BTypeSpec of a BIValidator. [BString encoding of BTypeSpec]
   */
  public static final String VALIDATOR_FACET = "validator";
}
