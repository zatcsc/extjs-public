/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget;

import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Style.Direction;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.core.DomHelper;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FxEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.util.Markup;
import com.extjs.gxt.ui.client.util.WidgetHelper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

/**
 * ContentPanel is a component container that has specific functionality and
 * structural components that make it the perfect building block for
 * application-oriented user interfaces. The Panel contains bottom and top
 * toolbars, along with separate header, footer and body sections. It also
 * provides built-in expandable and collapsible behavior, along with a variety
 * of prebuilt tool buttons that can be wired up to provide other customized
 * behavior.
 * 
 * <dl>
 * <dt><b>Events:</b></dt>
 * 
 * <dd><b>BeforeExpand</b> : ComponentEvent(component)<br>
 * <div>Fires before the panel is expanded. Listeners can set the
 * <code>doit</code> field to <code>false</code> to cancel the expand.</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>Expand</b> : ComponentEvent(component)<br>
 * <div>Fires after the panel is expanded</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>BeforeCollapse</b> : ComponentEvent(component)<br>
 * <div>Fires before the panel is collpased. Listeners can set the
 * <code>doit</code> field <code>false</code> to cancel the collapse.</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>Collapse</b> : ComponentEvent(component)<br>
 * <div>Fires after the panel is collapsed.</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>BeforeClose</b> : ComponentEvent(component)<br>
 * <div>Fires before a content panel is closed. Listeners can set the
 * <code>doit</code> field to <code>false</code> to cancel the operation.</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>Close</b> : ComponentEvent(component)<br>
 * <div>Fires after a content panel is closed.</div>
 * <ul>
 * <li>component : this</li>
 * </ul>
 * </dd>
 * 
 * @param <T> the child type
 */
public class GenericContentPanel<T extends Component> extends Container<T> {

  protected boolean frame;
  protected Header head;
  protected ButtonBar buttonBar;
  protected El body, bwrap;
  protected String title;

  private String bodyStyle;
  private boolean headerVisible = true;
  private boolean collapsed, hideCollapseTool;
  private boolean footer, titleCollapse;
  private HorizontalAlignment buttonAlign = HorizontalAlignment.RIGHT;
  private boolean animCollapse = true;
  private String expandTool = "x-tool-plus";
  private String collapseTool = "x-tool-minus";
  private boolean collapsible;
  private boolean borderStyle = true, bodyBorder = true;
  private Component topComponent;
  private Component bottomComponent;
  private boolean animating;
  private ToolButton collapseBtn;
  private String headerStyle, footerStyle;
  private String headerTextStyle;
  private String bwrapStyle;
  private String tbarStyle, bbarStyle;
  private String bodStyle;
  private String collapseStyle;
  private String iconStyle;
  private El foot, tbar, bbar;

  /**
   * Creates a new panel instance.
   */
  public GenericContentPanel() {
    super();
    baseStyle = "x-panel";
    setAutoHeight(true);
    buttonBar = new ButtonBar();
    head = new Header();
  }

  /**
   * Adds a button the the panel.
   * 
   * @param button the button to add
   */
  public void addButton(Button button) {
    buttonBar.add(button);
  }

  /**
   * Collapses the panel body so that it becomes hidden. Fires the
   * <i>BeforeCollapse</i> before collapsing, then the <i>Collapse</i> event
   * after collapsing.
   */
  public void collapse() {
    if (rendered) {
      if (collapsible && !collapsed && !animating) {
        ComponentEvent ce = new ComponentEvent(this);
        if (fireEvent(Events.BeforeCollapse, ce)) {
          onCollapse();
        }
      }
    } else {
      collapsed = true;
    }
  }

  /**
   * Expands the panel body so that it becomes visible. Fires the
   * <i>BeforeExpand</i> before expanding, then the <i>Expand</i> event after
   * expanding.
   */
  public void expand() {
    if (rendered && getCollapsible() && isCollapsed()) {
      ComponentEvent ce = new ComponentEvent(this);
      if (fireEvent(Events.BeforeExpand, ce)) {
        removeStyleName(collapseStyle);
        onExpand();
      }
    }
  }

  /**
   * Returns true if animation is enabled for expand / collapse.
   * 
   * @return the animCollapse true for animations
   */
  public boolean getAnimCollapse() {
    return animCollapse;
  }

  /**
   * Returns true if the body border is enabled.
   * 
   * @return the body border state
   */
  public boolean getBodyBorder() {
    return bodyBorder;
  }

  /**
   * Returns the body style.
   * 
   * @return the body style
   */
  public String getBodyStyle() {
    return bodyStyle;
  }

