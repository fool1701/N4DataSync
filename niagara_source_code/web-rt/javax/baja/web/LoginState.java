/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */

/**
 * @author    Bill Smith
 * @creation  12 August 2010
 * @version   $Revision: 1$ $Date: 12/30/10 11:13:58 AM EST$
 * @since     Baja 1.0
 */

package javax.baja.web;

public class LoginState
{
  public static LoginState make(int state)
  {
    return new LoginState(state, null);
  }

  public static LoginState make(int state, Object data)
  {
    return new LoginState(state, data);
  }

  private LoginState(int state, Object data)
  {
    this.state = state;
    this.data = data;
  }
  
  public int getState()
  {
    return state;
  }
  
  public Object getData()
  {
    return data;
  }
  
  private int state;
  private Object data;
}