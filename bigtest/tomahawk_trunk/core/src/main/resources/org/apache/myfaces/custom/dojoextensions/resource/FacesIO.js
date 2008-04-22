dojo.provide("extensions.FacesIO");

dojo.io.FacesTransport = new function() {
    this.canHandle = function(kwArgs) {
        return this.isClientStateSaving() && dojo.io.XMLHTTPTransport.canHandle(kwArgs);
    }

    this.bind = function(request) {
        if (this.isClientStateSaving()) {
            request.method = "post";
            this.addJsfState(request);
        }
        return dojo.io.XMLHTTPTransport.bind(request);
    }

    this.isClientStateSaving = function() {
        return dojo.byId("javax.faces.ViewState");
    }

    this.addJsfState = function(request) {
        request.content = request.content || {};
        this.addInputValue(request.content, "javax.faces.ViewState");
    }

    this.addInputValue = function (content, inputName) {
        var control = dojo.byId(inputName);
        if (!control || !control.value)
            return;
        content[inputName] = control.value;
    }

    dojo.io.transports.addTransport("FacesTransport");
    dojo.io.transports.reverse();
}