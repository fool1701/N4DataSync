/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench;

import java.util.ArrayList;

import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPassword;
import javax.baja.space.BSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BIValidator;
import javax.baja.util.BTypeSpec;
import javax.baja.util.CannotValidateException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.view.BWbComponentView;

import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BWbEditor is a BWbPlugin used to view and/or edit a BObject
 * via the <code>loadValue()</code> and <code>saveValue</code> 
 * methods.
 * <p>
 * All BWbEditors should support the <code>doLoadValue()</code> 
 * method.  Editors which are readonly should never fire the 
 * pluginModified event and can use the default implementation of 
 * {@code doSaveValue()}.  Editors which can both view and edit
 * should fire the pluginModified event when the user makes a change,
 * and must provide an implementation of <code>doSaveValue()</code>.
 * <p>
 * BComplex editors should support an edit-by-reference model which
 * saves their state directly back to the same instance which was
 * passed to the loadValue() method.  Since BSimple's are immutable, 
 * BSimple editors must use a edit-by-value model which loads 
 * one value, then saves into a new instance which is then 
 * returned from the saveValue() method.
 *
 * @author    Brian Frank       
 * @creation  7 Jan 01
 * @version   $Revision: 23$ $Date: 6/28/11 1:23:39 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWbEditor
  extends BWbPlugin
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.BWbEditor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Read Only
////////////////////////////////////////////////////////////////  

  /**
   * Is this editor configured to be read only.
   */
  public final boolean isReadonly()
  {
    return readonly;
  }
  
  /**
   * Set the editor to be read only.  A read only
   * editor should not ever call setModified() or
   * require a save.
   */
  public final void setReadonly(boolean readonly)
  {
    if (this.readonly == readonly) return;
    this.readonly = readonly;
    doSetReadonly(readonly);
    
    if (readonly)
    {
      StyleUtils.addStyleClass(this, "readonly");
    }
    else
    {
      StyleUtils.removeStyleClass(this, "readonly");
    }
  }
  
  /**
   * Override point for setting the editor's readonly
   * mode.  An editor should provide visual cues to the 
   * user indicating if it is editable or readonly.
   * BTextFields inside the editor should map readonly
   * to the editable property.
   */
  protected void doSetReadonly(boolean readonly)
  {
  }

