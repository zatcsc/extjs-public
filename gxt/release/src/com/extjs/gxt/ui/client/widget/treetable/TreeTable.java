/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.treetable;

import com.extjs.gxt.ui.client.XDOM;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.util.StyleTemplate;
import com.extjs.gxt.ui.client.util.WidgetHelper;
import com.extjs.gxt.ui.client.widget.table.BaseTable;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.table.TableHeader;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

/**
 * A hierarchical tree widget with support for additional columns. The tree
 * contains a hierarchy of <code>TreeTableItems</code> that the user can open,
 * close, and select.
 * 
 * <dl>
 * <dt><b>Events:</b></dt>
 * 
 * <dd><b>CellClick</b> : (widget, item, index)<br>
 * <div>Fires after a cell has been clicked.</div>
 * <ul>
 * <li>widget : tree table</li>
 * <li>item : item represented by the cell</li>
 * <li>index : cell column index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>CellDoubleClick</b> : (widget, item, index)<br>
 * <div>Fires after a cell has been double clicked.</div>
 * <ul>
 * <li>widget : tree table</li>
 * <li>item : item represented by the cell</li>
 * <li>index : cell column index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>RowClick</b> : (widget, item, index)<br>
 * <div>Fires after a cell has been clicked.</div>
 * <ul>
 * <li>widget : tree table</li>
 * <li>item : item that represents the row</li>
 * <li>index : cell column index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>RowDoubleClick</b> : (widget, item, index)<br>
 * <div>Fires after a cell has been double clicked.</div>
 * <ul>
 * <li>widget : tree table</li>
 * <li>item : item that represents the row</li>
 * <li>index : cell column index</li>
 * </ul>
 * </dd>
 * 
 * <dt><b>CSS:</b></dt>
 * <dd>.my-treetbl (the containing table)</dd>
 * <dd>.my-treetbl-data (the table data)</dd>
 * <dd>.my-treetbl-item (a row in the table)</dd>
 * <dd>.my-treetbl-tree (the tree itself)</dd>
 * <dd>.my-treetbl-item (a node within the tree)</dd>
 * <dd>.my-treetbl-item-text span (the tree item text)</dd>
 * </dl>
 */
public class TreeTable extends Tree implements BaseTable {

  /**
   * True for a checkbox tree (defaults to false).
   */
  public boolean checkable;

  /**
   * True to display a horizonatal scroll bar when needed (defaults to true).
   */
  public boolean horizontalScroll = true;

  /**
   * True to disable the column context menu (defaults to false).
   */
  public boolean disableColumnContextMenu;

  private TreeTableHeader header;
  private TreeTableColumnModel cm;
  private TreeTableView view;
  private int lastLeft;
  private Size lastSize;
  StyleTemplate styleTemplate = null;

  private DelayedTask scrollTask = new DelayedTask(new Listener() {
    public void handleEvent(BaseEvent be) {
      header.updateSplitBars();
    }
  });

  /**
   * Creates a new single select tree table. A column model must be set before
   * the table is rendered.
   */
  public TreeTable() {

  }

  /**
   * Creates a new tree table with the given column model.
   * 
   * @param cm the tree table column model
   */
  public TreeTable(TreeTableColumnModel cm) {
    this.cm = cm;
  }

  /**
   * Returns the column at the specified index.
   * 
   * @param index the column index
   * @return the column
   */
  public TableColumn getColumn(int index) {
    return cm.getColumn(index);
  }

  /**
   * Returns the column with the given id.
   * 
   * @param id the column id
   * @return the column
   */
  public TableColumn getColumn(String id) {
    return cm.getColumn(id);
  }

  /**
   * Returns the column context menu enabed state.
   * 
   * @return <code>true</code> if enabled, <code>false</code> otherwise.
   */
  public boolean getColumnContextMenu() {
    return !disableColumnContextMenu;
  }

  /**
   * Returns the number of columns contained in the table.
   * 
   * @return the number of columns
   */
  public int getColumnCount() {
    return cm.getColumnCount();
  }

  /**
   * Returns the table's column model.
   * 
   * @return the column model
   */
  public TableColumnModel getColumnModel() {
    return cm;
  }

  /**
   * Returns the tree table's header.
   * 
   * @return the table header
   */
  public TableHeader getTableHeader() {
    if (header == null) {
      header = new TreeTableHeader(this);
    }
    return header;
  }

