/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client;


import com.extjs.gxt.samples.explorer.client.model.Post;
import com.extjs.gxt.ui.client.data.BaseLoadConfig;
import com.extjs.gxt.ui.client.data.BaseLoadResult.ModelCollectionLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;

public interface ExplorerService extends RemoteService {

  public ModelCollectionLoadResult<Post> getPosts(BaseLoadConfig config);
  
}