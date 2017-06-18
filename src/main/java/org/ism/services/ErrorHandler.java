/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.services;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "errorHandlerController")
@ViewScoped
public class ErrorHandler {

    public String getStatusCode() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.status_code");
        if (obj != null) {
            String val = String.valueOf((Integer) obj);
            return val;
        }
        return "Status code Undef";
    }

    public String getMessage() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.message");
        if (obj != null) {
            String val = obj.toString();
            return val;
        }
        return "Message Undef";
    }

    public String getExceptionType() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.exception_type");
        if (obj != null) {
            String val = obj.toString();
            return val;
        }
        return "Exception Type Undef";
    }

    public String getException() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.exception");
        if (obj != null) {
            Exception exception = (Exception) obj;
            String val = exception.toString();
            return val;
        }
        return "Exception Undef";
    }

    public String getRequestURI() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.request_uri");
        if (obj != null) {
            String val = obj.toString();
            return val;
        }
        return "URI Undef";
    }

    public String getServletName() {
        Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Object obj = map.get("javax.servlet.error.servlet_name");
        if (obj != null) {
            String val = obj.toString();
            return val;
        }
        return "ServletName Undef";
    }

}
