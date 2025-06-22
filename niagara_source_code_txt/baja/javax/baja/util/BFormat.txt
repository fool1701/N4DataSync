/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.io.BIEncodable;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.BAbstractAuthenticator;
import javax.baja.security.BAbstractPasswordEncoder;
import javax.baja.security.BIProtected;
import javax.baja.security.BPassword;
import javax.baja.security.BPermissions;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import com.tridium.sys.Nre;
import com.tridium.util.EscUtil;
import com.tridium.util.FormatDenylist;

/**
 * BFormat is used to format Objects into Strings using
 * a standardized formatting pattern language.  The format
 * String is normal text with embedded scripts denoted by 
 * the % percent character (use %% to insert a real %). 
 * A script is one or more calls chained together using 
 * the . dot operator.  Calls are mapped to methods using 
 * reflections.  If a script can not be processed successfully,
 * an error will be output.  To define an alternate output to use
 * if an error is encountered, include a ? followed by a second
 * script within the same % pair.  Given call "foo", the 
 * order of reflection mapping is:
 * <ol>
 *   <li>special call (see below)</li>
 *   <li>getFoo(Context)</li>
 *   <li>getFoo()</li>
 *   <li>foo(Context)</li>
 *   <li>foo()</li>
 *   <li>get("foo")</li>
 *   <li>getFormatValue("foo")</li>
 * </ol>                
 * The following special functions are available to use in a script:
 * <ol>
 *   <li>time() calls Clock.time() to get current time as an AbsTime</li>
 *   <li>user() returns the current user's name</li>
 *   <li>lexicon(module:key) gets the specified lexicon text</li>
 *   <li>decodeFromString(module:type:escapedEncodedValue)</li>
 *   <li>substring(to) on string</li>
 *   <li>substring(-fromEnd) on string</li>
 *   <li>substring(from, to) on string</li>
 *   <li>escape(text) on string</li>
 *   <li>unescape(text) on string</li>
 * </ol>
 * <p>
 * Examples of formats:
 * <pre>
 * "hello world"
 * "my name is %displayName%"
 * "my parent's name is %parent.displayName%"
 * "%value% {%status.flagsToString%} @ %status.priority%"
 * "%time().toDateString%"
 * "%lexicon(bajaui:dialog.error)%"
 * "%parent.value?lexicon(bajaui:dialog.error)%"
 * </pre>
 *
 * @author    Brian Frank
 * @creation  19 May 04
 * @version   $Revision: 22$ $Date: 4/11/11 2:47:57 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BFormat
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////
  
  /**
   * Format the specified object using the given format pattern.
   *
   * @param format - format to use
   * @param obj - object being formatted
   * @return String
   */
  public static String format(String format, Object obj)
  {                                                             
    return BFormat.make(format).format(obj, null);
  }

  /**
   * Format the specified object using the given format pattern and context.
   *
   * Consider the following example:
   * <pre>
   * public class Foo
   * {
   *   public String bar()
   *   {
   *     return "call to function bar()";
   *   }
   *
   *   public String name()
   *   {
   *     return "Foo";
   *   }
   * }
   *
   * class FooPrinter
   * {
   *  static void printFoo()
   *  {
   *    Context context = new BasicContext();
   *    Foo foo = new Foo();
   *    BFormat.format("This is a %bar()% on class %name()%.", foo, context);
   *  }
   * }
   * </pre>
   *
   * The results from calling printFoo() would be:
   * <pre>
   * This is a call to function bar() on class Foo.
   * </pre>
   *
   * @param format format to use
   * @param obj object being formatted
   * @param cx context to use while formatting
   * @return String
   */
  public static String format(String format, Object obj, Context cx)
  {                                                             
    return BFormat.make(format).format(obj, cx);
  }
  
  /**
   * Return a format pattern that contains an encoded object
   *
   * @param enc encodable object
   * @return String
   * @throws IOException if BIEncodable cannot be encoded to String
   *
   * @since Niagara 3.5
   */
  public static String getEncodedPattern(BIEncodable enc)
    throws IOException
  {
    StringBuilder result = new StringBuilder();
    result.append("%decodeFromString(");
    result.append(enc.getType().getModule().getModuleName());
    result.append(':');
    result.append(enc.getType().getTypeName());
    result.append(':');
    result.append(SlotPath.escape(enc.encodeToString()));
    result.append(")%");
    return result.toString();
  }
  
  /**
   * Return a format pattern that uses the given module name and lexicon key, and
   * provides the lexicon arguments as format patterns.
   * 
   * @param module module name for lexicon key
   * @param key lexicon key
   * @param argFormatStrings format patterns for each of the arguments
   * 
   * @since Niagara 3.5
   */
  public static String getLexiconPattern(String module, String key, String[] argFormatStrings)
  {
    StringBuilder result = new StringBuilder();
    result.append("%lexicon(");
    result.append(module);
    result.append(':');
    result.append(key);
    if (argFormatStrings != null)
    {
      for (String argFormatString : argFormatStrings)
      {
        result.append(':');
        result.append(SlotPath.escape(argFormatString));
      }
    }
    result.append(")%");
    return result.toString();
  }
  
