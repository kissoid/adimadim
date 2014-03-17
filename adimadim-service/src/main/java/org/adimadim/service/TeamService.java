/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.adimadim.db.entity.Team;
import org.adimadim.facade.TeamFacade;

/**
 *
 * @author Adem
 */
@Stateless
public class TeamService {

    @Inject 
    private TeamFacade teamFacade;

    public List<Team> retrieveAllTeamsByRaceId(Integer raceId) throws Exception {
        Map map = new HashMap();
        map.put("raceId", raceId);
        return teamFacade.findAllByNamedQuery("Team.findAllByAceId", map, null);
    }
    
}