  /**
   * Returns true if borders are enabled.
   * 
   * @return the boder state
   */
  public boolean getBorder() {
    return bodyBorder;
  }

  /**
   * Returns the panel's bottom component.
   * 
   * @return the bottom component
   */
  public Component getBottomComponent() {
    return bottomComponent;
  }

  /**
   * Returns the panel's button alignment.
   * 
   * @return the button alignment
   */
  public HorizontalAlignment getButtonAlign() {
    return buttonAlign;
  }

  /**
   * Returns the panel's button bar.
   * 
   * @return the button bar
   */
  public ButtonBar getButtonBar() {
    return buttonBar;
  }

  /**
   * Returns true if the panel is collapsible.
   * 
   * @return the collapsible state
   */
  public boolean getCollapsible() {
    return collapsible;
  }

  /**
   * Provides access to internal elements.
   * <p>
   * Valid values are:
   * <ul>
   * <li>"body"</li>
   * <li>"header"</li>
   * <li>"bwrap"</li>
   * </ul>
   * </p>
   * 
   * @param name the element name
   * @return the element
   */
  public Element getElement(String name) {
    if (name.equals("header")) {
      return head.getElement();
    } else if (name.equals("bwrap")) {
      return bwrap.dom;
    } else if (name.equals("body")) {
      return body.dom;
    }
    return null;
  }

  /**
   * @return the footer
   */
  public boolean getFooter() {
    return footer;
  }

  /**
   * Returns true if framing is enabled.
   * 
   * @return the frame state
   */
  public boolean getFrame() {
    return frame;
  }

  /**
   * Returns the height in pixels of the framing elements of this panel
   * (including any top and bottom bars and header and footer elements, but not
   * including the body height). To retrieve the body height see
   * {@link #getInnerHeight}.
   * 
   * @return the frame height
   */
  public int getFrameHeight() {
    int h = el.getFrameWidth("tb");
    h += (tbar != null ? tbar.getHeight() : 0) + (bbar != null ? bbar.getHeight() : 0);
    if (frame) {
      Element hd = el.firstChild().dom;
      Element ft = bwrap.lastChild().dom;
      h += (fly(hd).getHeight() + fly(ft).getHeight());
      Element mc = bwrap.subChild(3).dom;
      h += fly(mc).getFrameWidth("tb");
    } else {
      if (head != null) {
        h += head.getOffsetHeight();
      }
      if (foot != null) {
        h += foot.getHeight();
      }
    }
    return h;
  }

  /**
   * Returns the width in pixels of the framing elements of this panel (not
   * including the body width). To retrieve the body width see
   * {@link #getInnerWidth}.
   * 
   * @return The frame width
   */
  public int getFrameWidth() {
    int w = el.getFrameWidth("lr");
    if (frame) {
      Element l = bwrap.firstChild().dom;
      w += (fly(l).getFrameWidth("l") + fly(l).firstChild().getFrameWidth("r"));
      Element mc = bwrap.subChild(3).dom;
      w += fly(mc).getFrameWidth("lr");
    }
    return w;
  }

  /**
   * Returns the panel's header.
   * 
   * @return the header
   */
  public Header getHeader() {
    return head;
  }

  /**
   * Returns the height in pixels of the body element (not including the height
   * of any framing elements). For the frame height see {@link #getFrameHeight}.
   * 
   * @return the inner height
   */
  public int getInnerHeight() {
    return getSize().height - getFrameHeight();
  }

  /**
   * Returns the width in pixels of the body element (not including the width of
   * any framing elements). For the frame width see {@link #getFrameWidth}.
   * 
   * @return the body width
   */
  public int getInnerWidth() {
    return this.getSize().width - this.getFrameWidth();
  }

  @Override
  public El getLayoutTarget() {
    return body;
  }

  /**
   * @return the titleCollapse
   */
  public boolean getTitleCollapse() {
    return titleCollapse;
  }

  /**
   * Returns the panel's title text.
   * 
   * @return the title text
   */
  public String getTitleText() {
    return title;
  }

  /**
   * Returns the panels top component.
   * 
   * @return the top component
   */
  public Component getTopComponent() {
    assert !rendered : "method call only be called before the component is rendered";
    return topComponent;
  }

  public Widget getWidget(String field) {
    if ("collapseBtn".equals(field)) {
      return collapseBtn;
    }
    return null;
  }

  /**
   * Returns <code>true</code> if the panel is expanded.
   * 
   * @return the expand state
   */
  public boolean isExpanded() {
    return !isCollapsed();
  }

