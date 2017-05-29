/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.converter;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.ism.domain.Theme;
import org.ism.services.ThemeService;

/**
 *
 * @author r.hendrick
 */
@FacesConverter("themeConverter")
public class ThemeConverter implements Converter {

    @ManagedProperty("#{themeService}")
    private ThemeService service;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                //service = (ThemeService) fc.getExternalContext().getApplicationMap().get("themeService");
                if (service == null) {
                    service = new ThemeService();
                }
                service.init();
                Theme t = service.getStrTheme(value.toLowerCase());
                return t;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            Theme t = (Theme) object;
            return t.getDisplayName();
        } else {
            return null;
        }
    }
}
