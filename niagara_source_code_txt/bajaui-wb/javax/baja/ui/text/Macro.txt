/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;                        

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.baja.ui.BAccelerator;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.Command;
import javax.baja.ui.commands.CompoundCommand;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.text.commands.DocumentEnd;
import javax.baja.ui.text.commands.DocumentStart;
import javax.baja.ui.text.commands.InsertText;
import javax.baja.ui.text.commands.LineEnd;
import javax.baja.ui.text.commands.LineStart;
import javax.baja.ui.text.commands.MoveDown;
import javax.baja.ui.text.commands.MoveLeft;
import javax.baja.ui.text.commands.MoveRight;
import javax.baja.ui.text.commands.MoveUp;
import javax.baja.ui.text.commands.WordLeft;
import javax.baja.ui.text.commands.WordRight;
import javax.baja.ui.transfer.Clipboard;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.ui.transfer.TransferFormat;
import javax.baja.xml.XElem;

import javax.baja.nre.util.TextUtil;

/**
 * Macro
 *
 * @author    Mike Jarmy
 * @creation  23 Sep 03
 * @version   $Revision: 5$ $Date: 1/30/08 9:02:48 AM EST$
 * @since     Baja 1.0
 */
class Macro 
{                                        
////////////////////////////////////////////////////////////////
// constructor
////////////////////////////////////////////////////////////////

  Macro(XElem m)
  {
    BAccelerator acc = BAccelerator.make(m.get("accelerator"));                
    
    // get the prompts                            
    this.prompts = decodePrompts(m.elems("prompts"));    
                      
    // make a by-name map for the prompts
    Map<String, Macro.Prompt> promptMap = new HashMap<>();
    for (int i = 0; i < prompts.length; i++)
    {
      promptMap.put(prompts[i].name, prompts[i]);
    }

    // load everything else    
    XElem[] phr = m.elems();
    List<Phrase> list = new ArrayList<>();
    for(int j=0; j<phr.length; ++j)
    {             
      String name = phr[j].name();
    
      // valid 'canned' phrase
      if (validPhraseSet.contains(name))           
      {                         
        if      (name.equals("text"))   list.add(new TextPhrase(phr[j].get("val")));
        else if (name.equals("date"))   list.add(new DatePhrase(phr[j].get("format")));
        else if (name.equals("nl"))     list.add(new NewlinePhrase());
        else if (name.equals("replace"))     
        {
          list.add(new ReplacePhrase(phr[j].get("from"), 
                                     phr[j].get("to"), 
                                     phr[j].get("options", "")));
        }
        else list.add(new NavPhrase(name));
      }                       
      // a prompt (we hope)
      else if (!name.equals("prompts"))
      {
        Prompt p = promptMap.get(name);
        if (p == null) 
        {
          System.out.println("WARNING: ignoring invalid macro phrase '" + name + "'.");  
        }                                                                                          
        else
        {
          list.add(new PromptPhrase(p));
        }
      }
    } 
    
    this.acc = acc;
    this.phrases = list.toArray(new Phrase[list.size()]);
  }                                           
  
////////////////////////////////////////////////////////////////
// package-scope
////////////////////////////////////////////////////////////////

  /**
   * makeCommand
   */
  CompoundCommand makeCommand(BTextEditor editor) 
  {  
    List<Command> list = new ArrayList<>();
                         
    if (!loadPrompts(editor)) return null;
    
    for (int i = 0; i < phrases.length; i++)
    {                          
      Command cmd = phrases[i].makeCommand(editor);
      if (cmd != null) list.add(cmd);
    }           
    
    return new CompoundCommand(editor, null, list.toArray(new Command[list.size()]));
  }

////////////////////////////////////////////////////////////////
// Prompts
////////////////////////////////////////////////////////////////

  /**
   * Prompt
   */
  static class Prompt
  {                                             
    Prompt(String name, String defaultVal) 
    { 
      this.name = name; 
      this.defaultVal = defaultVal; 
      this.val = null;
    }
    private String name;  
    private String defaultVal;
    private String val;
  }  

  /**
   * decodePrompts
   */
  private static Prompt[] decodePrompts(XElem[] defn)
  {
    if (defn.length > 0)            
    {
      if (defn.length > 1) System.out.println("WARNING: ignoring extra extraneous prompts definitions.");  
      
      XElem[] x = defn[0].elems();                                                                             
      Prompt[] p = new Prompt[x.length];
      for (int i = 0; i < x.length; i++)
      {
        p[i] = new Prompt(x[i].name(), x[i].get("default", null));
      }
      return p;
    }   
    else return new Prompt[0];    
  }      

