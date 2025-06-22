/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

import java.util.ArrayList;
import java.util.List;

import javax.baja.authn.BAuthenticationScheme;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BAbstractAuthenticator;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.sys.Validatable;

/**
 * BUserPrototypes is the container for prototype users in
 * the user service.  The defaultUser defines the properties
 * for new users.
 * 
 * @author    John Sublett
 * @creation  16 Aug 2007
 * @version   $Revision: 2$ $Date: 10/9/07 12:00:03 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "defaultPrototype",
  type = "BUser",
  defaultValue = "makeDefaultPrototype()"
)
@NiagaraProperty(
  name = "alternateDefaultPrototype",
  type = "String",
  defaultValue = "defaultPrototype",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"workbench:UserPrototypeFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"webEditors:UserPrototypeEditor\"")
  }
)
@NiagaraTopic(
  name = "userEvent",
  eventType = "BUserEvent"
)
public class BUserPrototypes
  extends BComponent
  implements IPropertyValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserPrototypes(3700404094)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultPrototype"

  /**
   * Slot for the {@code defaultPrototype} property.
   * @see #getDefaultPrototype
   * @see #setDefaultPrototype
   */
  public static final Property defaultPrototype = newProperty(0, makeDefaultPrototype(), null);

  /**
   * Get the {@code defaultPrototype} property.
   * @see #defaultPrototype
   */
  public BUser getDefaultPrototype() { return (BUser)get(defaultPrototype); }

  /**
   * Set the {@code defaultPrototype} property.
   * @see #defaultPrototype
   */
  public void setDefaultPrototype(BUser v) { set(defaultPrototype, v, null); }

  //endregion Property "defaultPrototype"

  //region Property "alternateDefaultPrototype"

  /**
   * Slot for the {@code alternateDefaultPrototype} property.
   * @see #getAlternateDefaultPrototype
   * @see #setAlternateDefaultPrototype
   */
  public static final Property alternateDefaultPrototype = newProperty(0, "defaultPrototype", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "workbench:UserPrototypeFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "webEditors:UserPrototypeEditor")));

  /**
   * Get the {@code alternateDefaultPrototype} property.
   * @see #alternateDefaultPrototype
   */
  public String getAlternateDefaultPrototype() { return getString(alternateDefaultPrototype); }

  /**
   * Set the {@code alternateDefaultPrototype} property.
   * @see #alternateDefaultPrototype
   */
  public void setAlternateDefaultPrototype(String v) { setString(alternateDefaultPrototype, v, null); }

  //endregion Property "alternateDefaultPrototype"

  //region Topic "userEvent"

  /**
   * Slot for the {@code userEvent} topic.
   * @see #fireUserEvent
   */
  public static final Topic userEvent = newTopic(0, null);

  /**
   * Fire an event for the {@code userEvent} topic.
   * @see #userEvent
   */
  public void fireUserEvent(BUserEvent event) { fire(userEvent, event, null); }

  //endregion Topic "userEvent"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserPrototypes.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Create the default prototype.
   */
  public static BUser makeDefaultPrototype()
  {
    BUser proto = new BUser();
    
    Property[] localProps =
      {
      BUser.permissions,
      BUser.roles,
      BUser.navFile
    };
    
    for (int i = 0; i < localProps.length; i++)
    {
      proto.setFlags(localProps[i],
                     Flags.USER_DEFINED_1 | proto.getFlags(localProps[i]));
    }
    return proto;
  }

  /**
   * Get the names of the prototypes not including the default user.
   */
  public String[] getPrototypeNames()
  {
    List<String> protos = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(BUser.class))
    {
      if (c.property() == defaultPrototype) continue;
      protos.add(c.property().getName());
    }
    
    return protos.toArray(new String[protos.size()]);
  }

  /**
   * get all children of type BUser or BUserPrototype
   *
   * @return array of prototypes
   */
  public BComponent[] getPrototypes()
  {
    List<BComponent> protos = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next())
    {
      BValue prot = c.get();
      if (! (prot instanceof BUser) && ! (prot instanceof BUserPrototype)) continue;
      protos.add((BComponent)prot);
    }

    return protos.toArray(new BComponent[protos.size()]);
  }
  
