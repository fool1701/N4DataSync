/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.text;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A JNLP file used for Java WebStart Applications.
 *
 * @author Gareth Johnson on 24/03/2015
 * @since Niagara 4.0
 */
@NiagaraType(
  ext = @FileExt(name = "jnlp")
)
@SuppressWarnings("unused")
public final class BJnlpFile extends BXmlFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BJnlpFile(3512397684)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJnlpFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BJnlpFile(BIFileStore store)
  {
    super(store);
  }

  public BJnlpFile() {}

  @Override
  public String getMimeType()
  {
    return mimeType;
  }

  public static final String mimeType = "application/x-java-jnlp-file";
}
