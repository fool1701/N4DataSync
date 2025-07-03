/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.velocity;

import com.tridium.velocity.HtmlEscaper;
import com.tridium.web.RequireJsUtil;
import com.tridium.web.WebUtil;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.OrdTarget;
import javax.baja.nav.BINavNode;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BSpace;
import javax.baja.sys.*;
import javax.baja.user.BUser;
import javax.baja.util.BNameMap;
import javax.baja.util.BTypeSpec;
import javax.baja.util.BWsAnnotation;
import javax.baja.util.Lexicon;
import javax.baja.web.WebDev;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * VelocityContextUtil contains utility methods for use in velocity templates.
 *
 * @author    John Sublett
 * @creation  23 Jun 2011
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
public final class VelocityContextUtil
{
  public VelocityContextUtil(Context cx)
  {
    this.cx = cx;
  }

////////////////////////////////////////////////////////////////
// Navigation
////////////////////////////////////////////////////////////////

  /**
   * Return the path to the specified target object as an array of objects.
   *
   * @see VelocityContextUtil#getPath(Object, Object)
   *
   * @param navObj a BINavNode or ORD (or String) that resolves to a BINavNode.
   * @return the path to the given node.
   */
  public ArrayList<BINavNode> getPath(Object navObj)
  {
    return getPath(navObj, null);
  }

  /**
   * Return the path to the specified target object as an array of objects using the specified base.
   *
   * @see VelocityContextUtil#getPath(Object)
   *
   * @param navObj  a BINavNode or ORD that resolves to a BINavNode.
   * @param base    if an ORD is being resolved, this base is used.
   *                If the base is a BOrd (or String) then it will be resolved as an ORD with
   *                the resulting argument being used as the base.
   * @return the path to the given node.
   */
  public ArrayList<BINavNode> getPath(Object navObj, Object base)
  {
    ArrayList<BINavNode> path = new ArrayList<>();
    BINavNode parent = toNavNode(navObj, base);
    BUser user = cx.getUser();

    while (parent != null && !(parent instanceof BSpace))
    {
      BPermissions permissions = user.getPermissionsFor((BIProtected)parent);

      path.add(0, parent);
      parent = parent.getNavParent();
    }

    return path;
  }

  /**
   * Return all the child values of a Complex.
   *
   * @see VelocityContextUtil#getChildren(Object, Object)
   *
   * @param compObj  the BComplex to get the children from.
   *                 This can also be an ORD that can resolve to a Complex.
   * @return a list of Property values from the given Component.
   */
  public ArrayList<BValue> getChildren(Object compObj)
  {
    return getChildren(compObj, "baja:Value");
  }

  /**
   * Return the child values of of a particular type from a Complex.
   *
   * @see VelocityContextUtil#getChildren(Object)
   *
   * @param compObj  the BComplex to get the children from.
   *                 This can also be an ORD that can resolve to a Complex.
   * @param type     the type (or String typeSpec) used to filter the children returned.
   * @return a list of Property values from the given Component.
   */
  public ArrayList<BValue> getChildren(Object compObj, Object type)
  {
    Type t = Sys.getType(type.toString());

    BComplex comp = toComplex(compObj, null);

    Property[] props = comp.getPropertiesArray();
    BUser user = cx.getUser();
    ArrayList<BValue> list = new ArrayList<>();

    for (int i = 0; i < props.length; ++i)
    {
      BValue value = comp.get(props[i]);

      // Make sure we match the original requesting type
      if (value.getType().is(t))
      {
        if (value instanceof BIProtected)
        {
          BPermissions permissions = user.getPermissionsFor((BIProtected)value);

          BPermissions requiredPermissions;
          if (value.isComponent())
            requiredPermissions = BPermissions.operatorRead;
          else
            requiredPermissions = Flags.isOperator(comp, props[i]) ? BPermissions.operatorRead : BPermissions.adminRead;

          if (permissions.has(requiredPermissions))
            list.add(value);
        }
        else
          list.add(value);
      }
    }

    return list;
  }

////////////////////////////////////////////////////////////////
// Value
////////////////////////////////////////////////////////////////

  /**
   * Is the value an instance of the specified type?
   *
   * @see Type#is(Type)
   *
   * @param value  the value to test against.
   * @param type   the Type (or String typeSpec) to test the value against.
   * @return true if the value is of the specified type.
   */
  public boolean is(BObject value, Object type)
  {
    return value.getType().is(BTypeSpec.make(type.toString()).getTypeInfo());
  }

