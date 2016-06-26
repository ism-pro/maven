/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ism.entities.IsmNcastate;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class IsmNcastateFacade extends AbstractFacade<IsmNcastate> {

    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IsmNcastateFacade() {
        super(IsmNcastate.class);
    }
    
}
