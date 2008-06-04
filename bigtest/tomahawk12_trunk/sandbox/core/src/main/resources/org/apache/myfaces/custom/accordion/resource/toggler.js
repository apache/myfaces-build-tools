//toggler.js

Rico.Toggler = Class.create();

Rico.Toggler.prototype = {

    initialize: function(container, options)
    {
        this.container = $(container);
        this.accordionTabs = new Array();
        this.setOptions(options);
        this._attachBehaviors();

        this.container.style.borderBottom = '1px solid ' + this.options.borderColor;

        // set the initial visual state...
        for (var i = 0; i < this.accordionTabs.length; i++)
        {
            this.accordionTabs[i].collapse();
            this.accordionTabs[i].content.style.display = 'none';
            if (this.accordionTabs[i].closedContent)
            {
                this.accordionTabs[i].closedContent.style.display = '';
                this.accordionTabs[i].closedContent.style.height = this.options.closedPanelHeight + "px";

            }
        }
    },

    setOptions: function(options)
    {
        this.options = {
            expandedBg          : '#63699c',
            hoverBg             : '#63699c',
            collapsedBg         : '#6b79a5',
            expandedTextColor   : '#ffffff',
            expandedFontWeight  : 'bold',
            hoverTextColor      : '#ffffff',
            collapsedTextColor  : '#ced7ef',
            collapsedFontWeight : 'normal',
            hoverTextColor      : '#ffffff',
            borderColor         : '#1f669b',
            panelHeight         : 200,
            closedPanelHeight   : 50,
            useRealHeight       : true,
            onHideTab           : null,
            onShowTab           : null
        }.extend(options || {});
    },

    showTabByIndex: function(anIndex, animate)
    {
        var doAnimate = arguments.length == 1 ? true : animate;
        this.showTab(this.accordionTabs[anIndex], doAnimate);
    },

    toggleTab: function(accordionTab, animate)
    {

        var doAnimate = arguments.length == 1 ? true : animate;

        if (accordionTab.expanded)
        {
            this._collapseTab(accordionTab, doAnimate);
        }
        else
        {
            this._expandTab(accordionTab, doAnimate);
        }
    },

    _collapseTab: function(accordionTab, doAnimate)
    {
        if (this.options.onHideTab)
            this.options.onHideTab(accordionTab);

        accordionTab.showCollapsed();
        var accordion = this;

        accordionTab.content.style.height = (this.options.panelHeight - 1) + 'px';

        if (doAnimate)
        {
            if (!accordionTab.closedContent)
            {
                new Effect.TogglerSize(accordionTab.content,
                        1,
                        100, 10,
                { complete: function()
                {
                    accordion.showTabDone(accordionTab)
                } });
            }
            else
            {
                new Effect.AccordionSize(accordionTab.content,
                        accordionTab.closedContent,
                        1,
                        this.options.closedPanelHeight,
                        100, 10,
                { complete: function()
                {
                    accordion.showTabDone(accordionTab)
                } });
            }
        }
        else
        {
            accordionTab.content.style.height = "1px";

            if (accordionTab.closedContent)
            {
                accordionTab.closedContent.style.height = this.options.closedPanelHeight + "px";
            }
            this.showTabDone(accordionTab);
        }
    },

    _expandTab: function(accordionTab, doAnimate)
    {
        var accordion = this;

        accordionTab.content.style.display = '';
        accordionTab.titleBar.style.fontWeight = this.options.expandedFontWeight;

        if (accordionTab.closedContent)
        {
            accordionTab.closedContent.style.height = (this.options.closedPanelHeight - 1) + 'px';
        }

        if (doAnimate)
        {
            if (!accordionTab.closedContent)
            {
                new Effect.TogglerSize(accordionTab.content,
                        this.options.panelHeight,
                        100, 10,
                { complete: function()
                {
                    accordionTab.showExpanded();
                    if (accordionTab.accordion.options.onShowTab)
                        accordionTab.accordion.options.onShowTab(accordionTab);

                    if(accordionTab.accordion.options.useRealHeight)
                        accordionTab.content.style.height = "";
                } });
            }
            else
            {
                new Effect.AccordionSize(accordionTab.closedContent,
                        accordionTab.content,
                        1,
                        this.options.panelHeight,
                        100, 10,
                { complete: function()
                {
                    accordionTab.showExpanded();
                    if (accordionTab.accordion.options.onShowTab)
                        accordionTab.accordion.options.onShowTab(accordionTab);

                    if(accordionTab.accordion.options.useRealHeight)
                        accordionTab.content.style.height = "";
                } });
            }

        }
        else
        {
            if(accordionTab.accordion.options.useRealHeight)
                accordionTab.content.style.height = "";
            else
                accordionTab.content.style.height = this.options.panelHeight + "px";

            if (accordionTab.closedContent)
            {
                accordionTab.closedContent.style.height = "1px";
            }
            accordionTab.showExpanded();
            if (this.options.onShowTab)
                this.options.onShowTab(accordionTab);

        }
    },

    showTabDone: function(collapsedTab)
    {
        collapsedTab.content.style.display = 'none';
    },

    _attachBehaviors: function()
    {
        var panels = this._getDirectChildrenByTag(this.container, 'DIV');
        for (var i = 0; i < panels.length; i++)
        {

            var tabChildren = this._getDirectChildrenByTag(panels[i], 'DIV');
            var tabTitleBar = null;
            var tabClosedContentBox = null;
            var tabContentBox = null;

            if (tabChildren.length == 2)
            {
                tabTitleBar = tabChildren[0];
                tabContentBox = tabChildren[1];
            }
            else if (tabChildren.length == 3)
            {
                tabTitleBar = tabChildren[0];
                tabClosedContentBox = tabChildren[1];
                tabContentBox = tabChildren[2];
            }
            else
            {
                continue;
                // unexpected
            }

            this.accordionTabs.push(new Rico.Toggler.Tab(this, tabTitleBar,
                    tabClosedContentBox, tabContentBox));
        }
    },

    _getDirectChildrenByTag: function(e, tagName)
    {
        var kids = new Array();
        var allKids = e.childNodes;
        for (var i = 0; i < allKids.length; i++)
            if (allKids[i] && allKids[i].tagName && allKids[i].tagName == tagName)
                kids.push(allKids[i]);
        return kids;
    }

};