  /**
   * Should the given Slot be shown?
   * <p>
   * This will also filter out Properties that don't normally show up on a Property Sheet
   * including BWsAnnotation, BLinks and BNampMap (displayNames).
   *
   * @param compObj  the complex for the Slot. This can be a BComplex or an ORD (or String) that resolves to
   *                 a BComplex.
   * @param slotObj  the slot to test. This can be a Slot or a String for the Slot name.
   * @return return true if the Slot should be hidden.
   */
  public boolean isHidden(Object compObj, Object slotObj)
  {
    BComplex complex = toComplex(compObj, null);
    Slot slot = toSlot(complex, slotObj);

    if (Flags.isHidden(complex, slot)) return true;

    // Check the security on this complex (if protected)
    if (complex instanceof BIProtected)
    {
      BPermissions permissions = cx.getUser().getPermissionsFor((BIProtected)complex);
      if (!permissions.hasOperatorRead()) return true;
    }

    // If we have a Property then check for the typical Types that we don't want to display on a Property Sheet...
    if (slot instanceof Property)
    {
      Property prop = (Property)slot;
      BValue value = complex.get(prop);

      if (value instanceof BWsAnnotation) return true;
      if (value instanceof BNameMap && prop.getName().equals("displayNames")) return true;
      if (value instanceof BLink) return true;
    }

    return false;
  }

////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////

  /**
   * Return true if the given Component has read permissions by the user.
   *
   * @param compObj  the Component used to checked permissions against. This can also
   *                 be a BOrd (or String) that resolves to a BComponent.
   * @return true if the given Component can be read.
   */
  public boolean canRead(Object compObj)
  {
    BComponent comp = toComponent(compObj, null);

    // If this is a Component then see if we've got operator read permissions for it...
    BPermissions permissions = cx.getUser().getPermissionsFor(comp);
    return permissions.has(BPermissions.operatorRead);
  }

  /**
   * Return true if the given Slot on the Component can be read by the user.
   *
   * @param compObj  the Component used to checked permissions against. This can also
   *                 be a BOrd (or String) that resolves to a BComponent.
   * @param slotObj  the Slot or String Slot name used to test against.
   * @return true if the given Slot on the Component can be read.
   */
  public boolean canRead(Object compObj, Object slotObj)
  {
    BComponent comp = toComponent(compObj, null);
    Slot slot = toSlot(comp, slotObj);
    BUser user = cx.getUser();

    if (slot.isProperty())
    {
      BValue value = comp.get(slot.asProperty());
      if (value instanceof BComponent)
      {
        // If this is a Component then see if we've got operator read permissions for it...
        BPermissions permissions = user.getPermissionsFor((BComponent)comp.get(slot.asProperty()));
        return permissions.has(BPermissions.operatorRead);
      }
    }

    // For anything that's not a Component, get the parent permissions and examine the slot permissions...
    BPermissions permissions = user.getPermissionsFor(comp);
    BPermissions requiredPermissions = Flags.isOperator(comp, slot) ? BPermissions.operatorRead : BPermissions.adminRead;
    return permissions.has(requiredPermissions);
  }

  /**
   * Return true if the given Slot on the Component can be written by the user.
   *
   * @param compObj  the Component used to checked permissions against. This can also
   *                 be a BOrd (or String) that resolves to a BComponent.
   * @param slotObj  the Slot or String Slot name used to test against.
   * @return true if the given Slot on the Component can be written too.
   */
  public boolean canWrite(Object compObj, Object slotObj)
  {
    BComponent comp = toComponent(compObj, null);
    Slot slot = toSlot(comp, slotObj);
    BUser user = cx.getUser();

    if (slot.isProperty())
    {
      BValue value = comp.get(slot.asProperty());
      if (value instanceof BComponent)
      {
        // If this is a Component then see if we've got operator read permissions for it...
        BPermissions permissions = user.getPermissionsFor((BComponent)comp.get(slot.asProperty()));
        return permissions.has(BPermissions.operatorWrite);
      }
    }

    // For anything that's not a Component, get the parent permissions and examine the slot permissions...
    BPermissions permissions = user.getPermissionsFor(comp);
    BPermissions requiredPermissions = Flags.isOperator(comp, slot) ? BPermissions.operatorWrite : BPermissions.adminWrite;
    return permissions.has(requiredPermissions);
  }

