/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.entityIo.json;

/**
 * JsonEntityConst contains some basic constants for use in encoding and decoding
 * Entities to/from JSON.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 1/29/14
 *         Time: 11:58 AM
 */
public interface JsonEntityConst
{
  char MARKER = 'M';
  char BOOLEAN = 'B';
  char INTEGER = 'I';
  char LONG = 'L';
  char FLOAT = 'F';
  char DOUBLE = 'D';
  char STRING = 'S';
  char ABSTIME = 'A';
  char RELTIME = 'R';
  char ORD = 'O';
  char ENUM = 'Y';
  char RANGE = 'G';
  char TIMEZONE = 'Z';
  char UNIT = 'U';
  char OBJECT = 'J';

  // Nonstandard encoding constants
  String JSON_F_NAN = "fNaN";
  String JSON_F_POS_INF = "fpInf";
  String JSON_F_NEG_INF = "fnInf";
  String JSON_D_NAN = "dNaN";
  String JSON_D_POS_INF = "dpInf";
  String JSON_D_NEG_INF = "dnInf";

  /**
   * This const handles non-standard JSON encoding.
   * For example, JSON does not allow for floating point NaN, or positive or negative
   * infinity.  However, these are valid IEEE floating point numbers which may exist
   * in a Niagara system, so we need to be able to handle them.
   */
  char NON_STD = 'N';

  String KEY_TAGS = "tags";
  String KEY_RELATIONS = "relations";
  String KEY_ORD = "ord";
  String KEY_ENTITY = "entity";
  String KEY_RELATION_TAGS = "rtags";
  String KEY_RELATION_ENTITY_ORD = "entityOrd";
  String KEY_REL_ID = "rId";
  String KEY_REL_INBOUND = "inbound";

}
