/**
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

/**
 * NrIo16IOStatus class represents the status data for the Nrio IO Module.
 *
 * @author    Andy Saunders (Original R2 code)       
 * @creation  12 Jan 2006
 * @author    Andy Saunders
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 */

public class NrIo16IOStatus
{
////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  
 /**
  * Empty default constructor
  */
  public NrIo16IOStatus ()
  {
  }
  
  public NrIo16IOStatus (byte[] data)
  {
    NrioInputStream in = new NrioInputStream(data);
    activeAIs         = in.readInt();
    for(int i = 0; i < valueAIs.length; i++)
    	valueAIs[i]   = in.readInt();
    activeDIs         = in.read() & 0x0ff;
    valueHighSpeedDIs = in.read() & 0x0ff;
    for(int i = 0; i < countHighSpeedDIs.length; i++)
      countHighSpeedDIs[i] = in.readCount();
    valueOverrides    = in.readInt();
    firstReadOfInputs = in.readInt() != 0;
  }
  
  public int   getActiveAIs() {return activeAIs; }
  public int[] getValueAIs () {return valueAIs ; }
  public int   getActiveDIs() {return activeDIs; }
  public int   getValueHighSpeedDIs() {return valueHighSpeedDIs; }
  public int[] getCountHighSpeedDIs() {return countHighSpeedDIs; }
  public int   getValueOverrides   () {return valueOverrides;    }
  public boolean   isFirstReadOfInputs() {return firstReadOfInputs; }



  /**
   * Return a debug string for this message.
   */
  public String toDebugString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("IoModuleIOStatus:");
    sb.append("\n  activeAIs         = " + Integer.toHexString(activeAIs)       );
    for(int i = 0; i < valueAIs.length; i++)
      sb.append("\n  valueAIs["+i+"] = " + valueAIs[i] );
    sb.append("\n  activeDIs         = " + Integer.toHexString(activeDIs));
    sb.append("\n  valueHighSpeedDIs = " + Integer.toHexString(valueHighSpeedDIs));
    for(int i = 0; i < countHighSpeedDIs.length; i++)
        sb.append("\n  countHighSpeedDIs["+i+"] = " + countHighSpeedDIs[i] );
    sb.append("\n  valueOverrides    = " + Integer.toHexString(valueOverrides));
    sb.append("\n  firstReadOfInputs = " + firstReadOfInputs       );
   return sb.toString();
  }
  

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////
//  UINT     activeAIs;
//  UINT     valueAIs[MAX_NUM_AIS];
//  UCHAR    activeDIs;
//  UCHAR    valueHighSpeedDIs;
//  ULONG    countHighSpeedDIs[MAX_NUM_HI_SPD_DIS];
//  UINT     valueOverrides;
//  UINT     firstReadOfInputs;

  protected int   activeAIs;
  protected int[] valueAIs = new int[8];
  protected int   activeDIs;
  protected int   valueHighSpeedDIs;
  protected int[] countHighSpeedDIs = new int[8];
  protected int   valueOverrides;
  protected boolean    firstReadOfInputs;


  
}
