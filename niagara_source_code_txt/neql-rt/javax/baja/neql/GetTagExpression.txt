/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import com.tridium.neql.NeqlUtil;

/**
 * GetTagExpression gets a tag from the evaluated entity.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public class GetTagExpression
  extends Expression
{
  public GetTagExpression(String namespace, String key)
  {
    this.namespace = namespace;
    this.key = key;

    cachedId = ((namespace != null) && !namespace.isEmpty()) ? Id.newId(namespace, key) : null;
  }

  /**
   * Get the node type.
   *
   * @return AST_GET_TAG
   */
  @Override
  public int getNodeType()
  {
    return AST_GET_TAG;
  }

  @Override
  public String getNodeDisplay()
  {
    if(hasNoNameSpace())
    {
      return key;
    }

    return namespace + ":" + key;
  }

  private boolean hasNoNameSpace()
  {
    return namespace == null || namespace.length() == 0;
  }

  /**
   * Get the namespace for the tag or null if it is a global tag.
   */
  public String getNamespace()
  {
    return namespace;
  }

  /**
   * Get the tag key.
   */
  public String getKey()
  {
    return key;
  }

  /**
   * Convert this expression to a GetRelationExpression with the same
   * namespace and key.
   *
   * @return Returns a GetRelationExpression with the same namespace and key
   *   as this expression.
   */
  public GetRelationExpression toRelation()
  {
    return new GetRelationExpression(namespace, key);
  }

  @Override
  public Collection<BIDataValue> evaluate(Entity entity, Context context)
  {
    Id id = NeqlUtil.getExpressionId(key, namespace, context);
    Optional<BIDataValue> tag = entity.tags().get(id);
    if(tag.isPresent())
    {
      return Collections.singletonList(tag.get());
    }

    return Collections.emptyList();
  }

  /**
   * Returns true if the entity contains a tag with this expression's tag id and that tag's value is
   * not BBoolean.FALSE.
   *
   * @param entity entity on which to search for the expression's tag id
   * @param context context that may contain the default namespace to form the tag id
   * @return true if the entity contains a tag with this expression's tag id and that tag's value is
   *   not BBoolean.FALSE
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    if (LEGACY_GET_TAG)
    {
      BIDataValue value = getValue(entity, context);
      return (value != null) && (value != BBoolean.FALSE);
    }
    else
    {
      Id id = getId(context);
      return entity.tags().get(id).isPresent();
    }
  }

  /**
   * If the entity has a tag with this expression's tag id, return that tag's value.
   *
   * @param entity entity on which to search for a tag with the expression's tag id
   * @param context context that may contain the default namespace to form the tag id
   * @return null if the entity does not contain a tag with the expression's tag id; otherwise,
   *   return the value of that tag
   * @since Niagara 4.9
   */
  BIDataValue getValue(Entity entity, Context context)
  {
    Id id = getId(context);
    Optional<BIDataValue> value = entity.tags().get(id);
    return value.isPresent() ? value.get() : null;
  }

  /**
   * @param context context that may contain the default namespace to form the tag id
   * @return expression's tag id, if fully specified; if the expression's namespace is not
   *   specified, look for one in the context
   * @since Niagara 4.9
   */
  private Id getId(Context context)
  {
    if (cachedId != null)
    {
      return cachedId;
    }

    String contextNamespace = (context != null) ? NeqlUtil.getNamespaceFromContext(context) : Id.NO_DICT;
    return Id.newId(contextNamespace, key);
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final String namespace;
  private final String key;
  private final Id cachedId;
  private static final boolean LEGACY_GET_TAG = AccessController.doPrivileged((PrivilegedAction<Boolean>)
    () -> Boolean.getBoolean("niagara.neql.legacyGetTagExpression"));
}