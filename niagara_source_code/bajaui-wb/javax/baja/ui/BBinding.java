/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.logging.Logger;

import javax.baja.agent.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.enums.*;

import com.tridium.sys.schema.*;
import com.tridium.ui.*;

/**
 * BBinding is used to bind a BWidget to a data source.  BBindings
 * are bound to one BObject using an ord.  Subclasses are registered 
 * as agents on the type of BObjects to which they can be bound to.
 *
 * @author    Brian Frank       
 * @creation  10 May 04
 * @version   $Revision: 10$ $Date: 1/24/08 9:36:03 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Specifies the ord of the binding target.
 */
@NiagaraProperty(
  name = "ord",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 Specifies the behavior for when the binding
 ord cannot be resolved or used due to security
 permissions.
 */
@NiagaraProperty(
  name = "degradeBehavior",
  type = "BDegradeBehavior",
  defaultValue = "BDegradeBehavior.none"
)
/*
 Fired whenever the binding target changed.
 */
@NiagaraTopic(
  name = "targetChanged"
)
public abstract class BBinding
  extends BComponent
  implements BIAgent
{                             

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BBinding(3433723524)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ord"

  /**
   * Slot for the {@code ord} property.
   * Specifies the ord of the binding target.
   * @see #getOrd
   * @see #setOrd
   */
  public static final Property ord = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code ord} property.
   * Specifies the ord of the binding target.
   * @see #ord
   */
  public BOrd getOrd() { return (BOrd)get(ord); }

  /**
   * Set the {@code ord} property.
   * Specifies the ord of the binding target.
   * @see #ord
   */
  public void setOrd(BOrd v) { set(ord, v, null); }

  //endregion Property "ord"

  //region Property "degradeBehavior"

  /**
   * Slot for the {@code degradeBehavior} property.
   * Specifies the behavior for when the binding
   * ord cannot be resolved or used due to security
   * permissions.
   * @see #getDegradeBehavior
   * @see #setDegradeBehavior
   */
  public static final Property degradeBehavior = newProperty(0, BDegradeBehavior.none, null);

  /**
   * Get the {@code degradeBehavior} property.
   * Specifies the behavior for when the binding
   * ord cannot be resolved or used due to security
   * permissions.
   * @see #degradeBehavior
   */
  public BDegradeBehavior getDegradeBehavior() { return (BDegradeBehavior)get(degradeBehavior); }

  /**
   * Set the {@code degradeBehavior} property.
   * Specifies the behavior for when the binding
   * ord cannot be resolved or used due to security
   * permissions.
   * @see #degradeBehavior
   */
  public void setDegradeBehavior(BDegradeBehavior v) { set(degradeBehavior, v, null); }

  //endregion Property "degradeBehavior"

  //region Topic "targetChanged"

  /**
   * Slot for the {@code targetChanged} topic.
   * Fired whenever the binding target changed.
   * @see #fireTargetChanged
   */
  public static final Topic targetChanged = newTopic(0, null);

  /**
   * Fire an event for the {@code targetChanged} topic.
   * Fired whenever the binding target changed.
   * @see #targetChanged
   */
  public void fireTargetChanged(BValue event) { fire(targetChanged, event, null); }

  //endregion Topic "targetChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the parent widget or null if not 
   * mounted as a child under a widget.
   */
  public final BWidget getWidget()
  {            
    return (BWidget)getParent();
  }                      

  /**
   * Get the ancestor widget shell or null if not 
   * mounted as a child under a widget shell.
   */
  public final BWidgetShell getShell()
  {
    BWidget widget = (BWidget)getParent();
    if (widget == null) return null;
    return widget.getShell();
  }
  
  /**
   * Return if this binding is currently bound to its ord target.
   */
  public final boolean isBound()
  {                                            
    return target != null;
  }                
  
  /**
   * Return the ord target.
   */
  public final OrdTarget getTarget()
  {             
    if (target == null) throw new UnboundException(getOrd().toString());        
    return target;
  }

  /**
   * Convenience for <code>getTarget().get()</code>.
   */
  public final BObject get()
  {                     
    return getTarget().get();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * Binding started callback.  Subclasses should call super.
   */
  public void started()
  {                    
    bind();
  }

  /**
   * Binding stopped callback.  Subclasses should call super.
   */
  public void stopped()
  {        
    unbind();
  }            
  
  /**
   * Callback when binding target has been modified.
   */
  public void targetChanged()
  {                        
    applyDegradeBehavior();     
    getWidget().relayout();
  }                  
  
  /**
   * Return if the binding is unbound or unusable for any reason (such
   * as lack permissions).  This method is called by applyDegradeBehavior() 
   * to decide if the configured degrade behavior should be applied 
   * or unapplied.  The default implementation returns !isBound().
   */
  public boolean isDegraded()
  {                       
    return !isBound();
  }      
  
  /**
   * Apply (or unapply) the configured degrade behavior
   * based on the current value of isDegraded().  This 
   * method is automatically called by targetChanged().
   */
  public void applyDegradeBehavior()
  {                               
    switch(getDegradeBehavior().getOrdinal())
    {
      case BDegradeBehavior.NONE:
        break;
      case BDegradeBehavior.DISABLE:
        getWidget().setEnabled(!isDegraded());
        break;
      case BDegradeBehavior.HIDE:
        getWidget().setVisible(!isDegraded());
        break;
    }
  }
  
  /**
   * Callback when containing WbView is being saved.
   */
  public void save(Context cx)
    throws Exception
  {
  }  
  
////////////////////////////////////////////////////////////////
// Slot Delegation
////////////////////////////////////////////////////////////////
  
  /**
   * This method allows a binding to override a get operation 
   * on the parent widget by returning a non-null value. 
   * Return null to use the parent's actual property value.
   */
  public BValue getOnWidget(Property prop) 
  { 
    return null;
  }
  
  /**
   * Callback for when the parent widgets's 
   * <code>changed</code> callback  is invoked.
   */
  public void changedOnWidget(Property property, Context context) 
  {
  }

  /**
   * Callback for when the specified action is invoked on 
   * the parent widget.          
   *
   * @return true to "consume" the invocation and prevent further
   *   routing to other bindings.
   */
  public boolean invokedOnWidget(Action action, BValue value, Context context) 
  {
    return false;
  }

  /**
   * Callback for when the specified topic is  fired on the 
   * parent widget.
   *
   * @return true to "consume" the event and prevent further
   *   routing to other bindings.
   */
  public boolean firedOnWidget(Topic topic, BValue event, Context context)
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////
  
  /**
   * Only BWidget is a valid parent.
   */
  public boolean isParentLegal(BComponent c)
  {                                 
    return c instanceof BWidget;
  }                
  
  /**
   * Return string representation.
   */
  public String toString(Context cx)
  {  
    String widget = "???";
    try
    {                                                  
      widget = getWidget().getType().getTypeName();
    }
    catch(Exception e)
    {
    }
    return "Binding " + getOrd() + " -> " + widget;
  }

////////////////////////////////////////////////////////////////
// Framework Implementation
////////////////////////////////////////////////////////////////

  /**
   * Bind to the target asynchronously. 
   */
  void bind()
  {
    Binder binder = getBinder();
    if (binder != null) binder.bind(this);
  }

  /**
   * Unbind to the target. 
   */
  void unbind()
  {
    Binder binder = getBinder();
    if (binder != null) binder.unbind(this);
  }

  /**
   * Rebind to the target asynchronously. 
   */
  void rebind()
  {
    unbind();
    bind();
  }  
  
  /**
   * Get the Binder by walking the parent tree, or
   * return null if not mounted under any component
   * which contains a Binder.
   */
  Binder getBinder()
  {
    BComplex p = getParent();
    while(p != null)
    {   
      Binder binder = (Binder)p.fw(Fw.GET_BINDER, null, null, null, null);        
      if (binder != null) return binder;
      p = p.getParent();
    }
    return null;
  }

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {   
      case Fw.CHANGED: 
        Property prop = (Property)a;
        if (prop == ord) rebind();
        break;
      case Fw.UPDATE_BINDING: 
        this.target = (OrdTarget)a;
        break;
    }   
    return super.fw(x, a, b, c, d);
  }

  public static final Logger LOGGER = Logger.getLogger("niagara.binding");

  private OrdTarget target;
}
