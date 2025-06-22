/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.folder;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;

/**
 * BFolderManager refines BAbstractManager to support managing
 * a deep tree of components organized in folders.  It supports
 * shallow or deep management via the allDescendants toggle.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 3$ $Date: 3/28/05 1:41:00 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BFolderManager
  extends BAbstractManager
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.folder.BFolderManager(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFolderManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  /**
   * Must use subclass of FolderModel.
   */
  protected abstract MgrModel makeModel();

  /**
   * Must use subclass of FolderController.
   */
  protected MgrController makeController() { return new FolderController(this); }

  /**
   * Must use subclass of FolderState.
   */
  protected MgrState makeState() { return new FolderState(); }

  public void doLoadValue(BObject obj, Context cx)
  {
    super.doLoadValue(obj, cx);
    maxSubscribeDepth = getModel().getSubscribeDepth();                       
  }                                                    
  
  void reloadNewDepth()
  {                  
    // check if we need a deeper subscribe
    int currentDepth = getModel().getSubscribeDepth();                       
    if (currentDepth > maxSubscribeDepth)
    {
      maxSubscribeDepth = currentDepth;    
      registerForComponentEvents(getTarget(), currentDepth);
    }                                     
    
    // reload model
    getModel().load(getTarget());
  }
  
  int maxSubscribeDepth = -1;  
} 
