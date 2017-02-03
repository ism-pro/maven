package org.ism.jsf.process.ctrl;

import org.ism.entities.process.ctrl.AnalyseData;
import org.ism.jsf.util.JsfUtil;
import org.ism.jsf.util.PaginationHelper;
import org.ism.sessions.process.ctrl.AnalyseDataFacade;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.ism.entities.process.ctrl.AnalyseAllowed;
import org.ism.entities.process.ctrl.AnalysePoint;
import org.ism.entities.process.ctrl.AnalyseType;

@Named("analyseDataControllerJsf")
@SessionScoped
public class AnalyseDataControllerJsf implements Serializable {

    private AnalyseData current;
    private DataModel items = null;
    @EJB
    private org.ism.sessions.process.ctrl.AnalyseDataFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public AnalyseDataControllerJsf() {
    }

    public AnalyseData getSelected() {
        if (current == null) {
            current = new AnalyseData();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AnalyseDataFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (AnalyseData) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new AnalyseData();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnalyseDataCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (AnalyseData) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnalyseDataUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (AnalyseData) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AnalyseDataDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    
    public List<AnalyseData> getItemsByLastChanged() {
        return getFacade().findAllByLastChanged();
    }

    public List<AnalyseData> getItemsByAnalyseData(String _AnalyseData) {
        return getFacade().findByCode(_AnalyseData);
    }

    public List<AnalyseData> getItemsByDesignation(String designation) {
        return getFacade().findByDesignation(designation);
    }

    public List<AnalyseData> getItemsByPointType(AnalysePoint point, AnalyseType type) {
        return getFacade().findByPointType(point, type);
    }

    public List<AnalyseData> getItemsByPointTypeSampleTimeRange(AnalysePoint point, AnalyseType type, Date from, Date to) {
        return getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeD</h3>
     * Surcharge method to have a double collection list that take following
     * parameters. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRange
     *
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @return List of all String value
     */
    public List<Double> getItemsByPointTypeSampleTimeRangeD(AnalysePoint point, AnalyseType type, Date from, Date to) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<Double> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((ad) -> {
            values.add(ad.getAdValue());
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRange
     *
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @return List of all String value
     */
    public HashMap<String, Double> getItemsByPointTypeSampleTimeRangeMap(AnalysePoint point, AnalyseType type, Date from, Date to) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        HashMap<String, Double> values = new HashMap<>();
        // Loop on iterator
        datas.stream().forEach((ad) -> {
            values.put(ad.getAdsampleTime().toString(), ad.getAdValue());
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRangeLimite
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @param limit is one of HH, H, B, BB
     * @return all value in a list
     */
    public List<Double> getItemsByPointTypeSampleTimeRangeLimite(AnalysePoint point, AnalyseType type, Date from, Date to, String limit) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<Double> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((AnalyseData ad) -> {
            switch (limit) {
                case "HH":
                    values.add(ad.getAdlimitHH());
                    break;
                case "H":
                    values.add(ad.getAdlimitH());
                    break;
                case "B":
                    values.add(ad.getAdlimitB());
                    break;
                case "BB":
                    values.add(ad.getAdlimitBB());
                    break;
                default:
                    break;
            }
        });
        return values;
    }

    /**
     * <h3>getItemsByPointTypeSampleTimeRangeMap</h3>
     * Surcharge method to have a map that containt sampleTime and corresponding
     * value. This method check if there is returned value otherwise return
     * null.
     *
     * @see getItemsByPointTypeSampleTimeRangeLimite
     *
     * @param point the sample point of analysis
     * @param type this type of analysis at this point
     * @param from date from
     * @param to date to
     * @param pattern the partern
     * @return list of allow date
     */
    public List<String> getItemsByPointTypeSampleTimeRangeSampleDate(AnalysePoint point, AnalyseType type, Date from, Date to, String pattern) {
        List<AnalyseData> datas = getFacade().findByPointTypeSampleTimeRange(point, type, from, to);
        // Save from null pointer
        if (datas == null) {
            return null;
        }
        // Preapare a string list
        List<String> values = new ArrayList<>();
        // Loop on iterator
        datas.stream().forEach((AnalyseData ad) -> {
            Date date = ad.getAdsampleTime();
            values.add(DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault()).format(date));
        });
        return values;
    }

    public List<AnalyseData> getItemsAvailableSelectManyList() {
        return getFacade().findAll();
    }

    public List<AnalyseData> getItemsAvailableSelectOneList() {
        return getFacade().findAll();
    }



    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public AnalyseData getAnalyseData(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = AnalyseData.class)
    public static class AnalyseDataControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AnalyseDataControllerJsf controller = (AnalyseDataControllerJsf) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "analyseDataControllerJsf");
            return controller.getAnalyseData(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AnalyseData) {
                AnalyseData o = (AnalyseData) object;
                return getStringKey(o.getAdId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AnalyseData.class.getName());
            }
        }

    }

}
