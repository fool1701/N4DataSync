/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

/**
 * AstNode is the base class for all nodes in the NEQL abstract syntax tree.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public abstract class AstNode
{
  /**
   * Get the integer id for the node type.  This is used to
   * streamline processing of the AST during query evaluation.
   *
   * @return The integer id of this node as defined in this class, AstNode.
   */
  public abstract int getNodeType();

  public String toString()
  {
    return getClass().getSimpleName() + ": " + getNodeDisplay();
  }

  protected String getNodeDisplay()
  {
    return "";
  }

///////////////////////////////////////////////////////////
// Node Types
///////////////////////////////////////////////////////////

  public static final int AST_SELECT           = 1;
  public static final int AST_PROJECTION       = 2;
  public static final int AST_GET_TAG          = 3;
  public static final int AST_GET_RELATION     = 4;
  public static final int AST_LITERAL          = 5;
  public static final int AST_AND              = 6;
  public static final int AST_OR               = 7;
  public static final int AST_EQUAL            = 8;
  public static final int AST_NOT_EQUAL        = 9;
  public static final int AST_LESS             = 10;
  public static final int AST_LESS_OR_EQUAL    = 11;
  public static final int AST_GREATER          = 12;
  public static final int AST_GREATER_OR_EQUAL = 13;
  public static final int AST_CONTEXT_EXPR     = 14;
  public static final int AST_NOT              = 15;
  public static final int AST_TRAVERSE         = 16;
  public static final int AST_TRAVERSE_IN      = 17;
  public static final int AST_TRAVERSE_OUT     = 18;
  public static final int AST_EVAL_ON          = 19;
  public static final int AST_LIKE             = 20;

  static final String OPERATOR_STR_AND = " and ";
  static final String OPERATOR_STR_OR = " or ";
  static final String OPERATOR_STR_EQUAL = " = ";
  static final String OPERATOR_STR_NOT_EQUAL = " != ";
  static final String OPERATOR_STR_LESS = " < ";
  static final String OPERATOR_STR_LESS_OR_EQUAL = " <= ";
  static final String OPERATOR_STR_GREATER = " > ";
  static final String OPERATOR_STR_GREATER_OR_EQUAL = " >= ";
  static final String OPERATOR_STR_LIKE = " like ";
}
