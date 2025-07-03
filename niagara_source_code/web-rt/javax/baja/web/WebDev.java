/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.naming.BModuleScheme;
import javax.baja.spy.ISpyDir;
import javax.baja.spy.Spy;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BAbsTime;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Used to enable/disable Web Development mode for a Web Application.
 * <p>
 * When a Web Application runs in release mode, the resources that form part of the 
 * web application are compressed (i.e. JS is minified etc). When a problem occurs with 
 * a web application, it's desirable to run the application in web development mode. 
 * In web development mode, resources for the application are no longer compressed/optimized.
 * These non-optimized resources typically provide more information when an error occurs.
 *
 * @author   gjohnson on Jul 30, 2012
 * @since    Niagara 3.8 
 */
public final class WebDev
{
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  private WebDev(String name) 
  {
    this.name = name;
  }
  
  private WebDev(String name, boolean enabled)
  {
    this.name = name;
    this.enabled = enabled;
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Return a web development object for the specified name. If the web
   * development name is not enabled then create a new one and return it. 
   * 
   * @param name The name of the webdev.
   * @return The WebDev.
   */
  public static WebDev get(String name)
  {    
    // Ensure everything is loaded
    load();
    
    WebDev wd;
    synchronized(webDevs)
    {            
      wd = webDevs.get(name);
      
      if (wd == null)
      {
        wd = new WebDev(name);
        webDevs.put(name, wd);
        lastModified = BAbsTime.now();
      }
    }
    
    return wd;
  }
  
  /**
   * Set whether web development mode is enabled.
   * 
   * @param enabled The enabled flag.
   */
  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
    lastModified = BAbsTime.now();
  }
  
  /**
   * Return true if web development mode is enabled.
   * 
   * @return Returns true if enabled.
   */
  public boolean isEnabled()
  {
    return enabled;
  }
  
  /**
   * Return the name of the web development object.
   * 
   * @return The name.
   */
  public String getName()
  {
    return name;
  }
      
  /**
   * Return a list of all the WebDev Objects.
   * 
   * @return A list of WebDevs.
   */
  public static List<WebDev> getWebDevs()
  {
    synchronized(webDevs)
    {
      return webDevs
        .values()
        .stream()
        .sorted((a, b) -> a.getName().compareTo(b.getName()))
        .collect(Collectors.toList());
    }
  }

  /**
   * Return when the Web Dev settings when last modified.
   */
  public static BAbsTime getLastModified()
  {
    // Ensure we're loaded before returning the last modified date time.
    load();
    return lastModified;
  }

////////////////////////////////////////////////////////////////
// File Management
////////////////////////////////////////////////////////////////  
  
  /**
   * Return the Web Development Properties file.
   * 
   * @return The Web Dev File.
   */
  private static File getWebDevFile()
  {
    return new File(
      NIAGARA_USER_HOME +
      File.separator + "etc" +
      File.separator + "webdev.properties"
    );
  }
  
  /**
   * Save the current Web Dev objects out to the "webdev.properties" file.
   */
  public static void save()
  {
    AccessController.doPrivileged((PrivilegedAction<Void>)() ->
    {
      if(!getWebDevFile().getParentFile().exists())
      {
        getWebDevFile().getParentFile().mkdirs();
      }

      try (PrintWriter out = new PrintWriter(new FileWriter(getWebDevFile())))
      {
        out.println("#");
        out.println("# WebDev file saved: " + new Date());
        out.println("#");

        for (WebDev wd : getWebDevs())
          out.println(wd.getName() + "=" + (wd.isEnabled() ? "true" : "false"));
      }
      catch (IOException e)
      {
        System.out.println("ERROR: Canot save 'webdev.properties'");
        System.out.println("  " + e);
      }

      return null;
    });
  }
  
  /**
   * Load the Web Dev objects out of the "webdev.properties" file if
   * not already loaded.
   */
  public static void load() 
  {
    AccessController.doPrivileged((PrivilegedAction<Void>)() ->
    {
      synchronized (webDevs)
      {
        // Lazily load Properties
        if (props == null)
        {
          props = new Properties();
          try
          {
            File file = getWebDevFile();

            try
            {
              long lm = file.lastModified();

              if (lm <= 0l)
                lastModified = BAbsTime.now();
              else
                lastModified = BAbsTime.make(lm);
            }
            catch (Exception e)
            {
              lastModified = BAbsTime.now();
            }

            try (InputStream in = new FileInputStream(file))
            {
              props.load(in);
            }
          }
          catch(Exception ignore) {}

          // If the Properties are loaded then create the corresponding WebDev objects
          if (props != null)
          {
            Enumeration<Object> keys = props.keys();
            while(keys.hasMoreElements())
            {
              Object k = keys.nextElement();

              WebDev wd = new WebDev(k.toString(), props.get(k).equals("true"));
              webDevs.put(k.toString(), wd);
            }
          }
        }
      }

      return null;
    });
  }

