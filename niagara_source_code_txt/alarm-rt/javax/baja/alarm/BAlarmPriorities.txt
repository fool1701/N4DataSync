/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.*;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.util.*;


/**
 * BAlarmPriorities contains the priority mapping for each 
 * Baja alarm transition type.
 *
 * @author    Dan Giorgis
 * @creation  23 Feb 01
 * @version   $Revision: 19$ $Date: 9/30/08 5:08:59 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BAlarmPriorities
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  private BAlarmPriorities(int toOffnormal, int toFault, int toNormal, int toAlert)
  {
    this.toOffnormal = checkPriority(toOffnormal);
    this.toFault = checkPriority(toFault);
    this.toNormal = checkPriority(toNormal);
    this.toAlert = checkPriority(toAlert);
  }

  private BAlarmPriorities()
  {
    this(255,255,255,255);
  }

  /**
   * Create a new instance.
   *
   * @param toOffnormal The off normal priority.
   * @param toFault The fault priority.
   * @param toNormal The normal priority.
   * @return The new instance.
   */
  public static BAlarmPriorities make(int toOffnormal, int toFault, int toNormal)
  {
    return new BAlarmPriorities(toOffnormal, toFault, toNormal, 255);
  }

  public static BAlarmPriorities make(int toOffnormal, int toFault, int toNormal, int toAlert)
  {
    return new BAlarmPriorities(toOffnormal, toFault, toNormal, toAlert);
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the priority assigned to the given transition
   * type.
   *
   * @param state The alarm transition type.
   * @return The priority.
   */
  public int getPriority(BSourceState state)
  {
    if (state == BSourceState.offnormal)
      return toOffnormal;
    else if (state == BSourceState.fault)
      return toFault;
    else if (state == BSourceState.normal)
      return toNormal;
    else if (state == BSourceState.alert)
      return toAlert;
    else
      throw new IllegalStateException();  
  }

  /**
   * Get the priority assigned to the toOffnormal transition
   * type.
   *
   * @return The toOffnormal priority.
   */
  public int getToOffnormal()
  {
    return toOffnormal;
  }

  /**
   * Get the priority assigned to the toNormal transition
   * type.
   *
   * @return The toNormal priority.
   */
  public int getToNormal()
  {
    return toNormal;
  }

  /**
   * Get the priority assigned to the toFault transition
   * type.
   *
   * @return The toFault priority.
   */
  public int getToFault()
  {
    return toFault;
  }

  public int getToAlert()
  {
    return toAlert;
  }
  
  /**
   * Check that the given priority is in range.
   *
   * @param priority The priority.
   * @exception IllegalStateException if the priority is out of range.
   * @return The priority.
   */
  private int checkPriority(int priority)
  {
    if (priority < MIN_PRIORITY || 
        priority > MAX_PRIORITY)
      throw new IllegalStateException(priority+": not in range 0-255");

    return priority;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BAlarmPriorities hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    int hash = 23 + toOffnormal;
    hash = (hash * 37) + toNormal;
    hash = (hash * 37) + toFault;
    return (hash * 37) + toAlert;
  }
  
  /**
   * Equality is based on priority equality.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BAlarmPriorities))
      return false;

    BAlarmPriorities ap = (BAlarmPriorities)obj;

    return (toOffnormal == ap.getToOffnormal() &&
            toNormal    == ap.getToNormal()    &&
            toFault     == ap.getToFault()     &&
            toAlert     == ap.getToAlert() );
  }
      
  /**
   * Binary encoding.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeInt(toOffnormal);
    out.writeInt(toFault);
    out.writeInt(toNormal);
    out.writeInt(toAlert);
  }
  
  /**
   * Binary decoding.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    int offnormal = in.readInt();
    int fault = in.readInt();
    int normal = in.readInt();
    int alert = in.readInt();

    return make(offnormal,fault,normal,alert);
  }

  /**
   * Text format name-value; pairs
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    StringBuilder s = new StringBuilder();
    s.append("toOffnormal=");
    s.append(toOffnormal).append(';');
    s.append("toFault=");
    s.append(toFault).append(';');
    s.append("toNormal=");
    s.append(toNormal).append(';');
    s.append("toAlert=");
    s.append(toAlert).append(';');

    return s.toString();
  }

  /**
   * Parse from a string.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    BAlarmPriorities ap = DEFAULT;

    StringTokenizer st = new StringTokenizer(s,";");

    int offnormal = 0;
    int fault = 0;
    int normal = 0;
    int alert = 0;
    
    while (st.hasMoreTokens())
    {
      String s1 = st.nextToken();
      int eq = s1.indexOf("=");
      String name = s1.substring(0,eq);
      String value = s1.substring(eq+1);

      if (name.equals("toOffnormal"))
        offnormal = Integer.parseInt(value);
      else if (name.equals("toNormal"))
        normal = Integer.parseInt(value);
      else if (name.equals("toFault"))
        fault = Integer.parseInt(value);
      else if (name.equals("toAlert"))
        alert = Integer.parseInt(value);
    }

    return make(offnormal,fault,normal,alert);
  }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private int toNormal;
  private int toOffnormal;
  private int toFault;
  private int toAlert;

  public static final BAlarmPriorities DEFAULT = new BAlarmPriorities();
  
  public static final int MAX_PRIORITY = 255;
  public static final int MIN_PRIORITY = 0;
  
////////////////////////////////////////////////////////////////
// Type 
////////////////////////////////////////////////////////////////  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmPriorities.class);


}