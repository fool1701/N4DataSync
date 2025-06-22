/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BKeyEvent;

/**
 * BAccelerator stores a key stroke which includes
 * modifiers which can be used to invoke actions
 * on the widget with focus.  The BNF grammar of 
 * an accelerator string is (case insensitive):
 * <pre>
 *   #accelerator = [ #modifiers "+" ] #keycode
 *   #modifiers   = #modifier { "+" #modifier" }
 *   #modifier    = "ctrl" | "shift" | "alt" | "meta"
 *   #keycode     =  #alpha | #number | #function | #symbol | #special
 *   #alpha       = "a".."z"
 *   #number      = "0".."9"
 *   #function    = "f1".."f12"
 *   #symbol      = "/"
 *   #special     = "up" | "down" | "left" | "right" | "esc"
 *                  "insert" | "delete" | "home" | "end" |
 *                  "pageup" | "pagedown" | "backspace" | "tab"
 * </pre> 
 *
 * @author    Brian Frank
 * @creation  30 Nov 00
 * @version   $Revision: 14$ $Date: 7/27/10 9:55:34 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BAccelerator
  extends BSimple
{
  /**
   * Null is a singleton used to define no accelerator.
   */
  public static final BAccelerator NULL = new BAccelerator();

  /**
   * The default accelerator is NULL.
   */
  public static final BAccelerator DEFAULT = NULL;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BAccelerator(2979906276)1.0$ @*/
/* Generated Thu Nov 18 13:07:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAccelerator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Accelerator factory method with specified key 
   * code and modifiers mask.
   */
  public static BAccelerator make(int keyCode, int modifiers)
  {
    BAccelerator acc = new BAccelerator();
    acc.string = null;
    acc.keyCode = keyCode;
    acc.modifiers = modifiers;
    return acc;
  }
  
  /**
   * Factory method for an accelerator by parsing
   * the specified string.  
   */
  public static BAccelerator make(String s)
  {
    try
    {
      BAccelerator acc = new BAccelerator();
      acc.parse(s);
      return acc;
    }
    catch(Exception e)
    {              
      throw new IllegalArgumentException("Invalid accelerator \"" + s + "\"");
    }
  }

  /**
   * Private null constructor.
   */
  private BAccelerator()
  {
    this.string = "null";
  }

