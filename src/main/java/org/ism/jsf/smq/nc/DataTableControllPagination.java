/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.jsf.smq.nc;

 import java.io.Serializable;  
 import javax.faces.bean.ManagedBean;  
 import javax.faces.bean.SessionScoped;  
import org.ism.jsf.util.JsfUtil;
 import org.primefaces.event.data.PageEvent;  
 import org.primefaces.component.datatable.DataTable;  
import org.primefaces.event.data.SortEvent;
/**
 * <h1>DataTableControllPagination</h1>
 * <p>
 * This class coverts 
 * </p>
 *
 *
 * @author r.hendrick
 */
 @ManagedBean  
 @SessionScoped  
 public class DataTableControllPagination implements Serializable {  
      private static final long serialVersionUID = 1L;  
      protected int first;  
      
      public int getFirst() {  
           return first;  
      }
      
      public void setFirst(int first) {  
           this.first = first;  
      }
      
      public void onPageChange(PageEvent event) {  
           this.setFirst(((DataTable) event.getSource()).getFirst());  
      }
      
      public void onSortBy(SortEvent event){
          JsfUtil.out("==> On sort event...");
          
          JsfUtil.out("<== End on sort event");
      }
 }