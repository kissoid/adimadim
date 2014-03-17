/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.adimadim.db.entity.TeamMember;

/**
 *
 * @author Adem
 */
@Stateless
public class TeamMemberFacade extends AbstractFacade<TeamMember> {
    @PersistenceContext(unitName = "adimadimPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamMemberFacade() {
        super(TeamMember.class);
    }
    
}
