/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.text;

import javax.baja.file.BIFileStore;
import javax.baja.file.types.text.BXmlFile;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Stores a Niagara certificate
 *
 * @author    Matt Boon
 * @creation  24 Feb 06
 * @version   $Revision: 1$ $Date: 2/24/06 9:46:34 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "certificate")
)
public class BCertificateFile
  extends BXmlFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.types.text.BCertificateFile(3566079317)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCertificateFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BCertificateFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BCertificateFile()
  {  
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("files/license.png");
}
