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
package org.primefaces.ism.component.overlaypanel;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import org.primefaces.expression.SearchExpressionFacade;
import static org.primefaces.ism.component.overlaypanel.OverlayPanel.CONSTRAINT;
import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.WidgetBuilder;

public class OverlayPanelRenderer extends CoreRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        OverlayPanel panel = (OverlayPanel) component;

        if (panel.isContentLoadRequest(context)) {
            renderChildren(context, panel);
        } else {
            encodeMarkup(context, panel);
            encodeScript(context, panel);
        }
    }

    protected void encodeMarkup(FacesContext context, OverlayPanel panel) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = panel.getClientId(context);
        String style = panel.getStyle();
        String styleClass = panel.getStyleClass();
        styleClass = styleClass == null ? OverlayPanel.STYLE_CLASS : OverlayPanel.STYLE_CLASS + " " + styleClass;

        // MANAGING TARGET
        writer.startElement("span", null);
        writer.writeAttribute("id", clientId + "_target", "id");
        writer.writeAttribute("class", OverlayPanel.TARGET_CLASS, "class");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.TARGET_ICON, "class");
        writer.endElement("span");

        writer.endElement("span");

        // MANAGING OVERLAY
        writer.startElement("div", panel);
        writer.writeAttribute("id", clientId, "id");
        writer.writeAttribute("class", styleClass, "styleClass");
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }

        writer.startElement("div", panel);
        writer.writeAttribute("class", OverlayPanel.CONTENT_CLASS, "styleClass");
        if (!panel.isDynamic()) {
            encodeFilter(context, panel);
            renderChildren(context, panel);
        }
        writer.endElement("div");

        writer.endElement("div");
    }

    protected void encodeScript(FacesContext context, OverlayPanel panel) throws IOException {
        String target = SearchExpressionFacade.resolveClientId(context, panel, panel.getFor());
        String clientId = panel.getClientId(context);
        //String target = clientId + "_target";

        WidgetBuilder wb = getWidgetBuilder(context);
        wb.initWithDomReady("OverlayFiltersPanel", panel.resolveWidgetVar(), clientId)
                .attr("target", target)
                .attr("showEvent", panel.getShowEvent(), null)
                .attr("hideEvent", panel.getHideEvent(), null)
                .attr("showEffect", panel.getShowEffect(), null)
                .attr("hideEffect", panel.getHideEffect(), null)
                .callback("onShow", "function()", panel.getOnShow())
                .callback("onHide", "function()", panel.getOnHide())
                .attr("my", panel.getMy(), null)
                .attr("at", panel.getAt(), null)
                .attr("appendToBody", panel.isAppendToBody(), false)
                .attr("dynamic", panel.isDynamic(), false)
                .attr("dismissable", panel.isDismissable(), true)
                .attr("showCloseIcon", panel.isShowCloseIcon(), false);

        wb.finish();
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        //Do nothing
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    public void encodeFilter(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        OverlayPanel panel = (OverlayPanel) component;
        String clientId = panel.getClientId(context);
        String style = panel.getStyle();
        String styleClass = panel.getStyleClass();
        styleClass = styleClass == null ? OverlayPanel.STYLE_CLASS : OverlayPanel.STYLE_CLASS + " " + styleClass;

        // =====================================================================
        // MENU 
        writer.startElement("ul", panel);
        writer.writeAttribute("class", OverlayPanel.COLUMN_OVERLAY, "class");
        writer.writeAttribute("data-ch", panel.getFor(), null);

        // =====================================================================
        // MENU ASCENDING
        writer.startElement("li", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_ASC, "class");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_ICON + " "
                + OverlayPanel.COLUMN_SORT_FILTER_ASC_ICON, "class");
        writer.endElement("span");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_ASC_TEXT, "class");
        writer.append("Trier de A à Z");
        writer.endElement("span");

        writer.endElement("li"); // end menu ascending

        // =====================================================================
        // MENU DESCENDING
        writer.startElement("li", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_DESC, "class");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_ICON + " "
                + OverlayPanel.COLUMN_SORT_FILTER_DESC_ICON, "class");
        writer.endElement("span");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_DESC_TEXT, "class");
        writer.append("Trier de Z à A");
        writer.endElement("span");

        writer.endElement("li"); // end menu descending

        // =====================================================================
        // MENU UNSORTED FILTERS
        writer.startElement("li", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_UNSORTED, "class");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_ICON + " "
                + OverlayPanel.COLUMN_SORT_FILTER_UNSORTED_ICON, "class");
        writer.endElement("span");

        writer.startElement("span", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SORT_FILTER_UNSORTED_TEXT, "class");
        writer.append("Effacer le Tri");
        writer.endElement("span");

        writer.endElement("li"); // end menu descending

        // =====================================================================
        // MENU SEPARATOR
        writer.startElement("li", null);
        writer.startElement("hr", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_SEPARATOR, "class");
        writer.endElement("hr");
        writer.endElement("li"); // en menu separator

        // =====================================================================
        // MENU FILTER SEARCH
        writer.startElement("li", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_SEARCH, "class");

        UISelectItems uiItems = new UISelectItems();
        uiItems.setValue(CONSTRAINT);
        SelectOneMenu som = new SelectOneMenu();
        som.getChildren().add(uiItems);
        som.setValue(panel.getConstraint());
        som.encodeAll(context);

        writer.startElement("input", null);
        writer.writeAttribute("id", panel.getFor() + ":filter", "id");
        writer.writeAttribute("name", panel.getFor() + ":filter", "name");
        writer.writeAttribute("value", "", "value");
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_SEARCH_INPUT, "class");
        writer.writeAttribute("type", "text", "type");
        writer.writeAttribute("autocomplete", "off", "autocomplete");
        writer.writeAttribute("role", "textbox", "role");
        writer.writeAttribute("aria-disabled", "false", "aria-disabled");
        writer.writeAttribute("aria-readonly", "false", "aria-readonly");
        writer.endElement("input");

        writer.startElement("span", null);
        writer.writeAttribute("class",
                OverlayPanel.COLUMN_FILTER_SEARCH_ICON, "class");
        writer.endElement("span");

        writer.endElement("li"); // end FILTER SEARCH

        // =====================================================================
        // MENU FILTER CONTENT
        writer.startElement("li", null);

        writer.startElement("div", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_CONTENT, "class");
        writer.endElement("div");

        writer.endElement("li"); // end FILTER CONTENT

        // =====================================================================
        // MENU FILTER ACTIONS
        /*
        writer.startElement("li", null);

        writer.startElement("div", null);
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_ACTIONS, "class");

        writer.startElement("button", null);
        writer.writeAttribute("name", OverlayPanel.COLUMN_FILTER_ACTIONS_OK, "name");
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_ACTIONS_OK, "class");
        writer.writeAttribute("value", "ok", "value");
        writer.append("Ok");
        writer.endElement("button");

        writer.startElement("button", null);
        writer.writeAttribute("name", OverlayPanel.COLUMN_FILTER_ACTIONS_CANCEL, "name");
        writer.writeAttribute("class", OverlayPanel.COLUMN_FILTER_ACTIONS_CANCEL, "class");
        writer.writeAttribute("value", "cancel", "value");
        writer.append("Annuler");
        writer.endElement("button");

        writer.endElement("div"); // en ACTION DIV

        writer.endElement("li"); // end FILTER ACTIONS
         */
        // ========
        writer.endElement("ul"); // end MENU
    }
}
