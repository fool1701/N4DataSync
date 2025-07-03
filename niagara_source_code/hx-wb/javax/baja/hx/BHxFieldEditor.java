/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentList;
import javax.baja.agent.NoSuchAgentException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BPassword;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIPropertyContainer;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIValidator;
import javax.baja.util.BTypeSpec;
import javax.baja.util.CannotValidateException;
import javax.baja.workbench.BWbEditor;

import com.tridium.bql.filter.BFilterEntry;
import com.tridium.hx.fieldeditors.BHxBajauxComplexFE;
import com.tridium.hx.fieldeditors.BHxBajauxFE;
import com.tridium.hx.fieldeditors.BHxDefaultFE;
import com.tridium.web.WebUtil;

/**
 * BHxFieldEditor is an editor designed to view and edit a
 * BObject.  Field Editors are typically smaller editors
 * meant to be assembled on a page by a parent HxView.
 *
 * @author Andy Frank on 4 Jan 05
 * @version $Revision: 9$ $Date: 8/16/10 3:05:07 PM EDT$
 * @since Baja 1.0
 */
@NiagaraType
@SuppressWarnings("WeakerAccess")
public abstract class BHxFieldEditor
  extends BHxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.BHxFieldEditor(2979906276)1.0$ @*/
/* Generated Fri Nov 19 13:59:13 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxFieldEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////      

  /**
   * Return the HxFieldEditor registered on the given object.
   * A Bajaux FieldEditor (web:IFormFactorMini) will be provided if one of the following is true:
   * 1) The "uxFieldEditor" facet is given as part of the Context.
   * 2) The "bajaux=true" facet is given and a bajaux editor is registered on the obj.
   * 3) No hx field editor is available and there is a bajaux field editor available.
   * <p>
   * If the object is a 'BComplex' and there are no field editors found and both facets for 'bajaux' and 'bajauxComplexFallback' are true, then
   * a simple bajaux PropertySheet will be provided.
   *
   * @throws NoSuchAgentException if no agent can be found in either bajaux or hx.
   */
  public static BHxFieldEditor makeFor(BObject obj, Context cx)
  {
    if (BAJA_UX_EDITORS_DISABLED)
    {
      return makeForHx(obj, cx);
    }

    BHxFieldEditor uxEditor;

    boolean preferBajaux = cx != null && (cx.getFacets().getb(BAJAUX_KEY, false) || cx.getFacets().get(BFacets.UX_FIELD_EDITOR) != null);
    if (preferBajaux)
    {
      uxEditor = ux(obj, cx);
      if (uxEditor != null)
        return BHxBajauxFE.INSTANCE;
    }

    BHxFieldEditor fieldEditor;
    try
    {
      fieldEditor = makeForHx(obj, cx);
      if (fieldEditor instanceof BHxDefaultFE && !preferBajaux)
      {
        uxEditor = ux(obj, cx);
        if (uxEditor != null)
          return BHxBajauxFE.INSTANCE;
      }
      return fieldEditor;
    }
    catch (NoSuchAgentException e)
    {
      if (!preferBajaux)
      {
        uxEditor = ux(obj, cx);
        if (uxEditor != null)
          return BHxBajauxFE.INSTANCE;
      }
      else if (cx.getFacets().getb(BAJAUX_COMPLEX_FALLBACK_KEY, false) && (obj instanceof BComplex || obj instanceof BIPropertyContainer))
      {
        return BHxBajauxComplexFE.INSTANCE;
      }
      throw e;
    }
  }

  /**
   * Similar to BHxFieldEditor.makeFor, but automatically prefer bajaux editors
   * and if no specific editor can be found for a complex, then fallback is the BajauxComplexFE which provides a
   * property sheet for editing direct children of the complex. Instead of throwing a NoSuchAgentException a
   * simple DefaultFE will be provided as a fallback.
   *
   * @since Niagara 4.7
   */
  public static BHxFieldEditor makeForUx(BObject obj, HxOp op)
  {
    Context cx;
    BHxFieldEditor editor;
    try
    {
      if (op != null)
      {
        op.mergeFacets(PREFER_BAJAUX);
        op.mergeFacets(BAJAUX_COMPLEX_FALLBACK);
        cx = op;
      }
      else
      {
        cx = BFacets.make(BAJAUX_KEY, BBoolean.TRUE, BAJAUX_COMPLEX_FALLBACK_KEY, BBoolean.TRUE);
      }
      editor = BHxFieldEditor.makeFor(obj, cx);
    }
    catch (NoSuchAgentException e)
    {
      editor = BHxDefaultFE.INSTANCE;
    }
    return editor;
  }

  /**
   * Return the HxFieldEditor registered on the given object, or
   * This function ignores bajaux field editors.
   *
   * @throws NoSuchAgentException if no agent can be found.
   */
  private static BHxFieldEditor makeForHx(BObject obj, Context cx)
  {
    // If obj is a WbFieldEditor, return the Hx agent on it
    if (obj.getType().is(wbType))
      return hx(obj.getAgents(), cx);

    // If the FE is explicitly specified, return it
    if (cx != null && cx.getFacet(BFacets.FIELD_EDITOR) != null)
    {
      String typeSpec = cx.getFacet(BFacets.FIELD_EDITOR).toString();
      TypeInfo info = Sys.getRegistry().getType(typeSpec);

      // HxFieldEditor directly specified
      if (info.is(hxType))
        return (BHxFieldEditor) info.getInstance();

      // Get Hx agent on WbFieldEditor
      return hx(Sys.getRegistry().getAgents(info), cx);
    }

    // Check if a HxFieldEditor is directly registered
    AgentList list = obj.getAgents().filter(getAgentFilter(hxFilter, cx));
    if (list.size() > 0)
      return hx(list, cx);

    // Else first find the WbFieldEditor for object type
    TypeInfo info = obj.getAgents().filter(getAgentFilter(wbFilter, cx)).getDefault().getAgentType();
    return hx(Sys.getRegistry().getAgents(info), cx);
  }

  /**
   * Return the BHxBajauxFE.INSTANCE if any bajaux field editor is registered on the obj or the "uxFieldEditor" facet
   * is set.
   */
  private static BHxFieldEditor ux(BObject obj, Context cx)
  {
    if (obj instanceof BFilterEntry || //NCCB-20741: BqlFilterEntryEditor doesn't save correctly
      obj instanceof BPassword)//NCCB-11517: Create a ux Client Credential Editor
    {
      return null;
    }

    if (cx != null && cx.getFacets().get(BFacets.UX_FIELD_EDITOR) != null)
      return BHxBajauxFE.INSTANCE;

    AgentList uxList = obj.getAgents().filter(getAgentFilter(uxMiniFilter, cx));

    if (uxList.size() > 0)
    {
      return BHxBajauxFE.INSTANCE;
    }
    else
    {
      return null; //none available
    }
  }

  private static BHxFieldEditor hx(AgentList list, Context cx)
  {
    return (BHxFieldEditor) list.filter(getAgentFilter(hxFilter, cx)).getDefault().getInstance();
  }
