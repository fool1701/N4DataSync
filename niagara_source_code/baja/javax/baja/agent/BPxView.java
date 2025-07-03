/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.naming.*;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BPxView is a BAbstractPxView which stores 
 * the view contents in an XML file with a px extension.  The 
 * view itself is defined as a tree of bajaui:Widgets.
 *
 * @author    Brian Frank
 * @creation  8 May 04
 * @version   $Revision: 9$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Defaults the px file which contains the view
 */
@NiagaraProperty(
  name = "pxFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, \"file:PxFile\")")
)
public class BPxView
  extends BAbstractPxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.agent.BPxView(3124616471)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pxFile"

  /**
   * Slot for the {@code pxFile} property.
   * Defaults the px file which contains the view
   * @see #getPxFile
   * @see #setPxFile
   */
  public static final Property pxFile = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "file:PxFile"));

  /**
   * Get the {@code pxFile} property.
   * Defaults the px file which contains the view
   * @see #pxFile
   */
  public BOrd getPxFile() { return (BOrd)get(pxFile); }

  /**
   * Set the {@code pxFile} property.
   * Defaults the px file which contains the view
   * @see #pxFile
   */
  public void setPxFile(BOrd v) { set(pxFile, v, null); }

  //endregion Property "pxFile"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with all fields.
   */
  public BPxView(BIcon icon, BOrd ord, BPermissions permissions, BTypeSpec media)
  {                
    super(icon, permissions, media);
    setPxFile(ord);
  }

  /**
   * Constructor with PxFile ord.
   */
  public BPxView(BOrd ord)
  {                      
    setPxFile(ord);
  }
  
  /**
   * No argument constructor.
   */
  public BPxView()
  {
  }
}