  /**
   * Return true if the web development mode is enabled on any module.
   *
   * @since Niagara 4.10
   */
  public static boolean isAnyWebDevEnabled()
  {
    return getWebDevs().stream().anyMatch(WebDev::isEnabled);
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////  
  
  private static class WebDevSpy
      extends Spy
      implements ISpyDir
  {
    @Override
    public String[] list()
    {
      return new String[0];
    }

    @Override
    public Spy find(String name)
    {
      if (SpyWriter.verifyNameAndCsrfToken(name, "save"))
      {
        // Handle save command
        log.info("Saved 'webdev.properties'.");
        save();
        return new MessageWebDevSetupSpy("Saved 'webdev.properties' file"); 
      }
      else if (SpyWriter.verifyNameAndCsrfToken(name, "enableAll"))
      {
        synchronized(webDevs)
        {
          webDevs.values().forEach(wd -> wd.setEnabled(true));
        }
        
        return new MessageWebDevSetupSpy("Enabled All");
      }
      else if (SpyWriter.verifyNameAndCsrfToken(name, "disableAll"))
      {
        synchronized(webDevs)
        {
          webDevs.values().forEach(wd -> wd.setEnabled(false));
        }
        
        return new MessageWebDevSetupSpy("Disabled All");
      }

      String namePart = SpyWriter.getNameWithoutCsrfToken(name);

      if (namePart.indexOf("enable-") == 0)
      {
        // Handle enable web dev command
        String wdName = namePart.substring(7, namePart.length());
        
        WebDev wd;
        synchronized(webDevs)
        {
          wd = webDevs.get(wdName);
        }
        
        if (wd != null)
        {
          wd.setEnabled(true);
          return new MessageWebDevSetupSpy("Enabled: " + wdName);
        }
        else
          return new MessageWebDevSetupSpy("Error enabing: could not find: " + wdName);
      }
      else if (namePart.indexOf("disable-") == 0)
      {
        // Handle disable web dev command
        String wdName = namePart.substring(8, namePart.length());
        
        WebDev wd;
        synchronized(webDevs)
        {
          wd = webDevs.get(wdName);
        }   
        if (wd != null)
        {
          wd.setEnabled(false);
          return new MessageWebDevSetupSpy("Disabled: " + wdName);
        }
        else
          return new MessageWebDevSetupSpy("Error disabling: could not find: " + wdName);
      }
      
      return null;
    }
    
    @Override
    public void write(SpyWriter out)
        throws Exception
    {      
      // Create a table of the enabled/disabled web devs
      out.startTable(/*border*/true);
      out.unsafe().trTitle("Web Devs " +
        "[" + out.createLink("spy:/webDevSetup/"+SpyWriter.addCsrfToken("save"), "Save To File") + "] " +
        "[" + out.createLink("spy:/webDevSetup/"+SpyWriter.addCsrfToken("enableAll"), "Enable All") + "]  " +
        "[" + out.createLink("spy:/webDevSetup/"+SpyWriter.addCsrfToken("disableAll"), "Disable All") + "]", 2);
      
      out.w("<tr><th align='left'>webdev.properties</th><td align='left'>")
         .w(lastModified).w("</td></tr>\n");
      
      out.w("<tr><th align='left'>Module Dev Mode</th><td align='left'>")
      .w(BModuleScheme.isModuleDevEnabled() ? "[Enabled]" : "[Disabled]").w("</td></tr>\n");
      
      out.w("<tr>")
         .thTitle("Name")
         .thTitle("State")
         .w("</tr>");

      // For each WebDev object, create an entry in the table
      for(WebDev wd : WebDev.getWebDevs())
      {
        String name = wd.getName();
        boolean enabled = wd.isEnabled();       
        
        out.w("<tr>");
        out.w("<td align='left' nowrap='true'>").w(name).w("</td>");
        
        out.w("<td>");
        out.w("[<a href='spy:/webDevSetup/");
        out.safe(enabled ? SpyWriter.addCsrfToken("disable-"+name) : SpyWriter.addCsrfToken("enable-"+name));
        out.w("'>");
        out.w(enabled ? "Enabled" : "Disabled");
        out.w("</a>]");
        out.w("</td>");
        
        out.w("</tr>\n");
      }
      
      out.endTable();
    }
  }
  
  private static final class MessageWebDevSetupSpy 
      extends WebDevSpy
  {
    private MessageWebDevSetupSpy(String message)
    {
      this.message = message;
    }
    
    @Override
    public void write(SpyWriter out)
        throws Exception
    {
      out.write("<br/><b>");
      out.write(message);
      out.write("</b><br/><br/>");
      super.write(out);
    }
    
    String message;
  }  
  
  static 
  {
    Spy.ROOT.add("webDevSetup", new WebDevSpy());
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private final String name;
  private boolean enabled;
  
  private static final Map<String, WebDev> webDevs = new HashMap<>();
  
  private static final Logger log = Logger.getLogger("webDev");
  private static volatile Properties props;
  private volatile static BAbsTime lastModified = BAbsTime.NULL;

  private static final String NIAGARA_USER_HOME =
    AccessController.doPrivileged((PrivilegedAction<String>)
      () -> System.getProperty("niagara.user.home"));
}
