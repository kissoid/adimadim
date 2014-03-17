/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.adimadim.db.entity.ContactMessage;

/**
 *
 * @author Adem
 */
@Stateless
public class ContactMessageFacade extends AbstractFacade<ContactMessage> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactMessageFacade() {
        super(ContactMessage.class);
    }
    
}
