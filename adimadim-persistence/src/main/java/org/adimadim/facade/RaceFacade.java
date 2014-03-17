/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.adimadim.db.entity.Race;

/**
 *
 * @author Adem
 */
@Stateless
public class RaceFacade extends AbstractFacade<Race> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RaceFacade() {
        super(Race.class);
    }
    
}