  /**
   * Return true if the given Slot on the Component can be invoked by the user.
   *
   * @param compObj  the Component used to checked permissions against. This can also
   *                 be a BOrd (or String) that resolves to a BComponent.
   * @param slotObj  the Slot or String Slot name used to test against.
   * @return true if the given Slot on the Component can be invoked by the user.
   */
  public boolean canInvoke(Object compObj, Object slotObj)
  {
    BComponent comp = toComponent(compObj, null);
    Slot slot = toSlot(comp, slotObj);
    BUser user = cx.getUser();

    if (slot.isProperty())
    {
      BValue value = comp.get(slot.asProperty());
      if (value instanceof BComponent)
      {
        // If this is a Component then see if we've got operator read permissions for it...
        BPermissions permissions = user.getPermissionsFor((BComponent)comp.get(slot.asProperty()));
        return permissions.has(BPermissions.operatorInvoke);
      }
    }

    // For anything that's not a Component, get the parent permissions and examine the slot permissions...
    BPermissions permissions = user.getPermissionsFor(comp);
    BPermissions requiredPermissions = Flags.isOperator(comp, slot) ? BPermissions.operatorInvoke : BPermissions.adminInvoke;
    return permissions.has(requiredPermissions);
  }

////////////////////////////////////////////////////////////////
// ORDs
////////////////////////////////////////////////////////////////

  /**
   * Return a valid URI from the given ORD, Component, Icon or Object.
   * <p>
   * The returned URI will be correctly encoded.
   *
   * @throws UnsupportedEncodingException
   *
   * @param obj  can be a BOrd, BComponent, BIcon or Object (that has toString called on it)
   *             to form a URI.
   * @return the encoded URI.
   */
  public String toUri(Object obj)
      throws UnsupportedEncodingException
  {
    BOrd ord = BOrd.NULL;

    // Get ORD from Component depending on object type...
    if (obj instanceof BOrd) ord = (BOrd)obj;
    else if (obj instanceof BComponent) ord = ((BComponent)obj).getNavOrd();
    else if (obj instanceof BIcon)
    {
      BOrdList ordList = ((BIcon)obj).getOrdList();
      if (ordList.isNull()) return "";
      ord = ordList.get(0);
    }
    else
      ord = BOrd.make(obj.toString());

    if (ord.isNull()) throw new BajaRuntimeException("Cannot create URI from: " + obj);

    return WebUtil.toUri(null, "/ord?", ord);
  }

  /**
   * Resolve an ORD to an Object and return the result.
   * <p>
   * If the ORD resolves to a mounted Component, the Component will be leased.
   *
   * @see VelocityContextUtil#get(Object, Object)
   *
   * @param ordObj  the ORD or (ORD String) to resolve.
   * @return the resolved object.
   */
  public Object get(Object ordObj)
  {
    return get(ordObj, null);
  }

  /**
   * Resolve an ORD to an Object and return the result.
   * <p>
   * If the ORD resolves to a mounted Component, the Component will be leased.
   *
   * @see VelocityContextUtil#get(Object)
   *
   * @param ordObj  the ORD or (ORD String) to resolve.
   * @param baseObj    the base to resolve the ORD against. If the base is a BOrd
   *                (or a String ORD) this will be resolved and then used as the base
   *                for the ORD.
   * @return the resolved object.
   */
  public Object get(Object ordObj, Object baseObj)
  {
    Object result = resolve(ordObj, baseObj).get();

    // TODO: this is really inefficient but I guess this will do for now...
    if (result instanceof BITable)
    {
      @SuppressWarnings("unchecked")
      BITable<BIObject> table = (BITable<BIObject>)result;
      Column[] columns = table.getColumns().list();

      ArrayList<TableRow> list = new ArrayList<>();
      try(TableCursor<BIObject> cursor = table.cursor())
      {
        while(cursor.next())
        {
          list.add(new TableRow(cursor, columns));
        }
      }

      result = list;
    }

    return result;
  }

