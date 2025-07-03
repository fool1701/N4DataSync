/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.search;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BStruct;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BSearchScope defines a scope that is searchable.
 *
 * @author John Sublett
 * @creation 3/17/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The non-localized name of the scope
 */
@NiagaraProperty(
  name = "scopeName",
  type = "String",
  defaultValue = ""
)
/*
 The module to use for looking up the lexicon to localize the name of this scope
 */
@NiagaraProperty(
  name = "scopeLexiconModule",
  type = "String",
  defaultValue = ""
)
/*
 The lexicon key to use for looking up the localized name of this scope
 */
@NiagaraProperty(
  name = "scopeLexiconKey",
  type = "String",
  defaultValue = ""
)
/*
 The ORD to the scope.  Typically this is an ORD to a BSpace that can be searched.
 */
@NiagaraProperty(
  name = "scopeOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 True if this scope matches one of the defaults as defined in the SearchService.
 */
@NiagaraProperty(
  name = "isDefault",
  type = "boolean",
  defaultValue = "false"
)
public class BSearchScope
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.search.BSearchScope(1517982285)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "scopeName"

  /**
   * Slot for the {@code scopeName} property.
   * The non-localized name of the scope
   * @see #getScopeName
   * @see #setScopeName
   */
  public static final Property scopeName = newProperty(0, "", null);

  /**
   * Get the {@code scopeName} property.
   * The non-localized name of the scope
   * @see #scopeName
   */
  public String getScopeName() { return getString(scopeName); }

  /**
   * Set the {@code scopeName} property.
   * The non-localized name of the scope
   * @see #scopeName
   */
  public void setScopeName(String v) { setString(scopeName, v, null); }

  //endregion Property "scopeName"

  //region Property "scopeLexiconModule"

  /**
   * Slot for the {@code scopeLexiconModule} property.
   * The module to use for looking up the lexicon to localize the name of this scope
   * @see #getScopeLexiconModule
   * @see #setScopeLexiconModule
   */
  public static final Property scopeLexiconModule = newProperty(0, "", null);

  /**
   * Get the {@code scopeLexiconModule} property.
   * The module to use for looking up the lexicon to localize the name of this scope
   * @see #scopeLexiconModule
   */
  public String getScopeLexiconModule() { return getString(scopeLexiconModule); }

  /**
   * Set the {@code scopeLexiconModule} property.
   * The module to use for looking up the lexicon to localize the name of this scope
   * @see #scopeLexiconModule
   */
  public void setScopeLexiconModule(String v) { setString(scopeLexiconModule, v, null); }

  //endregion Property "scopeLexiconModule"

  //region Property "scopeLexiconKey"

  /**
   * Slot for the {@code scopeLexiconKey} property.
   * The lexicon key to use for looking up the localized name of this scope
   * @see #getScopeLexiconKey
   * @see #setScopeLexiconKey
   */
  public static final Property scopeLexiconKey = newProperty(0, "", null);

  /**
   * Get the {@code scopeLexiconKey} property.
   * The lexicon key to use for looking up the localized name of this scope
   * @see #scopeLexiconKey
   */
  public String getScopeLexiconKey() { return getString(scopeLexiconKey); }

  /**
   * Set the {@code scopeLexiconKey} property.
   * The lexicon key to use for looking up the localized name of this scope
   * @see #scopeLexiconKey
   */
  public void setScopeLexiconKey(String v) { setString(scopeLexiconKey, v, null); }

  //endregion Property "scopeLexiconKey"

  //region Property "scopeOrd"

  /**
   * Slot for the {@code scopeOrd} property.
   * The ORD to the scope.  Typically this is an ORD to a BSpace that can be searched.
   * @see #getScopeOrd
   * @see #setScopeOrd
   */
  public static final Property scopeOrd = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code scopeOrd} property.
   * The ORD to the scope.  Typically this is an ORD to a BSpace that can be searched.
   * @see #scopeOrd
   */
  public BOrd getScopeOrd() { return (BOrd)get(scopeOrd); }

  /**
   * Set the {@code scopeOrd} property.
   * The ORD to the scope.  Typically this is an ORD to a BSpace that can be searched.
   * @see #scopeOrd
   */
  public void setScopeOrd(BOrd v) { set(scopeOrd, v, null); }

  //endregion Property "scopeOrd"

  //region Property "isDefault"

  /**
   * Slot for the {@code isDefault} property.
   * True if this scope matches one of the defaults as defined in the SearchService.
   * @see #getIsDefault
   * @see #setIsDefault
   */
  public static final Property isDefault = newProperty(0, false, null);

  /**
   * Get the {@code isDefault} property.
   * True if this scope matches one of the defaults as defined in the SearchService.
   * @see #isDefault
   */
  public boolean getIsDefault() { return getBoolean(isDefault); }

  /**
   * Set the {@code isDefault} property.
   * True if this scope matches one of the defaults as defined in the SearchService.
   * @see #isDefault
   */
  public void setIsDefault(boolean v) { setBoolean(isDefault, v, null); }

  //endregion Property "isDefault"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSearchScope.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor
   */
  public BSearchScope()
  {
  }

  /**
   * Constructor with parameters.
   *
   * @param scopeName The non-localized name of the scope
   * @param lexModule The module to use for looking up the lexicon to localize the name of this scope
   * @param lexKey The lexicon key to use for looking up the localized name of this scope
   * @param scopeOrd The ORD to the scope.  Typically this is an ORD to a BSpace that can be searched
   * @param isDefault True if this scope matches one of the defaults as defined in the SearchService
   */
  public BSearchScope(String scopeName,
                      String lexModule,
                      String lexKey,
                      BOrd scopeOrd,
                      boolean isDefault)
  {
    setScopeName(scopeName);
    setScopeLexiconModule(lexModule);
    setScopeLexiconKey(lexKey);
    setScopeOrd(scopeOrd);
    setIsDefault(isDefault);
  }
}
