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
package org.primefaces.ism.component.ribbon;

import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import org.primefaces.component.api.UITabPanel;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.util.Constants;

@ResourceDependencies({
    @ResourceDependency(library = "primefaces", name = "primefaces.css"),
    @ResourceDependency(library = "primefaces", name = "ribbon/ribbon.css"),
    @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
    @ResourceDependency(library = "primefaces", name = "jquery/jquery-plugins.js"),
    @ResourceDependency(library = "primefaces", name = "primefaces.js"),
    @ResourceDependency(library = "primefaces", name = "ribbon/ribbon.js")
})
public class Ribbon extends UITabPanel implements org.primefaces.component.api.Widget, org.primefaces.component.api.RTLAware, javax.faces.component.behavior.ClientBehaviorHolder {

    public static final String COMPONENT_TYPE = "org.primefaces.ism.component.Ribbon";
    public static final String COMPONENT_FAMILY = "org.primefaces.ism.component";
    private static final String DEFAULT_RENDERER = "org.primefaces.ism.component.RibbonRenderer";

    protected enum PropertyKeys {

        	widgetVar
		,activeIndex
		,effect
		,effectDuration
		,cache
		,onTabChange
		,onTabShow
		,style
		,styleClass
		,orientation
		,onTabClose
		,dir
		,scrollable
		,tabindex;
                
        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    public Ribbon() {
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

    public int getActiveIndex() {
        return (java.lang.Integer) getStateHelper().eval(PropertyKeys.activeIndex, 0);
    }

    public void setActiveIndex(int _activeIndex) {
        getStateHelper().put(PropertyKeys.activeIndex, _activeIndex);
    }

    public java.lang.String getEffect() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.effect, null);
    }

    public void setEffect(java.lang.String _effect) {
        getStateHelper().put(PropertyKeys.effect, _effect);
    }

