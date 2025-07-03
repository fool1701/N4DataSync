/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.px;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.BAbstractPxView;
import javax.baja.agent.BDynamicPxView;
import javax.baja.agent.BPxView;
import javax.baja.file.BIFile;
import javax.baja.file.BajaFileUtil;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.ViewQuery;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavListener;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BDialog;
import javax.baja.ui.BMenu;
import javax.baja.ui.BProgressDialog;
import javax.baja.ui.BSeparator;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.naming.BRootContainer;
import javax.baja.ui.pane.BTextEditorPane;
import javax.baja.ui.px.BPxInclude;
import javax.baja.ui.px.BPxMedia;
import javax.baja.ui.px.PxDecoder;
import javax.baja.ui.px.PxLayer;
import javax.baja.ui.px.PxProperty;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.text.parsers.XmlParser;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.view.BWbView;

import com.tridium.agent.BILoadablePxView;
import com.tridium.fox.session.FoxSession;
import com.tridium.fox.sys.BFoxSession;
import com.tridium.fox.sys.BSysChannel.NiagaraRpcServerException;
import com.tridium.fox.util.FoxRpcUtil;
import com.tridium.security.UrlWhitelist;
import com.tridium.sys.schema.Fw;
import com.tridium.ui.Binder;
import com.tridium.workbench.px.BWbPxMedia;
import com.tridium.workbench.shell.BErrorPanel;
import com.tridium.workbench.shell.BNiagaraWbShell;
import com.tridium.workbench.util.WbUtil;
import com.tridium.workbench.web.browser.BWebBrowser;
import com.tridium.workbench.web.browser.BWebWidget;

/**
 * BWbPxView is the workbench implementation of PxView
 *
 * @author    Brian Frank
 * @version $Revision: 9$ $Date: 9/10/09 2:03:17 PM EDT$
 * @creation  27 Mar 03
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "file:PxFile", "bajaui:PxInclude" },
    requiredPermissions = "r"
  )
)
public class BWbPxView
  extends BWbView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.px.BWbPxView(3844822628)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbPxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/





////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BWbPxView(BAbstractPxView agent)
  {
    this.agent = agent;
  }

  public BWbPxView()
  {
    this(null);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * @return false
   */
  public boolean isEditor()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Plugin
