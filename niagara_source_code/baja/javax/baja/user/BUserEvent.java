/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BUserEvent is fired when a user is added, removed, or modified.
 * 
 * @author    John Sublett
 * @creation  14 Aug 2007
 * @version   $Revision: 1$ $Date: 9/12/07 1:33:35 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "UNKNOWN"
)
/*
 The name of the user was affected by the event.
 */
@NiagaraProperty(
  name = "userName",
  type = "String",
  defaultValue = ""
)
/*
 The old name for a rename event.  If the event is not a
 rename, then this property is unused.
 */
@NiagaraProperty(
  name = "oldName",
  type = "String",
  defaultValue = ""
)
public class BUserEvent
  extends BStruct
{

////////////////////////////////////////////////////////////////
// Ids
////////////////////////////////////////////////////////////////

  /** Unknown event id. */
  public static final int UNKNOWN  = -1;
  
  /** A user has been added. */
  public static final int ADDED    = 0;
  
  /** A user has been removed. */
  public static final int REMOVED  = 1;

  /** A user has been modified. */
  public static final int MODIFIED = 2;

  /** A user has been renamed. */
  public static final int RENAMED = 3;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserEvent(615108463)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(0, UNKNOWN, null);

  /**
   * Get the {@code id} property.
   * @see #id
   */
  public int getId() { return getInt(id); }

  /**
   * Set the {@code id} property.
   * @see #id
   */
  public void setId(int v) { setInt(id, v, null); }

  //endregion Property "id"

  //region Property "userName"

  /**
   * Slot for the {@code userName} property.
   * The name of the user was affected by the event.
   * @see #getUserName
   * @see #setUserName
   */
  public static final Property userName = newProperty(0, "", null);

  /**
   * Get the {@code userName} property.
   * The name of the user was affected by the event.
   * @see #userName
   */
  public String getUserName() { return getString(userName); }

  /**
   * Set the {@code userName} property.
   * The name of the user was affected by the event.
   * @see #userName
   */
  public void setUserName(String v) { setString(userName, v, null); }

  //endregion Property "userName"

  //region Property "oldName"

  /**
   * Slot for the {@code oldName} property.
   * The old name for a rename event.  If the event is not a
   * rename, then this property is unused.
   * @see #getOldName
   * @see #setOldName
   */
  public static final Property oldName = newProperty(0, "", null);

  /**
   * Get the {@code oldName} property.
   * The old name for a rename event.  If the event is not a
   * rename, then this property is unused.
   * @see #oldName
   */
  public String getOldName() { return getString(oldName); }

  /**
   * Set the {@code oldName} property.
   * The old name for a rename event.  If the event is not a
   * rename, then this property is unused.
   * @see #oldName
   */
  public void setOldName(String v) { setString(oldName, v, null); }

  //endregion Property "oldName"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserEvent.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make an added event.
   */
  public static BUserEvent makeAdded(BUser user)
  {
    return new BUserEvent(ADDED, user);
  }
  
  /**
   * Make a removed event.
   *
   * @param user The user that was removed.
   * @param removedName The name of the user that was removed.
   *   This is necessary because the user is no longer mounted
   *   so the name is no longer available.
   */
  public static BUserEvent makeRemoved(BUser user, String removedName)
  {
    return new BUserEvent(REMOVED, user, removedName);
  }
  
  /**
   * Make a modified event.
   */
  public static BUserEvent makeModified(BUser user)
  {
    return new BUserEvent(MODIFIED, user);
  }

  /**
   * Make a renamed event.
   */
  public static BUserEvent makeRenamed(BUser user, String oldName)
  {
    BUserEvent e = new BUserEvent(RENAMED, user);
    e.setOldName(oldName);
    return e;
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////


  public BUserEvent()
  {
  }

  public BUserEvent(int id, BUser user)
  {
    this(id, user, user.getName());
  }

  public BUserEvent(int id, BUser user, String userName)
  {
    setId(id);
    setUserName(userName);
    
    this.user = user;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the user for this event.  This is only available when
   * the event is received locally.
   */
  public BUser getUser()
  {
    return user;
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * Get a string summary for this event.
   */
  public String toString(Context cx)
  {
    return idToString(getId()) + ": " + getUserName();
  }

  /**
   * Get a string for the specified id.
   */
  public static String idToString(int id)
  {
    switch (id)
    {
      case ADDED   : return "Added";
      case REMOVED : return "Removed";
      case MODIFIED: return "Modified";
      case RENAMED : return "Renamed";
      
      default:
        return "Unknown(" + id + ")";
    }
  }
  
////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private BUser    user;

}
