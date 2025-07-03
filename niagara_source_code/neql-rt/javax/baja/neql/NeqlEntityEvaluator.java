/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javax.baja.data.BIDataValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFloat;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BLong;
import javax.baja.sys.BNumber;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;

import com.tridium.neql.ExprParser;
import com.tridium.neql.NeqlTokenizer;
import com.tridium.neql.NeqlUtil;

/**
 * NeqlEntityEvaluator a simple evaluator that evaluates a neql expression
 * against an Entity.
 *
 * @author John Sublett
 * @creation 6/4/14
 * @since Niagara 4.0
 */
public class NeqlEntityEvaluator
{
  public NeqlEntityEvaluator()
  {

  }

///////////////////////////////////////////////////////////
// Predicate
///////////////////////////////////////////////////////////

  /**
   * Make a predicate from the specified neql expression.
   *
   * @param expression A neql boolean expression.
   * @return Returns a predicate for testing entities.
   */
  public static Predicate<Entity> makePredicate(String expression)
  {
    return makePredicate(expression, null);
  }

  public static Predicate<Entity> makePredicate(String expression, Context cx)
  {
    ExprParser parser = new ExprParser();
    Expression expr = parser.parse(new NeqlTokenizer(expression));
    return new EntityNeqlPredicate(expr, cx);
  }

  /**
   * EntityNeqlPredicate is a predicate for testing a boolean
   * neql expression against an Entity.
   */
  private static final class EntityNeqlPredicate
      implements Predicate<Entity>
  {
    private EntityNeqlPredicate(Expression expression, Context cx)
    {
      evaluator = new NeqlEntityEvaluator();
      this.context = cx;
      this.expression = expression;
    }

    @Override
    public boolean test(Entity entity)
    {
      return evaluator.evalBoolean(expression, entity, context);
    }

    private final NeqlEntityEvaluator evaluator;
    private final Expression expression;
    private final Context context;
  }

///////////////////////////////////////////////////////////
// Eval
///////////////////////////////////////////////////////////

  /**
   * Evaluate a boolean expression.
   *
   * @param context           A context with the collection of parameters
   *                          used to evaluate the expression.
   * @param contextEntity     The context entity against which context expressions are
   *                          evaluated.
   * @param entity            The entity to evaluate the expression against.
   * @param booleanExpression The expression to evaluate.
   * @return The primitive boolean value for the given entity and boolean expression
   * @deprecated since Niagara 4.4. Will be removed in a future release. Use
   *   {@link NeqlEntityEvaluator#booleanEvalAll(Context, Entity, Expression)} instead.
   */
  @Deprecated
  public boolean booleanEval(Context context,
                             Entity contextEntity,
                             Entity entity,
                             Expression booleanExpression)
  {
    Object result = eval(context, contextEntity, entity, booleanExpression);
    if (result == null)
      return false;
    else if (result instanceof BIBoolean)
      return ((BIBoolean)result).getBoolean();
    else if (result instanceof Boolean)
      return ((Boolean) result);
    else
      return true;
  }

  /**
   * Evaluate a boolean expression.
   *
   * @param context The context to consider when evaluating this expression for this
   *   entity
   * @param entity The entity to evaluate the expression against.
   * @param booleanExpression The expression to evaluate.
   * @since Niagara 4.4
   * @return A collection of booleans for the given entity and boolean expression
   * @deprecated {@link NeqlEntityEvaluator#evalBoolean(Expression, Entity, Context)} should be used
   *   instead to determine whether a boolean expression is true for a given entity and context.
   *   evalBoolean will return as soon as a result is determined instead of evaluating the entire
   *   expression
   */
  @Deprecated
  public Collection<BBoolean> booleanEvalAll(Context context, Entity entity, Expression booleanExpression)
  {
    Iterator<?> booleanValues = evalAll(context, entity, booleanExpression).iterator();

    Collection<BBoolean> booleanCollection = new LinkedList<>();

    while(booleanValues.hasNext())
    {
      Object current = booleanValues.next();
      if (current instanceof BBoolean)
      {
        booleanCollection.add(((BBoolean) current));
      }
      else if (current instanceof Boolean)
      {
        booleanCollection.add(BBoolean.make((Boolean) current));
      }
      else
      {
        booleanCollection.add(BBoolean.TRUE);
      }
    }

    return booleanCollection;
  }

