/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.form;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.util.Format;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;

/**
 * Basic text field.
 *
 * @param <D> the data type
 */
public class TextField<D> extends Field<D> {

  /**
   * TextField Messages.
   */
  public class TextFieldMessages extends FieldMessages {

    private String minLengthText;
    private String maxLengthText;
    private String blankText = GXT.MESSAGES.textField_blankText();
    private String regexText = "";

    /**
     * Returns the blank text.
     * 
     * @return the blank text
     */
    public String getBlankText() {
      return blankText;
    }

    /**
     * Returns the max length text.
     * 
     * @return the max length text
     */
    public String getMaxLengthText() {
      return maxLengthText;
    }

    /**
     * Returns the min length text.
     * 
     * @return the min length text
     */
    public String getMinLengthText() {
      return minLengthText;
    }

    /**
     * Returns the regex text.
     * 
     * @return the regex text
     */
    public String getRegexText() {
      return regexText;
    }

    /**
     * Sets the error text to display if the allow blank validation fails
     * (defaults to "This field is required").
     * 
     * @param blankText the blank text
     */
    public void setBlankText(String blankText) {
      this.blankText = blankText;
    }

    /**
     * Sets the error text to display if the maximum length validation fails
     * (defaults to "The maximum length for this field is {maxLength}").
     * 
     * @param maxLengthText the max length text
     */
    public void setMaxLengthText(String maxLengthText) {
      this.maxLengthText = maxLengthText;
    }

    /**
     * Sets the error text to display if the minimum length validation fails
     * (defaults to "The minimum length for this field is {minLength}").
     * 
     * @param minLengthText the min length text
     */
    public void setMinLengthText(String minLengthText) {
      this.minLengthText = minLengthText;
    }

    /**
     * The error text to display if {@link #setRegexText} is used and the test
     * fails during validation.
     * 
     * @param regexText the regex text
     */
    public void setRegexText(String regexText) {
      this.regexText = regexText;
    }

  }
  
  private static TextBoxImpl impl = (TextBoxImpl) GWT.create(TextBoxImpl.class);

  

  protected String emptyStyle = "x-form-empty-field";
  
  private boolean password;
  private boolean allowBlank = true;
  private String regex;
  private boolean selectOnFocus = false;
  private int minLength = 0;
  private int maxLength = Integer.MAX_VALUE;
  private Validator validator;
  private DelayedTask validationTask;

  /**
   * Creates a new text field.
   */
  public TextField() {
    messages = new TextFieldMessages();
  }

  /**
   * Returns the field's allow blank state.
   * 
   * @return true if blank values are allowed
   */
  public boolean getAllowBlank() {
    return allowBlank;
  }

  @Override
  public TextFieldMessages getMessages() {
    return (TextFieldMessages) messages;
  }

  /**
   * Returns the minimum length.
   * 
   * @return the min length
   */
  public int getMinLength() {
    return minLength;
  }

  /**
   * Returns the field's regex value.
   * 
   * @return the regex value
   */
  public String getRegex() {
    return regex;
  }

  /**
   * Returns the select of focus state.
   * 
   * @return true if select on focus is enabled
   */
  public boolean getSelectOnFocus() {
    return selectOnFocus;
  }

  /**
   * Returns the field's validator instance.
   * 
   * @return the validator
   */
  public Validator getValidator() {
    return validator;
  }

  /**
   * Returns true if the field is a password field.
   * 
   * @return that password state
   */
  public boolean isPassword() {
    return password;
  }

  /**
   * Selects text in the field.
   * 
   * @param start the index where the selection should start.
   * @param length the number of characters to be selected
   */
  public void select(int start, int length) {
    impl.setSelectionRange(getElement(), start, length);
  }

  /**
   * Selects all the text.
   */
  public void selectAll() {
    int length = getRawValue().length();
    if (length > 0) {
      select(0, length);
    }
  }

  /**
   * Sets whether a field is value when its value length = 0 (default to true).
   * 
   * @param allowBlank true to allow blanks, false otherwise
   */
  public void setAllowBlank(boolean allowBlank) {
    this.allowBlank = allowBlank;
  }

