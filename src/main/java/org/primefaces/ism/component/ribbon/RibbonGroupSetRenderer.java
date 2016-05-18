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

public class RibbonGroupSetRenderer extends CoreRenderer {

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        RibbonGroupSet groupSet = (RibbonGroupSet) component;

        // Manage child depending on ribbon set
        if (groupSet.getSet().matches(Ribbon.RIBBON_SET_BIG_BUTTON)) { // Manage big button
            encodeChildBigButtonSet(context, groupSet);
        } else if (groupSet.getSet().matches(Ribbon.RIBBON_SET_MID_BUTTON)) { // Manage middle button
            encodeChildMidButtonSet(context, groupSet);
        } else if (groupSet.getSet().matches(Ribbon.RIBBON_SET_SMALL_BUTTON)) { // Manage small button
            encodeChildSmallButtonSet(context, groupSet);
        } else if (groupSet.getSet().matches(Ribbon.RIBBON_SET_TINY_BIG_BUTTON)) { // Manage tiny-big button
            encodeChildTinyBigButtonSet(context, groupSet);
        } else if (groupSet.getSet().matches(Ribbon.RIBBON_SET_TINY_MID_BUTTON)) { // Manage tiny-middle button
            encodeChildTinyMidButtonSet(context, groupSet);
        } else if (groupSet.getSet().matches(Ribbon.RIBBON_SET_TINY_SMALL_BUTTON)) { // Manage tiny-small button
            encodeChildTinySmallButtonSet(context, groupSet);
        }

    }

    public void encodeChildBigButtonSet(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        RibbonGroupSet groupSet = (RibbonGroupSet) component;
        String ribbonSetClass = groupSet.getStyleClass();
        ribbonSetClass = (ribbonSetClass == null) ? Ribbon.GROUP_SET_CLASS : Ribbon.GROUP_SET_CLASS + " " + ribbonSetClass;
        String style = groupSet.getStyle();

        writer.startElement("div", null);
        writer.writeAttribute("class", ribbonSetClass + " " + Ribbon.RIBBON_SET_BIG_BUTTON, null);
        writer.writeAttribute("id", groupSet.getId(), null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        int childCount = groupSet.getChildCount();
        if (childCount > 0) {
            List<UIComponent> children = groupSet.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);
                writer.startElement("div", null);
                writer.writeAttribute("class", Ribbon.RIBBON_SET_BIG_BUTTON, null);
                writer.writeAttribute("id", child.getId(), null);
                renderChild(context, child);
                writer.endElement("div");
            }
        }
        writer.endElement("div");
    }

    public void encodeChildMidButtonSet(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        RibbonGroupSet groupSet = (RibbonGroupSet) component;
        String ribbonSetClass = groupSet.getStyleClass();
        ribbonSetClass = (ribbonSetClass == null) ? Ribbon.GROUP_SET_CLASS : Ribbon.GROUP_SET_CLASS + " " + ribbonSetClass;
        String style = groupSet.getStyle();
        String set = groupSet.getSet();

        writer.startElement("div", null);
        writer.writeAttribute("class", ribbonSetClass + " " + Ribbon.RIBBON_SET_BIG_BUTTON, null);
        writer.writeAttribute("id", groupSet.getId(), null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        writer.startElement("ul", null);
        writer.writeAttribute("class", Ribbon.SET_BIG_BUTTON_CLASS, null);
       
        int childCount = groupSet.getChildCount();
        if (childCount > 0) {
            List<UIComponent> children = groupSet.getChildren();
            for (int i = 0; i < childCount; i++) {
                UIComponent child = children.get(i);
                writer.startElement("div", null);
                writer.writeAttribute("class", Ribbon.SET_UI_ICON, null);
                writer.writeAttribute("id", child.getId(), null);
                renderChild(context, child);
                writer.endElement("div");
            }
        }
        
        writer.endElement("ul");
        writer.endElement("div");
    }

    public void encodeChildSmallButtonSet(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        RibbonGroupSet groupSet = (RibbonGroupSet) component;
        String ribbonSetClass = groupSet.getStyleClass();
        ribbonSetClass = (ribbonSetClass == null) ? Ribbon.GROUP_SET_CLASS : Ribbon.GROUP_SET_CLASS + " " + ribbonSetClass;
        String style = groupSet.getStyle();
        String set = groupSet.getSet();

        writer.startElement("div", null);
        writer.writeAttribute("class", ribbonSetClass + " ." + Ribbon.RIBBON_SET_SMALL_BUTTON, null);
        writer.writeAttribute("id", groupSet.getId(), null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        int childCount = groupSet.getChildCount();
        if (childCount > 0) {
            List<UIComponent> children = groupSet.getChildren();
            for (int i = 0; i < childCount; i++) {
                if ((i % 3) == 0) {
                    writer.startElement("ul", null);
                    writer.writeAttribute("class", Ribbon.SET_SMALL_BUTTON_CLASS, null);
                }

                UIComponent child = children.get(i);
                writer.startElement("div", null);
                writer.writeAttribute("class", Ribbon.SET_UI_ICON, null);
                writer.writeAttribute("id", child.getId(), null);
                renderChild(context, child);
                writer.endElement("div");

                if ((i % 3) == 2) {
                    writer.endElement("ul");
                }
            }
        }
        writer.endElement("div");
    }

    public void encodeChildTinyBigButtonSet(FacesContext context, UIComponent component) throws IOException {
        //Do nothing
    }

    public void encodeChildTinyMidButtonSet(FacesContext context, UIComponent component) throws IOException {
        //Do nothing
    }

    public void encodeChildTinySmallButtonSet(FacesContext context, UIComponent component) throws IOException {
        //Do nothing
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
