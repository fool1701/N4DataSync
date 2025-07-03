/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.naming.*;
import javax.baja.ui.event.*;

/**
 * HyperlinkInfo stores the information used to perform 
 * a hyperlink within a BIHyperlinkShell.
 *
 * @author    Brian Frank       
 * @creation  27 Oct 03
 * @version   $Revision: 5$ $Date: 12/7/06 2:15:33 PM EST$
 * @since     Baja 1.0
 */
public class HyperlinkInfo
{    

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor with ord and mode.
   */
  public HyperlinkInfo(BOrd ord, BHyperlinkMode mode)
  {                     
    if (ord == null || ord.isNull())
      throw new IllegalArgumentException("ord is null");
                
    this.ord  = ord;
    this.mode = mode;
  }

  /**
   * Convenicence for <code>HyperlinkInfo(ord, BHyperlinkMode.make(event))</code>.
   */
  public HyperlinkInfo(BOrd ord, BInputEvent event)
  {                               
    this(ord, BHyperlinkMode.make(event));
  }

  /**
   * Convenicence for <code>HyperlinkInfo(ord, HyperlinkMode.replace)</code>.
   */
  public HyperlinkInfo(BOrd ord)
  {                                                          
    this(ord, BHyperlinkMode.replace);
  }               
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the ord which is the destination of the hyperlink.
   */
  public BOrd getOrd() { return ord; }

  /**
   * Get the mode of the hyperlink.
   */
  public BHyperlinkMode getMode() { return mode; }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected BOrd ord;
  protected BHyperlinkMode mode;
  
}
