/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.primefaces.ism.component.datatable.feature;

import java.io.IOException;
import javax.faces.context.FacesContext;
import org.primefaces.ism.component.datatable.DataTable;
import org.primefaces.ism.component.datatable.DataTableRenderer;

/**
 * <h1>AddRowFeature</h1><br>
 * AddRowFeature class  
 *
 * @author r.hendrick
 *
 */
public class AddRowFeature implements DataTableFeature {

    public void decode(FacesContext context, DataTable table) {
        throw new RuntimeException("RowEditFeature should not encode.");
    }

    public void encode(FacesContext context, DataTableRenderer renderer, DataTable table) throws IOException {
        if (table.isSelectionEnabled()) {
            table.findSelectedRowKeys();
        }
        
        String clientId = table.getClientId(context);
        int rowIndex = table.getRowCount() - 1;
        table.setRowIndex(table.getRowCount() - 1);

        if (table.isRowAvailable()) {                    
            renderer.encodeRow(context, table, clientId, rowIndex);
        }
    }

    public boolean shouldDecode(FacesContext context, DataTable table) {
        return false;
    }

    public boolean shouldEncode(FacesContext context, DataTable table) {
        return context.getExternalContext().getRequestParameterMap().containsKey(table.getClientId(context) + "_addrow");
    }
}