  /**
   * Resolve an ORD to an Object and return the resolved OrdTarget.
   * <p>
   * If the ORD resolves to a mounted Component, the Component will be leased.
   *
   * @see VelocityContextUtil#resolve(Object, Object)
   *
   * @param ordObj  the ORD or (ORD String) to resolve.
   * @return the resolved OrdTarget.
   */
  public OrdTarget resolve(Object ordObj)
  {
    return resolve(ordObj, null);
  }

  /**
   * Resolve an ORD to an Object and return the OrdTarget.
   * <p>
   * If the ORD resolves to a mounted Component, the Component will be leased.
   *
   * @see VelocityContextUtil#resolve(Object)
   *
   * @param ordObj  the ORD or (ORD String) to resolve.
   * @param baseObj    the base to resolve the ORD against. If the base is a BOrd
   *                (or a String ORD) this will be resolved and then used as the base
   *                for the ORD.
   * @return the resolved OrdTarget.
   */
  public OrdTarget resolve(Object ordObj, Object baseObj)
  {
    BOrd ord = BOrd.make(ordObj.toString());
    OrdTarget target = ord.resolve(fromBase(baseObj), cx);

    // If this is a mounted Component then attempt to subscribe it
    if (target.getComponent() != null)
    {
      BComponent comp = target.getComponent();
      if (comp.isMounted()) comp.lease();
    }

    return target;
  }

  /**
   * Create a new ORD from a base and child.
   * <p>
   * The returned ORD will also be normalized.
   *
   * @see BOrd#make(BOrd, BOrd)
   *
   * @param base   the base ORD (or String).
   * @param child  the child ORD (or String).
   * @return the new ORD.
   */
  public BOrd ord(Object base, Object child)
  {
    return BOrd.make(BOrd.make(base.toString()), BOrd.make(child.toString())).normalize();
  }

////////////////////////////////////////////////////////////////
// Collections Support
////////////////////////////////////////////////////////////////

  // TODO: Try to optimize using an Iterator?

  public final class TableRow
  {
    private TableRow(TableCursor<BIObject> cursor, Column[] columns)
    {
      this.cursor = cursor;

      for (int i = 0; i < columns.length; ++i)
        map.put(columns[i].getName(), new TableData(cursor.cell(columns[i]), cursor.cell(columns[i]).toString()));
    }

    public Object get(String colName)
    {
      TableData data = map.get(colName);
      if (data == null) return "";
      else return data.val;
    }

    public Object getDisplay(String colName)
    {
      TableData data = map.get(colName);
      if (data == null) return "";
      else return data.valStr;
    }

    private TableCursor<BIObject> cursor;
    private Map<String, TableData> map = new HashMap<>();
  }

  public static class TableData
  {
    private TableData(Object val, String valStr)
    {
      this.val = val;
      this.valStr = valStr;
    }

    private Object val;
    private String valStr;
  }

////////////////////////////////////////////////////////////////
// Lexicon
////////////////////////////////////////////////////////////////

  /**
   * Return a Lexicon for the given module name
   */
  public Lexicon lex(String moduleName)
  {
    return Lexicon.make(moduleName, cx);
  }

////////////////////////////////////////////////////////////////
// String Formatting
////////////////////////////////////////////////////////////////

  public String escapeHtml(Object obj)
  {
    if (obj == null) return null;

    return escaper.escapeHtml(obj.toString());
  }

  /**
   * Return a display string of a BComplex.
   * <p>
   * If the BComplex is a BComponent then it will be leased.
   *
   * @see VelocityContextUtil#getDisplay(Object, Object)
   *
   * @param compObj  the complex to get the display string from.
   *                 If this is an ORD (or String) then it will be resolved
   *                 to a Complex.
   *
   * @return the display String.
   */
  public String getDisplay(Object compObj)
  {
    return getDisplay(compObj, null);
  }

  /**
   * Return a display string of a BComplex.
   * <p>
   * If the BComplex is a BComponent then it will be leased.
   *
   * @see VelocityContextUtil#getDisplay(Object)
   *
   * @param compObj  the complex to get the display string from.
   *                 If this is an ORD (or String) then it will be resolved
   *                 to a Complex.
   * @param base     if compObj is an ORD (or a String) this is used to resolve the ORD against.
   *                 If the base is an ORD (or a String) then it will be first resolved before
   *                 being used to resolve compObj.
   *
   * @return the display String.
   */
  public String getDisplay(Object compObj, Object base)
  {
    if (compObj instanceof BComplex)
    {
      BComplex complex = toComplex(compObj, null);

      BComponent comp = complex.getParentComponent();
      if (comp != null && comp.isMounted()) comp.lease();

      BComplex parent = complex.getParent();

      if (parent != null)
        return getDisplayFromProperty(parent, complex.getPropertyInParent(), base);
      else
        return complex.toString(cx);
    }
    else
    {
      OrdTarget target = resolve(compObj.toString(), base);

      if (target.getParent() != null)
        return getDisplayFromProperty(target.getParent(), target.getPropertyInParent());
      else
        return target.get().toString(cx);
    }
  }

