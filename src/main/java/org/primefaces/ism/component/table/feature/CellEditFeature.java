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
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.api.UIColumn;
import org.primefaces.ism.component.table.Table;
import org.primefaces.ism.component.table.TableRenderer;

public class CellEditFeature implements DataTableFeature {

    @Override
    public void decode(FacesContext context, Table table) {
        throw new RuntimeException("CellEditFeature should not decode.");
    }

    @Override
    public void encode(FacesContext context, TableRenderer renderer, Table table) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String clientId = table.getClientId(context);
        String[] cellInfo = params.get(clientId + "_cellInfo").split(",");
        int rowIndex = Integer.parseInt(cellInfo[0]);
        int cellIndex = Integer.parseInt(cellInfo[1]);
        int i = -1;
        UIColumn column = null;
        for (UIColumn col : table.getColumns()) {
            if (col.isRendered()) {
                i++;

                if (i == cellIndex) {
                    column = col;
                    break;
                }
            }
        }

        table.setRowIndex(rowIndex);

        if (column.isDynamic()) {
            DynamicColumn dynamicColumn = (DynamicColumn) column;
            dynamicColumn.applyStatelessModel();
        }

        column.getCellEditor().getFacet("output").encodeAll(context);

        if (column.isDynamic()) {
            ((DynamicColumn) column).cleanStatelessModel();
        }
    }

    public boolean shouldDecode(FacesContext context, Table table) {
        return false;
    }

    public boolean shouldEncode(FacesContext context, Table table) {
        return context.getExternalContext().getRequestParameterMap().containsKey(table.getClientId(context) + "_cellInfo");
    }
}
