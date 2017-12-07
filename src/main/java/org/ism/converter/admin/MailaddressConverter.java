/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.converter.admin;

import org.ism.converter.admin.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.ism.entities.admin.Mailaddress;
import org.ism.jsf.admin.MailaddressController;
import org.ism.jsf.util.JsfUtil;

/**
 * <h1>MailaddressConverter</h1><br>
 * MailaddressConverter class
 *
 * @author r.hendrick
 *
 */
@ManagedBean
@SessionScoped
@FacesConverter(value = "mailaddressConverter")
public class MailaddressConverter implements Converter, Serializable {
    
    @ManagedProperty(value = "#{mailaddressController}")
    MailaddressController mailaddressController;
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        
        
        try {
            Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            JsfUtil.out("MailaddressConverter :  Impossible de convertir la valeur " + value + " en entier ! Erreur : " + ex.getLocalizedMessage());
            return null;
        }

        return mailaddressController.getMailaddress(getKey(value));
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
        if (object instanceof Mailaddress) {
            Mailaddress o = (Mailaddress) object;
            return getStringKey(o.getMaId());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Mailaddress.class.getName()});
            return null;
        }
    }
    
    
    public void setMailaddressController(MailaddressController mailaddressController){
        this.mailaddressController = mailaddressController;
    }

}
