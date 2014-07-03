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
import javax.persistence.LockModeType;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.db.entity.Team;
import org.adimadim.facade.RaceFacade;
import org.adimadim.facade.RaceScoreFacade;
import org.adimadim.facade.TeamFacade;
import org.adimadim.service.exception.RaceException;

/**
 *
 * @author Adem
 */
@Stateless
public class RaceService {
    
    @Inject private RaceFacade raceFacade;
    @Inject private RaceScoreFacade raceScoreFacade;
    @Inject private TeamFacade teamFacade;

    public Race retrieveRace(Integer raceId) throws Exception{
        return raceFacade.find(raceId);
    } 
    
    public List<Race> retrieveAllRaces() throws Exception{
        return raceFacade.findAllByNamedQuery("Race.findAllByDateOrderDesc");
    } 
    
    public List<RaceScore> retrieveRaceScoreByRaceId(Integer raceId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        return raceScoreFacade.findAllByNamedQuery("RaceScore.findByRaceIdByTimeOrder", map);
    }    
    
    public List<Team> retrieveTeamsByRaceId(Integer raceId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        return teamFacade.findAllByNamedQuery("Team.findAllByRaceId", map);
    }    
    
    public List<RaceScore> retrieveRaceScoreByRaceIdAndTeamId(Integer raceId, Integer teamId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        map.put("teamId", teamId);
        return raceScoreFacade.findAllByNamedQuery("RaceScore.findByRaceIdAndTeamId", map);
    }
    
    public List<RaceScore> retrieveTeamMembersByRaceIdAndTeamId(Integer raceId, Integer teamId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        map.put("teamId", teamId);
        return raceScoreFacade.findAllByNamedQuery("TeamMember.findByRaceIdAndTeamId", map);
    }
    
    private Integer raceScoreCountByRaceId(Integer raceId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        String jpqlString = "select count(r) from RaceScore r where r.raceScorePK.raceId=:raceId";
        Long raceScoreCount = (Long)raceFacade.findByQuery(jpqlString, map, null);
        raceScoreCount = (raceScoreCount == null ? 0 : raceScoreCount);
        return raceScoreCount.intValue();
    }
    
    public void createRace(Race race) throws RaceException,Exception{
        race.setRaceId(getNextRaceId());
        raceFacade.create(race);
    }

    public void deleteRace(Race race) throws RaceException,Exception{
        if(raceScoreCountByRaceId(race.getRaceId()) > 0){
            throw new RaceException("Bu yarış tamamlandığı için silinemez.");
        }
        raceFacade.remove(race);
    }
    
    public void updateRace(Race race) throws RaceException,Exception{
        raceFacade.edit(race);
    }
             
    private Integer getNextRaceId() throws Exception{
        String jpqlString = "select max(a.raceId) from Race a";
        Integer raceId = (Integer)raceFacade.findByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
        raceId = (raceId == null ? 0 : raceId) + 1;
        return raceId;
    }

    public void createTeam(Team team) throws RaceException,Exception{
        Integer raceId = team.getTeamPK().getRaceId();
        team.getTeamPK().setTeamId(getNextTeamId(raceId));
        teamFacade.create(team);
    }

    public void updateTeam(Team team) throws RaceException,Exception{
        teamFacade.edit(team);
    }
    
    public void deleteTeam(Team team) throws RaceException,Exception{
        if(getTeamMemberCountByRaceIdAndTeamId(team.getTeamPK().getRaceId(), team.getTeamPK().getTeamId()) > 0){
            throw new RaceException("Bu takıma ait üyeler olduğu için silinemez.");
        }
        teamFacade.remove(team);
    }
    
    private Integer getNextTeamId(Integer raceId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        String jpqlString = "select max(a.teamPK.teamId) from Team a where a.teamPK.raceId = :raceId";
        Integer teamId = (Integer)raceFacade.findByQuery(jpqlString, map, LockModeType.PESSIMISTIC_READ);
        teamId = (teamId == null ? 0 : teamId) + 1;
        return teamId;
    }

    private int getTeamMemberCountByRaceIdAndTeamId(Integer raceId, Integer teamId) throws Exception{
        Map map = new HashMap();
        map.put("raceId", raceId);
        map.put("teamId", teamId);
        String jpqlString = "SELECT count(t) FROM TeamMember t WHERE t.teamMemberPK.raceId = :raceId and t.teamMemberPK.teamId = :teamId";
        Long count = (Long)raceFacade.findByQuery(jpqlString, map, null);
        count = (count == null ? 0 : count);
        return count.intValue();
    }
    
}
