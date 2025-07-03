/**
 * Copyright (c) 2014 Tridium, Inc.  All rights reserved.
 */
package javax.baja.entityIo.json;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BDouble;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLong;
import javax.baja.sys.BMarker;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tag;
import javax.baja.tag.Tags;
import javax.baja.tag.io.EntityEncoder;
import javax.baja.timezone.BTimeZone;
import javax.baja.units.BUnit;

import com.tridium.json.JSONException;
import com.tridium.json.JSONWriter;

/**
 * JsonEntityEncoder
 * This class provides encoding methods for serializing Niagara {@link Entity} objects
 * into JSON-encoded objects.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 1/23/14
 *         Time: 3:26 PM
 */
public final class JsonEntityEncoder implements EntityEncoder, JsonEntityConst
{
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a JsonEntityEncoder backed by a {@link File}. Both tags and relations are encoded.
   * @param f the backing {@link File} for this encoder.
   * @throws IOException if an error occurs in setting up the output writer.
   */
  public JsonEntityEncoder(File f) throws IOException
  {
    this(f, ENCODE_TAGS_AND_RELATIONS);
  }

  /**
   * Construct a JsonEntityEncoder backed by a {@link File}.
   * @param f the backing {@link File} for this encoder.
   * @param options encoding options such as whether to encode each entity's tags and/or relations
   * @throws IOException if an error occurs in setting up the output writer.
   * 
   * @since Niagara 4.9
   */
  public JsonEntityEncoder(File f, Options options) throws IOException
  {
    Objects.requireNonNull(f);
    out = new BufferedOutputStream(new FileOutputStream(f));
    this.options = (options != null) ? options : ENCODE_TAGS_AND_RELATIONS;
    init();
  }

  /**
   * Construct a JsonEntityEncoder backed by a {@link OutputStream}. Both tags and relations are
   * encoded.
   * @param out the backing output stream.
   * @throws IOException if an error occurs in setting up the output writer.
   */
  public JsonEntityEncoder(OutputStream out) throws IOException
  {
    this(out, ENCODE_TAGS_AND_RELATIONS);
  }

  /**
   * Construct a JsonEntityEncoder backed by a {@link OutputStream}.
   * @param out the backing output stream.
   * @param options encoding options such as whether to encode each entity's tags and/or relations
   * @throws IOException if an error occurs in setting up the output writer.
   *
   * @since Niagara 4.9
   */
  public JsonEntityEncoder(OutputStream out, Options options) throws IOException
  {
    Objects.requireNonNull(out);
    this.out = out;
    this.options = (options != null) ? options : ENCODE_TAGS_AND_RELATIONS;
    init();
  }

////////////////////////////////////////////////////////////////
// Convenience Encoder Methods
////////////////////////////////////////////////////////////////

  /**
   * Convenience method for encoding a single {@link Entity} to a JSON string.  Both tags and
   * relations are encoded.
   * @param e the {@link Entity} to be encoded
   * @return a String containing the JSON encoded value
   * @throws IOException
   */
  public static String encodeToString(Entity e) throws IOException
  {
    return encodeToString(e, ENCODE_TAGS_AND_RELATIONS);
  }

  /**
   * Convenience method for encoding a single {@link Entity} to a JSON string.
   * @param e the {@link Entity} to be encoded
   * @param options encoding options such as whether to encode each entity's tags and/or relations
   * @return a String containing the JSON encoded value
   * @throws IOException
   *
   * @since Niagara 4.9
   */
  public static String encodeToString(Entity e, Options options) throws IOException
  {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (JsonEntityEncoder encoder = new JsonEntityEncoder(os, options))
    {
      encoder.encode(e);
    }
    return os.toString();
  }

  /**
   * Convenience method for encoding a group of unnamed Entities. Both tags and relations are
   * encoded.
   * The JSON syntax requires names, so they will be given default names.
   * @param c the {@link Collection} of Entities
   * @return a String containing the JSON encoded value
   * @throws IOException
   */
  public static String encodeToString(Collection<Entity> c) throws IOException
  {
    return encodeToString(c, ENCODE_TAGS_AND_RELATIONS);
  }

