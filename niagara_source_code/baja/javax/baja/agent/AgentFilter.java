/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.sys.*;
import javax.baja.registry.*;
import javax.baja.security.*;
import java.util.function.Predicate;

/**
 * AgentFilter is used to filter an AgentList.  It also
 * provides factory methods for common agent filtering
 * functions.
 * 
 * @author    Brian Frank on 23 Dec 02
 * @version   $Revision: 3$ $Date: 5/12/04 4:11:16 PM EDT$
 * @since     Baja 1.0
 */
public abstract class AgentFilter
{  

////////////////////////////////////////////////////////////////
// Include
////////////////////////////////////////////////////////////////

  /**
   * Should the specified agent be included in 
   * the filtered agent list.
   */
  public abstract boolean include(AgentInfo agent);

////////////////////////////////////////////////////////////////
// IsFilter
////////////////////////////////////////////////////////////////          

  /**
   * Make a filter for the specified agent type.
   */
  public static AgentFilter is(String agentTypeSpec)
  {
    return is(Sys.getRegistry().getType(agentTypeSpec));
  }

  /**
   * Make a filter for the specified agent type.
   */
  public static AgentFilter is(Type type)
  {
    return is(type.getTypeInfo());
  }

  /**
   * Make a filter for the specified agent type.
   */
  public static AgentFilter is(TypeInfo typeInfo)
  {
    return new IsFilter(typeInfo);
  }
  
  static class IsFilter extends AgentFilter
  {
    IsFilter(TypeInfo t) { this.t = t; }
    @Override
    public final boolean include(AgentInfo agent) { return agent.getAgentType().is(t); }
    TypeInfo t;
  }
  
////////////////////////////////////////////////////////////////
// NotFilter
////////////////////////////////////////////////////////////////         

 /**
  * Make a filter for negating the specified filter
  * @since Niagara 3.8
  */
 public static AgentFilter not(AgentFilter filter)
 {
   return new NotFilter(filter);
 }
 
 /**
  * @since Niagara 3.8
  */ 
 static class NotFilter extends AgentFilter
 {
   NotFilter(AgentFilter t) { this.f = t; }
   @Override
   public final boolean include(AgentInfo agent) { return !f.include(agent); }
   AgentFilter f;
 }  

////////////////////////////////////////////////////////////////
// HasFilter
////////////////////////////////////////////////////////////////  

  /**
   * Create a filter which only includes types for which
   * {@code permissions.has(agentInfo.requiredPermissions)}
   */
  public static AgentFilter has(BPermissions permissions)
  {
    return new HasFilter(permissions);
  }
    
  static class HasFilter extends AgentFilter
  {
    HasFilter(BPermissions p) { this.p = p; }
    @Override
    public final boolean include(AgentInfo agent)
    { 
      try
      {
        return p.has(agent.getRequiredPermissions());
      }
      catch(Exception e)
      {
        e.printStackTrace();
        return false;
      }
    }
    BPermissions p;
  }

////////////////////////////////////////////////////////////////
// And Compound
////////////////////////////////////////////////////////////////  

  /**
   * Create a compound filter which performs an AND on 
   * the two specified agent filters.
   */
  public static AgentFilter and(AgentFilter f1, AgentFilter f2)
  {
    return new AndFilter(f1, f2);
  }

  /**
   * Convenience for {@code and(filter, has(permissions))}.
   */
  public static AgentFilter and(AgentFilter filter, BPermissions permissions)
  {
    return and(filter, has(permissions));
  }

  static class AndFilter extends AgentFilter
  {
    AndFilter(AgentFilter f1, AgentFilter f2) { this.f1 = f1;  this.f2 = f2; }
    @Override
    public final boolean include(AgentInfo agent)
    { 
      return f1.include(agent) && f2.include(agent);
    }
    AgentFilter f1, f2;
  }

////////////////////////////////////////////////////////////////
// Or Compound
////////////////////////////////////////////////////////////////  

  /**
   * Create a compound filter which performs an OR on 
   * the two specified agent filters.
   */
  public static AgentFilter or(AgentFilter f1, AgentFilter f2)
  {
    return new OrFilter(f1, f2, null, null);
  }

  /**
   * Create a compound filter which performs an OR on 
   * the three specified agent filters.
   */
  public static AgentFilter or(AgentFilter f1, AgentFilter f2, AgentFilter f3)
  {
    return new OrFilter(f1, f2, f3, null);
  }

  /**
   * Create a compound filter which performs an OR on 
   * the four specified agent filters.
   */
  public static AgentFilter or(AgentFilter f1, AgentFilter f2, AgentFilter f3, AgentFilter f4)
  {
    return new OrFilter(f1, f2, f3, f4);
  }

  static class OrFilter extends AgentFilter
  {
    OrFilter(AgentFilter f1, AgentFilter f2, AgentFilter f3, AgentFilter f4) 
    { 
      this.f1 = f1;  this.f2 = f2; this.f3 = f3;  this.f4 = f4; 
    }
    @Override
    public final boolean include(AgentInfo agent)
    { 
      if (f1 != null && f1.include(agent))
      {
        return true;
      }
      if (f2 != null && f2.include(agent))
      {
        return true;
      }
      if (f3 != null && f3.include(agent))
      {
        return true;
      }
      return f4 != null && f4.include(agent);
    }
    AgentFilter f1, f2, f3, f4;
  }

////////////////////////////////////////////////////////////////
// Predicate
////////////////////////////////////////////////////////////////

  /**
   * Returns this filter as a Predicate object.
   * @return A Predicate that wraps an AgentFilter.
   *
   * @since Niagara 4.0.
   */
  public Predicate<AgentInfo> toPredicate()
  {
    return this::include;
  }

  /**
   * An Agent Filter that doesn't filter anything.
   */
  public static final AgentFilter all = new AgentFilter()
  {
    @Override
    public boolean include(AgentInfo agent)
    {
      return true;
    }
  };
}
