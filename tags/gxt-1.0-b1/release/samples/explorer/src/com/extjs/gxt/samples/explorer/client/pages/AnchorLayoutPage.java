/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class AnchorLayoutPage extends Container {

  public AnchorLayoutPage() {
    setData("layout", new FitLayout());
  }

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    setLayout(new AnchorLayout());

    ContentPanel panel1 = new ContentPanel();
    panel1.setHeading("Panel 1");
    panel1.addText("Width = 50% of the container");
    panel1.setData(new AnchorData("50%"));

    ContentPanel panel2 = new ContentPanel();
    panel2.setHeading("Panel 2");
    panel2.addText("Width = container width - 100 pixels");

    ContentPanel panel3 = new ContentPanel();
    panel3.setHeading("Panel 3");
    panel3.addText("Width = container width - 10 pixels<br>Height = container height - 262 pixels");

    add(panel1);
    add(panel2);
    add(panel3);
  }

}