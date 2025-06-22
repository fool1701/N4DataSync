/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.search;

import java.lang.ref.SoftReference;
import java.util.Optional;

import javax.baja.control.BControlPoint;
import javax.baja.data.BIDataValue;
import javax.baja.history.BHistoryId;
import javax.baja.history.BHistoryService;
import javax.baja.history.BIHistory;
import javax.baja.history.HistorySpaceConnection;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Tags;

import com.tridium.util.CompUtil;

/**
 * An individual search result record.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2013-09-27
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The ORD of the object returned by {@link #getObject()}.  Used to support lazy
 evaluation of {@link #getObject()}. It is the ORD for the actual Object that
 this SearchResult references.
 */
@NiagaraProperty(
  name = "ord",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.TRANSIENT | Flags.READONLY
)
/*
 The epoch (version number, timestamp, etc.) that reflects the
 version, modification time, etc. of the object returned by {@link #getObject()}.
 */
@NiagaraProperty(
  name = "epoch",
  type = "BSimple",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.TRANSIENT | Flags.READONLY
)
public final class BSearchResult extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchResult(2083927827)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ord"

  /**
   * Slot for the {@code ord} property.
   * The ORD of the object returned by {@link #getObject()}.  Used to support lazy
   * evaluation of {@link #getObject()}. It is the ORD for the actual Object that
   * this SearchResult references.
   * @see #getOrd
   * @see #setOrd
   */
  public static final Property ord = newProperty(Flags.TRANSIENT | Flags.READONLY, BOrd.NULL, null);

  /**
   * Get the {@code ord} property.
   * The ORD of the object returned by {@link #getObject()}.  Used to support lazy
   * evaluation of {@link #getObject()}. It is the ORD for the actual Object that
   * this SearchResult references.
   * @see #ord
   */
  public BOrd getOrd() { return (BOrd)get(ord); }

  /**
   * Set the {@code ord} property.
   * The ORD of the object returned by {@link #getObject()}.  Used to support lazy
   * evaluation of {@link #getObject()}. It is the ORD for the actual Object that
   * this SearchResult references.
   * @see #ord
   */
  public void setOrd(BOrd v) { set(ord, v, null); }

  //endregion Property "ord"

  //region Property "epoch"

  /**
   * Slot for the {@code epoch} property.
   * The epoch (version number, timestamp, etc.) that reflects the
   * version, modification time, etc. of the object returned by {@link #getObject()}.
   * @see #getEpoch
   * @see #setEpoch
   */
  public static final Property epoch = newProperty(Flags.TRANSIENT | Flags.READONLY, BAbsTime.NULL, null);

  /**
   * Get the {@code epoch} property.
   * The epoch (version number, timestamp, etc.) that reflects the
   * version, modification time, etc. of the object returned by {@link #getObject()}.
   * @see #epoch
   */
  public BSimple getEpoch() { return (BSimple)get(epoch); }

  /**
   * Set the {@code epoch} property.
   * The epoch (version number, timestamp, etc.) that reflects the
   * version, modification time, etc. of the object returned by {@link #getObject()}.
   * @see #epoch
   */
  public void setEpoch(BSimple v) { set(epoch, v, null); }

  //endregion Property "epoch"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchResult.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * For internal use only.  Call make(...) methods to create a new BSearchResult.
   */
  public BSearchResult()
  {
    this(BOrd.DEFAULT, null, BAbsTime.DEFAULT);
  }

  /**
   * Default constructor.
   *
   * @param ord    ORD of the object returned by getObject().
   * @param object Object that is the result of the search.  May be null to
   *               force lazy evaluation of the ORD.
   * @param epoch  epoch (version number, timestamp, etc.) that reflects the
   *               version, modification time, etc. of the object returned.
   */
  private BSearchResult(BOrd ord, BIObject object, BSimple epoch)
  {
    setOrd(ord == null ? BOrd.DEFAULT : ord);
    if (object != null)
      this.object = new SoftReference<>(object);
    setEpoch(epoch == null ? BAbsTime.now() : epoch);
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a new search result containing only the ord and epoch.  The
   * object is evaluated lazily by resolving the ord the first time the
   * getObject() accessor is called.
   *
   * @param ord absolute ord of the search result object
   * @return search result object
   */
  public static BSearchResult make(BOrd ord)
  {
    return new BSearchResult(ord, null, null);
  }

  /**
   * Make a new search result containing the ord, object, and epoch.
   *
   * @param ord    absolute ord of the search result object
   * @param object the (resolved) object referred to by the ord.  If null,
   *               lazy evaluation will be performed to resolve the ord the
   *               first time getObject() is called.
   * @return search result object
   */
  public static BSearchResult make(BOrd ord, BIObject object)
  {
    return make(ord, object, null);
  }

  /**
   * Make a new search result containing the ord, object, and epoch.
   *
   * @param ord    absolute ord of the search result object
   * @param object the (resolved) object referred to by the ord.  If null,
   *               lazy evaluation will be performed to resolve the ord the
   *               first time getObject() is called.
   * @param epoch  epoch of this object
   * @return search result object
   */
  public static BSearchResult make(BOrd ord, BIObject object, BSimple epoch)
  {
    BSearchResult result = new BSearchResult(ord, object, epoch);
    if (object instanceof Entity) result.applyTags((Entity)object, null);
    return result;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Returns the actual Object that this SearchResult references.
   * It is the Object referenced by the BOrd returned by getOrd().  May use
   * lazy evaluation which can throw the exceptions shown.
   *
   * @return resolved object referred to by the ord or null if the ord is BOrd.DEFAULT
   * @throws javax.baja.naming.UnknownSchemeException
   *          if the ord contains a query scheme not registered in this system
   * @throws javax.baja.naming.SyntaxException
   *          if the ord or a scheme specific query cannot be parsed due to invalid syntax
   * @throws javax.baja.naming.UnresolvedException
   *          if the ord cannot be resolved to a BObject
   */
  public BIObject getObject()
  {
    if ((object == null) || (object.get() == null))
    {
      if (!getOrd().equals(BOrd.DEFAULT))
      {
        // Resolves non-default ORD, otherwise returns null
        BIObject obj = getOrd().resolve().get();
        if (obj != null)
        {
          object = new SoftReference<>(obj);
          if (obj instanceof BINavNode)
          {
            BINavNode navNode = (BINavNode)obj;
            // The reason we are resetting the ORD here is to address the SystemDb use case.  If the
            // search occurred against the SystemDb, then if we get this far the original ORD set on
            // this BSearchResult will most likely be an "nspace" based ORD that resolves to a
            // Niagara virtual. The search view relies on nav ORDs, so we'll make the switch here.
            setOrd(navNode.getNavOrd().relativizeToSession());

            // Also in the same scenario, the search view needs to know the display name and type of
            // the local object (ie. Niagara virtual) for the search result and not the type of the
            // original (remote) object, so we'll replace the "n:type" tag on this SearchResult
            // instance with the type of the resolved object.
            CompUtil.setOrAdd(this, N_TYPE_SLOT_NAME, BString.make(obj.getType().toString()), null);
            CompUtil.setOrAdd(this, N_DISPLAYNAME_SLOT_NAME, BString.make(navNode.getNavDisplayName(null)), null);
          }
        }
      }
    }

    if (object != null)
      return object.get();

    return null;
  }

  /**
   * Directly set the object referenced by this search result.
   */
  public void setObject(BIObject object)
  {
    if (object != null)
      this.object = new SoftReference<>(object);
  }

  /**
   * Returns true only if the object has already been set or resolved.
   */
  public boolean hasObject()
  {
    return ((object != null) && (object.get() != null));
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  /**
   * Apply an Entity's tags as Property slots on this search result so the BajaScript
   * search ux framework can use them.  Not all tags are applied at the moment;
   * only the ones we think will be helpful for now.
   * @param e the Entity whose tags should be applied as Properties if appropriate
   */
  void applyTags(Entity e, Context cx)
  {
    Tags tags = e.tags();

    if (!applyTagIfPresent(tags, "n:displayName") && (e instanceof BComplex))
    {
      String displayName = ((BComplex)e).getDisplayName(null);
      if (displayName != null)
        add(N_DISPLAYNAME_SLOT_NAME, BString.make(displayName));
    }

    if (!applyTagIfPresent(tags, "n:type") && (e instanceof BIObject))
    {
      add(N_TYPE_SLOT_NAME, BString.make(((BIObject)e).getType().toString()));
    }

    if (!(e instanceof BIObject))
    {
      // Only add "n:history" tag if the history actually exists on this station
      Optional<BIDataValue> historyTag = tags.get(historyTagId);
      if (historyTag.isPresent())
      {
        BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
        try (HistorySpaceConnection conn = service.getDatabase().getConnection(cx))
        {
          BIHistory history = conn.getHistory(BHistoryId.make(historyTag.get().toString()));
          if (history == null || !history.getPermissions(cx).hasOperatorRead())
          {
            return; // skip adding the "n:history" tag to the search result
          }
        }
        catch (Exception ex)
        {
          return; // if we had an error, skip adding the n:history tag to the search result
        }
      }
    }

    if (!applyTagIfPresent(tags, "n:history") && (e instanceof BControlPoint))
    {
      BHistoryId.getHistoryIdFromPoint((BControlPoint)e).ifPresent(id ->
        add(N_HISTORY_SLOT_NAME, BString.make(id.toString())));
    }
  }

  private boolean applyTagIfPresent(Tags tags, String tagName)
  {
    Optional<BIDataValue> maybe = tags.get(Id.newId(tagName));
    if (maybe.isPresent())
    {
      CompUtil.setOrAdd(this, SlotPath.escape(tagName), maybe.get().as(BValue.class), null);
      return true;
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final Id historyTagId = Id.newId("n:history");
  private static final String N_TYPE_SLOT_NAME = SlotPath.escape("n:type");
  private static final String N_DISPLAYNAME_SLOT_NAME = SlotPath.escape("n:displayName");
  private static final String N_HISTORY_SLOT_NAME = SlotPath.escape("n:history");

  private SoftReference<BIObject> object;

}