  /**
   * Evaluate an expression against an entity. This method will only return 1 value as a result. If
   * the given entity has multiple relations to traverse, it will return the first matching relation
   * and ignore all others. When multiple relations of the same type are expected, use
   * {@link NeqlEntityEvaluator#evalAll(Context, Entity, Expression)}
   *
   * @param context       A context with the collection of parameters used to
   *                      evaluate the expression.
   * @param contextEntity The context entity against which context expressions
   *                      are evaluated.
   * @param entity        The entity to evaluate the expression against.
   * @param expression    The expression to evaluate.
   * @return              1 of Entity, Relation, or BIDataValue (depending on the query)
   * @deprecated since Niagara 4.4. Will be removed in a future release.
   *   Use @link{NeqlEntityEvaluator#evalAll(Context, Entity, Expression)} instead.
   */
  @Deprecated
  public Object eval(Context context,
                     Entity contextEntity,
                     Entity entity,
                     Expression expression)
  {
    // short circuit if a previous eval came up null
    if (entity == null)
      return null;
    Expression left;
    Expression right;
    BIDataValue leftValue;
    BIDataValue rightValue;

    int nodeType = expression.getNodeType();
    switch (nodeType)
    {
      // Get a tag from the entity
      case AstNode.AST_GET_TAG:
        String namespace = ((GetTagExpression) expression).getNamespace();
        if ((namespace == null) || namespace.isEmpty())
          namespace = NeqlUtil.getNamespaceFromContext(context);
        String key = ((GetTagExpression) expression).getKey();
        Optional<BIDataValue> tag = entity.tags().get(Id.newId(namespace, key));
        return tag.isPresent() ? tag.get() : null;

      // Get a relation from the entity
      case AstNode.AST_GET_RELATION:
        namespace = ((GetRelationExpression) expression).getNamespace();
        if ((namespace == null) || namespace.isEmpty())
          namespace = NeqlUtil.getNamespaceFromContext(context);
        key = ((GetRelationExpression) expression).getKey();
        Optional<Relation> orelation = entity.relations().get(Id.newId(namespace, key));
        return orelation.isPresent() ? orelation.get() : null;

      // Return the literal value
      case AstNode.AST_LITERAL:
        return ((LiteralExpression) expression).getValue();

      // Evaluate a logical expression
      case AstNode.AST_AND:
      case AstNode.AST_OR:
        left = ((BinaryExpression) expression).getLeft();
        right = ((BinaryExpression) expression).getRight();
        boolean leftBool = booleanEval(context, contextEntity, entity, left);
        if (nodeType == AstNode.AST_AND)
        {
          return leftBool ? BBoolean.make(booleanEval(context, contextEntity, entity, right)) : BBoolean.FALSE;
        }
        else // if (nodeType == AstNode.AST_OR)
        {
          return leftBool ? BBoolean.TRUE : BBoolean.make(booleanEval(context, contextEntity, entity, right));
        }

        // Evaluate a not expression
      case AstNode.AST_NOT:
        right = ((NotExpression) expression).getExpression();
        return booleanEval(context, contextEntity, entity, right) ? BBoolean.FALSE : BBoolean.TRUE;

      // Evaluate an equal or not equal expression
      case AstNode.AST_EQUAL:
      case AstNode.AST_NOT_EQUAL:
        left = ((BinaryExpression) expression).getLeft();
        right = ((BinaryExpression) expression).getRight();
        leftValue = (BIDataValue) eval(context, contextEntity, entity, left);
        rightValue = (BIDataValue) eval(context, contextEntity, entity, right);
        if ((leftValue == null) || (rightValue == null))
          return BBoolean.FALSE;
        return nodeType == AstNode.AST_EQUAL ? BBoolean.make(equals(leftValue, rightValue)) : BBoolean.make(!equals(leftValue, rightValue));

      // Evaluate a comparison operator other than equal or not equal
      case AstNode.AST_LESS:
      case AstNode.AST_LESS_OR_EQUAL:
      case AstNode.AST_GREATER:
      case AstNode.AST_GREATER_OR_EQUAL:
        left = ((BinaryExpression) expression).getLeft();
        right = ((BinaryExpression) expression).getRight();
        leftValue = (BIDataValue) eval(context, contextEntity, entity, left);
        rightValue = (BIDataValue) eval(context, contextEntity, entity, right);
        if ((leftValue instanceof BIComparable) && (rightValue instanceof BIComparable))
        {
          int compVal = 0;
          compVal = (leftValue instanceof BNumber) && (rightValue instanceof BNumber) ?
              compareNumbers((BNumber) leftValue, (BNumber) rightValue) :
              ((BIComparable) leftValue).compareTo(rightValue);

          if (nodeType == AstNode.AST_LESS)
            return BBoolean.make(compVal == -1);
          else if (nodeType == AstNode.AST_LESS_OR_EQUAL)
            return BBoolean.make(compVal <= 0);
          else if (nodeType == AstNode.AST_GREATER)
            return BBoolean.make(compVal == 1);
          else // if (nodeType == AstNode.AST_GREATER_OR_EQUAL)
            return BBoolean.make(compVal >= 0);
        }
        else
          return BBoolean.FALSE;

        // Evaluate an expression against the context instead of the entity
      case AstNode.AST_CONTEXT_EXPR:
        if (context == null)
          return BBoolean.FALSE;
        else
        {
          GetTagExpression inner = (GetTagExpression) ((ContextExpression) expression).getExpression();
          key = inner.getKey();
          return context.getFacets().getFacet(key);
        }

      case AstNode.AST_EVAL_ON:
        left = ((EvalOnExpression) expression).getExpression();
        right = ((EvalOnExpression) expression).getTarget();
        Entity target = (Entity) eval(context, contextEntity, entity, right);
        return eval(context, contextEntity, target, left);

      case AstNode.AST_TRAVERSE_OUT:
        right = ((TraverseOutExpression) expression).getExpression();
        Relation relation = (Relation) eval(context, contextEntity, entity, right);
        return relation != null && relation.isOutbound() ? relation.getEndpoint() : null;

      case AstNode.AST_TRAVERSE_IN:
        right = ((TraverseInExpression) expression).getExpression();
        relation = (Relation) eval(context, contextEntity, entity, right);
        return relation != null && relation.isInbound() ? relation.getEndpoint() : null;

      // eval the like expression
      case AstNode.AST_LIKE:
        left = ((LikeExpression) expression).getLeft();
        right = ((LikeExpression) expression).getRight();
        leftValue = (BIDataValue) eval(context, contextEntity, entity, left);
        rightValue = (BIDataValue) eval(context, contextEntity, entity, right);
        if ((leftValue == null) || (rightValue == null) || !(rightValue instanceof BString))
          return BBoolean.FALSE;
        try
        {
          if (likeEval(BString.make(leftValue.encodeToString()), rightValue))
            return BBoolean.TRUE;
        } catch (Exception ignore)
        {
        }
        return BBoolean.FALSE;

      default:
        throw new UnsupportedOperationException("Unhandled expression type: " + nodeType);
    }
  }