////////////////////////////////////////////////////////////////
// Load / Save
////////////////////////////////////////////////////////////////

  /**
   * Get the value which was last loaded.  This is
   * not necessarily the value which is reflected by
   * the current state of the editor until saveValue()
   * is called.
   * @see #saveValue
   * @see #loadValue
   */
  public final BObject getCurrentValue() 
  { 
    return value; 
  }
  
  /**
   * Get the context that was passed to the
   * last call to loadValue().
   */
  public final Context getCurrentContext() 
  { 
    return context; 
  }
  
  /**
   * Get the Host of the current value.  The current 
   * value is the value that was last loaded or saved.
   */
  public BHost getCurrentValueHost()
  {
    // first try my current value
    BObject cur = getCurrentValue();
    BHost host = BOrd.toHost(cur);
    if (host != null) return host;

    // otherwise try the target of the current shell
    BWbShell shell = getWbShell();
    if (shell != null)
    {
      OrdTarget target = shell.getActiveOrdTarget();
      if (target != null)
      {
        cur = target.get();
        host = BOrd.toHost(cur);
        if (host != null) return host;
      }
    }
   
    return null;
  }
 
  /**
   * Get the Session of the current value.  The current 
   * value is the value that was last loaded or saved.
   */
  public BISession getCurrentValueSession()
  {
    // first try my current value
    BObject cur = getCurrentValue();
    if (cur == null) return null;

    BISession session = BOrd.toSession(cur);
    if (session != null) return session;
   
    // otherwise try the target of the current shell
    BWbShell shell = getWbShell();
    if (shell != null)
    {
       OrdTarget target = shell.getActiveOrdTarget();
       if (target != null)
       {
         cur = target.get();
         session = BOrd.toSession(cur);
         if (session != null) return session;
       }
    }
   
    return null;
  }
 
  /**
   * Get the Space of the current value.  The current 
   * value is the value that was last loaded or saved.
   */
  public BSpace getCurrentValueSpace()
  {
    // first try my current value
    BObject cur = getCurrentValue();
    BSpace space = BOrd.toSpace(cur);
    if (space != null) return space;
   
    // otherwise try the target of the current shell
    BWbShell shell = getWbShell();
    if (shell != null)
    { 
      OrdTarget target = shell.getActiveOrdTarget();
      if (target != null)
      {
        cur = target.get();
        space = BOrd.toSpace(cur);

        if (space != null) return space;
      }
    }
    else System.out.println("drat!");
    return null;
  }

  /**
   * If there was an error during load then return the 
   * exception, otherwise return null.
   */
  public Throwable getLoadError()
  {
    return loadError;
  }
  
  /**
   * Load the editor with a new value.  If the value is a BComplex
   * then edit-by-reference semantics apply and  the value instance 
   * passed to this method will be the same one used to apply the 
   * changes when saveValue() is called.  The implementation of this 
   * method is to:
   * <ol>
   *   <li>lock the modified state</li>
   *   <li>save away the value for access via getValue()</li>
   *   <li>route to doLoadValue()</li>
   *   <li>if the value is a BComponent then automatically
   *       create a link to receive component events</li>
   *   <li>unlock the modified state</li>
   * </ol>
   * If the load fails, this method does not raise the offending
   * exception, but rather displays a load error message.  If you
   * wish to know if the load failed, then check getLoadError().
   * @see #saveValue
   */
  public final void loadValue(BObject value, Context context)
  {
    synchronized(monitor)
    {
      loadError = null;
      
      // keep our old value in local variable
      BObject oldValue = this.value;
      
      // lock the modified state so that programatic changes 
      // do not result in a user modification event
      lockModifiedState();
      
      // save away the value and context and call subclass hook
      this.value = value;
      this.context = context;
      try
      {                 
        // hook to auto register for component events
        if (value instanceof BComponent && this instanceof BWbComponentView)
        {                                   
          BWbComponentView view = (BWbComponentView)this;
          if (view.isAutoRegisterForComponentEvents())
            view.registerForComponentEvents((BComponent)value);
        }
        
        // hook to find any registered validators
        loadValidators();
        
        // subclass hook
        doLoadValue(this.value, context);

        if (this.value instanceof BPassword && ((BPassword)this.value).isDefault())
        {
          this.value = BPassword.make("", context);
        }
      }
      catch(Throwable e)
      {
        loadError = e;
        e.printStackTrace();
        String msg = UiLexicon.bajaui().getText("plugin.load.error");
        if (e instanceof LocalizableException) msg += "\n" + ((LocalizableException)e).toString(null);
        if (e instanceof LocalizableRuntimeException) msg += "\n" + ((LocalizableRuntimeException)e).toString(null);
        setContent(new BLabel(msg));
      }
      
      // unlock the modified state  
      unlockModifiedState();
      
      // no longer modified
      modified = false;
    }
  }

  /**
   * Convenience for loadValue(value, null) with no context.
   */
  public final void loadValue(BObject value)
  {
    loadValue(value, null);
  }
  
  /**
   * This the override method used to populate the 
   * editor's state based on the specified value.
   */
  protected void doLoadValue(BObject value, Context context)
    throws Exception
  {
  }

  /**
   * Save the value based on the current state of the editor. 
   * In edit-by-reference editors (BComplex) the editor saves 
   * it's state back to the value instance passed.   In edit-by-value 
   * editors (BSimple's) then a new instance of the value is created 
   * and returned.
   *
   * @param cx If the object being saved is a BSimple then this
   *   value may be ignored, otherwise it should be passed to
   *   the set, add, remove, rename, or reorder methods as the
   *   context.
   * @return The instance passed to loadValue() if the editor has
   *   not been modified or if this is an edit-by-reference editor.  
   *   The new value in a edit-by-value editor.
   * @throws CannotSaveException if the current state of the
   *   editor indicates an improper set of data which may not
   *   be used to reconstruct a valid value.
   * @see #loadValue
   * @see javax.baja.util.BIValidator
   */
  public final BObject saveValue(BObject value, Context cx)
    throws CannotSaveException, Exception
  {
    synchronized(monitor)
    {
      // if not modified return original
      if (!isModified()) return value;
      
      // subclass hook
      BObject result = null;
      try
      {
        result = doSaveValue(value, cx);
        doValidation(result, cx);
      }
      catch (CannotValidateException e)
      {
        throw new CannotSaveException(e.getMessage(), e);
      }
      
      // Make sure we don't break our edit-by-ref semantics.
      // Note that if the two types are different, we skip the
      // check.  this is a rare case, but it does happen,
      // e.g. kitPx:ActionBinding.actionArg
      if (value != null && 
        !value.isSimple() && 
        value.getType() == result.getType() && 
        value != result)
        throw new IllegalStateException("Complex editors must support edit-by-ref semantics");
  
      // not modified anymore        
      modified = false;
  
      // return the final result
      return this.value = result;
    }
  }
  
  /**
   * Convenience for saveValue(getCurrentValue(), cx).
   */
  public final BObject saveValue(Context cx)
    throws CannotSaveException, Exception
  {
    return saveValue(value, cx);
  }

  /**
   * Convenience for saveValue(getCurrentValue(), null) with no context.
   */
  public final BObject saveValue() 
    throws CannotSaveException, Exception
  {
    return saveValue(value, null);
  }

  /**
   * This is the override method for saving the editor's
   * state back into an BObject form.  If the editor is
   * a edit-by-reference editor (BComplex) then it should
   * save its state back to the specified value instance and 
   * return that instance.  If the editor is edit-by-value 
   * (BSimple) then it should create a new instance and 
   * return it.
   * <p>
   * The default implementation assumes an editor which
   * is view only and does nothing but return the value.
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws CannotSaveException, Exception
  {
    return value;
  }
  
  /**
   * Check the loadValue context for BIValidator definitions and 
   * resolve them.  This only happens if this editor is a BWbFieldEditor.
   *  
   * @since Niagara 3.5
   */
  private void loadValidators()
  {
    ArrayList<BIValidator> arr = new ArrayList<>();
    if (context != null && (this instanceof BWbFieldEditor))
    {
      String[] keys = context.getFacets().list();
      for (int i=0; i<keys.length; ++i)
      {
        if (keys[i].startsWith(BIValidator.VALIDATOR_FACET))
        {
          final String spec = context.getFacets().gets(keys[i], "");
          BObject candidate = null;
          try
          {
            candidate = BTypeSpec.make(spec).getInstance();
          }
          catch (Exception e)
          {
            throw new LocalizableRuntimeException(TYPE.getModule().getModuleName(),
              "wbEditor.badValidatorSpec", new Object[] {keys[i], spec}, e);
          }
          
          if (!candidate.getType().is(BIValidator.TYPE))
          {
            throw new LocalizableRuntimeException(TYPE.getModule().getModuleName(),
              "wbEditor.badValidatorType", new Object[] {keys[i], spec});
          }
          arr.add((BIValidator)candidate);
        }
      }
    }
    validators = arr.toArray(new BIValidator[arr.size()]);
  }
  
  /**
   * Hook to do validation. If any fails to validate, an Exception is thrown.
   *
   * @since Niagara 3.5
   */
  private void doValidation(BObject value, Context saveCx) throws Exception
  {
    // merge the load context with save context - giving preference to saveCx
    BFacets vFacets = (context == null) ? BFacets.NULL : context.getFacets();
    if (saveCx != null) 
      vFacets = BFacets.make(vFacets, saveCx.getFacets());
    Context vContext = new BasicContext(saveCx, vFacets);

    for (int i=0; i<validators.length; ++i)
      validators[i].validate(value, vContext);
    
    if ((this instanceof BWbFieldEditor) && (this instanceof BIValidator))
      ((BIValidator)this).validate(value, vContext);
    
    // This is kind of weird since the value will be validating itself, but
    // we will tacitly allow it for now.
    if (value instanceof BIValidator)
      ((BIValidator)value).validate(value, vContext);
  }
            
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private BObject value;
  private Context context;
  private BIValidator[] validators = new BIValidator[0];
  private boolean readonly;
  private Throwable loadError;
  
}
