/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

import com.tridium.gx.GxEnv;
import com.tridium.gx.ImagePeer;
import com.tridium.gx.util.ImageUtil;
import com.tridium.sys.schema.Fw;

/**
 * BImage is a representation of a raster image.
 *
 * @author Brian Frank
 * @version $Revision: 40$ $Date: 11/13/08 4:35:32 PM EST$
 * @creation 1 Dec 00
 * @since Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BImage
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Load an image from a icon.
   */
  public static BImage make(BIcon icon)
  {
    if (icon == null) return NULL;

    // check if we've already cached the image
    BImage image = (BImage) icon.fw(Fw.GET_IMAGE);
    if (image == null)
    {
      image = BImage.make(icon.getOrdList());
      icon.fw(Fw.SET_IMAGE, image, null, null, null);
    }
    return image;
  }

  /**
   * Load an image from a ord list.  The list is z-ordered
   * from bottom to top.
   */
  public static BImage make(BOrdList ordList)
  {
    return new BImage(ordList);
  }

  /**
   * Load an image from a single ord.
   */
  public static BImage make(BOrd ord)
  {
    return make(BOrdList.make(ord));
  }

  /**
   * Convenience for <code>make(BOrdList.make(ordList))</code>.
   */
  public static BImage make(String ordList)
  {
    if (ordList.equals("null")) return NULL;
    if (ordList.equals("new")) return new BImage(BOrdList.NULL);
    return make(BOrdList.make(ordList));
  }

  /**
   * Load an image from a memory buffer.  The buffer should
   * contain an image file in GIF, PNG, or JPEG format.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public static BImage make(byte[] buf)
  {
    return GxEnv.get().makeImage(buf);
  }

  /**
   * Make a new image with the specified dimensions.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public static BImage make(double width, double height)
  {
    return GxEnv.get().makeImage(width, height);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BImage(BOrdList ordList)
  {
    this.ordList = ordList;
    this.absOrdList = ordList;
    this.loadLocal = ordList.toString().startsWith("module:");
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the list of ords which reference this image
   * files.  The list is z-ordered from bottom to top.
   * This ord list is immutable regardless of any calls
   * to <code>setBaseOrd()</code>.
   */
  public BOrdList getOrdList()
  {
    return ordList;
  }

  /**
   * Get the absolute ord list taking into account the
   * last call to <code>setBaseOrd()</code>.
   */
  public BOrdList getAbsoluteOrdList()
  {
    return absOrdList;
  }

  /**
   * Set the base ord used to resolve the ord list into
   * image files.  This call results in a new absOrdList
   * but doesn't alter the orginal ordList.
   */
  @SuppressWarnings("deprecation")
  public void setBaseOrd(BOrd baseOrd)
  {
    // null bases or images in a module: always 
    // load relative to local VM                           
    if (baseOrd == null || loadLocal)
    {
      absOrdList = ordList;
    }
    else
    {
      // TODO NCCB-46414: Refactor to remove this fixup.
      BOrd[] ords = ImageUtil.fixupImageOrds(baseOrd, ordList, absOrdList);
      for (int i = 0; i < ords.length; ++i)
        if (!ords[i].isNull())
          ords[i] = BOrd.make(baseOrd, ords[i]).normalize();
      absOrdList = BOrdList.make(ords);
    }
    awtSupport = null;
    peer = null;
  }

  /**
   * Return true if the image is fully loaded into memory.  If
   * the image is still being loaded asynchronously on a background
   * thread then return false.  Use the <code>sync()</code> method
   * if you wish to block until the image is fully loaded.
   *
   * @throws IllegalStateException if called from a compact3 VM for svg
   */
  public boolean isLoaded()
  {
    return peer().isLoaded();
  }

  /**
   * Return true if the image is fully loaded into memory.  If
   * thread then return false.  Use the <code>sync()</code> method
   * the image is still being loaded asynchronously on a background
   * if you wish to block until the image is fully loaded.
   */
  public boolean isDimensionsLoaded()
  {
    return peer().isDimensionsLoaded();
  }

  /**
   * This method blocks the calling thread until the image
   * dimensions are loaded into memory.  If the image dimensions or the image itself is already
   * loaded into memory then this method has no effect.
   */
  public BImage syncDimensions()
  {
    peer().syncDimensions();
    return this;
  }

  /**
   * This method blocks the calling thread until the image
   * is fully loaded into memory.  If the image is already
   * loaded into memory then this method has no effect.
   */
  public BImage sync()
  {
    peer().sync();
    return this;
  }

  /**
   * If the widget painting the image supports animation,
   * then this method should be called repeatly at the
   * standard framerate of 10/frames per second.  If the
   * image is animated and a repaint is required then return
   * true.  If this image doesn't support animation, or the
   * frame's images hasn't changed then return false.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public boolean animate()
  {
    return peer().animate();
  }

  /**
   * Get a graphics context used to paint to the image.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public Graphics getGraphics()
  {
    return peer().getGraphics();
  }

  /**
   * Return the width of the image in pixels.  If
   * the image cannot be loaded, then return 0.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public double getWidth()
  {
    return peer().getWidth();
  }

  /**
   * Return the height of the image in pixels.  If
   * the image cannot be loaded, then return 0.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public double getHeight()
  {
    return peer().getHeight();
  }

  /**
   * Return the image data as an array of pixels. The array
   * is ordered [(0,0),(1,0)...(w,0),(0,1),(1,1)...], where
   * (x,y) represents a pixel location on the image. Each
   * array element represents one pixel in ARGB. A new array
   * is allocated and returned on each call to this method,
   * so it is safe to modify this array without effecting the
   * original image.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public int[] getPixels()
  {
    return peer().getPixels();
  }

  /**
   * Set the image data for this image. See getPixels()
   * for array format information.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public void setPixels(int[] pixels)
  {
    peer().setPixels(pixels);
  }

  /**
   * Dispose of this image and any system
   * resources it might be holding.
   */
  public void dispose()
  {
    if (awtSupport instanceof ImagePeer) ((ImagePeer) awtSupport).dispose();
    if (peer != null) peer.dispose();
    awtSupport = null;
    peer = null;
  }

  /**
   * Get this image with a disabled effect.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public BImage getDisabledImage()
  {
    if (disabled == null)
      disabled = GxEnv.get().makeDisabled(this);
    return disabled;
  }

  /**
   * Get this image with a highlighted effect.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  public BImage getHighlightedImage()
  {
    if (highlighted == null)
      highlighted = GxEnv.get().makeHighlighted(this);
    return highlighted;
  }

  /**
   * Return a new image which is the result of applying the
   * specified transform.
   */
  public BImage transform(BTransform transform)
  {
    // get my size
    double w = getWidth();
    double h = getHeight();

    // transform the 4 corners
    Point c0 = transform.transform(new Point(0, 0), null);
    Point c1 = transform.transform(new Point(w, 0), null);
    Point c2 = transform.transform(new Point(w, h), null);
    Point c3 = transform.transform(new Point(0, h), null);

    // compute new size
    double minw = Math.min(c0.x, Math.min(c1.x, Math.min(c2.x, c3.x)));
    double minh = Math.min(c0.y, Math.min(c1.y, Math.min(c2.y, c3.y)));
    double maxw = Math.max(c0.x, Math.max(c1.x, Math.max(c2.x, c3.x)));
    double maxh = Math.max(c0.y, Math.max(c1.y, Math.max(c2.y, c3.y)));

    // compute translate and size
    double tx = -minw;
    double ty = -minh;
    double tw = maxw - minw;
    if (tw < 1) tw = 1;
    double th = maxh - minh;
    if (th < 1) th = 1;

    // create buffered image
    BImage buf = make(tw, th);

    // paint buffered image
    Graphics g = buf.getGraphics();
    g.translate(tx, ty);
    g.transform(transform);
    g.drawImage(this, 0, 0);

    // return result
    return buf;
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Return if this is the null image.
   */
  public boolean isNull()
  {
    return this == NULL;
  }

  /**
   * Starting in Niagara 3.4, the hash code returned
   * is based on the the list of ords which reference
   * this image file.
   */
  public int hashCode()
  {
    return ordList.hashCode();
  }

  /**
   * Equality.
   */
  public boolean equals(Object obj)
  {
    if (this == obj) return true;
    if (obj instanceof BImage)
    {
      BImage x = (BImage) obj;
      if (ordList.isNull() || x.ordList.isNull()) return false;
      return ordList.equals(x.ordList);
    }
    return false;
  }

  /**
   * Serialized using writeUTF() of string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }

  /**
   * Unserialized using readUTF() of string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  /**
   * Serialize using OrdList syntax.
   */
  public String encodeToString()
  {
    if (isNull()) return "null";
    return ordList.encodeToString();
  }

  /**
   * Deserialize using OrdList syntax.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The null image.
   */
  public static final BImage NULL = make(BOrdList.NULL);

  /**
   * The default image is NULL.
   */
  public static final BImage DEFAULT = NULL;

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BImage(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BImage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.GET_AWT:
        return awtSupport;
      case Fw.SET_AWT:
        awtSupport = a;
        return null;
      case Fw.SET_BASE_ORD:
        setBaseOrd((BOrd) a);
        return null;
      case Fw.GET_PEER:
        return peer();
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Get the ImagePeer from the default GxEnv.
   *
   * @throws IllegalStateException if called from a compact3 VM
   */
  private ImagePeer peer()
  {
    if (peer == null)
      peer = GxEnv.get().makeImagePeer(this);
    return peer;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BOrdList ordList;
  private boolean loadLocal;
  private BOrdList absOrdList;
  private ImagePeer peer;
  private Object awtSupport;
  private BImage disabled;
  private BImage highlighted;

}
