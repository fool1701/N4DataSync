/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import java.util.Objects;

/**
 * Position encapsulates a logical two dimensional character 
 * position in a document with a zero based line and column
 * index.  Because BTextWidget allows the caret to be freely 
 * moved around the pane where there might not actually be text 
 * yet, a Position doesn't necessary always reference a real 
 * position in the document text.
 *
 * @author    Brian Frank       
 * @creation  12 Dec 00
 * @version   $Revision: 6$ $Date: 7/22/10 3:11:53 PM EDT$
 * @since     Baja 1.0
 */
public final class Position
  implements Comparable<Position>
{ 

  /**
   * Constructor with line and column.
   *
   * @param line zero indexed line number
   * @param column zero indexed column number
   */
  public Position(int line, int column)
  {
    this.line = line;
    this.column = column;
  }
  
  /**
   * Return a negative integer, zero, or a positive integer 
   * as this Position is less than, equal to, or greater than 
   * the specified Position.
   */
  public final int compareTo(Position obj)
  {
    Position p = obj;
    if (line < p.line) return -1;
    if (line > p.line) return 1;
    if (column < p.column) return -1;
    if (column > p.column) return 1;
    return 0;
  }
      
  /**
   * Equality.
   */
  @Override
  public final boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (!(obj instanceof Position)) return false;
    return compareTo((Position)obj) == 0;
  }

  /**
   * Hash.
   */
  @Override
  public int hashCode() {
    return Objects.hash(line, column);
  }

  /**
   * To string.
   */
  @Override
  public final String toString()
  {
    return "[" + line + "," + column + "]";
  }
  
  /**
   * Zero indexed line number.
   */
  public final int line;
  
  /**
   * Zero indexed column number.
   */
  public final int column;
  
}
