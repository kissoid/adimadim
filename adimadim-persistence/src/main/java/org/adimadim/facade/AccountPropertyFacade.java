/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.adimadim.db.entity.AccountProperty;

/**
 *
 * @author Adem
 */
@Stateless
public class AccountPropertyFacade extends AbstractFacade<AccountProperty> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountPropertyFacade() {
        super(AccountProperty.class);
    }
    
}
