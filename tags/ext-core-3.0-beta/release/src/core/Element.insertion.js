/*
 * Ext Core Library 3.0 Beta
 * http://extjs.com/
 * Copyright(c) 2006-2009, Ext JS, LLC.
 * 
 * The MIT License
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

/**
 * @class Ext.Element
 */
Ext.Element.addMethods(
function() {
	var GETDOM = Ext.getDom,
		GET = Ext.get,
		DH = Ext.DomHelper;
	
	return {
	    /**
	     * Appends the passed element(s) to this element
	     * @param {String/HTMLElement/Array/Element/CompositeElement} el
	     * @return {Ext.Element} this
	     */
	    appendChild: function(el){        
	        return GET(el).appendTo(this);        
	    },
	
	    /**
	     * Appends this element to the passed element
	     * @param {Mixed} el The new parent element
	     * @return {Ext.Element} this
	     */
	    appendTo: function(el){        
	        GETDOM(el).appendChild(this.dom);        
	        return this;
	    },
	
	    /**
	     * Inserts this element before the passed element in the DOM
	     * @param {Mixed} el The element before which this element will be inserted
	     * @return {Ext.Element} this
	     */
	    insertBefore: function(el){  	          
	        (el = GETDOM(el)).parentNode.insertBefore(this.dom, el);
	        return this;
	    },
	
	    /**
	     * Inserts this element after the passed element in the DOM
	     * @param {Mixed} el The element to insert after
	     * @return {Ext.Element} this
	     */
	    insertAfter: function(el){
	        GETDOM(el).parentNode.insertBefore(this.dom, el.nextSibling);
	        return this;
	    },
	
	    /**
	     * Inserts (or creates) an element (or DomHelper config) as the first child of this element
	     * @param {Mixed/Object} el The id or element to insert or a DomHelper config to create and insert
	     * @return {Ext.Element} The new child
	     */
	    insertFirst: function(el, returnDom){
	        el = el || {};
	        if (Ext.isObject(el) && !el.nodeType && !el.dom) { // dh config
	            return this.createChild(el, this.dom.firstChild, returnDom);
	        } else {
	            el = GETDOM(el);
	            this.dom.insertBefore(el, this.dom.firstChild);
	            return !returnDom ? GET(el) : el;
	        }
	    },
	
	    /**
	     * Replaces the passed element with this element
	     * @param {Mixed} el The element to replace
	     * @return {Ext.Element} this
	     */
	    replace: function(el){
	        el = GET(el);
	        this.insertBefore(el);
	        el.remove();
	        return this;
	    },
	
	    /**
	     * Replaces this element with the passed element
	     * @param {Mixed/Object} el The new element or a DomHelper config of an element to create
	     * @return {Ext.Element} this
	     */
	    replaceWith: function(el){
		    var me = this,
		    	Element = Ext.Element;
	        if (Ext.isObject(el) && !el.nodeType && !el.dom){ // dh config	            
	            el = DH.insertBefore(me.dom, el);
	        } else {
	            el = GETDOM(el);
	            me.dom.parentNode.insertBefore(el, me.dom);
	        }
	        
	        delete El.cache[me.id];
	        Ext.removeNode(me.dom);      
	        me.id = Ext.id(me.dom = el);
	        return Element.cache[me.id] = me;        
	    },
	    
		/**
		 * Creates the passed DomHelper config and appends it to this element or optionally inserts it before the passed child element.
		 * @param {Object} config DomHelper element config object.  If no tag is specified (e.g., {tag:'input'}) then a div will be
		 * automatically generated with the specified attributes.
		 * @param {HTMLElement} insertBefore (optional) a child element of this element
		 * @param {Boolean} returnDom (optional) true to return the dom node instead of creating an Element
		 * @return {Ext.Element} The new child element
		 */
		createChild: function(config, insertBefore, returnDom){
		    config = config || {tag:'div'};
		    return insertBefore ? 
		    	   DH.insertBefore(insertBefore, config, returnDom !== true) :	
		    	   DH[!this.dom.firstChild ? 'overwrite' : 'append'](this.dom, config,  returnDom !== true);
		},
		
		/**
		 * Creates and wraps this element with another element
		 * @param {Object} config (optional) DomHelper element config object for the wrapper element or null for an empty div
		 * @param {Boolean} returnDom (optional) True to return the raw DOM element instead of Ext.Element
		 * @return {HTMLElement/Element} The newly created wrapper element
		 */
		wrap: function(config, returnDom){        
		    var newEl = DH.insertBefore(this.dom, config || {tag: "div"}, !returnDom);
		    newEl.dom ? newEl.dom.appendChild(this.dom) : newEl.appendChild(this.dom);
		    return newEl;
		},
		
		/**
		 * Inserts an html fragment into this element
		 * @param {String} where Where to insert the html in relation to this element - beforeBegin, afterBegin, beforeEnd, afterEnd.
		 * @param {String} html The HTML fragment
		 * @param {Boolean} returnEl (optional) True to return an Ext.Element (defaults to false)
		 * @return {HTMLElement/Ext.Element} The inserted node (or nearest related if more than 1 inserted)
		 */
		insertHtml : function(where, html, returnEl){
		    var el = DH.insertHtml(where, this.dom, html);
		    return returnEl ? Ext.get(el) : el;
		}
	}
}());