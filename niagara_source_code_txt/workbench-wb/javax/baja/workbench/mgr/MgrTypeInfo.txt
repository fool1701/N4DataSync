/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.agent.AgentInfo;
import javax.baja.gx.BImage;
import javax.baja.naming.SlotPath;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * MgrTypeInfo wraps information about what type to create
 * in the station database when doing a new or add operation.
 *
 * @author    Brian Frank
 * @creation  12 Jan 04
 * @version   $Revision: 9$ $Date: 3/28/05 1:41:00 PM EST$
 * @since     Baja 1.0
 */
public abstract class MgrTypeInfo
{         
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a array of MgrTypeInfo from an array of TypeInfo. 
   */
  public static MgrTypeInfo[] makeArray(TypeInfo[] typeInfo)
  {                        
    MgrTypeInfo[] r = new MgrTypeInfo[typeInfo.length];
    for(int i=0; i<r.length; ++i) r[i] = make(typeInfo[i]);
    return sort(r);    
  }

  /**
   * Convenience for <code>makeArray(Sys.getRegistry().getConcreteTypes(baseType))</code>.
   */
  public static MgrTypeInfo[] makeArray(TypeInfo baseType)
  {
    return makeArray(Sys.getRegistry().getConcreteTypes(baseType));
  }

  /**
   * Convenience for <code>makeArray(baseType.getTypeInfo())</code>.
   */
  public static MgrTypeInfo[] makeArray(Type baseType)
  {
    return makeArray(baseType.getTypeInfo());
  }

  /**
   * Make a array of MgrTypeInfo from an array of AgentInfo. 
   */
  public static MgrTypeInfo[] makeArray(AgentInfo[] agentInfo)
  {                        
    MgrTypeInfo[] r = new MgrTypeInfo[agentInfo.length];
    for(int i=0; i<r.length; ++i) r[i] = make(agentInfo[i]);
    return sort(r);    
  }
  
  /**
   * Make a MgrTypeInfo from a TypeInfo. 
   */
  public static MgrTypeInfo make(TypeInfo typeInfo)
  {                            
    return new TypeInfoImpl(typeInfo);
  }

  /**
   * Make a MgrTypeInfo from a AgentInfo. 
   */
  public static MgrTypeInfo make(AgentInfo agentInfo)
  {                            
    return new AgentInfoImpl(agentInfo);
  }

  /**
   * Make a MgrTypeInfo from a Type. 
   */
  public static MgrTypeInfo make(Type type)
  {                            
    return make(type.getTypeInfo());
  }

  /**
   * Make a MgrTypeInfo from a prototype BComponent. 
   */
  public static MgrTypeInfo make(BComponent proto)
  {                            
    return new ProtoImpl(proto);
  }
                                                                              
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the display name of the type to create.
   */
  public abstract String getDisplayName();
  
  /**
   * Get the icon of the type to create.
   */
  public abstract BImage getIcon();

  /**
   * Allocate an instance of this type.
   */
  public abstract BComponent newInstance()
    throws Exception;       
  
  /**
   * Return true if this type may be used to perform 
   * a match against the specified database component.
   */
  public abstract boolean isMatchable(BComponent database);
  
  /**
   * Default implementation of equals compares display name.
   */
  public boolean equals(Object o) 
  {                                                         
    if (o instanceof MgrTypeInfo)
    {                           
      MgrTypeInfo x = (MgrTypeInfo)o;
      return getCompareString().equals(x.getCompareString());
    }
    return false;
  }        
  
  /** 
   * Get string for use in equals method.
   * @since Niagara 3.6.48
   */
  protected String getCompareString() { return getDisplayName(); }
  
  
  /**
   * Get the type as a slot name.  The default implementation
   * returns the display name stripped of spaces and escaped.
   */
  public String toSlotName()
  {
    return SlotPath.escape(TextUtil.replace(getDisplayName(), " ", ""));
  }  
  
  /**
   * Return <code>getDisplayName()</code>
   */  
  public String toString()
  {
    return getDisplayName(); 
  }       
  
////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////
  
