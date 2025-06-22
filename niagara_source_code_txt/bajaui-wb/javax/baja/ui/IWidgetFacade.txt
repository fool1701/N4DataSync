/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

public interface IWidgetFacade
{
  public boolean isValidFor(BWidget widget);
  
  public void initFrom(BWidget widget);
  
  public void updateFrom(BWidget widget);
}