////////////////////////////////////////////////////////////////
// Fw
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      return this;
    }
    else
    {
      return null;
    }
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void validateSet(Validatable validatable, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      Property[] props = validatable.getModifiedProperties();
      for (Property prop : props)
      {
        String oldRoles = "";
        BValue existing = validatable.getExistingValue(prop);
        if(existing instanceof BUser || existing instanceof BUserPrototype)
        {
          oldRoles = (existing instanceof BUserPrototype) ?
            ((BUserPrototype)existing).getRoles().getValue().toString() :
            ((BUser)existing).getRoles();
        }

        checkRoleChange(validatable.getProposedValue(prop), oldRoles, context);
      }
    }
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if (isRunning())
    {
      checkRoleChange(value, "", context);
    }
    super.checkAdd(name, value, flags, facets, context);
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void checkRename(Property property, String newName, Context context)
  {
    if (isRunning())
    {
      checkRoleChange(get(property), "", context);
    }
    super.checkRename(property, newName, context);
  }

  /**
   * Convenience method to check for Role discrepancies.
   *
   * @since Niagara 4.4U1
   */
  static void checkRoleChange(BValue value, String oldRoles, Context context)
  {
    if(context != null && context != Context.decoding && context.getUser() != null &&
      (value instanceof BUser || value instanceof BUserPrototype))
    {
      String newRoles = (value instanceof BUserPrototype)?
        ((BUserPrototype)value).getRoles().getValue().toString():
        ((BUser)value).getRoles();

      BUser.checkRoleChange(context.getUser(), oldRoles, newRoles);
    }
  }

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (monitor != null) monitor.fw(x, a, b, c, d);
    return super.fw(x, a, b, c, d);
  }

  @Override
  public void added(Property property, Context context)
  {
    BValue added = get(property);
    if (isRunning() && (added instanceof BUser || added instanceof BUserPrototype))
    {
      subscribePrototype((BComponent)added);
    }
  }

  @Override
  public void started() throws Exception
  {
    //subscribe to child UserPrototypes so that we can update existing users if they are modified
    BComponent[] prototypes = getPrototypes();
    for( int i = 0; i < prototypes.length; i++){
      subscribePrototype(prototypes[i]);
    }

    super.started();
  }

  private void subscribePrototype(BComponent prototype){
    subscriber.subscribe(prototype);
    SlotCursor<Property> properties = prototype.getProperties();
    while(properties.next()){
      BObject bObject = properties.get();
      if( bObject instanceof BComponent){
        subscriber.subscribe((BComponent)bObject);
      }
    }
  }

  private PrototypeSubscriber subscriber = new PrototypeSubscriber();

  private class PrototypeSubscriber extends Subscriber
  {
    public void event(BComponentEvent event)
    {
      if( event.getId() == BComponentEvent.PROPERTY_CHANGED )
      {
        BComponent source = event.getSourceComponent();
        BComponent prototype = null;

        //check if we're dealing with the prototype itself
        if (source instanceof BUser || source instanceof BUserPrototype)
          prototype = source;
        else
        {
          //otherwise, we're dealing with some child component of prototype
          while (null != source && !(source instanceof BUser || source instanceof BUserPrototype))
          {
            source = (BComponent)source.getParent();
          }
          if (source instanceof BUser || source instanceof BUserPrototype)
          {
            prototype = source;
          }
          else
            return;
        }

        String prototypeName = prototype.getName();

        BUserService userService = (BUserService)Sys.getService(BUserService.TYPE);
        //get all the users from the user service
        BUser[] users = userService.getChildren(BUser.class);

        //each user that is based on the given prototype must be removed and
        //added back to the service with the modified prototype for the
        //prototype changes to take affect for that user account.
        for (int i = 0; i < users.length; i++)
        {
          BUser user = users[i];
          BAuthenticationScheme scheme = userService.getAuthenticationSchemeForUser(user);

          if (user.getPrototypeName().equals(prototypeName) && scheme.supportsRemoteUsers())
          {
            if (prototype instanceof BUser)
            {
              BUser userProto = (BUser)prototype.newCopy(true);
              String name = user.getName();
              userProto.setAuthenticator((BAbstractAuthenticator)user.getAuthenticator().newCopy());
              userProto.setAuthenticationSchemeName(scheme.getName());
              userProto.setPrototypeName(prototypeName);
              userService.set(name, userProto);
            }
            else if (prototype instanceof BUserPrototype)
            {
              // just update the readonly flags, values will be updated at next login
              SlotCursor<Property> c = prototype.getProperties();
              while (c.next(BUserPrototypeProperty.class))
              {
                BUserPrototypeProperty prop = (BUserPrototypeProperty)c.get();
                Slot userSlot = user.getSlot(prop.getName());
                if (userSlot != null)
                {
                  if (prop.getOverridable())
                  {
                    user.setFlags(userSlot, user.getFlags(userSlot) &~ Flags.READONLY);
                  }
                  else
                  {
                    user.setFlags(userSlot, user.getFlags(userSlot) | Flags.READONLY);
                  }
                }
              }
            }
          }
        }
      }

    }//END EVENT FUNCTION

  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private UserMonitor monitor = new UserMonitor(this);
  private static final BIcon icon = BIcon.std("profile.png");
}
