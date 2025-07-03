/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.security.AccessController;
import java.security.KeyStore;
import java.security.PrivilegedAction;
import java.util.Enumeration;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.crypto.core.cert.SigningUtil;
import com.tridium.crypto.core.io.CoreCryptoManager;
import com.tridium.nre.security.SecurityInitializer;

/**
 * BWbCertificateAliasFE allows selection of code signing certificate aliases installed
 * in the local keystore.
 *
 * @author    Patrick Sager
 * @creation  01/28/2016
 * @since     4.2
 */
@NiagaraType
public class BWbCertificateAliasFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BWbCertificateAliasFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbCertificateAliasFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BWbCertificateAliasFE()
  {    
    setContent(field);
    linkTo("lk0", field, BTextDropDown.valueModified, setModified);
    linkTo("lk1", field, BTextDropDown.actionPerformed, actionPerformed);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    field.getEditor().setEditable(!readonly);
    field.setDropDownEnabled(!readonly);
  }

  protected void doLoadValue(BObject v, Context cx)
  {
    BString alias = (BString) v;
    field.setText(alias.getString());
    field.getList().removeAllItems();
    
    try
    {
      CoreCryptoManager ccm = AccessController.doPrivileged((PrivilegedAction<CoreCryptoManager>)() ->
      {
        try
        {
          return CoreCryptoManager.get(SecurityInitializer.getInstance().getSecurityInfoProvider());
        }
        catch (Exception e)
        {
          return null;
        }
      });
      KeyStore keyStore = ccm.getKeyStore().getKeyStore();
      Enumeration<String> aliases = keyStore.aliases();
      
      while (aliases.hasMoreElements())
      {
        String s = aliases.nextElement();
        if (SigningUtil.isValidSigningCert(s, keyStore))
          field.getList().addItem(s);
      }

    }
    catch(Exception e)
    {
    }
  }
  
  protected BObject doSaveValue(BObject v, Context cx)
  {
    return BString.make(field.getText());
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BTextDropDown field = new BTextDropDown();
}
