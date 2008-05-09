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

import com.extjs.gxt.samples.resources.client.Stock;
import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.Model;
import com.extjs.gxt.ui.client.viewer.CellLabelProvider;
import com.extjs.gxt.ui.client.viewer.ModelContentProvider;
import com.extjs.gxt.ui.client.viewer.TableViewer;
import com.extjs.gxt.ui.client.viewer.TableViewerColumn;
import com.extjs.gxt.ui.client.viewer.Viewer;
import com.extjs.gxt.ui.client.viewer.ViewerCell;
import com.extjs.gxt.ui.client.viewer.ViewerFilterField;
import com.extjs.gxt.ui.client.viewer.ViewerSorter;
import com.extjs.gxt.ui.client.widget.Button;
import com.extjs.gxt.ui.client.widget.Container;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.table.Table;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.AdapterToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class TableViewerFilterPage extends Container implements EntryPoint {

  public void onModuleLoad() {
    Viewport v = new Viewport();
    v.setLayout(new FillLayout(8));
    v.add(this);
    v.layout();
  }

  @Override
  protected void onRender(Element parent, int pos) {
    super.onRender(parent, pos);

    FlowLayout layout = new FlowLayout();
    layout.margin = 10;
    setLayout(layout);
    setMonitorResize(false);

    final DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/d/y");
    final NumberFormat currency = NumberFormat.getCurrencyFormat();

    List<TableColumn> columns = new ArrayList<TableColumn>();

    TableColumn column = new TableColumn("Company", 180);
    column.setMinWidth(75);
    column.setMaxWidth(300);
    columns.add(column);

    column = new TableColumn("Symbol", 75);
    columns.add(column);

    column = new TableColumn("Last", 75);
    column.setMaxWidth(100);
    column.setAlignment(HorizontalAlignment.RIGHT);
    columns.add(column);

    column = new TableColumn("Change", 75);
    column.setAlignment(HorizontalAlignment.RIGHT);
    columns.add(column);

    column = new TableColumn("Last Updated", 100);
    column.setAlignment(HorizontalAlignment.RIGHT);
    columns.add(column);

    TableColumnModel cm = new TableColumnModel(columns);

    final Table table = new Table(cm);

    TableViewer viewer = new TableViewer(table);
    viewer.preventRender = true;
    viewer.setContentProvider(new ModelContentProvider());

    TableViewerColumn col = viewer.getViewerColumn(0);
    col.setLabelProvider(new CellLabelProvider<Stock>() {
      public void update(ViewerCell<Stock> cell) {
        cell.setText(cell.getElement().getName());
      }
    });

    col = viewer.getViewerColumn(1);
    col.setLabelProvider(new CellLabelProvider<Stock>() {
      public void update(ViewerCell<Stock> cell) {
        cell.setText((cell.getElement()).getSymbol());
      }
    });

    col = viewer.getViewerColumn(2);
    col.setLabelProvider(new CellLabelProvider<Stock>() {
      public void update(ViewerCell<Stock> cell) {
        double val = cell.getElement().getLast();
        cell.setText(currency.format(val));
      }
    });
    col.setViewerSorter(new ViewerSorter<Stock>() {
      public int compare(Viewer viewer, Stock s1, Stock s2) {
        return s1.getLast() < s2.getLast() ? 0 : 1;
      }
    });

    col = viewer.getViewerColumn(3);
    col.setLabelProvider(new CellLabelProvider<Stock>() {
      public void update(ViewerCell<Stock> cell) {
        Stock s = cell.getElement();
        cell.setText(currency.format(s.getLast() - s.getOpen()));
      }
    });
    col.setViewerSorter(new ViewerSorter<Stock>() {
      public int compare(Viewer viewer, Stock s1, Stock s2) {
        return (s1.getLast() - s1.getOpen()) < (s2.getLast() - s2.getOpen()) ? 0 : 1;
      }
    });

    col = viewer.getViewerColumn(4);
    col.setLabelProvider(new CellLabelProvider<Stock>() {
      public void update(ViewerCell<Stock> cell) {
        Stock s = cell.getElement();
        cell.setText(dateFormat.format(s.getLastTrans()));
      }
    });
    col.setViewerSorter(new ViewerSorter<Stock>() {
      public int compare(Viewer viewer, Stock s1, Stock s2) {
        return s1.getLastTrans().compareTo(s2.getLastTrans());
      }
    });

    List<Stock> stocks = TestData.getStocks();
    viewer.setInput(stocks);

    ContentPanel panel = new ContentPanel();
    panel.setCollapsible(true);
    panel.setFrame(true);
    panel.setAnimCollapse(false);
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setIconStyle("icon-table");
    panel.setHeading("Filter TableViewer Demo");
    panel.setLayout(new FitLayout());
    panel.add(table);
    panel.setSize(575, 350);

    ViewerFilterField filterField = new ViewerFilterField<Model, Model>() {

      @Override
      protected boolean doSelect(Model parent, Model element, String filter) {
        String name = element.get("name").toString().toLowerCase();
        if (name.startsWith(filter.toLowerCase())) {
          return true;
        }
        return false;
      }

    };
    filterField.setEmptyText("Filter company...");
    filterField.bind(viewer);

    // built in support for top component
    ToolBar toolBar = new ToolBar();
    toolBar.add(new AdapterToolItem(filterField));
    panel.setTopComponent(toolBar);
    
    // add buttons
    panel.addButton(new Button("Save"));
    panel.addButton(new Button("Cancel"));

    add(panel);
  }

}
