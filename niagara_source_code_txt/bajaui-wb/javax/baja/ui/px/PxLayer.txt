/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.px;

import javax.baja.naming.*;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.util.*;

/**
 * PxLayer.
 *
 * @author    Mike Jarmy
 * @creation  13 Jul 09
 * @version   $Revision: 1$ $Date: 9/10/09 2:02:31 PM EDT$
 * @since     Niagara 3.3
 */
public class PxLayer
{
  /**
   * Convenience for PxLayer(name, BLayerStatus.normal)
   */
  public PxLayer(String name)
  {
    this(name, BLayerStatus.normal);
  }

  public PxLayer(String name, BLayerStatus status)
  {
    this.name = name;
    this.status = status;
  }

  public String toString()
  {
    return "PxLayer{" + name + "}";
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String getName() { return name; }
  public BLayerStatus getStatus() { return status; }

  public void setName(String name) { this.name = name; }
  public void setStatus(BLayerStatus status) { this.status = status; }

//////////////////////////////////////////////////////////////////////////
// Fields
//////////////////////////////////////////////////////////////////////////

  private String name;
  private BLayerStatus status;
}
