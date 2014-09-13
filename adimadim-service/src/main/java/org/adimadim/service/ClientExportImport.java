/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.adimadim.db.dto.RaceScoreDto;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.Race;
import org.adimadim.db.entity.RaceScore;

/**
 *
 * @author Ergo
 */
@Stateless
@WebService
public class ClientExportImport {

    @Inject
    private AccountService accountService;
    @Inject
    private RaceService raceService;
    @Inject
    private RaceScoreService raceScoreService;

    /**
     * Web service operation
     *
     * @param raceScores
     * @return
     */
    @WebMethod(operationName = "saveRaceScores")
    public String saveRaceScores(@WebParam(name = "raceScores") List<RaceScoreDto> raceScores) {
        try {
            List<RaceScore> raceScoreList = new ArrayList<RaceScore>();
            for (RaceScoreDto raceScoreDto : raceScores) {
                RaceScore raceScore = new RaceScore();
                raceScore.setRaceScoreId(raceScoreDto.getRaceScoreId());
                raceScore.setDuration(raceScoreDto.getDuration());
                raceScore.setAccount(accountService.retrieveAccount(raceScoreDto.getAccountId()));
                raceScore.setRace(raceService.retrieveRace(raceScoreDto.getRaceId()));
                raceScore.setOrderNo(raceScoreDto.getOrderNo());
                raceScoreList.add(raceScore);
            }
            raceScoreService.saveRaceScores(raceScoreList);
            return "Kayıt işlemi başarılı";
        } catch (Exception ex) {
            return ("Server error : " + ex.getMessage());
        }
    }

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "retrieveRaces")
    public List<Race> retrieveRaces() {
        try {
            return raceService.retrieveAllRaces();
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Web service operation
     *
     * @param startAccountId
     * @param count
     * @return
     */
    @WebMethod(operationName = "retrieveAccounts")
    public List<Account> retrieveAccounts(@WebParam(name = "startAccountId") Integer startAccountId, @WebParam(name = "count") Integer count) {
        try {
            return accountService.retrieveAccountRangeStartByAccountId(startAccountId, count);
        } catch (Exception ex) {
            Logger.getLogger(ClientExportImport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @WebMethod(operationName = "createRace")
    public String createRace(@WebParam(name = "race") Race race) {
        try {
            Race tempRace = new Race();
            tempRace.setRaceName(race.getRaceName());
            tempRace.setRaceDate(race.getRaceDate());
            tempRace.setActive(race.getActive());
            raceService.createRace(tempRace);
            return "Kayıt işlemi başarılı";
        } catch (Exception ex) {
            return ("Server error : " + ex.getMessage());
        }
    }

    @WebMethod(operationName = "updateRace")
    public String updateRace(@WebParam(name = "race") Race race) {
        try {
            Race tempRace = raceService.retrieveRace(race.getRaceId());
            if (tempRace != null) {
                tempRace.setRaceName(race.getRaceName());
                tempRace.setRaceDate(race.getRaceDate());
                tempRace.setActive(race.getActive());
                raceService.updateRace(tempRace);
                return "Güncelleme işlemi başarılı";
            } else {
                return "Güncellenecek kayıt bulunamadı";
            }
        } catch (Exception ex) {
            return ("Server error : " + ex.getMessage());
        }
    }

}