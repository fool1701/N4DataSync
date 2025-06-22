/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.IOException;
import java.io.InputStream;

import javax.baja.agent.*;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.*;
import javax.baja.sys.*;
import javax.baja.transform.BITransformer;

import com.tridium.util.StreamUtil;

/**
 * BExporter is a component designed to export a BObject to
 * a file stream.  Exporters are registered as agents on
 * the source BObject type they export.  By convention you
 * should provide a TypeInfo icon in your lexicon using the
 * key "typename.icon".  Note that if you wish to register an 
 * exporter on a WbView then your subclass also needs to implement 
 * workbench:BIWbViewExporter.
 *
 * @author    Brian Frank on 22 May 04
 * @version   $Revision: 4$ $Date: 7/1/04 7:40:08 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BExporter
  extends BComponent
  implements BIAgent, BITransformer<OrdTarget, Context>
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BExporter(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExporter.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
////////////////////////////////////////////////////////////////
// Export
////////////////////////////////////////////////////////////////      
  
  /**
   * Get the output file type this exporter generates.
   */
  public abstract TypeInfo getFileType();

  /**
   * Get the default extension to use for the output file.
   */
  public abstract String getFileExtension();

  /**
   * Get the mime type of {@code getFileType}.
   */
  public final String getFileMimeType()
  {                                               
    return ((BIFile)getFileType().getInstance()).getMimeType();
  }
  
  /**
   * Export using the specified operation.  This is 
   * the method subclasses must implement.
   * <p>
   * Note that the {@code OutputStream} obtained from the {@code ExportOp} may
   * be {@code flush()}ed after writing, but must not be closed from within the
   * {@code export()} call.
   */
  public abstract void export(ExportOp op)
    throws Exception;

  @Override
  public BIcon getIcon(Context cx)
  {
    return getIcon();
  }

  @Override
  public final String getMimeType()
  {
    return getFileMimeType();
  }

  /**
   * Delegate to the {@code export()} method, and return a stream of the
   * exported data.
   *
   * @param target the ORD target to be transformed
   * @return a stream that will read the transformed data
   * @throws IOException if the transform could not be initiated. If the call to
   * {@code export()} throws, the exception will not be thrown immediately by
   * {@code transform()}, but will be thrown on the next call to {@code read()}
   * on the returned InputStream.
   */
  @Override
  public InputStream transform(OrdTarget target, Context cx) throws IOException
  {
    return StreamUtil.pipeAsync(out -> export(ExportOp.make(target, out)));
  }
}
