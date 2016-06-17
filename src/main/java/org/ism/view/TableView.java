/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.ism.jsf.util.JsfUtil;

/**
 *
 * @author r.hendrick
 */
@Named(value = "tableView")
@SessionScoped
public class TableView implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> boolFilter = new ArrayList<String>();

    private String widgetVar = "_wv";                     //  Name of the client side widget.
    private String rowIndexVar = "_riv";                    // null String Name of iterator to refer each row index.

    private Object selection;                           //selection null Object Reference to the selection data.

    private Integer first = 0;                         // Index of the first row to be displayed
    private Integer rows = 10;

    private Boolean paginator = true;
    private String paginatorTemplate = "{RowsPerPageDropdown} {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {CurrentPageReport} {Exporters}";
    private String rowsPerPageTemplate = "5,10,15,20,30,40,50,100"; //Template of the rowsPerPage dropdown.
    private String rowsPerPageLabel = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("TableNumberRowPerPage");         // Label for the rowsPerPage dropdown.
    private String currentPageReportTemplate = "{startRecord} - {endRecord} sur {totalRecords}";
    private Integer pageLinks = 5;                         // Integer Maximum number of page links to display.
    private String paginatorPosition = "both";                    //  String Position of the paginator.
    private Boolean paginatorAlwaysVisible = true;                      // Boolean Defines if paginator should be hidden if total data count is less than number of rows per page.

    private Boolean scrollable = false;                     // Boolean Makes data scrollable with fixed header.
    private Integer scrollHeight = 800;                       //null Integer Scroll viewport height.
    private Integer scrollWidth = 500;                       //null Integer Scroll viewport width.

    private String selectionMode = "single";                 //String Enables row selection, valid values are “single” and “multiple”.
    private Boolean dblClickSelect = true;                      // false Boolean Enables row selection on double click.

    private String emptyMessage = ResourceBundle.getBundle(JsfUtil.BUNDLE).getString("EmptyList");   // String Text to display when there is no data to display. Alternative is emptyMessage facet.

    private Boolean liveScroll = false;                     // Boolean Enables live scrolling.
    private Integer scrollRows = 0;                         //Number of rows to load on live scroll.

    private Boolean resizableColumns = true;

    private Object sortBy;                           //null Object Property to be used for default sorting.
    private String sortOrder = "descending";             //“ascending” or “descending”.
    private String sortMode = "multiple";               //!< single or multiple

    private String lazy = "true";

    private Boolean liveResize = true;                     //!< Directly see rizesing
    private Boolean reflow = true;                     //!< Auto-agency
    private Boolean draggableColumns = true;                     //!< Activate draggable

    /**
     * Creates a new instance of RibbonView
     */
    public TableView() {
        boolFilter.add("");
        boolFilter.add("true");
        boolFilter.add("false");
    }

    public List<String> getBoolFilter() {
        return boolFilter;
    }

    public void setBoolFilter(List<String> boolFilter) {
        this.boolFilter = boolFilter;
    }

    public Boolean getPaginator() {
        return paginator;
    }

    public void setPaginator(Boolean paginator) {
        this.paginator = paginator;
    }

    public Boolean getResizableColumns() {
        return resizableColumns;
    }

    public void setResizableColumns(Boolean resizableColumns) {
        this.resizableColumns = resizableColumns;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getRowsPerPageTemplate() {
        return rowsPerPageTemplate;
    }

    public void setRowsPerPageTemplate(String rowsPerPageTemplate) {
        this.rowsPerPageTemplate = rowsPerPageTemplate;
    }

    public String getPaginatorTemplate() {
        return paginatorTemplate;
    }

    public void setPaginatorTemplate(String paginatorTemplate) {
        this.paginatorTemplate = paginatorTemplate;
    }

    public String getCurrentPageReportTemplate() {
        return currentPageReportTemplate;
    }

    public void setCurrentPageReportTemplate(String currentPageReportTemplate) {
        this.currentPageReportTemplate = currentPageReportTemplate;
    }

    public String getLazy() {
        return lazy;
    }

    public void setLazy(String lazy) {
        this.lazy = lazy;
    }

    public String getSortMode() {
        return sortMode;
    }

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }

    public String getWidgetVar() {
        return widgetVar;
    }

    public void setWidgetVar(String widgetVar) {
        this.widgetVar = widgetVar;
    }

    public String getRowsPerPageLabel() {
        return rowsPerPageLabel;
    }

    public void setRowsPerPageLabel(String rowsPerPageLabel) {
        this.rowsPerPageLabel = rowsPerPageLabel;
    }

    public Integer getPageLinks() {
        return pageLinks;
    }

    public void setPageLinks(Integer pageLinks) {
        this.pageLinks = pageLinks;
    }

    public String getPaginatorPosition() {
        return paginatorPosition;
    }

    public void setPaginatorPosition(String paginatorPosition) {
        this.paginatorPosition = paginatorPosition;
    }

    public Boolean getPaginatorAlwaysVisible() {
        return paginatorAlwaysVisible;
    }

    public void setPaginatorAlwaysVisible(Boolean paginatorAlwaysVisible) {
        this.paginatorAlwaysVisible = paginatorAlwaysVisible;
    }

    public Boolean getScrollable() {
        return scrollable;
    }

    public void setScrollable(Boolean scrollable) {
        this.scrollable = scrollable;
    }

    public Integer getScrollHeight() {
        return scrollHeight;
    }

    public void setScrollHeight(Integer scrollHeight) {
        this.scrollHeight = scrollHeight;
    }

    public Integer getScrollWidth() {
        return scrollWidth;
    }

    public void setScrollWidth(Integer scrollWidth) {
        this.scrollWidth = scrollWidth;
    }

    public String getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    public Object getSelection() {
        return selection;
    }

    public void setSelection(Object selection) {
        this.selection = selection;
    }

    public Boolean getDblClickSelect() {
        return dblClickSelect;
    }

    public void setDblClickSelect(Boolean dblClickSelect) {
        this.dblClickSelect = dblClickSelect;
    }

    public String getRowIndexVar() {
        return rowIndexVar;
    }

    public void setRowIndexVar(String rowIndexVar) {
        this.rowIndexVar = rowIndexVar;
    }

    public String getEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public Boolean getLiveResize() {
        return liveResize;
    }

    public void setLiveResize(Boolean liveResize) {
        this.liveResize = liveResize;
    }

    public Boolean getLiveScroll() {
        return liveScroll;
    }

    public void setLiveScroll(Boolean liveScroll) {
        this.liveScroll = liveScroll;
    }

    public Integer getScrollRows() {
        return scrollRows;
    }

    public void setScrollRows(Integer scrollRows) {
        this.scrollRows = scrollRows;
    }

    public Object getSortBy() {
        return sortBy;
    }

    public void setSortBy(Object sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Boolean getReflow() {
        return reflow;
    }

    public void setReflow(Boolean reflow) {
        this.reflow = reflow;
    }

    public Boolean getDraggableColumns() {
        return draggableColumns;
    }

    public void setDraggableColumns(Boolean draggableColumns) {
        this.draggableColumns = draggableColumns;
    }

    /**
     * ************************************************************************
     * Evènement double click sur une ligne du tableau permet d'ouvrir l'édition
     * de la non conformité pour visualisation. Une initialisation des
     * paramètres par défaut vérification de selected et des booléenes
     *
     * @throws IOException
     * ***********************************************************************
     */
    public void handleRowDoubleClick() throws IOException {
        // PREPARATION DE LA PAGE DE CHARGEMENT
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String dir = ec.getRequestPathInfo();
        String[] dirs = dir.split("/");
        dir = "/";
        for (int i = 0; i < dirs.length - 1; i++) {
            dir += dirs[i] + "/";
        }
        dir += "View.xhtml";
        ec.redirect(ec.getRequestContextPath() + ec.getRequestServletPath() + dir);
    }

    /**
     * ************************************************************************
     * Gestion des document au format XLS
     *
     * @param document
     * ***********************************************************************
     */
    public void handlePostProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            header.getCell(i).setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
        }
    }

}
