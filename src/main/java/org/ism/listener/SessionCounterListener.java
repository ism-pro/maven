/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.listener;

/**
 *
 * @author r.hendrick
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.ism.entities.hr.Staff;

public class SessionCounterListener implements HttpSessionListener {

    private static int totalActiveSessions = 0;               //!< Compteur
    private static int totalActiveSessionsAuthenticated = 0;  //!< Compteur du nombre d'utilisateur authentifié

    public static List<HttpSession> sessionList = new ArrayList<HttpSession>(); //!< List de toutes les sessions de l'application
    private static int sessionListSize = 0;                     //!< Conserve le nombre de sessions pour éviter de recalculer inutilement

    public static String AUTHENTICATED = "authenticated";
    public static String EPOCH_START = "EpochStart";
    public static String EPOCH_END = "EpochEnd";
    public static String ATT_STAFF = "astaff";

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    public static int getTotalActiveSessionsAuthenticated() {
        updateTotalActiveSessionAuthenticated();
        return totalActiveSessionsAuthenticated;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        totalActiveSessions++;
        sessionEvent.getSession().setAttribute(EPOCH_START, System.currentTimeMillis());
        sessionList.add(sessionEvent.getSession());
        //System.out.println("sessionCreated - " + arg0.getSession().getId() + " - add one session into counter. Counter = "+ totalActiveSessions +  " contre List = " + sessionList.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        totalActiveSessions--;
        sessionEvent.getSession().setAttribute(EPOCH_END, System.currentTimeMillis());
        sessionList.remove(sessionEvent.getSession());
        //System.out.println("sessionDestroyed - " + arg0.getSession().getId() + " - deduct one session from counter");
    }

    public static void updateTotalActiveSessionAuthenticated() {
        // System.out.println("Update Total Active Session Authenticated method !");
        //if (sessionListSize != sessionList.size()) {
        sessionListSize = 0;
        //System.out.println("SessionListSize different !");
        Iterator<HttpSession> sessionIterator = sessionList.iterator();
        while (sessionIterator.hasNext()) {
            HttpSession s = sessionIterator.next();
            //System.out.println("    In one session active counter");
            if (s.getAttribute(AUTHENTICATED) != null) {
                //System.out.println("    Authenticated attribut is define");
                if (((boolean) s.getAttribute(AUTHENTICATED))) {
                    sessionListSize++;
                    //System.out.println("    increment sessionList to " + sessionListSize);
                }
            }
        }
        //}
        totalActiveSessionsAuthenticated = sessionListSize;
    }

    public static List<Staff> getActiveStaff() {
        List<Staff> staffs = new ArrayList<>();
        Iterator<HttpSession> sessionIterator = sessionList.iterator();
        while (sessionIterator.hasNext()) {
            HttpSession s = sessionIterator.next();
            //System.out.println("    In one session active counter");
            if (s.getAttribute(AUTHENTICATED) != null) {
                //System.out.println("    Authenticated attribut is define");
                if (((boolean) s.getAttribute(AUTHENTICATED))) {
                    Staff staff = (Staff) s.getAttribute(ATT_STAFF); // define from staffAuthFacade
                    if (staff != null) {
                        staffs.add(staff);
                    }
                }
            }
        }
        return staffs;
    }

    public static Boolean removeActiveStaff(Staff activeStaff) {
        Iterator<HttpSession> sessionIterator = sessionList.iterator();
        while (sessionIterator.hasNext()) {
            HttpSession s = sessionIterator.next();
            //System.out.println("    In one session active counter");
            if (s.getAttribute(AUTHENTICATED) != null) {
                //System.out.println("    Authenticated attribut is define");
                if (((boolean) s.getAttribute(AUTHENTICATED))) {
                    Staff staff = (Staff) s.getAttribute(ATT_STAFF); // define from staffAuthFacade
                    if (staff != null) {
                        if (staff == activeStaff) {
                            s.invalidate();
                            return true;
                        }
                    }
                }
            }
        }
        return false;

    }

    /**
     * Session duration all to return amount of milli second of a session
     * duration corresponding to a specific staff.
     *
     * @param staff on which to define the amount of duration time
     * @return the duration time since the session start
     */
    public static long sessionDuration(Staff staff) {
        for (HttpSession s : sessionList) {
            if (s.getAttribute(AUTHENTICATED) != null) {
                if (((boolean) s.getAttribute(AUTHENTICATED))) {
                    Staff staffIn = (Staff) s.getAttribute(ATT_STAFF);
                    if (staffIn == staff) {
                        return System.currentTimeMillis() - Long.valueOf(s.getAttribute(EPOCH_START).toString());
                    }
                }
            }
        }
        return 0;
    }
    
    public static List<HttpSession> getSessionList(){
        return sessionList;
    }
}
