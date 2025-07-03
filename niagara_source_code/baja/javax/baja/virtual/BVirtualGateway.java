/**
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

import com.tridium.sys.schema.Fw;


/**
 * A virtual gateway is a component that resides in the station component space
 * that acts as a gateway a the virtual component space.  This means that the
 * Nav children for this gateway component returns the virtual components that
 * live in the virtual space.
 *
 * @author    Scott Hoye
 * @creation  25 Oct 06
 * @version   $Revision: 15$ $Date: 7/28/10 1:40:27 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public abstract class BVirtualGateway
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.virtual.BVirtualGateway(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVirtualGateway.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the virtual component space associated with this gateway.
   * A gateway's space is only available while the gateway is running
   * in a station or if the gateway is being accessed as a remote
   * proxy over Fox.
   */
  public final BComponentSpace getVirtualSpace()
  {
    return space;
  }


////////////////////////////////////////////////////////////////
// Virtual Gateway
////////////////////////////////////////////////////////////////

  /**
   * Creates a new virtual component space to use for this virtual gateway.
   * Subclasses can override this method if they wish to insert their
   * own custom virtual component space, otherwise a default one will be used.
   * <p>
   * NOTE: The virtual space created should have its root component
   * assigned in this method.  This is done by using the makeVirtualRoot()
   * method.
   */
  protected BVirtualComponentSpace makeVirtualSpace()
  {
    BVirtualComponentSpace vSpace = new BVirtualComponentSpace("virtual",
                                                               LexiconText.make("baja", "nav.virtual"),
                                                               BOrd.make("virtual:"),
                                                               this);
    BVirtualComponent root = makeVirtualRoot();
    if (root == null)
      throw new IllegalStateException("makeVirtualRoot() returned null.  Must return a valid root component for the virtual space.");
    vSpace.setRootComponent(root);
    return vSpace;
  }

  /**
   * Returns the root component to use for this virtual gateway's virtual space.
   * Subclasses can override this method if they wish to insert their
   * own custom virtual root component, otherwise a new virtual component will be
   * returned by default.
   */
  protected BVirtualComponent makeVirtualRoot()
  {
    return new BVirtualComponent();
  }

//////////////////////////////////////////////////////////////////
// Virtual Component Resolution
//////////////////////////////////////////////////////////////////

  /**
   * This method is called by the virtual space's LoadCallbacks when a
   * slot should be loaded/resolved under the given parent.
   * This is the time to load an individual slot, or return the existing
   * slot if already loaded.
   * <p>
   * The default implementation of this method is sufficient for most
   * scenarios.  By default, if the specified virtual path name does not
   * specify an existing slot under the given parent, then the addVirtualSlot()
   * callback will be made to give subclasses a chance to generate and add
   * the appropriate virtual slot to the the parent component.
   * <p>
   * NOTE:  Virtual slots added should always use a slot name that is the
   * escaped virtual path name ie. use VirtualPath.toSlotPathName(virtualPathName) as
   * the name of the virtual slot added.
   * <p>
   * NOTE:  Due to the possibility of a partial loaded state supported by
   * virtuals, subclasses should also be aware of the subscription state.
   * This means that a loadVirtualSlot() call for a new virtual slot
   * could occur while the parent is already subscribed.  So this could affect
   * how the new virtual slot should be handled (ie. added to a poll scheduler).
   * Subclasses should always be aware of this potential state.
   */
  public Slot loadVirtualSlot(BVirtualComponent parent, String virtualPathName)
  {
    if (parent == null) return null;

    Slot result = null;
    if (virtualPathName != null)
    {
      String virtualSlotName = VirtualPath.toSlotPathName(virtualPathName);
      result = parent.getSlot(virtualSlotName);
      if (result == null)
      {
        // Generate the virtual instance to add as a slot to the parent
        result = addVirtualSlot(parent, virtualPathName);
        if (result != null)
        { // Quick check to make sure the virtual naming contract is followed...
          String nameAssigned = result.getName();
          if (!nameAssigned.equals(virtualSlotName))
            log.warning("Name of virtual slot added is inconsistent: \""+nameAssigned+"\" was expected to be \""+virtualSlotName+"\"");
        }
      }
    }

    return result;
  }

  /**
  * This method is called by the default implementation of loadVirtualSlot()
  * and should be implemented by subclasses to add a new
  * instance of a virtual component (or a BValue instance to be a property
  * on a virtual component).  The value added should be based on the
  * given virtual path name and relative to the parent virtual component.
  * The contract is that the new slot added for the generated virtual
  * instance MUST be named by the escaped virtual path name
  * (ie. always use VirtualPath.toSlotPathName(virtualPathName) as the name of the slot added
  * to the parent).  This method should return the property for the slot added.
  * <p>
  * NOTE:  Due to the possibility of a partial loaded state supported by
  * virtuals, subclasses should also be aware of the subscription state.
  * This means that an addVirtualSlot() call for a new virtual slot
  * could occur while the parent is already subscribed.  So this could affect
  * how the new virtual slot should be handled (ie. added to a poll scheduler).
  * Subclasses should always be aware of this potential state.
  */
  protected abstract Property addVirtualSlot(BVirtualComponent parent, String virtualPathName);

  /**
   * This method is called by the virtual space's LoadCallbacks when
   * it is time for a virtual component to lazy load all of its dynamic
   * slots.  Subclasses should implement this method to
   * "discover" and dynamically add the direct children of the parent
   * virtual component instance provided.
   * <p>
   * NOTE:  Due to the possiblity of a "partial" loaded state, this
   * method may be called when the parent instance already has some
   * of its dynamic child slots loaded, but not all.  So this means
   * that it is important to check for existing slots on the parent
   * before adding any new dynamic slots of the same name.
   * <p>
   * NOTE:  Due to the possibility of a partial loaded state supported by
   * virtuals, subclasses should also be aware of the subscription state.
   * This means that a loadVirtualSlots() call
   * could occur while the parent is already subscribed.  So this could affect
   * how any new virtual slots added should be handled (ie. added to a poll scheduler).
   * Subclasses should always be aware of this potential state.
   */
  public abstract void loadVirtualSlots(BVirtualComponent parent);

  /**
   * This method is called when a batch of virtual Ords are
   * pending resolution.  For example, when a Px graphic is first
   * loaded that contains a bunch of virtual Ord bindings, these
   * virtual Ords are bundled into a single request, so that they
   * can be "pre-loaded" in batch.  Subclasses may wish to override
   * this method to pre-load the virtual objects for the given virtual
   * Ords, especially if they can be resolved in batch for efficiency.
   * By default, this method doesn't do anything, which will lead to
   * each virtual Ord being resolved individually, causing individual
   * loadVirtualSlot() callbacks, which could be less efficient.
   * <p>
   * NOTE:  Due to the possiblity of a "partial" loaded state, this
   * method may be called with virtual Ords that resolve to existing
   * virtual components.  So this means that it is important to check
   * for existing slots on the resolved virtuals before adding any new
   * dynamic slots of the same name.
   */
  public void ensureVirtualsLoaded(BOrd[] virtualOrds) { }

