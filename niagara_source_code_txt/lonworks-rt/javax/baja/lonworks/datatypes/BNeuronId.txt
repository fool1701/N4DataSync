/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

import com.tridium.lonworks.util.LonByteArrayUtil;

/**
 *    Manufacturer assigned unique 6 byte identifier of Neuron Chip.
 *  See Neuron Chip Data Book A.1.1. <p>
 *
 * @author    Robert Adams
 * @creation  14 Dec 00
 * @version   $Revision: 1$ $Date: 9/12/01 2:04:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BNeuronId
  extends BSimple
  implements LonAddress
{
  private static final byte[] defaultId = {0,0,0,0,0,0};

  public static final BNeuronId DEFAULT = new BNeuronId(defaultId);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BNeuronId(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:21 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNeuronId.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Factory method for creating neuron id 
   * from a byte array.
   */
  public static BNeuronId make(byte[] b)
  {
    return new BNeuronId(b);
  }
 
  /**
   * Private constructor.
   */
  private BNeuronId(byte[] b) 
  {
    if(b.length != NEURON_ID_LENGTH) 
      throw new IllegalArgumentException("Invalid array length in BNeuronId constructor. " + 
                                             LonByteArrayUtil.toString(b));
    this.neuronId = b;
  }

  /**
   * Get 6 byte neuronId as byte array.
   */
  public byte[] getByteArray() 
  { 
    byte[] a = new byte[NEURON_ID_LENGTH];
    System.arraycopy(neuronId, 0, a, 0, NEURON_ID_LENGTH);
    return a; 
  }

  /**
   * BNeuronId hash code
   */
  public int hashCode()
  {
    return NEURON_ID<<24 |  
           (((neuronId[1] ^ neuronId[0]) & 0x0ff) << 16) |    
           (((neuronId[3] ^ neuronId[2]) & 0x0ff) << 8) | 
           ((neuronId[5] ^ neuronId[4])& 0x0ff);
  }
  
  /**
   * Is this BNeuronId all zeros.
   */
  public boolean isZero()
  {
    for (int i = 0; i < neuronId.length; i++)
    {
      if (neuronId[i] != 0) return false;
    }
    return true;
  }
  
   
  /**
   * Test if the obj is equal in value to this BNeuronId.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BNeuronId))
      return false;
    
    BNeuronId comp = (BNeuronId)obj;
 
    for (int i = 0; i < comp.neuronId.length; i++)
    {
      if (neuronId[i] != comp.neuronId[i])
        return false;
    }

    return true;
  }
  
  /** */
  public String toString(Context context)
  {
    return LonByteArrayUtil.toString(neuronId);
  }
  
  /**   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.write(neuronId);
  }
  
  /**  */
  public BObject decode(DataInput in)
    throws IOException
  {
    byte[] nId = new byte[NEURON_ID_LENGTH];
    in.readFully(nId, 0, NEURON_ID_LENGTH);
    return new BNeuronId(nId);
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
    throws IOException
  {
    return toString();
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return new BNeuronId(LonByteArrayUtil.getBytes(s,NEURON_ID_LENGTH));
  }
  
  public static final int NEURON_ID_LENGTH = 6;
  private byte[] neuronId;    

/////////////////////////////////////////////////  
// LonAddress  api
/////////////////////////////////////////////////

  /** Return address type NEURON_ID */
  public int getAddressType() { return NEURON_ID; }

/*
  //nre lonworks:javax.baja.lonworks.datatypes.BNeuronId 
  public static void main(String[] args)
    throws Exception
  {
    testNidHash(BNeuronId.make( new byte[]{0,0,0,0,0,0} ));
    testNidHash(BNeuronId.make( new byte[]{0,0,0,0,0,1} ));
    testNidHash(BNeuronId.make( new byte[]{1,0,1,0,1,0} ));
    testNidHash(BNeuronId.make( new byte[]{1,2,3,4,5,6} ));
    testNidHash(BNeuronId.make( new byte[]{8,0,3,12,0,0xb} ));
    testNidHash(BNeuronId.make( new byte[]{0,0,5,5,0,0} ));
    testNidHash(BNeuronId.make( new byte[]{(byte)0x0ff,(byte)0x0ff,(byte)0x0ff,(byte)0x0ff,(byte)0x0ff,(byte)0x0ff} ));
    testNidHash(BNeuronId.make( new byte[]{0,0,7,0,0,7} ));
    testNidHash(BNeuronId.make( new byte[]{0,2,0,9,9,0} ));
  }
  static void testNidHash(BNeuronId nid)
  {
    System.out.println(nid.toString(null) + " hash=" + Integer.toString(nid.hashCode(),16));
  }
*/  
}