////////////////////////////////////////////////////////////////
// BHxFieldEditor
////////////////////////////////////////////////////////////////

  /**
   * Save and validate the value.
   *
   * @since Niagara 3.6
   */
  public final BObject fwSave(HxOp op)
    throws Exception
  {
    BObject value = save(op);
    fwValidate(value, op);
    return value;
  }

  /**
   * Validate the value passed in as the parameter, use the HxOp for the context
   *
   * @since Niagara 3.6
   */
  @SuppressWarnings("UnusedReturnValue")
  public final BObject fwValidate(BObject value, HxOp op)
    throws CannotValidateException
  {

    BIValidator[] validators = getValidators(op);
    for (BIValidator validator : validators)
    {
      validator.validate(value, op);
    }

    if (this instanceof BIValidator)
      ((BIValidator) this).validate(value, op);

    if (value instanceof BIValidator)
      ((BIValidator) value).validate(value, op);

    return value;
  }

  /**
   * Check the context for BIValidator definitions and
   * resolve them.
   *
   * @since Niagara 3.6
   */
  private BIValidator[] getValidators(HxOp op) //carbon copy of BWbEditor.loadValidators
  {
    Array<BIValidator> arr = new Array<>(BIValidator.class);
    String[] keys = op.getFacets().list();
    for (String key : keys)
    {
      if (key.startsWith(BIValidator.VALIDATOR_FACET))
      {
        final String spec = op.getFacets().gets(key, "");
        BObject candidate;
        try
        {
          candidate = BTypeSpec.make(spec).getInstance();
        }
        catch (Exception e)
        {
          throw new LocalizableRuntimeException(BWbEditor.TYPE.getModule().getModuleName(),
            "wbEditor.badValidatorSpec", new Object[]{key, spec}, e);
        }

        if (!candidate.getType().is(BIValidator.TYPE))
        {
          throw new LocalizableRuntimeException(BWbEditor.TYPE.getModule().getModuleName(),
            "wbEditor.badValidatorType", new Object[]{key, spec});
        }
        arr.add((BIValidator) candidate);
      }
    }
    return arr.trim();
  }

////////////////////////////////////////////////////////////////
// Profile Agent Filtering
////////////////////////////////////////////////////////////////

  public static AgentFilter getAgentFilter(AgentFilter filter, Context cx)
  {
    if (!(cx instanceof HxOp) || ((HxOp) cx).getProfile() == null)
    {
      return filter;
    }
    HxOp op = (HxOp) cx;
    return AgentFilter.and(new WebUtil.ProfileFilter(op.getProfile(), op.get()), filter);
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static TypeInfo uxMini = Sys.getRegistry().getType("web:IFormFactorMini");
  private static TypeInfo hxType = Sys.getRegistry().getType("hx:HxFieldEditor");
  private static TypeInfo wbType = Sys.getRegistry().getType("workbench:WbFieldEditor");
  private static AgentFilter uxMiniFilter = AgentFilter.is(uxMini);
  private static AgentFilter hxFilter = AgentFilter.is(TYPE);
  private static AgentFilter wbFilter = AgentFilter.is(wbType);

  private static boolean BAJA_UX_EDITORS_DISABLED =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("hx.disableBajauxEditors"));

  public static final String BAJAUX_KEY = "bajaux";
  public static final BFacets PREFER_BAJAUX = BFacets.make(BAJAUX_KEY, true);

  public static final String BAJAUX_COMPLEX_FALLBACK_KEY = "bajauxComplexFallback";
  public static final BFacets BAJAUX_COMPLEX_FALLBACK = BFacets.make(BAJAUX_COMPLEX_FALLBACK_KEY, true);
}
