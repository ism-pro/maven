/**
 * PrimeFaces ColumnToggler Widget
 */
/*
 PrimeFaces.widget.ColumnToggler = PrimeFaces.widget.DeferredWidget.extend({
 init: function (cfg) {
 this._super(cfg);
 this.table = PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.datasource);
 this.trigger = PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.trigger);
 this.tableId = this.table.attr('id');
 this.thead = $(PrimeFaces.escapeClientId(this.tableId) + '_head');
 this.tbody = $(PrimeFaces.escapeClientId(this.tableId) + '_data');
 this.tfoot = $(PrimeFaces.escapeClientId(this.tableId) + '_foot');
 this.visible = false;
 
 this.render();
 this.bindEvents();
 },
 render: function () {
 this.columns = this.thead.find('> tr > th:not(.ui-static-column)');
 this.panel = $('<div></div>').attr('id', this.cfg.id).addClass('ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all')
 .append('<ul class="ui-columntoggler-items"></ul>').appendTo(document.body);
 
 this.itemContainer = this.panel.children('ul');
 
 for (var i = 0; i < this.columns.length; i++) {
 var column = this.columns.eq(i),
 hidden = column.hasClass('ui-helper-hidden'),
 boxClass = hidden ? 'ui-chkbox-box ui-widget ui-corner-all ui-state-default' : 'ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active',
 iconClass = (hidden) ? 'ui-chkbox-icon ui-icon ui-icon-blank' : 'ui-chkbox-icon ui-icon ui-icon-check';
 
 $('<li class="ui-columntoggler-item">' +
 '<div class="ui-chkbox ui-widget">' +
 '<div class="' + boxClass + '"><span class="' + iconClass + '"></span></div>' +
 '</div>'
 + '<label>' + column.children('.ui-column-title').text() + '</label></li>').data('column', column.attr('id')).appendTo(this.itemContainer);
 }
 
 this.closer = $('<a href="#" class="ui-columntoggler-close"><span class="ui-icon ui-icon-close"></span></a>')
 .attr("aria-label", "columntoggler.CLOSE").prependTo(this.panel);
 
 if (this.panel.outerHeight() > 200) {
 this.panel.height(200);
 }
 this.hide();
 },
 bindEvents: function () {
 var $this = this,
 hideNS = 'mousedown.' + this.id,
 resizeNS = 'resize.' + this.id;
 
 //trigger
 this.trigger.off('click.ui-columntoggler').on('click.ui-columntoggler', function (e) {
 if ($this.visible)
 $this.hide();
 else
 $this.show();
 });
 
 //Link
 this.itemLink.click(function () {
 alert('Avant');
 $this.hide();
 
 });
 
 
 //checkboxes
 this.itemContainer.find('> .ui-columntoggler-item > .ui-chkbox > .ui-chkbox-box').on('mouseover.columnToggler', function () {
 var item = $(this);
 if (!item.hasClass('ui-state-active')) {
 item.addClass('ui-state-hover');
 }
 })
 .on('mouseout.columnToggler', function () {
 $(this).removeClass('ui-state-hover');
 })
 .on('click.columnToggler', function (e) {
 $this.toggle($(this));
 e.preventDefault();
 });
 
 //labels
 this.itemContainer.find('> .ui-columntoggler-item > label').on('click.selectCheckboxMenu', function (e) {
 $this.toggle($(this).prev().children('.ui-chkbox-box'));
 PrimeFaces.clearSelection();
 e.preventDefault();
 });
 
 //hide overlay when outside is clicked
 $(document.body).off(hideNS).on(hideNS, function (e) {
 if (!$this.visible) {
 return;
 }
 
 //do nothing on trigger mousedown
 var target = $(e.target);
 if ($this.trigger.is(target) || $this.trigger.has(target).length) {
 return;
 }
 
 //hide the panel and remove focus from label
 var offset = $this.panel.offset();
 if (e.pageX < offset.left ||
 e.pageX > offset.left + $this.panel.width() ||
 e.pageY < offset.top ||
 e.pageY > offset.top + $this.panel.height()) {
 
 $this.hide();
 }
 });
 
 //Realign overlay on resize
 $(window).off(resizeNS).on(resizeNS, function () {
 if ($this.visible) {
 $this.alignPanel();
 }
 });
 },
 toggle: function (chkbox) {
 if (chkbox.hasClass('ui-state-active')) {
 this.uncheck(chkbox);
 } else {
 this.check(chkbox);
 }
 },
 check: function (chkbox) {
 chkbox.addClass('ui-state-active').removeClass('ui-state-hover').children('.ui-chkbox-icon').addClass('ui-icon-check').removeClass('ui-icon-blank');
 
 var index = $(document.getElementById(chkbox.closest('li.ui-columntoggler-item').data('column'))).index() + 1,
 columnHeader = this.thead.children('tr').find('th:nth-child(' + index + ')');
 
 columnHeader.removeClass('ui-helper-hidden');
 $(PrimeFaces.escapeClientId(columnHeader.attr('id') + '_clone')).removeClass('ui-helper-hidden');
 this.tbody.children('tr').find('td:nth-child(' + index + ')').removeClass('ui-helper-hidden');
 this.tfoot.children('tr').find('td:nth-child(' + index + ')').removeClass('ui-helper-hidden');
 
 this.fireToggleEvent(true, (index - 1));
 },
 uncheck: function (chkbox) {
 chkbox.removeClass('ui-state-active').children('.ui-chkbox-icon').addClass('ui-icon-blank').removeClass('ui-icon-check');
 
 var index = $(document.getElementById(chkbox.closest('li.ui-columntoggler-item').data('column'))).index() + 1,
 columnHeader = this.thead.children('tr').find('th:nth-child(' + index + ')');
 
 columnHeader.addClass('ui-helper-hidden');
 $(PrimeFaces.escapeClientId(columnHeader.attr('id') + '_clone')).addClass('ui-helper-hidden');
 this.tbody.children('tr').find('td:nth-child(' + index + ')').addClass('ui-helper-hidden');
 this.tfoot.children('tr').find('td:nth-child(' + index + ')').addClass('ui-helper-hidden');
 
 this.fireToggleEvent(false, (index - 1));
 },
 alignPanel: function () {
 this.panel.css({'left': '', 'top': '', 'z-index': ++PrimeFaces.zindex}).position({
 my: 'left top'
 , at: 'left bottom'
 , of: this.trigger
 });
 
 if (!this.widthAligned && (this.panel.width() < this.trigger.width())) {
 this.panel.width(this.trigger.width());
 this.widthAligned = true;
 }
 },
 show: function () {
 this.alignPanel();
 this.panel.show();
 this.visible = true;
 },
 hide: function () {
 this.panel.fadeOut('fast');
 this.visible = false;
 },
 fireToggleEvent: function (visible, index) {
 if (this.cfg.behaviors) {
 var toggleBehavior = this.cfg.behaviors['toggle'];
 
 if (toggleBehavior) {
 var visibility = visible ? 'VISIBLE' : 'HIDDEN',
 ext = {
 params: [
 {name: this.id + '_visibility', value: visibility},
 {name: this.id + '_index', value: index}
 ]
 };
 
 toggleBehavior.call(this, ext);
 }
 }
 }
 
 });
 */