  /**
   * Evaluate an expression against an entity.
   *
   * @param context       The context to consider when evaluating this expression for this entity
   * @param entity        The entity to evaluate the expression against.
   * @param expression    The expression to evaluate.
   * @since Niagara 4.4
   * @return A collection of Entities, BIDataValues, Relations, Booleans, or an empty collection
   *   (depending on the NEQL query).
   */
  public Collection<?> evalAll(Context context, Entity entity, Expression expression)
  {
    // short circuit if a previous evalAll came up null
    if (entity == null)
      return Collections.emptyList();

    return expression.evaluate(entity, context);
  }

  /**
   * Returns a result for the specified boolean expression as soon one can be determined.
   *
   * @param expression boolean expression to be evaluated
   * @param entity entity on which to evaluate the expression
   * @param context context to use while evaluating the expression
   * @return false if the entity is null or true or false as soon as a result can be determined
   * @throws UnsupportedOperationException if the expression is not supported
   * @since Niagara 4.9
   */
  public boolean evalBoolean(Expression expression, Entity entity, Context context)
  {
    if (entity == null)
    {
      return false;
    }

    switch (expression.getNodeType())
    {
      case AstNode.AST_GET_TAG:
      case AstNode.AST_GET_RELATION:
      case AstNode.AST_LITERAL:
      case AstNode.AST_AND:
      case AstNode.AST_OR:
      case AstNode.AST_NOT:
      case AstNode.AST_EQUAL:
      case AstNode.AST_NOT_EQUAL:
      case AstNode.AST_LESS:
      case AstNode.AST_LESS_OR_EQUAL:
      case AstNode.AST_GREATER:
      case AstNode.AST_GREATER_OR_EQUAL:
      case AstNode.AST_LIKE:
      case AstNode.AST_CONTEXT_EXPR:
      case AstNode.AST_TRAVERSE_OUT:
      case AstNode.AST_TRAVERSE_IN:
      case AstNode.AST_EVAL_ON:
        return expression.evalBoolean(entity, context);

      default:
        throw new LocalizableRuntimeException("neql", "neqlEntityEvaluator.evalBoolean.invalidExpression", new Object[] {expression.getClass().getSimpleName()});
    }
  }

