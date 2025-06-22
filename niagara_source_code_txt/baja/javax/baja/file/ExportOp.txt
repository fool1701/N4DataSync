/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.*;
import javax.baja.naming.*;
import javax.baja.sys.BFacets;

/**
 * ExportOp encapsulates all the information needed to process
 * a source BObject accessed via {@code get()} into a file
 * using {@code getOutputStream()}.
 *
 * @author Brian Frank on 22 May 04
 * @since Baja 1.0
 */
public abstract class ExportOp
  extends OrdTarget
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create a new instance of an ExportOp.
   */
  public static ExportOp make(OrdTarget from, OutputStream to)
  {
    return new BasicExportOp(from, to);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with source OrdTarget.
   */
  protected ExportOp(OrdTarget base)
  {
    super(base);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the output stream used to write the file.
   */
  public abstract OutputStream getOutputStream();

  /**
   * Get the facets which originates from context passed to BOrd.resolve().
   */
  @Override
  public BFacets getFacets()
  {
    return BFacets.make(super.getFacets(), facets);
  }

  /**
   * Mutate the existing ExportOp by merging additional facets and return the existing ExportOp.
   *
   * @since Niagara 4.8
   */
  public ExportOp mergeFacets(BFacets newFacets)
  {
    facets = BFacets.make(facets, newFacets);
    return this;
  }

////////////////////////////////////////////////////////////////
// BasicExportOp
////////////////////////////////////////////////////////////////

  /**
   * BasicExportOp is a simple predefined implementation
   * of ExportOp used by the make factory methods.
   */
  static class BasicExportOp
    extends ExportOp
  {
    BasicExportOp(OrdTarget base, OutputStream out)
    {
      super(base);
      this.out = out;
    }

    @Override
    public OutputStream getOutputStream()
    {
      return out;
    }

    OutputStream out;
  }

  private BFacets facets = BFacets.NULL;
}
