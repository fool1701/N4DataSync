/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * LocalizableRuntimeException is an exception which uses a 
 * lexicon key to derive a localized message.
 *
 * @author    Brian Frank
 * @creation  23 Jul 02
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:13 AM EST$
 * @since     Baja 1.0 
 */
public class LocalizableRuntimeException
  extends BajaRuntimeException
  implements Localizable
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor which uses the specified module name, 
   * lexicon key, and args to format a localized message.
   */
  public LocalizableRuntimeException(String lexiconModule, String lexiconKey, 
                                     Object[] lexiconArgs, Throwable cause)
  {  
    super(LocalizableException.format(lexiconModule, lexiconKey, lexiconArgs, null), cause);
    this.lexiconModule = lexiconModule;
    this.lexiconKey    = lexiconKey;
    this.lexiconArgs   = lexiconArgs;
  }

  /**
   * Convenience for {@code LocalizableRuntimeException(lexiconModule, lexiconKey, lexiconArgs, null)}
   */
  public LocalizableRuntimeException(String lexiconModule, String lexiconKey, Object[] lexiconArgs)
  {  
    this(lexiconModule, lexiconKey, lexiconArgs, null);
  }

  /**
   * Convenience for {@code LocalizableRuntimeException(lexiconModule, lexiconKey, null, cause)}
   */
  public LocalizableRuntimeException(String lexiconModule, String lexiconKey, Throwable cause)
  {  
    this(lexiconModule, lexiconKey, null, cause);
  }

  /**
   * Convenience for {@code LocalizableRuntimeException(lexiconModule, lexiconKey, null, null)}
   */
  public LocalizableRuntimeException(String lexiconModule, String lexiconKey)
  {  
    this(lexiconModule, lexiconKey, null, null);
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
    return LocalizableException.format(lexiconModule, lexiconKey, lexiconArgs, context);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String lexiconModule;
  private String lexiconKey;
  private Object[] lexiconArgs;
  
}
