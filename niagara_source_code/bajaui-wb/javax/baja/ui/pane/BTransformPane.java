/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.pane;

import javax.baja.gx.BTransform;
import javax.baja.gx.BTransform.Transform;
import javax.baja.gx.Graphics;
import javax.baja.gx.Point;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BButton;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BPicture;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BTransformMode;


/**
 * BTransformPane applies the specified transform mode to the content widget in the pane.
 * BTransformPane supports applying transformations for the following widget types: Label, Picture,
 * Button, and TransformPane.
 *
 * The content widget's complete orientation will be transformed. This means that any content widget
 * positioning or scaling such as label alignment will be based upon the orientation of the widget
 * after all transformations are applied.
 *
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
@NiagaraProperty(
  name = "transformMode",
  type = "BTransformMode",
  defaultValue = "BTransformMode.none"
)
public class BTransformPane
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BTransformPane(2690225097)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Property "transformMode"

  /**
   * Slot for the {@code transformMode} property.
   * @see #getTransformMode
   * @see #setTransformMode
   */
  public static final Property transformMode = newProperty(0, BTransformMode.none, null);

  /**
   * Get the {@code transformMode} property.
   * @see #transformMode
   */
  public BTransformMode getTransformMode() { return (BTransformMode)get(transformMode); }

  /**
   * Set the {@code transformMode} property.
   * @see #transformMode
   */
  public void setTransformMode(BTransformMode v) { set(transformMode, v, null); }

  //endregion Property "transformMode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTransformPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void paint(Graphics g)
  {
    g.push();
    try
    {
      BTransform transform = getPaintTransform();
      g.transform(transform);
      paintChild(g, getContent());
    }
    finally
    {
      g.pop();
    }
  }

  @Override
  public void doLayout(BWidget children[])
  {
    BWidget content = getContent();
    if (isSideways())
    {
      content.setBounds(0, 0, getHeight(), getWidth());
    }
    else
    {
      content.setBounds(0, 0, getWidth(), getHeight());
    }
  }

  @Override
  public void computePreferredSize()
  {
    BWidget c = getContent();
    c.computePreferredSize();
    if (isSideways())
    {
      setPreferredSize(c.getPreferredHeight(), c.getPreferredWidth());
    }
    else
    {
      setPreferredSize(c.getPreferredWidth(), c.getPreferredHeight());
    }
  }

  @Override
  public final boolean isChildLegal(BComponent child)
  {
    return child instanceof BPicture || child instanceof BLabel ||
      child instanceof BButton || child instanceof BTransformPane || child instanceof BBinding;
  }

  @Override
  public void changed(Property prop, Context context)
  {
    if(prop.equals(transformMode))
    {
      needsLayout();
    }
  }

  @Override
  public BWidget childAt(Point point)
  {
    Point result = new Point();
    getPaintTransform().getInverse().transform(point, result);
    BWidget widget = super.childAt(result);
    return widget;
  }


  private BTransform getPaintTransform()
  {
    BTransformMode transformMode = getTransformMode();
    if (transformMode.equals(BTransformMode.none))
    {
      return BTransform.DEFAULT;
    }
    else if (transformMode.equals(BTransformMode.flip))
    {
      return BTransform.make(new Transform[] {
        new BTransform.Scale(1, -1),
        new BTransform.Translate(0, -getHeight())
      });
    }
    else if (transformMode.equals(BTransformMode.mirror)) {
      return BTransform.make(new Transform[] {
        new BTransform.Scale(-1, 1),
        new BTransform.Translate(-getWidth(), 0)
      });
    }
    else
    {
      double degrees;
      double radians;

      if (transformMode.equals(BTransformMode.rotate90))
      {
        degrees = 90;
      }
      else if (transformMode.equals(BTransformMode.rotate180))
      {
        degrees = 180;
      }
      else
      {
        degrees = 270;
      }

      radians = degrees * Math.PI / 180;

      // we will rotate around the center of the widget
      Point center = new Point(
        (getWidth() / 2) * Math.cos(radians) - (getHeight() / 2) * Math.sin(radians),
        (getHeight() / 2) * Math.cos(radians) + (getWidth() / 2) * Math.sin(radians)
      );

      BWidget c = getContent();
      return BTransform.make(new Transform[]{
        new BTransform.Translate((getWidth() / 2) - center.x, (getHeight() / 2) - center.y),
        new BTransform.Rotate(degrees),
        new BTransform.Translate((getWidth() - c.getWidth()) / 2, (getHeight() - c.getHeight()) / 2)
      });

    }
  }

  private boolean isSideways()
  {
    BTransformMode mode = getTransformMode();
    return mode.equals(BTransformMode.rotate90) || mode.equals(BTransformMode.rotate270);
  }

}
