/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client.mvc;

import java.util.List;

import com.extjs.gxt.samples.mail.client.AppEvents;
import com.extjs.gxt.samples.mail.client.MailServiceAsync;
import com.extjs.gxt.samples.resources.client.Folder;
import com.extjs.gxt.samples.resources.client.MailItem;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MailController extends Controller {

  private MailServiceAsync service;
  private MailFolderView folderView;
  private MailView mailView;


  public MailController() {
    registerEventTypes(AppEvents.Init);
    registerEventTypes(AppEvents.NavMail);
    registerEventTypes(AppEvents.ViewMailItems);
    registerEventTypes(AppEvents.ViewMailItem);
  }

  @Override
  public void handleEvent(AppEvent event) {
    switch (event.type) {
      case AppEvents.Init:
        forwardToView(folderView, event);
        break;
      case AppEvents.NavMail:
        forwardToView(folderView, event);
        forwardToView(mailView, event);
        break;
      case AppEvents.ViewMailItems:
        onViewMailItems(event);
        break;
      case AppEvents.ViewMailItem:
        forwardToView(mailView, event);
        break;
    }
  }

  private void onViewMailItems(final AppEvent<Folder> event) {
    final Folder f = event.data;
    if (f != null) {
      service.getMailItems(f, new AsyncCallback<List<MailItem>>() {

        public void onSuccess(List<MailItem> result) {
          AppEvent ae = new AppEvent(event.type, result);
          ae.setData("folder", f);
          forwardToView(mailView, ae);
        }

        public void onFailure(Throwable caught) {
          Dispatcher.forwardEvent(AppEvents.Error, caught);
        }

      });
    }

  }

  public void initialize() {
    service = (MailServiceAsync) Registry.get("service");

    folderView = new MailFolderView(this);
    mailView = new MailView(this);
  }

}