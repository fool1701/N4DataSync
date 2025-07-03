/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.awt.Graphics2D;

import com.tridium.gx.ImagePeer;
import com.tridium.gx.awt.AwtGraphics;
import com.tridium.gx.awt.AwtImagePeer;
import com.tridium.gx.awt.ImageManager;

import javax.baja.gx.Graphics;

/**
 * An AWT picture implementation. This class contains all of the
 * AWT dependencies a {@link javax.baja.ui.BPicture} requires
 * to render a picture. An instance of this class is loaded
 * at runtime by BPicture if the JRE supports AWT.
 *
 * @see javax.baja.ui.BPicture
 *
 * @author Gareth Johnson on 24/06/2014
 * @since Niagara 4.0
 */
@SuppressWarnings("unused")
final class AwtPictureImpl implements BPicture.IPictureImpl
{
  /**
   * This constructor is invoked via reflection.
   *
   * @param picture The associated BPicture instance for this
   *                implementation.
   */
  @SuppressWarnings("unused")
  public AwtPictureImpl(BPicture picture)
  {
    this.picture = picture;
  }

  @Override
  public void resetPeer()
  {
    picturePeer = null;
  }

  @Override
  public ImagePeer ensurePeer()
  {
    if (picturePeer == null)
    {
      BPicture.doScaleLayout(picture);

      picturePeer = new AwtImagePeer(
          ImageManager.fetchImageData(
              picture.getImage(), picture.transScale));

      picturePeer.data.repaintOnLoad = true;
    }

    return picturePeer;
  }

  @Override
  public boolean paint(Graphics g)
  {
    if (!(g instanceof AwtGraphics))
      return false;

    g.push();
    try
    {
      g.translate(picture.dx, picture.dy);
      Graphics2D g2d = ((AwtGraphics)g).getAwtGraphics();
      g2d.drawImage(((AwtImagePeer)ensurePeer()).image(), 0, 0, null);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    finally
    {
      g.pop();
    }

    return true;
  }

  private AwtImagePeer picturePeer;
  private BPicture picture;
}
