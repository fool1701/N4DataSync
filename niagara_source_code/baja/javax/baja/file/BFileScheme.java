/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.File;

import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BNavContainer;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BISpaceNode;
import javax.baja.space.BSpace;
import javax.baja.space.BSpaceScheme;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BFileScheme manages the "file" scheme with a FilePath query.
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 7$ $Date: 12/17/09 1:13:31 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "file"
)
@NiagaraSingleton
public class BFileScheme
  extends BSpaceScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BFileScheme(1951334263)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BFileScheme INSTANCE = new BFileScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BFileScheme()
  {
    super("file");
  }

  /**
   * Private constructor.
   */
  protected BFileScheme(String schemeId)
  {
    super(schemeId);
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * Return an instance of FilePath.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new FilePath(getId(), queryBody);
  }

  /**
   * Return BFileSpace.TYPE.
   */
  @Override
  public Type getSpaceType()
  {
    return BFileSpace.TYPE;
  }

  /**
   * See documentation in class header for how resolve works.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    // map base to session
    BObject baseObj = base.get();
    BISession session = toSession(baseObj);

    // get or create space cached on authority
    BSpace space;

    // If the base is already a BIFileSpace, use this as the space.
    if (baseObj instanceof BIFileSpace)
    {
      space = (BSpace)baseObj;
    }
    else
    {
      space = (BSpace)session.getNavChild(getId());
    }
    if (space == null)
    {
      try
      {
        space = makeSpaceForSession(session);
        ((BNavContainer)session).addNavChild(space);
      }
      catch(RuntimeException e)
      {
        // it is possible (especially under local:) that the
        // space got mounted in a static initializer, but
        // not instantiated b/c it has a private constructor;
        // if this is the case, then that is ok
        space = (BSpace)session.getNavChild(getId());
        if (space == null) throw e;
      }
    }

    // let subclass do the rest
    return resolve(base, query, space);
  }

  /**
   * Map to <code>BFileSpace.resolveFile(FilePath)</code>
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query, BSpace space)
  {
    FilePath path = (FilePath)query;
    BFileSpace fs = (BFileSpace)space;
    String body = path.getBody();

    if (body.equals("") || body.equals("/"))
    {
      return new OrdTarget(base, fs);
    }

    if(!Sys.isStationStarted() && (path.isStationHomeAbsolute() || path.isProtectedStationHomeAbsolute()))
    {
      //Resolving against the base ord.
      BOrd baseOrd = base.getOrd();
      //Case where we are resolving against the base object.
      if(baseOrd.toString().equals(path.toString()))
      {
        BObject obj = base.get();
        if(obj instanceof BISpaceNode)
        {
          baseOrd = ((BISpaceNode)obj).getOrdInHost();
        }
      }
        path = localizeStationPath(baseOrd, path);
    }

    return new OrdTarget(base, (BObject)fs.resolveFile(path));
  }

  /*
   * Localize the file path based with the base of the Ord, if possible.
   */
  public static FilePath localizeStationPath(BOrd baseOrd, FilePath path)
  {
    if(baseOrd != null)
    {
      File stationsDirectory = BFileSystem.INSTANCE.pathToLocalFile(new FilePath("~stations"));
      OrdQuery[] list = baseOrd.parse();
      for(OrdQuery query : list)
      {
        //Find the FilePath in the base
        if(query instanceof FilePath)
        {
          FilePath baseFilePath = (FilePath)query;

          FilePath stationsFilePath = baseFilePath;
          baseFilePath = baseFilePath.getParent();
          while(baseFilePath != null)
          {
            if(baseFilePath.getName().equals("stations"))
            {
              File possibleStationsDir = null;
              try
              {
                possibleStationsDir = BFileSystem.INSTANCE.pathToLocalFile(baseFilePath);
              }
              catch(Exception e)
              {
                //If the directory does not exist, do nothing.
              }
              //If FilePath matches the directory of ~stations, this is the right place.
              if(stationsDirectory.equals(possibleStationsDir))
              {
                String pathBody = path.getBody();
                String newBody;
                if(path.isStationHomeAbsolute())
                {
                  //Assemble the new ord.  Trail with a forward slash if there is a path body.
                  newBody = pathBody.replaceFirst("\\^", stationsFilePath.getBody() + "/shared" + (pathBody.length() != 1 ? "/" : ""));
                }
                else if(path.isProtectedStationHomeAbsolute())
                {
                  //Assemble the new ord.  Trail with a forward slash if there is a path body.
                  newBody = pathBody.replaceFirst("\\^\\^", stationsFilePath.getBody() + (pathBody.length() != 2 ? "/" : ""));
                }
                else
                {
                  newBody = pathBody;
                }
                path = new FilePath(path.getScheme(), newBody);
                break;
              }
            }
            stationsFilePath = baseFilePath;
            baseFilePath = baseFilePath.getParent();
          }
          break;
        }
      }
    }
    return path;
  }
}
