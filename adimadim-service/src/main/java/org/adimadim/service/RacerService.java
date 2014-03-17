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
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.RaceScore;
import org.adimadim.facade.AccountFacade;
import org.adimadim.facade.RaceFacade;
import org.adimadim.facade.RaceScoreFacade;

/**
 *
 * @author Adem
 */
@Stateless
public class RacerService {

    @Inject
    private AccountFacade accountFacade;
    @Inject
    private RaceFacade raceFacade;
    @Inject
    private RaceScoreFacade raceScoreFacade;

    public List<Account> retrieveAllRacers() throws Exception {
        return accountFacade.findAllByNamedQuery("Account.findAllByIdOrder", null);
    }
    
    public List<Account> retrieveAllRacersHasNoPassword() throws Exception {
        return accountFacade.findAllByNamedQuery("Account.findAll", null);
        //return accountFacade.findAllByNamedQuery("Account.findAllHasNoPasswordByIdOrder", null);
    }

    public List<RaceScore> getRacerScoresByAccountId(Integer accountId) throws Exception {
        Map map = new HashMap();
        map.put("accountId", accountId);
        return raceScoreFacade.findAllByNamedQuery("RaceScore.findByAccountId", map, null);
    }
    
}
