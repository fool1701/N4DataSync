/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.file.BITemplate;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.util.BUnrestrictedFolder;
import javax.baja.virtual.*;

import com.tridium.virtual.BUnresolvedVirtualHandler;

/**
 * BSlotScheme is used to navigate the slot tree.
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 20$ $Date: 9/8/09 1:59:07 PM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType(
  ordScheme = "slot"
)
@NiagaraSingleton
public class BSlotScheme
    extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BSlotScheme(1383525928)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BSlotScheme INSTANCE = new BSlotScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSlotScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BSlotScheme()
  {
    super("slot");
  }

  /**
   * Create a BSlotScheme with the specified ID.
   *
   * @since     Niagara 3.2
   */
  protected BSlotScheme(String id)
  {
    super(id);
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of SlotPath.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new SlotPath(getId(), queryBody);
  }

  /**
   * Resolve the SlotPath query to a OrdTarget.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
      throws SyntaxException, UnresolvedException
  {
    OrdTarget target = null;
    RuntimeException ex = null;
    try
    {
      target = doResolve(base, query);
    }
    catch(RuntimeException e)
    {
      ex = e;
    }

    if (target == null)
    { // If there was a failure to resolve, let's check to see if the base is a BVirtualGateway, and
      // if so, look to see if there is a BUnresolvedVirtualHandler agent installed against the
      // BVirtualGateway. If there are any such agents registered, give them a chance to resolve
      // the virtual that couldn't be resolved by the normal mechanism (stop when the first agent
      // is successfully able to resolve it).
      target = BUnresolvedVirtualHandler.getFailedVirtualQuery(base, query, null);

      if (target == null && ex != null)
      { // If no registered BIUnresolvedVirtualHandlers could resolve the virtual,
        // throw the original resolution error (if any, otherwise null will be returned)
        throw ex;
      }
    }

    return target;
  }

  private OrdTarget doResolve(OrdTarget base, OrdQuery query)
  {
    BObject baseObject = base.get();
    BIPropertyContainer baseContainer = null;
    boolean baseContainerMayReallyBeParent = false;   // NCCB-37627, NCCB-35805
    SlotPath path = (SlotPath)query;
    boolean isVirtualPath = path instanceof VirtualPath;
    BComponentSpace space = null; // 10/30/06 - Virtual changes

    // figure out the space from the base
    if ((baseObject instanceof BVirtualGateway) && isVirtualPath)
    {
      space = ((BVirtualGateway)baseObject).getVirtualSpace();
      baseContainer = space.getRootComponent();
    }
    else if (baseObject instanceof BComponentSpace)
    {
      space = (BComponentSpace)baseObject; // 10/30/06 - Virtual changes
      baseContainer = space.getRootComponent();
    }
    else if (baseObject instanceof BComponent)
    {
      BComponent baseComponent = baseObject.asComponent();
      space = baseComponent.getComponentSpace(); // 10/30/06 - Virtual changes
      if (path.isAbsolute())
      {
        baseComponent = space.getRootComponent();
        if (baseComponent instanceof BUnrestrictedFolder)
        {
          // NCCB-35805: Handle absolute paths for BOGs embedded in station and application
          // templates where the root station is the first child of an unrestricted folder that
          // will have either one or two child components.
          // NCCB-37627: Actually, templates can have absolute slot paths of either type: resolved
          // from the station component or from the station's parent (unrestricted folder)!
          // TODO: Can we isolate this code to only happen against templates?
          // TODO: What about component templates? Do absolute paths even make sense?
          final BComponent[] children = baseComponent.getChildComponents();
          if (children.length == 1 || children.length == 2)
          {
            final BComponent first = children[0];
            if (first instanceof BStation)
            {
              baseComponent = first;
              baseContainerMayReallyBeParent = true;
            }
          }
        }
      }
      baseContainer = baseComponent;
    }
    else if (baseObject instanceof BIPropertyContainer)
    {
      baseContainer = (BIPropertyContainer)baseObject;
    }
    else if (baseObject instanceof BITemplate)
    {
      baseContainer = ((BITemplate)baseObject).getBaseComponent();   // space =
    }

    // hack for virtual space
    // Don't return the virtual space if the base object is already a virtual component. That can
    // occur if the preceding OrdQuery in the ORD resolution was not a "virtual" OrdScheme, but it
    // happened to resolve to a virtual component. For example, hierarchy OrdQueries can resolve to
    // virtual components when resolved against the SystemDb.
    if (isVirtualPath && !(baseObject instanceof BVirtualComponent) && path.getBody().isEmpty())
    {
      return new OrdTarget(base, space);
    }

    // get starting value
    BObject value = (BObject)baseContainer;
    if (value == null)
    {
      if (!(baseObject instanceof BValue))
        throw new InvalidOrdBaseException(""+baseObject);
      value = baseObject;
    }

    // now we walk down the path
    final BIPropertyContainer startContainer = baseContainer;
    BIPropertyContainer container = startContainer;
    Slot slot = null;
    final int depth = path.depth();
    Property[] propPath = null;
    int pathDepth = 0;
    final int backupDepth = path.getBackupDepth();

    // first walk up using backup
    for(int i=0; i<backupDepth; ++i)
    {
      container = (container instanceof BComponent)?(BComponent)(((BComponent)container).getParent()):null;
      value = (BObject)container;
      if (value == null)
        throw new UnresolvedException("Cannot walk backup depth" + baseObject);
    }

    // walk down starting at base
    for(int i=0; i<depth; ++i)
    {
      String nameAtDepth = path.nameAt(i);
      if (isVirtualPath)
        nameAtDepth = VirtualPath.toSlotPathName(nameAtDepth);

      // get the slot at current depth

      // 11/2/06 - Virtual Points - changed how we retrieve the slot:
      //   Old approach -
      //     1. Call loadSlots (loads all child slots).
      //     2. Get the child slot (getSlot).
      //     3. If child slot is null, throw an UnresolvedException.
      //   Here's what the old approach looked like:
      // value.asComplex().loadSlots();
      // slot = value.asComplex().getSlot(path.nameAt(i));
      //
      //   New approach -
      //     1. Try to get the child slot without calling loadSlot(s) first (ie. already loaded).
      //     2. If null, give the space a chance to load the individual slot (loadSlot) and call getSlot.
      //     3. If still null, call loadSlots (loads all child slots), then getSlot again.
      //     4. If child slot is still null, throw an UnresolvedException.

      // 1/4/07 - Remove Step 1, as this was causing problems (refer to pacman issues 9404 and 9406.
      // Step 1 isn't really necessary, as the loadSlot() call can check for slot existence.
      // The bug with Step 1 is that it was sometimes preventing the loadSlots() call (in Step 3)
      // which was needed for dynamic children when first loaded.

      // If true, this flag will indicate that a loadSlots is necessary.
      // Prior to this flag, the next iteration in this for loop wasn't functioning
      // correctly since slot != null.  This change fixes the test suite.
      boolean performLoadSlots = true;

      // Step 1. Try to get the slot (check for already loaded condition)
      //slot = value.asComplex().getSlot(nameAtDepth);

      if (/*(slot == null) && */(space != null) && (value.isComponent()))
      { // Step 2 - next attempt is to give the space a chance to load the individual slot
        slot = space.getLoadCallbacks().loadSlot(value.asComponent(), nameAtDepth);
        performLoadSlots = (slot == null);
      }

      boolean isPropContainer = (value instanceof BIPropertyContainer);
      if (performLoadSlots)
      { // Step 3 - default back to original method of calling loadSlots()
        if (isPropContainer)
        {
          ((BIPropertyContainer)value).loadSlots();
          slot = ((BIPropertyContainer)value).getSlot(nameAtDepth);
        }
        else
        {
          value.asComplex().loadSlots();
          slot = value.asComplex().getSlot(nameAtDepth);
        }
      }

      // NCCB-37627 HACK
      if (slot == null && baseContainerMayReallyBeParent)
      {
        // We ran into an ambiguous dead-end, reset everything (including i) and try again.
        baseContainerMayReallyBeParent = false;
        i = -1;
        container = (BComponent)(((BComponent)startContainer).getParent());
        value = (BObject)container;
        propPath = null;
        pathDepth = 0;
        continue;
      }

      if (slot == null)
      { // Step 4 - if still null, throw an UnresolvedException
        //if (space instanceof BIVirtualComponentSpace) // 10/30/06 - Virtual changes
        //  slot = ((BIVirtualComponentSpace)space).resolveVirtualComponent(value.asComplex(), path.nameAt(i));
        //if (slot == null)
        throw new UnresolvedException(path.getBody());
      }

      // action/topics must be at final depth
      if (!(slot instanceof Property))
      {
        if (i != depth-1)
          throw new UnresolvedException(path.getBody());

        return new OrdTarget(base, container, null, slot, null);
      }

      // must be a property
      Property prop = (Property)slot;
      value = (isPropContainer)?((BIPropertyContainer)value).get(prop):value.asComplex().get(prop);
      if (pathDepth == 0 && value instanceof BIPropertyContainer)
      {
        container = (BIPropertyContainer)value;
      }
      else
      {
        if (propPath == null) propPath = new Property[32];
        propPath[pathDepth++] = prop;
      }
    }

    // if container was leaf
    if (pathDepth == 0)
      return new OrdTarget(base, (BObject)container);

    // if leaf was direct child of container we don't
    // lazy allocate property path in Deref.propertyPath()
    slot = propPath[0];
    Property[] resize = new Property[pathDepth];
    System.arraycopy(propPath, 0, resize, 0, pathDepth);
    return new OrdTarget(base, container, value, slot, resize);
  }
}
