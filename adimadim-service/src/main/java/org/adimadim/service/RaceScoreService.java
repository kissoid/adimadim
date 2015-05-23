/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import org.adimadim.db.entity.RaceScore;
import org.adimadim.facade.RaceScoreFacade;

/**
 *
 * @author Ergo
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RaceScoreService {

    @Inject
    private RaceScoreFacade raceScoreFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void saveRaceScores(List<RaceScore> raceScores) throws Exception {
        for (RaceScore raceScore : raceScores) {
            RaceScore tempRaceScore = retrieveRaceScoreByAccountIdRaceId(raceScore.getAccount().getAccountId(), raceScore.getRace().getRaceId());
            if(tempRaceScore != null){
                raceScore.setRaceScoreId(tempRaceScore.getRaceScoreId());
            }
            raceScoreFacade.update(raceScore);
        }
    }

    public RaceScore retrieveRaceScoreByAccountIdRaceId(Integer accountId, Integer raceId) throws Exception {
        Map params = new HashMap();
        params.put("accountId", accountId);
        params.put("raceId", raceId);
        return raceScoreFacade.findByNamedQuery("RaceScore.findByAccountIdRaceId", params, null);
    }
}
