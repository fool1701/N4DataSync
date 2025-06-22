/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import javax.baja.category.*;
import javax.baja.file.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.session.CsrfException;
import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BSpySpace contains the set of spys in a VM.  It defines
 * the root of a spy directory tree via getSpyDir().
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 14$ $Date: 3/28/05 9:23:04 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraSingleton
public class BSpySpace
  extends BSpace 
  implements BICategorizable, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.spy.BSpySpace(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BSpySpace INSTANCE = new BSpySpace();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpySpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  protected BSpySpace()
  {
    super("spy", LexiconText.make("baja", "nav.spy"));
  }

  /**
   * Constructor.
   */
  protected BSpySpace(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

////////////////////////////////////////////////////////////////
// BSpace
////////////////////////////////////////////////////////////////  

  /**
   * Return instance of BSpy for path.
   */
  public BSpy resolveSpy(FilePath path)
    throws SyntaxException, UnresolvedException
  {
    Spy spy = Spy.ROOT;
    String name = null;
    try
    {
      for (int i = 0; i < path.depth(); ++i)
      {
        name = path.nameAt(i);
        if (spy instanceof ISpyDir)
          spy = ((ISpyDir)spy).find(name);
        else
          spy = null;
        if (spy == null) break;
      }

      // if didn't find use blank page
      if (spy == null) spy = new NotFoundSpy();
    }
    catch(CsrfException e)
    {
      String retryName = null;
      if (spy instanceof ISpyDir)
        retryName = "../"+((ISpyDir)spy).regenerateNameWithCsrfToken(name);
      spy = new NotFoundSpy("Invalid or missing token", retryName);
    }
    
    return BSpy.make(path, spy);
  }

////////////////////////////////////////////////////////////////
// ICategorizable
////////////////////////////////////////////////////////////////
  
  /**
   * SpySpaces are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return BCategoryService.getService().getCategoryMask(ordInSession);
  }  

  /**
   * SpySpaces are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return BCategoryService.getService().getAppliedCategoryMask(ordInSession);
  }  

////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////

  @Override
  public BPermissions getPermissions(Context cx)
  { 
    if (cx != null && cx.getUser() != null)
      return cx.getUser().getPermissionsFor(this);
    else
      return BPermissions.all;
  }
  
  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasAdminRead();
  }
  
  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return false;
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }
  
////////////////////////////////////////////////////////////////
// Navigation
////////////////////////////////////////////////////////////////   

  /**
   * Get the navigation ord.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }  

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("spy.png");
  
////////////////////////////////////////////////////////////////
// NotFoundSpy
////////////////////////////////////////////////////////////////  

  static class NotFoundSpy extends Spy
  {
    public NotFoundSpy()
    {
    }

    public NotFoundSpy(String error, String retryHref)
    {
      this.error = error;
      this.retryHref = retryHref;
    }

    @Override
    public String getTitle()
    {
      /*
       * Note: Certain other classes like ImageFileFactory in pxEditor
       * explicitly check for this string when querying a station to see if
       * a certain module is installed. Be mindful when changing it. -LAB
       */
      return "Spy Not Found"; 
    }
    
    @Override
    public void write(SpyWriter out)
    {
      String title = (error != null)?"Spy Not Found: "+error:"Spy Not Found";
      out.w("<h1>"+title+"</h1>");
      out.w(out.path);
      if (retryHref != null)
      {
        out.w("<p>It is possible that you were directed to this page due to a malicious hyperlink. " +
              "If you are sure you would like to proceed using a proper token, click " +
              "<a href='" + retryHref + "'>Retry</a></p>");
      }
    }

    String error;
    String retryHref;
  }  
    
  static
  {
    BLocalHost.INSTANCE.addNavChild(INSTANCE);
    BLocalHost.INSTANCE.mountSpace(INSTANCE);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final BOrd ordInSession = BOrd.make("spy:/");

}
