/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client;

import com.extjs.gxt.samples.mail.client.model.MailModel;
import com.extjs.gxt.samples.mail.client.mvc.AppController;
import com.extjs.gxt.samples.mail.client.mvc.ContactController;
import com.extjs.gxt.samples.mail.client.mvc.MailController;
import com.extjs.gxt.samples.mail.client.mvc.TaskController;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class Mail implements EntryPoint {

  private MailModel model;
  private Dispatcher dispatcher;

  public void onModuleLoad() {
    Window.alert("This demo is not currently functional");
    
    model = new MailModel();
    Registry.register("model", model);

    dispatcher = Dispatcher.get();
    dispatcher.addController(new AppController());
    dispatcher.addController(new MailController());
    dispatcher.addController(new TaskController());
    dispatcher.addController(new ContactController());
    dispatcher.dispatch(AppEvents.Init);
  }

}