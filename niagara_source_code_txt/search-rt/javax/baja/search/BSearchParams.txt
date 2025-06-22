/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.search;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.baja.naming.BLocalHost;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BISpace;
import javax.baja.space.BISpaceNode;
import javax.baja.spy.BSpy;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.util.Lexicon;

/**
 * Search parameters are used to define the input arguments to a search.
 *
 * @author Dan Heine
 * @author Scott Hoye
 * @creation 2014-02-06
 * @since Niagara 4.0
 */
@NiagaraType
/*
 String that defines the search query.  It is the String form of a query ORD.
 */
@NiagaraProperty(
  name = "query",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR
)
/*
 The list of scopes that will be used to process the search query against.
 */
@NiagaraProperty(
  name = "scopeVector",
  type = "BVector",
  defaultValue = "new BVector()",
  flags = Flags.OPERATOR
)
public class BSearchParams
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchParams(334510034)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "query"

  /**
   * Slot for the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #getQuery
   * @see #setQuery
   */
  public static final Property query = newProperty(Flags.OPERATOR, "", null);

  /**
   * Get the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #query
   */
  public String getQuery() { return getString(query); }

  /**
   * Set the {@code query} property.
   * String that defines the search query.  It is the String form of a query ORD.
   * @see #query
   */
  public void setQuery(String v) { setString(query, v, null); }

  //endregion Property "query"

  //region Property "scopeVector"

  /**
   * Slot for the {@code scopeVector} property.
   * The list of scopes that will be used to process the search query against.
   * @see #getScopeVector
   * @see #setScopeVector
   */
  public static final Property scopeVector = newProperty(Flags.OPERATOR, new BVector(), null);

  /**
   * Get the {@code scopeVector} property.
   * The list of scopes that will be used to process the search query against.
   * @see #scopeVector
   */
  public BVector getScopeVector() { return (BVector)get(scopeVector); }

  /**
   * Set the {@code scopeVector} property.
   * The list of scopes that will be used to process the search query against.
   * @see #scopeVector
   */
  public void setScopeVector(BVector v) { set(scopeVector, v, null); }

  //endregion Property "scopeVector"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchParams.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * For internal use only.  Use one of the other constructors to
   * specify valid search parameters.
   */
  public BSearchParams()
  {
  }

  /**
   * Creates a BSearchParams instance with the given query and scope.
   *
   * @param query The search query in ORD form.
   * @param scope The scope on which the search query should be run.
   */
  public BSearchParams(BOrd query, BIObject scope)
  {
    this(query.encodeToString(), new BIObject[] { scope });
  }

  /**
   * Creates a BSearchParams instance with the given query and scopes.
   *
   * @param query The search query in ORD form.
   * @param scopes The scopes on which the search query should be run.
   */
  public BSearchParams(BOrd query, BIObject[] scopes)
  {
    this(query.encodeToString(), scopes);
  }

  /**
   * Creates a BSearchParams instance with the given query and scope.
   *
   * @param query The search query in String form (should be convertable to an ORD).
   * @param scope The scope on which the search query should be run.
   */
  public BSearchParams(String query, BIObject scope)
  {
    this(query, new BIObject[] { scope });
  }

  /**
   * Creates a BSearchParams instance with the given query and scopes.
   *
   * @param query The search query in String form (should be convertable to an ORD).
   * @param scopes The scopes on which the search query should be run.
   */
  public BSearchParams(String query, BIObject[] scopes)
  {
    setQuery(query);
    if (scopes != null)
    {
      for (BIObject scope : scopes)
      {
        addScope(scope);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the search query as an ORD.
   *
   * @return ORD or null if the query is syntactically not an ORD.  No guarantee that all
   * schemes are registered however.
   */
  public BOrd getQueryOrd()
  {
    BOrd query = null;

    try
    {
      query = BOrd.make(getQuery());
      query.parse();
    }
    catch (Exception e)
    {
    }
    return query;
  }

  /**
   * Convenience method for adding a single scope to the search parameters.
   * For non-BValue scopes, must use a BOrd that resolves to that scope.
   *
   * @param scope the search scope to add
   */
  public void addScope(BIObject scope)
  {
    addScope(getScopeVector(), scope);
  }

  /**
   * Add a list of scopes to the search parameters.
   *
   * @param scopes array of scope objects
   */
  public void addScopes(BIObject[] scopes)
  {
    for (BIObject scope : scopes)
    {
      addScope(scope);
    }
  }

  /**
   * Convenience method for setting a single scope on the search parameters.
   * This method will replace any previous scopes on this search parameters instance.
   * For non-BValue scopes, must use a BOrd that resolves to that scope.
   *
   * @param scope the search scope to set
   */
  public void setScope(BIObject scope)
  {
    BVector v = getScopeVector();
    v.removeAll();
    addScope(v, scope);
  }

  /**
   * Convenience method for setting the scopes on the search parameters.
   * This method will replace any previous scopes on this search parameters instance.
   * For non-BValue scopes, must use a BOrd that resolves to that scope.
   *
   * @param scopes the search scopes to set
   */
  public void setScopes(BIObject[] scopes)
  {
    BVector v = getScopeVector();
    v.removeAll();
    for (BIObject scope : scopes)
    {
      addScope(v, scope);
    }
  }

  /**
   * Convenience method for resolving relative scopes on the local host.
   *
   * @return array of scope objects
   */
  public BIObject[] resolveScopes()
  {
    return resolveScopes(BLocalHost.INSTANCE);
  }

  /**
   * Return an array of objects from the list of scopes.  Resolve
   * any object that is a BOrd.
   *
   * @param base base object for resolving ords
   * @return array of scope objects
   */
  public BIObject[] resolveScopes(BIObject base)
  {
    BValue[] scopeValues = getScopesAsValues();
    ArrayList<BIObject> scopeList = new ArrayList<>();

    for (int i = 0; i < scopeValues.length; i++)
    {
      try
      {
        scopeList.add(resolveScope(scopeValues[i], base));
      }
      catch (Exception e)
      {
        BString error = BString.make("Cannot resolve scope " + scopeValues[i].toString(null) +
          " with base " + base == null ? "null" : base.toString(null));
        scopeList.add(error);
        Logger.getLogger("task").warning(error.getString());
      }
    }
    return scopeList.toArray(new BIObject[scopeList.size()]);
  }

  @Override
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();

    BVector v = getScopeVector();
    Property[] props = v.getPropertiesArray();
    for (int i = 0; i < props.length; i++)
    {
      if (i > 0) sb.append("; ");
      sb.append(v.get(props[i]).toString(context));
    }

    Lexicon lex = Lexicon.make("search", context);
    return lex.getText("searchParams.toString", new Object[] { getQuery(), sb.toString() });
  }

////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  /**
   * Return the array of scopes to search.  Many of these will be unresolved
   * ORDs.  Call resolveScopes() to return the actual objects.
   */
  private BValue[] getScopesAsValues()
  {
    ArrayList<BValue> scopes = new ArrayList<>();
    BVector v = getScopeVector();
    Property[] props = v.getPropertiesArray();
    for (Property prop : props)
    {
      if (prop.isProperty())
      {
        BValue obj = v.get(prop);
        scopes.add(obj);
      }
    }
    return scopes.toArray(new BValue[scopes.size()]);
  }

  /**
   * Resolve a scope.  If the scope is a BOrd, resolve it using
   * BLocalHost.INSTANCE and return the resolved object, otherwise
   * just return the scope.
   *
   * @param scope either a BValue scope or a BOrd that gets resolved to a scope
   * @return resolved scope
   */
  private static BIObject resolveScope(BValue scope)
  {
    return resolveScope(scope, BLocalHost.INSTANCE);
  }

  /**
   * Resolve a scope.  If the scope is a BOrd, resolve it using
   * the passed-in base object and return the resolved object, otherwise
   * just return the scope.
   *
   * @param scope either a BValue scope or a BOrd that gets resolved to a scope
   * @param base  base object against which to resolve the scope if it is a BOrd
   * @return resolved scope
   */
  private static BIObject resolveScope(BValue scope, BIObject base)
  {
    BIObject obj = scope;
    if (obj instanceof BOrd)
    {
      obj = ((BOrd)obj).resolve((BObject)base).get();
    }
    return obj;
  }

  /**
   * Convenience method for adding a single scope to the search parameters.
   * BISpaceNodes are added as ORDs.
   *
   * @param v     BVector to which the scope will be added
   * @param scope scope to add, ignored if null
   */
  private static void addScope(BVector v, BIObject scope)
  {
    BValue vScope = convertToValue(scope);

    if (vScope != null)
    {
      v.add("scope?", vScope);
    }
    else if (scope != null)
    {
      throw new IllegalArgumentException("Cannot use " + scope.toString(null) + " as a scope object");
    }
  }

  /**
   * Attempts to convert the scope to a BValue by (preferably) returning
   * the ORD for the object or, failing that, attempting to cast it.
   * If the object is BComplex and is not mounted (has no ORD), newCopy()
   * is called and the copy returned.
   *
   * @param scope object to convert to a BValue
   * @return scope as a BValue or null if it cannot be converted
   */
  private static BValue convertToValue(BIObject scope)
  {
    BValue vScope = null;
    if (scope != null)
    {
      if (scope instanceof BISpaceNode)
      {
        // Always use the ORD to make sure we get the actual instance once resolved
        vScope = ((BISpaceNode)scope).getOrdInSession();
      }
      else if (scope instanceof BSpy)
      {
        vScope = BOrd.make(((BSpy)scope).getPath());
      }
      if (vScope == null && scope instanceof Entity)
      {
        Entity eScope = (Entity)scope;
        if (eScope.getOrdToEntity().isPresent())
        {
          vScope = eScope.getOrdToEntity().get();
        }
      }
      if (vScope == null && scope instanceof BComplex)
      {
        BComplex cScope = (BComplex)scope;
        if (cScope.getParent() == null)
        {
          // Don't bother to copy if it is not already parented
          vScope = cScope;
        }
        else
        {
          vScope = cScope.newCopy();
        }
      }
      if (vScope == null && scope instanceof BISpace)
      {
        vScope = ((BISpace)scope).getOrdInSession();
      }
      if (vScope == null && scope instanceof BValue)
      {
        vScope = (BValue)scope;
      }
    }
    return vScope;
  }

}