Rico.Toggler.Tab = Class.create();

Rico.Toggler.Tab.prototype = {

    initialize: function(accordion, titleBar, closedContent, content)
    {
        this.accordion = accordion;
        this.titleBar = titleBar;
        this.content = content;
        this.closedContent = closedContent;
        this._attachBehaviors();
    },

    collapse: function()
    {
        this.showCollapsed();
        this.content.style.height = "1px";
    },

    showCollapsed: function()
    {
        this.expanded = false;
        this.titleBar.style.backgroundColor = this.accordion.options.collapsedBg;
        this.titleBar.style.color = this.accordion.options.collapsedTextColor;
        this.titleBar.style.fontWeight = this.accordion.options.collapsedFontWeight;
        this.content.style.overflow = "hidden";
        if (this.closedContent)
        {
            this.closedContent.style.overflow = "visible";
        }
    },

    showExpanded: function()
    {
        this.expanded = true;
        this.titleBar.style.backgroundColor = this.accordion.options.expandedBg;
        this.titleBar.style.color = this.accordion.options.expandedTextColor;
        this.content.style.overflow = "visible";
        if (this.closedContent)
        {
            this.closedContent.style.overflow = "hidden";
        }
    },

    titleBarClicked: function(e)
    {
        this.accordion.toggleTab(this);
    },

    hover: function(e)
    {
        this.titleBar.style.backgroundColor = this.accordion.options.hoverBg;
        this.titleBar.style.color = this.accordion.options.hoverTextColor;
    },

    unhover: function(e)
    {
        if (this.expanded)
        {
            this.titleBar.style.backgroundColor = this.accordion.options.expandedBg;
            this.titleBar.style.color = this.accordion.options.expandedTextColor;
        }
        else
        {
            this.titleBar.style.backgroundColor = this.accordion.options.collapsedBg;
            this.titleBar.style.color = this.accordion.options.collapsedTextColor;
        }
    },

    _attachBehaviors: function()
    {
        this.content.style.border = "1px solid " + this.accordion.options.borderColor;
        this.content.style.borderTopWidth = "0px";
        this.content.style.borderBottomWidth = "0px";
        this.content.style.margin = "0px";

        if (this.closedContent)
        {
            this.closedContent.style.border = "1px solid " + this.accordion.options.borderColor;
            this.closedContent.style.borderTopWidth = "0px";
            this.closedContent.style.borderBottomWidth = "0px";
            this.closedContent.style.margin = "0px";
        }

        this.titleBar.onclick = this.titleBarClicked.bindAsEventListener(this);
        this.titleBar.onmouseover = this.hover.bindAsEventListener(this);
        this.titleBar.onmouseout = this.unhover.bindAsEventListener(this);
    }

};

Effect.TogglerSize = Class.create();

Effect.TogglerSize.prototype = {

    initialize: function(elem, end, duration, steps, options)
    {
        this.elem = $(elem);
        this.end = end;
        this.duration = duration;
        this.steps = steps;
        this.options = arguments[4] || {};

        this.togglerSize();
    },

    togglerSize: function()
    {

        if (this.isFinished())
        {
            // just in case there are round errors or such...
            this.elem.style.height = this.end + "px";

            if (this.options.complete)
                this.options.complete(this);
            return;
        }

        if (this.timer)
            clearTimeout(this.timer);

        var stepDuration = Math.round(this.duration / this.steps) ;

        var diff = 0;

        if (this.steps > 0)
        {
            diff = (this.end - parseInt(this.elem.offsetHeight)) / this.steps;
        }

        this.resizeBy(diff);

        this.duration -= stepDuration;
        this.steps--;

        this.timer = setTimeout(this.togglerSize.bind(this), stepDuration);
    },

    isFinished: function()
    {
        return this.steps <= 0;
    },

    resizeBy: function(diff)
    {

        var elemHeight = this.elem.offsetHeight;
        var intDiff = parseInt(diff);
        if (diff != 0)
        {
            this.elem.style.height = (elemHeight + intDiff) + "px";
        }
    }

};