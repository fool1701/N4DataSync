/** Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.*;
import java.util.*;

import javax.baja.data.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * Ordered list of User Instructions for how to handle an alarm.
 *
 * @author    Blake M Puhak
 * @creation  20 Oct 05
 * @version   $Revision: 3$ $Date: 12/14/05 9:59:06 AM EST$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BAlarmInstructions
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make for a single instruction.
   */
  public static BAlarmInstructions make(BFormat instruction)
    throws IOException
  {
    return new BAlarmInstructions(new BFormat[] { instruction }, instruction.encodeToString());
  }

  /**
   * Make for an array of instructions.
   */
  public static BAlarmInstructions make(BFormat[] instructions)
  {
    if (instructions.length == 0)
      return NULL;
    else
      return new BAlarmInstructions(instructions.clone(), null);
  }

  /**
   * Make a new list by appending the specified 
   * instruction to the original list.
   */
  public static BAlarmInstructions add(BAlarmInstructions orig, BFormat instruction)
  {
    BFormat[] instructions = new BFormat[orig.instructions.length+1];
    System.arraycopy(orig.instructions, 0, instructions, 0, orig.instructions.length);
    instructions[instructions.length-1] = instruction;
    return new BAlarmInstructions(instructions, null);
  }

  /**
   * Make a new list by removing the instruction at the specified index.
   */
  public static BAlarmInstructions remove(BAlarmInstructions orig, int index)
  {
    BFormat[] instructions = new BFormat[orig.instructions.length-1];
    System.arraycopy(orig.instructions, 0, instructions, 0, index);
    if (index < orig.instructions.length)
      System.arraycopy(orig.instructions, index+1, instructions, index, orig.instructions.length-index-1);
    return new BAlarmInstructions(instructions, null);
  }

  /**
   * Convenience for <code>DEFAULT.decodeFromString(string)</code>
   */
  public static BAlarmInstructions make(String string)
  {                                                        
    return (BAlarmInstructions)DEFAULT.decodeFromString(string);
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BAlarmInstructions(BFormat[] instructions, String string)
  {
    this.instructions = instructions;
    this.string = string;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the instruction at the specified index.
   */
  public BFormat get(int index)
  {
    return instructions[index];
  }

  /**
   * Get the number of instructions in this list.
   */
  public int size()
  {
    return instructions.length;
  }

  /**
   * Is this instance null?
   */
  @Override
  public boolean isNull()
  {
    return instructions.length == 0;
  }

  /**
   * Get the array of ords.
   */
  public BFormat[] toArray()
  {
    return instructions.clone();
  }
      
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Hash is based on hashes of individual ords.
   */
  public int hashCode()
  {
    if (hashCode == -1)   
    {
      int x = 11;
      for(int i=0; i<instructions.length; ++i) x ^= instructions[i].hashCode();
      hashCode = x;
    }
    return hashCode;
  }
  
  /**
   * Equality is based on equality of individual ords.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BAlarmInstructions)
    {
      BFormat[] a = this.instructions;
      BFormat[] b = ((BAlarmInstructions)obj).instructions;
      if (a.length != b.length) return false;
      for(int i=0; i<a.length; ++i)
        if (!a[i].equals(b[i])) return false;
      return true;
    }
    return false;
  }
    
  /**
   * BOrd is encoded as using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BOrd is decoded using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Write the simple in text format.
   */
  @Override
  public String encodeToString()
    throws IOException
  {
    if (string == null)
    {              
      if (instructions.length == 1)
      {
        string = instructions[0].encodeToString();
      }
      else
      {
        StringBuilder s = new StringBuilder();
        for(int i=0; i<instructions.length; ++i)
        {
          if (i > 0) s.append('\n');
          s.append(instructions[i].encodeToString());
        }
        string = s.toString();
      }
    }
    return string;
  }
  
  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
  {                             
    if (s.length() == 0) return DEFAULT;
         
    int nl = s.indexOf('\n');
    if (nl < 0) return new BAlarmInstructions(new BFormat[] { BFormat.make(s) }, s);
    
    ArrayList<BFormat> list = new ArrayList<>();
    
    StringTokenizer st = new StringTokenizer(s, "\n");
    while(st.hasMoreTokens())
      list.add(BFormat.make(st.nextToken()));
     
    BFormat[] instructions = list.toArray(new BFormat[list.size()]);
    return new BAlarmInstructions(instructions, s);
  }  

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public String toString(Context cx)
  {                          
    if (instructions.length == 1) 
      return instructions[0].toString(cx);

    StringBuilder buf = new StringBuilder();
    for (int i=0; i<instructions.length; i++)
    {
      if (i > 0) buf.append("; ");
      buf.append(instructions[i].toString(cx));
    }
    return buf.toString();
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  /**
   * Null is the empty list.
   */
  public static final BAlarmInstructions NULL = new BAlarmInstructions(new BFormat[0], "");
  
  /**
   * The default is the empty list.
   */
  public static final BAlarmInstructions DEFAULT = NULL;

  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmInstructions.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////                        
  
  private int hashCode = -1;
  private BFormat[] instructions;
  private String string;
  
}
