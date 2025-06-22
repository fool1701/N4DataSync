/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import java.lang.reflect.*;
import javax.baja.gx.*;
import javax.baja.ui.*;
import javax.baja.util.*;

/**
 * ReflectCommand is a handy command implementation that
 * uses reflection to invoke a method on the command owner.
 * The method to invoke must take zero parameters.  If the
 * method returns an instance of CommandArtifact then it
 * is returned from doInvoke(), otherwise null is returned.
 *
 * <pre>
 *  BMyWidget()
 *  { 
 *    add(null, new BButton(new ReflectCommand(this, "Press Me", "doIt"));
 *  }
 *
 *  public void doIt() 
 *  {
 *    System.out.println("ok!");
 *  }
 * </pre>
 *
 * @author    Brian Frank
 * @creation  24 Apr 02
 * @version   $Revision: 9$ $Date: 3/31/05 11:08:52 AM EST$
 * @since     Baja 1.0
 */
public class ReflectCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Invoke the specified method on the command invoke.
   */
  public ReflectCommand(BWidget owner, String label, BImage icon, String method)
  {
    this(owner, label, icon, toMethod(owner, method));
  }

  /**
   * Invoke the specified method on the command invoke.
   */
  public ReflectCommand(BWidget owner, String label, String method)
  {
    this(owner, label, null, method);
  }

  /**
   * Invoke the specified method on command invoke.
   */
  public ReflectCommand(BWidget owner, String label, Method method)
  {
    this(owner, label, null, method);
  }            

  /**
   * Invoke the specified method on command invoke.
   */
  public ReflectCommand(BWidget owner, String label, BImage icon, Method method)
  {
    super(owner, label);
    this.icon = icon;

    init(method);
  }  

  /**
   * Invoke the specified method on command invoke.
   */
  public ReflectCommand(BWidget owner, Lexicon lexicon, String keyBase, String method)
  {
    this(owner, lexicon, keyBase, toMethod(owner, method));
  }

  /**
   * Invoke the specified method on command invoke.
   */
  public ReflectCommand(BWidget owner, Lexicon lexicon, String keyBase, Method method)
  {
    super(owner, lexicon, keyBase); 

    init(method);
  }

  void init(Method method)
  {
    this.method = method;
    if (method.getParameterTypes().length != 0)
      throw new IllegalArgumentException("Method must take zero params " + method);    
  }
  
  static Method toMethod(BWidget owner, String method)
  {
    try
    {
      return owner.getClass().getMethod(method, new Class<?>[0]);
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("Cannot resolve method: " + method);
    }
  }

////////////////////////////////////////////////////////////////
// Command
////////////////////////////////////////////////////////////////

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    Object result = method.invoke(getOwner());
    if (result instanceof CommandArtifact)
      return (CommandArtifact)result;
      
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  /** The method to invoke on the owner */
  protected Method method; 
  
}

