/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.explorer.client.pages;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.samples.resources.client.Folder;
import com.extjs.gxt.samples.resources.client.Music;
import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.viewer.CellLabelProvider;
import com.extjs.gxt.ui.client.viewer.ModelLabelProvider;
import com.extjs.gxt.ui.client.viewer.ModelTreeContentProvider;
import com.extjs.gxt.ui.client.viewer.SelectionChangedEvent;
import com.extjs.gxt.ui.client.viewer.SelectionChangedListener;
import com.extjs.gxt.ui.client.viewer.TreeTableViewer;
import com.extjs.gxt.ui.client.viewer.TreeTableViewerColumn;
import com.extjs.gxt.ui.client.viewer.ViewerCell;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.TextMenuItem;
import com.extjs.gxt.ui.client.widget.treetable.TreeTable;
import com.extjs.gxt.ui.client.widget.treetable.TreeTableColumn;
import com.extjs.gxt.ui.client.widget.treetable.TreeTableColumnModel;
import com.extjs.gxt.ui.client.widget.treetable.TreeTableItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;

public class TreeTableViewerPage extends Container implements EntryPoint {

  public void onModuleLoad() {
    Viewport v = new Viewport();
    v.setLayout(new FillLayout());
    v.add(this);
    v.layout();
  }

  private int count = 1;

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    FlowLayout layout = new FlowLayout();
    layout.margin = 10;
    setLayout(layout);

    final Folder folder = TestData.getTreeModel();

    List<TreeTableColumn> columns = new ArrayList<TreeTableColumn>();

    TreeTableColumn column = new TreeTableColumn("Name", 250);
    column.setMinWidth(75);
    columns.add(column);

    column = new TreeTableColumn("Author", 100);
    columns.add(column);

    column = new TreeTableColumn("Genre", 100);
    column.setAlignment(HorizontalAlignment.RIGHT);
    columns.add(column);

    TreeTableColumnModel cm = new TreeTableColumnModel(columns);

    final TreeTable table = new TreeTable(cm);
    table.animate = false;
    table.itemImageStyle = "icon-music";

    final TreeTableViewer viewer = new TreeTableViewer(table);
    viewer.setContentProvider(new ModelTreeContentProvider());
    viewer.setLabelProvider(new ModelLabelProvider());

    TreeTableViewerColumn col = viewer.getViewerColumn(1);
    col.setLabelProvider(new CellLabelProvider() {
      public void update(ViewerCell cell) {
        if (cell.getElement() instanceof Music) {
          cell.setText(((Music) cell.getElement()).getAuthor());
        }
        cell.setToolTipText(cell.getText());
      }
    });

    col = viewer.getViewerColumn(2);
    col.setLabelProvider(new CellLabelProvider() {
      public void update(ViewerCell cell) {
        if (cell.getElement() instanceof Music) {
          cell.setText(((Music) cell.getElement()).getGenre());
        }
        cell.setToolTipText("<b>This is content:</b> " + cell.getText());
      }
    });

    viewer.addSelectionListener(new SelectionChangedListener() {
      public void selectionChanged(SelectionChangedEvent se) {
        BaseTreeModel m = (BaseTreeModel) se.getSelection().getFirstElement();
        Info.display("Selection Changed", "{0} was selected", (String) m.get("name"));
      }
    });

    Menu contextMenu = new Menu();

    TextMenuItem insert = new TextMenuItem();
    insert.setText("Insert Item");
    insert.setIconStyle("icon-add");
    insert.addSelectionListener(new SelectionListener() {
      public void componentSelected(ComponentEvent ce) {
        Music music = new Music("Add Child: " + count++, "Unknown", "Unknown");
        BaseTreeModel m = (BaseTreeModel) viewer.getSelection().getFirstElement();
        m.add(music);
      }
    });
    contextMenu.add(insert);

    TextMenuItem remove = new TextMenuItem();
    remove.setText("Remove Selected");
    remove.setIconStyle("icon-delete");
    remove.addSelectionListener(new SelectionListener() {
      public void componentSelected(ComponentEvent ce) {
        TreeTableItem item = (TreeTableItem) table.getSelectedItem();
        if (item != null) {
          item.getParentItem().remove(item);
        }
      }
    });
    contextMenu.add(remove);

    table.setContextMenu(contextMenu);
    viewer.setInput(folder);

    ContentPanel panel = new ContentPanel();
    panel.frame = true;
    panel.collapsible = true;
    panel.animCollapse = false;
    panel.buttonAlign = HorizontalAlignment.CENTER;
    panel.setIconStyle("icon-table");
    panel.setHeading("TreeTableViewer Demo");
    panel.setLayout(new FitLayout());
    panel.add(table);
    panel.setSize(575, 350);

    add(panel);
  }

}