  /**
   * Returns true if the header is visible.
   * 
   * @return the header visible state
   */
  public boolean isHeaderVisible() {
    return headerVisible;
  }

  /**
   * Returns true if the collapse tool is hidden.
   * 
   * @return the hide collapse tool state
   */
  public boolean isHideCollapseTool() {
    return hideCollapseTool;
  }

  @Override
  public void onComponentEvent(ComponentEvent ce) {
    super.onComponentEvent(ce);
    if (ce.type == Event.ONCLICK) {
      onClick(ce);
    }
  }

  /**
   * @param animCollapse the animCollapse to set
   */
  public void setAnimCollapse(boolean animCollapse) {
    this.animCollapse = animCollapse;
  }

  /**
   * True to display an interior border on the body element of the panel, false
   * to hide it (defaults to true). This only applies when
   * {@link #setBodyBorder(boolean)} == true. If border == true and bodyBorder ==
   * false, the border will display as a 1px wide inset border, giving the
   * entire body element an inset appearance.
   * 
   * @param bodyBorder the body border state
   */
  public void setBodyBorder(boolean bodyBorder) {
    this.bodyBorder = bodyBorder;
  }

  /**
   * Custom CSS styles to be applied to the body element in the format expected
   * by {@link El#applyStyles}.
   * 
   * @param bodyStyle the body style
   */
  public void setBodyStyle(String bodyStyle) {
    this.bodyStyle = bodyStyle;
  }

  /**
   * True to display the borders of the panel's body element, false to hide them
   * (defaults to true). By default, the border is a 2px wide inset border, but
   * this can be further altered by setting {@link #setBodyBorder(boolean)} to
   * false.
   * 
   * @param show the border style state
   */
  public void setBorder(boolean show) {
    this.borderStyle = show;
  }

  /**
   * Sets the panel's bottom component. The component's natural height will be
   * used and will not be changed by the panel.
   * 
   * @param bottomComponent the bottom component
   */
  public void setBottomComponent(Component bottomComponent) {
    assert !rendered : "method call only be called before the component is rendered";
    this.bottomComponent = bottomComponent;
  }

  /**
   * Sets the button alignment of any buttons added to this panel (defaults to
   * RIGHT).
   * 
   * @param buttonAlign the button alignment
   */
  public void setButtonAlign(HorizontalAlignment buttonAlign) {
    this.buttonAlign = buttonAlign;
  }

  /**
   * True to make the panel collapsible and have the expand/collapse toggle
   * button automatically rendered into the header tool button area, false to
   * keep the panel statically sized with no button (defaults to false,
   * pre-render).
   * 
   * @param collapsible the collapsible to set
   */
  public void setCollapsible(boolean collapsible) {
    this.collapsible = collapsible;
  }

  /**
   * Sets the panel's expand state.
   * 
   * @param expanded <code>true<code> true to expand
   */
  public void setExpanded(boolean expanded) {
    if (expanded) {
      expand();
    } else {
      collapse();
    }
  }

  /**
   * True to create the footer element explicitly, false to skip creating it (pre-render). By
   * default, when footer is not specified, if one or more buttons have been
   * added to the panel the footer will be created automatically, otherwise it
   * will not.
   * 
   * @param footer the footer state
   */
  public void setFooter(boolean footer) {
    this.footer = footer;
  }

  /**
   * True to render the panel with custom rounded borders, false to render with
   * plain 1px square borders (defaults to false, pre-render).
   * 
   * @param frame true to use the frame style
   */
  public void setFrame(boolean frame) {
    this.frame = frame;
  }

  /**
   * True to create the header element explicitly, false to skip creating it. By
   * default, when header is not specified, if a {@link #setHeading(String)} is
   * set the header will be created automatically, otherwise it will not. If a
   * title is set but header is explicitly set to false, the header will not be
   * rendered.
   * 
   * @param headerVisible true to show the header
   */
  public void setHeaderVisible(boolean headerVisible) {
    this.headerVisible = headerVisible;
  }

  /**
   * Sets the title text for the panel.
   * 
   * @param text the title text
   */
  public void setHeading(String text) {
    this.title = text;
    if (head != null) {
      head.setText(text);
    }
  }

  /**
   * Sets whether the collapse tool should be displayed when the panel is
   * collapsible.
   * 
   * @param hideCollapseTool true if the tool is hidden
   */
  public void setHideCollapseTool(boolean hideCollapseTool) {
    this.hideCollapseTool = hideCollapseTool;
  }

