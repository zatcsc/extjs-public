/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class BorderLayoutPage extends LayoutContainer implements EntryPoint {

  public void onModuleLoad() {
    Viewport v = new Viewport();
    v.setLayout(new FitLayout());
    v.add(this);
    RootPanel.get().add(v);
  }
  
  public BorderLayoutPage() {
    // next line is only used to pass layout to containing container
    // this will have NO effect outside of the explorer demo
    setData("layout", new FitLayout());
  }

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);
    setLayout(new BorderLayout());
    
    ContentPanel north = new ContentPanel();
    ContentPanel west = new ContentPanel();
    ContentPanel center = new ContentPanel();
    ContentPanel east = new ContentPanel();
    ContentPanel south = new ContentPanel();
    
    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 100);
    northData.setCollapsible(true);
    northData.setFloatable(true);
    northData.setSplit(true);
    northData.setMargins(new Margins(5, 5, 0, 5));
    
    BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200);
    westData.setSplit(true);
    westData.setCollapsible(true);
    westData.setMargins(new Margins(5));
    
    BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
    centerData.setMargins(new Margins(5, 0, 5, 0));
    
    
    BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST, 200);
    eastData.setSplit(true);
    eastData.setCollapsible(true);
    eastData.setMargins(new Margins(5));
    
    BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH, 100);
    southData.setSplit(true);
    southData.setCollapsible(true);
    southData.setFloatable(true);
    southData.setMargins(new Margins(0, 5, 5, 5));
    
    add(north, northData);
    add(west, westData);
    add(center, centerData);
    add(east, eastData);
    add(south, southData);

  }

}