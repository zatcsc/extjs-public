/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.layouts;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.user.client.ui.FlexTable;

public class BorderLayoutExample extends LayoutContainer {

  public BorderLayoutExample() {
    final BorderLayout layout = new BorderLayout();
    setLayout(layout);

    ContentPanel north = new ContentPanel();
    ContentPanel west = new ContentPanel();
    ContentPanel center = new ContentPanel();
    center.setHeading("BorderLayout Example");
    center.setScrollMode(Scroll.AUTOX);

    FlexTable table = new FlexTable();
    table.getElement().getStyle().setProperty("margin", "10px");
    table.setCellSpacing(8);
    table.setCellPadding(4);

    for (int i = 0; i < LayoutRegion.values().length; i++) {
      final LayoutRegion r = LayoutRegion.values()[i];
      if (r == LayoutRegion.CENTER) {
        continue;
      }
      SelectionListener<ButtonEvent> sl = new SelectionListener<ButtonEvent>() {

        @Override
        public void componentSelected(ButtonEvent ce) {
          String txt = ce.button.getText();
          if (txt.equals("Expand")) {
            layout.expand(r);
          } else if (txt.equals("Collapse")) {
            layout.collapse(r);
          } else if (txt.equals("Show")) {
            layout.show(r);
          } else {
            layout.hide(r);
          }

        }
      };
      table.setHTML(i, 0, "<div style='font-size: 12px; width: 100px'>" + r.name() + ":</span>");
      table.setWidget(i, 1, new Button("Expand", sl));
      table.setWidget(i, 2, new Button("Collapse", sl));
      table.setWidget(i, 3, new Button("Show", sl));
      table.setWidget(i, 4, new Button("Hide", sl));
    }
    center.add(table);

    ContentPanel east = new ContentPanel();
    ContentPanel south = new ContentPanel();

    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 100);
    northData.setCollapsible(true);
    northData.setFloatable(true);
    northData.setHideCollapseTool(true);
    northData.setSplit(true);
    northData.setMargins(new Margins(5, 5, 0, 5));

    BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 150);
    westData.setSplit(true);
    westData.setCollapsible(true);
    westData.setMargins(new Margins(5));

    BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
    centerData.setMargins(new Margins(5, 0, 5, 0));

    BorderLayoutData eastData = new BorderLayoutData(LayoutRegion.EAST, 150);
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