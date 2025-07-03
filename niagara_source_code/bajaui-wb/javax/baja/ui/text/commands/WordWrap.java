/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import java.io.*;
import java.util.*;
import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * WordWrap takes the selected text removes all occurances
 * of single line breaks, then inserts new line breaks so 
 * that each line is no longer than 80 characters.
 *
 * @author    Brian Frank
 * @creation  1 Nov 01
 * @version   $Revision: 3$ $Date: 3/28/05 10:32:39 AM EST$
 * @since     Baja 1.0
 */
public class WordWrap
  extends EditCommand
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public WordWrap(BTextEditor editor)
  {
    super(editor, BKeyBindings.wordWrap);    
  }  

////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////  

  public CommandArtifact doInvoke()    
  {
    // get the original selected text
    String orig = editor.getModel().getSelectedText();
    if (orig == null || orig.length() == 0) return null;
    
    // reformat it to be wrapped
    char[] wrap = wrap(orig.toCharArray(), 79);
    
    // remove selection, and inserted reformatted text
    return insert(wrap);
  }

  /**
   * Given a body of text, reformat it to be wrapped
   * using the specified line width.
   */
  public static String wrap(String orig, int lineWidth)
  { 
    return new String(wrap(orig.toCharArray(), lineWidth));
  }
  
  /**
   * Given a body of text, reformat it to be wrapped
   * using the specified line width.
   */
  public static char[] wrap(char[] orig, int lineWidth)
  { 
    try
    {
      // strip \r\n combos
      String normal = stripRNs(orig);
      
      // break into words with two or more newlines
      // remaining a "paragraph break words"
      String[] words = parse(normal);
      
      // write the resulting text
      CharArrayWriter out = new CharArrayWriter();
      int line = 0;
      boolean lastWasBreak = true;
      for(int i=0; i<words.length; ++i)
      {
        String word = words[i];    
          
        // just stream newlines that remain
        if (word.charAt(0) == '\n') 
        { 
          if (!lastWasBreak) out.write('\n');
          out.write(word); 
          lastWasBreak = true;
          continue; 
        }
        
        // if we just wrote a break, then the 
        // word has to go on this line
        if (lastWasBreak)
        {
          out.write(word);
          line = word.length();
        }
        
        // check for enough space to fit space + word
        else if (line + 1 + word.length() > lineWidth)
        {
          out.write('\n');  
          out.write(word);
          line = word.length(); 
        }
        
        // otherwise just stream word
        else 
        { 
          out.write(' '); 
          out.write(word);
          line += 1 + word.length(); 
        }
        
        lastWasBreak = false; 
      }
      
      // all done!
      return out.toCharArray();
    }
    catch(IOException e)
    {
      throw new IllegalStateException(e.toString());  // should never happen
    }
  }

  static String stripRNs(char[] orig)
  {
    StringBuilder s = new StringBuilder(orig.length);
    int origLength = orig.length;
    for(int i=0; i<origLength; ++i)
    {
      char cur = orig[i];
      char next = (i+1 < origLength) ? orig[i+1] : 0;
      if (cur == '\r' && next == '\n') continue;
      s.append(cur);
    }
    return s.toString();
  }
  
  static String[] parse(String src)
  {
    ArrayList<String> v = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(new String(src), " \n", true);
    boolean lastWasNewline = false;
    while(st.hasMoreTokens())
    {
      String tok = st.nextToken();
      char c = tok.charAt(0);
      if (c != ' ')
      {
        if (c != '\n' || lastWasNewline) v.add(tok);
      }
      lastWasNewline = tok.charAt(0) == '\n';
    }
    return v.toArray(new String[v.size()]);
  }

}