  /**
   * Returns <code>true</code> if vertical lines are enabled.
   * 
   * @return the vertical line state
   */
  // public boolean getVeritcalLines() {
  // return verticalLines;
  // }
  /**
   * Returns the tree table's view.
   * 
   * @return the view
   */
  protected TreeTableView getView() {
    if (view == null) {
      view = new TreeTableView();
    }
    return view;
  }

  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);
    int type = DOM.eventGetType(event);

    if (type == Event.ONSCROLL) {
      int left = fly(view.getScrollElement()).getScrollLeft();
      if (left == lastLeft) {
        return;
      }
      lastLeft = left;
      header.el.setLeft(-left);
      scrollTask.delay(400);
    }
  }

  /**
   * Recalculates the ui based on the table's current size.
   */
  public void recalculate() {
    onResize(getOffsetWidth(), getOffsetHeight());
  }

  /**
   * Scrolls the item into view.
   * 
   * @param item the item
   */
  public void scrollIntoView(TreeTableItem item) {
    item.el.scrollIntoView(view.getScrollElement(), false);
  }

  /**
   * Sets the tree table's header. Should only be called when providing a custom
   * tree table header. Has no effect if called after the table has been
   * rendered.
   * 
   * @param header the table header
   */
  public void setTableHeader(TreeTableHeader header) {
    if (!isRendered()) {
      this.header = header;
    }
  }

  /**
   * Sets the tree table's view. Provides a way to provide specialized views.
   * table views.
   * 
   * @param view the view
   */
  public void setView(TreeTableView view) {
    this.view = view;
  }

  /**
   * Sorts the tree table using the specified column index.
   * 
   * @param index the column index
   * @param direction the direction to sort (NONE, ASC, DESC)
   */
  public void sort(int index, SortDir direction) {

  }

  protected void doAttachChildren() {
    WidgetHelper.doAttach(header);
  }

  protected void doDetachChildren() {
    WidgetHelper.doDetach(header);
  }

  protected String getRenderedValue(int column, Object value) {
    TreeTableColumn col = (TreeTableColumn) cm.getColumn(column);
    if (col.getRenderer() != null) {
      return col.getRenderer().render(col.getId(), value);
    } else {
      if (value != null) {
        return value.toString();
      }
      return null;
    }
  }

  @Override
  protected void onRender(Element target, int index) {
    setElement(DOM.createDiv());
    setStyleName("my-treetbl");
    el.insertInto(target, index);

    initSelectionModel();
    this.sm.init(this);

    DOM.appendChild(getElement(), root.getElement());
    ((RootTreeTableItem) root).renderChildren();

    cm.setTable(this);
    ((TreeTableItem) root).setValues(new String[getColumnCount()]);

    el.removeChildren();

    header = (TreeTableHeader) getTableHeader();
    header.render(el.dom);
    header.init(this);

    DOM.appendChild(getElement(), header.getElement());

    if (styleTemplate == null) {
      Element style = DOM.createElement("style");
      DOM.setElementProperty(style, "id", getId() + "-cols-style");
      DOM.appendChild(XDOM.getHead(), style);
      styleTemplate = new StyleTemplate(style);
    }

    for (int i = 0, n = cm.getColumnCount(); i < n; i++) {
      TreeTableColumn c = (TreeTableColumn) cm.getColumn(i);
      int w = cm.getWidthInPixels(c.getIndex());
      styleTemplate.set("." + getId() + "-col-" + i, "width:" + w + "px;");
    }

    view = getView();
    view.init(this);
    view.render();

    disableTextSelection(true);

    sinkEvents(Event.ONCLICK | Event.ONDBLCLICK | Event.MOUSEEVENTS | Event.KEYEVENTS
        | Event.ONSCROLL);
  }

  protected void onResize(int width, int height) {
    int h = width;
    int w = height;
    if (lastSize != null) {
      if (lastSize.width == w && lastSize.height == h) {
        return;
      }
    }
    lastSize = new Size(w, h);
    header.resizeColumns(false, true);
  }

  protected void onShowContextMenu(int x, int y) {
    super.onShowContextMenu(x, y);
    getView().clearHoverStyles();
  }

  @Override
  protected void createRootItem() {
    root = new RootTreeTableItem(this);
  }

}