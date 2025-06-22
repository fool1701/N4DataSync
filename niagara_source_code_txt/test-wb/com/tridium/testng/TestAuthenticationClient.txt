/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import javax.baja.security.AuthenticationRealm;
import javax.baja.security.BICredentials;
import javax.baja.security.BUsernameAndPassword;
import javax.baja.security.BUsernameCredential;
import javax.baja.sys.BIObject;
import com.tridium.authn.AuthenticationClient;

/**
 * An AuthenticationClient for test classes.
 *
 * @author Melanie Coggan
 * @creation 2014-03-25
 * @since Niagara 4.0
 */
public class TestAuthenticationClient implements AuthenticationClient
{
  public TestAuthenticationClient() {}

  public TestAuthenticationClient(String username, String password)
  {
    this.username = username;
    this.password = password;
  }

  @Override
  public BUsernameCredential requestUsername(AuthenticationRealm realm)
  {
    return new BUsernameCredential(username);
  }

  @Override
  public BICredentials requestInformation(AuthenticationRealm realm, String schemeName, int step, BIObject seedInfo)
  {
    return new BUsernameAndPassword(username, password);
  }

  private String username = "admin";
  private String password = "";
}
