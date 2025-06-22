/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.neql;

import com.tridium.neql.NeqlParser;
import com.tridium.neql.NeqlTokenizer;
import javax.baja.naming.BasicQuery;

/**
 * A NeqlQuery is an OrdQuery for the neql scheme which is the Niagara Entity Query Language.
 *
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
public class NeqlQuery
  extends BasicQuery
{
  public NeqlQuery(String queryText)
  {
    super(BNeqlScheme.INSTANCE.getId(), queryText);
    ast = new NeqlParser().parse(new NeqlTokenizer(queryText));
  }

  /**
   * Get the abstract syntax tree for the parsed query.
   *
   * @return Returns the root of the abstract syntax tree.
   */
  public AstNode getAst()
  {
    return ast;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  /** The abstract syntax tree of the parsed query text. */
  private AstNode ast;
}