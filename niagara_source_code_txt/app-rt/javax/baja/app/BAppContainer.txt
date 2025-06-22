/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.app;

import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

import com.tridium.sys.schema.Fw;

/**
 * Niagara Application Container
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
/*
 Fired when the Apps or AppFolders are modified
 */
@NiagaraTopic(
  name = "appsModified",
  flags = Flags.HIDDEN
)
public final class BAppContainer
    extends BComponent
    implements BIService, BIAppFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.app.BAppContainer(508525898)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Topic "appsModified"

  /**
   * Slot for the {@code appsModified} topic.
   * Fired when the Apps or AppFolders are modified
   * @see #fireAppsModified
   */
  public static final Topic appsModified = newTopic(Flags.HIDDEN, null);

  /**
   * Fire an event for the {@code appsModified} topic.
   * Fired when the Apps or AppFolders are modified
   * @see #appsModified
   */
  public void fireAppsModified(BValue event) { fire(appsModified, event, null); }

  //endregion Topic "appsModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAppContainer.class);

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
      case Fw.ADDED:
      case Fw.REMOVED:
      case Fw.RENAMED:
      case Fw.REORDERED:
        if (isRunning() && Sys.atSteadyState()) fireAppsModified(null);
        break;
    }

    return super.fw(x, a, b, c, d);
  }  
  
////////////////////////////////////////////////////////////////
// Service
////////////////////////////////////////////////////////////////
  
  public Type[] getServiceTypes()
  {
    return new Type[] { getType() };
  }
  
  public void serviceStarted() throws Exception {}
  public void serviceStopped() throws Exception {}
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////
  
  public BIcon getIcon()
  {
    return icon;
  }
  
////////////////////////////////////////////////////////////////
// App Component
////////////////////////////////////////////////////////////////
  
  public String getAppDisplayName(Context cx)
  {
    return Lexicon.make(TYPE.getModule(), cx).get("apps");
  }

  public BIcon getAppDisplayIcon()
  {
    return getIcon();
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final BIcon icon = BIcon.std("appContainer.png");
}
