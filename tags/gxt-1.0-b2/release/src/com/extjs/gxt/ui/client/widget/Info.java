/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget;

import java.util.ArrayList;
import java.util.Stack;

import com.extjs.gxt.ui.client.XDOM;
import com.extjs.gxt.ui.client.event.FxEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.util.Params;
import com.extjs.gxt.ui.client.util.Point;
import com.extjs.gxt.ui.client.util.Size;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Displays a message in the bottom right region of the browser for a specified
 * amount of time.
 */
public class Info extends ContentPanel {

  private static Stack<Info> infoStack = new Stack<Info>();
  private static ArrayList<Info> slots = new ArrayList<Info>();

  /**
   * Displays a message using the specified config.
   * 
   * @param config the info config
   */
  public static void display(InfoConfig config) {
    pop().show(config);
  }
  
  /**
   * Displays a message with the given text. All {0},{1}... values in text will be
   * replaced with values.
   * 
   * @param title the message title
   * @param text the message
   * @param values the values to be substituted
   */
  public static void display(String title, String text, String... values) {
    display(new InfoConfig(title, text, new Params((Object[])values)));
  }

  /**
   * Displays a message with the given title and text. The passed parameters
   * will be applied to both the title and text before being displayed.
   * 
   * @param title the info title
   * @param text the info text
   * @param params the paramters to be applied to the title and text
   */
  public static void display(String title, String text, Params params) {
    InfoConfig config = new InfoConfig(title, text, params);
    display(config);
  }

  private static int firstAvail() {
    int size = slots.size();
    for (int i = 0; i < size; i++) {
      if (slots.get(i) == null) {
        return i;
      }
    }
    return size;
  }

  private static Info pop() {
    Info info = infoStack.size() > 0 ? (Info) infoStack.pop() : null;
    if (info == null) {
      info = new Info();
    }
    return info;
  }

  private static void push(Info info) {
    infoStack.push(info);
  }
  
  private InfoConfig config;
  private int level;

  /**
   * Creates a new info instance.
   */
  public Info() {
    baseStyle = "x-info";
    frame = true;
    setShadow(true);
    setLayoutOnChange(true);
  }

  public void hide() {
    super.hide();
    afterHide();
  }

  /**
   * Displays the info.
   * 
   * @param config the info config
   */
  public void show(InfoConfig config) {
    this.config = config;
    onShowInfo();
  }

  private void afterHide() {
    slots.set(level, null);
    RootPanel.get().remove(this);
    push(this);
  }

  private void afterShow() {
    Timer t = new Timer() {
      public void run() {
        el.fadeOut(new Listener<FxEvent>() {
          public void handleEvent(FxEvent ce) {
            afterHide();
          }
        });
      }
    };
    t.schedule(config.display);
  }

  private void onShowInfo() {
    RootPanel.get().add(this);
    el.makePositionable(true);

    setTitle();
    setText();

    level = firstAvail();
    slots.add(level, this);
    
    Point p = position();
    el.setLeftTop(p.x, p.y);
    setSize(config.width, config.height);

    el.setVisibility(false);
    el.fadeIn(new Listener<FxEvent>() {
      public void handleEvent(FxEvent fe) {
        afterShow();
      }
    });
  }

  private Point position() {
    Size s = XDOM.getViewportSize();
    int left = (s.width - config.width - 10);
    int top = s.height - config.height - 10 - (level * (config.height + 10));
    return new Point(left, top);
  }
  
  private void setText() {
    if (config.text != null) {
      if (config.params != null) {
        config.text = Format.substitute(config.text, config.params);
      }
      removeAll();
      addText(config.text);
    }
  }

  private void setTitle() {
    if (config.title != null) {
      head.setVisible(true);
      if (config.params != null) {
        config.title = Format.substitute(config.title, config.params);
      }
      setHeading(config.title);
    } else {
      head.setVisible(false);
    }
  }

}