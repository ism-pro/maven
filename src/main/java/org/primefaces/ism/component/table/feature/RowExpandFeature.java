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
package org.primefaces.ism.component.table.feature;

import java.io.IOException;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.ism.component.table.Table;
import org.primefaces.ism.component.table.TableRenderer;
import org.primefaces.component.rowexpansion.RowExpansion;

public class RowExpandFeature implements DataTableFeature {

    public void decode(FacesContext context, Table table) {
        throw new RuntimeException("RowExpandFeature should not encode.");
    }

    public void encode(FacesContext context, TableRenderer renderer, Table table) throws IOException {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        int expandedRowIndex = Integer.parseInt(params.get(table.getClientId(context) + "_expandedRowIndex"));

        encodeExpansion(context, renderer, table, expandedRowIndex);
        table.setRowIndex(-1);
    }

    public void encodeExpansion(FacesContext context, TableRenderer renderer, Table table, int rowIndex) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String rowIndexVar = table.getRowIndexVar();
        RowExpansion rowExpansion = table.getRowExpansion();

        String styleClass = Table.EXPANDED_ROW_CONTENT_CLASS + " ui-widget-content";
        if (rowExpansion.getStyleClass() != null) {
            styleClass = styleClass + " " + rowExpansion.getStyleClass();
        }

        table.setRowIndex(rowIndex);

        if (rowExpansion.isRendered()) {
            if (rowIndexVar != null) {
                context.getExternalContext().getRequestMap().put(rowIndexVar, rowIndex);
            }

            writer.startElement("tr", null);
            writer.writeAttribute("class", styleClass, null);

            writer.startElement("td", null);
            writer.writeAttribute("colspan", table.getColumnsCount(), null);

            table.getRowExpansion().encodeAll(context);

            writer.endElement("td");

            writer.endElement("tr");
        }
    }

    public boolean shouldDecode(FacesContext context, Table table) {
        return false;
    }

    public boolean shouldEncode(FacesContext context, Table table) {
        return context.getExternalContext().getRequestParameterMap().containsKey(table.getClientId(context) + "_rowExpansion");
    }

}