  /**
   * loadPrompts via a dialog
   */    
  private boolean loadPrompts(BTextEditor editor)
  {                                               
    if (prompts.length == 0) return true;
    
    BGridPane pane = new BGridPane();                              
    BTextField[] fields = new BTextField[prompts.length];   
       
    for (int i = 0; i < prompts.length; i++)
    {
      fields[i] = new BTextField();                                
      if (prompts[i].defaultVal != null) fields[i].setText(prompts[i].defaultVal);
      pane.add(null, new BLabel(TextUtil.capitalize(prompts[i].name + ":")));
      pane.add(null, fields[i]);                      
    }                                              
        
    if (BDialog.open(editor, null, pane, BDialog.OK_CANCEL) == BDialog.CANCEL) 
      return false;

    for (int i = 0; i < prompts.length; i++)
    {
      prompts[i].val = fields[i].getText();      
    } 
    
    return true;
  }                                      

////////////////////////////////////////////////////////////////
// Phrase
////////////////////////////////////////////////////////////////

  private abstract static class Phrase
  {
    abstract Command makeCommand(BTextEditor editor);
    
    static InsertText makeInsert(BTextEditor editor, String text)
    {                                                              
      if ((text == null) || (text.length() == 0)) return null;
      return new InsertText(editor, text);  
    }
  }  

  private static class TextPhrase extends Phrase
  {                                             
    private TextPhrase(String val) { this.val = val; }
    private String val;
    
    Command makeCommand(BTextEditor editor)
    {
      return makeInsert(editor, val);
    }
  }  

  private static class PromptPhrase extends Phrase
  {                                             
    private PromptPhrase(Prompt prompt) { this.prompt = prompt; }
    private Prompt prompt;
    
    Command makeCommand(BTextEditor editor)
    {
      return makeInsert(editor, prompt.val);
    }
  }  

  private static class DatePhrase extends Phrase
  {                                             
    private DatePhrase(String format) 
    {                    
      this.format = format;
    }                       
    private String format;
    
    Command makeCommand(BTextEditor editor)
    {                                                                         
      return makeInsert(
        editor,       
        (new SimpleDateFormat(format)).format(
          new Date(System.currentTimeMillis())));
    }
  }  

  private static class NewlinePhrase extends Phrase
  {                                             
    Command makeCommand(BTextEditor editor)
    {
      return makeInsert(editor, 
        "\n" + TextUtil.getSpaces(
          editor.getController().columnForNewline()));       
    }
  }
  
  private static class ReplacePhrase extends Phrase
  {                                             
    private ReplacePhrase(String from, String to, String options)
    {
      this.from=from;
      this.to=to;
      this.options=options;
    }
    Command makeCommand(BTextEditor editor)
    {
      String fromClipBoard =
        Clipboard.getDefault().getContents().getData(TransferFormat.string).toString();                        
      String toPaste = TextUtil.replace(fromClipBoard, from, to);
       if(options != null && options.indexOf("trimRight") > -1)
        toPaste = TextUtil.trimRight(toPaste);
      
      if(options != null && options.indexOf("clipBoard") > -1)  
      {
        Clipboard.getDefault().setContents(TransferEnvelope.make(toPaste));
        return makeInsert(editor, "");       
      }
      
      return makeInsert(editor, toPaste);       
    }
    
    private String from;
    private String to;    
    private String options;
  }  

  private static class NavPhrase extends Phrase
  {                                               
    private NavPhrase(String name) { this.name = name; }
    private String name;
    
    Command makeCommand(BTextEditor editor)
    {                                                  
      if      (name.equals("up"))        return new MoveUp(editor);
      else if (name.equals("down"))      return new MoveDown(editor);
      else if (name.equals("left"))      return new MoveLeft(editor);
      else if (name.equals("right"))     return new MoveRight(editor);
      else if (name.equals("docEnd"))    return new DocumentEnd(editor);
      else if (name.equals("docStart"))  return new DocumentStart(editor);
      else if (name.equals("lineEnd"))   return new LineEnd(editor);
      else if (name.equals("lineStart")) return new LineStart(editor);
      else if (name.equals("wordLeft"))  return new WordLeft(editor);
      else if (name.equals("wordRight")) return new WordRight(editor);  
      else throw new IllegalStateException();
    }
  }  
  
  
////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  BAccelerator acc;
  Phrase[] phrases;
  Prompt[] prompts;
                             
////////////////////////////////////////////////////////////////
// static
////////////////////////////////////////////////////////////////

  private static String[] validPhrases = {
    "text", "nl", "date",
    "up", "down", 
    "left", "right",      
    "docEnd", "docStart", 
    "lineEnd", "lineStart", 
    "wordLeft", "wordRight", "replace" };
    
  private static Set<String> validPhraseSet = new HashSet<>();
  
  static 
  { 
    for (int i = 0; i < validPhrases.length; i++) 
      validPhraseSet.add(validPhrases[i]);
  }
}