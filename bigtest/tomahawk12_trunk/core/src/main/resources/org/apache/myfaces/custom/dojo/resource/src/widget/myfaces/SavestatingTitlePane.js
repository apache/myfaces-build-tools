/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

dojo.provide("dojo.widget.myfaces.SavestatingTitlePane");
dojo.provide("dojo.widget.html.myfaces.SavestatingTitlePane");

dojo.require("dojo.widget.*");
dojo.require("dojo.widget.HtmlWidget");
dojo.require("dojo.lfx.*");

dojo.widget.html.SavestatingTitlePane = function(){
	dojo.widget.HtmlWidget.call(this);
	this.widgetType = "SavestatingTitlePane";

	this.labelNode="";
	this.labelNodeClass="";
	this.containerNodeClass="";
	this.label="";
	this.persist=true;		// save open positions a cookie
	this.open=1;
	this.templatePath = dojo.uri.dojoUri("src/widget/templates/TitlePane.html");
};

dojo.inherits(dojo.widget.html.SavestatingTitlePane, dojo.widget.HtmlWidget);

dojo.lang.extend(dojo.widget.html.SavestatingTitlePane, {
	isContainer: true,
	postCreate: function() {
		if (this.label) {
			this.labelNode.appendChild(document.createTextNode(this.label));
		}

		if (this.labelNodeClass) {
			dojo.html.addClass(this.labelNode, this.labelNodeClass);
		}	

		if (this.containerNodeClass) {
			dojo.html.addClass(this.containerNode, this.containerNodeClass);
		}	
		if(this.persist)
			this.restoreState();
		if (this.open != 1) {
			dojo.lfx.wipeOut(this.containerNode,0).play();
		}
	},

	onLabelClick: function() {
		if (this.open == 1) {
			dojo.lfx.wipeOut(this.containerNode,250).play();
			this.open=0;
			if(this.persist)
				this.saveState();
		}else {
			dojo.lfx.wipeIn(this.containerNode,250).play();
			this.open=1;
			if(this.persist)
				this.saveState();
		}
	},

	setContent: function(content) {
		this.containerNode.innerHTML=content;
	},

	setLabel: function(label) {
		this.labelNode.innerHTML=label;
	}, 
	
	_getCookieName: function(i) {
		return this.widgetId + "_" + i;
	},
	
	
	restoreState: function () {
		var cookieName = this._getCookieName(0);
		var cookieValue = dojo.io.cookie.getCookie(cookieName);
		if(cookieValue == null) return;
		
		this.open = parseInt(cookieValue);
	},

	saveState: function (){
		var cookieName = this._getCookieName(0);
		dojo.io.cookie.setCookie(cookieName,this.open, null, null, null, null);
	}
	
});

dojo.widget.tags.addParseTreeHandler("dojo:SavestatingTitlePane");
