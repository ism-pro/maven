/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.cc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name="inputFilterDate")
@ViewScoped
public class InputFilterDate {
    
    private DateTwin date = new DateTwin();
    private String      input = "";

    public InputFilterDate() {
        date = new DateTwin();
    }
    
    
    
    public void handleChanged(SelectEvent e) {
        getDate();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Formatter formatter = new Formatter();
        formatter.format("%1$2s;%2$2s",
                df.format(date.begin),
                df.format(date.end));
        input = formatter.toString();
    }
    
    public void handleInput(String input){
        input = this.input;
    }
    
    
    
    

    public DateTwin getDate() {
        if(date==null)  date = new DateTwin();
        return date;
    }

    public void setDate(DateTwin date) {
        getDate();
        this.date = date;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    
    
    
    
    public class DateTwin {

        private Date begin = new Date();
        private Date end = new Date();

        DateTwin() {
        }

        public Date getBegin() {
            if (begin == null) {
                begin = new Date();
            }
            return begin;
        }

        public void setBegin(Date begin) {
            if (begin == null) {
                begin = new Date();
            }
            this.begin = begin;
        }

        public Date getEnd() {
            if (end == null) {
                end = new Date();
            }
            return end;
        }

        public void setEnd(Date end) {
            if (end == null) {
                end = new Date();
            }
            this.end = end;
        }

    }
}
