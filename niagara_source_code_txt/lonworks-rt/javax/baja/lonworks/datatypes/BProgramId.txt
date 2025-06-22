/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.lonworks.enums.BLonMfgId;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

import com.tridium.lonworks.util.LonByteArrayUtil;

/**
 *  Manufacturer supplied 8 byte program identifier. 
 * See Neuron Chip Data Book A.1.1. <p>
 *
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:41 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BProgramId
  extends BSimple
{
  private static final byte[] defaultId = {0,0,0,0,0,0,0,0};

  /**
   * Default program.
   */
  public static final BProgramId DEFAULT = new BProgramId(defaultId);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BProgramId(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProgramId.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Factory method for creating program id 
   * from a byte array.
   */
  public static BProgramId make(byte[] b)
  {
    return new BProgramId(b);
  }
  
  public static BProgramId make(int format,
                        BLonMfgId mfrId,
                        int deviceClass,
                        int deviceSubclass,
                        int modelNumber)
  {
    byte[] pId = new byte[PROGRAM_ID_LENGTH];
    int mfgId = mfrId.getOrdinal();
    pId[0] = (byte)(((format<<4) & 0xf0) + ((mfgId>>16) & 0x0f));
    pId[1] = (byte)((mfgId>>8) & 0xff);
    pId[2] = (byte)(mfgId & 0xff);
    pId[3] = (byte)((deviceClass>>8) & 0xff);
    pId[4] = (byte)(deviceClass & 0xff);
    pId[5] = (byte)((deviceSubclass>>8) & 0xff);
    pId[6] = (byte)(deviceSubclass & 0xff);
    pId[7] = (byte)(modelNumber & 0xff);
    
    return BProgramId.make(pId);
  }
  
 
  /**
   * Private constructor.
   */
  private BProgramId(byte[] b) 
  {
    if(b.length != PROGRAM_ID_LENGTH)
      throw new IllegalArgumentException("Invalid array length in BProgramId constructor. " +
                                                   LonByteArrayUtil.toString(b));

    this.programId = b;
  }

  /**
   * Get 8 byte programId as byte array.
   */
  public byte[] getByteArray() 
  { 
    byte[] a = new byte[PROGRAM_ID_LENGTH];
    System.arraycopy(programId, 0, a, 0, PROGRAM_ID_LENGTH);
    return a; 
  }

  /**
   * Test if the obj is equal in value to this BProgramId.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BProgramId))
      return false;
    
    BProgramId comp = (BProgramId)obj;
 
    for (int i = 0; i < comp.programId.length; i++)
    {
      if (programId[i] != comp.programId[i])
        return false;
    }

    return true;
  }
  
  /**
   * Is this BProgramId all zeros.
   */
  public boolean isZero()
  {
    for (int i = 0; i < programId.length; i++)
    {
      if (programId[i] != 0) return false;
    }
    return true;
  }
  
  
  /**
   *
   */
  public String toString(Context context)
  {
    return LonByteArrayUtil.toString(programId);
  }
  
  /**
   * 
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.write(programId);
  }
  
  /**
   *  
   */
  public BObject decode(DataInput in)
    throws IOException
  { 
    byte[] pId = new byte[PROGRAM_ID_LENGTH];
    in.readFully(pId, 0, PROGRAM_ID_LENGTH);
    return new BProgramId(pId);
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString(null);
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return new BProgramId(LonByteArrayUtil.getBytes(s,PROGRAM_ID_LENGTH));
  }

  /**
   * Get LonMfgId for this ProgramId.
   */
  public BLonMfgId getMfgId()
  {
    int mfgId = programId[0] & 0x0f;
    mfgId =  (mfgId<<8) + (programId[1] & 0xff);
    mfgId =  (mfgId<<8) + (programId[2] & 0xff);
    try
    { 
      return BLonMfgId.make(mfgId);
    }
    catch(Throwable e) {}
    
    return BLonMfgId.unknown;
  }

  private int getDeviceClass()
    { return ((programId[3] & 0x0FF) << 8) | (programId[4] & 0x0FF); }   

  public int getFormat() 
    { return (programId[0] & 0xF0) >> 4; }       

  public int getDeviceSubclass()
    { return (programId[5] & 0x0FF); }   

  public int getDeviceChannelType()
    { return (programId[6] & 0x0FF); }   
    
  public int getModelNumber()
    { return programId[7] & 0x0FF; }   

  /** Is this programId's deviceClass router. */
  public boolean isRouter()
    { return (getDeviceClass() == 0x0101); }

  /** Create a hashCode for ProgramId. */  
  public int hashCode()
  {
    return ((programId[0] & 0x80)<< 23 ) | 
           (((programId[1]^programId[2]) & 0x7f)<< 23 ) | 
           ((programId[3]^programId[4]) << 16 ) | 
           ((programId[5]^programId[6]) << 8 ) | 
           (programId[7] ) ;
  }
  
  public boolean hasChangeableNvs()
  {
    return isLonMarkCompliant() && ((getDeviceSubclass() & 0x80)!=0);
  }
  
  public boolean isLonMarkCompliant()
  {
    int format = getFormat();
    return (format==8) || (format==9);
  }
  
  
  ////////////////////////////////////////////////////////////
  //  Per LonMark Interoperability Guidelines, Section 5-3,
  //  a standard program ID has the following format
  //
  //   ---   ----------- --------- ---------  -----  
  //   | |   | | | | | | | | | | | | | | | |  | | |
  //   ---   ----------- --------- ---------  -----  
  // |4 bits|  20 bits  | 16 bits | 16 bits | 8 bits
  //    
  //  Format   Mfr ID     Device    Device    Model
  //                      Class     Subclass  Number
  ////////////////////////////////////////////////////////////
  
  public static final BProgramId TRIDIUM_PID  = BProgramId.make(9, BLonMfgId.tridium, 0x0103,0x8000,0x3);
  
  public static final int PROGRAM_ID_LENGTH = 8;
  private byte[] programId;    
}
