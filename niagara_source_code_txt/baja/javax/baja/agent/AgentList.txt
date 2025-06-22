/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * AgentList encapsulates an ordered list of agent AgentInfos.
 * 
 * @author    Brian Frank on 23 Dec 02
 * @version   $Revision: 8$ $Date: 3/28/05 9:22:53 AM EST$
 * @since     Baja 1.0
 */
public interface AgentList extends Iterable<AgentInfo>
{        

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the list size.
   */
  int size();
  
  /**
   * Get the default agent which is the agent at index zero.  If 
   * the agent list is a size of zero, then throw NoSuchAgentException.
   */
  AgentInfo getDefault();
  
  /**
   * Get the agent info at the specified index.
   */
  AgentInfo get(int index);

  /**
   * Get the agent info by the specified id or 
   * return null if no agent in list by given id.
   */
  AgentInfo get(String id);
  
  /**
   * Get the list of all agents.
   */
  AgentInfo[] list();
 
  /**
   * Get the index of the specified agent id or 
   * return -1 if no matching id in list.
   */
  int indexOf(String id);

  /**
   * Get the index of the specified AgentInfo or 
   * return -1 if the AgentInfo is not in the list.
   */
  int indexOf(AgentInfo info);
  
  /**
   * Make a copy of this AgentList.
   */
  Object clone();

////////////////////////////////////////////////////////////////
// Filter
////////////////////////////////////////////////////////////////

  /**
   * Run the specified filter against this agent list to
   * produce a new agent list which only includes agent types 
   * which return true for {@code filter.include()}.
   */
  AgentList filter(AgentFilter filter);

  /**
   * Run the specified predicate against this agent list to
   * produce a new agent list which only includes agent types.
   *
   * @param predicate The predicate used to filter the agent list.
   * @return The filtered agent list.
   *
   * @since Niagara 4.0
   */
  AgentList filter(Predicate<AgentInfo> predicate);
  
////////////////////////////////////////////////////////////////
// Add
////////////////////////////////////////////////////////////////  

  /**
   * Convenience for {@code add(registry.getType(typeSpec).getAgentInfo())}.
   * If the typespec is not found, then this method is silently ignored.
   */
  void add(String typeSpec);

  /**
   * Add the specified agent to the top of list.  If the
   * AgentInfo is already in the list then it is removed first.
   */
  void add(AgentInfo info);

  /**
   * Convenience for {@code add(index, registry.getType(typeSpec).getAgentInfo())}.
   * If the typespec is not found, then this method is silently ignored.
   */
  void add(int index, String typeSpec);

  /**
   * Add the specified agent to the list at the given index.  
   * If the AgentInfo is already in the list then it is 
   * removed first.
   */
  void add(int index, AgentInfo info);

////////////////////////////////////////////////////////////////
// Remove
////////////////////////////////////////////////////////////////  
  
  /**
   * Remove an agent by its id string.  If the id 
   * is not in the list then this call is ignored.
   */
  void remove(String id);

  /**
   * Remove the specified agent AgentInfo instance.  If 
   * the agent is not in the list this call is ignored.
   */
  void remove(AgentInfo info);

  /**
   * Remove an AgentInfo by its index.  If the index 
   * is out of range then this call is ignored.
   */
  void remove(int index);

  /**
   * Remove all the agents in the specified list
   * which are found in this list.
   */
  void remove(AgentList list);

////////////////////////////////////////////////////////////////
// ToTop
////////////////////////////////////////////////////////////////  
  
  /**
   * Move the specified agent to the top of the list.
   * If the list doesn't contain the specified id, then
   * this call is ignored.
   */  
  void toTop(String id);

  /**
   * Move the specified agent to the top of the list.
   * If the agent isn't in the list this call is ignored.
   */  
  void toTop(AgentInfo info);

  /**
   * Move the agent at the specified index to the top of the 
   * list.  If the index is out of range this call is ignored.
   */  
  void toTop(int index);

  /**
   * Move the agents that match the given filter to the top
   * of the list.
   *
   * @param filter The filter to select agents that should move
   *               to the top of this agent list.
   * @since Niagara 4.0
   */
  default void toTop(AgentFilter filter)
  {
    AgentList toTopList = filter(filter);
    remove(toTopList);

    int size = toTopList.size();
    for(int i=size-1; i>=0; --i)
    {
      add(0, toTopList.get(i));
    }
  }

  /**
   * Move the agents that match the given predicate to the top
   * of the list.
   *
   * @param predicate The predicate to select agents that should move
   *                  to the top of this agent list.
   * @since Niagara 4.0
   */
  default void toTop(Predicate<AgentInfo> predicate)
  {
    AgentList toTopList = filter(predicate);
    remove(toTopList);

    int size = toTopList.size();
    for(int i=size-1; i>=0; --i)
    {
      add(0, toTopList.get(i));
    }
  }

////////////////////////////////////////////////////////////////
// ToBottom
////////////////////////////////////////////////////////////////  

  /**
   * Move the specified agent to the bottom of the list.  
   * If this list doesn't contain the specified id, then
   * this call is ignored.
   */  
  void toBottom(String id);

  /**
   * Move the specified agent to the bottom of the list.
   * If the agent isn't in the list this call is ignored.
   */  
  void toBottom(AgentInfo info);

  /**
   * Move the type at the specified index to the bottom of the 
   * list. If the index is out of range this call is ignored.
   */  
  void toBottom(int index);

  /**
   * Move the agents that match the given filter to the bottom
   * of the list.
   *
   * @param filter The filter to select agents that should move
   *               to the bottom of this agent list.
   * @since Niagara 4.0
   */
  default void toBottom(AgentFilter filter)
  {
    AgentList toBottomList = filter(filter);
    remove(toBottomList);

    int endIndex = size();
    int size = toBottomList.size();
    for(int i=size-1; i>=0; --i)
    {
      add(endIndex, toBottomList.get(i));
    }
  }

  /**
   * Move the agents that match the given predicate to the bottom
   * of the list.
   *
   * @param predicate The predicate to select agents that should move
   *                  to the bottom of this agent list.
   * @since Niagara 4.0
   */
  default void toBottom(Predicate<AgentInfo> predicate)
  {
    AgentList toBottomList = filter(predicate);
    remove(toBottomList);

    int endIndex = size();
    int size = toBottomList.size();
    for(int i=size-1; i>=0; --i)
    {
      add(endIndex, toBottomList.get(i));
    }
  }

////////////////////////////////////////////////////////////////
// Swap
////////////////////////////////////////////////////////////////  
  
  /**
   * Swap the placement of the agents at the specified indices.
   */
  void swap(int index1, int index2);

////////////////////////////////////////////////////////////////
// Iterable
////////////////////////////////////////////////////////////////

  @Override
  default Iterator<AgentInfo> iterator()
  {
    return new Iterator<AgentInfo>()
    {
      @Override
      public boolean hasNext()
      {
        return index < size();
      }

      @Override
      public AgentInfo next()
      {
        if (!hasNext())
        {
          throw new NoSuchElementException();
        }
        return get(index++);
      }

      private int index;
    };
  }
}
