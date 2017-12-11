package org.ism.jsf.util;

import com.sun.faces.component.visit.FullVisitContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

public class JsfUtil {

    public static final String BUNDLE = "/com/ISM";
    public static final String SECURITY = "/com/Security";
    public static final String RSCDIR = "/ISM/rsc/";
    public static Boolean debug = true;
    public static final String defaultThemeName = "ism";
    /**
     * Default setup session max inactive inteval time in sec (300s = 5min)
     */
    public static final Integer sessionMaxInactiveIntervalDefault = 300;

    public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {
        int size = selectOne ? entities.size() + 1 : entities.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        if (selectOne) {
            items[0] = new SelectItem("", "---");
            i++;
        }
        for (Object x : entities) {
            items[i++] = new SelectItem(x, x.toString());
        }
        return items;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static void addErrorMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage("errorMsg", facesMsg);
    }

    public static void addErrorMessage(Exception ex, String summary, String defaultDetail) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(summary, msg);
        } else {
            addErrorMessage(summary, defaultDetail);
        }
    }

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addErrorMessageId(String id, String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
    }

    public static FacesMessage addErrorMessage(String id, String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(id, facesMsg);
        return facesMsg;
    }

    public static void addSuccessMessage(String summury, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summury, detail);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
        String theId = JsfUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static enum PersistAction {
        CREATE,
        DELETE,
        UPDATE
    }

    /**
     * Logging message on server
     *
     * @param msg message to be display
     */
    public static void out(String msg) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(df.format(new Date()) + " >> " + msg);
    }

    /**
     * Logging message on server
     *
     * @param msg message to be display
     * @param Group a group
     */
    public static void out(String msg, String Group) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(Group + " " + df.format(new Date()) + " >> " + msg);
    }

    /**
     * Allow to retrive a component specify by an id
     *
     * @param id defined html id
     * @return the corresponding component of the id
     */
    public static UIComponent findComponent(final String id) {
        final UIComponent[] found = new UIComponent[1];

        FacesContext.getCurrentInstance().getViewRoot().
                visitTree(new FullVisitContext(FacesContext.getCurrentInstance()), new VisitCallback() {
                    @Override
                    public VisitResult visit(VisitContext context, UIComponent component) {
                        if (component.getId().equals(id)) {
                            found[0] = component;
                            return VisitResult.COMPLETE;
                        }
                        return VisitResult.ACCEPT;
                    }
                });

        return found[0];

    }

    /**
     * Date Range In return true or fase depending respectively if a date range
     * spécified by value is in the range or not
     * 
     * @param value a value is the value to be filtrated
     * @param filter a object is the spécified object by the user
     * @param locale a local give local informations
     * @return true if ok
     */
    public static boolean dateRangeIn(Object value, Object filter, Locale locale)  {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }

        //{"start":"2016-04-18","end":"2016-05-31"}
        if (!filterText.contains(",")) {
            return false;
        }
        String strDate = filterText;
        strDate = strDate.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date begin = null;
        Date end = null;
        try {
            begin = format.parse(dates[0]);
            end = format.parse(dates[1]);
        } catch (ParseException ex) {
            //Logger.getLogger(JsfUtil.class.getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage("Erreur suivante lors du filtrage de la période de date", ex.getLocalizedMessage());
        }
        

        //Extend limite in order to make it containt
        Calendar calValue = Calendar.getInstance();
        Calendar calBegin = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calValue.setTime((Date) value);
        calBegin.setTime((Date) begin);
        calEnd.setTime((Date) end);
        calBegin.add(Calendar.DAY_OF_MONTH, -1);
        calEnd.add(Calendar.DAY_OF_MONTH, +1);
        begin = calBegin.getTime();
        end = calEnd.getTime();

        //Check contain
        if (value instanceof Date) {
            Date date = (Date) value;
            if (date.before(begin) && !date.equals(begin)) {
                return false;
            }
            if (date.after(end) && !date.equals(end)) {
                return false;
            }
            return true;
        }
        return false;
    }

}
