/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.util;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.store.Record;

/**
 * A UI theme. Themes are identified by there an id. The CSS stylesheet(s)
 * should be included in the host HTML file and given a class = the id. See
 * below for an example of the "gray" theme. Note the class name for each link
 * matches the id "gray":
 * 
 * <code><pre>
 * <link class="gray" rel="stylesheet" type="text/css" href="css/xtheme-gray.css" />
 * <link class="gray" rel="stylesheet" type="text/css" href="css/gxt-all-gray.css" />
 * </code></pre>
 * 
 * @see Theme#BLUE
 * @see Theme#GRAY
 * 
 */
public class Theme extends Record {

  /**
   * Default GXT blue theme.
   */
  public static Theme BLUE;

  /**
   * GXT gray theme.
   */
  public static Theme GRAY;

  static {
    BLUE = new Theme("blue", GXT.MESSAGES.themeSelector_blueTheme(), "ext-all.css");
    GRAY = new Theme("gray", GXT.MESSAGES.themeSelector_grayTheme(), "xtheme-gray.css");
  }

  public Theme(String id, String name, String file) {
    set("id", id);
    set("name", name);
    set("file", file);
  }

  public String getId() {
    return get("id").toString();
  }

  public String getName() {
    return get("name").toString();
  }

  public String getFile() {
    return get("file").toString();
  }

  public Map asMap() {
    Map map = new HashMap();
    map.put("id", getId());
    map.put("file", getFile());
    return map;
  }

}