/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.generic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 1001299
 */
@Stateless
public class GenericFacade extends GenericAbstractFacade{

    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;
   
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
