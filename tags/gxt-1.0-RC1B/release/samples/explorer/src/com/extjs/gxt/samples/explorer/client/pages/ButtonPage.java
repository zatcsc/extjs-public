/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;


import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ButtonBar;
import com.extjs.gxt.ui.client.widget.button.ToggleButton;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ButtonPage extends LayoutContainer implements EntryPoint {

  public void onModuleLoad() {
    RootPanel.get().add(this);
  }
  
  public ButtonPage() {
    SelectionListener listener = new SelectionListener<ComponentEvent>() {
      public void componentSelected(ComponentEvent ce) {
        Button btn = (Button) ce.component;
        Info.display("Click Event", "The '{0}' button was clicked.", btn.getText());
      }
    };

    ButtonBar buttonBar = new ButtonBar();
    buttonBar.add(new Button("Click Me", listener));

    Button iconBtn = new Button("Icon Button", listener);
    iconBtn.setIconStyle("icon-printer");
    buttonBar.add(iconBtn);

    Button disabled = new Button("Disabled", listener);
    disabled.disable();
    buttonBar.add(disabled);
    
    ToggleButton toggle = new ToggleButton("Toggle");
    toggle.toggle(true);
    buttonBar.add(toggle);

    setLayout(new FlowLayout(4));
    add(buttonBar);
  }


}