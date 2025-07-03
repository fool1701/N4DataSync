/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * The TypeIntrospectionException indicates a BObject 
 * class type which does not follow the Baja specification 
 * rules for introspection of slots.
 *
 * @author    Brian Frank
 * @creation  17 Mar 00
 * @version   $Revision: 2$ $Date: 2/28/01 9:45:11 AM EST$
 * @since     Baja 1.0 
 */
public class TypeIntrospectionException
  extends BajaRuntimeException
{

  /**
   * Constructor with class and message.
   */
  public TypeIntrospectionException(Class<?> cls, String msg)
  {  
    super(msg + " [" + cls.getName() + "]");
    this.cls = cls;
  }
  
  /**
   * Get the Class which failed introspection.
   */
  public Class<?> getIntrospectedClass()
  {
    return cls;
  }
  
  private Class<?> cls;
         
}
