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
 * BPxFile stores a PX XML document.
 *
 * @author    Brian Frank       
 * @creation  2 Apr 04
 * @version   $Revision: 1$ $Date: 8/29/07 1:38:11 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "px")
)
public class BPxFile
  extends BXmlFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BPxFile(3957858410)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BPxFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BPxFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("workbench:WbPxView");
    agents.toTop("html:HtmlPxView");
    return agents;
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("files/px.png");
}
