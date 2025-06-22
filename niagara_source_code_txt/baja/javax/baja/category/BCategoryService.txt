/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.role.BIRole;
import javax.baja.role.BRoleService;
import javax.baja.rpc.NiagaraRpc;
import javax.baja.rpc.Transport;
import javax.baja.rpc.TransportType;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.security.BPermissionsMap;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIService;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.util.CategoryValidator;
import com.tridium.util.HistoryCategoryUtil;

/**
 * BCategoryService maps BCategoryMasks bits to BCategory components.
 *
 * @author    Brian Frank
 * @creation  12 Feb 05
 * @version   $Revision: 7$ $Date: 4/15/09 1:30:41 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Used to map ords to categories for ICategorizable objects
 which aren't capabable of storing their own category mask.
 */
@NiagaraProperty(
  name = "ordMap",
  type = "BOrdToCategoryMap",
  defaultValue = "BOrdToCategoryMap.NULL",
  flags = Flags.READONLY | Flags.HIDDEN
)
/*
 Frequency of automatic updates, or zero to disable.
 */
@NiagaraProperty(
  name = "updatePeriod",
  type = "BRelTime",
  defaultValue = "BRelTime.make(60000L)"
)
/*
 Update causes a rescan of the complete component database
 to recompute categories.
 */