////////////////////////////////////////////////////////////////
// Accelerator
////////////////////////////////////////////////////////////////

  /**
   * Is this the null accelerator.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * Return true if the specified event is a match
   * against this accelerator.
   */
  public boolean isMatch(BKeyEvent event)
  {
    return keyCode == event.getKeyCode() &&
           (modifiers ^ event.getModifiersEx()) == 0;
  }

  /**
   * Get the modifiers which must be pressed 
   * in order to invoke this accelerator.  
   * This is a mask of the bits defined in BInputEvent.
   */
  public int getModifiers()
  {
    return modifiers;
  }

  /**
   * Get the key code which is a virtual code defined
   * by BKeyEvent.   
   */
  public int getKeyCode()
  {
    return keyCode;
  }

  /**
   * Get the modifiers as their grammar string.
   */
  public String getModifiersString()
  {
    StringBuilder s = new StringBuilder();
    if ((modifiers & BInputEvent.CTRL_DOWN_MASK)  != 0) s.append("Ctrl+");
    if ((modifiers & BInputEvent.SHIFT_DOWN_MASK) != 0) s.append("Shift+");
    if ((modifiers & BInputEvent.ALT_DOWN_MASK)   != 0) s.append("Alt+");
    if ((modifiers & BInputEvent.META_DOWN_MASK)  != 0) s.append("Meta+");
    if (s.length() > 0) s.setLength(s.length()-1);
    return s.toString();
  }
  
  /**
   * Get the key code as its grammar string.
   */
  public String getKeyCodeString()
  {
    String s = null;
    if (keyCode > 0 && keyCode < keyCodeStrings.length)
      s = keyCodeStrings[keyCode];
    if (s != null) return s;
    throw new IllegalArgumentException("Invalid key code: " + keyCode);      
  }  
    
  /**
   * Parse the specified string.
   */
  private void parse(String s)
  {
    this.string = s;
    StringTokenizer st = new StringTokenizer(TextUtil.toLowerCase(s), "+");
    while(st.hasMoreTokens())
    {
      String token = st.nextToken();    
      char c0 = token.charAt(0);
      int len = token.length();
      
      // check for alpha or number
      if (len == 1)
      {
        if ('a' <= c0 && c0 <= 'z') keyCode = BKeyEvent.VK_A + (c0 - 'a');
        else if ('0' <= c0 && c0 <= '9') keyCode = BKeyEvent.VK_0 + (c0 - '0');
        else if (c0 == '/') keyCode = BKeyEvent.VK_SLASH;
        else throw new RuntimeException();
      }
      
      // check for everything else
      else
      {
        char c1 = token.charAt(1);
        switch(c0)
        {
          case 'a':
            if (token.equals("alt")) {modifiers |= BInputEvent.ALT_DOWN_MASK; continue;}
            break;
          case 'b':
            if (token.equals("backspace")) { keyCode = BKeyEvent.VK_BACK_SPACE; continue;}
            break;
          case 'c':
            if (token.equals("ctrl")) {modifiers |= BInputEvent.CTRL_DOWN_MASK; continue;}
            break;
          case 'd':
            if (token.equals("down")) {keyCode = BKeyEvent.VK_DOWN; break;}
            if (token.equals("delete")) {keyCode = BKeyEvent.VK_DELETE; break;}
            break;
          case 'e':
            if (token.equals("end")) {keyCode = BKeyEvent.VK_END; break;}
            if (token.equals("esc")) {keyCode = BKeyEvent.VK_ESCAPE; break;}
            break;
          case 'f':
            if (len == 2 && '1' <= c1 && c1 <= '9') {keyCode = BKeyEvent.VK_F1 + (c1 - '1');break;}
            if (token.equals("f10")) {keyCode = BKeyEvent.VK_F10; break;}
            if (token.equals("f11")) {keyCode = BKeyEvent.VK_F11; break;}
            if (token.equals("f12")) {keyCode = BKeyEvent.VK_F12; break;}
            break;
          case 'h':
            if (token.equals("home")) {keyCode = BKeyEvent.VK_HOME; break;}
            break;
          case 'i':
            if (token.equals("insert")) {keyCode = BKeyEvent.VK_INSERT; break;}
            break;
          case 'l':
            if (token.equals("left")) {keyCode = BKeyEvent.VK_LEFT; break;}
            break;
          case 'm':
            if (token.equals("meta")) {modifiers |= BInputEvent.META_DOWN_MASK; continue;}
            break;
          case 'p':
            if (token.equals("pageup")) {keyCode = BKeyEvent.VK_PAGE_UP; break;}
            if (token.equals("pagedown")) {keyCode = BKeyEvent.VK_PAGE_DOWN; break;}
            break;
          case 'r':
            if (token.equals("right")) {keyCode = BKeyEvent.VK_RIGHT; break;}
            break;
          case 's':
            if (token.equals("shift")) {modifiers |= BInputEvent.SHIFT_DOWN_MASK; continue;}
            if (token.equals("space")) {keyCode = BKeyEvent.VK_SPACE; break;}
            break;
          case 't':
            if (token.equals("tab")) {keyCode = BKeyEvent.VK_TAB; break;}
            break;
          case 'u':
            if (token.equals("up")) {keyCode = BKeyEvent.VK_UP; break;}
            break;
        }
      }
      
      // if we didn't continue then it was the keycode, 
      // not a modifier and we should be done
      if (st.hasMoreTokens()) throw new RuntimeException();
      break;
    }
    
    // insure we mapped a valid key code
    if (keyCode == 0) throw new RuntimeException();
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////
  
  /**
   * Provide a hash using the modifiers and key code.
   */
  public int hashCode()
  {
    return keyCode ^ (~modifiers);
  }
  
  /**
   * Equality is based on identical key code 
   * and modifiers.
   */
  public boolean equals(Object object)
  {
    if (object instanceof BAccelerator)
    {
      BAccelerator a = (BAccelerator)object;
      return keyCode == a.keyCode && 
             modifiers == a.modifiers;
    }
    return false;
  }
  
  /**
   * To string.
   */
  public String toString(Context context)
  {
    return encodeToString();
  }
  
  /**
   * BAccelerator is serialized writeUTF() of 
   * its string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF( encodeToString() );
  }
  
  /**
   * BAccelerator is unserialized using readUTF()
   * of its string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString( in.readUTF() );
  }

  /**
   * Write the primitive in String format.
   */
  public String encodeToString()
  {
    if (string == null)
    {
      if (modifiers == 0) string = getKeyCodeString();
      else string = getModifiersString() + '+' + getKeyCodeString();
    }
    return string;
  }

  /**
   * Read the primitive from String format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      if (s.equals("null"))
        return NULL;
        
      return make(s);
    }
    catch(Exception e)
    {
      throw new IOException("Invalid accelerator: " + s);
    }
  }

////////////////////////////////////////////////////////////////
// KeyCodeStrings
////////////////////////////////////////////////////////////////  

  private static final String keyCodeStrings[] = new String[255];
  static
  {
    for(int i=BKeyEvent.VK_A; i<=BKeyEvent.VK_Z; ++i)
      keyCodeStrings[i] = String.valueOf( (char)('A'+i-BKeyEvent.VK_A) );
    for(int i=BKeyEvent.VK_0; i<=BKeyEvent.VK_9; ++i)
      keyCodeStrings[i] = String.valueOf( (char)('0'+i-BKeyEvent.VK_0) );
    for(int i=BKeyEvent.VK_F1; i<=BKeyEvent.VK_F12; ++i)
      keyCodeStrings[i] = "F" + (i-BKeyEvent.VK_F1+1);
    keyCodeStrings[BKeyEvent.VK_SLASH]  = "/";
    keyCodeStrings[BKeyEvent.VK_UP]     = "Up";
    keyCodeStrings[BKeyEvent.VK_DOWN]   = "Down";
    keyCodeStrings[BKeyEvent.VK_LEFT]   = "Left";
    keyCodeStrings[BKeyEvent.VK_RIGHT]  = "Right";
    keyCodeStrings[BKeyEvent.VK_HOME]   = "Home";
    keyCodeStrings[BKeyEvent.VK_END]    = "End";
    keyCodeStrings[BKeyEvent.VK_INSERT] = "Insert";
    keyCodeStrings[BKeyEvent.VK_DELETE] = "Delete";
    keyCodeStrings[BKeyEvent.VK_PAGE_UP]    = "PageUp";
    keyCodeStrings[BKeyEvent.VK_PAGE_DOWN]  = "PageDown";
    keyCodeStrings[BKeyEvent.VK_BACK_SPACE] = "Backspace";
    keyCodeStrings[BKeyEvent.VK_TAB]        = "Tab";
    keyCodeStrings[BKeyEvent.VK_SPACE]      = "Space";
    
  }

  private int modifiers;
  private int keyCode;
  private String string;
  
}
