/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client;

import java.util.Map;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.fx.FxConfig;
import com.extjs.gxt.ui.client.messages.MyMessages;
import com.extjs.gxt.ui.client.state.CookieProvider;
import com.extjs.gxt.ui.client.state.StateManager;
import com.extjs.gxt.ui.client.util.CSS;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;

/**
 * GXT core utilities and functions.
 */
public class GXT {

  /**
   * GXT messages.
   */
  public static MyMessages MESSAGES = (MyMessages) GWT.create(MyMessages.class);

  /**
   * <code>true</code> if the browser is safari.
   */
  public static boolean isSafari;

  /**
   * <code>true</code> if the browser is opera.
   */
  public static boolean isOpera;

  /**
   * <code>true</code> if the browser is ie.
   */
  public static boolean isIE;

  /**
   * <code>true</code> if the browser is ie6.
   */
  public static boolean isIE6;

  /**
   * <code>true</code> if the browser is ie7.
   */
  public static boolean isIE7;

  /**
   * <code>true</code> if the browser is gecko.
   */
  public static boolean isGecko;

  /**
   * <code>true</code> if the browser is in strict mode.
   */
  public static boolean isStrict;

  /**
   * <code>true</code> if using https.
   */
  public static boolean isSecure;

  /**
   * <code>true</code> if mac os.
   */
  public static boolean isMac;

  /**
   * <code>true</code> if linux os.
   */
  public static boolean isLinux;

  /**
   * URL to a blank file used by GXT when in secure mode for iframe src to
   * prevent the IE insecure content. Default value is 'blank.html'.
   */
  public static String SSL_SECURE_URL = GWT.getModuleBaseURL() + "blank.html";

  /**
   * URL to a 1x1 transparent gif image used by GXT to create inline icons with
   * CSS background images. Default value is 'images/default/shared/clear.gif';
   */
  public static String BLANK_IMAGE_URL = GWT.getModuleBaseURL() + "images/default/shared/clear.gif";

  private static boolean initialized;
  private static Theme defaultTheme;
  private static boolean forceTheme;

  /**
   * Returns the current theme id.
   * 
   * @return the theme id
   */
  public static String getThemeId() {
    Map map = StateManager.get().getMap("theme");
    if (map != null) {
      return map.get("id").toString();
    } else {
      return null;
    }
  }

  /**
   * Returns the browser's user agent.
   * 
   * @return the user agent
   */
  public native static String getUserAgent() /*-{
      return $wnd.navigator.userAgent.toLowerCase();
    }-*/;

  /**
   * Hides the loading panel.
   * 
   * @param id the loading panel id
   */
  public static void hideLoadingPanel(String id) {
    final Element loading = XDOM.getElementById(id);
    if (loading != null) {
      final El l = new El(loading);
      Timer t = new Timer() {
        @Override
        public void run() {
          l.fadeOut(FxConfig.NONE);
        }
      };
      t.schedule(1000);
    }
  }

  /**
   * Initializes GXT.
   */
  public static void init() {
    if (initialized) {
      return;
    }
    initialized = true;

    String ua = getUserAgent();
    isSafari = ua.indexOf("webkit") != -1;
    isOpera = ua.indexOf("opera") != -1;
    isIE = ua.indexOf("msie") != -1;
    isIE7 = ua.indexOf("msie 7") != -1;
    isIE6 = isIE && !isIE7;
    isGecko = ua.indexOf("gecko") != -1 && ua.indexOf("like gecko") == -1;
    isMac = ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") != -1;
    isLinux = ua.indexOf("linux") != -1;

    String mode = DOM.getElementProperty(XDOM.getDocument(), "compatMode");
    isStrict = mode != null ? mode.equals("CSS1Compat") : false;

    isSecure = isSecure();

    String cls = "";
    if (isIE) {
      cls = "ext-ie";
    } else if (isGecko) {
      cls = "ext-gecko";
    } else if (isOpera) {
      cls = "ext-opera";
    } else if (isSafari) {
      cls = "ext-safari";
    }

    if (isMac) {
      cls += " ext-mac";
    }

    if (isLinux) {
      cls += " ext-linux";
    }

    CookieProvider provider = new CookieProvider("/", null, null, false);
    StateManager.get().setProvider(provider);

    Map theme = StateManager.get().getMap("theme");
    if ((defaultTheme != null && forceTheme) || (theme == null && defaultTheme != null)) {
      theme = defaultTheme.asMap();
    }
    if (theme != null) {
      String themeId = theme.get("id").toString();
      String fileName = theme.get("file").toString();
      CSS.addStyleSheet(themeId, "css/" + fileName);
      cls += " x-theme-" + themeId;
      StateManager.get().set("theme", theme);
    }

    XDOM.getBody().setClassName(cls);

    initInternal();
  }

  /**
   * Sets the default theme which will be used if the user does not have a theme
   * selected with the state provider.
   * 
   * @param theme the default theme
   * @param force true to force the theme, ignoring the the theme saved with the
   *            state manager
   */
  public static void setDefaultTheme(Theme theme, boolean force) {
    defaultTheme = theme;
    forceTheme = force;
  }

  /**
   * Changes the theme. A theme's stylehseets should be given a class = to the
   * theme id. Any stylesheets that have a class that do not match the id be
   * removed (stylesheets with no class specified are ignored). The method will
   * reload the application after changing themes.
   * 
   * @param theme the new theme name.
   */
  public static void switchTheme(Theme theme) {
    StateManager.get().set("theme", theme.asMap());
    XDOM.reload();
  }

  private static native void initInternal() /*-{
    $wnd.GXT = {};
    $wnd.GXT.Ext = {};
    @com.extjs.gxt.ui.client.core.Ext::load()();
  }-*/;

  private static native boolean isSecure() /*-{
    return $wnd.location.href.toLowerCase().indexOf("https") === 0;
  }-*/;

}