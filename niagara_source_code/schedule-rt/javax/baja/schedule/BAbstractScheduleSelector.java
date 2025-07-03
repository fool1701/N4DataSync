/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.schedule;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LinkCheck;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Subscriber;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
/**
 * BAbstractScheduleSelector is a abstract component for the various Schedule Selector components.
 *
 * @author    John Huffman
 * @creation  20 June 2007
 * @version   $Revision: 3$ $Date: 7/31/09 10:44:20 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The ord to the schedule container
 */
@NiagaraProperty(
  name = "container",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT"
)
/*
 The list of available schedules in the container
 */
@NiagaraProperty(
  name = "schedule",
  type = "BDynamicEnum",
  defaultValue = "BDynamicEnum.DEFAULT",
  flags = Flags.OPERATOR
)
@NiagaraAction(
  name = "updateScheduleList"
)
public abstract class BAbstractScheduleSelector
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BAbstractScheduleSelector(3227851670)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "container"

  /**
   * Slot for the {@code container} property.
   * The ord to the schedule container
   * @see #getContainer
   * @see #setContainer
   */
  public static final Property container = newProperty(0, BOrd.DEFAULT, null);

  /**
   * Get the {@code container} property.
   * The ord to the schedule container
   * @see #container
   */
  public BOrd getContainer() { return (BOrd)get(container); }

  /**
   * Set the {@code container} property.
   * The ord to the schedule container
   * @see #container
   */
  public void setContainer(BOrd v) { set(container, v, null); }

  //endregion Property "container"

  //region Property "schedule"

  /**
   * Slot for the {@code schedule} property.
   * The list of available schedules in the container
   * @see #getSchedule
   * @see #setSchedule
   */
  public static final Property schedule = newProperty(Flags.OPERATOR, BDynamicEnum.DEFAULT, null);

  /**
   * Get the {@code schedule} property.
   * The list of available schedules in the container
   * @see #schedule
   */
  public BDynamicEnum getSchedule() { return (BDynamicEnum)get(schedule); }

  /**
   * Set the {@code schedule} property.
   * The list of available schedules in the container
   * @see #schedule
   */
  public void setSchedule(BDynamicEnum v) { set(schedule, v, null); }

  //endregion Property "schedule"

  //region Action "updateScheduleList"

  /**
   * Slot for the {@code updateScheduleList} action.
   * @see #updateScheduleList()
   */
  public static final Action updateScheduleList = newAction(0, null);

  /**
   * Invoke the {@code updateScheduleList} action.
   * @see #updateScheduleList
   */
  public void updateScheduleList() { invoke(updateScheduleList, null, null); }

  //endregion Action "updateScheduleList"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractScheduleSelector.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAbstractScheduleSelector()
  {
  }

  /**
   * Init if started after steady state has been reached.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    subscribeToContainer();
  }

  /**
   * cleanup any resources we created.
   */
  @Override
  public void stopped()
    throws Exception
  {
    super.stopped();
    unsubscribeFromContainer();
  }

  /**
   * Ensure that we only add links whose sourceSlot have the
   * same EnumRange facets as the schedule slot
   */
  @Override
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    if (targetSlot == schedule)
    {
      source.lease();
      BFacets sourceFacets = source.getSlotFacets(sourceSlot);

      // Check that the two slots have the same range
      if ( !getSchedule().getRange().equals(sourceFacets.get(BFacets.RANGE)) )
        return LinkCheck.makeInvalid(lexicon.get(LINK_ERROR_BAD_RANGE));
    }

    return LinkCheck.makeValid();
  }

  /**
   * handle any properties that have changed
   */
  @Override
  public void changed(Property p, Context cx)
  {
  	super.changed( p, cx );

    if( !Sys.atSteadyState() )
      return;

    if ( ( cx == noWriteContext ) || ( cx == Context.decoding ) || ( !isRunning() ) )
      return;

    if ( p.equals( schedule ) )
    {
      // create the link to the selected schedule
      linkSchedule( this, schedule, getSchedule().getDisplayTag( cx ), getSchedule().getOrdinal() );
    }

    if ( p.equals( container ) )
    {
      // remove any links we have in place
      removeCurrentLink();

      // subscribe to the schedule container
      subscribeToContainer();

      // fill in the list of schedules from the container
      fillInScheduleEnums( this, schedule );
    }
  }

  /**
   * If a link is removed, reset the schedule list
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    if (oldValue instanceof BLink)
    {
      BLink link = (BLink)oldValue;
      if (link.getTargetSlotName().equals(schedule.getName()))
        doUpdateScheduleList();
    }
  }

  /**
   * Subscribe to the container component so we can update the list of available
   * schedules and the facets if anything changes in the container.
   */
  private void subscribeToContainer()
  {
    BComponent scheduleContainer = null;
    BComponent[] kids = null;
    Class<?> scheduleClass = null;

    try
    {
      // make sure we unsubscribe first
      unsubscribeFromContainer();

      // get the schedule container component
      if ( ( scheduleContainer = getScheduleContainer() ) != null )
      {
        // subscribe to the container
        subscriber.subscribe( scheduleContainer );

        // get the appropriate class to subscribe to
        if ( ( scheduleClass = getScheduleClass( this ) ) != null )
        {
          // get all the appropriate schedules in the container
          if ( ( kids = (BComponent[]) scheduleContainer.getChildren( scheduleClass ) ) != null )
          {
            // subscribe to all the kids in the container
            for ( int i = 0; i < kids.length; ++i )
            {
              subscriber.subscribe( kids[i] );
            }
          }
        }
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    finally
    {
      // release the references
      scheduleContainer = null;
      kids = null;
      scheduleClass = null;
    }
  }

  /**
   * Unsubscribe from the container component
   */
  private void unsubscribeFromContainer()
  {
    try
    {
      subscriber.unsubscribeAll();
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


  /**
   * Match the type of schedule with the type of schedule selector
   */
  private Class<?> getScheduleClass( BComponent scheduleSelector )
  {
    try
    {
      if ( scheduleSelector instanceof BBooleanScheduleSelector )
      {
        return( BBooleanSchedule.class );
      }

      if ( scheduleSelector instanceof BNumericScheduleSelector )
      {
        return( BNumericSchedule.class );
      }

      if ( scheduleSelector instanceof BStringScheduleSelector )
      {
        return( BStringSchedule.class );
      }

      if ( scheduleSelector instanceof BEnumScheduleSelector )
      {
        return( BEnumSchedule.class );
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    return( null );
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Update the list of schedules from the container
   */
  public void doUpdateScheduleList()
  {
    try
    {
      fillInScheduleEnums( this, schedule );
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }
  }


  /*
   * get the selected schedule as a component
   */
  private BComponent getCurrentScheduleComponent( BComponent scheduleSelector )
  {
    BComponent sourceComponent = null;
    BLink link = null;

    try
    {
      // see if we are linked to a schedule in the container
      if ( ( link = (BLink) get( SCHEDULE_CONTAINER_LINK ) ) != null )
      {
        sourceComponent = link.getSourceComponent();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      // release the references
      link = null;
    }

    return sourceComponent;
  }


  /*
   * get the selected schedule as a string
   */
  private String getCurrentSchedule( BComponent scheduleSelector )
  {
    String currentSchedule = SlotPath.escape( lexicon.get( "selector.nullString" ) );  // default to NULL
    BComponent sourceComponent = null;

    try
    {
      // get the selected schedule as a component
      if ( ( sourceComponent = getCurrentScheduleComponent( scheduleSelector ) ) != null )
      {
        currentSchedule = sourceComponent.getName();
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      // release the references
      sourceComponent = null;
    }

    return currentSchedule;
  }

  /*
   * get the schedule container component
   */
  private BComponent getScheduleContainer()
  {
    BComponent scheduleContainer = null;
    BOrd containerOrd = null;

    try
    {
      // get the Schedule Container BOrd
      containerOrd = getContainer();
      if ( ( containerOrd != null ) && ( !containerOrd.isNull() ) )
      {
        try
        {
          // resolve the Schedule Container BOrd to a component
          scheduleContainer = (BComponent) containerOrd.resolve().get();
        }
        catch(Exception e){}
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      // release the references
      containerOrd = null;
    }

    return scheduleContainer;
  }

  /*
   * fill in the list of schedules to present to the user
   */
  private void fillInScheduleEnums( BComponent scheduleSelector, Property field )
  {
    String currentSchedule = null;
    BComponent scheduleContainer = null;
    BComponent[] kids = null;
    Class<?> scheduleClass = null;

    try
    {
      // get the current selected schedule
      currentSchedule = getCurrentSchedule( scheduleSelector );

      // get the schedule container component
      if ( ( scheduleContainer = getScheduleContainer() ) != null )
      {
        // get the appropriate class to subscribe to
        if ( ( scheduleClass = getScheduleClass( this ) ) != null )
        {
          // get all the appropriate schedules in the container
          kids = (BComponent[]) scheduleContainer.getChildren( scheduleClass );
        }
      }

      fillInScheduleEnums( scheduleSelector, field, kids, currentSchedule );
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    finally
    {
      // release the references
      currentSchedule = null;
      scheduleContainer = null;
      kids = null;
      scheduleClass = null;
    }
  }

  /*
   * fill in the list of schedules to present to the user
   * NOTE: always add the "null" string first so we can determine later if it was selected (instead of
   * comparing against a string value which may change)
   */
  private void fillInScheduleEnums( BComponent scheduleSelector, Property field, BComponent[] kids, String currentSchedule )
  {
    String[] scheduleList = null;
    String tag = null;
    int index = 0;

    try
    {
      if ( kids != null )
      {
        try
        {
          // create the list of schedules
          scheduleList = new String[kids.length + 1];    // add 1 for the NULL_STRING

          // add the NULL_STRING
          scheduleList[index++] = SlotPath.escape( lexicon.get( "selector.nullString" ) );

          // add all the kids
          for ( int i = 0; i < kids.length; ++i )
          {
            scheduleList[index++] = kids[i].getName();
          }
        }

        catch(Exception e)
        {
          e.printStackTrace();
        }
      }

      // we still need to add NULL to the list even though we didn't
      // find any schedules in the container.
      if ( scheduleList == null )
      {
        scheduleList = new String[1];

        // add the NULL_STRING
        scheduleList[0] = SlotPath.escape( lexicon.get( "selector.nullString" ) );
      }

      // set the selected enum value
      scheduleSelector.set( field, BDynamicEnum.make( 0, BEnumRange.make( scheduleList ) ), noWriteContext );
      if ( currentSchedule != null )
      {
        scheduleSelector.set( field, BDynamicEnum.make( ( (BDynamicEnum) scheduleSelector.get( field ) ).getRange().tagToOrdinal( currentSchedule ), ( (BDynamicEnum) scheduleSelector.get( field ) ).getRange() ), noWriteContext );
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    finally
    {
      // release the references
      scheduleList = null;
      tag = null;
    }
  }

  /*
   * manage the link to the container schedule
   */
  private void linkSchedule( BComponent scheduleSelector, Property field, String selectedSchedule, int ordinal )
  {
    try
    {
      // remove the current link
      removeCurrentLink();

      // no link required if we are using the NULL_STRING
      if ( ordinal != 0 )  // the NULL_STRING is ALWAYS the first item in the list
      {
        // create the link to the selected schedule
        linkToContainerSchedule( scheduleSelector, field, selectedSchedule );
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /*
   * remove and deactivate the link to the container schedule
   */
  private void removeCurrentLink()
  {
    BLink link = null;

    try
    {
      if ( ( link = (BLink) get( SCHEDULE_CONTAINER_LINK ) ) != null )
      {
        link.deactivate();
        remove( SCHEDULE_CONTAINER_LINK );
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    finally
    {
      // release the references
      link = null;
    }
  }

  /*
   * create a link to the selected container schedule
   */
  private void linkToContainerSchedule( BComponent scheduleSelector, Property field, String selectedSchedule )
  {
    BOrd containerOrd = null;
    BComponent scheduleContainer = null;
    BControlSchedule scheduleComponent = null;
    BOrd sourceOrd = null;
    BOrd linkToScheduleOrd = null;
    BLink link = null;

    try
    {
      // get the Schedule Container BOrd
      containerOrd = getContainer();
      if ( ( containerOrd != null ) && ( !containerOrd.isNull() ) )
      {
        try
        {
          // resolve the Schedule Container BOrd to a component
          scheduleContainer = (BComponent) containerOrd.resolve().get();
        }
        catch(Exception e){}

        // make sure we have a valid reference
        if ( scheduleContainer != null )
        {
          // get the schedule component
          if ( ( scheduleComponent = (BControlSchedule) scheduleContainer.get( SlotPath.escape( selectedSchedule ) ) ) != null )
          {
            // get the ord handle
            sourceOrd = scheduleComponent.getHandleOrd();

            // create the link property
            link = new BLink( sourceOrd, "out", "in", true );
            add( SCHEDULE_CONTAINER_LINK, link );
            link.activate();

            // set the facets to the source components facets
            scheduleSelector.set( scheduleSelector.getProperty( "facets" ), scheduleComponent.getFacets() );
          }
        }
      }
    }

    catch(Exception e)
    {
      e.printStackTrace();
    }

    finally
    {
      // release the references
      containerOrd = null;
      scheduleContainer = null;
      scheduleComponent = null;
      sourceOrd = null;
      linkToScheduleOrd = null;
      link = null;
    }
  }

////////////////////////////////////////////////////////////////
// Subscriber
////////////////////////////////////////////////////////////////

  class ContainerSubscriber extends Subscriber
  {
    @Override
    public void event(BComponentEvent e)
    {
      handleComponentEvent( e );
    }
  }

  void handleComponentEvent(BComponentEvent event)
  {
    // property added
    if ( event.getId() == BComponentEvent.PROPERTY_ADDED )
    {
      // The simplest thing to do is to unsubscribe from everything and re-subscribe to the container
      // and all of the children in the container that apply to this type of schedule selector.  We
      // can optimize this by not unsubscribing/re-subscribing and add the new component to the subscription
      // list if it is of the right type.
      // Subscribe to the schedule container
      subscribeToContainer();

      // fill in the list of schedules from the container
      fillInScheduleEnums( this, schedule );

      return;
    }

    // property removed
    if ( event.getId() == BComponentEvent.PROPERTY_REMOVED )
    {
      // The simplest thing to do is to unsubscribe from everything and re-subscribe to the container
      // and all of the children in the container that apply to this type of schedule selector.  We
      // can optimize this by not unsubscribing/re-subscribing and remove the component from the subscription
      // list if it is of the right type.
      // Subscribe to the schedule container
      subscribeToContainer();

      // fill in the list of schedules from the container
      fillInScheduleEnums( this, schedule );
      return;
    }

    // property renamed
    if ( event.getId() == BComponentEvent.PROPERTY_RENAMED )
    {
      // fill in the list of schedules from the container
      fillInScheduleEnums( this, schedule );

      return;
    }

    // property changed (check for PROPERTY_CHANGED instead of FACETS_CHANGED because
    // FACETS_CHANGED is used when the slot facets are modified NOT when the property
    // facets are modified)
    if ( event.getId() == BComponentEvent.PROPERTY_CHANGED )
    {
      // we only care about the facets changing
      if ( event.getSlotName().equals( "facets" ) == true )
      {
        BComponent sourceComponent = null;

        // we don't need to do anything unless the facets are changed on the schedule component we
        // are currently linked to... get the selected schedule
        if ( ( sourceComponent = getCurrentScheduleComponent( this ) ) != null )
        {
          // see if we are dealing with the same component
          if ( sourceComponent.equals( event.getSourceComponent() ) == true )
          {
            // modify the facets
            set( getProperty( "facets" ), ( (BControlSchedule) event.getSourceComponent() ).getFacets() );
          }

          // release the reference
          sourceComponent = null;
        }
      }

      return;
    }
  }


 	/**
	 * noWriteContext context: Used to prevent the 'Change' callback from
	 * executing when we modify a property programmatically.
	 */
  Context noWriteContext = new BasicContext()
	{
		public boolean equals(Object obj) { return this == obj; }
    public int hashCode()
    {
      return super.hashCode();
    }

		public String toString() { return "schedule:noWriteContext"; }
	};

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("schedule.png");

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////
  private static final String LINK_ERROR_BAD_RANGE = "selector.link.error.badRange";
  static final String SCHEDULE_CONTAINER_LINK = "ScheduleLink";

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

	static Lexicon lexicon = Lexicon.make(BAbstractSchedule.class);
  Subscriber subscriber = new ContainerSubscriber();
}
