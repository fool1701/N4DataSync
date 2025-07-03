/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.application;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BWasmFile stores a WebAssembly binary file.
 *
 * @author Patrick sager
 * @creation 8/8/2017
 * @since Niagara 4.4
 */
@NiagaraType(
  ext = @FileExt(name = "wasm")
)
public class BWasmFile
  extends BApplicationFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.application.BWasmFile(2858178586)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWasmFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Construct a file with the specified store.
   */
  public BWasmFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BWasmFile()
  {
  }

  /**
   * Return {@code "application/wasm"}.
   */
  @Override
  public String getMimeType()
  {
    return "application/wasm";
  }
}
