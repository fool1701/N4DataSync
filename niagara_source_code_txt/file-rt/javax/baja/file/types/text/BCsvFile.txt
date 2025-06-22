/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.text;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BCsvFile stores comma separated value text.
 *
 * @author    Brian Frank       
 * @creation  30 Jun 04
 * @version   $Revision: 1$ $Date: 7/1/04 7:39:55 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "csv")
)
public class BCsvFile
  extends BTextFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BCsvFile(2684158149)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCsvFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BCsvFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BCsvFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "text/csv"}.
   */
  @Override
  public String getMimeType()
  {
    return "text/csv";
  }
}