  /**
   * Return the display string of a Property value.
   * <p>
   * If the complex is a BComponent then it will be leased.
   *
   * @see VelocityContextUtil#getDisplayFromProperty(Object, Object, Object)
   *
   * @param compObj  the complex to get the display string from.
   *                 If this is an ORD (or String) then it will be resolved
   *                 to a Complex.
   * @param propObj  the Property or Property name.
   * @return the display string of the Property value.
   */
  public String getDisplayFromProperty(Object compObj, Object propObj)
  {
    return getDisplayFromProperty(compObj, propObj, null);
  }

  /**
   * Return the display string of a Property value.
   * <p>
   * If the complex is a BComponent then it will be leased.
   *
   * @see VelocityContextUtil#getDisplayFromProperty(Object, Object)
   *
   * @param compObj  the complex to get the display string from.
   *                 If this is an ORD (or String) then it will be resolved
   *                 to a Complex.
   * @param propObj  the Property or Property name.
   * @param base     if compObj is an ORD (or a String) this is used to resolve the ORD against.
   *                 If the base is an ORD (or a String) then it will be first resolved before
   *                 being used to resolve compObj.
   *
   * @return the display String.
   */
  public String getDisplayFromProperty(Object compObj, Object propObj, Object base)
  {
    // Work our way up to the Component and merge all the facets so we can create the correct display String
    Context context = cx;

    BComplex complex = toComplex(compObj, base);

    // If we have a Component then lease it
    if (complex.isComponent() && complex.asComponent().isMounted()) complex.asComponent().lease();

    Property prop;
    if (propObj instanceof Property)
      prop = (Property)propObj;
    else
      prop = complex.getProperty(propObj.toString());

    context = mergeContextToComponent(complex, new BasicContext(context, complex.getSlotFacets(prop)));

    return complex.get(prop).toString(context);
  }

  private Context mergeContextToComponent(BComplex complex, Context cx)
  {
    if (complex == null) return cx;
    if (complex instanceof BComponent || complex.getParent() == null) return cx;

    cx = new BasicContext(cx, complex.getParent().getSlotFacets(complex.getPropertyInParent()));

    return mergeContextToComponent(complex.getParent(), cx);
  }

////////////////////////////////////////////////////////////////
// Web Util
////////////////////////////////////////////////////////////////

  /**
   * Return the last Registry build time in milliseconds
   * since the epoch relative to UTC.
   *
   * @since Niagara 3.8
   */
  public String getLastRegBuild()
  {
    return String.valueOf(Sys.getRegistry().getLastBuildTime().getMillis());
  }


  /**
   * Return the HTML and JavaScript for injecting RequireJS into a web page.
   *
   * @return
   */
  public String requirejs()
    throws IOException
  {
    hasRequireJs = true;
    StringWriter out = new StringWriter();
    RequireJsUtil.make(/*useLocalWbRc*/false,
                       cx).requirejs(out);
    return out.toString();
  }

  /**
   * Return the HTML and JavaScript for injecting RequireJS into a web page.
   *
   * @param mainOrd an ORD that specifies the main entry point JavaScript for the Web Application.
   * @return
   */
  public String requirejs(String mainOrd)
    throws IOException
  {
    hasRequireJs = true;
    StringWriter out = new StringWriter();
    RequireJsUtil.make(/*useLocalWbRc*/false,
                       cx).requirejs(BOrd.make(mainOrd), out);
    return out.toString();
  }

