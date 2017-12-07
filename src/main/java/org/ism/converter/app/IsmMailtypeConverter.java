/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.converter.app;

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
import org.ism.entities.app.IsmMailtype;
import org.ism.jsf.app.IsmMailtypeController;
import org.ism.jsf.util.JsfUtil;

/**
 * <h1>IsmMailtypeConverter</h1><br>
 * IsmMailtypeConverter class
 *
 * @author r.hendrick
 *
 */
@ManagedBean
@SessionScoped
@FacesConverter(value = "ismMailtypeConverter")
public class IsmMailtypeConverter implements Converter, Serializable {

    @ManagedProperty(value = "#{ismMailtypeController}")
    IsmMailtypeController ismMailtypeController;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }

        try {
            Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            JsfUtil.out("IsmMailtypeConverter :  Impossible de convertir la valeur " + value + " en entier ! Erreur : " + ex.getLocalizedMessage());
            return null;
        }

        return ismMailtypeController.getIsmMailtype(getKey(value));
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
        if (object instanceof IsmMailtype) {
            IsmMailtype o = (IsmMailtype) object;
            return getStringKey(o.getId());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), IsmMailtype.class.getName()});
            return null;
        }
    }

    public void setIsmMailtypeController(IsmMailtypeController ismMailtypeController) {
        this.ismMailtypeController = ismMailtypeController;
    }

}
