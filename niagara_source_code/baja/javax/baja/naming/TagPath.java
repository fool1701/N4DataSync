/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import com.tridium.util.EscUtil;

/**
 * TagPath is a tag scheme for resolving tag values. 
 * The BNF is:
 * <pre>
 *   slotpath  := namespace | tag
 *   namespace := "" | name
 *   tag       := name
 *   name      := nameStart (namePart)*
 *
 *   nameStart  := alpha | escape
 *   namePart   := alpha | digit | safe | escape
 *   safe       := "_"
 *   alpha      := "a"-"z" | "A-Z"
 *   digit      := "0"-"9"
 *   escape     := asciiEsc | unicodeEsc
 *   asciiEsc   := "$" hex hex
 *   unicodeEsc := "$u" hex hex hex hex
 *   hex        := 'a'-'f' | 'A'-'F' | digit
 * </pre>
 *
 * @author    Lee Adcock
 * @creation  June 2012
 * @version   $Revision: 21$ $Date: 11/30/06 6:08:15 PM EST$
 * @since     Niagara 4.0
 */
public class TagPath
  implements OrdQuery
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an SlotPath with the specified scheme and body.
   *
   * @throws SyntaxException if the body isn't a valid file path.
   */
  public TagPath(String body)
    throws SyntaxException
  {
    int index = body.indexOf(':');
    if(index>0)
    {
      // namespace:tag
      namespace = body.substring(0, index);
      tag = body.substring(index);
    } if(index==0) {
      // :tag (why would you do this?)
      namespace = null;
      tag = body.substring(1);
    } else {
      // tag
      namespace = null;
      tag = body;
    }
  }  
  
  public TagPath(String namespace, String tag)
    throws SyntaxException
  {
    this.namespace = namespace;
    this.tag = tag;
  }

////////////////////////////////////////////////////////////////
// Escape / Unescape
////////////////////////////////////////////////////////////////  

  /**
   * Does the specified string contain a tag name.
   */
  protected boolean isValidTagName(String name)
  {   
    return EscUtil.slot.isValid(name);
  }
  
  /**
   * Does the specified string contain a tag name.
   */
  protected boolean isValidNamespace(String namespace)
  {   
    if(namespace==null || namespace.isEmpty())
      return true;
    return EscUtil.slot.isValid(namespace);
  }
  
  /**
   * Escape the specified string.
   */
  public static String escape(String s)
  {
    return EscUtil.slot.escape(s);
  }

  /**
   * Unescape the specified string.
   */
  public static String unescape(String s)
  {                            
    return EscUtil.slot.unescape(s);
  }

////////////////////////////////////////////////////////////////
// OrdQuery
////////////////////////////////////////////////////////////////

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
   * Return the scheme field.
   */
  @Override
  public String getScheme()
  {
    return "tag";
  }

  /**
   * Return the body field.
   */
  @Override
  public String getBody()
  {
    if(namespace==null || namespace.isEmpty())
      return tag;
    else
      return namespace+":"+tag;
  }

  @Override
  public void normalize(OrdQueryList list, int index)
  {
  }
  
  /**
   * Return the body with path names unescaped.
   */
  public String toDisplayString()
  {
    if(namespace==null || namespace.isEmpty())
      return unescape(tag);
    else
      return unescape(namespace)+":"+unescape(tag);
  }
  
  /**
   * Return {@code scheme + ":" + body}.
   */  
  public String toString()
  {
    if(namespace==null || namespace.isEmpty())
      return tag;
    else
      return namespace+":"+tag;
  }
  
////////////////////////////////////////////////////////////////
// Tag Path
////////////////////////////////////////////////////////////////  

  public String getNamespace()
  {
    return namespace;
  }

  public String getTag()
  {
    return tag;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String namespace;
  private String tag;
    
}

