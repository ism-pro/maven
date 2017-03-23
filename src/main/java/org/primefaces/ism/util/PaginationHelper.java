package org.primefaces.ism.util;

import java.util.List;
import javax.faces.model.DataModel;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class PaginationHelper extends Object {

    /**
     * Number of rows contain in a page
     */
    private int pageSize = 10;
    /**
     * Current page secance
     */
    private int page;
    private DataModel model = null;
    List<SortMeta> sortMeta = null;
    List<FilterMeta> filterMeta = null;

    public PaginationHelper() {
    }

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract int getItemsCount();

    public abstract DataModel createPageDataModel();

    public abstract DataModel sortBy();

    public int getPageFirstItem() {
        return page * pageSize;
    }

    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }

    public void nextPage() {
        if (isHasNextPage()) {
            page++;
            model = createPageDataModel();
        }
    }

    public boolean isHasPreviousPage() {
        return page > 0;
    }

    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
            model = createPageDataModel();
        }
    }

    public void seekToPage(Integer numPage) {
        if (numPage > page) {
            if (numPage >= getNumberOfPage()) {
                page = getNumberOfPage();
            } else {
                page = numPage;
            }
            model = createPageDataModel();
        } else if (numPage < page) {
            if (numPage < 0) {
                page = 0;
            } else {
                page = numPage;
            }
            model = createPageDataModel();
        }
    }

    public Integer getNumberOfPage() {
        Integer nbPage = (Integer) Math.floorDiv(getItemsCount(), pageSize);
        return nbPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public List<SortMeta> getSortMeta() {
        return sortMeta;
    }

    public void setSortMeta(List<SortMeta> sortMeta) {
        this.sortMeta = sortMeta;
    }

    public List<FilterMeta> getFilterMeta() {
        return filterMeta;
    }

    public void setFilterMeta(List<FilterMeta> filterMeta) {
        this.filterMeta = filterMeta;
    }

}
