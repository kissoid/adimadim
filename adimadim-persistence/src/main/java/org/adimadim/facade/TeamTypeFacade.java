/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.adimadim.db.entity.TeamType;

/**
 *
 * @author Adem
 */
@Stateless
public class TeamTypeFacade extends AbstractFacade<TeamType> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamTypeFacade() {
        super(TeamType.class);
    }
    
}
