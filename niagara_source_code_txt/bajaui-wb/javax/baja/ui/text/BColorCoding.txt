/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BColorCoding stores the colors mappings for 
 * segment type codes.
 *
 * @author    Brian Frank
 * @creation  6 Jul 01
 * @version   $Revision: 9$ $Date: 3/28/05 10:32:32 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "foreground",
  type = "BColor",
  defaultValue = "BColor.black"
)
@NiagaraProperty(
  name = "whitespace",
  type = "BColor",
  defaultValue = "BColor.make(192,192,192)"
)
@NiagaraProperty(
  name = "numberLiteral",
  type = "BColor",
  defaultValue = "BColor.make(128, 0, 128)"
)
@NiagaraProperty(
  name = "stringLiteral",
  type = "BColor",
  defaultValue = "BColor.make(128, 0, 128)"
)
@NiagaraProperty(
  name = "identifier",
  type = "BColor",
  defaultValue = "BColor.black"
)
@NiagaraProperty(
  name = "keyword",
  type = "BColor",
  defaultValue = "BColor.blue"
)
@NiagaraProperty(
  name = "preprocessor",
  type = "BColor",
  defaultValue = "BColor.make(128, 0, 0)"
)
@NiagaraProperty(
  name = "bracket",
  type = "BColor",
  defaultValue = "BColor.red"
)
@NiagaraProperty(
  name = "lineComment",
  type = "BColor",
  defaultValue = "BColor.make(0, 128, 0)"
)
@NiagaraProperty(
  name = "multiLineComment",
  type = "BColor",
  defaultValue = "BColor.make(0, 128, 0)"
)
@NiagaraProperty(
  name = "nonJavadocComment",
  type = "BColor",
  defaultValue = "BColor.gray"
)
public class BColorCoding
  extends BComponent
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.text.BColorCoding(1807245514)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "foreground"

  /**
   * Slot for the {@code foreground} property.
   * @see #getForeground
   * @see #setForeground
   */
  public static final Property foreground = newProperty(0, BColor.black, null);

  /**
   * Get the {@code foreground} property.
   * @see #foreground
   */
  public BColor getForeground() { return (BColor)get(foreground); }

  /**
   * Set the {@code foreground} property.
   * @see #foreground
   */
  public void setForeground(BColor v) { set(foreground, v, null); }

  //endregion Property "foreground"

  //region Property "whitespace"

  /**
   * Slot for the {@code whitespace} property.
   * @see #getWhitespace
   * @see #setWhitespace
   */
  public static final Property whitespace = newProperty(0, BColor.make(192,192,192), null);

  /**
   * Get the {@code whitespace} property.
   * @see #whitespace
   */
  public BColor getWhitespace() { return (BColor)get(whitespace); }

  /**
   * Set the {@code whitespace} property.
   * @see #whitespace
   */
  public void setWhitespace(BColor v) { set(whitespace, v, null); }

  //endregion Property "whitespace"

  //region Property "numberLiteral"

  /**
   * Slot for the {@code numberLiteral} property.
   * @see #getNumberLiteral
   * @see #setNumberLiteral
   */
  public static final Property numberLiteral = newProperty(0, BColor.make(128, 0, 128), null);

  /**
   * Get the {@code numberLiteral} property.
   * @see #numberLiteral
   */
  public BColor getNumberLiteral() { return (BColor)get(numberLiteral); }

  /**
   * Set the {@code numberLiteral} property.
   * @see #numberLiteral
   */
  public void setNumberLiteral(BColor v) { set(numberLiteral, v, null); }

  //endregion Property "numberLiteral"

  //region Property "stringLiteral"

  /**
   * Slot for the {@code stringLiteral} property.
   * @see #getStringLiteral
   * @see #setStringLiteral
   */
  public static final Property stringLiteral = newProperty(0, BColor.make(128, 0, 128), null);

  /**
   * Get the {@code stringLiteral} property.
   * @see #stringLiteral
   */
  public BColor getStringLiteral() { return (BColor)get(stringLiteral); }

  /**
   * Set the {@code stringLiteral} property.
   * @see #stringLiteral
   */
  public void setStringLiteral(BColor v) { set(stringLiteral, v, null); }

  //endregion Property "stringLiteral"

  //region Property "identifier"

  /**
   * Slot for the {@code identifier} property.
   * @see #getIdentifier
   * @see #setIdentifier
   */
  public static final Property identifier = newProperty(0, BColor.black, null);

  /**
   * Get the {@code identifier} property.
   * @see #identifier
   */
  public BColor getIdentifier() { return (BColor)get(identifier); }

  /**
   * Set the {@code identifier} property.
   * @see #identifier
   */
  public void setIdentifier(BColor v) { set(identifier, v, null); }

  //endregion Property "identifier"

  //region Property "keyword"

  /**
   * Slot for the {@code keyword} property.
   * @see #getKeyword
   * @see #setKeyword
   */
  public static final Property keyword = newProperty(0, BColor.blue, null);

  /**
   * Get the {@code keyword} property.
   * @see #keyword
   */
  public BColor getKeyword() { return (BColor)get(keyword); }

  /**
   * Set the {@code keyword} property.
   * @see #keyword
   */
  public void setKeyword(BColor v) { set(keyword, v, null); }

  //endregion Property "keyword"

  //region Property "preprocessor"

  /**
   * Slot for the {@code preprocessor} property.
   * @see #getPreprocessor
   * @see #setPreprocessor
   */
  public static final Property preprocessor = newProperty(0, BColor.make(128, 0, 0), null);

  /**
   * Get the {@code preprocessor} property.
   * @see #preprocessor
   */
  public BColor getPreprocessor() { return (BColor)get(preprocessor); }

  /**
   * Set the {@code preprocessor} property.
   * @see #preprocessor
   */
  public void setPreprocessor(BColor v) { set(preprocessor, v, null); }

  //endregion Property "preprocessor"

  //region Property "bracket"

  /**
   * Slot for the {@code bracket} property.
   * @see #getBracket
   * @see #setBracket
   */
  public static final Property bracket = newProperty(0, BColor.red, null);

  /**
   * Get the {@code bracket} property.
   * @see #bracket
   */
  public BColor getBracket() { return (BColor)get(bracket); }

  /**
   * Set the {@code bracket} property.
   * @see #bracket
   */
  public void setBracket(BColor v) { set(bracket, v, null); }

  //endregion Property "bracket"

  //region Property "lineComment"

  /**
   * Slot for the {@code lineComment} property.
   * @see #getLineComment
   * @see #setLineComment
   */
  public static final Property lineComment = newProperty(0, BColor.make(0, 128, 0), null);

  /**
   * Get the {@code lineComment} property.
   * @see #lineComment
   */
  public BColor getLineComment() { return (BColor)get(lineComment); }

  /**
   * Set the {@code lineComment} property.
   * @see #lineComment
   */
  public void setLineComment(BColor v) { set(lineComment, v, null); }

  //endregion Property "lineComment"

  //region Property "multiLineComment"

  /**
   * Slot for the {@code multiLineComment} property.
   * @see #getMultiLineComment
   * @see #setMultiLineComment
   */
  public static final Property multiLineComment = newProperty(0, BColor.make(0, 128, 0), null);

  /**
   * Get the {@code multiLineComment} property.
   * @see #multiLineComment
   */
  public BColor getMultiLineComment() { return (BColor)get(multiLineComment); }

  /**
   * Set the {@code multiLineComment} property.
   * @see #multiLineComment
   */
  public void setMultiLineComment(BColor v) { set(multiLineComment, v, null); }

  //endregion Property "multiLineComment"

  //region Property "nonJavadocComment"

  /**
   * Slot for the {@code nonJavadocComment} property.
   * @see #getNonJavadocComment
   * @see #setNonJavadocComment
   */
  public static final Property nonJavadocComment = newProperty(0, BColor.gray, null);

  /**
   * Get the {@code nonJavadocComment} property.
   * @see #nonJavadocComment
   */
  public BColor getNonJavadocComment() { return (BColor)get(nonJavadocComment); }

  /**
   * Set the {@code nonJavadocComment} property.
   * @see #nonJavadocComment
   */
  public void setNonJavadocComment(BColor v) { set(nonJavadocComment, v, null); }

  //endregion Property "nonJavadocComment"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BColorCoding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the color used to draw the specified segment type.  
   * This method will automatically check if the segment 
   * appears inside a line or multi-line comment.
   */
  public BColor getColor(Segment segment)
  {
    if (segment.type == Segment.SPACES ||
        segment.type == Segment.TAB ||
        segment.type == Segment.NEWLINE)
      return getWhitespace();
        
    if (segment.isInMultiLineComment())
    {
      if (segment.isInNonJavadoc())
        return getNonJavadocComment();
      else
        return getMultiLineComment();
    }

    if (segment.isInStringLiteral())
      return getStringLiteral();
    
    if (segment.isInLineComment())
      return getLineComment();
    
    switch(segment.type)
    {
      case Segment.TEXT:           return getForeground();
      case Segment.NUMBER_LITERAL: return getNumberLiteral();
      case Segment.IDENTIFIER:     return getIdentifier();
      case Segment.KEYWORD:        return getKeyword();
      case Segment.PREPROCESSOR:   return getPreprocessor();
      case Segment.BRACKET:        return getBracket();
      default:                     return getForeground();
    }
  }
  
  public BIcon getIcon() { return icon; }
  static BIcon icon = BIcon.std("colorWheel.png");

}
