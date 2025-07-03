/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.fieldeditor;

import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;

import com.tridium.sys.schema.Fw;

/**
 * BWbFieldEditorBinding is used to bind WbFieldEditors to an object.
 *
 * @author    Brian Frank
 * @creation  5 Jul 04
 * @version   $Revision: 7$ $Date: 10/22/08 1:11:24 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "workbench:WbFieldEditor"
  )
)
public class BWbFieldEditorBinding
  extends BBinding
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.fieldeditor.BWbFieldEditorBinding(796073698)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbFieldEditorBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the parent widget cast as a BWbFieldEditor.
   */
  public final BWbFieldEditor getFieldEditor()
  {
    return (BWbFieldEditor)getWidget();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  public void started()
  {
    super.started();

    // make sure we start with a fresh editor (sometimes
    // constructors accidently set the modified flag)
    getFieldEditor().clearModified();
  }

  public void targetChanged()
  {
    super.targetChanged();

    // don't reload if currently modified
    BWbFieldEditor editor = getFieldEditor();
    if (editor.isModified()) return;

    OrdTarget target = getTarget();
    BFacets facets = target.getFacets();

    if (!target.canWrite()) editor.setReadonly(true);
    editor.loadValue(target.get().asValue().newCopy(), facets);
  }

  public void save(Context cx)
    throws Exception
  {
    BWbFieldEditor editor = getFieldEditor();
    if (!editor.isModified()) return;
    if (!isBound()) return;


    // set property with value
    OrdTarget ord = getTarget();
    BComplex parent = null;
    BValue value = (BValue)editor.saveValue(cx);
    if(ord.get() instanceof BComponent)
    {
      // Is this an editor on a component?
      // Fix for issue 12477
      BComponent component = (BComponent)ord.get();
      parent = component.getParent();
      parent.set(component.getPropertyInParent(), value);
      fw(Fw.CHANGED, BBinding.ord, null,null,null);
    } else {
      // Must be an editor on a slot
      parent = getTarget().getComponent();
      Property[] props = getTarget().getPropertyPathInComponent();
      for(int i=0; i<props.length-1; ++i)
        parent = (BComplex)parent.get(props[i]);
      Property prop = props[props.length-1];
      parent.set(prop, value, cx);
    }
  }

////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  public boolean isParentLegal(BComponent c)
  {
    return c instanceof BWbFieldEditor;
  }


}
