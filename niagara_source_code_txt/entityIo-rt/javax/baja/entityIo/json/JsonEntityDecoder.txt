/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.entityIo.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BMarker;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.tag.BasicRelation;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tags;
import javax.baja.tag.io.EntityDecoder;
import javax.baja.tag.util.BasicEntity;
import javax.baja.tag.util.RelationSet;
import javax.baja.tag.util.TagSet;
import javax.baja.timezone.BTimeZone;
import javax.baja.units.BUnit;
import com.tridium.json.JSONArray;
import com.tridium.json.JSONException;
import com.tridium.json.JSONObject;
import com.tridium.json.JSONTokener;
import com.tridium.json.JSONUtil;

/**
 * JsonEntityDecoder
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 2/3/14
 *         Time: 8:33 PM
 */
@SuppressWarnings("rawtypes") // because the json.org code uses raw types
public final class JsonEntityDecoder implements EntityDecoder, JsonEntityConst
{
  /**
   * Constructor.
   * @param reader The {@link Reader} which supplies JSON tokens.
   * @throws JSONException
   */
  public JsonEntityDecoder(Reader reader)
    throws JSONException
  {
    Objects.requireNonNull(reader);
    this.reader = reader;
    tokener = new JSONTokener(reader);
  }

  /**
   * Constructor.
   * @param in The {@link InputStream} of JSON tokens.
   * @throws JSONException
   */
  public JsonEntityDecoder(InputStream in)
    throws JSONException
  {
    this(new InputStreamReader(in));
  }


////////////////////////////////////////////////////////////////
// Convenience Decoder Methods
////////////////////////////////////////////////////////////////

  /**
   * Convenience method for decoding a String containing a single JSON-encoded
   * {@link Entity}.  This will return a {@link BasicEntity}.
   * @param encodedString the {@link String} containing the JSON-encoded entity
   * @return a new {@link BasicEntity} with the decoded tags.
   * @throws IOException
   */
  public static Entity decodeFromString(String encodedString)
    throws IOException
  {
    try
    {
      StringReader rdr = new StringReader(encodedString);
      JsonEntityDecoder dec = new JsonEntityDecoder(rdr);
      return dec.decode();
    }
    catch (JSONException e)
    {
      throw new IOException(e);
    }
  }

  /**
   * Convenience method for merging a String containing a single JSON-encoded
   * {@link Entity}.  This will merge the Entity's tags with the decoded ones and
   * return the Entity.
   * @param encodedString the {@link String} containing the JSON-encoded entity
   * @throws IOException
   */
  public static void mergeFromString(String encodedString, Entity entity)
    throws IOException
  {
    try
    {
      StringReader rdr = new StringReader(encodedString);
      JsonEntityDecoder dec = new JsonEntityDecoder(rdr);
      dec.merge(entity);
    }
    catch (JSONException x)
    {
      throw new IOException(x);
    }
  }

  /**
   * Convenience method to decode a group of Entities from a supplied
   * {@link Reader}, and return them in a {@link Collection}.
   * The results are returned in an {@code ArrayList<Entity>}.
   * All {@link JSONException}s thrown by the decoder are wrapped with
   * {@link IOException} for ease of use.
   *
   * @param r a {@link Reader} loaded with a list of encoded Entities
   * @return an {@code ArrayList<Entity>}, returned as a {@code Collection}.
   * @throws IOException
   */
  public static Collection<Entity> decodeEntities(Reader r)
    throws IOException
  {
    try
    {
      ArrayList<Entity> list = new ArrayList<>();
      JsonEntityDecoder dec = new JsonEntityDecoder(r);
      JSONObject root = new JSONObject(dec.tokener);
      Iterator it = root.keys();
      while (it.hasNext())
        list.add(dec.decodeEntity(root.getJSONObject(it.next().toString())));
      return list;
    }
    catch (JSONException e)
    {
      throw new IOException(e);
    }
  }


////////////////////////////////////////////////////////////////
// AutoCloseable
////////////////////////////////////////////////////////////////

  /**
   * Close the input stream and relinquish any underlying resources.
   */
  @Override
  public void close()
    throws IOException
  {
    reader.close();
  }


