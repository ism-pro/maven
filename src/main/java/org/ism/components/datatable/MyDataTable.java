/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.components.datatable;

import java.util.HashMap;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;

/**
 * <h1>MyDataTable</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
/**
 * Extending PF data table to allow for binding of the filter map.
 */
public class MyDataTable extends DataTable {

    /**
     * Locates the filterMap attribute on the datatable.
     * <p>
     * @return the ValueExpression provided in the filterMap attribute if
     * specified or null if not
     */
    protected ValueExpression getFilterFacetValueExpression() {
        ValueExpression ve = getValueExpression("filterMap");
        return ve;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.primefaces.component.datatable.DataTable#getFilters()
     * @return
     */
    @Override
    public Map<String, Object> getFilters() {
        ValueExpression ve = getFilterFacetValueExpression();
        if (ve == null) {
            return super.getFilters();
        }

        Map<String, Object> map = (Map<String, Object>) ve.getValue(FacesContext.getCurrentInstance().getELContext());
        //LOG.trace("Facet found and map is {}", map);
        if (map == null) {
            return new HashMap<String, Object>();
        } else {
            return map;
        }
    }

    ;

   /*
    * (non-Javadoc)
    * @see
    * org.primefaces.component.datatable.DataTable#setFilters(java.util.Map)
    */
   @Override
    public void setFilters(java.util.Map<String, Object> filters) {
        ValueExpression ve = getFilterFacetValueExpression();
        if (ve == null) {
            super.setFilters(filters);
            return;
        }

        ve.setValue(FacesContext.getCurrentInstance().getELContext(), filters);
        //LOG.trace("Facet updated to {}", filters);
    }
;
}
