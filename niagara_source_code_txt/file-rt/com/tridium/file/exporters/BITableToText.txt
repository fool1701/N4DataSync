/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.exporters;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.file.BExporter;
import javax.baja.file.ExportOp;
import javax.baja.file.types.text.BTextFile;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;

/**
 * BITableToText.
 *
 * @author    Brian Frank       
 * @creation  20 Oct 04
 * @version   $Revision: 6$ $Date: 2/11/11 12:32:01 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:ITable",
    requiredPermissions = "r"
  )
)
@NiagaraProperty(
  name = "columnSeparator",
  type = "String",
  defaultValue = ""
)
public class BITableToText
  extends BExporter
{             
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.exporters.BITableToText(3299546897)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "columnSeparator"

  /**
   * Slot for the {@code columnSeparator} property.
   * @see #getColumnSeparator
   * @see #setColumnSeparator
   */
  public static final Property columnSeparator = newProperty(0, "", null);

  /**
   * Get the {@code columnSeparator} property.
   * @see #columnSeparator
   */
  public String getColumnSeparator() { return getString(columnSeparator); }

  /**
   * Set the {@code columnSeparator} property.
   * @see #columnSeparator
   */
  public void setColumnSeparator(String v) { setString(columnSeparator, v, null); }

  //endregion Property "columnSeparator"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BITableToText.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Exporter
////////////////////////////////////////////////////////////////

  @Override
  public TypeInfo getFileType()
  {
    return BTextFile.TYPE.getTypeInfo();
  }

  @Override
  public String getFileExtension()
  {
    return "txt";
  }

  @Override
  public void export(ExportOp op)
    throws Exception
  {            
    // target is ITable      
    export((BITable)op.get(), op);
  }
  
  public void export(BITable<?> table, ExportOp op)
    throws Exception
  {                   
    // build array of Rows
    //Array rows = new Array();
    //Replaced the above Array with ArrayList
    ArrayList<Row> rows = new ArrayList<>();

    // add column row
    Column[] cols   = table.getColumns().list();
    int colCount    = cols.length;
    int[] maxWidth  = new int[colCount];
    Row header = new Row(colCount);
    for(int i=0; i<colCount; ++i)
    {             
      //include unit information with column name
      BFacets colFacets = cols[i].getFacets();
      BUnit units = null;
      BObject sName = null;
      if( null != colFacets)
      {
        units = (BUnit)colFacets.get(BFacets.UNITS);
        sName = colFacets.get("SERIES_NAME");
      }
      String name = cols[i].getDisplayName(op);
      if( null != sName)
      {
        name = sName.toString(op); //": " + name;
      }
      if( null != units && units != BUnit.NULL)
      {
        name += " (" + units.toString(op) + ")";
      }
      
      Cell cell = new Cell(name);
      header.cells[i] = cell;
      maxWidth[i] = cell.width;
    }                                  
    rows.add(header);
    
    // column facets
    Context[] colCx = new Context[colCount];
    for (int i=0; i<colCx.length; i++)
    {
      colCx[i] = new BasicContext(op, cols[i].getFacets());
    }
        
    // add data rows  
    TableCursor<?> cursor = table.cursor();
    while (cursor.next())
    {              
      Row row = new Row(colCount);
      for(int i=0; i<colCount; ++i)
      {            
        BObject object = cursor.cell(cols[i]).as(BObject.class);
        String s = object.toString(colCx[i]); 
        Cell cell = new Cell(s);
        row.cells[i] = cell;
        maxWidth[i] = Math.max(maxWidth[i], cell.width);
      }        
      rows.add(row);
    }
    
    // now output                            
    PrintWriter out = new PrintWriter(new OutputStreamWriter(op.getOutputStream(), "UTF-8"));
    String spacer = " " + getColumnSeparator() + " ";
    for(int i=0; i<rows.size(); ++i)
    {                 
      Row row = rows.get(i);
      row.print(out, maxWidth, i == 0, spacer);
    }              
    out.flush();
  }   

  static String spaces(int n)
  {             
    return TextUtil.getSpaces(n);
  }

  static class Row
  {            
    Row(int colCount)
    {
      cells = new Cell[colCount];
    }      
    
    public void print(PrintWriter out, int[] maxWidth, boolean header, String spacer)
    {                
      int colCount = maxWidth.length;
      
      // first compute the number of lines of cells combined
      int lineCount = 0;
      for(int i=0; i<colCount; ++i)
      {
        lineCount = Math.max(cells[i].lines.length, lineCount);
      }
      
      // now output the lines
      for(int line=0; line<lineCount; ++line)
      {
        for(int i=0; i<colCount; ++i)
        {
          Cell cell = cells[i];
          if (line >= cell.lines.length) 
          {
            out.print(spaces(maxWidth[i]));
          }
          else
          {                    
            String s = cell.lines[line];
            out.print(s);
            out.print(spaces(maxWidth[i]-s.length()));
          }
          if (i < colCount-1)
          {
            out.print(spacer);
          }
        }
        out.println();
      }  
      
      // underline header
      if (header)
      {
        for(int i=0; i<colCount; ++i)
        {                            
          for(int x=0; x<maxWidth[i]; ++x)
          {
            out.print('-');
          }
          if (i < colCount-1)
          {
            out.print(spacer);
          }
        }
        out.println();
      } 
    }   
    
    Cell[] cells;
  }
  
  static class Cell
  {                
    Cell(String s)
    {                   
      lines = TextUtil.split(s, '\n');
      for(int i=0; i<lines.length; ++i)
      {
        width = Math.max(width, lines[i].length());
      }
    }
    
    String[] lines;
    int width;
  }               
  
  static final int pad = 2;
    
}
