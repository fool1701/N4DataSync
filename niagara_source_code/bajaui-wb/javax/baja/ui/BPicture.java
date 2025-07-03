/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.logging.Logger;

import javax.baja.gx.BImage;
import javax.baja.gx.BSize;
import javax.baja.gx.BTransform;
import javax.baja.gx.Graphics;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BScaleMode;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.BWidgetEvent;

import com.tridium.gx.ImagePeer;
import com.tridium.ui.util.ScaledLayout;


/**
 * BPicture displays a BImage.  BPicture has properties which
 * control how the image will be displayed and animated.
 *
 * @author    Mike Jarmy on 20 Sep 11
 * @version   $Revision: 74$ $Date: 6/30/11 11:38:13 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Image to display on the label.
 */
@NiagaraProperty(
  name = "image",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Specifies if/how to scale the image to fit the pictures's bounds.
 */
@NiagaraProperty(
  name = "scale",
  type = "BScaleMode",
  defaultValue = "BScaleMode.none"
)
/*
 Defines how to align the image horizontally.
 */
@NiagaraProperty(
  name = "halign",
  type = "BHalign",
  defaultValue = "BHalign.center"
)
/*
 Defines how to align the image vertically.
 */
@NiagaraProperty(
  name = "valign",
  type = "BValign",
  defaultValue = "BValign.center"
)
/*
 Whether animation is currently enabled.
 */
@NiagaraProperty(
  name = "animate",
  type = "boolean",
  defaultValue = "true"
)
/*
 This topic fires a BWidgetEvent whenever the widget is clicked.
 @since Niagara 4.13
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BWidgetEvent"
)
public class BPicture
  extends BWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BPicture(993598454)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "image"

  /**
   * Slot for the {@code image} property.
   * Image to display on the label.
   * @see #getImage
   * @see #setImage
   */
  public static final Property image = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code image} property.
   * Image to display on the label.
   * @see #image
   */
  public BImage getImage() { return (BImage)get(image); }

  /**
   * Set the {@code image} property.
   * Image to display on the label.
   * @see #image
   */
  public void setImage(BImage v) { set(image, v, null); }

  //endregion Property "image"

  //region Property "scale"

  /**
   * Slot for the {@code scale} property.
   * Specifies if/how to scale the image to fit the pictures's bounds.
   * @see #getScale
   * @see #setScale
   */
  public static final Property scale = newProperty(0, BScaleMode.none, null);

  /**
   * Get the {@code scale} property.
   * Specifies if/how to scale the image to fit the pictures's bounds.
   * @see #scale
   */
  public BScaleMode getScale() { return (BScaleMode)get(scale); }

  /**
   * Set the {@code scale} property.
   * Specifies if/how to scale the image to fit the pictures's bounds.
   * @see #scale
   */
  public void setScale(BScaleMode v) { set(scale, v, null); }

  //endregion Property "scale"

  //region Property "halign"

  /**
   * Slot for the {@code halign} property.
   * Defines how to align the image horizontally.
   * @see #getHalign
   * @see #setHalign
   */
  public static final Property halign = newProperty(0, BHalign.center, null);

  /**
   * Get the {@code halign} property.
   * Defines how to align the image horizontally.
   * @see #halign
   */
  public BHalign getHalign() { return (BHalign)get(halign); }

  /**
   * Set the {@code halign} property.
   * Defines how to align the image horizontally.
   * @see #halign
   */
  public void setHalign(BHalign v) { set(halign, v, null); }

  //endregion Property "halign"

  //region Property "valign"

  /**
   * Slot for the {@code valign} property.
   * Defines how to align the image vertically.
   * @see #getValign
   * @see #setValign
   */
  public static final Property valign = newProperty(0, BValign.center, null);

  /**
   * Get the {@code valign} property.
   * Defines how to align the image vertically.
   * @see #valign
   */
  public BValign getValign() { return (BValign)get(valign); }

  /**
   * Set the {@code valign} property.
   * Defines how to align the image vertically.
   * @see #valign
   */
  public void setValign(BValign v) { set(valign, v, null); }

  //endregion Property "valign"

  //region Property "animate"

  /**
   * Slot for the {@code animate} property.
   * Whether animation is currently enabled.
   * @see #getAnimate
   * @see #setAnimate
   */
  public static final Property animate = newProperty(0, true, null);

  /**
   * Get the {@code animate} property.
   * Whether animation is currently enabled.
   * @see #animate
   */
  public boolean getAnimate() { return getBoolean(animate); }

  /**
   * Set the {@code animate} property.
   * Whether animation is currently enabled.
   * @see #animate
   */
  public void setAnimate(boolean v) { setBoolean(animate, v, null); }

  //endregion Property "animate"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * This topic fires a BWidgetEvent whenever the widget is clicked.
   * @since Niagara 4.13
   * @see #actionPerformed
   */
  public void fireActionPerformed(BWidgetEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPicture.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public BPicture()
  {
    // Dynamically create an instance of the underlying picture implementation.
    Optional<IPictureImpl> im;
    try
    {
      Class<?> awtComponentClass = Class.forName("java.awt.Component");
      Constructor<?> ctor = Class.forName("javax.baja.ui.AwtPictureImpl").getConstructor(BPicture.class);
      im = Optional.of((IPictureImpl)ctor.newInstance(this));
    }
    catch(Throwable e)
    {
      im = Optional.empty();
    }
    this.impl = im;
  }

  @Override
  public void changed(Property prop, Context context)
  {
    this.impl.ifPresent(IPictureImpl::resetPeer);
    relayout();

    super.changed(prop, context);

// TODO -- from BLabel
//        else if (prop.equals(styleClasses))
//        {
//            setImage(Theme.label().getIcon(this));
//        }
  }

  @Override
  public void paint(javax.baja.gx.Graphics g)
  {
    wipePreviousPeer();
    
    BImage image = getImage();

    if (image.isNull() || !impl.isPresent() || !impl.get().ensurePeer().isDimensionsLoaded() || !isImageReadyToScale())
    {      
      return;
    }

    if (!impl.get().paint(g))
    {
      LOG.warning("cannot render " + image +
        " to graphics context " + g.getClass().getName());
    }
  }

  @Override
  public void computePreferredSize()
  {
    // Note that we don't use the custom peer here.
    // That is because the preferred size is just the
    // default image size.
    BImage image = getImage();

    if (image.isNull() || !image.isDimensionsLoaded())
    {
      setPreferredSize(0, 0);
    }
    else
    {
      setPreferredSize(
        image.getWidth(),
        image.getHeight());
    }
  }

  /**
   * Layout children.
   */
  @Override
  public void doLayout(BWidget[] kids)
  {
    // 'layout' happens in ensurePeer().
    if (!wipePreviousPeer()) {
      //we didn't wipe out our peer, so createPeer won't get run. re-align
      //the image anyway since our dimensions might be changing.
      doScaleLayout(this);
    }

    impl.ifPresent(IPictureImpl::ensurePeer);
  }

  @Override
  public void animate()
  {
    if (!getAnimate()) return;

    BImage image = getImage();
    if (image.isNull() || !impl.isPresent() || !impl.get().ensurePeer().isLoaded())
      return;

    if (impl.get().ensurePeer().animate())
    {
      this.impl.get().resetPeer();
      repaint();
    }
  }

  @Override
  public void mousePressed(BMouseEvent event)
  {
    fireActionPerformed(event);
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * Determine if our current AwtImagePeer needs to be wiped and recreated.
   * It will be wiped if our image ORD changes or if the image needs to be
   * rescaled.
   * @return true if the peer got wiped.
   */
  private boolean wipePreviousPeer()
  {
    BImage image = getImage();
    boolean wipePeer = false;
    
    if (lastImageOrdList != null && 
        !lastImageOrdList.equals(image.getOrdList()))
    {
      /*
       * Image is animated by a ValueBinding or similar - wipe peer and
       * rebuild
       */
      wipePeer = true;
    }
    
    lastImageOrdList = image.getOrdList();
    
    if (!imageWasScaled)
    {
      if (isImageReadyToScale())
      {
        /*
         * Image has freshly completed loading and widget has a proper height and width, which (especially in the
         * case of SVG) means we now know width, height, and scale - so we
         * must wipe out our image data and rebuild. 
         */
        wipePeer = true;
      }
    }
    
    if (wipePeer) impl.ifPresent(IPictureImpl::resetPeer);
    
    return wipePeer;
  }
  
  
  /**
   * In order for an image to be "ready to scale", the image must be loaded into memory and current BPicture's width and height 
   * must be properly initialized (width and height must be a number greater than zero).
   */
  public boolean isImageReadyToScale()
  {
    BImage image = getImage();
    return !image.isNull() && image.isLoaded() && getWidth() > 0 && getHeight() > 0;
  }
  
  static void doScaleLayout(final BPicture pic) {
    BImage image = pic.getImage();

    //only scale when the image is ready to scale
    pic.imageWasScaled = pic.isImageReadyToScale();
    if(!pic.imageWasScaled)
    {
      pic.dx = 0;
      pic.dy = 0;
      pic.transScale = null;
    }
    else
    {
      BSize inner = BSize.make(image.getWidth(), image.getHeight());
      BSize outer = BSize.make(pic.getWidth(), pic.getHeight());
      ScaledLayout scaledLayout = ScaledLayout.scale(inner, outer,
        pic.getHalign(), pic.getValign(), pic.getScale());
      pic.dx = scaledLayout.getOffsetX();
      pic.dy = scaledLayout.getOffsetY();
      pic.transScale = scaledLayout.getScale();
    }
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/image.png");

////////////////////////////////////////////////////////////////
// Picture Implementation
////////////////////////////////////////////////////////////////

  /**
   * An interface for a Picture implementation. This is required because
   * BPicture has direct AWT dependencies. Because an instance of a BPicture
   * could be used on an Hx Px page on a JACE (running Java 8 Compact 3),
   * the AWT dependencies need to be dynamically loaded at runtime.
   * <p>
   * Any implementations require a constructor that takes a BPicture instance
   * as an argument.
   */
  public interface IPictureImpl
  {
    /**
     * Reset the underlying peer reference so it can be recreated.
     */
    void resetPeer();

    /**
     * Return an image peer. If the peer isn't created, this will
     * lazily create it.
     *
     * @return The underlying ImagePeer instance.
     */
    ImagePeer ensurePeer();

    /**
     * Paint the picture and return true if it successfully rendered the image.
     * If false is returned, the image was unable to paint in its current state.
     *
     * @param g The Graphics Context.
     * @return Returns true if painted successfully.
     */
    boolean paint(Graphics g);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final Logger LOG = Logger.getLogger("bajaui");

  // these are set in ensurePeer()
  BTransform.Scale transScale;
  protected double dx,dy;

  private boolean imageWasScaled = false;
  private BOrdList lastImageOrdList = null;

  protected final Optional<IPictureImpl> impl;
}