  /**
   * Sets the header's icon style.
   * 
   * @param iconStyle the icon style
   */
  public void setIconStyle(String iconStyle) {
    this.iconStyle = iconStyle;
    if (rendered) {
      head.setIconStyle(iconStyle);
    }
  }

  /**
   * True to allow expanding and collapsing the panel (when {@link #collapsible} =
   * true) by clicking anywhere in the header bar, false to allow it only by
   * clicking to tool button (defaults to false).
   * 
   * @param titleCollapse the titleCollapse to set
   */
  public void setTitleCollapse(boolean titleCollapse) {
    this.titleCollapse = titleCollapse;
  }

  /**
   * Sets the panel's top component.
   * 
   * @param topComponent the component
   */
  public void setTopComponent(Component topComponent) {
    this.topComponent = topComponent;
  }

  /**
   * Adds the content from the given url.
   * 
   * @param url the url
   * @return the new frame instance
   */
  public Frame setUrl(String url) {
    Frame f = new Frame(url);
    fly(f.getElement()).setStyleAttribute("frameBorder", "0");
    f.setSize("100%", "100%");
    removeAll();
    add(new WidgetComponent(f));
    return f;
  }

  protected void afterCollapse() {
    addStyleName(collapseStyle);
    collapsed = true;
    animating = false;
    el.setHeight("auto");
    ComponentEvent ce = new ComponentEvent(this);
    fireEvent(Events.Collapse, ce);
    fireEvent(Events.Resize, ce);
  }

  protected void afterExpand() {
    collapsed = false;
    animating = false;
    layout(true);
    ComponentEvent ce = new ComponentEvent(this);
    fireEvent(Events.Expand, ce);
    fireEvent(Events.Resize, ce);
  }

  @Override
  protected void afterRender() {
    super.afterRender();
    if (title != null) {
      setHeading(title);
    }
    if (iconStyle != null) {
      setIconStyle(iconStyle);
    }
    if (collapsed) {
      collapsed = false;
      collapse();
    }
  }

  protected void createStyles(String baseStyle) {
    headerStyle = baseStyle + "-header";
    headerTextStyle = baseStyle + "-header-text";
    bwrapStyle = baseStyle + "-bwrap";
    tbarStyle = baseStyle + "-tbar";
    bodStyle = baseStyle + "-body";
    bbarStyle = baseStyle + "-bbar";
    footerStyle = baseStyle + "-footer";
    collapseStyle = baseStyle + "-collapse";
  }

  @Override
  protected void doAttachChildren() {
    super.doAttachChildren();
    if (head != null) head.onAttach();
    if (getFooter() && buttonBar != null) {
      if (buttonBar.getItemCount() > 0) {
        buttonBar.onAttach();
      }
    }
    if (topComponent != null) topComponent.onAttach();
    if (bottomComponent != null) bottomComponent.onAttach();
  }

  @Override
  protected void doDetachChildren() {
    super.doDetachChildren();
    if (head != null) WidgetHelper.doDetach(head);
    if (buttonBar != null && buttonBar.isAttached()) {
      WidgetHelper.doDetach(buttonBar);
    }
    if (topComponent != null) WidgetHelper.doDetach(topComponent);
    if (bottomComponent != null) WidgetHelper.doDetach(bottomComponent);
  }

  protected void initTools() {
    if (getCollapsible()) {
      collapseBtn = new ToolButton(collapseTool);
      collapseBtn.addListener(Events.Select, new Listener<ComponentEvent>() {
        public void handleEvent(ComponentEvent ce) {
          ce.stopEvent();
          setExpanded(!isExpanded());
        }
      });
      head.addTool(collapseBtn);
    }
  }

  protected void onClick(ComponentEvent ce) {
    if (head != null && ce.within(head.getElement())) {
      setExpanded(!isExpanded());
    }
  }

  protected void onCollapse() {
    collapseBtn.changeStyle(expandTool);
    if (getAnimCollapse() && !animating) {
      animating = true;
      bwrap.slideOut(Direction.UP, 300, new Listener<FxEvent>() {
        public void handleEvent(FxEvent fe) {
          afterCollapse();
        }
      });
    } else {
      bwrap.setVisible(false);
      afterCollapse();
    }
  }

