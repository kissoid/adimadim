/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.db.entity.TeamType;
import org.adimadim.facade.TeamFacade;
import org.adimadim.facade.TeamMemberFacade;
import org.adimadim.facade.TeamTypeFacade;

/**
 *
 * @author Adem
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless(name = "teamService")
public class TeamService {

    @Inject
    private TeamFacade teamFacade;
    @Inject
    private TeamTypeFacade teamTypeFacade;
    @Inject
    private TeamMemberFacade teamMemberFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Team saveTeam(Team team) throws Exception {
        if (team.getTeamId() == null) {
            team = teamFacade.saveAndReturn(team);
        } else {
            team = teamFacade.updateAndReturn(team);
        }
        return team;
    }

    public List<Team> findTeamsByRaceId(Integer raceId) throws Exception {
        String jpql = "select t from Team t where t.race.raceId = :raceId ";
        Map<String, Integer> parameters = new HashMap<String, Integer>();
        parameters.put("raceId", raceId);
        return teamFacade.findListByQuery(jpql, parameters);
    }

    public List<TeamType> findTeamTypes() throws Exception {
        String jpql = "select t from TeamType t order by t.teamTypeId ";
        return teamTypeFacade.findListByQuery(jpql);
    }

    public Team findTeamById(Integer teamId) throws Exception {
        return teamFacade.find(teamId);
    }
    
    public TeamMember findTeamMemberByAccountIdRaceId(Integer accountId, Integer raceId) throws Exception{
        String jpql = "select t from TeamMember t where t.account.accountId = :accountId and t.team.race.raceId = :raceId ";
        Map<String, Integer> parameters = new HashMap<String, Integer>();
        parameters.put("accountId", accountId);
        parameters.put("raceId", raceId);
        return teamMemberFacade.findByQuery(jpql, parameters); 
    }
    
}