  /**
   * Decode from the decoder's input a serialized {@link Entity} tag set.
   * This method will return a new {@link Entity},
   * decorated with the decoded {@link javax.baja.tag.Tag}s.
   * The default implementation will use a {@link BasicEntity} to hold the
   * deserialized {@link javax.baja.tag.Tag}s.
   * @return an (@code Entity} with the decoded {@link javax.baja.tag.Tag}s.
   * @throws IOException if anything fails with the decoding.
   */
  @Override
  public Entity decode()
    throws IOException
  {
    try
    {
      JSONObject jsonObject = new JSONObject(tokener);

      return decodeEntity(jsonObject);
    }
    catch (JSONException e)
    {
      throw new IOException(e);
    }
  }

  /**
   * Decode a single {@link Entity} tag set from the supplied {@link JSONObject}.
   * This method will return a new {@link Entity},
   * decorated with the decoded {@link javax.baja.tag.Tag}s.
   * This implementation will use a {@link BasicEntity} to hold the
   * deserialized object.
   * @return a {@link BasicEntity} with the decoded tags, relations, and ord.
   * @throws IOException if anything fails with the decoding.
   * @throws JSONException if the underlying JSON processing fails.
   */
  @SuppressWarnings("SpellCheckingInspection")
  public Entity decodeEntity(JSONObject jsonObject)
    throws JSONException, IOException
  {
    BOrd ord = null;
    Tags tags = new TagSet();
    Relations relations = new RelationSet();

    if (jsonObject.has(KEY_ORD))
    {
      String ordstr = JSONUtil.getString(jsonObject, KEY_ORD);
      if (ordstr != null)
        ord = BOrd.make(ordstr);
    }

    JSONObject tagsObj = jsonObject.optJSONObject(KEY_TAGS);
    if (tagsObj != null)
      tags = decodeTags(tagsObj);

    JSONArray relationsArr = jsonObject.optJSONArray(KEY_RELATIONS);
    if (relationsArr != null)
      relations = decodeRelations(relationsArr);

    return new BasicEntity(ord, tags, relations);
  }

  /**
   * Decode {@link Tags} from a {@link JSONObject}.
   * @param jsonObject the JSON object containing the encoded tags
   * @return a {@link TagSet} containing the tags.
   * @throws JSONException
   * @throws IOException
   */
  public Tags decodeTags(JSONObject jsonObject)
    throws JSONException, IOException
  {
    TagSet tags = new TagSet();
    Iterator it = jsonObject.keys();
    while (it.hasNext())
    {
      String key = (String)it.next();
      char valueType = key.charAt(key.length()-1);
      Object val = jsonObject.get(key);
      Id id = Id.newId(key.substring(0,key.length()-1));
      if (val instanceof JSONArray)
      {
        // re-add any existing non-multi tag as a multi tag
        // so the incoming multi tags can be merged
        if (tags.contains(id) && !tags.isMulti(id))
        {
          BIDataValue existing = tags.get(id).get();
          tags.removeAll(id);
          tags.addMulti(id, existing);
        }

        JSONArray array = (JSONArray)val;
        int len = array.length();
        for (int i=0; i<len; i++)
        {
          tags.addMulti(id, toDataValue(valueType, array.get(i)));
        }
      }
      else
      {
        if (tags.contains(id) && tags.isMulti(id))
        {
          if (jsonObject.isNull(key))
            tags.addMulti(id, BMarker.MARKER);
          else
            tags.addMulti(id, toDataValue(valueType, val));
        }
        else
        {
          if (jsonObject.isNull(key))
            tags.set(id, BMarker.MARKER);
          else
            tags.set(id, toDataValue(valueType, val));
        }
      }
    }
    return tags;
  }

  /**
   * Decode {@link Relations} from a {@link JSONArray}.
   * @param jsonArray the JSON array containing the encoded relations.
   * @return a {@link RelationSet} containing the relations.
   * @throws JSONException
   * @throws IOException
   */
  public Relations decodeRelations(JSONArray jsonArray)
    throws JSONException, IOException
  {
    Relations relations = new RelationSet();
    int len = jsonArray.length();
    for (int i=0; i<len; i++)
    {
      relations.add(decodeRelation(jsonArray.getJSONObject(i)));
    }
    return relations;
  }

