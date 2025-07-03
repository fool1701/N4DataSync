/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BModifyFlags defines qualifiers for configuration
 * data in nci's and config properties. 
 *
 * @author    Robert Adams
 * @creation  15 Jan 01
 * @version   $Revision: 2$ $Date: 10/12/01 9:43:21 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BModifyFlags
  extends BSimple
{
  /**
   * The default.
   */
  public static final BModifyFlags DEFAULT = BModifyFlags.make(true,false,false,false,false,false);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BModifyFlags(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModifyFlags.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create a BModifyFlags with the given value.
   */
  public static BModifyFlags make( boolean readWrite ,
                       boolean mfgOnly   ,
                       boolean reset     ,
                       boolean constant  ,
                       boolean offline   ,
                       boolean disabled  ) 
  {
    return new BModifyFlags(readWrite,mfgOnly,reset,constant,offline,disabled);
  }
  
  /**
   * Create a BModifyFlags with the given value.
   */
  public static BModifyFlags fromFlags(int flags) 
  {
    boolean mfgOnly     = ((flags & 0x10)!=0);    
    boolean reset       = ((flags & 0x08)!=0);    
    boolean constant    = ((flags & 0x04)!=0);    
    boolean offline     = ((flags & 0x02)!=0);    
    boolean disabled    = ((flags & 0x01)!=0);    
    boolean readWrite   = !constant && !mfgOnly;    
    
    return make(readWrite,mfgOnly,reset,constant,offline,disabled);
  }
  
  private BModifyFlags( boolean readWrite ,
                       boolean mfgOnly   ,
                       boolean reset     ,
                       boolean constant  ,
                       boolean offline   ,
                       boolean disabled  ) 
  {
    this.readWrite_   = readWrite  ;
    this.mfgOnly_     = mfgOnly    ;
    this.reset_       = reset      ;
    this.constant_    = constant   ;
    this.offline_     = offline    ;
    this.disabled_    = disabled   ;
  } 


  /**
   * BFloat's hash code is the floating value's
   * integer represention given by the
   * Float.floatToIntBits method.

  public int hashCode()
  {
    return Float.floatToIntBits(value);
  }
   */  
  /**
   * Test if the obj is equal in value to this BModifyFlags.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof BModifyFlags))
      return false;
    
    BModifyFlags comp = (BModifyFlags)obj;
    if ( (comp.readWrite_   == readWrite_ ) &&
         (comp.mfgOnly_     == mfgOnly_   ) &&
         (comp.reset_       == reset_     ) &&
         (comp.constant_    == constant_  ) &&
         (comp.offline_     == offline_   ) &&
         (comp.disabled_    == disabled_  )  )
    {
      return true;
    }
    return false;
  }
  
  /**
   *
   */
  public String toString(Context context)
  {
    return encodeToString();
  }
  
  /**
   *   Encode BModifyFlags using a binary format.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeBoolean(readWrite_ );
    out.writeBoolean(mfgOnly_   );
    out.writeBoolean(reset_     );
    out.writeBoolean(constant_  );
    out.writeBoolean(offline_   );
    out.writeBoolean(disabled_  );
  }
  
  /**
   * Decode BModifyFlags using the same binary format
   * that was written using encode, and return the new 
   * instance.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BModifyFlags( in.readBoolean(),
                             in.readBoolean(),
                             in.readBoolean(),
                             in.readBoolean(),
                             in.readBoolean(),
                             in.readBoolean() );
  }

  /**
   * Write the primitive in String format.
   *      readWrite 
   *      mfgOnly
   *      reset
   *      constant
   *      offline
   *      objectDisabl
   */
  public String encodeToString()
  {
    StringBuilder sb = new StringBuilder();
    boolean comma = false;
    if(readWrite_){ sb.append("readWrite"); comma = true; }
    if(mfgOnly_  ){ if(comma)sb.append(","); sb.append("mfgOnly"); comma = true; }
    if(reset_    ){ if(comma)sb.append(","); sb.append("reset"); comma = true; }
    if(constant_ ){ if(comma)sb.append(","); sb.append("constant"); comma = true; }
    if(offline_  ){ if(comma)sb.append(","); sb.append("offline"); comma = true; }
    if(disabled_ ){ if(comma)sb.append(","); sb.append("objDisable"); comma = true; }
    return sb.toString();
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
      boolean readWrite   = false;    
      boolean mfgOnly     = false;    
      boolean reset       = false;    
      boolean constant    = false;    
      boolean offline     = false;    
      boolean disabled    = false;    
      StringTokenizer st = new  StringTokenizer(s, ",");
      while( st.hasMoreTokens() )
      {
        String flag = st.nextToken();
        if(flag.equals("readWrite"))       readWrite  = true;
        else if(flag.equals("mfgOnly"))    mfgOnly    = true;
        else if(flag.equals("reset"))      reset      = true;
        else if(flag.equals("constant"))   constant   = true;
        else if(flag.equals("offline"))    offline    = true;
        else if(flag.equals("objDisabl"))  disabled   = true; // 1/5/06 old spelling - leave this in to catch older dbs
        else if(flag.equals("objDisable")) disabled   = true;
      }
      
      return make( readWrite,mfgOnly,reset,constant,
                              offline,disabled ); 
  }

  public  boolean   isReadWrite     () { return  readWrite_ ; }
  public  boolean   isMfgOnly       () { return  mfgOnly_   ; }
  public  boolean   isReset         () { return  reset_     ; }
  public  boolean   isConst         () { return  constant_  ; }
  public  boolean   isOffline       () { return  offline_   ; }
  public  boolean   isDisabled      () { return  disabled_  ; }

  private  boolean readWrite_   = false;
  private  boolean mfgOnly_     = false;    
  private  boolean reset_       = false;    
  private  boolean constant_    = false;    
  private  boolean offline_     = false;    
  private  boolean disabled_    = false;
  
  // BModify flags FIXX - remove these
  public static final int MFG_ONLY       = 0x10;	           
  public static final int MUST_RESET     = 0x08;	           
  public static final int CONSTANT       = 0x04;	           
  public static final int MOD_OFFLINE    = 0x02;	           
  public static final int MOD_DISABLED   = 0x01;	           
 
  public static final BModifyFlags mfgOnly      = fromFlags(MFG_ONLY    );	           
  public static final BModifyFlags mustReset    = fromFlags(MUST_RESET  );	           
  public static final BModifyFlags constant     = fromFlags(CONSTANT    );	           
  public static final BModifyFlags modOffline   = fromFlags(MOD_OFFLINE );	           
  public static final BModifyFlags modDisabled  = fromFlags(MOD_DISABLED);	           
 
}
