/*
 * Generated, Do Not Modify
 */
 /*
 * Copyright 2009-2013 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.ism.component.overlaypanel;

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;
import javax.faces.render.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.model.SelectItem;
import org.primefaces.util.Constants;

@ResourceDependencies({
//    @ResourceDependency(library = "primefaces", name = "primefaces.css"),
    //    @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
    //    @ResourceDependency(library = "primefaces", name = "jquery/jquery-plugins.js"),
    //    @ResourceDependency(library = "primefaces", name = "primefaces.js")
        
        @ResourceDependency(library="primefaces", name="components.css"),
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="jquery/jquery-plugins.js"),
	@ResourceDependency(library="primefaces", name="ism/core.js"),
	@ResourceDependency(library="primefaces", name="ism/components.js")
})
public class OverlayPanel extends UIPanel implements org.primefaces.component.api.Widget {

    public static final String COMPONENT_TYPE = "org.primefaces.ism.component.OverlayPanel";
    public static final String COMPONENT_FAMILY = "org.primefaces.ism.component";
    private static final String DEFAULT_RENDERER = "org.primefaces.ism.component.OverlayPanelRenderer";

    protected enum PropertyKeys {

        widgetVar,
        style,
        styleClass,
        forValue("for"),
        showEvent,
        hideEvent,
        showEffect,
        hideEffect,
        appendToBody,
        onShow,
        onHide,
        my,
        at,
        dynamic,
        dismissable,
        showCloseIcon,
        constraint;

        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        @Override
        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public static final List<SelectItem> CONSTRAINT = Arrays.asList(
            new SelectItem("=", "Est égal à..."),
            new SelectItem("<=", "Est inférieur ou égal à..."),
            new SelectItem("<", "Est inférieur à..."),
            new SelectItem(">=", "Est supérieur ou égal à..."),
            new SelectItem(">", "Est supérieur à..."),
            new SelectItem("<>", "Est différent de..."),
            new SelectItem("~", "Contient..."),
            new SelectItem("[", "Commence par..."),
            new SelectItem("]", "Termine par..."),
            new SelectItem("[]", "Est compris entre..."),
            new SelectItem("][", "N'est pas compris entre..."),
            new SelectItem("[...]", "Est parmi..."),
            new SelectItem("]...[", "N'est pas parmis...")
    );

    public OverlayPanel() {
        setRendererType(DEFAULT_RENDERER);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public java.lang.String getWidgetVar() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }

    public void setWidgetVar(java.lang.String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }

    public java.lang.String getStyle() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
    }

    public void setStyle(java.lang.String _style) {
        getStateHelper().put(PropertyKeys.style, _style);
    }

    public java.lang.String getStyleClass() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
    }

    public void setStyleClass(java.lang.String _styleClass) {
        getStateHelper().put(PropertyKeys.styleClass, _styleClass);
    }

    public java.lang.String getFor() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.forValue, null);
    }

    public void setFor(java.lang.String _for) {
        getStateHelper().put(PropertyKeys.forValue, _for);
    }

    public java.lang.String getShowEvent() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.showEvent, null);
    }

    public void setShowEvent(java.lang.String _showEvent) {
        getStateHelper().put(PropertyKeys.showEvent, _showEvent);
    }

    public java.lang.String getHideEvent() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.hideEvent, null);
    }

    public void setHideEvent(java.lang.String _hideEvent) {
        getStateHelper().put(PropertyKeys.hideEvent, _hideEvent);
    }

    public java.lang.String getShowEffect() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.showEffect, null);
    }

    public void setShowEffect(java.lang.String _showEffect) {
        getStateHelper().put(PropertyKeys.showEffect, _showEffect);
    }

    public java.lang.String getHideEffect() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.hideEffect, null);
    }

    public void setHideEffect(java.lang.String _hideEffect) {
        getStateHelper().put(PropertyKeys.hideEffect, _hideEffect);
    }

    public boolean isAppendToBody() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.appendToBody, false);
    }

    public void setAppendToBody(boolean _appendToBody) {
        getStateHelper().put(PropertyKeys.appendToBody, _appendToBody);
    }

    public java.lang.String getOnShow() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onShow, null);
    }

    public void setOnShow(java.lang.String _onShow) {
        getStateHelper().put(PropertyKeys.onShow, _onShow);
    }

    public java.lang.String getOnHide() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onHide, null);
    }

    public void setOnHide(java.lang.String _onHide) {
        getStateHelper().put(PropertyKeys.onHide, _onHide);
    }

    public java.lang.String getMy() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.my, null);
    }

    public void setMy(java.lang.String _my) {
        getStateHelper().put(PropertyKeys.my, _my);
    }

    public java.lang.String getAt() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.at, null);
    }

    public void setAt(java.lang.String _at) {
        getStateHelper().put(PropertyKeys.at, _at);
    }

    public boolean isDynamic() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.dynamic, false);
    }

    public void setDynamic(boolean _dynamic) {
        getStateHelper().put(PropertyKeys.dynamic, _dynamic);
    }

    public boolean isDismissable() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.dismissable, true);
    }

    public void setDismissable(boolean _dismissable) {
        getStateHelper().put(PropertyKeys.dismissable, _dismissable);
    }

    public boolean isShowCloseIcon() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.showCloseIcon, false);
    }

    public void setShowCloseIcon(boolean _showCloseIcon) {
        getStateHelper().put(PropertyKeys.showCloseIcon, _showCloseIcon);
    }

    public java.lang.String getConstraint() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.constraint, "~");
    }

    public void setConstraint(java.lang.String constraint) {
        getStateHelper().put(PropertyKeys.constraint, constraint);
    }

    public static final String TARGET_CLASS = "ui-column-filters";
    public static final String TARGET_ICON = "ui-column-filters-icon";
    public static final String STYLE_CLASS = "ui-overlaypanel ui-column-filters-overlaypanel ui-widget ui-widget-content ui-overlay-hidden ui-corner-all ui-shadow";
    public static final String CONTENT_CLASS = "ui-overlaypanel-content ui-column-filters-overlaypanel-content";

    public static final String COLUMN_OVERLAY = "ui-column-overlay";
    public static final String COLUMN_SEPARATOR = "ui-column-separator";
    public static final String COLUMN_SORT_FILTER_ICON = "ui-sort-filter-icon";

    public static final String COLUMN_SORT_FILTER_ASC = "ui-sort-filter-ascending";
    public static final String COLUMN_SORT_FILTER_ASC_ICON = "ui-sort-filter-ascending-icon";
    public static final String COLUMN_SORT_FILTER_ASC_TEXT = "ui-sort-filter-ascending-text";

    public static final String COLUMN_SORT_FILTER_DESC = "ui-sort-filter-descending";
    public static final String COLUMN_SORT_FILTER_DESC_ICON = "ui-sort-filter-descending-icon";
    public static final String COLUMN_SORT_FILTER_DESC_TEXT = "ui-sort-filter-descending-text";

    public static final String COLUMN_SORT_FILTER_UNSORTED = "ui-sort-filter-unsorted";
    public static final String COLUMN_SORT_FILTER_UNSORTED_ICON = "ui-sort-filter-unsorted-icon";
    public static final String COLUMN_SORT_FILTER_UNSORTED_TEXT = "ui-sort-filter-unsorted-text";

    public static final String COLUMN_FILTER_SEARCH = "ui-filter-search";
    public static final String COLUMN_FILTER_SEARCH_ICON = "ui-filter-search-icon";
    public static final String COLUMN_FILTER_SEARCH_CONSTRAINT_ICON = "ui-filter-search-constaint-icon";
    public static final String COLUMN_FILTER_SEARCH_INPUT = "ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all ui-filter-search-input";

    public static final String COLUMN_FILTER_CONTENT = "ui-filter-content";

    public static final String COLUMN_FILTER_ACTIONS = "ui-filter-actions";
    public static final String COLUMN_FILTER_ACTIONS_OK = "ui-filter-actions-ok";
    public static final String COLUMN_FILTER_ACTIONS_CANCEL = "ui-filter-actions-cancel";

    @Override
    public void processDecodes(FacesContext context) {
        if (isRequestSource(context)) {
            this.decode(context);
        } else {
            super.processDecodes(context);
        }
    }

    @Override
    public void processValidators(FacesContext context) {
        if (!isRequestSource(context)) {
            super.processValidators(context);
        }
    }

    @Override
    public void processUpdates(FacesContext context) {
        if (!isRequestSource(context)) {
            super.processUpdates(context);
        }
    }

    private boolean isRequestSource(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_contentLoad");
    }

    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");

        if (userWidgetVar != null) {
            return userWidgetVar;
        } else {
            return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
        }
    }
}