  /**
   * Decode a {@link Relation} from a {@link JSONObject}.
   * Note this decode contains only Tags and the Entity Ord.  It uses an empty {@link BasicEntity}
   * as the {@link Entity}, because the full Entity is not encoded
   * to prevent cycles and/or recursion issues. (Possibly to be addressed/fixed later).
   * @param jsonObject the JSON object containing the encoded {@code Relation}.
   * @return a new {@link Relation} with the encoded Tags and entityOrd.
   * @throws JSONException
   * @throws IOException
   */
  public Relation decodeRelation(JSONObject jsonObject)
    throws JSONException, IOException
  {
    Id id = Id.newId(JSONUtil.getString(jsonObject, KEY_REL_ID));

    // Since encoding of the inbound boolean was added in a later release, if it's not specified,
    // we'll fallback to use true for it
    boolean inbound = jsonObject.optBoolean(KEY_REL_INBOUND, true);

    BOrd entityOrd = BOrd.make(jsonObject.optString(KEY_RELATION_ENTITY_ORD));

    Tags tags;
    if (jsonObject.has(KEY_TAGS))
      tags = decodeTags(jsonObject.getJSONObject(KEY_TAGS));
    else
      tags = new TagSet();

    return new BasicRelation(id, entityOrd, tags, inbound);
  }

  /**
   * Convert an Object returned from JSON into the appropriate Niagara {@link BIDataValue}
   * based on the type extracted from the key.
   * @see JsonEntityConst for a description of key codes.
   * @param type the key code
   * @param o the JSON object as an {@link Object}.
   * @return a {@link BIDataValue} converted from the Object.
   * @throws IOException
   */
  private static BIDataValue toDataValue(char type, Object o)
    throws IOException
  {
    switch (type)
    {
      case MARKER: return BMarker.MARKER;
      case BOOLEAN: return BBoolean.make((Boolean)o);
      case DOUBLE: return BDouble.make(((Number)o).doubleValue());
      case INTEGER: return BInteger.make(((Number)o).intValue());
      case FLOAT: return BFloat.make(((Number)o).floatValue());
      case LONG: return BLong.make(((Number)o).longValue());
      case STRING: return BString.make(o.toString());
      case ABSTIME: return (BIDataValue)BAbsTime.DEFAULT.decodeFromString(o.toString());
      case ORD: return BOrd.make(o.toString());
      case ENUM: return (BIDataValue)BDynamicEnum.DEFAULT.decodeFromString(o.toString());
      case RANGE: return (BIDataValue)BEnumRange.DEFAULT.decodeFromString(o.toString());
      case RELTIME: return (BIDataValue)BRelTime.DEFAULT.decodeFromString(o.toString());
      case TIMEZONE: return (BIDataValue)BTimeZone.DEFAULT.decodeFromString(o.toString());
      case UNIT: return (BIDataValue)BUnit.DEFAULT.decodeFromString(o.toString());
      case NON_STD: return nonStandard(o.toString());
      case OBJECT: return BString.make(o.toString());
      default : throw new IllegalArgumentException("unknown value type: '"+type+'\'');
    }
  }

  private static BIDataValue nonStandard(String s)
  {
    if (JSON_F_NAN.equals(s)) return BFloat.NaN;
    if (JSON_F_POS_INF.equals(s)) return BFloat.POSITIVE_INFINITY;
    if (JSON_F_NEG_INF.equals(s)) return BFloat.NEGATIVE_INFINITY;
    if (JSON_D_NAN.equals(s)) return BDouble.NaN;
    if (JSON_D_POS_INF.equals(s)) return BDouble.POSITIVE_INFINITY;
    if (JSON_D_NEG_INF.equals(s)) return BDouble.NEGATIVE_INFINITY;
    throw new IllegalArgumentException("unknown nonstandard value:" + s);
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final JSONTokener tokener;
  private final Reader reader;

}
