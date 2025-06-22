/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.exporters;

import java.io.PrintWriter;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.file.BExporter;
import javax.baja.file.ExportOp;
import javax.baja.file.types.text.BHtmlFile;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.xml.XWriter;

/**
 * BITableToHtml.
 *
 * @author    Lee Adcock       
 * @creation  20 Jul 09
 * @version   $Revision: 5$ $Date: 6/27/11 11:41:17 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:ITable",
    requiredPermissions = "r"
  )
)
public class BITableToHtml
  extends BExporter
{                 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.exporters.BITableToHtml(726150234)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BITableToHtml.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Exporter
////////////////////////////////////////////////////////////////

  @Override
  public TypeInfo getFileType()
  {
    return BHtmlFile.TYPE.getTypeInfo();
  }

  @Override
  public String getFileExtension()
  {
    return "html";
  }

  @Override
  public void export(ExportOp op)
      throws Exception
  {
    // target is ITable      
    export((BITable)op.get(), op);
  }

  public String getTitle(ExportOp op)
  {
    return "Table";
  }
  
  public void export(BITable<?> table, ExportOp op)
    throws Exception
  {            
    // get output writer
    PrintWriter out = new PrintWriter(op.getOutputStream());                 
    
    out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>"+getTitle(op)+"</title>");
    out.println("<style type='text/css'>");
    out.println("body {font: 10pt verdana;}");
    out.println("table {border: 0px; border-spacing: 0px;}");
    out.println("th {white-space: nowrap; text-align:left; padding: 1px 8px; background: #BFBFBF;}");
    out.println("td {white-space: nowrap; padding: 1px 8px; margin: 0px;}");      
    out.println(".odd {background:#F7F7F7}");      
    out.println(".fill {width:100%;}");      
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<table class='fill'>");
    out.println("<tr>");
    
    Column[] cols = table.getColumns().list();
    int colCount = cols.length;
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
      
      out.print("<th>");
      XWriter.safe(out, name, true);
      out.println("</th>");      
    }
    out.println("<th class='fill'></td>");      
    out.println("</tr>");

    // column facets
    Context[] columnContext = new Context[colCount];
    for (int i=0; i<columnContext.length; i++)
    {
      columnContext[i] = new BasicContext(op, cols[i].getFacets());
    }
    
    // write rows  
    TableCursor<?> cursor = table.cursor();
    int row = 0;
    while (cursor.next())
    {   
      if(row++ %2==0)
      {
        out.println("<tr class='odd'>");
      }
      else
      {
        out.println("<tr class='even'>");
      }
      
      for(int i=0; i<colCount; ++i)
      { 
        // get cell        
        BObject cell = cursor.cell(cols[i]).as(BObject.class);
        
        // get the text of the cell
        String text;     
        text = cell.toString(columnContext[i]);
        
        out.print("<td>");        
        XWriter.safe(out, text, true);
        out.println("</td>");        
      }
      out.println("<td class='fill'></td>");
      out.println("</tr>");
    }
    out.println("</table>");
    out.println("</body>");
    out.println("</html>");
    out.flush();
  }
}
