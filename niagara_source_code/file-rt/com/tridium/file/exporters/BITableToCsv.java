/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.exporters;

import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.TableCursor;
import javax.baja.file.BExporter;
import javax.baja.file.ExportOp;
import javax.baja.file.types.text.BCsvFile;
import javax.baja.io.BIEncodable;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.BNumber;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.BUnit;
import javax.baja.units.BUnitConversion;

import com.tridium.sys.Nre;

/**
 * BITableToCsv.
 *
 * @author Brian Frank on 21 Jul 04
 * @since Baja 1.0
 */

@NiagaraType(
  agent = @AgentOn(
    types = "baja:ITable",
    requiredPermissions = "r"
  )
)
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
@NiagaraProperty(
  name = "includeHeaders",
  type = "boolean",
  defaultValue = "true"
)
/*
 set to false to omit the UTF-8
 */
@NiagaraProperty(
  name = "includeBOM",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "encodeToString",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "useCRLF",
  type = "boolean",
  defaultValue = "false",
  facets = {
    @Facet(name = "BFacets.TRUE_TEXT", value = "\"%lexicon(file:useCRLF.trueText)%\""),
    @Facet(name = "BFacets.FALSE_TEXT", value = "\"%lexicon(file:useCRLF.falseText)%\"")
  }
)
@NiagaraProperty(
  name = "delimiter",
  type = "String",
  defaultValue = ",",
  facets = @Facet(name = "BFacets.FIELD_WIDTH", value = "4")
)
@SuppressWarnings("WeakerAccess")

public class BITableToCsv
  extends BExporter
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.exporters.BITableToCsv(3571843042)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "includeHeaders"

  /**
   * Slot for the {@code includeHeaders} property.
   * @see #getIncludeHeaders
   * @see #setIncludeHeaders
   */
  public static final Property includeHeaders = newProperty(0, true, null);

  /**
   * Get the {@code includeHeaders} property.
   * @see #includeHeaders
   */
  public boolean getIncludeHeaders() { return getBoolean(includeHeaders); }

  /**
   * Set the {@code includeHeaders} property.
   * @see #includeHeaders
   */
  public void setIncludeHeaders(boolean v) { setBoolean(includeHeaders, v, null); }

  //endregion Property "includeHeaders"

  //region Property "includeBOM"

  /**
   * Slot for the {@code includeBOM} property.
   * set to false to omit the UTF-8
   * @see #getIncludeBOM
   * @see #setIncludeBOM
   */
  public static final Property includeBOM = newProperty(0, true, null);

  /**
   * Get the {@code includeBOM} property.
   * set to false to omit the UTF-8
   * @see #includeBOM
   */
  public boolean getIncludeBOM() { return getBoolean(includeBOM); }

  /**
   * Set the {@code includeBOM} property.
   * set to false to omit the UTF-8
   * @see #includeBOM
   */
  public void setIncludeBOM(boolean v) { setBoolean(includeBOM, v, null); }

  //endregion Property "includeBOM"

  //region Property "encodeToString"

  /**
   * Slot for the {@code encodeToString} property.
   * @see #getEncodeToString
   * @see #setEncodeToString
   */
  public static final Property encodeToString = newProperty(0, false, null);

  /**
   * Get the {@code encodeToString} property.
   * @see #encodeToString
   */
  public boolean getEncodeToString() { return getBoolean(encodeToString); }

  /**
   * Set the {@code encodeToString} property.
   * @see #encodeToString
   */
  public void setEncodeToString(boolean v) { setBoolean(encodeToString, v, null); }

  //endregion Property "encodeToString"

  //region Property "useCRLF"

  /**
   * Slot for the {@code useCRLF} property.
   * @see #getUseCRLF
   * @see #setUseCRLF
   */
  public static final Property useCRLF = newProperty(0, false, BFacets.make(BFacets.make(BFacets.TRUE_TEXT, "%lexicon(file:useCRLF.trueText)%"), BFacets.make(BFacets.FALSE_TEXT, "%lexicon(file:useCRLF.falseText)%")));

  /**
   * Get the {@code useCRLF} property.
   * @see #useCRLF
   */
  public boolean getUseCRLF() { return getBoolean(useCRLF); }

  /**
   * Set the {@code useCRLF} property.
   * @see #useCRLF
   */
  public void setUseCRLF(boolean v) { setBoolean(useCRLF, v, null); }

  //endregion Property "useCRLF"

  //region Property "delimiter"

  /**
   * Slot for the {@code delimiter} property.
   * @see #getDelimiter
   * @see #setDelimiter
   */
  public static final Property delimiter = newProperty(0, ",", BFacets.make(BFacets.FIELD_WIDTH, 4));

  /**
   * Get the {@code delimiter} property.
   * @see #delimiter
   */
  public String getDelimiter() { return getString(delimiter); }

  /**
   * Set the {@code delimiter} property.
   * @see #delimiter
   */
  public void setDelimiter(String v) { setString(delimiter, v, null); }

  //endregion Property "delimiter"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BITableToCsv.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Exporter
