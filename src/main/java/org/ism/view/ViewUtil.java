/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author r.hendrick
 */
@ManagedBean(name = "viewUtil")
@ViewScoped
public class ViewUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of FilterNCRequestView
     */
    public ViewUtil() {
    }

    /**
     * Init. processus controller
     */
    @PostConstruct
    public void init() {
    }

    /**
     *
     * @return
     */
    public Date getMaintenant() {
        return getNow();
    }

    /**
     * This is the date time when request is made
     *
     * @return the actual request date time
     */
    public Date getNow() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Give the actual date and time
     *
     * @return the actual day and time
     */
    public Date getToday() {
        LocalDateTime ldt = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.systemDefault());
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Give the actual year
     *
     * @return the actual year
     */
    public Integer getNowYear() {
        LocalDateTime ldt = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.systemDefault());
        return ldt.getYear();
    }

    /**
     * Give the actual month value
     *
     * @return the actual month value
     */
    public Integer getNowMonth() {
        LocalDateTime ldt = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.systemDefault());
        return ldt.getMonthValue();
    }
    

    /**
     * Give the actual day of the month
     *
     * @return the actual day of the month
     */
    public Integer getNowDayOfMonth() {
        LocalDateTime ldt = LocalDateTime.ofInstant((new Date()).toInstant(), ZoneId.systemDefault());
        return ldt.getDayOfMonth();
    }
    
    /**
     * This method recrate the local date and time of today with last time
     * 23:59:59
     *
     * @return the actual day with last time of the day 23:59:59
     */
    public Date getTodayLastTime() {
        LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(Integer year, Integer month, Integer day, Integer hour, Integer min, Integer sec) {
        LocalDateTime ldt = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(23, 59, 59));
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(Integer year, Integer month, Integer day, Integer hour, Integer min) {
        return toDate(year, month, day, hour, min, 0);
    }

}