  protected void onExpand() {
    collapseBtn.changeStyle(collapseTool);
    if (getAnimCollapse() && !animating) {
      animating = true;
      bwrap.slideIn(Direction.DOWN, 300, new Listener<FxEvent>() {
        public void handleEvent(FxEvent fe) {
          afterExpand();
        }
      });
    } else {
      bwrap.setVisible(true);
      afterExpand();
    }
  }

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    if (buttonBar.getItemCount() > 0) {
      buttonBar.setButtonAlign(getButtonAlign());
      setFooter(true);
    }

    if (frame) {
      String s = Format.substitute(Markup.BBOX, baseStyle);
      DomHelper.insertHtml("afterBegin", el.dom, s);

      head.baseStyle = headerStyle;
      head.setTextStyle(headerTextStyle);
      initTools();
      head.render(el.dom);
      el.subChild(3).appendChild(head.getElement());
      bwrap = el.createChild("<div class='" + bwrapStyle + "'></div>");

      Element bw = bwrap.dom;
      Element ml = DOM.getChild(el.dom, 1);
      Element bl = DOM.getChild(el.dom, 2);
      DOM.appendChild(bw, ml);
      DOM.appendChild(bw, bl);

      Element mc = fly(bw).getSubChild(3);
      tbar = fly(mc).createChild("<div class=" + tbarStyle + "></div>");
      body = fly(mc).createChild("<div class=" + bodStyle + "></div>");
      bbar = fly(mc).createChild("<div class=" + bbarStyle + "></div>");

      tbar.setVisible(false);
      bbar.setVisible(false);

      El e = fly(bw).lastChild().firstChild().firstChild();
      foot = e.createChild("<div class=" + footerStyle + "></div>");

      if (!isHeaderVisible()) {
        head.setVisible(false);
        body.addStyleName(bodStyle + "-noheader");
        if (tbar != null) {
          tbar.addStyleName(tbarStyle + "-noheader");
        }
      }

    } else {
      head.baseStyle = headerStyle;
      head.setTextStyle(headerTextStyle);
      initTools();
      head.render(el.dom);
      bwrap = el.createChild("<div class=" + bwrapStyle + "></div>");

      Element bw = bwrap.dom;
      tbar = fly(bw).createChild("<div class=" + tbarStyle + "></div>");
      body = fly(bw).createChild("<div class=" + bodStyle + "></div>");
      bbar = fly(bw).createChild("<div class=" + bbarStyle + "></div>");
      foot = fly(bw).createChild("<div class=" + footerStyle + "></div>");

      tbar.setVisible(false);
      bbar.setVisible(false);

      if (!isHeaderVisible()) {
        head.setVisible(false);
        body.addStyleName(bodStyle + "-noheader");
        if (tbar != null) {
          tbar.addStyleName(tbarStyle + "-noheader");
        }
      }
    }

    if (head != null) {
      String t = title != null ? title : "&#160;";
      head.setText(t);
    }

    if (footer && buttonBar.getItemCount() > 0) {
      buttonBar.render(foot.dom);
    }

    if (!footer) {
      bwrap.lastChild().addStyleName("x-panel-nofooter");
    }

    if (!borderStyle) {
      el.addStyleName(baseStyle + "-noborder");
      body.addStyleName(bodStyle + "-noborder");
      if (head != null) {
        head.addStyleName(headerStyle + "-noborder");
      }
      if (tbar != null) {
        tbar.addStyleName(tbarStyle + "-noborder");
      }
      if (bbar != null) {
        bbar.addStyleName(bbarStyle + "-noborder");
      }
    }

    if (!bodyBorder) {
      body.addStyleName(bodStyle + "noborder");
    }

    if (bodyStyle != null) {
      body.applyStyles(getBodyStyle());
    }

    if (isHeaderVisible()) {
      head.disableTextSelection(true);
    }

    if (topComponent != null) {
      tbar.setVisible(true);
      topComponent.render(tbar.dom);
    }

    if (bottomComponent != null) {
      bbar.setVisible(true);
      bottomComponent.render(bbar.dom);
    }

    if (titleCollapse) {
      head.setStyleAttribute("cursor", "pointer");
      el.addEventsSunk(Event.ONCLICK);
    }

  }

  @Override
  protected void onResize(final int width, final int height) {
    if (isAutoWidth()) {
      body.setWidth("auto");
    } else if (width != -1) {
      body.setWidth(width - getFrameWidth(), true);
    }
    if (isAutoHeight()) {
      body.setHeight("auto");
    } else if (height != -1) {
      body.setHeight(height - getFrameHeight(), true);
    }
    layout();
  }

  /**
   * Returns true if the panel is collapsed.
   * 
   * @return the collapsed state
   */
  private boolean isCollapsed() {
    return collapsed;
  }

}