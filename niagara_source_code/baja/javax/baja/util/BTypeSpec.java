/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.ModuleException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeException;
import javax.baja.sys.TypeNotFoundException;
import com.tridium.sys.Nre;
import com.tridium.sys.module.ModuleManager;
import com.tridium.sys.module.NModule;

/**
 * BTypeSpec is a Baja simple which stores a module name
 * and type name pair.  A BTypeSpec's string format is 
 * "moduleName:typeName".
 *
 * @author    Brian Frank
 * @creation  22 Jan 01
 * @version   $Revision: 20$ $Date: 1/25/08 4:04:37 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BTypeSpec
  extends BSimple
  implements BIComparable
{

  /**
   * Create a type spec with its components.
   */
  public static BTypeSpec make(String moduleName, String typeName)
  {
    if (moduleName == null || typeName == null)
      throw new NullPointerException();
    
    BTypeSpec ts = new BTypeSpec();
    ts.moduleName = moduleName;
    ts.typeName = typeName;
    ts.spec = moduleName + ':' + typeName;
    return (BTypeSpec)(ts.intern());
  }

  /**
   * Create a type spec with string format.
   */
  public static BTypeSpec make(String spec)
  {
    if (spec.equals("null")) return NULL;
    
    try
    {
      BTypeSpec ts = new BTypeSpec();
      int colon = spec.indexOf(':');
      ts.moduleName = spec.substring(0, colon);
      ts.typeName = spec.substring(colon+1);
      ts.spec = spec;

      return (BTypeSpec)(ts.intern());
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException(spec);
    }
  }
  
  /**
   * Construct a type spec with a Type reference.
   */
  public static BTypeSpec make(Type type)
  {
    BTypeSpec ts = new BTypeSpec();
    ts.moduleName = type.getModule().getModuleName();
    ts.typeName = type.getTypeName();
    ts.spec = ts.moduleName + ":" + ts.typeName;
    ts = (BTypeSpec)(ts.intern());
    ts.type = type;
    return ts;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BTypeSpec()
  {
  }

////////////////////////////////////////////////////////////////
// BIComparable
////////////////////////////////////////////////////////////////

  /**
   * Compares this object with the specified object for 
   * order. Returns a negative integer, zero, or a positive 
   * integer as this object is less than, equal to, or greater 
   * than the specified object.
   */

  @Override
  public int compareTo(@SuppressWarnings("NullableProblems") Object obj)
  {
    return spec.compareTo(((BTypeSpec)obj).spec);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Return if this is the null BTypeSpec.
   */
  @Override
  public boolean isNull()
  {
    return spec.equals("null");
  }

  /**
   * Get the module name.
   */
  public String getModuleName()
  {
    return moduleName;
  }

  /**
   * Get the type name.
   */
  public String getTypeName()
  {
    return typeName;
  }                 
  
  /**
   * Get the TypeInfo for the type.
   */
  public TypeInfo getTypeInfo()
  {        
    return Sys.getRegistry().getType(spec);
  }  
  
  /**
   * Attempt to get the type resolved if possible.
   */
  public Type getResolvedType()
    throws ModuleException, TypeException
  {
    if (type == null)
    {
      // use non BModule API so that we don't 
      // attempt to load BIFiles before their time
      for (NModule module : Nre.getModuleManager().loadModuleParts(moduleName))
      {
        if (module.hasType(typeName))
        {
          return module.getType(typeName);
        }
      }
      throw new TypeNotFoundException(moduleName + ":" + typeName);
    }
    return type;
  }
  
  /**
   * Get a newInstance of the resolved type.
   */
  public BObject getInstance()
    throws ModuleException, TypeException
  {
    return getResolvedType().getInstance();
  }
  
  /**
   * BTypeSpec hash code is based on string hashcode.
   */
  public int hashCode()
  {
    return spec.hashCode();
  }
  
  /**
   * BTypeSpec equality is based on string equality.
   */
  public boolean equals(Object obj)
  {
    return obj instanceof BTypeSpec && spec.equals(((BTypeSpec)obj).spec);
  }

  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    return spec;
  }  
    
  /**
   * BTypeSpec is serialized using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(spec);
  }
  
  /**
   * BTypeSpec is unserialized using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }
  
  /**
   * Encode to the string format.
   */               
  @Override
  public String encodeToString()
    throws IOException
  {
    return spec;
  }

  /**
   * Read the simple from its string format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return make(s);
    }
    catch(RuntimeException e)
    {
      throw new IOException("Invalid type spec: " + s);   
    }
  }
  
  public static final BTypeSpec NULL = new BTypeSpec();
  static
  {
    NULL.spec = "null";
  }

  /** The default is NULL */
  public static final BTypeSpec DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeSpec.class);
          
  private String spec;
  private String moduleName;
  private String typeName;
  private Type type;
}
