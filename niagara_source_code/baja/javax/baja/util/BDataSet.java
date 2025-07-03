/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.baja.data.BIDataValue;
import javax.baja.data.DataUtil;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import com.tridium.sys.schema.Fw;

/**
 * A naive implementation of an immutable set of {@link BIDataValue}. It is strongly recommended
 * that this class not be used to store "large" sets of data values.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
@NiagaraType
@NoSlotomatic
public final class BDataSet extends BSimple
{
  public static final BDataSet DEFAULT = new BDataSet();
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $javax.baja.util.BDataSet(2979906276)1.0$ @*/
/* Generated Fri Apr 10 10:49:46 EDT 2015 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDataSet.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  private final Set<BIDataValue> values;

  public static BDataSet make(BIDataValue...values)
  {
    return make(Arrays.asList(values));
  }

  public static BDataSet make(Collection<? extends BIDataValue> values)
  {
    return new BDataSet(new HashSet<>(values));
  }

  private BDataSet()
  {
    this(new HashSet<BIDataValue>());
  }

  private BDataSet(Set<BIDataValue> values)
  {
    requireHomogenous(values);
    this.values = Collections.unmodifiableSet(values);
  }

  private static void requireHomogenous(Set<BIDataValue> values)
  {
    Iterator<BIDataValue> iter = values.iterator();
    if (!iter.hasNext())
    {
      return;
    }
    final Type requiredType = iter.next().getType();
    Type check;
    while (iter.hasNext())
    {
      check = iter.next().getType();
      if (!requiredType.equals(check))
      {
        throw new IllegalArgumentException("All elements in the data set must be " + requiredType + ", but found element of type " + check);
      }
    }
  }

  public BDataSet add(BDataSet other)
  {
    Set<BIDataValue> dataset = new HashSet<>(values);
    dataset.addAll(other.values);
    return new BDataSet(dataset);
  }

  public BDataSet add(BIDataValue...vals)
  {
    return add(make(vals));
  }

  public BDataSet remove(BIDataValue...vals)
  {
    Set<BIDataValue> dataset = new HashSet<>(values);
    dataset.removeAll(Arrays.asList(vals));
    return new BDataSet(dataset);
  }

  public boolean contains(BIDataValue value)
  {
    return values.contains(value);
  }

  public int size()
  {
    return values.size();
  }

  public boolean isEmpty()
  {
    return values.isEmpty();
  }

  public Set<BIDataValue> asImmutableSet()
  {
    return values;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    BDataSet bDataSet = (BDataSet)o;
    if (!values.equals(bDataSet.values))
    {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }

  @Override
  public void encode(DataOutput encoder) throws IOException
  {
    encoder.writeUTF(encodeToString());
  }

  @Override
  public BObject decode(DataInput decoder) throws IOException
  {
    return decodeFromString(decoder.readUTF());
  }

  @Override
  public String encodeToString() throws IOException
  {
    if (values.isEmpty())
    {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (BIDataValue dv : values)
    {
      sb.append(escape(dv)).append(',');
    }
    // Remove last ',' and return encoded string
    //
    return sb.deleteCharAt(sb.length() - 1).toString();
  }

  @SuppressWarnings("fallthrough")
  private static String escape(BIDataValue dataValue) throws IOException
  {
    String s = DataUtil.marshal(dataValue);
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray())
    {
      // Switch case has fall-through so a comma or backslash is escaped with a backslash.
      switch (c)
      {
        case ',':
        case '\\':
          sb.append('\\');
        default:
          sb.append(c);
      }
    }
    return sb.toString();
  }

  @Override
  public BObject decodeFromString(String s) throws IOException
  {
    if (s.isEmpty())
    {
      return DEFAULT;
    }

    char[] buf = s.toCharArray();
    int pos = 0;
    Set<BIDataValue> dataset = new HashSet<>();

    StringBuilder sb = new StringBuilder();
    while (pos < buf.length)
    {
      if (buf[pos] == ',')
      {
        dataset.add(DataUtil.unmarshal(sb.toString()).as(BIDataValue.class));
        sb.setLength(0);
      }
      else if (buf[pos] == '\\')
      {
        // Skip escape char and store actual char
        pos++;
        sb.append(buf[pos]);
      }
      else
      {
        sb.append(buf[pos]);
      }
      ++pos;
    }
    dataset.add(DataUtil.unmarshal(sb.toString()).as(BIDataValue.class));
    return new BDataSet(dataset);
  }

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.SKIP_INTERN:
        return Boolean.TRUE;
    }
    return super.fw(x, a, b, c, d);
  }

}