////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Construct using format string.
   *
   * @param format the format string
   * @return BFormat
   */
  public static BFormat make(String format)
  {                           
    if (format.isEmpty()) return DEFAULT;
    if ("%.%".equals(format)) return IDENTITY;
    return (BFormat)(new BFormat(format).intern());
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Private constructor.
   */
  private BFormat(String format)
  {              
    this.format = format;
  }
  
////////////////////////////////////////////////////////////////
// Format
////////////////////////////////////////////////////////////////
  
  /**
   * Get the format pattern string.
   */
  public final String getFormat()
  {            
    return format;
  }
  
  /**
   * Convenience for <code>format(obj, null)</code>.
   */
  public final String format(Object obj)
  {
    return format(obj, null);
  }

  /**
   * Format the specified object using the format pattern.
   */
  public final String format(Object obj, Context cx)
  {                           
    StringBuilder s = new StringBuilder();
    Object[] segments = parse();
    for (Object segment : segments)
    {
      if (segment instanceof String)
      {
        s.append(segment);
      }
      else
      {
        Call call = (Call) segment;
        try
        {
          s.append(call.eval(obj, obj, cx));
        }
        catch (Throwable e)
        {
          log.log(Level.FINE, "Could not format object", e);
          s.append(call.toErrorString(obj, obj, cx));
        }
      }
    }
    return s.toString();      
  }


  
////////////////////////////////////////////////////////////////
// Parse
////////////////////////////////////////////////////////////////
  
  protected static Object[] parse(String format)
  {
    try
    {      
      ArrayList<Object> acc = new ArrayList<>();
      int len = format.length();
      StringBuilder cur = new StringBuilder();
      
      for(int i=0; i<len; ++i)
      {
        char c = format.charAt(i);
      
        // normal char
        if (c != '%') { cur.append(c); continue; }
      
        // special %% is just %
        c = format.charAt(++i);
        if (c == '%') { cur.append('%'); continue; }

        // make text segment up to this point
        String str = cur.toString();
        if (str.length() > 0) acc.add(str);
        
        // parse linked list of scripts
        cur.setLength(0);
        Call head = null, tail = null;
        int firstParen = -1; // index of open paren
        int parenCount = 0;
        while((parenCount > 0) || (format.charAt(i) != '%' && format.charAt(i) != '?')) 
        {           
          c = format.charAt(i++);
          if (c == '(') 
          { 
            if (firstParen < 0) firstParen = cur.length(); 
            parenCount++; 
          }
          if (c == ')') { parenCount--; }
          if (c != '.' || (parenCount > 0)) { cur.append(c); continue; }
          
          tail = parseCall(tail, cur.toString(), firstParen);
          if (head == null) head = tail;
          cur.setLength(0);                           
          firstParen = -1;
        }

        tail = parseCall(tail, cur.toString(), firstParen);
        if (head == null) head = tail;
        cur.setLength(0);

        // Is there alternate output if in case of an error?
        if(format.charAt(i) == '?')
        {
          Call errHead = null, errTail = null;
          firstParen = -1; // index of open paren
          parenCount = 0;
          cur.setLength(0);                           
          i++;
          
          while((parenCount > 0) || (format.charAt(i) != '%')) 
          {           
            c = format.charAt(i++);
            if (c == '(') 
            {
              if (firstParen < 0)
              {
                firstParen = cur.length();
              }
              parenCount++; 
            }
            if (c == ')') { parenCount--; }
            if (c != '.' || (parenCount > 0)) { cur.append(c); continue; }
            
            errTail = parseCall(errTail, cur.toString(), firstParen);
            if (errHead == null) errHead = errTail;
            cur.setLength(0);                           
            firstParen = -1;
          }
          errTail = parseCall(errTail, cur.toString(), firstParen);
          if (errHead == null) errHead = errTail;
          cur.setLength(0);
          head.error = errHead;
          
          // Apply error to each level, in case 
          Call cursor = head;
          while(cursor!=tail)
          {
            cursor = cursor.next;
            cursor.error = head.error;
          }
        }
        
        // add script segment
        acc.add(head);
      }          
      
      // create segment list
      String str = cur.toString();
      if (str.length() > 0) acc.add(str);
      return acc.toArray();
    }
    catch(Exception e)
    {    
      // return error
      return new Object[] { "ERROR " + format };
    }
  }
  
  /**
   * Parse the format into segments
   */ 
  public Object[] parse()
  {     
    if (segments == null)
    {
      segments = parse(format);
    }
    return segments;
  }
  
  static Call parseCall(Call tail, String id, int firstParen)
    throws IOException
  {                         
    if (id.length() == 0) return new IdentityCall(tail, id);
    
    if (firstParen > 0)
    {                  
      // no args    
      if (id.equals("time()")) return new TimeCall(tail, id);
      if (id.equals("user()")) return new UserCall(tail, id);
      if (id.equals("sys()")) return new SysCall(tail, id);
      
      if (id.startsWith("escape(")) return new EscapeCall(tail, id);
      if (id.startsWith("unescape(")) return new UnescapeCall(tail, id);

      // args
      String arg = id.substring(firstParen+1, id.length()-1);
      if (id.startsWith("lexicon(")) return new LexiconCall(tail, id, arg);
      if (id.startsWith("substring(")) return new SubstringCall(tail, id, arg);
      if (id.startsWith("decodeFromString(")) return new DecodeFromStringCall(tail, id, arg);
    }              
    
    return new ReflectCall(tail, id);
  }         
  
////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////
  
  public void dump()
  {
    System.out.println("BFormat: " + format);  
    for(int i=0; i<parse().length; ++i)
    {
      Object seg = segments[i];
      if (seg instanceof String)
        System.out.println(" [" + i + "] " + "\"" + seg + "\"");
      else if (seg instanceof Call)
      {
        System.out.println(" [" + i + "] " + seg + " " + TextUtil.getClassName(seg.getClass()));
        Call call = (Call)seg;
        if(call.error!=null)
          System.out.println("     - err: " + seg + " " + TextUtil.getClassName(seg.getClass()));
      }
      else
      {
        System.out.println(" [" + i + "] " + seg + " " + TextUtil.getClassName(seg.getClass()));
      }
      
    }
  }

////////////////////////////////////////////////////////////////
// Call
////////////////////////////////////////////////////////////////

  public static abstract class Call
  { 
    Call(Call prev, String id) 
    {                   
      if (prev != null) prev.next = this;
      this.id = id; 
    }
    
    public final String toString() 
    { 
      if (next == null) return id;
      else return id + "." + next;
    }   

    String toString(Object obj, Context cx)
    {
      if (obj instanceof BObject)
        return ((BObject)obj).toString(cx);
      else
        return String.valueOf(obj);
    }

    String toErrorString(Object obj, Object errorBase, Context cx) 
    {             
      if(error!=null)
      {
        try
        {
          return error.eval(errorBase, errorBase, cx).toString();
        }
        catch (Throwable ignored)
        {
        }
      }

      String type;
      if (obj == null)
        type = "null";
      else if (obj instanceof BObject)
        type = ((BObject)obj).getType().toString();
      else
        type = TextUtil.getClassName(obj.getClass());
      return "%err:" + type + ":" + id + "%";
    }           
    
    abstract Object eval(Object base, Object errorBase, Context cx)
      throws Throwable;

    final Object chain(Object base, Object errorBase, Context cx)
      throws Throwable
    {                 
      if (next != null)
        return next.eval(base, errorBase, cx);
      else
        return toString(base, cx);
    }
    public boolean getEvalFailed() { return evalFailed; }

    String id;
    Call next;
    Call error = null;
    boolean evalFailed = false;
  }               

////////////////////////////////////////////////////////////////
// IdentityCall
////////////////////////////////////////////////////////////////

  static class IdentityCall extends Call
  {
    IdentityCall(Call prev, String id) 
    {
      super(prev, id);
    }
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
    {
      if (obj instanceof BObject)
        return ((BObject)obj).toString(cx);
      else
        return obj;
    }
  }
  
////////////////////////////////////////////////////////////////
// ReflectCall
////////////////////////////////////////////////////////////////

  public static class ReflectCall extends Call
  { 
    ReflectCall(Call prev, String id) 
    {                
      super(prev, id);   
    }

    @Override
    public Object eval(final Object obj, final Object errorBase, Context cx)
      throws Throwable
    {
      // Short circuit any security related types.
      if (obj instanceof BPassword ||
          obj instanceof BAbstractAuthenticator ||
          obj instanceof BAbstractPasswordEncoder)
      {
        return toErrorString(obj, errorBase, cx);
      }
      
      BComplex complex = obj instanceof BComplex ? (BComplex)obj : null;
      
      // if this is a complex property, then add the slot
      // facets to the context as we walk the call chain
      if (complex != null)
      {
        // Make sure user in context has permission for this object
        if (!hasPermission(complex, null, cx))
        {
          return toErrorString(obj, errorBase, cx);
        }
        
        Property prop = complex.getProperty(id);
        
        if (prop == null) 
        { // For slots of a component, we must also enforce permission checking.
          // Detect case where trying to sneak around permission checks by
          // directly calling the slot-o-matic generated get/do/fire method for 
          // a frozen property, action, or topic.  
          String slotName = id;
          if (id.startsWith("get"))
          {
            slotName = TextUtil.decapitalize(id.substring(3));
          }
          else if (id.startsWith("do"))
          {
            slotName = TextUtil.decapitalize(id.substring(2));
          }
          else if (id.startsWith("fire"))
          {
            slotName = TextUtil.decapitalize(id.substring(4));
          }
          
          // If we detect any of these sneaky cases, make sure we check permissions!
          if (slotName != null)
          {
            Slot slot = complex.getSlot(slotName);
            if ((slot != null) && // indicates a matching slot found by name
                slot.isFrozen()) // only check frozen slots
            {
              if (!hasPermission(complex, slot, cx)) // must have appropriate permissions
              {
                return toErrorString(obj, errorBase, cx);
              }
              if (slot.isAction())
              { // Actions calls are now denied, but check the exclusions list
                // before denying action calls. Be sure to check the various permutations:
                //   1. Check for excluded action slot by slot name
                //   2. Check for excluded action by id (doXXX)
                Class<?> objCls = obj.getClass();
                if (!FormatDenylist.isExcludedFromDenylist(objCls, slotName, null) &&
                    ((!slotName.equals(id)) && !FormatDenylist.isExcludedFromDenylist(objCls, id, null)))
                {
                  String result = toErrorString(obj, errorBase, cx);
                  if (log.isLoggable(Level.FINEST))
                  {
                    log.finest("Format attempted to call a denied action: "+objCls.getName()+'.'+id);
                  }
                  return result;
                }
              }
            }
          }
        }
          
        if (prop != null)                         
        {
          // Check property permissions
          if (!hasPermission(complex, prop, cx))
          {
            return toErrorString(obj, errorBase, cx);
          }
          
          // Create a new context and merge the facets in. This will trickle
          // down the call chain. For example, when the format `%out.value%` is
          // applied to a `control:NumericPoint`, the point's facets will be passed
          // down to the value (BDouble) when its `toString(Context)` method is called.
          cx = new BasicContext(cx, complex.getSlotFacets(prop));
          
          // If this is the tail of the call chain, then do the
          // string conversion here since we have the slots facets 
          if (next == null)
          {
            return complex.get(prop).toString(cx);
          }
        }
      }
      
      String getId = "get" + TextUtil.capitalize(id);
      Object r;

      r = reflect(obj, getId, paramContext, cx); if (r != null) return chain(r, errorBase, cx);
      r = reflect(obj, getId, paramNone, null);  if (r != null) return chain(r, errorBase, cx);                      
      r = reflect(obj, id, paramContext, cx);    if (r != null) return chain(r, errorBase, cx);                    
      r = reflect(obj, id, paramNone, null);     if (r != null) return chain(r, errorBase, cx);                   
      r = reflect(obj, "get", paramString, id);  if (r != null) return chain(r, errorBase, cx);                      
      r = reflect(obj, "getFormatValue", paramString, id);  if (r != null) return chain(r, errorBase, cx);
  
      // Handle the format property handler last so we can give a chance for reflected calls
      // (such as `getParent()`) to be attempted first. This is done to avoid unnecessary loading work
      // in `BIFormatPropertyHandler` as a result of a format call.
      if (complex != null && obj instanceof BIFormatPropertyHandler)
      {
        Property prop = ((BIFormatPropertyHandler) obj).getFormatPropertyByName(id);
        
        if (prop != null)
        {
          // Check property permissions
          if (!hasPermission(complex, prop, cx))
          {
            return toErrorString(obj, errorBase, cx);
          }
  
          // Create a new context and merge the facets in. This will trickle
          // down the call chain. For example, when the format `%out.value%` is
          // applied to a `control:NumericPoint`, the point's facets will be passed
          // down to the value (BDouble) when its `toString(Context)` method is called.
          cx = new BasicContext(cx, complex.getSlotFacets(prop));
          
          BValue value = complex.get(prop);
          
          if (value != null)
          {
            if (next == null)
            {
              return value.toString(cx);
            }
            else
            {
              return chain(value, errorBase, cx);
            }
          }
        }
      }
      
      evalFailed = true;
      return toErrorString(obj, errorBase, cx);
    }
  }

  private static boolean hasPermission(BComplex complex, Slot slot, Context cx)
  {          
    if (!complex.getType().is(BIProtected.TYPE))
      return true;  
    
    BPermissions permissions = ((BIProtected)complex).getPermissions(cx);
    if (slot == null)
      return permissions.hasOperatorRead();
    else if (Flags.isOperator(complex, slot))
    {
      if (slot.isAction())
        return permissions.hasOperatorInvoke();
      else
        return (permissions.hasOperatorRead() &&
                checkProtectedSlot(complex, slot, cx));
    }
    else
    {
      if (slot.isAction())
        return permissions.hasAdminInvoke();
      else
        return (permissions.hasAdminRead() &&
                checkProtectedSlot(complex, slot, cx));
    }
  }
  
  private static boolean checkProtectedSlot(BComplex complex, Slot slot, Context cx)
  {
    if (slot.isProperty())
    {
      BValue child = complex.get((Property)slot);
      if (child.getType().is(BIProtected.TYPE))
      {
        BPermissions childPermissions = ((BIProtected)child).getPermissions(cx);
        return childPermissions.hasOperatorRead();
      }  
    }
    return true;
  }
  
////////////////////////////////////////////////////////////////
// Reflection method caching
////////////////////////////////////////////////////////////////
 
  /*
   * Create a LinkedHashMap with a limited size, determined by a system property.
   */
  private static class CacheMap<K, V>
    extends LinkedHashMap<K, V>
  {
    public CacheMap(int maxSize)
    {
      this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
    {
      return size() > maxSize;
    }

    private final int maxSize;
  }
  
  /*
   * Make a cache key for the method cache. methods are keyed by taking the method
   * name and concatenating the fully-qualified types of all parameters to it, 
   * separated by a colon. This should ensure a unique key when paired with a 
   * class name.
   */
  private static String makeCacheKey(String methodName, Class<?>[] methodParams)
  {
    StringJoiner sj = new StringJoiner(":");
    sj.add(methodName);
    for (Class<?> param : methodParams)
    {
      sj.add(param.getName());
    }
    return sj.toString();
  }
  
  /*
   * Invalidate the cache for a given class by setting its cache entry to a new,
   * empty map. 
   */
  private static void invalidateCache(Class<?> cls)
  {
    classToMethodCache.computeIfPresent(cls, (c, m) ->
      Collections.synchronizedMap(new CacheMap<>(METHOD_CACHE_SIZE)));
  }
  
  private static Object reflect(Object obj, String name, Class<?>[] params, Object arg)
    throws Throwable
  {
    Class<?> cls = obj.getClass();
    try
    {
      String cacheKey = makeCacheKey(name, params);
      Map<String, Optional<Method>> methodMap = classToMethodCache.computeIfAbsent(cls, c ->
        Collections.synchronizedMap(new CacheMap<>(METHOD_CACHE_SIZE)));

      // We want to cache when a method is not found, to prevent costly lookups when
      // ReflectCall.eval tries to call 6 different methods--store Optional.empty()
      // as the value for this case.
      methodMap.computeIfAbsent(cacheKey, c -> {
        try
        {
          // Check class and method name against denylist and filter out
          // any denied calls by caching Optional.empty().
          if (FormatDenylist.isDenied(cls, name, params))
          {
            if (log.isLoggable(Level.FINEST))
            {
              log.finest("Format attempted to call a denied method: "+cls.getName()+'.'+name);
            }
            return Optional.empty();
          }

          Method method = cls.getMethod(name, params);

          // Don't allow methods that return void to be called from BFormats (NCCB-15861)
          if (method.getReturnType().equals(void.class))
          {
            if (log.isLoggable(Level.FINEST))
            {
              log.finest("Format attempted to call a denied method that returns void: "+cls.getName()+'.'+name);
            }
            return Optional.empty();
          }

          return Optional.of(method);
        }
        catch (NoSuchMethodException e)
        {
          return Optional.empty();
        }
      });

      Optional<Method> method = methodMap.getOrDefault(cacheKey, Optional.empty());
      if (method.isPresent())
      {
        Object[] args = params.length == 0 ? null : new Object[] { arg };
        return method.get().invoke(obj, args);
      }
      return null;
    }
    // Invalidate the method cache if we got an unexpected exception--we may have
    // cached a method that will never work.
    catch(Exception e)
    {
      invalidateCache(cls);
      throw e instanceof InvocationTargetException ? ((InvocationTargetException)e).getTargetException() : e;
    }
  }

////////////////////////////////////////////////////////////////
// Special Calls
////////////////////////////////////////////////////////////////

  static class TimeCall extends Call
  { 
    TimeCall(Call prev, String id) 
    {                
      super(prev, id);   
    }                   
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
      throws Throwable
    {
      return chain(Clock.time(), errorBase, cx);
    }
  }
  
  static class SysCall extends Call
  { 
    SysCall(Call prev, String id) 
    {                
      super(prev, id);   
    }                   
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
      throws Throwable
    {
      return chain(new Sys(), errorBase, cx);
    }
  }  
  
  static class UserCall extends Call
  { 
    UserCall(Call prev, String id) 
    {                
      super(prev, id);   
    }                   
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
      throws Throwable
    {
      if(obj instanceof BComponent)
      {
        BComponent comp = (BComponent)obj;
        String username;
        Context sessionCx = comp.getSession().getSessionContext();
        if(sessionCx!=null)
          username = ((BFacets)sessionCx).gets("username", null); // get session user
        else if (cx.getUser()!=null)
          username = cx.getUser().getUsername(); // get local user
        else
          throw new BajaRuntimeException("Username unavailable"); // failed
        if(username!=null && username.length()>0)
          return chain(username, errorBase, cx);
      }
      return chain(null, errorBase, cx);
      
    }
  }  

  static class LexiconCall extends Call
  { 
    LexiconCall(Call prev, String id, String arg) 
    {                
      super(prev, id);   
      int colon = arg.indexOf(':');
      module = arg.substring(0, colon);
      //key = arg.substring(colon+1);
      arg = arg.substring(colon+1);
      colon = arg.indexOf(':');
      if (colon >= 0)
      {
        key = arg.substring(0, colon);
        argFormats = TextUtil.split(arg.substring(colon + 1), ':');
      }
      else
      {
        key = arg;
        argFormats = null;
      }
    }                   
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
      throws Throwable
    {          
      if (argFormats == null)
      {
        return chain(Lexicon.make(module, cx).getText(key, new Object[] { obj }), errorBase, cx);
      }
      else
      {
        Object[] args = new Object[argFormats.length];
        for (int i = 0; i < args.length; i++)
        {
          args[i] = format(SlotPath.unescape(argFormats[i]), obj, cx);
        }
        return chain(Lexicon.make(module, cx).getText(key, args), errorBase, cx);
      }
    }                                              
    
    String module, key;
    String[] argFormats;
  }
  
  static class DecodeFromStringCall extends Call
  {
    DecodeFromStringCall(Call prev, String id, String arg)
      throws IOException
    {
      super(prev, id);
      int colon = arg.indexOf(':');
      String module = arg.substring(0, colon);
      arg = arg.substring(colon+1);
      colon = arg.indexOf(':');
      String type = arg.substring(0, colon);
      BIEncodable enc = (BIEncodable)Sys.getType(module + ':' + type).getInstance();
      this.obj = enc.decodeFromString(SlotPath.unescape(arg.substring(colon + 1)));
    }
    
    @Override
    Object eval(Object ignored, Object errorBase, Context cx)
    { 
      return obj;
    }
    
    BObject obj;
  }

  static class SubstringCall extends Call
  { 
    SubstringCall(Call prev, String id, String arg) 
    {                
      super(prev, id);   
      args = TextUtil.splitAndTrim(arg, ',');
    }                   
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
      throws Throwable
    {                
      String s = toString(obj, cx);        
      
      if (args.length == 1)   
      {
        int i = Integer.parseInt(args[0]);
        if (i >= 0)
          s = s.substring(i);
        else
          s = s.substring(s.length()+i);
      }
      else
      {
        s = s.substring(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      }
        
      return chain(s, errorBase, cx);
    }                                              
    
    String[] args;
  }

  static class EscapeCall extends Call
  {
    EscapeCall(Call prev, String id)
    {
      super(prev, id);
    }
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
    { 
      return EscUtil.slot.escape(toString(obj, cx));
    }
  }
  
  static class UnescapeCall extends Call
  {
    UnescapeCall(Call prev, String id)
    {
      super(prev, id);
    }
    
    @Override
    Object eval(Object obj, Object errorBase, Context cx)
    { 
      return EscUtil.slot.unescape(toString(obj, cx));
    }
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * BFormat uses its encodeToString() value's hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    try
    {
      if (hashCode == -1) 
        hashCode = encodeToString().hashCode();
      return hashCode;
    }
    catch(Exception e) 
    { 
      return System.identityHashCode(this);
    }
  }
  
  /**
   * BFormats are equal if they have equal format patterns.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BFormat)
    {
      BFormat x = (BFormat)obj;
      return format.equals(x.format);
    }
    return false;
  }
  
  /**
   * Encode format pattern String.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {                              
    out.writeUTF(format);
  }
  
  /**
   * Decode format pattern String.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {         
    return make(in.readUTF());
  }

  /**
   * Encode format pattern String.
   */
  @Override
  public String encodeToString()
    throws IOException
  {                   
    return format;
  }

  /**
   * Decode format pattern String.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  /**
   * Get default is the empty string "" format pattern.
   */
  public static final BFormat DEFAULT = new BFormat("");

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFormat.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final Class<?>[] paramNone    = new Class<?>[0];
  static final Class<?>[] paramContext = new Class<?>[] { Context.class };
  static final Class<?>[] paramString  = new Class<?>[] { String.class };
  
  static final Logger log = Logger.getLogger("sys.formatter");

  private static final int CLASS_CACHE_SIZE =
    AccessController.doPrivileged((PrivilegedAction<Integer>)
      () -> Integer.getInteger("niagara.baja.formatCacheSize",
      "Workstation".equals(Nre.getHostModel()) ? 2048 : 64));

  private static final int METHOD_CACHE_SIZE =
    AccessController.doPrivileged((PrivilegedAction<Integer>)
      () -> Integer.getInteger("niagara.baja.formatMethodCacheSize",
      "Workstation".equals(Nre.getHostModel()) ? 1024 : 64));

  private static final Map<Class<?>, Map<String, Optional<Method>>> classToMethodCache =
    Collections.synchronizedMap(new CacheMap<>(CLASS_CACHE_SIZE));

  String format;      // format pattern
  Object[] segments;  // list of Strings and Calls
  private int hashCode = -1;
  
  /**
   * The most common format identity pattern commonly used on graphic pages.
   */
  private static final BFormat IDENTITY = new BFormat("%.%");
}
