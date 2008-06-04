dojo.provide("extensions.widget.InputSuggestAjax");
dojo.require("dojo.widget.*");
dojo.require("dojo.widget.ComboBox");
dojo.require("dojo.lang.common");

dojo.widget.defineWidget(
	"extensions.widget.InputSuggestAjax",
	dojo.widget.ComboBox,
	{
        templatePath: dojo.uri.dojoUri("../dojoextensions.ResourceLoader/templates/TableSuggest.html"),
        textInputId: "",

        fillInTemplate: function(args, frag) {
            //Insert the textInputNode
            this.textInputNode = dojo.byId(this.textInputId);
            this.textInputNode.className = "dojoComboBox";
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

            if (this.textInputNode.disabled == true) {
                this.disable();
            }
        },

        setAllValues: function(a, b) {
            //Super...
            dojo.widget.ComboBox.prototype.setAllValues.call(this, a, b);
            if (this.textInputNode.onchange) {
                this.textInputNode.onchange();
            }
        }
    }
);
