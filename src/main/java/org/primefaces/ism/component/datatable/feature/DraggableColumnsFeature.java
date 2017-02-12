/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.primefaces.ism.component.datatable.feature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.columns.Columns;
import org.primefaces.ism.component.datatable.DataTable;
import org.primefaces.ism.component.datatable.DataTableRenderer;

/**
 * <h1>DraggableColumnsFeature</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class DraggableColumnsFeature implements DataTableFeature {

    @Override
    public void decode(FacesContext context, DataTable table) {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String columnOrderParam = params.get(table.getClientId(context) + "_columnOrder");
        if (columnOrderParam == null) {
            return;
        }

        String[] order = columnOrderParam.split(",");
        List orderedColumns = new ArrayList();
        String separator = String.valueOf(UINamingContainer.getSeparatorChar(context));

        for (String columnId : order) {

            for (UIComponent child : table.getChildren()) {
                if (child instanceof Column && child.getClientId(context).equals(columnId)) {
                    orderedColumns.add(child);
                    break;
                } else if (child instanceof Columns) {
                    String columnsClientId = child.getClientId(context);

                    if (columnId.startsWith(columnsClientId)) {
                        String[] ids = columnId.split(separator);
                        int index = Integer.parseInt(ids[ids.length - 1]);

                        orderedColumns.add(new DynamicColumn(index, (Columns) child, (columnsClientId + separator + index)));
                        break;
                    }

                }
            }

        }

        table.setColumns(orderedColumns);
    }

    public void encode(FacesContext context, DataTableRenderer renderer, DataTable table) throws IOException {
        throw new RuntimeException("DraggableColumns Feature should not encode.");
    }

    public boolean shouldDecode(FacesContext context, DataTable table) {
        return table.isDraggableColumns();
    }

    public boolean shouldEncode(FacesContext context, DataTable table) {
        return false;
    }

}
