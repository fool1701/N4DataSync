/*
 * Copyright 2013 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.util.CannotValidateException;

/**
 * Defines an interface for validating a pending property set 
 * on a BComplex instance.
 * 
 * @author Scott Hoye
 * @creation Jul 19, 2013
 * @version $Revision: 1$ $Date: 7/19/13 8:57:55 AM EDT$
 * @since Niagara 4.0
 */
public interface IPropertyValidator
{
  
  /**
   * Validates a pending (possibly batch) property set on a BComplex instance.
   * 
   * @param validatable The validatable instance which contains the existing
   * and proposed state of a BComplex that needs to be validated.
   * @param context the Context for the pending set operation. Might be null.
   * @throws CannotValidateException if the new property value(s) for the pending
   * set on the BComplex instance failed to validate.
   * @throws LocalizableRuntimeException under the same conditions that a 
   * CannotValidateException occurs, but allows for more user friendly exception reporting
   * @see CannotValidateException
   */
  void validateSet(Validatable validatable, Context context);
  
  /**
   * Validates a pending property set on a BComplex instance.
   * 
   * @param instance The BComplex instance for which there is 
   * a pending set operation requiring validation.
   * @param property The property on the BComplex instance for
   * which there is a pending set operation.
   * @param newValue The pending new value for the property on the
   * BComplex instance which should be validated prior to commit.
   * @param context the Context for the pending set operation. Might be null.
   * @throws CannotValidateException if the new property value for the pending
   * set on the BComplex instance failed to validate.
   * @throws LocalizableRuntimeException under the same conditions that a 
   * CannotValidateException occurs, but allows for more user friendly exception reporting
   * @see CannotValidateException
   */
  default void validateSet(BComplex instance, 
                           Property property, 
                           BValue newValue, 
                           Context context)
  {
    validateSet(new Validatable(instance, 
                                new Property[] { property },
                                new BValue[] { newValue }),
                context);
  }

  /**
   * Callback that provides an opportunity to adjust (and possibly validate) a pending property
   * batch set operation on a BComplex instance.
   *
   * @param instance The BComplex instance for which there is a pending batch set operation
   *                 requiring possible property value adjustment/validation.
   * @param properties The properties on the BComplex instance for which there is a pending batch
   *                   set operation.
   * @param values The pending new values for the properties on the BComplex instance which should
   *               be adjusted/validated prior to commit. This array argument should be the same
   *               size and matching order of the preceding properties argument.
   * @param context the Context for the pending batch set operation. Might be null.
   * @return The adjusted/validated values for the properties on the BComplex instance which should
   *         be ready to commit. This array result should be the same size and matching order of the
   *         preceding properties argument.
   * @throws CannotValidateException if any new property value for the pending batch set on the
   * BComplex instance failed to validate.
   * @throws LocalizableRuntimeException under the same conditions that a
   * CannotValidateException occurs, but allows for more user friendly exception reporting
   * @see CannotValidateException
   *
   * @since Niagara 4.8
   */
  default BValue[] adjustPendingSetValues(BComplex instance,
                                          Property[] properties,
                                          BValue[] values,
                                          Context context)
  {
    return values;
  }

  /**
   * Callback that provides an opportunity to adjust (and possibly validate) a pending property
   * set operation on a BComplex instance.
   *
   * @param instance The BComplex instance for which there is a pending set operation requiring
   *                 possible property value adjustment/validation.
   * @param property The property on the BComplex instance for which there is a pending set
   *                 operation.
   * @param value The pending new value for the property on the BComplex instance which should
   *              be adjusted/validated prior to commit.
   * @param context the Context for the pending set operation. Might be null.
   * @return The adjusted/validated value for the property on the BComplex instance which should
   *         be ready to commit.
   * @throws CannotValidateException if the new property value for the pending
   * set on the BComplex instance failed to validate.
   * @throws LocalizableRuntimeException under the same conditions that a
   * CannotValidateException occurs, but allows for more user friendly exception reporting
   * @see CannotValidateException
   *
   * @since Niagara 4.8
   */
  default BValue adjustPendingSetValue(BComplex instance,
                                       Property property,
                                       BValue value,
                                       Context context)
  {
    return value;
  }

}