PrimeFaces.widget.ColumnToggler = PrimeFaces.widget.DeferredWidget.extend({
    init: function (a) {
        this._super(a);
        this.table = PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.datasource);
        this.trigger = PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.trigger);
        this.tableId = this.table.attr("id");
        this.thead = $(PrimeFaces.escapeClientId(this.tableId) + "_head");
        this.tbody = $(PrimeFaces.escapeClientId(this.tableId) + "_data");
        this.tfoot = $(PrimeFaces.escapeClientId(this.tableId) + "_foot");
        this.visible = false;
        this.render();
        this.bindEvents()
    },
    render: function () {
        this.columns = this.thead.find("> tr > th:not(.ui-static-column)");
        this.panel = $("<div></div>").attr("id", this.cfg.id).attr("role", "dialog")
                .addClass("ui-columntoggler ui-widget ui-widget-content ui-shadow ui-corner-all")
                .append('<ul class="ui-columntoggler-items" role="group"></ul').appendTo(document.body);
        this.itemContainer = this.panel.children("ul");

        for (var e = 0; e < this.columns.length; e++) {
            var b = this.columns.eq(e),
                    f = b.hasClass("ui-helper-hidden"),
                    g = f ? "ui-chkbox-box ui-widget ui-corner-all ui-state-default" : "ui-chkbox-box ui-widget ui-corner-all ui-state-default ui-state-active",
                    h = (f) ? "ui-chkbox-icon ui-icon ui-icon-blank" : "ui-chkbox-icon ui-icon ui-icon-check",
                    k = b.children(".ui-column-title").text();
            this.hasPriorityColumns = b.is('[class*="ui-column-p-"]');
            var m = $('<li class="ui-columntoggler-item"><div class="ui-chkbox ui-widget"><div class="ui-helper-hidden-accessible"><input type="checkbox" role="checkbox"></div><div class="' + g + '"><span class="' + h + '"></span></div></div><label>' + k + "</label></li>").data("column", b.attr("id"));
            if (this.hasPriorityColumns) {
                var a = b.attr("class").split(" ");
                for (var d = 0; d < a.length; d++) {
                    var c = a[d], l = c.indexOf("ui-column-p-");
                    if (l !== -1) {
                        m.addClass(c.substring(l, l + 13))
                    }
                }
            }
            if (!f) {
                m.find("> .ui-chkbox > .ui-helper-hidden-accessible > input").prop("checked", true).attr("aria-checked", true)
            }
            m.appendTo(this.itemContainer)
        }
        this.closer = $('<a href="#" class="ui-columntoggler-close"><span class="ui-icon ui-icon-close"></span></a>').
                attr("aria-label", "Close").prependTo(this.panel);

        if (this.panel.outerHeight() > 200) {
            this.panel.height(200)
        }
        this.hide()
    },
    bindEvents: function () {
        var c = this, b = "mousedown." + this.id, a = "resize." + this.id;
        this.trigger.off("click.ui-columntoggler").on("click.ui-columntoggler", function (d) {
            if (c.visible) {
                c.hide()
            } else {
                c.show()
            }
        });

        this.itemContainer.find("> .ui-columntoggler-item > .ui-chkbox > .ui-chkbox-box").on("mouseover.columnToggler", function () {
            var d = $(this);
            if (!d.hasClass("ui-state-active")) {
                d.addClass("ui-state-hover")
            }
        }).on("mouseout.columnToggler", function () {
            $(this).removeClass("ui-state-hover")
        }).on("click.columnToggler", function (d) {
            c.toggle($(this));
            d.preventDefault()
        });
        this.itemContainer.find("> .ui-columntoggler-item > label").on("click.selectCheckboxMenu", function (d) {
            c.toggle($(this).prev().children(".ui-chkbox-box"));
            PrimeFaces.clearSelection();
            d.preventDefault()
        });

        this.closer.on("click", function (d) {
            c.trigger.focus();
            c.hide();
            d.preventDefault();
        });

        this.bindKeyEvents();
        $(document.body).off(b).on(b, function (f) {
            if (!c.visible) {
                return
            }
            var d = $(f.target);
            if (c.trigger.is(d) || c.trigger.has(d).length) {
                return
            }
            var g = c.panel.offset();
            if (f.pageX < g.left || f.pageX > g.left + c.panel.width() || f.pageY < g.top || f.pageY > g.top + c.panel.height()) {
                c.hide()
            }
        });
        $(window).off(a).on(a, function () {
            if (c.visible) {
                c.alignPanel()
            }
        })
    },
    bindKeyEvents: function () {
        var b = this, a = this.itemContainer.find("> li > div.ui-chkbox > div.ui-helper-hidden-accessible > input");
        this.trigger.on("focus.columnToggler", function () {
            $(this).addClass("ui-state-focus")
        }).on("blur.columnToggler", function () {
            $(this).removeClass("ui-state-focus")
        }).on("keydown.columnToggler", function (f) {
            var d = $.ui.keyCode, c = f.which;
            switch (c) {
                case d.ENTER:
                case d.NUMPAD_ENTER:
                    if (b.visible) {
                        b.hide()
                    } else {
                        b.show()
                    }
                    f.preventDefault();
                    break;
                case d.TAB:
                    if (b.visible) {
                        b.itemContainer.children("li:not(.ui-state-disabled):first").find("div.ui-helper-hidden-accessible > input").trigger("focus");
                        f.preventDefault()
                    }
                    break
            }
        });
        a.on("focus.columnToggler", function () {
            var c = $(this), d = c.parent().next();
            if (c.prop("checked")) {
                d.removeClass("ui-state-active")
            }
            d.addClass("ui-state-focus")
        }).on("blur.columnToggler", function (f) {
            var c = $(this), d = c.parent().next();
            if (c.prop("checked")) {
                d.addClass("ui-state-active")
            }
            d.removeClass("ui-state-focus")
        }).on("keydown.columnToggler", function (d) {
            if (d.which === $.ui.keyCode.TAB) {
                var c = $(this).closest("li").index();
                if (d.shiftKey) {
                    if (c === 0) {
                        b.closer.focus()
                    } else {
                        a.eq(c - 1).focus()
                    }
                } else {
                    if (c === (b.columns.length - 1) && !d.shiftKey) {
                        b.closer.focus()
                    } else {
                        a.eq(c + 1).focus()
                    }
                }
                d.preventDefault()
            }
        }).on("change.columnToggler", function (f) {
            var c = $(this), d = c.parent().next();
            if (c.prop("checked")) {
                b.check(d);
                d.removeClass("ui-state-active")
            } else {
                b.uncheck(d)
            }
        });
        this.closer.on("keydown.columnToggler", function (f) {
            var c = f.which, d = $.ui.keyCode;
            if ((c === d.ENTER || c === d.NUMPAD_ENTER)) {
                b.hide();
                b.trigger.focus();
                f.preventDefault()
            } else {
                if (c === d.TAB) {
                    if (f.shiftKey) {
                        a.eq(b.columns.length - 1).focus()
                    } else {
                        a.eq(0).focus()
                    }
                    f.preventDefault()
                }
            }
        })
    },
    toggle: function (a) {
        if (a.hasClass("ui-state-active")) {
            this.uncheck(a)
        } else {
            this.check(a)
        }
    },
    check: function (c) {
        c.addClass("ui-state-active").removeClass("ui-state-hover").children(".ui-chkbox-icon").addClass("ui-icon-check").removeClass("ui-icon-blank");
        var b = $(document.getElementById(c.closest("li.ui-columntoggler-item").data("column"))).index() + 1, d = this.thead.children("tr").find("th:nth-child(" + b + ")"), a = c.prev().children("input");
        a.prop("checked", true).attr("aria-checked", true);
        d.removeClass("ui-helper-hidden");
        $(PrimeFaces.escapeClientId(d.attr("id") + "_clone")).removeClass("ui-helper-hidden");
        this.tbody.children("tr").find("td:nth-child(" + b + ")").removeClass("ui-helper-hidden");
        this.tfoot.children("tr").find("td:nth-child(" + b + ")").removeClass("ui-helper-hidden");
        this.fireToggleEvent(true, (b - 1));
        this.updateColspan()
    },
    uncheck: function (c) {
        c.removeClass("ui-state-active").children(".ui-chkbox-icon").addClass("ui-icon-blank").removeClass("ui-icon-check");
        var b = $(document.getElementById(c.closest("li.ui-columntoggler-item").data("column"))).index() + 1, d = this.thead.children("tr").find("th:nth-child(" + b + ")"), a = c.prev().children("input");
        a.prop("checked", false).attr("aria-checked", false);
        d.addClass("ui-helper-hidden");
        $(PrimeFaces.escapeClientId(d.attr("id") + "_clone")).addClass("ui-helper-hidden");
        this.tbody.children("tr").find("td:nth-child(" + b + ")").addClass("ui-helper-hidden");
        this.tfoot.children("tr").find("td:nth-child(" + b + ")").addClass("ui-helper-hidden");
        this.fireToggleEvent(false, (b - 1));
        this.updateColspan()
    },
    alignPanel: function () {
        this.panel.css({left: "", top: "", "z-index": ++PrimeFaces.zindex}).position({my: "left top", at: "left bottom", of: this.trigger});
        if (this.hasPriorityColumns) {
            if (this.panel.outerWidth() <= this.trigger.outerWidth()) {
                this.panel.css("width", "auto")
            }
            this.widthAligned = false
        }
        if (!this.widthAligned && (this.panel.outerWidth() < this.trigger.outerWidth())) {
            this.panel.width(this.trigger.width());
            this.widthAligned = true
        }
    },
    show: function () {
        this.alignPanel();
        this.panel.show();
        this.visible = true;
        this.trigger.attr("aria-expanded", true);
        this.closer.trigger("focus")
    },
    hide: function () {
        this.panel.fadeOut("fast");
        this.visible = false;
        this.trigger.attr("aria-expanded", false)
    },
    fireToggleEvent: function (e, c) {
        if (this.cfg.behaviors) {
            var b = this.cfg.behaviors.toggle;
            if (b) {
                var a = e ? "VISIBLE" : "HIDDEN",
                        d = {params: [{name: this.id + "_visibility", value: a}, {name: this.id + "_index", value: c}]};
                b.call(this, d)
            }
        }
    },
    updateColspan: function () {
        var a = this.tbody.children("tr:first");
        if (a && a.hasClass("ui-datatable-empty-message")) {
            var b = this.itemContainer.find("> .ui-columntoggler-item > .ui-chkbox > .ui-chkbox-box.ui-state-active");
            if (b.length) {
                a.children("td").removeClass("ui-helper-hidden").attr("colspan", b.length)
            } else {
                a.children("td").addClass("ui-helper-hidden")
            }
        }
    }
});



