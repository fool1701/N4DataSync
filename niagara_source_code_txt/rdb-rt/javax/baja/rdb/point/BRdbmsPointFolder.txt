/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.point;

import javax.baja.agent.AgentList;
import javax.baja.control.BControlPoint;
import javax.baja.control.ext.BNullProxyExt;
import javax.baja.driver.BDevice;
import javax.baja.driver.BDeviceNetwork;
import javax.baja.driver.point.BIPointFolder;
import javax.baja.driver.point.BPointDeviceExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbms;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.*;
import javax.baja.util.BFolder;

import com.tridium.rdb.BResultSetTable;

/**
 * @author    Lee Adcock       
 * @creation  30 July 09
 * @version   $Revision: 1$ $Date: 7/30/09 11:15:08 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BRdbmsPointFolder 
  extends BFolder
  implements BIPointFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.point.BRdbmsPointFolder(2979906276)1.0$ @*/
/* Generated Sat Jan 29 17:54:41 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsPointFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//BIPointFolder
////////////////////////////////////////////////////////////////
  
  public BDevice getDevice()
  {
    BComplex parent = this.getParent();
    while (parent != null && !parent.getType().is(BRdbms.TYPE))
      parent = parent.getParent();
    return (BRdbms) parent;
  }

  public BPointDeviceExt getDeviceExt()
  {
    BRdbms device = (BRdbms) getDevice();
    if (device != null)
      return device.getPoints();
    else
      return null;
  }

  public BDeviceNetwork getNetwork()
  {
    BDevice device = getDevice();
    if (device != null)
      return getDevice().getNetwork();
    else
      return null;
  }

  public Type getPointFolderType()
  {
    return BRdbmsPointFolder.TYPE;
  }

  public Type getProxyExtType()
  {
    return BRdbmsProxyExt.TYPE;
  }
  
////////////////////////////////////////////////////////////////
//BComponent
////////////////////////////////////////////////////////////////
 
 public boolean isChildLegal(BComponent child)
 {
   return child.getType().is(BControlPoint.TYPE) || child.getType().is(getPointFolderType());
 }

 /**
  * Make sure any control point that is added has the proxy extension correctly configured
  */
 public void added(Property property, Context context)
 {
   if (property.getType().is(BControlPoint.TYPE))
   {
     BControlPoint cp = ((BControlPoint) get(property));
     if (cp.getProxyExt().getType().is(BNullProxyExt.TYPE))
     {
       BRdbmsProxyExt proxy = new BRdbmsProxyExt();
       proxy.setEnabled(false);
       cp.setProxyExt(proxy);
     }
     // Load value into new BControlPoint
     getQuery().execute();
   }

   super.added(property, context);
 }  
 
////////////////////////////////////////////////////////////////
//Presentation
////////////////////////////////////////////////////////////////
  
  public String toString(Context cx)
  {
    return "";
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("pointFolder.png");

////////////////////////////////////////////////////////////////
//Methods
////////////////////////////////////////////////////////////////  
  
   public BRdbmsPointQuery getQuery()
   {
     BComplex parent = this;
     while (parent != null && !parent.getType().is(BRdbmsPointQuery.TYPE))
       parent = parent.getParent();
     return (BRdbmsPointQuery) parent;
   } 
 
  /**
   * Call the execute method on all child control points with BRdbmsProxyExt proxy extensions
   */
  protected int executeChildren(BResultSetTable<? extends BComponent> table)
  {
    int controlPoints = 0;
    BComponent[] children = this.getChildComponents();
    for (int i = 0; i < children.length; i++)
    {
      if (children[i].getType().is(BControlPoint.TYPE))
      {
        BControlPoint cp = ((BControlPoint) children[i]);
        if (cp.getProxyExt().getType().is(BRdbmsProxyExt.TYPE))
        {
          controlPoints ++;
          BRdbmsProxyExt proxyExt = (BRdbmsProxyExt) cp.getProxyExt();
          proxyExt.execute(table);
        }
      }
    }
    
    BRdbmsPointFolder[] folders = this.getChildren(BRdbmsPointFolder.class);
    for (int i = 0; i < folders.length; i++)
      controlPoints+=folders[i].executeChildren(table);
    
    return controlPoints;
  }
}
