/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.bql;

import javax.baja.naming.*;
import javax.baja.sys.*;
import com.tridium.collection.*;
import com.tridium.bql.compiler.*;

/**
 * BqlQuery is a query specified using the Baja Query Language.
 *
 * @author    John Sublett
 * @creation  26 Mar 2002
 * @version   $Revision: 15$ $Date: 6/24/10 3:13:25 PM EDT$
 * @since     Baja 1.0
 */
public abstract class BqlQuery
  implements OrdQuery
{
  
  public BqlQuery()
  {
    scheme = "bql";
  }
  
///////////////////////////////////////////////////////////
// Factory
///////////////////////////////////////////////////////////

  /**
   * This is a factory method for creating a BQuery from BQL text.
   */

  public static BqlQuery make(String bqlText)
  {
    return make("bql", bqlText);
  }
  
  public static BqlQuery make(String scheme, String bqlText)
  {
    if ((bqlText == null) || (bqlText.length() == 0))
    {
      BqlQuery result = new EmptyBqlQuery();
      result.body = "";
      result.scheme = scheme;
      return result;
    }

    String unescaped = SlotPath.unescape(bqlText);    
    BqlTokenizer tokens = new BqlTokenizer(unescaped);
    try
    {
      BqlParser parser = new BqlParser();
      BqlQuery result = parser.parse(tokens);
      result.body = bqlText;
      result.unescaped = unescaped;
      result.scheme = scheme;

      return result;
    }
    catch(RuntimeCompilerException e)
    {
//      System.out.println(e.toDisplay());
      throw e;
    }
  }

////////////////////////////////////////////////////////////////
// Eval
////////////////////////////////////////////////////////////////

  /**
   * Get the result of this query based on the specified base.
   */
  public abstract OrdTarget resolve(OrdTarget base);

////////////////////////////////////////////////////////////////
// OrdQuery
////////////////////////////////////////////////////////////////

  /**
   * Get the ord scheme.
   */
  @Override
  public String getScheme()
  {
    return scheme;
  }
 
  /**
   * Get the body text.
   */
  @Override
  public String getBody()
  {
    return body;
  }

  public String getUnescaped()
  {
    if (unescaped == null)
    {
      if (body == null)
        unescaped = "";
      else
        unescaped = SlotPath.unescape(body);
    }
    return unescaped;
  }

  protected void setBody(String body)
  {
    this.body = body;
  }
  
  /**
   * Return false.
   */
  @Override
  public boolean isHost()
  {
    return false;
  }

  /**
   * Return false.
   */
  @Override
  public boolean isSession()
  {
    return false;
  }

  /**
   * Normalization does not alter a BQL ord.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
  }

  public String toString()
  {
    return getScheme() + ":" + getBody();
  }

///////////////////////////////////////////////////////////
// EmptyQuery
///////////////////////////////////////////////////////////

  private static class EmptyBqlQuery
    extends BqlQuery
  {
    @Override
    public OrdTarget resolve(OrdTarget base)
    {
      return new OrdTarget(base, new BEmptyTable());
    }
  }
  
///////////////////////////////////////////////////////////
// Util
///////////////////////////////////////////////////////////

  /**
   * Create a string representing the BQL literal for the given BSimple if it
   * were to be used in an expression in a bql query. The table below is just
   * meant to give some examples of expected output.
   * <p>
   * In BQL, simple types from the baja module do not need to be prefixed with
   * "baja:", and this method will omit the baja prefix. 
   * <p>
   * Also, the string encoding of the given BSimple will be escaped using
   * {@link javax.baja.naming.SlotPath#escape(String) SlotPath.escape(String)}.
   * This means it is already valid for use in constructing a BOrd.
   * 
   * <p>
   * <table border='1' summary="BQL Literals for Simples and their shorthand equivalents">
   * <tr>
   * <th align='left'>BSimple</th>
   * <th>BQL Literal</th>
   * <th>BQL Shorthand
   * </tr>
   * <tr>
   * <td><code>BLong.make(1)</code></td>
   * <td><code>Long '1'</code></td>
   * <td><code>1</code>
   * </tr>
   * <tr>
   * <td><code>BAckState.acked</code></td>
   * <td><code>alarm:AckState 'acked'</code></td>
   * <td><code>alarm:AckState.acked</code></td>
   * </tr>
   * <tr>
   * <td><code>BUuid.make("568aa62c-4f45-4287-be3c-b136e9bb8171")</code></td>
   * <td><code>Uuid '568aa62c-4f45-4287-be3c-b136e9bb8171'</code></td>
   * <td>No shorthand supported; use bql literal.</td>
   * </tr>
   * <tr>
   * <td><code>BOrdList.make("local:|station:")</code></td>
   * <td><code>OrdList 'local$3a$7cstation$3a'</code></td>
   * <td>No shorthand supported; use bql literal.</td>
   * </tr>
   * </table>
   * <p>
   * Note: In BQL, the string encoding of a BSimple is the value returned by
   * <code>s.encodeToString()</code>. If this method throws an exception, then
   * <code>s.toString()</code> will be used as the string encoding for the given
   * BSimple.
   * 
   * @param s
   *          the BSimple to encode to a BQL literal.
   * @return a String that is the BQL literal representation of the given
   *         BSimple. This value is already escaped and ready for use in
   *         constructing a BOrd.
   * 
   * @since Niagara 3.5
   */
  public static String toBqlLiteral(BSimple s)
  {
    String encoded = null;
    // In BQL, a type from the baja module does not need to be prefixed
    // with "baja:"
    String type = s.getType().getModule().getModuleName().equals("baja")
      ? s.getType().getTypeName()
      : s.getType().toString();
    try
    {
      encoded = s.encodeToString();
    }
    catch (Exception x) 
    {
      encoded = s.toString();
    }
    return new StringBuilder().append(type)
      .append(" '").append(SlotPath.escape(encoded)).append("'").toString();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected String scheme;
  private String body;
  private String unescaped;
}
