/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.listener;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.ism.jsf.util.JsfUtil;
/**
 * <h1>LogPhaseListener</h1>
 * This is a phase listener message which allow to compute rendering time when
 * developping.
 *
 * @see http://www.andygibson.net/blog/tutorial/timing-jsf-requests-using-a-phase-listener/
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
        
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            long endTime = System.nanoTime();
            long diffMs = (long) ((endTime - startTime) * 0.000001);
            long diffStepTimeMs = (long) ((endTime - stepTime) * 0.000001);
            phaseTimeMs = Long.valueOf(diffMs).intValue();
            stepTimeMs = Long.valueOf(diffStepTimeMs).intValue();
            if(phasesTimeMs==null)  phasesTimeMs = new ArrayList<>();
            phasesTimeMs.add(phaseTimeMs);
            JsfUtil.out("P[" + event.getPhaseId() + "] :  Ts[" + diffStepTimeMs + "]ms Tt[" + diffMs + "]ms");
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