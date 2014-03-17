/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.validator;

import org.adimadim.db.entity.Team;
import org.adimadim.service.exception.RaceException;

/**
 *
 * @author Adem
 */
public class TeamValidator extends AbstractValidator{
    
    public synchronized static void validateTeamForCreation(Team team) throws RaceException {
        if (isStringEmpty(team.getTeamName())) {
            throw new RaceException("Takım adı boş olamaz.");
        }
    }
    
}
