/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import java.io.PrintWriter;

import javax.baja.file.BIFile;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIDynamicFile is a file whose content may be preprocessed used for dynamic
 * content generation.  A BIDynamicFile receives special handling from the
 * BFileDownloadServlet.  When a BIDyanmicFile is downloaded, it is automatically
 * processed rather than being served out in raw form.
 * 
 * @author    John Sublett
 * @creation  08 Jan 2011
 * @version   $Revision: 1$ $Date: 1/13/11 1:02:55 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIDynamicFile
  extends BIFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIDynamicFile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDynamicFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  /**
   * Get the mime type for the dynamically generated content.
   */
  public String getDynamicMimeType();

  /**
   * Write the contents of the file as a response to the specified WebOp.
   */
  public void write(Object agent, OrdTarget target, PrintWriter writer)
    throws Exception;




}
