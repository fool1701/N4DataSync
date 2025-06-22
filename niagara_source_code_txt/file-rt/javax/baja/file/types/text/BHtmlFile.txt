/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.text;

import javax.baja.agent.AgentList;
import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHtmlFile stores HTML markup.
 *
 * @author    Brian Frank on 27 Jan 03
 * @version   $Revision: 3$ $Date: 7/9/03 9:57:37 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = {
    @FileExt(name = "htm"),
    @FileExt(name = "html"),
    @FileExt(name = "body")
  }
)
public class BHtmlFile
  extends BTextFile
  implements BIHtmlFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BHtmlFile(3337131576)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHtmlFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BHtmlFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BHtmlFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "text/html"}.
   */
  @Override
  public String getMimeType()
  {
    return "text/html";
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("files/html.png");

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toTop("webEditors:HtmlViewer");
    return list;
  }
}
