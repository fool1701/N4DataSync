/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.space.BISpaceNode;
import javax.baja.space.BSpace;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIComparable;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import com.tridium.authn.AuthenticationClient;

/**
 * BOrd is an "Object Resolution Descriptor".  An ord is Baja's
 * universal identification system for integratating heteregeneous
 * naming systems into a single String.  An ord is composed of
 * one or more queries.  Each query has a scheme which identifies
 * how to parse and resolve the query into a BObject.
 *
 * <pre>
 * ord      := query (ws "|" ws query)*
 * query    := scheme ws ":" ws body
 * scheme   := alpha (alpha | digit)*
 * body     := bodyChar (bodyChar)*
 * alpha    := "a"-"z" | "A"-"Z"
 * digit    := "0"-"9"
 * bodyChar := 32 - 127 except "|"
 * ws       := (space)*
 * </pre>
 *
 * <p>
 * Ords can be relative or absolute.  An absolute ord usually
 * takes the general format of {@code host|session|space}.
 * The host query identifies a machine usually by an IP address
 * such as "ip:hostname".  The session is used to identify a
 * protocol being used to communicate with the host.  For example
 * "fox:" indicates a fox session to the host.  The space query
 * is used to identify a particular type of object.  Common spaces
 * are "module:", "file:", "station:", "spy:", or "history:".
 * <p>
 * The local VM is a special case identified by "local:" which
 * always resolves to BLocalHost.INSTANCE.  The local host is both
 * a host and a session (since no communication protocols are
 * required for access).
 * <p>
 * Components within a ComponentSpace can be named by both a slot
 * path and a handle.  So ords to a component usually involve both
 * a space query and a path/handle query.
 * <p>
 * Examples of ords:
 * <pre>
 * ip:somehost|fox:|station:|slot:/MyService
 * ip:somehost|fox:|station:|h:42
 * ip:somehost|fox:|file:/C:/dir/file.txt
 * local:|file:~etc/log.properties
 * local:|module://icons/x16/cloud.png
 * local:|spy:/
 * </pre>
 *
 * <p>
 * In Niagara you may view the complete list of installed ord
 * schemes at "spy:/sysManagers/registryManager/ordSchemes".
 *
 * @author    Brian Frank
 * @creation  15 Nov 02
 * @version   $Revision: 53$ $Date: 1/24/11 4:38:21 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BOrd
  extends BSimple
  implements BIComparable, BIAlias, BIDataValue
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make a BOrd from the specified string.
   */
  public static BOrd make(String s)
  {
    if (s.equals("null")) return NULL;
    return new BOrd(s);
  }

  /**
   * Create a new ord composed of the child appended to the base ord.
   *
   * @throws NullOrdException if this base or child is null
   */
  public static BOrd make(BOrd base, BOrd child)
  {
    if (base.toString().isEmpty()) return child;
    if (child.toString().isEmpty()) return base;

    if (base.isNull()) throw new NullOrdException("base");
    if (child.isNull()) throw new NullOrdException("child");

    return new BOrd(base.string + '|' + child.string);
  }

  /**
   * Create a new ord composed of the child appended to the base ord.
   *
   * @throws NullOrdException if this base or child is null
   */
  public static BOrd make(BOrd base, String child)
  {
    return make(base, make(child));
  }

  /**
   * Create a new ord composed of the child appended to the base ord.
   *
   * @throws NullOrdException if this base or child is null
   */
  public static BOrd make(BOrd base, OrdQuery child)
  {
    return make(base, make(child));
  }

  /**
   * Convenience for {@code make(new OrdQuery[] { query })}.
   */
  public static BOrd make(OrdQuery query)
  {
    return make(new OrdQuery[] { query });
  }

  /**
   * Convenience for {@code make(queries, 0, queries.length)}.
   */
  public static BOrd make(OrdQuery[] queries)
  {
    return make(queries, 0, queries.length);
  }

  /**
   * Get a ord from query list which spans the specified
   * start and end indices.
   *
   * @param start the beginning index, inclusive.
   * @param end the ending index, exclusive.
   *
   * @throws IllegalArgumentException if {@code start > end}
   */
  public static BOrd make(OrdQuery[] queries, int start, int end)
  {
    if (start > end) throw new IllegalArgumentException("start > end");

    StringBuilder s = new StringBuilder();
    for(int i=start; i<end; ++i)
    {
      OrdQuery q = queries[i];
      String body = q.getBody();

      if (i > start) s.append('|');

      // scheme:body
      s.append(q.getScheme()).append(':').append(q.getBody());
    }
    return new BOrd(s.toString());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BOrd(String string)
  {
    this.string = string;
  }

////////////////////////////////////////////////////////////////
// Resolve
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code resolve(BLocalHost.INSTANCE, null).get()}.
   */
  public BObject get()
  {
    return resolve(BLocalHost.INSTANCE, null).get();
  }

  /**
   * Convenience for {@code resolve(base, null).get()}.
   */
  public BObject get(BObject base)
  {
    return resolve(base).get();
  }

  /**
   * Convenience for {@code resolve(base, cx).get()}.
   */
  public BObject get(BObject base, Context cx)
  {
    return resolve(base, cx).get();
  }

  /**
   * Convenience for {@code resolve(BLocalHost.INSTANCE, null)}.
   */
  public OrdTarget resolve()
  {
    return resolve(BLocalHost.INSTANCE, null);
  }

  /**
   * Convenience for {@code resolve(base, null)}.
   */
  public OrdTarget resolve(BObject base)
  {
    return resolve(base, null);
  }

  /**
   * Attempt to resolve this BOrd to a BObject.  This
   * method recursively resolves the queries, passing
   * the result of one query to the next as the base.
   *
   * @throws NullOrdException if this is the null ord
   * @throws UnknownSchemeException if the ord contains
   *   a query scheme not registered in this system
   * @throws SyntaxException if the ord or a scheme specific
   *   query cannot be parsed due to invalid syntax
   * @throws UnresolvedException if the ord cannot be
   *   resolved to a BObject
   */
  public OrdTarget resolve(BObject base, Context cx)
          throws NullOrdException, UnknownSchemeException,
          SyntaxException, UnresolvedException
  {
    return resolve(base, cx, null);
  }
  public OrdTarget resolve(BObject base, Context cx, AuthenticationClient client)
    throws NullOrdException, UnknownSchemeException,
           SyntaxException, UnresolvedException
  {
    if (base == null)
    {
      base = BLocalHost.INSTANCE;
    }

    OrdQuery[] queries = parse();
    if (isNull() || queries.length == 0)
    {
      throw new NullOrdException();
    }

    try
    {
      // normalize first (using the Context parameter that indicates it's resolution time)
      OrdQueryList list = new OrdQueryList(queries);
      while(true)
      {
        if (!normalize(list, OrdQuery.RESOLVING_ORD_CX))
        {
          break;
        }
      }
      queries = list.toArray();

      // resolve each query
      OrdTarget target = new OrdTarget(cx, this, queries, base);
      for(int i=0; i<queries.length; ++i)
      {
        OrdQuery q = queries[i];
        BOrdScheme scheme = BOrdScheme.lookup(q.getScheme());
        target = scheme.resolve(target, q, client);
        if (target == null)
        {
          throw new UnresolvedException(string);
        }
      }
      return target;
    }
    catch(RuntimeException e)
    {
      //System.out.println("BOrd.resolve: " + string);
      throw e;
    }
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Attempt to get the host of the specified object
   * or return null if not applicable:
   * <ol>
   * <li>if object instanceof Host, return object</li>
   * <li>if object instanceof Session, return object.getHost()</li>
   * <li>if object instanceof Space, return object.getHost()</li>
   * <li>if object instanceof ISpaceNode, return object.getHost()</li>
   * </ol>
   */
  public static BHost toHost(BObject object)
  {
    if (object instanceof BHost) return (BHost)object;
    if (object instanceof BISession) return ((BISession)object).getHost();
    if (object instanceof BSpace) return ((BSpace)object).getHost();
    if (object instanceof BISpaceNode) return ((BISpaceNode)object).getHost();
    return null;
  }

  /**
   * Attempt to get the session of the specified object
   * or return null if not applicable:
   * <ol>
   * <li>if object instanceof Session, return object</li>
   * <li>if object instanceof Space, return object.getSession()</li>
   * <li>if object instanceof ISpaceNode, return object.getSession()</li>
   * </ol>
   */
  public static BISession toSession(BObject object)
  {
    if (object instanceof BISession) return (BISession)object;
    if (object instanceof BSpace) return ((BSpace)object).getSession();
    if (object instanceof BISpaceNode) return ((BISpaceNode)object).getSession();
    return null;
  }

  /**
   * Attempt to get the space of the specified object
   * or return null if not applicable:
   * <ol>
   * <li>if object instanceof Space, return object</li>
   * <li>if object instanceof ISpaceNode, return object.getSpace()</li>
   * </ol>
   */
  public static BSpace toSpace(BObject object)
  {
    if (object instanceof BSpace) return (BSpace)object;
    if (object instanceof BISpaceNode) return ((BISpaceNode)object).getSpace();
    return null;
  }

////////////////////////////////////////////////////////////////
// Parsing
////////////////////////////////////////////////////////////////

  /**
   * Parse the ord to return the list of queries.
   * <p>
   * Note that the parsed queries are *not* cached.  Therefore
   * this is a fairly expensive operation.  If resolving the
   * ord, then the queries are cached on the OrdTarget.
   *
   * @throws NullOrdException if this is the null ord
   * @throws SyntaxException if the ord contains invalid characters
   */
  public OrdQuery[] parse()
  {
    if (isNull())
    {
      throw new NullOrdException();
    }

    // allocate temporary list of queries
    OrdQuery[] temp = new OrdQuery[DEFAULT_ORD_QUERY_CAPACITY];
    int n =0;

    String string = this.string;
    int len = string.length();
    int lastPipe = 0;
    int lastNonSpace = 0;
    int colon = -1;
    for(int i=0; i<len; ++i)
    {
      int c = string.charAt(i);
      if (c < ' ')
      {
        throw new SyntaxException("Invalid ord char 0x" + Integer.toHexString(c));
      }
      if (c == ' ')
      {
        if (i == lastPipe)
        {
          lastPipe = i + 1;
        }
      }
      else if (c == '|')
      {
        if (n >= temp.length)
        {
          // If the number of OrdQueries in this ORD exceeds the DEFAULT_ORD_QUERY_CAPACITY,
          // we'll allow the array to grow in chunks of DEFAULT_ORD_QUERY_CAPACITY up to
          // the MAX_ORD_QUERY_CAPACITY. We could use ArrayList to handle all this for us,
          // but since this could be a hot path of code, doing it here reduces a
          // little overhead.
          int newCapacity =
            Math.min(temp.length + DEFAULT_ORD_QUERY_CAPACITY, MAX_ORD_QUERY_CAPACITY);
          if (newCapacity > temp.length)
          {
            OrdQuery[] newTemp = new OrdQuery[newCapacity];
            System.arraycopy(temp, 0, newTemp, 0, temp.length);
            temp = newTemp;
          }
        }

        temp[n++] = parse(string, lastPipe, lastNonSpace, colon);
        lastPipe = i+1;
        lastNonSpace = i+1;
        colon = -1;
      }
      else
      {
        if (c == ':' && colon < 0)
        {
          colon = i;
        }
        lastNonSpace = i;
      }
    }

    // add last query
    if (lastPipe < len)
    {
      if (n >= temp.length)
      {
        // If adding the last OrdQuery exceeds the current array capacity, we'll
        // allow the array to grow by 1 up to the MAX_ORD_QUERY_CAPACITY. We
        // could use ArrayList to handle all this for us, but since this could be
        // a hot path of code, doing it here reduces a little overhead.
        int newCapacity = Math.min(temp.length + 1, MAX_ORD_QUERY_CAPACITY);
        if (newCapacity > temp.length)
        {
          // If we get here, we can just create and return a new array
          // instance because we're on the last OrdQuery in the ORD anyways
          OrdQuery[] newTemp = new OrdQuery[newCapacity];
          System.arraycopy(temp, 0, newTemp, 0, temp.length);
          newTemp[n] = parse(string, lastPipe, lastNonSpace, colon);
          return newTemp;
        }
      }

      temp[n++] = parse(string, lastPipe, lastNonSpace, colon);
    }

    // copy temp into trimmed array
    OrdQuery[] trim = new OrdQuery[n];
    System.arraycopy(temp, 0, trim, 0, n);
    return trim;
  }

  /**
   * Parse a query.
   */
  static OrdQuery parse(String s, int lastPipe, int lastNonSpace, int colon)
  {
    if (colon < 0)
      throw new SyntaxException("Missing scheme name: " + s);

    String scheme = s.substring(lastPipe, colon);
    String body = s.substring(colon+1, lastNonSpace+1);
    return parse(scheme, body);
  }

  /**
   * Given a scheme and body instaniate an OrdQuery.  If the
   * given scheme can be mapped into a valid BOrdScheme then
   * this return {@code BOrdScheme.parse()}.  Otherwise
   * an instance of BasicQuery is returned.  Throws SyntaxException
   * if a ord scheme was found, but could not parse the query.
   */
  public static OrdQuery parse(String scheme, String body)
  {
    scheme = TextUtil.toLowerCase(scheme);
    body = body.trim();
    try
    {
      return BOrdScheme.lookup(scheme).parse(body);
    }
    catch(UnknownSchemeException e)
    {
      return new BasicQuery(scheme, body);
    }
    catch(SyntaxException e)
    {
      throw e;
    }
    catch(Throwable e)
    {
      throw new SyntaxException(e);
    }
  }

  /**
   * Get this's ord parent in a hierarchical naming system.
   * If the last query is a Path, then Path.getParentPath()
   * is used to construct the parent ord.  If a parent ord
   * cannot be determined then return null.
   */
  public BOrd getParent()
  {
    OrdQuery[] q = parse();

    // get last
    int lastIndex = q.length-1;
    if (lastIndex >= q.length || lastIndex < 0) return null;
    OrdQuery lastQuery = q[lastIndex];

    // if last is view, then get second to last
    if (lastQuery instanceof ViewQuery)
    {
      lastIndex--;
      if (lastIndex >= q.length || lastIndex < 0) return null;
      lastQuery = q[lastIndex];
    }

    // if last if not a Path, then cannot determine
    if (!(lastQuery instanceof Path)) return null;

    // get parent path
    Path path = (Path)lastQuery;
    Path parent = path.getParentPath();
    if (parent == null) return null;

    // return new ord
    q[lastIndex] = (OrdQuery)parent;
    return make(q, 0, lastIndex+1);
  }

////////////////////////////////////////////////////////////////
// Normalization & Relativization
////////////////////////////////////////////////////////////////

  /**
   * Normalize reformats the BOrd into its standardized form:
   * <ol>
   * <li>
   * This method recursively calls {@code OrdQuery.normalize()}
   * until no queries can normalize further.  This process allows
   * absolute and relative paths to be merged and truncated as
   * necessary.</li>
   * <li>
   * Whitespace is normalized so that no space is left between
   * pipes nor between schemes and bodies.</li>
   * </ol>
   */
  public BOrd normalize()
  {
    OrdQueryList list = new OrdQueryList(parse());
    while(true)
    {
      if (!normalize(list, null))
      {
        break;
      }
    }
    for(int i=0; i<list.size(); ++i)
    {
      BOrdScheme.lookup(list.get(i).getScheme());
    }
    return make(list.toArray());
  }

  /**
   * Relativize is used to extract the relative portion
   * of this ord within a host:
   * <ol>
   * <li>
   * First the ord is normalized.</li>
   * <li>
   * Starting from the left to right, if any queries are
   * found which return true for isHost(), then remove
   * everything from that query to the left.</li>
   * </ol>
   */
  public BOrd relativizeToHost()
  {
    OrdQueryList list = new OrdQueryList(parse());

    while(true)
    {
      if (!normalize(list, null))
      {
        break;
      }
    }

    for(int i=list.size()-1; i>=0; --i)
    {
      if (list.get(i).isHost())
      {
        list.trim(i+1);
        break;
      }
    }

    return make(list.toArray());
  }

  /**
   * Relativize is used to extract the relative portion
   * of this ord within an session:
   * <ol>
   * <li>
   * First the ord is normalized.</li>
   * <li>
   * Starting from the left to right, if any queries are
   * found which return true for isSession(), then remove
   * everything from that query to the left.</li>
   * </ol>
   */
  public BOrd relativizeToSession()
  {
    OrdQueryList list = new OrdQueryList(parse());

    while(true)
    {
      if (!normalize(list, null))
      {
        break;
      }
    }

    for(int i=list.size()-1; i>=0; --i)
    {
      if (list.get(i).isHost() || list.get(i).isSession())
      {
        list.trim(i+1);
        break;
      }
    }

    return make(list.toArray());
  }

  /**
   * Attempt to normalize queries.  Return true
   * if an ord query modifies the list.
   */
  private static boolean normalize(OrdQueryList list, Context cx)
  {
    list.modified = false;
    int size = list.size();
    for(int i=0; i<size; ++i)
    {
      OrdQuery q = list.get(i);
      q.normalize(list, i, cx);
      if (list.modified)
      {
        return true;
      }
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Substitute
////////////////////////////////////////////////////////////////

  /**
   * Return a new BOrd by substituting the given variable map
   * into this BOrd.  Those parts of this ord to be replaced
   * are marked with the format $({@code varName}), where
   * {@code varName} contains any letter or digit.
   * <p>
   * Examples:
   * <pre>{@code
   * $(foo)|slot:/a/b/c
   *    -> foo = "station:"
   *    -> station:|slot:/a/b/c
   *
   * $(foo)|slot:/a/b/c
   *    -> foo = "ip:somehost|fox:|station:"
   *    -> ip:somehost|fox:|station:|slot:/a/b/c
   *
   * slot:$(Vav1)/points/OATemp
   *    -> Vav1 = "/a/b/c"
   *    -> slot:/a/b/c/points/OATemp
   * }</pre>
   */
  public BOrd substitute(BFacets varMap) throws Exception
  {
    final BFacets f = varMap;
    final StringBuilder result = new StringBuilder();

    scanForVariables(new Scanner() {
      @Override
      public void handleVariable(String key)
      {
        String val = f.gets(key, null);
        if (val == null)
        {
          // if val not in facets, retain var
          result.append("$(").append(key).append(")");
        }
        else
        {
          // otherwise replace it with the facet value
          result.append(val);
        }
      }
      @Override
      public void appendChar(char ch) { result.append(ch); }
    });

    return BOrd.make(result.toString());
  }

  /**
   * Return whether these ord contains any variables.
   * Variables are marked with the format $({@code varName}),
   * where {@code varName} contains any letter or digit.
   * @since     Niagara 3.3
   */
  public boolean hasVariables()
  {
    final boolean[] result = new boolean[1];
    result[0] = false;

    scanForVariables(new Scanner() {
      @Override
      public void handleVariable(String key) { result[0] = true; }
      @Override
      public void appendChar(char ch) {}
    });

    return result[0];
  }

  /**
   * Return the names of all the variables contained in this ord,
   * or an empty array if there are no variables.
   * @since     Niagara 3.3
   */
  public String[] getVariables()
  {
    final List<String> arr = new ArrayList<String>();

    scanForVariables(new Scanner() {
      @Override
      public void handleVariable(String key) { arr.add(key); }
      @Override
      public void appendChar(char ch) {}
    });

    return arr.toArray(new String[arr.size()]);
  }

  /**
   * Scanner
   */
  private interface Scanner
  {
    void handleVariable(String key);
    void appendChar(char ch);
  }

  /**
   * scanForVariables
   */
  private void scanForVariables(Scanner scanner)
  {
    StringBuilder temp = new StringBuilder();
    int len = string.length();

    for (int i=0; i<len; i++)
    {
      int curr = string.charAt(i);
      int peek = (i < len-1) ? string.charAt(i+1) : -1;

      if (curr == '$' && peek == '(')
      {
        temp.setLength(0);
        i += 2;
        if (i >= len) throw new SyntaxException("Missing closing paren");
        curr = string.charAt(i);

        while (curr != ')')
        {
          if (i >= len-1) throw new SyntaxException("Missing closing paren");

          boolean valid = false;
          if (curr >= 'A' && curr <= 'Z') valid = true;
          else if (curr >= 'a' && curr <= 'z') valid = true;
          else if (curr >= '0' && curr <= '9') valid = true;

          if (!valid)
            throw new SyntaxException("Illegal character in variable name: '"
              + (char)curr + "'");

          temp.append((char)curr);
          curr = string.charAt(++i);
        }

        String key = temp.toString();
        if (key.isEmpty()) throw new SyntaxException("Empty variable name");

        scanner.handleVariable(key);
      }
      else scanner.appendChar((char)curr);
    }
  }

////////////////////////////////////////////////////////////////
// Sub Ords
////////////////////////////////////////////////////////////////

  /**
   * Get a sub-ord from this BOrd which spans from
   * the specified start query index to the end of
   * the ord.  This is a convenience method for
   * {@code make(this, start, getQueryDepth())}.
   *
   * @param start the beginning index, inclusive.
   */
  public final BOrd getSubOrd(int start)
  {
    OrdQuery[] q = parse();
    return make(q, start, q.length);
  }

  /**
   * Get a sub-ord from this BOrd which spans the specified
   * start and end indices.  This is a convenience method for
   * {@code make(this, start, end)}.
   *
   * @param start the beginning index, inclusive.
   * @param end the ending index, exclusive.
   */
  public final BOrd getSubOrd(int start, int end)
  {
    OrdQuery[] q = parse();
    return make(q, start, end);
  }

////////////////////////////////////////////////////////////////
// BIAlias
////////////////////////////////////////////////////////////////
  
  @Override
  public BOrd getOrd()
  {
    return this;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Return if this is the null BOrd.
   */
  @Override
  public boolean isNull()
  {
    return this == NULL;
  }

  /**
   * BOrd uses its lower case version of the String's hash code.
   */
  @Override
  public int hashCode()
  {
    if (hashCode == -1) hashCode = TextUtil.toLowerCase(string).hashCode();
    return hashCode;
  }

  /**
   * BOrd equality is based on String value equality.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BOrd)
    {
      return string.equals( ((BOrd)obj).string );
    }
    return false;
  }

  /**
   * Comparision is based on value of String.
   */
  @Override
  public int compareTo(Object obj)
  {
    return string.compareTo( ((BOrd)obj).string );
  }

  /**
   * To string method.
   */
  @Override
  public String toString(Context context)
  {
    return string;
  }

  /**
   * BOrd is encoded as using writeUTF().
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(string);
  }

  /**
   * BOrd is decoded using readUTF().
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }

  /**
   * Write the simple in text format.
   */
  @Override
  public String encodeToString()
  {
    return string;
  }

  /**
   * Read the simple from text format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }

  @Override
  public BIDataValue toDataValue()
  {
    return this;
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  /**
   * The null BOrd is encoded as "null".
   */
  public static final BOrd NULL = new BOrd("null");

  /**
   * The default is NULL.
   */
  public static final BOrd DEFAULT = NULL;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrd.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final int DEFAULT_ORD_QUERY_CAPACITY = 32;
  private static final int MAX_ORD_QUERY_CAPACITY =
    Integer.MAX_VALUE - DEFAULT_ORD_QUERY_CAPACITY;

  int hashCode = -1;
  String string;

}

