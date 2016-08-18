/*
 * Copyright 2009-2014 PrimeTek.
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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.tabview.Tab;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.ComponentUtils;
import org.primefaces.util.HTML;
import org.primefaces.util.WidgetBuilder;

public class RibbonRenderer extends CoreRenderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Ribbon ribbon = (Ribbon) component;
        String activeIndexValue = params.get(ribbon.getClientId(context) + "_activeIndex");

        if (!ComponentUtils.isValueBlank(activeIndexValue)) {
            ribbon.setActiveIndex(Integer.parseInt(activeIndexValue));
        }

        decodeBehaviors(context, component);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Ribbon ribbon = (Ribbon) component;
        String clientId = ribbon.getClientId(context);
        String var = ribbon.getVar();

        if (ribbon.isContentLoadRequest(context)) {
            Tab tabToLoad = null;

            if (var == null) {
                String tabClientId = params.get(clientId + "_newTab");
                tabToLoad = (Tab) ribbon.findTab(tabClientId);

                tabToLoad.encodeAll(context);
                tabToLoad.setLoaded(true);
            } else {
                int tabindex = Integer.parseInt(params.get(clientId + "_tabindex"));
                ribbon.setIndex(tabindex);
                tabToLoad = (Tab) ribbon.getChildren().get(0);
                tabToLoad.encodeAll(context);
                ribbon.setIndex(-1);
            }
        } else {
            encodeMarkup(context, ribbon);
            encodeScript(context, ribbon);
        }

        //encodeMarkup(context, ribbon);
        //encodeScript(context, ribbon);
    }

    private void encodeScript(FacesContext context, Ribbon ribbon) throws IOException {
        String clientId = ribbon.getClientId(context);
        boolean dynamic = ribbon.isDynamic();

        WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("Ribbon", ribbon.resolveWidgetVar(), clientId, "ribbon");

        if (dynamic) {
            wb.attr("dynamic", true).attr("cache", ribbon.isCache());
        }

        wb.callback("onTabChange", "function(index)", ribbon.getOnTabChange())
                .callback("onTabShow", "function(index)", ribbon.getOnTabShow())
                .callback("onTabClose", "function(index)", ribbon.getOnTabClose());

        wb.attr("effect", ribbon.getEffect(), null)
                .attr("effectDuration", ribbon.getEffectDuration(), null)
                .attr("scrollable", ribbon.isScrollable())
                .attr("tabindex", ribbon.getTabindex(), null);

        encodeClientBehaviors(context, ribbon);

        wb.finish();
    }

    protected void encodeMarkup(FacesContext context, Ribbon ribbon) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = ribbon.getClientId(context);
        String widgetVar = ribbon.resolveWidgetVar();
        String orientation = ribbon.getOrientation();
        String style = ribbon.getStyle();
        String styleClass = ribbon.getStyleClass();
        String defaultStyleClass = Ribbon.CONTAINER_CLASS + " ui-tabs-" + orientation;
        styleClass = (styleClass == null) ? Ribbon.CONTAINER_CLASS : Ribbon.CONTAINER_CLASS + " " + styleClass;

        if (ComponentUtils.isRTL(context, ribbon)) {
            styleClass += " ui-tabs-rtl";
        }

        writer.startElement("div", ribbon);
        writer.writeAttribute("id", clientId, "id");
        writer.writeAttribute("class", styleClass, "styleClass");
        if (style != null) {
            writer.writeAttribute("class", style, "style");
        }

        writer.writeAttribute(HTML.WIDGET_VAR, widgetVar, null);

        if(orientation.equals("bottom")) {
            encodeTabContents(context, ribbon);
            encodeHeaders(context, ribbon);
        }
        else {
            encodeHeaders(context, ribbon);
            encodeTabContents(context, ribbon);
        }

        //encodeTabHeaders(context, ribbon);
        //encodeTabContents(context, ribbon);

        encodeStateHolder(context, ribbon, clientId + "_activeIndex", String.valueOf(ribbon.getActiveIndex()));

        if (ribbon.isScrollable()) {
            String scrollParam = clientId + "_scrollState";
            String scrollState = context.getExternalContext().getRequestParameterMap().get(scrollParam);
            String scrollValue = scrollState == null ? "0" : scrollState;
            encodeStateHolder(context, ribbon, scrollParam, scrollValue);
        }

        writer.endElement("div");
    }

    protected void encodeHeaders(FacesContext context, Ribbon ribbon) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String var = ribbon.getVar();
        int activeIndex = ribbon.getActiveIndex();
        boolean scrollable = ribbon.isScrollable();

        if (scrollable) {
            writer.startElement("div", null);
            writer.writeAttribute("class", Ribbon.NAVIGATOR_SCROLLER_CLASS, null);

            encodeScrollerButton(context, ribbon, Ribbon.NAVIGATOR_LEFT_CLASS, Ribbon.NAVIGATOR_LEFT_ICON_CLASS);
            encodeScrollerButton(context, ribbon, Ribbon.NAVIGATOR_RIGHT_CLASS, Ribbon.NAVIGATOR_RIGHT_ICON_CLASS);
        }

        writer.startElement("ul", null);
        writer.writeAttribute("class", Ribbon.NAVIGATOR_CLASS, null);
        writer.writeAttribute("role", "tablist", null);

        if (var == null) {
            int i = 0;
            for (UIComponent kid : ribbon.getChildren()) {
                if (kid.isRendered() && kid instanceof Tab) {
                    encodeTabHeader(context, ribbon, (Tab) kid, (i == activeIndex));
                    i++;
                }
            }
        } else {
            int dataCount = ribbon.getRowCount();

            //boundary check
            activeIndex = activeIndex >= dataCount ? 0 : activeIndex;

            Tab tab = (Tab) ribbon.getChildren().get(0);
            for (int i = 0; i < dataCount; i++) {
                ribbon.setIndex(i);
                encodeTabHeader(context, ribbon, tab, (i == activeIndex));
            }
            ribbon.setIndex(-1);
        }

        writer.endElement("ul");

        if (scrollable) {
            writer.endElement("div");
        }
    }

    protected void encodeTabHeader(FacesContext context, Ribbon ribbon, Tab tab, boolean active) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String defaultStyleClass = active ? Ribbon.ACTIVE_TAB_HEADER_CLASS : Ribbon.INACTIVE_TAB_HEADER_CLASS;
        defaultStyleClass = defaultStyleClass + " ui-corner-" + ribbon.getOrientation();   //cornering
        if(tab.isDisabled()) {
            defaultStyleClass = defaultStyleClass + " ui-state-disabled";
        }
        String styleClass = tab.getTitleStyleClass();
        styleClass = (styleClass == null) ? defaultStyleClass : defaultStyleClass + " " + styleClass;
        UIComponent titleFacet = tab.getFacet("title");
        
        //header container
        String idString = tab.getClientId(context).split(":")[2];
        writer.startElement("li", null);
        writer.writeAttribute("id", "P" + idString, null);
        writer.writeAttribute("class", styleClass, null);
        writer.writeAttribute("role", "tab", null);
        writer.writeAttribute("aria-expanded", String.valueOf(active), null);
        writer.writeAttribute("aria-selected", String.valueOf(active), null);
        writer.writeAttribute("aria-label", tab.getAriaLabel(), null);
        if(tab.getTitleStyle() != null)  writer.writeAttribute("style", tab.getTitleStyle(), null);
        if(tab.getTitletip() != null)  writer.writeAttribute("title", tab.getTitletip(), null);

        //title
        writer.startElement("a", null);
        writer.writeAttribute("id", idString, null);
        writer.writeAttribute("href", "#" + tab.getClientId(context), null);
        writer.writeAttribute("tabindex", "-1", null);
        if(titleFacet == null) {
        	String tabTitle = tab.getTitle(); 
        	if(tabTitle != null) {
        	    writer.write(tabTitle);
        	}
        }
        else {
            titleFacet.encodeAll(context);
        }        
        writer.endElement("a");

        //closable
        if(tab.isClosable()) {
            writer.startElement("span", null);
            writer.writeAttribute("class", "ui-icon ui-icon-close", null);
            writer.endElement("span");
        }

        writer.endElement("li");
    }

    
    
    protected void encodeTabHeaders(FacesContext context, Ribbon ribbon) throws IOException {
        /*
        ResponseWriter writer = context.getResponseWriter();
        String var = ribbon.getVar();
        int activeIndex = ribbon.getActiveIndex();
        boolean scrollable = ribbon.isScrollable();
        int childCount = ribbon.getChildCount();

        writer.startElement("ul", ribbon);
        writer.writeAttribute("class", Ribbon.NAVIGATOR_CLASS, null);
        writer.writeAttribute("role", "tablist", null);

        if (var == null) {
            int i = 0;
            for (UIComponent kid : ribbon.getChildren()) {
                if (kid.isRendered() && kid instanceof Tab) {
                    encodeTabHeader(context, ribbon, (Tab) kid, (i == activeIndex));
                    i++;
                }
            }
        } else {
            int dataCount = tabView.getRowCount();

            //boundary check
            activeIndex = activeIndex >= dataCount ? 0 : activeIndex;

            Tab tab = (Tab) tabView.getChildren().get(0);

            for (int i = 0; i < dataCount; i++) {
                tabView.setIndex(i);

                encodeTabHeader(context, tabView, tab, (i == activeIndex));
            }

            tabView.setIndex(-1);
        }

        if (childCount > 0) {
            List<UIComponent> children = ribbon.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);

                if (child instanceof Tab && child.isRendered()) {
                    Tab tab = (Tab) child;
                    String title = tab.getTitle();
                    boolean active = (i == activeIndex);
                    String headerClass = (active) ? Ribbon.ACTIVE_TAB_HEADER_CLASS : Ribbon.INACTIVE_TAB_HEADER_CLASS;

                    //header container
                    writer.startElement("li", null);
                    writer.writeAttribute("id", tab.getId(), null);
                    writer.writeAttribute("class", headerClass, null);
                    writer.writeAttribute("role", "tab", null);
                    writer.writeAttribute("aria-expanded", String.valueOf(active), null);

                    writer.startElement("a", null);
                    writer.writeAttribute("id", tab.getClientId(context), null);
                    writer.writeAttribute("href", tab.getClientId(context), null);
                    if (title != null) {
                        writer.writeText(title, null);
                    }
                    writer.endElement("a");

                    writer.endElement("li");
                }
            }
        }
        writer.endElement("ul");
        */
    }

    protected void encodeTabContents(FacesContext context, Ribbon ribbon) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        int activeIndex = ribbon.getActiveIndex();
        int childCount = ribbon.getChildCount();

        writer.startElement("div", ribbon);
        writer.writeAttribute("class", Ribbon.PANELS_CLASS, null);

        if (childCount > 0) {
            List<UIComponent> children = ribbon.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);

                if (child instanceof Tab && child.isRendered()) {
                    Tab tab = (Tab) child;
                    encodeTabContent(context, ribbon, tab, (i == activeIndex));
                }
            }
        }

        writer.endElement("div");
    }

    protected void encodeTabContent(FacesContext context, Ribbon ribbon, Tab tab, boolean active) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String contentClass = active ? Ribbon.ACTIVE_TAB_CONTENT_CLASS : Ribbon.INACTIVE_TAB_CONTENT_CLASS;
        int childCount = tab.getChildCount();

        writer.startElement("div", ribbon);
        writer.writeAttribute("id", tab.getClientId(context), null);
        writer.writeAttribute("class", contentClass, null);

        if (childCount > 0) {
            writer.startElement("ul", ribbon);
            writer.writeAttribute("class", Ribbon.GROUPS_CLASS, null);

            List<UIComponent> children = tab.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);

                if (child instanceof RibbonGroup && child.isRendered()) {
                    RibbonGroup group = (RibbonGroup) child;
                    group.encodeAll(context);
                }
            }

            writer.endElement("ul");
        }

        writer.endElement("div");
    }

    protected void encodeStateHolder(FacesContext facesContext, Ribbon ribbon, String name, String value) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();

        writer.startElement("input", null);
        writer.writeAttribute("type", "hidden", null);
        writer.writeAttribute("id", name, null);
        writer.writeAttribute("name", name, null);
        writer.writeAttribute("value", value, null);
        writer.writeAttribute("autocomplete", "off", null);
        writer.endElement("input");
    }

    protected void encodeScrollerButton(FacesContext context, Ribbon ribbon, String styleClass, String iconClass) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        
        writer.startElement("a", null);
        writer.writeAttribute("class", styleClass, null);
        
        writer.startElement("span", null);
        writer.writeAttribute("class", iconClass, null);
        writer.endElement("span");
        
        writer.endElement("a");
    }
    
    
    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //Do nothing
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }
}