  /**
   * Sets the maximum input field length.
   * 
   * @param maxLength the max length
   */
  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  /**
   * Minimum input field length required (defaults to 0).
   * 
   * @param minLength the min length
   */
  public void setMinLength(int minLength) {
    this.minLength = minLength;
  }

  /**
   * True to create the text field as a password input (defaults to false,
   * pre-render).
   * 
   * @param password the password state
   */
  public void setPassword(boolean password) {
    assertPreRender();
    this.password = password;
  }

  /**
   * Sets regular expression to be tested against the field value during
   * validation. If available, this regex will be evaluated only after the basic
   * validators all return true. If the test fails, the field will be marked
   * invalid using the regex error message.
   * 
   * @param regex the regex expression
   */
  public void setRegex(String regex) {
    this.regex = regex;
  }

  /**
   * True to automatically select any existing field text when the field
   * receives input focus (defaults to false).
   * 
   * @param selectOnFocus true to focus
   */
  public void setSelectOnFocus(boolean selectOnFocus) {
    this.selectOnFocus = selectOnFocus;
  }

  /**
   * Sets the validator instance to be called during field validation. It will
   * be called only after the basic validators all return true, and will be
   * passed the current field value and expected to return <code>null</code>
   * if the value is valid or a string error message if invalid. Default value
   * is <code>null</code>.
   * 
   * @param validator the validator
   */
  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  @Override
  public void setValue(D value) {
    if (rendered && emptyText != null && !emptyText.equals("")) {
      removeStyleName(emptyStyle);
    }
    super.setValue(value);
    applyEmptyText();
  }

  protected void applyEmptyText() {
    if (rendered && !password && emptyText != null && getRawValue().length() < 1) {
      setRawValue(emptyText);
      getInputEl().addStyleName(emptyStyle);
    }
  }

  @Override
  protected void onBlur(ComponentEvent be) {
    super.onBlur(be);
    applyEmptyText();
  }

  @Override
  protected void onFocus(ComponentEvent be) {
    super.onFocus(be);
    if (emptyText != null) {
      String v = getInputEl().getValue();
      if (emptyText.equals(v)) {
        setRawValue("");
      }
      getInputEl().removeStyleName(emptyStyle);
    }
    if (selectOnFocus) {
      selectAll();
    }
  }

  @Override
  protected void onKeyPress(FieldEvent fe) {
    super.onKeyPress(fe);
    if (validationTask != null) {
      validationTask.delay(validationDelay);
    }
  }

  @Override
  protected void onRender(Element target, int index) {
    if (el() == null) {
      if (password) {
        setElement(DOM.createInputPassword());

      } else {
        setElement(DOM.createInputText());
      }

      el().insertInto(target, index);
    }

    super.onRender(target, index);

    if (autoValidate) {
      validationTask = new DelayedTask(new Listener() {
        public void handleEvent(BaseEvent be) {
          validate();
        }
      });
    }

    getInputEl().addStyleName("x-form-text");

    applyEmptyText();
  }

  @Override
  protected boolean validateValue(String value) {
    int length = value.length();
    if (value.length() < 1 || value.equals("")) {
      if (allowBlank) {
        clearInvalid();
        return true;
      } else {
        markInvalid(getMessages().getBlankText());
        return false;
      }
    }
    if (length < minLength) {
      String error = "";
      if (getMessages().getMinLengthText() == null) {
        error = GXT.MESSAGES.textField_minLengthText(minLength);
      } else {
        error = Format.substitute(getMessages().getMinLengthText(), minLength);
      }
      markInvalid(error);
      return false;
    }

    if (length > maxLength) {
      String error = "";
      if (getMessages().getMaxLengthText() == null) {
        error = GXT.MESSAGES.textField_maxLengthText(maxLength);
      } else {
        error = Format.substitute(getMessages().getMaxLengthText(), maxLength);
      }
      markInvalid(error);
      return false;
    }

    if (validator != null) {
      String msg = validator.validate(this, value);
      if (msg != null) {
        markInvalid(msg);
        return false;
      }
    }

    if (regex != null && !value.matches(regex)) {
      markInvalid(getMessages().getRegexText());
      return false;
    }

    return true;
  }

}