/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rdb.point;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.collection.TableCursor;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BStringPoint;
import javax.baja.driver.point.BProxyExt;
import javax.baja.driver.point.BReadWriteMode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.rdb.BRdbmsNetwork;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.status.BStatusNumeric;
import javax.baja.status.BStatusString;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BNumber;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.collection.BFilteredTable;
import com.tridium.rdb.BResultSetTable;

/**
 * Rdb implementation of BProxyExt
 *
 * @author Lee Adcock on 18 Dec 07
 * @version $Revision: 9$ $Date: 10/28/09 2:31:22 PM EDT$
 * @since Baja 1.0
 */
@NiagaraType
/*
 The column containing the requested value
 */
@NiagaraProperty(
  name = "valueColumn",
  type = "String",
  defaultValue = "",
  facets = @Facet("BFacets.make(BFacets.FIELD_EDITOR, \"rdb:RdbmsColumnNamePickerFE\")")
)
/*
 Value of data in key column
 */
@NiagaraProperty(
  name = "keyValue1",
  type = "String",
  defaultValue = ""
)
/*
 Value of data in key column (optional)
 */
@NiagaraProperty(
  name = "keyValue2",
  type = "String",
  defaultValue = ""
)
public class BRdbmsProxyExt
  extends BProxyExt
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.rdb.point.BRdbmsProxyExt(817929452)1.0$ @*/
/* Generated Thu Jun 02 14:30:05 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "valueColumn"

  /**
   * Slot for the {@code valueColumn} property.
   * The column containing the requested value
   * @see #getValueColumn
   * @see #setValueColumn
   */
  public static final Property valueColumn = newProperty(0, "", BFacets.make(BFacets.FIELD_EDITOR, "rdb:RdbmsColumnNamePickerFE"));

  /**
   * Get the {@code valueColumn} property.
   * The column containing the requested value
   * @see #valueColumn
   */
  public String getValueColumn() { return getString(valueColumn); }

  /**
   * Set the {@code valueColumn} property.
   * The column containing the requested value
   * @see #valueColumn
   */
  public void setValueColumn(String v) { setString(valueColumn, v, null); }

  //endregion Property "valueColumn"

  //region Property "keyValue1"

  /**
   * Slot for the {@code keyValue1} property.
   * Value of data in key column
   * @see #getKeyValue1
   * @see #setKeyValue1
   */
  public static final Property keyValue1 = newProperty(0, "", null);

  /**
   * Get the {@code keyValue1} property.
   * Value of data in key column
   * @see #keyValue1
   */
  public String getKeyValue1() { return getString(keyValue1); }

  /**
   * Set the {@code keyValue1} property.
   * Value of data in key column
   * @see #keyValue1
   */
  public void setKeyValue1(String v) { setString(keyValue1, v, null); }

  //endregion Property "keyValue1"

  //region Property "keyValue2"

  /**
   * Slot for the {@code keyValue2} property.
   * Value of data in key column (optional)
   * @see #getKeyValue2
   * @see #setKeyValue2
   */
  public static final Property keyValue2 = newProperty(0, "", null);

  /**
   * Get the {@code keyValue2} property.
   * Value of data in key column (optional)
   * @see #keyValue2
   */
  public String getKeyValue2() { return getString(keyValue2); }

  /**
   * Set the {@code keyValue2} property.
   * Value of data in key column (optional)
   * @see #keyValue2
   */
  public void setKeyValue2(String v) { setString(keyValue2, v, null); }

  //endregion Property "keyValue2"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRdbmsProxyExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  // Access
  ////////////////////////////////////////////////////////////////
  @Override
  public Type getDeviceExtType()
  {
    return BRdbmsPointDeviceExt.TYPE;
  }

  @Override
  public BReadWriteMode getMode()
  {
    return BReadWriteMode.readonly;
  }

  ////////////////////////////////////////////////////////////////
  // Callbacks
  ////////////////////////////////////////////////////////////////

  /**
   * Handle property change
   */
  @Override
  public void changed(Property prop, Context cx)
  {
    super.changed(prop, cx);

    if(!this.isRunning())
    {
      return;
    }

    if (prop.equals(BRdbmsProxyExt.enabled) ||
      prop.getName().startsWith("keyValue") ||
      prop.equals(BRdbmsProxyExt.valueColumn))
    {
      BRdbmsPointQuery query = getQuery();
      if(query!=null)
      {
        query.invoke(BRdbmsPointQuery.execute, null, cx);
      }
    }
  }

  /**
   * Not supported
   */
  @Override
  public boolean write(Context cx)
  {
    return false;
  }

  ////////////////////////////////////////////////////////////////
  // Methods
  ////////////////////////////////////////////////////////////////

  /**
   * Get the parent BRdbmsPointQuery component
   */
  private BRdbmsPointQuery getQuery()
  {
      BComplex c = this;
      while (!(c.getType().is(BRdbmsPointQuery.TYPE)))
      {
        c = c.getParent();
        if (c == null)
        {
          return null;
        }
      }
      return (BRdbmsPointQuery)c;
  }

  public void execute(BResultSetTable<? extends BComponent> table)
  {
    // If this proxy is disabled, then bail out
    if (!this.getEnabled() || !this.isRunning())
    {
      return;
    }

    if(getValueColumn() == null || getValueColumn().trim().length()==0)
    {
      this.readFail("Value column must be set.");
      return;
    }

    // Retrieve all data rows which match our defined key columns
    BFilteredTable<? extends BIObject> filteredTable;
    try
    {

      filteredTable = new BFilteredTable<>(table, obj -> {
        BRdbmsPointQuery query = getQuery();

        if (query == null)
        {
          return false;
        }

        // Each key must match for this row to be considered a match
        for (int i = 0; ; i++)
        {
          // Get key column's name and value (always consider an empty key column name to
          // be a match)
          BString column = (BString) query.get("keyColumn" + (i + 1));
          if(column==null || column.equals(BString.DEFAULT))
          {
            return true;
          }
          String name = column.toString().trim();
          BValue dbValue = ((BComponent) obj).get(name);
          String queryValue = BRdbmsProxyExt.this.get("keyValue" + (i + 1)).toString();

          if(dbValue==null)
          {
            throw new BajaRuntimeException("No value returned by query for primary key " + name + ".");
          }

          // Does the db value match the defined query value?
          try
          {
            if (queryValue == null)
            {
              throw new BajaRuntimeException("No key value defined.");
            }

            if (dbValue.getType().is(BNumber.TYPE))
            {
              if (((BNumber)dbValue).getDouble() != Double.parseDouble(queryValue))
              {
                return false;
              }
            }
            else if (dbValue.getType().is(BBoolean.TYPE))
            {
              if (((BBoolean)dbValue).getBoolean())
              {
                if (!(queryValue.equals("1") || queryValue.equalsIgnoreCase("true")))
                {
                  return false;
                }
              }
              else
              {
                if (!(queryValue.equals("0") || queryValue.equalsIgnoreCase("false")))
                {
                  return false;
                }
              }
            }
            else if (dbValue.getType().is(BString.TYPE))
            {
              if (!((BString)dbValue).getString().trim().equals(queryValue.trim()))
              {
                return false;
              }
            }
            else
            {
              throw new BajaRuntimeException("Unexpected data type: " + dbValue.getType() + " on " + BRdbmsProxyExt.this.toString(null));
            }

          } catch (Exception e)
          {
            throw new BajaRuntimeException("Invalid value for KeyValue"+(i+1)+" on "+BRdbmsProxyExt.this.toString(null), e);
          }
        }
      });
    }
    catch (Exception e)
    {
      getLog().log(Level.SEVERE, e.getMessage(), e);
      this.readFail(e.getMessage());
      return;
    }

    try(TableCursor<? extends BIObject> cursor = filteredTable.cursor())
    {
      // If there are one or more matching rows
      if (cursor.next())
      {
        // Get the matching row, and get the value for our control point
        BComponent row = (BComponent)cursor.get();
        BValue value = row.get(this.getValueColumn());

        // Set the value on the control point
        try
        {
          try
          {
            if (this.getParentPoint() != null)
            {
              if (this.getParentPoint().getType().is(BEnumPoint.TYPE))
              {
                if (value.getType().is(BNumber.TYPE))
                {
                  this.readOk(new BStatusEnum(BDynamicEnum.make(((BNumber)value).getInt())));
                }
                else if (value.getType().is(BBoolean.TYPE))
                {
                  this.readOk(new BStatusEnum(BDynamicEnum.make(((BBoolean)value).getOrdinal())));
                }
                else
                {
                  throw new BajaRuntimeException(toString(null) + " is type " + getParentPoint().getType() + " but value is " + value.getType());
                }
              }
              else if (this.getParentPoint().getType().is(BBooleanPoint.TYPE))
              {
                this.readOk(new BStatusBoolean(((BBoolean)value).getBoolean()));
              }
              else if (this.getParentPoint().getType().is(BNumericPoint.TYPE))
              {
                this.readOk(new BStatusNumeric(((BNumber)value).getDouble()));
              }
              else if (this.getParentPoint().getType().is(BStringPoint.TYPE))
              {
                this.readOk(new BStatusString(value.toString().trim()));
              }
            }
          }
          catch (NumberFormatException | ClassCastException e)
          {
            String parentPointType = "null";
            if (getParentPoint() != null)
            {
              parentPointType = getParentPoint().getType().toString();
            }
            throw new BajaRuntimeException(toString(null) + " is type " + parentPointType + " but value is " + value.getType(), e);
          }
        }
        catch (Exception e)
        {
          getLog().log(Level.SEVERE, e.getMessage(), e);
          this.readFail(e.getMessage());
        }

        // If there are more than one matching rows, give us a warning
        if (cursor.next())
        {
          getLog().warning("Key columns not specific enough, multiple matches for " + toString(null) + ".");
        }
      }
      else
      {
        // If no matching rows exist
        this.readFail("No value found.");
        getLog().warning("Key columns do not match any value for " + toString(null) + ".");
      }
    }
  }

  @Override
  public void subscribed()
  {
    super.subscribed();
    this.readSubscribed(null);
  }

  @Override
  public void unsubscribed()
  {
    super.unsubscribed();
    this.readUnsubscribed(null);
  }

  @Override
  public void readSubscribed(Context cx)
  {
    // Find the BRdbmsPointQuery object above me in the tree
    BRdbmsPointQuery query = getQuery();

    // Add myself to the queries poll group, the query will continue
    // executing it's sql as long as there are members in the group
    if (query != null)
    {
      query.addToPollGroup(this);
    }
  }

  @Override
  public void readUnsubscribed(Context cx)
  {
    // Find the BRdbmsPointQuery object above me in the tree
    BRdbmsPointQuery query = getQuery();

    // Remove myself from the queries poll group, the query will continue
    // executing it's sql as long as there are members in the group
    if (query != null)
    {
      query.removeFromPollGroup(this);
    }
  }

  @Override
  public String toString(Context cx)
  {
    StringBuilder string = new StringBuilder();
    if (getQuery() != null)
    {
      string.append(getQuery().getName());
    }
    string.append(".");
    if (getParentPoint() != null)
    {
      string.append(getParentPoint().getDisplayName(cx));
    }
    return string.toString();
  }

  private Logger getLog()
  {
    //attempt to log with the device's logger, otherwise fallback to the network's logger
    if (getQuery() != null && getQuery().getLog() != null)
    {
      return getQuery().getLog();
    }
    else
    {
      return BRdbmsNetwork.log;
    }
  }
  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////

}
