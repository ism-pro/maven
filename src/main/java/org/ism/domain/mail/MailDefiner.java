/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.domain.mail;

import java.util.ArrayList;
import java.util.List;
import org.ism.entities.hr.Staff;

/**
 * MailDefiner class  
 *
 * @author r.hendrick
 */
public class MailDefiner {

    List<Staff> tos = new ArrayList<>();
    List<Staff> ccs = new ArrayList<>();
    List<Staff> ccis = new ArrayList<>();
    
    /**
     * Rclam are preceded by r
     */
    List<Staff> rtos = new ArrayList<>();
    List<Staff> rccs = new ArrayList<>();
    List<Staff> rccis = new ArrayList<>();

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////
    
    public String tosAsString(){
        String str = "";
        if(tos==null) return "";
        for(Staff to : tos){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }
    
    public String ccsAsString(){
        String str = "";
        if(ccs==null) return "";
        for(Staff to : ccs){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }
    
    public String ccisAsString(){
        String str = "";
        if(ccis==null) return "";
        for(Staff to : ccis){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }
    
    public String tosReclamAsString(){
        String str = "";
        if(rtos==null) return "";
        for(Staff to : rtos){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }
    
    public String ccsReclamAsString(){
        String str = "";
        if(rccs==null) return "";
        for(Staff to : rccs){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }
    
    public String ccisReclamAsString(){
        String str = "";
        if(rccis==null) return "";
        for(Staff to : rccis){
            if(to.getStMaillist()!=null && !to.getStMaillist().isEmpty()){
                str += to.getStMaillist() + ";";
            }
        }
        return str;
    }

    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////

    public List<Staff> getTos() {
        return tos;
    }

    public void setTos(List<Staff> tos) {
        this.tos = tos;
    }

    public List<Staff> getCcs() {
        return ccs;
    }

    public void setCcs(List<Staff> ccs) {
        this.ccs = ccs;
    }

    public List<Staff> getCcis() {
        return ccis;
    }

    public void setCcis(List<Staff> ccis) {
        this.ccis = ccis;
    }

    public List<Staff> getRtos() {
        return rtos;
    }

    public void setRtos(List<Staff> rtos) {
        this.rtos = rtos;
    }

    public List<Staff> getRccs() {
        return rccs;
    }

    public void setRccs(List<Staff> rccs) {
        this.rccs = rccs;
    }

    public List<Staff> getRccis() {
        return rccis;
    }

    public void setRccis(List<Staff> rccis) {
        this.rccis = rccis;
    }
    

}
