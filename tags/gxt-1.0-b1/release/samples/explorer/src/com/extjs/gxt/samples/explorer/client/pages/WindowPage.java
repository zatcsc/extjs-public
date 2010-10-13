/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Button;
import com.extjs.gxt.ui.client.widget.ButtonBar;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.ToolButton;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.AccordianLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class WindowPage extends Container implements EntryPoint {

  public void onModuleLoad() {
    RootPanel.get().add(this);
  }

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    final Window simple = new Window();
    simple.setHeading("Simple Window");
    simple.setWidth(250);
    simple.addText(TestData.DUMMY_TEXT_SHORT);
    simple.setScrollMode(Scroll.AUTO);

    final Window complex = new Window();
    complex.maximizable = true;
    complex.setHeading("Accordian Window");
    complex.setWidth(200);
    complex.setHeight(350);
    
    ToolBar toolBar = new ToolBar();
    TextToolItem item = new TextToolItem();
    item.setIconStyle("icon-connect");
    toolBar.add(item);

    toolBar.add(new SeparatorToolItem());
    complex.setTopComponent(toolBar);
    
    item = new TextToolItem();
    item.setIconStyle("icon-user-add");
    toolBar.add(item);
    
    item = new TextToolItem();
    item.setIconStyle("icon-user-delete");
    toolBar.add(item);
    
    complex.setIconStyle("icon-accordian");
    complex.setLayout(new AccordianLayout());

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Online Users");
    cp.setScrollMode(Scroll.AUTO);
    cp.getHeader().addTool(new ToolButton("x-tool-refresh"));
    
    complex.add(cp);

    Tree tree = new Tree();
    TreeItem family = new TreeItem("Family");
    tree.getRootItem().add(family);
    family.add(newItem("Darrell", "user"));
    family.add(newItem("Maro", "user-girl"));
    family.add(newItem("Lia", "user-kid"));
    family.add(newItem("Alec", "user-kid"));
    family.setExpanded(true);

    TreeItem friends = new TreeItem("Friends");
    tree.getRootItem().add(friends);
    friends.add(newItem("Bob", "user"));
    friends.add(newItem("Mary", "user-girl"));
    friends.add(newItem("Sally", "user-girl"));
    friends.add(newItem("Jack", "user"));
    friends.setExpanded(true);

    cp.add(tree);

    cp = new ContentPanel();
    cp.setHeading("Settings");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    complex.add(cp);

    cp = new ContentPanel();
    cp.setHeading("Stuff");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    complex.add(cp);

    cp = new ContentPanel();
    cp.setHeading("More Stuff");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    complex.add(cp);

    ButtonBar buttons = new ButtonBar();

    buttons.add(new Button("Simple", new SelectionListener() {
      public void componentSelected(ComponentEvent ce) {
        simple.show();
      }
    }));

    buttons.add(new Button("Complex", new SelectionListener() {
      public void componentSelected(ComponentEvent ce) {
        complex.show();
      }
    }));

    add(buttons);
    setLayout(new FlowLayout(4));
    layout(true);
  }

  private TreeItem newItem(String text, String iconStyle) {
    TreeItem item = new TreeItem(text);
    item.setIconStyle(iconStyle);
    return item;
  }

}