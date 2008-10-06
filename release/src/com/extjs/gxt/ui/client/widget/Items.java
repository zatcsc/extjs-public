/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget;

import java.util.ArrayList;
import java.util.List;

/**
 * A selection of items in a container. The selection can be specified with an
 * index, a range, a single item, an array, and a list. Allows
 * 
 * @param <T> the child type
 */
public class Items<T extends Component> {
  private int start = -1, end = -1;
  private List<T> items = new ArrayList<T>();

  /**
   * Creates a items instance with a single item.
   * 
   * @param index the index of the item
   */
  public Items(int index) {
    this(index, index+1);
  }

  /**
   * Creates a items instance with a range "start <= index < end"
   * 
   * @param start the start index
   * @param end the end index
   */
  public Items(int start, int end) {
    if (end > start) {
      this.start = start;
      this.end = end;
    } else {
      this.start = end;
      this.end = start;
    }
  }

  /**
   * Creates a items instance with a list.
   * 
   * @param items the list of items
   */
  public Items(List<T> items) {
    this.items = items;
  }

  /**
   * Createa a items instance with 1 to many items.
   * 
   * @param item the varargs items
   */
  public Items(T... item) {
    if (item != null) {
      for (int i = 0; i < item.length; i++) {
        this.items.add(item[i]);
      }
    }
  }

  /**
   * Returns the first matching item from the specified container.
   * 
   * @param c the container
   * @return the matching item
   */
  public T getItem(Container c) {
    List<T> match = getItems(c);
    if (match.size() > 0) {
      return (T) getItems(c).get(0);
    }
    return null;
  }

  /**
   * Returns the matching items from the specified container.
   * 
   * @param c the container
   * @return the selected items
   */
  public List<T> getItems(Container c) {
    List<T> temp = new ArrayList<T>();
    if (start > -1) {
      for (int i = start; i < end; i++) {
        temp.add((T) c.getItem(i));
      }
      return temp;
    }
    return items;
  }

  /**
   * Returns true if there is a single selected item.
   * 
   * @return true for single, false otherwise
   */
  public boolean isSingle() {
    return ((end-start) == 1 || items.size() == 1);
  }
}
