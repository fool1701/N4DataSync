/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.app;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * Niagara Application Folder 
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public class BAppFolder
    extends BComponent
    implements BIAppFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.app.BAppFolder(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:34:19 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAppFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
 
////////////////////////////////////////////////////////////////
// Fw
////////////////////////////////////////////////////////////////  
  
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED: 
        container = (BAppContainer)Sys.getService(BAppContainer.TYPE); 
        break; 
      case Fw.ADDED:
      case Fw.REMOVED:
      case Fw.RENAMED:
      case Fw.REORDERED:
        if (isRunning() && Sys.atSteadyState()) container.fireAppsModified(null);
        break;
    }

    return super.fw(x, a, b, c, d);
  }
  
////////////////////////////////////////////////////////////////
// Object
////////////////////////////////////////////////////////////////  
  
  public BIcon getIcon() 
  { 
    BValue dynamic = get("icon");
    
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    
    return icon;
  }
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////
  
  public final boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BIAppFolder;
  }
  
////////////////////////////////////////////////////////////////
// App Component
////////////////////////////////////////////////////////////////
  
  public String getAppDisplayName(Context cx)
  {
    return getDisplayName(cx);
  }

  public BIcon getAppDisplayIcon()
  {
    return mobileIcon;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BIcon icon = BIcon.make(BIcon.std("folder.png"), BIcon.std("badges/puzzle.png"));
  private static final BIcon mobileIcon = BIcon.make("module://mobile/rc/niagaraApps.png");
  private BAppContainer container = null;
}