////////////////////////////////////////////////////////////////

  @Override
  public TypeInfo getFileType()
  {
    return BCsvFile.TYPE.getTypeInfo();
  }

  @Override
  public String getFileExtension()
  {
    return "csv";
  }

  /**
   * When the getUseCRLF Property is true use '\r\n'.
   * When false, use '\n'.
   *
   * @since Niagara 4.8
   */
  public String getLineEnding()
  {
    return getUseCRLF() ? "\r\n" : "\n";
  }

  @Override
  public void export(ExportOp op)
    throws Exception
  {
    // get output writer
    PrintWriter out = new PrintWriter(op.getOutputStream());

    // Prepend a byte order mark to indicate we're using UTF-8
    // Certain applications won't recognize it as UTF-8 otherwise,
    // and may contain garbage characters.
    if (getIncludeBOM())
    {
      out.print("\uFEFF");
      out.flush();
    }

    // target is ITable      
    export((BITable) op.get(), op);
  }

  public void export(BITable<?> table, ExportOp op)
    throws Exception
  {
    if (this.delimiterContainsFunctionCharacter(getDelimiter())) {
      throw new IllegalArgumentException("+, -, =, @ cannot be part of delimiter");
    }
    op.mergeFacets(getFacets());

    // get output writer
    PrintWriter out = new PrintWriter(op.getOutputStream());

    Column[] cols = table.getColumns().list();
    int colCount = cols.length;

    BFacets defaultFacets = BFacets
      .make(BFacets.UNIT_CONVERSION, Nre.unitConversion);

    // column facets
    Context[] colCx = new Context[colCount];
    for (int i = 0; i < colCx.length; i++)
    {
      //ensure that the ExportOp facets overrule any column facets
      // which in turn overrides the table facets,
      // which in turn overrides the default facets from workbench
      colCx[i] = new BasicContext(op, BFacets.make(defaultFacets,
        BFacets.make(table.getTableFacets(),
        BFacets.make(cols[i].getFacets(), op.getFacets()))));
    }

    if (getIncludeHeaders())
    {
      // write header (don't use quotes, but make sure we don't
      // accidentally have a name containing a comma or quote)

      for (int i = 0; i < colCount; ++i)
      {
        String name = this.toHeaderDisplayName(cols[i], colCx[i]);

        if (i > 0)
        {
          out.print(getDelimiter());
        }

        name = escapeFormulas(name);

        if (LEGACY_CSV_ENCODING)
        {

          // write header (don't use quotes, but make sure we don't
          // accidentally have a name containing a comma or quote)
          name = name.replace(',', '_');  // no commas
          name = name.replace('"', '\''); // no
          out.print(name);
        }
        else
        {
          printValue(name, out);
        }
      }
      out.print(getLineEnding());
    }

    // Update the column contexts to not show units in the data rows
    for (int i = 0; i < colCx.length; i++)
    {
      colCx[i] = new BasicContext(colCx[i], BFacets.make(BFacets.SHOW_UNITS, BBoolean.FALSE));
    }

    // decide which cells should be double quoted (we don't want to 
    // double quote everything just to make it a little prettier)
    boolean[] quote = new boolean[colCount];
    for (int i = 0; i < colCount && LEGACY_CSV_ENCODING; ++i)
    {
      Type type = cols[i].getType();
      quote[i] = !type.is(BNumber.TYPE) &&
        !type.is(BBoolean.TYPE) &&
        !type.is(BRelTime.TYPE) &&
        !type.is(BAbsTime.TYPE);
    }

    // write rows  
    TableCursor<?> cursor = table.cursor();
    while (cursor.next())
    {
      for (int i = 0; i < colCount; ++i)
      {
        // get cell        
        BObject cell = cursor.cell(cols[i]).as(BObject.class);

        // get the text of the cell
        String text;

        if (encodeToString(cell))
        {
          text = ((BIEncodable) cell).encodeToString();
        }
        else
        {
          text = cell.toString(colCx[i]);
        }

        // write the cell (make sure to escape double quotes)
        if (i != 0)
        {
          out.print(getDelimiter());
        }

        text = escapeFormulas(text);

        if (LEGACY_CSV_ENCODING)
        {
          if (quote[i])
          {
            out.print('"');
            out.print(TextUtil.replace(text, "\"", "\"\""));
            out.print('"');
          }
          else if (text.indexOf(',') >= 0)
          {
            out.print('"');
            out.print(text);
            out.print('"');
          }
          else
          {
            out.print(text);
          }
        }
        else
        {
          printValue(text, out);
        }
      }
      out.print(getLineEnding());
    }
    out.flush();
  }

  final protected boolean delimiterContainsFunctionCharacter(String delimiter) {
    final String allExceptFirst = delimiter.substring(1);
    return allExceptFirst.contains("=") ||
            allExceptFirst.contains("+") ||
            allExceptFirst.contains("-") ||
            allExceptFirst.contains("@");
  }

  // NCCB-42917: If text begins with a formula character, prepend a space
  final protected String escapeFormulas(String text)
  {
    if (PREVENT_CSV_INJECTION) {
      String results = text;
      if (text.length() > 0) {
        final char c = text.charAt(0);
        if (c == '=' || c == '@' || c == '+' || c == '-') {
          results = " " + text;
        }
      }
      return results;
    }
    return text;
  }

  private String toHeaderDisplayName(Column col, Context context)
  {
    //include unit information with column name
    BFacets colFacets = context.getFacets();
    BUnit units = (BUnit) colFacets.get(BFacets.UNITS);
    BObject sName = colFacets.get("SERIES_NAME");
    int colConvert = colFacets.geti(BFacets.UNIT_CONVERSION, 0);
    BUnitConversion colConversion = BUnitConversion.make(colConvert);

    String name = col.getDisplayName(context);
    if (null != sName)
    {
      name = sName.toString(context); //": " + name;
    }
    if (null != units && units != BUnit.NULL)
    {
      units = colConversion.getDesiredUnit(units);

      name += " (" + units.toString(context) + ")";
    }

    return name;
  }

  /**
   * Decide whether to encodeToString:
   * 1) Only BIEncodables can encodeToString
   * 2) In LEGACY_CSV_ENCODING Mode AbsTime, Unit, and Numbers will use BObject.toString
   * 3) Otherwise use the Csv Option for 'encodeToString'
   * @since Niagara 4.8
   */
  protected boolean encodeToString(BObject cell) throws Exception
  {
    if(!(cell instanceof BIEncodable))
    {
      //encodeToString only applies to BIEncodables
      return false;
    }

    if(LEGACY_CSV_ENCODING && (cell.getType() == BAbsTime.TYPE || cell.getType() == BUnit.TYPE
      || cell.getType().is(BNumber.TYPE)))
    {
      // Fix issue 7676 - BAbsTime's should use toString(cx)
      // so the appropriate timezone will be used!
      // Fix issue 16599 - BUnit should use toString to get the symbol (e.g. %)
      // and not the encoded string (e.g. percent;%;;;)

      //in legacy mode, don't encodeToString for AbsTime, Unit, or Numbers
      return false;
    }

    return getEncodeToString();
  }

  /**
   * Print the String value to the csv PrintWriter. Escape any delimiters, lineEndings,
   * or double quotes as specified in the csv mime type:
   * <a href='https://www.ietf.org/rfc/rfc4180.txt'>RFC 4180</a>
   *
   * @since Niagara 4.8
   */
  public void printValue(String text, PrintWriter out)
  {
    boolean hasDoubleQuote = text.contains("\"");
    if (hasDoubleQuote || text.contains(getDelimiter()) || text.contains(getLineEnding()))
    {
      out.print("\"");
      if (hasDoubleQuote)
      {
        text = TextUtil.replace(text, "\"", "\"\""); //duplicate any double quotes as specified in https://tools.ietf.org/html/rfc4180
      }
      out.print(text);
      out.print("\"");
    }
    else
    {
      out.print(text);
    }
  }


  /**
   * Enabling LEGACY_CSV_ENCODING brings back the non-standard CSV export behavior in Niagara 4.7-
   */
  public static final boolean LEGACY_CSV_ENCODING = AccessController.doPrivileged((PrivilegedAction<Boolean>)
    () -> Boolean.getBoolean("niagara.export.legacyCsvEncoding"));

  public static final boolean PREVENT_CSV_INJECTION = AccessController.doPrivileged((PrivilegedAction<Boolean>)
          () -> {
              String property = System.getProperty("niagara.export.preventCSVInjection");
              if (property == null) {
                return true;
              }
              return Boolean.parseBoolean(property);
          });

}
