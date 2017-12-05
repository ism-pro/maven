/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ism.entities.hr.Staff;
import org.ism.jsf.util.JsfUtil;
import static org.ism.listener.SessionCounterListener.ATT_STAFF;
import static org.ism.listener.SessionCounterListener.AUTHENTICATED;

/**
 * <h1>LogPhaseListener</h1>
 * This is a phase listener message which allow to compute rendering time when
 * developping.
 *
 * @see
 * http://www.andygibson.net/blog/tutorial/timing-jsf-requests-using-a-phase-listener/
 * @author r.hendrick
 */
public class LogPhaseListener implements PhaseListener {

    public long startTime;
    public long stepTime;
    public static Integer phaseTimeMs = 0;
    public static Integer stepTimeMs = 0;
    public static List<Integer> phasesTimeMs;

    @Override
    public void afterPhase(PhaseEvent event) {

        switch (event.getPhaseId().getOrdinal()) {
            case 1:
                JsfUtil.out("--+ After Phase >> " + event.getPhaseId() + " start processing ");

                Iterator<HttpSession> sessionIterator;
                sessionIterator = SessionCounterListener.getSessionList().iterator();
                while (sessionIterator.hasNext()) {
                    HttpSession s = sessionIterator.next();
                    //System.out.println("    In one session active counter");
                    if (s.getAttribute(AUTHENTICATED) != null) {
                        ExternalContext ec = event.getFacesContext().getExternalContext();
                        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
                        String path = ec.getRequestPathInfo();
                        if (path.contains("company") && request.isRequestedSessionIdValid()) {
                            try {
                                JsfUtil.out(ec.getRequestContextPath()
                                        + " PathInfos :" + ec.getRequestPathInfo());
                                ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml?faces-redirect=true");
                            } catch (IOException ex) {
                                Logger.getLogger(LogPhaseListener.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //s.invalidate();
                        }
                    }
                }

                JsfUtil.out("--+ After Phase >> " + event.getPhaseId() + " end processing");
                break;
            case 6: {
                JsfUtil.out("--+ After Phase >> " + event.getPhaseId() + " start processing");
                long endTime = System.nanoTime();
                long diffMs = (long) ((endTime - startTime) * 0.000001);
                long diffStepTimeMs = (long) ((endTime - stepTime) * 0.000001);
                phaseTimeMs = Long.valueOf(diffMs).intValue();
                stepTimeMs = Long.valueOf(diffStepTimeMs).intValue();
                if (phasesTimeMs == null) {
                    phasesTimeMs = new ArrayList<>();
                }
                phasesTimeMs.add(phaseTimeMs);
                JsfUtil.out("P[" + event.getPhaseId() + "] :  Ts[" + diffStepTimeMs + "]ms Tt[" + diffMs + "]ms");

                JsfUtil.out("--+ After Phase >> " + event.getPhaseId() + " end processing");
            }
            break;
            default:
                JsfUtil.out("After Phase >> " + event.getPhaseId() + " is not managed !");
                break;
        }

    }

    @Override
    public void beforePhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW) {
            startTime = System.nanoTime();
        }
        stepTime = System.nanoTime();
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
