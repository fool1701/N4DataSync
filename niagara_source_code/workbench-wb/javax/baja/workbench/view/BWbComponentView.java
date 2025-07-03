/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.view;

import java.util.ArrayList;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.Binder;

/**
 * BWbComponentView is a BWbView designed to view/edit BComponents.  
 * It provides built-in support to automatically register for component 
 * events and handle the callbacks.
 *
 * @author    Brian Frank       
 * @creation  7 Jan 01
 * @version   $Revision: 12$ $Date: 10/1/09 3:02:25 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWbComponentView
  extends BWbView
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.view.BWbComponentView(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbComponentView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Registration
////////////////////////////////////////////////////////////////

  /** 
   * Get the autoRegisterForComponentEvents flag.
   */
  public final boolean isAutoRegisterForComponentEvents()
  {
    return autoRegisterForComponentEvents;
  }

  /**
   * Is this editor registered to receive component 
   * events from the specified source.
   */
  public final boolean isRegisteredForComponentEvents(BComponent source)
  {         
    Binder binder = getBinder();
    return binder != null && binder.isSubscribed(source);
  }

  /**
   * Register for component events on the specified 
   * source by creating a link from its componentEvent
   * topic to my own handleComponentEvent action. 
   * The link created is an indirect link which requires
   * the source to be mounted.  Using an indirect link
   * ensures that it gets removed automatically if the
   * source becomes unmounted.  If the source component is
   * not mounted, then this call is silently ignored.
   */
  public final void registerForComponentEvents(BComponent source)
  {
    Binder binder = getBinder();                                        
    if (binder != null)
    {
      binder.subscribe(source);
    }
  }

  /**
   * This is a recursive method of registerForComponent events, which
   * registers the source, plus children of the source.  If depth is
   * zero then only the source is registered.  If depth is 1 then 
   * the source's children are registered too.  If depth is 2 then
   * the source's children and grandchildren.
   */
  public final void registerForComponentEvents(BComponent source, int depth)
  {
    Binder binder = getBinder();                                        
    if (binder != null)
    {
      binder.subscribe(source, depth);
    }
  }

  /**
   * This is method is a batch version of registerForComponent events.
   * It provides higher performance when many different components
   * need to be subscribed.
   */
  public final void registerForComponentEvents(BComponent[] sources, int depth)
  {
    Binder binder = getBinder();                                        
    if (binder != null)
    {
      binder.subscribe(sources, depth, null);
    }
  }

  /**
   * This is method is a batch version of registerForComponent events
   * using a list of ords.  The resolved list of BComponents is returned.
   */
  public final BComponent[] registerForComponentEvents(BComponentSpace space, BOrd[] sources, int depth)
  {
    Binder binder = getBinder();
    return binder != null ? binder.subscribe(space, sources, depth, null) : null;
  }

  /**
   * Register for component events on the specified 
   * target by creating a link from its componentEvent
   * topic to my own handleComponentEvent action. 
   */
  public final void unregisterForComponentEvents(BComponent source)
  {
    Binder binder = getBinder();                                        
    if (binder != null)
    {
      binder.unsubscribe(source);
    }
  }
  
  /**
   * Unregister all component events this editor is 
   * currently registered on.
   */
  public final void unregisterForAllComponentEvents()
  {
    Binder binder = getBinder();                                        
    if (binder != null)
    {
      binder.unsubscribeAll();
    }
  }
  
  /**
   * This is the built in callback for handling a 
   * component event from the binder.  It routes 
   * to handleComponentEvent and each binding.
   */
  @Override
  final void route(BComponentEvent event)
  {
    handleComponentEvent(event);
    Attachable[] attached = getAttached();
    for (Attachable attachable : attached)
    {
      try
      {
        attachable.handleComponentEvent(event);
      }
      catch (Throwable e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * This is the built in callback for handling a 
   * component event, usually from a proxy component
   * subscribed to by registerForComponentEvents().  Note
   * that if this view is composited into a px page
   * you may receive events for components not explicitly 
   * registered by yourself.  As a general rule check 
   * the event source component before processing.
   */
  public void handleComponentEvent(BComponentEvent event)
  {
  }

////////////////////////////////////////////////////////////////
// Binding
////////////////////////////////////////////////////////////////  

  /**
   * The Attachable interface is used to bind controls to a 
   * editor to share in its component subscription and eventing.  
   * Objects which use the Attachable interface are ensured to 
   * cleanup correctly when the editor is deactivated.
   */
  public interface Attachable
  {
    /** Callback during BWbComponentView.attach() */
    void attached(BWbComponentView editor);
    
    /** Callback during BWbComponentView.detach() */
    void detached(BWbComponentView editor);
    
    /** Called when component events are routed to the editor */
    void handleComponentEvent(BComponentEvent event);
  }
  
  /**
   * Get the list of current Attachables bound to 
   * this view using the attach() method.
   */
  public Attachable[] getAttached()
  {
    synchronized (attached)
    {
      return attached.toArray(new Attachable[attached.size()]);
    }
  }
  
  /**
   * Bind the specified object to receive my component event callbacks.
   */
  public void attach(Attachable a)
  {
    synchronized (attached)
    {
      attached.add(a);
      a.attached(this);
    }
  }
  
  /**
   * Unbind the specified object from my component event callbacks.
   */
  public void unattach(Attachable a)
  {
    synchronized (attached)
    {
      for(int i=0; i<attached.size(); i++)
      {
        if (attached.get(i) == a)
        {
          attached.remove(i);
          a.detached(this);
          return;
        }
      }
    }
  }
  
////////////////////////////////////////////////////////////////
//Framework Access
////////////////////////////////////////////////////////////////

 /**
  * Framework support access; this method should
  * never be used by developers.
  */
 @Override
 public Object fw(int x, Object a, Object b, Object c, Object d)
 {
   if (x == Fw.DEACTIVATED)
   {
     synchronized (attached)
     {
       for (Attachable attachable : attached)
       {
         try
         {
           attachable.detached(this);
         }
         catch (Exception ignored)
         {
         }
       }
       attached.clear();
     }
   }

   return super.fw(x, a, b, c, d);
 }  
            
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  /**
   * If set to true in the constructor, then a call to load() 
   * with a BComponent automatically registers for component 
   * events.  If the view does it own registration then set false.  
   * The default is true. 
   */
  protected boolean autoRegisterForComponentEvents = true;

  private final ArrayList<Attachable> attached = new ArrayList<>();
}
