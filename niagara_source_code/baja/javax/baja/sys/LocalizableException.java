/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.text.*;                       
import javax.baja.util.*;

/**
 * LocalizableException is an exception which uses a 
 * lexicon key to derive a localized message.
 *
 * @author    Brian Frank
 * @creation  23 Jul 02
 * @version   $Revision: 3$ $Date: 2/10/05 3:42:11 PM EST$
 * @since     Baja 1.0 
 */
public class LocalizableException
  extends BajaException
  implements Localizable
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor which uses the specified module name, 
   * lexicon key, and args to format a localized message.
   */
  public LocalizableException(String lexiconModule, String lexiconKey, 
                              Object[] lexiconArgs, Throwable cause)
  {  
    super(format(lexiconModule, lexiconKey, lexiconArgs, null), cause);
    this.lexiconModule = lexiconModule;
    this.lexiconKey    = lexiconKey;
    this.lexiconArgs   = lexiconArgs;
  }

  /**
   * Convenience for {@code LocalizableException(lexiconModule, lexiconKey, lexiconArgs, null)}
   */
  public LocalizableException(String lexiconModule, String lexiconKey, Object[] lexiconArgs)
  {  
    this(lexiconModule, lexiconKey, lexiconArgs, null);
  }

  /**
   * Convenience for {@code LocalizableException(lexiconModule, lexiconKey, null, cause)}
   */
  public LocalizableException(String lexiconModule, String lexiconKey, Throwable cause)
  {  
    this(lexiconModule, lexiconKey, null, cause);
  }

  /**
   * Convenience for {@code LocalizableException(lexiconModule, lexiconKey, null, null)}
   */
  public LocalizableException(String lexiconModule, String lexiconKey)
  {  
    this(lexiconModule, lexiconKey, null, null);
  }

  /**
   * Convenience for {@code LocalizableException(lexicon.module, lexiconKey, null, null)}
   */
  public LocalizableException(Lexicon lexicon, String lexiconKey)
  {  
    this(lexicon.module.getModuleName(), lexiconKey, null, null);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the module name of the lexicon to use.
   */
  public String getLexiconModule()
  {
    return lexiconModule;
  }

  /**
   * Get the lexicon key.
   */
  public String getLexiconKey()
  {
    return lexiconKey;
  }

  /**
   * Get the lexicon arguments used to format the message.
   */
  public Object[] getLexiconArguments()
  {
    return lexiconArgs;
  }
  
  /**
   * To localized string.
   */
  @Override
  public String toString(Context context)
  {
    return format(lexiconModule, lexiconKey, lexiconArgs, context);
  }

  /**
   * Format the exception using the specified lexicon input.
   */
  public static String format(String lexiconModule, String lexiconKey, Object[] lexiconArgs, Context context)
  {
    try
    {
      String value = Lexicon.make(lexiconModule, context).get(lexiconKey, null);
      if (value != null)
        return MessageFormat.format(value, lexiconArgs);
      
      StringBuilder s = new StringBuilder(lexiconKey);
      if (lexiconArgs != null)
      {
        s.append(" {");
        for(int i=0; i<lexiconArgs.length; ++i)
        {
          if (i > 0) s.append(", ");
          s.append(lexiconArgs[i]);
        }
        s.append(" }");
      }
      
      return s.toString();
    }
    catch(Throwable e)
    {
      // no matter how bad we mess this up, don't 
      // raise a new meaningless exception
      e.printStackTrace();
      return lexiconKey;
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String lexiconModule;
  private String lexiconKey;
  private Object[] lexiconArgs;
  
}
