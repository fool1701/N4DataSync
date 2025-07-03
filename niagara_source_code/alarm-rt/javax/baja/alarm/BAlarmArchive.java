/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.util.LexiconText;

import com.tridium.alarm.fox.BAlarmArchiveChannel;
import com.tridium.fox.sys.BFoxChannelRegistry;
import com.tridium.sys.schema.Fw;

/**
 * An Alarm Archive is a archive of cleared alarms.  These alarms are typically removed from the
 * Alarm Database and stored for historical purposes.  Since they are not open, they do not appear
 * in the AlarmConsole and a viewable only via views on the Alarm Archive or via queries using
 * alarm:archive
 *
 * @since Niagara 4.11
 */
@NiagaraType
public abstract class BAlarmArchive
  extends BAlarmDatabase
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmArchive(2979906276)1.0$ @*/
/* Generated Thu Jan 13 17:12:01 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmArchive.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Default constructor
   */
  protected BAlarmArchive()
  {
    super("alarm:archive", LexiconText.make("alarm", "space.alarmArchive"));
  }

  /**
   * Return the associated ArchiveAlarmProvider.
   */
  public abstract BArchiveAlarmProvider getProvider();

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  @Override
  public final BHost getHost()
  {
    Object p = getNavParent();
    if (p instanceof BSpace)
    {
      return ((BSpace)p).getHost();
    }
    return super.getHost();
  }

  @Override
  public BISession getSession()
  {
    Object p = getNavParent();
    if (p instanceof BISession) return (BISession)p;
    if (p instanceof BSpace) return ((BSpace)p).getSession();
    return null;
  }

  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

  @Override
  public String getNavName()
  {
    return NAV_NAME;
  }

  @Override
  public BIcon getNavIcon()
  {
    return BIcon.make(
      BIcon.make(LEX.get("space.alarmArchive.icon")),
      BIcon.make(LEX.get("space.alarmArchive.icon.badge")));
  }

  @Override
  public BINavNode[] getNavChildren()
  {
    return EMPTY_NAV_CHILDREN;
  }

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.STARTED)
    {
      fwStarted();
    }

    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    BFoxChannelRegistry.getPrototype().add(BAlarmArchiveChannel.NAME, new BAlarmArchiveChannel());
  }

  private static final BOrd ordInSession = BOrd.make("alarm:archive");
  private static final Lexicon LEX = Lexicon.make("alarm");
  static final String NAV_NAME = "alarmArchive";
  private final static BINavNode[] EMPTY_NAV_CHILDREN = new BINavNode[0];
}
