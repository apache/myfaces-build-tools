dojo.provide("extensions.widget.TableSuggest");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.ComboBox");
dojo.require("dojo.lang.common");

dojo.widget.defineWidget(
	"extensions.widget.TableSuggest",
	dojo.widget.ComboBox,
	{
        templatePath: dojo.uri.dojoUri("../dojoextensions.ResourceLoader/templates/TableSuggest.html"),
        templateCssPath: dojo.uri.dojoUri("../dojoextensions.ResourceLoader/templates/TableSuggest.css"),
        startRequest: 1,
        popupId: null,
        popupStyleClass: "dojoPopupContainer dojoComboBoxOptions",
		tableStyleClass: "dojoTableSuggest",
		comboBoxStyleClass: "dojoComboBox",
		rowStyleClass: "dojoComboBoxItem",
		evenRowStyleClass: "dojoComboBoxItemEven",
		oddRowStyleClass: "dojoComboBoxItemOdd",
		hoverRowStyleClass: "dojoComboBoxItemHighlight",
        textInputId: "",

        fillInTemplate: function(args, frag) {
            //Insert the textInputNode
            this.textInputNode = dojo.byId(this.textInputId);
            this.textInputNode.parentNode.removeChild(this.textInputNode);
            this.domNode.insertBefore(this.textInputNode, this.downArrowNode);

            //Super...
            dojo.widget.ComboBox.prototype.fillInTemplate.call(this, args, frag);

            //Connect Events
            var that = this;
            dojo.event.browser.addListener(this.textInputNode, "key", function(evt){ that["_handleKeyEvents"](dojo.event.browser.fixEvent(evt, this)) }, false, true);
            dojo.event.browser.addListener(this.textInputNode, "keyUp", function(evt){ that["onKeyUp"](dojo.event.browser.fixEvent(evt, this)) }, false, true);
            dojo.event.browser.addListener(this.textInputNode, "compositionEnd", function(evt){ that["compositionEnd"](dojo.event.browser.fixEvent(evt, this)) }, false, true);
            dojo.event.browser.addListener(this.textInputNode, "onResize", function(evt){ that["onResize"](dojo.event.browser.fixEvent(evt, this)) }, false, true);

            if (this.popupId) {
				this.popupWidget.domNode.id = this.popupId;
			}
            this.popupWidget.domNode.className = this.popupStyleClass;
            this.textInputNode.className = this.comboBoxStyleClass;
            this.downArrowNode.className = this.comboBoxStyleClass;
            if (this.textInputNode.disabled == true) {
                this.disable();
            }
        },

        _openResultList: function(results) {
            if (this.disabled){
				return;
			}
			this._clearResultList();
			if(results[1].length === 0){
				this._hideResultList();
			}

			var headers = results[0];
			var values = results[1];

			if(	(this.autoComplete) &&
				(results.length) &&
				(!this._prev_key_backspace) &&
				(this.textInputNode.value.length > 0) ) {
				var cpos = this._getCaretPos(this.textInputNode);
				// only try to extend if we added the last character at the end of the input
				if((cpos+1) > this.textInputNode.value.length){
					// only add to input node as we would overwrite Capitalisation of chars
					this.textInputNode.value += this._getLabelForText(values[0], this.textInputNode.id).substr(cpos);
					// build a new range that has the distance from the earlier
					// caret position to the end of the first string selected
					this._setSelectedRange(this.textInputNode, cpos, this.textInputNode.value.length);
				}
			}

			//Build Header
			var table = document.createElement("table");
			table.className = this.tableStyleClass;
			var thead = document.createElement("thead");
			var headerLine = document.createElement("tr");
			dojo.lang.forEach(headers, function(heading) {
                if (!heading) {
                    return;
                }
                var headerColumn = document.createElement("th");
				headerColumn.appendChild(document.createTextNode(heading));
				headerLine.appendChild(headerColumn);
			});
			thead.appendChild(headerLine);
			table.appendChild(thead);

			//Build value lines
			var tbody = document.createElement("tbody");
			var even = true;
			while(values.length) {
				var value = values.shift();
				if(value) {
					var contentLine = document.createElement("tr");
					contentLine.className = this.rowStyleClass + " "+((even) ? this.evenRowStyleClass : this.oddRowStyleClass);
					contentLine.suggestData = [];
					even = (!even);
					dojo.lang.forEach(value, function(column) {
                        if (!column) {
                            return;
                        }
                        contentLine.suggestData.push(column);
                        var lineColumn = document.createElement("td");
						lineColumn.appendChild(document.createTextNode(column.label));
						contentLine.appendChild(lineColumn);
					});
					tbody.appendChild(contentLine);
				}
			}
            
            // show our list (only if we have content, else nothing)
			table.appendChild(tbody);
			this.optionsListNode.appendChild(table);
			this._showResultList();
		},

		_showResultList: function() {
			// Our dear friend IE doesnt take max-height so we need to calculate that on our own every time
			//firstChild is the table, firstChild is the thead, nextSibling is the tbody
			var children = this.optionsListNode.firstChild.firstChild.nextSibling.childNodes;
            if(children.length){
                var visibleCount = Math.min(children.length,this.maxListLength);

				with(this.optionsListNode.style)
				{
					display = "";
					if (visibleCount == children.length){
						//no scrollbar is required, so unset height to let browser calcuate it,
						//as in css, overflow is already set to auto
						height = "";
                        width = "";
                    } else {
						//If the height of trs can't be measured (results in 0) use 20 as default value --> Safari
						height = (visibleCount + 1) * Math.max(dojo.html.getMarginBox(children[0]).height, 20) +"px";
                        //because of the vertical scrollbar in IE takes away the needed horicontal space we add 20 px
                        dojo.lang.setTimeout(function(node) {
                            node.style.width = (dojo.html.getMarginBox(node.firstChild).width + 20) + "px";
                        }, 50, this.optionsListNode);
                    }
				}
                this.popupWidget.open(this.domNode, this, this.downArrowNode);
			}else{
                this._hideResultList();
			}
		},

		_highlightNextOption: function() {
			//If table not inserted... wait
			if (!this.optionsListNode.firstChild) {
				return;
			}

			//parentNode = tbody, parentNode = table, parentNode = popup
			if((!this._highlighted_option) || (!this._highlighted_option.parentNode) || (!this._highlighted_option.parentNode.parentNode) || (!this._highlighted_option.parentNode.parentNode.parentNode)){
				//firstChild is the table, firstChild is the thead, nextSibling is the tbody and then firstChild is the first row
				this._focusOptionNode(this.optionsListNode.firstChild.firstChild.nextSibling.firstChild);
			}else if(this._highlighted_option.nextSibling){
				this._focusOptionNode(this._highlighted_option.nextSibling);
			}
			dojo.html.scrollIntoView(this._highlighted_option);
		},

		_itemMouseOver: function(/*Event*/ evt){
			if (evt.target === this.optionsListNode || evt.target.tagName !== "TD" || evt.target.parentNode.tagName !== "TR"){ return; }
			this._focusOptionNode(evt.target.parentNode);
			dojo.html.addClass(this._highlighted_option, this.hoverRowStyleClass);
		},

        _focusOptionNode: function(/*DomNode*/ node){
			// summary: does the actual highlight
			if(this._highlighted_option != node){
				this._blurOptionNode();
				this._highlighted_option = node;
				dojo.html.addClass(this._highlighted_option, this.hoverRowStyleClass);
			}
		},

		_blurOptionNode: function(){
			// sumary: removes highlight on highlighted
			if(this._highlighted_option){
				dojo.html.removeClass(this._highlighted_option, this.hoverRowStyleClass);
				this._highlighted_option = null;
			}
		},

        _handleKeyEvents: function(evt) {
            //ALWAYS stop the event on ENTER! Never submit the form by pressing enter here!
            if (evt.key == dojo.event.browser.keys.KEY_ENTER) {
                dojo.event.browser.stopEvent(evt);
            }
            dojo.widget.ComboBox.prototype._handleKeyEvents.call(this, evt);
        },

        _selectOption: function(evt) {
            var tgt = null;
			if(!evt){
				evt = { target: this._highlighted_option };
			}

			if(!dojo.html.isDescendantOf(evt.target, this.optionsListNode)){
				//User entered a value manually and pressed ENTER or TAB --> autocomplete

				// if the input is empty do nothing
				if(!this.textInputNode.value.length){
					return;
				}

                if (this.optionsListNode.firstChild && this.optionsListNode.firstChild.firstChild
                        && this.optionsListNode.firstChild.firstChild.nextSibling
                        && this.optionsListNode.firstChild.firstChild.nextSibling.firstChild) {
                    tgt = this.optionsListNode.firstChild.firstChild.nextSibling.firstChild;
                }
                if(!tgt || !this._isInputEqualToResult(this._getLabelForText(tgt.suggestData, this.textInputNode.id))) {
					return;
				}
			}else{
				// otherwise the user has accepted the autocompleted value
				if (evt.target.tagName === "TR") {
					tgt = evt.target;
				} else if (evt.target.tagName === "TD" && evt.target.parentNode.tagName === "TR") {
					tgt = evt.target.parentNode;
				}

				if (tgt === null) {
					return;
				}
			}

            dojo.lang.forEach(tgt.suggestData, function(column) {
                if (column.forText) {
					if (column.forText === this.textInputNode.id) {
						this.setAllValues(column.label, column.label);
                        if (this.textInputNode.onchange) {
                            this.textInputNode.onchange();
                        }
                    }
					else {
						var element = dojo.byId(column.forText);
						if (element) {
							if (element.tagName === "INPUT") {
								element.value = column.label;
                                if (element.onchange)
                                {
                                    element.onchange();
                                }
                            }
							else {
								element.innerHTML = column.label;
							}
						}
					}
				}
				else if (column.forValue) {
					var element = dojo.byId(column.forValue);
					for (i = 0; i < element.options.length; i++)
                    {
						if (element.options[i].value == column.value) {
							element.options[i].selected = true;
						}
					}
				}
			}, this);

			if(!evt.noHide){
				this._hideResultList();
				this._setSelectedRange(this.textInputNode, 0, null);
			}
			this._tryFocus();
		},

		_getLabelForText: function(suggestData, forId) {
			if (!suggestData) {
				return null;
			}
			var returnValue = null;
			dojo.lang.forEach(suggestData, function(column) {
				if (column.forText && column.forText === forId) {
					returnValue = column.label;
				}
			});
			return returnValue;
		},

		_isInputEqualToResult: function(result){
			if (!result) {
				return false;
			}
			var input = this.textInputNode.value;
			if(!this.dataProvider.caseSensitive){
				input = input.toLowerCase();
				result = result.toLowerCase();
			}
			return (input == result);
		},

		_startSearchFromInput: function(){
			if (this.textInputNode.value.length < this.startRequest) {
				return;
			}
			this._startSearch(this.textInputNode.value);
		}
	}
);