  /**
   * Convenience method for encoding a group of unnamed Entities.
   * The JSON syntax requires names, so they will be given default names.
   * @param c the {@link Collection} of Entities
   * @param options encoding options such as whether to encode each entity's tags and/or relations
   * @return a String containing the JSON encoded value
   * @throws IOException
   *
   * @since Niagara 4.9
   */
  public static String encodeToString(Collection<Entity> c, Options options) throws IOException
  {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (JsonEntityEncoder encoder = new JsonEntityEncoder(os, options))
    {
      int count = 0;
      for (Entity e : c)
      {
        encoder.encode(e, "e" + count);
        ++count;
      }
    }
    return os.toString();
  }

////////////////////////////////////////////////////////////////
// AutoCloseable
////////////////////////////////////////////////////////////////

  /**
   * Close the output stream and relinquish any underlying resources.
   * This will flush the stream, then reset the internal buffer.
   */
  @Override
  public void close() throws IOException
  {
    try
    {
      if (!root)
      {
        jout.endObject();
      }

      flush();
      out.close();
      buffer.reset();
    }
    catch (JSONException e)
    {
      throw new IOException(e);
    }
  }

////////////////////////////////////////////////////////////////
// EntityEncoder
////////////////////////////////////////////////////////////////

  /**
   * Encode an {@link Entity} to the output stream.
   * @param entity the {@link Entity} to be encoded.
   * @param name the name of the {@link Entity} being encoded
   * @throws IOException if an I/O error occurs.
   */
  @Override
  public void encode(Entity entity, String name) throws IOException
  {
    try
    {
      if (root)
      {
        if (name == null)
        {
          jout.object();
          encodeEntity(entity);
          jout.endObject();
        }
        else
        {
          jout.object();
          root = false;
          jout.key(name).object();
          encodeEntity(entity);
          jout.endObject();
        }
      }
      else
      {
        if (name == null)
        {
          throw new IllegalArgumentException("Non-Root JSON object must be named");
        }
        else
        {
          jout.key(name).object();
          encodeEntity(entity);
          jout.endObject();
        }
      }
    }
    catch (JSONException e)
    {
      flush();
      System.out.println("JSONException encoding, output so far is " + out);
      throw new IOException(e);
    }
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Initialize the {@link JSONWriter} that will be used to serialize
   * the entities written to this encoder.
   */
  private void init()
  {
    writer = new OutputStreamWriter(buffer, StandardCharsets.UTF_8);
    jout = new JSONWriter(writer);
    root = true;
  }

  /**
   * Flush the output stream's internal buffer.  This will write the internal
   * buffer to the stream, and then reset it.
   * @throws IOException if any of the I/O operations fail.
   */
  private void flush() throws IOException
  {
    try
    {
      writer.flush();
      int size = buffer.size();
      if (size > 0)
      {
        out.write(buffer.getBytes(),0,size);
        out.flush();
      }

      buffer.reset();
    }
    catch (Exception x)
    {
      throw new IOException(x);
    }
  }

  /**
   * Encode an {@link Entity}.
   * @param e the {@link Entity} to be encoded
   * @throws JSONException
   * @throws IOException
   */
  public void encodeEntity(Entity e) throws JSONException, IOException
  {
    if (e == null)
    {
      jout.value(null);
    }
    else
    {
      if (options.shouldEncodeTags())
      {
        encodeTags(jout, e.tags(), !ALLOW_EMPTY, KEY_TAGS);
      }

      if (options.shouldEncodeRelations())
      {
        encodeRelations(jout, e.relations(), !ALLOW_EMPTY, KEY_RELATIONS);
      }

      Optional<BOrd> maybeOrd = e.getOrdToEntity();
      if (maybeOrd.isPresent())
      {
        jout.key(KEY_ORD).value(maybeOrd.get());
      }
    }
  }

  /**
   * Encode the {@link Tags} of an {@link Entity}.
   *
   * @see #encodeTags(JSONWriter, Tags)
   * @param tags The tags to be encoded (must not be null).
   * @throws JSONException
   * @throws IOException
   */
  public void encodeTags(Tags tags) throws JSONException, IOException
  {
    encodeTags(jout, tags, ALLOW_EMPTY, KEY_TAGS);
  }

  /**
   * Encode the {@link Tags} using the specified JSONWriter.
   *
   * @see #encodeTags(Tags)
   * @param jout The JSONWriter to write out too.
   * @param tags The tags to be encoded (must not be null).
   * @throws JSONException
   * @throws IOException
   */
  public static void encodeTags(JSONWriter jout, Tags tags) throws JSONException, IOException
  {
    encodeTags(jout, tags, ALLOW_EMPTY, null);
  }

  /**
   * Encode the tags using the specified JSONWriter. If allowEmpty is true, start a JSONObject
   * whether or not tags is empty. The JSONObject will be prefixed with the key, if supplied.
   * @since Niagara 4.9
   */
  private static void encodeTags(JSONWriter jout, Tags tags, boolean allowEmpty, String key)
    throws JSONException, IOException
  {
    Objects.requireNonNull(tags);

    Collection<Id> multisWritten = new ArrayList<>();
    BIDataValue value;

    boolean objectStarted = false;
    if (allowEmpty)
    {
      startObject(jout, key);
      objectStarted = true;
    }

    for (Tag tag : tags)
    {
      if (!objectStarted)
      {
        startObject(jout, key);
        objectStarted = true;
      }

      Id id = tag.getId();

      // Skip if we've already written this tag as part of a multi
      if (multisWritten.contains(id))
      {
        continue;
      }

      if (tags.isMulti(id))
      {
        multisWritten.add(id);

        Collection<BIDataValue> vals = tags.getValues(id);
        Iterator<BIDataValue> it = vals.iterator();
        value = it.next();
        key(jout, id.toString(), value);
        jout.array();
        encodeValue(jout, value);
        while (it.hasNext())
        {
          value = it.next();
          encodeValue(jout, value);
        }
        jout.endArray();
      }
      else
      {
        encodeSingleTag(jout, tag);
      }
    }

    if (objectStarted)
    {
      jout.endObject();
    }
  }

  /**
   * Start a JSONObject. Add a key first, if supplied.
   * @since Niagara 4.9
   */
  private static void startObject(JSONWriter jout, String key)
  {
    if (key != null)
    {
      jout.key(key);
    }
    jout.object();
  }

  /**
   * Encode a single-valued {@link Tag}.  This should NOT be
   * used for encoding multi-valued tags, as they are encoded together as a
   * JSON Array object.
   *
   * @see #encodeSingleTag(JSONWriter, Tag)
   * @param tag the single tag.
   * @throws JSONException
   * @throws IOException
   */
  public void encodeSingleTag(Tag tag) throws JSONException, IOException
  {
    encodeSingleTag(jout, tag);
  }

  /**
   * Encodes a single-valued {@link Tag} using the specified
   * JSONWriter.  This should NOT be used for encoding multi-valued tags,
   * as they are encoded together as a JSON Array object.
   *
   * @see #encodeSingleTag(Tag)
   * @param jout The JSONWriter to write out too.
   * @param tag the single tag.
   * @throws JSONException
   * @throws IOException
   */
  public static void encodeSingleTag(JSONWriter jout, Tag tag) throws JSONException, IOException
  {
    BIDataValue value = tag.getValue();
    try
    {
      key(jout, tag.getId().toString(), value);
      encodeValue(jout, value);
    }
    catch(JSONException e)
    {
      // Ignore duplicate key exceptions as this indicates multiple tags with the same ID on the
      // same Entity, but they were *not* declared as multi-value tags (those are properly handled).
      // In such a scenario, only the first tag will be encoded for the Entity, and any subsequent
      // tags with the same ID will be silently skipped. This is OK because Niagara already can't
      // handle duplicate (non-multi value) tags and queries in Niagara for Entities that contain
      // them will only use the first tag's value, so we shouldn't be affecting existing behavior
      // by removing duplicates in this way.

      // That said, this is definitely not the most robust way to check for a duplicate key
      // exception, but at the time of this writing, I was hesitant to change JSONObject.java to
      // throw a better duplicate exception type since that code appears to be taken from JSON.org
      // and I'm not sure if/when/how it gets upgraded. While not robust, I expect the exception
      // message is unlikely to change on upgrade, so it's probably low risk to do it this way.
      String msg = e.getMessage();
      if ((msg == null) || !msg.startsWith("Duplicate key"))
      {
        throw e;
      }
    }
  }

  /**
   * Encode the {@link Relations} of an {@link Entity}.
   * @param relations the Relations (must not be null).
   * @throws JSONException
   * @throws IOException
   */
  public void encodeRelations(Relations relations) throws JSONException, IOException
  {
    encodeRelations(jout, relations, ALLOW_EMPTY, KEY_RELATIONS);
  }

  /**
   * Encode the {@link Relations} of an {@link Entity}.
   * @param jout The JSONWriter to write out too.
   * @param relations the Relations (must not be null).
   * @throws JSONException
   * @throws IOException
   */
  public static void encodeRelations(JSONWriter jout, Relations relations) throws JSONException, IOException
  {
    encodeRelations(jout, relations, ALLOW_EMPTY, null);
  }

  /**
   * Encode the relations using the specified JSONWriter. If allowEmpty is true, start a JSONArray
   * whether or not relations is empty. The JSONArray will be prefixed with the key, if supplied.
   * @since Niagara 4.9
   */
  private static void encodeRelations(JSONWriter jout, Relations relations, boolean allowEmpty, String key)
    throws JSONException, IOException
  {
    Objects.requireNonNull(relations);

    boolean arrayStarted = false;
    if (allowEmpty)
    {
      startArray(jout, key);
      arrayStarted = true;
    }

    for (Relation rel : relations)
    {
      if (!arrayStarted)
      {
        startArray(jout, key);
        arrayStarted = true;
      }

      encodeRelation(jout, rel);
    }

    if (arrayStarted)
    {
      jout.endArray();
    }
  }

  /**
   * Start a JSONArray. Add a key first, if supplied.
   * @since Niagara 4.9
   */
  private static void startArray(JSONWriter jout, String key)
  {
    if (key != null)
    {
      jout.key(key);
    }
    jout.array();
  }

  /**
   * Encode the {@link Relation}.
   * Note this encodes only Tags and the Entity's Ord.  The full Entity is not encoded
   * to prevent cycles and/or recursion issues. (Possibly to be addressed/fixed later).
   * @param rel the supplied {@code Relation}.
   * @throws JSONException
   * @throws IOException
   */
  public void encodeRelation(Relation rel) throws JSONException, IOException
  {
    encodeRelation(jout, rel);
  }

  /**
   * Encode the {@link Relation}.
   * Note this encodes only Tags and the Entity's Ord.  The full Entity is not encoded
   * to prevent cycles and/or recursion issues. (Possibly to be addressed/fixed later).
   * @param jout The JSONWriter to write out too.
   * @param rel the supplied {@code Relation}.
   * @throws JSONException
   * @throws IOException
   */
  public static void encodeRelation(JSONWriter jout, Relation rel) throws JSONException, IOException
  {
    jout.object();
    jout.key(KEY_REL_ID).value(rel.getId().toString());
    jout.key(KEY_REL_INBOUND).value(rel.isInbound());
    Entity target = rel.getEndpoint();
    if (target != null)
    {
      Optional<BOrd> optOrd = target.getOrdToEntity();
      if (optOrd.isPresent())
      {
        jout.key(KEY_RELATION_ENTITY_ORD).value(optOrd.get());
      }
    }
    else if (rel.getEndpointOrd() != null)
    {
      jout.key(KEY_RELATION_ENTITY_ORD).value(rel.getEndpointOrd());
    }
    if (!rel.tags().isEmpty())
    {
      jout.key(KEY_TAGS);
      encodeTags(jout, rel.tags());
    }
    jout.endObject();
  }

  /**
   * Write the key for a {@link Tag}'s id string.  In order to allow the decoder
   * to determine the correct type for reconstruction, this appends a single character
   * indicating the Niagara {@link Type} to the end of the key.
   * @param id the tag's {@link Id} which will be the key
   * @param value the tag value; its type abbreviation will be appended to the key
   * @throws JSONException
   */
  private static void key(JSONWriter jout, String id, BIDataValue value) throws JSONException
  {
    Objects.requireNonNull(value);
    Type type = value.getType();
    if (type.is(BMarker.TYPE))
    {
      jout.key(id + MARKER);
    }
    else if (type.is(BString.TYPE))
    {
      jout.key(id + STRING);
    }
    else if (type.is(BDouble.TYPE))
    {
      jout.key(Double.isFinite(value.as(BDouble.class).getDouble()) ?
        (id + DOUBLE) :
        (id + NON_STD));
    }
    else if (type.is(BInteger.TYPE))
    {
      jout.key(id + INTEGER);
    }
    else if (type.is(BBoolean.TYPE))
    {
      jout.key(id + BOOLEAN);
    }
    else if (type.is(BFloat.TYPE))
    {
      jout.key(Float.isFinite(value.as(BFloat.class).getFloat()) ?
        (id + FLOAT) :
        (id + NON_STD));
    }
    else if (type.is(BLong.TYPE))
    {
      jout.key(id + LONG);
    }
    else if (type.is(BAbsTime.TYPE))
    {
      jout.key(id + ABSTIME);
    }
    else if (type.is(BOrd.TYPE))
    {
      jout.key(id + ORD);
    }
    else if (type.is(BDynamicEnum.TYPE))
    {
      jout.key(id + ENUM);
    }
    else if (type.is(BEnumRange.TYPE))
    {
      jout.key(id + RANGE);
    }
    else if (type.is(BRelTime.TYPE))
    {
      jout.key(id + RELTIME);
    }
    else if (type.is(BTimeZone.TYPE))
    {
      jout.key(id + TIMEZONE);
    }
    else if (type.is(BUnit.TYPE))
    {
      jout.key(id + UNIT);
    }
    else
    {
      jout.key(id + OBJECT);
    }
  }
    
  /**
   * Encode a value <b>v</b>, of {@link Type} t, to the JSONWriter <b>jout</b>.
   * This implementation probably needs to be improved later.
   * @param jout the output JSON writer
   * @param v the value to be encoded
   * @throws JSONException
   * @throws IOException
   */
  private static void encodeValue(JSONWriter jout, BIDataValue v) throws JSONException, IOException
  {
    Objects.requireNonNull(v);
    Type type = v.getType();
    if (type.is(BMarker.TYPE))
    {
      jout.value(null);
    }
    else if (type.is(BString.TYPE))
    {
      jout.value(v.as(BString.class));
    }
    else if (type.is(BDouble.TYPE))
    {
      double d = v.as(BDouble.class).getDouble();
      if (Double.isNaN(d))
      {
        jout.value(JSON_D_NAN);
      }
      else if (Double.isFinite(d))
      {
        jout.value(d);
      }
      else
      {
        jout.value((Math.signum(d) > 0) ? JSON_D_POS_INF : JSON_D_NEG_INF);
      }
    }
    else if (type.is(BInteger.TYPE))
    {
      jout.value(v.as(BInteger.class).getInt());
    }
    else if (type.is(BBoolean.TYPE))
    {
      jout.value(v.as(BBoolean.class).getBoolean());
    }
    else if (type.is(BFloat.TYPE))
    {
       float f = v.as(BFloat.class).getFloat();
      if (Float.isNaN(f))
      {
        jout.value(JSON_F_NAN);
      }
      else if (Float.isFinite(f))
      {
        jout.value(f);
      }
      else
      {
        jout.value((Math.signum(f) > 0) ? JSON_F_POS_INF : JSON_F_NEG_INF);
      }
    }
    else if (type.is(BLong.TYPE))
    {
      jout.value(v.as(BLong.class).getLong());
    }
    else if (type.is(BDynamicEnum.TYPE))
    {
      jout.value(v.as(BDynamicEnum.class).encodeToString());
    }
    else if (type.is(BTimeZone.TYPE))
    {
      jout.value(v.as(BTimeZone.class).encodeToString());
    }
    else if (type.is(BUnit.TYPE))
    {
      jout.value(v.as(BUnit.class).encodeToString());
    }
    else if (type.is(BEnumRange.TYPE))
    {
      jout.value(v);
    }
    else if (type.is(BAbsTime.TYPE))
    {
      jout.value(v.as(BAbsTime.class).encodeToString());
    }
    else if (type.is(BRelTime.TYPE))
    {
      jout.value(v.as(BRelTime.class).encodeToString());
    }
    else if (type.is(BOrd.TYPE))
    {
      jout.value(v);
    }
    else
    {
      jout.value(v);
    }
  }

  /**
   * Convenience method to generate a Context instance that contains a special facet (named
   * "jsonEntityEncoderShouldEncodeTags") to signify that an entity's tags should be encoded when
   * handling queries over fox.
   *
   * @param cx A Context that will be used as a base for generating the result. This parameter can
   *           be null.
   * @return A Context instance generated from the given base Context argument enhanced with one
   * additional special facet called "jsonEntityEncoderShouldEncodeTags" that has a boolean value of
   * true.
   *
   * @since Niagara 4.9
   */
  public static Context makeShouldEncodeTagsContext(Context cx)
  {
    return (cx == null) ? SHOULD_ENCODE_TAGS_FACET : new BasicContext(cx, SHOULD_ENCODE_TAGS_FACET);
  }

  /**
   * Convenience method to generate a Context instance that contains a special facet (named
   * "jsonEntityEncoderShouldEncodeTags") to signify that an entity's tags should NOT be encoded
   * when handling queries over fox.
   *
   * @param cx A Context that will be used as a base for generating the result. This parameter can
   *           be null.
   * @return A Context instance generated from the given base Context argument enhanced with one
   * additional special facet called "jsonEntityEncoderShouldEncodeTags" that has a boolean value of
   * false.
   *
   * @since Niagara 4.9
   */
  public static Context makeShouldNotEncodeTagsContext(Context cx)
  {
    return (cx == null) ? SHOULD_NOT_ENCODE_TAGS_FACET : new BasicContext(cx, SHOULD_NOT_ENCODE_TAGS_FACET);
  }

  /**
   * Convenience method that checks the given Context for the presence of a special
   * "jsonEntityEncoderShouldEncodeTags" facet, and if found, returns its boolean value. This value
   * is used to signify whether an entity's tags should be encoded when handling queries over fox.
   *
   * @param cx the Context instance to check; can be null
   * @return true if cx is null, the "jsonEntityEncoderShouldEncodeTags" facet is missing, or has a
   * boolean true value. Otherwise false is returned.
   *
   * @since Niagara 4.9
   */
  public static boolean shouldEncodeTags(Context cx)
  {
    return (cx == null) || !(BBoolean.FALSE == cx.getFacet(SHOULD_ENCODE_TAGS_KEY));
  }

  /**
   * Convenience method to generate a Context instance that contains a special facet (named
   * "jsonEntityEncoderShouldEncodeRelations") to signify that an entity's relations should be
   * encoded when handling queries over fox.
   *
   * @param cx A Context that will be used as a base for generating the result. This parameter can
   *           be null.
   * @return A Context instance generated from the given base Context argument enhanced with one
   * additional special facet called "jsonEntityEncoderShouldEncodeRelations" that has a boolean
   * value of true.
   *
   * @since Niagara 4.9
   */
  public static Context makeShouldEncodeRelationsContext(Context cx)
  {
    return (cx == null) ? SHOULD_ENCODE_RELATIONS_FACET : new BasicContext(cx, SHOULD_ENCODE_RELATIONS_FACET);
  }

  /**
   * Convenience method to generate a Context instance that contains a special facet (named
   * "jsonEntityEncoderShouldEncodeRelations") to signify that an entity's relations should NOT be
   * encoded when handling queries over fox.
   *
   * @param cx A Context that will be used as a base for generating the result. This parameter can
   *           be null.
   * @return A Context instance generated from the given base Context argument enhanced with one
   * additional special facet called "jsonEntityEncoderShouldEncodeRelations" that has a boolean
   * value of false.
   *
   * @since Niagara 4.9
   */
  public static Context makeShouldNotEncodeRelationsContext(Context cx)
  {
    return (cx == null) ? SHOULD_NOT_ENCODE_RELATIONS_FACET : new BasicContext(cx, SHOULD_NOT_ENCODE_RELATIONS_FACET);
  }

  /**
   * Convenience method that checks the given Context for the presence of a special
   * "jsonEntityEncoderShouldEncodeRelations" facet, and if found, returns its boolean value. This value
   * is used to signify whether an entity's relations should be encoded when handling queries over fox.
   *
   * @param cx the Context instance to check; can be null
   * @return true if cx is null, the "jsonEntityEncoderShouldEncodeRelations" facet is missing, or
   * has a boolean true value. Otherwise false is returned.
   *
   * @since Niagara 4.9
   */
  public static boolean shouldEncodeRelations(Context cx)
  {
    return (cx == null) || !(BBoolean.FALSE == cx.getFacet(SHOULD_ENCODE_RELATIONS_KEY));
  }

  /**
   * Facet key used to signify whether an entity's tags should be encoded when handling queries over
   * fox.
   */
  public static final String SHOULD_ENCODE_TAGS_KEY = "jsonEntityEncoderShouldEncodeTags";
  private static final BFacets SHOULD_ENCODE_TAGS_FACET = BFacets.make(SHOULD_ENCODE_TAGS_KEY, true);
  private static final BFacets SHOULD_NOT_ENCODE_TAGS_FACET = BFacets.make(SHOULD_ENCODE_TAGS_KEY, false);

  /**
   * Facet key used to signify whether an entity's relations should be encoded when handling queries
   * over fox.
   */
  public static final String SHOULD_ENCODE_RELATIONS_KEY = "jsonEntityEncoderShouldEncodeRelations";
  private static final BFacets SHOULD_ENCODE_RELATIONS_FACET = BFacets.make(SHOULD_ENCODE_RELATIONS_KEY, true);
  private static final BFacets SHOULD_NOT_ENCODE_RELATIONS_FACET = BFacets.make(SHOULD_ENCODE_RELATIONS_KEY, false);

  /**
   * Static Class: Buffer
   * This class is used to aid in flushing the bytes to the output stream.
   */
  static class Buffer extends ByteArrayOutputStream
  {
    byte[] getBytes() { return buf; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * {@link Options} to encode both {@link Entity#tags()} and
   * {@link Entity#relations()}.
   *
   * @since Niagara 4.9
   */
  public static final Options ENCODE_TAGS_AND_RELATIONS = new Options(true, true);

  /**
   * {@link Options} to encode neither {@link Entity#tags()} nor
   * {@link Entity#relations()}.
   *
   * @since Niagara 4.9
   */
  public static final Options NO_TAGS_OR_RELATIONS = new Options(false, false);

  private static final boolean ALLOW_EMPTY = true;

  private final OutputStream out;
  private final Options options;
  private JSONWriter jout;
  private Writer writer;
  private final Buffer buffer = new Buffer();
  private volatile boolean root = true;

  /**
   * Encoding options for serializing Niagara {@link Entity} objects into JSON-encoded objects.
   * The {@link Entity#getOrdToEntity()} is always encoded. {@link Entity#tags()} and/or
   * {@link Entity#relations()} can be optionally encoded; they are both encoded by default.
   *
   * @since Niagara 4.9
   */
  public static class Options
  {
    public Options(boolean shouldEncodeTags, boolean shouldEncodeRelations)
    {
      this.shouldEncodeTags = shouldEncodeTags;
      this.shouldEncodeRelations = shouldEncodeRelations;
    }

    /**
     * Whether the {@link JsonEntityEncoder} should encode the {@link Entity#tags()}.
     */
    public boolean shouldEncodeTags()
    {
      return shouldEncodeTags;
    }

    /**
     * Whether the {@link JsonEntityEncoder} should encode the {@link Entity#relations()}.
     */
    public boolean shouldEncodeRelations()
    {
      return shouldEncodeRelations;
    }

    private final boolean shouldEncodeTags;
    private final boolean shouldEncodeRelations;
  }
}