  /**
   * Test two BIDataValues for equality
   * If both are null return true;
   * If either one is null return false.
   * If both are numbers compare as numbers
   * if Left is BBoolean and right is BString
   * Convert BBoolean to "true" or "false" and compare to right String value.
   * Otherwise
   * Convert both left and right to String using encodeToString() and compare results.
   **/
  private static boolean equals(BIDataValue left, BIDataValue right)
  {
    if (left instanceof BNumber)
    {
      return right instanceof BNumber && compareNumbers((BNumber)left, (BNumber)right) == 0;
    }
    else if (left == null && right == null)
      return true;
    else if (left == null || right == null)
      return false;
    if (left instanceof BBoolean && right instanceof BString)
    {
      String leftStr = ((BBoolean)left).getBoolean() ? "true" : "false";
      return leftStr.equals(right.toString());
    }
    else if (left.getType().is(right.getType()))
    {
      return left.equals(right);
    }
    else
    {
      try
      {
        return left.encodeToString().equals(right.encodeToString());
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return false;
  }

  private static int compareNumbers(BNumber left, BNumber right)
  {
    if (left instanceof BDouble || right instanceof BDouble)
    {
      return Double.compare(left.getDouble(), right.getDouble());
    }
    else if (left instanceof BFloat || right instanceof BFloat)
    {
      return Float.compare(left.getFloat(), right.getFloat());
    }
    else if (left instanceof BLong || right instanceof BLong)
    {
      return Long.compare(left.getLong(), right.getLong());
    }
    else
    {
      return Integer.compare(left.getInt(), right.getInt());
    }
  }

  /**
   * like: implementation uses pattern caching.
   */
  public boolean likeEval(BIDataValue left, BIDataValue right)
  {
    if (left instanceof BString && right instanceof BString)
    {
      return Pattern.matches(((BString)right).getString(), ((BString)left).getString());
    }
    return false;
  }
}
