/*
 * Copyright 2006, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;

import com.tridium.web.BDefaultLoginTemplate;

/**
 * LoginTemplate is the hook for defining a custom login page for a web
 * application. The job of the template is to write the full HTML content
 * required to display the login form.  In order to use the Niagara
 * standard cookie based authentication you need to follow a few rules.
 * <p>
 * The form body must contain at minimum the following code.  The elements
 * may be arranged however you want, but all must exist.  Attributes may
 * be added (such as style and class attributes), but none may be changed or
 * removed.
 * <pre><code>
 *   &lt;form method='post' action='/j_security_check'&gt;
 *     &lt;input type='text' name='j_username' /&gt;
 *     &lt;input type='text' name='j_password' /&gt;
 *     &lt;input type='submit' name='submit' /&gt;
 *   &lt;/form&gt;
 *</code></pre>
 * It should be noted that in a lot of cases, it's unnecessary to 
 * override BLoginTemplate directly. When using the default log in template,
 * Properties can be added to a Station's WebService to customize the log in 
 * screen...
 * <ul>
 *   <li>
 *     <strong>logo</strong>: an ORD that maps to a logo that's used in the log in template.
 *   </li>
 *   <li>
 *     <strong>loginCss</strong>: an ORD that maps to a CSS file.
 *   </li>
 * </ul>
 * <p>
 *
 * @author    John Sublett and Gareth Johnson
 * @creation  30 Aug 2006
 * @version   $Revision: 8$ $Date: 12/30/10 11:14:06 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BLoginTemplate
  extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BLoginTemplate(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLoginTemplate.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  protected BLoginTemplate() {}
  


  
////////////////////////////////////////////////////////////////
// Template
////////////////////////////////////////////////////////////////
  
  /**
   * Write the full HTML login page.
   *
   * @param req The request object.
   * @param resp The response object.
   */
  public abstract void write(HttpServletRequest req,
                             HttpServletResponse resp)

    throws IOException, ServletException;

  /**
   * Convert a resource path to an ord that can be resolved to locate
   * the resource.  This can be used to included images, etc. on the
   * login page.  The actual path for accessing a resource is
   * /login/path.  The path that is passed to this method has "/login/"
   * stripped off.
   * <p>
   * Please note, the resolved file types are restricted to web resources
   * (JavaScript, CSS and images).
   *
   * For example: /login/a/b/c =&gt; a/b/c
   */
  public abstract BOrd resourceToOrd(String path);
  
////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////
  
  /**
   * Returns the existing log in template. Please note, this should only 
   * be called from the Station VM.
   * 
   * @return The current template.
   */
  public static final BLoginTemplate getLoginTemplate()
  {
    BWebService service = BWebService.getMainService();
    BTypeSpec spec = service.getLoginTemplate();
    return (BLoginTemplate)(spec.isNull() ? BDefaultLoginTemplate.INSTANCE : spec.getInstance());
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("web.auth");
  public static final String defaultWebRc = "module://web/com/tridium/web/rc";
}
