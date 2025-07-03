/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

/**
 * The LonAddress interface is implemented by classes
 * which represent a LonTalk address.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  14 Feb 02
 * @version   $Revision: 4$ $Date: 10/18/01 2:56:44 PM$
 * @since     Niagara 3.0
 */
public interface LonAddress
{
  /** Get the type of LonAddress */
  public int getAddressType();

  //
  //  Address Type definitions
  //
  public static final int UNASSIGNED  = 0;
  public static final int SUBNET_NODE = 1;
  public static final int NEURON_ID   = 2;
  public static final int BROADCAST   = 3;
  public static final int IMPLICIT    = 126;
  public static final int LOCAL       = 127;

}