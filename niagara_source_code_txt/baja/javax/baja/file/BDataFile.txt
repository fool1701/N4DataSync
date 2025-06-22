/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.agent.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BDataFile is a BIFile used when no specified file 
 * type is known.
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 7$ $Date: 3/28/05 9:22:55 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDataFile
  extends BAbstractFile
  implements BIDataFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BDataFile(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BDataFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BDataFile()
  {  
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * Get a short description.
   */
  @Override
  public String getNavDescription(Context cx)
  {
    return "" + getSize()/1024 + "kb" + " " + getLastModified();
  }

  /**
   * Return false.
   */
  @Override
  public boolean hasNavChildren()
  {
    return false;
  }
  
  /**
   * Return null
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    return null;
  }
  
  /**
   * Get empty array.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    return NO_NAV_NODES;
  }
  
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("web:FileDownloadView");
    return agents;
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("file.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BINavNode[] NO_NAV_NODES = new BINavNode[0];

}
