/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ism.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ism.entities.DocExplorer;

/**
 *
 * @author r.hendrick
 */
@Stateless
public class DocExplorerFacade extends AbstractFacade<DocExplorer> {
    @PersistenceContext(unitName = "ISM_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocExplorerFacade() {
        super(DocExplorer.class);
    }
    
}
