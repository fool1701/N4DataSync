/*
 * Copyright 2001, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BClassSpec is a Baja simple which stores a module name
 * and class name pair.  A BClassSpec's string format is 
 * "moduleName:className".
 *
 * @author    John Sublett
 * @creation  14 May 2002
 * @version   $Revision: 4$ $Date: 1/25/08 4:04:06 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BClassSpec
  extends BSimple
{

  /**
   * Create a class spec with its components.
   */
  public static BClassSpec make(String moduleName, String className)
  {
    if (moduleName == null || className == null)
      throw new NullPointerException();
      
    BClassSpec cs = new BClassSpec();
    cs.moduleName = moduleName;
    cs.className = className;
    cs.spec = moduleName + ':' + className;
    return (BClassSpec)(cs.intern());
  }

  /**
   * Create a class spec with string format.
   */
  public static BClassSpec make(String spec)
  {
    if (spec.equals("null")) return NULL;
    
    try
    {
      BClassSpec cs = new BClassSpec();
      int colon = spec.indexOf(':');
      cs.moduleName = spec.substring(0, colon);
      cs.className = spec.substring(colon+1);
      cs.spec = spec;
      return (BClassSpec)(cs.intern());
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException(spec);
    }
  }
  
  /**
   * Construct a class spec with a module and class reference.
   */
  public static BClassSpec make(BModule module, Class<?> cls)
  {
    BClassSpec cs = new BClassSpec();
    cs.moduleName = module.getModuleName();
    cs.className = cls.getName();
    cs.spec = cs.moduleName + ":" + cs.className;
    cs = (BClassSpec)(cs.intern());
    cs.cls = cls;
    return cs;
  }
  
  /**
   * Construct a class spec with a class reference.
   */
  public static BClassSpec make(Class<?> cls)
  {
    BClassSpec cs = new BClassSpec();
    cs.moduleName = Sys.getModuleForClass(cls).getModuleName();
    cs.className = cls.getName();
    cs.spec = cs.moduleName + ":" + cs.className;
    cs = (BClassSpec)(cs.intern());
    cs.cls = cls;
    return cs;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BClassSpec()
  {
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Return if this is the null BClassSpec.
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
  public String getClassName()
  {
    return className;
  }
  
  /**
   * Attempt to get the type resolved if possible.
   */
  public Class<?> getResolvedClass()
    throws IOException, ModuleException, ClassNotFoundException
  {
    if (cls == null)
    {
      BModule module = Sys.loadModule(moduleName);
      if (module == null) throw new ModuleException("Should never happen");
      return cls = module.loadClass(className);
    }
    return cls;
  }
  
  /**
   * Get a new instance of the resolved class.
   */
  public Object newInstance()
    throws IOException,
           ModuleException,
           IllegalAccessException,
           InstantiationException,
           ClassNotFoundException,
           ClassCastException
  {
    return getResolvedClass().newInstance();
  }
  
  /**
   * BClassSpec hash code is based on string hashcode.
   */
  public int hashCode()
  {
    return spec.hashCode();
  }
  
  /**
   * BClassSpec equality is based on string equality.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BClassSpec)
    {
      return spec.equals(((BClassSpec)obj).spec);
    }
    return false;
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
   * BClassSpec is serialized using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(spec);
  }
  
  /**
   * BClassSpec is unserialized using readUTF().
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
  
  public static final BClassSpec NULL = new BClassSpec();
  static
  {
    NULL.spec = "null";
  }

  /** The default is NULL */
  public static final BClassSpec DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BClassSpec.class);
          
  private String spec;
  private String moduleName;
  private String className;
  private Class<?>  cls;
}