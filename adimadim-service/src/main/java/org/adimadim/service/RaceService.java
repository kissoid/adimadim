/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.db.entity.Team;
import org.adimadim.db.entity.TeamMember;
import org.adimadim.facade.RaceFacade;
import org.adimadim.facade.RaceScoreFacade;
import org.adimadim.facade.TeamFacade;
import org.adimadim.facade.TeamMemberFacade;
import org.adimadim.service.exception.RaceException;

/**
 *
 * @author Adem
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Stateless(name = "raceService")
public class RaceService {

    @Inject
    private RaceFacade raceFacade;
    @Inject
    private RaceScoreFacade raceScoreFacade;
    @Inject
    private TeamFacade teamFacade;
    @Inject
    private TeamMemberFacade teamMemberFacade;

    public Race retrieveRace(Integer raceId) throws Exception {
        return raceFacade.find(raceId);
    }

    public List<Race> retrieveAllRaces() throws Exception {
        return raceFacade.findListByNamedQuery("Race.findAllOrderByIdDesc");
    }

    public List<Race> retrieveAllActiveRaces() throws Exception {
        String jpql = "select r from Race r where r.active='E' order by r.raceId desc";
        return raceFacade.findListByQuery(jpql);
    }

    public List<RaceScore> retrieveRaceScoreByRaceId(Integer raceId) throws Exception {
        Map map = new HashMap();
        map.put("raceId", raceId);
        return raceScoreFacade.findListByNamedQuery("RaceScore.findByRaceIdByTimeOrder", map);
    }

    public List<Team> retrieveTeamsByRaceId(Integer raceId) throws Exception {
        String jpql = "select t from Team t where t.race.raceId = :raceId";
        Map map = new HashMap();
        map.put("raceId", raceId);
        return teamFacade.findListByQuery(jpql, map);
    }

    public List<RaceScore> retrieveRaceScoreByRaceIdAndTeamId(Integer raceId, Integer teamId) throws Exception {
        Map map = new HashMap();
        map.put("raceId", raceId);
        map.put("teamId", teamId);
        return raceScoreFacade.findListByNamedQuery("RaceScore.findByRaceIdAndTeamId", map);
    }

    public List<RaceScore> retrieveTeamMembersByRaceIdAndTeamId(Integer raceId, Integer teamId) throws Exception {
        Map map = new HashMap();
        map.put("raceId", raceId);
        map.put("teamId", teamId);
        return raceScoreFacade.findListByNamedQuery("TeamMember.findByRaceIdAndTeamId", map);
    }

    private Integer raceScoreCountByRaceId(Integer raceId) throws Exception {
        Map map = new HashMap();
        map.put("raceId", raceId);
        String jpqlString = "select count(r) from RaceScore r where r.raceScorePK.raceId=:raceId";
        Long raceScoreCount = (Long) raceFacade.findValueByQuery(jpqlString, map, null);
        raceScoreCount = (raceScoreCount == null ? 0 : raceScoreCount);
        return raceScoreCount.intValue();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createRace(Race race) throws RaceException, Exception {
        race.setRaceId(getNextRaceId());
        raceFacade.save(race);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteRace(Race race) throws RaceException, Exception {
        if (raceScoreCountByRaceId(race.getRaceId()) > 0) {
            throw new RaceException("Bu yarış tamamlandığı için silinemez.");
        }
        raceFacade.remove(race);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateRace(Race race) throws RaceException, Exception {
        raceFacade.update(race);
    }

    private Integer getNextRaceId() throws Exception {
        String jpqlString = "select max(a.raceId) from Race a";
        Integer raceId = (Integer) raceFacade.findValueByQuery(jpqlString, LockModeType.PESSIMISTIC_WRITE);
        raceId = (raceId == null ? 0 : raceId) + 1;
        return raceId;
    }

    public Race findNextTeamRace() throws Exception {
        String jpql = "select r from Race r where r.isTeamRace = :isTeamRace and r.raceDate >= :date";
        Map map = new HashMap();
        map.put("isTeamRace", Boolean.TRUE);
        map.put("date", new Date());
        return raceFacade.findByQuery(jpql, map);
    }

    public List<RaceScore> findTeamResultList(Team team) throws Exception {
        List<Integer> accountIdList = new ArrayList<Integer>();
        for (TeamMember teamMember : team.getTeamMemberList()) {
            accountIdList.add(teamMember.getAccount().getAccountId());
        }
        String jpql = "select r from RaceScore r  ";
        jpql += " where r.race.raceId = :raceId ";
        jpql += " and r.account.accountId in :accountIdList ";
        Map map = new HashMap();
        map.put("raceId", team.getRace().getRaceId());
        map.put("accountIdList", accountIdList);
        return raceScoreFacade.findListByQuery(jpql, map);
    }

}
