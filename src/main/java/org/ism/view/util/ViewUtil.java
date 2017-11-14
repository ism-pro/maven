/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.view.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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

    /**
     * Method permettant de traduire la variable de deux date
     *
     * @param value a value
     * @param filter a object filtred
     * @param locale a local
     * @return true if ok
     * @throws ParseException an error
     */
    public boolean handleDateRangeFilters(Object value, Object filter, Locale locale) throws ParseException {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        if (value == null) {
            return false;
        }

        //{"start":"2016-04-18","end":"2016-05-31"}
        if (!filterText.contains("start")) {
            return false;
        }
        String strDate = filterText;
        strDate = strDate.replace("\"", "").replace(":", "")
                .replace("{", "").replace("}", "")
                .replace("start", "").replace("end", "");
        String dates[] = strDate.split(",");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
        Date begin = format.parse(dates[0]);
        Date end = format.parse(dates[1]);

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

    /**
     * yearsFrom allow to calculate the number of accomplished years from a
     * specified date till now.
     * <br>
     * This method is useful for example while your need to compute the
     * accomplished age.
     *
     * @param date from which to calculate the year from now. It has to be
     * bigger than now.
     * @return -1 if error, 0 if date is null, otherwise the correct
     * accomplished years
     */
    public Integer yearsFrom(Date date) {
        if (date == null) {
            return 0;
        }

        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long dt = date.toInstant().toEpochMilli();

        if (dt > now) {
            return -1;
        }

        long yearsl = now - dt;

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(yearsl), ZoneId.systemDefault()).getYear() - 1970;
    }

    /**
     * daysOverYearsFrom allow to calculate the number of accomplished days from
     * a specified date till now.
     * <br>
     * This method is useful for example while your need to compute the
     * accomplished days over years of age.
     *
     * @param date from which to calculate the days from now. It has to be
     * bigger than now.
     * @return -1 if error, 0 if date is null, otherwise the correct
     * accomplished days over years
     */
    public Integer daysOverYearsFrom(Date date) {
        if (date == null) {
            return 0;
        }

        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long dt = date.toInstant().toEpochMilli();

        if (dt > now) {
            return -1;
        }

        long yearsl = now - dt;

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(yearsl), ZoneId.systemDefault()).getDayOfYear();
    }

    /**
     * Methodd permettand de convertir une list en entier en double method utile
     * pour utilisation des graphique
     *
     * @param integerList list d'entier à convertir en double
     * @return liste des entiers ayant été converti en double
     */
    public static List<Double> IntToDoubleList(List<Integer> integerList) {
        List<Double> l = new ArrayList<>();
        Iterator<Integer> iter = integerList.iterator();
        while (iter.hasNext()) {
            l.add(1.00 * iter.next());
        }
        return l;
    }

    /**
     * Convert a duraction in in the way of day, hour, min, sec
     *
     * @param milliSec correspond to a time specefied in millesecond
     * @return a string formated like ##j ##:##.30
     */
    public String durationFromMilliSec(Long milliSec) {

        Integer value = Integer.valueOf(milliSec.toString()) / 1000;
        Integer sec = (value % 60);
        Integer min = (value / 60) % 60;
        Integer hour = (value / (60 * 60)) % 24;
        Integer day = (value / (60 * 60 * 24));

        String date = "";
        if (day > 0) {
            date += day + "j ";
        }
        if (hour > 0) {
            date += hour + "h ";
        }
        if (min > 0) {
            date += min + "' ";
        }
        date += sec + "''";

        return date;
    }

    public static final Integer PART_DAY = 0;
    public static final Integer PART_HOUR = 1;
    public static final Integer PART_MIN = 2;
    public static final Integer PART_SEC = 3;

    /**
     * Get part of a duration specify in milli seconde
     *
     * @param milliSec a number of milli seconde
     * @param part one of PART_DAY, PART_HOUR, PART_MIN, PART_SEC
     * @return the corresponding value of the part if recognized otherwise value
     * in seconde
     */
    public Integer durationPartFromMilliSec(Long milliSec, Integer part) {
        Integer value = Integer.valueOf(milliSec.toString()) / 1000;

        switch (part) {
            case 0:
                return (value / (60 * 60 * 24));
            case 1:
                return (value / (60 * 60)) % 24;
            case 2:
                return (value / 60) % 60;
            case 3:
                return (value % 60);
            default:
                return value;
        }
    }
    
    public Integer durationPFMS(Long milliSec, Integer part){
        return durationPartFromMilliSec(milliSec, part);
    }
}
