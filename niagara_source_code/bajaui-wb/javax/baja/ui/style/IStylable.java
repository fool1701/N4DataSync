/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.style;

/**
 * Interface for a component that can be styled using an external theme
 * configuration file.
 *
 * @author    Logan Byam
 * @creation  12 Jun 11
 * @version   $Revision: 2$ $Date: 7/12/11 11:12:41 AM EDT$
 * @since     Niagara 3.7
 */
public interface IStylable
{
  /**
   * Returns the style ID for this component. This allows a theme configuration
   * file to refer to this component by ID, similar to a CSS 
   * <code>#myStyleId</code> selector.
   * @return the style ID for this component
   */
  String getStyleId();
  
  /**
   * Sets the style ID for this component.
   * @param styleId the style ID to set
   */
  void setStyleId(String styleId);
  
  /**
   * Returns the list of style classes (whitespace separated) that this
   * component currently has. This allows a theme configuration file to assign
   * styles to components given a certain class, similar to a CSS
   * <code>.myStyleClass</code> selector.
   * 
   * @return a whitespace-separated list of style classes assigned to this
   * component
   */
  String getStyleClasses();
  
  /**
   * Sets the list of style classes assigned to this component.
   * @param styleClasses a whitespace-separated list of style classes
   */
  void setStyleClasses(String styleClasses);
  
  /**
   * Returns a selector indicating where in a configuration file to find style
   * information for this component. This property is most analogous to an
   * HTML tag: e.g., a <code>BLabel</code> will return <code>"label"</code>,
   * a <code>BTable</code> will return <code>"table"</code>, etc.
   * 
   * <p>There is no corresponding <code>setStyleSelector</code> method - if
   * you need different styling for a certain subset of components, it is best 
   * to assign style classes to these components instead.
   * 
   * @return the style selector for this component
   */
  String getStyleSelector();
}
