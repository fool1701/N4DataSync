/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.BFolder;

/**
 * BAlarmClassFolder is a Folder for grouping AlarmClasses under the AlarmService.
 *
 * @author    Blake M Puhak
 * @creation  10 Jun 08
 * @version   $Revision: 5$ $Date: 11/3/10 9:49:23 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BAlarmClassFolder
  extends BFolder
  implements BIAlarmClassFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmClassFolder(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmClassFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Disallow duplicate alarm classes 
   */
  @Override
  public void checkAdd(String newName, BValue value, int flags, BFacets facets,
                       Context context)
  {
    if(isRunning() && value.getType().is(BAlarmClass.TYPE) && newName!=null)
    {
      BAlarmService alarmService = (BAlarmService)BOrd.make("service:alarm:AlarmService").get(this);
      BAlarmClass existingClass = alarmService.lookupAlarmClass(newName);
      if(newName.equals("defaultAlarmClass") || !existingClass.getName().equals("defaultAlarmClass"))
        throw new LocalizableRuntimeException("alarm","DuplicateAlarmClass",new String[] {newName});
    }
    super.checkAdd(newName, value, flags, facets, context);
  }

  /**
   * Disallow duplicate alarm classes 
   */
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if(property.getType().is(BAlarmClass.TYPE))
    {
      BAlarmService alarmService = (BAlarmService)BOrd.make("service:alarm:AlarmService").get(this);
      BAlarmClass existingClass = alarmService.lookupAlarmClass(newName);
      if(newName.equals("defaultAlarmClass") || !existingClass.getName().equals("defaultAlarmClass"))
        throw new IllegalNameException("alarm","DuplicateAlarmClass",new String[] {newName});
      alarmService.alarmClassDisplayNames.clear();
    }
    super.checkRename(property, newName, context);
  }

  @Override
  public void changed(Property prop, Context cx)
  {
    if (alarmService != null && prop.getName().equals("displayNames"))
    {
      alarmService.alarmClassDisplayNames.clear();
    }
  }

  @Override
  public void added(Property prop, Context cx)
  {
    if (alarmService != null && prop.getName().equals("displayNames"))
    {
      alarmService.alarmClassDisplayNames.clear();
    }
  }

  @Override
  public void removed(Property prop, BValue oldValue, Context cx)
  {
    if (alarmService != null && prop.getName().equals("displayNames"))
    {
      alarmService.alarmClassDisplayNames.clear();
    }
  }

  protected BAlarmService getAlarmService()
  {
    if (alarmService == null)
      alarmService = (BAlarmService)Sys.getService(BAlarmService.TYPE);
    return alarmService;
  }  
  
  /**
   * Only the BAlarmService or another BAlarmClassFolder is a legal parent. 
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    if ((parent instanceof BAlarmService) || (parent instanceof BAlarmClassFolder)) 
      return true;
    else
      return false;
  }
  
  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon()
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }  
  
  private static final BIcon icon = BIcon.make(BIcon.std("folder.png"), BIcon.std("badges/alarm.png"));
  private BAlarmService alarmService;
}