////////////////////////////////////////////////////////////////

  @Override
  public BMenu[] getViewMenus()
  {
    BMenu menu = new BMenu(lex.getText("menu.px.label"));
    menu.add(null, cmdToggleMode);
    menu.add(null, cmdTogglePreviewMode);
    menu.add(null, new BSeparator());
    menu.add(null, cmdViewSource);
    menu.add(null, cmdGotoSource);
    return new BMenu[] { menu };
  }

  @Override
  public BToolBar getViewToolBar()
  {
    BToolBar bar = new BToolBar();
    bar.add(null, cmdToggleMode);
    bar.add(null, cmdTogglePreviewMode);
    return bar;
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////

  @Override
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {
    boolean isPreviewMediaMode = isPreviewMediaMode();
    cmdTogglePreviewMode.setSelected(isPreviewMediaMode);
    if (isPreviewMediaMode)
    {
      loadPreviewMediaMode(value, cx);
    }
    else
    {
      setContent(new BRootContainer(loadPx(value, cx)));
    }
  }

  /**
   * Validate User is present and has an HxProfile
   * @throws Exception if the User's Web Profile cannot be found or is not an HxProfile.
   */
  private void validateProfile(Context cx)
    throws Exception
  {
    Type profileType = WbUtil.getWebProfileFromContext(this, cx);
    Type hxProfile = BTypeSpec.make("hx:HxProfile").getResolvedType();

    if(profileType == null)
      throw new LocalizableException(Lexicon.make("workbench", cx), "previewModeRequiresHxProfile");
    else if(!profileType.is(hxProfile))
      throw new LocalizableException("workbench", "previewModeRequiresHxProfileCurrentKnown", new Object[]{profileType.toString()});

  }

  /**
   * Load the current WbPxView within a WebBrowser to provide a preview of the Java Page within a Web Browser
   */
  private void loadPreviewMediaMode(BObject value, Context cx)
    throws Exception
  {
    validateProfile(cx);

    BWebBrowser browser = new BWebBrowser();
    setContent(browser);

    BISession session = WbUtil.findSession(this);

    String ord = "/ord/" + getWbShell().getActiveOrdTarget().getOrd().relativizeToSession().toString();

    //NCCB-21339: ViewQuery normalization of duplicate params causes undesired syntax
    if(ord.endsWith(";"))
      ord = ord.substring(0, ord.length()-1);

    if(value instanceof BIFile)
      ord+= "|view:hx:HxPxView?fullScreen=true;previewMode=true";
    else
      ord+= "|view:?fullScreen=true;previewMode=true";

    URI uri = BWebWidget.makeUri(this, browser, session, ord, null);

    // Add this uri to station whitelist
    String uriToWhitelist = "regex:^" + uri.getScheme() + "://" + uri.getHost();
    browser.setWhitelist(UrlWhitelist.getDefaultInstance(uriToWhitelist));
    if(session instanceof BFoxSession)
    {
      BFoxSession foxSession = (BFoxSession) session;
      FoxSession sess = foxSession.getConnection().session();
      if (sess != null)
      {
        navListener = new BWebWidget.FoxSessionRemoveListener(foxSession, browser, uri);
        BNavRoot.INSTANCE.addNavListener(navListener);
      }
    }
    browser.load(BOrd.make(uri.toASCIIString()), cx);
  }

  /**
   * Load the Presentation XML from the specified value.  On
   * failure the widget field is set to null and we return an error
   * pane to display the failure.
   */
  public BWidget loadPx(BObject value, Context cx)
  {
    String errMsg = "";
    PxDecoder decoder;
    TypeInfo pxViewMedia = null;

    try
    {
      // needed to support PxInclude inside palettes
      if (value instanceof BPxInclude)
      {
        value = ((BPxInclude)value).getOrd().resolve(value).get();
      }

      // find out what kind of PxView we're dealing with
      if (agent != null)
      {
        if (agent instanceof BPxView)
        {
          // 'normal' PxView
          errMsg = errNull;

          // look up the ord
          BPxView view = (BPxView)agent;
          pxOrd = view.getPxFile();
          if (pxOrd.isNull()) throw new Exception();

          // save the media
          pxViewMedia = view.getMedia().getTypeInfo();

          // read the file
          errMsg  = errUnresolved;
          pxFile  = (BIFile)pxOrd.get(getCurrentValue());
          decoder = loadPxFile(cx);
        }
        else if (agent instanceof BDynamicPxView)
        {
          // dynamic PxView
          dynamic = true;
          BComponent target = (BComponent)value;
          BDynamicPxView view = (BDynamicPxView)agent;

          // call 'view.generateXml()' via RPC
          Property p = view.getPropertyInParent();

          // Use try/catch when calling isPxViewModified() because implementers may not gracefully handle
          // proxy side calls
          boolean showLoadingDialog = false;
          try
          {
            if (view instanceof BILoadablePxView)
            {
              BILoadablePxView loadablePx = (BILoadablePxView)view;
              showLoadingDialog = loadablePx.isPxViewModified(BAbsTime.END_OF_TIME) ||
                                  loadablePx.isPxViewLoading();
            }
          }
          catch (Throwable ignore) {}

          try
          {
            if (showLoadingDialog)
            {
              Optional<BString> xml = BProgressDialog.openIndeterminate(this, loadingTitle, dlg -> {
                try
                {
                  return FoxRpcUtil.<BString>doRpcProperty(target, p, "generateXml")
                    .orElseThrow(() -> new BajaRuntimeException("RPC call to generateXml failed"));
                }
                catch (RuntimeException err)
                {
                  throw err;
                }
                catch (Throwable err)
                {
                  throw new BajaRuntimeException("Error during RPC call to generateXml", err);
                }
              });

              pxSource = xml.orElse(BString.DEFAULT).toString();
            }
            else
            {
              BString xml = FoxRpcUtil.<BString>doRpcProperty(target, p, "generateXml")
                .orElseThrow(() -> new BajaRuntimeException("RPC call to generateXml failed"));

              pxSource = xml.toString();
            }
          }
          catch (RuntimeException err)
          {
            // Try to find an RPC error and throw it.
            Throwable throwable = err;
            while (throwable != null)
            {
              if (throwable instanceof NiagaraRpcServerException)
              {
                errMsg = Objects.toString(throwable.getMessage(), "");
                throw (NiagaraRpcServerException)throwable;
              }

              throwable = throwable.getCause();
            }

            throw err;
          }

          // decode the xml
          InputStream in = new ByteArrayInputStream(pxSource.getBytes());
          decoder = new PxDecoder(target.getNavOrd(), in, cx);
        }
        else
        {
          // unknown
          throw new IllegalStateException(
            "Don't know how to load from agent " + agent.getType());
        }
      }
      else if (value instanceof BIFile)
      {
        // file based
        fileBased = true;
        pxFile  = (BIFile)value;
        pxOrd   = pxFile.getNavOrd();
        decoder = loadPxFile(cx);
      }
      else
      {
        // unknown
        throw new IllegalStateException(
          "Don't know how to load from " + value.getType());
      }

      // decode PxFile
      if (widget == null)
      {
        errMsg = errXml;
        widget = decoder.decodeDocument();
        pxProperties = decoder.getPxProperties();
        pxLayers = decoder.getPxLayers();

        // apply any PxProperties
        for (PxProperty pxProperty : pxProperties)
        {
          pxProperty.apply(widget);
        }

        //////////////////////////////////////////
        // set the PxMedia type
        // looks for pxViewMedia file type first
        // if not found, it then will attempt to get the media type from the px file
        // if neither are found, it will fallback to the 'WbPxMedia' media type.

        if(pxViewMedia != null)
        {
          media = (BPxMedia)pxViewMedia.getInstance();
        }
        else
        {
          media = decoder.getMedia() != null
            ? (BPxMedia) decoder.getMedia().getInstance()
            : BWbPxMedia.INSTANCE;
        }

        //////////////////////////////////////////
        // cache

        if (useCache)
        {
          cache.put(pxOrd, new CacheItem(
            widget, media, pxProperties, pxLayers));
        }
      }

      return widget;
    }
    catch (Exception e)
    {
      widget = null;

      BOrd ord = pxOrd;

      // If we're dealing with a dynamic px view, use the ORD of the px view instead of the
      // px file.
      if (ord.isNull() && value instanceof BComponent && agent instanceof BDynamicPxView)
      {
        BOrd navOrd = ((BComponent)value).getNavOrd();
        String name = agent.getName();

        if (navOrd != null && !navOrd.isNull() && name != null)
        {
          ord = BOrd.make(navOrd, "view:" + name);
        }
      }

      return new BErrorPanel(getWbShell(), errTitle, errMsg, ord, e);
    }
  }

////////////////////////////////////////////////////////////////
// Saving
////////////////////////////////////////////////////////////////

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    // save all my sub-editors
    Binder binder = (Binder)fw(Fw.GET_BINDER);
    BBinding[] bindings = binder.getAllBindings();
    for(int i=0; i<bindings.length; ++i)
      bindings[i].save(cx);
    return value;
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  public class ViewSource extends Command
  {
    public ViewSource(BWbPxView owner)
    {
      super(owner, lex, "commands.px.viewSource");
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      if (pxSource == null) pxSource = BajaFileUtil.readString(pxFile);

      BTextEditor editor = new BTextEditor();
      editor.setEditable(false);
      editor.setParser(new XmlParser());
      editor.setText(pxSource);

      BTextEditorPane pane = new BTextEditorPane(editor, 40, 100);
      BDialog.open(getOwner(), getLabel(), pane, BDialog.OK);
      return null;
    }
  }

  public class GotoSource extends Command
  {
    public GotoSource(BWbPxView owner)
    {
      super(owner, lex, "commands.px.gotoSource");
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      BWbShell shell = (BWbShell)this.getShell();
      shell.hyperlink(BOrd.make(pxOrd.toString() + "|view:workbench:TextFileEditor"));
      return null;
    }
  }

  public class ToggleMode extends ToggleCommand
  {
    public ToggleMode(BWbPxView owner)
    {
      super(owner, lex, "commands.px.editMode");
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      BWbPxView owner = (BWbPxView)getOwner();
      BNiagaraWbShell shell = (BNiagaraWbShell)this.getShell();

      if (owner == null || shell == null)
        return null;

      // NOTE: When the view is a dynamic px view:
      // shell.tab().getViewAgent().getAgentId() somehow
      // gets set to null during shell.getSaveCommand().invoke();
      // (It is a total mystery as to how this happens.)
      // So we call it before the save.
      String from = shell.tab().getViewAgent().getAgentId();

      // do auto-save if an editor
      if (owner.isEditor() && shell.getSaveCommand().isEnabled())
        shell.getSaveCommand().invoke();

      // figure out what new viewId should be
      String to;
      if (from.equals("workbench:WbPxView"))
        to = "pxEditor:PxEditor";
      else if (from.equals("pxEditor:PxEditor"))
        to = "workbench:WbPxView";
      else if (from.indexOf('/') > 0)
        to = from.substring(0, from.indexOf('/'));
      else
        to = from + "/editor";

      // hyperlink
      BOrd ord = shell.getActiveOrd();
      shell.hyperlink(BOrd.make(ord, "view:" + to));
      return null;
    }
  }

  /**
   * Command to Toggle PreviewMedia on and off
   */
  public class TogglePreviewMedia extends ToggleCommand
  {
    public TogglePreviewMedia(BWbPxView owner)
    {
      super(owner, lex, "commands.px.previewMedia");
    }

    @Override
    public CommandArtifact doInvoke()
      throws Exception
    {
      BWbPxView owner = (BWbPxView) getOwner();
      BNiagaraWbShell shell = (BNiagaraWbShell) this.getShell();

      if (owner == null || shell == null)
        return null;

      boolean previewMediaMode = isPreviewMediaMode();

      // hyperlink
      BOrd ord = shell.getActiveOrd();
      shell.hyperlink(BOrd.make(ord, "view:?previewMedia=" + (!previewMediaMode)));
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Caching
////////////////////////////////////////////////////////////////

  /**
   * Make sure pxOrd and pxFile are set before calling
   * this method.
   */
  PxDecoder loadPxFile(Context cx)
    throws Exception
  {
    CacheItem item = cache.get(pxOrd);
    if (item == null)
    {
      if (log.isLoggable(Level.FINE)) log.fine("not cached: " + pxOrd);
      return new PxDecoder(pxFile, cx);
    }
    else
    {
      if (log.isLoggable(Level.FINE)) log.fine("cached: " + pxOrd);
      this.widget = (BWidget)item.widget.newCopy(true);
      this.media = item.media;

      // properties
      this.pxProperties = new PxProperty[item.props.length];
      for (int i=0; i<pxProperties.length; i++)
      {
        PxProperty p = item.props[i];
        this.pxProperties[i] = new PxProperty(
          p.getName(), p.getTypeSpec(), p.getValue(), p.getTargets());
      }

      // layers
      this.pxLayers = new PxLayer[item.layers.length];
      for (int i=0; i<pxLayers.length; i++)
      {
        PxLayer p = item.layers[i];
        this.pxLayers[i] = new PxLayer(p.getName());
      }

      setBaseOrd(item.widget, widget, cx);
      return null;
    }
  }

  void setBaseOrd(BWidget master, BWidget copy, Context cx)
  {
    if (master instanceof BPxInclude)
    {
      BOrd ord = ((BPxInclude)master).getBaseOrd();
      BPxInclude copyInclude = (BPxInclude)copy;
      copyInclude.fw(Fw.SET_BASE_ORD, ord, cx, null, null);
      copyInclude.remove("root");
      return;
    }

    BWidget[] masterKids = master.getChildWidgets();
    BWidget[] copyKids   = copy.getChildWidgets();

    for (int i=0; i<masterKids.length; i++)
      setBaseOrd(masterKids[i], copyKids[i], cx);
  }

  static class CacheItem
  {
    public CacheItem(
      BWidget widget,
      BPxMedia media,
      PxProperty[] props,
      PxLayer[] layers)
    {
      this.widget = widget;
      this.media  = media;
      this.props  = props;
      this.layers = layers;
    }

    BWidget widget;
    BPxMedia media;
    PxProperty[] props;
    PxLayer[] layers;
  }

  static boolean useCache =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("wbPxView.useCache"));

  static HashMap<BOrd, CacheItem> cache = new HashMap<>();

///////////////////////////////////////////////////////////////
// Preview Other Media
////////////////////////////////////////////////////////////////

  /**
   * Return true if we should preview the media in a browser. This requires the Web Profile to honor the ViewQuery
   * fullScreen=true to prevent Profile Chrome from showing up.
   *
   * @return
   */
  protected boolean isPreviewMediaMode()
  {
    String previewMedia = null;
    if(getWbShell() != null)
    {
      OrdTarget target = getWbShell().getActiveOrdTarget();
      ViewQuery viewQuery = target.getViewQuery();

      if (viewQuery != null)
        previewMedia = viewQuery.getParameter("previewMedia", "false");
    }
    return "true".equals(previewMedia);
  }

////////////////////////////////////////////////////////////////
// Deactivation
////////////////////////////////////////////////////////////////

  @Override
  public void deactivated()
  {
    deactivate(getContent());
  }

  /**
   * Deactivates any WbViews on a Px page. This enables
   * extra clean up to happen when the view is deactivated by Workbench.
   *
   * @param c The component to test for deactivation.
   */
  private void deactivate(BComponent c)
  {
    //if in preview mode, make sure to remoe the navListener which is no longer needed
    if (navListener != null)
      BNavRoot.INSTANCE.removeNavListener(navListener);

    if (c == null) return;
    if (c instanceof BWbView)
    {
      ((BWbView)c).deactivated();
    }

    for (BComponent kid : c.getChildComponents())
    {
      deactivate(kid);
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public boolean isEditable()
  {
    return !(
      isDynamic() ||
      (getPxFile() == null) ||
      getPxFile().isReadonly());
  }

  public BWidget getWidget()
  {
    return widget;
  }

  public PxProperty[] getPxProperties()
  {
    return pxProperties;
  }

  public PxLayer[] getPxLayers()
  {
    return pxLayers;
  }

  public BPxMedia getMedia()
  {
    return media;
  }

  public BAbstractPxView getPxAgent()
  {
    return agent;
  }

  public void setWidget(BWidget widget)
  {
    this.widget = widget;
  }

  public void setMedia(BPxMedia media)
  {
    if(agent != null)
    {
      agent.setMedia(media.getType().getTypeSpec());
    }
    this.media = media;
  }

  public BIFile getPxFile()
  {
    return pxFile;
  }

  public boolean isFileBased()
  {
    return fileBased;
  }

  public boolean isDynamic()
  {
    return dynamic;
  }

  public Command getViewSource()
  {
    return cmdViewSource;
  }

  public Command getGotoSource()
  {
    return cmdGotoSource;
  }

  public ToggleCommand getToggleMode()
  {
    return cmdToggleMode;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static Logger log = Logger.getLogger("wbPxView");
  /*static { log.setSeverity(log.fine); }*/

  protected PxProperty[] pxProperties = null;
  protected PxLayer[] pxLayers = null;

  private final UiLexicon lex = UiLexicon.bajaui();
  private final String errTitle      = lex.getText("pxview.error.title");
  private final String errNull       = lex.getText("pxview.error.null");
  private final String errUnresolved = lex.getText("pxview.error.unresolved");
  private final String errXml        = lex.getText("pxview.error.xml");
  private final String errCast       = lex.getText("pxview.error.cast");

  private final String loadingTitle = Lexicon.make("js").getText("dialogs.loading");

  private final BAbstractPxView agent; // maybe null, passed to ctor

  // this are initialized in loadPx()
  private BPxMedia media       = BWbPxMedia.INSTANCE;
  private BWidget  widget      = null;
  private boolean  fileBased   = false;
  private boolean  dynamic     = false;
  private BIFile   pxFile      = null;
  private BOrd     pxOrd       = BOrd.NULL;
  private String   pxSource    = null;

  private final Command cmdViewSource = new ViewSource(this);
  private final Command cmdGotoSource = new GotoSource(this);
  private final ToggleCommand cmdToggleMode = new ToggleMode(this);
  private final ToggleCommand cmdTogglePreviewMode = new TogglePreviewMedia(this);
  private NavListener navListener;
}
