/*
 * Copyright (c) Tridium, Inc. 2017. All Rights Reserved.
 */

package javax.baja.test;

import javax.baja.nre.security.IKeyPurpose;
import javax.baja.nre.security.IX509CertificateEntry;
import javax.baja.security.crypto.X509CertificateFactory;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO: Class description
 *
 * @author Bill Smith on 3/20/2017
 */
public class TestHelper
{
  public static IX509CertificateEntry createTestClientCertEntry(String alias, String cn, int days, String passphrase)
      throws Exception
  {
    String dn = "CN=" + cn + ",OU=client_testing,O=Tridium,C=US";

    Calendar cal = Calendar.getInstance();
    Date notBefore = cal.getTime();
    cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);
    Date notAfter = cal.getTime();

    return X509CertificateFactory.getInstance().generateSelfSignedCert(alias, dn, dn, notBefore, notAfter,
                                                                       2048, IKeyPurpose.CLIENT_CERT, null, null);
  }

  public static IX509CertificateEntry createTestServerCertEntry(String alias, String cn, int days, String passphrase)
      throws Exception
  {
    return createTestServerCertEntry(alias, cn, days, null, null);
  }

  public static IX509CertificateEntry createTestServerCertEntry(String alias, String cn, int days, String san, String passphrase)
      throws Exception
  {
    String dn = "CN=" + cn + ",OU=server_testing,O=Tridium,C=US";

    Calendar cal = Calendar.getInstance();
    Date notBefore = cal.getTime();
    cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);
    Date notAfter = cal.getTime();

    return X509CertificateFactory.getInstance().generateSelfSignedCert(alias, dn, dn, notBefore, notAfter,
                                                                       2048, IKeyPurpose.SERVER_CERT, san, null);
  }
}
