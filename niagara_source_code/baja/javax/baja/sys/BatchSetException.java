/**
 * Copyright 2013 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.sys;

/**
 * A BatchSetException is thrown when a failure occurs during a batch
 * {@code set()} operation on a BComplex instance.  It gives information
 * about which properties in the batch set failed.
 * 
 * @author Scott Hoye
 * @creation Aug 23, 2013
 * @version $Revision: 1$ $Date: 8/23/13 1:57:55 PM EDT$
 * @since Niagara 4.0
 */
public class BatchSetException
    extends BajaRuntimeException
{

  /**
   * Constructor to create a BatchSetException indicating that
   * the given properties failed to be set on the given BComplex
   * instance.
   * 
   * @param complex - The BComplex instance on which the failed batch set occurred.
   * @param properties - The properties of the batch set operation that failed to be 
   * committed on the BComplex instance.
   * @param failedValues - The proposed property values that failed to be committed
   * on the BComplex instance during the batch set operation.  The size of this array
   * should match the properties array argument and the elements should correspond with those
   * in the properties array argument.
   * @param causes - The causes for the failed property sets on the BComplex instance.
   * The size of this array should match the properties array argument and the elements should 
   * correspond with those in the properties array argument.
   */
  public BatchSetException(BComplex complex, 
                           Property[] properties, 
                           BValue[] failedValues, 
                           Throwable[] causes)
  {
    super();
    this.complex = complex;
    this.properties = properties;
    this.failedValues = failedValues;
    this.causes = causes;
  }
  
  /**
   * Constructor to create a BatchSetException indicating that
   * the given properties failed to be set on the given BComplex
   * instance.  Also includes an optional localized message for the
   * exception.
   * 
   * @param localizedMessage - A message for this exception (should be localized for the 
   * current Locale)
   * @param complex - The BComplex instance on which the failed batch set occurred.
   * @param properties - The properties of the batch set operation that failed to be 
   * committed on the BComplex instance.
   * @param failedValues - The proposed property values that failed to be committed
   * on the BComplex instance during the batch set operation.  The size of this array
   * should match the properties array argument and the elements should correspond with those
   * in the properties array argument.
   * @param causes - The causes for the failed property sets on the BComplex instance.
   * The size of this array should match the properties array argument and the elements should 
   * correspond with those in the properties array argument.
   */
  public BatchSetException(String localizedMessage, 
                           BComplex complex, 
                           Property[] properties, 
                           BValue[] failedValues, 
                           Throwable[] causes)
  {
    super(localizedMessage);
    this.complex = complex;
    this.properties = properties;
    this.failedValues = failedValues;
    this.causes = causes;
  }
  
  /**
   * Returns the BComplex instance for which this BatchSetException indicates
   * a failed batch {@code set()} operation.
   */
  public BComplex getComplex()
  {
    return complex;
  }
  
  /**
   * Returns the properties of the batch {@code set()} operation that failed to be
   * committed on the BComplex instance.
   */
  public Property[] getFailedProperties()
  {
    return properties;
  }
  
  /**
   * Returns the proposed property values that failed to be committed
   * on the BComplex instance during the batch {@code set()} operation.
   */
  public BValue[] getFailedValues()
  {
    return failedValues;
  }
  
  /**
   * Returns the causes for the failed property sets on the BComplex instance.
   */
  public Throwable[] getFailureCauses()
  {
    return causes;
  }
  
  private BComplex complex;
  private Property[] properties;
  private BValue[] failedValues;
  private Throwable[] causes;
}