    public java.lang.String getEffectDuration() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.effectDuration, "normal");
    }

    public void setEffectDuration(java.lang.String _effectDuration) {
        getStateHelper().put(PropertyKeys.effectDuration, _effectDuration);
    }

    public boolean isCache() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.cache, true);
    }

    public void setCache(boolean _cache) {
        getStateHelper().put(PropertyKeys.cache, _cache);
    }

    public java.lang.String getOnTabChange() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onTabChange, null);
    }

    public void setOnTabChange(java.lang.String _onTabChange) {
        getStateHelper().put(PropertyKeys.onTabChange, _onTabChange);
    }

    public java.lang.String getOnTabShow() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onTabShow, null);
    }

    public void setOnTabShow(java.lang.String _onTabShow) {
        getStateHelper().put(PropertyKeys.onTabShow, _onTabShow);
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

    public java.lang.String getOrientation() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.orientation, "top");
    }

    public void setOrientation(java.lang.String _orientation) {
        getStateHelper().put(PropertyKeys.orientation, _orientation);
    }

    public java.lang.String getOnTabClose() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.onTabClose, null);
    }

    public void setOnTabClose(java.lang.String _onTabClose) {
        getStateHelper().put(PropertyKeys.onTabClose, _onTabClose);
    }

    public java.lang.String getDir() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.dir, "ltr");
    }

    public void setDir(java.lang.String _dir) {
        getStateHelper().put(PropertyKeys.dir, _dir);
    }

    public boolean isScrollable() {
        return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.scrollable, false);
    }

    public void setScrollable(boolean _scrollable) {
        getStateHelper().put(PropertyKeys.scrollable, _scrollable);
    }

    public java.lang.String getTabindex() {
        return (java.lang.String) getStateHelper().eval(PropertyKeys.tabindex, null);
    }

    public void setTabindex(java.lang.String _tabindex) {
        getStateHelper().put(PropertyKeys.tabindex, _tabindex);
    }

    public static final String CONTAINER_CLASS = "ui-ribbon ui-tabs ui-tabs-top ui-widget ui-widget-content ui-corner-top ui-hidden-container";
    public static final String NAVIGATOR_CLASS = "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all";
    public static final String INACTIVE_TAB_HEADER_CLASS = "ui-state-default ui-corner-top";
    public static final String ACTIVE_TAB_HEADER_CLASS = "ui-state-default ui-tabs-selected ui-state-active ui-corner-top";
    public static final String PANELS_CLASS = "ui-tabs-panels";
    public static final String ACTIVE_TAB_CONTENT_CLASS = "ui-tabs-panel ui-widget-content ui-corner-bottom";
    public static final String INACTIVE_TAB_CONTENT_CLASS = "ui-tabs-panel ui-widget-content ui-corner-bottom ui-helper-hidden";
    public static final String GROUPS_CLASS = "ui-ribbon-groups ui-helper-reset ui-helper-clearfix ui-widget-content";
    public static final String GROUP_CLASS = "ui-ribbon-group";
    public static final String GROUP_CONTENT_CLASS = "ui-ribbon-group-content";
    public static final String GROUP_LABEL_CLASS = "ui-ribbon-group-label";
    
    public static final String GROUPS_SET_CLASS = "ui-ribbon-groups-set ui-helper-reset ui-helper-clearfix ui-widget-content";
    public static final String GROUP_SET_CLASS = "ui-ribbon-group-set";
    public static final String GROUP_SET_CONTENT_CLASS = "ui-ribbon-group-content";
    
    public static final String RIBBON_SET_BIG_BUTTON = "big";
    public static final String RIBBON_SET_MID_BUTTON = "middle";
    public static final String RIBBON_SET_SMALL_BUTTON = "small";
    public static final String RIBBON_SET_TINY_BIG_BUTTON = "tiny-big";
    public static final String RIBBON_SET_TINY_MID_BUTTON = "tiny-middle";
    public static final String RIBBON_SET_TINY_SMALL_BUTTON = "tiny-small";
    
    public static final String SET_BIG_BUTTON_CLASS = "ui-ribbonset-big";
    public static final String SET_MID_BUTTON_CLASS = "ui-ribbonset-mid";
    public static final String SET_SMALL_BUTTON_CLASS = "ui-ribbonset-small";
    public static final String SET_TINY_BIG_BUTTON_CLASS = "ui-ribbonset-tinybig";
    public static final String SET_TINY_MID_BUTTON_CLASS = "ui-ribbonset-tinymid";
    public static final String SET_TINY_SMALL_BUTTON_CLASS = "ui-ribbonset-tinysmall";
    
    public static final String SET_UI_ICON="ui-ribbonset-icon";

    public static final String NAVIGATOR_SCROLLER_CLASS = "ui-tabs-navscroller";
    public static final String NAVIGATOR_LEFT_CLASS = "ui-tabs-navscroller-btn ui-tabs-navscroller-btn-left ui-state-default ui-corner-right";
    public static final String NAVIGATOR_RIGHT_CLASS = "ui-tabs-navscroller-btn ui-tabs-navscroller-btn-right ui-state-default ui-corner-left";
    public static final String NAVIGATOR_LEFT_ICON_CLASS = "ui-icon ui-icon-carat-1-w";
    public static final String NAVIGATOR_RIGHT_ICON_CLASS = "ui-icon ui-icon-carat-1-e";
    public static final String SCROLLABLE_TABS_CLASS = "ui-tabs-scrollable";
    
    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("tabChange", "tabClose"));
 
    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    public boolean isContentLoadRequest(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap().containsKey(this.getClientId(context) + "_contentLoad");
    }

    private boolean isRequestSource(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    public Tab findTab(String tabClientId) {
        for(UIComponent component : getChildren()) {
            if(component.getClientId().equals(tabClientId))
                return (Tab) component;
        }

        return null;
    }

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();

        if(isRequestSource(context) && event instanceof AjaxBehaviorEvent) {
            Map<String,String> params = context.getExternalContext().getRequestParameterMap();
            String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
            String clientId = this.getClientId(context);
            boolean repeating = this.isRepeating();
            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

            if(eventName.equals("tabChange")) {
                String tabClientId = params.get(clientId + "_newTab");
                TabChangeEvent changeEvent = new TabChangeEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId));

                if(repeating) {
                    int tabindex = Integer.parseInt(params.get(clientId + "_tabindex"));
                    setIndex(tabindex);
                    changeEvent.setData(this.getIndexData());
                    changeEvent.setTab((Tab) getChildren().get(0));
                }

                changeEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(changeEvent);

                if(repeating) {
                    this.setIndex(-1);
                }
            }
            else if(eventName.equals("tabClose")) {
                String tabClientId = params.get(clientId + "_closeTab");
                TabCloseEvent closeEvent = new TabCloseEvent(this, behaviorEvent.getBehavior(), findTab(tabClientId));

                if(repeating) {
                    int tabindex = Integer.parseInt(params.get(clientId + "_tabindex"));
                    setIndex(tabindex);
                    closeEvent.setData(this.getIndexData());
                    closeEvent.setTab((Tab) getChildren().get(0));
                }

                closeEvent.setPhaseId(behaviorEvent.getPhaseId());

                super.queueEvent(closeEvent);

                if(repeating) {
                    this.setIndex(-1);
                }
            }
        }
        else {
            super.queueEvent(event);
        }
    }

    protected void resetActiveIndex() {
		getStateHelper().remove(PropertyKeys.activeIndex);
    }

    public boolean isRTL() {
        return this.getDir().equalsIgnoreCase("rtl");
    }

    @Override
    public void processUpdates(FacesContext context) {
        if(!isRendered()) {
            return;
        }

        super.processUpdates(context);

        ValueExpression expr = this.getValueExpression("activeIndex");
        if(expr != null) {
            expr.setValue(getFacesContext().getELContext(), getActiveIndex());
            resetActiveIndex();
        }
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