  /**
   * Return the HTML and JavaScript for injecting RequireJS into a web page.
   *
   * @param mainOrd an ORD that specifies the main entry point JavaScript for the Web Application.
   * @param configFileOrd an ORD that specifies the location of a JavaScript file that contains the
   *                      declaration of the require config object. Please note, the JavaScript in
   *                      this file must contain a declaration of a global require object.
   * @return
   */
  public String requirejs(String mainOrd, String configFileOrd)
      throws IOException
  {
    hasRequireJs = true;
    StringWriter out = new StringWriter();
    RequireJsUtil.make(/*useLocalWbRc*/false,
                       cx).requirejs(BOrd.make(mainOrd), BOrd.make(configFileOrd), out);
    return out.toString();
  }

  /**
   * Return the script tag for activity monitor to start the monitoring.
   * @return Script tag
   */
  public String startActivityMonitor()
  {
    return getActivityMonitorScript("start");
  }

  /**
   * Return the script tag for activity monitor to keep session alive.
   * @return Script tag
   */
  public String keepSessionAlive()
  {
    return getKeepAliveScript();
  }

  private String getKeepAliveScript(){
    if (hasRequireJs)
    {
      return "<script type='text/javascript'>require(['/module/web/rc/util/activityMonitor.js'], " +
        "function(am){" +
        "var token;" +
        "am.keepAlive()" +
        ".then(function(t){" +
            "token = t;" +
        "});" +
        "window.releaseKeepAliveToken = function(){" +
            "am.release(token);" +
        "}" +
        "})</script>";
    }
    else
    {
      return "<script type='text/javascript' src='/module/web/rc/util/activityMonitor.js'></script>" +
        "<script>" +
        "var token;" +
        "activityMonitor.keepAlive()" +
          ".then(function(t){" +
            "token = t;" +
        "});" +
        "function releaseKeepAliveToken(){activityMonitor.release(token);}" +
        "</script>";
    }
  }

  /**
   * Get the script tag for activity monitor based on the passed in action
   * Action can be "start" or "keepAlive"
   *
   * @param action - "start" or "keepAlive"
   * @return Script tag
   */
  private String getActivityMonitorScript(String action)
  {
    String callStr = new StringBuilder(".").append(action).append("()").toString();
    if (hasRequireJs)
    {
      return "<script type='text/javascript'>require(['/module/web/rc/util/activityMonitor.js'], function(am){am" + callStr + ";})</script>";
    }
    else
    {
      return "<script type='text/javascript' src='/module/web/rc/util/activityMonitor.js'></script>" +
        "<script>activityMonitor" + callStr + ";</script>";
    }
  }

  /**
   * Return true if Web Development Mode is enabled for the given name.
   *
   * @param name the name of the web development application.
   * @return
   *
   * @since Niagara 4.0
   */
  public boolean isWebDev(String name)
  {
    return WebDev.get(name).isEnabled();
  }

////////////////////////////////////////////////////////////////
// jQuery
////////////////////////////////////////////////////////////////  

  /**
   * Return the HTML script tag for jQuery.
   */
  public String jQuery()
  {
    return "<script type='text/javascript' src='/module/js/rc/jquery/jquery.min.js'></script>\n";
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  private BComplex toComplex(Object obj, Object base)
  {
    if (obj == null) return null;

    if (obj instanceof BComplex)
      return (BComplex)obj;
    else
      return (BComplex)BOrd.make(obj.toString()).get(fromBase(base), cx);
  }

  private BComponent toComponent(Object obj, Object base)
  {
    if (obj == null) return null;

    if (obj instanceof BComplex)
      return ((BComplex)obj).getParentComponent();
    else
      return (BComponent)BOrd.make(obj.toString()).get(fromBase(base), cx);
  }

  private Slot toSlot(BComplex complex, Object slotObj)
  {
    if (slotObj instanceof Slot) return (Slot)slotObj;
    else return complex.getSlot(slotObj.toString());
  }

  private BObject fromBase(Object base)
  {
    if (base == null) return BLocalHost.INSTANCE;
    else if (base instanceof BOrd) return ((BOrd)base).get();
    else if (!(base instanceof BObject)) return BOrd.make(base.toString()).get();
    else return (BObject)base;
  }

  private BINavNode toNavNode(Object obj, Object base)
  {
    if (obj == null) return null;

    if (obj instanceof BINavNode)
      return (BINavNode)obj;
    else
      return (BINavNode)BOrd.make(obj.toString()).get(fromBase(base), cx);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private HtmlEscaper escaper = new HtmlEscaper();
  private Context cx;
  private boolean hasRequireJs = false;
}
