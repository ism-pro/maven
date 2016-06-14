/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.cc;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputtext.InputText;

/**
 *
 * @author r.hendrick
 */
@FacesComponent(createTag = true, tagName = "dateRangeFilter")
public class DateRangeFilter extends UIInput  implements NamingContainer {

    public final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private InputText dateRange = new InputText();
    private Calendar beginCalendar = new Calendar();
    private Calendar endCalendar = new Calendar();

    @Override
    public String getFamily() {
        return InputText.COMPONENT_FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        logger.log(Level.FINEST, "encodeBegin(FacesContext)");
        //encodeComponent(context);
        super.encodeBegin(context);
    }

    private void encodeComponent(FacesContext context) throws IOException {
        beginCalendar.setValue((Object)new Date());
        beginCalendar.setStyle("width: 20px;");
        
        endCalendar.setValue((Object)new Date());
        beginCalendar.setStyle("width: 20px;");
        
        dateRange.setValue((Object)beginCalendar.getValue().toString() + ";" + endCalendar.getValue().toString());
        
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", null);
        
        beginCalendar.encodeAll(context);
        
        dateRange.encodeAll(context);
        endCalendar.encodeAll(context);
        
        writer.endElement("div");
    }
}
