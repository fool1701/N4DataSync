/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.hx.px.BHxPxButton;
import com.tridium.hx.px.ux.UxLabelUtil;
import com.tridium.kitpx.BImageButton;
import com.tridium.kitpx.hx.ux.UxImageButtonUtil;
import com.tridium.web.WebUtil;

@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:ImageButton",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxPxImageButton
  extends BHxPxButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxImageButton(3522139693)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxImageButton INSTANCE = new BHxPxImageButton();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxImageButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxImageButton() {}

  @Override
  public void write(HxOp op)
    throws Exception
  {
    if (!UxLabelUtil.hasLegacyLabel(op))
    {
      UxImageButtonUtil.write(op);
      return;
    }

    super.write(op);
  }

  private static String imgSrcJs(BOrd backgroundImage, HxOp op)
    throws Exception
  {
    BImageButton button = (BImageButton)op.get();
    BOrd normalImage = getImageOrd(button.getNormal(), op);

    String textId = op.scope("text");
    String imgId = op.scope("backgroundImage");
    String iconId = op.scope("aImage");
    String js = "";

    if (!backgroundImage.isNull())
    {
      //set the background image
      //the extra quote escapes allow for the uri to contain a single quotes in the image which is valid and protects from XSS
      js += "var url = 'url(\\\"' + decodeURIComponent('" + HxUtil.encodeURLForHref((WebUtil.toUri(op, op.getRequest(), backgroundImage))) + "') + '\\\")';";
      js += "document.getElementById('" + imgId + "').style.backgroundImage= url;";
      //hide the text and icon
      js += "document.getElementById('" + textId + "').style.display = 'none';";
      js += "document.getElementById('" + iconId + "').style.display = 'none';";
    }

    else if (!normalImage.isNull())
    {
      //hide the text and display the normal image
      js += "document.getElementById('" + textId + "').style.display = 'none';";
      //the extra quote escapes allow for the uri to contain a single quote in the image which is valid and protects from XSS
      js += "var url = 'url(\\\"' + decodeURIComponent('" + HxUtil.encodeURLForHref((WebUtil.toUri(op, op.getRequest(), normalImage))) + "') + '\\\")';";
      js += "document.getElementById('" + imgId + "').style.backgroundImage= url;";
    }

    else
    {
      //display the text and hide the background image
      js += "document.getElementById('" + textId + "').style.display = 'block';";
      js += "document.getElementById('" + imgId + "').style.backgroundImage= 'none';";
    }

    return js;
  }

  @Override
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    if (!UxLabelUtil.hasLegacyLabel(op))
    {
      UxImageButtonUtil.update(width, height, getEnabled(op), isMouseEnabled(op), op);
      return;
    }

    super.update(width, height, forceUpdate, op);
    BImageButton button = (BImageButton)op.get();

    BOrd normalImage = getImageOrd(button.getNormal(), op);
    BOrd mouseOverImage = getImageOrd(button.getMouseOver(), op);
    BOrd mousePressedImage = getImageOrd(button.getPressed(), op);
    BOrd disabledImage = getImageOrd(button.getDisabled(), op);

    //Update text (don't render text if normal image is set)
    PropertiesCollection textStyle = new PropertiesCollection.Styles();
    textStyle.add("display", normalImage.isNull() ? "block" : "none");
    textStyle.write(op.scope("text"), op);

    //Update Icon (only display icon if no images have been set)
    boolean displayIcon = normalImage.isNull() && mouseOverImage.isNull() && mousePressedImage.isNull();
    PropertiesCollection iconStyle = new PropertiesCollection.Styles();
    iconStyle.add("opacity", "inherit");
    iconStyle.add("filter", "none"); //for IE
    iconStyle.add("display", displayIcon ? "block" : "none");
    iconStyle.write(op.scope("aImage"), op);

    //Update background image
    PropertiesCollection backgroundImageStyle = new PropertiesCollection.Styles();

    if (!isMouseEnabled(op))
    {
      if (disabledImage.isNull() && !normalImage.isNull())
        disabledImage = getImageOrd(button.getNormal().getDisabledImage(), op);

      if(!disabledImage.isNull())
      {
        backgroundImageStyle.setSnoopEnabled(false);
        backgroundImageStyle.appendUnsafe("backgroundImage", "url(" + HxUtil.escapeJsStringLiteral((WebUtil.toUri(op, op.getRequest(), disabledImage))) + ")");
      }
    }

    else if (!normalImage.isNull())
    {
      backgroundImageStyle.setSnoopEnabled(false);
      backgroundImageStyle.appendUnsafe("backgroundImage", "url(" + HxUtil.escapeJsStringLiteral((WebUtil.toUri(op, op.getRequest(), normalImage))) + ")");
    }

    backgroundImageStyle.add("backgroundPosition", "center center");
    backgroundImageStyle.write(op.scope("backgroundImage"), op);

    //arm events if the button is enabled
    if (isMouseEnabled(op))
    {
      PropertiesCollection domEvents = new PropertiesCollection.Events();
      domEvents.setSnoopEnabled(false);
      domEvents.add("onmouseover", imgSrcJs(mouseOverImage, op));
      domEvents.add("onmouseout", imgSrcJs(normalImage, op));
      domEvents.add("onmousedown", imgSrcJs(mousePressedImage, op));
      domEvents.add("onmouseup", imgSrcJs(!mouseOverImage.isNull() ? mouseOverImage : normalImage, op));
      domEvents.write(op.scope("border"), op);
      domEvents.write(op.scope("text"), op);
    }
  }
}
