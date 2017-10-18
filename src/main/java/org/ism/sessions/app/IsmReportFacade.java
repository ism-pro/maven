/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ism.sessions.app;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ism.entities.app.IsmReport;
import org.ism.sessions.AbstractFacade;

/**
 * IsmReportFacade class  
 *
 * @author r.hendrick
 */
@Stateless
public class IsmReportFacade extends AbstractFacade<IsmReport> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Container
    // 
    //
    // /////////////////////////////////////////////////////////////////////////


    // /////////////////////////////////////////////////////////////////////////
    //
    //
    // Getter / Setter
    // 
    //
    // /////////////////////////////////////////////////////////////////////////

    public IsmReportFacade() {
        super(IsmReport.class);
    }

    public List<IsmReport> findAllByLastChanged() {
        return null;
    }

    public List<IsmReport> findByCode(String _IsmReport) {
        return null;
    }

    public List<IsmReport> findByDesignation(String designation) {
        return null;
    }


}