  /**
   * This is a utility method to check an array of MgrTypeInfo for
   * duplicate types.  Any TypeInfoImpl with duplicate type names will 
   * have the duplicate flag set true.  This is used by MgrController
   * when implementing the "new" command.<p>
   * This utility has public access to allow its use if functionality 
   * in MgrController is overridden.
   * 
   * @since Niagara 3.6.48 - becomes public in 3.8
   */
  public static void markDuplicates(MgrTypeInfo[] types)
  {
    if(types==null || types.length==0) return;
    
    String[] tags = new String[types.length];
    for(int i=0; i<tags.length; ++i) tags[i] = types[i].getDisplayName();
    
    // if duplicate name set duplicate flag
    for(int i=0; i<tags.length; ++i)
    {
      String tnam = tags[i];
      for(int n=i+1; n<tags.length; ++n)
      {
        if(tnam.equals(tags[n]))
        {
          if(types[i] instanceof TypeInfoImpl) ((TypeInfoImpl)types[i]).duplicate = true;
          if(types[n] instanceof TypeInfoImpl) ((TypeInfoImpl)types[n]).duplicate = true;
        }
      }
    }
  }  
  
  /**
   * Sort utility to alphabetize array of MgrTypeInfo.
   * @since Niagara 3.6.48 
   */
  static MgrTypeInfo[] sort(MgrTypeInfo[] v)
  {
    if (v.length==0) return v;

    // get array of names to speed sort
    int n = v.length;
    String[] k = new String[n];
    for(int i=0 ; i<n ; ++i) k[i] = v[i].getDisplayName();
    
    // quick sort
    int incr = n / 2;
    while(incr >= 1)
    {
      for(int i=incr; i<n; i++)
      {
        String tempKey = k[i];
        MgrTypeInfo tempValue = v[i];
        int j = i;
        while(j >= incr && tempKey.compareTo(k[j-incr]) < 0)
        {
          k[j] = k[j-incr];
          v[j] = v[j-incr];
          j -= incr;
        }
        k[j] = tempKey;
        v[j] = tempValue;
      }
      incr /= 2;
    }
    return v;
  }
  

////////////////////////////////////////////////////////////////
// TypeInfoImpl
////////////////////////////////////////////////////////////////

  static class TypeInfoImpl extends MgrTypeInfo
  {                        
    TypeInfoImpl(TypeInfo ti) { this.ti = ti; }
    
    /* Override to use the TypeInfo displayName not getDisplayName() which may
     * have module name appended. */
    public String toSlotName()
    {
      return SlotPath.escape(TextUtil.replace(ti.getDisplayName(null), " ", ""));
    }  
    public String getDisplayName() 
    { 
      if(!duplicate) return ti.getDisplayName(null);
      // If duplicate flag set then append the module name. 
      return ti.getDisplayName(null) + "(" + ti.getModuleName() + ")"; 
    }
    public BImage getIcon() { return BImage.make(ti.getIcon(null)); }
    public BComponent newInstance() { return (BComponent)ti.getInstance(); }
    public boolean isMatchable(BComponent db) { return db.getType().is(ti); }
    protected String getCompareString() { return ti.toString(); }
    boolean duplicate = false;
    TypeInfo ti;
  }
  

////////////////////////////////////////////////////////////////
// AgentInfoImpl
////////////////////////////////////////////////////////////////

  static class AgentInfoImpl extends MgrTypeInfo
  {                        
    AgentInfoImpl(AgentInfo ai) { this.ai = ai; }
    public String getDisplayName() { return ai.getDisplayName(null); }
    public BImage getIcon() { return BImage.make(ai.getIcon(null)); }
    public BComponent newInstance() { return (BComponent)ai.getInstance(); }
    public boolean isMatchable(BComponent db) { return db.getType().is(ai.getAgentType()); }
    protected String getCompareString() { return ai.getAgentType().toString(); }
    AgentInfo ai;
  }

////////////////////////////////////////////////////////////////
// ProtoImpl
////////////////////////////////////////////////////////////////

  static class ProtoImpl extends MgrTypeInfo
  {                        
    ProtoImpl(BComponent proto) { this.proto = proto; }
    public String getDisplayName() { return proto.getType().getDisplayName(null); }
    public BImage getIcon() { return BImage.make(proto.getIcon()); }
    public BComponent newInstance() { return (BComponent)proto.newCopy(true); }
    public boolean isMatchable(BComponent db) { return db.getType().is(proto.getType()); }
    protected String getCompareString() { return proto.getType().getTypeInfo().toString(); }
    BComponent proto;
  }
  
  
}                    


