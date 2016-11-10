/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.view.app;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.ism.listener.LogPhaseListener;

/**
 * <h1>ViewPhaseControl</h1>
 *
 * <h2>Description</h2>
 *
 *
 * @author r.hendrick
 */
@ManagedBean(name="viewPhaseControl")
@RequestScoped
public class ViewPhaseControl {

    /**
     * Corresponding to time for one step phase
     */
    private Integer stepPhasesMs;
    /**
     * Corresponding to time for rendering all phases from 1 to 6
     */
    private Integer phaseMs;
    /**
     * Correspondint to log of all complete phases
     */
    private List<Integer> phasesMs;

    public Integer getStepPhasesMs() {
        stepPhasesMs = LogPhaseListener.stepTimeMs;
        return stepPhasesMs;
    }

    public void setStepPhasesMs(Integer stepPhasesMs) {
        this.stepPhasesMs = stepPhasesMs;
    }

    public Integer getPhaseMs() {
        phaseMs = LogPhaseListener.phaseTimeMs;
        return phaseMs;
    }

    public void setPhasesMs(Integer phasesMs) {
        this.phaseMs = phasesMs;
    }

    public List<Integer> getPhasesMs() {
        this.phasesMs = LogPhaseListener.phasesTimeMs;
        return phasesMs;
    }

    public void setPhasesMs(List<Integer> phasesMs) {
        this.phasesMs = phasesMs;
    }
    
    public Integer phaseMsLast(){
        return getPhasesMs().get(getPhasesMs().size());
    }
    
    
    
}
