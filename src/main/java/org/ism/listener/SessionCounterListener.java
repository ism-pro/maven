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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.ism.entities.hr.Staff;
import org.ism.jsf.hr.StaffAuthController;
import org.ism.jsf.hr.StaffController;

public class SessionCounterListener implements HttpSessionListener {

    private static int totalActiveSessions = 0;               //!< Compteur
    private static int totalActiveSessionsAuthenticated = 0;  //!< Compteur du nombre d'utilisateur authentifié

    private static List<HttpSession> sessionList = new ArrayList<HttpSession>(); //!< List de toutes les sessions de l'application
    private static int sessionListSize = 0;                     //!< Conserve le nombre de sessions pour éviter de recalculer inutilement

    public static int getTotalActiveSession() {
        return totalActiveSessions;
    }

    public static int getTotalActiveSessionsAuthenticated() {
        updateTotalActiveSessionAuthenticated();
        return totalActiveSessionsAuthenticated;
    }

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        totalActiveSessions++;
        sessionList.add(arg0.getSession());
        //System.out.println("sessionCreated - " + arg0.getSession().getId() + " - add one session into counter. Counter = "+ totalActiveSessions +  " contre List = " + sessionList.size());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        totalActiveSessions--;
        sessionList.remove(arg0.getSession());
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
            if (s.getAttribute("authenticated") != null) {
                //System.out.println("    Authenticated attribut is define");
                if (((boolean) s.getAttribute("authenticated"))) {
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
            if (s.getAttribute("authenticated") != null) {
                //System.out.println("    Authenticated attribut is define");
                if (((boolean) s.getAttribute("authenticated"))) {
                    Staff staff = (Staff) s.getAttribute("astaff"); // define from staffAuthFacade
                    if (staff != null) {
                        staffs.add(staff);
                    }
                }
            }
        }
        return staffs;
    }
}
