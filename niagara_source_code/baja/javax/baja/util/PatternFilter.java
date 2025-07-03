/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.baja.file.BIFile;
import javax.baja.file.IFileFilter;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IFilter;
import javax.baja.sys.Context;

/**
 * PatternFileFilter provides simple DOS like pattern
 * file matching where * represents zero or more wildcard
 * characters and ? represents one wildcard character.
 *
 * @author    Brian Frank
 * @creation  20 Dec 03
 * @version   $Revision: 4$ $Date: 9/17/08 12:00:27 PM EDT$
 * @since     Baja 1.0
 */
public class PatternFilter
  implements IFilter, IFileFilter
{

////////////////////////////////////////////////////////////////
// Lists
////////////////////////////////////////////////////////////////

  /**
   * Parse a list of patterns using the specified separator
   * using StringTokenizer semantics.
   */
  public static PatternFilter[] parseList(String listOfPatterns, String separators)
  {
    ArrayList<PatternFilter> list = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(listOfPatterns, separators);
    while(st.hasMoreTokens())
      list.add(new PatternFilter(st.nextToken()));
    return list.toArray(new PatternFilter[list.size()]);
  }

  /**
   * Convenience for <code>parseList(listOfPatterns, " \t,;")</code>.
   */
  public static PatternFilter[] parseList(String listOfPatterns)
  {
    return parseList(listOfPatterns, " \t,;");
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor which takes a pattern.
   */
  public PatternFilter(String pattern)
  {
    this.pattern = pattern;
    parse();
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the pattern passed to the constructor.
   */
  public String getPattern()
  {
    return pattern;
  }

  /**
   * Get the pattern passed to the constructor.
   */
  @Override
  public String getDescription(Context cx)
  {
    return pattern;
  }

  /**
   * Return <code>accept(file.getFileName())</code>
   */
  @Override
  public boolean accept(BIFile file)
  {
    return accept(file.getFileName());
  }

  /**
   * Return <code>accept(object.toString())</code>
   */
  @Override
  public boolean accept(Object object)
  {
    return accept(object.toString());
  }

  /**
   * Return if the specified string matches the pattern.
   */
  public boolean accept(String string)
  {
    Array<State> curStates = new Array<>(State.class);
    curStates.push(start);
    char[] buf = string.toCharArray();
    for (int i=0; i<buf.length; ++i)
    {
      Array<State> nextStates = new Array<>(State.class);
      while (!curStates.isEmpty())
        nextStates.addAll((curStates.pop()).transition(buf[i]));

      if (nextStates.isEmpty())
        return false;

      curStates = nextStates;
    }
    return curStates.contains(accept);
  }

  /**
   * Does this pattern include wildcard characters (? or *)?
   */
  public boolean hasWildChars()
  {
    return pattern.indexOf('*') != -1 || pattern.indexOf('?') != -1;
  }

////////////////////////////////////////////////////////////////
// Parse
////////////////////////////////////////////////////////////////

  private void parse()
  {
    State cur = start = new State();
    char[] buf = pattern.toCharArray();
    for (int i=0; i<buf.length; ++i)
    {
      final char c = buf[i];
      if (c == '*')
      {
        // skip consecutive wildcards
        if (i > 0 && buf[i-1] == '*')
          continue;
        cur.addTransition(Condition.any(), cur);
      }
      else if (c == '?')
        cur = cur.addTransition(Condition.any(), new State());
      else
        cur = cur.addTransition(Condition.exact(c), new State());
    }
    accept = cur;
  }

  private class State
  {
    public State addTransition(Condition c, State next)
    {
      transitions.put(c, next);
      return next;
    }

    /**
     * Return the states that are reachable by consuming the given char.
     */
    public State[] transition(final char c)
    {
      Array<State> outStates = new Array<>(State.class);
      for (Iterator<Condition> iter = transitions.keySet().iterator(); iter.hasNext(); )
      {
        Condition cond = iter.next();
        if (cond.match(c))
          outStates.add(transitions.get(cond));
      }
      return outStates.trim();
    }

    private Map<Condition, State> transitions = new HashMap<>();
  }

  private static abstract class Condition
  {
    public abstract boolean match(final char test);

    public static Condition exact(final char c)
    {
      return new Condition()
      {
        @Override
        public boolean match(final char test)
        {
          return c == test;
        }
      };
    }

    public static Condition any()
    {
      return new Condition()
      {
        @Override
        public boolean match(final char test)
        {
          return true;
        }
      };
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private State start;
  private State accept;
  private String pattern;
}

