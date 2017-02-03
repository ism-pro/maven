/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.jsf.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * <h1>Util</h1>
 * <p>
 * This class coverts
 * </p>
 *
 *
 * @author r.hendrick
 */
public class Util {

    static public boolean debug = true;

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
     * @param group groupe
     * @param msg message to be display
     */
    public static void out(String group, String msg) {
        if (!debug) {
            return;
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        System.out.println(group + " " + df.format(new Date()) + " >> " + msg);
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Object getController(String controller) {
        if (controller == null || controller.isEmpty()) {
            return null;
        }
        FacesContext ctx = getFacesContext();
        return ctx.getApplication().getELResolver().getValue(ctx.getELContext(), null, controller);
    }

    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    public static UIViewRoot getViewRoot() {
        return getFacesContext().getViewRoot();
    }

    public static void setLocale(Locale locale) {
        getViewRoot().setLocale(locale);
    }

    public static ResourceBundle getResourceBundle(String key) {
        return getApplication().getResourceBundle(getFacesContext(), key);
    }
}
