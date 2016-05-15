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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.renderkit.CoreRenderer;

public class RibbonGroupRenderer extends CoreRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        RibbonGroup group = (RibbonGroup) component;
        String label = group.getLabel();
        String groupClass = group.getStyleClass();
        groupClass = (groupClass == null) ? Ribbon.GROUP_CLASS : Ribbon.GROUP_CLASS + " " + groupClass;
        String style = group.getStyle();

        writer.startElement("li", null);
        writer.writeAttribute("class", groupClass, null);
        String idStrings[] = group.getClientId(context).split(":");
        String idString = idStrings[idStrings.length - 1];
        writer.writeAttribute("id", idString, null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        writer.startElement("div", null);
        writer.writeAttribute("class", Ribbon.GROUP_CONTENT_CLASS, null);
        writer.writeAttribute("id", group.getId() + "-content", null);

        //renderChildren(context, group);
        int childCount = group.getChildCount();
        if (childCount > 0) {
            List<UIComponent> children = group.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);
                if (child instanceof RibbonGroupSet && child.isRendered()) {
                    RibbonGroupSet ribbonSet = (RibbonGroupSet) child;
                    ribbonSet.encodeAll(context);
                } else {
                    writer.startElement("div", null);
                    writer.writeAttribute("class", Ribbon.GROUP_SET_CLASS + "-undef", null);
                    writer.writeAttribute("id", child.getId() + "-undef", null);
                    renderChildren(context, child);
                    writer.endElement("div");
                }
            }
        }

        writer.endElement("div");

        writer.startElement("div", null);
        writer.writeAttribute("class", Ribbon.GROUP_LABEL_CLASS, null);
        if (label != null) {
            writer.writeText(label, null);
        }
        writer.endElement("div");

        writer.endElement("li");
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
