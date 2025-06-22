/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.user.BUser;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.PxIncludeManager;

/**
 * BPxInclude is used to embed one PxFile inside another PxFile.
 *
 * @author    Andy Frank
 * @creation  18 Apr 07
 * @version   $Revision: 18$ $Date: 4/21/08 5:12:58 AM EDT$
 * @since     Niagara 3.3
 */
@NiagaraType
/*
 The ord to the PxFile this drawing will display.
 */
@NiagaraProperty(
  name = "ord",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 User defined variable values for ord substitution.
 */
@NiagaraProperty(
  name = "variables",
  type = "BFacets",
  defaultValue = "BFacets.NULL"
)
public class BPxInclude
  extends BWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.px.BPxInclude(2090423249)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ord"

  /**
   * Slot for the {@code ord} property.
   * The ord to the PxFile this drawing will display.
   * @see #getOrd
   * @see #setOrd
   */
  public static final Property ord = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code ord} property.
   * The ord to the PxFile this drawing will display.
   * @see #ord
   */
  public BOrd getOrd() { return (BOrd)get(ord); }

  /**
   * Set the {@code ord} property.
   * The ord to the PxFile this drawing will display.
   * @see #ord
   */
  public void setOrd(BOrd v) { set(ord, v, null); }

  //endregion Property "ord"

  //region Property "variables"

  /**
   * Slot for the {@code variables} property.
   * User defined variable values for ord substitution.
   * @see #getVariables
   * @see #setVariables
   */
  public static final Property variables = newProperty(0, BFacets.NULL, null);

  /**
   * Get the {@code variables} property.
   * User defined variable values for ord substitution.
   * @see #variables
   */
  public BFacets getVariables() { return (BFacets)get(variables); }

  /**
   * Set the {@code variables} property.
   * User defined variable values for ord substitution.
   * @see #variables
   */
  public void setVariables(BFacets v) { set(variables, v, null); }

  //endregion Property "variables"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxInclude.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

///////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Force the PxFile to be reloaded.
   */
  public void reload()
  {
    if (!loaded) return; // don't need to reload more than once

    loaded       = false;
    root         = null;
    props        = noProps;
    origOrds     = noOrds;
    pxFile       = null;
    lastModified = 0;
    if (get("root") != null) remove("root");
  }

  /**
   * Return the root widget loaded from the PxFile.  This
   * may return null if the PxFile has not yet been loaded
   * or if it failed to load.
   */
  public BWidget getRootWidget()
  {
    return root;
  }

  /**
   * Get the baseOrd the PxFile should be resolved against.
   */
  public BOrd getBaseOrd()
  {
    return baseOrd;
  }

  /**
   * Set the baseOrd the PxFile should be resolved against.
   */
  public void setBaseOrd(BOrd baseOrd)
  {
    this.baseOrd = baseOrd;
  }
  
  /**
   * This method blocks the calling thread until the PxInclude 
   * is fully loaded into memory.  If the PxInclude is already
   * loaded into memory then this method has no effect.  
   */
  public void sync()
  {
    if (loaded) return;

    // If the lock is already held by this thread then bail the
    // call to sync.
    if (lock.isHeldByCurrentThread()) return;

    lock.lock();
    try
    {
      load();
      while (!loaded)
      {
        try { loadCondition.await(); } catch(InterruptedException e) {}
      }
    }
    finally
    {
      lock.unlock();
    }
  }
  
  /**
   * Return true if the PxInclude is fully loaded into memory.  If
   * the PxInclude is still being loaded asynchronously on a 
   * background thread then return false.  Use the <code>sync()</code>
   * method if you wish to block until the PxInclude is fully loaded.
   */
  public boolean isLoaded()
  {                 
    return loaded;
  }
  
  /**
   * This method is called just before the Widget for the PxInclude is loaded
   * @param root
   * @param props
   * @param origOrds
   */
  protected void preLoad(BWidget root, PxProperty[] props, BOrd[] origOrds)
  {    
  }

  /**
   * This method is called after the PxFile has been loaded.
   */
  protected void loaded()
  {
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);
    if (prop == ord || prop == variables) reload();
  }

  public void computePreferredSize()
  {
    if (!loaded) 
    {
      load();
    }
    else if (root != null)
    {
      root.computePreferredSize();
      setPreferredSize(root.getPreferredWidth(), root.getPreferredHeight());
    }
  }

  public void doLayout(BWidget[] kids)
  {
    if (!loaded) 
    {
      load();
    }
    else if (root != null)
    {
      root.setBounds(0, 0, getWidth(), getHeight());
    }
  }

