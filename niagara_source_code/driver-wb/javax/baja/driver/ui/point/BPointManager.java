/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.point;

import javax.baja.control.BControlPoint;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.folder.BFolderManager;

/**
 * BPointManager defines an the default implementation of BAbstractManager 
 * used to manage the proxy points under a BPointDeviceExt.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 12$ $Date: 9/12/04 1:43:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BPointManager
  extends BFolderManager
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.point.BPointManager(2979906276)1.0$ @*/
/* Generated Wed Jan 26 13:34:24 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  protected MgrModel makeModel() { return new PointModel(this); }

  protected MgrController makeController() { return new PointController(this); }

  protected MgrState makeState() { return new PointState(); }

  public Type getTargetType() { return BControlPoint.TYPE; }

//  protected void selectMatchingLearn(DriverTagMapInfo tagMapInfo, String tagKey)
//  {
//    MgrLearn learn = getLearn();
//    BLearnTable learnTable = learn.getTable();
//    TableSelection learnSelection = learnTable.getSelection();
//    TableModel learnTableModel = learnTable.getModel();
//    int learnColIndex = getLearnColumnIndex(learnTableModel, tagMapInfo.getPointLearnColumn());
//    if(learnColIndex < 0)
//    {
//      System.out.println("can't find learn column: " + tagMapInfo.getPointLearnColumn());
//      return;
//    }
//    Hashtable tagMap = tagMapInfo.getPointTagMap();
//    if(tagMap.containsKey(tagKey))
//    {
//      learnSelection.deselectAll();
//      String valueObj = (String)tagMap.get(tagKey);
//      String deviceType = getDeviceType(tagMapInfo.getDeviceTypeProp());
//      int rowCount = learnTableModel.getRowCount();
//      for(int i = 0; i < rowCount; ++i)
//      {
//        String checkKey = learnTableModel.getValueAt(i, learnColIndex).toString().trim();
//        if(deviceType != null)
//          checkKey = deviceType + '.' + checkKey;
//        if(checkKey.equals(valueObj))
//        {
//          learnSelection.select(i);
//          break;
//        }
//      }
//    }
//  }

  protected void selectMatchingTag( int learnRow)
  {

//    TableModel learnTableModel = this.getLearn().getTable().getModel();
//    int learnColIndex = getLearnColumnIndex(learnTableModel, tagMapInfo.getPointLearnColumn());
//    if(learnColIndex < 0)
//    {
//      System.out.println("can't find learn column: " + tagMapInfo.getPointLearnColumn());
//      return;
//    }
//    String learnKey = learnTableModel.getValueAt(learnRow, learnColIndex).toString().trim();
//    String deviceType = getDeviceType(tagMapInfo.getDeviceTypeProp());
//    if(deviceType!= null)
//      learnKey = deviceType + '.' + learnKey;
//    
//    BTagTable tagTable = getMgrTag().getTable();
//    TableModel tagModel = tagTable.getModel();
//    TableSelection tagSelection = tagTable.getSelection();
//    Hashtable tagMap = tagMapInfo.getPointTagMap();
//    if(tagMap.containsKey(learnKey))
//    {
//      tagSelection.deselectAll();
//      String[] value = TextUtil.split( (String)tagMap.get(learnKey), '.');
//      if(value.length >= 2)
//      {  
//        int rowCount = tagModel.getRowCount();
//        for(int i = 0; i < rowCount; ++i)
//        {
//          if(tagModel.getValueAt(i, 0).toString().trim().equals(value[1]))
//          {
//            tagSelection.select(i);
//            break;
//          }
//        }
//      }
//    }
  }
} 
