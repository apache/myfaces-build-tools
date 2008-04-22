dojo.provide("extensions.ComboBox");
dojo.require("dojo.widget.html.ComboBox");
dojo.require("dojo.widget.*");

dojo.lang.extend(dojo.widget.html.ComboBox, {
    original_startSearchFromInput: dojo.widget.html.ComboBox.prototype.startSearchFromInput,
    original_showResultList: dojo.widget.html.ComboBox.prototype.showResultList,
    original_hideResultList: dojo.widget.html.ComboBox.prototype.hideResultList,

    templatePath: dojo.uri.dojoUri("../dojoextensions.ResourceLoader/templates/HtmlComboBox.html"),

    showResultList: function(results) {
        this.hideThrobber();
        this.original_showResultList.call(this, results);
    },

    hideResultList: function() {
        this.hideThrobber();
        this.original_hideResultList.call(this);
    },

    startSearchFromInput: function() {
        if (this.textInputNode.value != "")
            this.showThrobber();
        this.original_startSearchFromInput.call(this);
    },

    showThrobber: function() {
        this.downArrowNode.style.backgroundPosition = '-18px center';
    },

    hideThrobber: function() {
        this.downArrowNode.style.backgroundPosition = '2px center';
    }
});
