/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.security.IX509CertificateEntry;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.util.Version;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.crypto.core.cert.CertUtils;
import com.tridium.crypto.core.cert.KeyPurpose;
import com.tridium.crypto.core.io.CryptoStoreId;
import com.tridium.fox.sys.BFoxChannel;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.sys.schema.Fw;

/**
 * BCertificateAliasFE allows selection of certificate aliases installed on the JACE.
 *
 * @author Bill Smith
 * @version $Revision: Original$
 * @creation 13 Dec 11
 * @since Baja 3.7
 */
@NiagaraType
public class BCertificateAliasFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BCertificateAliasFE(2979906276)1.0$ @*/
/* Generated Wed Oct 26 14:51:14 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCertificateAliasFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  @SuppressWarnings("unused")
  public BCertificateAliasFE()
  {
    this(CryptoStoreId.USER_KEY_STORE);
  }

  public BCertificateAliasFE(CryptoStoreId id)
  {
    setContent(field);
    linkTo("lk0", field, BTextDropDown.valueModified, setModified);
    linkTo("lk1", field, BTextDropDown.actionPerformed, actionPerformed);

    this.id = id;
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  @Override
  public void started()
  {
    // This widget requires a reference to it's WbShell to resolve
    // the available certificate aliases (see #doLoadValue), but might be
    // used in situations where doLoadValue is called before its mounted
    // in its parent (such is the case when used inside a BTypeSpecFE),
    // so another explicit call to doLoadValue is used here to allow
    // the certificate alias list to be fetched in such cases.
    BObject currentVal = getCurrentValue();
    if (currentVal != null)
    {
      lockModifiedState();
      doLoadValue(currentVal, getCurrentContext());
      unlockModifiedState();
    }
  }

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    field.getEditor().setEditable(!readonly);
    field.setDropDownEnabled(!readonly);
  }

  @Override
  protected void doLoadValue(BObject v, Context cx)
  {
    BString alias = (BString)v;
    field.setText(alias.getString());
    field.getList().removeAllItems();

    KeyPurpose keyPurpose;
    BString purposeId = (BString) cx.getFacet("purposeId");
    if (purposeId == null)
    {
      // This achieves the same behavior as CertificateAliasEditor.$resolveCertificateAliasNames
      // where the purposeId parameter is set to "SERVER_CERT" if the purposeId facet is missing.
      keyPurpose = KeyPurpose.SERVER_CERT;
    }
    else if (purposeId.getString().isEmpty())
    {
      // If the purposeId facet is present but an empty string, then all certificate aliases should
      // be returned. This achieves the same behavior as
      // com.tridium.platcrypto.core.BCertManagerService.getCertificateAliases. Note that
      // getCertificateAliases will return all aliases if the purposeIdName parameter is null. As
      // explained above, however, CertificateAliasEditor passes "SERVER_CERT" and not null to
      // getCertificateAliases if the purposeId facet is missing.
      keyPurpose = null;
    }
    else
    {
      keyPurpose = KeyPurpose.valueOf(purposeId.getString());
    }

    try
    {
      BWbShell shell = getWbShell();
      if (shell == null)
      {
        return;
      }
      BOrd ord = shell.getActiveOrd();
      if (ord == null)
      {
        return;
      }
      BComponent service = (BComponent) ord.get();
      if (service == null)
      {
        return;
      }

      Version remoteVersion = (Version)service.fw(Fw.GET_REMOTE_VERSION, "web", null, null, null);
      if (remoteVersion.compareTo(BCertificateAliasFE.version) < 0)
      {
        return;
      }

      BFoxSession session = (BFoxSession)service.getSession();
      BFoxChannel channel = session.getConnection().getChannels().get("crypto", Sys.getType("platCrypto:CryptoChannel"));

      Type type = Sys.getType("platCrypto:CryptoChannel");
      Class<?> cls = type.getTypeClass();
      Method mthd = cls.getMethod("keyStoreGetCertificates", CryptoStoreId.class);

      @SuppressWarnings("unchecked")
      Iterable<IX509CertificateEntry> certs = AccessController.doPrivileged((PrivilegedExceptionAction<Iterable<IX509CertificateEntry>>)() ->
      {
        return (Iterable<IX509CertificateEntry>)mthd.invoke(channel, id);
      });

      for (IX509CertificateEntry entry : certs)
      {
        if (filterCert(entry, keyPurpose))
        {
          field.getList().addItem(entry.getAlias());
        }
      }
    }
    catch (Exception e)
    {
      LOG.log(Level.SEVERE, "error loading alias fe", e);
    }
  }

  /**
   * Return true if the certificate should be included for the specified KeyPurpose, false if it
   * should be excluded.
   *
   * @param entry      the certificate entry
   * @param keyPurpose the KeyPurpose to filter on
   * @return true if the cert should be included, false otherwise
   * @since Niagara 4.10
   */
  private static boolean filterCert(IX509CertificateEntry entry, KeyPurpose keyPurpose)
  {
    // return all aliases with no key purpose specified
    if (keyPurpose == null)
    {
      return true;
    }

    switch (keyPurpose)
    {
      case CLIENT_CERT:
        return CertUtils.isClientCert(entry.getCertificate(0).getCertificate());
      case CA_CERT:
        return CertUtils.isCACertificate(entry.getCertificate(0).getCertificate());
      case CODE_SIGNING_CERT:
        return CertUtils.isCodeSigningCertificate(entry.getCertificate(0).getCertificate());
      case SERVER_CERT:
      default:
        return CertUtils.isServerCertificate(entry.getCertificate(0).getCertificate());
    }
  }

  @Override
  protected BObject doSaveValue(BObject v, Context cx)
  {
    return BString.make(field.getText());
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final Logger LOG = Logger.getLogger("workbench");
  private final BTextDropDown field = new BTextDropDown();
  private static final Version version = new Version("3.7");
  private final CryptoStoreId id;
}