@NiagaraAction(
  name = "update"
)
public final class BCategoryService
  extends BComponent
  implements BIService, BIRestrictedComponent
{            

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.category.BCategoryService(1728556185)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ordMap"

  /**
   * Slot for the {@code ordMap} property.
   * Used to map ords to categories for ICategorizable objects
   * which aren't capabable of storing their own category mask.
   * @see #getOrdMap
   * @see #setOrdMap
   */
  public static final Property ordMap = newProperty(Flags.READONLY | Flags.HIDDEN, BOrdToCategoryMap.NULL, null);

  /**
   * Get the {@code ordMap} property.
   * Used to map ords to categories for ICategorizable objects
   * which aren't capabable of storing their own category mask.
   * @see #ordMap
   */
  public BOrdToCategoryMap getOrdMap() { return (BOrdToCategoryMap)get(ordMap); }

  /**
   * Set the {@code ordMap} property.
   * Used to map ords to categories for ICategorizable objects
   * which aren't capabable of storing their own category mask.
   * @see #ordMap
   */
  public void setOrdMap(BOrdToCategoryMap v) { set(ordMap, v, null); }

  //endregion Property "ordMap"

  //region Property "updatePeriod"

  /**
   * Slot for the {@code updatePeriod} property.
   * Frequency of automatic updates, or zero to disable.
   * @see #getUpdatePeriod
   * @see #setUpdatePeriod
   */
  public static final Property updatePeriod = newProperty(0, BRelTime.make(60000L), null);

  /**
   * Get the {@code updatePeriod} property.
   * Frequency of automatic updates, or zero to disable.
   * @see #updatePeriod
   */
  public BRelTime getUpdatePeriod() { return (BRelTime)get(updatePeriod); }

  /**
   * Set the {@code updatePeriod} property.
   * Frequency of automatic updates, or zero to disable.
   * @see #updatePeriod
   */
  public void setUpdatePeriod(BRelTime v) { set(updatePeriod, v, null); }

  //endregion Property "updatePeriod"

  //region Action "update"

  /**
   * Slot for the {@code update} action.
   * Update causes a rescan of the complete component database
   * to recompute categories.
   * @see #update()
   */
  public static final Action update = newAction(0, null);

  /**
   * Invoke the {@code update} action.
   * Update causes a rescan of the complete component database
   * to recompute categories.
   * @see #update
   */
  public void update() { invoke(update, null, null); }

  //endregion Action "update"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCategoryService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get the CategoryService or throw ServiceNotFoundException.
   */
  public static BCategoryService getService()
  {                                                             
    return (BCategoryService)Sys.getService(TYPE);
  }
                                                                              
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get all the categories configured.
   */
  public BCategory[] getCategories()
  {
    return getChildren(BCategory.class);
  }
  
  /**
   * Get the maximum category index currently defined in this
   * service.
   */
  public int getMaxCategoryIndex()
  {
    if (!isRunning())
    {
      throw new NotRunningException();
    }
    
    for (int i = lookup.length-1; i > 0; i--)
    {
      if (lookup[i] != null)
      {
        return i;
      }
    }
    
    return 0;
  }
  
  /**
   * Get a category by index or return null if index not configured.
   *
   * @throws NotRunningException if not in a running station.
   */
  public BCategory getCategory(int index)
  { 
    if (!isRunning())
    {
      throw new NotRunningException();
    }
    if (index >= lookup.length)
    {
      return null;
    }
    return lookup[index];
  }

  /**
   * Get the list of categories for the specified mask.  Ignore
   * any bits in the mask which don't have a category configured.
   *
   * @throws NotRunningException if not in a running station.
   */
  public BCategory[] getCategories(BCategoryMask mask)
  { 
    if (!isRunning())
    {
      throw new NotRunningException();
    }
    
    int size = Math.min(mask.size(), lookup.length) + 1;
    Array<BCategory> acc = new Array<>(BCategory.class, size);
    
    for(int i=1; i<size; ++i)
    {
      if (mask.get(i) && lookup[i] != null)
      {
        acc.add(lookup[i]);
      }
    }
    
    return acc.trim();
  }

  /**
   * Map the ord to a mask using the ordMap property.  This method 
   * is used to implement {@code getCategoryMask()} by
   * ICategorizable objects incapable of storing their own mask.
   * If no match find, then return a default mask of "1".
   */
  public BCategoryMask getCategoryMask(BOrd ord)
  {                               
    if (ord != null && !ord.isNull())
    {

      BCategoryMask mask = getOrdMap().getCategoryMask(ord);
      if (mask != null)
      {
        return mask;
      }
    }
    return DEFAULT_MASK;
  }  

  /**
   * Map the ord to a mask using the ordMap property.  This method 
   * is used to implement {@code getAppliedCategoryMask()} by
   * ICategorizable objects incapable of storing their own mask.
   * If no match find, then return a default mask of "1".
   */
  public BCategoryMask getAppliedCategoryMask(BOrd ord)
  {             
    if (ord != null && !ord.isNull())
    {
      BCategoryMask mask = getOrdMap().getAppliedCategoryMask(ord);
      if (mask != null)
      {
        return mask;
      }
    }
    return DEFAULT_MASK;
  }  

////////////////////////////////////////////////////////////////
// Service Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Register this component under "baja:CategoryService".
   */
  @Override
  public Type[] getServiceTypes()
  {
    return serviceTypes;
  }
  private static final Type[] serviceTypes = { TYPE };

  /**
   * This convenience method (called on startup) will loop through the current
   * {@link #ordMap} and convert any legacy ORDs to the current supported form.
   * For example, NCCB-44069 required converting certain history ORDs to
   * shorthand form, and then NCCB-56823 required ensuring all ORDs are
   * lowercase and removes any duplicates (giving preference to ones
   * that are already lowercase).
   */
  private void convertLegacyOrdsInMap()
  {
    BOrdToCategoryMap ordMap = getOrdMap();
    List<String> origOrdStrings = new ArrayList<>(ordMap.size());
    List<BOrd> lowerCaseOrds = new ArrayList<>(ordMap.size());
    List<BCategoryMask> cats = new ArrayList<>(ordMap.size());

    for (int i=0; i<ordMap.size(); i++)
    {
      BOrd ord = ordMap.getOrd(i);
      String origOrdStr = ord.relativizeToSession().encodeToString();

      // Check for duplicate ORDs and remove them, favoring the last one that is already
      // in lowercase form, since that should be the most recent change (4.9+).
      String lowerCaseOrdStr = TextUtil.toLowerCase(origOrdStr);
      boolean origOrdIsLowercase = origOrdStr.equals(lowerCaseOrdStr);
      BOrd lowerCaseOrd = BOrd.make(lowerCaseOrdStr);
      int idxOfDuplicate = lowerCaseOrds.indexOf(lowerCaseOrd);
      if (origOrdIsLowercase || idxOfDuplicate < 0 ||
          !origOrdStrings.get(idxOfDuplicate).equals(lowerCaseOrdStr))
      {
        if (idxOfDuplicate >= 0)
        {
          origOrdStrings.remove(idxOfDuplicate);
          lowerCaseOrds.remove(idxOfDuplicate);
          cats.remove(idxOfDuplicate);
        }

        origOrdStrings.add(origOrdStr);

        if (lowerCaseOrdStr.startsWith("history:"))
        {
          // NCCB-44069
          lowerCaseOrds.add(HistoryCategoryUtil.convertToShorthand(ord, Sys.getStation().getStationName()));
        }
        else
        {
          // NCCB-56823
          lowerCaseOrds.add(lowerCaseOrd);
        }
        cats.add(ordMap.getCategoryMask(i));
      }
    }
    setOrdMap(BOrdToCategoryMap.make(lowerCaseOrds.toArray(EMPTY_ORDS), cats.toArray(EMPTY_MASKS)));
  }

  @Override
  public void serviceStarted()
    throws Exception
  {
    convertLegacyOrdsInMap();
  }

  @Override
  public void serviceStopped()
    throws Exception
  {
  }           
  
  @Override
  public void started()
    throws Exception
  {
    rebuildLookup();
    thread = new UpdateThread();
    thread.start();
  }

  @Override
  public void stopped()
    throws Exception
  {
    lookup = null;
    if (thread != null)
    {
      thread.kill();
    }
    thread = null;  
  }  

  @Override
  public void added(Property prop, Context cx)
  {
    super.added(prop, cx);
    if (isRunning())
    {
      rebuildLookup();
    }
  }
  
  @Override
  public void removed(Property prop, BValue old, Context cx)
  {
    super.removed(prop, old, cx);
    if (isRunning())
    {
      rebuildLookup();
    }
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one allowed to live under the station's BServiceContainer.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

////////////////////////////////////////////////////////////////
// Lookup
////////////////////////////////////////////////////////////////

  void rebuildLookup()
  {             
    BCategory[] lookup = new BCategory[16];

    // process all my children
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    { 
      // process next category
      BObject child = c.get();                    
      if (!(child instanceof BCategory))
      {
        continue;
      }

        BCategory cat = (BCategory)child;

        // check for disabled
        int index = cat.getIndex();
        if (index <= 0)
        {
          cat.setStatus(BStatus.disabled);
          cat.setFaultCause("");
          continue;
        }

        // grow array if needed
        if (lookup.length <= index)
        {
          int len = Math.max(lookup.length*2, index+1);
          if (len < 10)
          {
            len = 10;
          }
          BCategory[] temp = new BCategory[len];
          System.arraycopy(lookup, 0, temp, 0, lookup.length);
          lookup = temp;
        }

        // check for duplicate
        if (lookup[index] != null)
        {
          cat.setStatus(BStatus.fault);
          cat.setFaultCause("Duplicate index with " + lookup[index].getName());
          continue;
        }

        // all is ok
        lookup[index] = cat;

      // Set common BAbstractCategory properties
      ((BAbstractCategory)child).setStatus(BStatus.ok);
      ((BAbstractCategory)child).setFaultCause("");
    }

    // switch to table I just rebuilt
    this.lookup = lookup; 
  }

  void rebuildFilters()
  {
    rebuildLookup();
  }


////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////

  public void doUpdate()
  {                                  
    long t1 = Clock.ticks();
    
    // create an array of working masks, each index mapping
    // to a depth of the component tree (we should be able to
    // safely assume a depth no greater than 256 I hope)
    BCategoryMask[] working = new BCategoryMask[256];
    for(int i=0; i<working.length; ++i)
    {
      working[i] = BCategoryMask.NULL;
    }
    
    // make a private call into the slot map to walk the 
    // tree to update the "deep or" mask
    ComponentSlotMap slotMap = (ComponentSlotMap)Sys.getStation().fw(Fw.SLOT_MAP);
    slotMap.updateDeepOr(working, 0);
    
    long t2 = Clock.ticks();
    lastUpdateTicks = t2;
    lastUpdateDuration = t2-t1;
  }                
  
  class UpdateThread extends Thread
  {                 
    UpdateThread() 
    { 
      super("CategoryService:Update"); 
      setDaemon(true); 
      setPriority(getPriority()-1);
    }
    
    public void kill()
    {
      alive = false;
      interrupt();
    }
    
    @Override
    public void run()
    {
      while(alive)
      {
        try
        {                                  
          // sleep for a while
          Thread.sleep(5000);
          
          // if period is zero then disable
          long period = getUpdatePeriod().getMillis();
          if (period == 0)
          {
            continue;
          }
          
          // make sure period is never too fast
          if (period < 1000)
          {
            period = 1000;
          }
          
          // if period has not elapsed, go back to sleep
          if (Clock.ticks() - lastUpdateTicks < period)
          {
            continue;
          }
          
          // time do to an update
          doUpdate();
        }         
        catch(InterruptedException ignored)
        {
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }
    }            
    
    boolean alive = true;
  }

  /**
   * Utility method to allow a user with operator read privileges on the
   * BCategoryService to remotely invoke the {@link #update()} action on this
   * BCategoryService instance. This is needed since certain views (such as the
   * Category Browser and Category Sheet views) don't necessarily require admin
   * invoke privileges on the BCategoryService to load and modify category
   * settings for station objects. In those cases, those views still need to
   * call the {@link #update()} action to synchronously update the "deep or"
   * masks. Therefore, this RPC call allows the {@link #update()} action to be
   * called in such cases by users who do not have admin invoke privileges.
   * Since the {@link #update()} action is called automatically (at a periodic
   * interval), it is safe to allow a non-admin invoke user to invoke it
   * remotely via this mechanism as long as the user has at least basic
   * (operator read) access to the BCategoryService.
   *
   * @since Niagara 4.8
   * @param cx the context associated with the RPC call
   */
  @NiagaraRpc(
    permissions = "r",
    transports = {
      @Transport(type = TransportType.box),
      @Transport(type = TransportType.fox),
      @Transport(type = TransportType.web)
    }
  )
  public void updateRpc(Context cx)
  {
    update();
  }

  /**
   * Utility method to allow a user with admin read privileges on the
   * BCategoryService to remotely retrieve deepOr category masks for the given
   * list of String ORDs referencing station objects. The result list of
   * deepOr category masks is guaranteed to have the same number of entries as
   * the request list.  The context user must have at least operator read access
   * to a resolved ORD in the request list, otherwise a
   * {@link BCategoryMask#NULL} value will be returned for that entry. If there
   * are any problems retrieving the deepOr category mask for any entry (e.g.
   * the resolved ORD is not a {@link BICategorizable} that lives in the
   * component space), then a {@link BCategoryMask#NULL} value will be returned
   * for that entry.
   *
   * @since Niagara 4.8
   * @param ords A list of String ORDs for which this RPC call should attempt
   *             to resolve and retrieve the deepOr category masks
   * @param cx the context associated with the RPC call
   * @return A list of String encoded deepOr category masks that match up with
   *         the list of requested String ORDs.
   */
  @NiagaraRpc(
    permissions = "R",
    transports = @Transport(type = TransportType.fox)
  )
  public List<String> retrieveDeepOrMasks(List<String> ords, Context cx)
  {
    return ords.stream()
      .map(ord ->
        {
          BCategoryMask mask = BCategoryMask.NULL;
          try
          {
            BObject obj = BOrd.make(ord).relativizeToSession().get(this, cx);

            if (cx != null && cx.getUser() != null && obj instanceof BIProtected)
            {
              if (!cx.getUser().getPermissionsFor((BIProtected)obj).hasOperatorRead())
              {
                return mask.encodeToString();
              }
            }

            if (obj.isComponent())
            {
              BComponent comp = obj.asComponent();
              mask = ((ComponentSlotMap)comp.fw(Fw.SLOT_MAP)).getDeepOrCategoryMask();
            }
          }
          catch(Exception ignore) { }
          return mask.encodeToString();
        })
      .collect(Collectors.toList());
  }

////////////////////////////////////////////////////////////////
// Remove check
////////////////////////////////////////////////////////////////
  
  /**
   * Check that the category being removed is not being referenced by a Role.
   */
  @Override
  public void checkRemove(Property property, Context context)
  {
    BValue value = get(property);
    // This is a station side check because we need to get the roles from the running BIRoleService
    if(value instanceof BCategory && Sys.getStation() != null)
    {
      // Get the RoleIDs from the RoleService
      BRoleService roleService = BRoleService.getService();

      if (roleService == null)
      {
        throw new LocalizableRuntimeException("baja", "category.removal.cannotFindValidRoleService");
      }
      List<String> roles = roleService.getRoleIds();
      // Check each role to see if it refers to this category
      for (String roleId : roles)
      {
        BIRole role = roleService.getRole(roleId);
        if (role == null)
        {
          throw new LocalizableRuntimeException("baja",
            "category.removal.cannotFindValidRoleService",
            new String[] { roleId });
        }
        BPermissionsMap pmap = role.getPermissions();
        // Skip checks if the permissions map for this role is superuser.
        // Categories do not come into play with superuser permissions.
        if (pmap.isSuperUser())
        {
          continue;
        }
        // Check against the permissions map BCategories
        for (BCategory category : getCategories())
        {
          if (category.getIndex() == ((BCategory)value).getIndex() &&
            pmap.getPermissions(category.getIndex()) != BPermissions.none)
          {
            throw new LocalizableRuntimeException("baja",
              "category.removal.categoryIsReferenced",
              new String[] { ((BComplex)value).getName(), roleId });
          }
        }
      }
    }
    super.checkRemove(property, context);
  }

////////////////////////////////////////////////////////////////
// IPropertyValidator
////////////////////////////////////////////////////////////////

  /**
   * Returns a category validator to validate server-side user changes to the {@link #ordMap}
   * property.
   *
   * @since Niagara 4.8
   */
  @Override
  public IPropertyValidator getPropertyValidator(Property[] properties, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      return CategoryValidator.INSTANCE;
    }
    else
    {
      return null;
    }
  }

  /**
   * Returns a category validator to validate server-side user changes to the {@link #ordMap}
   * property.
   *
   * @since Niagara 4.8
   */
  @Override
  public IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      return CategoryValidator.INSTANCE;
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/categoryService.png");

////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {                      
    if (lookup != null)
    {                               
      out.startProps("CategoryService");
      out.prop("lastUpdateTicks",    BRelTime.toString(Clock.ticks()-lastUpdateTicks));
      out.prop("lastUpdateDuration", BRelTime.toString(lastUpdateDuration));
      out.endProps();
      
      out.startTable(true);
      out.trTitle("Lookup Table [" + lookup.length + "]", 2);
      for(int i=0; i<lookup.length; ++i)
      {          
        if (lookup[i] != null)
        {
          out.tr("" + i, lookup[i].getName());
        }
      }
      out.endTable();
    }
    
    super.spy(out);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final BCategoryMask DEFAULT_MASK = BCategoryMask.make("1");
  private static final BOrd[] EMPTY_ORDS = new BOrd[0];
  private static final BCategoryMask[] EMPTY_MASKS = new BCategoryMask[0];
  
  BCategory[] lookup;       // fast lookup table by index
  UpdateThread thread;      // update background thread   
  long lastUpdateTicks;     // ticks of last update
  long lastUpdateDuration;  // time it take last update to complete
} 
