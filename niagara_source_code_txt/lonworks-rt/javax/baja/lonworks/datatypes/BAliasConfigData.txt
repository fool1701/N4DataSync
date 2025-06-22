/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonServiceType;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 *  This class file represents the network variable alias 
 *  table field described in Appendix A.4.2: Neuron Chip 
 *  Data Structures in the Neuron Chip Data Book.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  8 Jan 01
 * @version   $Revision: 2$ $Date: 9/18/01 9:50:15 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Index of primary network variable for which this is an alias.
 */
@NiagaraProperty(
  name = "primary",
  type = "int",
  defaultValue = "-1",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY
)
@NiagaraProperty(
  name = "state",
  type = "int",
  defaultValue = "0",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "selector",
  type = "int",
  defaultValue = "UNBOUND_NV_BASE_SELECTOR",
  flags = Flags.READONLY | Flags.DEFAULT_ON_CLONE,
  facets = @Facet("BFacets.make(BFacets.RADIX,BInteger.make(16))"),
  override = true
)
@NiagaraProperty(
  name = "direction",
  type = "BLonNvDirection",
  defaultValue = "BLonNvDirection.input",
  flags = Flags.DEFAULT_ON_CLONE | Flags.READONLY,
  override = true
)
public class BAliasConfigData
  extends BNvConfigData
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BAliasConfigData(2446153947)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "primary"

  /**
   * Slot for the {@code primary} property.
   * Index of primary network variable for which this is an alias.
   * @see #getPrimary
   * @see #setPrimary
   */
  public static final Property primary = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, -1, null);

  /**
   * Get the {@code primary} property.
   * Index of primary network variable for which this is an alias.
   * @see #primary
   */
  public int getPrimary() { return getInt(primary); }

  /**
   * Set the {@code primary} property.
   * Index of primary network variable for which this is an alias.
   * @see #primary
   */
  public void setPrimary(int v) { setInt(primary, v, null); }

  //endregion Property "primary"

  //region Property "state"

  /**
   * Slot for the {@code state} property.
   * @see #getState
   * @see #setState
   */
  public static final Property state = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY | Flags.HIDDEN, 0, null);

  /**
   * Get the {@code state} property.
   * @see #state
   */
  public int getState() { return getInt(state); }

  /**
   * Set the {@code state} property.
   * @see #state
   */
  public void setState(int v) { setInt(state, v, null); }

  //endregion Property "state"

  //region Property "selector"

  /**
   * Slot for the {@code selector} property.
   * @see #getSelector
   * @see #setSelector
   */
  public static final Property selector = newProperty(Flags.READONLY | Flags.DEFAULT_ON_CLONE, UNBOUND_NV_BASE_SELECTOR, BFacets.make(BFacets.RADIX,BInteger.make(16)));

  //endregion Property "selector"

  //region Property "direction"

  /**
   * Slot for the {@code direction} property.
   * @see #getDirection
   * @see #setDirection
   */
  public static final Property direction = newProperty(Flags.DEFAULT_ON_CLONE | Flags.READONLY, BLonNvDirection.input, null);

  //endregion Property "direction"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAliasConfigData.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  API
////////////////////////////////////////////////////////////////  
  public boolean isAlias() { return true; }
  
  /** Return all properties to default value */
  public void clearData()
  {
      setPriority     (false);
      setDirection    (BLonNvDirection.input);
      setSelector     (UNBOUND_NV_BASE_SELECTOR);
      setTurnAround   (false);
      setServiceType  (BLonServiceType.unacked);
      setAuthenticated(false);
      setAddrIndex    (DEFAULT_ADDR_INDEX);
      setPrimary      (-1);    
      setState        (FREE);
  }
  

  /** Write this aliasNvConfig to the specified output stream. */
  public void writeNetworkBytes(LonOutputStream outputStream)
  {
    super.writeNetworkBytes(outputStream);
    int primary = getPrimary();
    if(primary<0)
    {
      // set unused
      outputStream.writeUnsigned8(-1);
      outputStream.writeUnsigned16(-1);
    }
    else if(primary >= 255)
    {
      outputStream.writeUnsigned8(255);
      outputStream.writeUnsigned16(primary);
    }
    else
    {
      outputStream.writeUnsigned8(primary);
    }
  }

  /**********************************************
  *  Extract the Nv Config Response data from
  *  the LonInputStream 
  *  
  *  @param  inputStream  data stream of message
  **********************************************/
  public void fromInputStream(LonInputStream inputStream)
  {
    super.fromInputStream(inputStream);
    
    // If no more bytes this config was for a nv not an alias
    if(inputStream.available()<=0) return;
    
    int primary = inputStream.readUnsigned8();
    if (primary == 255 && inputStream.available()>=2) primary = inputStream.readUnsigned16();
    
    setPrimary(primary);    
  }


  public String toString(Context c)
  {
    return super.toString(c) + ",pri:" + getPrimary() + ",s:" + getState();                                                                      
  }
  
////////////////////////////////////////////////////////////////
//  Manage state
////////////////////////////////////////////////////////////////  

  public static final int UNAVAILABLE_BIT    = 1;
  public static final int RESERVE_BIT        = 2;
  public static final int BOUND_BIT          = 4;
  
  public static final int FREE               = 0;
  public static final int RESERVED           = RESERVE_BIT + UNAVAILABLE_BIT;
  public static final int BOUND              = BOUND_BIT + UNAVAILABLE_BIT;
 
  public boolean isAvailable() {return (getState() & UNAVAILABLE_BIT) == 0; }
  public boolean isReserved()  {return (getState() & RESERVE_BIT) != 0; }
  
  // Don't change bound bit
  public void reserve()        { setState( getState() | RESERVED); }
  public void makeAvailable()  { setState( getState() & ~RESERVED); }

  public void setBound()       { setState( BOUND ); }
  public void setFree()        { setState( FREE ); }
  
}