////////////////////////////////////////////////////////////////
// Bindings
////////////////////////////////////////////////////////////////

  public void bindingsChanged()
  {
    for (int i=0; i<props.length; i++)
      props[i].apply(root, get(props[i].getName()));
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  /**
   * Load the PxInclude from its source PxFile.
   */
  private void load()
  {
    // make sure we only load once
    if (loaded || loading) return;
    loading = true;
    
    // load from manager    
    PxIncludeManager.load(this);
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/px.png");

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.SET_BASE_ORD:
        setBaseOrd((BOrd)a);
        if (b != null) cx = (Context)b;
        return null;
      
      case Fw.PX_EDITOR:
        sync();
        return new Object[] { origOrds, props };

      case Fw.RELOAD_REQUIRED:

        if(isReloadRequired((Context)a))
        {
          return BBoolean.TRUE;
        }
        else
        {
          return BBoolean.FALSE;
        }
      
      case Fw.PX_INCLUDE:

        lock.lock();
        try
        {
          // if a is null, assume the load failed
          if (a != null)
          {
            root = (BWidget)a;
            props = (PxProperty[])b;
            origOrds = (BOrd[])c;
            BIFile file = (BIFile)d;

            if(file != null)
            {
              this.pxFile = file;
              lastModified = file.getLastModified().getMillis();
            }

            // Enforce security permissions here. We can't do this at the PxIncludeManager level
            // because a Cache could be used that's shared between users.
            boolean add = true;
            if (file != null && cx != null && cx.getUser() != null)
            {
              add = file.getPermissions(cx).has(BPermissions.operatorRead);
            }

            if (add)
            {
              preLoad(root, props, origOrds);
              add("root", root, Flags.TRANSIENT);
              bindingsChanged();
              loaded();
            }
          }
          loading = false;
          loaded = true;
          loadCondition.signalAll();
          return null;
        }
        finally
        {
          lock.unlock();
        }
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Require a reload if the pxFIle has been modified or the new user no longer has permission.
   * Cache a new BasicContext if the user has changed since the last context.
   */
  private boolean isReloadRequired(Context context)
  {
    boolean fileModified = pxFile != null && pxFile.getLastModified().getMillis() != lastModified;
    boolean userDegraded = false;

    if(context == null || context.getUser() == null)
    {
      return fileModified;
    }

    if(cx == null || context.getUser() != cx.getUser())
    {
      cx = new BasicContext(context.getUser());
      userDegraded = pxFile != null && !pxFile.getPermissions(cx).has(BPermissions.operatorRead);
    }

     return fileModified || userDegraded;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////
  
  private static final PxProperty[] noProps = new PxProperty[0];
  private static final BOrd[] noOrds = new BOrd[0];
  
  private boolean loading = false;       // are currently loading the PxFile
  private boolean loaded = false;        // has the PxFile been loaded
  private long lastModified;             // lastModified for the PxFile
  private BIFile pxFile;                 // the PxFile
  private BOrd baseOrd = null;           // base ord to resolve ords against
  private BOrd[] origOrds = noOrds;      // original ords found in PxFile
  private Context cx;                    // the Context used to enforce file loading permissions
  
  BWidget root = null;           // root widget from PxFile
  PxProperty[] props = noProps;  // px properties for PxFile

  private ReentrantLock lock = new ReentrantLock();
  private Condition loadCondition = lock.newCondition();
}