////////////////////////////////////////////////////////////////
// BINavNode Overrides
////////////////////////////////////////////////////////////////

  /**
   * Overridden to return the nav ord of the virtual space's root component.
   */
  @Override
  public final BOrd getNavOrd()
  {
    BOrd navOrd = super.getNavOrd();
    if (navOrd != null)
      navOrd = BOrd.make(navOrd, "virtual:/");
    return navOrd;
  }

  /**
   * Overridden to return the nav children state in the virtual space.
   */
  @Override
  public boolean hasNavChildren()
  {
    if (space != null)
      return space.hasNavChildren();
    else
      return super.hasNavChildren();
  }

  /**
   * Overridden to return the nav child in the virtual space.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    if (space != null)
      return space.getNavChild(navName);
    else
      return super.getNavChild(navName);
  }

  /**
   * Overridden to resolve the nav child in the virtual space.
   */
  @Override
  public BINavNode resolveNavChild(String navName)
  {
    if (space != null)
      return space.resolveNavChild(navName);
    else
      return super.resolveNavChild(navName);
  }

  /**
   * Overridden to return the nav children of the virtual space.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    if (space != null)
      return space.getNavChildren();
    else
      return super.getNavChildren();
  }

////////////////////////////////////////////////////////////////
// Component Presentation Overrides
////////////////////////////////////////////////////////////////

  /**
   * Override the agent list to remove the pxEditor, since
   * px views are not allowed on virtual gateways.  Also
   * removes the Wiresheet and LinkSheet views since linking
   * is not allowed for virtual components, and Tag Manager
   * as tags are not allowed on virtual components.
   */
  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.remove("pxEditor:PxEditor");
    list.remove("wiresheet:WireSheet");
    list.remove("wiresheet:WebWiresheet");
    list.remove("workbench:LinkSheet");
    list.remove("tagdictionary:TagManager");
    return list;
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/virtual.png");


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Overridden to be sure to include spy info about the virtual gateway's
   * corresponding virtual space.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    // First append virtual space info
    out.startProps();
    out.trTitle("VirtualSpace", 2);
    out.prop("virtualSpaceType", (space != null)?space.getType():null);
    out.endProps();
    if(space != null)
      space.spy(out);

    // Then append normal virtual gateway component info
    out.startProps();
    out.trTitle("VirtualGateway", 2);
    out.endProps();
    super.spy(out);
  }


////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:   fwStarted(); break;
      case Fw.STOPPED:   fwStopped(); break;
      case Fw.SET_SPACE: setSpace((BComponentSpace)a); break;
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * The started() method is called when a component's
   * running state moves to true.  Components are started
   * top-down, children after their parent.
   */
  void fwStarted()
  {
    BVirtualComponentSpace vSpace = makeVirtualSpace();
    setSpace(vSpace);
    vSpace.start(); // start the virtual Space
  }

  /**
   * The stopped() method is called when a component's
   * running state moves to false.
   */
  void fwStopped()
  {
    if (space != null)
    {
      try
      {
        // stop the virtual space
        if (space instanceof BVirtualComponentSpace)
          ((BVirtualComponentSpace)space).stop();
      }
      finally
      {
        space = null;
      }
    }
  }

  /**
   * Set the associated component space for this gateway.
   */
  void setSpace(BComponentSpace space)
  {
    this.space = space;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final Logger log = Logger.getLogger("virtual.gateway");

  BComponentSpace space;

}
